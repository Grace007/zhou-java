package com.zhou.test.thread.CountDownLatch;

import java.util.concurrent.CountDownLatch;

/**
 * 主线程就是调用线程，主线中设置了CountDownLatch的值为2，并启动两个线程，每个线程执行完成之后将CountDownLatch减1，最后主线程中调用了latch.await()。此时主线程就会等到CountDownLatch值为0时才能继续往下执行。也是说，必须等到两个线程执行完成之后，才能执行。需要注意的是，如果CountDownLatch设置的值大于2的话，那么主线程就会一直等待下去，因为CountDownLatch的值即使减去2次，还是大于0，主线程只能一直等待。
 * @author eli
 * @date 2018/3/20 17:17
 */
public class CountDownLatchTest {
    public static void main(String[] args) {
        final CountDownLatch latch = new CountDownLatch(2);

        new Thread(){
            public void run() {
                try {
                    System.out.println("子线程"+Thread.currentThread().getName()+"正在执行");
                    Thread.sleep(3000);
                    System.out.println("子线程"+Thread.currentThread().getName()+"执行完毕");
                    latch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
        }.start();

        new Thread(){
            public void run() {
                try {
                    System.out.println("子线程"+Thread.currentThread().getName()+"正在执行");
                    Thread.sleep(3000);
                    System.out.println("子线程"+Thread.currentThread().getName()+"执行完毕");
                    latch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
        }.start();

        try {
            System.out.println("等待2个子线程执行完毕...");
            latch.await();
            System.out.println("2个子线程已经执行完毕");
            System.out.println("继续执行主线程");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
