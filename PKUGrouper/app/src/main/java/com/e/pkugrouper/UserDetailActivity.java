package com.e.pkugrouper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.e.pkugrouper.Models.Evaluation;
import com.e.pkugrouper.Models.IEvaluation;
import com.e.pkugrouper.Managers.HttpManager;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserDetailActivity extends AppCompatActivity implements  DialogCompleted {

    private TextView userNameText, userContactText, userEvaluationTotalText, userEvaluationText,
            evaluationText,firstCharacterText;
    private Button kickOrAcceptButton,refuseButton, evaluateButton,reportButton;
    private MaterialCardView manageCard, evaluationCard;
    private RatingBar ratingBar;

    private List<View> contents;
    private List<ProgressBar> progressBars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        userNameText = findViewById(R.id.userDetail_memberName);
        userContactText = findViewById(R.id.userDetail_email);
        userEvaluationText = findViewById(R.id.userDetail_evaluation);
        userEvaluationTotalText = findViewById(R.id.userDeatail_evaluationTotal);
        kickOrAcceptButton = findViewById(R.id.userDetail_kickOrAccept);
        refuseButton = findViewById(R.id.userDetail_refuseApplicant);
        manageCard = findViewById(R.id.userDeatail_manageCard);
        evaluationCard = findViewById(R.id.userDetail_evaluationCard);
        evaluateButton = findViewById(R.id.userDetail_evaluate);

        evaluationText = findViewById(R.id.userDetail_evaluationText);
        ratingBar = findViewById(R.id.userDetail_rating);

        reportButton = findViewById(R.id.userDetail_report);

        firstCharacterText = findViewById(R.id.userDetail_firstCharacter);

        contents = new ArrayList<>();
        progressBars = new ArrayList<>();

        contents.add(findViewById(R.id.userDetail_content1));
        contents.add(findViewById(R.id.userDetail_content2));
        contents.add(findViewById(R.id.userDetail_content3));
        contents.add(findViewById(R.id.userDetail_content4));
        contents.add(findViewById(R.id.userDetail_content5));

        progressBars.add(findViewById(R.id.userDetail_progress1));
        progressBars.add(findViewById(R.id.userDetail_progress2));
        progressBars.add(findViewById(R.id.userDetail_progress3));
        progressBars.add(findViewById(R.id.userDetail_progress4));
        progressBars.add(findViewById(R.id.userDetail_progress5));




//        for(View view : contents){
//            view.setVisibility(View.INVISIBLE);
//        }
//        for(ProgressBar progressBar : progressBars){
//            progressBar.setVisibility(View.VISIBLE);
//        }
//
//        new UserDetailLoadTask().execute();


    }

    @Override
    protected void onResume() {
        super.onResume();

        evaluationCard.setVisibility(View.GONE);
        if(!GlobalObjects.currentMission.hasPublisher(GlobalObjects.currentUser)
                || GlobalObjects.currentMission.isFinished()){
            manageCard.setVisibility(View.GONE);
        }
        if(GlobalObjects.currentMission.isFinished()){
            evaluationCard.setVisibility(View.VISIBLE);
        }

        for(View view : contents){
            view.setVisibility(View.INVISIBLE);
        }
        for(ProgressBar progressBar : progressBars){
            progressBar.setVisibility(View.VISIBLE);
        }
        new UserDetailLoadTask().execute();


    }

    private void userDetailLoadSucceeded(String evaluationAverage, String missionTotal, boolean isApplicant,
                                         IEvaluation evaluation){

        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

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

        String fisrtCharacter = ""+GlobalObjects.currentMember.getUserName().charAt(0);
        firstCharacterText.setText(fisrtCharacter);
        userNameText.setText(GlobalObjects.currentMember.getUserName());
        if(GlobalObjects.currentMission.hasPublisher(GlobalObjects.currentUser)
                || GlobalObjects.currentMission.hasMember(GlobalObjects.currentUser)) {
            if(GlobalObjects.currentMember.getTele() == null || GlobalObjects.currentMember.getTele().equals(""))
                userContactText.setText("联系方式：" + "该用户还没有设置联系方式");
            else
                userContactText.setText("联系方式：" + GlobalObjects.currentMember.getTele());
        }
        else
            userContactText.setText("联系方式：仅任务成员可见");


        userEvaluationText.setText(evaluationAverage);
        userEvaluationTotalText.setText(missionTotal);

        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                ReportFragment evaluationDialogFragment = new ReportFragment();
                evaluationDialogFragment.show(fragmentManager,"reportDialog");
            }
        });

        if(!GlobalObjects.currentMission.hasPublisher(GlobalObjects.currentUser)
                || GlobalObjects.currentMission.isFinished()){
            kickOrAcceptButton.setVisibility(View.GONE);
            refuseButton.setVisibility(View.GONE);
            manageCard.setVisibility(View.GONE);
        }
        if(GlobalObjects.currentMission.isFinished()){
            evaluationCard.setVisibility(View.VISIBLE);
            if(evaluation!=null) {
                evaluateButton.setClickable(false);
                evaluateButton.setText("已评价");
                ratingBar.setVisibility(View.VISIBLE);
                ratingBar.setRating((float)evaluation.getScore()/2);
                evaluationText.setVisibility(View.GONE);
            }
            else{
                ratingBar.setVisibility(View.GONE);
                evaluationText.setVisibility(View.VISIBLE);
                evaluateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        EvaluationDialogFragment evaluationDialogFragment = new EvaluationDialogFragment();
                        evaluationDialogFragment.show(fragmentManager,"evaluationDialog");
                    }
                });
            }
        }
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

    @Override
    public void OnDialogCompleted() {
        evaluationCard.setVisibility(View.GONE);
        if(!GlobalObjects.currentMission.hasPublisher(GlobalObjects.currentUser)
                || GlobalObjects.currentMission.isFinished()){
            manageCard.setVisibility(View.GONE);
        }
        if(GlobalObjects.currentMission.isFinished()){
            evaluationCard.setVisibility(View.VISIBLE);
        }

        for(View view : contents){
            view.setVisibility(View.INVISIBLE);
        }
        for(ProgressBar progressBar : progressBars){
            progressBar.setVisibility(View.VISIBLE);
        }
        new UserDetailLoadTask().execute();
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
        IEvaluation eval=new Evaluation();
        Boolean isevaluate=Boolean.FALSE;
        private HttpManager http=new HttpManager();
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
                GlobalObjects.currentMember = GlobalObjects.userManager
                        .findMemberByID(GlobalObjects.currentMission.getID(),GlobalObjects.currentMember.getUserID());
                List<Integer> evaluationlist=GlobalObjects.currentMember.getEvaluationIDs();
                String url = "/user/evaluation";
                for(Integer evaluationID:evaluationlist){
                    List<String> parameters = Arrays.asList(String.valueOf(evaluationID));
                    JSONObject request_body = new JSONObject();
                    request_body.put("senderID",GlobalObjects.currentUser.getUserID());
                    request_body.put("passwordAfterRSA", GlobalObjects.currentUser.getPassword());

                    String evaluation_JSON = http.httpGet(url,parameters,request_body.toJSONString());

                    //获取失败
                    if(evaluation_JSON.equals("\"evaluatee Not Found\"")){
                        throw new RuntimeException("evaluation is not found!");
                    }

                    IEvaluation evaluation = new Evaluation();
                    evaluation.loadFromJSON(evaluation_JSON);
                    if(evaluation.getMissionID()==GlobalObjects.currentMission.getID()&&evaluation.getEvaluaterID()==GlobalObjects.currentUser.getUserID()){
                        eval=evaluation;
                        isevaluate=Boolean.TRUE;
                    }
                    average+=evaluation.getScore();
                }
                if(isevaluate==Boolean.FALSE){
                    eval=null;
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
            userDetailLoadSucceeded(averagescore,missiontotal,isapplicant, eval);
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
