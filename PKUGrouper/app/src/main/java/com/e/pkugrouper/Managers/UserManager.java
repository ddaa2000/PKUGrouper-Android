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
import java.security.KeyFactory;
import java.security.PublicKey;


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
    private final String bad_username = "\"bad username\"";
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
        JSONObject request_body = new JSONObject();
        request_body.put("senderID",user.getUserID());
        request_body.put("passwordAfterRSA", user.getPassword());
        String Member_JSON = httpGet(url, parameters, request_body.toJSONString());

        //查找信息失败
        if(Member_JSON.equals(getter_not_found)) {
            //report getter not Found
            return null;
        }
        else if(Member_JSON.equals(mission_not_found)){
            //report mission Not Found
            return null;
        }
        else if(Member_JSON.equals(gettee_not_found)){
            //report gettee Not Found
            return null;
        }
        else if(Member_JSON.equals(bad_request)){
            //report bad request
            return null;
        }
        else if(Member_JSON.equals(forbidden)){
            //report forbidden
            return null;
        }

        //生成member对应的ICommonUser对象
        ICommonUser member = new CommonUser();
        member.setUserID(memberID);
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
        JSONObject request_body = new JSONObject();
        request_body.put("senderID",user.getUserID());
        request_body.put("passwordAfterRSA", user.getPassword());
        String User_JSON = httpGet(url, parameters, request_body.toJSONString());


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

    /*
        现在登录和注册的流程还需要进一步细化，究竟什么时候将UserManager的user设置为正在登录的user
        什么时候发送验证码，什么时候得到反馈的验证码，整套流程要详细规划
     */
    @Override
    public IUser userLogIn(IUser currentUser) {
        //检查currentUser是否存在
        if(currentUser == null) {
            //report or throw
            return null;
        }

        //生成登录产生的JSON字符串
        String url = "/user/login";
        JSONObject User_JSON = new JSONObject();
        User_JSON.put("mailbox", currentUser.getMailBox());
        User_JSON.put("passwordAfterRSA", currentUser.getPassword());
        String User_Login_JSON = httpPost(url, null, User_JSON.toJSONString());

        //如果登录失败
        if(User_Login_JSON.equals(forbidden)){
            //report login failed
            return null;
        }
        else{
            //生成登陆后的user对象
            int User_ID = JSONObject.parseObject(User_Login_JSON).getInteger("UID");
            currentUser.setUserID(User_ID);
            user = currentUser;

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
            return currentUser;
        }
    }

    @Override
    public IUser userRegister(IUser currentUser) {
        //注册的细节问题需要考量一下 首先是注册和发送验证码的嵌套 其次是设置user的位置
        //检查currentUser是否为null
        if(currentUser == null) {
            //report or throw
            return null;
        }

        //生成注册产生的JSON字符串
        String url = "/user/register";
        JSONObject User_JSON = new JSONObject();
        User_JSON.put("mailbox", currentUser.getMailBox());
        User_JSON.put("username", currentUser.getUserName());
        User_JSON.put("passwordAfterRSA", currentUser.getPassword());
        User_JSON.put("captchaCode", "");
        String User_Register_JSON = httpPost(url, null, User_JSON.toJSONString());

        //检查注册是否失败
        if(User_Register_JSON.equals(bad_request)) {
            //report register failed
            return null;
        }

        if(User_Register_JSON.equals(bad_username)){
            //report bad username
            return null;
        }
        //生成注册后的User对象
        IUser User_Register = new User();
        User_Register.setUserID(JSONObject.parseObject(User_Register_JSON).getInteger("UID"));
        User_Register.loadFromJSON(User_Register_JSON);
        user = User_Register;

        return User_Register;
    }

    @Override
    public List<IEvaluation> getEvaluations() {
        //检查user
        if(user == null){
            //report or throw
            return null;
        }

        //尝试获取信息
        String url = "/user/evaluation";
        List<String> parameters = Arrays.asList(String.valueOf(user.getUserID()));
        JSONObject request_body = new JSONObject();
        request_body.put("senderID",user.getUserID());
        request_body.put("passwordAfterRSA", user.getPassword());
        String evaluations_id_list = httpGet(url, parameters, request_body.toJSONString());

        //如果user not found
        if(evaluations_id_list.equals(user_not_found)){
            //report user not found
            return null;
        }

        //生成Evaluations
        List<Integer> evaluation_ids = JSONObject.parseArray(evaluations_id_list, Integer.class);
        if(evaluation_ids.size() == 0)
            return null;
        List<IEvaluation> Evaluations = new ArrayList<>();
        for (int evaluation_id : evaluation_ids){
            IEvaluation evaluation = findEvaluationByID(evaluation_id);
            if(evaluation != null)
                Evaluations.add(evaluation);
        }
        return Evaluations;
    }

    @Override
    public IEvaluation findEvaluationByID(int evaluationID) {
        //检查参数
        if(user == null){
            //report user null
            return null;
        }
        if(evaluationID <= 0){
            //report
            return null;
        }

        //获取evaluation
        String url = "/user/evaluation";
        List<String> parameters = Arrays.asList(String.valueOf(evaluationID));
        JSONObject request_body = new JSONObject();
        request_body.put("senderID",user.getUserID());
        request_body.put("passwordAfterRSA", user.getPassword());
        String evaluation_JSON = httpGet(url,parameters,request_body.toJSONString());

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
        JSONObject request_body = new JSONObject();
        request_body.put("senderID",user.getUserID());
        request_body.put("passwordAfterRSA", user.getPassword());
        request_body.put("username", user.getUserName());
        request_body.put("tele", user.getIdentification());
        String editinfo_response = httpPut(url, parameters, request_body.toJSONString());

        //修改信息请求失败
        if(editinfo_response.equals(user_not_found)){
            //report user not found
            return false;
        }

        if(editinfo_response.equals(bad_request)) {
            //report bad request
            return false;
        }

        return true;
    }

    @Override
    public boolean editTags() {
        //目前认为这个过程是先在user对象里设置tags再调用这个函数
        //判断user是否存在
        if(user == null){
            //report or throw exception
            return false;
        }

        //修改用户的Tags
        String url = "/user/tags";
        List<String> parameters = Arrays.asList(String.valueOf(user.getUserID()));
        List<String> tags = user.getTags();//怎么得到新的标签集合
        JSONArray tagsArray = JSONArray.parseArray(JSON.toJSONString(tags));
        //需要验证转换的tagsArray是否是符合要求的格式
        String tag_list = tagsArray.toJSONString();//把标签集合转换成JSON对应的string
        String tags_response = httpPut(url, parameters, tag_list);

        //修改tags成功
        if(tags_response.equals(ok)){
            //report success
            //之后是否要更新messageManager和missionManager中的user?
            return true;
        }

        //修改tags失败
        if(tags_response.equals(bad_request)){
            //report bad request
            return false;
        }
        else if(tags_response.equals(user_not_found)){
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
        JSONObject request_body = new JSONObject();
        request_body.put("senderID",user.getUserID());
        request_body.put("passwordAfterRSA", user.getPassword());

        String password_response = httpPut(url, parameters, request_body.toJSONString());

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
        else if(password_response.equals(user_not_found)){
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

        String url = "/user/evaluate";
        List<String> parameters = Arrays.asList(String.valueOf(user.getUserID()),
                String.valueOf(missionID), String.valueOf(evaluateeID));

        JSONObject request_body = new JSONObject();
        request_body.put("senderID",user.getUserID());
        request_body.put("passwordAfterRSA", user.getPassword());
        request_body.put("score", score);

        //对evaluation_JSON.toJSONString的结果进行测试
        String evaluate_response = httpPost(url, parameters, request_body.toJSONString());

        //评价成功
        if(evaluate_response.equals(ok)){
            //report success
            return true;
        }

        //评价失败
        if(evaluate_response.equals(evaluator_not_found)) {
            //report evaluator not Found
            return false;
        }
        else if(evaluate_response.equals(mission_not_found)){
            //report mission Not Found
            return false;
        }
        else if(evaluate_response.equals(evaluatee_not_found)){
            //report evaluatee Not Found
            return false;
        }
        else if(evaluate_response.equals(bad_request)){
            //report bad request
            return false;
        }
        else if(evaluate_response.equals(forbidden)){
            //report forbidden
            return false;
        }

        return false;
    }

    @Override
    public boolean sendCaptcha(String mailbox) {
        String url = "/user/captcha";
        JSONObject request_body = new JSONObject();
        request_body.put("mailbox", mailbox);
        String send_response = httpPost(url, null, request_body.toJSONString());

        if(send_response.equals(ok)){
            return true;
        }
        else if(send_response.equals(bad_request)){
            return false;
        }
        return false;
    }
}
