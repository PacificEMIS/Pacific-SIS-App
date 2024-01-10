package com.opensissas.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.opensissas.R;
import com.opensissas.model.AttendanceCodeModel;
import com.opensissas.ui.teacher.fragment.ClassAttendanceFragment;

import java.util.ArrayList;


public class AttendanceCodeAdapter extends RecyclerView.Adapter<AttendanceCodeAdapter.MyViewHolder> {
    ArrayList<AttendanceCodeModel> itemList = new ArrayList<>();
    Context context;
    ClassAttendanceFragment fragment;
    int pos;


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.attendance_code_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.tvAttendanceName.setText(itemList.get(i).getAttendanceName()+"( "+itemList.get(i).getAttendanceShortName()+" )");
        if (itemList.get(i).getAttendanceName().equalsIgnoreCase("Present")){
            myViewHolder.lnAttendance.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.green));

        }else if (itemList.get(i).getAttendanceName().equalsIgnoreCase("Absent")){
            myViewHolder.lnAttendance.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.missing_attendance));

        }else if (itemList.get(i).getAttendanceName().equalsIgnoreCase("Tardy")){
            myViewHolder.lnAttendance.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.color3));

        }else if (itemList.get(i).getAttendanceShortName().equalsIgnoreCase("EA")){
            myViewHolder.lnAttendance.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.color1));

        }else if (itemList.get(i).getAttendanceName().equalsIgnoreCase("Late")){
            myViewHolder.lnAttendance.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.orangecolor));

        }else if (itemList.get(i).getAttendanceShortName().equalsIgnoreCase("HF")){
            myViewHolder.lnAttendance.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.orangecolor));

        }else {
            myViewHolder.lnAttendance.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.green));

        }

        myViewHolder.lnAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ClassAttendanceFragment)fragment).getcode(i,pos);
            }
        });



    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

       LinearLayout lnAttendance;

       TextView tvAttendanceName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAttendanceName=(TextView)itemView.findViewById(R.id.tvAttendanceName);
            lnAttendance=(LinearLayout)itemView.findViewById(R.id.lnAttendance);




        }
    }

    public AttendanceCodeAdapter(ArrayList<AttendanceCodeModel> itemList, Context context,ClassAttendanceFragment fragment,int pos) {
        this.itemList = itemList;
        this.context = context;
        this.fragment=fragment;
        this.pos=pos;
    }
}
