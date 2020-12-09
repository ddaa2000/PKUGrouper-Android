package com.e.pkugrouper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.dialog.MaterialDialogs;

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

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            userDetailLoadSucceeded("5.0","25",true);
        }
    }

    private class AcceptApplicantTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            acceptSucceeded();
        }
    }
    private class RefuseApplicantTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            refuseSucceeded();
        }
    }
    private class KickMemberTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            kickSucceeded();
        }
    }
}