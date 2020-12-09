package com.e.pkugrouper.Models;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public class Mission implements IMission{
    private String content;
    private String Title;
    private int missionID;
    @Override
    public int getID() {
        return missionID;
    }

    @Override
    public void setID(int _missionID) {
        missionID = _missionID;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void setContent(String _content) {
        content = _content;
    }

    @Override
    public String getTitle() {
        return Title;
    }

    @Override
    public void setTitle(String title) {
        Title = title;
    }

    @Override
    public List<Integer> getApplicantIDs() {
        return null;
    }

    @Override
    public List<Integer> getMemberIDs() {
        return null;
    }

    @Override
    public void setState() {

    }

    @Override
    public int getState() {
        return 0;
    }

    @Override
    public List<String> getTags() {
        return null;
    }

    @Override
    public int getPublisher() {
        return 0;
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public void setSize(int size) {

    }

    @Override
    public String toJSON() {
        JSONObject mission_JSON = new JSONObject();
        if(Title != null){
            mission_JSON.put("title",Title);
        }
        if(content != null){
            mission_JSON.put("content", content);
        }
        return mission_JSON.toJSONString();
    }

    @Override
    public void loadFromJSON(String JSONString) {
        JSONObject mission_json = JSONObject.parseObject(JSONString);
        setContent(mission_json.getString("content"));
        setTitle(mission_json.getString("title"));
    }
}
