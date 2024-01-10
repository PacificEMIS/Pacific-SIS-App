package com.opensissas.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.opensissas.R;
import com.opensissas.model.TeacherNotificationModel;

import java.util.ArrayList;


public class TeacherNotificationAdapter extends RecyclerView.Adapter<TeacherNotificationAdapter.MyViewHolder> {
    ArrayList<TeacherNotificationModel> itemList = new ArrayList<>();
    Context context;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.teacher_notifcation_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {



    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


        }
    }

    public TeacherNotificationAdapter(ArrayList<TeacherNotificationModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }
}
