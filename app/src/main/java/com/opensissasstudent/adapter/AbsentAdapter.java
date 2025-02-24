package com.opensissasstudent.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.opensissasstudent.R;
import com.opensissasstudent.model.AbsentModel;
import com.opensissasstudent.model.CurseDetailsModel;
import com.opensissasstudent.others.utility.Util;
import com.opensissasstudent.ui.student.activity.AbsentSummaryDetailsActivity;
import com.opensissasstudent.ui.student.activity.MyGardeBookGradeDetailsActivity;

import java.util.ArrayList;


public class AbsentAdapter extends RecyclerView.Adapter<AbsentAdapter.MyViewHolder> {
    ArrayList<AbsentModel> itemList = new ArrayList<>();
    Context context;
    Activity activity;

    private RecyclerView.RecycledViewPool
            viewPool
            = new RecyclerView
            .RecycledViewPool();

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.absent_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {



        myViewHolder.setIsRecyclable(false);

            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(
                    myViewHolder
                            .rvChild
                            .getContext(),
                    LinearLayoutManager.VERTICAL,
                    false);

            layoutManager
                    .setInitialPrefetchItemCount(
                            itemList.get(i)
                                    .getItemList()
                                    .size());

            AbsentSummaryChildAdapter childItemAdapter
                    = new AbsentSummaryChildAdapter(
                    itemList.get(i)
                            .getItemList());
            myViewHolder
                    .rvChild
                    .setLayoutManager(layoutManager);
            myViewHolder
                    .rvChild
                    .setAdapter(childItemAdapter);
            myViewHolder
                    .rvChild
                    .setRecycledViewPool(viewPool);

            myViewHolder.tvAttendanceShortCode.setText(itemList.get(i).getAttendanceshortCode());
        myViewHolder.tvAttendanceCode.setText(itemList.get(i).getAttendance());
        myViewHolder.tvDate.setText(Util.changeAnyDateFormat(itemList.get(i).getAbsenceDate(),"yyyy-MM-dd","dd MMM, yyyy"));
        myViewHolder.tvAdminComment.setText(itemList.get(i).getAdminComment());
        myViewHolder.tvTeacherComment.setText(itemList.get(i).getTeacherComment());

        myViewHolder.llAbsent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!itemList.get(i).isExpanded()){
                    ((AbsentSummaryDetailsActivity)activity).updateStatus(i,true);
                }else {
                    ((AbsentSummaryDetailsActivity)activity).updateStatus(i,false);
                }
            }
        });

        if (itemList.get(i).isExpanded())
        {
            myViewHolder.imgDown.setVisibility(View.GONE);
            myViewHolder.imgUp.setVisibility(View.VISIBLE);
            myViewHolder.llAbsentDetails.setVisibility(View.VISIBLE);
            myViewHolder.llAbsent.setVisibility(View.VISIBLE);

        }
        else
        {
            myViewHolder.imgDown.setVisibility(View.VISIBLE);
            myViewHolder.imgUp.setVisibility(View.GONE);
            myViewHolder.llAbsentDetails.setVisibility(View.GONE);
            myViewHolder.llAbsent.setVisibility(View.VISIBLE);
        }

        if (itemList.get(i).getAttendanceshortCode().equalsIgnoreCase("P")){
            myViewHolder.llShortCode.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.green));

        }else if (itemList.get(i).getAttendanceshortCode().equalsIgnoreCase("A")){
            myViewHolder.llShortCode.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.missing_attendance));

        }else {
            myViewHolder.llShortCode.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.color3));

        }

        if (!itemList.get(i).getAdminComment().equalsIgnoreCase("") && itemList.get(i).getTeacherComment().equalsIgnoreCase("")  ){
            myViewHolder.llComment.setVisibility(View.VISIBLE);
        }else {
            myViewHolder.llComment.setVisibility(View.GONE);
        }




    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

       LinearLayout llAbsent,llAbsentDetails,llComment,llShortCode;
       TextView tvAttendanceShortCode,tvAttendanceCode,tvDate,tvAdminComment,tvTeacherComment;
       RecyclerView rvChild;
       ImageView imgDown,imgUp;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAttendanceShortCode=(TextView)itemView.findViewById(R.id.tvAttendanceShortCode);
            tvAttendanceCode=(TextView)itemView.findViewById(R.id.tvAttendanceCode);
            tvDate=(TextView)itemView.findViewById(R.id.tvDate);
            tvAdminComment=(TextView)itemView.findViewById(R.id.tvAdminComment);
            tvTeacherComment=(TextView)itemView.findViewById(R.id.tvTeacherComment);

            imgUp=(ImageView)itemView.findViewById(R.id.imgUp);
            imgDown=(ImageView)itemView.findViewById(R.id.imgDown);;


            llAbsent=(LinearLayout)itemView.findViewById(R.id.llAbsent);
            llAbsentDetails=(LinearLayout)itemView.findViewById(R.id.llAbsentDetails);
            llComment=(LinearLayout)itemView.findViewById(R.id.llComment);
            llShortCode=(LinearLayout)itemView.findViewById(R.id.llShortCode);

            rvChild=(RecyclerView) itemView.findViewById(R.id.rvChild);



        }
    }

    public AbsentAdapter(ArrayList<AbsentModel> itemList, Context context, Activity activity) {
        this.itemList = itemList;
        this.context = context;
        this.activity = activity;
    }
}
