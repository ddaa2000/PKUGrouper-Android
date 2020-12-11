package com.e.pkugrouper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.e.pkugrouper.HelloWorldFragment;
import com.e.pkugrouper.Managers.MessageManager;
import com.e.pkugrouper.Managers.MissionManager;
import com.e.pkugrouper.Managers.UserManager;
import com.e.pkugrouper.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GlobalObjects.userManager = new UserManager();
        GlobalObjects.missionManager = new MissionManager();
        GlobalObjects.messageManager = new MessageManager();
        GlobalObjects.currentUser = null;
        GlobalObjects.currentMember = null;
        GlobalObjects.currentMessage = null;
        GlobalObjects.currentMission = null;
        GlobalObjects.userManager.setMessageManager(GlobalObjects.messageManager);
        GlobalObjects.userManager.setMissionManager(GlobalObjects.missionManager);
        
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.main_frame);
        if(fragment == null){
             fragment = new HelloWorldFragment();
             fm.beginTransaction().add(R.id.main_frame,fragment).commit();
        }
        else
            fm.beginTransaction().replace(R.id.main_frame,fragment).commit();
    }
}
