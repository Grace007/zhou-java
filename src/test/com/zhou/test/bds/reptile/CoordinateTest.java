package com.zhou.test.bds.reptile;

import com.zhou.util.ExcelExporterOrImpUtil;
import com.zhou.util.HttpClientUtils;
import com.zhou.util.RegexUtil;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author eli
 * @date 2018/2/27 12:08
 */
public class CoordinateTest {
    @Test
    public void test1() throws Exception{
        String temp="";

        //通过类加载得到当前class文件路径
        String path = this.getClass().getResource("/com/zhou/test/bds/reptile/").getPath();
        path = path.substring(1,path.length()).trim();

        File dataFile = new File(path+"data");
        List<String> dataList = FileUtils.readLines(dataFile);
        List<KeywordModel> list = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            String forITemp = dataList.get(i);
            KeywordModel keywordModel = new KeywordModel();
            String [] ids = forITemp.split("\t");
            int formIndex = forITemp.indexOf(ids[1]);
            keywordModel.setId(Integer.parseInt(ids[0]));
            keywordModel.setCity(ids[1]);
            keywordModel.setAddress(forITemp.substring(forITemp.indexOf("\t",formIndex),forITemp.length()));
            list.add(keywordModel);

            /*System.out.println("ids[1] = " + ids[1]);
            System.out.println("keywordModel = " + keywordModel.getAddress());*/

        }
        System.out.println("list装配完成...");

        for (int i = 0; i < list.size(); i++) {
            System.out.println(i+"####################正在下载："+list.get(i).getId());
            String url = "http://restapi.amap.com/v3/place/text?s=rsv3&children=&key=8325164e247e15eea68b59e89200988b&page=1&offset=10&language=zh_cn&callback=jsonp_656056_&platform=JS&logversion=2.0&sdkversion=1.3&appname=http%3A%2F%2Flbs.amap.com%2Fconsole%2Fshow%2Fpicker&csid=AC58B9E9-898F-4F7C-B8FC-7F45651D545B&keywords="+ URLEncoder.encode(list.get(i).getCity()+" " +list.get(i).getAddress());
            String html = HttpClientUtils.doGet(url);
            //System.out.println("html = " + html);
            Thread.sleep(3000);
            //处理html
            String jsonHtml = RegexUtil.match1("\\(\\{.*?\\}\\)",html).trim();
            jsonHtml = jsonHtml.substring(1,jsonHtml.length()-1);
            System.out.println("jsonHtml = " + jsonHtml);


            try {
                //构建json
                JSONObject jsonObject = new JSONObject(jsonHtml);
                //System.out.println("jsonObject = " + jsonObject);
                if(jsonObject !=null){
                    JSONArray jsonArray = jsonObject.getJSONArray("pois");
                    System.out.println("jsonArray = " + jsonArray.length());
                    if (jsonArray.length()!=0){
                        JSONObject poisOne = (JSONObject) jsonArray.get(0);
                        //第一重判断：城市包含判断
                        if (poisOne.getString("cityname").contains(list.get(i).getCity())){
                            list.get(i).setResult(poisOne.getString("location"));
                            continue;
                        }
                        //第二重判断：坐标判断
                        if (!poisOne.isNull("location")){
                            list.get(i).setResult(poisOne.getString("location"));

                            try {
                                String pname="",cityname="",adname="";
                                if (!poisOne.isNull("pname")) pname=poisOne.getString("pname");
                                if (!poisOne.isNull("cityname")) cityname=poisOne.getString("cityname");
                                if (!poisOne.isNull("adname")) adname=poisOne.getString("adname");
                                list.get(i).setRemark(pname+"  "+cityname+"  "+adname);
                            } catch (JSONException e) {
                                list.get(i).setRemark("#####自行判断");
                            }
                            continue;
                        }

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        //测试
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i+"############" +list.get(i).getCity()+"  "+list.get(i).getAddress()+"####  "+ list.get(i).getResult()+"    "+list.get(i).getRemark());

        }

        //导出

        FileOutputStream os = new FileOutputStream(new File("d:/test.xls"));

        //导出部分
        List<List<String>> listLists = new ArrayList<List<String>>();
        for (int j = 0; j <list.size(); j++) {

            List<String> lists = new ArrayList<>();
            lists.add(String.valueOf(list.get(j).getId()) );
            lists.add(list.get(j).getCity());
            lists.add(list.get(j).getAddress());
            lists.add(list.get(j).getResult());
            lists.add(list.get(j).getRemark());
            listLists.add(lists);
        }


        ExcelExporterOrImpUtil.exportToExcel(os, listLists);

    }
}
