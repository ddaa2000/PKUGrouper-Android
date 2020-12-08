package com.e.pkugrouper;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.e.pkugrouper.Models.IUser;
import com.e.pkugrouper.Models.TestUser;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MissionManageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MissionManageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView memberRecyclerView, applicantRecyclerView;
    private List<IUser> members = new ArrayList<IUser>(),applicants = new ArrayList<IUser>();
    private UserCardAdapter memberCardAdapter,applicantCardAdapter;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MissionManageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MissionManageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MissionManageFragment newInstance(String param1, String param2) {
        MissionManageFragment fragment = new MissionManageFragment();
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
        View v = inflater.inflate(R.layout.fragment_mission_manage, container, false);
        for(int i = 0;i<20;i++){
            members.add(new TestUser());
            applicants.add(new TestUser());
        }


        memberRecyclerView = v.findViewById(R.id.mission_detail_members_recyclerView);
        memberCardAdapter = new UserCardAdapter(members,getActivity());
        memberRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        memberRecyclerView.setAdapter(memberCardAdapter);
        memberRecyclerView.addItemDecoration(new LineDividerItemDecoration(getContext()));

        applicantRecyclerView = v.findViewById(R.id.mission_detail_applicants_recyclerView);
        applicantCardAdapter = new UserCardAdapter(applicants,getActivity());
        applicantRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        applicantRecyclerView.setAdapter(applicantCardAdapter);
        applicantRecyclerView.addItemDecoration(new LineDividerItemDecoration(getContext()));

        return v;
    }
}