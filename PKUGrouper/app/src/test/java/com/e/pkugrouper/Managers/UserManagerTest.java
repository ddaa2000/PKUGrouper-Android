package com.e.pkugrouper.Managers;

import com.e.pkugrouper.Models.IEvaluation;
import com.e.pkugrouper.Models.IUser;
import com.e.pkugrouper.Models.Mission;
import com.e.pkugrouper.Models.User;

import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class UserManagerTest {
    UserManager userManager;
    MessageManager messageManager;
    MissionManager missionManager;
    @Before
    public void setUp() throws Exception {
        userManager = new UserManager();
        messageManager = new MessageManager();
        missionManager = new MissionManager();
        System.out.println("Test Begin!");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Test End!");
    }

    @Test
    public void userLogIn() {
        try{
            IUser user1 = userManager.userLogIn(null);
            fail("need a RuntimeException");
        } catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "currentUser is null!");
        }
        IUser currentUser = new User();
        currentUser.setPassword("fuck");
        try{
            IUser user2 = userManager.userLogIn(currentUser);
            fail("need a RuntimeException");
        } catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "Login is forbidden");
        }
        currentUser.setPassword("happy");
        try{
            IUser user3 = userManager.userLogIn(currentUser);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "messageManager is null!");
        }
        try{
            userManager.setMessageManager(messageManager);
            IUser user4 = userManager.userLogIn(currentUser);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "missionManager is null!");
        }

        userManager.setMessageManager(messageManager);
        userManager.setMissionManager(missionManager);
        IUser user5 = userManager.userLogIn(currentUser);
        assertEquals(user5.getUserID(),12);
    }

    @Test
    public void userRegister1() {
        try {
            IUser user = userManager.userRegister(null, "");
            fail("need a RunTimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "currentUser is null!");
        }
    }

    @Test
    public void userRegister2() {
        try {
            IUser currentUser = new User();
            currentUser.setMailBox("1800013014@pku.edu.cn");
            currentUser.setUserName("myx");
            currentUser.setPassword("abcdefg");
            IUser user = userManager.userRegister(currentUser, null);
            fail("need a RunTimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "captcha is null!");
        }
    }

    @Test
    public void userRegister3() {
        try {
            IUser currentUser = new User();
            currentUser.setMailBox("1800013014@pku.edu.cn");
            currentUser.setUserName("myx");
            currentUser.setPassword("abcdefg");
            IUser user = userManager.userRegister(currentUser, "1");
            fail("need a RunTimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "Register is bad request!");
        }
    }

    @Test
    public void userRegister4() {
        try{
            IUser currentUser = new User();
            currentUser.setMailBox("1800013014@pku.edu.cn");
            currentUser.setUserName("myx");
            currentUser.setPassword("abcdefg");
            IUser user = userManager.userRegister(currentUser, "2");
            fail("need a RunTimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "username is bad!");
        }
    }

    @Test
    public void userRegister5() {
        IUser currentUser = new User();
        currentUser.setMailBox("1800013014@pku.edu.cn");
        currentUser.setUserName("myx");
        currentUser.setPassword("abcdefg");
        IUser user = userManager.userRegister(currentUser, "3");
        assertEquals(user.getUserID(), 12);
    }

    @Test
    public void setMissionManager() {
        try{
            userManager.setMissionManager(null);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "missionManager is null!");
        }
        userManager.setMissionManager(missionManager);
        assertSame(userManager.getMissionManager(),missionManager);
    }

    @Test
    public void setMessageManager() {
        try{
            userManager.setMessageManager(null);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "messageManager is null!");
        }
        userManager.setMessageManager(messageManager);
        assertSame(userManager.getMessageManager(),messageManager);
    }

    @Test
    public void sendCaptcha() {
        String mailbox_true = "1800013014";
        String mailbox_false = "abcd";
        try{
            boolean send = userManager.sendCaptcha(null);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "mailbox is null!");
        }

        try{
            boolean send2 = userManager.sendCaptcha(mailbox_false);
            fail("need a RuntimeException!");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "Send captcha is bad request!");
        }

        boolean send3 = userManager.sendCaptcha(mailbox_true);
        assertEquals(send3, true);
    }

    @Test
    public void changePassword(){
        String new_password = "myx";
        try{
            boolean change1 = userManager.changePassword(new_password);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "User is null");
        }

        IUser currentUser = new User();
        currentUser.setPassword("hzh");
        currentUser.setUserID(10);
        userManager.setUser(currentUser);

        try{
            boolean change2 = userManager.changePassword(new_password);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "change password is bad request!");
        }

        currentUser.setUserID(11);
        try{
            boolean change3 = userManager.changePassword(new_password);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "User is not found!");
        }

        currentUser.setUserID(12);
        try{
            boolean change4 = userManager.changePassword(new_password);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "Old password is wrong!");
        }

        currentUser.setPassword("azc");
        try{
            boolean change5 = userManager.changePassword(new_password);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "New password is invalid!");
        }

        new_password = "lth";
        boolean change6 = userManager.changePassword(new_password);
        assertEquals(new_password, currentUser.getPassword());
        assertEquals(change6, true);
    }

    @Test
    public void evaluate(){
        try{
            boolean evaluate1 = userManager.evaluate(1,1,1);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "User is null!");
        }

        IUser currentUser = new User();
        currentUser.setUserID(16);
        userManager.setUser(currentUser);
        try{
            boolean evaluate2 = userManager.evaluate(0,1,1);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "Mission ID and Evaluatee ID should be greater than 0!");
        }

        try{
            boolean evaluate3 = userManager.evaluate(1,0,1);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "Mission ID and Evaluatee ID should be greater than 0!");
        }

        try{
            boolean evaluate4 = userManager.evaluate(1,1,0);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "Score should be greater than 0!");
        }

        try{
            boolean evaluate5 = userManager.evaluate(1,1,1);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "Evaluator is not found!");
        }

        currentUser.setUserID(17);
        try{
            boolean evaluate6 = userManager.evaluate(1,1,1);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "Mission is not found!");
        }

        try{
            boolean evaluate7 = userManager.evaluate(2,1,1);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "Evaluatee is not found!");
        }

        currentUser.setUserID(18);
        try{
            boolean evaluate8 = userManager.evaluate(2,2,1);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "Evaluate is bad request!");
        }

        currentUser.setUserID(19);
        try{
            boolean evaluate9 = userManager.evaluate(2,2,1);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "Evaluate is forbidden!");
        }

        currentUser.setUserID(17);
        boolean evaluate10 = userManager.evaluate(2,2,1);
        assertEquals(evaluate10, true);

    }

    @Test
    public void findEvaluationByID(){
        try{
            IEvaluation evaluation1 = userManager.findEvaluationByID(0);
            fail("need a RuntimeException!");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "user is null!");
        }

        IUser currentUser = new User();
        currentUser.setUserID(16);
        userManager.setUser(currentUser);
        try{
            IEvaluation evaluation2 = userManager.findEvaluationByID(0);
            fail("need a RuntimeException!");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "evaluationID should be greater than 0!");
        }

        try{
            IEvaluation evaluation3 = userManager.findEvaluationByID(1);
            fail("need a RuntimeException!");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "evaluation is not found!");
        }

        IEvaluation evaluation3 = userManager.findEvaluationByID(2);
        assertEquals(evaluation3.getScore(), 2, 0.001);
    }

    @Test
    public void editInfo(){
        String username = "myx";
        String tele = "1224769259";
        try{
            boolean edit1 = userManager.editInfo(username, tele);
            fail("need a RuntimeException!");
        }catch (RuntimeException exception){
            assertEquals(exception.getMessage(), "user is null!");
        }

        IUser currentUser = new User();
        currentUser.setUserName("kliar");
        currentUser.setTele("18000285706");
        currentUser.setUserID(16);
        userManager.setUser(currentUser);
        try{
            boolean edit2 = userManager.editInfo(null, tele);
            fail("need a RuntimeException!");
        }catch (RuntimeException exception){
            assertEquals(exception.getMessage(), "username can't be null!");
        }

        try{
            boolean edit3 = userManager.editInfo(username, tele);
            fail("need a RuntimeException!");
        }catch (RuntimeException exception){
            assertEquals(exception.getMessage(), "User is not found!");
        }

        currentUser.setUserID(17);
        try{
            boolean edit4 = userManager.editInfo(username, tele);
            fail("need a RuntimeException!");
        }catch (RuntimeException exception){
            assertEquals(exception.getMessage(), "editInfo is bad request!");
        }

        currentUser.setUserID(18);
        boolean edit5 = userManager.editInfo(username, tele);
        assertEquals(username, currentUser.getUserName());
        assertEquals(tele, currentUser.getTele());
    }

    @Test
    public void getSelf(){
        try{
            IUser commonUser1 = userManager.getSelf();
            fail("need a RuntimeException!");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "User is Null");
        }

        IUser user = new User();
        user.setUserID(16);
        userManager.setUser(user);
        try{
            IUser commonUser2 = userManager.getSelf();
            fail("need a RuntimeException!");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "User is not found!");
        }

        user.setUserID(17);
        user.setMailBox("1800013014");
        user.setUserName("myx");
        IUser commonUser3 = userManager.getSelf();
        assertEquals(commonUser3.getMailBox(), user.getMailBox());
        assertEquals(commonUser3.getUserName(), user.getUserName());
    }

    @Test
    public void findMemberByID(){
        try{
            IUser commonUser1 = userManager.findMemberByID(1,1);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "User is Null");
        }

        IUser currentUser = new User();
        currentUser.setUserID(16);
        userManager.setUser(currentUser);
        try{
            IUser commonUser2 = userManager.findMemberByID(0,1);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "Mission ID and Member ID should be greater than 0");
        }

        try{
            IUser commonUser3 = userManager.findMemberByID(1,0);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "Mission ID and Member ID should be greater than 0");
        }

        try{
            IUser commonUser4 = userManager.findMemberByID(1,1);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "Getter is not found!");
        }

        currentUser.setUserID(17);
        try{
            IUser commonUser5 = userManager.findMemberByID(1,1);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "Mission is not found!");
        }

        try{
            IUser commonUser6 = userManager.findMemberByID(2,1);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "Gettee is not found!");
        }

        currentUser.setUserID(18);
        try{
            IUser commonUser7 = userManager.findMemberByID(2,2);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "This is bad request!");
        }

        currentUser.setUserID(19);
        try{
            IUser commonUser8 = userManager.findMemberByID(2,2);
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "Find is Forbidden");
        }

        currentUser.setUserID(17);
        IUser commonUser9 = userManager.findMemberByID(2,2);
        assertEquals(commonUser9.getUserID(), 2);
        assertEquals(commonUser9.getAverageScore(), 5, 0.01);
    }

    @Test
    public void getEvaluations(){
        try{
            List<IEvaluation> evaluations1 = userManager.getEvaluations();
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "user is null!");
        }

        IUser currentUser = new User();
        currentUser.setUserID(1);
        userManager.setUser(currentUser);
        try{
            List<IEvaluation> evaluations2 = userManager.getEvaluations();
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "User is not found!");
        }

        currentUser.setUserID(13);
        List<IEvaluation> evaluations3 = userManager.getEvaluations();
//        for(int i = 0; i < evaluations3.size(); i++){
//            IEvaluation evaluation = evaluations3.get(i);
//            assertEquals(evaluation.getEvaluatorID(), currentUser.getUserID());
//            assertEquals(evaluation.getScore(), i + 2, 0.01);
//        }
    }
}