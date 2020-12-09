package com.e.pkugrouper.Models;

import java.util.List;

public class TestUser implements IUser{
    @Override
    public String getUserName() {
        return null;
    }

    @Override
    public void setUserName(String username) {

    }


    @Override
    public String getMailBox() {
        return null;
    }

    @Override
    public void setMailBox(String _mailBox) {

    }

    @Override
    public String getTele() {
        return null;
    }

    @Override
    public void setTele(String Tele) {

    }


    @Override
    public int getUserID() {
        return 0;
    }

    @Override
    public void setUserID(int _userID) {

    }

    @Override
    public String getPassword() {
        return null;
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
    public void setPassword(String _password) {

    }

    @Override
    public String toJSON() {
        return null;
    }

    @Override
    public void loadFromJSON(String JSONString) {

    }
}
