package com.e.pkugrouper.Managers;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.e.pkugrouper.Models.Evaluation;
import com.e.pkugrouper.Models.IEvaluation;
import com.e.pkugrouper.Models.IMessage;
import com.e.pkugrouper.Models.IUser;
import com.e.pkugrouper.Models.Message;
import com.e.pkugrouper.Models.User;

import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public IUser findMemberByID(int missionID, int memberID) {
        if(memberID == user.getUserID())
            return user;
        if(user == null) {
            throw new RuntimeException("User is Null");
        } //检测user对象是否存在

        //检查参数
        if(missionID <= 0 || memberID <= 0) {
            throw new RuntimeException("Mission ID and Member ID should be greater than 0");
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
            throw new RuntimeException("Getter is not found!");
        }
        else if(Member_JSON.equals(mission_not_found)){
            throw new RuntimeException("Mission is not found!");
        }
        else if(Member_JSON.equals(gettee_not_found)){
            throw new RuntimeException("Gettee is not found!");
        }
        else if(Member_JSON.equals(bad_request)){
            throw new RuntimeException("This is bad request!");
        }
        else if(Member_JSON.equals(forbidden)){
            throw new RuntimeException("Find is Forbidden");
        }

        //生成member对应的IUser对象
        IUser member = new User();
        member.setUserID(memberID);
        member.loadFromJSON(Member_JSON);
        return member;
    }

    @Override
    public IUser getSelf() {
        if(user == null) {
            throw new RuntimeException("User is Null");
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
            throw new RuntimeException("User is not found!");
        }

        //从返回的JSON字符串中得到当前使用的User对象
//        IUser Self = new User();
//        Self.loadFromJSON(User_JSON);
        user.loadFromJSON(User_JSON);
        missionManager.setCurrentUser(user);
        messageManager.setCurrentUser(user);
        return user;
    }

    /*
        现在登录和注册的流程还需要进一步细化，究竟什么时候将UserManager的user设置为正在登录的user
        什么时候发送验证码，什么时候得到反馈的验证码，整套流程要详细规划
     */

    public IUser getUser(){
        if(user == null){
            throw new RuntimeException("user is null!");
        }
        else
            return user;
    }
    public IMessageManager getMessageManager(){
        if(messageManager == null){
            throw new RuntimeException("messageManager is null!");
        }
        else
            return messageManager;
    }
    public IMissionManager getMissionManager(){
        if(missionManager == null){
            throw new RuntimeException("missionManager is null!");
        }
        else
            return missionManager;
    }
    public void setUser(IUser currentUser){
        user = currentUser;
    }

    @Override
    public IUser userLogIn(IUser currentUser) {
        //检查currentUser是否存在
        if(currentUser == null) {
            throw new RuntimeException("currentUser is null!");
        }

        //生成登录产生的JSON字符串
        String url = "/user/login";
        JSONObject User_JSON = new JSONObject();
        User_JSON.put("mailbox", currentUser.getMailBox());
        String password = currentUser.getPassword();
        RSAUtils rsaUtils = new RSAUtils();
        String passwordAfterRSA =  rsaUtils.encrypto(password);
        currentUser.setPassword(passwordAfterRSA);
        User_JSON.put("passwordAfterRSA", currentUser.getPassword());
        String User_Login_JSON = httpPost(url, null, User_JSON.toJSONString());

        //如果登录失败
        if(User_Login_JSON.equals(forbidden)){
            throw new RuntimeException("Login is forbidden");
        }
        else{
            //生成登陆后的user对象
            int User_ID = JSONObject.parseObject(User_Login_JSON).getInteger("UID");
            currentUser.setUserID(User_ID);
            user = currentUser;
            if(missionManager!=null)
                missionManager.setCurrentUser(user);
            if(messageManager!=null)
                messageManager.setCurrentUser(user);

            //更新messageManager和missionMagager中的currentUser
            if (messageManager == null) {
                throw new RuntimeException("messageManager is null!");
            }
            messageManager.setCurrentUser(user);

            if (missionManager == null) {
                throw new RuntimeException("missionManager is null!");
            }
            missionManager.setCurrentUser(user);
            return currentUser;
        }
    }

    @Override
    public IUser userRegister(IUser currentUser, String captcha) {
        //注册的细节问题需要考量一下 首先是注册和发送验证码的嵌套 其次是设置user的位置
        //检查currentUser是否为null
        if(currentUser == null) {
            throw new RuntimeException("currentUser is null!");
        }

        if(captcha == null){
            throw new RuntimeException("captcha is null!");
        }

        //生成注册产生的JSON字符串
        String url = "/user/register";
        JSONObject User_JSON = new JSONObject();
        User_JSON.put("mailbox", currentUser.getMailBox());
        User_JSON.put("username", currentUser.getUserName());
        String password = currentUser.getPassword();
        RSAUtils rsaUtils = new RSAUtils();
        String passwordAfterRSA = rsaUtils.encrypto(password);
        currentUser.setPassword(passwordAfterRSA);
        User_JSON.put("passwordAfterRSA", passwordAfterRSA);
        User_JSON.put("captchaCode", captcha);
        String User_Register_JSON = httpPost(url, null, User_JSON.toJSONString());

        //检查注册是否失败
        if(User_Register_JSON.equals(bad_request)) {
            throw new RuntimeException("Register is bad request!");
        }

        if(User_Register_JSON.equals(bad_username)){
            throw new RuntimeException("username is bad!");
        }
        //生成注册后的User对象
        currentUser.setUserID(JSONObject.parseObject(User_Register_JSON).getInteger("UID"));

        user = currentUser;
        if(missionManager!=null)
            missionManager.setCurrentUser(user);
        if(messageManager!=null)
            messageManager.setCurrentUser(user);

        return currentUser;
    }

    @Override
    public List<IEvaluation> getEvaluations() {
        //检查user
        if(user == null){
            throw new RuntimeException("user is null!");
        }

        //尝试获取信息
        String url = "/user/evaluations";
        List<String> parameters = Arrays.asList(String.valueOf(user.getUserID()));
        JSONObject request_body = new JSONObject();
        request_body.put("senderID",user.getUserID());
        request_body.put("passwordAfterRSA", user.getPassword());
        String evaluations_id_list = httpGet(url, parameters, request_body.toJSONString());

        //如果user not found
        if(evaluations_id_list.equals(user_not_found)){
            throw new RuntimeException("User is not found!");
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
            throw new RuntimeException("user is null!");
        }
        if(evaluationID <= 0){
            throw new RuntimeException("evaluationID should be greater than 0!");
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
            throw new RuntimeException("evaluation is not found!");
        }

        IEvaluation evaluation = new Evaluation();
        evaluation.loadFromJSON(evaluation_JSON);
        return evaluation;
    }

    @Override
    public boolean setMissionManager(IMissionManager _missionManager) {
        if (_missionManager == null) {
            throw new RuntimeException("missionManager is null!");
        }
        else{
            missionManager = _missionManager;
            return true;
        }
    }

    @Override
    public boolean setMessageManager(IMessageManager _messageManager) {
        if (_messageManager == null) {
            throw new RuntimeException("messageManager is null!");
        }
        else{
            messageManager = _messageManager;
            return true;
        }
    }

    @Override
    public boolean editInfo(String username, String tele) {
        //判断user是否存在
        if(user == null){
            throw new RuntimeException("user is null!");
        }

        if(username == null || username.equals("")){
            throw new RuntimeException("username can't be null!");
        }
        //用Put修改用户的信息
        String url = "/user/info";
        List<String> parameters = Arrays.asList(String.valueOf(user.getUserID()));
        JSONObject request_body = new JSONObject();
        request_body.put("senderID",user.getUserID());
        request_body.put("passwordAfterRSA", user.getPassword());
        request_body.put("username", username);
        request_body.put("tele", tele);
        String editinfo_response = httpPut(url, parameters, request_body.toJSONString());

        //修改信息请求失败
        if(editinfo_response.equals(user_not_found)){
            throw new RuntimeException("User is not found!");
        }

        if(editinfo_response.equals(bad_request)) {
            throw new RuntimeException("editInfo is bad request!");
        }

        user.setUserName(username);
        user.setTele(tele);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean changePassword(String password) {
        //怎么得到旧的密码和新的密码
        //判断user是否存在
        if(user == null){
            throw new RuntimeException("User is null");
        }

        //修改用户的password
        String url = "/user/code";
        List<String> parameters = Arrays.asList(String.valueOf(user.getUserID()));
        JSONObject request_body = new JSONObject();
        request_body.put("senderID",user.getUserID());
        request_body.put("passwordAfterRSA", user.getPassword());
        RSAUtils rsaUtils = new RSAUtils();
        String passwordAfterRSA = rsaUtils.encrypto(password);
        request_body.put("newPasswordAfterRSA", passwordAfterRSA);
        String password_response = httpPut(url, parameters, request_body.toJSONString());

        //修改密码成功
        if(password_response.equals(ok)){
            user.setPassword(passwordAfterRSA);
            return true;
        }

        //修改password失败
        if(password_response.equals(bad_request)){
            throw new RuntimeException("change password is bad request!");
        }
        else if(password_response.equals(user_not_found)){
            throw new RuntimeException("User is not found!");
        }
        //旧密码错误 和 新密码无效
        if(password_response.equals(wrong_password)){
            throw new RuntimeException("Old password is wrong!");
        }
        else if(password_response.equals(invalid_password)){
            throw new RuntimeException("New password is invalid!");
        }

        return false;
    }

    @Override
    public boolean evaluate(int missionID, int evaluateeID, int score) {
        //判断user是否存在
        if(user == null){
            throw new RuntimeException("User is null!");
        }

        //参数检查
        if(missionID <= 0 || evaluateeID <= 0) {
            throw new RuntimeException("Mission ID and Evaluatee ID should be greater than 0!");
        }

        if(score <= 0){
            throw new RuntimeException("Score should be greater than 0!");
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
            throw new RuntimeException("Evaluator is not found!");
        }
        else if(evaluate_response.equals(mission_not_found)){
            throw new RuntimeException("Mission is not found!");
        }
        else if(evaluate_response.equals(evaluatee_not_found)){
            throw new RuntimeException("Evaluatee is not found!");
        }
        else if(evaluate_response.equals(bad_request)){
            throw new RuntimeException("Evaluate is bad request!");
        }
        else if(evaluate_response.equals(forbidden)){
            throw new RuntimeException("Evaluate is forbidden!");
        }

        return false;
    }

    @Override
    public boolean sendCaptcha(String mailbox) {
        if(mailbox == null){
            throw new RuntimeException("mailbox is null!");
        }
        String url = "/user/captcha";
        JSONObject request_body = new JSONObject();
        request_body.put("mailbox", mailbox);
        String send_response = httpPost(url, null, request_body.toJSONString());

        if(send_response.equals(ok)){
            return true;
        }
        else if(send_response.equals(bad_request)){
            throw new RuntimeException("Send captcha is bad request!");
        }
        return false;
    }

    @Override
    public List<IEvaluation> findEvaluations(int[] evaluationIDs) {
        if(user == null){
            throw new RuntimeException("currentUser is null!");
        }

        String url = "/findevaluations";
        List<String> parameters = Arrays.asList(String.valueOf(user.getUserID()));
        JSONObject request_body = new JSONObject();
        request_body.put("senderID",user.getUserID());
        request_body.put("passwordAfterRSA", user.getPassword());
        request_body.put("evaluationIDs", evaluationIDs);
        String find_response = httpPost(url, parameters, request_body.toJSONString());

        if(find_response.equals(user_not_found)){
            throw new RuntimeException("User is not found!");
        }

        if(find_response.equals(evaluation_not_found)){
            throw new RuntimeException("evaluation is not found!");
        }

        List<IEvaluation> Evaluation_List = new ArrayList<>();
        JSONArray platformArray = JSON.parseArray(find_response);
        for (Object jsonObject : platformArray) {
            String evaluation_json = jsonObject.toString();
            IEvaluation evaluation = new Evaluation();
            evaluation.loadFromJSON(evaluation_json);
            Evaluation_List.add(evaluation);
        }
        return Evaluation_List;
    }

    @Override
    public List<IUser> findUsers(int missionID, int[] getteeIDs) {
        if(user == null){
            throw new RuntimeException("currentUser is null!");
        }

        String url = "/findusers";
        List<String> parameters = Arrays.asList(String.valueOf(user.getUserID()),
                String.valueOf(missionID));
        JSONObject request_body = new JSONObject();
        request_body.put("senderID",user.getUserID());
        request_body.put("passwordAfterRSA", user.getPassword());
        request_body.put("getteeIDs", getteeIDs);
        String find_response = httpPost(url, parameters, request_body.toJSONString());

        if(find_response.equals(getter_not_found)){
            throw new RuntimeException("getter is not found!");
        }

        if(find_response.equals(evaluation_not_found)){
            throw new RuntimeException("evaluation is not found!");
        }

        if(find_response.equals(gettee_not_found)){
            throw new RuntimeException("gettee is not found!");
        }

        if(find_response.equals(bad_request)){
            throw new RuntimeException("find users is bad request!");
        }

        if(find_response.equals(forbidden)){
            throw new RuntimeException("find users is forbidden!");
        }

        List<IUser> gettee_List = new ArrayList<>();
        JSONArray platformArray = JSON.parseArray(find_response);
        for (Object jsonObject : platformArray) {
            String gettee_json = jsonObject.toString();
            IUser gettee = new User();
            gettee.loadFromJSON(gettee_json);
            gettee_List.add(gettee);
        }
        return gettee_List;
    }

    @Override
    public boolean sendPasswordCaptcha(String mailbox) {
        if(user == null){
            throw new RuntimeException("currentUser is null!");
        }

        String url = "/user/fixpasswordcaptcha";
        JSONObject request_body = new JSONObject();
        request_body.put("mailbox",mailbox);
        String send_response = httpPost(url, null, request_body.toJSONString());

        if(send_response.equals(bad_request)){
            throw new RuntimeException("send password captcha is bad request");
        }

        if(send_response.equals(ok))
            return true;

        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean findPassword(String captcha, String newPassword) {
        if(user == null){
            throw new RuntimeException("currentUser is null!");
        }

        String url = "/user/fixpasswordcaptcha";
        JSONObject request_body = new JSONObject();
        request_body.put("mailbox",user.getMailBox());
        RSAUtils rsaUtils = new RSAUtils();
        String passwordRSA = rsaUtils.encrypto(newPassword);
        request_body.put("passwordAfterRSA", passwordRSA);
        request_body.put("captchaCode", captcha);
        String send_response = httpPost(url, null, request_body.toJSONString());

        if(send_response.equals(bad_request)){
            throw new RuntimeException("find password is bad request");
        }

        JSONObject find_json = JSON.parseObject(send_response);
        int UID = find_json.getInteger("UID");
        return true;
    }
}
