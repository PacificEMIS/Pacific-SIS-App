package com.pacific.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pacific.R;
import com.pacific.model.CourseVeriableScheduleModel;

import java.util.ArrayList;


public class CourseVariableScheduleAdapter extends RecyclerView.Adapter<CourseVariableScheduleAdapter.MyViewHolder> {
    ArrayList<CourseVeriableScheduleModel> itemList = new ArrayList<>();
    Context context;



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.course_variable_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

        myViewHolder.tvDay.setText(itemList.get(i).getDay());
        myViewHolder.tvRoom.setText(itemList.get(i).getRoom());
        myViewHolder.tvPeriod.setText(itemList.get(i).getPeriod());
        myViewHolder.tvTime.setText(itemList.get(i).getTime());

        myViewHolder.setIsRecyclable(false);

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


       TextView tvDay,tvPeriod,tvTime,tvRoom;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDay=(TextView)itemView.findViewById(R.id.tvDay);
            tvTime=(TextView)itemView.findViewById(R.id.tvTime);
            tvPeriod=(TextView)itemView.findViewById(R.id.tvPeriod);
            tvRoom=(TextView)itemView.findViewById(R.id.tvRoom);






        }
    }

    public CourseVariableScheduleAdapter(ArrayList<CourseVeriableScheduleModel> itemList) {
        this.itemList = itemList;

    }
}
