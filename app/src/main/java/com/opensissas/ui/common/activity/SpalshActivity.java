package com.opensissas.ui.common.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.opensissas.R;
import com.opensissas.others.parser.PostJsonDataParser;
import com.opensissas.others.utility.Pref;
import com.opensissas.others.utility.Util;
import com.opensissas.ui.teacher.activity.TeacherDashboardActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class SpalshActivity extends AppCompatActivity {
    Pref pref;
    private AppUpdateManager appUpdateManager;
    private static final int IMMEDIATE_APP_UPDATE_REQ_CODE = 124;
    private AppUpdateManager mAppUpdateManager;
    private static final int RC_APP_UPDATE=100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh);
        pref=new Pref(SpalshActivity.this);
        mAppUpdateManager= AppUpdateManagerFactory.create(this);
        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)){
                    try {
                        mAppUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.IMMEDIATE,SpalshActivity.this,RC_APP_UPDATE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        appUpdateManager = AppUpdateManagerFactory.create(getApplicationContext());
        if (!pref.getName().equals("")){
            showSplash();
   /*         JSONObject jsonObject=new JSONObject();
            byte[] data = new byte[0];
            try {
                data = pref.getPassword().trim().getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String base64 = Base64.encodeToString(data, Base64.DEFAULT).trim();
            try {
                jsonObject.put("tenantId",pref.getTenatID());
                jsonObject.put("userId",0);
                jsonObject.put("_tenantName",pref.getTenatName());
                jsonObject.put("password",base64);
                jsonObject.put("email",pref.getEmail());
                jsonObject.put("schoolId",null);
                jsonObject.put("_userName","");
                jsonObject.put("_token",null);
                jsonObject.put("isMobileLogin",true);
                login(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
            }*/
        }else {
            showSplash();
        }

    }

    private void showSplash() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


                    startActivity(new Intent(SpalshActivity.this, LoginActivity.class));
                    finish();



            }
        }, 3500);
    }


    public void login(JSONObject jsonObject) {
        String url=pref.getAPI()+"User/ValidateLogin";

        new PostJsonDataParser(SpalshActivity.this, Request.Method.POST, url,jsonObject, true,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("loginresponse",response.toString());
                    try {
                        boolean _failure = response.optBoolean("_failure");
                        if (_failure){

                            Intent intent=new Intent(SpalshActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();

                        }else {
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
                            String membershipName=response.optString("membershipName");
                            pref.saveMemberShip(membershipName);
                            String _token=response.optString("_token");
                            pref.saveToken(_token);
                            String userPhoto= Util.getFreshValue(response.optString("userPhoto"),"");
                            pref.saveUserPhoto(userPhoto);

                            Intent intent=new Intent(SpalshActivity.this, TeacherDashboardActivity.class);
                            intent.putExtra("token",_token);
                            startActivity(intent);
                            finish();
                            pref.saveSplashFlag("1");
                        }






                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }

    @Override
    protected void onStop() {
        if (mAppUpdateManager!=null){
            // mAppUpdateManager.registerListener(installStateUpdatedListener);
        }
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==RC_APP_UPDATE && resultCode !=RESULT_OK){

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private InstallStateUpdatedListener installStateUpdatedListener=new InstallStateUpdatedListener() {
        @Override
        public void onStateUpdate(@NonNull InstallState installState) {
            if (installState.installStatus()== InstallStatus.DOWNLOADED){
                showCompleteUpdate();
            }

        }

        private void showCompleteUpdate() {
            Toast.makeText(SpalshActivity.this,"Update Complete",Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onResume() {
        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS){
                    try {
                        mAppUpdateManager.startUpdateFlowForResult(appUpdateInfo,AppUpdateType.IMMEDIATE,SpalshActivity.this,RC_APP_UPDATE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        super.onResume();
    }
}