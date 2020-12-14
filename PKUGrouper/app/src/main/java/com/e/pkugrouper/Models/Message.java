package com.e.pkugrouper.Models;

import java.util.List;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


public class Message implements  IMessage{
    private int publisherID;
    private String messageType;
    private String messageContent;
    private int reporteeID;
    private String timeStamp;

    List<Integer> recipientIDs;


    @Override
    public int getPublisherID() {
        return publisherID;
    }

    @Override
    public void setPublisherID(int _ID) {
        publisherID=_ID;
    }

    @Override
    public String getType() {
        return messageType;
    }

    @Override
    public void setType(String _type) {
        messageType=_type;
    }

    @Override
    public String getTimeStamp(){return timeStamp;}

    @Override
    public void setTimeStamp(String _timeStamp){timeStamp=_timeStamp; }

    @Override
    public String getMessageContent() {
        return messageContent;
    }

    @Override
    public void setMessageContent(String content) {
        messageContent=content;
    }

    @Override
    public int getReporteeID() {
        if(messageType=="Report"){
            return reporteeID;
        }
        else{
            return 0;
        }
    }

    @Override
    public void setReporteeID(int _ID) {
        if(messageType=="Report") {
            reporteeID=_ID;
        }
    }

    @Override
    public List<Integer> getRecipientIDs() {
        return recipientIDs;
    }
    
    @Override
    public void setRecipientIDs(List<Integer> _recipientIDs) {
    	recipientIDs=_recipientIDs;
    }
    

    @Override
    public String toJSON() {
        JSONObject object=new JSONObject();

        object.put("timeStamp",timeStamp);
        object.put("publisherID",publisherID);
        object.put("type",messageType);
        object.put("messageContent",messageContent);
        object.put("reportee",reporteeID);

        String objStr=JSON.toJSONString(object);
        return objStr;
    }

    @Override
    public void loadFromJSON(String JSONString) {
        JSONObject object=JSON.parseObject(JSONString);

        timeStamp=object.getString("timeStamp");
        publisherID=object.getIntValue("publisherID");
        messageType=object.getString("type");
        messageContent=object.getString("messageContent");
        reporteeID=object.getIntValue("reportee");
    }
}
