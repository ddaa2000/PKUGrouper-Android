package com.e.pkugrouper.Managers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.e.pkugrouper.Models.CommonUser;
import com.e.pkugrouper.Models.Evaluation;
import com.e.pkugrouper.Models.ICommonUser;
import com.e.pkugrouper.Models.IEvaluation;
import com.e.pkugrouper.Models.IUser;
import com.e.pkugrouper.Models.Administrator;
import com.e.pkugrouper.Models.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.alibaba.fastjson.JSONArray;
public class UserManager extends HttpManager implements IUserManager{
    //在修改了user的属性后要重新设置missionManager和messageManager中的currentUser
    private IMissionManager missionManager;
    private IMessageManager messageManager;
    private IUser user;
    private final String user_not_found = "\"user Not Found\"";
    private final String getter_not_found = "\"getter Not Found\"";
    private final String mission_not_found = "\"mission Not Found\"";
    private final String gettee_not_found = "\"gettee Not Found\"";
    private final String bad_request = "\"Bad Request\"";
    private final String forbidden = "\"Forbidden\"";
    private final String ok = "\"OK\"";
    private final String wrong_password = "\"wrong old password\"";
    private final String invalid_password = "\"invalid new password\"";
    private final String evaluator_not_found = "\"evaluater Not Found\"";
    private final String evaluatee_not_found = "\"evaluatee Not Found\"";
    private final String evaluation_not_found = "\"evaluation Not Found\"";

    @Override
    public ICommonUser findMemberByID(int missionID, int memberID) {
        if(user == null) {
            //report or throw;
            return null;
        } //检测user对象是否存在

        //检查参数
        if(missionID <= 0 || memberID <= 0) {
            //report or throw
            return null;
        }

        //读取要查找的member的JSON序列
        List<String> parameters = Arrays.asList(String.valueOf(user.getUserID()),
                String.valueOf(missionID), String.valueOf(memberID));
        String url = "/user/member";
        String Member_JSON = httpGet(url, parameters, null);

        //如果查找者不存在
        if(Member_JSON.equals(getter_not_found)) {
            //report getter not Found
            return null;
        }

        //如果任务不存在
        if(Member_JSON.equals(mission_not_found)){
            //report mission Not Found
            return null;
        }

        //如果被查询者不存在
        if(Member_JSON.equals(gettee_not_found)){
            //report gettee Not Found
            return null;
        }

        //如果自己查看自己的信息
        if(Member_JSON.equals(bad_request)){
            //report bad request
            return null;
        }

        //如果被查询者不在该任务中
        if(Member_JSON.equals(forbidden)){
            //report forbidden
            return null;
        }

        //生成member对应的ICommonUser对象
        ICommonUser member = new CommonUser();
        member.loadFromJSON(Member_JSON);
        return member;
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

        //如果当前用户不存在
        if(User_JSON.equals(user_not_found)) {
            //report user not found
            return null;
        }

        //从返回的JSON字符串中得到当前使用的User对象
        ICommonUser Self = new CommonUser();
        Self.loadFromJSON(User_JSON);
        return Self;
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

        //如果登录失败
        if(User_Login_JSON.equals(forbidden)){
            //report login failed
            return null;
        }

        if(User_Login_JSON.equals(ok)) {
            //生成登陆后的user对象
            IUser User_Login = new User();
            User_Login.loadFromJSON(User_Login_JSON);
            user = User_Login;//存在疑惑

            //更新messageManager和missionMagager中的currentUser
            if (messageManager == null) {
                //messageManager不存在
                return null;
            }
            messageManager.setCurrentUser(user);

            if (missionManager == null) {
                //missionManager不存在
                return null;
            }
            missionManager.setCurrentUser(user);
            return User_Login;
        }

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

        //检查注册是否失败
        if(User_Register_JSON.equals(forbidden)) {
            //report register failed
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

        return User_Register;
    }

    @Override
    public List<IEvaluation> getEvaluations() {
        //问题是返回的是evaluation的id列表不能通过这个生成Evaluation列表
        //检查user
        if(user == null){
            //report or throw
            return null;
        }

        //尝试获取信息
        String url = "/user/evaluation";
        List<String> parameters = Arrays.asList(String.valueOf(user.getUserID()));
        String evaluations_id_list = httpGet(url, parameters, null);

        //如果user not found
        if(evaluations_id_list.equals(user_not_found)){
            //report user not found
            return null;
        }

        //生成Evaluations 先采用简单的split 似乎不适用
        List<IEvaluation> Evaluations = new ArrayList<>();
        List<String> evaluation_ids = JSONObject.parseArray(evaluations_id_list, String.class);
        for (String evaluation_id : evaluation_ids){
            IEvaluation evaluation = findEvaluationByID(Integer.valueOf(evaluation_id));
            if(evaluation != null)
                Evaluations.add(evaluation);
        }
        return Evaluations;
    }

    @Override
    public IEvaluation findEvaluationByID(int evaluationID) {
        //检查参数
        if(evaluationID <= 0){
            //report
            return null;
        }

        //获取evaluation
        String url = "/user/evaluation";
        List<String> parameters = Arrays.asList(String.valueOf(evaluationID));
        String evaluation_JSON = httpGet(url,parameters,null);

        //获取失败
        if(evaluation_JSON.equals(evaluation_not_found)){
            //report
            return null;
        }

        IEvaluation evaluation = new Evaluation();
        evaluation.loadFromJSON(evaluation_JSON);
        return evaluation;
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

        //修改密码请求失败
        if(EditInfo_JSON.equals(bad_request)) {
            return false;
        }

        //如果成功修改了信息，用修改后返回的JSON更新当前用户的信息
        //之后是否要更新messageManager和missionManager中的user?
        user.loadFromJSON(EditInfo_JSON);
        return true;
    }

    @Override
    public boolean editTags() {
        //新的tags从哪里得到
        //判断user是否存在
        if(user == null){
            //report or throw exception
            return false;
        }

        //修改用户的Tags
        String url = "/user/tags";
        List<String> parameters = Arrays.asList(String.valueOf(user.getUserID()));
        List<String> tags = user.getTags();//怎么得到新的标签集合
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(tags));
        String tag_list = jsonArray.toJSONString();//把标签集合转换成JSON对应的string
        String tags_response = httpPut(url, parameters, tag_list);

        //修改tags成功
        if(tags_response.equals(ok)){
            //report success
            //修改user里的tags
            //之后是否要更新messageManager和missionManager中的user?
            return true;
        }

        //修改tags失败
        if(tags_response.equals(bad_request)){
            //report bad request
            return false;
        }

        if(tags_response.equals(user_not_found)){
            //user not found
            return false;
        }

        return false;
    }

