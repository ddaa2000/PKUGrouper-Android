package com.e.pkugrouper.Managers;

import com.e.pkugrouper.Models.IMessage;
import com.e.pkugrouper.Models.IUser;

import java.util.List;

public class MessageManager extends HttpManager implements IMessageManager{
    private IUser currentUser;


    @Override
    public void setCurrentUser(IUser _currentUser) {

    }

    @Override
    public List<IMessage> getCurrentUserMessages() {
        return null;
    }

    @Override
    public boolean reportBug(IMessage bug) {
        return false;
    }

    @Override
    public boolean report(IMessage report) {
        return false;
    }
}
