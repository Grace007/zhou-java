package com.zhou.test.bds.mycat;

import org.junit.Test;
import org.nutz.dao.Cnd;
import org.nutz.dao.impl.NutDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @author eli
 * @date 2017/12/27 12:02
 */
public class JdDaojiaMyCatTest {

    @Test
    public void importJdComment(){
        ApplicationContext ctx = new FileSystemXmlApplicationContext("src/test/com/zhou/test/bds/mycat/dataSource.xml");
        NutDao mycatDao = (NutDao) ctx.getBean("tDao");
        NutDao o2oDao = (NutDao) ctx.getBean("o2oDao");

        List<JDDaoJiaCommentJobO2o> commentJobList = new ArrayList<>();
        List<JDDaoJiaCommentJob> commentJobMycatList = new ArrayList<>();
        commentJobList = o2oDao.query(JDDaoJiaCommentJobO2o.class, Cnd.where("id", "<>", "null").limit(1, 1000));
        System.out.println("o2oDao.fetch(JDDaoJiaCommentJobO2o.class,Cnd.where(\"id\", \"<>\", \"null\")); = " + o2oDao.fetch(JDDaoJiaCommentJobO2o.class,Cnd.where("id", "<>", "null")));
        System.out.println("commentJobList = " + commentJobList.size());



    }
}
