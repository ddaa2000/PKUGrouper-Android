package com.e.pkugrouper.Models;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public class CommonUser  extends User implements ICommonUser{
    double AverageScore;
    @Override
    public List<Integer> getEvaluationIDs() {
        return null;
    }

    @Override
    public List<Integer> getMissionIDs() {
        return null;
    }

    @Override
    public List<Integer> getViolationIDs() {
        return null;
    }

    @Override
    public double getAverageScore() {
        return AverageScore;
    }

    @Override
    public void setAverageScore(double _averageScore) {
        AverageScore = _averageScore;
    }

    @Override
    public String toJSON(){
        return null;
    }

    @Override
    public void loadFromJSON(String JSONString){
        JSONObject user_JSON = JSONObject.parseObject(JSONString);
        setMailBox(user_JSON.getString("mailbox"));
        setUserName(user_JSON.getString("username"));
        if(user_JSON.getDouble("averageScore") != null)
            setAverageScore(user_JSON.getDouble("averageScore"));
    }
}
