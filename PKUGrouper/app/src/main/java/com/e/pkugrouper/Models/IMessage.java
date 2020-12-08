package com.e.pkugrouper.Models;

import java.util.List;

public interface IMessage extends ISerializable{
    int getPublisherID();
    void setPublisherID(int _ID);
    String getType();
    void setType(String _type);
    List<Integer> getRecipientIDs();
    String getMessageContent();
    void setMessageContent(String content);
    void setReporteeID(int _reporteeID);
    int getReporteeID();
}
