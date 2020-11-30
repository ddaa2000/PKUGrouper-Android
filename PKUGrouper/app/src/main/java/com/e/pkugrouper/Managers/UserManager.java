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
    public boolean setMissionManager(IMissionManager _missionManager) {
        return false;
    }

    @Override
    public boolean setMessageManager(IMessageManager _messageManager) {
        return false;
    }

    @Override
    public boolean editInfo() {
        return false;
    }

    @Override
    public boolean editTags() {
        return false;
    }

    @Override
    public boolean changePassword() {
        return false;
    }

    @Override
    public boolean evaluate(int missionID, int evauateeID, int score) {
        return false;
    }


}
