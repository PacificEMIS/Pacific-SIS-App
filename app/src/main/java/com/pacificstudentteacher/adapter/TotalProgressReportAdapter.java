package com.pacificstudentteacher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pacificstudentteacher.R;
import com.pacificstudentteacher.model.ProgressReportModel;

import java.util.ArrayList;


public class TotalProgressReportAdapter extends RecyclerView.Adapter<TotalProgressReportAdapter.MyViewHolder> {
    ArrayList<ProgressReportModel> itemList = new ArrayList<>();
    Context context;



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.total_progress_report_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

        myViewHolder.tvCourse.setText(itemList.get(i).getCourse());
        myViewHolder.tvCourseSection.setText(itemList.get(i).getCourseSection());
        myViewHolder.tvTeacher.setText(itemList.get(i).getTeacher());
        myViewHolder.tvTotalGrade.setText(itemList.get(i).getTotalGrade());
        myViewHolder.tvTotalWeightGrade.setText(itemList.get(i).getTotlWeightgrade());



    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {



       TextView tvCourse,tvCourseSection,tvTeacher,tvTotalGrade,tvTotalWeightGrade;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCourse=(TextView)itemView.findViewById(R.id.tvCourse);
            tvCourseSection=(TextView)itemView.findViewById(R.id.tvCourseSection);
            tvTeacher=(TextView)itemView.findViewById(R.id.tvTeacher);
            tvTotalGrade=(TextView)itemView.findViewById(R.id.tvTotalGrade);
            tvTotalWeightGrade=(TextView)itemView.findViewById(R.id.tvTotalWeightGrade);



        }
    }

    public TotalProgressReportAdapter(ArrayList<ProgressReportModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;


    }


}
