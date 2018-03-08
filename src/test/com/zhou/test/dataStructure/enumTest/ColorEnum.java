package com.zhou.test.dataStructure.enumTest;

/**
 * @author eli
 * @date 2017/12/14 14:01
 */
public enum ColorEnum {
    //黑梅花,菱形,心形,黑菱形
    //给每种类型的牌初始值
    CLUBES("black"), DIAMONDS("red"), HEARTS("red"), SPADES("black");

    private String color;

    ColorEnum(String color){
        this.color=color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
