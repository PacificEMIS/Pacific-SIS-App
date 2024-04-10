package com.pacificstudentteacher.ui.student.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.pacificstudentteacher.R;
import com.pacificstudentteacher.adapter.MyAttendancePeriodAdapter;
import com.pacificstudentteacher.adapter.MyAttendanceStatusAdapter;
import com.pacificstudentteacher.databinding.FragmentMyDailyAttendanceBinding;
import com.pacificstudentteacher.model.AttendanceStatusDetailsModel;
import com.pacificstudentteacher.model.MyAttendanceStatusModel;
import com.pacificstudentteacher.model.MyDaillyAttendancePeriodModel;
import com.pacificstudentteacher.others.parser.PostJsonDataParser;
import com.pacificstudentteacher.others.utility.Pref;
import com.pacificstudentteacher.others.utility.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.naishadhparmar.zcustomcalendar.CustomCalendar;
import org.naishadhparmar.zcustomcalendar.OnNavigationButtonClickedListener;
import org.naishadhparmar.zcustomcalendar.Property;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class MyDailyAttendanceFragment extends Fragment implements OnNavigationButtonClickedListener {

    FragmentMyDailyAttendanceBinding binding;
    private View view;
    Pref pref;
    ArrayList<MyDaillyAttendancePeriodModel>periodList=new ArrayList<>();
    ArrayList<MyAttendanceStatusModel>dateList=new ArrayList<>();
    ArrayList<AttendanceStatusDetailsModel>statusList=new ArrayList<>();
    ArrayList<AttendanceStatusDetailsModel>statusList1=new ArrayList<>();
    ArrayList<String>period=new ArrayList<>();
    HashMap<String,String>hperiod=new HashMap<>();
    String month,year;
    String formdate,todate;
    int date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_my_daily_attendance,container,false);
        view=binding.getRoot();
        initView();
        return view;
    }

    private void initView(){
        pref=new Pref(getContext());
        LinearSnapHelper linearSnapHelper = new SnapHelperOneByOne();
        linearSnapHelper.attachToRecyclerView(binding.rvPeriod);
        binding.rvPeriod.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.rvStatus.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

       /* JSONObject obj=new JSONObject();
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
        }*/
        period.add("P1");
        period.add("P1");
        period.add("P2");
        period.add("P3");
        period.add("P5");
        period.add("P6");
        period.add("P7");
        period.add("P8");

        periodItem();

        HashMap<Object, Property> descHashMap = new HashMap<>();

        // Initialize default property
        Property defaultProperty = new Property();

        // Initialize default resource
        defaultProperty.layoutResource = R.layout.default_view;

        // Initialize and assign variable
        defaultProperty.dateTextViewResource = R.id.text_view;
        descHashMap.put("default", defaultProperty);


        Property presentProperty = new Property();
        presentProperty.layoutResource = R.layout.present_view;
        presentProperty.dateTextViewResource = R.id.text_view;
        descHashMap.put("P", presentProperty);


        Property absentProperty = new Property();
        absentProperty.layoutResource = R.layout.absent_view;
        absentProperty.dateTextViewResource = R.id.text_view;
        descHashMap.put("A", absentProperty);

        Property othersProperty = new Property();
        othersProperty.layoutResource = R.layout.others_view;
        othersProperty.dateTextViewResource = R.id.text_view;
        descHashMap.put("H", othersProperty);


        binding.customCalendar.setMapDescToProp(descHashMap);
        binding.customCalendar.setOnNavigationButtonClickedListener(CustomCalendar.PREVIOUS, this);
        binding.customCalendar.setOnNavigationButtonClickedListener(CustomCalendar.NEXT, this);





        DateFormat monthFormat = new SimpleDateFormat("MM");
        Date date = new Date();
        month=monthFormat.format(date);
        Log.d("month",month);

        DateFormat yearFormat = new SimpleDateFormat("yyyy");
        Date yeardate = new Date();
        year=yearFormat.format(yeardate);

        if (month.equals("03")){
            formdate=year+"-"+month+"-01T00:00:00";
            todate=year+"-"+month+"-31T00:00:00";
        }else if (month.equals("04")){
            formdate=year+"-"+month+"-01T00:00:00";
            todate=year+"-"+month+"-30T00:00:00";
        }else if (month.equals("05")){
            formdate=year+"-"+month+"-01T00:00:00";
            todate=year+"-"+month+"-31T00:00:00";
        }else if (month.equals("06")){
            formdate=year+"-"+month+"-01T00:00:00";
            todate=year+"-"+month+"-30T00:00:00";
        }else if (month.equals("07")){
            formdate=year+"-"+month+"-01T00:00:00";
            todate=year+"-"+month+"-31T00:00:00";
        }else if (month.equals("08")){
            formdate=year+"-"+month+"-01T00:00:00";
            todate=year+"-"+month+"-31T00:00:00";
        }else if (month.equals("09")){
            formdate=year+"-"+month+"-01T00:00:00";
            todate=year+"-"+month+"-30T00:00:00";
        }else if (month.equals("10")){
            formdate=year+"-"+month+"-01T00:00:00";
            todate=year+"-"+month+"-31T00:00:00";
        }else if (month.equals("11")){
            formdate=year+"-"+month+"-01T00:00:00";
            todate=year+"-"+month+"-30T00:00:00";
        }else if (month.equals("12")){
            formdate=year+"-"+month+"-01T00:00:00";
            todate=year+"-"+month+"-31T00:00:00";
        }else if (month.equals("01")){
            formdate=year+"-"+month+"-01T00:00:00";
            todate=year+"-"+month+"-31T00:00:00";
        }else if (month.equals("02")){
            formdate=year+"-"+month+"-01T00:00:00";
            todate=year+"-"+month+"-28T00:00:00";
        }


        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("pageNumber",1);
            jsonObject.put("pageSize",30);
            jsonObject.put("periodId","");
            jsonObject.put("markingPeriodStartDate",formdate);
            jsonObject.put("markingPeriodEndDate",todate);
            jsonObject.put("academicYear",pref.getAcademicYear());
            jsonObject.put("studentId",pref.getUserID());
            jsonObject.put("_tenantName",pref.getTenatName());
            jsonObject.put("_userName",pref.getName());
            jsonObject.put("_token",pref.getToken());
            jsonObject.put("_academicYear",pref.getAcademicYear());
            jsonObject.put("tenantId",pref.getTenatID());
            jsonObject.put("schoolId",pref.getSchoolID());
            getAbsentSummary(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }





    }



    public void getAbsentSummary(JSONObject jsonObject) {
        String url=pref.getAPI()+"Report/getAbsenceListByStudent";

        final HashMap<Integer, Object> dateHashmap = new HashMap<>();
        // initialize calendar
        final Calendar calendar = Calendar.getInstance();
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




                            JSONArray studendList=response.optJSONArray("studendList");
                            for (int i=0;i<studendList.length();i++){

                                JSONObject absentOBJ=studendList.optJSONObject(i);
                                String absenceDate=Util.changeAnyDateFormat(absentOBJ.optString("absenceDate").replaceAll("T00:00:00",""),"yyyy-MM-dd","dd");
                                try {
                                    date = Integer.parseInt(absenceDate);

                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }
                                String attendance=absentOBJ.optString("attendance");
                                char first = attendance.charAt(0);
                                String shortCode=Character.toString(first);

                                dateHashmap.put(date, shortCode);

                            }

                            binding.customCalendar.setDate(calendar, dateHashmap);




                        }







                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }


    public void getAbsentSummaryNav(JSONObject jsonObject,Calendar calendar) {
        String url=pref.getAPI()+"Report/getAbsenceListByStudent";

        final HashMap<Integer, Object> dateHashmap = new HashMap<>();
        // initialize calendar

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




                            JSONArray studendList=response.optJSONArray("studendList");
                            for (int i=0;i<studendList.length();i++){

                                JSONObject absentOBJ=studendList.optJSONObject(i);
                                String absenceDate=Util.changeAnyDateFormat(absentOBJ.optString("absenceDate").replaceAll("T00:00:00",""),"yyyy-MM-dd","dd");
                                try {
                                    date = Integer.parseInt(absenceDate);

                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }
                                String attendance=absentOBJ.optString("attendance");
                                char first = attendance.charAt(0);
                                String shortCode=Character.toString(first);

                                dateHashmap.put(date, shortCode);

                            }

                            binding.customCalendar.setDate(calendar, dateHashmap);




                        }







                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }

    private void periodItem(){
        for (int i=0;i<period.size();i++){
            hperiod.put("period",period.get(i));
        }



        MyAttendancePeriodAdapter periodAdapter=new MyAttendancePeriodAdapter(hperiod,getContext());
        binding.rvPeriod.setAdapter(periodAdapter);
        StatusDateList();
    }


    private void StatusDateList(){



        statusList.add(new AttendanceStatusDetailsModel("P"));
        statusList.add(new AttendanceStatusDetailsModel("P"));
        statusList.add(new AttendanceStatusDetailsModel("P"));
        statusList.add(new AttendanceStatusDetailsModel("A"));
        statusList.add(new AttendanceStatusDetailsModel("A"));
        statusList.add(new AttendanceStatusDetailsModel("A"));
        statusList.add(new AttendanceStatusDetailsModel("A"));

        statusList1.add(new AttendanceStatusDetailsModel("P"));
        statusList1.add(new AttendanceStatusDetailsModel("T"));
        statusList1.add(new AttendanceStatusDetailsModel("P"));
        statusList1.add(new AttendanceStatusDetailsModel("P"));
        statusList1.add(new AttendanceStatusDetailsModel("P"));
        statusList1.add(new AttendanceStatusDetailsModel("P"));
        statusList1.add(new AttendanceStatusDetailsModel("P"));




        dateList.add(new MyAttendanceStatusModel("02-02-2024",statusList));
        dateList.add(new MyAttendanceStatusModel("03-02-2024",statusList1));


        MyAttendanceStatusAdapter statusAdapter=new MyAttendanceStatusAdapter(dateList,getContext());
        binding.rvStatus.setAdapter(statusAdapter);


    }

    @Override
    public Map<Integer, Object>[] onNavigationButtonClicked(int whichButton, Calendar newMonth) {
        Map<Integer, Object>[] arr = new Map[2];
        arr[0] = new HashMap<>();

        switch(newMonth.get(Calendar.MONTH)) {
            case Calendar.JANUARY:
                Calendar calendar=Calendar.getInstance();
                calendar.set(newMonth.get(Calendar.YEAR),0,1);

                String formdate=newMonth.get(Calendar.YEAR)+"-"+"01-01T00:00:00";
                String todate=newMonth.get(Calendar.YEAR)+"-"+"01-31T00:00:00";


                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("pageNumber",1);
                    jsonObject.put("pageSize",30);
                    jsonObject.put("periodId","");
                    jsonObject.put("markingPeriodStartDate",formdate);
                    jsonObject.put("markingPeriodEndDate",todate);
                    jsonObject.put("academicYear",pref.getAcademicYear());
                    jsonObject.put("studentId",pref.getUserID());
                    jsonObject.put("_tenantName",pref.getTenatName());
                    jsonObject.put("_userName",pref.getName());
                    jsonObject.put("_token",pref.getToken());
                    jsonObject.put("_academicYear",pref.getAcademicYear());
                    jsonObject.put("tenantId",pref.getTenatID());
                    jsonObject.put("schoolId",pref.getSchoolID());
                    getAbsentSummaryNav(jsonObject,calendar);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
            case Calendar.FEBRUARY:
                Calendar calendar1=Calendar.getInstance();
                calendar1.set(newMonth.get(Calendar.YEAR),1,1);

                String febformdate=newMonth.get(Calendar.YEAR)+"-"+"02-01T00:00:00";
                String febtodate=newMonth.get(Calendar.YEAR)+"-"+"02-28T00:00:00";


                JSONObject febjsonObject=new JSONObject();
                try {
                    febjsonObject.put("pageNumber",1);
                    febjsonObject.put("pageSize",30);
                    febjsonObject.put("periodId","");
                    febjsonObject.put("markingPeriodStartDate",febformdate);
                    febjsonObject.put("markingPeriodEndDate",febtodate);
                    febjsonObject.put("academicYear",pref.getAcademicYear());
                    febjsonObject.put("studentId",pref.getUserID());
                    febjsonObject.put("_tenantName",pref.getTenatName());
                    febjsonObject.put("_userName",pref.getName());
                    febjsonObject.put("_token",pref.getToken());
                    febjsonObject.put("_academicYear",pref.getAcademicYear());
                    febjsonObject.put("tenantId",pref.getTenatID());
                    febjsonObject.put("schoolId",pref.getSchoolID());
                    getAbsentSummaryNav(febjsonObject,calendar1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //getAttendanceListForNav(newMonth.get(Calendar.YEAR),2,calendar1);
                break;
            case Calendar.MARCH:
                Calendar calendar2=Calendar.getInstance();
                calendar2.set(newMonth.get(Calendar.YEAR),2,1);


                String marchformdate=newMonth.get(Calendar.YEAR)+"-"+"03-01T00:00:00";
                String marchtodate=newMonth.get(Calendar.YEAR)+"-"+"03-31T00:00:00";


                JSONObject marchjsonObject=new JSONObject();
                try {
                    marchjsonObject.put("pageNumber",1);
                    marchjsonObject.put("pageSize",30);
                    marchjsonObject.put("periodId","");
                    marchjsonObject.put("markingPeriodStartDate",marchformdate);
                    marchjsonObject.put("markingPeriodEndDate",marchtodate);
                    marchjsonObject.put("academicYear",pref.getAcademicYear());
                    marchjsonObject.put("studentId",pref.getUserID());
                    marchjsonObject.put("_tenantName",pref.getTenatName());
                    marchjsonObject.put("_userName",pref.getName());
                    marchjsonObject.put("_token",pref.getToken());
                    marchjsonObject.put("_academicYear",pref.getAcademicYear());
                    marchjsonObject.put("tenantId",pref.getTenatID());
                    marchjsonObject.put("schoolId",pref.getSchoolID());
                    getAbsentSummaryNav(marchjsonObject,calendar2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                //getAttendanceListForNav(newMonth.get(Calendar.YEAR),3,calendar2);

                break;
            case  Calendar.APRIL:
                Calendar calendar3=Calendar.getInstance();
                calendar3.set(newMonth.get(Calendar.YEAR),3,1);

                String aprilformdate=newMonth.get(Calendar.YEAR)+"-"+"04-01T00:00:00";
                String apriltodate=newMonth.get(Calendar.YEAR)+"-"+"04-30T00:00:00";


                JSONObject apriljsonObject=new JSONObject();
                try {
                    apriljsonObject.put("pageNumber",1);
                    apriljsonObject.put("pageSize",30);
                    apriljsonObject.put("periodId","");
                    apriljsonObject.put("markingPeriodStartDate",aprilformdate);
                    apriljsonObject.put("markingPeriodEndDate",apriltodate);
                    apriljsonObject.put("academicYear",pref.getAcademicYear());
                    apriljsonObject.put("studentId",pref.getUserID());
                    apriljsonObject.put("_tenantName",pref.getTenatName());
                    apriljsonObject.put("_userName",pref.getName());
                    apriljsonObject.put("_token",pref.getToken());
                    apriljsonObject.put("_academicYear",pref.getAcademicYear());
                    apriljsonObject.put("tenantId",pref.getTenatID());
                    apriljsonObject.put("schoolId",pref.getSchoolID());
                    getAbsentSummaryNav(apriljsonObject,calendar3);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                //getAttendanceListForNav(newMonth.get(Calendar.YEAR),4,calendar3);
                break;
            case Calendar.MAY:
                Calendar calendar4=Calendar.getInstance();
                calendar4.set(newMonth.get(Calendar.YEAR),4,1);

                String mayformdate=newMonth.get(Calendar.YEAR)+"-"+"05-01T00:00:00";
                String maytodate=newMonth.get(Calendar.YEAR)+"-"+"05-31T00:00:00";


                JSONObject mayjsonObject=new JSONObject();
                try {
                    mayjsonObject.put("pageNumber",1);
                    mayjsonObject.put("pageSize",30);
                    mayjsonObject.put("periodId","");
                    mayjsonObject.put("markingPeriodStartDate",mayformdate);
                    mayjsonObject.put("markingPeriodEndDate",maytodate);
                    mayjsonObject.put("academicYear",pref.getAcademicYear());
                    mayjsonObject.put("studentId",pref.getUserID());
                    mayjsonObject.put("_tenantName",pref.getTenatName());
                    mayjsonObject.put("_userName",pref.getName());
                    mayjsonObject.put("_token",pref.getToken());
                    mayjsonObject.put("_academicYear",pref.getAcademicYear());
                    mayjsonObject.put("tenantId",pref.getTenatID());
                    mayjsonObject.put("schoolId",pref.getSchoolID());
                    getAbsentSummaryNav(mayjsonObject,calendar4);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //getAttendanceListForNav(newMonth.get(Calendar.YEAR),5,calendar4);
                break;
            case Calendar.JUNE:
                Calendar calendar5=Calendar.getInstance();
                calendar5.set(newMonth.get(Calendar.YEAR),5,1);


                String juneformdate=newMonth.get(Calendar.YEAR)+"-"+"06-01T00:00:00";
                String junetodate=newMonth.get(Calendar.YEAR)+"-"+"06-30T00:00:00";


                JSONObject junejsonObject=new JSONObject();
                try {
                    junejsonObject.put("pageNumber",1);
                    junejsonObject.put("pageSize",30);
                    junejsonObject.put("periodId","");
                    junejsonObject.put("markingPeriodStartDate",juneformdate);
                    junejsonObject.put("markingPeriodEndDate",junetodate);
                    junejsonObject.put("academicYear",pref.getAcademicYear());
                    junejsonObject.put("studentId",pref.getUserID());
                    junejsonObject.put("_tenantName",pref.getTenatName());
                    junejsonObject.put("_userName",pref.getName());
                    junejsonObject.put("_token",pref.getToken());
                    junejsonObject.put("_academicYear",pref.getAcademicYear());
                    junejsonObject.put("tenantId",pref.getTenatID());
                    junejsonObject.put("schoolId",pref.getSchoolID());
                    getAbsentSummaryNav(junejsonObject,calendar5);
                } catch (JSONException e) {
                    e.printStackTrace();
                }



                //getAttendanceListForNav(newMonth.get(Calendar.YEAR),6,calendar5);
                break;
            case Calendar.JULY:
                Calendar calendar6=Calendar.getInstance();
                calendar6.set(newMonth.get(Calendar.YEAR),6,1);

                String julyformdate=newMonth.get(Calendar.YEAR)+"-"+"07-01T00:00:00";
                String julytodate=newMonth.get(Calendar.YEAR)+"-"+"07-31T00:00:00";


                JSONObject julyjsonObject=new JSONObject();
                try {
                    julyjsonObject.put("pageNumber",1);
                    julyjsonObject.put("pageSize",30);
                    julyjsonObject.put("periodId","");
                    julyjsonObject.put("markingPeriodStartDate",julyformdate);
                    julyjsonObject.put("markingPeriodEndDate",julytodate);
                    julyjsonObject.put("academicYear",pref.getAcademicYear());
                    julyjsonObject.put("studentId",pref.getUserID());
                    julyjsonObject.put("_tenantName",pref.getTenatName());
                    julyjsonObject.put("_userName",pref.getName());
                    julyjsonObject.put("_token",pref.getToken());
                    julyjsonObject.put("_academicYear",pref.getAcademicYear());
                    julyjsonObject.put("tenantId",pref.getTenatID());
                    julyjsonObject.put("schoolId",pref.getSchoolID());
                    getAbsentSummaryNav(julyjsonObject,calendar6);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                //getAttendanceListForNav(newMonth.get(Calendar.YEAR),7,calendar6);
                break;
            case Calendar.AUGUST:
                Calendar calendar7=Calendar.getInstance();
                calendar7.set(newMonth.get(Calendar.YEAR),7,1);

                String augustformdate=newMonth.get(Calendar.YEAR)+"-"+"08-01T00:00:00";
                String augusttodate=newMonth.get(Calendar.YEAR)+"-"+"08-31T00:00:00";


                JSONObject augustjsonObject=new JSONObject();
                try {
                    augustjsonObject.put("pageNumber",1);
                    augustjsonObject.put("pageSize",30);
                    augustjsonObject.put("periodId","");
                    augustjsonObject.put("markingPeriodStartDate",augustformdate);
                    augustjsonObject.put("markingPeriodEndDate",augusttodate);
                    augustjsonObject.put("academicYear",pref.getAcademicYear());
                    augustjsonObject.put("studentId",pref.getUserID());
                    augustjsonObject.put("_tenantName",pref.getTenatName());
                    augustjsonObject.put("_userName",pref.getName());
                    augustjsonObject.put("_token",pref.getToken());
                    augustjsonObject.put("_academicYear",pref.getAcademicYear());
                    augustjsonObject.put("tenantId",pref.getTenatID());
                    augustjsonObject.put("schoolId",pref.getSchoolID());
                    getAbsentSummaryNav(augustjsonObject,calendar7);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                //getAttendanceListForNav(newMonth.get(Calendar.YEAR),8,calendar7);
                break;
            case Calendar.SEPTEMBER:
                Calendar calendar8=Calendar.getInstance();
                calendar8.set(newMonth.get(Calendar.YEAR),8,1);


                String septformdate=newMonth.get(Calendar.YEAR)+"-"+"09-01T00:00:00";
                String septtodate=newMonth.get(Calendar.YEAR)+"-"+"09-30T00:00:00";


                JSONObject septjsonObject=new JSONObject();
                try {
                    septjsonObject.put("pageNumber",1);
                    septjsonObject.put("pageSize",30);
                    septjsonObject.put("periodId","");
                    septjsonObject.put("markingPeriodStartDate",septformdate);
                    septjsonObject.put("markingPeriodEndDate",septtodate);
                    septjsonObject.put("academicYear",pref.getAcademicYear());
                    septjsonObject.put("studentId",pref.getUserID());
                    septjsonObject.put("_tenantName",pref.getTenatName());
                    septjsonObject.put("_userName",pref.getName());
                    septjsonObject.put("_token",pref.getToken());
                    septjsonObject.put("_academicYear",pref.getAcademicYear());
                    septjsonObject.put("tenantId",pref.getTenatID());
                    septjsonObject.put("schoolId",pref.getSchoolID());
                    getAbsentSummaryNav(septjsonObject,calendar8);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                //getAttendanceListForNav(newMonth.get(Calendar.YEAR),9,calendar8);
                break;
            case Calendar.OCTOBER:
                Calendar calendar9=Calendar.getInstance();
                calendar9.set(newMonth.get(Calendar.YEAR),9,1);

                String octformdate=newMonth.get(Calendar.YEAR)+"-"+"10-01T00:00:00";
                String octtodate=newMonth.get(Calendar.YEAR)+"-"+"10-31T00:00:00";


                JSONObject octjsonObject=new JSONObject();
                try {
                    octjsonObject.put("pageNumber",1);
                    octjsonObject.put("pageSize",30);
                    octjsonObject.put("periodId","");
                    octjsonObject.put("markingPeriodStartDate",octformdate);
                    octjsonObject.put("markingPeriodEndDate",octtodate);
                    octjsonObject.put("academicYear",pref.getAcademicYear());
                    octjsonObject.put("studentId",pref.getUserID());
                    octjsonObject.put("_tenantName",pref.getTenatName());
                    octjsonObject.put("_userName",pref.getName());
                    octjsonObject.put("_token",pref.getToken());
                    octjsonObject.put("_academicYear",pref.getAcademicYear());
                    octjsonObject.put("tenantId",pref.getTenatID());
                    octjsonObject.put("schoolId",pref.getSchoolID());
                    getAbsentSummaryNav(octjsonObject,calendar9);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                //getAttendanceListForNav(newMonth.get(Calendar.YEAR),10,calendar9);
                break;
            case Calendar.NOVEMBER:
                Calendar calendar10=Calendar.getInstance();
                calendar10.set(newMonth.get(Calendar.YEAR),10,1);


                String novformdate=newMonth.get(Calendar.YEAR)+"-"+"11-01T00:00:00";
                String novtodate=newMonth.get(Calendar.YEAR)+"-"+"11-30T00:00:00";


                JSONObject novjsonObject=new JSONObject();
                try {
                    novjsonObject.put("pageNumber",1);
                    novjsonObject.put("pageSize",30);
                    novjsonObject.put("periodId","");
                    novjsonObject.put("markingPeriodStartDate",novformdate);
                    novjsonObject.put("markingPeriodEndDate",novtodate);
                    novjsonObject.put("academicYear",pref.getAcademicYear());
                    novjsonObject.put("studentId",pref.getUserID());
                    novjsonObject.put("_tenantName",pref.getTenatName());
                    novjsonObject.put("_userName",pref.getName());
                    novjsonObject.put("_token",pref.getToken());
                    novjsonObject.put("_academicYear",pref.getAcademicYear());
                    novjsonObject.put("tenantId",pref.getTenatID());
                    novjsonObject.put("schoolId",pref.getSchoolID());
                    getAbsentSummaryNav(novjsonObject,calendar10);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //getAttendanceListForNav(newMonth.get(Calendar.YEAR),11,calendar10);
                break;
            case Calendar.DECEMBER:
                Calendar calendar11=Calendar.getInstance();

                calendar11.set(newMonth.get(Calendar.YEAR),11,1);

                String decformdate=newMonth.get(Calendar.YEAR)+"-"+"12-01T00:00:00";
                String dectodate=newMonth.get(Calendar.YEAR)+"-"+"12-31T00:00:00";


                JSONObject decjsonObject=new JSONObject();
                try {
                    decjsonObject.put("pageNumber",1);
                    decjsonObject.put("pageSize",30);
                    decjsonObject.put("periodId","");
                    decjsonObject.put("markingPeriodStartDate",decformdate);
                    decjsonObject.put("markingPeriodEndDate",dectodate);
                    decjsonObject.put("academicYear",pref.getAcademicYear());
                    decjsonObject.put("studentId",pref.getUserID());
                    decjsonObject.put("_tenantName",pref.getTenatName());
                    decjsonObject.put("_userName",pref.getName());
                    decjsonObject.put("_token",pref.getToken());
                    decjsonObject.put("_academicYear",pref.getAcademicYear());
                    decjsonObject.put("tenantId",pref.getTenatID());
                    decjsonObject.put("schoolId",pref.getSchoolID());
                    getAbsentSummaryNav(decjsonObject,calendar11);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //getAttendanceListForNav(newMonth.get(Calendar.YEAR),12,calendar11);
                break;
        }
        return arr;
    }


    public class SnapHelperOneByOne extends LinearSnapHelper {

        @Override
        public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {

            if (!(layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider)) {
                return RecyclerView.NO_POSITION;
            }

            final View currentView = findSnapView(layoutManager);

            if (currentView == null) {
                return RecyclerView.NO_POSITION;
            }

            final int currentPosition = layoutManager.getPosition(currentView);

            if (currentPosition == RecyclerView.NO_POSITION) {
                return RecyclerView.NO_POSITION;
            }

            return currentPosition;
        }
    }
}