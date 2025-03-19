package com.opensissasstudent.ui.student.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.applandeo.materialcalendarview.CalendarWeekDay;
import com.applandeo.materialcalendarview.EventDay;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.opensissasstudent.R;
import com.opensissasstudent.adapter.CalendrEventAdapter;
import com.opensissasstudent.adapter.CourseCatalogAdapter;
import com.opensissasstudent.adapter.MyScheduleListAdapter;
import com.opensissasstudent.databinding.FragmentCurseCatalogBinding;
import com.opensissasstudent.databinding.FragmentMyScheduleBinding;
import com.opensissasstudent.model.CourseCatalogModel;
import com.opensissasstudent.model.MyScheduleListModel;
import com.opensissasstudent.model.RedModel;
import com.opensissasstudent.others.parser.PostJsonDataParser;
import com.opensissasstudent.others.utility.Pref;
import com.opensissasstudent.others.utility.Util;
import com.opensissasstudent.ui.common.activity.LoginActivity;
import com.suke.widget.SwitchButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;


public class MyScheduleFragment extends Fragment implements View.OnClickListener {

    FragmentMyScheduleBinding binding;
    private View view;
    Pref pref;
    ArrayList<MyScheduleListModel>itemList=new ArrayList<>();
    ArrayList<String>staffList=new ArrayList<>();
    private CompactCalendarView compactCalendarView;

