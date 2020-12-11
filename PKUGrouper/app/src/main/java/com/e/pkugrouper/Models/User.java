package com.e.pkugrouper.Models;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public class User implements IUser{
    private int UserID = 0;
    private String password;
    private String Username;
    private String tele;
    private double averageScore;
    @Override
    public String getUserName() {
        return Username;
    }

    @Override
    public void setUserName(String username) {
        Username = username;
    }

    @Override
    public String getMailBox() {
        return null;
    }

    @Override
    public void setMailBox(String _mailBox) {

    }

    @Override
    public String getTele() {
        return tele;
    }

    @Override
    public void setTele(String Tele) {
        tele = Tele;
    }

    @Override
    public int getUserID() {
        return UserID;
    }

    @Override
    public void setUserID(int _userID) {
        UserID = _userID;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public IIdentification getIdentification() {
        return null;
    }

    @Override
    public void setIdentification(IIdentification _identification) {

    }

    @Override
    public List<Integer> getMessageIDs() {
        return null;
    }

    @Override
    public List<Integer> getEvaluationIDs() {
        return null;
    }

    @Override
    public List<String> getTags() {
        return null;
    }

    @Override
    public void setPassword(String _password) {
        password = _password;
    }

    @Override
    public String toJSON() {
        return null;
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
    }

    @Override
    public double getAverageScore(){
        return averageScore;
    }

    @Override
    public void setAverageScore(double _averageScore){
        averageScore=_averageScore;
    }
}
