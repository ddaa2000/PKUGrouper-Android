package com.e.pkugrouper;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainViewFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private BottomNavigationView bottomNavigationView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private int present_item = 0;

    public MainViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainViewFragment newInstance(String param1, String param2) {
        MainViewFragment fragment = new MainViewFragment();
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
        FragmentManager fm = getActivity().getSupportFragmentManager();
        Fragment fragment = new SquareFragment();
        fm.beginTransaction().replace(R.id.page_content_frame,fragment).commit();
        View v = inflater.inflate(R.layout.fragment_main_view, container, false);
        bottomNavigationView = v.findViewById(R.id.bottom_navigation_view);
       // if(bottomNavigationView==null)
         //   return inflater.inflate(R.layout.fragment_main_view, container, false);
        bottomNavigationView.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);

        return v;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.e("test","selected");
        int selected_item = 0;
        switch(item.getItemId()){
            case R.id.page_square:
                selected_item = 0;
                break;
            case R.id.page_mission:
                selected_item = 1;
                break;
            case R.id.page_me:
                selected_item = 2;
                break;
        }
        if(selected_item == present_item)
            return true;
        present_item = selected_item;

        Fragment fragment = null;

        if(present_item == 0){
            fragment = new SquareFragment();
        }
        else if(present_item == 1){
            fragment = new MissionFragment();
        }
        else if(present_item == 2){
            fragment = new MeFragment();
        }

        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.page_content_frame,fragment).commit();

        return true;
    }
}