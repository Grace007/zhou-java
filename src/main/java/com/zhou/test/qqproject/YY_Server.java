package com.zhou.test.qqproject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class YY_Server {
	//1.创建serverSocket类型变量
	ServerSocket serverSocket = null;
	//创建Client类型的集合
	List<Client> clients = new ArrayList<Client>();
	//声明变量，赋值为false
	boolean started = false;

	public static void main(String[] args) {
		new YY_Server().start();
	}
	/**
	 * 实现了多个客户端通过，此服务器端进行通信
	 */
	public void start() {//如用static修饰无法访问到实例变量
		//在线人数
		int i = 1;
		try {
			//1.创建创建serverSocket对象，端口号：1818
			serverSocket = new ServerSocket(1818);
			started = true;
		} catch (BindException e) {
			System.out.println(YY_Final.getTakeUp()); //端口使用中
			System.out.println(YY_Final.getShutDown());//关闭重启
			//退出JVM不在执行以下程序
			System.exit(0);
		}catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("server启动成功...");

		try {
			while(started) {
				//2.监听是否有客户端连接,如果有客户端连接服务器，则accept()方法返回Socket对象
				Socket socket = serverSocket.accept();
				System.out.println("有客户端上线,socket = " + socket);
				//3.获取对象的字节流，调用Client类构造器，实现Runnable接口
				Client c = new Client(socket);
				//获取字节输出流，向新上线的客户端发送消息
				DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
				//将在线人数通知发送给，刚上线的客户端
				dos.writeUTF(i + YY_Final.getOnline());//在线
				i++;

				//唤醒线程
				new Thread(c).start();
				//将Client类型添加到集合
				clients.add(c);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				//关闭serverSocket
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 	创建Client类，实现Runnable接口，实现多线程
	 * @author table
	 *
	 */
	class  Client implements Runnable{
		//3.创建Socket类型变量
		private Socket socket;
		//3.创建DataInputStream类型变量
		private DataInputStream dis = null;
		//3.创建DataOutputStream类型变量
		private DataOutputStream dos = null;
		//创建boolean类型变量，并给其赋值为false
		private boolean flag = false;
		/**
		 * 构造方法，对socket进行初始化，实现获取字节流
		 * @param socket Socket类型对象，用于获取字节流
		 */
		public Client(Socket socket) {
			//对socket进行初始化
			this.socket = socket;

			try {
				//3.获取网络数据字节输输入流，读取客户端发过来的信息
				dis = new DataInputStream(socket.getInputStream());
				//3.获取网络数据字节输出流，向客户端发送信息
				dos = new DataOutputStream(socket.getOutputStream());

				flag = true;
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		/**
		 * 实现网络数据的写入
		 * @param str
		 */
		public void send(String str) {
			try {
				//3.1将获得的网络数据，写入内存，通过getOutputStream()发送给客户端
				dos.writeUTF(str);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		/**
		 * 重写run方法，创建子线程，实现被多个客户端访问
		 */
		public void run() {
			try {
				while(flag) {
					//3.2通过getInputStream()读取客户端发过来的信息
					String str = dis.readUTF();
					//3.2输出接收到的客户端信息
					System.out.println("客户端发来的消息:"+str);
					//遍历集合
					for(int i = 0; i < clients.size(); i++) {
						//得到集合中的Client类型对象
						Client c = clients.get(i);
						//调用send方法将获得的网络数据，写入内存，通过getOutputStream()发送给客户端
						c.send(str);
					}
				}
			} catch (EOFException e) {
				System.out.println("Client closed!");
			} catch (IOException e) {
				e.printStackTrace();
				//4.释放资源
			} finally {
				try {
					//4.1关闭流
					if (dis != null) dis.close();
					if (dos != null) dos.close();
					//4.2关闭socket
					if (socket != null) {
						socket.close();
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

}