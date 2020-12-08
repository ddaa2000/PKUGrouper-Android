package com.e.pkugrouper.Managers;

import com.e.pkugrouper.Models.IMessage;
import com.e.pkugrouper.Models.IUser;
import com.e.pkugrouper.Models.Message;
import com.e.pkugrouper.Models.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class MessageManagerTest {
    private MessageManager messageManager;
    @Before
    public void setUp() throws Exception {
        messageManager = new MessageManager();
        System.out.println("Test Begin!");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Test End!");
    }
    @Test
    public void setCurrentUser() {
        try{
            messageManager.setCurrentUser(null);
            fail("need a RuntimeException");
        }catch (RuntimeException exception){
            assertEquals(exception.getMessage(), "currentUser is null!");
        }
        IUser currentUser = new User();
        messageManager.setCurrentUser(currentUser);
        assertEquals(messageManager.getCurrentUser(), currentUser);
    }

    @Test
    public void getCurrentUserMessages() {
        try{
            List<IMessage> messages1 = messageManager.getCurrentUserMessages();
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "currentUser is null!");
        }

        IUser currentUser = new User();
        currentUser.setUserID(1);
        messageManager.setCurrentUser(currentUser);
        try{
            List<IMessage> messages2 = messageManager.getCurrentUserMessages();
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "User is not found!");
        }

        currentUser.setUserID(2);
        try{
            List<IMessage> messages3 = messageManager.getCurrentUserMessages();
            fail("need a RuntimeException");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "Get messages is bad request!");
        }

        String[] types={"Report", "Bug", "Notice"};
        String[] Contents = {"Hello", "Hi", "Are you OK?"};
        currentUser.setUserID(3);
        List<IMessage> messages4 = messageManager.getCurrentUserMessages();
        for(int i = 0; i < messages4.size(); i++){
            assertEquals(messages4.get(i).getType(), types[i]);
            assertEquals(messages4.get(i).getMessageContent(), Contents[i]);
        }
    }

    @Test
    public void reportBug() {
        try{
            boolean report1 = messageManager.reportBug(null);
            fail("need a RuntimeException!");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "currentUser is null!");
        }

        IUser currentUser = new User();
        currentUser.setUserID(1);
        messageManager.setCurrentUser(currentUser);
        try{
            boolean report1 = messageManager.reportBug(null);
            fail("need a RuntimeException!");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "You should send a bug!");
        }

        try{
            IMessage message = new Message();
            message.setType("Report");
            boolean report2 = messageManager.reportBug(message);
            fail("need a RuntimeException!");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "You should send a bug!");
        }

        IMessage message = new Message();
        message.setType("Bug");
        try{
            boolean report3 = messageManager.reportBug(message);
            fail("need a RuntimeException!");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "User is not found!");
        }

        currentUser.setUserID(2);
        boolean report4 = messageManager.reportBug(message);
        assertEquals(report4, true);
    }

    @Test
    public void report() {
        try{
            boolean report1 = messageManager.report(null);
            fail("need a RuntimeException!");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "currentUser is null!");
        }

        IUser currentUser = new User();
        currentUser.setUserID(1);
        messageManager.setCurrentUser(currentUser);
        try{
            boolean report1 = messageManager.report(null);
            fail("need a RuntimeException!");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "You should send a Report!");
        }

        try{
            IMessage message = new Message();
            message.setType("Bug");
            boolean report2 = messageManager.report(message);
            fail("need a RuntimeException!");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "You should send a Report!");
        }

        IMessage message = new Message();
        message.setType("Report");
        message.setReporteeID(1);
        try{
            boolean report3 = messageManager.report(message);
            fail("need a RuntimeException!");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "User is not found!");
        }

        currentUser.setUserID(2);
        try{
            boolean report4 = messageManager.report(message);
            fail("need a RuntimeException!");
        }catch(RuntimeException exception){
            assertEquals(exception.getMessage(), "Reportee is not found!");
        }

        message.setReporteeID(2);
        boolean report5 = messageManager.report(message);
        assertEquals(report5, true);
    }

    @Test
    public void findMessageByID() {
        try{
            IMessage message1 = messageManager.findMessageByID(0);
            fail("need a RuntimeException");
        }catch (RuntimeException exception){
            assertEquals(exception.getMessage(), "currentUser is null!");
        }

        IUser currentUser = new User();
        currentUser.setUserID(16);
        messageManager.setCurrentUser(currentUser);
        try{
            IMessage message2 = messageManager.findMessageByID(0);
            fail("need a RuntimeException");
        }catch (RuntimeException exception){
            assertEquals(exception.getMessage(), "messageID should be greater than 0!");
        }

        try{
            IMessage message3 = messageManager.findMessageByID(2);
            fail("need a RuntimeException");
        }catch (RuntimeException exception){
            assertEquals(exception.getMessage(), "message is not found!");
        }

        IMessage message4 = messageManager.findMessageByID(3);
        assertEquals(message4.getMessageContent(), "How are you?");
        assertEquals(message4.getType(), "Notice");
    }
}