package com.e.pkugrouper.Managers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.e.pkugrouper.Models.IMessage;
import com.e.pkugrouper.Models.IMission;
import com.e.pkugrouper.Models.IUser;
import com.e.pkugrouper.Models.Message;
import com.e.pkugrouper.Models.Mission;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class MessageManager extends HttpManager implements IMessageManager{
    private IUser currentUser;

    private final String user_not_found = "\"user Not Found\"";
    private final String bad_request = "\"Bad Request\"";
    private final String ok = "\"OK\"";
    private final String reportee_not_found = "\"reportee Not Found\"";
    private final String message_not_found = "\"message Not Found\"";

    public IUser getCurrentUser(){
        return currentUser;
    }

    @Override
    public void setCurrentUser(IUser _currentUser) {
        if (_currentUser == null) {
            throw new RuntimeException("currentUser is null!");
        }
        else {
            currentUser = _currentUser;
        }
    }

    @Override
    public List<IMessage> getCurrentUserMessages() {
        //判断currentUser是否存在
        if(currentUser == null){
            throw new RuntimeException("currentUser is null!");
        }

        //得到currentUser的信息
        String url = "/messages";
        List<String> parameters = Arrays.asList(String.valueOf(currentUser.getUserID()));
        JSONObject request_body = new JSONObject();
        request_body.put("senderID",currentUser.getUserID());
        request_body.put("passwordAfterRSA", currentUser.getPassword());
        String Message_JSON_All = httpGet(url, parameters, request_body.toJSONString());

        //如果得不到信息
        if(Message_JSON_All.equals(user_not_found)){
            throw new RuntimeException("User is not found!");
        }
        else if(Message_JSON_All.equals(bad_request)){
            throw new RuntimeException("Get messages is bad request!");
        }

        List<Integer> message_id_list = JSONObject.parseArray(Message_JSON_All, Integer.class);

        List<IMessage> message_list = new ArrayList<>();
        for(int message_id: message_id_list){
            IMessage message = findMessageByID(message_id);
            if(message != null){
                message_list.add(message);
            }
        }
        return message_list;
    }

    @Override
    public boolean reportBug(IMessage bug) {
        //检查currentUser
        if(currentUser == null){
            throw new RuntimeException("currentUser is null!");
        }

        //检查参数
        if(bug == null || !bug.getType().equals("Bug")) {
            throw new RuntimeException("You should send a bug!");
        }

        //报告bug
        String url = "/message/bug";
        List<String> parameters = Arrays.asList(String.valueOf(currentUser.getUserID()));
        JSONObject request_body = new JSONObject();
        request_body.put("senderID", currentUser.getUserID());
        request_body.put("passwordAfterRSA", currentUser.getPassword());
        request_body.put("messageContent", bug.getMessageContent());
        String bug_response = httpPost(url, parameters, request_body.toJSONString());

        //检查是否报告成功
        if (bug_response.equals(ok)){
            //report bug success
            return true;
        }
        else if (bug_response.equals(user_not_found)){
            throw new RuntimeException("User is not found!");
        }

        return false;
    }

    @Override
    public boolean report(IMessage report) {
        //检查currentUser
        if(currentUser == null){
            throw new RuntimeException("currentUser is null!");
        }

        //检查参数
        if(report == null || !report.getType().equals("Report")){ //目前是0，后面根据report类型对应的数字更换
            throw new RuntimeException("You should send a Report!");
        }

        //报告
        String url = "/message/report";
        List<String> parameters = Arrays.asList(String.valueOf(currentUser.getUserID()),
                String.valueOf(report.getReporteeID()));
        JSONObject request_body = new JSONObject();
        request_body.put("senderID",currentUser.getUserID());
        request_body.put("passwordAfterRSA", currentUser.getPassword());
        request_body.put("messageContent", report.getMessageContent());
        String report_response = httpPost(url, parameters, request_body.toJSONString());

        //检查是否报告成功
        if(report_response.equals(ok)){
            //report success
            return true;
        }

        if(report_response.equals(user_not_found)){
            throw new RuntimeException("User is not found!");
        }
        else if(report_response.equals(reportee_not_found)){
            throw new RuntimeException("Reportee is not found!");
        }

        return false;
    }

    @Override
    public IMessage findMessageByID(int messageID) {
        //检查参数
        if(currentUser == null){
            throw new RuntimeException("currentUser is null!");
        }

        if(messageID <= 0){
            throw new RuntimeException("messageID should be greater than 0!");
        }

        String url = "/message";
        List<String> parameters = Arrays.asList(String.valueOf(messageID));
        JSONObject request_body = new JSONObject();
        request_body.put("senderID",currentUser.getUserID());
        request_body.put("passwordAfterRSA", currentUser.getPassword());
        String message_JSON = httpGet(url, parameters, request_body.toJSONString());



        //获取Message失败
        if(message_JSON.equals(message_not_found)){
            throw new RuntimeException("message is not found!");
        }
        //成功得到信息
        else{
            IMessage message = new Message();
            message.loadFromJSON(message_JSON);
            return message;
        }
    }

    @Override
    public List<IMessage> findMessages(int[] messageIDs) {
        if(currentUser == null){
            throw new RuntimeException("currentUser is null!");
        }

        String url = "/findmessages";
        List<String> parameters = Arrays.asList(String.valueOf(currentUser.getUserID()));
        JSONObject request_body = new JSONObject();
        request_body.put("senderID",currentUser.getUserID());
        request_body.put("passwordAfterRSA", currentUser.getPassword());
        request_body.put("messageIDs", messageIDs);
        String find_response = httpPost(url, parameters, request_body.toJSONString());

        if(find_response.equals(user_not_found)){
            throw new RuntimeException("User is not found!");
        }

        if(find_response.equals(message_not_found)){
            throw new RuntimeException("message is not found!");
        }

        List<IMessage> Message_List = new ArrayList<>();
        JSONArray platformArray = JSON.parseArray(find_response);
        for (Object jsonObject : platformArray) {
            String message_json = jsonObject.toString();
            IMessage message = new Message();
            message.loadFromJSON(message_json);
            Message_List.add(message);
        }
        return Message_List;
    }

}
