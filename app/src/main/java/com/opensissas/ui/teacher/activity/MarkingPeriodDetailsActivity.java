package com.opensissas.ui.teacher.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.opensissas.R;
import com.opensissas.others.utility.Util;

public class MarkingPeriodDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tvTitle,tvBegin,tvEnd,tvPostingBegin,tvPostingEnd,tvShortName,tvPeriodName;
    LinearLayout lnGraded,lnExam,lnComment;

    String title,shortname,begindate,enddate,postingbegindate,postingenddate;
    boolean exam,graded,comment;

    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marking_period_details);
        initView();
    }
    private void initView(){
        imgBack=(ImageView)findViewById(R.id.imgBack);
        imgBack.setOnClickListener(this);
        title=getIntent().getStringExtra("title");
        shortname=getIntent().getStringExtra("shortname");
        begindate= Util.changeAnyDateFormat(getIntent().getStringExtra("begindate"),"yyyy-MM-dd","dd MMM,yyyy");
        enddate=Util.changeAnyDateFormat(getIntent().getStringExtra("enddate"),"yyyy-MM-dd","dd MMM,yyyy");
        postingbegindate=Util.changeAnyDateFormat(getIntent().getStringExtra("postingbegindate"),"yyyy-MM-dd","dd MMM,yyyy");
        postingenddate=Util.changeAnyDateFormat(getIntent().getStringExtra("postingenddate"),"yyyy-MM-dd","dd MMM,yyyy");
        exam=getIntent().getBooleanExtra("exam",true);
        graded=getIntent().getBooleanExtra("graded",true);
        comment=getIntent().getBooleanExtra("comment",true);


        tvTitle=(TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(title);
        tvPeriodName=(TextView)findViewById(R.id.tvPeriodName);
        tvPeriodName.setText(title);
        tvBegin=(TextView) findViewById(R.id.tvBegin);
        tvBegin.setText(begindate);
        tvEnd=(TextView) findViewById(R.id.tvEnd);
        tvEnd.setText(enddate);
        tvPostingBegin=(TextView) findViewById(R.id.tvPostingBegin);
        tvPostingBegin.setText(postingbegindate);
        tvPostingEnd=(TextView) findViewById(R.id.tvPostingEnd);
        tvPostingEnd.setText(postingenddate);
        tvShortName=(TextView)findViewById(R.id.tvShortName);
        tvShortName.setText(shortname);

        lnGraded=(LinearLayout) findViewById(R.id.lnGraded);
        lnExam=(LinearLayout) findViewById(R.id.lnExam);
        lnComment=(LinearLayout) findViewById(R.id.lnComment);

        if (graded){
            lnGraded.setVisibility(View.VISIBLE);
        }else {
            lnGraded.setVisibility(View.GONE);
        }

        if (comment){
            lnComment.setVisibility(View.VISIBLE);
        }else {
            lnComment.setVisibility(View.GONE);
        }

        if (exam){
            lnExam.setVisibility(View.VISIBLE);
        }else {
            lnExam.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        if (view==imgBack){
            onBackPressed();
        }
    }
}