package com.e.pkugrouper.Models;

import java.util.List;

public class User implements IUser{
    private int UserID = 0;
    private String password;
    private String Username;
    private String tele;
    @Override
    public String getUserName() {
        return Username;
    }

    @Override
    public void setUserName(String username) {
        Username = username;
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
        return tele;
    }

    @Override
    public void setTele(String Tele) {
        tele = Tele;
    }

    @Override
    public int getUserID() {
        return UserID;
    }

    @Override
    public void setUserID(int _userID) {
        UserID = _userID;
    }

    @Override
    public String getPassword() {
        return password;
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
        password = _password;
    }

    @Override
    public String toJSON() {
        return null;
    }

    @Override
    public void loadFromJSON(String JSONString) {

    }
}
