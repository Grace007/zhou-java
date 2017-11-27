package com.zhou.test;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.junit.Test;

import java.io.*;

/**
 * @author eli
 * @date 2017/11/23 12:05
 */
public class FileIOTest {
    @Test
    public void fileApiTest(){
        String filepath = "E:\\Test";
        File file = new File(filepath);
        System.out.println("file.getAbsolutePath() = " + file.getAbsolutePath());
        System.out.println("得到目录下的文件列表列表##########");
        String [] files1 = file.list();
        for (String filestr :files1){
            System.out.println("filestr = " + filestr);
        }

        System.out.println("根据过滤器来筛选文件名称##########");
        File [] files2 =  file.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                //return name.contains(".txt");
                //return name.endsWith(".txt");
                return  dir.isDirectory();
            }
        });
        for (File filestr :files2){
            System.out.println("filestr = " + filestr.getName());
        }

        System.out.println("根据过滤器来筛选文件属性##########");
        File [] files3 =  file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File dir) {
                //return name.contains(".txt");
                return dir.isDirectory();
            }
        });
        for (File filestr :files3){
            System.out.println("filestr = " + filestr.getName());
        }

        //读取文件数据
        File fileTest = new File("E:\\Test\\test.txt");
        try {
            FileInputStream fileInputStream1 = new FileInputStream(fileTest);
            BufferedInputStream fileInputStream = new BufferedInputStream(fileInputStream1);
            byte [] buf = new byte[1024];
            int length=0;
            String data ="";
            while ((length = fileInputStream.read(buf))!=-1){
                data+=new String(buf,0,length);
            }
            fileInputStream.close();
            System.out.println("data = " + data);
        }catch (IOException e){
            e.printStackTrace();
        }
        //写入数据
        try{
            FileOutputStream fileOutputStream1 = new FileOutputStream(fileTest,true);
            BufferedOutputStream fileOutputStream = new BufferedOutputStream(fileOutputStream1);
            String data="\r\n"+"It is a test";
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }

        //复制图片数据
        File pictureFile = new File("E:\\Test\\picture1.png");
        try {
            if (pictureFile.exists()) {
                FileInputStream input = new FileInputStream(pictureFile);
                FileOutputStream output=new FileOutputStream("E:\\Test\\picture_copy.png");
                int length = 0;
                byte[] buf = new byte[1024];
                while((length=input.read(buf)) != -1){
                    output.write(buf,0,length);
                }
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(output);
                input.close();
                output.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        //commons-IO包工具类
        System.out.println("FilenameUtils.getName(filepath) = " + FilenameUtils.getName(filepath));
        File fileUtils1 = new File("E:\\Test\\test.txt");
        try {
            System.out.println("FileUtils.readFileToString(fileUtils1) = " + FileUtils.readFileToString(fileUtils1));;
            FileUtils.writeStringToFile(fileUtils1,"\n"+"你是一个傻逼",true);
            System.out.println("FileUtils.readFileToString(fileUtils1) = " + FileUtils.readFileToString(fileUtils1));;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}