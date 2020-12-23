package com.e.pkugrouper;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.e.pkugrouper.Models.IMessage;
import com.e.pkugrouper.Models.IMission;

import java.util.List;

public class MessageHolder extends RecyclerView.ViewHolder{

    private TextView messageTitleText;
    private TextView messageContentText;
    private Activity activity;
    public MessageHolder(LayoutInflater inflater, ViewGroup parent, Activity activity) {
        super(inflater.inflate(R.layout.message_card,parent,false));
        this.activity = activity;
        messageTitleText = itemView.findViewById(R.id.messageCard_title);
        messageContentText = itemView.findViewById(R.id.messageCard_content);
    }

    public void bind(IMessage message){
        messageTitleText.setText("暂时还没有标题");
        messageContentText.setText(message.getMessageContent());
    }

}
