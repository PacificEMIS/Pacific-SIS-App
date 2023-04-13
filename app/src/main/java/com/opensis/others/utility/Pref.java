package com.opensis.others.utility;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by kiit on 05-01-2018.
 */

public class Pref {
    private SharedPreferences _pref;
    private static final String PREF_FILE = "com.deus";
    private SharedPreferences.Editor _editorPref;
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";


    @SuppressLint("CommitPrefEdits")
    public Pref(Context context) {
        _pref = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        _editorPref = _pref.edit();
    }




    public void saveName(String Name){
        _editorPref.putString("Name", Name);
        _editorPref.commit();
    }

    public String getName(){
        return _pref.getString("Name","");
    }


    public void saveMemberName(String MemberName){
        _editorPref.putString("MemberName", MemberName);
        _editorPref.commit();
    }

    public String getMemberName(){
        return _pref.getString("MemberName","");
    }

    public void saveMemberShip(String MemberShip){
        _editorPref.putString("MemberShip", MemberShip);
        _editorPref.commit();
    }

    public String getMemberShip(){
        return _pref.getString("MemberShip","");
    }

    public void saveToken(String Token){
        _editorPref.putString("Token", Token);
        _editorPref.commit();
    }

    public String getToken(){
        return _pref.getString("Token","");
    }

    public void saveSchoolID(int SchoolID){
        _editorPref.putInt("SchoolID", SchoolID);
        _editorPref.commit();
    }

    public int getSchoolID(){
        return _pref.getInt("SchoolID",0);
    }

    public void saveUserID(int UserID){
        _editorPref.putInt("UserID", UserID);
        _editorPref.commit();
    }

    public int getUserID(){
        return _pref.getInt("UserID",0);
    }

    public void saveMemberShipID(int MemberShipID){
        _editorPref.putInt("MemberShipID", MemberShipID);
        _editorPref.commit();
    }

    public int getMemberShipID(){
        return _pref.getInt("MemberShipID",0);
    }

    public void saveEmail(String Email){
        _editorPref.putString("Email", Email);
        _editorPref.commit();
    }

    public String getEmail(){
        return _pref.getString("Email","");
    }


    public void savePasswordWithoutEncrypt(String PasswordWithoutEncrypt){
        _editorPref.putString("PasswordWithoutEncrypt", PasswordWithoutEncrypt);
        _editorPref.commit();
    }

    public String getPasswordWithoutEncrypt(){
        return _pref.getString("PasswordWithoutEncrypt","");
    }


    public void savePassword(String Password){
        _editorPref.putString("Password", Password);
        _editorPref.commit();
    }

    public String getPassword(){
        return _pref.getString("Password","");
    }


    public void saveAcademicYear(Integer AcademicYear){
        _editorPref.putInt("AcademicYear", AcademicYear);
        _editorPref.commit();
    }

    public Integer getAcademicYear(){
        return _pref.getInt("AcademicYear",0);
    }

    public void savePeriodStartYear(String PeriodStartYear){
        _editorPref.putString("PeriodStartYear", PeriodStartYear);
        _editorPref.commit();
    }

    public String getPeriodStartYear(){
        return _pref.getString("PeriodStartYear","");
    }

    public void savePeriodEndYear(String EndYear){
        _editorPref.putString("EndYear", EndYear);
        _editorPref.commit();
    }

    public String getPeriodEndYear(){
        return _pref.getString("EndYear","");
    }

    public void savePeriodID(int PeriodID){
        _editorPref.putInt("PeriodID", PeriodID);
        _editorPref.commit();
    }

    public int getPeriodID(){
        return _pref.getInt("PeriodID",0);
    }

    public void saveAcademyYear(String AcademyYear){
        _editorPref.putString("AcademyYear", AcademyYear);
        _editorPref.commit();
    }

    public String getAcademyYear(){
        return _pref.getString("AcademyYear","");
    }


    public void saveAPI(String API){
        _editorPref.putString("API", API);
        _editorPref.commit();
    }

    public String getAPI(){
        return _pref.getString("API","");
    }


    public void saveTenatName(String TenatName){
        _editorPref.putString("TenatName", TenatName);
        _editorPref.commit();
    }

    public String getTenatName(){
        return _pref.getString("TenatName","");
    }


    public void saveUserPhoto(String UserPhoto){
        _editorPref.putString("UserPhoto", UserPhoto);
        _editorPref.commit();
    }

    public String getUserPhoto(){
        return _pref.getString("UserPhoto","");
    }


    public void saveSplashFlag(String SplashFlag){
        _editorPref.putString("SplashFlag", SplashFlag);
        _editorPref.commit();
    }

    public String getSplashFlag(){
        return _pref.getString("SplashFlag","");
    }


    public void saveLogo(String Logo){
        _editorPref.putString("Logo", Logo);
        _editorPref.commit();
    }

    public String getLogo(){
        return _pref.getString("Logo","");
    }


    public void saveEnteredURL(String EnteredURL){
        _editorPref.putString("EnteredURL", EnteredURL);
        _editorPref.commit();
    }

    public String getEnteredURL(){
        return _pref.getString("EnteredURL","");
    }

}

