package com.zhou.test.dailyTest.observerMode;

import java.util.Observable;
import java.util.Observer;

/**
 * @author eli
 * @date 2017/12/1 14:17
 */
public class Subscriber implements Observer{
    private String name;
    private Publisher publisher;
    Subscriber(String name ){
        this.name=name;
    }

    @Override
    public void update(Observable o, Object arg) {
        this.publisher = (Publisher) o;
        System.out.println(name+"观察者发现发布者改变,变化为:"+publisher.getData());
    }
}
