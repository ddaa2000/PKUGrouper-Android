package com.e.pkugrouper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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