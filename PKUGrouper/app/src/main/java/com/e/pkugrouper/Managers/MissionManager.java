package com.e.pkugrouper.Managers;

import com.e.pkugrouper.Models.IMission;
import com.e.pkugrouper.Models.IUser;

import java.util.List;

public class MissionManager implements IMissionManager{
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
    public void deleteMission(int missionID) {

    }

    @Override
    public void addMission(IMission mission) {

    }

    @Override
    public void editMission(IMission mission) {

    }

    @Override
    public void accept(int missionID, int applicantID) {

    }

    @Override
    public void fire(int missionID, int applicantID) {

    }

    @Override
    public void reject(int missionID, int applicantID) {

    }

    @Override
    public void join(int missionID) {

    }

    @Override
    public void quit(int missionID) {

    }

    @Override
    public void start(int missionID) {

    }

    @Override
    public void finish(int missionID) {

    }
}
