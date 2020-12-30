package com.e.pkugrouper;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.e.pkugrouper.Models.IMessage;
import com.e.pkugrouper.Models.Message;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReportFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReportFragment extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private Button commit,cancel;
    private TextInputEditText reportContent;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ReportFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReportFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReportFragment newInstance(String param1, String param2) {
        ReportFragment fragment = new ReportFragment();
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
        View v = inflater.inflate(R.layout.fragment_report, container, false);
        commit = v.findViewById(R.id.report_commit);
        cancel = v.findViewById(R.id.report_cancel);
        reportContent = v.findViewById(R.id.report_content);

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ReportTask().execute(reportContent.getText().toString());
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return v;
    }

    private void reportSucceeded(){
        Toast.makeText(getContext(),"举报成功",2).show();
        dismiss();
    }
    private void reportFailed(){
        Toast.makeText(getContext(),"举报失败",2).show();
        dismiss();
    }
    private class ReportTask extends AsyncTask<String,Void,Void>{
        Boolean isreport=Boolean.FALSE;
        IMessage message=new Message();
        @Override
        protected Void doInBackground(String... strings) {
            String content=strings[0];
            message.setType(Message.TYPE_REPORT);
            message.setMessageContent(content);
            message.setPublisherID(GlobalObjects.currentUser.getUserID());
            message.setReporteeID(GlobalObjects.currentMember.getUserID());
            List<Integer> rep=new ArrayList<Integer>();
            rep.add(1);
            message.setRecipientIDs(rep);
            message.setTimeStamp(String.valueOf(System.currentTimeMillis()));
            try{
                isreport=GlobalObjects.messageManager.report(message);
            }catch(Exception e){
                String s=e.getMessage();
                if(s.equals("currentUser is null!")){

                }else if(s.equals("You should send a Report!")){

                }else{

                }
                System.out.println(s);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(isreport){
                reportSucceeded();
            }else{
                reportFailed();
            }
        }
    }
}