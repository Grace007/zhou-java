package com.zhou.test.thread.threadpool;

import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * ThreadPoolExecutor 类是JDK提供的ExecutorService接口的默认实现。提供一个可扩展的线程池实现。ExecutorService在Executor接口的基础上，添加了一些可以管理Executor自身生命周期以及任务的生命周期的方法，每个 ThreadPoolExecutor 还维护着一些基本的统计数据，如完成的任务数。
 * @author eli
 * @date 2018/3/20 18:19
 */
public class ThreadPoolExecutorTest {
    //测试corePoolSize和MaximumPoolSize随着任务提交量的变化,以及keepAliveTime与TimeUnit
    @Test
    public void threadPoolExecutorTest() throws InterruptedException {
        /**
         *  corePoolSize和 maximumPoolSize设置的边界自动调整线程池大小
         *  如果运行的线程少于 corePoolSize，则创建新线程来处理请求，即使其他辅助线程是空闲的。如果运行的线程多于 corePoolSize 而少于 maximumPoolSize，则仅当队列满时才创建新线程
         */
        int corePoolSize = 2;
        int maximumPoolSize = 5;
        /**
         * 池中当前有多于 corePoolSize 的线程，则这些多出的线程在空闲时间超过 keepAliveTime 时将会终止
         */
        int keepAliveTime = 5;
        TimeUnit seconds = TimeUnit.SECONDS;
        /**
         * 排队有三种通用策略：
         1、直接提交。工作队列的默认选项是 SynchronousQueue
         2、无界队列。使用无界队列（例如，不具有预定义容量的 LinkedBlockingQueue）将导致在所有 corePoolSize 线程都忙时新任务在队列中等待
         3、有界队列。当使用有限的 maximumPoolSizes 时，有界队列（如 ArrayBlockingQueue）有助于防止资源耗尽，但是可能较难调整和控制
         */
        BlockingQueue workQueue = new SynchronousQueue();
        int taskCount = 5;
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
                keepAliveTime, seconds, workQueue);
        doTest(keepAliveTime, taskCount, threadPoolExecutor);
    }

    private void doTest(int keepAliveTime, int taskCount, ThreadPoolExecutor threadPoolExecutor)
            throws InterruptedException {
        threadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());
        System.out.println("-------threadPoolExecutor刚刚创建----------");
        printPoolSize(threadPoolExecutor);

        for (int i = 1; i <= taskCount; i++) {
            threadPoolExecutor.execute(new Task(threadPoolExecutor,i));
            System.out.print("--------已提交任务"+ i +"个任务--------");
            printPoolSize(threadPoolExecutor);
        }

        //等到所有的任务都执行完
        TimeUnit.SECONDS.sleep(11);//休眠10秒
        System.out.println("---------所有的任务都执行完--------");
        printPoolSize(threadPoolExecutor);

        //此时maximumPoolSize>corePoolSize，当前时间再休眠keepAliveTime时间，测试多出corePoolSize的线程是否能自动销毁
        System.out.println("---------休眠keepAliveTime，测试maximumPoolSize>corePoolSize的部分能否自动回收--------");
        TimeUnit.SECONDS.sleep(keepAliveTime);
        printPoolSize(threadPoolExecutor);
    }

    private void printPoolSize(ThreadPoolExecutor threadPoolExecutor){
        int corePoolSize = threadPoolExecutor.getCorePoolSize();
        int maximumPoolSize = threadPoolExecutor.getMaximumPoolSize();
        int poolSize = threadPoolExecutor.getPoolSize();
        System.out.println("核心线程池大小："+corePoolSize+",最大线程池大小:"+maximumPoolSize+",当前线程池大小:"+poolSize);
    }

    class Task implements Runnable{
        private ThreadPoolExecutor threadPoolExecutor;
        private int taskId;

        public Task(ThreadPoolExecutor threadPoolExecutor,final int taskId) {
            this.threadPoolExecutor = threadPoolExecutor;
            this.taskId = taskId;
        }

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(10);//休眠10秒
                System.out.print("第"+taskId+"个任务执行完:");
                printPoolSize(threadPoolExecutor);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
