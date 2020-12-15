package com.e.pkugrouper;

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
import android.widget.TextView;

import com.e.pkugrouper.Models.IMessage;
import com.e.pkugrouper.Models.Message;
import com.e.pkugrouper.Models.User;

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

    private TextView userNameText, userEmailText, userContactText; //userDescriptionText;
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
        //userDescriptionText = v.findViewById(R.id.me_selfDescription);
        userEmailText = v.findViewById(R.id.me_Email);

        logOutButton = v.findViewById(R.id.me_logOut);
        editPasswordButton = v.findViewById(R.id.me_EditPassword);
        editInfoButton = v.findViewById(R.id.me_editSelf);

        new UserLoadTask().execute();

        return v;
    }

    private void userLoadSucceeded(List<IMessage> messages){
        userNameText.setText(GlobalObjects.currentUser.getUserName());
        userContactText.setText(GlobalObjects.currentUser.getTele());
        userEmailText.setText(GlobalObjects.currentUser.getMailBox());

        messageAdapter.reloadData(messages);


        logOutButton.setVisibility(View.GONE);
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
                Intent intent = new Intent(getActivity(),EditMeActivity.class);
                startActivity(intent);
            }
        });

        editPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditPasswordActivity.class);
                startActivity(intent);
            }
        });
    }
    private enum FailCode{
        USERNF,ERROR
    }
    private void userLoadFailed(FailCode failCode){

    }

    private class UserLoadTask extends AsyncTask<Void, Void, Void>{

        List<IMessage> messagelist=new ArrayList<>();
        Boolean isload=Boolean.FALSE;
        FailCode failure;
        @Override
        protected Void doInBackground(Void... voids) {
            GlobalObjects.currentUser=GlobalObjects.userManager.getSelf();
            try{
                messagelist=GlobalObjects.messageManager.getCurrentUserMessages();
                isload=Boolean.TRUE;
            }catch(Exception e){
                String s=e.getMessage();
                if(s.equals("currentUser is null")||s.equals("User is not found!")){
                    failure= FailCode.USERNF;
                }else{
                    failure=FailCode.ERROR;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(isload){
                userLoadSucceeded(messagelist);
            }else{
                userLoadFailed(failure);
            }

        }
    }
}
