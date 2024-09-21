package com.opensissasstudent.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.opensissasstudent.R;
import com.opensissasstudent.model.CourseCatalogModel;
import com.opensissasstudent.model.ReportCardModel;
import com.opensissasstudent.ui.student.activity.CourseCatalogDetailsActivity;
import com.opensissasstudent.ui.student.activity.MyGardeBookGradeDetailsActivity;
import com.opensissasstudent.ui.student.activity.ReportCardDetailsActivity;

import java.util.ArrayList;


public class ReportCardAdapter extends RecyclerView.Adapter<ReportCardAdapter.MyViewHolder> {
    ArrayList<ReportCardModel> itemList = new ArrayList<>();
    Context context;
    Activity activity;



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.report_card_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

        myViewHolder.tvCourse.setText(itemList.get(i).getCourseSectionName());
        myViewHolder.tvTeacher.setText(itemList.get(i).getStaffName());
        myViewHolder.tvGrade.setText(itemList.get(i).getGradeObtained()+"("+itemList.get(i).getPercentMarks()+"%)");
        myViewHolder.tvGPA.setText(""+itemList.get(i).getGpa());
        myViewHolder.tvComments.setText(itemList.get(i).getComments());
        myViewHolder.tvTeacherComments.setText(itemList.get(i).getTeacherComments());

        myViewHolder.llComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!itemList.get(i).isExpanded()){
                    ((ReportCardDetailsActivity)activity).updateStatus(i,true);
                }else {
                    ((ReportCardDetailsActivity)activity).updateStatus(i,false);
                }
            }
        });

        if (itemList.get(i).isExpanded())
        {

            myViewHolder.llCommentDetails.setVisibility(View.VISIBLE);
            myViewHolder.llComment.setVisibility(View.GONE);

        }
        else
        {
            myViewHolder.llCommentDetails.setVisibility(View.GONE);
            myViewHolder.llComment.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

       TextView tvCourse,tvTeacher,tvGrade,tvGPA,tvComments,tvTeacherComments;
       LinearLayout llComment,llCommentDetails;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCourse=(TextView)itemView.findViewById(R.id.tvCourse);
            tvTeacher=(TextView)itemView.findViewById(R.id.tvTeacher);
            tvGrade=(TextView)itemView.findViewById(R.id.tvGrade);
            tvGPA=(TextView)itemView.findViewById(R.id.tvGPA);
            tvComments=(TextView)itemView.findViewById(R.id.tvComments);
            tvTeacherComments=(TextView)itemView.findViewById(R.id.tvTeacherComments);

            llCommentDetails=(LinearLayout) itemView.findViewById(R.id.llCommentDetails);
            llComment=(LinearLayout) itemView.findViewById(R.id.llComment);
        }
    }

    public ReportCardAdapter(ArrayList<ReportCardModel> itemList, Context context, Activity activity) {
        this.itemList = itemList;
        this.context = context;
        this.activity = activity;
    }
}
