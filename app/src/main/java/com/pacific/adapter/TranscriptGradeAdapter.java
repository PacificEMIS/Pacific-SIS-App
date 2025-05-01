package com.pacific.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.pacific.R;
import com.pacific.model.TranscriptGradeModel;
import com.pacific.ui.student.fragment.TranscriptGradeFragment;

import java.util.ArrayList;


public class TranscriptGradeAdapter extends RecyclerView.Adapter<TranscriptGradeAdapter.MyViewHolder> {
    ArrayList<TranscriptGradeModel> itemList = new ArrayList<>();
    Context context;
    Fragment fragment;



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.transcript_grade_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        final TranscriptGradeModel gradeModel = itemList.get(i);
        myViewHolder.tvGrade.setText(itemList.get(i).getGradeName());

        myViewHolder.setIsRecyclable(false);

        if (gradeModel.isSelected()){
            myViewHolder.llTick.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.text_color));
            myViewHolder.imgTick.setVisibility(View.VISIBLE);
        }else {
            myViewHolder.imgTick.setVisibility(View.GONE);
        }


        myViewHolder.llTick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                gradeModel.setSelected(!gradeModel.isSelected());


                if (gradeModel.isSelected()) {

                    myViewHolder.imgTick.setVisibility(View.VISIBLE);
                    notifyDataSetChanged();
                    itemList.get(i).setSelected(true);
                    ((TranscriptGradeFragment) fragment).updateAttendanceStatus(i, true );
                    myViewHolder.llTick.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.text_color));



                } else {

                    myViewHolder.imgTick.setVisibility(View.GONE);
                   ((TranscriptGradeFragment) fragment).updateAttendanceStatus(i, false );
                    itemList.get(i).setSelected(false);
                    notifyDataSetChanged();

                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

       TextView tvGrade;
       LinearLayout llTick;
       ImageView imgTick;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGrade=(TextView)itemView.findViewById(R.id.tvGrade);

            llTick=(LinearLayout) itemView.findViewById(R.id.llTick);
            imgTick=(ImageView) itemView.findViewById(R.id.imgTick);


        }
    }

    public TranscriptGradeAdapter(ArrayList<TranscriptGradeModel> itemList, Context context, Fragment fragment) {
        this.itemList = itemList;
        this.context = context;
        this.fragment = fragment;
    }
}
