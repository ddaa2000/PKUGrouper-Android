package com.e.pkugrouper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class MissionDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_detail);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = new MissionManageFragment();
        fragmentManager.beginTransaction().replace(R.id.mission_detail_context_fragment,fragment).commit();
    }
}