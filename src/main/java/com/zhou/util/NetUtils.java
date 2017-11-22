package com.zhou.util;

import org.apache.log4j.Logger;
import sun.net.util.IPAddressUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;


public class NetUtils {
	private static final Logger LOGGER = Logger.getLogger(NetUtils.class);

	/**
	 * 判断机器是否联网
	 * @return
	 */
	public static boolean isConnected() {
		boolean flag = false;
		Runtime runtime = Runtime.getRuntime();
		Process process;
		try {
			process = runtime.exec("ping " + "www.baidu.com");
			InputStream is = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(is,"gb2312");
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			System.out.println("返回值为:" + sb);
			is.close();
			isr.close();
			br.close();

			if (!sb.toString().equals("")) {
				if (sb.toString().indexOf("TTL") > 0) {
					// 网络畅通
					Constant.STATUS = "0";
					flag = true;
				} else {
					// 网络不畅通
					Constant.STATUS = "1";
					flag = false;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static boolean reConnect() {
		boolean flag = false;

		try {
			for (int i = 0; i < 5; i++) {
				if (i >= 5) {
					flag = false;
					LOGGER.warn("重连ADSL异常");
					break;
				}
				Runtime runtime = Runtime.getRuntime();
				runtime.exec("Rasdial " + Constant.ADSL_TITLE + " " + Constant.ADSL_ACCOUNT + " " + Constant.ADSL_PWD);
				if (isConnected()) {
					Constant.STATUS = "0";
					LOGGER.info("重连ADSL成功");

					Thread.sleep(Constant.ADSL_WAITE_TIME);

					flag = true;
					break;
				} else {
					Thread.sleep(120000);
				}
			}

		} catch (Exception e) {
			LOGGER.warn("重连ADSL异常:", e);
		}

		return flag;
	}

	public static boolean disConnect() {
		boolean flag = false;

		try {
			Runtime runtime = Runtime.getRuntime();
			runtime.exec("Rasdial /DISCONNECT");
			Constant.STATUS = "1";
			LOGGER.info("断开ADSL成功");
			Thread.sleep(3000);
			flag = true;
		} catch (Exception e) {
			LOGGER.warn("断开ADSL异常:", e);
		}

		return flag;
	}

	public static String getLocalHostIP() {
		String ret = null;
		String[] ips = null;
		try {
			String hostName = getLocalHostName();
			if (hostName.length() > 0) {
				InetAddress[] addrs = InetAddress.getAllByName(hostName);
				if (addrs.length > 0) {
					ips = new String[addrs.length];
					for (int i = 0; i < addrs.length; i++) {
						ips[i] = addrs[i].getHostAddress();
					}
				}
			}
			for (String ip : ips) {
				if (!internalIp(ip)) {
					ret = ip;
					break;
				} else {
					System.out.println("************本地IP不做操作:" + ip + "************");
				}

			}
		} catch (Exception ex) {
			ret = null;
		}
		return ret;
	}

	private static String getLocalHostName() {
		String hostName;
		try {
			InetAddress addr = InetAddress.getLocalHost();
			hostName = addr.getHostName();
		} catch (Exception ex) {
			hostName = "";
		}
		return hostName;
	}

	private static boolean internalIp(String ip) {
		byte[] addr = IPAddressUtil.textToNumericFormatV4(ip);
		return internalIp(addr);
	}

	private static boolean internalIp(byte[] addr) {
		final byte b0 = addr[0];
		final byte b1 = addr[1];
		// 10.x.x.x/8
		final byte SECTION_1 = 0x0A;
		// 172.16.x.x/12
		final byte SECTION_2 = (byte) 0xAC;
		final byte SECTION_3 = (byte) 0x10;
		final byte SECTION_4 = (byte) 0x1F;
		// 192.168.x.x/16
		final byte SECTION_5 = (byte) 0xC0;
		final byte SECTION_6 = (byte) 0xA8;
		switch (b0) {
		case SECTION_1:
			return true;
		case SECTION_2:
			if (b1 >= SECTION_3 && b1 <= SECTION_4) {
				return true;
			}
		case SECTION_5:
			switch (b1) {
			case SECTION_6:
				return true;
			}
		default:
			return false;

		}
	}

	private void broadcastNetStatus() {
		for (Integer port : Constant.PORT_LIST) {
			// todo:根据port向别的程序广播联网状态
			// communicateService.transNetStatus()
		}
	}

	public static void main(String[] args) throws Exception {
		String currentIp = getLocalHostIP();
		System.out.println(currentIp);
		Constant.STATUS = "0";
		Constant.ADSL_ACCOUNT = "07449969151";
		Constant.ADSL_PWD = "a123456";

		for (int i = 0; i < 3; i++) {
			disConnect();
			reConnect();
			currentIp = getLocalHostIP();
			System.out.println(currentIp);
			internalIp(currentIp);
			Thread.sleep(60000);
		}
	}
}
