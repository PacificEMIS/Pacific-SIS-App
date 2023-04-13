package com.opensis.ui.teacher.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.opensis.R;
import com.opensis.adapter.StudentAdapter;
import com.opensis.model.SpinnerModel;
import com.opensis.model.StudentsModel;
import com.opensis.model.TeacheSchoolModel;
import com.opensis.others.parser.PostJsonDataParser;
import com.opensis.others.utility.Api;
import com.opensis.others.utility.AppData;
import com.opensis.others.utility.Pref;
import com.opensis.others.utility.Util;
import com.opensis.ui.teacher.activity.AssignmentDetailsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class StudentsFragment extends Fragment implements View.OnClickListener {
    View view;
    RecyclerView rvStudents;
    ArrayList<StudentsModel> itmList=new ArrayList<>();
    LinearLayout lnStudents,lnSearch;
    ImageView imgFilter;
    JSONObject jsonObject=new JSONObject();
    Pref pref;
    LinearLayout lnStudentSearch;
    ImageView imgSearch;
    StudentAdapter studentAdapter;
    EditText etSearch;
    Spinner spGender,spRace,spEthnicity,spMartialStatus,spCountryofBirth,spNationality,spFrstLanguage,spScndLanguage,spThirdLanguage;
    ArrayList<String>genderList=new ArrayList<>();
    ArrayList<String>raceList=new ArrayList<>();
    ArrayList<String>enthnicityList=new ArrayList<>();
    ArrayList<String>martialStatusList=new ArrayList<>();
    ArrayList<String>countryList=new ArrayList<>();
    ArrayList<SpinnerModel>mcountryList=new ArrayList<>();

    ArrayList<String>nationalityList=new ArrayList<>();
    ArrayList<SpinnerModel>mnationalityList=new ArrayList<>();

    ArrayList<String>frstLanguageList=new ArrayList<>();
    ArrayList<SpinnerModel>mfrstLanguageList=new ArrayList<>();


    ArrayList<String>scndLanguageList=new ArrayList<>();
    ArrayList<SpinnerModel>mscndLanguageList=new ArrayList<>();

    ArrayList<String>thrdLanguageList=new ArrayList<>();
    ArrayList<SpinnerModel>mthrdLanguageList=new ArrayList<>();

    EditText etFirstName,etMiddlleName,etLastName,etStudentID,etStudentAltID,etAdmissionNumber,etRollNumber;

    LinearLayout llFromDate,llToDate,lnDOB;

    TextView tvFormDate,tvToDate,tvDOB,tvSearch;

    String startDate="",endDate="",dob="";

    String gender="",maritalStatus="",race="",ethnicity="";
    int firstLanguageId=0,secondLanguageId=0,thirdLanguageId=0,countryOfBirth=0,nationality=0;
    LinearLayout lnNoData;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_students, container, false);
        initView();
        ONCLICK();
        return view;
    }

    private void initView(){
        pref=new Pref(getContext());
        lnNoData=(LinearLayout)view.findViewById(R.id.lnNoData);
        etFirstName=(EditText)view.findViewById(R.id.etFirstName);
        etMiddlleName=(EditText)view.findViewById(R.id.etMiddlleName);
        etLastName=(EditText)view.findViewById(R.id.etLastName);
        etStudentID=(EditText)view.findViewById(R.id.etStudentID);
        etStudentAltID=(EditText)view.findViewById(R.id.etStudentAltID);
        etAdmissionNumber=(EditText)view.findViewById(R.id.etAdmissionNumber);
        etRollNumber=(EditText)view.findViewById(R.id.etRollNumber);

        llFromDate=(LinearLayout)view.findViewById(R.id.llFromDate);
        llToDate=(LinearLayout)view.findViewById(R.id.llToDate);
        lnDOB=(LinearLayout)view.findViewById(R.id.lnDOB);

        tvFormDate=(TextView)view.findViewById(R.id.tvFormDate);
        tvToDate=(TextView)view.findViewById(R.id.tvToDate);
        tvDOB=(TextView)view.findViewById(R.id.tvDOB);
        tvSearch=(TextView)view.findViewById(R.id.tvSearch);

        spThirdLanguage=(Spinner)view.findViewById(R.id.spThirdLanguage);
        spScndLanguage=(Spinner)view.findViewById(R.id.spScndLanguage);
        spFrstLanguage=(Spinner)view.findViewById(R.id.spFrstLanguage);
        spNationality=(Spinner)view.findViewById(R.id.spNationality);
        spGender=(Spinner)view.findViewById(R.id.spGender);
        spCountryofBirth=(Spinner)view.findViewById(R.id.spCountryofBirth);
        spRace=(Spinner)view.findViewById(R.id.spRace);
        spEthnicity=(Spinner)view.findViewById(R.id.spEthnicity);
        spMartialStatus=(Spinner)view.findViewById(R.id.spMartialStatus);
        etSearch=(EditText)view.findViewById(R.id.etSearch);
        imgFilter=(ImageView)view.findViewById(R.id.imgFilter);
        imgSearch=(ImageView)view.findViewById(R.id.imgSearch);
        lnSearch=(LinearLayout)view.findViewById(R.id.lnSearch);
        lnStudentSearch=(LinearLayout)view.findViewById(R.id.lnStudentSearch);
        lnStudents=(LinearLayout)view.findViewById(R.id.lnStudents);
        rvStudents=(RecyclerView)view.findViewById(R.id.rvStudents);
        rvStudents.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        jsonObject=new JSONObject();
        JSONArray filterParamsArray=new JSONArray();
        JSONArray courseSectionIdsArray=new JSONArray();
        try {
            jsonObject.put("pageNumber",0);
            jsonObject.put("_pageSize",0);
            jsonObject.put("sortingModel",null);
            jsonObject.put("staffId",pref.getUserID());
            jsonObject.put("academicYear",pref.getAcademyYear());
            jsonObject.put("_tenantName", pref.getTenatName());
            jsonObject.put("tenantId", AppData.tenatID);
            jsonObject.put("_userName", pref.getName());
            jsonObject.put("_token", pref.getToken());
            jsonObject.put("schoolId", pref.getSchoolID());
            jsonObject.put("filterParams",filterParamsArray);
            jsonObject.put("courseSectionIds",courseSectionIdsArray);
            getStudentList(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        imgFilter.setOnClickListener(this);
        imgSearch.setOnClickListener(this);
        llFromDate.setOnClickListener(this);
        llToDate.setOnClickListener(this);
        lnDOB.setOnClickListener(this);
        tvSearch.setOnClickListener(this);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // filter your list from your input
                filter(s.toString());
                //you can use runnable postDelayed like 500 ms to delay search text
            }
        });
    }

    private void ONCLICK(){
        spGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position>0){
                    gender=genderList.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spRace.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position>0){
                    race=raceList.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spEthnicity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position>0){
                    ethnicity=enthnicityList.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spMartialStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position>0){
                    maritalStatus=martialStatusList.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spCountryofBirth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position>0){
                    countryOfBirth= Integer.parseInt(mcountryList.get(position).getItemID());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spNationality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position>0){
                    nationality= Integer.parseInt(mnationalityList.get(position).getItemID());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spFrstLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position>0){
                    firstLanguageId= Integer.parseInt(mfrstLanguageList.get(position).getItemID());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spScndLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position>0){
                    secondLanguageId= Integer.parseInt(mscndLanguageList.get(position).getItemID());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spThirdLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position>0){
                    thirdLanguageId= Integer.parseInt(mthrdLanguageList.get(position).getItemID());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setAdapter(){
         studentAdapter=new StudentAdapter(itmList,getContext());
        rvStudents.setAdapter(studentAdapter);
    }

    @Override
    public void onClick(View view) {
        if (view==imgFilter){
            lnSearch.setVisibility(View.VISIBLE);
            lnStudents.setVisibility(View.GONE);
            JSONObject obj=new JSONObject();
            try {
                obj.put("schoolId",pref.getSchoolID());
                obj.put("_token",pref.getToken());
                obj.put("lovName","Gender");
                obj.put("_tenantName",pref.getTenatName());
                obj.put("_userName",pref.getName());
                obj.put("tenantId",AppData.tenatID);
                getGender(obj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else if (view==imgSearch){
            lnStudentSearch.setVisibility(View.VISIBLE);
        }else if (view==llFromDate){
            showStartDateDialog();
        }else if (view==llToDate){
            showEndDateDialog();
        }else if (view==lnDOB){
            showDOBDateDialog();
        }else if (view==tvSearch){
            lnSearch.setVisibility(View.GONE);
            lnStudents.setVisibility(View.VISIBLE);
            itmList.clear();
            JSONArray filterParamsArray=new JSONArray();
            JSONArray courseSectionIdsArray=new JSONArray();
            JSONObject filterfrstnameOBJ=new JSONObject();
            JSONObject filtermiddlenameOBJ=new JSONObject();
            JSONObject filterlastnameOBJ=new JSONObject();
            JSONObject filterstudentidOBJ=new JSONObject();
            JSONObject filterstudentaltidOBJ=new JSONObject();
            JSONObject filteradmissionOBJ=new JSONObject();
            JSONObject filterrollOBJ=new JSONObject();
            JSONObject filterdobstrtOBJ=new JSONObject();
            JSONObject filterdobendOBJ=new JSONObject();
            JSONObject filterdobOBJ=new JSONObject();
            JSONObject filtergenderOBJ=new JSONObject();
            JSONObject filtermartialOBJ=new JSONObject();
            JSONObject filterfrstlanguageOBJ=new JSONObject();
            JSONObject filterscndlanguageOBJ=new JSONObject();
            JSONObject filterthrdlanguageOBJ=new JSONObject();
            JSONObject filtercountryOBJ=new JSONObject();
            JSONObject filternationalityOBJ=new JSONObject();
            JSONObject filterraceOBJ=new JSONObject();
            JSONObject filterethnicityOBJ=new JSONObject();
            try {
                //frtsname
                if (etFirstName.getText().toString().length()>0) {
                    filterfrstnameOBJ.put("filterOption", 1);
                    filterfrstnameOBJ.put("columnName", "firstGivenName");
                    filterfrstnameOBJ.put("filterValue", etFirstName.getText().toString());
                }

                //mdllename
                if (etMiddlleName.getText().toString().length()>0) {
                    filtermiddlenameOBJ.put("filterOption", 1);
                    filtermiddlenameOBJ.put("columnName", "middleName");
                    filtermiddlenameOBJ.put("filterValue", etMiddlleName.getText().toString());
                }

                //lastname


                if (etLastName.getText().toString().length()>0) {
                    filterlastnameOBJ.put("filterOption", 1);
                    filterlastnameOBJ.put("columnName", "lastFamilyName");
                    filterlastnameOBJ.put("filterValue", etLastName.getText().toString());
                }

                //studentInternalId

                if (etStudentID.getText().toString().length()>0) {
                    filterstudentidOBJ.put("filterOption", 1);
                    filterstudentidOBJ.put("columnName", "studentInternalId");
                    filterstudentidOBJ.put("filterValue", etStudentID.getText().toString());
                }

                //alternateId
                if (etStudentAltID.getText().toString().length()>0) {
                    filterstudentaltidOBJ.put("filterOption", 1);
                    filterstudentaltidOBJ.put("columnName", "alternateId");
                    filterstudentaltidOBJ.put("filterValue", etStudentAltID.getText().toString());
                }

                //admissionNumber

                if (etAdmissionNumber.getText().toString().length()>0) {
                    filteradmissionOBJ.put("filterOption", 1);
                    filteradmissionOBJ.put("columnName", "admissionNumber");
                    filteradmissionOBJ.put("filterValue", etAdmissionNumber.getText().toString());
                }

                //rollNumber

                if (etRollNumber.getText().toString().length()>0) {
                    filterrollOBJ.put("filterOption", 1);
                    filterrollOBJ.put("columnName", "rollNumber");
                    filterrollOBJ.put("filterValue", etRollNumber.getText().toString());
                }
                //dobStartDate

                if (!startDate.equals("")) {
                    filterdobstrtOBJ.put("filterOption", 1);
                    filterdobstrtOBJ.put("columnName", "dobStartDate");
                    filterdobstrtOBJ.put("filterValue", startDate+"T18:30:00.000Z");
                }

                //dobEndDate

                if (!endDate.equals("")) {
                    filterdobendOBJ.put("filterOption", 1);
                    filterdobendOBJ.put("columnName", "dobEndDate");
                    filterdobendOBJ.put("filterValue", endDate+"T18:30:00.000Z");
                }

                //dob


                if (!dob.equals("")) {
                    filterdobOBJ.put("filterOption", 1);
                    filterdobOBJ.put("columnName", "dob");
                    filterdobOBJ.put("filterValue", dob);
                }

                //gender

                if (!gender.equals("")) {
                    filtergenderOBJ.put("filterOption", 1);
                    filtergenderOBJ.put("columnName", "gender");
                    filtergenderOBJ.put("filterValue", gender);
                }

                //maritalStatus

                if (!maritalStatus.equals("")) {
                    filtermartialOBJ.put("filterOption", 1);
                    filtermartialOBJ.put("columnName", "maritalStatus");
                    filtermartialOBJ.put("filterValue", maritalStatus);
                }

                //firstLanguageId

                if (firstLanguageId!=0) {
                    filterfrstlanguageOBJ.put("filterOption", 1);
                    filterfrstlanguageOBJ.put("columnName", "firstLanguageId");
                    filterfrstlanguageOBJ.put("filterValue", firstLanguageId);
                }

                //secondLanguageId

                if (secondLanguageId!=0) {
                    filterscndlanguageOBJ.put("filterOption", 1);
                    filterscndlanguageOBJ.put("columnName", "secondLanguageId");
                    filterscndlanguageOBJ.put("filterValue", secondLanguageId);
                }
                //thirdLanguageId

                if (thirdLanguageId!=0) {
                    filterthrdlanguageOBJ.put("filterOption", 1);
                    filterthrdlanguageOBJ.put("columnName", "thirdLanguageId");
                    filterthrdlanguageOBJ.put("filterValue", thirdLanguageId);
                }
                //countryOfBirth

                if (countryOfBirth!=0) {
                    filtercountryOBJ.put("filterOption", 1);
                    filtercountryOBJ.put("columnName", "countryOfBirth");
                    filtercountryOBJ.put("filterValue", countryOfBirth);
                }
                //nationality

                if (nationality!=0) {
                    filternationalityOBJ.put("filterOption", 1);
                    filternationalityOBJ.put("columnName", "nationality");
                    filternationalityOBJ.put("filterValue", nationality);
                }


                filterParamsArray.put(filterfrstnameOBJ);
                filterParamsArray.put(filtermiddlenameOBJ);
                filterParamsArray.put(filterlastnameOBJ);
                filterParamsArray.put(filterstudentidOBJ);
                filterParamsArray.put(filterstudentaltidOBJ);
                filterParamsArray.put(filteradmissionOBJ);
                filterParamsArray.put(filterrollOBJ);
                filterParamsArray.put(filterdobstrtOBJ);
                filterParamsArray.put(filterdobendOBJ);
                filterParamsArray.put(filterdobOBJ);
                filterParamsArray.put(filtergenderOBJ);
                filterParamsArray.put(filtermartialOBJ);
                filterParamsArray.put(filterfrstlanguageOBJ);
                filterParamsArray.put(filterscndlanguageOBJ);
                filterParamsArray.put(filterthrdlanguageOBJ);
                filterParamsArray.put(filtercountryOBJ);
                filterParamsArray.put(filternationalityOBJ);





                jsonObject.put("pageNumber",0);
                jsonObject.put("_pageSize",0);
                jsonObject.put("sortingModel",null);
                jsonObject.put("staffId",pref.getUserID());
                jsonObject.put("academicYear",pref.getAcademyYear());
                jsonObject.put("_tenantName", pref.getTenatName());
                jsonObject.put("tenantId", AppData.tenatID);
                jsonObject.put("_userName", pref.getName());
                jsonObject.put("_token", pref.getToken());
                jsonObject.put("schoolId", pref.getSchoolID());
                jsonObject.put("filterParams",filterParamsArray);
                jsonObject.put("courseSectionIds",courseSectionIdsArray);
                getStudentList(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    public void getStudentList(JSONObject jsonObject) {
        lnNoData.setVisibility(View.GONE);
        lnStudents.setVisibility(View.VISIBLE);

        String url=pref.getAPI()+"StudentSchedule/getStudentListByCourseSection";
        new PostJsonDataParser(getContext(), Request.Method.POST, url,jsonObject, true,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("studentresponse",response.toString());
                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");
                        if (_failure){
                            Toast.makeText(getContext(),_message,Toast.LENGTH_LONG).show();

                            lnNoData.setVisibility(View.VISIBLE);
                            lnStudents.setVisibility(View.GONE);

                        }else {
                            JSONArray scheduleStudentForView = response.optJSONArray("scheduleStudentForView");
                            if (scheduleStudentForView.length() > 0) {
                                for (int i=0;i<scheduleStudentForView.length();i++){
                                    JSONObject studentMasterOBJ=scheduleStudentForView.optJSONObject(i);
                                    String firstGivenName=studentMasterOBJ.optString("firstGivenName");
                                    String lastFamilyName=studentMasterOBJ.optString("lastFamilyName");
                                    String middleName=Util.getFreshValue(studentMasterOBJ.optString("middleName"));
                                    String name=firstGivenName+" "+middleName+" "+lastFamilyName;
                                    String studentInternalId=studentMasterOBJ.optString("studentInternalId");
                                    String studentPhoto=Util.getFreshValue(studentMasterOBJ.optString("studentPhoto"),"-");
                                    StudentsModel model=new StudentsModel();
                                    model.setStudentName(name);
                                    model.setStudentImage(studentPhoto);
                                    model.setStudentsID(studentInternalId);
                                    itmList.add(model);



                                }
                                lnNoData.setVisibility(View.GONE);
                                lnStudents.setVisibility(View.VISIBLE);


                                setAdapter();



                            }else {
                                lnNoData.setVisibility(View.VISIBLE);
                                lnStudents.setVisibility(View.GONE);
                            }
                        }






                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }

    void filter(String text){
        ArrayList<StudentsModel> temp = new ArrayList();
        for(StudentsModel d: itmList){
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if(d.getStudentName().toLowerCase().contains(text) || d.getStudentName().toUpperCase().contains(text)){
                temp.add(d);
            }
        }
        //update recyclerview
        studentAdapter.updateList(temp);
    }

    public void getGender(JSONObject jsonObject) {
        genderList.clear();
        genderList.add("Gender");

        String url=pref.getAPI()+"Common/getAllDropdownValues";
        new PostJsonDataParser(getContext(), Request.Method.POST, url,jsonObject, true,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("studentresponse",response.toString());
                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");


                            JSONArray dropdownList = response.optJSONArray("dropdownList");

                                for (int i=0;i<dropdownList.length();i++){
                                    JSONObject dropDownObj=dropdownList.optJSONObject(i);
                                    String lovColumnValue=dropDownObj.optString("lovColumnValue");
                                    genderList.add(lovColumnValue);



                                }


                                ArrayAdapter ad
                                        = new ArrayAdapter(
                                        getContext(),
                                        android.R.layout.simple_spinner_item,
                                        genderList);

                                // set simple layout resource file
                                // for each item of spinner
                                ad.setDropDownViewResource(
                                        android.R.layout
                                                .simple_spinner_dropdown_item);

                                // Set the ArrayAdapter (ad) data on the
                                // Spinner which binds data to spinner
                                spGender.setAdapter(ad);


                                JSONObject obj=new JSONObject();
                                try {
                                    obj.put("schoolId",pref.getSchoolID());
                                    obj.put("_token",pref.getToken());
                                    obj.put("lovName","Race");
                                    obj.put("_tenantName",pref.getTenatName());
                                    obj.put("_userName",pref.getName());
                                    obj.put("tenantId",AppData.tenatID);
                                    getRace(obj);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }











                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }

    public void getRace(JSONObject jsonObject) {
        raceList.clear();
        raceList.add("Race");
        String url=pref.getAPI()+"Common/getAllDropdownValues";
        new PostJsonDataParser(getContext(), Request.Method.POST, url,jsonObject, false,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("studentresponse",response.toString());
                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");


                            JSONArray dropdownList = response.optJSONArray("dropdownList");

                                for (int i=0;i<dropdownList.length();i++){
                                    JSONObject dropDownObj=dropdownList.optJSONObject(i);
                                    String lovColumnValue=dropDownObj.optString("lovColumnValue");
                                    raceList.add(lovColumnValue);



                                }


                                ArrayAdapter ad
                                        = new ArrayAdapter(
                                        getContext(),
                                        android.R.layout.simple_spinner_item,
                                        raceList);

                                // set simple layout resource file
                                // for each item of spinner
                                ad.setDropDownViewResource(
                                        android.R.layout
                                                .simple_spinner_dropdown_item);

                                // Set the ArrayAdapter (ad) data on the
                                // Spinner which binds data to spinner
                                spRace.setAdapter(ad);

                        JSONObject obj=new JSONObject();
                        try {
                            obj.put("schoolId",pref.getSchoolID());
                            obj.put("_token",pref.getToken());
                            obj.put("lovName","Ethnicity");
                            obj.put("_tenantName",pref.getTenatName());
                            obj.put("_userName",pref.getName());
                            obj.put("tenantId",AppData.tenatID);
                            getEthnicity(obj);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }









                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }

    public void getEthnicity(JSONObject jsonObject) {
        enthnicityList.clear();
        enthnicityList.add("Ethnicity");
        String url=pref.getAPI()+"Common/getAllDropdownValues";
        new PostJsonDataParser(getContext(), Request.Method.POST, url,jsonObject, false,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("studentresponse",response.toString());
                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");


                        JSONArray dropdownList = response.optJSONArray("dropdownList");

                        for (int i=0;i<dropdownList.length();i++){
                            JSONObject dropDownObj=dropdownList.optJSONObject(i);
                            String lovColumnValue=dropDownObj.optString("lovColumnValue");
                            enthnicityList.add(lovColumnValue);



                        }


                        ArrayAdapter ad
                                = new ArrayAdapter(
                                getContext(),
                                android.R.layout.simple_spinner_item,
                                enthnicityList);

                        // set simple layout resource file
                        // for each item of spinner
                        ad.setDropDownViewResource(
                                android.R.layout
                                        .simple_spinner_dropdown_item);

                        // Set the ArrayAdapter (ad) data on the
                        // Spinner which binds data to spinner
                        spEthnicity.setAdapter(ad);
                        JSONObject obj=new JSONObject();
                        try {
                            obj.put("schoolId",pref.getSchoolID());
                            obj.put("_token",pref.getToken());
                            obj.put("lovName","Marital Status");
                            obj.put("_tenantName",pref.getTenatName());
                            obj.put("_userName",pref.getName());
                            obj.put("tenantId",AppData.tenatID);
                            getMartialStatus(obj);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }











                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }

    public void getMartialStatus(JSONObject jsonObject) {
        martialStatusList.clear();
        martialStatusList.add("Marital Status");
        String url=pref.getAPI()+"Common/getAllDropdownValues";
        new PostJsonDataParser(getContext(), Request.Method.POST, url,jsonObject, false,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("studentresponse",response.toString());
                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");


                        JSONArray dropdownList = response.optJSONArray("dropdownList");

                        for (int i=0;i<dropdownList.length();i++){
                            JSONObject dropDownObj=dropdownList.optJSONObject(i);
                            String lovColumnValue=dropDownObj.optString("lovColumnValue");
                            martialStatusList.add(lovColumnValue);



                        }


                        ArrayAdapter ad
                                = new ArrayAdapter(
                                getContext(),
                                android.R.layout.simple_spinner_item,
                                martialStatusList);

                        // set simple layout resource file
                        // for each item of spinner
                        ad.setDropDownViewResource(
                                android.R.layout
                                        .simple_spinner_dropdown_item);

                        // Set the ArrayAdapter (ad) data on the
                        // Spinner which binds data to spinner
                        spMartialStatus.setAdapter(ad);


                        JSONObject obj=new JSONObject();
                        try {
                            obj.put("isCountryAvailable",false);
                            obj.put("schoolId",pref.getSchoolID());
                            obj.put("_token",pref.getToken());
                            obj.put("_tenantName",pref.getTenatName());
                            obj.put("_userName",pref.getName());
                            obj.put("tenantId",AppData.tenatID);
                            getCountryList(obj);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }









                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }

    public void getCountryList(JSONObject jsonObject) {
        countryList.clear();
        mcountryList.clear();
        mcountryList.add(new SpinnerModel("0","0"));
        countryList.add("Country of Birth");

        nationalityList.clear();
        mnationalityList.clear();
        mnationalityList.add(new SpinnerModel("0","0"));
        nationalityList.add("Nationality");
        String url=pref.getAPI()+"Common/getAllCountries";
        new PostJsonDataParser(getContext(), Request.Method.POST, url,jsonObject, false,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("studentresponse",response.toString());
                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");


                        JSONArray tableCountry = response.optJSONArray("tableCountry");

                        for (int i=0;i<tableCountry.length();i++){
                            JSONObject dropDownObj=tableCountry.optJSONObject(i);
                            String name=dropDownObj.optString("name");
                            String id=dropDownObj.optString("id");
                            countryList.add(name);
                            nationalityList.add(name);
                            SpinnerModel spmodel=new SpinnerModel(name,String.valueOf(id));
                            mcountryList.add(spmodel);
                            mnationalityList.add(spmodel);



                        }


                        ArrayAdapter ad
                                = new ArrayAdapter(
                                getContext(),
                                android.R.layout.simple_spinner_item,
                                countryList);

                        // set simple layout resource file
                        // for each item of spinner
                        ad.setDropDownViewResource(
                                android.R.layout
                                        .simple_spinner_dropdown_item);

                        // Set the ArrayAdapter (ad) data on the
                        // Spinner which binds data to spinner
                        spCountryofBirth.setAdapter(ad);

                        ArrayAdapter ac
                                = new ArrayAdapter(
                                getContext(),
                                android.R.layout.simple_spinner_item,
                                nationalityList);

                        // set simple layout resource file
                        // for each item of spinner
                        ac.setDropDownViewResource(
                                android.R.layout
                                        .simple_spinner_dropdown_item);

                        spNationality.setAdapter(ac);

                        JSONObject obj=new JSONObject();
                        try {

                            obj.put("schoolId",pref.getSchoolID());
                            obj.put("_token",pref.getToken());
                            obj.put("_tenantName",pref.getTenatName());
                            obj.put("_userName",pref.getName());
                            obj.put("tenantId",AppData.tenatID);
                            obj.put("academicYear",2022);
                            getLanguageList(obj);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }










                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }

    public void getLanguageList(JSONObject jsonObject) {
        frstLanguageList.clear();
        mfrstLanguageList.clear();
        mfrstLanguageList.add(new SpinnerModel("0","0"));
        frstLanguageList.add("First Language");

        scndLanguageList.clear();
        mscndLanguageList.clear();
        mscndLanguageList.add(new SpinnerModel("0","0"));
        scndLanguageList.add("Second Language");

        thrdLanguageList.clear();
        mthrdLanguageList.clear();
        mthrdLanguageList.add(new SpinnerModel("0","0"));
        thrdLanguageList.add("Third Language");

        String url=pref.getAPI()+"Common/getAllLanguage";



        new PostJsonDataParser(getContext(), Request.Method.POST, url,jsonObject, true,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("studentresponse",response.toString());
                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");


                        JSONArray tableLanguage = response.optJSONArray("tableLanguage");

                        for (int i=0;i<tableLanguage.length();i++){
                            JSONObject dropDownObj=tableLanguage.optJSONObject(i);
                            String name=dropDownObj.optString("locale");
                            int langId=dropDownObj.optInt("langId");
                            frstLanguageList.add(name);
                            scndLanguageList.add(name);
                            thrdLanguageList.add(name);
                            SpinnerModel spmodel=new SpinnerModel(name,String.valueOf(langId));
                            mfrstLanguageList.add(spmodel);
                            mscndLanguageList.add(spmodel);
                            mthrdLanguageList.add(spmodel);



                        }


                        ArrayAdapter frstLanguage
                                = new ArrayAdapter(
                                getContext(),
                                android.R.layout.simple_spinner_item,
                                frstLanguageList);

                        // set simple layout resource file
                        // for each item of spinner
                        frstLanguage.setDropDownViewResource(
                                android.R.layout
                                        .simple_spinner_dropdown_item);

                        // Set the ArrayAdapter (ad) data on the
                        // Spinner which binds data to spinner
                        spFrstLanguage.setAdapter(frstLanguage);

                        ArrayAdapter scndLanguage
                                = new ArrayAdapter(
                                getContext(),
                                android.R.layout.simple_spinner_item,
                                scndLanguageList);

                        // set simple layout resource file
                        // for each item of spinner
                        scndLanguage.setDropDownViewResource(
                                android.R.layout
                                        .simple_spinner_dropdown_item);

                        spScndLanguage.setAdapter(scndLanguage);


                        ArrayAdapter thrdLanguage
                                = new ArrayAdapter(
                                getContext(),
                                android.R.layout.simple_spinner_item,
                                thrdLanguageList);

                        // set simple layout resource file
                        // for each item of spinner
                        thrdLanguage.setDropDownViewResource(
                                android.R.layout
                                        .simple_spinner_dropdown_item);

                        spThirdLanguage.setAdapter(thrdLanguage);










                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }


    private void showStartDateDialog() {

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
                tvFormDate.setText(date);
                startDate=date;


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

    private void showEndDateDialog() {

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
                tvToDate.setText(date);
                endDate=date;

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


    private void showDOBDateDialog() {

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
                tvDOB.setText(date);
                dob=date;

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




}