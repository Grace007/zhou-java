package com.zhou.test.bds.mycat;

import org.junit.Test;
import org.nutz.dao.impl.NutDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @author eli
 * @date 2017/12/21 18:03
 */
public class MycatJoinTest {
    @Test
    public void test1(){
        ApplicationContext ctx = new FileSystemXmlApplicationContext("src/test/com/zhou/test/bds/mycat/dataSource.xml");
        NutDao mycatDao = (NutDao) ctx.getBean("tDao");
        //模拟Customer数据

        List<Customer> customerList = new ArrayList();
        for (int i=1;i<=100;i++){
            Customer customer =new Customer();
            customer.setId(i);
            customer.setUsername("test"+i);
            customerList.add(customer);
        }
        System.out.println("customerList = " + customerList.size());
        try {
            mycatDao.fastInsert(customerList);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    @Test
    public void test2(){
        ApplicationContext ctx = new FileSystemXmlApplicationContext("src/test/com/zhou/test/bds/mycat/dataSource.xml");
        NutDao mycatDao = (NutDao) ctx.getBean("tDao");
        //模拟Customer数据

        List<Order> orderList = new ArrayList();
        for (int i=1;i<=100;i++){
            Order order =new Order();
            order.setId(i+20);
            order.setCustomer_id(i);
            order.setRemark("remark"+i);
            orderList.add(order);
        }
        System.out.println("orderList = " + orderList.size());
        try {
            mycatDao.fastInsert(orderList);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    @Test
    public void test3(){
        ApplicationContext ctx = new FileSystemXmlApplicationContext("src/test/com/zhou/test/bds/mycat/dataSource.xml");
        NutDao mycatDao = (NutDao) ctx.getBean("tDao");

        List<Order> orderList = new ArrayList();
        for (int i=1;i<=100;i++){
            Order order =new Order();
            order.setCustomer_id(i);
            order.setRemark("remark"+(i+1000));
            orderList.add(order);
        }
        System.out.println("orderList = " + orderList.size());
        try {
            mycatDao.fastInsert(orderList);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
