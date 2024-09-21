package com.opensissasstudent.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.opensissasstudent.R;
import com.opensissasstudent.model.AbsentSummaryChildModel;
import com.opensissasstudent.model.RequestedClassModel;

import java.util.ArrayList;


public class RequestedClassAdapter extends RecyclerView.Adapter<RequestedClassAdapter.MyViewHolder> {
    ArrayList<RequestedClassModel> itemList = new ArrayList<>();
    Context context;



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.requested_class_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {


        myViewHolder.tvStatus.setText(itemList.get(i).getApprovalStatus());
        myViewHolder.tvCourse.setText(itemList.get(i).getSubject());
        if (itemList.get(i).getApprovalStatus().equalsIgnoreCase("Rejected")){
            myViewHolder.tvStatus.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F44336")));
        }


        myViewHolder.setIsRecyclable(false);

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


       TextView tvCourse,tvStatus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStatus=(TextView)itemView.findViewById(R.id.tvStatus);
            tvCourse=(TextView)itemView.findViewById(R.id.tvCourse);







        }
    }

    public RequestedClassAdapter(ArrayList<RequestedClassModel> itemList) {
        this.itemList = itemList;

    }
}
