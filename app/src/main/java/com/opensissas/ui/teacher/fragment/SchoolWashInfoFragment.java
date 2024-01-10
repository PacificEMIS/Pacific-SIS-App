package com.opensissas.ui.teacher.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.opensissas.R;


public class SchoolWashInfoFragment extends Fragment {
    String runningWater,mainSourceOfDrinkingWater,currentlyAvailable,handwashingAvailable,soapAndWaterAvailable,hygeneEducation;
    String femaleToiletType,femaleToiletAccessibility;
    String totalFemaleToilets,totalFemaleToiletsUsable;
    String maleToiletType,maleToiletAccessibility;
    String totalMaleToilets,totalMaleToiletsUsable;
    String comonToiletType,commonToiletAccessibility;
    String totalCommonToilets,totalCommonToiletsUsable;




    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_school_wash_info, container, false);
        initView();
        return view;
    }

    private void initView(){
        runningWater=getArguments().getString("runningWater");
        mainSourceOfDrinkingWater=getArguments().getString("mainSourceOfDrinkingWater");
        currentlyAvailable=getArguments().getString("currentlyAvailable");
        handwashingAvailable=getArguments().getString("handwashingAvailable");
        soapAndWaterAvailable=getArguments().getString("soapAndWaterAvailable");
        hygeneEducation=getArguments().getString("hygeneEducation");

        femaleToiletType=getArguments().getString("femaleToiletType");
        femaleToiletAccessibility=getArguments().getString("femaleToiletAccessibility");
        totalFemaleToilets=getArguments().getString("totalFemaleToilets");
        totalFemaleToiletsUsable=getArguments().getString("totalFemaleToiletsUsable");

        maleToiletType=getArguments().getString("maleToiletType");
        maleToiletAccessibility=getArguments().getString("maleToiletAccessibility");
        totalMaleToilets=getArguments().getString("totalMaleToilets");
        totalMaleToiletsUsable=getArguments().getString("totalMaleToiletsUsable");

        comonToiletType=getArguments().getString("comonToiletType");
        commonToiletAccessibility=getArguments().getString("commonToiletAccessibility");
        totalCommonToilets=getArguments().getString("totalCommonToilets");
        totalCommonToiletsUsable=getArguments().getString("totalCommonToiletsUsable");



        TextView tvRunningWater=(TextView)view.findViewById(R.id.tvRunningWater);
        tvRunningWater.setText(runningWater);

        TextView tvMainDrinkingWater=(TextView)view.findViewById(R.id.tvMainDrinkingWater);
        tvMainDrinkingWater.setText(mainSourceOfDrinkingWater);

        TextView tvCurAvailable=(TextView)view.findViewById(R.id.tvCurAvailable);
        tvCurAvailable.setText(currentlyAvailable);

        TextView tvHandwashing=(TextView)view.findViewById(R.id.tvHandwashing);
        tvHandwashing.setText(handwashingAvailable);


        TextView tvSopaWater=(TextView)view.findViewById(R.id.tvSopaWater);
        tvSopaWater.setText(soapAndWaterAvailable);

        TextView tvHygineEducation=(TextView)view.findViewById(R.id.tvHygineEducation);
        tvHygineEducation.setText(handwashingAvailable);


        TextView tvFemaleToiletType=(TextView)view.findViewById(R.id.tvFemaleToiletType);
        tvFemaleToiletType.setText(femaleToiletType);

        TextView tvTotalFemaleToilets=(TextView)view.findViewById(R.id.tvTotalFemaleToilets);
        tvTotalFemaleToilets.setText(totalFemaleToilets);

        TextView tvTotalFemale=(TextView)view.findViewById(R.id.tvTotalFemale);
        tvTotalFemale.setText(totalFemaleToiletsUsable);

        TextView tvFemaleToiletAccessbility=(TextView)view.findViewById(R.id.tvFemaleToiletAccessbility);
        tvFemaleToiletAccessbility.setText(femaleToiletAccessibility);

        TextView tvMaleToiletType=(TextView)view.findViewById(R.id.tvMaleToiletType);
        tvMaleToiletType.setText(maleToiletType);

        TextView tvTotalMaleUsable=(TextView)view.findViewById(R.id.tvTotalMaleUsable);
        tvTotalMaleUsable.setText(totalMaleToiletsUsable);

        TextView tvTotalMaleToilets=(TextView)view.findViewById(R.id.tvTotalMaleToilets);
        tvTotalMaleToilets.setText(totalMaleToilets);

        TextView tvMaleToiletAccessbility=(TextView)view.findViewById(R.id.tvMaleToiletAccessbility);
        tvMaleToiletAccessbility.setText(maleToiletAccessibility);

        TextView tvCommonToiletType=(TextView)view.findViewById(R.id.tvCommonToiletType);
        tvCommonToiletType.setText(comonToiletType);

        TextView tvTotalCommonUsable=(TextView)view.findViewById(R.id.tvTotalCommonUsable);
        tvTotalCommonUsable.setText(totalCommonToiletsUsable);


        TextView tvTotalCommonToilets=(TextView)view.findViewById(R.id.tvTotalCommonToilets);
        tvTotalCommonToilets.setText(totalCommonToilets);


        TextView tvCommonToiletAccessbility=(TextView)view.findViewById(R.id.tvCommonToiletAccessbility);
        tvCommonToiletAccessbility.setText(commonToiletAccessibility);
    }
}