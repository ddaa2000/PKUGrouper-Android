package com.e.pkugrouper.Models;

public class Evaluation implements IEvaluation{


    @Override
    public int getEvaluatorID() {
        return 0;
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
        return 0;
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

    }

    @Override
    public String toJSON() {
        return null;
    }

    @Override
    public void loadFromJSON(String JSONString) {

    }
}
