package com.luowj.Utils;



import jxl.Sheet;
import jxl.Workbook;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtil {
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

     public static List< HashMap<String, String>[][]>  getExcelData(){
         //实例化一个list，用来存放每一个sheet返回的sheetdata

         List< HashMap<String, String>[][]>  list = new ArrayList < HashMap<String, String>[][]>() ;
         for (Sheet sheet:sheets){
             HashMap<String, String>[][] map= getSheetData(sheet);
             list.add(map);
         }
         return list;
    }


    public static  Map<String,HashMap<String,String>[][]>  getExcelDataBig(){
        //实例化一个map，用来存放每一个sheet返回的sheetdata
        Map<String,HashMap<String,String>[][]> map_ = new HashMap <String,HashMap<String,String>[][]> ();

        for (Sheet sheet:sheets){
            HashMap<String, String>[][] map= getSheetData(sheet);
            map_.put(sheet.getName(),map);
        }
        return map_;
    }
    /**
     * 获得Sheet中的数据
     */
    private static HashMap<String, String>[][] getSheetData(Sheet sheet)  {
        ArrayList<String> arrkey = new ArrayList<String>();
        int rows = sheet.getRows();
        int columns = sheet.getColumns();
        // 为了返回值是Object[][],定义一个多行单列的二维数组
        HashMap<String, String>[][] arrmap = new HashMap[rows - 1][1];
        // 对数组中所有元素hashmap进行初始化
        if (rows > 1) {
            for (int i = 0; i < rows - 1; i++) {
                arrmap[i][0] = new HashMap<String, String>();
            }
        } else {
            System.out.println("excel中没有数据");
        }

        // 获得首行的列名，作为hashmap的key值
        for (int c = 0; c < columns; c++) {
            String cellvalue = sheet.getCell(c, 0).getContents();
            arrkey.add(cellvalue);
        }
        // 遍历所有的单元格的值添加到hashmap中
        for (int r = 1; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                String cellvalue = sheet.getCell(c, r).getContents();
                arrmap[r - 1][0].put(arrkey.get(c), cellvalue);
            }
        }
        return arrmap;
    }

    /**
     * 获得excel文件的路径
     * @return
     * @throws IOException
     */
    private static String getPath() throws IOException {
        File directory = new File(".");
        String sourceFile = directory.getCanonicalPath() + "\\src\\main\\resources\\"
                +  ConfigUtil.getExcelName() + ".xls";
        return sourceFile;
    }

}
