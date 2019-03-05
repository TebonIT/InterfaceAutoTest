package com.luowj.Utils;



import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.util.Properties;
import java.util.Set;

public class SwaToExc {

    public static  void main(String args[])throws  Exception{





        //新建excle文件
        String excelName="testdata.xls";
        File xlsFile = new File(getPath()+excelName);
        WritableWorkbook workbook = Workbook.createWorkbook(xlsFile);

        //将swagger 文件 转成json
        InputStream in = SwaToExc.class.getClassLoader().getResourceAsStream("swagger.json");
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        JSONTokener jt = new JSONTokener(br);
        JSONObject jo= (JSONObject)jt.nextValue();


        //将host写到配置文件中
        Properties pro = new Properties();
        OutputStream out = new FileOutputStream(getPath()+"config.properties");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out,"UTF-8"));
        String host=jo.getString("host");

        System.out.println(host);
        pro.setProperty("host", host);
        pro.setProperty("excelName", excelName);

        pro.store(bw, "uri");
        out.close();

        //取对应的值
        JSONObject pathJson=jo.getJSONObject("paths");
        //System.out.println(pathJson);

        Set<String> set =pathJson.keySet();

        JSONObject tem;

        //循环去参数

        for(String key : set)
        {
            tem=pathJson.getJSONObject(key);

            int x=0;
            WritableSheet sheet = workbook.createSheet(key.replace("/","_"),x++ );
           // System.out.println(key.replace("/","_"));
            if(!key.startsWith("/rebuildApi") )
            {
                JSONObject  postJson =tem.getJSONObject("post");
                String desStr = postJson.getString("description");
                JSONArray consumesArr=postJson.getJSONArray("consumes");
                String consumesStr  =consumesArr.getString(0);
                JSONArray jsonArray =postJson.getJSONArray("parameters");

                String  param ="";
                // JSONObject requestJson =new JSONObject();



                for (int i=0 ;i<jsonArray.length();i++)
                {

                    JSONObject  para= jsonArray.getJSONObject(i);
                    String name = para.getString("name");
                    String type = para.getString("type");
                    String d_value;
                    try
                    {
                        d_value=para.getString("default");
                    }catch (JSONException exception)
                    {
                        d_value="";
                    }

                    if(name.equals("checkCode"))
                    {
                        d_value="888888";
                    }

                    //创建单元格  将键值写到对应的单元格中

                    Label label =new Label(i+1,0,name);
                    Label labelVa =new Label(i+1,1,d_value);

                    //添加到sheet中
                    sheet.addCell(label);
                    sheet.addCell(labelVa);


                }
                //创建uri、断言方式、预期结果单元格式
                Label uriLabel = new Label(0,0,"uri");
                Label uriLabelVa = new Label(0,1,key);
                Label myTypeLabel = new Label(jsonArray.length()+1,0,"myType");
                Label myTypeLabelVa = new Label(jsonArray.length()+1,1,"");
                Label ExpectLabel = new Label(jsonArray.length()+2,0,"expect");
                Label ExpectLabelVa = new Label(jsonArray.length()+2,1,"");

                //加入sheet中
                sheet.addCell(uriLabel);
                sheet.addCell(uriLabelVa);
                sheet.addCell(myTypeLabel);
                sheet.addCell(myTypeLabelVa);
                sheet.addCell(ExpectLabel);
                sheet.addCell(ExpectLabelVa);

            }
        }

        //写入到excel文件中
        workbook.write();
        workbook.close();
    }

    public static String getPath() throws IOException {
        File directory = new File(".");
        String sourceFile = directory.getCanonicalPath() + "\\src\\main\\resources\\";
        return sourceFile;
    }
}
