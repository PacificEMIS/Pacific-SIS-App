package com.pacificstudentteacher.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pacificstudentteacher.R;
import com.pacificstudentteacher.model.ProgressReportChildModel;

import java.util.ArrayList;


public class ProgressReportChildAdapter extends RecyclerView.Adapter<ProgressReportChildAdapter.MyViewHolder> {
    ArrayList<ProgressReportChildModel> itemList = new ArrayList<>();




    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.progress_report_child_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

        myViewHolder.tvAssignment.setText(itemList.get(i).getAssignmentTitle());
        myViewHolder.tvAssignmentType.setText(itemList.get(i).getAssignmentTypeTitle());
        myViewHolder.tvWeight.setText(""+itemList.get(i).getWeight());
        myViewHolder.tvAssignmentDate.setText(itemList.get(i).getAssignmentDate());
        myViewHolder.tvDueDate.setText(itemList.get(i).getDueDate());
        myViewHolder.tvGrade.setText(itemList.get(i).getGrade());
        myViewHolder.tvWeightGrade.setText(itemList.get(i).getWieghtedGrade());
        myViewHolder.tvComment.setText(itemList.get(i).getComment());
        myViewHolder.tvPoints.setText(itemList.get(i).getPoints()+"/"+itemList.get(i).getAssignmentPoint());

        myViewHolder.setIsRecyclable(false);



    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

       TextView tvAssignmentType,tvWeight,tvAssignment,tvAssignmentDate,tvDueDate,tvPoints,tvGrade,tvWeightGrade,tvComment;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAssignmentType=(TextView)itemView.findViewById(R.id.tvAssignmentType);
            tvWeight=(TextView)itemView.findViewById(R.id.tvWeight);
            tvAssignment=(TextView)itemView.findViewById(R.id.tvAssignment);
            tvAssignmentDate=(TextView)itemView.findViewById(R.id.tvAssignmentDate);
            tvDueDate=(TextView)itemView.findViewById(R.id.tvDueDate);
            tvPoints=(TextView)itemView.findViewById(R.id.tvPoints);
            tvGrade=(TextView)itemView.findViewById(R.id.tvGrade);
            tvWeightGrade=(TextView)itemView.findViewById(R.id.tvWeightGrade);
            tvComment=(TextView)itemView.findViewById(R.id.tvComment);


        }
    }

    public ProgressReportChildAdapter(ArrayList<ProgressReportChildModel> itemList) {
        this.itemList = itemList;

    }
}
