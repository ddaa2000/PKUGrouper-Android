package com.e.pkugrouper;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.pkugrouper.Models.IMission;
import com.e.pkugrouper.Models.IUser;

import java.util.List;

public class UserCardAdapter extends RecyclerView.Adapter<UserCardHolder> {
    private List<IUser> users;
    private Activity activity;

    public UserCardAdapter(List<IUser> users, Activity activity){
        this.users = users;
        this.activity = activity;
    }

    @NonNull
    @Override
    public UserCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        return new UserCardHolder(layoutInflater, parent,activity);
    }

    @Override
    public void onBindViewHolder(@NonNull UserCardHolder holder, int position) {
        IUser user = users.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
