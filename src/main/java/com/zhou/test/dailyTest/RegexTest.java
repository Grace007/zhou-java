package com.zhou.test.dailyTest;

import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author eli
 * @date 2017/11/9 14:51
 */
public class RegexTest {
    public static final Logger logger = Logger.getLogger(RegexTest.class);

    public static void main(String[] args){
        RegexTest regexTest =new RegexTest();
        //regexTest.test1();
        regexTest.test2();
        /*String str1="<div class=\"post_item\">\n" +
                "<div class=\"digg\">\n" +
                "    <div class=\"diggit\" onclick=\"DiggIt(3439076,120879,1)\"> \n" +
                "    <span class=\"diggnum\" id=\"digg_count_3439076\">4</span>\n" +
                "    </div>\n" +
                "    <div class=\"clear\"></div>    \n" +
                "    <div id=\"digg_tip_3439076\" class=\"digg_tip\"></div>\n" +
                "</div>      \n" +
                "<div class=\"post_item_body\">\n" +
                "    <h3><a class=\"titlelnk\" href=\"http://www.cnblogs.com/swq6413/p/3439076.html\" target=\"_blank\">分享完整的项目工程目录结构</a></h3>                   \n" +
                "    <p class=\"post_item_summary\">\n" +
                "<a href=\"http://www.cnblogs.com/swq6413/\" target=\"_blank\"><img width=\"48\" height=\"48\" class=\"pfs\" src=\"http://pic.cnitblog.com/face/142964/20131116170946.png\" alt=\"\"/></a>    在项目开发过程中，如何有序的保存项目中的各类数据文件，建立一个分类清晰、方便管理的目录结构是非常重要的。 综合以前的项目和一些朋友的项目结构，我整理了一份我觉得还不错的项目目录结构。 在这里分享给大家，欢迎各位提出你宝贵的意见和建议。如果喜欢请“推荐”则个，感激万分！！ 整个目录设置到4级子目录，实... \n" +
                "    </p>              \n" +
                "    <div class=\"post_item_foot\">                    \n" +
                "    <a href=\"http://www.cnblogs.com/swq6413/\" class=\"lightblue\">七少爷</a> \n" +
                "    发布于 2013-11-23 15:48 \n" +
                "    <span class=\"article_comment\"><a href=\"http://www.cnblogs.com/swq6413/p/3439076.html#commentform\" title=\"2013-11-23 16:40\" class=\"gray\">\n" +
                "        评论(4)</a></span><span class=\"article_view\"><a href=\"http://www.cnblogs.com/swq6413/p/3439076.html\" class=\"gray\">阅读(206)</a></span></div>\n" +
                "</div>\n" +
                "<div class=\"clear\"></div>\n" +
                "</div>";
        logger.info(str1);
        String pattern="";
        Pattern p=Pattern.compile(pattern);
        Matcher m=p.matcher(str1);
        if (m.find()){

        }*/


    }

    /**
     * find(int i):从第i个字符开始匹配.
     * group(int i):返回匹配的字符串,i默认是0,表示regex中的括号中的内容.
     */
    public void test1(){
        //  \b(\w+).*?\1.*?\1\b
        Matcher m = Pattern.compile("\\w+")
                .matcher("Paris in the the spring the");
        while(m.find())  System.out.println(m.group());
        //这句匹配所有符合regex的所有字符串.
        /**
         Paris
         in
         the
         the
         spring
         the
         */
        /*int i = 0;
        while(m.find(i)) {
            System.out.println(i+"="+m.group() + " ");
            i++;
        }*/
    }
    public void test2(){
        //[{"adurl":"","advid":"","result":"0","sid":""}]
        //[.*?]
        String json = "[{\"adurl\":\"\",\"advid\":\"\",\"result\":\"0\",\"sid\":\"\"}]";
        logger.info("match1   "+match1("\\[.*?\\]",json));
        logger.info("match2   "+match2("(\\[.*?\\])",json));
    }
    public static String match1(String regex,String str){
        Matcher matcher = Pattern.compile(regex).matcher(str);
        if (matcher.find()){
            return matcher.group();
        }
        return "";
    }
    public static String match2(String regex,String str){
        Matcher matcher = Pattern.compile(regex).matcher(str);
        if (matcher.find()){
            return matcher.group(1);
        }
        return "";
    }
}
