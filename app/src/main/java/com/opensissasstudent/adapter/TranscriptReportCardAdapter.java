package com.opensissasstudent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.opensissasstudent.R;
import com.opensissasstudent.model.CourseVeriableScheduleModel;
import com.opensissasstudent.model.TranscriptReportCardModel;

import java.util.ArrayList;


public class TranscriptReportCardAdapter extends RecyclerView.Adapter<TranscriptReportCardAdapter.MyViewHolder> {
    ArrayList<TranscriptReportCardModel> itemList = new ArrayList<>();
    Context context;



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.transcript_report_card_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

        myViewHolder.tvCourseCode.setText(itemList.get(i).getCourseCode());
        myViewHolder.tvCourse.setText(itemList.get(i).getCourseSectionName());
        myViewHolder.tvCredtHours.setText(itemList.get(i).getCreditHours());
        myViewHolder.tvCreditEarned.setText(itemList.get(i).getCreditEarned());
        myViewHolder.tvGrade.setText(itemList.get(i).getGrade());
        myViewHolder.tvGP.setText(itemList.get(i).getGpValue());

        myViewHolder.setIsRecyclable(false);

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


       TextView tvCourseCode,tvCourse,tvCredtHours,tvCreditEarned,tvGrade,tvGP;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCourseCode=(TextView)itemView.findViewById(R.id.tvCourseCode);
            tvCourse=(TextView)itemView.findViewById(R.id.tvCourse);
            tvCredtHours=(TextView)itemView.findViewById(R.id.tvCredtHours);
            tvCreditEarned=(TextView)itemView.findViewById(R.id.tvCreditEarned);
            tvGrade=(TextView)itemView.findViewById(R.id.tvGrade);
            tvGP=(TextView)itemView.findViewById(R.id.tvGP);







        }
    }

    public TranscriptReportCardAdapter(ArrayList<TranscriptReportCardModel> itemList) {
        this.itemList = itemList;

    }
}
