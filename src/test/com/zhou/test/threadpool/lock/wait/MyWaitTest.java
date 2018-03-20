package com.zhou.test.threadpool.lock.wait;

/**
 * @author eli
 * @date 2018/3/20 11:46
 */
public class MyWaitTest {

    public static void main(String[] args) throws Exception {
        MyWaitNotify myWaitNotify = new MyWaitNotify();
        Thread thread_notify = new Thread(new Runnable() {
            @Override
            public void run() {
                myWaitNotify.doNotify();
            }
        });
        Thread thread_wait = new Thread(new Runnable() {
            @Override
            public void run() {
                myWaitNotify.doWait();
            }
        });
        Thread thread_notify01 = new Thread(new Runnable() {
            @Override
            public void run() {
                myWaitNotify.doNotify();
            }
        });
        thread_wait.start();
        Thread.sleep(10);

        thread_notify.start();
        Thread.sleep(10);



        thread_notify01.start();
        Thread.sleep(10);
    }
}
