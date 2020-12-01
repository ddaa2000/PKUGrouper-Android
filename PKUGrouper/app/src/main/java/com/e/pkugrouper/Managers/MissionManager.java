package com.e.pkugrouper.Managers;

import com.e.pkugrouper.Models.IMission;
import com.e.pkugrouper.Models.IUser;
import com.e.pkugrouper.Models.Mission;

import java.util.Arrays;
import java.util.List;

public class MissionManager extends HttpManager implements IMissionManager{

    private IUser currentUser;
    @Override
    public void setCurrentUser(IUser _currentUser) {
        if (_currentUser == null){
            //report or throw
        }
        else
            currentUser = _currentUser;
    }

    @Override
    public IMission findMissionByID(int missionID) {
        //检查currentUser是否存在
        if(currentUser == null){
            //throw
            return null;
        }

        //检查参数
        if(missionID < 0) {
            //report or throw
            return null;
        }

        //获得Mission的JSON序列
        String url = "/mission";
        List<String> parameters = Arrays.asList(String.valueOf(currentUser.getUserID()),
                String.valueOf(missionID));
        String Mission_JSON = httpGet(url, parameters, null);

        //检查是否返回JSON序列
        if(Mission_JSON == null) {
            //report or throw
            return null;
        }

        IMission mission = new Mission();
        mission.loadFromJSON(Mission_JSON);
        //return mission
        return null;
    }

    @Override
    public List<IMission> findMissionByDescription(String description, List<String> tags) {
        //检查currentUser是否为空
        if(currentUser == null){
            //throw
            return null;
        }

        //检查参数
        if(description == null || tags == null) {
            //report or throw
            return null;
        }

        //得到Mission的JSON序列
        String url = "/missions";
        List<String> parameters = Arrays.asList(String.valueOf(currentUser.getUserID()));

        String body = "";
        for (String tag: tags) {
            body += tag + " ";
        }
        body += description;
        String Mission_JSON_List = httpGet(url, parameters, body);

        //check 返回信息
        if(Mission_JSON_List == null) {
            //report or throw
            return null;
        }

        return null;
    }

    @Override
    public boolean deleteMission(int missionID) {
        //检查currentUser是否为空
        if(currentUser == null){
            //throw
            return false;
        }

        //检查参数
        if (missionID < 0) {
            //report or throw;
            return false;
        }

        //删除信息
        String url = "/mission/delete";
        List<String> parameters = Arrays.asList(String.valueOf(currentUser.getUserID()),
                String.valueOf(missionID));
        String delete_JSON = httpPost(url, parameters, null);

        if(delete_JSON == null){
            //report or throw
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public boolean addMission(IMission mission) {
        //检查currentUser
        if(currentUser == null){
            //throw
            return false;
        }

        //检查参数
        if(mission == null) {
            //report or throw
            return false;
        }

        //添加Mission
        String url = "/mission/create";
        List<String> parameters = Arrays.asList(String.valueOf(currentUser.getUserID()));
        String mission_JSON = mission.toJSON();
        String add_response = httpPost(url, parameters, mission_JSON);

        if(add_response == null){
            //report or throw
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public boolean editMission(IMission mission) {
        //检查currentUser
        if(currentUser == null){
            //throw
            return false;
        }

        //检查参数
        if(mission == null) {
            //report or throw
            return false;
        }

        //修改Mission
        String url = "/mission/edit";
        List<String> parameters = Arrays.asList(String.valueOf(currentUser.getUserID()),
                String.valueOf(mission.getID()));
        String body = mission.getContent();
        String edit_response = httpPut(url, parameters, body);

        if(edit_response == null){
            //report or throw
            return false;
        }
        else {
            return true;
        }

    }

    @Override
    public boolean accept(int missionID, int applicantID) {
        //检查currentUser
        if(currentUser == null){
            //throw
            return false;
        }

        //检查参数
        if(missionID < 0 || applicantID < 0) {
            //report or throw
            return false;
        }

        //接受成员
        String url = "/mission/accept";
        List<String> parameters = Arrays.asList(String.valueOf(currentUser.getUserID()),
                String.valueOf(missionID), String.valueOf(applicantID));
        String accept_response = httpPost(url, parameters, null);

        if(accept_response == null){
            //report or throw
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public boolean fire(int missionID, int applicantID) {
        //检查currentUser
        if(currentUser == null){
            //throw
            return false;
        }

        //检查参数
        if(missionID < 0 || applicantID < 0) {
            //report or throw
            return false;
        }

        //踢出参与者
        String url = "/mission/fire";
        List<String> parameters = Arrays.asList(String.valueOf(currentUser.getUserID()),
                String.valueOf(missionID), String.valueOf(applicantID));
        String fire_response = httpPost(url, parameters, null);

        if(fire_response == null){
            //report or throw
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public boolean reject(int missionID, int applicantID) {
        //检查currentUser
        if(currentUser == null){
            //throw
            return false;
        }

        //检查参数
        if(missionID < 0 || applicantID < 0) {
            //report or throw
            return false;
        }

        //拒绝参与者申请
        String url = "/mission/reject";
        List<String> parameters = Arrays.asList(String.valueOf(currentUser.getUserID()),
                String.valueOf(missionID), String.valueOf(applicantID));
        String reject_response = httpPost(url, parameters, null);

        if(reject_response == null){
            //report or throw
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public boolean join(int missionID) {
        //检查currentUser
        if(currentUser == null){
            //throw
            return false;
        }

        //检查参数
        if(missionID < 0) {
            //report or throw
            return false;
        }

        //申请加入某个任务
        String url = "/mission/join";
        List<String> parameters = Arrays.asList(String.valueOf(currentUser.getUserID()),
                String.valueOf(missionID));
        String join_response = httpPost(url, parameters, null);

        if(join_response == null){
            //report or throw
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public boolean quit(int missionID) {
        //检查currentUser
        if(currentUser == null){
            //throw
            return false;
        }

        //检查参数
        if(missionID < 0) {
            //report or throw
            return false;
        }

        //退出任务
        String url = "/mission/quit";
        List<String> parameters = Arrays.asList(String.valueOf(currentUser.getUserID()),
                String.valueOf(missionID));
        String quit_response = httpPost(url, parameters, null);

        if(quit_response == null){
            //report or throw
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public boolean start(int missionID) {
        //检查currentUser
        if(currentUser == null){
            //throw
            return false;
        }

        //检查参数
        if(missionID < 0) {
            //report or throw
            return false;
        }

        //开始任务
        String url = "/mission/start";
        List<String> parameters = Arrays.asList(String.valueOf(currentUser.getUserID()),
                String.valueOf(missionID));
        String start_response = httpPost(url, parameters, null);

        if(start_response == null){
            //report or throw
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public boolean finish(int missionID) {
        //检查currentUser
        if(currentUser == null){
            //throw
            return false;
        }

        //检查参数
        if(missionID < 0) {
            //report or throw
            return false;
        }

        //结束任务
        String url = "/mission/finish";
        List<String> parameters = Arrays.asList(String.valueOf(currentUser.getUserID()),
                String.valueOf(missionID));
        String finish_response = httpPost(url, parameters, null);

        if(finish_response == null){
            //report or throw
            return false;
        }
        else{
            return true;
        }
    }
}
