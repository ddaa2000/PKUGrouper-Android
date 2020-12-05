package com.e.pkugrouper.Models;

import java.util.List;

public interface IUser extends ISerializable{

    String getUserName();
    String setUserName();
    String getMailBox();
    void setMainBox(String _mainBox);
    int getUserID();
    void setUserID(int _userID);
    String getPassword();
    IIdentification getIdentification();
    void setIdentification(IIdentification _identification);
    List<Integer> getMessageIDs();
    List<Integer> getEvaluationIDs();
    List<String> getTags();


}
