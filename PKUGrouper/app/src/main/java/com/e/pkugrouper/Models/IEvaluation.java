package com.e.pkugrouper.Models;

public interface IEvaluation extends ISerializable{
    int getEvaluatorID();
    int getEvaluateeID();
    int getMissionID();
    double getScore();
    void setEvaluationID(int evaluationID);
    void setEvaluateeID(int evaluateeID);
    void setMissionID(int missionID);
    void setScore(double score);
}
