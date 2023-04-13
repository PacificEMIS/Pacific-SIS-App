package com.opensis.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.opensis.R;
import com.opensis.model.TeacherNoticeModel;

import java.util.ArrayList;


public class TeacherNoticeAdapter extends RecyclerView.Adapter<TeacherNoticeAdapter.MyViewHolder> {
    ArrayList<TeacherNoticeModel> itemList = new ArrayList<>();
    Context context;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.teacher_notice_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.tvNotice.setText(itemList.get(i).getNotice());
        myViewHolder.tvNoticeDetails.setText(HtmlCompat.fromHtml(itemList.get(i).getDetails(), 0));



    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNotice,tvNoticeDetails;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNoticeDetails=(TextView)itemView.findViewById(R.id.tvNoticeDetails);
            tvNotice=(TextView)itemView.findViewById(R.id.tvNotice);


        }
    }

    public TeacherNoticeAdapter(ArrayList<TeacherNoticeModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }
}
