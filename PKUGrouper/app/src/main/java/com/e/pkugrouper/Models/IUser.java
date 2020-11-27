package com.e.pkugrouper.Models;

import java.util.List;

public interface IUser {

    String getUserName();
    String setUserName();
    String getMailBox();
    void setMainBox(String _mainBox);
    int getUserID();
    void setUserID(int _userID);
    IIdentification getIdentification();
    void setIdentification(IIdentification _identification);
    List<Integer> getMessageIDs();


}
