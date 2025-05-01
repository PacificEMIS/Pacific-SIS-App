package com.pacific.adapter;

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

import com.pacific.R;
import com.pacific.model.CurseDetailsModel;

import java.util.ArrayList;


public class CourseDetailsAdapter extends RecyclerView.Adapter<CourseDetailsAdapter.MyViewHolder> {
    ArrayList<CurseDetailsModel> itemList = new ArrayList<>();
    Context context;

    private RecyclerView.RecycledViewPool
            viewPool
            = new RecyclerView
            .RecycledViewPool();

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.coursedetails_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

        myViewHolder.tvCourseSectionName.setText(itemList.get(i).getCourseSectionName());
        myViewHolder.tvRoom.setText(itemList.get(i).getRoom());
        myViewHolder.tvPeriod.setText(itemList.get(i).getPeriodTitle());
        myViewHolder.tvTime.setText(itemList.get(i).getTime());
        myViewHolder.tvScheduleType.setText("Schedule Type: "+itemList.get(i).getScheduleType());
        if (!itemList.get(i).getStaffName().equalsIgnoreCase("")){
            myViewHolder.tvTeacherName.setText(itemList.get(i).getStaffName());
        }else {
            myViewHolder.tvTeacherName.setText("No Teacher Scheduled Yet");
        }


            if (itemList.get(i).getMeetingDays().contains("Monday")) {
                myViewHolder.lnMon.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.text_color));
                myViewHolder.tvMon.setTextColor(ContextCompat.getColorStateList(context, R.color.white));
            }
            if (itemList.get(i).getMeetingDays().contains("Tuesday")) {
                myViewHolder.lnTus.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.text_color));
                myViewHolder.tvTus.setTextColor(ContextCompat.getColorStateList(context, R.color.white));
            }

            if (itemList.get(i).getMeetingDays().contains("Wednesday")) {
                myViewHolder.lnWed.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.text_color));
                myViewHolder.tvWed.setTextColor(ContextCompat.getColorStateList(context, R.color.white));
            }

            if (itemList.get(i).getMeetingDays().contains("Thursday")) {
                myViewHolder.lnThu.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.text_color));
                myViewHolder.tvThu.setTextColor(ContextCompat.getColorStateList(context, R.color.white));
            }

            if (itemList.get(i).getMeetingDays().contains("Friday")) {
                myViewHolder.lnFri.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.text_color));
                myViewHolder.tvFri.setTextColor(ContextCompat.getColorStateList(context, R.color.white));
            }

            if (itemList.get(i).getMeetingDays().contains("Saturday")) {
                myViewHolder.lnSat.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.text_color));
                myViewHolder. tvSat.setTextColor(ContextCompat.getColorStateList(context, R.color.white));
            }

            if (itemList.get(i).getMeetingDays().contains("Sunday")) {
                myViewHolder. lnSun.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.text_color));
                myViewHolder. tvSun.setTextColor(ContextCompat.getColorStateList(context, R.color.white));
            }



        myViewHolder.setIsRecyclable(false);

        if (itemList.get(i).getMeetingDays().equals("")){
            myViewHolder.llFixedSchedule.setVisibility(View.GONE);
            myViewHolder.llVariableSchedule.setVisibility(View.VISIBLE);
            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(
                    myViewHolder
                            .rvVariable
                            .getContext(),
                    LinearLayoutManager.VERTICAL,
                    false);

            layoutManager
                    .setInitialPrefetchItemCount(
                            itemList.get(i)
                                    .getVariablescheduleList()
                                    .size());

            CourseVariableScheduleAdapter childItemAdapter
                    = new CourseVariableScheduleAdapter(
                    itemList.get(i)
                            .getVariablescheduleList());
            myViewHolder
                    .rvVariable
                    .setLayoutManager(layoutManager);
            myViewHolder
                    .rvVariable
                    .setAdapter(childItemAdapter);
            myViewHolder
                    .rvVariable
                    .setRecycledViewPool(viewPool);

        }else {
            myViewHolder.llFixedSchedule.setVisibility(View.VISIBLE);
            myViewHolder.llVariableSchedule.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

       LinearLayout llFixedSchedule,llVariableSchedule,lnSun,lnMon,lnTus,lnWed,lnThu,lnFri,lnSat;
       TextView tvCourseSectionName,tvScheduleType,tvPeriod,tvRoom,tvTeacherName,tvTime,tvSun,tvMon,tvTus,tvWed,tvThu,tvFri,tvSat;
       RecyclerView rvVariable;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCourseSectionName=(TextView)itemView.findViewById(R.id.tvCourseSectionName);
            tvScheduleType=(TextView)itemView.findViewById(R.id.tvScheduleType);
            tvPeriod=(TextView)itemView.findViewById(R.id.tvPeriod);
            tvRoom=(TextView)itemView.findViewById(R.id.tvRoom);
            tvTeacherName=(TextView)itemView.findViewById(R.id.tvTeacherName);
            tvTime=(TextView)itemView.findViewById(R.id.tvTime);

            tvSun=(TextView)itemView.findViewById(R.id.tvSun);
            tvMon=(TextView)itemView.findViewById(R.id.tvMon);
            tvTus=(TextView)itemView.findViewById(R.id.tvTus);
            tvWed=(TextView)itemView.findViewById(R.id.tvWed);
            tvThu=(TextView)itemView.findViewById(R.id.tvThu);
            tvFri=(TextView)itemView.findViewById(R.id.tvFri);
            tvSat=(TextView)itemView.findViewById(R.id.tvSat);

            llFixedSchedule=(LinearLayout)itemView.findViewById(R.id.llFixedSchedule);
            llVariableSchedule=(LinearLayout)itemView.findViewById(R.id.llVariableSchedule);
            lnSun=(LinearLayout)itemView.findViewById(R.id.lnSun);
            lnMon=(LinearLayout)itemView.findViewById(R.id.lnMon);
            lnTus=(LinearLayout)itemView.findViewById(R.id.lnTus);
            lnWed=(LinearLayout)itemView.findViewById(R.id.lnWed);
            lnThu=(LinearLayout)itemView.findViewById(R.id.lnThu);
            lnFri=(LinearLayout)itemView.findViewById(R.id.lnFri);
            lnSat=(LinearLayout)itemView.findViewById(R.id.lnSat);

            rvVariable=(RecyclerView) itemView.findViewById(R.id.rvVariable);



        }
    }

    public CourseDetailsAdapter(ArrayList<CurseDetailsModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }
}
