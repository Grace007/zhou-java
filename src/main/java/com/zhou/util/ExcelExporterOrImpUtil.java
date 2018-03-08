package com.zhou.util;

import org.apache.poi.hssf.usermodel.*;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author eli
 * @date 2018/1/3 9:49
 */
public class ExcelExporterOrImpUtil {
    /**
     * 导出数据
     * @param os
     * @param data
     * @throws IOException
     */
    public static void exportToExcel(OutputStream os, List<List<String>> data) throws IOException {
        HSSFWorkbook wb=null;
        try {
            wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet("Data");

            for (int r = 0 ; r < data.size() ; r++) {
                HSSFRow row = sheet.createRow(r);

                List<String> cols = data.get(r);
                for (int c = 0 ; c < cols.size() ; c++) {
                    HSSFCell cell = row.createCell(c);
                    cell.setCellValue(new HSSFRichTextString(cols.get(c)));
                }
            }


        } catch (Exception e) {

            e.printStackTrace();
        }finally{
            if(wb!=null){
                wb.write(os);
            }
            os.flush();
            os.close();
        }
    }
    /**
     * 数据导入
     * @param is
     * @return
     * @throws IOException
     */
    public static List<List<String>> importFromExcel(InputStream is) throws IOException {
        HSSFWorkbook wb = new HSSFWorkbook(is);
        HSSFSheet sheet = wb.getSheetAt(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<List<String>> data = new ArrayList<List<String>>();
        try {
            for (int r = sheet.getFirstRowNum() ; r <= sheet.getLastRowNum() ; r++) {
                HSSFRow row = sheet.getRow(r);
                if (row == null) {
                    continue;
                }

                List<String> cols = new ArrayList<String>();
                for (int c = row.getFirstCellNum() ; c < row.getLastCellNum() ; c++) {
                    HSSFCell cell = row.getCell(c);
                    if (cell == null) {
                        cols.add("");
                    } else {
                        switch(cell.getCellType()) {
                            case HSSFCell.CELL_TYPE_STRING:
                                cols.add(cell.getStringCellValue());
                                break;
                            case HSSFCell.CELL_TYPE_BOOLEAN:
                                cols.add(""+cell.getBooleanCellValue());
                                break;
                            case HSSFCell.CELL_TYPE_NUMERIC:
                                if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                                    cols.add(sdf.format(cell.getDateCellValue()));
                                } else {
                                    cols.add(""+cell.getNumericCellValue());
                                }

                                break;
                            case HSSFCell.CELL_TYPE_FORMULA:
                                cols.add(cell.getCellFormula());
                                break;
                            case HSSFCell.CELL_TYPE_BLANK:
                                cols.add("");
                                break;
                        }
                    }
                }

                data.add(cols);
            }
        } finally {
            is.close();
        }
        return data;
    }
    /**
     *
     * @param is
     * @param firstCellNum
     * @param lastCellNum
     * @return
     * @throws IOException
     */
    public static List<List<String>> importFromExcelWithCell(InputStream is,int firstCellNum,int lastCellNum) throws IOException {
        HSSFWorkbook wb = new HSSFWorkbook(is);
        HSSFSheet sheet = wb.getSheetAt(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<List<String>> data = new ArrayList<List<String>>();
        try {
            for (int r = sheet.getFirstRowNum() ; r <= sheet.getLastRowNum() ; r++) {
                HSSFRow row = sheet.getRow(r);
                if (row == null) {
                    continue;
                }

                List<String> cols = new ArrayList<String>();
                for (int c = firstCellNum ; c < lastCellNum ; c++) {
                    HSSFCell cell = row.getCell(c);
                    if (cell == null) {
                        cols.add("");
                    } else {
                        switch(cell.getCellType()) {
                            case HSSFCell.CELL_TYPE_STRING:
                                cols.add(cell.getStringCellValue());
                                break;
                            case HSSFCell.CELL_TYPE_BOOLEAN:
                                cols.add(""+cell.getBooleanCellValue());
                                break;
                            case HSSFCell.CELL_TYPE_NUMERIC:
                                if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                                    cols.add(sdf.format(cell.getDateCellValue()));
                                } else {
                                    cols.add(""+cell.getNumericCellValue());
                                }

                                break;
                            case HSSFCell.CELL_TYPE_FORMULA:
                                cols.add(cell.getCellFormula());
                                break;
                            case HSSFCell.CELL_TYPE_BLANK:
                                cols.add("");
                                break;
                        }
                    }
                }

                data.add(cols);
            }
        } finally {
            is.close();
        }
        return data;
    }
    /**
     * 该方法把num类型强转成了string
     * @param is
     * @param firstCellNum
     * @param lastCellNum
     * @return
     * @throws IOException
     */
    public static List<List<String>> importFromExcelWithCellWithNUm(InputStream is,int firstCellNum,int lastCellNum) throws IOException {
        HSSFWorkbook wb = new HSSFWorkbook(is);
        HSSFSheet sheet = wb.getSheetAt(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        DecimalFormat df = new DecimalFormat("#");
        List<List<String>> data = new ArrayList<List<String>>();
        try {
            for (int r = sheet.getFirstRowNum() ; r <= sheet.getLastRowNum() ; r++) {
                HSSFRow row = sheet.getRow(r);
                if (row == null) {
                    continue;
                }

                List<String> cols = new ArrayList<String>();
                for (int c = firstCellNum ; c < lastCellNum ; c++) {
                    HSSFCell cell = row.getCell(c);
                    if (cell == null) {
                        cols.add("");
                    } else {
                        switch(cell.getCellType()) {
                            case HSSFCell.CELL_TYPE_STRING:
                                cols.add(cell.getStringCellValue());
                                break;
                            case HSSFCell.CELL_TYPE_BOOLEAN:
                                cols.add(""+cell.getBooleanCellValue());
                                break;
                            case HSSFCell.CELL_TYPE_NUMERIC:
                                if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                                    cols.add(sdf.format(cell.getDateCellValue()));
                                } else {

                                    cols.add( ""+df.format(cell.getNumericCellValue()));
//                                cols.add(""+cell.getNumericCellValue());
                                }

                                break;
                            case HSSFCell.CELL_TYPE_FORMULA:
                                cols.add(cell.getCellFormula());
                                break;
                            case HSSFCell.CELL_TYPE_BLANK:
                                cols.add("");
                                break;
                        }
                    }
                }

                data.add(cols);
            }
        } finally {
            is.close();
        }
        return data;
    }

