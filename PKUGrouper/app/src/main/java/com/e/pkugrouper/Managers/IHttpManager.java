package com.e.pkugrouper.Managers;

import java.util.List;

public interface IHttpManager {
    String httpGet(String url, List<String> parameters, String body);
    String httpPost(String url, List<String> parameters, String body);
    String httpPut(String url, List<String> parameters, String body);
}
