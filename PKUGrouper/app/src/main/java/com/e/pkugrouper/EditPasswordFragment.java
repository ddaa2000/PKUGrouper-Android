package com.e.pkugrouper;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.e.pkugrouper.Managers.RSAUtils;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditPasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditPasswordFragment extends DialogFragment {

    private TextInputEditText oldPasswordText, newPasswordText, confirmPasswordText;
    private Button confirmButton,cancelButton;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditPasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditPasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditPasswordFragment newInstance(String param1, String param2) {
        EditPasswordFragment fragment = new EditPasswordFragment();
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

        View v = inflater.inflate(R.layout.fragment_edit_password, container, false);
        oldPasswordText = v.findViewById(R.id.editPassword_oldPassword);
        newPasswordText = v.findViewById(R.id.editPassword_newPassword);
        confirmPasswordText = v.findViewById(R.id.editPassword_newPasswordConfirm);
        confirmButton = v.findViewById(R.id.editPassword_confirm);
        cancelButton = v.findViewById(R.id.editPassword_cancel);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UserEditPasswordTask().execute(oldPasswordText.getText().toString(),
                        newPasswordText.getText().toString());
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

    private void userEditPasswordSucceeded(){
        Toast.makeText(getContext(),"修改成功",2).show();
        dismiss();
    }

    private enum FailCode{
        OLDPWRONG,OLDNEWSAME,USERNF,INVALIDPW,ERROR
    }
    private void userEditPasswordFailed(FailCode failCode){

    }


    private class UserEditPasswordTask extends AsyncTask<String, Void, Void> {

        Boolean ischange=Boolean.FALSE;
        FailCode failure;
        /**
         *
         * @param strings 依次是 旧密码，新密码
         * @return
         */
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected Void doInBackground(String... strings) {
            String oldpassword=strings[0];
            String newpassword=strings[1];
            String savepassword=new RSAUtils().decrypto(GlobalObjects.currentUser.getPassword());
            if(savepassword.equals(oldpassword)){
                if(newpassword.equals(oldpassword)){
                    failure= FailCode.OLDNEWSAME;
                }else{
                    try{
                        ischange=GlobalObjects.userManager.changePassword(newpassword);
                    }catch(Exception e){
                        String s=e.getMessage();
                        if(s.equals("User is null")||s.equals("User is not found!")){
                            failure= FailCode.USERNF;
                        }else if(s.equals("New password is invalid!")){
                            failure= FailCode.INVALIDPW;
                        }else{
                            failure= FailCode.ERROR;
                        }
                    }

                }

            }else{
                failure= FailCode.OLDPWRONG;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(ischange){
                userEditPasswordSucceeded();
            }else{
                userEditPasswordFailed(failure);
            }

        }
    }

}