package com.zhou.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 参数读取工具类
 */
public class PropertiesUtils {
    /**
     * 测试读取properties
     * @param properties
     */
    public void readPropInfo(Properties properties) {
        for (String propName : properties.stringPropertyNames()) {
            System.out.print(propName + " = ");
            System.out.println(properties.getProperty(propName));
        }
    }

    /**
     * 保存properties文件在本地路径中
     * @param properties
     * @param filePath
     */
    public void saveToLocal(Properties properties, String filePath) {
        OutputStream oFile = null;
        try {
            File file = new File(filePath);
            oFile = new FileOutputStream(file);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (oFile != null) {
                try {
                    properties.store(oFile, null);
                    oFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 读取本地路径上的配置文件
     * @param path
     * @return
     */
    public Properties readLocalProp(String path) {
        InputStream inputStream = null;
        Properties properties = null;
        try {
            inputStream = new FileInputStream(new File(path));
            properties = new Properties();
            properties.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return properties;
    }

/*    public JSONObject compareProp(Properties zkProperties, Properties localProperties) {
        //若本地version为null，则不用比较，说明是新机器，直接按照zk_prop执行下载
        if (localProperties == null) {
            return null;
        }
        JSONObject jsonObject = new JSONObject();

        //比较local和zk的prop,放进list再比较
        List<String> zkList = new ArrayList<>();
        List<String> localList = new ArrayList<>();
        JSONArray jsonArray = new JSONArray();

        for (String propName1 : zkProperties.stringPropertyNames()) {
            zkList.add(propName1);
        }
        for (String propName2 : localProperties.stringPropertyNames()) {
            localList.add(propName2);
        }
        try {
            for (String s : zkList) {
                JSONObject tmp = new JSONObject();
                //判断local中的是否包含zk中的程序，若不包含，则加入待下载列表，若包含，则再做后续比较
                if (localList.contains(s)) {
                    //若local和zk的 版本 不同，则需要下载，加入待下载列表中
                    if (!(
                            zkProperties.getProperty(s).split(",")[0]
                                    .equals(localProperties.getProperty(s).split(",")[0])
                    )) {
                        tmp.put("jar", s.split("\\$")[0]);
                        tmp.put("version", zkProperties.getProperty(s).split(",")[0]);
                        tmp.put("sname", s);
                    }
                } else {
                    tmp.put("jar", s.split("\\$")[0]);
                    tmp.put("version", zkProperties.getProperty(s).split(",")[0]);
                    tmp.put("sname", s);
                }
                jsonArray.put(tmp);
            }
            jsonObject.put("info", jsonArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }*/

    /**
     * 比较本地配置与zk中的配置是否一样
     */
    public static boolean compareConfigProp(Properties zkProperties, Properties localProperties) {
        boolean flag = true;
        //若本地version为null，则不用比较，说明是新机器,直接返回false，表示配置不一样
        if (localProperties == null) {
            return false;
        }

        //比较local和zk的prop,放进list再比较
        List<String> zkList = new ArrayList<>();
        List<String> localList = new ArrayList<>();

        for (String propName1 : zkProperties.stringPropertyNames()) {
            zkList.add(propName1);
        }
        for (String propName2 : localProperties.stringPropertyNames()) {
            localList.add(propName2);
        }
        try {
            for (String s : zkList) {
                //判断local中的是否包含zk中的配置项，若不包含，则直接返回false，若包含，则再比较配置项的值是否一致
                if (localList.contains(s)) {
                    //若local和zk的 版本 不同，则需要下载，加入待下载列表中
                    if (!(zkProperties.getProperty(s).equals(localProperties.getProperty(s)))) {
                        flag = false;
                        break;
                    }
                } else {
                    flag = false;
                    break;
                }
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }

        return flag;
    }
}
