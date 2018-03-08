package com.zhou.test.dataStructure.enumTest;

import org.junit.Test;

/**
 * @author eli
 * @date 2017/12/14 14:06
 */
public class MainEnum {

    @Test
    public void test1(){
        //ColorEnum.CLUBES.setColor("doubi");
        System.out.println("ColorEnum.CLUBES = " + ColorEnum.CLUBES.getColor());
        //遍历枚举类
        for(ColorEnum color : ColorEnum.values()){
            System.out.println(color.ordinal()+","+color.name()+ " = " + color.getColor());
        }

    }
}
