package com.e.pkugrouper;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.e.pkugrouper.Models.IMission;
import com.e.pkugrouper.Models.Mission;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MissionAddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MissionAddFragment extends DialogFragment {

    private Button missionCreateButton,cancelButton;
    private TextInputEditText missionTitleText, missionContentText;
    //private CheckBox channelProfessional, channelLife, channelGeneral;
    private RadioGroup channelGroup;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MissionAddFragment() {
        // Required empty public constructor


    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MissionAddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MissionAddFragment newInstance(String param1, String param2) {
        MissionAddFragment fragment = new MissionAddFragment();
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
        View v = inflater.inflate(R.layout.fragment_mission_add, container, false);
        missionCreateButton = v.findViewById(R.id.missionAdd_missionCreate);
        missionContentText = v.findViewById(R.id.missionAdd_mssionContent);
        missionTitleText = v.findViewById(R.id.missionAdd_missionTitle);
//        channelProfessional = v.findViewById(R.id.missionAdd_channelProfessional);
//        channelGeneral = v.findViewById(R.id.missionAdd_channelGeneral);
//        channelLife = v.findViewById(R.id.missionAdd_channelLife);
        channelGroup = v.findViewById(R.id.missionAdd_radioGroup);
        cancelButton = v.findViewById(R.id.missionAdd_cancel);

        missionCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IMission newMission = new Mission();
                /*
                这里要改！！！
                 */

                newMission.setChannels(new ArrayList<String>());
                newMission.setApplicationEndTime("2100-12-01 18:23:30");
                newMission.setExecutionStartTime("2200-12-01 18:23:30");
                newMission.setExecutionEndTime("2300-12-01 18:23:30");
                newMission.setTitle(missionTitleText.getText().toString());
                newMission.setContent(missionContentText.getText().toString());

                if(channelGroup.getCheckedRadioButtonId() == R.id.missionAdd_channelProfessional)
                    newMission.getChannels().add(Mission.CHANNEL_PROFESSIONAL);
                if(channelGroup.getCheckedRadioButtonId() == R.id.missionAdd_channelGeneral)
                    newMission.getChannels().add(Mission.CHANNEL_GENERAL);
                if(channelGroup.getCheckedRadioButtonId() == R.id.missionAdd_channelLife)
                    newMission.getChannels().add(Mission.CHANNEL_LIFE);
                if(channelGroup.getCheckedRadioButtonId() == R.id.missionAdd_channelOther)
                    newMission.getChannels().add(Mission.CHANNEL_OTHER);
                new AddMissionTask().execute(newMission);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return v;
    }


    private enum FailCode{
        USERNF,MISSIONNULL,TIMEINVALID
    }

    private void addMissionSucceeded(){
        Toast.makeText(getContext(),"创建成功",2).show();
        dismiss();
    }

    private void addMissionFailed(FailCode failCode){
        Toast.makeText(getContext(),"创建失败",2).show();
        dismiss();
    }

    private class AddMissionTask extends AsyncTask<IMission, Void, Void> {

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
                    failure= FailCode.USERNF;
                }else if(s.equals("this time is invalid to add mission!")){
                    failure= FailCode.TIMEINVALID;
                }else{
                    failure= FailCode.MISSIONNULL;
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