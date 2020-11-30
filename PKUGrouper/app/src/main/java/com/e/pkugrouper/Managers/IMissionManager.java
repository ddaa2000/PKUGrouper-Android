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

    boolean deleteMission(int missionID);              // mission/delete
    boolean addMission(IMission mission);              // mission/create
    boolean editMission(IMission mission);             // mission
    boolean accept(int missionID, int applicantID);    // mission/accept
    boolean fire(int missionID, int applicantID);      // mission/fire
    boolean reject(int missionID, int applicantID);    // mission/reject
    boolean join(int missionID);                       // mission/join
    boolean quit(int missionID);                       // mission/quit
    boolean start(int missionID);                      // mission/start
    boolean finish(int missionID);                     // mission/finish
}
