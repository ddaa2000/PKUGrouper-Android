package com.e.pkugrouper.Managers;

import com.e.pkugrouper.Models.IMessage;
import com.e.pkugrouper.Models.IUser;

import java.util.List;

public class MessageManager extends HttpManager implements IMessageManager{
    private IUser currentUser;

    @Override
    public void setCurrentUser(IUser _currentUser) {
        if (_currentUser == null) {
            //throw
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
            return false;
        }

        //得到currentUser的信息
        String url = "/message";
        List<String> parameters = [currentUser.getUserID()];
        String Message_JSON_All = self.httpGet(url, parameters, null);

        //如果得不到信息
        if(Message_JSON_All == null){
            //report
            return null;
        }

        //下一步是分割的问题 暂时得不到解决
        List<String> Message_JSON_List;
        List<IMessage> Message_List;
        for (String Message_JSON: Message_JSON_List){
            IMessage current_message = new IMessage;
            current_message.loadFromJSON(Message_JSON);
            Message_List.add(current_message);
        }
        //return Message_List;
        return null;
    }

    @Override
    public boolean reportBug(IMessage bug) {
        //检查currentUser
        if(currentUser == null){
            //throw
            return false;
        }

        //报告bug
        String url = "/message/bug";
        List<String> parameters = [currentUser.getUserID()];
        String bug_JSON = bug.toJSON();
        String bug_final_JSON = self.httpPost(url, parameters, bug_JSON);

        //检查是否报告成功
        if(bug_final_JSON == null){
            //report or throw exception
            return false;
        }
        else{
            return true;
        }
        return false;
    }

    @Override
    public boolean report(IMessage report) {
        //检查currentUser
        if(currentUser == null){
            //throw
            return false;
        }

        //报告
        String url = "/message/report";
        List<String> parameters = [currentUser.getUserID()];
        String report_JSON = report.toJSON();
        String report_final_JSON = self.httpPost(url, parameters, report_JSON);

        //检查是否报告成功
        if(report_final_JSON == null){
            //report or throw exception
            return false;
        }
        else {
            return true;
        }
        return false;
    }

}
