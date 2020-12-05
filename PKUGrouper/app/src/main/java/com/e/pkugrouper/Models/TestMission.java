package com.e.pkugrouper.Models;

import java.util.List;

public class TestMission implements IMission{
    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void setID(int _missionID) {

    }

    @Override
    public String getContent() {
        return "求大佬组队呀，计概大作业写不出来了";
    }

    @Override
    public void setContent(String _content) {

    }

    @Override
    public String getTitle() {
        return "计概大作业";
    }

    @Override
    public void setTitle(String title) {

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
        return null;
    }

    @Override
    public void loadFromJSON(String JSONString) {

    }
}
