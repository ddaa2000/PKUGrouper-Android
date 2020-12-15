package com.e.pkugrouper;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.e.pkugrouper.Models.IUser;
import com.e.pkugrouper.Models.User;
import com.e.pkugrouper.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginFragment extends Fragment {

    private Button logInButton;
    private TextInputEditText userNameEditText,passwordEditText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        userNameEditText = v.findViewById(R.id.logInUserNameEditText);
        passwordEditText = v.findViewById(R.id.logInPasswordEditText);

        userNameEditText.setText("1800013008@pku.edu.cn");
        passwordEditText.setText("DD2000114%%%");

        logInButton = v.findViewById(R.id.logInButton);
        logInButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                LogInParams params = new LogInParams();
                params.userName = userNameEditText.getText().toString();
                params.password = passwordEditText.getText().toString();
                new LogInTask().execute(params);
            }
        });
        return v;
    }

    /**
     * 登录成功
     */
    private void logInSuccessfully() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        Fragment fragment = new MainViewFragment();
        fm.beginTransaction().replace(R.id.main_frame,fragment).commit();
    }

    private final int TIME_EXCEEDED = 0, WRONG_USER_OR_PASSWORD = 1, UNKNOWN_FAILURE = 2, SERVER_FAILURE = 3;

    /**
     * 登陆失败
     * @param failureType 登陆失败的错误码
     */
    private void logInFailed(int failureType){
        switch (failureType){
            case TIME_EXCEEDED:
                Toast.makeText(getActivity(),"请求超时",2).show();
                break;
            case WRONG_USER_OR_PASSWORD:
                Toast.makeText(getActivity(),"登陆失败，用户名或密码错误",2).show();
                break;
            case SERVER_FAILURE:
                Toast.makeText(getActivity(),"服务器错误",2).show();
                break;
            case UNKNOWN_FAILURE:
                Toast.makeText(getActivity(),"服务器错误",2).show();
                break;


        }

    }


    private class LogInParams{
        public String userName;
        public String password;

    }

    private class LogInTask extends AsyncTask<LogInParams,Void,Void>{



        private IUser currentUser = new User();
        Boolean isLogin=Boolean.FALSE;
        String failureType;
        /**
         * 执行登录请求，如果成功，调用logInSuccessfully，如果失败，调用logInFailed并设置错误码参数
         * @param params
         * @return
         */
        @Override
        protected Void doInBackground(LogInParams ...params) {
            LogInParams param=params[0];
            currentUser.setMailBox(param.userName);
            currentUser.setPassword(param.password);
            try{
                GlobalObjects.currentUser=GlobalObjects.userManager.userLogIn(currentUser);
                GlobalObjects.currentUser = GlobalObjects.userManager.getSelf();
                isLogin=Boolean.TRUE;
            }catch (Exception e) {
                failureType=e.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(isLogin){
                logInSuccessfully();
            }
            else{
                logInFailed(WRONG_USER_OR_PASSWORD);
            }
        }
    }


}
