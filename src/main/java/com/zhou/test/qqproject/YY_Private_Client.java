package com.zhou.test.qqproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class YY_Private_Client extends JFrame{//JFrame类是带有标题和边框的顶层窗口
	//1.创建Socket类型变量
	Socket socket = null;
	//2.创建DataOutputStream类型变量
	DataOutputStream dos = null;
	//2.创建DataInputStream类型变量
	DataInputStream dis = null;
	//创建boolean类型变量，并给其赋值为false
	private boolean flag = false;
	//创建TextField对象，用来实现在窗口添加文本框
	TextField tfTxt = new TextField();
	//创建TextArea对象，用来装载文本内容的文本域
	TextArea taContent = new TextArea();

	//创建Runnable实现类对象,调用子线程类
	Thread tRecv = new Thread(new RecvThread());

	public static void main(String[] args) {
		new YY_Private_Client().launchFrame("127.0.0.1",4444);
	}
	/**
	 * 实现多人同时聊天及传输数据
	 * @param address 服务器IP
	 * @param port 服务器端口号
	 */
	public void launchFrame(String address,int port) {
		//将组件移到新位置。通过此组件父级坐标空间中的 x 和 y 参数来指定新位置的左上角
		setLocation(400, 300);
		//调整组件的大小，使其宽度为 width，高度为 height,单位像素
		this.setSize(300, 300);
		this.setTitle(YY_Final.getPeople());//单人聊天
		//将文本框设置默认布局（BorderLayout），添加至JFrame类中
		add(tfTxt, BorderLayout.SOUTH);
		//将文本域中的内容设置为默认布局（BorderLayout），添加至JFrame类中
		add(taContent, BorderLayout.NORTH);
		//将文件选择器设置默认布局（BorderLayout），添加至JFrame类中
		add(new FileChooser().open,BorderLayout.CENTER);
		//将窗口设置为可以显示状态或用show();
		pack();
		//向文本对象添加窗口事件监听 addWindowListener
		this.addWindowListener(new WindowAdapter() {//WindowAdapter()实现了所有方法
			@Override
			public void windowClosing(WindowEvent arg0) {//关闭窗口时，执行以下操作
				//3.释放资源
				disconnect();
				//退出JVM不在执行以下程序，关闭系统
				System.exit(0);
			}
		});
		//进行文本框操作的时候触发，创建TFListener类对象。
		tfTxt.addActionListener(new TFListener());
		//进行文件选择操作时触发FileChooser()方法
		new FileChooser().open.addActionListener(new FileChooser());
		setVisible(true); //将文本框的内容设置为可见
		//2.联结服务器，读取服务器发过来的信息以及向服务器发送信息
		connect(address,port);
		//唤醒线程
		tRecv.start();
	}
	/**
	 * 实现与服务器建立联结，读取服务器发过来的信息以及向服务器发送信息
	 */
	public void connect(String address,int port) {
		try {
			//1.创建Socket对象，指定服务端的IP地址与端口号
			socket = new Socket(address, port);
			//2.获取网络数据字节输出流，向服务器发送信息
			dos = new DataOutputStream(socket.getOutputStream());
			//2.获取网络数据字节输入流，读取服务器发过来的信息
			dis = new DataInputStream(socket.getInputStream());
			flag = true;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 实现释放资源
	 */
	public void disconnect() {
		try {//3.释放资源
			dos.close();//关闭输出流
			dis.close();//关闭输入流
			socket.close();//关闭socket
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private class TFListener extends JFrame implements ActionListener {
		/**
		 * 重写actionPerformed方法	在文本框内输入回车时都会触发这个方法
		 */
		public void actionPerformed(ActionEvent e) {
			//创建SimpleDateFormat类对象，获取系统时间
			SimpleDateFormat dateFormat = new SimpleDateFormat(YY_Final.getSjFormat());//可以方便地修改日期格式
			//将文本框中的内容，去掉空格后，赋值给str
			String str = tfTxt.getText().trim();
			//文本框中显示的字符串
			tfTxt.setText(YY_Final.getEmpty());//空
			taContent.setText(taContent.getText() + dateFormat.format(new Date()) + '\n' + str + '\n');
			try {
				//2.1将获得的网络数据，写入内存，通过getOutputStream()发送给服务器
				dos.writeUTF(str);
				//立即执行
				dos.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	private class FileChooser extends JFrame implements ActionListener{
		//创建JButton类型变量
		JButton open = null;
		/**
		 * 构造方法实现文件选择
		 */
		public FileChooser(){
			//JButton用来创建没有复选框的选择文件按钮,
			open = new JButton(YY_Final.getSelectFile());//选择文件
			//添加按钮
			this.add(open);
			//this.setBounds(400, 200, 100, 100);
			//this.setVisible(true);
			//用户单击窗口的关闭按钮时程序执行的操作
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			//监听，进行某一个操作的时候触发某项功能
			open.addActionListener(this);
		}
		/**
		 * 获得文件的后缀名
		 * @param file 文件名
		 * @return 文件的后缀
		 */
		public String Prefix(File file) {
			String filename = file.getName();//获得文件名
			String prefix = filename.substring(filename.lastIndexOf(".") + 1);//获得文件后缀名
			return prefix;
		}

		@Override
		/**
		 * 重写actionPerformed方法，当按钮按下选择文件时执行文件的选择与传输
		 */
		public void actionPerformed(ActionEvent e) {
			//创建JFileChooser对象
			JFileChooser jfc = new JFileChooser();
			//选择目录
			jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
			jfc.showDialog(new JLabel(), YY_Final.getSelect()); //选择
			//获取选中的文件
			File file = jfc.getSelectedFile();
			if(file.isDirectory()){//判断是否为文件夹
				System.out.println(YY_Final.getNonsupport());//暂不支持
			}else if(file.isFile()){//判断是否为文件
				//打印文件路径
				System.out.println(YY_Final.getFile() + file.getAbsolutePath());//获得文件的绝对路径
				String strfile = YY_Final.getEmpty();//声明空字符串
				FileInputStream in = null;///声明文件输入流为空
				try {
					//给输入流对象赋值
					in = new FileInputStream(file.getAbsoluteFile());
				} catch (FileNotFoundException e2) {
					e2.printStackTrace();
				}
				// size  为字串的长度 ，这里一次性读完
				int size = 0;
				try {
					size = in.available();//读取文件的总大小
				} catch (IOException e2) {
					e2.printStackTrace();
				}
				//创建数组
				byte[] buffer = new byte[size];

				try {
					in.read(buffer);//把元素读入数组
				} catch (IOException e2) {
					e2.printStackTrace();
				}

				try {
					in.close();//关闭输入流
				} catch (IOException e2) {
					e2.printStackTrace();
				}

				try {
					strfile = new String(buffer,"GB2312");//赋值给字符串并确定编码格式
					strfile = strfile + "." + Prefix(file.getAbsoluteFile());//字符串加上后缀
				} catch (UnsupportedEncodingException e2) {
					e2.printStackTrace();
				}
				//新建输出流对象
				DataOutputStream out = null;
				try {
					out = new DataOutputStream(socket.getOutputStream());//为输出流对象赋值
					out.writeUTF(strfile);//将字符串写入
					out.flush();
					System.out.println(YY_Final.getSuccess());//成功
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}

	}
	private class RecvThread implements Runnable {
		/**
		 * 覆盖子线程中的run方法，多线程，实现可以读取多条信息
		 */
		public void run() {
			//创建SimpleDateFormat类对象，获取系统时间
			SimpleDateFormat dateFormat = new SimpleDateFormat(YY_Final.getSjFormat());//可以方便地修改日期格式

			try {
				while(flag) {
					//2.2通过getInputStream()读取服务器端发过来的信息
					String str = dis.readUTF();
					if(!str.endsWith(YY_Final.getTxt())) {//判断字符串尾部元素

						//在文本域中显示所有读到的信息，（把每次读到的信息累计在文本域中显示）
						taContent.setText(taContent.getText() + dateFormat.format(new Date()) + '\n' + str + '\n');
					} else {

						//新建file文件对象
						File distFile = new File(YY_Final.getCreatePate() + str.substring(str.length()-4));
						boolean flag = true;//定义变量为true
						while(flag){
							if(distFile.exists()){//如果文件已存在
								String name = distFile.getName();//获得文件名
								name = name.substring(0, name.length()-4);//去掉后缀的文件名
								String i = "_1";
								//文件名后加_1，截取掉str从首字母起长度为str.length()-4的字符串，剩余字符串拼接
								distFile = new File(YY_Final.getRemovePath() + name + i + str.substring(str.length()-4));
								continue;
							} else {
								flag = false;
							}
						}
						if (!distFile.getParentFile().exists()) {//如果上级目录不存在
							distFile.getParentFile().mkdirs();//创建上级目录
						}
						//截取str中从0开始至str.length()-4结束时的字符串，并将其赋值给str;
						str = str.substring(0, str.length()-4);
						BufferedReader bufferedReader = new BufferedReader(new StringReader(str));//获取包装字符输入流
						BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(distFile));//获取包装 字符输出流
						//每行字符串内容
						String line = null;
						while ((line = bufferedReader.readLine()) != null) {
							bufferedWriter.write(line);//将字符串写入文件
							bufferedWriter.newLine();//实现写入时做换行操作
						}
						bufferedWriter.flush();//立即执行
					}
				}
			} catch (SocketException e) {
				System.out.println(YY_Final.getRetreatFrom());//退出群聊
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}