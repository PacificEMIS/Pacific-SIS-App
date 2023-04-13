package com.opensis.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.opensis.R;
import com.opensis.model.StudentsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.MyViewHolder> {
    ArrayList<StudentsModel> itemList = new ArrayList<>();
    Context context;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.students_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

    myViewHolder.tvID.setText("ID :"+itemList.get(i).getStudentsID());
    myViewHolder.tvStudentName.setText(itemList.get(i).getStudentName());
    Picasso.with(context).load(itemList.get(i).getStudentImage()).skipMemoryCache().placeholder(R.drawable.student_user).error(R.drawable.student_user).into(myViewHolder.imgUser);



    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvStudentName,tvID;
        ImageView imgUser;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvID=(TextView)itemView.findViewById(R.id.tvID);
            tvStudentName=(TextView)itemView.findViewById(R.id.tvStudentName);
            imgUser=(ImageView)itemView.findViewById(R.id.imgUser);


        }
    }

    public StudentAdapter(ArrayList<StudentsModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    public void updateList(ArrayList<StudentsModel> list){
        itemList = list;
        notifyDataSetChanged();
    }
}
