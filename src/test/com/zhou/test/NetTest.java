package com.zhou.test;

import com.zhou.util.NetUtils;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author eli
 * @date 2017/11/20 14:51
 */
public class NetTest {
    @Test
    public void netTest1(){
        System.out.println("NetUtils.isConnected() = " + NetUtils.isConnected());

    }
    /**
     * 网上的一篇文章用来说明java中Runtime.exec的一些问题.
     * 比如下面的要想得到javac的信息.执行程序发现报错.java.lang.IllegalThreadStateException: process has not exited
     * 因为exitValue这个方法是不阻塞的，程序在调用这个方法时外部命令并没有返回所以造成了异常的出现，这里是由另外的方法来等待外部命令执行完毕的，就是waitFor方法，这个方法会一直阻塞直到外部命令执行结束，然后返回外部命令执行的结果
     * 但是使用waitfor()会出现隐患,它使主程序堵塞直到子进程结束.但是我们不知道子进程的结束时间,无法控制.
     * 下面的程序换成waitfor()之后,仍然无法得到结果,它没有输出但却一直悬在那里，这是为什么那？
     * DK文档中对此有如此的解释：因为本地的系统对标准输入和输出所提供的缓冲池有效，所以错误的对标准输出快速的写入和从标准输入快速的读入都有可能造成子进程的锁，甚至死锁。
     * 所以我们需要控制输入和输出,netTestForExitValueTrue();
     */
    @Test
    public void netTestForExitValueFalse(){
        try
        {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec("javac");
            int exitVal = proc.exitValue();
            System.out.println("Process exitValue: " + exitVal);
        } catch (Throwable t)
        {
            t.printStackTrace();
        }
    }

    /**
     * 在这个例子里边因为调用的是Javac，而在没有参数的情况下会将提示信息输出到标准出错,所以使用getErrorStream();
     * 有参数的话使用getOutputStream();
     */
    @Test
    public void netTestForExitValueTrue(){
        try
        {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec("java -version");
            InputStream stderr = proc.getErrorStream();
            //InputStream stderr = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(stderr);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            System.out.println("<error></error>");
            while ( (line = br.readLine()) != null)
                System.out.println(line);
            System.out.println("");
            int exitVal = proc.waitFor();
            System.out.println("Process exitValue: " + exitVal);
        } catch (Throwable t)
        {
            t.printStackTrace();
        }
    }
    @Test
    public void netOwnTest1(){
        System.out.println("NetUtils.getLocalHostIP() = " + NetUtils.getLocalHostIP());;

    }

}
