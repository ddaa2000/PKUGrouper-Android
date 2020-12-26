package com.e.pkugrouper;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.e.pkugrouper.Models.IMission;
import com.e.pkugrouper.Models.Mission;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SquareFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SquareFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FloatingActionButton addMissionButton;
    private TabLayout channelTab;
    private SearchView searchView;
    private MissionListFragment squareMissionListFragment;

    private SwipeRefreshLayout swipeRefreshLayout;

    private String presentContent = null;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ChannelSearchTask channelSearchTask;

    private MissionAdapter missionAdapter;
    private RecyclerView missionRecyclerView;
    private List<IMission> missions = new ArrayList<IMission>();


    public Handler mHandler = new Handler() {
        public void handleMessage (Message msg) {//此方法在ui线程运行
            ChannelSearchParams params = new ChannelSearchParams();
            params.keywords = presentContent;
            switch (channelTab.getSelectedTabPosition()){
                case 0:
                    params.channel = Channel.ALL;
                    break;
                case 1:
                    params.channel = Channel.PROFESSIONAL_COURSE;
                    break;
                case 2:
                    params.channel = Channel.GENERAL_COURSE;
                    break;
                case 3:
                    params.channel = Channel.LIFE;
                    break;
            }
            params.reload = false;
            channelSearchTask.cancel(false);
            channelSearchTask = new ChannelSearchTask();
            missionAdapter.setLoadState(missionAdapter.LOADING);
            channelSearchTask.execute(params);
        }
    };

    public SquareFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SquareFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SquareFragment newInstance(String param1, String param2) {
        SquareFragment fragment = new SquareFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_square, container, false);

        channelSearchTask = new ChannelSearchTask();

        channelTab = v.findViewById(R.id.squareChannelTab);
        channelTab.addOnTabSelectedListener(new ChannelChange());

        squareMissionListFragment = (MissionListFragment)getChildFragmentManager().findFragmentById(R.id.squareMissionListFragment);

        swipeRefreshLayout = v.findViewById(R.id.missionList_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.e("refresh","refresh");
                ChannelSearchParams params = new ChannelSearchParams();
                params.keywords = presentContent;
                switch (channelTab.getSelectedTabPosition()){
                    case 0:
                        params.channel = Channel.ALL;
                        break;
                    case 1:
                        params.channel = Channel.PROFESSIONAL_COURSE;
                        break;
                    case 2:
                        params.channel = Channel.GENERAL_COURSE;
                        break;
                    case 3:
                        params.channel = Channel.LIFE;
                        break;
                }
                params.reload = true;
                channelSearchTask.cancel(false);
                channelSearchTask = new ChannelSearchTask();
                channelSearchTask.execute(params);
            }
        });


        searchView = v.findViewById(R.id.squareSearchView);
        addMissionButton = v.findViewById(R.id.add_mission_floatingButton);
        addMissionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                MissionAddFragment fragment = new MissionAddFragment();
                fragment.show(getActivity().getSupportFragmentManager(),"missionAddDialog");
            }
        });

        ChannelSearchParams params = new ChannelSearchParams();
        switch(channelTab.getSelectedTabPosition()){
            case 0:
                params.channel = Channel.ALL;
                break;
            case 1:
                params.channel = Channel.PROFESSIONAL_COURSE;
                break;
            case 2:
                params.channel = Channel.GENERAL_COURSE;
                break;
            case 3:
                params.channel = Channel.LIFE;
                break;
        }
        params.keywords = null;
        params.reload = true;
        channelSearchTask.execute(params);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ChannelSearchParams params = new ChannelSearchParams();
                switch(channelTab.getSelectedTabPosition()){
                    case 0:
                        params.channel = Channel.ALL;
                        break;
                    case 1:
                        params.channel = Channel.PROFESSIONAL_COURSE;
                        break;
                    case 2:
                        params.channel = Channel.GENERAL_COURSE;
                        break;
                    case 3:
                        params.channel = Channel.LIFE;
                        break;
                }
                presentContent = searchView.getQuery().toString();
                params.reload = true;
                params.keywords = presentContent;
                Toast.makeText(getActivity(),presentContent,2).show();
                channelSearchTask.cancel(false);
                channelSearchTask = new ChannelSearchTask();
                channelSearchTask.execute(params);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        missionAdapter = new MissionAdapter(missions,getActivity(),mHandler);
        missionAdapter.setLoadState(missionAdapter.LOADING);
        missionRecyclerView = v.findViewById(R.id.mission_list);
        missionRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        missionRecyclerView.setAdapter(missionAdapter);
