package com.zhou.test.jms.springActivemq;

import com.zhou.util.SpringContextUtil;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.jms.*;

/**
 * @author eli
 * @date 2017/11/30 11:19
 */
public class Comsumer implements MessageListener {
    @Autowired
    private static ActiveMQConnectionFactory activemqConnectionFactory;
    private Connection conn = null;
    @Autowired
    private Destination destination;
    private Session session = null;
    MessageConsumer messageConsumer=null;
    public  void receive() {
        try {
            activemqConnectionFactory = SpringContextUtil.getBean(ActiveMQConnectionFactory.class);
            conn = activemqConnectionFactory.createConnection();
            conn.start();
            destination = (Destination) SpringContextUtil.getBean("destination");
            session = conn.createSession(true, Session.AUTO_ACKNOWLEDGE);
            messageConsumer = session.createConsumer(destination);
            messageConsumer.setMessageListener(this);
        }catch (Exception e){
            System.out.println("接受消息失败..");
            e.printStackTrace();
        }
    }

    /**
     * 如果在消费端的onMessage中没有session.commit()，那么这条消息可以正常被接收，但不会被消费，换句话説客户端只要不commit这条消息，这条消息可以被客户端无限消费下去，直到commit（从MQ所persistent的DB中被删除）。
     * @param message
     */
    @Override
    public void onMessage(Message message) {
        try {
            TextMessage textMessage = (TextMessage) message;
            System.out.println("textMessage = " + textMessage.getText());
            session.commit();
        } catch (JMSException e) {
            e.printStackTrace();
            try {
                session.rollback();
            } catch (JMSException e1) {
            }
        }
    }

    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("classpath:spring/applicationContext*.xml");
        Comsumer comsumer =new Comsumer();
        comsumer.receive();
    }
}
