package com.e.pkugrouper;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.e.pkugrouper.Models.IMission;
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

    private String presentContent = null;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ChannelSearchTask channelSearchTask;

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


        searchView = v.findViewById(R.id.squareSearchView);
        addMissionButton = v.findViewById(R.id.add_mission_floatingButton);
        addMissionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MissionAddActivity.class);
                startActivity(intent);
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
                params.keywords = presentContent;
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

    private void changeMissionList(List<IMission> missions){
        Message msg = new Message();
        msg.obj = missions;
        squareMissionListFragment.mHandler.sendMessage(msg);
    }

    private enum Channel{
        ALL, PROFESSIONAL_COURSE, GENERAL_COURSE, LIFE
    }
    private class ChannelSearchParams{
        String keywords;  //注意，可能为null或者""
        Channel channel;
    }

    private class ChannelSearchTask extends AsyncTask<ChannelSearchParams, Void, Void>{

        private IMissionManager missionManager;
        private List<IMission>missionList=new ArrayList<IMission>();
        @Override
        protected Void doInBackground(ChannelSearchParams... channelSearchParams) {
            ChannelSearchParams param=channelSearchParams[0];
            String channel=param.channel.toString();
            List<String>channels=new ArrayList<String>();
            channels.add(channel);
            try{
                missionList=missionManager.findMissionByDescription(param.keywords,channels,1,20);
            }catch(Exception e){
                String s=e.getMessage();
                System.out.println(s);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            changeMissionList(missionList);
        }
    }

}
