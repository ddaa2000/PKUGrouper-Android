package com.e.pkugrouper.Models;

import java.util.List;


public interface IMessage extends ISerializable{
    int getPublisherID();
    void setPublisherID(int _ID);
    String getType();
    void setType(String _type);
    String getTimeStamp();
    void setTimeStamp(String _timeStamp);
    String getMessageContent();
    void setMessageContent(String content);
    int getReporteeID();
    void setReporteeID(int _ID);

    List<Integer> getRecipientIDs();
    void setRecipientIDs(List<Integer> _recipientIDs);
}
