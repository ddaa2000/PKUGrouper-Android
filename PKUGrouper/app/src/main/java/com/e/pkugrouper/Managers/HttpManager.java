package com.e.pkugrouper.Managers;


import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/*
使用HttpURLConnection实现
 */
public class HttpManager implements IHttpManager{
    private final String user_not_found = "\"user Not Found\"";
    private final String getter_not_found = "\"getter Not Found\"";
    private final String mission_not_found = "\"mission Not Found\"";
    private final String gettee_not_found = "\"gettee Not Found\"";
    private final String bad_request = "\"Bad Request\"";
    private final String forbidden = "\"Forbidden\"";
    private final String bad_username = "\"bad username\"";
    private final String ok = "\"OK\"";
    private final String wrong_password = "\"wrong old password\"";
    private final String invalid_password = "\"invalid new password\"";
    private final String evaluator_not_found = "\"evaluater Not Found\"";
    private final String evaluatee_not_found = "\"evaluatee Not Found\"";
    private final String evaluation_not_found = "\"evaluation Not Found\"";
    private final String reportee_not_found = "\"reportee Not Found\"";
    private final String message_not_found = "\"message Not Found\"";
    private final String invalid_time = "\"invalid time\"";
    private final String applicant_not_found = "\"applicant Not Found\"";
    private final String httpbegin="http://62.234.0.67:8000";
    @Override
    public String httpGet(String url, List<String> parameters, String body) {
        String result="";
        OutputStreamWriter out=null;
        BufferedReader in=null;
        HttpURLConnection connection=null;
        url=httpbegin+url;
        if(parameters!=null && parameters.size()>0){
            for (String str:parameters){
                str='/'+str;
                url=url+str;
            }
        }//根据参数修改url
        try{
            URL geturl=new URL(url);
            // 打开和URL之间的连接
            connection = (HttpURLConnection) geturl.openConnection();

            // 在connect之前，设置通用的请求属性
            connection.setRequestProperty("accept", "application/json");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("Charsert", "UTF-8");

            connection.setConnectTimeout(15000);
            connection.setReadTimeout(60000);
            // 发送POST请求必须设置如下两行，参数要放在http正文内
            connection.setDoOutput(true);
            connection.setDoInput(true);
            // 默认是 GET方式
            connection.setRequestMethod("POST");
            // Post 请求不使用缓存
            connection.setUseCaches(false);
            // 配置本次连接的Content-type，json是"application/json"
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();

            // 定义BufferedReader输入流来读取URL的响应
            if (connection.getResponseCode() == 200) {
                in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line;
                while ((line = in.readLine()) != null) {
                    result = result+line;
                }
            }else{
                in = new BufferedReader(new InputStreamReader(connection.getErrorStream(), "UTF-8"));
                String line;
                while ((line = in.readLine()) != null) {
                    result = result+line;
                }
            }
            // 定义BufferedReader输入流来读取URL的响应
//            if (connection.getResponseCode() == 200) {
//                in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
//                String line;
//                while ((line = in.readLine()) != null) {
//                    result = result+line;
//                }
//            }
        }catch(Exception e){
            System.out.println("发送GET请求出现异常！"+e);
            e.printStackTrace();
        }finally {
            try {
                if(out != null){
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
                if (connection != null) {
                    //关闭连接
                    connection.disconnect();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
//        Log.e("result:",result);
        return result;
    }

    @Override
    public String httpPost(String url, List<String> parameters, String body) {
        String result = "";
        OutputStreamWriter out=null;
        BufferedReader in = null;
        HttpURLConnection connection = null;
        url=httpbegin+url;
        if(parameters!=null && parameters.size()>0){
            for (String str:parameters){
                str='/'+str;
                url=url+str;
            }
        }
        try {
            URL posturl = new URL(url);
            // 打开和URL之间的连接
            connection = (HttpURLConnection) posturl.openConnection();

            // 在connect之前，设置通用的请求属性
            connection.setRequestProperty("accept", "application/json");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("Charsert", "UTF-8");

            connection.setConnectTimeout(15000);
            connection.setReadTimeout(60000);
            // 发送POST请求必须设置如下两行，参数要放在http正文内
            connection.setDoOutput(true);
            connection.setDoInput(true);
            // 默认是 GET方式
            connection.setRequestMethod("POST");
            // Post 请求不使用缓存
            connection.setUseCaches(false);
            // 配置本次连接的Content-type，json是"application/json"
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();

            // 参数要放在http正文内
            //1.获取URLConnection对象对应的输出流
            out=new OutputStreamWriter(connection.getOutputStream(),"UTF-8");
            out.write(body);
            out.flush();

            // 定义BufferedReader输入流来读取URL的响应

            if (connection.getResponseCode() == 200) {
                in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line;
                while ((line = in.readLine()) != null) {
                    result =result+line;
                }
            }else{
                in = new BufferedReader(new InputStreamReader(connection.getErrorStream(), "UTF-8"));
                String line;
                while ((line = in.readLine()) != null) {
                    result = result+line;
                }
            }
//            if (connection.getResponseCode() == 200) {
//                in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
//                String line;
//                while ((line = in.readLine()) != null) {
//                    result =result+line;
//                }
//            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
                if (connection != null) {
                    //关闭连接
                    connection.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public String httpPut(String url, List<String> parameters, String body) {
        OutputStreamWriter out=null;
        BufferedReader in = null;
        String result = "";
        HttpURLConnection connection = null;
        url=httpbegin+url;
        if(parameters!=null && parameters.size()>0){
            for (String str:parameters){
                str='/'+str;
                url=url+str;
            }
        }
        try {
            URL puturl = new URL(url);
            // 打开和URL之间的连接
            connection = (HttpURLConnection) puturl.openConnection();

            // 在connect之前，设置通用的请求属性
            connection.setRequestProperty("accept", "application/json");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("Charsert", "UTF-8");
            connection.setRequestProperty("Authorization", body);
            // 默认是 GET方式
            connection.setRequestMethod("PUT");
            // 配置本次连接的Content-type，json是"application/json"
            connection.setRequestProperty("Content-Type", "application/json");


            connection.setConnectTimeout(15000);
            connection.setReadTimeout(60000);
            // 发送PUT请求必须设置如下两行，参数要放在http正文内
            connection.setDoOutput(true);
            connection.setDoInput(true);

            connection.connect();

            out=new OutputStreamWriter(connection.getOutputStream(),"UTF-8");
            out.write(body);
            out.flush();

            in = new BufferedReader(new InputStreamReader(

                    connection.getInputStream()));


                        connection.getInputStream()));

                String line;

                while ((line = in.readLine()) != null) {
                    result += line;
                }
            } else{
                in = new BufferedReader(new InputStreamReader(connection.getErrorStream(), "UTF-8"));
                String line;
                while ((line = in.readLine()) != null) {
                    result = result+line;
                }
            }

//            if (connection.getResponseCode() == 200) {
//
//                // 定义BufferedReader输入流来读取URL的响应
//
//                in = new BufferedReader(new InputStreamReader(
//
//                        connection.getInputStream()));
//
//                String line;
//
//                while ((line = in.readLine()) != null) {
//                    result += line;
//                }
//            } else {
//
//            }
        } catch (Exception e) {
            System.out.println("发送 PUT 请求出现异常！" + e);
            e.printStackTrace();
        }finally{
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
                if (connection != null) {
                    //关闭连接
                    connection.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

}
