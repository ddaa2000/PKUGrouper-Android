package com.e.pkugrouper.Managers;

import com.e.pkugrouper.Models.IMessage;
import com.e.pkugrouper.Models.IUser;

import java.util.List;

public class MessageManager implements IMessageManager{
    @Override
    public void setCurrentUser(IUser _currentUser) {

    }

    @Override
    public List<IMessage> getCurrentUserMessages() {
        return null;
    }
}
