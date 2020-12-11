package com.e.pkugrouper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.e.pkugrouper.Models.IMission;
import com.e.pkugrouper.Models.Mission;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

public class MissionAddActivity extends AppCompatActivity {

    private Button missionCreateButton;
    private TextInputEditText missionTitleText, missionContentText;
    private CheckBox channelProfessional, channelLife, channelGeneral;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_add);
        missionCreateButton = findViewById(R.id.missionAdd_missionCreate);
        missionContentText = findViewById(R.id.missionAdd_mssionContent);
        missionTitleText = findViewById(R.id.missionAdd_missionTitle);
        channelProfessional = findViewById(R.id.missionAdd_channelProfessional);
        channelGeneral = findViewById(R.id.missionAdd_channelGeneral);
        channelLife = findViewById(R.id.missionAdd_channelLife);

        missionCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IMission newMission = new Mission();
                newMission.setTitle(missionTitleText.getText().toString());
                newMission.setContent(missionContentText.getText().toString());
                new AddMissionTask().execute(newMission);
            }
        });
    }

    private enum FailCode{
        USERNF,MISSIONID,TIMEINVALID
    }

    private void addMissionSucceeded(){
        new MaterialAlertDialogBuilder(this).setTitle("创建成功")
                .setMessage("您已成功创建任务")
                .setPositiveButton("完成", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onBackPressed();
                        dialog.cancel();
                    }
                })
                .show();
    }

    private void addMissionFailed(FailCode failCode){

    }

    private class AddMissionTask extends AsyncTask<IMission, Void, Void>{

        Boolean isadded=Boolean.FALSE;
        FailCode failure;
        @Override
        protected Void doInBackground(IMission... iMissions) {
            IMission mission=new Mission();
            mission=iMissions[0];
            try{
                GlobalObjects.missionManager.addMission(mission);
                isadded=Boolean.TRUE;
            }catch(Exception e){
                String s=e.getMessage();
                if(s.equals("User is not found!")||s.equals("currentUser is null!")){
                    failure=FailCode.USERNF;
                }else if(s.equals("mission is not found!")){
                    failure=FailCode.TIMEINVALID;
                }else{
                    failure=FailCode.MISSIONID;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(isadded){
                addMissionSucceeded();
            }else{
                addMissionFailed(failure);
            }

        }
    }
}
