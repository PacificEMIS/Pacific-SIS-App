package com.opensissasstudent.ui.student.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.opensissasstudent.R;
import com.opensissasstudent.adapter.CalendrEventAdapter;
import com.opensissasstudent.adapter.MyScheduleListAdapter;
import com.opensissasstudent.databinding.FragmentMyScheduleBinding;
import com.opensissasstudent.databinding.FragmentReportCardBinding;
import com.opensissasstudent.model.MArkinPeriodSpinnerModel;
import com.opensissasstudent.model.MyScheduleListModel;
import com.opensissasstudent.model.SpinnerMixedModel;
import com.opensissasstudent.model.SpinnerModel;
import com.opensissasstudent.others.parser.PostJsonDataParser;
import com.opensissasstudent.others.utility.Pref;
import com.opensissasstudent.ui.common.activity.LoginActivity;
import com.opensissasstudent.ui.student.activity.ReportCardDetailsActivity;
import com.opensissasstudent.ui.student.activity.StudentDashboardActivity;
import com.suke.widget.SwitchButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


public class ReportCardFragment extends Fragment implements View.OnClickListener {

    FragmentReportCardBinding binding;
    private View view;
    Pref pref;
    ArrayList<SpinnerMixedModel>macademyYearList=new ArrayList<>();
    ArrayList<String>academyYearList=new ArrayList<>();
    ArrayList<String>strtDateList=new ArrayList<>();

