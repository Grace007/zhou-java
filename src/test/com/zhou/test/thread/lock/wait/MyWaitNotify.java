package com.zhou.test.thread.lock.wait;

/**
 * 一个线程操作的时候，doWait()会一直堵塞
 *
 * @author eli
 * @date 2018/3/20 11:01
 */
public class MyWaitNotify {
    MonitorObject myMonitorObject = new MonitorObject();
    boolean wasSignalled = false;

    public void doWait(){
        synchronized(myMonitorObject){
            while(!wasSignalled){
                try{
                    System.out.println("即将wait()");
                    myMonitorObject.wait();
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
            //clear signal and continue running.
            wasSignalled = false;
        }
    }

    public void doNotify(){
        synchronized(myMonitorObject){
            wasSignalled = true;
            System.out.println("即将notify()");
            myMonitorObject.notify();
        }
    }

    /**
     * 操作同一线程，会一直wait
     * @param args
     */
    public static void main(String[] args) {
        MyWaitNotify myWaitNotify = new MyWaitNotify();
        myWaitNotify.doWait();
        myWaitNotify.doNotify();
        myWaitNotify.doNotify();
    }
}

