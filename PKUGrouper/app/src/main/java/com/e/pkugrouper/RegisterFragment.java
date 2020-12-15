package com.e.pkugrouper;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.e.pkugrouper.Models.IUser;
import com.e.pkugrouper.Models.User;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button registerButton,getVerificationButton;
    private TextInputEditText userNameText, passwordText, passwordDoubleText, verificationText, mailText;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
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
        View v = inflater.inflate(R.layout.fragment_register, container, false);

        getVerificationButton = v.findViewById(R.id.registerGetVerificationButton);
        registerButton = v.findViewById(R.id.registerButton);

        userNameText = v.findViewById(R.id.registerUserNameEditText);
        verificationText = v.findViewById(R.id.registerVerificationEditText);
        passwordText = v.findViewById(R.id.registerPasswordEditText);
        passwordDoubleText = v.findViewById(R.id.registerPasswordDoubleCheckEditText);
        mailText = v.findViewById(R.id.registerEmailEditText);

        getVerificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetVerificationTask().execute(mailText.getText().toString());
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                if(!passwordDoubleText.getText().toString().equals(passwordText.getText().toString())){
                    Toast.makeText(getActivity(),"两次输入密码不同",2).show();
                    return;
                }
                RegisterParams params = new RegisterParams();

                params.userName = userNameText.getText().toString();
                params.mailNum = mailText.getText().toString();
                params.password = passwordText.getText().toString();
                params.verificationCode = verificationText.getText().toString();

                new RegisterTask().execute(params);


            }
        });
        return v;
    }

    private void registerSucceeded(){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        Fragment fragment = new MainViewFragment();
        fm.beginTransaction().replace(R.id.main_frame,fragment).commit();
    }

    private void getVerificationCodeSucceeded(){
        Toast.makeText(getActivity(),"获取成功",2).show();
    }



    private enum FailCode{
        TIME_EXCEEDED,WRONG_VERIFICATION,UNKNOWN_FAILURE,SERVER_FAILURE,MAIL_EXIST
    }

    private void registerFailed(FailCode failCode){
        Log.e("failed","failed"+failCode);
    }

    private void getVerificationFailed(FailCode failCode){
        switch(failCode){
            case MAIL_EXIST:
                Toast.makeText(getActivity(),"该邮箱已经被注册",2).show();
                break;
            case TIME_EXCEEDED:
                Toast.makeText(getActivity(),"获取验证码超时",2).show();
                break;
            case UNKNOWN_FAILURE:
                Toast.makeText(getActivity(),"未知错误",2).show();
                break;
        }
    }

    private class RegisterParams{
        public String userName, mailNum, verificationCode, password;
    }


    private class RegisterTask extends AsyncTask<RegisterParams,Void,Void>{

        private IUser currentUser = new User();

        Boolean isRegister=Boolean.FALSE;
        FailCode failureType;
        @Override
        protected Void doInBackground(RegisterParams...params) {
            RegisterParams param=params[0];
            currentUser.setUserName(param.userName);
            currentUser.setMailBox(param.mailNum);
            currentUser.setPassword(param.password);
            Log.e("doInBackground","doInBackground");
            try{
                GlobalObjects.userManager.userRegister(currentUser,param.verificationCode);
                GlobalObjects.currentUser = GlobalObjects.userManager.getSelf();
                Log.e("doInBackground","doInBackgroundOver");
                isRegister=Boolean.TRUE;
            }catch (Exception e) {
                Log.e("doInBackground","doInBackgroundException");
                String s=e.getMessage();
                if(s.equals("Register is bad request!")){
                    failureType=FailCode.SERVER_FAILURE;
                }else{
                    failureType=FailCode.UNKNOWN_FAILURE;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(isRegister){
                registerSucceeded();
            }
            else{
                registerFailed(failureType);
            }
        }
    }

    private class GetVerificationTask extends AsyncTask<String,Void,Void>{

        Boolean isMail=Boolean.FALSE;
        FailCode failureType = FailCode.UNKNOWN_FAILURE;
        @Override
        protected Void doInBackground(String... mail) {
            String mailbox=mail[0];
            try{
                boolean result = GlobalObjects.userManager.sendCaptcha(mailbox);
                isMail= result;
            }catch(Exception e){
                failureType=FailCode.TIME_EXCEEDED;
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(isMail){
                getVerificationCodeSucceeded();
            }
            else{
                getVerificationFailed(failureType);
            }
        }
    }

}
