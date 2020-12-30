package com.e.pkugrouper;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.pkugrouper.Models.IMission;
import com.e.pkugrouper.Models.Mission;

import java.util.List;

public class MissionHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private IMission mission;
    private TextView missionTitleText;
    private TextView missionDescriptionText;
    private Activity activity;
    private ImageView professional,life,other,general;
    public MissionHolder(LayoutInflater inflater, ViewGroup parent, Activity activity) {
        super(inflater.inflate(R.layout.mission_item,parent,false));
        this.activity = activity;
        missionTitleText = itemView.findViewById(R.id.mission_item_title);
        missionDescriptionText = itemView.findViewById(R.id.mission_item_description);
        professional = itemView.findViewById(R.id.missionItem_professional);
        general = itemView.findViewById(R.id.missionItem_general);
        life = itemView.findViewById(R.id.missionItem_life);
        other = itemView.findViewById(R.id.missionItem_other);
        itemView.findViewById(R.id.mission_item_card).setOnClickListener(this);
    }

    public void bind(IMission mission){
        this.mission = mission;
        missionTitleText.setText(mission.getTitle());
        missionDescriptionText.setText(mission.getContent());
        general.setVisibility(View.INVISIBLE);
        life.setVisibility(View.INVISIBLE);
        other.setVisibility(View.INVISIBLE);
        professional.setVisibility(View.INVISIBLE);
        if(mission.getChannels().size()!=0){
            if(mission.getChannels().get(0).equals(Mission.CHANNEL_PROFESSIONAL))
                professional.setVisibility(View.VISIBLE);
            else if(mission.getChannels().get(0).equals(Mission.CHANNEL_GENERAL))
                general.setVisibility(View.VISIBLE);
            else if(mission.getChannels().get(0).equals(Mission.CHANNEL_LIFE))
                life.setVisibility(View.VISIBLE);
            else
                other.setVisibility(View.VISIBLE);
        }
        final ObjectAnimator anim1 = ObjectAnimator.ofFloat(itemView,"scaleX",0f,1f);
        final ObjectAnimator anim2 = ObjectAnimator.ofFloat(itemView,"scaleY",0f,1f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(anim1,anim2);
        animatorSet.setDuration((long)(Math.random()*300+150));
        animatorSet.start();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(activity,MissionDetailActivity.class);
        GlobalObjects.currentMission = mission;
        activity.startActivity(intent);
    }
}
