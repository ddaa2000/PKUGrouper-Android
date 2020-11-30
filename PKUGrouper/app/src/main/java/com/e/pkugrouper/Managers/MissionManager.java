package com.e.pkugrouper.Managers;

import com.e.pkugrouper.Models.IMission;
import com.e.pkugrouper.Models.IUser;

import java.util.List;

public class MissionManager extends HttpManager implements IMissionManager{

    private IUser currentUser;
    @Override
    public void setCurrentUser(IUser _currentUser) {
        if (_currentUser == null){

        }
        else
            currentUser = _currentUser;
    }

    @Override
    public IMission findMissionByID(int missionID) {
        String url = "/mission";
        if(currentUser == null){
            //throw
            return null;
        }
        List<String> parameters = [currentUser.getUserID(), String.ValueOf(missionID)];
        String Mission_Info = self.httpGet(url, parameters, null);
        return null;
    }

    @Override
    public List<IMission> findMissionByDescription(String description, List<String> tags) {
        String url = "/missions";
        if(currentUser == null){
            //throw
            return null;
        }
        List<String> parameters = [currentUser.getUserID()];
        String body = "";
        for (String tag: tags) {
            body += tag;
        }
        body += description;
        String Mission_List = self.httpGet(url, parameters, body);
        return null;
    }

    @Override
    public boolean deleteMission(int missionID) {
        String url = "/mission/delete";
        if(currentUser == null){
            //throw
            return null;
        }
        List<String> parameters = [currentUser.getUserID(), String.ValueOf(missionID)];
        String delete = self.httpPost(url, parameters, null);
        return false;
    }

    @Override
    public boolean addMission(IMission mission) {
        String url = "/mission/create";
        if(currentUser == null){
            //throw
            return null;
        }
        List<String> parameters = [currentUser.getUserID()];
        String body = null;
        String add = self.httpPost(url, parameters, body);
        return false;
    }

    @Override
    public boolean editMission(IMission mission) {
        String url = '/mission/edit';
        if(currentUser == null){
            //throw
            return null;
        }
        List<String> parameters = [currentUser.getUserID(), mission.getID()];
        String body = mission.getContent();
        String edit = self.httpPut(url, parameters, body);
        return false;
    }

    @Override
    public boolean accept(int missionID, int applicantID) {
        String url = "/mission/accept";
        if(currentUser == null){
            //throw
            return null;
        }
        List<String> parameters = [currentUser.getUserID(),
                String.ValueOf(missionID), String.ValueOf(applicantID)];
        String Accept = self.httpPost(url, parameters, null);
        return false;
    }

    @Override
    public boolean fire(int missionID, int applicantID) {
        String url = "/mission/fire";
        if(currentUser == null){
            //throw
            return null;
        }
        List<String> parameters = [currentUser.getUserID(),
                String.ValueOf(missionID), String.ValueOf(applicantID)];
        String Fire = self.httpPost(url, parameters, null);
        return false;
    }

    @Override
    public boolean reject(int missionID, int applicantID) {
        String url = "/mission/reject";
        if(currentUser == null){
            //throw
            return null;
        }
        List<String> parameters = [currentUser.getUserID(),
                String.ValueOf(missionID), String.ValueOf(applicantID)];
        String Reject = self.httpPost(url, parameters, null);
        return false;
    }

    @Override
    public boolean join(int missionID) {
        String url = "/mission/join";
        if(currentUser == null){
            //throw
            return null;
        }
        List<String> parameters = [currentUser.getUserID(), String.ValueOf(missionID)];
        String Join = self.httpPost(url, parameters, null);
        return false;
    }

    @Override
    public boolean quit(int missionID) {
        String url = "/mission/quit";
        if(currentUser == null){
            //throw
            return null;
        }
        List<String> parameters = [currentUser.getUserID(), String.ValueOf(missionID)];
        String Quit = self.httpPost(url, parameters, null);
        return false;
    }

    @Override
    public boolean start(int missionID) {
        String url = "/mission/start";
        if(currentUser == null){
            //throw
            return null;
        }
        List<String> parameters = [currentUser.getUserID(), String.ValueOf(missionID)];
        String Start = self.httpPost(url, parameters, null);
        return false;
    }

    @Override
    public boolean finish(int missionID) {
        String url = "/mission/finish";
        if(currentUser == null){
            //throw
            return null;
        }
        List<String> parameters = [currentUser.getUserID(), String.ValueOf(missionID)];
        String Finish = self.httpPost(url, parameters, null);
        return false;
    }
}