    private static final String TAG = "MainActivity";
    private Calendar currentCalender = Calendar.getInstance(Locale.getDefault());
    private SimpleDateFormat dateFormatForDisplaying = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());
    private boolean shouldShow = false;
    JSONArray calendarArray;
    ArrayList<RedModel> eventList=new ArrayList<>();
    final ArrayList<String> event = new ArrayList<>();
    List<Event> events = new ArrayList<>();
    JSONArray markingPeriodArray;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_my_schedule,container,false);
        view=binding.getRoot();
        initView();
        return view;

    }

    private void initView(){
        pref=new Pref(getContext());

        binding.rvListItem.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

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

       binding.switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(SwitchButton view, boolean isChecked) {
               if (isChecked){
                   JSONObject jsonObject1=new JSONObject();

                   try {
                       jsonObject1.put("isDropped", true);
                       jsonObject1.put("studentId", pref.getUserID());
                       jsonObject1.put("_tenantName", pref.getTenatName());
                       jsonObject1.put("_userName", pref.getName());
                       jsonObject1.put("_token", pref.getToken());
                       jsonObject1.put("tenantId", pref.getTenatID());
                       jsonObject1.put("_academicYear", pref.getAcademicYear());
                       jsonObject1.put("schoolId", pref.getSchoolID());
                       jsonObject1.put("academicYear", pref.getAcademicYear());
                       getcoursecatalog(jsonObject1);
                   } catch (JSONException e) {
                       e.printStackTrace();
                   }
               }else {
                   JSONObject jsonObject1=new JSONObject();

                   try {
                       jsonObject1.put("isDropped", false);
                       jsonObject1.put("studentId", pref.getUserID());
                       jsonObject1.put("_tenantName", pref.getTenatName());
                       jsonObject1.put("_userName", pref.getName());
                       jsonObject1.put("_token", pref.getToken());
                       jsonObject1.put("tenantId", pref.getTenatID());
                       jsonObject1.put("_academicYear", pref.getAcademicYear());
                       jsonObject1.put("schoolId", pref.getSchoolID());
                       jsonObject1.put("academicYear", pref.getAcademicYear());
                       getcoursecatalog(jsonObject1);
                   } catch (JSONException e) {
                       e.printStackTrace();
                   }
               }
           }
       });

        binding.imgList.setOnClickListener(this);
        binding.imgCalendar.setOnClickListener(this);



        binding.rvEvent.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        CalendrEventAdapter eventAdapter=new CalendrEventAdapter(event);
        binding.rvEvent.setAdapter(eventAdapter);
        compactCalendarView = view.findViewById(R.id.compactcalendar_view);
        compactCalendarView.setUseThreeLetterAbbreviation(false);
        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);
        compactCalendarView.setIsRtl(false);
        compactCalendarView.displayOtherMonthDays(false);

        compactCalendarView.invalidate();

        logEventsByMonth(compactCalendarView);



        binding.tvMonth.setText(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));


        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                binding.tvMonth.setText(dateFormatForMonth.format(dateClicked));
                binding.tvDateText.setText("Event & Schedule on "+ dateFormatForDisplaying.format(dateClicked));
                List<Event> bookingsFromMap = compactCalendarView.getEvents(dateClicked);
                Log.d(TAG, "inside onclick " + dateFormatForDisplaying.format(dateClicked));
                if (bookingsFromMap != null) {
                    Log.d(TAG, bookingsFromMap.toString());
                    event.clear();
                    for (Event booking : bookingsFromMap) {
                        event.add((booking.getData().toString()));
                    }
                    Set<String> set = new HashSet<>(event);
                    event.clear();
                    event.addAll(set);
                    eventAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                binding.tvMonth.setText(dateFormatForMonth.format(firstDayOfNewMonth));
            }
        });

        compactCalendarView.setAnimationListener(new CompactCalendarView.CompactCalendarAnimationListener() {
            @Override
            public void onOpened() {
            }

            @Override
            public void onClosed() {
            }
        });

        binding.imgBackword.setOnClickListener(this);
        binding.imgForward.setOnClickListener(this);


    }

    @NonNull
    private View.OnClickListener getCalendarShowLis() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!compactCalendarView.isAnimating()) {
                    if (shouldShow) {
                        compactCalendarView.showCalendar();
                    } else {
                        compactCalendarView.hideCalendar();
                    }
                    shouldShow = !shouldShow;
                }
            }
        };
    }

    @NonNull
    private View.OnClickListener getCalendarExposeLis() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!compactCalendarView.isAnimating()) {
                    if (shouldShow) {
                        compactCalendarView.showCalendarWithAnimation();
                    } else {
                        compactCalendarView.hideCalendarWithAnimation();
                    }
                    shouldShow = !shouldShow;
                }
            }
        };
    }



    @Override
    public void onResume() {
        super.onResume();
        binding.tvMonth.setText(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));
        // Set to current day on resume to set calendar to latest day
        // toolbar.setTitle(dateFormatForMonth.format(new Date()));
    }





    private void logEventsByMonth(CompactCalendarView compactCalendarView) {
        currentCalender.setTime(new Date());
        currentCalender.set(Calendar.DAY_OF_MONTH, 1);
        currentCalender.set(Calendar.MONTH, Calendar.AUGUST);
        List<String> dates = new ArrayList<>();
        for (Event e : compactCalendarView.getEventsForMonth(new Date())) {
            dates.add(dateFormatForDisplaying.format(e.getTimeInMillis()));
        }
        Log.d(TAG, "Events for Aug with simple date formatter: " + dates);
        Log.d(TAG, "Events for Aug month using default local and timezone: " + compactCalendarView.getEventsForMonth(currentCalender.getTime()));
    }




    private void addEvents(JSONArray jsonArray) {
        String periodStartTime = null;
        currentCalender.setTime(new Date());
        currentCalender.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDayOfMonth = currentCalender.getTime();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object=jsonArray.optJSONObject(i);
            String startDate=object.optString("effectiveStartDate").replaceAll("T00:00:00","");
            String effectiveDropDate=object.optString("effectiveDropDate").replaceAll("T00:00:00","");
            List<Date> dates = getDates(startDate, effectiveDropDate);
            Log.d("datebetween",dates.toString());
            for (int j=0;j<dates.size();j++){
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String todayAsString = df.format(dates.get(j));
                String formattedStrtDate=Util.changeAnyDateFormat(todayAsString,"yyyy-MM-dd","dd");
                int idate= Integer.parseInt(formattedStrtDate);
                int date=idate-1;

                String formattedStrtMonth=Util.changeAnyDateFormat(todayAsString,"yyyy-MM-dd","MM");
                int iMonth= Integer.parseInt(formattedStrtMonth);
                int months=iMonth-1;

                String formattedStrtyear=Util.changeAnyDateFormat(todayAsString,"yyyy-MM-dd","yyyy");
                int year= Integer.parseInt(formattedStrtyear);
                String title=object.optString("courseTitle");
                String courseSectionName=object.optString("courseSectionName");
                JSONObject courseFixedSchedule=object.optJSONObject("courseFixedSchedule");
                if (courseFixedSchedule!=null){
                    JSONObject blockPeriod=courseFixedSchedule.optJSONObject("blockPeriod");
                     periodStartTime=blockPeriod.optString("periodStartTime");
                }else {
                    JSONArray courseVariableSchedule=object.optJSONArray("courseVariableScheduleList");
                    for (int y=0;y<courseVariableSchedule.length();y++){
                        JSONObject varibleobject=courseVariableSchedule.optJSONObject(y);
                        JSONObject blockPeriod=varibleobject.optJSONObject("blockPeriod");
                        periodStartTime=blockPeriod.optString("periodStartTime");
                    }
                    
                }

                currentCalender.setTime(firstDayOfMonth);
                if (months > -1) {
                    currentCalender.set(Calendar.MONTH, months);
                }
                if (year > -1) {
                    currentCalender.set(Calendar.ERA, GregorianCalendar.AD);
                    currentCalender.set(Calendar.YEAR, year);
                }
                currentCalender.add(Calendar.DAY_OF_MONTH, date);


                setToMidnight(currentCalender);
                long timeInMillis = currentCalender.getTimeInMillis();


                 events = new ArrayList<>();
                events.add( new Event(Color.parseColor("#F44336"), timeInMillis, title+" - "+courseSectionName));

                compactCalendarView.addEvents(events);
            }





        }
    }



    private void setToMidnight(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
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

                            JSONObject obj=new JSONObject();
                            obj.put("academicYear",pref.getAcademicYear());
                            obj.put("_tenantName",pref.getTenatName());
                            obj.put("_userName",pref.getName());
                            obj.put("_token",pref.getToken());
                            obj.put("_academicYear",pref.getAcademicYear());
                            obj.put("tenantId",pref.getTenatID());
                            obj.put("schoolId",pref.getSchoolID());
                            getMarkingPeriod(obj);


                        }






                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }

    public void getMarkingPeriod(JSONObject jsonObject) {
        String url=pref.getAPI()+"MarkingPeriod/getAllMarkingPeriodList";


        new PostJsonDataParser(getContext(), Request.Method.POST, url,jsonObject, true,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("scholldetailsresponse",response.toString());
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
                            JSONArray getMarkingPeriodView=response.optJSONArray("getMarkingPeriodView");
                            markingPeriodArray=getMarkingPeriodView;
                            JSONObject jsonObject1=new JSONObject();

                            try {
                                jsonObject1.put("isDropped", false);
                                jsonObject1.put("studentId", pref.getUserID());
                                jsonObject1.put("_tenantName", pref.getTenatName());
                                jsonObject1.put("_userName", pref.getName());
                                jsonObject1.put("_token", pref.getToken());
                                jsonObject1.put("tenantId", pref.getTenatID());
                                jsonObject1.put("_academicYear", pref.getAcademicYear());
                                jsonObject1.put("schoolId", pref.getSchoolID());
                                jsonObject1.put("academicYear", pref.getAcademicYear());
                                getcoursecatalog(jsonObject1);
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

    public void getcoursecatalog(JSONObject jsonObject) {
        String url=pref.getAPI()+"StudentSchedule/scheduleCoursesForStudent360";


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

                            itemList.clear();
                            eventList.clear();
                            event.clear();
                            calendarArray=new JSONArray();
                            JSONArray sortedJsonArray = new JSONArray();
                            JSONArray scheduleCourseSectionForView=response.optJSONArray("scheduleCourseSectionForView");
                            ArrayList<JSONObject> jsonValues = new ArrayList<JSONObject>();
                            for (int i = 0; i < scheduleCourseSectionForView.length(); i++) {
                                jsonValues.add(scheduleCourseSectionForView.getJSONObject(i));
                            }

                            Collections.sort( jsonValues, new Comparator<JSONObject>() {
                                //You can change "Name" with "ID" if you want to sort by ID
                                private static final String KEY_NAME = "effectiveStartDate";

                                @Override
                                public int compare(JSONObject a, JSONObject b) {
                                    String valA = new String();
                                    String valB = new String();

                                    try {
                                        valA = (String) a.get(KEY_NAME);
                                        valB = (String) b.get(KEY_NAME);
                                    }
                                    catch (JSONException e) {
                                        //do something
                                    }

                                    return valA.compareTo(valB);
                                    //if you want to change the sort order, simply use the following:
                                    //return -valA.compareTo(valB);
                                }
                            });

                            for (int i = 0; i < scheduleCourseSectionForView.length(); i++) {
                                sortedJsonArray.put(jsonValues.get(i));
                            }
                            calendarArray=sortedJsonArray;
                            for (int i = 0; i < calendarArray.length(); i++) {
                                JSONObject object=calendarArray.optJSONObject(i);
                                String startDate=object.optString("effectiveStartDate").replaceAll("T00:00:00","");
                                String effectiveDropDate=object.optString("effectiveDropDate").replaceAll("T00:00:00","");
                                List<Date> dates = getDates(startDate, effectiveDropDate);
                                Log.d("datebetween",dates.toString());
                                for (int j=0;j<dates.size();j++){
                                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                    String todayAsString = df.format(dates.get(j));
                                    String formattedStrtDate=Util.changeAnyDateFormat(todayAsString,"yyyy-MM-dd","dd");
                                    int idate= Integer.parseInt(formattedStrtDate);
                                    int date=idate;

                                    String formattedStrtMonth=Util.changeAnyDateFormat(todayAsString,"yyyy-MM-dd","MM");
                                    int iMonth= Integer.parseInt(formattedStrtMonth);
                                    int months=iMonth-1;

                                    String formattedStrtyear=Util.changeAnyDateFormat(todayAsString,"yyyy-MM-dd","yyyy");
                                    int year= Integer.parseInt(formattedStrtyear);
                                    String title=object.optString("courseTitle");
                                    String courseSectionName=object.optString("courseSectionName");
                                    RedModel redModel=new RedModel(year,months,date);
                                    eventList.add(redModel);


                                }

                                List<EventDay> events = new ArrayList<>();
                                for (int z=0;z<eventList.size();z++){
                                    Calendar redCalender = Calendar.getInstance();
                                    redCalender.set(eventList.get(z).getYear(), eventList.get(z).getMonth(), eventList.get(z).getDate());
                                    events.add(new EventDay(redCalender, R.drawable.eventoval));


                                }









                            }

                           addEvents(calendarArray);

                            for (int i=0;i<sortedJsonArray.length();i++){
                                MyScheduleListModel scheduleListModel=new MyScheduleListModel();
                                JSONObject object=sortedJsonArray.optJSONObject(i);
                                String courseTitle=object.optString("courseTitle");
                                String courseSectionName=object.optString("courseSectionName");
                                String dayOfWeek=object.optString("dayOfWeek");
                                String effectiveStartDate=object.optString("effectiveStartDate");
                                String effectiveDropDate=object.optString("effectiveDropDate");
                                boolean attendanceTaken=object.optBoolean("attendanceTaken");




                                JSONArray staffMasterList=object.optJSONArray("staffMasterList");
                                JSONObject courseFixedSchedule=object.optJSONObject("courseFixedSchedule");
                                if (courseFixedSchedule!=null){
                                    JSONObject blockPeriod=courseFixedSchedule.optJSONObject("blockPeriod");
                                    String periodTitle=blockPeriod.optString("periodTitle");
                                    String periodStartTime=blockPeriod.optString("periodStartTime");
                                    String periodEndTime =blockPeriod.optString("periodEndTime");
                                    JSONObject rooms=courseFixedSchedule.optJSONObject("rooms");
                                    String room=rooms.optString("title");
                                    Log.d("periodEndTime",periodEndTime);

                                    scheduleListModel.setPeriodTitle(periodTitle);
                                    scheduleListModel.setPeriodStartTime(periodStartTime);
                                    scheduleListModel.setPeriodEndTime(periodEndTime);
                                    scheduleListModel.setRoom(room);
                                    scheduleListModel.setScheduleType("Fixed Schedule");

                                }else {
                                    JSONArray courseVariableSchedule=object.optJSONArray("courseVariableScheduleList");
                                    if (courseVariableSchedule.length()>0){
                                        String variableschedule=courseVariableSchedule.toString();
                                        scheduleListModel.setVariableScheduleList(variableschedule);
                                        scheduleListModel.setScheduleType("Variable Schedule");
                                    }else {
                                        JSONArray courseCalendarScheduleList=object.optJSONArray("courseCalendarScheduleList");
                                        if (courseCalendarScheduleList.length()>0) {
                                            String variableschedule = courseCalendarScheduleList.toString();
                                            scheduleListModel.setVariableScheduleList(variableschedule);
                                            scheduleListModel.setScheduleType("Variable Schedule");
                                        }
                                    }



                                }


                                JSONObject quarters=object.optJSONObject("quarters");
                                if (quarters!=null){
                                    String qtr =quarters.optString("title");
                                    scheduleListModel.setQtr(qtr);
                                }

                                scheduleListModel.setCourseTitle(courseTitle);
                                scheduleListModel.setCourseSectionName(courseSectionName);
                                scheduleListModel.setDayOfWeek(dayOfWeek);
                                scheduleListModel.setEffectiveStartDate(effectiveStartDate.replaceAll("T00:00:00",""));
                                scheduleListModel.setEffectiveDropDate(effectiveDropDate.replaceAll("T00:00:00",""));
                                scheduleListModel.setAttendanceTaken(attendanceTaken);
                                staffList=new ArrayList<>();
                                if (staffMasterList.length()>0){
                                    for (int z=0;z<staffMasterList.length();z++){
                                        JSONObject staffOBJ=staffMasterList.optJSONObject(z);
                                        String firstGivenName=staffOBJ.optString("firstGivenName");
                                        String lastFamilyName=staffOBJ.optString("lastFamilyName");
                                        staffList.add(firstGivenName+" "+lastFamilyName);

                                    }

                                    scheduleListModel.setTeacher(staffList.toString());
                                }else {
                                    scheduleListModel.setTeacher("");
                                }
                                itemList.add(scheduleListModel);

                                


                            }

                            for (int i=0;i<itemList.size();i++){
                                for (int j=0;j<markingPeriodArray.length();j++){
                                    JSONObject job=markingPeriodArray.optJSONObject(j);
                                    String startDate=job.optString("startDate").replaceAll("T00:00:00","");
                                    String endDate=job.optString("endDate").replaceAll("T00:00:00","");
                                    if (itemList.get(i).getEffectiveStartDate().equalsIgnoreCase(startDate)||itemList.get(i).getEffectiveDropDate().equalsIgnoreCase(endDate)){
                                        String text=job.optString("text");
                                        Log.d("riku",text);
                                    }else {
                                        Log.d("riku","B");
                                    }
                                }


                            }
                            MyScheduleListAdapter scheduleListAdapter=new MyScheduleListAdapter(itemList,getContext());
                            binding.rvListItem.setAdapter(scheduleListAdapter);

                        }







                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }

    @Override
    public void onClick(View v) {
        if (v==binding.imgList){
            binding.llList.setVisibility(View.VISIBLE);
            binding.llCalendar.setVisibility(View.GONE);
            binding.imgList.setImageTintList(ContextCompat.getColorStateList(getContext(), R.color.white));
            binding.imgCalendar.setImageTintList(ContextCompat.getColorStateList(getContext(), R.color.bar));
        }else if (v==binding.imgCalendar){
            binding.llList.setVisibility(View.GONE);
            binding.llCalendar.setVisibility(View.VISIBLE);
            binding.imgList.setImageTintList(ContextCompat.getColorStateList(getContext(), R.color.bar));
            binding.imgCalendar.setImageTintList(ContextCompat.getColorStateList(getContext(), R.color.white));
        }else if (v==binding.imgForward){
            compactCalendarView.scrollRight();
        }else if (v==binding.imgBackword){
            compactCalendarView.scrollLeft();
        }

    }

    private static List<Date> getDates(String dateString1, String dateString2)
    {
        ArrayList<Date> dates = new ArrayList<Date>();
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");

        Date date1 = null;
        Date date2 = null;

        try {
            date1 = df1 .parse(dateString1);
            date2 = df1 .parse(dateString2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);


        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        while(!cal1.after(cal2))
        {
            dates.add(cal1.getTime());
            cal1.add(Calendar.DATE, 1);
        }
        return dates;
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
                            JSONObject obj=new JSONObject();
                            obj.put("academicYear",pref.getAcademicYear());
                            obj.put("_tenantName",pref.getTenatName());
                            obj.put("_userName",pref.getName());
                            obj.put("_token",pref.getToken());
                            obj.put("_academicYear",pref.getAcademicYear());
                            obj.put("tenantId",pref.getTenatID());
                            obj.put("schoolId",pref.getSchoolID());
                            getMarkingPeriod(obj);




                        }






                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }
}