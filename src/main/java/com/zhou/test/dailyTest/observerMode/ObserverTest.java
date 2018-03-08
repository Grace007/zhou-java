package com.zhou.test.dailyTest.observerMode;

/**
 * @author eli
 * @date 2017/12/1 14:21
 */
public class ObserverTest {
    public static void main(String[] args) {

        Publisher publisher = new Publisher();
        Subscriber subscriber1 = new Subscriber("观察者1");
        Subscriber subscriber2 = new Subscriber("观察者2");
        publisher.addObserver(subscriber1);
        publisher.addObserver(subscriber2);
        publisher.setData("小二");
        publisher.setData("打烊了");
    }
}
