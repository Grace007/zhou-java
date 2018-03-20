package com.zhou.test.jvm;

import org.junit.Test;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * @author eli
 * @date 2018/3/19 16:55
 */
public class JvmTest {
    @Test
    public void test1(){
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] threadInfos=threadMXBean.dumpAllThreads(false,false);
        for (ThreadInfo threadInfo : threadInfos) {
            System.out.println(threadInfo.getThreadId()+"-"+threadInfo.getThreadName());
        }
//        6-Monitor Ctrl-Break代码在 idea 中运行，会多出一个 Monitor Ctrl-Break 线程
//        5-Attach Listener //添加事件
//        4-Signal Dispatcher //分发处理发送给JVM信号的线程
//        3-Finalizer //调用对象的finalize方法的线程，就是垃圾回收的线程
//        2-Reference Handler //清除reference的线程
//        1-main //主线程
    }
}
