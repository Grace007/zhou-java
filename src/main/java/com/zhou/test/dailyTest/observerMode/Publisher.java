package com.zhou.test.dailyTest.observerMode;

import java.util.Observable;

/**
 * @author eli
 * @date 2017/12/1 14:14
 */
public class Publisher extends Observable {
    private String data="";

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
        setChanged();
        notifyObservers();
    }


    /**
     * 设置内部状态为已改变
     */
    @Override
    protected synchronized void setChanged() {
        super.setChanged();
    }

    /**
     * 通知观察者所发生的改变，参数obj是一些改变的信息
     */
    @Override
    public void notifyObservers() {
        super.notifyObservers();
    }


}
