package com.e.pkugrouper.Managers;

import java.util.List;

/**
 * @author ddaa
 * HttpManager应当实现的接口，HttpManager是对Http协议的有关方法的一个封装，便于其他模块的调用
 */
public interface IHttpManager {
    String httpGet(String url, List<String> parameters, String body);
    String httpPost(String url, List<String> parameters, String body);
    String httpPut(String url, List<String> parameters, String body);
}
