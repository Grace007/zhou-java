package com.zhou.test.qqproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
/**
 * 创建一个窗体
 * @author table
 *
 */
public class choose extends JFrame{
	/**
	 * 主方法
	 * @param args
	 */
	public static void main(String[] args) {
		new choose().lunchFrame();
	}

	public void lunchFrame()
	{
		//窗体居中
		setLocationRelativeTo(null);
		//填写标题
		setTitle(YY_Final.getSystemPeople());//系统聊天
		//显示窗体
		setVisible(true);
		/**
		 * 窗体中加入内容
		 */
		add(new SingleChat().open,BorderLayout.WEST);
		add(new exit().open,BorderLayout.EAST);
		add(new AllChat().open,BorderLayout.CENTER);
		add(new guide().open,BorderLayout.NORTH);
		pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
/**
 * 实现单人聊天的类
 * @author table
 *
 */
class SingleChat extends JFrame implements ActionListener{
	//创建JButton类型变量
	JButton open = null;
	/**
	 * 构造方法实现文件选择
	 */
	public SingleChat(){
		//JButton用来创建没有复选框的选择文件按钮,
		open = new JButton(YY_Final.getPeople());//单人聊天
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
	 * 监听方法
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		new Main().ChooseMain("com.zhou.test.qqproject.YY_Private_Client");

	}
}
/**
 * 此类可以实现调用某个类的主方法
 * @author table
 *
 */
class Main {
	public void ChooseMain(String pakage) {
		ClassLoader classLoader = YY_Client.class.getClassLoader();
		Class<?> loadClass = null;
		try {
			loadClass = classLoader.loadClass(pakage);
		} catch (ClassNotFoundException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		Method method = null;
		try {
			method = loadClass.getMethod("main", String[].class);
		} catch (NoSuchMethodException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (SecurityException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			method.invoke(null, new Object[] { new String[] {} });
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
/**
 * 实现多人聊天的类
 * @author table
 *
 */
class AllChat extends JFrame implements ActionListener{
	//创建JButton类型变量
	JButton open = null;
	/**
	 * 构造方法实现文件选择
	 */
	public AllChat(){
		//JButton用来创建没有复选框的选择文件按钮,
		open = new JButton(YY_Final.getManyPeople());//多人聊天
		//添加按钮
		this.add(open);
		//this.setBounds(400, 200, 100, 100);
		//this.setVisible(true);
		//用户单击窗口的关闭按钮时程序执行的操作
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//监听，进行某一个操作的时候触发某项功能
		open.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		new Main().ChooseMain("com.zhou.test.qqproject.YY_Client");
	}
}
/**
 * 退出按钮
 * @author table
 *
 */
class exit extends JFrame{
	//创建JButton类型变量
	JButton open = null;
	/**
	 * 构造方法实现文件选择
	 */
	public exit(){
		//JButton用来创建没有复选框的选择文件按钮,
		open = new JButton(YY_Final.getRetreat());//退出
		//添加按钮
		this.add(open);
		//this.setBounds(400, 200, 100, 100);
		//this.setVisible(true);
		//用户单击窗口的关闭按钮时程序执行的操作
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//监听，进行某一个操作的时候触发某项功能
		open.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);//隐藏窗体
				System.exit(0);//退出程序
			}
		});
	}

}
/**
 * 此类为指引类
 * @author table
 *
 */
class guide extends JFrame implements ActionListener{
	//创建JButton类型变量
	JButton open = null;
	/**
	 * 构造方法实现文件选择
	 */
	public guide(){
		//JButton用来创建没有复选框的选择文件按钮,
		open = new JButton(YY_Final.getSelectPedole());//请选择你想选的聊天方式
		//添加按钮
		this.add(open);
		//this.setBounds(400, 200, 100, 100);
		//this.setVisible(true);
		//用户单击窗口的关闭按钮时程序执行的操作
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		open.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(YY_Final.getNameCalling());
	}
}