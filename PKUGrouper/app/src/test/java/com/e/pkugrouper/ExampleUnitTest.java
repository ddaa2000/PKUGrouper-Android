package com.e.pkugrouper;

import com.e.pkugrouper.Managers.HttpManager;
import com.e.pkugrouper.Managers.MessageManager;
import com.e.pkugrouper.Managers.MissionManager;
import com.e.pkugrouper.Managers.RSAUtils;
import com.e.pkugrouper.Managers.UserManager;
import com.e.pkugrouper.Models.IMission;
import com.e.pkugrouper.Models.IUser;
import com.e.pkugrouper.Models.Mission;
import com.e.pkugrouper.Models.User;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void httpManagerTest(){
        HttpManager httpManager = new HttpManager();
        List<String> params = new ArrayList<String>();
        params.add("2");
        String body = "{\"senderID\":2,\"passwordAfterRSA\":\"LhsE31d3n4DXCd3a33xqMaleB4m55wm1uGzgd4aE9J8lDNaWOmRgvGbmlHm+GPADnZhGez0hNbdDf5qZ6l33H1gmTKrvQcbns4P78c2mt82PYTCDHngI3BOImYp+hX4CFJkczeZafoCjsjrp4GNm+t6GV78ZSxgavvjXiyYzB04=\"}";
        httpManager.httpGet("/user/self",params,body);
    }

    @Test
    public void userManagerTest(){
        MessageManager messageManager = new MessageManager();
        MissionManager missionManager = new MissionManager();
        UserManager userManager = new UserManager();
        userManager.setMessageManager(messageManager);
        userManager.setMissionManager(missionManager);
        IUser user = new User();
        user.setMailBox("1800013018@pku.edu.cn");
        user.setUserID(2);
        user.setPassword("DD2000114%%%");
        RSAUtils utils = new RSAUtils();
        String afterRSA = utils.encrypto(user.getPassword());
        String afterRSA1 = utils.encrypto(user.getPassword());
        String afterRSA2 = utils.encrypto(user.getPassword());
        String afterRSA3 = new RSAUtils().encrypto(user.getPassword());
        user = userManager.userLogIn(user);
        user = userManager.getSelf();


        IMission newMission = new Mission();
                /*
                这里要改！！！
                 */

//        newMission.setChannels(new ArrayList<String>());
//        newMission.getChannels().add("abc");
//        newMission.setApplicationEndTime("2100-12-01 18:23:30");
//        newMission.setExecutionStartTime("2200-12-01 18:23:30");
//        newMission.setExecutionEndTime("2300-12-01 18:23:30");
//        newMission.setTitle("mission1");
//        newMission.setContent("mission content");
//        missionManager.addMission(newMission);
        List<String> channels = new ArrayList<String>();
        channels.add(Mission.CHANNEL_PROFESSIONAL);
        channels.add(Mission.CHANNEL_OTHER);
        channels.add(Mission.CHANNEL_GENERAL);
        channels.add(Mission.CHANNEL_LIFE);
        List<IMission> missions = missionManager.findMissionByDescription("",channels,1,20);

        int a = 1;

    }
}