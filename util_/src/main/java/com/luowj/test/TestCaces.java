package com.luowj.test;

import com.luowj.Utils.ExcelUtil_;
import org.testng.annotations.Test;

import java.util.HashMap;

@Test
public class TestCaces {

    public  void test( ) throws Exception {
        ExcelUtil_  excelData = new ExcelUtil_("testdata2","account");
        HashMap<String,String>[][]  data= (HashMap<String,String>[][])excelData.getExcelData();
        for(HashMap<String,String>[]data1:data){
            for(HashMap<String,String> map:data1){

                System.out.println(map.toString());
            }
        }

    }
}

