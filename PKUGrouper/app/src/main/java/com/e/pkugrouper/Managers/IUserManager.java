package com.e.pkugrouper.Managers;

import com.e.pkugrouper.Models.IEvaluation;
import com.e.pkugrouper.Models.IUser;

import java.util.List;

/**
 * @author ddaa
 * UserManager应当实现的接口
 * 注意，真正的UserManager应当保存有当前用户的CommonUser对象，这个对象应当是在userLogIn或者userRegister时
 * 从服务器获取的
 * 当user的信息发生变化时，UserManager应当通知MissionManager与MessageManager，为它们赋予新值
 */
public interface IUserManager {
    IUser findMemberByID(int missionID, int memberID);  // user/member
    IUser getSelf();                                  // user/self
    IUser userLogIn(IUser currentUser);                     // user/login
    IUser userRegister(IUser currentUser, String captcha);  // user/register
    List<IEvaluation> getEvaluations();                     // user/evaluations
    IEvaluation findEvaluationByID(int evaluationID);

    boolean setMissionManager(IMissionManager _missionManager);
    boolean setMessageManager(IMessageManager _messageManager);


    boolean editInfo(String username, String tele);                                        // user/info
    //boolean editTags();                                        // user/tags
    boolean changePassword(String password);                    // user/code
    boolean evaluate(int missionID, int evaluateeID,int score); // user/evaluate
    boolean sendCaptcha(String mailbox);                        //user/captcha

    List<IEvaluation> findEvaluations(int[] evaluationIDs);
    List<IUser> findUsers(int missionID, int[] getteeIDs);
    boolean sendPasswordCaptcha(String mailbox);
    boolean findPassword(String captcha, String newPassword);
}
