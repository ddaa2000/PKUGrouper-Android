package com.e.pkugrouper;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditMeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditMeFragment extends DialogFragment {

    private TextInputEditText userNameText,contactText;
    private Button confirmButton,cancelButton;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditMeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditMeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditMeFragment newInstance(String param1, String param2) {
        EditMeFragment fragment = new EditMeFragment();
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
        View v = inflater.inflate(R.layout.fragment_edit_me, container, false);
        userNameText = v.findViewById(R.id.editMe_userName);
        contactText = v.findViewById(R.id.editMe_contact);
        confirmButton = v.findViewById(R.id.editMe_confirm);
        cancelButton = v.findViewById(R.id.editMe_cancel);

        userNameText.setText(GlobalObjects.currentUser.getUserName());
        contactText.setText(GlobalObjects.currentUser.getTele());

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EditMeFragment.UserEditTask().execute(userNameText.getText().toString(),
                        contactText.getText().toString());
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return v;
    }

    private void userEditSucceeded(){
        Toast.makeText(getContext(),"修改成功",2).show();
        Intent intent = new Intent();
        getTargetFragment().onActivityResult(getTargetRequestCode(),1,intent);
        dismiss();
    }

    private enum FailCode{
        USERNF,ERROR,USERNAMENULL
    }
    private void userEditFailed(FailCode failCode){
        Toast.makeText(getContext(),"修改失败",2).show();
        dismiss();
    }


    private class UserEditTask extends AsyncTask<String, Void, Void> {

        Boolean isedit=Boolean.FALSE;
        FailCode failure;
        /**
         *
         * @param strings 依次是新的 用户名 联系方式
         * @return
         */
        @Override
        protected Void doInBackground(String... strings) {
            String name=strings[0];
            String tel=strings[1];
            try{
                GlobalObjects.userManager.editInfo(name,tel);
                isedit=Boolean.TRUE;
            }catch(Exception e){
                String s=e.getMessage();
                if(s.equals("User is null")||s.equals("User is not found!")){
                    failure= FailCode.USERNF;
                } else if(s.equals("username can't be null!")){
                    failure= FailCode.USERNAMENULL;
                }else{
                    failure= FailCode.ERROR;
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(isedit){
                userEditSucceeded();
            }else{
                userEditFailed(failure);
            }

        }
    }

}