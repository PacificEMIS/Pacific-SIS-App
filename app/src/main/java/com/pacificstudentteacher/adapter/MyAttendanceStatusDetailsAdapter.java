package com.pacificstudentteacher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pacificstudentteacher.R;
import com.pacificstudentteacher.model.AttendanceStatusDetailsModel;

import java.util.ArrayList;


public class MyAttendanceStatusDetailsAdapter extends RecyclerView.Adapter<MyAttendanceStatusDetailsAdapter.MyViewHolder> {
    ArrayList<AttendanceStatusDetailsModel> itemList = new ArrayList<>();
    Context context;



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.attendace_status_details_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

        myViewHolder.tvStatus.setText(itemList.get(i).getStatus());




    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


       TextView tvStatus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStatus=(TextView)itemView.findViewById(R.id.tvStatus);







        }
    }

    public MyAttendanceStatusDetailsAdapter(ArrayList<AttendanceStatusDetailsModel> itemList) {
        this.itemList = itemList;

    }
}
