package com.e.pkugrouper;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
        for(int i = 0;i<40;i++){
            stringArrayList.add("Mission "+i);
        }
        missionAdapter = new MissionAdapter(getActivity(),R.layout.mission_item,stringArrayList);
        listView = v.findViewById(R.id.mission_list);
        listView.setAdapter(missionAdapter);
        Log.e("list added","list added");
        return v;
    }
}