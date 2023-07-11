package com.opensis.ui.teacher.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.opensis.R;
import com.opensis.adapter.TeacherGradeAdapter;
import com.opensis.model.TeacherClassesModel;
import com.opensis.model.TeacherGradeModel;
import com.opensis.others.parser.PostJsonDataParser;
import com.opensis.others.utility.Api;
import com.opensis.others.utility.AppData;
import com.opensis.others.utility.Pref;
import com.opensis.ui.common.activity.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class TeacherGradeFragment extends Fragment implements View.OnClickListener {

    View view;
    LinearLayout lnChildFragment;
    TextView tvGradeBook,tvInputGrades;
    int courseSectionID;
    String gradescaletype;
    private FragmentTransaction transaction;
    int courseID;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_teacher_grade, container, false);
        initView();
        return view;
    }


    private void initView(){
        courseSectionID= Integer.parseInt(getArguments().getString("courseSectionID"));
        gradescaletype=getArguments().getString("gradescaletype");
        courseID=getArguments().getInt("courseID");
        lnChildFragment=(LinearLayout) view.findViewById(R.id.lnChildFragment);
        tvInputGrades=(TextView) view.findViewById(R.id.tvInputGrades);
        tvGradeBook=(TextView) view.findViewById(R.id.tvGradeBook);

        viewGradeBookFragment();

        tvInputGrades.setOnClickListener(this);
        tvGradeBook.setOnClickListener(this);
    }

    private void viewGradeBookFragment(){
        Log.d("riku","001");

        GradeBookGradesFragment fragment = new GradeBookGradesFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("courseSectionID", courseSectionID);
        bundle.putString("gradescaletype", gradescaletype);
        fragment.setArguments(bundle);

        transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.lnChildFragment, fragment, "gradebook");
        transaction.commit();

        tvGradeBook.setTextColor(Color.parseColor("#FFFFFFFF"));
        tvInputGrades.setTextColor(Color.parseColor("#094990"));

        tvGradeBook.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.green));
        tvInputGrades.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.white));




    }


    private void viewInputGradeFragment(){
        Log.d("riku","001");

        InputGradeFragment fragment = new InputGradeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("courseSectionID", courseSectionID);
        bundle.putString("gradescaletype", gradescaletype);
        bundle.putInt("courseID",courseID);
        fragment.setArguments(bundle);

        transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.lnChildFragment, fragment, "inputgrade");
        transaction.commit();

        tvGradeBook.setTextColor(Color.parseColor("#094990"));
        tvInputGrades.setTextColor(Color.parseColor("#FFFFFF"));

        tvGradeBook.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.white));
        tvInputGrades.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.green));




    }

    @Override
    public void onClick(View v) {
        if (v==tvGradeBook){
            viewGradeBookFragment();
        }else {
            viewInputGradeFragment();
        }
    }
}