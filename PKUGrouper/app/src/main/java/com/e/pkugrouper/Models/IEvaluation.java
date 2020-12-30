package com.e.pkugrouper.Models;

public interface IEvaluation extends ISerializable{
	int getEvaluationID();
    int getEvaluaterID();
    int getEvaluateeID();
    int getMissionID();
    double getScore();
    void setEvaluationID(int _evaluationID);
    void setEvaluateeID(int _evaluateeID);
    void setMissionID(int _missionID);
    void setEvaluaterID(int _evaluaterID);
    void setScore(double _score);
    String getTimeStamp();
    void setTimeStamp(String _timeStamp);
}