    @Override
    public boolean changePassword() {
        //怎么得到旧的密码和新的密码
        //判断user是否存在
        if(user == null){
            //report or throw exception
            return false;
        }

        //修改用户的password
        String url = "/user/code";
        List<String> parameters = Arrays.asList(String.valueOf(user.getUserID()));
        String Password = null;
        String password_response = httpPut(url, parameters, Password);

        //修改密码成功
        if(password_response.equals(ok)){
            //report success
            return true;
        }

        //修改password失败
        if(password_response.equals(bad_request)){
            //report bad request
            return false;
        }

        if(password_response.equals(user_not_found)){
            //user not found
            return false;
        }

        //旧密码错误 和 新密码无效
        if(password_response.equals(wrong_password)){
            //report wrong old password
            return false;
        }
        else if(password_response.equals(invalid_password)){
            //report invalid new password
            return false;
        }

        return false;
    }

    @Override
    public boolean evaluate(int missionID, int evaluateeID, int score) {
        //判断user是否存在
        if(user == null){
            //report or throw exception
            return false;
        }

        //参数检查
        if(missionID <= 0 || evaluateeID <= 0) {
            //report or throw exception
            return false;
        }

        if(score <= 0){
            //score must > 0
            return false;
        }

        String url = "/user/evalute";
        List<String> parameters = Arrays.asList(String.valueOf(user.getUserID()),
                String.valueOf(missionID), String.valueOf(evaluateeID));

        //设置评价信息
        IEvaluation evaluation = new Evaluation();
        evaluation.setEvaluateeID(evaluateeID);
        evaluation.setMissionID(missionID);
        evaluation.setScore(score);
        String evaluation_JSON = evaluation.toJSON(); //request body的正确形式 {"score" : num}
        //实际上要把score转换成json形式

        String evaluate_response = httpPost(url, parameters, evaluation_JSON);

        //评价成功
        if(evaluate_response.equals(ok)){
            //report success
            return true;
        }

        //评价失败
        //如果查找者不存在
        if(evaluate_response.equals(evaluator_not_found)) {
            //report evaluator not Found
            return false;
        }

        //如果任务不存在
        if(evaluate_response.equals(mission_not_found)){
            //report mission Not Found
            return false;
        }

        //如果被查询者不存在
        if(evaluate_response.equals(evaluatee_not_found)){
            //report evaluatee Not Found
            return false;
        }

        //如果自己查看自己的信息
        if(evaluate_response.equals(bad_request)){
            //report bad request
            return false;
        }

        //如果被查询者不在该任务中
        if(evaluate_response.equals(forbidden)){
            //report forbidden
            return false;
        }

        return false;
    }
}
