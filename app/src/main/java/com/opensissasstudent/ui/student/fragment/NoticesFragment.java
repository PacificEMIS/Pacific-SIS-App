package com.opensissasstudent.ui.student.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.opensissasstudent.R;
import com.opensissasstudent.adapter.MyScheduleListAdapter;
import com.opensissasstudent.adapter.NoticeAdapter;
import com.opensissasstudent.databinding.FragmentMyScheduleBinding;
import com.opensissasstudent.databinding.FragmentNoticesBinding;
import com.opensissasstudent.model.MyScheduleListModel;
import com.opensissasstudent.model.NoticeModel;
import com.opensissasstudent.others.parser.PostJsonDataParser;
import com.opensissasstudent.others.utility.Pref;
import com.opensissasstudent.ui.common.activity.LoginActivity;

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


public class NoticesFragment extends Fragment implements View.OnClickListener {
    FragmentNoticesBinding binding;
    private View view;
    Pref pref;
    ArrayList<NoticeModel>currentList=new ArrayList<>();
    ArrayList<NoticeModel>pastList=new ArrayList<>();
    Calendar c;
    SimpleDateFormat df;
    String currentDate;
    NoticeAdapter noticeAdapter;
    String imagestring="";
    String toolbar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_notices,container,false);
        view=binding.getRoot();
        initView();
        return view;
    }

    private void initView(){
        pref=new Pref(getContext());
        toolbar=getArguments().getString("toolbar");
        binding.tvTitle.setText(toolbar);

        c= Calendar.getInstance();
        System.out.println("Current time => " + c);

        df = new SimpleDateFormat("yyyy-MM-dd");
        currentDate = df.format(c.getTime());

        binding.rvCurrent.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.rvPast.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

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

        binding.tvCurrent.setOnClickListener(this);
        binding.tvPast.setOnClickListener(this);
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

                            if (toolbar.equalsIgnoreCase("notices")){
                                try {
                                    JSONArray blnkArray=new JSONArray();
                                    JSONObject object=new JSONObject();
                                    object.put("noticeList",blnkArray);
                                    object.put("membershipId",pref.getMemberShipID());
                                    object.put("_tenantName",pref.getTenatName());
                                    object.put("_userName",pref.getName());
                                    object.put("_token",pref.getToken());
                                    object.put("tenantId",pref.getTenatID());
                                    object.put("_academicYear", pref.getAcademicYear());
                                    object.put("schoolId",pref.getSchoolID());
                                    getNotice(object);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }else {
                                try {

                                    JSONObject object=new JSONObject();
                                    object.put("membershipId",pref.getMemberShipID());
                                    object.put("_tenantName",pref.getTenatName());
                                    object.put("_userName",pref.getName());
                                    object.put("_token",pref.getToken());
                                    object.put("tenantId",pref.getTenatID());
                                    object.put("_academicYear", pref.getAcademicYear());
                                    object.put("schoolId",pref.getSchoolID());
                                    getMedicalNotice(object);
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

    public void getNotice(JSONObject jsonObject) {
        String url=pref.getAPI()+"Notice/getAllNotice";


        new PostJsonDataParser(getContext(), Request.Method.POST, url,jsonObject, true,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("scholldetailsresponse",response.toString());
                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");
                        if (_failure){

                            Toast.makeText(getContext(),_message,Toast.LENGTH_LONG).show();

                        }else {
                            currentList.clear();
                            pastList.clear();

                            JSONArray noticeList=response.optJSONArray("noticeList");

                            for (int i=0;i<noticeList.length();i++){

                                JSONObject object=noticeList.optJSONObject(i);
                                String title=object.optString("title");
                                String body=object.optString("body");
                                String image=body.replaceAll("\\<.*?\\>","");

                                String validFrom=object.optString("validFrom").replace("T00:00:00","");
                                String validTo=object.optString("validTo").replace("T00:00:00","");


                                    NoticeModel noticeModel=new NoticeModel();
                                    noticeModel.setTitle(title);
                                    noticeModel.setBody(body);
                                    noticeModel.setImage(image);
                                    pastList.add(noticeModel);





                            }

                                 noticeAdapter=new NoticeAdapter(pastList,getContext(),NoticesFragment.this);
                                binding.rvPast.setAdapter(noticeAdapter);



                        }







                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }


    public void getMedicalNotice(JSONObject jsonObject) {
        String url=pref.getAPI()+"StudentMentalInfo/getAllStudentMentalInfoList";


        new PostJsonDataParser(getContext(), Request.Method.POST, url,jsonObject, true,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("scholldetailsresponse",response.toString());
                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");
                        if (_failure){

                            Toast.makeText(getContext(),_message,Toast.LENGTH_LONG).show();

                        }else {
                            currentList.clear();
                            pastList.clear();

                            JSONArray studentMentalInfoList=response.optJSONArray("studentMentalInfoList");

                            for (int i=0;i<studentMentalInfoList.length();i++){

                                JSONObject object=studentMentalInfoList.optJSONObject(i);
                                String title=object.optString("title");
                                String body=object.optString("body");
                                String image=body.replaceAll("\\<.*?\\>","");

                                String validFrom=object.optString("validFrom").replace("T00:00:00","");
                                String validTo=object.optString("validTo").replace("T00:00:00","");


                                NoticeModel noticeModel=new NoticeModel();
                                noticeModel.setTitle(title);
                                noticeModel.setBody(body);
                                noticeModel.setImage(image);
                                pastList.add(noticeModel);





                            }

                            noticeAdapter=new NoticeAdapter(pastList,getContext(),NoticesFragment.this);
                            binding.rvPast.setAdapter(noticeAdapter);



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

    public void updateStatus(int position,boolean status)
    {
        for (int i =0 ;i<pastList.size();i++)
        {
            if (i==position)
            {
                pastList.get(i).setExpanded(status);
            }
            else
            {
                pastList.get(i).setExpanded(false);
            }
        }
        noticeAdapter.notifyDataSetChanged();


    }

    @Override
    public void onClick(View v) {
        if (v==binding.tvCurrent){
            binding.llCurrent.setVisibility(View.VISIBLE);
            binding.llPast.setVisibility(View.GONE);
            binding.tvCurrent.setTextColor(Color.parseColor("#FFFFFF"));
            binding.tvCurrent.setTextColor(Color.parseColor("#094990"));
        }else if (v==binding.tvPast){
            binding.llCurrent.setVisibility(View.GONE);
            binding.llPast.setVisibility(View.VISIBLE);
            binding.tvCurrent.setTextColor(Color.parseColor("#094990"));
            binding.tvPast.setTextColor(Color.parseColor("#FFFFFF"));
        }

    }
}