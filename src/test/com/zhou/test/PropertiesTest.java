package com.zhou.test;

import com.zhou.util.PropertiesUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.net.URL;
import java.util.Properties;

/**
 * @author eli
 * @date 2017/11/20 14:11
 */
public class PropertiesTest {
    @Test
    public void propertiesTest1(){
        final Logger logger = Logger.getLogger(PropertiesTest.class);

        PropertiesUtils propertiesUtils = new PropertiesUtils();
        //获取配置文件所在的路径
        URL path = this.getClass().getClassLoader().getResource("log4j.properties");
        System.out.println("path = " + path.getPath());
        logger.info("测试Log4j");
        Properties properties = propertiesUtils.readLocalProp(path.getPath());
        //propertiesUtils.readPropInfo(properties);
        //遍历输出Properties
        for(String key : properties.stringPropertyNames()){
            System.out.println(key+" = " + properties.getProperty(key));
        }
        //把Properties对象下载的本地
        propertiesUtils.saveToLocal(properties,"F:/HCR/Java/IdeaProjects/zhou-demo/target/classes/log4jTest.properties");

    }

}