    ArrayList<String>markingperiodList=new ArrayList<>();
    ArrayList<SpinnerModel>mmarkingperiodlist=new ArrayList<>();
    String strtDate;
    String currentDate;
    Calendar  c;
    SimpleDateFormat df;
    int academicyear;
    String periodid;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_report_card,container,false);
        view=binding.getRoot();
        initView();
        onclick();
        return view;
    }


    private void initView(){
        pref=new Pref(getContext());
        c= Calendar.getInstance();
        System.out.println("Current time => " + c);

        df = new SimpleDateFormat("yyyy-MM-dd");
        currentDate = df.format(c.getTime());


        JSONObject obj=new JSONObject();
        JSONObject accessobj=new JSONObject();
        try {
            obj.put("tenantId",pref.getTenatID());
            obj.put("userId",0);
            obj.put("userAccessLog",accessobj);
            obj.put("_tenantName", pref.getTenatName());
            obj.put("_userName", pref.getName());
            obj.put("_token", pref.getToken());
            obj.put("schoolId", pref.getSchoolID());
            obj.put("email", pref.getEmail());
            getRefreshToken(obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        binding.tvReportCard.setOnClickListener(this);

    }

    private void onclick(){
        binding.spSchoolYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                academicyear=macademyYearList.get(position).getItemID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.spMarkingPeriod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                periodid=mmarkingperiodlist.get(position).getItemID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void getRefreshToken(JSONObject jsonObject) {
        String url=pref.getAPI()+"User/RefreshToken";


        new PostJsonDataParser(getContext(), Request.Method.POST, url,jsonObject, true,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("dashboardresponse",response.toString());

                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");
                        if (_failure){

                            Intent intent=new Intent(getContext(), LoginActivity.class);
                            startActivity(intent);

                        }else {
                            String _token=response.optString("_token");
                            pref.saveToken(_token);


                            JSONObject schoolOBJ=new JSONObject();
                            try {

                                schoolOBJ.put("_tenantName",pref.getTenatName());
                                schoolOBJ.put("_userName",pref.getName());
                                schoolOBJ.put("_token",_token);
                                schoolOBJ.put("tenantId",pref.getTenatID());
                                schoolOBJ.put("schoolId",pref.getSchoolID());
                                getAcademyYearList(schoolOBJ);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }






                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }


    public void getAcademyYearList(JSONObject jsonObject) {
        String url=pref.getAPI()+"MarkingPeriod/getAcademicYearList";


        new PostJsonDataParser(getContext(), Request.Method.POST, url,jsonObject, true,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("dashboardresponse",response.toString());
                    academyYearList.clear();
                    macademyYearList.clear();

                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");
                        if (_failure){



                        }else {
                            JSONArray academicYears=response.optJSONArray("academicYears");
                            if (academicYears.length()>0){
                                for (int i=0;i<academicYears.length();i++){
                                    JSONObject academicYearsOBJ=academicYears.optJSONObject(i);
                                    int academyYear= academicYearsOBJ.optInt("academyYear");
                                    String year=academicYearsOBJ.optString("year");
                                    academyYearList.add(year);
                                    String startDate=academicYearsOBJ.optString("startDate");
                                    strtDateList.add(startDate);
                                    String endDate=academicYearsOBJ.optString("endDate");
                                    if (checkDate(startDate.replace("T00:00:00",""),currentDate,endDate.replace("T00:00:00",""))){
                                        strtDate=academicYearsOBJ.optString("startDate");


                                    }

                                    SpinnerMixedModel spModel=new SpinnerMixedModel(year,academyYear);
                                    macademyYearList.add(spModel);
                                }

                                int pos=strtDateList.indexOf(strtDate);



                                ArrayAdapter ad
                                        = new ArrayAdapter(
                                        getContext(),
                                        android.R.layout.simple_spinner_item,
                                        academyYearList);

                                // set simple layout resource file
                                // for each item of spinner
                                ad.setDropDownViewResource(
                                        android.R.layout
                                                .simple_spinner_dropdown_item);

                                // Set the ArrayAdapter (ad) data on the
                                // Spinner which binds data to spinner
                                binding.spSchoolYear.setAdapter(ad);
                                binding.spSchoolYear.setSelection(pos);

                                try {
                                    jsonObject.put("academicYear",pref.getAcademicYear());
                                    jsonObject.put("isReportCard",true);
                                    jsonObject.put("_tenantName",pref.getTenatName());
                                    jsonObject.put("_userName",pref.getName());
                                    jsonObject.put("_token",pref.getToken());
                                    jsonObject.put("_academicYear",pref.getAcademicYear());
                                    jsonObject.put("tenantId",pref.getTenatID());
                                    jsonObject.put("schoolId",pref.getSchoolID());
                                    jsonObject.put("markingPeriodStartDate",pref.getPeriodStartYear());
                                    jsonObject.put("markingPeriodEndDate",pref.getPeriodEndYear());
                                    getPeriodList(jsonObject);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }



                            }
                        }






                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }


    public void getPeriodList(JSONObject jsonObject) {

        String url=pref.getAPI()+"MarkingPeriod/getMarkingPeriodsByCourseSection";


        new PostJsonDataParser(getContext(), Request.Method.POST,url,jsonObject, false,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("dashboardresponse",response.toString());
                    markingperiodList.clear();
                    mmarkingperiodlist.clear();
                    strtDateList.clear();

                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");
                        if (_failure){



                        }else {
                            int academicYear= response.optInt("academicYear");

                            JSONArray getMarkingPeriodView = response.optJSONArray("getMarkingPeriodView");
                            if (getMarkingPeriodView.length() > 0) {
                                for (int i=0;i<getMarkingPeriodView.length();i++){
                                    JSONObject obj=getMarkingPeriodView.optJSONObject(i);
                                    String value=obj.optString("value");
                                    String text=obj.optString("fullName");
                                    String startDate=obj.optString("startDate");
                                    String endDate=obj.optString("endDate");
                                    markingperiodList.add(text);
                                    strtDateList.add(startDate);
                                    SpinnerModel spModel=new SpinnerModel(text,value);
                                    mmarkingperiodlist.add(spModel);
                                    if (checkDate(startDate.replace("T00:00:00",""),currentDate,endDate.replace("T00:00:00",""))){
                                        strtDate=obj.optString("startDate");
                                        Log.d("strtDate",strtDate);
                                    }
                                }


                                int pos=markingperiodList.indexOf(pref.getPeriod());



                                ArrayAdapter ad
                                        = new ArrayAdapter(
                                        getContext(),
                                        android.R.layout.simple_spinner_item,
                                        markingperiodList);

                                // set simple layout resource file
                                // for each item of spinner
                                ad.setDropDownViewResource(
                                        android.R.layout
                                                .simple_spinner_dropdown_item);

                                // Set the ArrayAdapter (ad) data on the
                                // Spinner which binds data to spinner



                                binding.spMarkingPeriod.setAdapter(ad);
                                binding.spMarkingPeriod.setSelection(pos);



                            }else {

                            }
                        }






                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }

    private boolean checkDate(String start,String check,String end){
        boolean dateflag;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);

        Date strtd = null;
        Date cureentd=null;
        Date endd=null;
        try {
            strtd = sdf.parse(start);
            cureentd = sdf.parse(check);
            endd = sdf.parse(end);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        if (cureentd.compareTo(strtd) >= 0) {
            if (cureentd.compareTo(endd) <= 0) {
                dateflag=true;
            } else {
                dateflag=false;
            }
        } else {
            dateflag=false;
        }

        return dateflag;
    }

    @Override
    public void onClick(View v) {
        if (v==binding.tvReportCard){
            Intent intent=new Intent(getContext(), ReportCardDetailsActivity.class);
            intent.putExtra("academicyear",academicyear);
            intent.putExtra("period",periodid);
            startActivity(intent);
        }

    }
}