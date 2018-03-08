package com.zhou.test.jms.springActivemq;

import com.zhou.util.SpringContextUtil;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.annotation.Resource;
import javax.jms.*;

/**
 *
 * @author eli
 * @date 2017/11/30 11:19
 */
public class Producter {

    @Resource
    private ActiveMQConnectionFactory activemqConnectionFactory;
    private Connection conn = null;
    @Autowired
    private Destination destination;
    private Session session = null;
    private MessageProducer producer = null;

    public  void  sendMessage(String data) {
        try {
            activemqConnectionFactory = SpringContextUtil.getBean(ActiveMQConnectionFactory.class);
            conn = activemqConnectionFactory.createConnection();
            destination = (Destination) SpringContextUtil.getBean("destination");
            session = conn.createSession(true, Session.AUTO_ACKNOWLEDGE);

            MessageProducer messageProducer = session.createProducer(destination);
            TextMessage textMessage = session.createTextMessage(data);
            messageProducer.send(textMessage);
            try {
                session.commit();
            } catch (JMSException e) {
                try {
                    session.rollback();
                } catch (Exception ex) {
                }
            }
        }catch (Exception e){
            System.out.println("发送消息失败..");
            e.printStackTrace();
        }finally {
            try {
                producer.close();
                producer = null;
            } catch (Exception e) {
            }
            try {
                session.close();
                session = null;
            }  catch (Exception e) {
        }
        try {
            conn.stop();
        } catch (Exception e) {
        }
        try {
            conn.close();
        } catch (Exception e) {
        }
    }
}

    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("classpath:spring/applicationContext*.xml");
        Producter producter = new Producter();
        producter.sendMessage("第一条消息发送...");

    }

}
