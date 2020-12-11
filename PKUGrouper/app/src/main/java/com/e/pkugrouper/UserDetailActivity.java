package com.e.pkugrouper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.e.pkugrouper.Managers.IHttpManager;
import com.e.pkugrouper.Models.CommonUser;
import com.e.pkugrouper.Models.Evaluation;
import com.e.pkugrouper.Models.IEvaluation;
import com.e.pkugrouper.Managers.HttpManager;
import com.e.pkugrouper.Models.IMission;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.dialog.MaterialDialogs;

import java.util.Arrays;
import java.util.List;

public class UserDetailActivity extends AppCompatActivity {

    private TextView userNameText, userEmailText, userMissionTotalText, userEvaluationText;
    private Button kickOrAcceptButton,refuseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        userNameText = findViewById(R.id.userDetail_memberName);
        userEmailText = findViewById(R.id.userDetail_email);
        userEvaluationText = findViewById(R.id.userDetail_evaluation);
        userMissionTotalText = findViewById(R.id.userDeatail_missionTotal);
        kickOrAcceptButton = findViewById(R.id.userDetail_kickOrAccept);
        refuseButton = findViewById(R.id.userDetail_refuseApplicant);

        new UserDetailLoadTask().execute();


    }

    private void userDetailLoadSucceeded(String evaluationAverage, String missionTotal, boolean isApplicant){
        userNameText.setText(GlobalObjects.currentMember.getUserName());
        userEmailText.setText(GlobalObjects.currentMember.getMailBox());

        userEvaluationText.setText(evaluationAverage);
        userMissionTotalText.setText(missionTotal);
        if(isApplicant){
            kickOrAcceptButton.setText("同意申请");
            kickOrAcceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AcceptApplicantTask().execute();
                }
            });
            refuseButton.setText("拒绝申请");
            refuseButton.setVisibility(View.VISIBLE);
            refuseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new RefuseApplicantTask().execute();
                }
            });
        }
        else{
            kickOrAcceptButton.setText("踢出成员");
            kickOrAcceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new KickMemberTask().execute();
                }
            });
            refuseButton.setVisibility(View.GONE);
        }
    }

    private enum FailCode{
        REJECTFB,
        KICKFB,
        ACCEPTFB,
        APPLICANTNF,
        APPLICANTID,
        USERNF,
        MISSIONNF,
        MISSIONID,
        MISSIONSF,
        MISSIONFF,
        DELETEFB
    }
    private void acceptSucceeded(){
        new MaterialAlertDialogBuilder(this).setTitle("成功")
                .setMessage("已同意申请")
                .setPositiveButton("完成", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onBackPressed();
                        dialog.cancel();
                    }
                })
                .show();
    }
    private void acceptFailed(FailCode failCode){

    }
    private void refuseSucceeded(){
        new MaterialAlertDialogBuilder(this).setTitle("成功")
                .setMessage("已拒绝申请")
                .setPositiveButton("完成", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onBackPressed();
                        dialog.cancel();
                    }
                })
                .show();
    }
    private void refuseFailed(FailCode failCode){

    }
    private void kickSucceeded(){
        new MaterialAlertDialogBuilder(this).setTitle("成功")
                .setMessage("已踢出成员")
                .setPositiveButton("完成", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onBackPressed();
                        dialog.cancel();
                    }
                })
                .show();
    }
    private void kickFailed(FailCode failCode){

    }

    private class UserDetailLoadTask extends AsyncTask<Void, Void, Void>{

        String averagescore;
        String missiontotal;
        double average=0.0;
        Boolean isapplicant;
        private HttpManager http;
        @Override
        protected Void doInBackground(Void... voids) {
            try{
                GlobalObjects.currentMission=GlobalObjects.missionManager.findMissionByID(GlobalObjects.currentMission.getID());
                GlobalObjects.currentUser=GlobalObjects.userManager.getSelf();
                int memberid=GlobalObjects.currentMember.getUserID();
                List<Integer> list=GlobalObjects.currentMission.getApplicantIDs();
                if(list.contains(memberid)){
                    isapplicant=Boolean.TRUE;
                }else{
                    isapplicant=Boolean.FALSE;
                }
                List<Integer> evaluationlist=GlobalObjects.currentMember.getEvaluationIDs();
                String url = "/user/evaluation";
                for(Integer evaluationID:evaluationlist){
                    List<String> parameters = Arrays.asList(String.valueOf(evaluationID));
                    JSONObject request_body = new JSONObject();
                    request_body.put("senderID",GlobalObjects.currentMember.getUserID());
                    request_body.put("passwordAfterRSA", GlobalObjects.currentMember.getPassword());

                    String evaluation_JSON = http.httpGet(url,parameters,request_body.toJSONString());

                    //获取失败
                    if(evaluation_JSON.equals("\"evaluatee Not Found\"")){
                        throw new RuntimeException("evaluation is not found!");
                    }

                    IEvaluation evaluation = new Evaluation();
                    evaluation.loadFromJSON(evaluation_JSON);
                    average+=evaluation.getScore();
                }
                if(evaluationlist.size()==0){
                    averagescore="暂无";
                }else{
                    average=average/evaluationlist.size();
                    averagescore=String.valueOf(average);
                }
                int length=evaluationlist.size();
                missiontotal=String.valueOf(length);
            }catch(Exception e){
                String s=e.getMessage();
                System.out.println(s);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            userDetailLoadSucceeded(averagescore,missiontotal,isapplicant);
        }
    }

    private class AcceptApplicantTask extends AsyncTask<Void, Void, Void>{

        Boolean isaccept=Boolean.FALSE;
        FailCode failure;
        @Override
        protected Void doInBackground(Void... voids) {
            int memberid=GlobalObjects.currentMember.getUserID();
            int missionid=GlobalObjects.currentMission.getID();
            try{
                isaccept=GlobalObjects.missionManager.accept(missionid,memberid);
            }catch(Exception e){
                String s=e.getMessage();
                if(s.equals("User is not found!")||s.equals("currentUser is null!")){
                    failure= FailCode.USERNF;
                }else if(s.equals("mission is not found!")){
                    failure= FailCode.MISSIONNF;
                }else if(s.equals("missionID should be greater than 0!")){
                    failure= FailCode.MISSIONID;
                }else if(s.equals("applicant is not found!")){
                    failure= FailCode.APPLICANTNF;
                }else if(s.equals("applicantID should be greater than 0!")) {
                    failure = FailCode.APPLICANTID;
                }else{
                    failure= FailCode.ACCEPTFB;
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            if(isaccept){
                acceptSucceeded();
            }else{
                acceptFailed(failure);
            }

        }
    }
    private class RefuseApplicantTask extends AsyncTask<Void, Void, Void>{

        Boolean isrefuse=Boolean.FALSE;
        FailCode failure;
        @Override
        protected Void doInBackground(Void... voids) {
            int memberid=GlobalObjects.currentMember.getUserID();
            int missionid=GlobalObjects.currentMission.getID();
            try{
                isrefuse=GlobalObjects.missionManager.reject(missionid,memberid);
            }catch(Exception e){
                String s=e.getMessage();
                if(s.equals("User is not found!")||s.equals("currentUser is null!")){
                    failure= FailCode.USERNF;
                }else if(s.equals("mission is not found!")){
                    failure= FailCode.MISSIONNF;
                }else if(s.equals("missionID should be greater than 0!")){
                    failure= FailCode.MISSIONID;
                }else if(s.equals("applicant is not found!")){
                    failure= FailCode.APPLICANTNF;
                }else if(s.equals("applicantID should be greater than 0!")) {
                    failure = FailCode.APPLICANTID;
                }else{
                    failure= FailCode.REJECTFB;
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            if(isrefuse){
                refuseSucceeded();
            }else{
                refuseFailed(failure);
            }
        }
    }
    private class KickMemberTask extends AsyncTask<Void, Void, Void>{

        Boolean iskick=Boolean.FALSE;
        FailCode failure;
        @Override
        protected Void doInBackground(Void... voids) {
            int memberid=GlobalObjects.currentMember.getUserID();
            int missionid=GlobalObjects.currentMission.getID();
            try{
                iskick=GlobalObjects.missionManager.fire(missionid,memberid);
            }catch(Exception e){
                String s=e.getMessage();
                if(s.equals("User is not found!")||s.equals("currentUser is null!")){
                    failure= FailCode.USERNF;
                }else if(s.equals("mission is not found!")){
                    failure= FailCode.MISSIONNF;
                }else if(s.equals("missionID should be greater than 0!")){
                    failure= FailCode.MISSIONID;
                }else if(s.equals("applicant is not found!")){
                    failure= FailCode.APPLICANTNF;
                }else if(s.equals("applicantID should be greater than 0!")) {
                    failure = FailCode.APPLICANTID;
                }else{
                    failure= FailCode.KICKFB;
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            if(iskick){
                kickSucceeded();
            }else{
                kickFailed(failure);
            }
        }
    }
}
