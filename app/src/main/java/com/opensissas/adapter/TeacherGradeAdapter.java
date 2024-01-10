package com.opensissas.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.opensissas.R;
import com.opensissas.model.TeacherGradeModel;
import com.opensissas.others.utility.Util;
import com.opensissas.ui.teacher.activity.StudentOfGradeActivity;

import java.util.ArrayList;


public class TeacherGradeAdapter extends RecyclerView.Adapter<TeacherGradeAdapter.MyViewHolder> {
    ArrayList<TeacherGradeModel> itemList = new ArrayList<>();
    Context context;
    int courseSectionID;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.teacher_grade_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

        myViewHolder.tvGradeName.setText(itemList.get(i).getGradeName());
        myViewHolder.tvGradeType.setText(itemList.get(i).getGradeType());
        myViewHolder.tvAssignedDate.setText(Util.changeAnyDateFormat(itemList.get(i).getAssignedDate(),"yyyy-MM-dd","MMM dd,yyyy"));
        myViewHolder.tvDueDate.setText(Util.changeAnyDateFormat(itemList.get(i).getDueDate(),"yyyy-MM-dd","MMM dd,yyyy"));
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos=i;
                Intent intent=new Intent(context, StudentOfGradeActivity.class);
                intent.putExtra("assignmentID",itemList.get(i).getGraderID());
                intent.putExtra("gradeTitle",itemList.get(i).getGradeType());
                intent.putExtra("assignmentTypeID",itemList.get(i).getAssignmentTypeId());
                intent.putExtra("gradeName",itemList.get(i).getGradeName());
                intent.putExtra("studentList",itemList.get(i).getStudentList());
                intent.putExtra("gradeList",itemList.get(i).getGradeList());
                intent.putExtra("assignedDate",itemList.get(i).getAssignedDate());
                intent.putExtra("dueDate",itemList.get(i).getDueDate());
                intent.putExtra("pos",pos);
                intent.putExtra("courseSectionID",courseSectionID);
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
        TextView tvAssignedDate,tvDueDate,tvGradeType,tvGradeName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGradeName=(TextView)itemView.findViewById(R.id.tvGradeName);
            tvGradeType=(TextView)itemView.findViewById(R.id.tvGradeType);
            tvDueDate=(TextView)itemView.findViewById(R.id.tvDueDate);
            tvAssignedDate=(TextView)itemView.findViewById(R.id.tvAssignedDate);


        }
    }

    public TeacherGradeAdapter(ArrayList<TeacherGradeModel> itemList, Context context,int courseSectionID) {
        this.itemList = itemList;
        this.context = context;
        this.courseSectionID=courseSectionID;
    }
}
