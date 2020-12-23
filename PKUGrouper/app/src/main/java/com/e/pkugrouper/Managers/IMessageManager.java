package com.e.pkugrouper.Managers;

import com.e.pkugrouper.Models.IMessage;
import com.e.pkugrouper.Models.IUser;

import java.util.List;

/**
 * @author ddaa
 * MessageManager应当实现的接口
 */
public interface IMessageManager {
    void setCurrentUser(IUser _currentUser);

    List<IMessage> getCurrentUserMessages();    // messages
    boolean reportBug(IMessage bug);               // message/bug
    boolean report(IMessage report);               // message/report
    IMessage findMessageByID(int messageID);       // message
    List<IMessage> findMessages(int[] messageIDs);
}
