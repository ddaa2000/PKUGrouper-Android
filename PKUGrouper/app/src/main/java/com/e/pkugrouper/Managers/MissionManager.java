package com.e.pkugrouper.Managers;

import com.e.pkugrouper.Models.IMission;
import com.e.pkugrouper.Models.IUser;

import java.util.List;

public class MissionManager extends HttpManager implements IMissionManager{

    @Override
    public void setCurrentUser(IUser _currentUser) {

    }

    @Override
    public IMission findMissionByID(int missionID) {
        return null;
    }

    @Override
    public List<IMission> findMissionByDescription(String description, List<String> tags) {
        return null;
    }

    @Override
    public boolean deleteMission(int missionID) {
        return false;
    }

    @Override
    public boolean addMission(IMission mission) {
        return false;
    }

    @Override
    public boolean editMission(IMission mission) {
        return false;
    }

    @Override
    public boolean accept(int missionID, int applicantID) {
        return false;
    }

    @Override
    public boolean fire(int missionID, int applicantID) {
        return false;
    }

    @Override
    public boolean reject(int missionID, int applicantID) {
        return false;
    }

    @Override
    public boolean join(int missionID) {
        return false;
    }

    @Override
    public boolean quit(int missionID) {
        return false;
    }

    @Override
    public boolean start(int missionID) {
        return false;
    }

    @Override
    public boolean finish(int missionID) {
        return false;
    }
}
