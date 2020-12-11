package com.e.pkugrouper.Models;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class Evaluation implements IEvaluation{
    private int evaluationID;
    private int evaluateeID;
    private int missionID;
    private int evaluaterID;
    private double score;
    private String timeStamp;
    
    @Override
    public int getEvaluationID() {
    	return evaluationID;
    }
    
    @Override
    public int getEvaluaterID() {
        return evaluaterID;
    }

    @Override
    public int getEvaluateeID() {
        return evaluateeID;
    }

    @Override
    public int getMissionID() {
        return missionID;
    }

    @Override
    public double getScore() {
        return score;
    }

    @Override
    public void setEvaluationID(int _evaluationID) {
        evaluationID=_evaluationID;
    }

    @Override
    public void setEvaluaterID(int _evaluaterID) {
    	evaluaterID=_evaluaterID;
    }
    
    @Override
    public void setEvaluateeID(int _evaluateeID) {
        evaluateeID=_evaluateeID;
    }

    @Override
    public void setMissionID(int _missionID) {
        missionID=_missionID;
    }

    @Override
    public void setScore(double _score) {
        score=_score;
    }

    @Override
    public String getTimeStamp(){return timeStamp;}

    @Override
    public void setTimeStamp(String _timeStamp){ timeStamp=_timeStamp;}


    @Override
    public String toJSON() {
        JSONObject object=new JSONObject();

        object.put("timeStamp",timeStamp);
        object.put("evaluaterID",evaluaterID);
        object.put("evaluateeID",evaluateeID);
        object.put("missionID",missionID);
        object.put("evaluationScore",score);

        String objStr=JSON.toJSONString(object);
        return objStr;
    }

    @Override
    public void loadFromJSON(String JSONString) {
        JSONObject object=JSON.parseObject(JSONString);

        timeStamp=object.getString("timeStamp");
        evaluaterID=object.getIntValue("evaluaterID");
        evaluateeID=object.getIntValue("evaluateeID");
        missionID=object.getIntValue("missionID");
        score=object.getDoubleValue("evaluationScore");
    }
}
