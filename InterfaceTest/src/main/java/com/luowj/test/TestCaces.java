package com.luowj.test;

import com.luowj.Utils.CustomizedException;
import com.luowj.Utils.ConfigUtil;
import com.luowj.Utils.ExcelUtils;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.HashMap;
import java.util.Set;


public class TestCaces {



    @DataProvider(name = "excelData")
    public  Object[][] testdata( ) throws Exception {
        Object[][] array=ExcelUtils.getExcelDataBig();
        return array;
    }



    @Test(dataProvider = "excelData",groups = "InterfaceTest")
    public  void  test(HashMap<String, String> map ) throws Exception
    {

        String url= ConfigUtil.getHost()+map.get("uri");
        String param="";
        Set<String> set= map.keySet();
        //迭代每条案例，取值拼接请求参数
        int i=0;
        for(String key:set)
        {

            String k_value =map.get(key);

            if (!(key.equals("uri")||key.equals("myType")||key.equals("expect"))){

                if(i==(set.size()-1) )
                {
                    param = param + key + "=" + k_value;
                }
                else
                    param=param+key+"="+k_value+"&";
            }
            i++;
        }

        try {
            //发送请求，得到相应字符串
            String result = ConfigUtil.executeReqGetResStr(url,param);
            if (result.contains("\"result\":\"0000\"")) {
                Assert.assertTrue(true);
            } else {
                Assert.fail("请求应答为" + result);
            }
        }catch (Exception e)
        {
            throw new CustomizedException(e.getMessage());
        }

    }

    @Test(groups ="demo1",priority = 1)
    public  void  testdemo1(){
        System.out.println("ceshi1");
       // Assert.fail("测试失败");
    }

    @Test(groups ="demo1",priority = 3)
    public  void  testdeme3() throws  Exception{
        System.out.println("ceshi3");
    }
    @Test(groups ="demo1",priority = 2)
    public  void  testdeme2(){
        System.out.println("ceshi2");
    }

}

