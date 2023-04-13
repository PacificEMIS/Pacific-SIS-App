package com.opensis.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.opensis.R;
import com.opensis.model.AssignmentDetailsModel;
import com.opensis.model.ClassAssignmentModel;
import com.opensis.ui.teacher.activity.AssignmentDetailsActivity;
import com.opensis.ui.teacher.fragment.AssignmentsFragment;

import java.util.ArrayList;


public class ClassAssignmentAdapter extends RecyclerView.Adapter<ClassAssignmentAdapter.MyViewHolder> {
    ArrayList<ClassAssignmentModel> itemList = new ArrayList<>();
    Context context;
    AssignmentsFragment assignmentsFragment;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.class_assignment_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, AssignmentDetailsActivity.class);
                intent.putExtra("assignmentDetails",itemList.get(i).getAssignmentDetails());
                intent.putExtra("assignmentName",itemList.get(i).getAssignmentName());
                intent.putExtra("assignmentTypeID",itemList.get(i).getAssignmentTypeId());
                intent.putExtra("coursectionID",itemList.get(i).getCourseSectionId());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        myViewHolder.tvAssignmentName.setText(itemList.get(i).getAssignmentName());
        myViewHolder.lnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AssignmentsFragment)assignmentsFragment).editWorkoutPopup(i);
            }
        });





    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvAssignmentName;
        LinearLayout lnMore;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            lnMore=(LinearLayout)itemView.findViewById(R.id.lnMore);
            tvAssignmentName=(TextView)itemView.findViewById(R.id.tvAssignmentName);

        }
    }

    public ClassAssignmentAdapter(ArrayList<ClassAssignmentModel> itemList, Context context,AssignmentsFragment assignmentsFragment) {
        this.itemList = itemList;
        this.context = context;
        this.assignmentsFragment=assignmentsFragment;
    }
}
