package com.e.pkugrouper.Managers;

import com.e.pkugrouper.Models.ICommonUser;
import com.e.pkugrouper.Models.IEvaluation;
import com.e.pkugrouper.Models.IUser;
import com.e.pkugrouper.Models.Administrator;

import java.util.List;

public class UserManager extends HttpManager implements IUserManager{
    //在修改了user的属性后要重新设置missionManager和messageManager中的currentUser
    private IMissionManager missionManager;
    private MessageManager messageManager;
    private IUser user;

    @Override
    public ICommonUser findMemberByID(int missionID, int memberID) {
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
    public List<IEvaluation> getEvaluations() {
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
    public boolean evaluate(int missionID, int evaluateeID, int score) {
        return false;
    }
}
