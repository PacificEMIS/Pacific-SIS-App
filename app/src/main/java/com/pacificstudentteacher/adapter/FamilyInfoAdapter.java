package com.pacificstudentteacher.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.pacificstudentteacher.R;
import com.pacificstudentteacher.model.FamilyInfoModel;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class FamilyInfoAdapter extends RecyclerView.Adapter<FamilyInfoAdapter.MyViewHolder> {
    ArrayList<FamilyInfoModel> itemList = new ArrayList<>();
    Context context;



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.familyinfo_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

        myViewHolder.tvRealation.setText(itemList.get(i).getRelationship());

        myViewHolder.tvName.setText( itemList.get(i).getName());

        myViewHolder.tvContactType.setText(itemList.get(i).getContactType());
        if (itemList.get(i).getContactType().equalsIgnoreCase("Primary")){
            myViewHolder.tvContactType.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.missing_attendance));

        }else {
            myViewHolder.tvContactType.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.text_color));

        }

        if (!itemList.get(i).getParentPhoto().equals("")) {
            byte[] decodedString = Base64.decode(itemList.get(i).getParentPhoto(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            myViewHolder.imgProfile.setImageBitmap(decodedByte);
        }

        myViewHolder.setIsRecyclable(false);
        if (itemList.get(i).isCustodian()){
            myViewHolder.tvCustody.setVisibility(View.VISIBLE);
        }else {
            myViewHolder.tvCustody.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        CircleImageView imgProfile;
       TextView tvName,tvRealation,tvCustody,tvContactType;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName=(TextView)itemView.findViewById(R.id.tvName);
            tvRealation=(TextView)itemView.findViewById(R.id.tvRealation);
            tvCustody=(TextView)itemView.findViewById(R.id.tvCustody);
            tvContactType=(TextView)itemView.findViewById(R.id.tvContactType);
            imgProfile=(CircleImageView)itemView.findViewById(R.id.imgProfile);


        }
    }

    public FamilyInfoAdapter(ArrayList<FamilyInfoModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;


    }


}
