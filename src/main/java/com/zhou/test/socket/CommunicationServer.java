package com.zhou.test.socket;

import org.quartz.SchedulerFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author eli
 * @date 2017/11/28 9:47
 */
public class CommunicationServer {
    private ServerSocket serverSocket=null;
    private List<Client> clients=new ArrayList<Client>();
    private Boolean status=false;
    //count的意义不大,和clients.size一致
    private static int count=0;
    public static SchedulerFactory schedulerFactory;

    public static void main(String[] args) {
        new CommunicationServer().start();
    }
    private void start(){
        try {
            serverSocket = new ServerSocket(9999);
            status=true;
        } catch (IOException e) {
            System.out.println("ServerSocket(9999)开启失败..");
            return;
        }
        System.out.println("ServerSocket(9999)开启成功..");
        try {
            while (status) {
                Socket socket = serverSocket.accept();
                Client client = new Client(socket);
                count++;
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeUTF(count + "人当前在线");
                new Thread(client).start();
                clients.add(client);
            }
        } catch (IOException e) {
            try {
                serverSocket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public class Client implements Runnable{
        private Socket socket =null;
        private DataInputStream dis=null;
        private DataOutputStream dos=null;
        private Boolean clientSatus=false;

        Client(Socket socket){
            try {
                this.socket = socket;
                dis = new DataInputStream(socket.getInputStream());
                dos = new DataOutputStream(socket.getOutputStream());
                clientSatus=true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        /**
         * //向客户端发送数据
         * @param data
         */
        public void send(String data){
            try {
                dos.writeUTF(data);
                dos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                while (clientSatus) {
                    //获得客户端发来的消息
                    String message = dis.readUTF();
                    System.out.println("message = " + message);
                    //消息下发到每一个客户端
                    for (int i = 0; i < clients.size(); i++) {
                        Client client = clients.get(i);
                        client.send(message);
                    }

                }
            } catch (IOException e) {
                System.out.println("连接关闭!");
            } finally {
                try {
                    if (dis != null) dis.close();
                    if (dos != null) dos.close();
                    if (socket != null) socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

    }
    public class HreadbeatQuartz implements Runnable{


        @Override
        public void run() {
            //创建调度中心(调度器)
            //Scheduler scheduler = schedulerFactory.getScheduler();
            //创建jobDeil(作业)
            //QuartzDemoJob job = new QuartzDemoJob();
            //JobDetail的作用就是给job作业添加附加信息,比如name,group等
            //JobDetail jobDetail = JobBuilder.newJob(job.getClass()).withIdentity("quartzDemoJob").build();
            //创建触发器
            //Trigger trigger = TriggerBuilder.newTrigger().withIdentity("quartzDemoJob","testjob").withSchedule(CronScheduleBuilder.cronSchedule("*/10 * * * * ?")).build();
            //向调度中心注册
            //scheduler.scheduleJob(jobDetail,trigger);
            // 启动调度
            //scheduler.start();

        }
    }

}
