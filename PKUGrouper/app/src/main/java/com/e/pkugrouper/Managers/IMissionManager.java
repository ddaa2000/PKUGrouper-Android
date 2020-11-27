package com.e.pkugrouper.Managers;

import com.e.pkugrouper.Models.IMission;
import com.e.pkugrouper.Models.IUser;

import java.util.List;

/**
 * @author ddaa
 * MissionManager应当实现的接口
 *
 */
public interface IMissionManager {
    void setCurrentUser(IUser _currentUser);

    IMission findMissionByID(int missionID);        // /mission
    List<IMission> findMissionByDescription(String description,List<String> tags);  // /missions

    void deleteMission(int missionID);              // mission/delete
    void addMission(IMission mission);              // mission/create
    void editMission(IMission mission);             // mission/accept
    void accept(int missionID, int applicantID);    // mission/accept
    void fire(int missionID, int applicantID);      // mission/fire
    void reject(int missionID, int applicantID);    // mission/reject
    void join(int missionID);                       // mission/join
    void quit(int missionID);                       // mission/quit
    void start(int missionID);                      // mission/start
    void finish(int missionID);                     // mission/finish
}
