package com.e.pkugrouper.Managers;

import com.alibaba.fastjson.JSONObject;
import com.e.pkugrouper.Models.IMission;
import com.e.pkugrouper.Models.IUser;
import com.e.pkugrouper.Models.Mission;
import com.e.pkugrouper.Models.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class MissionManagerTest {
    private MissionManager missionManager;
    @Before
    public void setUp() throws Exception {
        missionManager = new MissionManager();
        System.out.println("Test Begin!");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Test End!");
    }

    @Test
    public void setCurrentUser() {
        try{
            missionManager.setCurrentUser(null);
            fail("need a RuntimeException");
        }catch (RuntimeException exception){
            assertEquals(exception.getMessage(), "currentUser is null!");
        }
        IUser currentUser = new User();
        missionManager.setCurrentUser(currentUser);
        assertEquals(missionManager.getCurrentUser(), currentUser);
    }

    @Test
    public void findMissionByID() {
        try{
            IMission mission1 = missionManager.findMissionByID(0);
            fail("need a RuntimeException");
        }catch (RuntimeException exception){
            assertEquals(exception.getMessage(), "currentUser is null!");
        }

        IUser currentUser = new User();
        currentUser.setUserID(0);
        missionManager.setCurrentUser(currentUser);
        try{
            IMission mission2 = missionManager.findMissionByID(0);
            fail("need a RuntimeException");
        }catch (RuntimeException exception){
            assertEquals(exception.getMessage(), "missionID should be greater than 0!");
        }

        try{
            IMission mission3 = missionManager.findMissionByID(1);
            fail("need a RuntimeException");
        }catch (RuntimeException exception){
            assertEquals(exception.getMessage(), "User is not found!");
        }

        currentUser.setUserID(1);
        try{
            IMission mission4 = missionManager.findMissionByID(1);
            fail("need a RuntimeException");
        }catch (RuntimeException exception){
            assertEquals(exception.getMessage(), "mission is not found!");
        }

        IMission mission5 = missionManager.findMissionByID(2);
        assertEquals(mission5.getContent(), "new mission");
        assertEquals(mission5.getTitle(), "new mission");
    }

    @Test
    public void findMissionByDescription() {
        try{
            List<IMission> missions1 = missionManager.findMissionByDescription(null, null,0,0);
            fail("need a RuntimeException");
        }catch (RuntimeException exception){
            assertEquals(exception.getMessage(), "currentUser is null!");
        }

        IUser currentUser = new User();
        currentUser.setUserID(0);
        missionManager.setCurrentUser(currentUser);
        try{
            List<IMission> missions2 = missionManager.findMissionByDescription(null, null,0,0);
            fail("need a RuntimeException");
        }catch (RuntimeException exception){
            assertEquals(exception.getMessage(), "description and channels should not be null!");
        }

        List<String> channels = Arrays.asList("study", "game");
        String description = "software engineering";
        try{
            List<IMission> missions3 = missionManager.findMissionByDescription(description, channels,0,0);
            fail("need a RuntimeException");
        }catch (RuntimeException exception){
            assertEquals(exception.getMessage(), "User is not found!");
        }

        currentUser.setUserID(1);
        try{
            List<IMission> missions4 = missionManager.findMissionByDescription(description, channels,0,0);
            fail("need a RuntimeException");
        }catch (RuntimeException exception){
            assertEquals(exception.getMessage(), "find mission is bad request!");
        }

        currentUser.setUserID(2);
        List<IMission> missions5 = missionManager.findMissionByDescription(description, channels,0,0);
        String[] contents = {"Masiwei", "KnowKnow", "KeyNG"};
        String[] titles = {"CDC", "Higher Brothers", "Woken Day"};
        assertEquals(missions5.size(), titles.length);
        for(int i = 0; i < missions5.size(); i++){
            assertEquals(missions5.get(i).getTitle(), titles[i]);
            assertEquals(missions5.get(i).getContent(), contents[i]);
        }
    }

    @Test
    public void deleteMission() {
        try{
            boolean delete1 = missionManager.deleteMission(0);
            fail("need a RuntimeException");
        }catch (RuntimeException exception){
            assertEquals(exception.getMessage(), "currentUser is null!");
        }

        IUser currentUser = new User();
        currentUser.setUserID(0);
        missionManager.setCurrentUser(currentUser);
        try{
            boolean delete2 = missionManager.deleteMission(0);
            fail("need a RuntimeException");
        }catch (RuntimeException exception){
            assertEquals(exception.getMessage(), "missionID should be great than 0!");
        }

        try{
            boolean delete3 = missionManager.deleteMission(1);
            fail("need a RuntimeException");
        }catch (RuntimeException exception){
            assertEquals(exception.getMessage(), "User is not found!");
        }

        currentUser.setUserID(1);
        try{
            boolean delete4 = missionManager.deleteMission(1);
            fail("need a RuntimeException");
        }catch (RuntimeException exception){
            assertEquals(exception.getMessage(), "delete is forbidden!");
        }

        currentUser.setUserID(2);
        try{
            boolean delete5 = missionManager.deleteMission(1);
            fail("need a RuntimeException");
        }catch (RuntimeException exception){
            assertEquals(exception.getMessage(), "mission is not found!");
        }

        boolean delete6 = missionManager.deleteMission(2);
        assertTrue(delete6);
    }

    @Test
    public void addMission() {
        try{
            boolean add1 = missionManager.addMission(null);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "currentUser is null!");
        }

        IUser currentUser = new User();
        currentUser.setUserID(0);
        missionManager.setCurrentUser(currentUser);
        try{
            boolean add2 = missionManager.addMission(null);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "mission should not be null!");
        }

        IMission mission = new Mission();
        mission.setContent("myx");
        mission.setTitle("new generation");
        try{
            boolean add3 = missionManager.addMission(mission);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "User is not found!");
        }

        currentUser.setUserID(1);
        try{
            boolean add4 = missionManager.addMission(mission);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "this time is invalid to add mission!");
        }

        currentUser.setUserID(2);
        boolean add5 = missionManager.addMission(mission);
        assertTrue(add5);
        assertEquals(mission.getID(), 12);
    }

    @Test
    public void editMission() {
        try{
            boolean edit1 = missionManager.editMission(null);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "currentUser is null!");
        }

        IUser currentUser = new User();
        currentUser.setUserID(0);
        missionManager.setCurrentUser(currentUser);
        try{
            boolean edit2 = missionManager.editMission(null);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "mission should not be null!");
        }

        IMission mission = new Mission();
        mission.setContent("myx");
        mission.setTitle("new generation");
        mission.setID(1);
        try{
            boolean edit3 = missionManager.editMission(mission);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "User is not found!");
        }

        currentUser.setUserID(1);
        try{
            boolean edit4 = missionManager.editMission(mission);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "this time is invalid to edit mission!");
        }

        currentUser.setUserID(2);
        try{
            boolean edit5 = missionManager.editMission(mission);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "mission is not found!");
        }

        mission.setID(2);
        boolean edit6 = missionManager.addMission(mission);
        assertTrue(edit6);
    }

    @Test
    public void accept() {
        try{
            boolean accept1 = missionManager.accept(0,0);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "currentUser is null!");
        }

        IUser currentUser = new User();
        currentUser.setUserID(0);
        missionManager.setCurrentUser(currentUser);

        try{
            boolean accept2 = missionManager.accept(0,1);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "missionID should be greater than 0!");
        }

        try{
            boolean accept3 = missionManager.accept(1,0);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "applicantID should be greater than 0!");
        }

        try{
            boolean accept4 = missionManager.accept(1,1);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "User is not found!");
        }

        currentUser.setUserID(1);
        try{
            boolean accept5 = missionManager.accept(1,1);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "accept applicant is forbidden!");
        }

        currentUser.setUserID(2);
        try{
            boolean accept6 = missionManager.accept(1,2);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "mission is not found!");
        }

        try{
            boolean accept7 = missionManager.accept(2,1);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "applicant is not found!");
        }

        boolean accept8 = missionManager.accept(2, 2);
        assertTrue(accept8);
    }

    @Test
    public void fire() {
        try{
            boolean fire1 = missionManager.fire(0,0);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "currentUser is null!");
        }

        IUser currentUser = new User();
        currentUser.setUserID(0);
        missionManager.setCurrentUser(currentUser);
        try{
            boolean fire2 = missionManager.fire(0,1);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "missionID should be greater than 0!");
        }

        try{
            boolean fire3 = missionManager.fire(1,0);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "applicantID should be greater than 0!");
        }

        try{
            boolean fire4 = missionManager.fire(1,1);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "User is not found!");
        }

        currentUser.setUserID(1);
        try{
            boolean fire5 = missionManager.fire(1,1);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "fire applicant is forbidden!");
        }

        currentUser.setUserID(2);
        try{
            boolean fire6 = missionManager.fire(1,2);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "mission is not found!");
        }

        try{
            boolean fire7 = missionManager.fire(2,1);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "applicant is not found!");
        }

        boolean fire8 = missionManager.fire(2, 2);
        assertTrue(fire8);
    }

    @Test
    public void reject() {
        try{
            boolean reject1 = missionManager.reject(0,0);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "currentUser is null!");
        }

        IUser currentUser = new User();
        currentUser.setUserID(0);
        missionManager.setCurrentUser(currentUser);
        try{
            boolean reject2 = missionManager.reject(0,1);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "missionID should be greater than 0!");
        }

        try{
            boolean reject3 = missionManager.reject(1,0);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "applicantID should be greater than 0!");
        }

        try{
            boolean reject4 = missionManager.reject(1,1);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "User is not found!");
        }

        currentUser.setUserID(1);
        try{
            boolean reject5 = missionManager.reject(1,1);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "reject applicant is forbidden!");
        }

        currentUser.setUserID(2);
        try{
            boolean reject6 = missionManager.reject(1,2);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "mission is not found!");
        }

        try{
            boolean reject7 = missionManager.reject(2,1);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "applicant is not found!");
        }

        boolean reject8 = missionManager.reject(2, 2);
        assertTrue(reject8);
    }

    @Test
    public void join() {
        try{
            boolean join1 = missionManager.join(0);
            fail("need a RuntimeException");
        }catch (RuntimeException exception){
            assertEquals(exception.getMessage(), "currentUser is null!");
        }

        IUser currentUser = new User();
        currentUser.setUserID(0);
        missionManager.setCurrentUser(currentUser);
        try{
            boolean join2 = missionManager.join(0);
            fail("need a RuntimeException");
        }catch (RuntimeException exception){
            assertEquals(exception.getMessage(), "missionID should be greater than 0!");
        }

        try{
            boolean join3 = missionManager.join(1);
            fail("need a RuntimeException");
        }catch (RuntimeException exception){
            assertEquals(exception.getMessage(), "User is not found!");
        }

        currentUser.setUserID(1);
        try{
            boolean join4 = missionManager.join(1);
            fail("need a RuntimeException");
        }catch (RuntimeException exception){
            assertEquals(exception.getMessage(), "join mission is forbidden!");
        }

        currentUser.setUserID(2);
        try{
            boolean join5 = missionManager.join(1);
            fail("need a RuntimeException");
        }catch (RuntimeException exception){
            assertEquals(exception.getMessage(), "mission is not found!");
        }

        boolean join6 = missionManager.join(2);
        assertTrue(join6);
    }

    @Test
    public void quit() {
        try{
            boolean quit1 = missionManager.quit(0);
            fail("need a RuntimeException");
        }catch (RuntimeException exception){
            assertEquals(exception.getMessage(), "currentUser is null!");
        }

        IUser currentUser = new User();
        currentUser.setUserID(0);
        missionManager.setCurrentUser(currentUser);
        try{
            boolean quit2 = missionManager.quit(0);
            fail("need a RuntimeException");
        }catch (RuntimeException exception){
            assertEquals(exception.getMessage(), "missionID should be greater than 0!");
        }

        try{
            boolean quit3 = missionManager.quit(1);
            fail("need a RuntimeException");
        }catch (RuntimeException exception){
            assertEquals(exception.getMessage(), "User is not found!");
        }

        currentUser.setUserID(1);
        try{
            boolean quit4 = missionManager.quit(1);
            fail("need a RuntimeException");
        }catch (RuntimeException exception){
            assertEquals(exception.getMessage(), "quit mission is forbidden!");
        }

        currentUser.setUserID(2);
        try{
            boolean quit5 = missionManager.quit(1);
            fail("need a RuntimeException");
        }catch (RuntimeException exception){
            assertEquals(exception.getMessage(), "mission is not found!");
        }

        boolean quit6 = missionManager.quit(2);
        assertTrue(quit6);
    }

    @Test
    public void start() {
        try{
            boolean start1 = missionManager.start(0);
            fail("need a RuntimeException");
        }catch (RuntimeException exception){
            assertEquals(exception.getMessage(), "currentUser is null!");
        }

        IUser currentUser = new User();
        currentUser.setUserID(0);
        missionManager.setCurrentUser(currentUser);
        try{
            boolean start2 = missionManager.start(0);
            fail("need a RuntimeException");
        }catch (RuntimeException exception){
            assertEquals(exception.getMessage(), "missionID should be greater than 0!");
        }

        try{
            boolean start3 = missionManager.start(1);
            fail("need a RuntimeException");
        }catch (RuntimeException exception){
            assertEquals(exception.getMessage(), "User is not found!");
        }

        currentUser.setUserID(1);
        try{
            boolean start4 = missionManager.start(1);
            fail("need a RuntimeException");
        }catch (RuntimeException exception){
            assertEquals(exception.getMessage(), "start mission is forbidden!");
        }

        currentUser.setUserID(2);
        try{
            boolean start5 = missionManager.start(1);
            fail("need a RuntimeException");
        }catch (RuntimeException exception){
            assertEquals(exception.getMessage(), "mission is not found!");
        }

        boolean start6 = missionManager.start(2);
        assertTrue(start6);

    }

    @Test
    public void finish() {
        try{
            boolean finish1 = missionManager.finish(0);
            fail("need a RuntimeException");
        }catch (RuntimeException exception){
            assertEquals(exception.getMessage(), "currentUser is null!");
        }

        IUser currentUser = new User();
        currentUser.setUserID(0);
        missionManager.setCurrentUser(currentUser);
        try{
            boolean finish2 = missionManager.finish(0);
            fail("need a RuntimeException");
        }catch (RuntimeException exception){
            assertEquals(exception.getMessage(), "missionID should be greater than 0!");
        }

        try{
            boolean finish3 = missionManager.finish(1);
            fail("need a RuntimeException");
        }catch (RuntimeException exception){
            assertEquals(exception.getMessage(), "User is not found!");
        }

        currentUser.setUserID(1);
        try{
            boolean finish4 = missionManager.finish(1);
            fail("need a RuntimeException");
        }catch (RuntimeException exception){
            assertEquals(exception.getMessage(), "finish mission is forbidden!");
        }

        currentUser.setUserID(2);
        try{
            boolean finish5 = missionManager.finish(1);
            fail("need a RuntimeException");
        }catch (RuntimeException exception){
            assertEquals(exception.getMessage(), "mission is not found!");
        }

        boolean finish6 = missionManager.finish(2);
        assertTrue(finish6);
    }
}