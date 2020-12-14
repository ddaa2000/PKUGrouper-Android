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
import com.e.pkugrouper.Models.Mission;

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
        GlobalObjects.currentMission = new Mission();
        activity.startActivity(intent);
    }
}
