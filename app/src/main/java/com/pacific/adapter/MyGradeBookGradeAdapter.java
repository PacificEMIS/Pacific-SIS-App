package com.pacific.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pacific.R;
import com.pacific.model.GradeBookGradeModel;
import com.pacific.ui.student.activity.MyGardeBookGradeDetailsActivity;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class MyGradeBookGradeAdapter extends RecyclerView.Adapter<MyGradeBookGradeAdapter.MyViewHolder> {
    ArrayList<GradeBookGradeModel> itemList = new ArrayList<>();
    Context context;


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.gradebook_grade_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

      myViewHolder.tvLetter.setText(itemList.get(i).getLetter());
        myViewHolder.tvCourseName.setText(itemList.get(i).getCourseSectionName());
        myViewHolder.tvPercentage.setText(itemList.get(i).getPercent());
        myViewHolder.tvStaff.setText(itemList.get(i).getStaff());
        if (itemList.get(i).getStaff().equals(" ")){
            myViewHolder.imgTeacher.setVisibility(View.GONE);
        }else {
            myViewHolder.imgTeacher.setVisibility(View.VISIBLE);
        }

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, MyGardeBookGradeDetailsActivity.class);
                intent.putExtra("courseName",itemList.get(i).getCourseSectionName());
                intent.putExtra("percentage",itemList.get(i).getPercent());
                intent.putExtra("letter",itemList.get(i).getLetter());
                intent.putExtra("staff",itemList.get(i).getStaff());
                intent.putExtra("courseid",itemList.get(i).getCourseSectionId());
                intent.putExtra("ungarde",itemList.get(i).getUngraded());
                intent.putExtra("garderange",itemList.get(i).getHighestGrade()+"-"+itemList.get(i).getLowestGrade());
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
        TextView tvLetter,tvCourseName,tvPercentage,tvStaff;
        CircleImageView imgTeacher;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvLetter=(TextView)itemView.findViewById(R.id.tvLetter);
            tvCourseName=(TextView)itemView.findViewById(R.id.tvCourseName);
            tvPercentage=(TextView)itemView.findViewById(R.id.tvPercentage);
            tvStaff=(TextView)itemView.findViewById(R.id.tvStaff);
            imgTeacher=(CircleImageView) itemView.findViewById(R.id.imgTeacher);

        }
    }

    public MyGradeBookGradeAdapter(ArrayList<GradeBookGradeModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;

    }
}
