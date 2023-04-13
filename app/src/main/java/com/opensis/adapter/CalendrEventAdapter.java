package com.opensis.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.opensis.R;
import com.opensis.model.AttendanceCodeModel;
import com.opensis.model.AttendanceStudentModel;
import com.opensis.model.EventModel;
import com.opensis.ui.teacher.fragment.ClassAttendanceFragment;

import java.util.ArrayList;


public class CalendrEventAdapter extends RecyclerView.Adapter<CalendrEventAdapter.MyViewHolder> {
    ArrayList<String> itemList = new ArrayList<>();


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.calendar_event_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
       myViewHolder.tvEvent.setText(itemList.get(i));

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

       TextView tvEvent;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEvent=(TextView)itemView.findViewById(R.id.tvEvent);


        }
    }

    public CalendrEventAdapter(ArrayList<String> itemList) {
        this.itemList = itemList;
    }
}
