package com.opensissasstudent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.opensissasstudent.R;
import com.opensissasstudent.model.CurseDetailsModel;
import com.opensissasstudent.model.MyDaillyAttendancePeriodModel;

import java.util.ArrayList;
import java.util.HashMap;


public class MyAttendancePeriodAdapter extends RecyclerView.Adapter<MyAttendancePeriodAdapter.MyViewHolder> {
    HashMap<String,String>itemList=new HashMap<>();
    Context context;


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.attendace_period_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.tvPeriod.setText(itemList.get(i));







    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {



       TextView tvPeriod;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPeriod=(TextView)itemView.findViewById(R.id.tvPeriod);





        }
    }

    public MyAttendancePeriodAdapter(HashMap<String, String> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }
}
