package com.e.pkugrouper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class MissionAdapter extends ArrayAdapter<String> {
    int viewID;
    public MissionAdapter(@NonNull Context context, int resourceID, List<String> objects) {
        super(context, resourceID,objects);
        viewID = resourceID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view = LayoutInflater.from(getContext()).inflate(viewID,null);
        TextView textView = view.findViewById(R.id.mission_list_mission_name);
        textView.setText(getItem(position));
        return view;
    }
}
