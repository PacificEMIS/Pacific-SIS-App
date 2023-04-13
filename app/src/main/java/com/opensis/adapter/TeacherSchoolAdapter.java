package com.opensis.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.opensis.R;
import com.opensis.model.ClassStudentModel;
import com.opensis.model.TeacheSchoolModel;
import com.opensis.ui.teacher.activity.ClassDetailsActivity;
import com.opensis.ui.teacher.activity.SchoolDetailsActivity;

import java.util.ArrayList;


public class TeacherSchoolAdapter extends RecyclerView.Adapter<TeacherSchoolAdapter.MyViewHolder> {
    ArrayList<TeacheSchoolModel> itemList = new ArrayList<>();
    Context context;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.teacher_school_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.tvSchoolAddress.setText(itemList.get(i).getSchoolAddress());
        myViewHolder.tvSchoolName.setText(itemList.get(i).getSchoolName());

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, SchoolDetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("schoolID",itemList.get(i).getSchoolID());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvSchoolName,tvSchoolAddress;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSchoolAddress=(TextView)itemView.findViewById(R.id.tvSchoolAddress);
            tvSchoolName=(TextView)itemView.findViewById(R.id.tvSchoolName);


        }
    }

    public TeacherSchoolAdapter(ArrayList<TeacheSchoolModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }
}
