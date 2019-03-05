package com.luowj.Utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.Locale;
import java.util.ResourceBundle;

public class ConfigUtil {
    private static  ResourceBundle bundle =ResourceBundle.getBundle("config", Locale.CHINA);

    //返回excel名称
    public static   String getExcelName(){
        return bundle.getString("excelName");
    }

    //返回host
    public static   String getHost(){
        return bundle.getString("host");
    }

    // 实例化客户端
    public static DefaultHttpClient client = new DefaultHttpClient();

    //发起请求 返回response

    public  static HttpResponse  executeReqGetResponse(String url,String param)throws Exception{
        HttpPost post  = new HttpPost("http://"+url);
        post.setHeader("Content-type","application/x-www-form-urlencoded");

        StringEntity entity = new StringEntity(param);

        post.setEntity(entity);
        HttpResponse response=ConfigUtil.client.execute(post);


        return response;
    }


    //发起请求返回 response.字符串
    public static  String  executeReqGetResStr(String url,String param)throws Exception{
        HttpPost post  = new HttpPost("http://"+url);
        post.setHeader("Content-type","application/x-www-form-urlencoded");

        StringEntity entity = new StringEntity(param);

        post.setEntity(entity);
        HttpResponse response=ConfigUtil.client.execute(post);

        String result= EntityUtils.toString(response.getEntity(),"utf-8");

        return result;
    }
}
