package com.e.pkugrouper;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.pkugrouper.Models.IMission;
import com.e.pkugrouper.Models.Mission;

import java.util.List;

public class MissionAdapter extends RecyclerView.Adapter<MissionHolder> {

    private List<IMission> missions;
    private Activity activity;

    public MissionAdapter(List<IMission> missions, Activity activity){
        this.missions = missions;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MissionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        return new MissionHolder(layoutInflater, parent,activity);
    }

    @Override
    public void onBindViewHolder(@NonNull MissionHolder holder, int position) {
        IMission mission = missions.get(position);
        holder.bind(mission);
    }

    @Override
    public int getItemCount() {
        return missions.size();
    }
}
