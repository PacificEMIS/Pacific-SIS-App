package com.opensissasstudent.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.opensissasstudent.R;
import com.opensissasstudent.model.CourseCatalogModel;
import com.opensissasstudent.ui.student.activity.CourseCatalogDetailsActivity;

import java.util.ArrayList;


public class CourseCatalogAdapter extends RecyclerView.Adapter<CourseCatalogAdapter.MyViewHolder> {
    ArrayList<CourseCatalogModel> itemList = new ArrayList<>();
    Context context;



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.course_catalog_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

        myViewHolder.tvCourseTitle.setText(itemList.get(i).getCourseTitle());
        myViewHolder.tvCourseShortName.setText(itemList.get(i).getCourseShortName());
        myViewHolder.setIsRecyclable(false);

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, CourseCatalogDetailsActivity.class);
                intent.putExtra("courseTitle",itemList.get(i).getCourseTitle());
                intent.putExtra("courseShortName",itemList.get(i).getCourseShortName());
                intent.putExtra("credithours",itemList.get(i).getCreditHours());
                intent.putExtra("subject",itemList.get(i).getCourseSubject());
                intent.putExtra("grade",itemList.get(i).getCourseGradeLevel());
                intent.putExtra("coursesectionarray",itemList.get(i).getCourseSectionArray());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

       TextView tvCourseTitle,tvCourseShortName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCourseTitle=(TextView)itemView.findViewById(R.id.tvCourseTitle);
            tvCourseShortName=(TextView)itemView.findViewById(R.id.tvCourseShortName);

        }
    }

    public CourseCatalogAdapter(ArrayList<CourseCatalogModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }
}
