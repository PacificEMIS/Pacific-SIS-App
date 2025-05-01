package com.pacific.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pacific.R;
import com.pacific.model.GradeBookAssignmentDetailsModel;
import com.pacific.ui.student.activity.MyGardeBookGradeDetailsActivity;

import java.util.ArrayList;


public class MyGradeBookAssignmentAdapter extends RecyclerView.Adapter<MyGradeBookAssignmentAdapter.MyViewHolder> {
    ArrayList<GradeBookAssignmentDetailsModel> itemList = new ArrayList<>();
    Context context;
    Activity activity;


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.gradebook_assignment_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.tvAssignmentName.setText(itemList.get(i).getAssignemtnName());
        myViewHolder.tvAssignmentType.setText(itemList.get(i).getAssignmentType());
        myViewHolder.tvTitlePoints.setText(itemList.get(i).getPoints()+"/"+itemList.get(i).getPossible());
        myViewHolder.tvPointsPossibles.setText(itemList.get(i).getPoints()+"/"+itemList.get(i).getPossible());
        myViewHolder.tvPercent.setText(itemList.get(i).getPercentage());
        myViewHolder.tvTitlePercentage.setText(itemList.get(i).getPercentage());
        myViewHolder.tvTitleLetter.setText(itemList.get(i).getLetter());
        myViewHolder.tvLetter.setText(itemList.get(i).getLetter());
        myViewHolder.tvGradeRange.setText(itemList.get(i).getGradeRange());
        myViewHolder.tvAssignedDate.setText(itemList.get(i).getAssignedDate());
        myViewHolder.tvDueDate.setText(itemList.get(i).getDueDate());
        myViewHolder.tvAssignementDesc.setText(itemList.get(i).getDescription());

        myViewHolder.llAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!itemList.get(i).isExpanded()){
                    ((MyGardeBookGradeDetailsActivity)activity).updateStatus(i,true);
                }else {
                    ((MyGardeBookGradeDetailsActivity)activity).updateStatus(i,false);
                }
            }
        });

        if (itemList.get(i).isExpanded())
        {
            myViewHolder.imgDown.setVisibility(View.GONE);
            myViewHolder.imgUp.setVisibility(View.VISIBLE);
            myViewHolder.llAssignDetails.setVisibility(View.VISIBLE);
            myViewHolder.llTitle.setVisibility(View.GONE);

        }
        else
        {
            myViewHolder.imgDown.setVisibility(View.VISIBLE);
            myViewHolder.imgUp.setVisibility(View.GONE);
            myViewHolder.llAssignDetails.setVisibility(View.GONE);
            myViewHolder.llTitle.setVisibility(View.VISIBLE);
        }



    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvAssignmentName,tvTitlePoints,tvTitlePercentage,tvTitleLetter,tvAssignmentType,tvPointsPossibles,tvPercent,tvLetter,tvGradeRange,tvAssignementDesc;
        LinearLayout llAssignDetails,llAssignment,llTitle;
        ImageView imgDown,imgUp;
        TextView tvAssignedDate,tvDueDate;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvAssignmentName=(TextView)itemView.findViewById(R.id.tvAssignmentName);
            tvTitlePoints=(TextView)itemView.findViewById(R.id.tvTitlePoints);
            tvTitlePercentage=(TextView)itemView.findViewById(R.id.tvTitlePercentage);
            tvTitleLetter=(TextView)itemView.findViewById(R.id.tvTitleLetter);

            tvAssignmentType=(TextView)itemView.findViewById(R.id.tvAssignmentType);
            tvPointsPossibles=(TextView)itemView.findViewById(R.id.tvPointsPossibles);
            tvPercent=(TextView)itemView.findViewById(R.id.tvPercent);
            tvLetter=(TextView)itemView.findViewById(R.id.tvLetter);
            tvGradeRange=(TextView)itemView.findViewById(R.id.tvGradeRange);
            tvAssignementDesc=(TextView)itemView.findViewById(R.id.tvAssignementDesc);

            llAssignment=(LinearLayout) itemView.findViewById(R.id.llAssignment);
            llAssignDetails=(LinearLayout) itemView.findViewById(R.id.llAssignDetails);
            llTitle=(LinearLayout) itemView.findViewById(R.id.llTitle);

            imgDown=(ImageView) itemView.findViewById(R.id.imgDown);
            imgUp=(ImageView) itemView.findViewById(R.id.imgUp);


            tvAssignedDate=(TextView)itemView.findViewById(R.id.tvAssignedDate);
            tvDueDate=(TextView)itemView.findViewById(R.id.tvDueDate);


        }
    }

    public MyGradeBookAssignmentAdapter(ArrayList<GradeBookAssignmentDetailsModel> itemList, Context context, Activity activity) {
        this.itemList = itemList;
        this.context = context;
        this.activity = activity;
    }
}
