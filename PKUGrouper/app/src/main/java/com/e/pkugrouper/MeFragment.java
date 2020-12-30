package com.e.pkugrouper;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.e.pkugrouper.Models.IMessage;
import com.e.pkugrouper.Models.IMission;
import com.e.pkugrouper.Models.Message;
import com.e.pkugrouper.Models.Mission;
import com.e.pkugrouper.Models.User;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView messageRecycler;

    private MessageAdapter messageAdapter;
    private List<IMessage> messages;

    private List<MaterialCardView> cards;
    private List<View> contents;
    private List<ProgressBar> progressBars;

    private TextView userNameText, userEmailText, userContactText,userNameSecondText, missionTotal;
    private TextView missionPresent,missionPublished,evaluationText,firstCharacter;
    private RatingBar ratingBar;
    private Button logOutButton, editPasswordButton, editInfoButton;

    public MeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MeFragment newInstance(String param1, String param2) {
        MeFragment fragment = new MeFragment();
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
        View v =  inflater.inflate(R.layout.fragment_me, container, false);

        messageRecycler = v.findViewById(R.id.me_messageRecycler);

        messages = new ArrayList<IMessage>();
        for(int i = 0;i<5;i++){
            messages.add(new Message());
        }
        messageAdapter = new MessageAdapter(messages,getActivity());
        messageRecycler= v.findViewById(R.id.me_messageRecycler);
        messageRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        messageRecycler.setAdapter(messageAdapter);



        userNameText = v.findViewById(R.id.me_userName);
        userContactText = v.findViewById(R.id.me_contact);
        userEmailText = v.findViewById(R.id.me_Email);
        userNameSecondText = v.findViewById(R.id.me_userName2);
        missionTotal = v.findViewById(R.id.me_missionAll);
        missionPresent = v.findViewById(R.id.me_missionPresent);
        missionPublished = v.findViewById(R.id.me_missionPublish);
        evaluationText = v.findViewById(R.id.me_evaluationNum);
        firstCharacter = v.findViewById(R.id.me_firstCharacter);
        ratingBar = v.findViewById(R.id.me_ratingBar);

        logOutButton = v.findViewById(R.id.me_logOut);
        editPasswordButton = v.findViewById(R.id.me_EditPassword);
        editInfoButton = v.findViewById(R.id.me_editSelf);

        cards = new ArrayList<>();
        contents = new ArrayList<>();
        progressBars = new ArrayList<>();
        cards.add(v.findViewById(R.id.me_card1));
        cards.add(v.findViewById(R.id.me_card2));
        cards.add(v.findViewById(R.id.me_card3));
        cards.add(v.findViewById(R.id.me_card4));
        cards.add(v.findViewById(R.id.me_card5));

        contents.add(v.findViewById(R.id.me_content1));
        contents.add(v.findViewById(R.id.me_content2));
        contents.add(v.findViewById(R.id.me_content3));
        contents.add(v.findViewById(R.id.me_content4));
        contents.add(v.findViewById(R.id.me_content5));

        progressBars.add(v.findViewById(R.id.me_progress1));
        progressBars.add(v.findViewById(R.id.me_progress2));
        progressBars.add(v.findViewById(R.id.me_progress3));
        progressBars.add(v.findViewById(R.id.me_progress4));
        progressBars.add(v.findViewById(R.id.me_progress5));



        return v;
    }

    private void userLoadSucceeded(List<IMessage> messages,int missionPresentNum, int missionPublishedNum, double evaluationAverage,int evaluationNumber){

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

        userNameText.setText(GlobalObjects.currentUser.getUserName());
        userContactText.setText(GlobalObjects.currentUser.getTele());
        userEmailText.setText(GlobalObjects.currentUser.getMailBox());

        missionTotal.setText(""+GlobalObjects.currentUser.getMissionIDs().size());
        missionPublished.setText(""+missionPublishedNum);
        missionPresent.setText(""+missionPresentNum);
        userNameSecondText.setText(GlobalObjects.currentUser.getUserName());
        evaluationText.setText(""+evaluationNumber);
        ratingBar.setRating((float)evaluationAverage/2);
        firstCharacter.setText(""+GlobalObjects.currentUser.getUserName().charAt(0));

        messageAdapter.reloadData(messages);


        //logOutButton.setVisibility(View.GONE);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalObjects.currentUser = null;
                FragmentManager fm = getActivity().getSupportFragmentManager();
                Fragment fragment = new HelloWorldFragment();
                fm.beginTransaction().replace(R.id.main_frame,fragment).commit();
            }
        });

        editInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                EditMeFragment fragment = new EditMeFragment();
                fragment.show(fm,"editMeFragment");
            }
        });

        editPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                EditPasswordFragment fragment = new EditPasswordFragment();
                fragment.show(fm,"editPasswordFragment");
            }
        });
    }
    private enum FailCode{
        USERNF,BADREQUEST,MISSIONID,MISSIONNF
    }
    private void userLoadFailed(FailCode failCode){

    }

    private class UserLoadTask extends AsyncTask<Void, Void, Void>{

        List<IMessage> messagelist=new ArrayList<>();
        Boolean isload=Boolean.FALSE;
        int missionPresentNum=0;
        int missionPublishedNum=0;
        double evaluationAverage=0.0;
        int evaluationNumber=0;
        FailCode failure;
        @Override
        protected Void doInBackground(Void... voids) {
            GlobalObjects.currentUser=GlobalObjects.userManager.getSelf();
            try{
                messagelist=GlobalObjects.messageManager.getCurrentUserMessages();
                evaluationAverage=GlobalObjects.currentUser.getAverageScore();
                List<Integer> missionlist=GlobalObjects.currentUser.getMissionIDs();
                int missionidlist[]=new int[missionlist.size()];
                for(int i=0;i<missionlist.size();i++){
                    missionidlist[i]=missionlist.get(i);
                }
                List<IMission> missions=GlobalObjects.missionManager.findMissions(missionidlist);
                evaluationNumber=GlobalObjects.currentUser.getEvaluationIDs().size();
                for(IMission mission:missions){

                    if(!mission.isFinished()){
                        missionPresentNum=missionPresentNum+1;
                    }
                    if(mission.getPublisher()==GlobalObjects.currentUser.getUserID()){
                        missionPublishedNum=missionPublishedNum+1;
                    }
                }
                isload=Boolean.TRUE;
            }catch(Exception e){
                String s=e.getMessage();
                if(s.equals("currentUser is null")||s.equals("User is not found!")){
                    failure= FailCode.USERNF;
                }else if(s.equals("missionID should be greater than 0!")){
                    failure= FailCode.MISSIONID;
                }else if(s.equals("mission is not found!")){
                    failure= FailCode.MISSIONNF;
                } else{
                    failure=FailCode.BADREQUEST;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(isload){
                userLoadSucceeded(messagelist,missionPresentNum,missionPublishedNum,evaluationAverage,evaluationNumber);
            }else{
                userLoadFailed(failure);
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
        for(MaterialCardView card : cards){
            final ObjectAnimator anim1 = ObjectAnimator.ofFloat(card,"scaleX",0f,1f);
            final ObjectAnimator anim2 = ObjectAnimator.ofFloat(card,"scaleY",0f,1f);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(anim1,anim2);
            animatorSet.setDuration((long)(Math.random()*300+150));
            animatorSet.start();
        }

        new UserLoadTask().execute();
    }
}
