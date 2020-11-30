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
        String url = "/message";
        List<String> parameters = [currentUser.getUserID()];
        String Message = self.httpGet(url, parameters, null);
        return null;
    }

    @Override
    public boolean reportBug(IMessage bug) {
        String url = "/message/bug";
        if(currentUser == null){
            //throw
            return false;
        }
        List<String> parameters = [currentUser.getUserID()];
        String bug_content = bug.getMessageContent();
        String bug_list = self.httpPost(url, parameters, bug_content);
        return false;
    }

    @Override
    public boolean report(IMessage report) {
        String url = "/message/report";
        if(currentUser == null){
            //throw
            return false;
        }
        List<String> parameters = [currentUser.getUserID()];
        String report_content = report.getMessageContent();
        String report_list = self.httpPost(url, parameters, report_content);
        return false;
    }

}
