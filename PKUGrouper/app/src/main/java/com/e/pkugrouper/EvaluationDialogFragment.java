package com.e.pkugrouper;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EvaluationDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EvaluationDialogFragment extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Button confirm,cancel;
    private RatingBar ratingBar;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EvaluationDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EvaluationDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EvaluationDialogFragment newInstance(String param1, String param2) {
        EvaluationDialogFragment fragment = new EvaluationDialogFragment();
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
        View v = inflater.inflate(R.layout.fragment_evaluation_dialog, container, false);
        confirm = v.findViewById(R.id.EvaluationDialog_confirm);
        cancel = v.findViewById(R.id.EvaluationDialog_cancel);
        ratingBar = v.findViewById(R.id.EvaluationDialog_Rating);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EvaluationTask().execute((double)(ratingBar.getRating()*2));
            }
        });
        return v;
    }

    private void evaluateSucceeded(){
        Toast.makeText(getActivity(),"评价成功",2).show();
        ((UserDetailActivity)getActivity()).OnDialogCompleted();
        dismiss();
    }

    private void evaluateFailed(){
        Toast.makeText(getActivity(),"评价失败",2).show();
        ((UserDetailActivity)getActivity()).OnDialogCompleted();
        dismiss();
    }

    private class EvaluationTask extends AsyncTask<Double,Void,Void>{

        @Override
        protected Void doInBackground(Double... doubles) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            evaluateSucceeded();
        }
    }
}