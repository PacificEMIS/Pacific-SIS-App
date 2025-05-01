package com.pacific.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pacific.R;
import com.pacific.model.SiblingModel;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class SiblingInfoAdapter extends RecyclerView.Adapter<SiblingInfoAdapter.MyViewHolder> {
    ArrayList<SiblingModel> itemList = new ArrayList<>();
    Context context;



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sibling_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

        myViewHolder.tvName.setText(itemList.get(i).getName());
        myViewHolder.tvGrade.setText(itemList.get(i).getGarde());
        myViewHolder.tvSchoolName.setText(itemList.get(i).getSchoolName());



        if (!itemList.get(i).getStudentPhoto().equals("")) {
            byte[] decodedString = Base64.decode(itemList.get(i).getStudentPhoto(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            myViewHolder.imgProfile.setImageBitmap(decodedByte);
        }

        myViewHolder.setIsRecyclable(false);


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        CircleImageView imgProfile;
       TextView tvName,tvSchoolName,tvGrade;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName=(TextView)itemView.findViewById(R.id.tvName);
            tvSchoolName=(TextView)itemView.findViewById(R.id.tvSchoolName);
            tvGrade=(TextView)itemView.findViewById(R.id.tvGrade);
            imgProfile=(CircleImageView)itemView.findViewById(R.id.imgProfile);


        }
    }

    public SiblingInfoAdapter(ArrayList<SiblingModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;


    }


}
