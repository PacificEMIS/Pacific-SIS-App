package com.opensissas.ui.teacher.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.opensissas.R;
import com.opensissas.adapter.ClassAssignmentDetailsAdapter;
import com.opensissas.model.AssignmentDetailsModel;
import com.opensissas.others.parser.PostJsonDataParser;
import com.opensissas.others.utility.AppData;
import com.opensissas.others.utility.Pref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class AssignmentDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView rvItem;
    ArrayList<AssignmentDetailsModel> itemList=new ArrayList<>();
    TextView tvCreateAssignment,tvCancel,tvNoRecord,tvAssignmentName,tvEditCancel;
    LinearLayout lnAssignment,lnCreateAssignment,lnEditAssignment;
    String assignmentDetails,assignmentName;
    JSONArray assignmentArray;
    int assignmentTypeID,coursectionID;
    EditText etTitle,etPoint,etEditTitle,etEditPoint;
    TextView tvAssignedDate,tvDueDate,tvSubmit,tvEditAssignedDate,tvEditDueDate,tvEditSubmit;
    LinearLayout lnAssignedDate,lnDueDate,lnEditAssignedDate,lnEditDueDate;
    String assigedDate="";
    String dueDate="";
    Pref pref;
    AlertDialog alerDialog1;
    ClassAssignmentDetailsAdapter assignmentAdapter;
    ImageView imgBack;
    LinearLayout lnCLose,lnEditCLose;
    int assignmentId;
    String assignmentTypeName,points,assignedate,duedate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_details);
        initView();
    }

    private void initView(){
        imgBack=(ImageView)findViewById(R.id.imgBack);
        imgBack.setOnClickListener(this);
        pref=new Pref(AssignmentDetailsActivity.this);
        coursectionID=getIntent().getIntExtra("coursectionID",0);
        assignmentTypeID=getIntent().getIntExtra("assignmentTypeID",0);
        assignmentDetails=getIntent().getStringExtra("assignmentDetails");
        assignmentName=getIntent().getStringExtra("assignmentName");
        tvAssignmentName=(TextView)findViewById(R.id.tvAssignmentName);
        tvAssignmentName.setText(assignmentName);
        if (assignmentDetails!=null) {
            try {
                assignmentArray = new JSONArray(assignmentDetails);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        lnCreateAssignment=(LinearLayout)findViewById(R.id.lnCreateAssignment);
        lnEditAssignment=(LinearLayout)findViewById(R.id.lnEditAssignment);
        lnCLose=(LinearLayout)findViewById(R.id.lnCLose);
        lnEditCLose=(LinearLayout)findViewById(R.id.lnEditCLose);
        lnAssignment=(LinearLayout)findViewById(R.id.lnAssignment);
        tvCreateAssignment=(TextView)findViewById(R.id.tvCreateAssignment);
        tvNoRecord=(TextView)findViewById(R.id.tvNoRecord);
        tvCancel=(TextView)findViewById(R.id.tvCancel);
        rvItem=(RecyclerView)findViewById(R.id.rvItem);
        rvItem.setLayoutManager(new LinearLayoutManager(AssignmentDetailsActivity.this, LinearLayoutManager.VERTICAL, false));
        getAssignmentDetails();
        lnDueDate=(LinearLayout)findViewById(R.id.lnDueDate);
        lnAssignedDate=(LinearLayout)findViewById(R.id.lnAssignedDate);

        lnEditDueDate=(LinearLayout)findViewById(R.id.lnEditDueDate);
        lnEditAssignedDate=(LinearLayout)findViewById(R.id.lnEditAssignedDate);

        etPoint=(EditText)findViewById(R.id.etPoint);
        etTitle=(EditText)findViewById(R.id.etTitle);

        etEditPoint=(EditText)findViewById(R.id.etEditPoint);
        etEditTitle=(EditText)findViewById(R.id.etEditTitle);

        tvAssignedDate=(TextView)findViewById(R.id.tvAssignedDate);
        tvDueDate=(TextView)findViewById(R.id.tvDueDate);
        tvSubmit=(TextView)findViewById(R.id.tvSubmit);
        tvEditCancel=(TextView)findViewById(R.id.tvEditCancel);

        tvEditAssignedDate=(TextView)findViewById(R.id.tvEditAssignedDate);
        tvEditDueDate=(TextView)findViewById(R.id.tvEditDueDate);
        tvEditSubmit=(TextView)findViewById(R.id.tvEditSubmit);


        lnAssignedDate.setOnClickListener(this);
        lnDueDate.setOnClickListener(this);
        tvCreateAssignment.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
        lnCLose.setOnClickListener(this);


        lnEditAssignedDate.setOnClickListener(this);
        lnEditDueDate.setOnClickListener(this);
        tvEditCancel.setOnClickListener(this);
        tvEditSubmit.setOnClickListener(this);
        lnEditCLose.setOnClickListener(this);
    }

    private void setAdapter(){
         assignmentAdapter=new ClassAssignmentDetailsAdapter(itemList,AssignmentDetailsActivity.this);
        rvItem.setAdapter(assignmentAdapter);
    }

    @Override
    public void onClick(View view) {
        if (view==tvCreateAssignment){
            lnCreateAssignment.setVisibility(View.VISIBLE);
        }else if (view==tvCancel){
            lnCreateAssignment.setVisibility(View.GONE);
        }else if (view==lnCLose){
            lnCreateAssignment.setVisibility(View.GONE);
        }else if (view==lnAssignedDate){
              showAssignedDateDialog();
        }else if (view==lnDueDate){
              showDueDateDialog();
        }else if (view==tvSubmit){
            if (etTitle.getText().toString().length()>0){
                if (etPoint.getText().toString().length()>0){
                    if (!assigedDate.equals("")){
                        if (!dueDate.equals("")){
                            JSONObject assignmentobj=new JSONObject();
                            JSONObject obj=new JSONObject();
                            try {
                                assignmentobj.put("assignmentTitle",etTitle.getText().toString());
                                assignmentobj.put("points",etPoint.getText().toString());
                                assignmentobj.put("assignmentTypeId",assignmentTypeID);
                                assignmentobj.put("assignmentDate",assigedDate);
                                assignmentobj.put("dueDate",dueDate);
                                assignmentobj.put("assignmentDescription","");
                                assignmentobj.put("schoolId",pref.getSchoolID());
                                assignmentobj.put("courseSectionId",coursectionID);
                                assignmentobj.put("tenantId",pref.getTenatID());
                                assignmentobj.put("staffId",pref.getUserID());
                                assignmentobj.put("createdBy","2199863c-a77b-4e86-bad3-a524424d969b");
                                obj.put("assignment",assignmentobj);
                                obj.put("_userName",pref.getName());
                                obj.put("_tenantName",pref.getTenatName());
                                obj.put("_token",pref.getToken());
                                obj.put("_academicYear",pref.getAcademicYear());
                                obj.put("tenantId",pref.getTenatID());
                                obj.put("schoolId",pref.getSchoolID());
                                addAssignemnts(obj);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }else {
                            Toast.makeText(AssignmentDetailsActivity.this,"Please Select Due Date",Toast.LENGTH_LONG).show();
                        }

                    }else {
                        Toast.makeText(AssignmentDetailsActivity.this,"Please Select Assigned Date",Toast.LENGTH_LONG).show();
                    }


                }else {
                    Toast.makeText(AssignmentDetailsActivity.this,"Please Enter Points",Toast.LENGTH_LONG).show();

                }

            }else {
                Toast.makeText(AssignmentDetailsActivity.this,"Please Enter Assignment Title",Toast.LENGTH_LONG).show();
            }

        }else if (view==imgBack){
            onBackPressed();
        }else if (view==tvEditCancel){
            lnEditAssignment.setVisibility(View.GONE);
        }else if (view==lnEditCLose){
            lnEditAssignment.setVisibility(View.GONE);

        }else if (view==lnEditAssignedDate){
            showAssignedDateDialog();
        }else if (view==lnEditDueDate){
            showDueDateDialog();
        }else if (view==tvEditSubmit){
            if (etEditTitle.getText().toString().length()>0){
                if (etEditPoint.getText().toString().length()>0){
                    if (!assigedDate.equals("")){
                        if (!dueDate.equals("")){
                            JSONObject assignmentobj=new JSONObject();
                            JSONObject obj=new JSONObject();
                            try {
                                assignmentobj.put("assignmentTitle",etEditTitle.getText().toString());
                                assignmentobj.put("points",Integer.parseInt(etEditPoint.getText().toString()));
                                assignmentobj.put("assignmentTypeId",assignmentTypeID);
                                assignmentobj.put("assignmentDate",assigedDate);
                                assignmentobj.put("dueDate",dueDate);
                                assignmentobj.put("assignmentDescription","");
                                assignmentobj.put("schoolId",pref.getSchoolID());
                                assignmentobj.put("courseSectionId",coursectionID);
                                assignmentobj.put("tenantId",pref.getTenatID());
                                assignmentobj.put("staffId",pref.getUserID());
                                assignmentobj.put("assignmentId",assignmentId);
                                assignmentobj.put("updatedBy","f2bd0177-3e85-467d-b838-a4cba18aa791");
                                obj.put("assignment",assignmentobj);
                                obj.put("_userName",pref.getName());
                                obj.put("_tenantName",pref.getTenatName());
                                obj.put("_token",pref.getToken());
                                obj.put("_academicYear",pref.getAcademicYear());
                                obj.put("tenantId",pref.getTenatID());
                                obj.put("schoolId",pref.getSchoolID());
                                updateAssignemnts(obj);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }else {
                            Toast.makeText(AssignmentDetailsActivity.this,"Please Select Due Date",Toast.LENGTH_LONG).show();
                        }

                    }else {
                        Toast.makeText(AssignmentDetailsActivity.this,"Please Select Assigned Date",Toast.LENGTH_LONG).show();
                    }


                }else {
                    Toast.makeText(AssignmentDetailsActivity.this,"Please Enter Points",Toast.LENGTH_LONG).show();

                }

            }else {
                Toast.makeText(AssignmentDetailsActivity.this,"Please Enter Assignment Title",Toast.LENGTH_LONG).show();
            }

        }

    }

    private void getAssignmentDetails(){
        rvItem.setVisibility(View.VISIBLE);
        tvNoRecord.setVisibility(View.GONE);
        if (assignmentArray.length()>0){
            for (int i=0;i<assignmentArray.length();i++){
                JSONObject OBJ=assignmentArray.optJSONObject(i);
                String assignmentTitle=OBJ.optString("assignmentTitle");
                String points= String.valueOf(OBJ.optInt("points"));
                int assignmentId=OBJ.optInt("assignmentId");
                String assignmentDate=OBJ.optString("assignmentDate").replace("T00:00:00","");
                String dueDate=OBJ.optString("dueDate").replace("T00:00:00","");
                AssignmentDetailsModel assignmentDetailsModel=new AssignmentDetailsModel();
                assignmentDetailsModel.setAssignmentdetailsName(assignmentTitle);
                assignmentDetailsModel.setPoints(points);
                assignmentDetailsModel.setAssignedDate(assignmentDate);
                assignmentDetailsModel.setDueDate(dueDate);
                assignmentDetailsModel.setAssignmentId(assignmentId);
                itemList.add(assignmentDetailsModel);

            }
            setAdapter();
        }else {
            Toast.makeText(AssignmentDetailsActivity.this,"No Records Found",Toast.LENGTH_LONG).show();
            rvItem.setVisibility(View.GONE);
            tvNoRecord.setVisibility(View.VISIBLE);
        }

    }

    private void showAssignedDateDialog() {

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
        final DatePickerDialog dialog = new DatePickerDialog(AssignmentDetailsActivity.this, android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {

                String sdate = (m + 1) + "/" + d + "/" + y;
                int s = (m + 1) + d + y;

                int month = (m + 1);


                String date = y + "-" + month + "-" + d;
                tvAssignedDate.setText(date);
                tvEditAssignedDate.setText(date);
                assigedDate=date;


                //  pref.saveDOJ(sdate);


            }
        }, year2, month, day);


        // Set dialog icon and title.
        dialog.setIcon(R.drawable.clock);
        dialog.setTitle("Please select date.");
        dialog.getDatePicker();

        // Popup the dialog.

        dialog.show();

    }

    private void showDueDateDialog() {

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
        final DatePickerDialog dialog = new DatePickerDialog(AssignmentDetailsActivity.this, android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {

                String sdate = (m + 1) + "/" + d + "/" + y;
                int s = (m + 1) + d + y;

                int month = (m + 1);


                String date = y + "-" + month + "-" + d;
                tvDueDate.setText(date);
                tvEditDueDate.setText(date);
                dueDate=date;

                //  pref.saveDOJ(sdate);


            }
        }, year2, month, day);


        // Set dialog icon and title.
        dialog.setIcon(R.drawable.clock);
        dialog.setTitle("Please select date.");
        dialog.getDatePicker();

        // Popup the dialog.

        dialog.show();

    }

    public void addAssignemnts(JSONObject jsonObject) {

        String url=pref.getAPI()+"StaffPortalAssignment/addAssignment";

        new PostJsonDataParser(AssignmentDetailsActivity.this, Request.Method.POST, url,jsonObject, true,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("addassignmnet",response.toString());
                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");
                        if (_failure){
                            Toast.makeText(AssignmentDetailsActivity.this,_message,Toast.LENGTH_LONG).show();



                        }else {

                            Toast.makeText(AssignmentDetailsActivity.this,_message,Toast.LENGTH_LONG).show();
                            lnAssignment.setVisibility(View.VISIBLE);
                            lnCreateAssignment.setVisibility(View.GONE);
                            rvItem.setVisibility(View.VISIBLE);
                            tvNoRecord.setVisibility(View.GONE);
                            AssignmentDetailsModel assignmentDetailsModel=new AssignmentDetailsModel();
                            assignmentDetailsModel.setDueDate(tvDueDate.getText().toString());
                            assignmentDetailsModel.setAssignedDate(tvAssignedDate.getText().toString());
                            assignmentDetailsModel.setPoints(etPoint.getText().toString());
                            assignmentDetailsModel.setAssignmentdetailsName(etTitle.getText().toString());
                            itemList.add(assignmentDetailsModel);
                            setAdapter();



                        }






                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }


    public void updateAssignemnts(JSONObject jsonObject) {

        String url=pref.getAPI()+"StaffPortalAssignment/updateAssignment";

        new PostJsonDataParser(AssignmentDetailsActivity.this, Request.Method.PUT, url,jsonObject, true,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("addassignmnet",response.toString());
                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");
                        if (_failure){
                            Toast.makeText(AssignmentDetailsActivity.this,_message,Toast.LENGTH_LONG).show();



                        }else {

                            Toast.makeText(AssignmentDetailsActivity.this,_message,Toast.LENGTH_LONG).show();
                            onBackPressed();




                        }






                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }


    public void editWorkoutPopup(int pos) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AssignmentDetailsActivity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.edit_delete_popup, null);
        dialogBuilder.setView(dialogView);
        LinearLayout lnDelete=(LinearLayout)dialogView.findViewById(R.id.lnDelete);
        TextView tvDelte=(TextView)dialogView.findViewById(R.id.tvDelte);
        tvDelte.setText("Delete Assignment");
        TextView tvEdit=(TextView)dialogView.findViewById(R.id.tvEdit);
        tvEdit.setText("Edit Assignment");

        LinearLayout lnEdit=(LinearLayout)dialogView.findViewById(R.id.lnEdit);
        lnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assignmentId=itemList.get(pos).getAssignmentId();

                assigedDate=itemList.get(pos).getAssignedDate();
                dueDate=itemList.get(pos).getDueDate();
                etEditTitle.setText(itemList.get(pos).getAssignmentdetailsName());
                etEditPoint.setText(itemList.get(pos).getPoints());
                tvEditAssignedDate.setText(itemList.get(pos).getAssignedDate());
                tvEditDueDate.setText(itemList.get(pos).getDueDate());
                lnEditAssignment.setVisibility(View.VISIBLE);
                alerDialog1.dismiss();

            }
        });

        lnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerDialog1.dismiss();
                JSONObject obj=new JSONObject();
                JSONObject assesmentOBJ=new JSONObject();
                try {
                    assesmentOBJ.put("courseSectionId",coursectionID);
                    assesmentOBJ.put("assignmentId",itemList.get(pos).getAssignmentId());
                    assesmentOBJ.put("assignmentTypeId",assignmentTypeID);
                    assesmentOBJ.put("schoolId",pref.getSchoolID());
                    assesmentOBJ.put("tenantId",pref.getTenatID());
                    obj.put("assignment",assesmentOBJ);
                    obj.put("_tenantName",pref.getTenatName());
                    obj.put("_userName",pref.getName());
                    obj.put("_token",pref.getToken());
                    obj.put("tenantId",pref.getTenatID());
                    obj.put("schoolId",pref.getSchoolID());
                    deleteAssignemntsType(obj,pos);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });




        alerDialog1 = dialogBuilder.create();
        alerDialog1.setCancelable(true);
        Window window = alerDialog1.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        alerDialog1.show();
    }

    public void deleteAssignemntsType(JSONObject jsonObject,int pos) {

        String url=pref.getAPI()+"StaffPortalAssignment/deleteAssignment";

        new PostJsonDataParser(AssignmentDetailsActivity.this, Request.Method.POST, url,jsonObject, true,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("addassignmnet",response.toString());
                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");
                        if (_failure){
                            Toast.makeText(AssignmentDetailsActivity.this,_message,Toast.LENGTH_LONG).show();
                            alerDialog1.dismiss();



                        }else {

                            Toast.makeText(AssignmentDetailsActivity.this,_message,Toast.LENGTH_LONG).show();
                            itemList.remove(pos);
                            assignmentAdapter.notifyDataSetChanged();



                        }






                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }
}