package com.e.pkugrouper.Managers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/*
使用HttpURLConnection实现
 */
public class HttpManager implements IHttpManager{
    @Override
    public String httpGet(String url, List<String> parameters, String body) {
        String result="";
        BufferedReader in=null;
        HttpURLConnection connection=null;
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
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("Charsert", "UTF-8");
            connection.setRequestProperty("Authorization", body);

            // 配置本次连接的Content-type，json是"application/json"
            connection.setRequestProperty("Content-Type", "application/json");
            // 设置连接主机服务器的超时时间：15000毫秒
            connection.setConnectTimeout(15000);
            // 设置读取远程返回的数据时间：60000毫秒
            connection.setReadTimeout(60000);
            // 设置连接方式：get
            connection.setRequestMethod("GET");
            // 建立实际的连接，可不写，注意connection.getOutputStream会隐含的进行connect。
            connection.connect();

            // 定义BufferedReader输入流来读取URL的响应
            if (connection.getResponseCode() == 200) {
                in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line;
                while ((line = in.readLine()) != null) {
                    result = result+line;
                }
            }
        }catch(Exception e){
            System.out.println("发送GET请求出现异常！"+e);
            e.printStackTrace();
        }finally {
            try {
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
        return result;
    }

    @Override
    public String httpPost(String url, List<String> parameters, String body) {
        String result = "";
        PrintWriter out = null;
        BufferedReader in = null;
        HttpURLConnection connection = null;
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
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("Charsert", "UTF-8");
            connection.setRequestProperty("Authorization", body);

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
            out = new PrintWriter(connection.getOutputStream());
            out.print(parameters);
            out.flush();

            // 定义BufferedReader输入流来读取URL的响应
            if (connection.getResponseCode() == 200) {
                in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line;
                while ((line = in.readLine()) != null) {
                    result =result+line;
                }
            }
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
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        HttpURLConnection connection = null;
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
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("Charsert", "UTF-8");
            connection.setRequestProperty("Authorization", body);
            // 默认是 GET方式
            connection.setRequestMethod("PUT");
            // 配置本次连接的Content-type，json是"application/json"
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();

            connection.setConnectTimeout(15000);
            connection.setReadTimeout(60000);
            // 发送PUT请求必须设置如下两行，参数要放在http正文内
            connection.setDoOutput(true);
            connection.setDoInput(true);
            out = new PrintWriter(connection.getOutputStream());
            out.print(parameters);
            out.flush();
            if (connection.getResponseCode() == 200) {

                // 定义BufferedReader输入流来读取URL的响应

                in = new BufferedReader(new InputStreamReader(

                        connection.getInputStream()));

                String line;

                while ((line = in.readLine()) != null) {
                    result += line;
                }
            } else {
                
            }
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
