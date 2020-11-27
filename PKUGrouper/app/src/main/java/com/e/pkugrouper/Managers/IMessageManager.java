package com.e.pkugrouper.Managers;

import com.e.pkugrouper.Models.IMessage;
import com.e.pkugrouper.Models.IUser;

import java.util.List;

public interface IMessageManager {
    void setCurrentUser(IUser _currentUser);

    List<IMessage> getCurrentUserMessages();    // messages
    void reportBug(IMessage bug);               // message/bug
    void report(IMessage report);               // message/report

}
