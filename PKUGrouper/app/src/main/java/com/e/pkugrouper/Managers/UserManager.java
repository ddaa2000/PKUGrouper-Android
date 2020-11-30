package com.e.pkugrouper.Managers;

import com.e.pkugrouper.Models.ICommonUser;
import com.e.pkugrouper.Models.IUser;
import com.e.pkugrouper.Models.Administrator;

public class UserManager extends HttpManager implements IUserManager{
    //在修改了user的属性后要重新设置missionManager和messageManager中的currentUser
    private IMissionManager missionManager;
    private MessageManager messageManager;
    private IUser user;
    @Override
    public ICommonUser findMemberByID(int missionID, int memberID) {
        List<String> parameters = [String.ValueOf(missionID), String.ValueOf(memberID)];
        String url = "/user/member";
        String UID = self.httpGet(url, parameters, null);
        if (UID == null) {
            return null;
        }
        else {
            return null;
        }
    }

    @Override
    public ICommonUser getSelf(int userID) {
        List<String> parameters = [String.ValueOf(userID)];
        String url = "/user/self";
        String UID = user.getUserID();
        String User_Info = self.httpGet(url, parameters, null);
        return null;
    }

    @Override
    public IUser userLogIn(IUser currentUser) {
        String url = "/user/login";
        self.httpPost(url, null, null);
        return null;
    }

    @Override
    public IUser userRegister(IUser currentUser) {
        String url = "/user/register";
        self.httpPost(url, null, null);
        return null;
    }

    @Override
    public boolean setMissionManager(IMissionManager _missionManager) {
        if (_missionManager == null)
            return false;
        else{
            missionManager = _missionManager;
            return true;
        }
    }

    @Override
    public boolean setMessageManager(IMessageManager _messageManager) {
        if (_messageManager == null)
            return false;
        else{
            messageManager = _messageManager;
            return true;
        }
    }

    @Override
    public boolean editInfo() {
        String url = "/user/info";
        List<String> parameters = [user.getUserID()];
        String body = null;
        String EditInfo = self.httpPut(url, parameters, body);
    }

    @Override
    public boolean editTags() {
        String url = "/user/tags";
        List<String> parameters = [user.getUserID()];
        String taglist = user.getTags();
        String tags = self.httpPut(url, parameters, taglist);
    }

    @Override
    public boolean changePassword() {
        String url = '/user/code';
        List<String> parameters = [user.getUserID()];
        String Password = null;
        String new_password = self.httpPut(url, parameters, Password);
    }

    @Override
    public boolean evaluate(int missionID, int evaluateeID, int score) {
        List<String> parameters = [String.ValueOf(missionID), String.ValueOf(evaluateeID),
            String.ValueOf(score)];
        String evaluation = null;
        String url = "/user/evalute";
        self.httpPost(url, parameters, evaluation);
        return false;
    }
}
