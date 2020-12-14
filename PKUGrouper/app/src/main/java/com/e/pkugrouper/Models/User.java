package com.e.pkugrouper.Models;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class User implements IUser{
    private String mailBox;
    private String userName;
    private int userID;
    private String password;
    private String tele;
    private double averageScore;

    private List<Integer> missionIDs;
    private List<Integer> messageIDs;
    private List<Integer> violationIDs;
    private List<Integer> evaluationIDs;

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public void setUserName(String _userName) {
        userName=_userName;
    }

    
    
    @Override
    public String getPassword() {
    	return password;
    }
    
    @Override
    public void setPassword(String _password) {
    	password=_password;
    }
   
    
    @Override
    public String getMailBox() {
        return mailBox;
    }

    @Override
    public void setMailBox(String _mailBox) {
        mailBox=_mailBox;
    }

    @Override
    public int getUserID() {
        return userID;
    }

    @Override
    public void setUserID(int _userID) {
        userID=_userID;
    }

    @Override
    public String getTele() {
        return tele;
    }

    @Override
    public void setTele(String _contactInformation) {
        tele =_contactInformation;
    }

    @Override
    public List<Integer> getMessageIDs() {
        return messageIDs;
    }

    @Override
    public List<Integer> getEvaluationIDs() {
        return evaluationIDs;
    }

    @Override
    public List<Integer> getViolationIDs() {
        return  violationIDs;
    }

    @Override
    public List<Integer> getMissionIDs() {
        return missionIDs;
    }

    @Override
    public double getAverageScore(){
        return averageScore;
    }

    @Override
    public void setAverageScore(double _averageScore){
        averageScore=_averageScore;
    }

    @Override
    public void setMessageIDs(List<Integer> _messageIDs) {
    	messageIDs=_messageIDs;
    }
    @Override
    public void setEvaluationIDs(List<Integer> _evaluationIDs) {
    	evaluationIDs=_evaluationIDs;
    }
    @Override
    public void setMissionIDs(List<Integer> _missionIDs) {
    	missionIDs=_missionIDs;
    }
    @Override
    public void setViolationIDs(List<Integer> _violationIDs) {
    	violationIDs=_violationIDs;
    }


    @Override
    public String toJSON() {
        JSONObject object=new JSONObject();

        object.put("mailbox",mailBox);
        object.put("missionIDs",missionIDs);
        object.put("evaluationIDs",evaluationIDs);
        object.put("violationIDs",violationIDs);
        object.put("averageScore",averageScore);


        String objStr=JSON.toJSONString(object);
        return objStr;
    }

    @Override
    public void loadFromJSON(String JSONString) {
        JSONObject user_JSON = JSONObject.parseObject(JSONString);
        if(user_JSON.getString("mailbox") != null)
            setMailBox(user_JSON.getString("mailbox"));
        if(user_JSON.getString("username") != null)
            setUserName(user_JSON.getString("username"));
        if(user_JSON.getDouble("averageScore") != null)
            setAverageScore(user_JSON.getDouble("averageScore"));
        if(user_JSON.getJSONArray("missionIDs") != null)
            setMissionIDs(JSON.parseArray(user_JSON.getJSONArray("missionIDs").toJSONString(),Integer.class));
        if(user_JSON.getJSONArray("evaluationIDs") != null)
            setMissionIDs(JSON.parseArray(user_JSON.getJSONArray("evaluationIDs").toJSONString(),Integer.class));
        if(user_JSON.getJSONArray("violationIDs") != null)
            setMissionIDs(JSON.parseArray(user_JSON.getJSONArray("violationIDs").toJSONString(),Integer.class));

    }
}
