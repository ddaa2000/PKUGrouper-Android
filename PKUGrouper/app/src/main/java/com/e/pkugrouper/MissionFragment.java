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

import com.e.pkugrouper.Models.IMission;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MissionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MissionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FloatingActionButton addMissionButton;
    private TabLayout statusTab;
    private MissionListFragment missionListFragment;

    private StatusSearchTask statusSearchTask;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MissionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MissionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MissionFragment newInstance(String param1, String param2) {
        MissionFragment fragment = new MissionFragment();
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

       /* FragmentManager fm = getFragmentManager();
        Fragment fragment = new SquareFragment();
        fm.beginTransaction().replace(R.id.mission_list_frame1,fragment).commit();*/

        View v = inflater.inflate(R.layout.fragment_mission, container, false);

        statusTab = v.findViewById(R.id.mission_statusTab);
        statusTab.addOnTabSelectedListener(new ChannelChange());

        missionListFragment = (MissionListFragment)getChildFragmentManager().findFragmentById(R.id.mission_missionListFragment);

        addMissionButton = v.findViewById(R.id.mission_addMissionFloat);
        addMissionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MissionAddActivity.class);
                startActivity(intent);
            }
        });

        ChannelSearchParams params = new ChannelSearchParams();
        switch(statusTab.getSelectedTabPosition()){
            case 0:
                params.channel = Channel.PRESENT;
                break;
            case 1:
                params.channel = Channel.APPLYING;
                break;
            case 2:
                params.channel = Channel.COMPLETED;
                break;
        }
        params.keywords = null;
        statusSearchTask = new StatusSearchTask();
        statusSearchTask.execute(params);


        return v;
    }

    private class ChannelChange implements TabLayout.OnTabSelectedListener{
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            ChannelSearchParams params = new ChannelSearchParams();
            params.keywords = null;
            switch (tab.getPosition()){
                case 0:
                    params.channel = Channel.PRESENT;
                    break;
                case 1:
                    params.channel = Channel.APPLYING;
                    break;
                case 2:
                    params.channel = Channel.COMPLETED;
                    break;
            }
            statusSearchTask.cancel(false);
            statusSearchTask = new StatusSearchTask();
            statusSearchTask.execute(params);
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
        missionListFragment.mHandler.sendMessage(msg);
    }

    private enum Channel{
        PRESENT, APPLYING, COMPLETED
    }
    private class ChannelSearchParams{
        String keywords;  //注意，可能为null或者""
        Channel channel;
    }

    private class StatusSearchTask extends AsyncTask<ChannelSearchParams, Void, Void> {

        private List<IMission>missionList=new ArrayList<IMission>();
        @Override
        protected Void doInBackground(ChannelSearchParams... channelSearchParams) {
            ChannelSearchParams param=channelSearchParams[0];
            String channel=param.channel.toString();
            List<String>channels=new ArrayList<String>();
            channels.add(channel);
            try{
                missionList=GlobalObjects.missionManager.findMissionByDescription(param.keywords,channels,1,20);
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