    /***
     * 该方法把导入excel时对于数字类型的数据进行处理
     * 一般情况下excel中数字太长会转化成科学计数法
     * @param is
     * @return
     * @throws IOException
     */
    public static List<List<String>> importExcelWithCellWithNum(InputStream is) throws IOException {
        HSSFWorkbook wb = new HSSFWorkbook(is);
        HSSFSheet sheet = wb.getSheetAt(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        DecimalFormat df = new DecimalFormat("#");
        List<List<String>> data = new ArrayList<List<String>>();
        try {
            for (int r = sheet.getFirstRowNum() ; r <= sheet.getLastRowNum() ; r++) {
                HSSFRow row = sheet.getRow(r);
                if (row == null) {
                    continue;
                }

                List<String> cols = new ArrayList<String>();
                for (int c = row.getFirstCellNum() ; c < row.getLastCellNum() ; c++) {
                    HSSFCell cell = row.getCell(c);
                    if (cell == null) {
                        cols.add("");
                    } else {
                        switch(cell.getCellType()) {
                            case HSSFCell.CELL_TYPE_STRING:
                                cols.add(cell.getStringCellValue());
                                break;
                            case HSSFCell.CELL_TYPE_BOOLEAN:
                                cols.add(""+cell.getBooleanCellValue());
                                break;
                            case HSSFCell.CELL_TYPE_NUMERIC:
                                if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                                    cols.add(sdf.format(cell.getDateCellValue()));
                                } else {
                                    cols.add( ""+df.format(cell.getNumericCellValue()));
                                }
                                break;
                            case HSSFCell.CELL_TYPE_FORMULA:
                                cols.add(cell.getCellFormula());
                                break;
                            case HSSFCell.CELL_TYPE_BLANK:
                                cols.add("");
                                break;
                        }
                    }
                }

                data.add(cols);
            }
        } finally {
            is.close();
        }
        return data;
    }


    public static List<List<String>> importExcelWithNullCell(InputStream is) throws IOException {
        HSSFWorkbook wb = new HSSFWorkbook(is);
        HSSFSheet sheet = wb.getSheetAt(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        DecimalFormat df = new DecimalFormat("#");
        List<List<String>> data = new ArrayList<List<String>>();

        try {
            int lastCellNum = sheet.getRow(sheet.getFirstRowNum()).getLastCellNum();

            for (int r = sheet.getFirstRowNum() ; r <= sheet.getLastRowNum() ; r++) {
                HSSFRow row = sheet.getRow(r);
                if (row == null) {
                    continue;
                }

                List<String> cols = new ArrayList<String>();
                for (int c = 0 ; c < lastCellNum ; c++) {
                    HSSFCell cell = row.getCell(c);
                    if (cell == null) {
                        cols.add("");
                    } else {
                        switch(cell.getCellType()) {
                            case HSSFCell.CELL_TYPE_STRING:
                                cols.add(cell.getStringCellValue());
                                break;
                            case HSSFCell.CELL_TYPE_BOOLEAN:
                                cols.add(""+cell.getBooleanCellValue());
                                break;
                            case HSSFCell.CELL_TYPE_NUMERIC:
                                if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                                    cols.add(sdf.format(cell.getDateCellValue()));
                                } else {
                                    cols.add( ""+df.format(cell.getNumericCellValue()));
                                }
                                break;
                            case HSSFCell.CELL_TYPE_FORMULA:
                                cols.add(cell.getCellFormula());
                                break;
                            case HSSFCell.CELL_TYPE_BLANK:
                                cols.add("");
                                break;
                        }
                    }
                }

                data.add(cols);
            }
        } finally {
            is.close();
        }
        return data;
    }


    public static void main(String[] args) throws IOException {

        FileOutputStream os = new FileOutputStream(new File("d:/test.xls"));
        List<List<String>> data = new ArrayList<List<String>>();
        List<String> s = new ArrayList<String>();
        s.add("11");
        s.add("22");
        s.add("33");
        data.add(s);
        List<String> s1 = new ArrayList<String>();
        s1.add("11");
        s1.add("22");
        s1.add("33");
        data.add(s1);
        exportToExcel(os, data);
        System.out.println("data:"+data);

//        FileInputStream is = new FileInputStream(new File("d:/bank.xlsx"));
//        List<List<String>> data = importFromExcel(is);
//        System.out.println("data:"+data);
    }


}