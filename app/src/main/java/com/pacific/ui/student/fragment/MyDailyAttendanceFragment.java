package com.pacific.ui.student.fragment;

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
import com.applandeo.materialcalendarview.CalendarWeekDay;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnCalendarPageChangeListener;
import com.pacific.R;
import com.pacific.adapter.MyAttendancePeriodAdapter;
import com.pacific.adapter.MyAttendanceStatusAdapter;
import com.pacific.databinding.FragmentMyDailyAttendanceBinding;
import com.pacific.model.AttendanceStatusDetailsModel;
import com.pacific.model.GreenModel;
import com.pacific.model.MyAttendanceStatusModel;
import com.pacific.model.MyDaillyAttendancePeriodModel;
import com.pacific.model.RedModel;
import com.pacific.model.YellowModel;
import com.pacific.others.parser.PostJsonDataParser;
import com.pacific.others.utility.Pref;
import com.pacific.others.utility.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class MyDailyAttendanceFragment extends Fragment  {

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
    int m;
    String formdate,todate;
    int date,ayear,amonth;
    ArrayList<RedModel> redList=new ArrayList<>();
    ArrayList<YellowModel>yellowList=new ArrayList<>();
    ArrayList<GreenModel>greenList=new ArrayList<>();

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




        DateFormat monthFormat = new SimpleDateFormat("MM");
        Date date = new Date();
        month=monthFormat.format(date);
        m= Integer.parseInt(month);
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

        binding.calendarView.setOnPreviousPageChangeListener(new OnCalendarPageChangeListener() {
            @Override
            public void onChange() {
                m=m-1;
                month= String.valueOf(m);
                if (month.equals("3")){
                    formdate=year+"-"+month+"-01T00:00:00";
                    todate=year+"-"+month+"-31T00:00:00";
                }else if (month.equals("4")){
                    formdate=year+"-"+month+"-01T00:00:00";
                    todate=year+"-"+month+"-30T00:00:00";
                }else if (month.equals("5")){
                    formdate=year+"-"+month+"-01T00:00:00";
                    todate=year+"-"+month+"-31T00:00:00";
                }else if (month.equals("6")){
                    formdate=year+"-"+month+"-01T00:00:00";
                    todate=year+"-"+month+"-30T00:00:00";
                }else if (month.equals("7")){
                    formdate=year+"-"+month+"-01T00:00:00";
                    todate=year+"-"+month+"-31T00:00:00";
                }else if (month.equals("8")){
                    formdate=year+"-"+month+"-01T00:00:00";
                    todate=year+"-"+month+"-31T00:00:00";
                }else if (month.equals("9")){
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
                }else if (month.equals("1")){
                    formdate=year+"-"+month+"-01T00:00:00";
                    todate=year+"-"+month+"-31T00:00:00";
                }else if (month.equals("2")){
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
        });


        binding.calendarView.setOnForwardPageChangeListener(new OnCalendarPageChangeListener() {
            @Override
            public void onChange() {
                m=m+1;
                month= String.valueOf(m);
                if (month.equals("3")){
                    formdate=year+"-"+month+"-01T00:00:00";
                    todate=year+"-"+month+"-31T00:00:00";
                }else if (month.equals("4")){
                    formdate=year+"-"+month+"-01T00:00:00";
                    todate=year+"-"+month+"-30T00:00:00";
                }else if (month.equals("5")){
                    formdate=year+"-"+month+"-01T00:00:00";
                    todate=year+"-"+month+"-31T00:00:00";
                }else if (month.equals("6")){
                    formdate=year+"-"+month+"-01T00:00:00";
                    todate=year+"-"+month+"-30T00:00:00";
                }else if (month.equals("7")){
                    formdate=year+"-"+month+"-01T00:00:00";
                    todate=year+"-"+month+"-31T00:00:00";
                }else if (month.equals("8")){
                    formdate=year+"-"+month+"-01T00:00:00";
                    todate=year+"-"+month+"-31T00:00:00";
                }else if (month.equals("9")){
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
                }else if (month.equals("1")){
                    formdate=year+"-"+month+"-01T00:00:00";
                    todate=year+"-"+month+"-31T00:00:00";
                }else if (month.equals("2")){
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
        });


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
                    redList.clear();
                    greenList.clear();
                    yellowList.clear();
                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");
                        if (_failure){
                            if (_message.contains("token")||_message.contains("Token") ){
                                JSONObject jobj=new JSONObject();
                                try {
                                    jobj.put("tenantId",pref.getTenatID());
                                    jobj.put("userId",0);
                                    jobj.put("_tenantName",pref.getTenatName());
                                    jobj.put("password",pref.getPasswordBase64());
                                    jobj.put("email",pref.getLoginEmail());
                                    jobj.put("schoolId",null);
                                    jobj.put("_userName","");
                                    jobj.put("_token",null);
                                    jobj.put("isMobileLogin",true);
                                    login(jobj);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }else {
                                Toast.makeText(getContext(), _message, Toast.LENGTH_LONG).show();
                            }

                        }else {




                            JSONArray studendList=response.optJSONArray("studendList");
                            for (int i=0;i<studendList.length();i++){

                                JSONObject absentOBJ=studendList.optJSONObject(i);
                                String absenceDate=Util.changeAnyDateFormat(absentOBJ.optString("absenceDate").replaceAll("T00:00:00",""),"yyyy-MM-dd","dd");
                                String absenceMonth=Util.changeAnyDateFormat(absentOBJ.optString("absenceDate").replaceAll("T00:00:00",""),"yyyy-MM-dd","MM");
                                String absenceYr=Util.changeAnyDateFormat(absentOBJ.optString("absenceDate").replaceAll("T00:00:00",""),"yyyy-MM-dd","yyyy");
                                try {
                                    date = Integer.parseInt(absenceDate);
                                     ayear = Integer.parseInt(absenceYr);
                                     amonth = Integer.parseInt(absenceMonth)-1;

                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }
                                String attendance=absentOBJ.optString("attendance");
                                char first = attendance.charAt(0);
                                String shortCode=Character.toString(first);
                                if (shortCode.equalsIgnoreCase("A")){
                                    RedModel redModel=new RedModel(ayear,amonth,date);
                                    redList.add(redModel);
                                }
                                if (shortCode.equalsIgnoreCase("P")){
                                    GreenModel greenModel=new GreenModel();
                                    greenModel.setYear(ayear);
                                    greenModel.setMonth(amonth);
                                    greenModel.setDate(date);
                                    greenList.add(greenModel);
                                } if (shortCode.equalsIgnoreCase("H")){
                                    YellowModel yellowModel=new YellowModel();
                                    yellowModel.setYear(ayear);
                                    yellowModel.setMonth(amonth);
                                    yellowModel.setDate(date);
                                    yellowList.add(yellowModel);
                                }


                            }

                            List<EventDay> events = new ArrayList<>();
                            for (int i=0;i<redList.size();i++){
                                Calendar redCalender = Calendar.getInstance();
                                redCalender.set(redList.get(i).getYear(), redList.get(i).getMonth(), redList.get(i).getDate());
                                events.add(new EventDay(redCalender, R.drawable.redoval));


                            }

                            for (int i=0;i<yellowList.size();i++){
                                Calendar yellowCalender = Calendar.getInstance();
                                yellowCalender.set(yellowList.get(i).getYear(), yellowList.get(i).getMonth(), yellowList.get(i).getDate());
                                events.add(new EventDay(yellowCalender, R.drawable.yellowoval));


                            }

                            for (int i=0;i<greenList.size();i++){
                                Calendar greenCalender = Calendar.getInstance();
                                greenCalender.set(greenList.get(i).getYear(), greenList.get(i).getMonth(), greenList.get(i).getDate());
                                events.add(new EventDay(greenCalender, R.drawable.oval));


                            }

                            binding.calendarView.setFirstDayOfWeek(CalendarWeekDay.SUNDAY);
                            binding.calendarView.setEvents(events);






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


    public void login(JSONObject jsonObject) {

        String url=pref.getAPI()+"User/ValidateLogin";

        new PostJsonDataParser(getContext(), Request.Method.POST, url,jsonObject, true,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("loginresponse",response.toString());
                    try {
                        boolean _failure = response.optBoolean("_failure");
                        if (_failure){


                            Toast.makeText(getContext(),"Invalid Login Credentials",Toast.LENGTH_LONG).show();

                        }else {


                            String _token=response.optString("_token");
                            pref.saveToken(_token);
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






                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }
}