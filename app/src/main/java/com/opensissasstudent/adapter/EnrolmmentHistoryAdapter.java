package com.opensissasstudent.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.opensissasstudent.R;
import com.opensissasstudent.model.EnrpllmentHistoryModel;
import com.opensissasstudent.model.FamilyInfoModel;
import com.opensissasstudent.others.utility.Util;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class EnrolmmentHistoryAdapter extends RecyclerView.Adapter<EnrolmmentHistoryAdapter.MyViewHolder> {
    ArrayList<EnrpllmentHistoryModel> itemList = new ArrayList<>();
    Context context;




    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.enrollment_history_raw, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.tvSchoolYear.setText(itemList.get(i).getStartYear()+"-"+itemList.get(i).getEndYear());
        myViewHolder.tvEnroolMentCode.setText(itemList.get(i).getEnrollmentCode());
        myViewHolder.tvEnroolMentDate.setText(Util.changeAnyDateFormat(itemList.get(i).getEnrollmentDate(),"yyyy-MM-dd","dd MMM,yyyy"));
        myViewHolder.tvExitCode.setText(itemList.get(i).getExitCode());
        myViewHolder.tvExitDate.setText(Util.changeAnyDateFormat(itemList.get(i).getExitDate(),"yyyy-MM-dd","dd MMM,yyyy"));
        myViewHolder.tvGrade.setText(itemList.get(i).getGradeLevelTitle());
        myViewHolder.tvSchool.setText(itemList.get(i).getSchoolName());
        if (itemList.get(i).getEnrollmentType().equalsIgnoreCase("Add")){
            myViewHolder.imgEnrollCode.setImageDrawable(context.getResources().getDrawable(R.drawable.plus));
        }


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        ImageView  imgEnrollCode,imgExitCode;
       TextView tvSchoolYear,tvEnroolMentDate,tvEnroolMentCode,tvGrade,tvExitDate,tvExitCode,tvSchool;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSchoolYear=(TextView)itemView.findViewById(R.id.tvSchoolYear);
            tvEnroolMentDate=(TextView)itemView.findViewById(R.id.tvEnroolMentDate);
            tvEnroolMentCode=(TextView)itemView.findViewById(R.id.tvEnroolMentCode);
            tvGrade=(TextView)itemView.findViewById(R.id.tvGrade);
            tvExitDate=(TextView)itemView.findViewById(R.id.tvExitDate);
            tvExitCode=(TextView)itemView.findViewById(R.id.tvExitCode);
            tvSchool=(TextView)itemView.findViewById(R.id.tvSchool);
            imgEnrollCode=(ImageView) itemView.findViewById(R.id.imgEnrollCode);
            imgExitCode=(ImageView) itemView.findViewById(R.id.imgExitCode);


        }
    }

    public EnrolmmentHistoryAdapter(ArrayList<EnrpllmentHistoryModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }
}
