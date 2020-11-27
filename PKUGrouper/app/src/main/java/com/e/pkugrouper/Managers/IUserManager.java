package com.e.pkugrouper.Managers;

import com.e.pkugrouper.Models.ICommonUser;
import com.e.pkugrouper.Models.IUser;

/**
 * @author ddaa
 * UserManager应当实现的接口
 * 注意，真正的UserManager应当保存有当前用户的CommonUser对象，这个对象应当是在userLogIn或者userRegister时
 * 从服务器获取的
 * 当user的信息发生变化时，UserManager应当通知MissionManager与MessageManager，为它们赋予新值
 */
public interface IUserManager {
    ICommonUser findMemberByID(int missionID, int userID);  // user/member
    ICommonUser getSelf();                                  // user/self
    IUser userLogIn(IUser currentUser);                     // user/login
    IUser userRegister(IUser currentUser);                  // user/register

    void setMissionManager(IMissionManager _missionManager);
    void setMessageManager(IMessageManager _messageManager);


    void editInfo();                                        // user/info
    void changePassword();                                  // user/code
    void evaluate(int missionID, int evauateeID,int score); // evaluation

}
