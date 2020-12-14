package com.e.pkugrouper;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.e.pkugrouper.Models.IMission;
import com.e.pkugrouper.Models.Mission;
import com.e.pkugrouper.Models.TestMission;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MissionListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MissionListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private List<String> stringArrayList = new ArrayList<String>();
    private MissionAdapter missionAdapter;
    private ListView listView;
    private RecyclerView missionRecyclerView;
    private List<IMission> missions = new ArrayList<IMission>();


    public Handler mHandler = new Handler() {
        public void handleMessage (Message msg) {//此方法在ui线程运行
            setMissions((List<IMission>)msg.obj);
        }
    };

    public void setMissions(List<IMission> newMissions){
        missionAdapter.reloadData(newMissions);
    }

    public MissionListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MissionListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MissionListFragment newInstance(String param1, String param2) {
        MissionListFragment fragment = new MissionListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("list added","list added");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.e("list added","list added");

        View v = inflater.inflate(R.layout.fragment_mission_list, container, false);
        for(int i = 0;i<5;i++){
            missions.add(new TestMission());
        }
        missionAdapter = new MissionAdapter(missions,getActivity());
        missionRecyclerView = v.findViewById(R.id.mission_list);
        missionRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        missionRecyclerView.setAdapter(missionAdapter);
        for(int i = 0;i<5;i++){
            missions.add(new TestMission());
        }
        //listView = v.findViewById(R.id.mission_list);
        //listView.setAdapter(missionAdapter);
        Log.e("list added","list added");
        return v;
    }
}