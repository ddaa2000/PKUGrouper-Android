package com.e.pkugrouper.Managers;

import com.e.pkugrouper.Models.IUser;
import com.e.pkugrouper.Models.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
    }

    @Test
    public void findMissionByDescription() {
    }

    @Test
    public void deleteMission() {
    }

    @Test
    public void addMission() {
    }

    @Test
    public void editMission() {
    }

    @Test
    public void accept() {
    }

    @Test
    public void fire() {
    }

    @Test
    public void reject() {
    }

    @Test
    public void join() {
    }

    @Test
    public void quit() {
    }

    @Test
    public void start() {
    }

    @Test
    public void finish() {
    }
}