package com.pacificstudentteacher.ui.student.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.pacificstudentteacher.R;
import com.pacificstudentteacher.databinding.FragmentAbsentSummaryBinding;
import com.pacificstudentteacher.model.SpinnerModel;
import com.pacificstudentteacher.others.parser.PostJsonDataParser;
import com.pacificstudentteacher.others.utility.Pref;
import com.pacificstudentteacher.others.utility.Util;
import com.pacificstudentteacher.ui.common.activity.LoginActivity;
import com.pacificstudentteacher.ui.student.activity.AbsentSummaryDetailsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;


public class AbsentSummaryFragment extends Fragment implements View.OnClickListener {
    FragmentAbsentSummaryBinding binding;
    private View view;
    Pref pref;
    ArrayList<String>periodList=new ArrayList<>();
    ArrayList<SpinnerModel>blockperiodList=new ArrayList<>();
    String periodID="";
    String fromDate="";
    String toDate="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_absent_summary,container,false);
        initView();
        view=binding.getRoot();
        return view;
    }

    private void initView(){
        pref=new Pref(getContext());
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

        binding.llFromDate.setOnClickListener(this);
        binding.llToDate.setOnClickListener(this);
        binding.tvSearch.setOnClickListener(this);

        binding.spPeriod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position>0){
                    periodID= String.valueOf(blockperiodList.get(position).getId());
                }else {
                    periodID= "";
                }


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

                            JSONObject yearjob=new JSONObject();
                            JSONArray yearArray=new JSONArray();
                            yearArray.put(yearjob);
                            JSONObject obj=new JSONObject();
                            obj.put("_tenantName",pref.getTenatName());
                            obj.put("_userName",pref.getName());
                            obj.put("_token",pref.getToken());
                            obj.put("_academicYear",pref.getAcademicYear());
                            obj.put("tenantId",pref.getTenatID());
                            obj.put("schoolId",pref.getSchoolID());
                            obj.put("academicYear",pref.getAcademicYear());
                            getPeriod(obj);




                        }






                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }

    public void getPeriod(JSONObject jsonObject) {
        String url=pref.getAPI()+"Period/getAllBlockList";


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

                            blockperiodList.add(new SpinnerModel("",""));
                            periodList.add("Daily");


                            JSONArray getBlockListForView=response.optJSONArray("getBlockListForView");
                            for (int i=0;i<getBlockListForView.length();i++){
                                JSONObject blockOBJ=getBlockListForView.optJSONObject(i);
                                JSONArray blockPeriod=blockOBJ.optJSONArray("blockPeriod");
                                for (int j=0;j<blockPeriod.length();j++){
                                    JSONObject periodOBJ=blockPeriod.optJSONObject(j);
                                    int periodId=periodOBJ.optInt("periodId");
                                    String periodTitle=periodOBJ.optString("periodTitle");
                                    periodList.add(periodTitle);
                                    SpinnerModel spModel=new SpinnerModel(periodTitle,"");
                                    spModel.setId(periodId);
                                    blockperiodList.add(spModel);

                                }

                            }

                            ArrayAdapter ad
                                    = new ArrayAdapter(
                                    getContext(),
                                    android.R.layout.simple_spinner_item,
                                    periodList);

                            // set simple layout resource file
                            // for each item of spinner
                            ad.setDropDownViewResource(
                                    android.R.layout
                                            .simple_spinner_dropdown_item);

                            binding.spPeriod.setAdapter(ad);



                        }







                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }

    private void showFromDateDialog() {

        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                StringBuffer strBuf = new StringBuffer();
                strBuf.append("Select date is ");
                strBuf.append(year);
                strBuf.append("-");
                strBuf.append(month + 1);
                strBuf.append("-");
                strBuf.append(dayOfMonth);


            }
        };

        // Get current year, month and day.
        Calendar now = Calendar.getInstance();
        final int year2 = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DAY_OF_MONTH);

        // Create the new DatePickerDialog instance.
        /*DatePickerDialog datePickerDialog = new DatePickerDialog(SalesManageActivity.this, android.R.style.Theme_Holo_Dialog, onDateSetListener, year, month, day);*/
        final DatePickerDialog dialog = new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {

                String sdate = (m + 1) + "/" + d + "/" + y;
                int s = (m + 1) + d + y;

                int month = (m + 1);


                String date = y + "-" + month + "-" + d;
                fromDate = date+"T00:00:00";
                binding.tvFromDate.setText(Util.changeAnyDateFormat(date, "yyyy-MM-dd", "dd MMM,yyyy"));




                //  pref.saveDOJ(sdate);


            }
        }, year2, month, day);


        // Set dialog icon and title.
        dialog.setIcon(R.drawable.clock);
        dialog.setTitle("Please select date.");
        dialog.getDatePicker();
        dialog.getDatePicker().setMaxDate(now.getTimeInMillis()-1000);


        // Popup the dialog.

        dialog.show();

    }

    private void showToDateDialog() {

        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                StringBuffer strBuf = new StringBuffer();
                strBuf.append("Select date is ");
                strBuf.append(year);
                strBuf.append("-");
                strBuf.append(month + 1);
                strBuf.append("-");
                strBuf.append(dayOfMonth);


            }
        };

        // Get current year, month and day.
        Calendar now = Calendar.getInstance();
        final int year2 = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DAY_OF_MONTH);

        // Create the new DatePickerDialog instance.
        /*DatePickerDialog datePickerDialog = new DatePickerDialog(SalesManageActivity.this, android.R.style.Theme_Holo_Dialog, onDateSetListener, year, month, day);*/
        final DatePickerDialog dialog = new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {

                String sdate = (m + 1) + "/" + d + "/" + y;
                int s = (m + 1) + d + y;

                int month = (m + 1);


                String date = y + "-" + month + "-" + d;
                toDate = date+"T00:00:00";
                binding.tvToDate.setText(Util.changeAnyDateFormat(date, "yyyy-MM-dd", "dd MMM,yyyy"));




                //  pref.saveDOJ(sdate);


            }
        }, year2, month, day);


        // Set dialog icon and title.
        dialog.setIcon(R.drawable.clock);
        dialog.setTitle("Please select date.");
        dialog.getDatePicker();
        dialog.getDatePicker().setMaxDate(now.getTimeInMillis()-1000);


        // Popup the dialog.

        dialog.show();

    }

    @Override
    public void onClick(View v) {
        if (v==binding.llFromDate){
            showFromDateDialog();
        }else if (v==binding.llToDate){
            showToDateDialog();
        }else if (v==binding.tvSearch){
            if (!fromDate.equals("")){
                if (!toDate.equals("")){
                    Intent intent=new Intent(getContext(), AbsentSummaryDetailsActivity.class);
                    intent.putExtra("periodid",periodID);
                    intent.putExtra("fromdate",fromDate);
                    intent.putExtra("todate",toDate);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }else {
                    Toast.makeText(getContext(),"Please select To Date",Toast.LENGTH_LONG).show();
                }

            }else {
                Toast.makeText(getContext(),"Please select From Date",Toast.LENGTH_LONG).show();
            }
        }

    }
}