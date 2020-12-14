package com.e.pkugrouper;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.pkugrouper.Models.IMessage;
import com.e.pkugrouper.Models.IMission;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageHolder>{
    private List<IMessage> messages;
    private Activity activity;

    public MessageAdapter(List<IMessage> messages, Activity activity){
        this.messages = messages;
        this.activity = activity;
    }

    public void reloadData(List<IMessage> newMessages){
        this.messages.clear();
        this.messages.addAll(newMessages);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        return new MessageHolder(layoutInflater, parent,activity);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, int position) {
        IMessage message = messages.get(position);
        holder.bind(message);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
