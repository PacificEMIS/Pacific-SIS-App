package com.pacific.ui.common.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.Html;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.pacific.R;
import com.pacific.databinding.ActivityChangePasswordBinding;
import com.pacific.others.parser.PostJsonDataParser;
import com.pacific.others.utility.Pref;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Random;

public class ChangePasswordActivity extends AppCompatActivity {
    ActivityChangePasswordBinding binding;
    String color;
    Pref pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_change_password);
        initView();
    }

    private void initView(){
        pref=new Pref(ChangePasswordActivity.this);
        color = "<font color='#EE0000'>*</font>";
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.tvCurrentPassword.setText(Html.fromHtml("Current Password" + color));
        binding.tvNewPassword.setText(Html.fromHtml("New Password" + color));
        binding.tvConNewPassword.setText(Html.fromHtml("Confirm New Password" + color));
        binding.tvAutoPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = generateRandomPassword(8, true, true, true,true);
                binding.etNewPassword.setText(pass);
                binding.etConNewPassword.setText(pass);
            }
        });
        binding.imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.imgOpen.setVisibility(View.VISIBLE);
                binding.imgClose.setVisibility(View.GONE);
                binding.etNewPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

            }
        });


        binding.imgOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.imgOpen.setVisibility(View.GONE);
                binding.imgClose.setVisibility(View.VISIBLE);
                binding.etNewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        });

        binding.tvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.etCurrentPassword.getText().toString().length()>0){
                    if (binding.etNewPassword.getText().toString().length()>0){
                        if (binding.etConNewPassword.getText().toString().length()>0){
                            if (binding.etNewPassword.getText().toString().equals(binding.etConNewPassword.getText().toString())){
                                byte[] currentpassworddata = new byte[0];
                                try {
                                    currentpassworddata = binding.etCurrentPassword.getText().toString().trim().getBytes("UTF-8");
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                                String etCurrentPasswordbase64 = Base64.encodeToString(currentpassworddata, Base64.DEFAULT).trim();

                                byte[] newpassworddata = new byte[0];
                                try {
                                    newpassworddata = binding.etConNewPassword.getText().toString().trim().getBytes("UTF-8");
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                                String etNewPasswordbase64 = Base64.encodeToString(newpassworddata, Base64.DEFAULT).trim();
                                JSONObject jsonObject=new JSONObject();
                                try {
                                    jsonObject.put("currentPasswordHash",etCurrentPasswordbase64);
                                    jsonObject.put("newPasswordHash",etNewPasswordbase64);
                                    jsonObject.put("_tenantName",pref.getTenatName());
                                    jsonObject.put("_userName",pref.getName());
                                    jsonObject.put("_token",pref.getToken());
                                    jsonObject.put("_academicYear",pref.getAcademicYear());
                                    jsonObject.put("tenantId",pref.getTenatID());
                                    jsonObject.put("schoolId",pref.getSchoolID());
                                    jsonObject.put("userId",pref.getUserID());
                                    jsonObject.put("emailAddress",pref.getEmail());
                                    jsonObject.put("isMobileUser",true);
                                    changePassword(jsonObject);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }else {
                                Toast.makeText(ChangePasswordActivity.this,"New password and confirm password should be same",Toast.LENGTH_LONG).show();
                            }

                        }else {
                            Toast.makeText(ChangePasswordActivity.this,"Please enter confirm new password",Toast.LENGTH_LONG).show();
                        }

                    }else {
                        Toast.makeText(ChangePasswordActivity.this,"Please enter new password",Toast.LENGTH_LONG).show();
                    }

                }else {
                    Toast.makeText(ChangePasswordActivity.this,"Please enter current password",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private static String generateRandomPassword(int max_length, boolean upperCase, boolean lowerCase, boolean numbers, boolean specialCharacters)
    {
        String upperCaseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseChars = "abcdefghijklmnopqrstuvwxyz";
        String numberChars = "0123456789";
        String specialChars = "!@#$%^&*()_-+=<>?/{}~|";
        String allowedChars = "";

        Random rn = new Random();
        StringBuilder sb = new StringBuilder(max_length);

        //this will fulfill the requirements of atleast one character of a type.
        if(upperCase) {
            allowedChars += upperCaseChars;
            sb.append(upperCaseChars.charAt(rn.nextInt(upperCaseChars.length()-1)));
        }

        if(lowerCase) {
            allowedChars += lowerCaseChars;
            sb.append(lowerCaseChars.charAt(rn.nextInt(lowerCaseChars.length()-1)));
        }

        if(numbers) {
            allowedChars += numberChars;
            sb.append(numberChars.charAt(rn.nextInt(numberChars.length()-1)));
        }

        if(specialCharacters) {
            allowedChars += specialChars;
            sb.append(specialChars.charAt(rn.nextInt(specialChars.length()-1)));
        }


        //fill the allowed length from different chars now.
        for(int i=sb.length();i < max_length;++i){
            sb.append(allowedChars.charAt(rn.nextInt(allowedChars.length())));
        }

        return  sb.toString();
    }


    public void changePassword(JSONObject jsonObject) {
        String url = pref.getAPI() + "Common/changePassword";

        new PostJsonDataParser(ChangePasswordActivity.this, Request.Method.POST, url, jsonObject, true, true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("schoolresponse", response.toString());
                    try {

                        boolean _failure = response.optBoolean("_failure");
                        String _message = response.optString("_message");
                        if (_failure) {
                            Toast.makeText(ChangePasswordActivity.this,_message,Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(ChangePasswordActivity.this,_message,Toast.LENGTH_LONG).show();
                             onBackPressed();
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }
}