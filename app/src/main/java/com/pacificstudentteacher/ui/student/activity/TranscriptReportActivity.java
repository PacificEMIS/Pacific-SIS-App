package com.pacificstudentteacher.ui.student.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.pacificstudentteacher.R;
import com.pacificstudentteacher.adapter.TranscriptReportCardAdapter;
import com.pacificstudentteacher.databinding.ActivityTranscriptReportcardBinding;
import com.pacificstudentteacher.model.TranscriptReportCardModel;
import com.pacificstudentteacher.others.utility.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TranscriptReportActivity extends AppCompatActivity implements View.OnClickListener {


    ArrayList<TranscriptReportCardModel> transcriptreporcardList = new ArrayList<>();
    ActivityTranscriptReportcardBinding binding;
    String gradeID;
    JSONArray reportCardDetailsForTranscripts;

    String toolbar,reportlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_transcript_reportcard);
        initView();
    }

    private void initView() {

        reportlist=getIntent().getStringExtra("reportlist");
        toolbar=getIntent().getStringExtra("toolbar");
        binding.tvToolBar.setText(toolbar);
        binding.rvItem.setLayoutManager(new LinearLayoutManager(TranscriptReportActivity.this, LinearLayoutManager.VERTICAL, false));

        try {
            reportCardDetailsForTranscripts=new JSONArray(reportlist);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        binding.imgBack.setOnClickListener(this);

        getTranscript();

    }


    public void getTranscript() {


        for (int z = 0; z < reportCardDetailsForTranscripts.length(); z++) {
            JSONObject reportcardOBJ = reportCardDetailsForTranscripts.optJSONObject(z);
            String courseSectionName = reportcardOBJ.optString("courseSectionName");
            String courseCode = Util.getFreshValue(reportcardOBJ.optString("courseCode"),"");
            String creditHours = reportcardOBJ.optString("creditHours");
            String creditEarned = reportcardOBJ.optString("creditEarned");
            String grade = reportcardOBJ.optString("grade");
            String gpValue = reportcardOBJ.optString("gpValue");
            TranscriptReportCardModel reportCardModel = new TranscriptReportCardModel();
            reportCardModel.setCourseSectionName(courseSectionName);
            reportCardModel.setCourseCode(courseCode);
            reportCardModel.setCreditHours(Util.getFreshValue(creditHours, ""));
            reportCardModel.setCreditEarned(Util.getFreshValue(creditEarned, ""));
            reportCardModel.setGrade(Util.getFreshValue(grade, ""));
            reportCardModel.setGpValue(Util.getFreshValue(gpValue, ""));
            transcriptreporcardList.add(reportCardModel);
        }


        TranscriptReportCardAdapter transcriptAdapter = new TranscriptReportCardAdapter(transcriptreporcardList);
        binding.rvItem.setAdapter(transcriptAdapter);

    }


    @Override
    public void onClick(View v) {
        if (v==binding.imgBack){
            onBackPressed();
        }

    }
}
