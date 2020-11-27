package com.e.pkugrouper.Managers;

import com.e.pkugrouper.Models.ICommonUser;
import com.e.pkugrouper.Models.IUser;

public class UserManager extends HttpManager implements IUserManager{

    @Override
    public ICommonUser findMemberByID(int missionID, int userID) {
        return null;
    }

    @Override
    public ICommonUser getSelf() {
        return null;
    }

    @Override
    public IUser userLogIn(IUser currentUser) {
        return null;
    }

    @Override
    public IUser userRegister(IUser currentUser) {
        return null;
    }

    @Override
    public void setMissionManager(IMissionManager _missionManager) {

    }

    @Override
    public void setMessageManager(IMessageManager _messageManager) {

    }

    @Override
    public void editInfo() {

    }

    @Override
    public void changePassword() {

    }

    @Override
    public void evaluate(int missionID, int evauateeID, int score) {

    }

}
