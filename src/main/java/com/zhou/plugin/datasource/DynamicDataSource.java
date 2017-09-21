package com.zhou.plugin.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * AbstractRoutingDataSource抽象类（该类充当了DataSource的路由中介, 能有在运行时, 根据某种key值来动态切换到真正的DataSource上。）
 *
 * 扩展AbstractRoutingDataSource类，并重写其中的determineCurrentLookupKey()方法，来实现数据源的切换
 * @author eli
 * @date 2017/9/20 14:03
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    private static Logger logger = LoggerFactory.getLogger(DynamicDataSource.class);
    //变量是同一个，但是每个线程都使用同一个初始值，也就是使用同一个变量的一个新的副本。这种情况之下ThreadLocal就非常使用，比如说DAO的数据库连接，我们知道DAO是单例的，那么他的属性Connection就不是一个线程安全的变量。而我们每个线程都需要使用他，并且各自使用各自的。这种情况，ThreadLocal就比较好的解决了这个问题。
    private static ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    public DynamicDataSource(){
        logger.info("DynamicDataSource初始化");
    }
    protected Object determineCurrentLookupKey() {
        String dataSource = getDataSource();
        logger.info("当前操作使用的数据源：{}", dataSource);
        return dataSource;
    }

    /**
     * 设置数据源
     * @param dataSource
     */
    public static void setDataSource(String dataSource){
        contextHolder.set(dataSource);
    }
    /**
     * 获取数据源
     * @return
     */
    public static String getDataSource(){
        logger.info("DynamicDataSource.getDataSource()");
        String dataSource = contextHolder.get();
        if (dataSource == null ){
            DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getDefault());
        }
        logger.info(contextHolder.get());
        return contextHolder.get() ;
    }

    /**
     * 清除数据源
     */
    public static void clearDataSource() {
        contextHolder.remove();
    }


}
