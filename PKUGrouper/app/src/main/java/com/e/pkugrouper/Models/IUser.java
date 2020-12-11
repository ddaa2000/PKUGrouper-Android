package com.e.pkugrouper.Models;

import java.util.List;

public interface IUser extends ISerializable{

    String getUserName();
    void setUserName(String username);
    String getMailBox();
    void setMailBox(String _mailBox);
    String getTele();
    void setTele(String Tele);
    int getUserID();
    void setUserID(int _userID);
    String getPassword();
    IIdentification getIdentification();
    void setIdentification(IIdentification _identification);
    List<Integer> getMessageIDs();
    List<Integer> getEvaluationIDs();
    List<String> getTags();
    void setPassword(String _password);
    double getAverageScore();
    void setAverageScore(double _averageScore);
}
