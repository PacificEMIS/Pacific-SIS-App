package com.pacific.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pacific.R;
import com.pacific.model.InputGradeStudentModel;
import com.pacific.model.SpinnerModelForInput;

import java.util.ArrayList;


public class InputGradeStudentAdapter extends RecyclerView.Adapter<InputGradeStudentAdapter.MyViewHolder> {
    ArrayList<InputGradeStudentModel> itemList = new ArrayList<>();
    Context context;
    ArrayList<String>gradeList=new ArrayList<>();
    ArrayList<SpinnerModelForInput>gradeModelList=new ArrayList<>();
    Fragment fContext;
    int letterFlag;


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.input_grade_student_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.tvStudentName.setText(itemList.get(i).getStudentName());
        myViewHolder.tvGrade.setText(itemList.get(i).getGradeLevel());


        //myViewHolder.etTeacherComment.setText(itemList.get(i).getTeacherComment());



        if (!itemList.get(i).getStudentPhoto().equals("")) {
            byte[] decodedString = Base64.decode(itemList.get(i).getStudentPhoto(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            myViewHolder.imgUser.setImageBitmap(decodedByte);
        }else {
            myViewHolder.imgUser.setImageDrawable(context.getResources().getDrawable(R.drawable.student_user));
        }



            myViewHolder.lnLetterGrade.setVisibility(View.VISIBLE);
            myViewHolder.etPercent.setText(itemList.get(i).getPercent()+"");
        myViewHolder.etCredit.setText(itemList.get(i).getCredit()+"");



        myViewHolder.tvLetterGrade.setText((itemList.get(i).getObtainedGrade().isEmpty())?"Select": itemList.get(i).getObtainedGrade());


        myViewHolder.textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()){

                    //int index = rangeString.indexOf(findRange(Integer.parseInt(s.toString())));
                    myViewHolder.tvLetterGrade.setText(findRange(Integer.parseInt(s.toString())));
                    itemList.get(i).setObtainedGrade(findRange(Integer.parseInt(s.toString())));
                    itemList.get(i).setPercent(Integer.parseInt(s.toString()));



                }
            }
        };





        myViewHolder.etPercent.addTextChangedListener(myViewHolder.textWatcher);

        myViewHolder.etCredit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()){
                    itemList.get(i).setCredit(Integer.parseInt(s.toString()));
                }

            }
        });

        myViewHolder.llLetterGrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGradPopup(context,myViewHolder,i);
            }
        });

       // myViewHolder.spLetter.setSelection(itemList.get(i).getPos1());















    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvStudentName,tvGrade;
        ImageView imgUser;
        EditText etPercent,etCredit;
        LinearLayout lnLetterGrade;
        ImageView imgComment;
        LinearLayout lnComment,lnPercent,llLetterGrade;
        Spinner spLetter;
        TextView tvLetterGrade;
        TextWatcher textWatcher;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGrade=(TextView)itemView.findViewById(R.id.tvGrade);
            tvStudentName=(TextView)itemView.findViewById(R.id.tvStudentName);
            spLetter=(Spinner)itemView.findViewById(R.id.spLetter);
            imgUser=(ImageView)itemView.findViewById(R.id.imgUser);

            etPercent=(EditText) itemView.findViewById(R.id.etPercent);
           // etTeacherComment=(EditText) itemView.findViewById(R.id.etTeacherComment);
            etCredit=(EditText)itemView.findViewById(R.id.etCredit);
            lnLetterGrade=(LinearLayout) itemView.findViewById(R.id.lnLetterGrade);
            imgComment=(ImageView)itemView.findViewById(R.id.imgComment);
            lnComment=(LinearLayout) itemView.findViewById(R.id.lnComment);
            lnPercent=(LinearLayout) itemView.findViewById(R.id.lnPercent);
            tvLetterGrade=(TextView) itemView.findViewById(R.id.tvLetterGrade);
            llLetterGrade=(LinearLayout) itemView.findViewById(R.id.llLetterGrade);
           /* etPercent.addTextChangedListener(myCustomEditTextListenerForNumber);
            etPercent.setOnFocusChangeListener(cutomfocusForNumber);*/
        }
    }

    public InputGradeStudentAdapter(ArrayList<InputGradeStudentModel> itemList, Context context, ArrayList<String>gradeList, ArrayList<SpinnerModelForInput>gradeModelList, Fragment fContext, int letterFlag) {
        this.itemList = itemList;
        this.context = context;
        this.gradeList=gradeList;
        this.gradeModelList=gradeModelList;
        this.fContext=fContext;
        this.letterFlag=letterFlag;

    }


    private void openGradPopup(Context context, MyViewHolder holder,int pos) {
        Dialog openGradPopup = new Dialog(context, R.style.CustomDialogNew);
        openGradPopup.setContentView(R.layout.grad_selection_popup);
        openGradPopup.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        openGradPopup.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        RecyclerView rvGrad = openGradPopup.findViewById(R.id.rvGrad);
        LinearLayout lnCancel = openGradPopup.findViewById(R.id.lnCancel);
        rvGrad.setLayoutManager(new LinearLayoutManager(context));

        GradAdapter gradAdapter = new GradAdapter(context,gradeModelList);
        rvGrad.setAdapter(gradAdapter);
        gradAdapter.setSelectedGard(new setOnGradSelect() {
            @Override
            public void onClick(int position, String grad, int value) {
                holder.tvLetterGrade.setText(grad);
                itemList.get(pos).setObtainedGrade(grad);
                holder.etPercent.removeTextChangedListener(holder.textWatcher);
                holder.etPercent.setText(String.valueOf(value));
                itemList.get(pos).setPercent(value);
                holder.etPercent.addTextChangedListener(holder.textWatcher);



                openGradPopup.cancel();
            }
        });

        lnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGradPopup.cancel();
            }
        });


        Window window = openGradPopup.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.NO_GRAVITY;
        openGradPopup.setCancelable(false);
        openGradPopup.show();

    }

    String findRange(int value){
        Integer nearestLower = null;
        String  nearestGrad = null;
        for (SpinnerModelForInput num : gradeModelList) {
            if (num.getBreakoff() == value){
                nearestLower = num.getBreakoff();
                nearestGrad = num.getItemName();
            } else if (num.getBreakoff() < value) {
                if (nearestLower == null || num.getBreakoff() > nearestLower) {
                    nearestLower = num.getBreakoff(); // Update nearest lower if found
                    nearestGrad = num.getItemName();
                }
            }
        }

        // Output result
        if (nearestLower != null) {

            return nearestGrad;
        } else {

            nearestGrad = gradeModelList.get(gradeModelList.size()-1).getItemName();
            return nearestGrad;
        }
    }

    public interface setOnGradSelect{
        void onClick(int position,String grad,int value);
    }
}
