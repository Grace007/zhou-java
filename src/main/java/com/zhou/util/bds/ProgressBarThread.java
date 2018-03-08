package com.zhou.util.bds;

import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProgressBarThread implements Runnable {
	private static final Logger LOGGER = Logger.getLogger(ProgressBarThread.class);
	private ArrayList<Integer> proList = new ArrayList<Integer>();
	private int progress;// 当前进度
	private int totalSize;// 总大小
	private boolean run = true;
	private String name;// 当前进度

	private Map<String, String> map = new HashMap<String, String>();

	public ProgressBarThread(int totalSize, String name) {
		this.totalSize = totalSize;
		this.name = name;
		// TODO 创建进度条
	}

	/**
	 * @param progress
	 *            进度
	 */
	public void updateProgress(int progress) {
		synchronized (this.proList) {
			if (this.run) {
				this.proList.add(progress);
				this.proList.notify();
			}
		}
	}

	public void finish() {
		this.run = false;
		// 关闭进度条
	}

	@Override
	public void run() {
		synchronized (this.proList) {
			try {
				while (this.run) {
					if (this.proList.size() == 0) {
						this.proList.wait();
					}
					synchronized (proList) {
						this.progress += this.proList.remove(0);
						// TODO 更新进度条
						BigDecimal size = new BigDecimal(this.progress).divide(new BigDecimal(this.totalSize), 2,
								BigDecimal.ROUND_HALF_DOWN);

						//当前进度条的数值 设置成小数点两位.
						double count = size.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() * 100;

						if (!map.containsKey(name + count)) {
							map.put(name + count, count + "");
							LOGGER.info("********" + name + "当前进度：" + count + "%********");
						}

					}

				}
				LOGGER.info(name + "下载完成");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {

		BigDecimal a = new BigDecimal(3564000).divide(new BigDecimal(5000000));

		double ss = a.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() * 100;
		System.out.println(ss);
		System.out.println(ss % 10);
		LOGGER.info("******** 当前进度：" + ss + "%********");
	}
}
