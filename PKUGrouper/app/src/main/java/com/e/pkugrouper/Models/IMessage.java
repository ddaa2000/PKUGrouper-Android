package com.e.pkugrouper.Models;

import java.util.List;

public interface IMessage extends ISerializable{
    int getPublisherID();
    void setPublisherID(int _ID);
    int getType();
    void setType(int _type);
    List<Integer> getRecipientIDs();
    String getMessageContent();
    void setMessageContent(String content);
}
