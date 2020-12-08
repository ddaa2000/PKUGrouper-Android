package com.e.pkugrouper.Models;

import com.alibaba.fastjson.JSONObject;

public class Evaluation implements IEvaluation{

    private double Score;
    private int EvaluatorID;
    @Override
    public int getEvaluatorID() {
        return EvaluatorID;
    }

    @Override
    public int getEvaluateeID() {
        return 0;
    }

    @Override
    public int getMissionID() {
        return 0;
    }

    @Override
    public double getScore() {
        return Score;
    }

    @Override
    public void setEvaluationID(int evaluationID) {

    }

    @Override
    public void setEvaluateeID(int evaluateeID) {

    }

    @Override
    public void setMissionID(int missionID) {

    }



    @Override
    public void setScore(double score) {
        Score = score;
    }

    @Override
    public void setEvaluatorID(int evaluatorID) {
        EvaluatorID = evaluatorID;
    }

    @Override
    public String toJSON() {
        return null;
    }

    @Override
    public void loadFromJSON(String JSONString) {
        JSONObject evaluation = JSONObject.parseObject(JSONString);
        setScore(evaluation.getDouble("evaluationScore"));
        setEvaluatorID(evaluation.getInteger("evaluatorID"));
    }
}
