package com.e.pkugrouper.Models;

import java.util.List;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class User implements IUser{
    private String mailBox;
    private String userName;
    private int userID;
    private String password;
    private String contactInformation;
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
    public String getContactInformation() {
        return contactInformation;
    }

    @Override
    public void setContactInformation(String _contactInformation) {
        contactInformation=_contactInformation;
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
        JSONObject object=JSON.parseObject(JSONString);
        
        if(object.containsKey("mailbox")) {
        	mailBox=object.getString("mailbox");
        }
        if(object.containsKey("missionIDs")) {
        	missionIDs=JSON.parseArray(object.getJSONArray("missionIDs").toJSONString(),Integer.class);
        }
        if(object.containsKey("evaluationIDs")) {
        	evaluationIDs=JSON.parseArray(object.getJSONArray("evaluationIDs").toJSONString(),Integer.class);
        }
        if(object.containsKey("violationIDs")) {
        	violationIDs=JSON.parseArray(object.getJSONArray("violationIDs").toJSONString(),Integer.class);
        }
        if(object.containsKey("averageScore")) {
        	averageScore=object.getDoubleValue("averageScore");
        }
    }
}
