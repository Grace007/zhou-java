package com.zhou.test.threadpool.AQS;

/**
 * 不可重入的独占锁接口
 * @author eli
 * @date 2018/3/20 15:23
 */
public interface Mutex {
    //获取锁
    public void lock();
    //释放锁
    public void release();
}
