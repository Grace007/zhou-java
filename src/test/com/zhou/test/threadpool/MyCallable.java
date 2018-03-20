package com.zhou.test.threadpool;

import java.util.concurrent.Callable;

/**
 * @author eli
 * @date 2017/11/27 15:35
 *
 */

/**
 * 	Callable接口：与Runnable接口功能相似，用来指定线程的任务。其中的call()方法，用来返回线程任务执行完毕后的结果，call方法可抛出异常。
 */
public class MyCallable implements Callable<Integer> {
    public static int count=0;
    Object object =new Object();
    String name="";
    MyCallable(){
        count = 100;
    }
    MyCallable(int count1,String name){
        count = count1;
        this.name=name;
    }


    @Override
    public Integer call() throws Exception {
        synchronized (object) {
            while (count > 0) {
//                Thread.sleep(1000);
                count--;
                System.out.println("name:" + Thread.currentThread().getName() + "  count = " + count);
            }
        }
        return 1;
    }

}
