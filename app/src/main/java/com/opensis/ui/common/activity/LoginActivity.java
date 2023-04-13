package com.opensis.ui.common.activity;

import static com.tozny.crypto.android.AesCbcWithIntegrity.encrypt;
import static com.tozny.crypto.android.AesCbcWithIntegrity.generateKey;
import static com.tozny.crypto.android.AesCbcWithIntegrity.generateKeyFromPassword;
import static com.tozny.crypto.android.AesCbcWithIntegrity.generateSalt;
import static com.tozny.crypto.android.AesCbcWithIntegrity.keyString;
import static com.tozny.crypto.android.AesCbcWithIntegrity.keys;
import static com.tozny.crypto.android.AesCbcWithIntegrity.saltString;

import androidx.appcompat.app.AppCompatActivity;

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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.opensis.R;
import com.opensis.others.parser.PostJsonDataParser;
import com.opensis.others.utility.AESHelper;
import com.opensis.others.utility.Api;
import com.opensis.others.utility.AppData;
import com.opensis.others.utility.CryptLib;
import com.opensis.others.utility.Pref;
import com.opensis.others.utility.Util;
import com.opensis.ui.teacher.activity.TeacherDashboardActivity;
import com.scottyab.aescrypt.AESCrypt;
import com.tozny.crypto.android.AesCbcWithIntegrity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etWebAddress,etEmail,etPassword;
    TextView tvSubmit,tvLogin,tvForgotUser,tvUrl;
    LinearLayout lnWebAddress,lnLogin;
    JSONObject jsonObject=new JSONObject();
    Pref pref;
    private static String EXAMPLE_PASSWORD = "oPen$!$.b14Ca5898a4e4133b!";
    AesCbcWithIntegrity.SecretKeys key ;
    String salt;
    String chipherText;
    String passwordEncrypted;
    ProgressDialog PD;
    LinearLayout lnEdit;
    String mainURL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        pref=new Pref(LoginActivity.this);
        etWebAddress=(EditText)findViewById(R.id.etWebAddress);
        etWebAddress.setText(pref.getEnteredURL());
        etEmail=(EditText)findViewById(R.id.etEmail);
        etEmail.setText(pref.getEmail());
        etPassword=(EditText)findViewById(R.id.etPassword);
        etPassword.setText(pref.getPasswordWithoutEncrypt());

        lnEdit=(LinearLayout)findViewById(R.id.lnEdit);
        lnEdit.setOnClickListener(this);

        tvSubmit=(TextView)findViewById(R.id.tvSubmit);
        tvLogin=(TextView)findViewById(R.id.tvLogin);
        tvForgotUser=(TextView)findViewById(R.id.tvForgotUser);
        tvUrl=(TextView)findViewById(R.id.tvUrl);

        lnWebAddress=(LinearLayout)findViewById(R.id.lnWebAddress);
        lnLogin=(LinearLayout)findViewById(R.id.lnLogin);

        tvSubmit.setOnClickListener(this);
        tvLogin.setOnClickListener(this);

        PD=new ProgressDialog(LoginActivity.this);
        PD.setMessage("Loading");
    }

    @Override
    public void onClick(View view) {
        if (view==tvSubmit){
            if (etWebAddress.getText().toString().length()>0) {
                lnWebAddress.setVisibility(View.GONE);
                lnLogin.setVisibility(View.VISIBLE);
                tvUrl.setText(etWebAddress.getText().toString());
                pref.saveEnteredURL(etWebAddress.getText().toString());

                String withoutHttp = etWebAddress.getText().toString().replace("https://", "").replace("/","");
                 mainURL = "https://"+withoutHttp + ":8088/";
                String[] spliturl = withoutHttp.split("\\.");
                String tenantName = spliturl[0];
                String fullURL = mainURL + tenantName + "/";
                pref.saveAPI(fullURL);
                pref.saveTenatName(tenantName);
            }else {
                Toast.makeText(LoginActivity.this,"Please Enter Web URL",Toast.LENGTH_SHORT).show();
            }


        }else if (view==tvLogin){
            if (etEmail.getText().toString().length()>0){
                if (etPassword.getText().toString().length()>0){

                    getPassword(etPassword.getText().toString());








                }else {
                    Toast.makeText(LoginActivity.this,"Please Enter Password",Toast.LENGTH_LONG).show();

                }

            }else {
                Toast.makeText(LoginActivity.this,"Please Enter Email Address",Toast.LENGTH_LONG).show();
            }

        }else if (view==lnEdit){
            lnWebAddress.setVisibility(View.VISIBLE);
            lnLogin.setVisibility(View.GONE);

        }
    }

    public void login(JSONObject jsonObject) {
        PD.show();
        String url=pref.getAPI()+"User/ValidateLogin";

        new PostJsonDataParser(LoginActivity.this, Request.Method.POST, url,jsonObject, false,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("loginresponse",response.toString());
                    try {
                        boolean _failure = response.optBoolean("_failure");
                        if (_failure){
                            PD.dismiss();

                            Toast.makeText(LoginActivity.this,"Invalid Login Credentials",Toast.LENGTH_LONG).show();

                        }else {
                            PD.show();
                            int schoolId=response.optInt("schoolId");
                            pref.saveSchoolID(schoolId);
                            int userId=response.optInt("userId");
                            pref.saveUserID(userId);
                            int membershipId=response.optInt("membershipId");
                            pref.saveMemberShipID(membershipId);
                            String name=response.optString("name");
                            pref.saveName(name);
                            String firstGivenName=response.optString("firstGivenName");
                            String lastFamilyName=response.optString("lastFamilyName");
                            String memberFullName=firstGivenName+" "+lastFamilyName;
                            pref.saveMemberName(memberFullName);
                            String _token=response.optString("_token");
                            pref.saveToken(_token);
                            pref.saveEmail(etEmail.getText().toString());
                            pref.savePasswordWithoutEncrypt(etPassword.getText().toString());
                            String membershipName=response.optString("membershipName");
                            pref.saveMemberShip(membershipName);
                            String userPhoto= Util.getFreshValue(response.optString("userPhoto"),"");
                            pref.saveUserPhoto(userPhoto);


                            JSONObject tenatOBJ=new JSONObject();
                            tenatOBJ.put("id",0);
                            tenatOBJ.put("tenantName",pref.getTenatName());
                            tenatOBJ.put("isActive",false);
                            tenatOBJ.put("tenantFooter","");
                            JSONObject jsonObject1=new JSONObject();
                            jsonObject1.put("tenant",tenatOBJ);
                            tenantLogo(jsonObject1,_token);


                        }






                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }


    public void tenantLogo(JSONObject jsonObject,String _token) {
        PD.show();
        String url=mainURL+"api/CatalogDB/CheckIfTenantIsAvailable";

        new PostJsonDataParser(LoginActivity.this, Request.Method.POST, url,jsonObject, false,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("loginresponse",response.toString());
                    try {
                        boolean _failure = response.optBoolean("_failure");
                        if (_failure){
                            PD.dismiss();


                        }else {
                            PD.dismiss();
                            JSONObject tenantobj=response.optJSONObject("tenant");
                            String tenantFavIcon=tenantobj.optString("tenantLogo");
                            pref.saveLogo(tenantFavIcon);

                            Intent intent=new Intent(LoginActivity.this, TeacherDashboardActivity.class);
                            intent.putExtra("token",_token);
                            startActivity(intent);
                            finish();
                        }






                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }


    public void getPassword(String password) {


        PD.show();

        String surl = pref.getAPI()+"User/getEncryptPassword?password="+password;
        Log.d("inputLogin", surl);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseLogin", response);
                        PD.show();
                        pref.savePassword(response);
                        try {
                            jsonObject.put("tenantId","1E93C7BF-0FAE-42BB-9E09-A1CEDC8C0355");
                            jsonObject.put("userId",0);
                            jsonObject.put("_tenantName",pref.getTenatName());
                            jsonObject.put("password",response);
                            jsonObject.put("email",etEmail.getText().toString());
                            jsonObject.put("isMobileLogin",true);
                            jsonObject.put("schoolId",null);
                            jsonObject.put("_userName","");
                            jsonObject.put("_token",null);
                            login(jsonObject);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }




                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                PD.dismiss();
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }












}