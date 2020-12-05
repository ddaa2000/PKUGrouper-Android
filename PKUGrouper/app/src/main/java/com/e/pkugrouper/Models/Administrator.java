package com.e.pkugrouper.Models;

import java.util.List;

public class Administrator implements IAdministrator{
    @Override
    public String getUserName() {
        return null;
    }

    @Override
    public String setUserName() {
        return null;
    }

    @Override
    public String getMailBox() {
        return null;
    }

    @Override
    public void setMainBox(String _mainBox) {

    }

    @Override
    public int getUserID() {
        return 0;
    }

    @Override
    public void setUserID(int _userID) {

    }

    @Override
    public IIdentification getIdentification() {
        return null;
    }

    @Override
    public void setIdentification(IIdentification _identification) {

    }

    @Override
    public List<Integer> getMessageIDs() {
        return null;
    }

    @Override
    public List<Integer> getEvaluationIDs() {
        return null;
    }

    @Override
    public List<String> getTags() {
        return null;
    }

    @Override
    public String toJSON() {
        return null;
    }

    @Override
    public void loadFromJSON(String JSONString) {

    }
}
