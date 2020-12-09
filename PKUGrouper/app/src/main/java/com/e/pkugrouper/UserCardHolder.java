package com.e.pkugrouper;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.e.pkugrouper.Models.IUser;

public class UserCardHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView missionTitleText;
    private TextView missionDescriptionText;
    private Activity activity;
    private IUser user;
    public UserCardHolder(LayoutInflater inflater, ViewGroup parent, Activity activity) {
        super(inflater.inflate(R.layout.user_card,parent,false));
        this.activity = activity;
  //      missionTitleText = itemView.findViewById(R.id.mission_item_title);
  //      missionDescriptionText = itemView.findViewById(R.id.mission_item_description);
        itemView.setOnClickListener(this);
    }

    public void bind(IUser user){
        this.user = user;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(activity,UserDetailActivity.class);
        GlobalObjects.currentMember = user;
        activity.startActivity(intent);
    }

}