//        missionRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
//            @Override
//            public void onLoadMore() {
//                missionAdapter.setLoadState(missionAdapter.LOADING);
//                Toast.makeText(getContext(),"loading",2).show();
//                ChannelSearchParams params = new ChannelSearchParams();
//                params.keywords = presentContent;
//                params.reload = false;
//                switch (channelTab.getSelectedTabPosition()){
//                    case 0:
//                        params.channel = Channel.ALL;
//                        break;
//                    case 1:
//                        params.channel = Channel.PROFESSIONAL_COURSE;
//                        break;
//                    case 2:
//                        params.channel = Channel.GENERAL_COURSE;
//                        break;
//                    case 3:
//                        params.channel = Channel.LIFE;
//                        break;
//                }
//
//                new ChannelSearchTask().execute(params);
//            }
//        });

        return v;
    }

    private class ChannelChange implements TabLayout.OnTabSelectedListener{
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            ChannelSearchParams params = new ChannelSearchParams();
            params.keywords = presentContent;
            switch (tab.getPosition()){
                case 0:
                    params.channel = Channel.ALL;
                    break;
                case 1:
                    params.channel = Channel.PROFESSIONAL_COURSE;
                    break;
                case 2:
                    params.channel = Channel.GENERAL_COURSE;
                    break;
                case 3:
                    params.channel = Channel.LIFE;
                    break;
            }
            params.reload = true;
            channelSearchTask.cancel(false);
            channelSearchTask = new ChannelSearchTask();
            channelSearchTask.execute(params);
            Log.e("sss","selected");
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    }

    private void changeMissionList(List<IMission> missions, boolean reload){
        swipeRefreshLayout.setRefreshing(false);
        if(reload)
            missionAdapter.reloadData(missions);
        else
            missionAdapter.appendData(missions);
    }

    private void changeMissionListfailed(FailCode failCode){
        swipeRefreshLayout.setRefreshing(false);
    }

    private enum Channel{
        ALL, PROFESSIONAL_COURSE, GENERAL_COURSE, LIFE
    }
    private enum FailCode{
        BADREQUEST,USERNF,DCNULL
    }
    private class ChannelSearchParams{
        String keywords;  //注意，可能为null或者""
        Channel channel;
        boolean reload;
    }

    private int presentEnd = 0;
    private final int LOAD_COUNT_ONCE = 7;

    private class ChannelSearchTask extends AsyncTask<ChannelSearchParams, Void, Void>{

        private List<IMission>missionList=new ArrayList<IMission>();
        private FailCode failure;
        private boolean reload;
        Boolean issearch=Boolean.FALSE;
        @Override
        protected Void doInBackground(ChannelSearchParams... channelSearchParams) {
            ChannelSearchParams param=channelSearchParams[0];
            List<String>channels=new ArrayList<String>();

            reload = param.reload;
            switch(param.channel){
                case ALL:
                    channels.add(Mission.CHANNEL_LIFE);
                    channels.add(Mission.CHANNEL_OTHER);
                    channels.add(Mission.CHANNEL_GENERAL);
                    channels.add(Mission.CHANNEL_PROFESSIONAL);
                    break;
                case LIFE:
                    channels.add(Mission.CHANNEL_LIFE);
                    break;
                case GENERAL_COURSE:
                    channels.add(Mission.CHANNEL_GENERAL);
                    break;
                case PROFESSIONAL_COURSE:
                    channels.add(Mission.CHANNEL_PROFESSIONAL);
                    break;
            }
            try{
                if(reload)
                    presentEnd = 0;
                Log.e("presentEnd",""+presentEnd);
                missionList=GlobalObjects.missionManager.
                        findMissionByDescription(param.keywords,channels,presentEnd+1,
                                presentEnd+LOAD_COUNT_ONCE);
                issearch=Boolean.TRUE;
            }catch(Exception e){
                String s=e.getMessage();
                if(s.equals("currentUser is null!")||s.equals("User is not found!")){
                    failure=FailCode.USERNF;
                }else if(s.equals("find mission is bad request!")){
                    failure=FailCode.BADREQUEST;
                }else{
                    failure=FailCode.DCNULL;
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(issearch){
                if(missionList!=null)
                    presentEnd+=missionList.size();
                if(missionList.size()<LOAD_COUNT_ONCE)
                    missionAdapter.setLoadState(missionAdapter.LOADING_END);
                else
                    missionAdapter.setLoadState(missionAdapter.LOADING_COMPLETE);
                changeMissionList(missionList,reload);
            }else{
                changeMissionListfailed(failure);
            }
        }
    }


}
