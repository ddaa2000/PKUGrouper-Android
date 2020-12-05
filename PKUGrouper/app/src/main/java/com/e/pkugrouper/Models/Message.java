package com.e.pkugrouper.Models;

import java.util.List;

public class Message implements IMessage{
    @Override
    public int getPublisherID() {
        return 0;
    }

    @Override
    public void setPublisherID(int _ID) {

    }

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public void setType(int _type) {

    }

    @Override
    public List<Integer> getRecipientIDs() {
        return null;
    }

    @Override
    public String getMessageContent() {
        return null;
    }

    @Override
    public void setMessageContent(String content) {

    }

    @Override
    public void setReporteeID(int _reporteeID) {

    }


    @Override
    public int getReporteeID() {
        return 0;
    }

    @Override
    public String toJSON() {
        return null;
    }

    @Override
    public void loadFromJSON(String JSONString) {

    }
}
