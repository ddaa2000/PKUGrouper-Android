package com.e.pkugrouper;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.e.pkugrouper.Models.IMission;
import com.e.pkugrouper.Models.IUser;
import com.e.pkugrouper.Models.Mission;
import com.e.pkugrouper.Models.User;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;
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

    private List<View> contents;
    private List<ProgressBar> progressBars;


    private MaterialCardView applicantCard;

    private TextView missionTitleText, missionContentText, missionStatusText;

    private Button missionStartOrStopButton, missionDeleteButton;

    private Chip startChip,endChip,createChip;
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
//        for(int i = 0;i<20;i++){
//            members.add(new User());
//            applicants.add(new User());
//        }

        createChip = v.findViewById(R.id.missionManage_createChip);
        startChip = v.findViewById(R.id.missionManage_startChip);
        endChip = v.findViewById(R.id.missionManage_endChip);

        memberRecyclerView = v.findViewById(R.id.mission_detail_members_recyclerView);
        memberCardAdapter = new UserCardAdapter(members,getActivity());
        memberRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        memberRecyclerView.setAdapter(memberCardAdapter);
      //  memberRecyclerView.addItemDecoration(new LineDividerItemDecoration(getContext()));

        applicantRecyclerView = v.findViewById(R.id.mission_detail_applicants_recyclerView);
        applicantCardAdapter = new UserCardAdapter(applicants,getActivity());
        applicantRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        applicantRecyclerView.setAdapter(applicantCardAdapter);
  //      applicantRecyclerView.addItemDecoration(new LineDividerItemDecoration(getContext()));

        applicantCard = v.findViewById(R.id.missionManage_applicantCard);

        missionTitleText = v.findViewById(R.id.missionManage_title);
        missionContentText = v.findViewById(R.id.missionManage_description);
        missionStatusText = v.findViewById(R.id.missionManage_missionStatus);

        missionTitleText.setText(GlobalObjects.currentMission.getTitle());
        missionContentText.setText(GlobalObjects.currentMission.getContent());

