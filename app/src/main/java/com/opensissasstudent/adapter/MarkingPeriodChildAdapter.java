package com.opensissasstudent.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.opensissasstudent.R;
import com.opensissasstudent.model.MarkingPeriodChildModel;
import com.opensissasstudent.ui.student.activity.MarkingPeriodDetailsActivity;

import java.util.ArrayList;


public class MarkingPeriodChildAdapter extends RecyclerView.Adapter<MarkingPeriodChildAdapter.MyViewHolder> {
    ArrayList<MarkingPeriodChildModel> itemList = new ArrayList<>();
    Context context;
    private RecyclerView.RecycledViewPool
            viewPool
            = new RecyclerView
            .RecycledViewPool();
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.marking_child_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {


        myViewHolder.tvTitle.setText(itemList.get(i).getTitle());
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
                                .getItemlist()
                                .size());

        MarkingPeriodChildtoChildAdapter childItemAdapter
                = new MarkingPeriodChildtoChildAdapter(
                itemList.get(i)
                        .getItemlist(),context);
        myViewHolder
                .rvChild
                .setLayoutManager(layoutManager);
        myViewHolder
                .rvChild
                .setAdapter(childItemAdapter);
        myViewHolder
                .rvChild
                .setRecycledViewPool(viewPool);

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, MarkingPeriodDetailsActivity.class);
                intent.putExtra("title",itemList.get(i).getTitle());
                intent.putExtra("shortname",itemList.get(i).getShortName());
                intent.putExtra("begindate",itemList.get(i).getStartDate());
                intent.putExtra("enddate",itemList.get(i).getEndDate());
                intent.putExtra("postingbegindate",itemList.get(i).getPostStartDate());
                intent.putExtra("postingenddate",itemList.get(i).getPostEndDate());
                intent.putExtra("exam",itemList.get(i).isDoesExam());
                intent.putExtra("graded",itemList.get(i).isDoesGrades());
                intent.putExtra("comment",itemList.get(i).isDoesComments());
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

        TextView tvTitle;
        RecyclerView rvChild;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle=(TextView)itemView.findViewById(R.id.tvTitle);
            rvChild=(RecyclerView) itemView.findViewById(R.id.rvChild);

        }
    }

    public MarkingPeriodChildAdapter(ArrayList<MarkingPeriodChildModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;

    }






}
