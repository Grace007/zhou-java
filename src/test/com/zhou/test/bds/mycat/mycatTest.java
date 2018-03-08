package com.zhou.test.bds.mycat;

import org.junit.Test;
import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.impl.NutDao;
import org.nutz.dao.sql.Sql;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @author eli
 * @date 2017/12/20 11:46
 */
public class mycatTest {
    @Test
    public void test1(){
        ApplicationContext ctx = new FileSystemXmlApplicationContext("src/test/com/zhou/test/bds/mycat/dataSource.xml");
        NutDao mycatDao = (NutDao) ctx.getBean("tDao");
        NutDao biDao = (NutDao) ctx.getBean("biDao");
        //User user = mycatDao.fetch(User.class);
        //System.out.println("user = " + user.getUsername());
        /*User user1 = new User();
        user1.setPassword("我是密码");
        user1.setUsername("我是用户名");
        mycatDao.insert(user1);*/
        for (int j=5;j<100;j++) {
            List<EleShopInfo> eleShopInfoList = new ArrayList();
            eleShopInfoList = biDao.query(EleShopInfo.class, Cnd.where("id", "<>", "null").limit(j, 10000));
            System.out.println(j+"###eleShopInfoList = " + eleShopInfoList.size());

            long time1 = System.currentTimeMillis();
            try {
                mycatDao.fastInsert(eleShopInfoList);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("(System.currentTimeMillis()-time1) = " + (System.currentTimeMillis() - time1));


        /*for (int i=0;i<eleShopInfoList.size();i++){
            try {
                mycatDao.insert(eleShopInfoList.get(i));
                System.out.println("i = " + i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
        }

    }
    @Test
    public void test2(){
        ApplicationContext ctx = new FileSystemXmlApplicationContext("src/test/com/zhou/test/bds/mycat/dataSource.xml");
        NutDao mycatDao = (NutDao) ctx.getBean("tDao");
        NutDao biDao = (NutDao) ctx.getBean("biDao");
        Sql sql = Sqls.create("INSERT INTO ele_search_shop_info_all(request_id,task_id,create_date,create_time,restaurant_id,sname,url,average_cost,address,opening_hours,activities,shop_description,score,monthSale,min_price,delivery_detail,shipEfficiency,distance,phone,delivery_fee_detail,promotion_info,payInfo,delivery_mode,keyword,city_name,category,category_id,sub_category,uniqueId,rank,office_id,office_name) VALUES(20161010,20161010,'2016-10-26 00:00:00','2016-10-26 20:35:19','522402','1454''s café （南京西路店）','https://www.ele.me/shop/522402','NULL','NULL','NULL','NULL','NULL','3.7','50','NULL','NULL','29','NULL','NULL','NULL','NULL','已加入“外卖保”计划，食品安全有保障准时必达，超时秒赔','NULL','西藏中路268号来福士广场办公楼1层大堂L1-02(福州路西藏中路)','上海','NULL','NULL','NULL','6840f3107cd34c3ec1c639d51a1510e3',0,'NULL','NULL')\n");
        mycatDao.execute(sql);
    }
    @Test
    public void test3(){
        ApplicationContext ctx = new FileSystemXmlApplicationContext("src/test/com/zhou/test/bds/mycat/dataSource.xml");
        NutDao mycatDao = (NutDao) ctx.getBean("tDao");
        NutDao localDao = (NutDao) ctx.getBean("lDao");
        NutDao biDao = (NutDao) ctx.getBean("biDao");

        for(int j=1;j<=50000;j++) {
            System.out.println(j+"##########" );
            long time1 = System.currentTimeMillis();

            List<EleShopInfo> eleShopInfoList = new ArrayList();
            eleShopInfoList = biDao.query(EleShopInfo.class, Cnd.where("id", "<>", "null").limit(j, 1000));
            System.out.println("sqlserver 取任务时间 = " + (System.currentTimeMillis() - time1));
            time1 = System.currentTimeMillis();
            StringBuffer stringBuffer = new StringBuffer("/*!mycat:catlet=io.mycat.route.sequence.BatchInsertSequence */INSERT INTO ele_search_shop_info_all (uniqueId,restaurant_id,keyword) VALUES ");
            for (int i = 0; i < eleShopInfoList.size(); i++) {
                stringBuffer.append("('" + eleShopInfoList.get(i).getUniqueId() + "',"+"'" + eleShopInfoList.get(i).getRestaurant_id() + "',"+"'" + eleShopInfoList.get(i).getKeyword() + "'),");
            }
            System.out.println("拼接sql = " + (System.currentTimeMillis() - time1));
            String values = stringBuffer.toString();
            values = values.substring(0, values.length()-1);
            System.out.println("values = " + values);
            //Sql sqlClass = Sqls.queryEntity(values);
            //sqlClass.setEntity(mycatDao.getEntity(EleShopInfo.class));
            Sql sqlClass = Sqls.create(values);
            time1 = System.currentTimeMillis();

            try {
                mycatDao.execute(sqlClass);
            } catch (Exception e) {
                System.out.println();
                e.printStackTrace();
            }
            System.out.println("mycat执行时间 = " + (System.currentTimeMillis() - time1));
        /*localDao.create(EleShopInfo.class,false);
        time1=System.currentTimeMillis();
        localDao.execute(sql);
        System.out.println("mysql执行时间 = " + (System.currentTimeMillis()-time1));*/

        }

    }

}
