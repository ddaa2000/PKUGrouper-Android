package com.e.pkugrouper;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.e.pkugrouper.Models.IMission;
import com.e.pkugrouper.Models.IUser;
import com.e.pkugrouper.Models.Mission;
import com.e.pkugrouper.Models.User;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

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

    private TextView missionTitleText, missionContentText, missionStatusText;

    private Button missionStartOrStopButton, missionDeleteButton;
//    private Button missionEditButton;


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
            members.add(new User());
            applicants.add(new User());
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

        missionTitleText = v.findViewById(R.id.missionManage_title);
        missionContentText = v.findViewById(R.id.missionManage_description);
        missionStatusText = v.findViewById(R.id.missionManage_missionStatus);

        missionTitleText.setText(GlobalObjects.currentMission.getTitle());
        missionContentText.setText(GlobalObjects.currentMission.getContent());

//        missionEditButton = v.findViewById(R.id.missionManage_editMission);
        missionDeleteButton = v.findViewById(R.id.missionManage_deleteMission);
        missionStartOrStopButton = v.findViewById(R.id.missionManage_startOrStopMission);


        missionStartOrStopButton.setText("开始");
        missionDeleteButton.setText("删除");
        missionDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialAlertDialogBuilder(getContext()).setTitle("警告")
                        .setMessage("这将删除整个任务，此操作无法撤销")
                        .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new MissionDelete().execute();
                                dialog.cancel();
                            }
                        })
                        .show();
            }
        });


        new MissionLoadTask().execute();

        return v;
    }

    private void missionLoadSucceeded(List<IUser> members,@Nullable List<IUser> applicants){
        missionTitleText.setText(GlobalObjects.currentMission.getTitle());
        missionContentText.setText(GlobalObjects.currentMission.getContent());
        missionStartOrStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(GlobalObjects.currentMission.getState()=="start"){
                    new MaterialAlertDialogBuilder(getContext()).setTitle("开始任务")
                            .setMessage("任务开始后，就不可以修改任务成员了，确认要开始吗？")
                            .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    new MissionStart().execute();
                                    dialog.cancel();
                                }
                            })
                            .show();
                    //new MissionStart().execute();
                }
                else{
                    new MaterialAlertDialogBuilder(getContext()).setTitle("结束任务")
                            .setMessage("确认任务已经完成，可以结束？")
                            .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    new MissionStop().execute();
                                    dialog.cancel();
                                }
                            })
                            .show();
                }
            }
        });
        missionStartOrStopButton.setText("开始");



    }


    private enum FailCode{
        SERVER_ERROR,
        TIME_OUT,
        NO_LONGER_IN_MISSION,
        USERNF,
        MISSIONNF,
        MISSIONID,
        MISSIONSF,
        MISSIONFF,
        DELETEFB
    }

    private void missionLoadFailed(FailCode failCode){

    }

    private void missionStartSucceeded(){

        missionStartOrStopButton.setText("完成");
    }

    private void missionStartFailed(FailCode failCode){

    }

    private void missionStopSucceeded(){

    }

    private void missionDeleteFailed(FailCode failCode){

    }

    private void missionDeleteSucceeded(){
        getActivity().onBackPressed();
    }

    private void missionStopFailed(FailCode failCode){

    }



    private class MissionLoadTask extends AsyncTask<Void, Void, Void>{
        private List<IUser> member=new ArrayList<IUser>() ;
        private List<IUser> applicant=new ArrayList<IUser>() ;
        private IMission mission=new Mission();
        Boolean isload=Boolean.FALSE;
        FailCode failure;
        @Override
        protected Void doInBackground(Void... voids) {
            try{
                mission=GlobalObjects.currentMission;
                GlobalObjects.currentMission=GlobalObjects.missionManager.findMissionByID(mission.getID());
                int userid=GlobalObjects.currentUser.getUserID();
                if(userid==GlobalObjects.currentMission.getPublisher()){
                    List<Integer> list1=GlobalObjects.currentMission.getMemberIDs();
                    List<Integer> list2=GlobalObjects.currentMission.getApplicantIDs();
                    int missionid=GlobalObjects.currentMission.getID();
                    for(Integer i:list1){
                        member.add(GlobalObjects.userManager.findMemberByID(missionid,i));
                    }
                    for(Integer i:list2){
                        applicant.add(GlobalObjects.userManager.findMemberByID(missionid,i));
                    }
                    isload=Boolean.TRUE;
                }
            }catch(Exception e){
                String s=e.getMessage();
                if(s.equals("User is not found!")||s.equals("currentUser is null!")){
                    failure= FailCode.USERNF;
                }else if(s.equals("mission is not found!")){
                    failure= FailCode.MISSIONNF;
                }else{
                    failure= FailCode.MISSIONID;
                }
            }
            

            //这里应该从服务器再次获取当前的任务，以防任务发生了变化，当前的任务在GlobalObjects中，新获取的任务应该覆盖GlobalObjects中的currentMission
            //同时，应当返回所有的成员和申请（当当前用户是当前任务的管理员时）的信息


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(isload){
                missionLoadSucceeded(member,applicant);
            }else{
                missionLoadFailed(failure);
            }

        }
    }

    private class MissionStart extends AsyncTask<Void, Void, Void>{

        Boolean isstart=Boolean.FALSE;
        FailCode failure;
        @Override
        protected Void doInBackground(Void... voids) {
            int missionid=GlobalObjects.currentMission.getID();
            try{
                GlobalObjects.missionManager.start(missionid);
                isstart=Boolean.TRUE;
            }catch (Exception e){
                String s=e.getMessage();
                if(s.equals("User is not found!")||s.equals("currentUser is null!")){
                    failure= FailCode.USERNF;
                }else if(s.equals("mission is not found!")){
                    failure= FailCode.MISSIONNF;
                }else if(s.equals("missionID should be greater than 0!")){
                    failure= FailCode.MISSIONID;
                }else{
                    failure=FailCode.MISSIONSF;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(isstart){
                missionStartSucceeded();
            }else{
                missionStartFailed(failure);
            }

        }
    }

    private class MissionStop extends AsyncTask<Void, Void, Void>{

        Boolean isstop=Boolean.FALSE;
        FailCode failure;
        @Override
        protected Void doInBackground(Void... voids) {
            int missionid=GlobalObjects.currentMission.getID();
            try{
                GlobalObjects.missionManager.finish(missionid);
                isstop=Boolean.TRUE;
            }catch (Exception e){
                String s=e.getMessage();
                if(s.equals("User is not found!")||s.equals("currentUser is null!")){
                    failure= FailCode.USERNF;
                }else if(s.equals("mission is not found!")){
                    failure= FailCode.MISSIONNF;
                }else if(s.equals("missionID should be greater than 0!")){
                    failure= FailCode.MISSIONID;
                }else{
                    failure=FailCode.MISSIONFF;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(isstop){
                missionStopSucceeded();
            }else{
                missionStopFailed(failure);
            }
        }
    }

    private class MissionDelete extends AsyncTask<Void, Void, Void>{

        Boolean isdelete=Boolean.FALSE;
        FailCode failure;
        @Override
        protected Void doInBackground(Void... voids) {
            int missionid=GlobalObjects.currentMission.getID();
            try{
                GlobalObjects.missionManager.deleteMission(missionid);
                isdelete=Boolean.TRUE;
            }catch (Exception e){
                String s=e.getMessage();
                if(s.equals("User is not found!")||s.equals("currentUser is null!")){
                    failure= FailCode.USERNF;
                }else if(s.equals("mission is not found!")){
                    failure= FailCode.MISSIONNF;
                }else if(s.equals("missionID should be greater than 0!")){
                    failure= FailCode.MISSIONID;
                }else{
                    failure=FailCode.DELETEFB;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(isdelete){
                missionDeleteSucceeded();
            }else{
                missionDeleteFailed(failure);
            }
        }
    }


}
