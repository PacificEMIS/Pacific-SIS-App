package com.opensis.ui.teacher.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.opensis.R;
import com.opensis.adapter.AttendanceStudentAdapter;
import com.opensis.adapter.InputGradeStudentAdapter;
import com.opensis.model.AttendanceStudentModel;
import com.opensis.model.InputGradeStudentModel;
import com.opensis.model.MArkinPeriodSpinnerModel;
import com.opensis.model.SpinnerModel;
import com.opensis.model.TeacherGradeModel;
import com.opensis.others.parser.PostJsonDataParser;
import com.opensis.others.utility.AppData;
import com.opensis.others.utility.Pref;
import com.opensis.others.utility.Util;
import com.opensis.ui.common.activity.LoginActivity;
import com.opensis.ui.teacher.activity.TeacherDashboardActivity;
import com.suke.widget.SwitchButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class InputGradeFragment extends Fragment implements View.OnClickListener {


    View view;
    Pref pref;
    Spinner spMarkingPeriod;
    ArrayList<SpinnerModel>markingperiodmodulelist=new ArrayList<>();
    ArrayList<String>markingperiodlist=new ArrayList<>();

    int academicYear;
    String gradescaletype;
    int courseSectionID;

    RecyclerView rvItem;

    ArrayList<InputGradeStudentModel>itemList=new ArrayList<>();
    InputGradeStudentAdapter studentAdapter;
    ArrayList<String>gradelist=new ArrayList<>();
    ArrayList<SpinnerModel>gradeModellist=new ArrayList<>();
    TextView tvSubmit;
    String markingPeriodID;
    int courseID;
    ArrayList<String>obtanGradeList=new ArrayList<>();
    ArrayList<Integer>percentLidt=new ArrayList<>();
    ArrayList<String>commentList=new ArrayList<>();
    AlertDialog commentPopUp,markpopup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_inout_grade, container, false);
        initView();
        return view;
    }

    private void initView(){
        pref=new Pref(getContext());
        tvSubmit=(TextView)view.findViewById(R.id.tvSubmit);
        tvSubmit.setOnClickListener(this);
        spMarkingPeriod=(Spinner)view.findViewById(R.id.spMarkingPeriod);
        courseSectionID= getArguments().getInt("courseSectionID");
        gradescaletype=getArguments().getString("gradescaletype");
        courseID=getArguments().getInt("courseID");
        rvItem=(RecyclerView)view.findViewById(R.id.rvItem);
        rvItem.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));


        spMarkingPeriod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position>0){
                    try {
                        JSONObject object=new JSONObject();
                        object.put("_tenantName",pref.getTenatName());
                        object.put("_userName",pref.getName());
                        object.put("_token",pref.getToken());
                        object.put("tenantId",AppData.tenatID);
                        object.put("schoolId",pref.getSchoolID());
                        object.put("academicYear",pref.getAcademyYear());
                        getGradeList(object);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    markingPeriodID=markingperiodmodulelist.get(position).getItemID();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        com.suke.widget.SwitchButton switchButton = (com.suke.widget.SwitchButton)
                view.findViewById(R.id.switch_button);


        switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                //TODO do your job

                if (isChecked) {
                    studentAdapter = new InputGradeStudentAdapter(itemList, getContext(), gradelist, gradeModellist, InputGradeFragment.this, 1);

                }else {
                    studentAdapter = new InputGradeStudentAdapter(itemList, getContext(), gradelist, gradeModellist, InputGradeFragment.this, 2);

                }

                rvItem.setAdapter(studentAdapter);

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



                        }else {
                            String _token=response.optString("_token");
                            pref.saveToken(_token);
                            JSONObject jsonObject=new JSONObject();
                            try {
                                jsonObject.put("courseSectionId",courseSectionID);
                                jsonObject.put("_tenantName",pref.getTenatName());
                                jsonObject.put("_userName",pref.getName());
                                jsonObject.put("_token",pref.getToken());
                                jsonObject.put("tenantId",AppData.tenatID);
                                jsonObject.put("schoolId",pref.getSchoolID());
                                jsonObject.put("academicYear",pref.getAcademyYear());
                                jsonObject.put("markingPeriodStartDate",pref.getPeriodStartYear());
                                jsonObject.put("markingPeriodEndDate",pref.getPeriodEndYear());
                                getMarkingPeriodItem(jsonObject);
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

    @Override
    public void onResume() {
        super.onResume();
        JSONObject obj = new JSONObject();
        JSONObject accessobj = new JSONObject();
        try {
            obj.put("tenantId", AppData.tenatID);
            obj.put("userId", 0);
            obj.put("userAccessLog", accessobj);
            obj.put("_tenantName", pref.getTenatName());
            obj.put("_userName", pref.getName());
            obj.put("_token", pref.getToken());
            obj.put("schoolId", pref.getSchoolID());
            obj.put("email", pref.getEmail());
            getRefreshToken(obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void getMarkingPeriodItem(JSONObject jsonObject) {

        String url=pref.getAPI()+"MarkingPeriod/getMarkingPeriodsByCourseSection";


        new PostJsonDataParser(getContext(), Request.Method.POST, url,jsonObject, true,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("dashboardresponse",response.toString());
                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");
                        if (_failure){


                        }else {

                            markingperiodmodulelist=new ArrayList<>();
                            markingperiodlist=new ArrayList<>();
                            markingperiodlist.add("Marking Period");
                            markingperiodmodulelist.add(new SpinnerModel("0","0"));
                           JSONArray getMarkingPeriodView=response.optJSONArray("getMarkingPeriodView");
                           if (getMarkingPeriodView.length()>0){
                               for (int i=0;i<getMarkingPeriodView.length();i++){
                                   JSONObject obj=getMarkingPeriodView.optJSONObject(i);
                                   String text=obj.optString("text");
                                   String value=obj.optString("value");
                                   markingperiodlist.add(text);
                                   SpinnerModel spModel=new SpinnerModel(text,value);
                                   markingperiodmodulelist.add(spModel);
                               }

                               ArrayAdapter ad
                                       = new ArrayAdapter(
                                       getContext(),
                                       android.R.layout.simple_spinner_item,
                                       markingperiodlist);

                               ad.setDropDownViewResource(
                                       android.R.layout
                                               .simple_spinner_dropdown_item);


                               spMarkingPeriod.setAdapter(ad);
                           }
                        }






                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });




    }







    public void getStudentList(JSONObject jsonObject,ArrayList<String>gradeList,ArrayList<SpinnerModel>gradeModelList) {
        String url=pref.getAPI()+"StudentSchedule/getStudentListByCourseSection";

        new PostJsonDataParser(getContext(), Request.Method.POST, url,jsonObject, true,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("schoolresponse",response.toString());
                    try {
                        itemList.clear();
                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");
                        if (_failure){


                        }else {
                            JSONArray scheduleStudentForView = response.optJSONArray("scheduleStudentForView");
                            if (scheduleStudentForView.length() > 0) {
                                for (int i=0;i<scheduleStudentForView.length();i++){
                                    JSONObject studentOBJ=scheduleStudentForView.optJSONObject(i);
                                    String firstGivenName=studentOBJ.optString("firstGivenName");
                                    String lastFamilyName=studentOBJ.optString("lastFamilyName");
                                    String alternateId=Util.getFreshValue(studentOBJ.optString("alternateId"),"-");
                                    String gradeLevel=studentOBJ.optString("gradeLevel");
                                    String studentPhoto=Util.getFreshValue(studentOBJ.optString("studentPhoto"),"");
                                    int studentId=studentOBJ.optInt("studentId");
                                    int gradeId=studentOBJ.optInt("gradeId");
                                    int gradeScaleId=studentOBJ.optInt("gradeScaleId");

                                    InputGradeStudentModel studentModel=new InputGradeStudentModel();
                                    studentModel.setStudentName(firstGivenName+" "+lastFamilyName);
                                    studentModel.setStudenID(studentId);
                                    studentModel.setStudentAltID(alternateId);
                                    studentModel.setGrade(gradeLevel);
                                    studentModel.setPercent(0);

                                   studentModel.setStudentPhoto(studentPhoto);
                                   studentModel.setGradeId(gradeId);
                                   studentModel.setGradeScaleId(gradeScaleId);

                                   if (obtanGradeList.size()>0){
                                       studentModel.setObtainedGrade(obtanGradeList.get(i));
                                   }else {
                                       studentModel.setObtainedGrade("0");
                                   }

                                    if (percentLidt.size()>0){
                                        studentModel.setPercent(percentLidt.get(i));
                                    }else {
                                        studentModel.setPercent(0);
                                    }

                                    if (commentList.size()>0){
                                        studentModel.setTeacherComment(commentList.get(i));
                                    }else {
                                        studentModel.setTeacherComment("");
                                    }
                                    itemList.add(studentModel);

                                }

                                studentAdapter=new InputGradeStudentAdapter(itemList,getContext(),gradeList,gradeModelList,InputGradeFragment.this,1);
                                rvItem.setAdapter(studentAdapter);








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


    public void getGradeList(JSONObject jsonObject) {
        String url=pref.getAPI()+"Grade/getAllGradeScaleList";

        new PostJsonDataParser(getContext(), Request.Method.POST, url,jsonObject, true,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("schoolresponse",response.toString());
                    try {
                        itemList.clear();
                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");
                        if (_failure){


                        }else {
                            gradelist=new ArrayList<>();
                            gradeModellist=new ArrayList<>();
                            gradelist.add("Select");
                            gradeModellist.add(new SpinnerModel("0","0"));
                            JSONArray gradeScaleList = response.optJSONArray("gradeScaleList");
                            if (gradeScaleList.length() > 0) {
                                for (int i=0;i<gradeScaleList.length();i++){
                                    JSONObject gradeScaleListOBJ=gradeScaleList.optJSONObject(i);
                                    String gradeScaleName=gradeScaleListOBJ.optString("gradeScaleName");
                                    if (gradeScaleName.equalsIgnoreCase("Main")){
                                        JSONArray grade=gradeScaleListOBJ.optJSONArray("grade");
                                        for (int j=0;j<grade.length();j++){
                                            JSONObject gradeOBJ=grade.optJSONObject(j);
                                            String title=gradeOBJ.optString("title");
                                            String breakoff= String.valueOf(gradeOBJ.optInt("breakoff"));
                                            gradelist.add(title);
                                            SpinnerModel gradeModel=new SpinnerModel(title,breakoff);
                                            gradeModellist.add(gradeModel);
                                        }
                                    }

                                }

                               //////////

                                JSONArray studentFinalGradeList=new JSONArray();
                                JSONObject object=new JSONObject();
                                object.put("studentFinalGradeList",studentFinalGradeList);
                                object.put("markingPeriodId",markingPeriodID);
                                object.put("schoolId",pref.getSchoolID());
                                object.put("tenantId",AppData.tenatID);
                                object.put("courseId",courseID);
                                object.put("courseSectionId",courseSectionID);
                                object.put("calendarId",1);
                                object.put("_tenantName",pref.getTenatName());
                                object.put("_userName",pref.getName());
                                object.put("_token",pref.getToken());
                                object.put("academicYear",pref.getAcademyYear());

                                getStudentGradeList(object);








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

    public void setPercentage(int pos,int percent,String text){

        itemList.get(pos).setPercent(percent);
        itemList.get(pos).setObtainedGrade(text);
        studentAdapter.notifyDataSetChanged();




    }

    @Override
    public void onClick(View v) {
        if (v==tvSubmit){
          datasave();
        }
    }

    private void datasave() {
        JSONArray gradeArray = new JSONArray();


        JSONArray studentFinalGradeStandard=new JSONArray();
        JSONArray studentFinalGradeComments=new JSONArray();


        for (int j = 0; j < itemList.size(); j++) {

            JSONObject gradeOBJ = new JSONObject();
            try {
                gradeOBJ.put("studentFinalGradeStandard",studentFinalGradeStandard);
                gradeOBJ.put("studentId", itemList.get(j).getStudenID());
                gradeOBJ.put("gradeId", itemList.get(j).getGradeId());
                gradeOBJ.put("gradeScaleId", itemList.get(j).getGradeScaleId());
                gradeOBJ.put("percentMarks", itemList.get(j).getPercent());
                gradeOBJ.put("basedOnStandardGrade", true);
                gradeOBJ.put("studentFinalGradeSrlno", 0);
                gradeOBJ.put("gradeObtained", itemList.get(j).getObtainedGrade());
                gradeOBJ.put("teacherComment", itemList.get(j).getTeacherComment());
                gradeOBJ.put("studentFinalGradeComments",studentFinalGradeComments);

            } catch (JSONException e) {
                e.printStackTrace();
            }


            gradeArray.put(gradeOBJ);



        }

        JSONObject finalOBJ=new JSONObject();
        try {
            finalOBJ.put("studentFinalGradeList",gradeArray);
            finalOBJ.put("markingPeriodId",markingPeriodID);
            finalOBJ.put("schoolId",pref.getSchoolID());
            finalOBJ.put("tenantId",AppData.tenatID);
            finalOBJ.put("courseId",courseID);
            finalOBJ.put("courseSectionId",courseSectionID);
            finalOBJ.put("calendarId",1);
            finalOBJ.put("_tenantName",pref.getTenatName());
            finalOBJ.put("_userName",pref.getName());
            finalOBJ.put("_token",pref.getToken());
            finalOBJ.put("isPercent",false);
            finalOBJ.put("createdOrUpdatedBy","3d37d665-24dc-43e4-8d68-407b6d473d19");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Log.d("finalOBJ",finalOBJ.toString());
        postData(finalOBJ);
    }


    private void postData(JSONObject jsonObject){
        String url=pref.getAPI()+"InputFinalGrade/addUpdateStudentFinalGrade";

        new PostJsonDataParser(getContext(), Request.Method.POST, url,jsonObject, true,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("schoolresponse",response.toString());
                    try {
                        itemList.clear();
                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");
                        if (_failure){


                        }else {

                            Toast.makeText(getContext(),_message,Toast.LENGTH_LONG).show();
                            JSONArray studentFinalGradeList=new JSONArray();
                            JSONObject object=new JSONObject();
                            object.put("studentFinalGradeList",studentFinalGradeList);
                            object.put("markingPeriodId",markingPeriodID);
                            object.put("schoolId",pref.getSchoolID());
                            object.put("tenantId",AppData.tenatID);
                            object.put("courseId",courseID);
                            object.put("courseSectionId",courseSectionID);
                            object.put("calendarId",1);
                            object.put("_tenantName",pref.getTenatName());
                            object.put("_userName",pref.getName());
                            object.put("_token",pref.getToken());
                            object.put("academicYear",pref.getAcademyYear());

                            getStudentGradeList(object);

                        }






                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    public void getStudentGradeList(JSONObject jsonObject) {
        String url=pref.getAPI()+"InputFinalGrade/getAllStudentFinalGradeList";

        new PostJsonDataParser(getContext(), Request.Method.POST, url,jsonObject, true,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("schoolresponse",response.toString());
                    try {

                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");

                            commentList=new ArrayList<>();
                            obtanGradeList=new ArrayList<>();
                            percentLidt=new ArrayList<>();
                            JSONArray studentFinalGradeList=response.optJSONArray("studentFinalGradeList");
                            if (studentFinalGradeList.length()>0){
                                for (int i=0;i<studentFinalGradeList.length();i++){
                                    JSONObject obj=studentFinalGradeList.optJSONObject(i);
                                    String gradeObtained=Util.getFreshValue(obj.optString("gradeObtained"),"0");
                                    obtanGradeList.add(gradeObtained);
                                    int percentMarks=obj.optInt("percentMarks");
                                    percentLidt.add(percentMarks);
                                    String teacherComment=Util.getFreshValue(obj.optString("teacherComment"),"");
                                    commentList.add(teacherComment);
                                }
                            }

                            try {
                                JSONArray filterArray=new JSONArray();
                                JSONArray sectionArray=new JSONArray();
                                sectionArray.put(courseSectionID);
                                JSONObject obj=new JSONObject();
                                obj.put("pageNumber",1);
                                obj.put("_pageSize",10);
                                obj.put("sortingModel",null);
                                obj.put("filterParams",filterArray);
                                obj.put("courseSectionIds",sectionArray);
                                obj.put("profilePhoto",true);
                                obj.put("_tenantName",pref.getTenatName());
                                obj.put("_userName",pref.getName());
                                obj.put("_token",pref.getToken());
                                obj.put("tenantId",AppData.tenatID);
                                obj.put("schoolId",pref.getSchoolID());
                                getStudentList(obj,gradelist,gradeModellist);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }









                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }


    public void commentPopUp(int pos,String studentName,String comment) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext(), R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.attendance_comment_popup, null);
        dialogBuilder.setView(dialogView);
        TextView tvStudentName=(TextView)dialogView.findViewById(R.id.tvStudentName);
        tvStudentName.setText("Add Comment to "+studentName);
        EditText etComment=(EditText)dialogView.findViewById(R.id.etComment);
        etComment.setText(comment);
        TextView tvSubmit=(TextView)dialogView.findViewById(R.id.tvSubmit);
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemList.get(pos).setTeacherComment(etComment.getText().toString());
                studentAdapter.notifyDataSetChanged();
                commentPopUp.dismiss();
            }
        });

        LinearLayout lnCLose=(LinearLayout)dialogView.findViewById(R.id.lnCLose);
        lnCLose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commentPopUp.dismiss();
            }
        });





        commentPopUp = dialogBuilder.create();
        commentPopUp.setCancelable(true);
        Window window = commentPopUp.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        commentPopUp.show();
    }


    public void addMarkpopup(int pos,String studentName,String comment) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext(), R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.marks_popup, null);
        dialogBuilder.setView(dialogView);
        TextView tvStudentName=(TextView)dialogView.findViewById(R.id.tvStudentName);
        tvStudentName.setText("Add Marks to "+studentName);
        EditText etComment=(EditText)dialogView.findViewById(R.id.etComment);
        etComment.setText(comment);
        TextView tvSubmit=(TextView)dialogView.findViewById(R.id.tvSubmit);
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemList.get(pos).setPercent(Integer.parseInt(etComment.getText().toString().replace("%","")));

                int percent=Integer.parseInt(etComment.getText().toString().replace("%",""));
                if (percent>=90){
                    itemList.get(pos).setObtainedGrade("A");


                }else if (percent >= 80 && percent < 90){
                    itemList.get(pos).setObtainedGrade("B");


                }else if (percent >= 70 && percent < 80){
                    itemList.get(pos).setObtainedGrade("C");


                }else if (percent >= 60 && percent < 70){
                    itemList.get(pos).setObtainedGrade("D");


                }else if (percent >= 20 && percent < 60){
                    itemList.get(pos).setObtainedGrade("F");


                }else if (percent >= 0 && percent < 20){
                    itemList.get(pos).setObtainedGrade("Inc");


                }
                studentAdapter.notifyDataSetChanged();
                markpopup.dismiss();
            }
        });

        LinearLayout lnCLose=(LinearLayout)dialogView.findViewById(R.id.lnCLose);
        lnCLose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                markpopup.dismiss();
            }
        });





        markpopup = dialogBuilder.create();
        markpopup.setCancelable(true);
        Window window = markpopup.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        markpopup.show();
    }
}