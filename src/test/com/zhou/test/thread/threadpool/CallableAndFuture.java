package com.zhou.test.thread.threadpool;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * Callable与Future的作用是，我们可以启动一个线程去执行某个任务，而另外一个线程等待获取这个结果后执行响应的操作。
 假设我们有这样一个案例，线程A中进行某种运算，而主线程需要等待其运算结果，以便进行接下来的操作。

 * @author eli
 * @date 2018/3/20 18:30
 */
public class CallableAndFuture {
    public static void main(String[] args) {

        System.out.println("开始时间:" + new Date());
        ExecutorService service = Executors.newSingleThreadExecutor();
        //Future与Callable中的泛型，就是返回值的类型
        Future<String> future = service.submit(new Callable<String>() {
            public String call() throws Exception {
                System.out.println("进入callable的call方法。。。");
                Thread.sleep(2000);
                return "Hello";
            }

        });

        try {
            String result = future.get();// 该方法会进行阻塞，等待执行完成
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("结束时间:" + new Date());
        service.shutdown();
    }
}
