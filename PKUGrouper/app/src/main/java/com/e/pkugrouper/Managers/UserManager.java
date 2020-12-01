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
        if(user == null) {
            //throw;
            return null;
        } //检测user对象是否存在

        //读取要查找的member的JSON序列
        List<String> parameters = [user.getUserID(), String.ValueOf(missionID),
                String.ValueOf(memberID)];
        String url = "/user/member";
        String Member_JSON = self.httpGet(url, parameters, null);

        //如果该member查找不到
        if (Member_JSON == null) {
            //not found
            return null;
        }
        //生成member对应的ICommonUser对象
        else {
            ICommonUser member = new ICommonUser;
            member.loadFromJSON(Member_JSON);
            //return member;
            return null;
        }
    }

    @Override
    public ICommonUser getSelf() {
        //根据给定的userID生成对象
        List<String> parameters = [String.ValueOf(userID)];
        String url = "/user/self";
        String User_JSON = self.httpGet(url, parameters, null);

        //从返回的JSON字符串中得到当前使用的User对象
        ICommonUser Self = new ICommonUser;
        Self.loadFromJSON(User_JSON);
        //return Self;
        return null;
    }

    @Override
    public IUser userLogIn(IUser currentUser) {
        //生成登录产生的JSON字符串
        String url = "/user/login";
        String User_JSON = currentUser.toJSON();
        String User_Login_JSON = self.httpPost(url, null, User_JSON);

        //如果返回JSON字符串为空 认为登录失败
        if(User_Login_JSON == null){
            //report login failed
            return null;
        }

        //生成登陆后的user对象
        IUser User_Login = new IUser;
        User_Login.loadFromJSON(User_Login_JSON);
        user = User_Login;

        //更新messageManager和missionMagager中的currentUser
        if(messageManager == null){
            return null;
        }
        messageManager.setCurrentUser(user);

        if(missionManager == null) {
            return null
        }
        missionManager.setCurrentUser(user);
        //return User_Login;
        return null;
    }

    @Override
    public IUser userRegister(IUser currentUser) {
        //生成注册产生的JSON字符串
        String url = "/user/register";
        String User_JSON = currentUser.toJSON();
        String User_Register_JSON = self.httpPost(url, null, User_JSON);

        //生成注册后的User对象
        IUser User_Register = new IUser;
        User_Register.loadFromJSON(User_Register_JSON);
        user = User_Register;

        //更新messageManager和missionManager的currentUser
        if(messageManager == null){
            return null;
        }
        messageManager.setCurrentUser(user);

        if(missionManager == null) {
            return null
        }
        missionManager.setCurrentUser(user);
        //return User_Register;
        return null;
    }

    @Override
    public boolean setMissionManager(IMissionManager _missionManager) {
        if (_missionManager == null) {
            //throw exception
            return false;
        }
        else{
            missionManager = _missionManager;
            return true;
        }
    }

    @Override
    public boolean setMessageManager(IMessageManager _messageManager) {
        if (_messageManager == null) {
            //throw exception;
            return false;
        }
        else{
            messageManager = _messageManager;
            return true;
        }
    }

    @Override
    public boolean editInfo() {
        //判断user是否存在
        if(user == null){
            //report or throw exception
            return false;
        }

        //用Put修改用户的信息
        String url = "/user/info";
        List<String> parameters = [user.getUserID()];
        String User_JSON = user.toJSON();
        String EditInfo_JSON = self.httpPut(url, parameters, User_JSON);

        //如果成功修改了信息，用修改后返回的JSON更新当前用户的信息
        if(EditInfo_JSON != null) {
            user.loadFronJSON(EditInfo_JSON);
            return true;
        }
        else {
            //report or throw exception
            return false;
        }
    }

    @Override
    public boolean editTags() {
        //判断user是否存在
        if(user == null){
            //report or throw exception
            return false;
        }

        //修改用户的Tags
        String url = "/user/tags";
        List<String> parameters = [user.getUserID()];
        List<String> tags = user.getTags();
        String taglist = "";
        for (String tag: tags){
            taglist += tag + " ";
        }
        String tags_JSON = self.httpPut(url, parameters, taglist);

        //如果成功修改了tags，用修改后返回的JSON更新当前用户的tags
        if(tags_JSON != null) {
            user.loadFromJSON(tags_JSON);
            return true;
        }
        else{
            //report or throw exception
            return false;
        }
    }

    @Override
    public boolean changePassword() {
        //判断user是否存在
        if(user == null){
            //report or throw exception
            return false;
        }

        //修改用户的password
        String url = '/user/code';
        List<String> parameters = [user.getUserID()];
        String Password = null;
        String password_JSON = self.httpPut(url, parameters, Password);

        //如果成功修改了password，用修改后返回的JSON更新当前用户的password
        //目前得到用户的password和把修改后的password加载到用户对象 都不明确
        if(password_JSON != null) {
            user.loadFromJSON(password_JSON);
            return true;
        }
        else{
            //report or throw exception
            return false;
        }
    }

    @Override
    public boolean evaluate(int missionID, int evaluateeID, int score) {
        //判断user是否存在
        if(user == null){
            //report or throw exception
            return false;
        }

        String url = "/user/evalute";
        List<String> parameters = [user.getUserID(), String.ValueOf(missionID),
                String.ValueOf(evaluateeID)];

        Evaluation evaluation = new Evaluation;
        evaluation.setEvaluateeID(evaluateeID);
        evaluation.setMissionID(missionID);
        evaluation.setScore(score);
        String evaluation_JSON = evaluation.toJSON();

        String evaluate_JSON = self.httpPost(url, parameters, evaluation_JSON);
        if(evaluate_JSON != null){
            evaluation.loadFromJSON(evaluate_JSON);
            return true;
        }
        else {
            // report or throw exception
            return false;
        }
    }
}
