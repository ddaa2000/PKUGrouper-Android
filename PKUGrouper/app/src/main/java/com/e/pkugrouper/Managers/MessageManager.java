package com.e.pkugrouper.Managers;

import com.e.pkugrouper.Models.IMessage;
import com.e.pkugrouper.Models.IUser;
import com.e.pkugrouper.Models.Message;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class MessageManager extends HttpManager implements IMessageManager{
    private IUser currentUser;

    private final String user_not_found = "\"user Not Found\"";
    private final String bad_request = "\"Bad Request\"";
    private final String ok = "\"OK\"";
    private final String reportee_not_found = "\"reportee Not Found\"";
    private final String message_not_found = "\"message Not Found\"";

    @Override
    public void setCurrentUser(IUser _currentUser) {
        if (_currentUser == null) {
            //report or throw
        }
        else {
            currentUser = _currentUser;
        }
    }

    @Override
    public List<IMessage> getCurrentUserMessages() {
        //判断currentUser是否存在
        if(currentUser == null){
            //report or throw exception
            return null;
        }

        //得到currentUser的信息
        String url = "/messages";
        List<String> parameters = Arrays.asList(String.valueOf(currentUser.getUserID()));
        String Message_JSON_All = httpGet(url, parameters, null);

        //如果得不到信息
        if(Message_JSON_All == null){
            //report
            return null;
        }

        //下一步是分割的问题 暂时得不到解决
        //return Message_List;
        return null;
    }

    @Override
    public boolean reportBug(IMessage bug) {
        // 两份api文档不一致，是否要加reportee的ID，怎么获取这个ID，
        // request body是bug的JSON模式 还是{"messageContent": "xxx"}
        //检查currentUser
        if(currentUser == null){
            //throw
            return false;
        }

        //检查参数
        if(bug == null) {
            //report or throw
            return false;
        }

        //报告bug
        String url = "/message/bug";
        List<String> parameters = Arrays.asList(String.valueOf(currentUser.getUserID()));
        String bug_JSON = bug.toJSON();
        String bug_response = httpPost(url, parameters, bug_JSON);

        //检查是否报告成功
        if (bug_response.equals(ok)){
            //report bug success
            return true;
        }

        if (bug_response.equals(user_not_found)){
            //report user not found
            return false;
        }

        return false;
    }

    @Override
    public boolean report(IMessage report) {
        //和reportBug一样的问题
        //检查currentUser
        if(currentUser == null){
            //report or throw
            return false;
        }

        //检查参数
        if(report == null){
            //report or throw;
            return false;
        }

        //报告
        String url = "/message/report";
        List<String> parameters = Arrays.asList(String.valueOf(currentUser.getUserID()));
        String report_JSON = report.toJSON();
        String report_response = httpPost(url, parameters, report_JSON);

        //检查是否报告成功
        if(report_response.equals(ok)){
            //report success
            return true;
        }

        if(report_response.equals(user_not_found)){
            //user not found
            return false;
        }

        if(report_response.equals(reportee_not_found)){
            //reportee not found
            return false;
        }

        return false;
    }

    @Override
    public IMessage findMessageByID(int messageID) {
        //检查参数
        if(messageID <= 0){
            //report
            return null;
        }

        String url = "/message";
        List<String> parameters = Arrays.asList(String.valueOf(messageID));
        String message_JSON = httpGet(url, parameters, null);

        //成功得到信息
        if(message_JSON.equals(ok)){
            IMessage message = new Message();
            message.loadFromJSON(message_JSON);
            return message;
        }

        //获取Message失败
        if(message_JSON.equals(message_not_found)){
            //report message not found
            return null;
        }

        return null;
    }

}
