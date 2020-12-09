package com.e.pkugrouper;


import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.pkugrouper.Models.IMission;

import java.util.List;

public class MissionHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private TextView missionTitleText;
    private TextView missionDescriptionText;
    private Activity activity;
    public MissionHolder(LayoutInflater inflater, ViewGroup parent, Activity activity) {
        super(inflater.inflate(R.layout.mission_item,parent,false));
        this.activity = activity;
        missionTitleText = itemView.findViewById(R.id.mission_item_title);
        missionDescriptionText = itemView.findViewById(R.id.mission_item_description);
        itemView.findViewById(R.id.mission_item_card).setOnClickListener(this);
    }

    public void bind(IMission mission){
        missionTitleText.setText(mission.getTitle());
        missionDescriptionText.setText(mission.getContent());
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(activity,MissionDetailActivity.class);
        GlobalObjects.currentMission = new IMission() {
            @Override
            public int getID() {
                return 0;
            }

            @Override
            public void setID(int _missionID) {

            }

            @Override
            public String getContent() {
                return "当前的任务内容";
            }

            @Override
            public void setContent(String _content) {

            }

            @Override
            public String getTitle() {
                return "当前的全局静态任务";
            }

            @Override
            public void setTitle(String title) {

            }

            @Override
            public List<Integer> getApplicantIDs() {
                return null;
            }

            @Override
            public List<Integer> getMemberIDs() {
                return null;
            }

            @Override
            public void setState() {

            }

            @Override
            public int getState() {
                return 0;
            }

            @Override
            public List<String> getTags() {
                return null;
            }

            @Override
            public int getPublisher() {
                return 0;
            }

            @Override
            public int getSize() {
                return 0;
            }

            @Override
            public void setSize(int size) {

            }

            @Override
            public String toJSON() {
                return null;
            }

            @Override
            public void loadFromJSON(String JSONString) {

            }
        };
        activity.startActivity(intent);
    }
}
