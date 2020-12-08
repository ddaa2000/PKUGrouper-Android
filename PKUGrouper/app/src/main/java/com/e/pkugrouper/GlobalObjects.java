package com.e.pkugrouper;

import com.e.pkugrouper.Managers.IMessageManager;
import com.e.pkugrouper.Managers.IMissionManager;
import com.e.pkugrouper.Managers.IUserManager;
import com.e.pkugrouper.Models.IMessage;
import com.e.pkugrouper.Models.IMission;
import com.e.pkugrouper.Models.IUser;

public class GlobalObjects {
    public static IUserManager userManager;
    public static IMessageManager messageManager;
    public static IMissionManager missionManager;
    public static IMission currentMission;
    public static IUser currentUser;
    public static IMessage currentMessage;
    public static IUser currentMember;
}
