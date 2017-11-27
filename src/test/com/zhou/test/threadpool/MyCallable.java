package com.zhou.test.threadpool;

import java.util.concurrent.Callable;

/**
 * @author eli
 * @date 2017/11/27 15:35
 */
public class MyCallable implements Callable<Integer> {
    public static int count=0;
    Object object =new Object();
    String name="";
    int x;
    int y;
/*    MyCallable() {
        this.x=10;
        this.y=20;
    }
    MyCallable(int x,int y){
        this.x=x;
        this.y=y;
    }*/
    MyCallable(){
        count = 100;
    }
    MyCallable(int count1,String name){
        count = count1;
        this.name=name;
    }

    @Override
    public Integer call() throws Exception {
        return null;
    }

/*    @Override
    public Integer call() throws Exception {
        //System.out.println("x+y = " + (x+y));
        synchronized (object) {
            while (count > 0) {
                Thread.sleep(1000);
                count--;
                System.out.println("name:" + Thread.currentThread().getName() + "  count = " + count);
            }
        }
        return 1;
    }*/

}
