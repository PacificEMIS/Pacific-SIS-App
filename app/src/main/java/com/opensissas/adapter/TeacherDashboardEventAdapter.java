package com.opensissas.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.opensissas.R;
import com.opensissas.model.TeacherDashboardEventModel;
import com.opensissas.others.utility.Util;

import java.util.ArrayList;


public class TeacherDashboardEventAdapter extends RecyclerView.Adapter<TeacherDashboardEventAdapter.MyViewHolder> {
    ArrayList<TeacherDashboardEventModel> itemList = new ArrayList<>();
    Context context;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.event_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.tvEventName.setText(itemList.get(i).getEventName());
        myViewHolder.tvEventDate.setText(Util.changeAnyDateFormat(itemList.get(i).getEventDate(),"yyyy-MM-dd","MMM dd,yyyy"));
        if (itemList.get(i).getEventColor().equalsIgnoreCase("#d23240")) {
            myViewHolder.lnColor.setBackgroundTintList((ContextCompat.getColorStateList(context, R.color.color1)));
        }else if (itemList.get(i).getEventColor().equalsIgnoreCase("#FF9800")) {

            myViewHolder.lnColor.setBackgroundTintList((ContextCompat.getColorStateList(context, R.color.color2)));
        }else if (itemList.get(i).getEventColor().equalsIgnoreCase("#FFC107")) {
            myViewHolder.lnColor.setBackgroundTintList((ContextCompat.getColorStateList(context, R.color.color3)));
        }else if (itemList.get(i).getEventColor().equalsIgnoreCase("#4CAF50")) {
            myViewHolder.lnColor.setBackgroundTintList((ContextCompat.getColorStateList(context, R.color.color4)));
        }else if (itemList.get(i).getEventColor().equalsIgnoreCase("#00BCD4")) {
            myViewHolder.lnColor.setBackgroundTintList((ContextCompat.getColorStateList(context, R.color.color5)));
        }else if (itemList.get(i).getEventColor().equalsIgnoreCase("#E91E63")) {
            myViewHolder.lnColor.setBackgroundTintList((ContextCompat.getColorStateList(context, R.color.color6)));
        }else {
            myViewHolder.lnColor.setBackgroundTintList((ContextCompat.getColorStateList(context, R.color.color7)));
        }

        myViewHolder.tvEndDate.setText(Util.changeAnyDateFormat(itemList.get(i).getEndDate(),"yyyy-MM-dd","MMM dd,yyyy"));




    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvEventName,tvEventDate,tvEndDate;
        LinearLayout lnColor;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEventDate=(TextView)itemView.findViewById(R.id.tvEventDate);
            tvEventName=(TextView)itemView.findViewById(R.id.tvEventName);
            tvEndDate=(TextView)itemView.findViewById(R.id.tvEndDate);
            lnColor=(LinearLayout)itemView.findViewById(R.id.lnColor);


        }
    }

    public TeacherDashboardEventAdapter(ArrayList<TeacherDashboardEventModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }
}
