package com.e.pkugrouper.Managers;

import com.e.pkugrouper.Models.IMessage;
import com.e.pkugrouper.Models.IUser;

import java.util.List;

public class MessageManager extends HttpManager implements IMessageManager{
    @Override
    public void setCurrentUser(IUser _currentUser) {

    }

    @Override
    public List<IMessage> getCurrentUserMessages() {
        return null;
    }

    @Override
    public void reportBug(IMessage bug) {

    }

    @Override
    public void report(IMessage report) {

    }
}
