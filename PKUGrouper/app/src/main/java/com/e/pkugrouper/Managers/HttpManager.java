package com.e.pkugrouper.Managers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Arrays;
import java.util.List;

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
    @Override
    public String httpGet(String url, List<String> parameters, String body) {
        JSONObject JSON = JSONObject.parseObject(body);

        if(url.equals("/user/evaluation")){
            int UID = JSON.getInteger("senderID");
            String evaluationID = parameters.get(0);
            if(evaluationID.equals("1")){
                return evaluation_not_found;
            }
            else {
                JSONObject evaluation = new JSONObject();
                evaluation.put("evaluationScore", evaluationID);
                evaluation.put("evaluatorID", UID);
                return evaluation.toJSONString();
            }
        }

        if(url.equals("/user/evaluations")){
            int UID = JSON.getInteger("senderID");
            if(UID == 1){
                return user_not_found;
            }
            else {
                List<Integer> evaluation_ids = Arrays.asList(2,3,4,5,6);
                return com.alibaba.fastjson.JSON.toJSONString(evaluation_ids);
            }
        }

        if(url.equals("/user/self")){
            int UID = JSON.getInteger("senderID");
            if(UID == 16)
                return user_not_found;
            else{
                JSONObject Common_JSON = new JSONObject();
                Common_JSON.put("mailbox","1800013014");
                Common_JSON.put("username", "myx");
                return Common_JSON.toJSONString();
            }
        }

        if(url.equals("/user/member")){
            int UID = JSON.getInteger("senderID");
            if(UID == 16){
                return getter_not_found;
            }
            else if(UID == 18){
                return bad_request;
            }
            else if(UID == 19){
                return forbidden;
            }

            String mission = parameters.get(1);
            if(mission.equals("1")){
                return mission_not_found;
            }

            String member = parameters.get(2);
            if(member.equals("1")){
                return gettee_not_found;
            }

            JSONObject member_JSON = new JSONObject();
            member_JSON.put("mailbox", "1800013014");
            member_JSON.put("username", "myx");
            member_JSON.put("averageScore", 5);
            return member_JSON.toJSONString();
        }

        if(url.equals("/message")){
            String messageID = parameters.get(0);
            if(messageID.equals("2")){
                return message_not_found;
            }
            if(messageID.equals("3")) {
                JSONObject message_JSON = new JSONObject();
                message_JSON.put("messageContent", "How are you?");
                message_JSON.put("type", "Notice");
                return message_JSON.toJSONString();
            }
            int messageid = Integer.parseInt(messageID);
            String[] types={"Report", "Bug", "Notice"};
            String[] Contents = {"Hello", "Hi", "Are you OK?"};
            JSONObject message_JSON = new JSONObject();
            message_JSON.put("messageContent", Contents[messageid-5]);
            message_JSON.put("type", types[messageid-5]);
            return message_JSON.toJSONString();
        }

        if(url.equals("/messages")){
            int UID = JSON.getInteger("senderID");
            if(UID == 1){
                return user_not_found;
            }
            else if(UID == 2){
                return bad_request;
            }
            List<Integer> evaluation_ids = Arrays.asList(5,6,7);
            return com.alibaba.fastjson.JSON.toJSONString(evaluation_ids);
        }
        return null;
    }

    @Override
    public String httpPost(String url, List<String> parameters, String body) {
        JSONObject JSON = JSONObject.parseObject(body);

        if(url.equals("/user/register")){
            String captha = JSON.getString("captchaCode");
            if(captha == null)
                return null;
            if(captha.equals("1")){
                return bad_request;
            }
            else if(captha.equals("2")){
                return bad_username;
            }
            else if(captha.equals("3")){
                JSONObject UID_JSON = new JSONObject();
                UID_JSON.put("UID", 12);
                return UID_JSON.toJSONString();
            }
        }
        if(url.equals("/user/login")){
            String password = JSON.getString("passwordAfterRSA");
            if(password.equals("fuck")){
                return forbidden;
            }
            else{
                JSONObject UID_JSON = new JSONObject();
                UID_JSON.put("UID", 12);
                return UID_JSON.toJSONString();
            }
        }
        if(url.equals("/user/captcha")){
            String mailbox = JSON.getString("mailbox");
            if(mailbox.equals("abcd"))
                return bad_request;
            else
                return ok;
        }

        if(url.equals("/user/evaluate")){
            String evaluator = parameters.get(0);
            if(evaluator.equals("16")){
                return evaluator_not_found;
            }
            else if(evaluator.equals("18")){
                return bad_request;
            }
            else if(evaluator.equals("19")){
                return forbidden;
            }

            String mission = parameters.get(1);
            if(mission.equals("1")){
                return mission_not_found;
            }

            String evaluatee = parameters.get(2);
            if(evaluatee.equals("1")){
                return evaluatee_not_found;
            }
            return ok;
        }

        if(url.equals("/message/bug")){
            int UID = JSON.getInteger("senderID");
            if(UID == 1)
                return user_not_found;
            else
                return ok;
        }

        if(url.equals("/message/report")){
            int UID = JSON.getInteger("senderID");
            if(UID == 1)
                return user_not_found;

            String reporteeID = parameters.get(1);
            if(reporteeID.equals("1")){
                return reportee_not_found;
            }
            return ok;
        }
        return null;
    }

    @Override
    public String httpPut(String url, List<String> parameters, String body) {
        JSONObject JSON = JSONObject.parseObject(body);
        if(url.equals("/user/code")){
            int UID = JSON.getInteger("senderID");
            System.out.println(UID);
            if(UID == 10){
                return bad_request;
            }
            else if(UID == 11){
                return user_not_found;
            }

            String old_password = JSON.getString("passwordAfterRSA");
            if(old_password.equals("hzh")){
                return wrong_password;
            }

            String new_password = JSON.getString("newPasswordAfterRSA");
            if(new_password.equals("myx")){
                return invalid_password;
            }
            return ok;
        }

        if(url.equals("/user/info")){
            int UID = JSON.getInteger("senderID");
            if(UID == 16){
                return user_not_found;
            }
            else if(UID == 17){
                return bad_request;
            }
            else
                return ok;
        }
        return null;
    }
}
