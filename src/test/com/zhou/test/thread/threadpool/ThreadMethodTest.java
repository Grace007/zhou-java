package com.zhou.test.thread.threadpool;

import org.junit.Test;

import java.util.Date;

/**
 * @author eli
 * @date 2018/3/19 18:28
 */
public class ThreadMethodTest {
    @Test
    public void test1(){
        Thread t=new Thread(){
            @Override
            public void run() {
                //预期循环10次
                for (int i = 0; i <10 ; i++) {
                    try {
                        Thread.sleep(1000);
                        System.out.println("自定义线程:当前时间："+new Date().toLocaleString());
                    } catch (InterruptedException e) {//这个异常由sleep方法抛出
                        e.printStackTrace();
                        System.out.println("自定义线程:收到中断信号，总共循环了"+i+"次...");
                        //接受到中断信号时，停止运行,不用return也可以
                        //return;
                    }
                }
            }
        };
        t.start();
        //主线程休眠3秒

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("主线程：等待3秒后发送中断信号...");
        t.interrupt();
    }

    @Test
    public void test02(){
        Thread t=new Thread(){
            @Override
            public void run() {
                int i=0;
                while (true){
                    //每次打印前都判断是否被中断
                    if (!Thread.interrupted()) {
                        i++;
                        System.out.println("自定义线程，打印...."+i+"次");
                    }else{//如果被中断，停止运行
                        System.out.println("自定义线程：被中断...");
                        return;
                    }

                }

            }
        };
        t.start();
        //主线程休眠1毫秒，以便自定义线程执行
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("主线程:休眠1毫秒后发送中断信号...");
        t.interrupt();
    }

    @Test
    public void test03() throws Exception{
        long start=System.currentTimeMillis();
        System.out.println("程序开始运行...");
        Thread t=new Thread(){
            @Override
            public void run() {
                try {//模拟自定义线程干某个事花了5秒
                    System.out.println("自定义线程执行开始...");
                    Thread.sleep(5000);
                    System.out.println("自定义线程执行完毕...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();

        //模拟主线干其他事花了2秒
        Thread.sleep(2000);
        System.out.println("主线程执行完毕，等待t线程执行完成...");
        //主线程2秒后就可以继续执行，但是其必须执行的条件是t线程必须执行完成，此时调用t的join方法
        long joinstart=System.currentTimeMillis();
        t.join();
        System.out.println("主线程：t执行已经执行完毕...，等待了"+(System.currentTimeMillis()-joinstart)/1000+"秒");
        System.out.println("程序运行总时间..."+(System.currentTimeMillis()-start)/1000+"秒");
    }


}
