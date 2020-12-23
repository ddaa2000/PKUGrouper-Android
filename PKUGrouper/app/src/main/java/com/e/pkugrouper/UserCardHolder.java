package com.e.pkugrouper;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.e.pkugrouper.Models.IUser;

public class UserCardHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView userNameText;
    private Activity activity;
    private IUser user;
    public UserCardHolder(LayoutInflater inflater, ViewGroup parent, Activity activity) {
        super(inflater.inflate(R.layout.user_card,parent,false));
        this.activity = activity;
  //      missionTitleText = itemView.findViewById(R.id.mission_item_title);
  //      missionDescriptionText = itemView.findViewById(R.id.mission_item_description);
        userNameText = itemView.findViewById(R.id.userCard_userName);
        itemView.setOnClickListener(this);
    }

    public void bind(IUser user){
        this.user = user;
        userNameText.setText(user.getUserName());
    }

    @Override
    public void onClick(View v) {

        if(user.getUserID()==GlobalObjects.currentUser.getUserID())
            return;
        if(GlobalObjects.currentMission.hasPublisher(GlobalObjects.currentUser)||
                GlobalObjects.currentMission.hasMember(GlobalObjects.currentUser)){
            Intent intent = new Intent(activity,UserDetailActivity.class);
            GlobalObjects.currentMember = user;
            activity.startActivity(intent);
        }
        else{
            Toast.makeText(activity,"只有任务成员可以查看详细信息",2).show();
        }

    }

}
