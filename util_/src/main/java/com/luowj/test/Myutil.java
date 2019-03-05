package com.luowj.test;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.util.Set;


public class Myutil{
    public static  void main(String args[]) throws Exception {

  /*      InputStream in = Myutil.class.getClassLoader().getResourceAsStream("demo.json");
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
*/
        int succ=0;
        int fail=0;
        JSONTokener jt = new JSONTokener(new FileReader(new File("C:\\Users\\lenovo\\workspace\\InterfaceAutoTest\\util_\\target\\classes\\swagger.json")));


        JSONObject jo= (JSONObject)jt.nextValue();
        //System.out.println("jsonFile:"+jo.toString());
        String host=jo.getString("host");

        JSONObject pathJson=jo.getJSONObject("paths");
        //System.out.println(pathJson);

        Set<String> set =pathJson.keySet();

        JSONObject tem;

        //实例化客户端socket；
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();


        for(String key : set){
            tem=pathJson.getJSONObject(key);
            //if(!key.startsWith("/rebuildApi") )

            if(key.startsWith("/AccountService") )
            {
                JSONObject  postJson =tem.getJSONObject("post");
                String desStr = postJson.getString("description");
                JSONArray consumesArr=postJson.getJSONArray("consumes");
                String consumesStr  =consumesArr.getString(0);
                JSONArray jsonArray =postJson.getJSONArray("parameters");

                String  param ="";
                // JSONObject requestJson =new JSONObject();
                for (int i=0 ;i<jsonArray.length();i++){
                    JSONObject  para= jsonArray.getJSONObject(i);
                    String name = para.getString("name");
                    String type = para.getString("type");
                    String d_value;
                    try {
                        d_value=para.getString("default");
                    }catch (JSONException exception){
                        d_value="";
                    }

                    if(name.equals("checkCode")){
                        d_value="888888";
                    }

                    boolean required=para.getBoolean("required");

                    //requestJson.put(name,d_value);
                    if(i==(jsonArray.length()-1) )
                        param=param+name+"="+d_value;
                    else
                        param=param+name+"="+d_value+"&";

                }
                System.out.println(param);
                System.out.println("http://"+host+key);

                HttpPost post  = new HttpPost("http://"+host+key);
                post.setHeader("Content-type","application/x-www-form-urlencoded");

                StringEntity entity = new StringEntity(param);

                post.setEntity(entity);
                HttpResponse response=defaultHttpClient.execute(post);

                String result= EntityUtils.toString(response.getEntity(),"utf-8");
                //System.out.println(result);

                if(result.contains("\"result\":\"0000\"")){
                    succ++;
                }else {
                    fail++;
                }
            }

        }

        System.out.println("成功的次数"+succ+"\n失败的次数"+fail);


    }
}
