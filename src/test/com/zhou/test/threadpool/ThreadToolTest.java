package com.zhou.test.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池技术
 * @author eli
 * @date 2017/11/24 15:34
 */
public class ThreadToolTest {
    //public static int count=1000;
    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        MyCallable myCallable = new MyCallable(1000000,"线程1");

        executorService.submit(myCallable);
        executorService.submit(myCallable);
        //int result = future.get();
        //System.out.println("result = " + result);
        executorService.shutdown();;


    }
}
