package com.opensissasstudent.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.text.Html;
import android.util.Base64;
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

import com.opensissasstudent.R;
import com.opensissasstudent.model.FamilyInfoModel;
import com.opensissasstudent.model.NoticeModel;
import com.opensissasstudent.ui.student.activity.MyGardeBookGradeDetailsActivity;
import com.opensissasstudent.ui.student.fragment.NoticesFragment;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.MyViewHolder> {
    ArrayList<NoticeModel> itemList = new ArrayList<>();
    Context context;
    Fragment fragment;



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notice_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            myViewHolder.tvNoticeTitle.setText(Html.fromHtml(itemList.get(i).getTitle(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            myViewHolder.tvNoticeTitle.setText(Html.fromHtml(itemList.get(i).getTitle()));
        }

      //  myViewHolder.tvNoticeDetails.setText(itemList.get(i).getBody());




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            myViewHolder.tvNoticeDetails.setText(Html.fromHtml(itemList.get(i).getBody(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            myViewHolder.tvNoticeDetails.setText(Html.fromHtml(itemList.get(i).getBody()));
        }


        if (!itemList.get(i).getImage().equals("")) {
            byte[] decodedString = Base64.decode(itemList.get(i).getImage(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            myViewHolder.imgNotice.setImageBitmap(decodedByte);
        }else {
        }

        myViewHolder.llNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!itemList.get(i).isExpanded()){
                    ((NoticesFragment)fragment).updateStatus(i,true);
                }else {
                    ((NoticesFragment)fragment).updateStatus(i,false);
                }
            }
        });

        if (itemList.get(i).isExpanded())
        {
            myViewHolder.imgDown.setVisibility(View.GONE);
            myViewHolder.imgUp.setVisibility(View.VISIBLE);
            myViewHolder.llNoticeDetails.setVisibility(View.VISIBLE);
            myViewHolder.llNotice.setVisibility(View.VISIBLE);

        }
        else
        {
            myViewHolder.imgDown.setVisibility(View.VISIBLE);
            myViewHolder.imgUp.setVisibility(View.GONE);
            myViewHolder.llNoticeDetails.setVisibility(View.GONE);
            myViewHolder.llNotice.setVisibility(View.VISIBLE);
        }




    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


       TextView tvNoticeTitle,tvNoticeDetails;
       LinearLayout llNoticeDetails,llNotice;
       ImageView imgDown,imgUp,imgNotice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNoticeTitle=(TextView)itemView.findViewById(R.id.tvNoticeTitle);
            tvNoticeDetails=(TextView)itemView.findViewById(R.id.tvNoticeDetails);

            llNotice=(LinearLayout) itemView.findViewById(R.id.llNotice);
            llNoticeDetails=(LinearLayout) itemView.findViewById(R.id.llNoticeDetails);

            imgDown=(ImageView) itemView.findViewById(R.id.imgDown);
            imgUp=(ImageView) itemView.findViewById(R.id.imgUp);
            imgNotice=(ImageView) itemView.findViewById(R.id.imgNotice);

        }
    }

    public NoticeAdapter(ArrayList<NoticeModel> itemList, Context context, Fragment fragment) {
        this.itemList = itemList;
        this.context = context;
        this.fragment = fragment;
    }
}
