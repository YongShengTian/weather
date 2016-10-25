package com.example.tys.weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by tys on 2016/10/24.
 */
public class GetCityList {
       final static String appkey="087d4a821184edc8351fe73006d2d09d";
       public static String  post(String citynames)
       {
           StringBuffer sb=new StringBuffer();
           String cityname="";
           ///如果是不是直辖市，就加载到市
           if (citynames.split(",")[0].equals("北京")||citynames.split(",")[0].equals("天津")||citynames.split(",")[0].equals("上海")||citynames.split(",")[0].equals("重庆"))
           {
               cityname=citynames.split(",")[0];
           }
           ///直辖市，加载到市
           else
           {
               cityname=citynames.split(",")[1];
           }
           String encodecity=URLEncoder.encode(cityname);
           try {
               URL url=new URL("http://v.juhe.cn/weather/index?format=2&cityname="+encodecity+"&key="+appkey);
               BufferedReader br=new BufferedReader(new InputStreamReader(url.openStream()));
               String str="";
               while ((str=br.readLine())!=null){
                  sb.append(str);
               }
               br.close();
           } catch (MalformedURLException e) {
               e.printStackTrace();
           } catch (IOException e) {
               e.printStackTrace();
           }
           return sb.toString();
       }
}
