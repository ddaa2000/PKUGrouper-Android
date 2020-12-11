package com.e.pkugrouper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

public class EditPasswordActivity extends AppCompatActivity {

    private TextInputEditText oldPasswordText, newPasswordText, confirmPasswordText;
    private Button confirmButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password_activity);

        oldPasswordText = findViewById(R.id.editPassword_oldPassword);
        newPasswordText = findViewById(R.id.editPassword_newPassword);
        confirmPasswordText = findViewById(R.id.editPassword_newPasswordConfirm);
        confirmButton = findViewById(R.id.editPassword_confirm);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UserEditPasswordTask().execute(oldPasswordText.getText().toString(),
                        newPasswordText.getText().toString());
            }
        });
    }

    private void userEditPasswordSucceeded(){
        new MaterialAlertDialogBuilder(this).setTitle("修改成功")
                .setPositiveButton("完成", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onBackPressed();
                        dialog.cancel();
                    }
                })
                .show();
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
        @Override
        protected Void doInBackground(String... strings) {
            String oldpassword=strings[0];
            String newpassword=strings[1];
            String savepassword=GlobalObjects.currentUser.getPassword();
            if(savepassword.equals(oldpassword)){
                if(newpassword.equals(oldpassword)){
                    failure=FailCode.OLDNEWSAME;
                }else{
                    try{
                        GlobalObjects.userManager.changePassword(newpassword);
                        ischange=Boolean.TRUE;
                    }catch(Exception e){
                        String s=e.getMessage();
                        if(s.equals("User is null")||s.equals("User is not found!")){
                            failure=FailCode.USERNF;
                        }else if(s.equals("New password is invalid!")){
                            failure=FailCode.INVALIDPW;
                        }else{
                            failure=FailCode.ERROR;
                        }
                    }
                    
                }

            }else{
                failure=FailCode.OLDPWRONG;
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
