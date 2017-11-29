package com.zhou.test.socket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author eli
 * @date 2017/11/28 9:47
 */
public class CommunicationClient extends JFrame {
    private Socket socket=null;
    private DataOutputStream dos=null;
    private DataInputStream dis=null;
    private Boolean status=false;
    //显示聊天内容
    TextArea taContent = new TextArea();
    //输入框
    TextField tfTxt = new TextField();
    private String localIp="";
    RecvThread recvThread = new RecvThread();

    public static void main(String[] args) {
        new CommunicationClient().start("127.0.0.1",9999);
    }
    private void start(String host,int port){
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            localIp = inetAddress.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        this.setTitle("聊天室");
        this.setSize(600,600);
        this.add(tfTxt,BorderLayout.SOUTH);
        this.add(taContent,BorderLayout.CENTER);
        this.setVisible(true);
        this.addWindowListener(new WindowAdapter() {
            //WindowAdapter()实现了所有方法
            @Override
            public void windowClosing(WindowEvent arg0) {//关闭窗口时，执行以下操作
                //增加提示下线通知
                String str2 = "localIp:"+localIp+"下线了";
                try {
                    //将下线通知发送给服务器
                    dos = new DataOutputStream(socket.getOutputStream());
                    dos.writeUTF(str2);
                    dos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(0);
                }
                disconnect();
                //退出JVM不在执行以下程序，关闭系统
                System.exit(0);
            }
        });
        tfTxt.addActionListener(new TFListener());
        status =  connect(host,port);
        if (status){
            try {
                //将上线通知发送给服务器
                String str3 = "localIp:"+localIp+"上线了";
                dos = new DataOutputStream(socket.getOutputStream());
                dos.writeUTF(str3);
                dos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        new Thread(recvThread).start();


    }
    private boolean connect(String host,int port){
        try {
            socket = new Socket(host,port);
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            String data = dis.readUTF();
            System.out.println("data = " + data);
            status=true;
        } catch (IOException e) {
            e.printStackTrace();
            try {
                if (dis != null) dis.close();
                if (dos != null) dos.close();
                if (socket != null) socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return false;
        }
        return true;
    }
    private void disconnect(){
        try {
            if (dis != null) dis.close();
            if (dos != null) dos.close();
            if (socket != null) socket.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }


    private class TFListener extends JFrame implements ActionListener {
        /**
         * 重写actionPerformed方法	在文本框内输入回车时都会触发这个方法
         */
        public void actionPerformed(ActionEvent e) {

            String str = tfTxt.getText().trim();
            str = localIp+":" + str;
            //文本框中显示的字符串
            tfTxt.setText("");//空字符
            try {
                dos.writeUTF(str);
                //立即执行
                dos.flush();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * 创建线程,每一个client对应一个线程 (省略也可以)
     */
    private class RecvThread implements Runnable {
        /**
         * 覆盖子线程中的run方法，多线程，实现可以读取多条信息
         */
        public void run() {
            //创建SimpleDateFormat类对象，获取系统时间
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

            try {
                while(status) {
                    String str = dis.readUTF();
                        taContent.setText(taContent.getText() + dateFormat.format(new Date()) + '\n' + str + '\n');
                }
            } catch (SocketException e) {
                System.out.println(localIp+"退出聊天室");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
