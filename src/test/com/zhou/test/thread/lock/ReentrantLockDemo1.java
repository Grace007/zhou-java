package com.zhou.test.thread.lock;

import org.junit.Test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 *
 * @author eli
 * @date 2018/3/20 15:51
 */
public class ReentrantLockDemo1 {



    public static void main(String[] args) {
        Lock lock=new ReentrantLock();
        new Thread("Thread A"){
            @Override
            public void run() {
                lock.lock();//加锁
                try{
                    work();//work
                }finally {
                    lock.unlock();//释放锁
                }

            }
        }.start();
        new Thread("Thread B"){
            @Override
            public void run() {
                lock.lock();//加锁
                try{
                    work();//work
                }finally {
                    lock.unlock();//释放锁
                }

            }
        }.start();
    }

    public static void work(){
        try {
            System.out.println(Thread.currentThread().getName()+" started to work,currrentTime:"+System.currentTimeMillis());
            Thread.currentThread().sleep(1000);
            System.out.println(Thread.currentThread().getName()+" end work,currrentTime:"+System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//      Lock模版使用方法
//    Lock lock = new ReentrantLock();
//        ...
//                lock.lock();//获取锁
//        try {
//            ...
//    } finally {
//        lock.unlock();//释放锁
//    }


    /**
     * getHoldCount()，这个方法表示的是当前线程持有锁的次数
     */
    @Test
    public void ReentrantLickTest01(){
        ReentrantLock lock=new ReentrantLock();
        System.out.println(lock.getHoldCount());//没有调用lock之前，hold count为0
        lock.lock();//holdCount+1
        System.out.println(lock.getHoldCount());
        lock.lock();//holdCount+1
        System.out.println(lock.getHoldCount());
        lock.unlock();//holdCount-1
        System.out.println(lock.getHoldCount());
        lock.unlock();//holdCount-1
        System.out.println(lock.getHoldCount());
    }
}
