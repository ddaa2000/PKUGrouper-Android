package com.e.pkugrouper.Models;

import java.util.List;

public interface IUser extends ISerializable{

    String getUserName();
    void setUserName(String _userName);
    String getMailBox();
    void setMailBox(String _mailBox);
    int getUserID();
    void setUserID(int _userID);
    String getTele();
    void setTele(String _contactInformation);
    double getAverageScore();
    void setAverageScore(double _averageScore);
    
    String getPassword();
    void setPassword(String _password);

    List<Integer> getMessageIDs();
    List<Integer> getEvaluationIDs();
    List<Integer> getMissionIDs();
    List<Integer> getViolationIDs();
    
    void setMessageIDs(List<Integer> _messageIDs);
    void setEvaluationIDs(List<Integer> _evaluationIDs);
    void setMissionIDs(List<Integer> _missionIDs);
    void setViolationIDs(List<Integer> _violationIDs);

}
