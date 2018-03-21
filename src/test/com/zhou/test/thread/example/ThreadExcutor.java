package com.zhou.test.thread.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author eli
 * @date 2018/3/12 16:33
 */
public class ThreadExcutor {

    //创建
    private volatile boolean RUNNING = true;

    //所有任务都放队列中，让工作线程来消费
    private static BlockingQueue<Runnable> queue = null;

    private final HashSet<Worker> workers = new HashSet<Worker>();

    /**
     * 记录threadList
     */
    private final List<Thread> threadList = new ArrayList<Thread>();

    //工作线程数
    int poolSize = 0;
    //核心线程数（创建了多少个工作线程）
    int coreSize = 0;

    boolean shutdown = false;

    public ThreadExcutor(int poolSize){
        this.poolSize = poolSize;
        queue = new LinkedBlockingQueue<Runnable>(poolSize);
    }

    /**
     * coreSize小于poolSize, 添加线程，反之放到堵塞队列，有线程池中的线程执行
     * @param runnable
     */
    public void exec(Runnable runnable) {
        if (runnable == null) throw new NullPointerException();
        if(coreSize < poolSize){
            addThread(runnable);
        }else{
            //System.out.println("offer" +  runnable.toString() + "   " + queue.size());
            try {
                queue.put(runnable);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 添加任务：将Runnable封装成Worker，并由Worker构建成一个Thread，添加在threadList上，并start当前线程，调用worker的run方法
     * @param runnable
     */
    public void addThread(Runnable runnable){
        coreSize ++;
        Worker worker = new Worker(runnable);
        workers.add(worker);
        Thread t = new Thread(worker);
        threadList.add(t);
        try {
            t.start();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void shutdown() {
        RUNNING = false;
        if(!workers.isEmpty()){
            for (Worker worker : workers){
                worker.interruptIfIdle();
            }
        }
        shutdown = true;
        Thread.currentThread().interrupt();
    }
    /**
     * 这个内部类Worker是用来执行每个任务的，在创建线程池后，往线程里添加任务，每个任务都是由Worker一个一个来启动的。
     * 这个工作线程实例化的时候就先加入一个任务到队列中，也就是说在实例化这个工作线程时，这个工作线程也是一个任务被加入到线程池中。然后就是run方法，这个run方法是线程调start方法生成的线程，而Worker调的run方法并没有生成新的线程。就是一个循环，一直在不停的从队列中取任务，然后执行。可以看到，取队列的方法是take()，这个方法意思如果队列为空了，取不到数据时就阻塞队列。
     *
     * 当coreSize小于poolSize时，创建worker并添加执行其中任务
     */
    class  Worker implements Runnable{

        /**
         * 向队列中插入一个线程，是LinkedBlockingQueue，但是是固定容量
         * @param runnable
         */
        public Worker(Runnable runnable){
            queue.offer(runnable);
        }

        /**
         * 从队列中取出任务并执行
         */
        @Override
        public void run() {
            while (true && RUNNING){
                if(shutdown == true){
                    Thread.interrupted();
                }
                Runnable task = null;
                try {
                    task = getTask();
                    task.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * 取出一个任务（线程对象）
         * @return
         * @throws InterruptedException
         */
        public Runnable getTask() throws InterruptedException {
            return queue.take();
        }

        /**
         * 中断线程
         */
        public void interruptIfIdle() {
            for (Thread thread :threadList) {
                System.out.println(thread.getName() + " interrupt");
                thread.interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadExcutor excutor = new ThreadExcutor(3);
        for (int i = 0; i < 10; i++) {
            excutor.exec(new Runnable() {
                @Override
                public void run() {
                    System.out.println("线程 " + Thread.currentThread().getName() + " 在帮我干活");
                }
            });
        }
        excutor.shutdown();
    }
}
