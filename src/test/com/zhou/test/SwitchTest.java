package com.zhou.test;

import org.junit.Test;

/**
 * @author eli
 * @date 2017/11/22 18:05
 */
public class SwitchTest {
    @Test
    public void test1(){

    }

    @Test
    public void testSwitch(){
        System.out.println("getSwitchResult(\"00\") = " + getSwitchResult("00"));
    }
    public String getSwitchResult(String str){
        String result="";
        switch (str){
            case "zhangsan":
                result ="zhangsan+";
                break;
            case  "zhaosi":
                result = "zhaosi+";
                break;
            default:
                result="null+";
                break;
        }
        return  result;

    }
}
