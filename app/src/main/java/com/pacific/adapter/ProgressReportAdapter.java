package com.pacific.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pacific.R;
import com.pacific.model.ProgressReportModel;
import com.pacific.ui.student.fragment.ProgressReportFragment;

import java.util.ArrayList;


public class ProgressReportAdapter extends RecyclerView.Adapter<ProgressReportAdapter.MyViewHolder> {
    ArrayList<ProgressReportModel> itemList = new ArrayList<>();
    Context context;
    Fragment fragment;

    private RecyclerView.RecycledViewPool
            viewPool
            = new RecyclerView
            .RecycledViewPool();

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.progress_report_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

        myViewHolder.tvCourse.setText(itemList.get(i).getCourse());
        myViewHolder.tvCourseSection.setText(itemList.get(i).getCourseSection());
        myViewHolder.tvTeacher.setText(itemList.get(i).getTeacher());
        myViewHolder.tvPoints.setText(itemList.get(i).getTotalpoints() + "/" + itemList.get(i).getTotalassignpoints());



        if (itemList.get(i).getSize() > 0) {
            myViewHolder.llTotal.setVisibility(View.VISIBLE);
        } else {
            myViewHolder.llTotal.setVisibility(View.GONE);

        }


        myViewHolder.setIsRecyclable(false);


        LinearLayoutManager layoutManager
                = new LinearLayoutManager(
                myViewHolder
                        .rvProgressReport
                        .getContext(),
                LinearLayoutManager.VERTICAL,
                false);

        layoutManager
                .setInitialPrefetchItemCount(
                        itemList.get(i)
                                .getItemList()
                                .size());

        ProgressReportChildAdapter childItemAdapter
                = new ProgressReportChildAdapter(
                itemList.get(i)
                        .getItemList());
        myViewHolder
                .rvProgressReport
                .setLayoutManager(layoutManager);
        myViewHolder
                .rvProgressReport
                .setAdapter(childItemAdapter);
        myViewHolder
                .rvProgressReport
                .setRecycledViewPool(viewPool);


        myViewHolder.llProgressReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!itemList.get(i).isExpanded()) {
                    ((ProgressReportFragment) fragment).updateStatus(i, true);
                } else {
                    ((ProgressReportFragment) fragment).updateStatus(i, false);
                }


            }
        });

        if (itemList.get(i).isExpanded()) {


            myViewHolder.imgDown.setVisibility(View.GONE);
            myViewHolder.imgUp.setVisibility(View.VISIBLE);
            myViewHolder.llProgressReportDetails.setVisibility(View.VISIBLE);


        } else {
            myViewHolder.imgDown.setVisibility(View.VISIBLE);
            myViewHolder.imgUp.setVisibility(View.GONE);
            myViewHolder.llProgressReportDetails.setVisibility(View.GONE);
        }


    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView tvCourse, tvCourseSection, tvTeacher, tvPoints, tvTotalGrade, tvWeightGrade;
        RecyclerView rvProgressReport;
        LinearLayout llProgressReportDetails, llProgressReport,llTotal;
        ImageView imgDown, imgUp;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCourse = (TextView) itemView.findViewById(R.id.tvCourse);
            tvCourseSection = (TextView) itemView.findViewById(R.id.tvCourseSection);
            tvTeacher = (TextView) itemView.findViewById(R.id.tvTeacher);
            tvPoints = (TextView) itemView.findViewById(R.id.tvPoints);
            tvTotalGrade = (TextView) itemView.findViewById(R.id.tvTotalGrade);
            tvWeightGrade = (TextView) itemView.findViewById(R.id.tvWeightGrade);


            rvProgressReport = (RecyclerView) itemView.findViewById(R.id.rvProgressReport);
            llProgressReport = (LinearLayout) itemView.findViewById(R.id.llProgressReport);
            llProgressReportDetails = (LinearLayout) itemView.findViewById(R.id.llProgressReportDetails);
            llTotal=(LinearLayout)itemView.findViewById(R.id.llTotal);

            imgUp = (ImageView) itemView.findViewById(R.id.imgUp);
            imgDown = (ImageView) itemView.findViewById(R.id.imgDown);


        }
    }

    public ProgressReportAdapter(ArrayList<ProgressReportModel> itemList, Context context, Fragment fragment) {
        this.itemList = itemList;
        this.context = context;
        this.fragment = fragment;
    }
}
