package com.zhou.test.bds.milk;

import com.zhou.test.bds.milk.model.TCompany;
import com.zhou.test.bds.milk.model.TMetadata;
import com.zhou.test.bds.milk.model.TUser;
import org.junit.Test;
import org.nutz.dao.impl.NutDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @author eli
 * @date 2018/2/1 12:20
 */
public class MilkTest {
    @Test
    public void test1(){
        ApplicationContext ctx = new FileSystemXmlApplicationContext("src/test/com/zhou/test/bds/milk/dataSource.xml");
        NutDao biDao = (NutDao) ctx.getBean("biDao");
        TUser tUser = new TUser();
        TCompany tCompany = new TCompany();
        TMetadata tMetadata =new TMetadata();




    }
}
