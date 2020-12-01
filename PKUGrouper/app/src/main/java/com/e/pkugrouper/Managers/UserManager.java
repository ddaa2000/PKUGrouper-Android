package com.e.pkugrouper.Managers;

import com.e.pkugrouper.Models.CommonUser;
import com.e.pkugrouper.Models.Evaluation;
import com.e.pkugrouper.Models.ICommonUser;
import com.e.pkugrouper.Models.IEvaluation;
import com.e.pkugrouper.Models.IUser;
import com.e.pkugrouper.Models.Administrator;
import com.e.pkugrouper.Models.User;

import java.util.Arrays;
import java.util.List;


public class UserManager extends HttpManager implements IUserManager{
    //在修改了user的属性后要重新设置missionManager和messageManager中的currentUser
    private IMissionManager missionManager;
    private IMessageManager messageManager;
    private IUser user;
    @Override
    public ICommonUser findMemberByID(int missionID, int memberID) {
        if(user == null) {
            //throw;
            return null;
        } //检测user对象是否存在

        if(missionID < 0 || memberID < 0) {
            //report or throw
            return null;
        }
        //读取要查找的member的JSON序列
        List<String> parameters = Arrays.asList(String.valueOf(user.getUserID()),
                String.valueOf(missionID), String.valueOf(memberID));
        String url = "/user/member";
        String Member_JSON = httpGet(url, parameters, null);

        //如果该member查找不到
        if (Member_JSON == null) {
            //not found
            return null;
        }
        //生成member对应的ICommonUser对象
        else {
            ICommonUser member = new CommonUser();
            member.loadFromJSON(Member_JSON);
            //return member;
            return null;
        }
    }

    @Override
    public ICommonUser getSelf() {
        if(user == null) {
            //report or throw;
            return null;
        } //检测user对象是否存在

        //根据给定的userID生成对象
        List<String> parameters = Arrays.asList(String.valueOf(user.getUserID()));
        String url = "/user/self";
        String User_JSON = httpGet(url, parameters, null);

        if(User_JSON == null) {
            //report or throw
            return null;
        }

        //从返回的JSON字符串中得到当前使用的User对象
        ICommonUser Self = new CommonUser();
        Self.loadFromJSON(User_JSON);
        //return Self;
        return null;
    }

    @Override
    public IUser userLogIn(IUser currentUser) {
        //检查currentUser是否存在
        if(currentUser == null) {
            //report or throw
            return null;
        }

        //生成登录产生的JSON字符串
        String url = "/user/login";
        String User_JSON = currentUser.toJSON();
        String User_Login_JSON = httpPost(url, null, User_JSON);

        //如果返回JSON字符串为空 认为登录失败
        if(User_Login_JSON == null){
            //report login failed
            return null;
        }

        //生成登陆后的user对象
        IUser User_Login = new User();
        User_Login.loadFromJSON(User_Login_JSON);
        user = User_Login;

        //更新messageManager和missionMagager中的currentUser
        if(messageManager == null){
            return null;
        }
        messageManager.setCurrentUser(user);

        if(missionManager == null) {
            return null;
        }
        missionManager.setCurrentUser(user);
        //return User_Login;
        return null;
    }

    @Override
    public IUser userRegister(IUser currentUser) {
        //检查currentUser是否为null
        if(currentUser == null) {
            //report or throw
            return null;
        }

        //生成注册产生的JSON字符串
        String url = "/user/register";
        String User_JSON = currentUser.toJSON();
        String User_Register_JSON = httpPost(url, null, User_JSON);

        //检查User_Register_JSON是否为空
        if(User_Register_JSON == null) {
            //report or throw
            return null;
        }

        //生成注册后的User对象
        IUser User_Register = new User();
        User_Register.loadFromJSON(User_Register_JSON);
        user = User_Register;

        //更新messageManager和missionManager的currentUser
        if(messageManager == null){
            return null;
        }
        messageManager.setCurrentUser(user);

        if(missionManager == null) {
            return null;
        }
        missionManager.setCurrentUser(user);
        //return User_Register;
        return null;
    }

    @Override
    public List<IEvaluation> getEvaluations() {
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
        List<String> parameters = Arrays.asList(String.valueOf(user.getUserID()));
        String User_JSON = user.toJSON();
        String EditInfo_JSON = httpPut(url, parameters, User_JSON);

        //如果成功修改了信息，用修改后返回的JSON更新当前用户的信息
        if(EditInfo_JSON != null) {
            user.loadFromJSON(EditInfo_JSON);
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
        List<String> parameters = Arrays.asList(String.valueOf(user.getUserID()));
        List<String> tags = user.getTags();
        String taglist = "";
        for (String tag: tags){
            taglist += tag + " ";
        }
        String tags_JSON = httpPut(url, parameters, taglist);

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
        String url = "/user/code";
        List<String> parameters = Arrays.asList(String.valueOf(user.getUserID()));
        String Password = null;
        String password_JSON = httpPut(url, parameters, Password);

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

        if(missionID < 0 || evaluateeID < 0 || score < 0) {
            //report or throw exception
            return false;
        }

        String url = "/user/evalute";
        List<String> parameters = Arrays.asList(String.valueOf(user.getUserID()),
                String.valueOf(missionID), String.valueOf(evaluateeID));

        IEvaluation evaluation = new Evaluation();
        evaluation.setEvaluateeID(evaluateeID);
        evaluation.setMissionID(missionID);
        evaluation.setScore(score);
        String evaluation_JSON = evaluation.toJSON();

        String evaluate_JSON = httpPost(url, parameters, evaluation_JSON);
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
