package com.zhou.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author eli
 * @date 2017/11/20 14:44
 */
public class Constant {
    public static String Node;

    public static String ZNode = "control_center";
    public static String APIUrl = "http://ds.bds-analytics.com:8424/api/tasks";
    public static String Zookepper_Nodes = "zk.bds-analytics.com:12181,zk.bds-analytics.com:22181,211.152.47.91:2181";

    public static int THREAD_NUM = 5;
    public static int THREAD_QUEEN_NUM = 10;

    public static String DOWNLOAD_URL = "http://211.152.47.77:8000/pan/upload/";
    public static String DOWNLOAD_TO_PATH = "C:\\app";
    public static String CONFIG_PATH = "C:\\app\\";

    public static int SOCKET_PORT = 50300;
    public static int RMI_PORT = 50199;

    public static String ADSL_TITLE;
    public static String ADSL_ACCOUNT;
    public static String ADSL_PWD;
    //切IP时间，单位秒
    public static int ADSL_TIME = 180;
    public static boolean IF_RMI = false;
    public static boolean IF_ADSL = false;
    public static int ADSL_WAITE_TIME = 35;

    //表示状态，0：已联网，1：已断网，2：正在更新程序，不能切IP
    public static volatile String STATUS = "0";

    public static ScheduledExecutorService CHANGE_IP_SERVICE;

    public static List<Integer> PORT_LIST = new ArrayList<>();
}
