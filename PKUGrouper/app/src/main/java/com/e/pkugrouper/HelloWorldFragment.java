package com.e.pkugrouper;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ViewFlipper;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HelloWorldFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HelloWorldFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button helloButton;
    private TabLayout logInRegisterTab;
    private ViewPager2 viewPager;
    private LogInRegisterAdpater logInRegisterAdpater;

    private View logoPage;

    public HelloWorldFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HelloWorldFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HelloWorldFragment newInstance(String param1, String param2) {
        Log.e("new instance","new instance");
        HelloWorldFragment fragment = new HelloWorldFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("hello on created","hello on created");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.e("hello created","hello created");
        View v = inflater.inflate(R.layout.fragment_hello_world, container, false);

        logoPage = v.findViewById(R.id.logInRegister_logoPage);

        viewPager = v.findViewById(R.id.logInRegisterViewPager);
        logInRegisterTab = v.findViewById(R.id.logInRegisterTab);

        final ObjectAnimator anim = ObjectAnimator.ofFloat(logoPage,"alpha",1f,0f)
                .setDuration(500);
        anim.setStartDelay(4000);
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                logoPage.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        anim.start();


        Log.e("setup over","hello created");
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        logInRegisterAdpater = new LogInRegisterAdpater(getActivity());
        viewPager.setAdapter(null);
        viewPager.setAdapter(logInRegisterAdpater);
        new TabLayoutMediator(logInRegisterTab, viewPager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position){
                        switch (position){
                            case 0:
                                tab.setText("登录");
                                break;
                            case 1:
                                tab.setText("注册");
                                break;
                        }

                    }
                }
        ).attach();
    }
}