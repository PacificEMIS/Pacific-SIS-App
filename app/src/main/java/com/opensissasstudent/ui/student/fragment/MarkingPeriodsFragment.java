package com.opensissasstudent.ui.student.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;

import com.opensissasstudent.R;
import com.opensissasstudent.adapter.MarkingPeriodParentAdapter;
import com.opensissasstudent.model.MarkingPeriodChildModel;
import com.opensissasstudent.model.MarkingPeriodChildToChildModel;
import com.opensissasstudent.model.MarkingPeriodParentModel;
import com.opensissasstudent.others.parser.PostJsonDataParser;
import com.opensissasstudent.others.utility.Pref;
import com.opensissasstudent.ui.common.activity.LoginActivity;
import com.opensissasstudent.ui.student.activity.MarkingPeriodDetailsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MarkingPeriodsFragment extends Fragment implements View.OnClickListener {
    View view;
    LinearLayout lnFY,lnSem1,lnQtr1,lnQtr2,lnSem2,lnQtr3,lnQtr4;
    TextView tvFY,tvQtr1,tvQtr2,tvSem2,tvQtr3,tvQtr4,tvSem1;
    Pref pref;
    String fytitle,fyshortName,fystartDate,fyendDate,fypostStartDate,fypostEndDate;
    boolean fydoesGrades,fydoesExam,fydoesComments;

    String semititle,semishortName,semistartDate,semiendDate,semipostStartDate,semipostEndDate;
    boolean semidoesGrades,semidoesExam,semidoesComments;

    String semiititle,semiishortName,semiistartDate,semiiendDate,semiipostStartDate,semiipostEndDate;
    boolean semiidoesGrades,semiidoesExam,semiidoesComments;


    String frstqtrtitle,frstqtrshortName,frstqtrstartDate,frstqtrendDate,frstqtrpostStartDate,frstqtrpostEndDate;
    boolean frstqtrdoesGrades,frstqtrdoesExam,frstqtrdoesComments;

    String scndqtrtitle,scndqtrshortName,scndqtrstartDate,scndqtrendDate,scndqtrpostStartDate,scndqtrpostEndDate;
    boolean scndqtrdoesGrades,scndqtrdoesExam,scndqtrdoesComments;


    String thrdqtrtitle,thrdqtrshortName,thrdqtrstartDate,thrdqtrendDate,thrdqtrpostStartDate,thrdqtrpostEndDate;
    boolean thrdqtrdoesGrades,thrdqtrdoesExam,thrdqtrdoesComments;

    String frthqtrtitle,frthqtrshortName,frthqtrstartDate,frthqtrendDate,frthqtrpostStartDate,frthqtrpostEndDate;
    boolean frthqtrdoesGrades,frthqtrdoesExam,frthqtrdoesComments;
    RecyclerView rvParent;
    ArrayList<MarkingPeriodParentModel>parentList=new ArrayList<>();
    ArrayList<MarkingPeriodChildModel>childList=new ArrayList<>();
    ArrayList<MarkingPeriodChildToChildModel>childtochildList=new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_marking_periods, container, false);
        initView();
        return view;
    }

    private void initView(){
        pref=new Pref(getContext());
        lnFY=(LinearLayout) view.findViewById(R.id.lnFY);
        lnSem1=(LinearLayout) view.findViewById(R.id.lnSem1);
        lnQtr1=(LinearLayout) view.findViewById(R.id.lnQtr1);
        lnQtr2=(LinearLayout) view.findViewById(R.id.lnQtr2);
        lnSem2=(LinearLayout) view.findViewById(R.id.lnSem2);
        lnQtr3=(LinearLayout) view.findViewById(R.id.lnQtr3);
        lnQtr4=(LinearLayout) view.findViewById(R.id.lnQtr4);

        rvParent=(RecyclerView)view.findViewById(R.id.rvParent);
        rvParent.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        lnFY.setOnClickListener(this);
        lnSem1.setOnClickListener(this);
        lnQtr1.setOnClickListener(this);
        lnQtr2.setOnClickListener(this);
        lnSem2.setOnClickListener(this);
        lnQtr3.setOnClickListener(this);
        lnQtr4.setOnClickListener(this);

        tvFY=(TextView) view.findViewById(R.id.tvFY);
        tvQtr1=(TextView) view.findViewById(R.id.tvQtr1);
        tvQtr2=(TextView) view.findViewById(R.id.tvQtr2);
        tvSem2=(TextView) view.findViewById(R.id.tvSem2);
        tvQtr3=(TextView) view.findViewById(R.id.tvQtr3);
        tvQtr4=(TextView) view.findViewById(R.id.tvQtr4);
        tvSem1=(TextView) view.findViewById(R.id.tvSem1);

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
                            JSONObject jsonObject1=new JSONObject();
                           JSONObject blankObj=new JSONObject();
                            JSONArray schoolYearsView=new JSONArray();
                            schoolYearsView.put(blankObj);
                            jsonObject1.put("schoolYearsView",schoolYearsView);
                            jsonObject1.put("academicYear",pref.getAcademicYear());
                            jsonObject1.put("_academicYear",pref.getAcademicYear());
                            jsonObject1.put("_tenantName",pref.getTenatName());
                            jsonObject1.put("_userName",pref.getName());
                            jsonObject1.put("_token",pref.getToken());
                            jsonObject1.put("tenantId",pref.getTenatID());
                            jsonObject1.put("schoolId",pref.getSchoolID());
                            getMarkingPeriod(jsonObject1);
                        }






                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }

    public void getMarkingPeriod(JSONObject jsonObject) {
        String url=pref.getAPI()+"MarkingPeriod/getMarkingPeriod";


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
                            JSONArray schoolYearsView=response.optJSONArray("schoolYearsView");

                            for (int i=0;i<schoolYearsView.length();i++){
                                childList=new ArrayList<>();
                                childtochildList=new ArrayList<>();
                                JSONObject fyOBJ=schoolYearsView.optJSONObject(i);
                                JSONArray semChildern=fyOBJ.optJSONArray("children");
                                if (semChildern.length()>0){
                                    childList=new ArrayList<>();
                                    for (int j=0;j<semChildern.length();j++){
                                        JSONObject semiobj=semChildern.optJSONObject(j);
                                        JSONArray semiqtrChildern=semiobj.optJSONArray("children");
                                        if (semiqtrChildern.length()>0){
                                            childtochildList=new ArrayList<>();
                                            for (int z=0;z<semiqtrChildern.length();z++){
                                                JSONObject QtrOBJ=semiqtrChildern.optJSONObject(z);
                                                String frstqtrtitle=QtrOBJ.optString("title");
                                                String frstqtrstartDate=QtrOBJ.optString("startDate").replace("T00:00:00","");
                                                String frstqtrendDate=QtrOBJ.optString("endDate").replace("T00:00:00","");;
                                                String frstqtrshortName=QtrOBJ.optString("shortName");
                                                String frstqtrpostEndDate=QtrOBJ.optString("postEndDate").replace("T00:00:00","");;
                                                String frstqtrpostStartDate=QtrOBJ.optString("postStartDate").replace("T00:00:00","");;
                                                Boolean frstqtrdoesComments=QtrOBJ.optBoolean("doesComments");
                                                Boolean frstqtrdoesExam=QtrOBJ.optBoolean("doesExam");
                                                Boolean frstqtrdoesGrades=QtrOBJ.optBoolean("doesGrades");
                                                MarkingPeriodChildToChildModel childtochildmodel=new MarkingPeriodChildToChildModel();
                                                childtochildmodel.setTitle(frstqtrtitle);
                                                childtochildmodel.setStartDate(frstqtrstartDate);
                                                childtochildmodel.setEndDate(frstqtrendDate);
                                                childtochildmodel.setShortName(frstqtrshortName);
                                                childtochildmodel.setPostEndDate(frstqtrpostEndDate);
                                                childtochildmodel.setPostStartDate(frstqtrpostStartDate);
                                                childtochildmodel.setDoesComments(frstqtrdoesComments);
                                                childtochildmodel.setDoesExam(frstqtrdoesExam);
                                                childtochildmodel.setDoesGrades(frstqtrdoesGrades);
                                                childtochildList.add(childtochildmodel);
                                            }
                                        }else {
                                            childList.clear();
                                        }

                                        String frstqtrtitle=semiobj.optString("title");
                                        String frstqtrstartDate=semiobj.optString("startDate").replace("T00:00:00","");
                                        String frstqtrendDate=semiobj.optString("endDate").replace("T00:00:00","");;
                                        String frstqtrshortName=semiobj.optString("shortName");
                                        String frstqtrpostEndDate=semiobj.optString("postEndDate").replace("T00:00:00","");;
                                        String frstqtrpostStartDate=semiobj.optString("postStartDate").replace("T00:00:00","");;
                                        Boolean frstqtrdoesComments=semiobj.optBoolean("doesComments");
                                        Boolean frstqtrdoesExam=semiobj.optBoolean("doesExam");
                                        Boolean frstqtrdoesGrades=semiobj.optBoolean("doesGrades");
                                        MarkingPeriodChildModel childmodel=new MarkingPeriodChildModel();
                                        childmodel.setTitle(frstqtrtitle);
                                        childmodel.setStartDate(frstqtrstartDate);
                                        childmodel.setEndDate(frstqtrendDate);
                                        childmodel.setShortName(frstqtrshortName);
                                        childmodel.setPostEndDate(frstqtrpostEndDate);
                                        childmodel.setPostStartDate(frstqtrpostStartDate);
                                        childmodel.setDoesComments(frstqtrdoesComments);
                                        childmodel.setDoesExam(frstqtrdoesExam);
                                        childmodel.setDoesGrades(frstqtrdoesGrades);
                                        childmodel.setItemlist(childtochildList);
                                        childList.add(childmodel);
                                    }
                                }
                                String frstqtrtitle=fyOBJ.optString("title");
                                String frstqtrstartDate=fyOBJ.optString("startDate").replace("T00:00:00","");
                                String frstqtrendDate=fyOBJ.optString("endDate").replace("T00:00:00","");;
                                String frstqtrshortName=fyOBJ.optString("shortName");
                                String frstqtrpostEndDate=fyOBJ.optString("postEndDate").replace("T00:00:00","");;
                                String frstqtrpostStartDate=fyOBJ.optString("postStartDate").replace("T00:00:00","");;
                                Boolean frstqtrdoesComments=fyOBJ.optBoolean("doesComments");
                                Boolean frstqtrdoesExam=fyOBJ.optBoolean("doesExam");
                                Boolean frstqtrdoesGrades=fyOBJ.optBoolean("doesGrades");
                                MarkingPeriodParentModel parentModel=new MarkingPeriodParentModel();
                                parentModel.setTitle(frstqtrtitle);
                                parentModel.setStartDate(frstqtrstartDate);
                                parentModel.setEndDate(frstqtrendDate);
                                parentModel.setShortName(frstqtrshortName);
                                parentModel.setPostEndDate(frstqtrpostEndDate);
                                parentModel.setPostStartDate(frstqtrpostStartDate);
                                parentModel.setDoesComments(frstqtrdoesComments);
                                parentModel.setDoesExam(frstqtrdoesExam);
                                parentModel.setDoesGrades(frstqtrdoesGrades);
                                parentModel.setItemlist(childList);
                                parentList.add(parentModel);



                            }

                            MarkingPeriodParentAdapter periodParentAdapter=new MarkingPeriodParentAdapter(parentList,getContext());
                            rvParent.setAdapter(periodParentAdapter);

                            /* fytitle=fyOBJ.optString("title");
                            tvFY.setText(fytitle);
                            fystartDate=fyOBJ.optString("startDate").replace("T00:00:00","");
                            fyendDate=fyOBJ.optString("endDate").replace("T00:00:00","");;
                            fyshortName=fyOBJ.optString("shortName");
                            fypostEndDate=fyOBJ.optString("postEndDate").replace("T00:00:00","");;
                            fypostStartDate=fyOBJ.optString("postStartDate").replace("T00:00:00","");;
                            fydoesComments=fyOBJ.optBoolean("doesComments");
                            fydoesExam=fyOBJ.optBoolean("doesExam");
                            fydoesGrades=fyOBJ.optBoolean("doesGrades");




                            semititle=semiobj.optString("title");
                            tvSem1.setText(semititle);
                            semistartDate=semiobj.optString("startDate").replace("T00:00:00","");
                            semiendDate=semiobj.optString("endDate").replace("T00:00:00","");;
                            semishortName=semiobj.optString("shortName");
                            semipostEndDate=semiobj.optString("postEndDate").replace("T00:00:00","");;
                            semipostStartDate=semiobj.optString("postStartDate").replace("T00:00:00","");;
                            semidoesComments=semiobj.optBoolean("doesComments");
                            semidoesExam=semiobj.optBoolean("doesExam");
                            semidoesGrades=semiobj.optBoolean("doesGrades");
*/





                          /*  if (semiqtrChildern.length()>1){
                                lnQtr2.setVisibility(View.VISIBLE);

                                JSONObject scndQtrOBJ=semiqtrChildern.optJSONObject(semiqtrChildern.length()-1);

                                scndqtrtitle=scndQtrOBJ.optString("title");
                                tvQtr2.setText(scndqtrtitle);
                                scndqtrstartDate=scndQtrOBJ.optString("startDate").replace("T00:00:00","");
                                scndqtrendDate=scndQtrOBJ.optString("endDate").replace("T00:00:00","");;
                                scndqtrshortName=scndQtrOBJ.optString("shortName");
                                scndqtrpostEndDate=scndQtrOBJ.optString("postEndDate").replace("T00:00:00","");;
                                scndqtrpostStartDate=scndQtrOBJ.optString("postStartDate").replace("T00:00:00","");;
                                scndqtrdoesComments=scndQtrOBJ.optBoolean("doesComments");
                                scndqtrdoesExam=scndQtrOBJ.optBoolean("doesExam");
                                scndqtrdoesGrades=scndQtrOBJ.optBoolean("doesGrades");

                            }else {
                                lnQtr2.setVisibility(View.GONE);
                            }



                            if (semChildern.length()>1){
                                lnSem2.setVisibility(View.VISIBLE);
                                JSONObject semiiobj=semChildern.optJSONObject(semChildern.length()-1);

                                semiititle=semiiobj.optString("title");
                                tvSem2.setText(semiititle);
                                semiistartDate=semiiobj.optString("startDate").replace("T00:00:00","");
                                semiiendDate=semiiobj.optString("endDate").replace("T00:00:00","");;
                                semiishortName=semiiobj.optString("shortName");
                                semiipostEndDate=semiiobj.optString("postEndDate").replace("T00:00:00","");;
                                semiipostStartDate=semiiobj.optString("postStartDate").replace("T00:00:00","");;
                                semiidoesComments=semiiobj.optBoolean("doesComments");
                                semiidoesExam=semiiobj.optBoolean("doesExam");
                                semiidoesGrades=semiiobj.optBoolean("doesGrades");


                                JSONArray semiiqtrChildern=semiiobj.optJSONArray("children");
                                JSONObject thrdQtrOBJ=semiiqtrChildern.optJSONObject(0);

                                thrdqtrtitle=thrdQtrOBJ.optString("title");
                                tvQtr3.setText(thrdqtrtitle);
                                thrdqtrstartDate=thrdQtrOBJ.optString("startDate").replace("T00:00:00","");
                                thrdqtrendDate=thrdQtrOBJ.optString("endDate").replace("T00:00:00","");;
                                thrdqtrshortName=thrdQtrOBJ.optString("shortName");
                                thrdqtrpostEndDate=thrdQtrOBJ.optString("postEndDate").replace("T00:00:00","");;
                                thrdqtrpostStartDate=thrdQtrOBJ.optString("postStartDate").replace("T00:00:00","");;
                                thrdqtrdoesComments=thrdQtrOBJ.optBoolean("doesComments");
                                thrdqtrdoesExam=thrdQtrOBJ.optBoolean("doesExam");
                                thrdqtrdoesGrades=thrdQtrOBJ.optBoolean("doesGrades");

                                if (semiiqtrChildern.length()>1){
                                    lnQtr4.setVisibility(View.VISIBLE);

                                    JSONObject frthQtrOBJ=semiiqtrChildern.optJSONObject(semiiqtrChildern.length()-1);

                                    frthqtrtitle=frthQtrOBJ.optString("title");
                                    tvQtr4.setText(frthqtrtitle);
                                    frthqtrstartDate=frthQtrOBJ.optString("startDate").replace("T00:00:00","");
                                    frthqtrendDate=frthQtrOBJ.optString("endDate").replace("T00:00:00","");;
                                    frthqtrshortName=frthQtrOBJ.optString("shortName");
                                    frthqtrpostEndDate=frthQtrOBJ.optString("postEndDate").replace("T00:00:00","");;
                                    frthqtrpostStartDate=frthQtrOBJ.optString("postStartDate").replace("T00:00:00","");;
                                    frthqtrdoesComments=frthQtrOBJ.optBoolean("doesComments");
                                    frthqtrdoesExam=frthQtrOBJ.optBoolean("doesExam");
                                    frthqtrdoesGrades=frthQtrOBJ.optBoolean("doesGrades");

                                }else {
                                    lnQtr4.setVisibility(View.GONE);
                                }


                            }else {
                                lnSem2.setVisibility(View.GONE);


                            }*/



                        }






                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }

    @Override
    public void onClick(View view) {
        if (view==lnFY){
            Intent intent=new Intent(getContext(), MarkingPeriodDetailsActivity.class);
            intent.putExtra("title",fytitle);
            intent.putExtra("shortname",fyshortName);
            intent.putExtra("begindate",fystartDate);
            intent.putExtra("enddate",fyendDate);
            intent.putExtra("postingbegindate",fypostStartDate);
            intent.putExtra("postingenddate",fypostEndDate);
            intent.putExtra("exam",fydoesExam);
            intent.putExtra("graded",fydoesGrades);
            intent.putExtra("comment",fydoesComments);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else if (view==lnSem1){
            Intent intent=new Intent(getContext(), MarkingPeriodDetailsActivity.class);
            intent.putExtra("title",semititle);
            intent.putExtra("shortname",semishortName);
            intent.putExtra("begindate",semistartDate);
            intent.putExtra("enddate",semiendDate);
            intent.putExtra("postingbegindate",semipostStartDate);
            intent.putExtra("postingenddate",semipostEndDate);
            intent.putExtra("exam",semidoesExam);
            intent.putExtra("graded",semidoesGrades);
            intent.putExtra("comment",semidoesComments);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else if (view==lnSem2){
            Intent intent=new Intent(getContext(), MarkingPeriodDetailsActivity.class);
            intent.putExtra("title",semiititle);
            intent.putExtra("shortname",semiishortName);
            intent.putExtra("begindate",semiistartDate);
            intent.putExtra("enddate",semiiendDate);
            intent.putExtra("postingbegindate",semiipostStartDate);
            intent.putExtra("postingenddate",semiipostEndDate);
            intent.putExtra("exam",semiidoesExam);
            intent.putExtra("graded",semiidoesGrades);
            intent.putExtra("comment",semiidoesComments);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else if (view==lnQtr1){
            Intent intent=new Intent(getContext(), MarkingPeriodDetailsActivity.class);
            intent.putExtra("title",frstqtrtitle);
            intent.putExtra("shortname",frstqtrshortName);
            intent.putExtra("begindate",frstqtrstartDate);
            intent.putExtra("enddate",frstqtrendDate);
            intent.putExtra("postingbegindate",frstqtrpostStartDate);
            intent.putExtra("postingenddate",frstqtrpostEndDate);
            intent.putExtra("exam",frstqtrdoesExam);
            intent.putExtra("graded",frstqtrdoesGrades);
            intent.putExtra("comment",frstqtrdoesComments);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else if (view==lnQtr2){
            Intent intent=new Intent(getContext(), MarkingPeriodDetailsActivity.class);
            intent.putExtra("title",scndqtrtitle);
            intent.putExtra("shortname",scndqtrshortName);
            intent.putExtra("begindate",scndqtrstartDate);
            intent.putExtra("enddate",scndqtrendDate);
            intent.putExtra("postingbegindate",scndqtrpostStartDate);
            intent.putExtra("postingenddate",scndqtrpostEndDate);
            intent.putExtra("exam",scndqtrdoesExam);
            intent.putExtra("graded",scndqtrdoesGrades);
            intent.putExtra("comment",scndqtrdoesComments);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else if (view==lnQtr3){
            Intent intent=new Intent(getContext(), MarkingPeriodDetailsActivity.class);
            intent.putExtra("title",thrdqtrtitle);
            intent.putExtra("shortname",thrdqtrshortName);
            intent.putExtra("begindate",thrdqtrstartDate);
            intent.putExtra("enddate",thrdqtrendDate);
            intent.putExtra("postingbegindate",thrdqtrpostStartDate);
            intent.putExtra("postingenddate",thrdqtrpostEndDate);
            intent.putExtra("exam",thrdqtrdoesExam);
            intent.putExtra("graded",thrdqtrdoesGrades);
            intent.putExtra("comment",thrdqtrdoesComments);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else if (view==lnQtr4){
            Intent intent=new Intent(getContext(), MarkingPeriodDetailsActivity.class);
            intent.putExtra("title",frthqtrtitle);
            intent.putExtra("shortname",frthqtrshortName);
            intent.putExtra("begindate",frthqtrstartDate);
            intent.putExtra("enddate",frthqtrendDate);
            intent.putExtra("postingbegindate",frthqtrpostStartDate);
            intent.putExtra("postingenddate",frthqtrpostEndDate);
            intent.putExtra("exam",frthqtrdoesExam);
            intent.putExtra("graded",frthqtrdoesGrades);
            intent.putExtra("comment",frthqtrdoesComments);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

    }
}