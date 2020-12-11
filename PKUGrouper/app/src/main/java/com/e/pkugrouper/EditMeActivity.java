package com.e.pkugrouper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

public class EditMeActivity extends AppCompatActivity {

    private TextInputEditText userNameText, userDescriptionText,contactText;

    private Button confirmButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_me);


        userNameText = findViewById(R.id.editMe_userName);
        userDescriptionText = findViewById(R.id.editMe_userDescription);
        contactText = findViewById(R.id.editMe_contact);
        confirmButton = findViewById(R.id.editMe_confirm);

        userNameText.setText(GlobalObjects.currentUser.getUserName());
        userDescriptionText.setText("自我介绍暂无");
        contactText.setText(GlobalObjects.currentUser.getTele());

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UserEditTask().execute(userNameText.getText().toString(),
                        userDescriptionText.getText().toString(),
                        contactText.getText().toString());
            }
        });
    }

    private void userEditSucceeded(){
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

    }
    private void userEditFailed(FailCode failCode){

    }


    private class UserEditTask extends AsyncTask<String, Void, Void>{

        /**
         *
         * @param strings 依次是新的 用户名 介绍 联系方式
         * @return
         */
        @Override
        protected Void doInBackground(String... strings) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            userEditSucceeded();
        }
    }
}