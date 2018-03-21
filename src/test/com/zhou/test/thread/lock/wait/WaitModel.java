package com.zhou.test.thread.lock.wait;

/**
 * 诡异的java.lang.IllegalMonitorStateException
 *  原因：this.wait这个变量是一个Boolean，并且，在调用this.wait.wait()之前，this.wait执行了一次赋值操作
 *  Boolean型变量在执行赋值语句的时候，其实是创建了一个新的对象。简单的说，在赋值语句的之前和之后，this.wait并不是同一个对象。/
 *  解决方案：一个解决方案是采用java.util.concurrent.atomic中对应的类型，比如这里就应该是AtomicBoolean。采用AtomicBoolean类型，可以保证对它的修改不会产生新的对象。
 * @author eli
 * @date 2018/3/20 10:49
 */
public class WaitModel {
    private Boolean wait = false;

    public boolean pleaseWait() {
        synchronized (this.wait) {
            System.out.println("进入同步代码块wait = " + wait);
            if (this.wait == true) {
                return false;
            }
            this.wait = true;
            try {
                System.out.println("wait成员变量即将等待wait = " + wait);
                this.wait.wait();
            } catch (InterruptedException e) {

            }
            return true;
        }
    }

    public static void main(String[] args) {
        WaitModel waitModel=new WaitModel();
        waitModel.pleaseWait();
    }
}
