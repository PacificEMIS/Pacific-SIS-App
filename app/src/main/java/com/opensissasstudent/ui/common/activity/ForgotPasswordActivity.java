package com.opensissasstudent.ui.common.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.opensissasstudent.R;
import com.opensissasstudent.others.parser.PostJsonDataParser;
import com.opensissasstudent.others.utility.Pref;


import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class ForgotPasswordActivity extends AppCompatActivity {
    EditText etEmail;
    TextView tvSubmit,tvSchool,tvResendMail,tvLogin;
    Pref pref;
    String schoolname,email;
    LinearLayout llForgotSubmit,llSucess;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        initView();
    }

    private void initView(){
        pref=new Pref(ForgotPasswordActivity.this);
        schoolname=getIntent().getStringExtra("schoolname");
        url=getIntent().getStringExtra("url");
        email=getIntent().getStringExtra("email");
        etEmail=(EditText) findViewById(R.id.etEmail);
        tvSubmit=(TextView)findViewById(R.id.tvSubmit);
        tvSchool=(TextView)findViewById(R.id.tvSchool);
        tvResendMail=(TextView)findViewById(R.id.tvResendMail);
        tvLogin=(TextView)findViewById(R.id.tvLogin);
        tvSchool.setText(schoolname);
        etEmail.setText(email);
        llSucess=(LinearLayout)findViewById(R.id.llSucess);
        llForgotSubmit=(LinearLayout)findViewById(R.id.llForgotSubmit);

      tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etEmail.getText().toString().length()>0){
                    String urlkey= url+"|"+pref.getTenatID()+"|"+etEmail.getText().toString().trim();

                    byte[] data = new byte[0];
                    try {
                        data =urlkey.getBytes("UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    String base64 = Base64.encodeToString(data, Base64.DEFAULT).trim();
                    JSONObject jsonObject=new JSONObject();
                    try {
                        jsonObject.put("urlKey",base64);
                        jsonObject.put("_tenantName",pref.getTenatName());
                        jsonObject.put("isMobileUser",true);
                        forgotpassword(jsonObject);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        tvResendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlkey= url+"|"+pref.getTenatID()+"|"+etEmail.getText().toString().trim();

                byte[] data = new byte[0];
                try {
                    data =urlkey.getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                String base64 = Base64.encodeToString(data, Base64.DEFAULT).trim();
                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("urlKey",base64);
                    jsonObject.put("_tenantName",pref.getTenatName());
                    jsonObject.put("isMobileUser",true);
                    forgotpassword(jsonObject);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ForgotPasswordActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    public void forgotpassword(JSONObject jsonObject) {
        ProgressDialog pd=new ProgressDialog(ForgotPasswordActivity.this);
        pd.setMessage("Loading");
        pd.setCancelable(false);
        pd.show();
        String url=pref.getAPI()+"Password/sendMailForResetPassword";

        new PostJsonDataParser(ForgotPasswordActivity.this, Request.Method.POST, url,jsonObject, false,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("loginresponse",response.toString());
                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");
                        if (_failure){
                            pd.dismiss();

                            Toast.makeText(ForgotPasswordActivity.this,_message,Toast.LENGTH_LONG).show();

                        }else {
                            llForgotSubmit.setVisibility(View.GONE);
                            llSucess.setVisibility(View.VISIBLE);
                            pd.dismiss();

                            Toast.makeText(ForgotPasswordActivity.this,_message,Toast.LENGTH_LONG).show();



                        }






                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }
}