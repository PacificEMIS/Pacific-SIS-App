package com.opensissasstudent.ui.student.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.opensissasstudent.R;
import com.opensissasstudent.databinding.FragmentMyInfoAddressBinding;
import com.opensissasstudent.databinding.FragmentMyInfoGeneralBinding;
import com.opensissasstudent.others.parser.PostJsonDataParser;
import com.opensissasstudent.others.utility.Pref;
import com.opensissasstudent.ui.common.activity.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MyInfoAddressFragment extends Fragment implements View.OnClickListener {
    FragmentMyInfoAddressBinding binding;
    private View view;
    String homePhone,mobilePhone,personalEmail,schoolEmail,twitter,facebook,instagram,youtube,linkedin,homeAddressLineOne,homeAddressLineTwo,homeAddressCity,homeAddressState,homeAddressCountry,homeAddressZip,busNo,mailingAddressLineOne,mailingAddressLineTwo,mailingAddressCity,mailingAddressState,mailingAddressCountry,mailingAddressZip,schoolBusPickUp,schoolBusDropOff;
    Pref pref;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_my_info_address,container,false);
        view=binding.getRoot();
        initView();
        return view;
    }

    private void initView(){
        pref=new Pref(getContext());
        homePhone=getArguments().getString("homePhone");
        binding.tvHomePhone.setText(homePhone);

        mobilePhone=getArguments().getString("mobilePhone");
        binding.tvMobilePhone.setText(mobilePhone);

        personalEmail=getArguments().getString("personalEmail");
        binding.tvPersonalEmail.setText(personalEmail);

        schoolEmail=getArguments().getString("schoolEmail");
        binding.tvSchoolEmail.setText(schoolEmail);

        twitter=getArguments().getString("twitter");
        if (twitter.equalsIgnoreCase("")){
            binding.imgTwitter.setVisibility(View.GONE);
        }else {
            binding.imgTwitter.setVisibility(View.VISIBLE);
        }
        facebook=getArguments().getString("facebook");
        if (facebook.equalsIgnoreCase("")){
            binding.imgFacebook.setVisibility(View.GONE);
        }else {
            binding.imgFacebook.setVisibility(View.VISIBLE);
        }

        instagram=getArguments().getString("instagram");
        if (instagram.equalsIgnoreCase("")){
            binding.imgInstagram.setVisibility(View.GONE);
        }else {
            binding.imgInstagram.setVisibility(View.VISIBLE);
        }


        youtube=getArguments().getString("youtube");
        if (youtube.equalsIgnoreCase("")){
            binding.imgYoutube.setVisibility(View.GONE);
        }else {
            binding.imgYoutube.setVisibility(View.VISIBLE);
        }


        linkedin=getArguments().getString("linkedin");
        if (linkedin.equalsIgnoreCase("")){
            binding.imgLinkdin.setVisibility(View.GONE);
        }else {
            binding.imgLinkdin.setVisibility(View.VISIBLE);
        }


        homeAddressLineOne=getArguments().getString("homeAddressLineOne");
        binding.tvStreetAddress1.setText(homeAddressLineOne);

        homeAddressLineTwo=getArguments().getString("homeAddressLineTwo");
        binding.tvStreetAddress2.setText(homeAddressLineTwo);


        homeAddressCity=getArguments().getString("homeAddressCity");
        binding.tvCity.setText(homeAddressCity);


        homeAddressState=getArguments().getString("homeAddressState");
        binding.tvState.setText(homeAddressState);


        homeAddressCountry=getArguments().getString("homeAddressCountry");
        binding.tvCountry.setText(homeAddressCountry);


        homeAddressZip=getArguments().getString("homeAddressZip");
        binding.tvZip.setText(homeAddressZip);


        busNo=getArguments().getString("busNo");
        binding.tvBus.setText(busNo);

        mailingAddressLineOne=getArguments().getString("mailingAddressLineOne");
        binding.tvMailingStreetAddress1.setText(mailingAddressLineOne);


        mailingAddressLineTwo=getArguments().getString("mailingAddressLineTwo");
        binding.tvMailingStreetAddress2.setText(mailingAddressLineTwo);


        mailingAddressCity=getArguments().getString("mailingAddressCity");
        binding.tvMailingCity.setText(mailingAddressCity);


        mailingAddressState=getArguments().getString("mailingAddressState");
        binding.tvMailingState.setText(mailingAddressState);


        mailingAddressCountry=getArguments().getString("mailingAddressCountry");
        binding.tvMailingCountry.setText(mailingAddressCountry);


        mailingAddressZip=getArguments().getString("mailingAddressZip");
        binding.tvMailingZip.setText(mailingAddressZip);


        schoolBusPickUp=getArguments().getString("schoolBusPickUp");
        binding.tvBusPickup.setText(schoolBusPickUp);


        schoolBusDropOff=getArguments().getString("schoolBusDropOff");
        binding.tvBusDropOff.setText(schoolBusDropOff);

        binding.imgYoutube.setOnClickListener(this);
        binding.imgTwitter.setOnClickListener(this);
        binding.imgFacebook.setOnClickListener(this);
        binding.imgInstagram.setOnClickListener(this);
        binding.imgLinkdin.setOnClickListener(this);

        getcountry();

    }

    @Override
    public void onClick(View v) {
        if (v==binding.imgFacebook){
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebook));
            startActivity(browserIntent);
        }else if (v==binding.imgTwitter){
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitter));
            startActivity(browserIntent);
        }else if (v==binding.imgInstagram){
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(instagram));
            startActivity(browserIntent);
        }else if (v==binding.imgYoutube){
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(youtube));
            startActivity(browserIntent);
        }else if (v==binding.imgLinkdin){
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkedin));
            startActivity(browserIntent);
        }

    }

    public void getcountry() {
        String api="https://"+pref.getTentantURLAPI()+"/assets/allCountries.json";


        new PostJsonDataParser(getContext(), Request.Method.GET, api, null, true, true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("dashboardresponse", response.toString());

                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message = response.optString("_message");
                        if (_failure) {

                            Intent intent = new Intent(getContext(), LoginActivity.class);
                            startActivity(intent);

                        } else {

                            JSONArray tableCountry=response.optJSONArray("tableCountry");
                            for (int i=0;i<tableCountry.length();i++){
                                JSONObject obj=tableCountry.optJSONObject(i);
                                int id=obj.optInt("id");
                                String name=obj.optString("name");
                                if (id==Integer.parseInt(homeAddressCountry)){
                                    binding.tvCountry.setText(name);
                                }

                                if (id==Integer.parseInt(mailingAddressCountry)){
                                    binding.tvMailingCountry.setText(name);
                                }
                            }

                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }
}