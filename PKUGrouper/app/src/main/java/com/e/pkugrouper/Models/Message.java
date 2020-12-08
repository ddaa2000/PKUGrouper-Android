package com.e.pkugrouper.Models;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

public class Message implements IMessage{
    private String type;
    private String messageContent;
    private int ReporteeID;
    @Override
    public int getPublisherID() {
        return 0;
    }

    @Override
    public void setPublisherID(int _ID) {

    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String _type) {
        type = _type;
    }

    @Override
    public List<Integer> getRecipientIDs() {
        return null;
    }

    @Override
    public String getMessageContent() {
        return messageContent;
    }

    @Override
    public void setMessageContent(String content) {
        messageContent = content;
    }

    @Override
    public void setReporteeID(int _reporteeID) {
        ReporteeID = _reporteeID;
    }


    @Override
    public int getReporteeID() {
        return ReporteeID;
    }

    @Override
    public String toJSON() {
        return null;
    }

    @Override
    public void loadFromJSON(String JSONString) {
        JSONObject message_json = JSONObject.parseObject(JSONString);
        setMessageContent(message_json.getString("messageContent"));
        setType(message_json.getString("type"));
    }
}
