package com.e.pkugrouper;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.pkugrouper.Models.IMission;
import com.e.pkugrouper.Models.Mission;

import java.util.List;

public class MissionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<IMission> missions;
    private Activity activity;

    private final int TYPE_ITEM = 1;
    // 脚布局
    private final int TYPE_FOOTER = 2;

    public final int LOADING = 1;
    // 加载完成
    public final int LOADING_COMPLETE = 2;
    // 加载到底
    public final int LOADING_END = 3;

    public int loadState = 2;

    private Handler handler;



    public MissionAdapter(List<IMission> missions, Activity activity, Handler handler){
        this.handler = handler;
        this.missions = missions;
        this.activity = activity;
    }

    public void reloadData(List<IMission> newMissions){
        this.missions.clear();
        if(newMissions!=null)
            this.missions.addAll(newMissions);
        notifyDataSetChanged();
    }
    public void appendData(List<IMission> newMissions){
        if(newMissions!=null)
            this.missions.addAll(newMissions);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为FooterView
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            LayoutInflater layoutInflater = LayoutInflater.from(activity);
            return new MissionHolder(layoutInflater, parent,activity);
        }
        else if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.footer_layout, parent, false);
            return new FootViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MissionHolder){
            IMission mission = missions.get(position);
            ((MissionHolder)holder).bind(mission);
        }

        if (holder instanceof FootViewHolder) {
            FootViewHolder footViewHolder = (FootViewHolder) holder;
            switch (loadState) {
                case LOADING: // 正在加载
                    footViewHolder.loading.setVisibility(View.VISIBLE);
                    footViewHolder.loadMore.setVisibility(View.GONE);
                    footViewHolder.noMore.setVisibility(View.GONE);
                    break;

                case LOADING_COMPLETE: // 加载完成
                    footViewHolder.loading.setVisibility(View.GONE);
                    footViewHolder.loadMore.setVisibility(View.VISIBLE);
                    footViewHolder.noMore.setVisibility(View.GONE);
                    break;

                case LOADING_END: // 加载到底
                    footViewHolder.loading.setVisibility(View.GONE);
                    footViewHolder.loadMore.setVisibility(View.GONE);
                    footViewHolder.noMore.setVisibility(View.VISIBLE);
                    break;

                default:
                    break;
            }
        }

    }

    @Override
    public int getItemCount() {
        return missions.size()+1;
    }

    private class FootViewHolder extends RecyclerView.ViewHolder {

        ProgressBar loading;
        TextView noMore;
        Button loadMore;

        FootViewHolder(View itemView) {
            super(itemView);
            loading = itemView.findViewById(R.id.footer_progressBar);
            noMore = itemView.findViewById(R.id.footer_noMore);
            loadMore = itemView.findViewById(R.id.footer_loadMore);

            loadMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handler.sendMessage(new Message());
                }
            });
            //pbLoading = (ProgressBar) itemView.findViewById(R.id.pb_loading);
            //tvLoading = (TextView) itemView.findViewById(R.id.tv_loading);
            //llEnd = (LinearLayout) itemView.findViewById(R.id.ll_end);
        }
    }

    /**
     * 设置上拉加载状态
     *
     * @param loadState 0.正在加载 1.加载完成 2.加载到底
     */
    public void setLoadState(int loadState) {
        this.loadState = loadState;
        //notifyDataSetChanged();
    }

}
