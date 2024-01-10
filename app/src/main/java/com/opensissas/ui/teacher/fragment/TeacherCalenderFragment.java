package com.opensissas.ui.teacher.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.opensissas.R;
import com.opensissas.adapter.CalendrEventAdapter;
import com.opensissas.others.parser.PostJsonDataParser;
import com.opensissas.others.utility.AppData;
import com.opensissas.others.utility.Pref;
import com.opensissas.others.utility.Util;
import com.opensissas.ui.common.activity.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;


public class TeacherCalenderFragment extends Fragment {


   View view;
   TextView tvMonth;
    private static final String TAG = "MainActivity";
    private Calendar currentCalender = Calendar.getInstance(Locale.getDefault());
    private SimpleDateFormat dateFormatForDisplaying = new SimpleDateFormat("MMM dd,yyyy", Locale.getDefault());
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());
    private boolean shouldShow = false;
    private CompactCalendarView compactCalendarView;

    Pref pref;
    TextView tvDateText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_teacher_calender, container, false);
        initView();
        return view;
    }

    private void initView(){
        pref=new Pref(getContext());
        tvDateText=(TextView)view.findViewById(R.id.tvDateText);
        tvMonth=(TextView) view.findViewById(R.id.tvMonth);
        final ArrayList<String> mutableBookings = new ArrayList<>();

        final ImageView imgBackword = view.findViewById(R.id.imgBackword);
        final ImageView imgForward = view.findViewById(R.id.imgForward);
        final RecyclerView rvEvent = view.findViewById(R.id.rvEvent);
        rvEvent.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        CalendrEventAdapter eventAdapter=new CalendrEventAdapter(mutableBookings);
        rvEvent.setAdapter(eventAdapter);
        compactCalendarView = view.findViewById(R.id.compactcalendar_view);

        // below allows you to configure color for the current day in the month
        // compactCalendarView.setCurrentDayBackgroundColor(getResources().getColor(R.color.black));
        // below allows you to configure colors for the current day the user has selected
        // compactCalendarView.setCurrentSelectedDayBackgroundColor(getResources().getColor(R.color.dark_red));
        compactCalendarView.setUseThreeLetterAbbreviation(false);
        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);
        compactCalendarView.setIsRtl(false);
        compactCalendarView.displayOtherMonthDays(false);
        //compactCalendarView.setIsRtl(true);


        JSONObject obj=new JSONObject();
        JSONObject accessobj=new JSONObject();
        try {
            obj.put("tenantId", pref.getTenatID());
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


        compactCalendarView.invalidate();

        logEventsByMonth(compactCalendarView);



        tvMonth.setText(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));


        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                tvMonth.setText(dateFormatForMonth.format(dateClicked));
                tvDateText.setText("Event & Schedule on "+ dateFormatForDisplaying.format(dateClicked));
                List<Event> bookingsFromMap = compactCalendarView.getEvents(dateClicked);
                Log.d(TAG, "inside onclick " + dateFormatForDisplaying.format(dateClicked));
                if (bookingsFromMap != null) {
                    Log.d(TAG, bookingsFromMap.toString());
                    mutableBookings.clear();
                    for (Event booking : bookingsFromMap) {
                        mutableBookings.add((booking.getData().toString()));
                    }
                    eventAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                tvMonth.setText(dateFormatForMonth.format(firstDayOfNewMonth));
            }
        });

        imgBackword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactCalendarView.scrollLeft();
            }
        });

        imgForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactCalendarView.scrollRight();
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
        tvMonth.setText(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));
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
        currentCalender.setTime(new Date());
        currentCalender.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDayOfMonth = currentCalender.getTime();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object=jsonArray.optJSONObject(i);
            String startDate=object.optString("startDate").replaceAll("T00:00:00","");
            String formattedStrtDate=Util.changeAnyDateFormat(startDate,"yyyy-MM-dd","dd");
            int idate= Integer.parseInt(formattedStrtDate);
            int date=idate-1;

            String formattedStrtMonth=Util.changeAnyDateFormat(startDate,"yyyy-MM-dd","MM");
            int iMonth= Integer.parseInt(formattedStrtMonth);
            int months=iMonth-1;

            String formattedStrtyear=Util.changeAnyDateFormat(startDate,"yyyy-MM-dd","yyyy");
            int year= Integer.parseInt(formattedStrtyear);

            String title=object.optString("title");

            String color=object.optString("eventColor");
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


            List<Event> events = new ArrayList<>();
            events.add( new Event(Color.parseColor(color), timeInMillis, title));

            compactCalendarView.addEvents(events);

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
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                        }else {
                            String _token=response.optString("_token");
                            pref.saveToken(_token);
                            JSONArray blnkArray=new JSONArray();
                            JSONObject object=new JSONObject();
                            object.put("calendarList",blnkArray);
                            object.put("_tenantName",pref.getTenatName());
                            object.put("_userName",pref.getName());
                            object.put("_token",pref.getToken());
                            object.put("tenantId",pref.getTenatID());
                            object.put("schoolId",pref.getSchoolID());
                            object.put("membershipId",pref.getMemberShipID());
                            object.put("academicYear",pref.getAcademicYear());
                            object.put("_academicYear", pref.getAcademicYear());
                            getCalendarID(object);

                        }






                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }


    public void getCalendarID(JSONObject jsonObject) {
        String url=pref.getAPI()+"Calendar/getAllCalendar";

        new PostJsonDataParser(getContext(), Request.Method.POST, url,jsonObject, true,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("schoolresponse",response.toString());
                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");
                        if (_failure){
                            Toast.makeText(getContext(),_message,Toast.LENGTH_LONG).show();



                        }else {
                            JSONArray calendarList = response.optJSONArray("calendarList");
                            if (calendarList.length() > 0) {
                                JSONObject obj=calendarList.optJSONObject(0);
                                int calenderId=obj.optInt("calenderId");
                                JSONArray blnkArray=new JSONArray();
                                JSONArray calendarArray=new JSONArray();
                                calendarArray.put(calenderId);
                                JSONObject object=new JSONObject();
                                object.put("calendarEventList",blnkArray);
                                object.put("assignmentList",blnkArray);
                                object.put("calendarId",calendarArray);
                                object.put("_tenantName",pref.getTenatName());
                                object.put("_userName",pref.getName());
                                object.put("_token",pref.getToken());
                                object.put("tenantId",pref.getTenatID());
                                object.put("schoolId",pref.getSchoolID());
                                object.put("membershipId",pref.getMemberShipID());
                                object.put("academicYear",pref.getAcademicYear());
                                object.put("_academicYear", pref.getAcademicYear());
                                getEventList(object);





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


    public void getEventList(JSONObject jsonObject) {
        String url=pref.getAPI()+"CalendarEvent/getAllCalendarEvent";

        new PostJsonDataParser(getContext(), Request.Method.POST, url,jsonObject, true,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("schoolresponse",response.toString());
                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");
                        if (_failure){
                            Toast.makeText(getContext(),_message,Toast.LENGTH_LONG).show();



                        }else {
                            JSONArray calendarEventList = response.optJSONArray("calendarEventList");
                            if (calendarEventList.length() > 0) {


                                addEvents(calendarEventList);



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
}