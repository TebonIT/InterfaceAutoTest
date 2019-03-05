package com.luowj.Utils;


import jxl.Sheet;
import jxl.Workbook;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtils {
    private static Workbook workbook;

    static  {
        try {
            workbook = Workbook.getWorkbook(new File(getPath()));
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException("获取excel对象失败");
        }
    }



    private static Sheet sheets[]=workbook.getSheets();





    //返回一个二维数组包括excel所有的值
    public static Object[][]  getExcelDataBig(){
        //实例化一个map，用来存放每一个sheet返回的sheetdata

        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

        for (Sheet sheet:sheets){
            List<HashMap<String, String>> listShell= getSheetData(sheet);
            list.addAll(listShell);
        }

        // 定义一个object[][] 的二维数组，存放list中的值
        Object[][] arrayObject = new Object[list.size()][1];
        for (int a = 0; a < list.size(); a++) {
            arrayObject[a][0]=list.get(a);
        }
        return arrayObject;
    }
    /**
     * 获得Sheet中的数据
     */
    private static List<HashMap<String, String>>  getSheetData(Sheet sheet)  {
        List<HashMap<String, String>> listResult = new ArrayList<HashMap<String, String>>();
        ArrayList<String> arrkey = new ArrayList<String>();
        int rows = sheet.getRows();
        int columns = sheet.getColumns();
        // 为了返回值是Object[][],定义一个多行单列的二维数组

        // 对数组中所有元素hashmap进行初始化
/*        if (rows > 1) {
            for (int i = 0; i < rows - 1; i++) {
                arrmap[i] = new HashMap<String, String>();
            }
        } else {
            System.out.println("excel中没有数据");
        }*/

        // 获得首行的列名，作为hashmap的key值
        for (int c = 0; c < columns; c++) {
            String cellvalue = sheet.getCell(c, 0).getContents();
            arrkey.add(cellvalue);
        }
        // 遍历所有的单元格的值添加到hashmap中
        for (int r = 1; r < rows; r++) {
            HashMap<String, String> arrmap = new HashMap<String, String> ();

            for (int c = 0; c < columns; c++) {
                String cellvalue = sheet.getCell(c, r).getContents();
                arrmap.put(arrkey.get(c), cellvalue);
            }

            listResult.add(arrmap);
        }
        return listResult;
    }

    /**
     * 获得excel文件的路径
     * @return
     * @throws IOException
     */
    private static String getPath() throws IOException {
        File directory = new File(".");
        String sourceFile = directory.getCanonicalPath() + "\\src\\main\\resources\\"
                +  ConfigUtil.getExcelName();
        return sourceFile;
    }







}