//        missionEditButton = v.findViewById(R.id.missionManage_editMission);
        missionDeleteButton = v.findViewById(R.id.missionManage_deleteMission);
        missionStartOrStopButton = v.findViewById(R.id.missionManage_startOrStopMission);


        contents = new ArrayList<>();
        progressBars = new ArrayList<>();

        contents.add(v.findViewById(R.id.missionDetail_content1));
        contents.add(v.findViewById(R.id.missionDetail_content2));
        contents.add(v.findViewById(R.id.missionDetail_content3));
        contents.add(v.findViewById(R.id.missionDetail_content4));

        progressBars.add(v.findViewById(R.id.missionDetail_progress1));
        progressBars.add(v.findViewById(R.id.missionDetail_progress2));
        progressBars.add(v.findViewById(R.id.missionDetail_progress3));
        progressBars.add(v.findViewById(R.id.missionDetail_progress4));



        return v;
    }


    private void missionLoadSucceeded(List<IUser> members,@Nullable List<IUser> applicants){

        startChip.setChipIconVisible(false);
        endChip.setChipIconVisible(false);
        createChip.setChipIconVisible(false);
        if(GlobalObjects.currentMission.isInApplication())
            createChip.setChipIconVisible(true);
        if(GlobalObjects.currentMission.isInExecution()) {
            createChip.setChipIconVisible(true);
            startChip.setChipIconVisible(true);
        }
        if(GlobalObjects.currentMission.isFinished()){
            createChip.setChipIconVisible(true);
            startChip.setChipIconVisible(true);
            endChip.setChipIconVisible(true);
        }
        for(View view : contents){
            view.setAlpha(0f);
            view.setVisibility(View.VISIBLE);
            ObjectAnimator.ofFloat(view,"alpha",0f,1f).setDuration(1000).start();
        }
        for(ProgressBar progressBar : progressBars){
            progressBar.setVisibility(View.VISIBLE);
            final ObjectAnimator anim = ObjectAnimator.ofFloat(progressBar,"alpha",1f,0f).setDuration(1000);
            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    progressBar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            anim.start();
        }

        missionTitleText.setText(GlobalObjects.currentMission.getTitle());
        missionContentText.setText(GlobalObjects.currentMission.getContent());

        if(!GlobalObjects.currentMission.hasPublisher(GlobalObjects.currentUser))
            applicantCard.setVisibility(View.GONE);

        if(GlobalObjects.currentMission.hasPublisher(GlobalObjects.currentUser)){
            if(GlobalObjects.currentMission.isInApplication()){
                missionStatusText.setText("未开始");
                missionStartOrStopButton.setText("开始");
                missionDeleteButton.setVisibility(View.VISIBLE);
            }

            else if(GlobalObjects.currentMission.isInExecution()){
                missionStatusText.setText("进行中");
                missionStartOrStopButton.setText("结束");
                missionDeleteButton.setVisibility(View.GONE);
            }

            else if(GlobalObjects.currentMission.isFinished()){
                missionStatusText.setText("已结束");
                missionStartOrStopButton.setVisibility(View.GONE);
                missionDeleteButton.setVisibility(View.GONE);
            }
        }
        else if(GlobalObjects.currentMission.hasMember(GlobalObjects.currentUser)){
            if(GlobalObjects.currentMission.isInApplication()){
                missionStatusText.setText("未开始");
                missionStartOrStopButton.setText("退出");
                missionDeleteButton.setVisibility(View.GONE);
            }

            else if(GlobalObjects.currentMission.isInExecution()){
                missionStatusText.setText("进行中");
                missionStartOrStopButton.setVisibility(View.GONE);
                missionDeleteButton.setVisibility(View.GONE);
            }

            else if(GlobalObjects.currentMission.isFinished()){
                missionStatusText.setText("已结束");
                missionStartOrStopButton.setVisibility(View.GONE);
                missionDeleteButton.setVisibility(View.GONE);
            }
        }
        else{
            if(GlobalObjects.currentMission.isInApplication()){
                missionStatusText.setText("未开始");
                missionStartOrStopButton.setText("申请加入");
                missionDeleteButton.setVisibility(View.GONE);
            }

            else if(GlobalObjects.currentMission.isInExecution()){
                missionStatusText.setText("进行中");
                missionStartOrStopButton.setVisibility(View.GONE);
                missionDeleteButton.setVisibility(View.GONE);
            }

            else if(GlobalObjects.currentMission.isFinished()){
                missionStatusText.setText("已结束");
                missionStartOrStopButton.setVisibility(View.GONE);
                missionDeleteButton.setVisibility(View.GONE);
            }
            if(GlobalObjects.currentMission.hasApplicant(GlobalObjects.currentUser)){
                missionStartOrStopButton.setText("已申请");
                missionStartOrStopButton.setClickable(false);
            }
        }



        memberCardAdapter.reloadData(members);
        applicantCardAdapter.reloadData(applicants);

        if(GlobalObjects.currentMission.hasPublisher(GlobalObjects.currentUser)){
            missionStartOrStopButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("onclick","onclick");
                    if(GlobalObjects.currentMission.isInApplication()){
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
                    else if(GlobalObjects.currentMission.isInExecution()){
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
        }
        else if(GlobalObjects.currentMission.hasMember(GlobalObjects.currentUser)){
            missionStartOrStopButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new MaterialAlertDialogBuilder(getContext()).setTitle("退出任务")
                            .setMessage("退出任务无法撤销，确认要退出吗？")
                            .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    new MissionQuit().execute();
                                    dialog.cancel();
                                }
                            })
                            .show();
                }
            });
        }
        else{
            missionStartOrStopButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new ApplicationSend().execute();
                }
            });
        }




    }


    private enum FailCode{
        MISSIONJF,
        MISSIONQF,
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

        new MissionLoadTask().execute();
    }

    private void missionStartFailed(FailCode failCode){

    }

    private void missionStopSucceeded(){
        new MissionLoadTask().execute();
    }

    private void missionDeleteFailed(FailCode failCode){

    }

    private void missionDeleteSucceeded(){
        getActivity().onBackPressed();
    }

    private void missionStopFailed(FailCode failCode){

    }
    private void missionQuitFailed(FailCode failCode){

    }

    private void missionQuitSucceeded(){
        getActivity().onBackPressed();
    }

    private void applicationsendFailed(FailCode failCode){

    }

    private void applicationsendSucceeded(){
        Toast.makeText(getActivity(),"申请已发送",2).show();
        new MissionLoadTask().execute();
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
                List<Integer> list1=GlobalObjects.currentMission.getMemberIDs();
                List<Integer> list2=GlobalObjects.currentMission.getApplicantIDs();

                int missionid=GlobalObjects.currentMission.getID();
                if(GlobalObjects.currentMission.hasPublisher(GlobalObjects.currentUser))
                    member.add(GlobalObjects.currentUser);
                else
                    member.add(GlobalObjects.userManager.findMemberByID(missionid,GlobalObjects.currentMission.getPublisher()));
                for(Integer i:list1){
                    member.add(GlobalObjects.userManager.findMemberByID(missionid,i));
                }
                for(Integer i:list2){
                    applicant.add(GlobalObjects.userManager.findMemberByID(missionid,i));
                }

                isload=Boolean.TRUE;
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
                isstart=GlobalObjects.missionManager.start(missionid);
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
                isstop=GlobalObjects.missionManager.finish(missionid);
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
                isdelete=GlobalObjects.missionManager.deleteMission(missionid);
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
    private class MissionQuit extends AsyncTask<Void, Void, Void>{

        Boolean isquit=Boolean.FALSE;
        FailCode failure;
        @Override
        protected Void doInBackground(Void... voids) {
            int missionid=GlobalObjects.currentMission.getID();
            try{
                isquit=GlobalObjects.missionManager.quit(missionid);
            }catch (Exception e){
                String s=e.getMessage();
                if(s.equals("User is not found!")||s.equals("currentUser is null!")){
                    failure= FailCode.USERNF;
                }else if(s.equals("mission is not found!")){
                    failure= FailCode.MISSIONNF;
                }else if(s.equals("missionID should be greater than 0!")){
                    failure= FailCode.MISSIONID;
                }else{
                    failure=FailCode.MISSIONQF;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(isquit){
                missionQuitSucceeded();
            }else{
                missionQuitFailed(failure);
            }

        }
    }

    private class ApplicationSend extends AsyncTask<Void, Void, Void>{

        Boolean issend=Boolean.FALSE;
        FailCode failure;
        @Override
        protected Void doInBackground(Void... voids) {
            int missionid=GlobalObjects.currentMission.getID();
            try{
                issend=GlobalObjects.missionManager.join(missionid);
            }catch (Exception e){
                String s=e.getMessage();
                if(s.equals("User is not found!")||s.equals("currentUser is null!")){
                    failure= FailCode.USERNF;
                }else if(s.equals("mission is not found!")){
                    failure= FailCode.MISSIONNF;
                }else if(s.equals("missionID should be greater than 0!")){
                    failure= FailCode.MISSIONID;
                }else{
                    failure=FailCode.MISSIONJF;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(issend){
                applicationsendSucceeded();
            }else{
                applicationsendFailed(failure);
            }

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        for(View view : contents){
            view.setVisibility(View.INVISIBLE);
        }
        for(ProgressBar progressBar : progressBars){
            progressBar.setVisibility(View.VISIBLE);
        }

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
    }
}
