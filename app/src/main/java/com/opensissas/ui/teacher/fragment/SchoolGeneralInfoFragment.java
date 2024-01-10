package com.opensissas.ui.teacher.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.opensissas.R;
import com.opensissas.others.utility.Util;


public class SchoolGeneralInfoFragment extends Fragment implements View.OnClickListener {
     View view;
     String schoolName,alternateName,schoolID,schoolAltID,stateID,schoolDistrictId,schoolLevel,schoolClassification,affiliation,associations,lowestGradeLevel,highestGradeLevel;
     String dateSchoolOpened,locale,gender,internet,electricity,status,streetAddress1,streetAddress2,country,state,city,county,division,district,zip,longitude,latitude;
    String nameOfPrincipal,nameOfAssistantPrincipal,telephone,fax,website,email,twitter,facebook,instagram,youtube,linkedIn;
    LinearLayout lnGooglMap;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_school_general_info, container, false);
        initView();
        return view;
    }

    private void initView(){
        lnGooglMap=(LinearLayout)view.findViewById(R.id.lnGooglMap);
        schoolName=getArguments().getString("schoolName");
        alternateName=getArguments().getString("alternateName");
        schoolID=getArguments().getString("schoolID");
        schoolAltID=getArguments().getString("schoolAltID");
        stateID=getArguments().getString("stateID");
        schoolDistrictId=getArguments().getString("schoolDistrictId");
        schoolLevel=getArguments().getString("schoolLevel");
        schoolClassification=getArguments().getString("schoolClassification");
        affiliation=getArguments().getString("affiliation");
        associations=getArguments().getString("associations");
        lowestGradeLevel=getArguments().getString("lowestGradeLevel");
        highestGradeLevel=getArguments().getString("highestGradeLevel");
        dateSchoolOpened=getArguments().getString("dateSchoolOpened");
        locale=getArguments().getString("locale");
        gender=getArguments().getString("gender");
        internet=getArguments().getString("internet");
        electricity=getArguments().getString("electricity");
        status=getArguments().getString("status");
        streetAddress1=getArguments().getString("streetAddress1");
        streetAddress2=getArguments().getString("streetAddress2");
        country=getArguments().getString("country");
        state=getArguments().getString("state");
        city=getArguments().getString("city");
        county=getArguments().getString("county");
        division=getArguments().getString("division");
        district=getArguments().getString("district");
        zip=getArguments().getString("zip");
        longitude=Util.getFreshValue(getArguments().getString("longitude"),"-");
        latitude=Util.getFreshValue(getArguments().getString("latitude"),"-");
        nameOfPrincipal=getArguments().getString("nameOfPrincipal");
        nameOfAssistantPrincipal=getArguments().getString("nameOfAssistantPrincipal");
        telephone=getArguments().getString("telephone");
        fax=getArguments().getString("fax");
        website=getArguments().getString("website");
        email=getArguments().getString("email");
        twitter=getArguments().getString("twitter");
        facebook=getArguments().getString("facebook");
        instagram=getArguments().getString("instagram");
        youtube=getArguments().getString("youtube");
        linkedIn=getArguments().getString("linkedIn");
        lnGooglMap.setOnClickListener(this);






        TextView tvSchoolName=(TextView)view.findViewById(R.id.tvSchoolName);
        tvSchoolName.setText(schoolName);

        TextView tvSchoolAltName=(TextView)view.findViewById(R.id.tvSchoolAltName);
        tvSchoolAltName.setText(alternateName);

        TextView tvSchoolID=(TextView)view.findViewById(R.id.tvSchoolID);
        tvSchoolID.setText(schoolID);

        TextView tvSchoolAltID=(TextView)view.findViewById(R.id.tvSchoolAltID);
        tvSchoolAltID.setText(schoolAltID);

        TextView tvStateID=(TextView)view.findViewById(R.id.tvStateID);
        tvStateID.setText(stateID);

        TextView tvDisID=(TextView)view.findViewById(R.id.tvDisID);
        tvDisID.setText(schoolDistrictId);

        TextView tvSchoolLevel=(TextView)view.findViewById(R.id.tvSchoolLevel);
        tvSchoolLevel.setText(schoolLevel);

        TextView tvSchoolClassification=(TextView)view.findViewById(R.id.tvSchoolClassification);
        tvSchoolClassification.setText(schoolClassification);

        TextView tvAffiliation=(TextView)view.findViewById(R.id.tvAffiliation);
        tvAffiliation.setText(affiliation);

        TextView tvAssociation=(TextView)view.findViewById(R.id.tvAssociation);
        tvAssociation.setText(associations);

        TextView tvLowGradeLvl=(TextView)view.findViewById(R.id.tvLowGradeLvl);
        tvLowGradeLvl.setText(lowestGradeLevel);

        TextView tvHighGradeLvl=(TextView)view.findViewById(R.id.tvHighGradeLvl);
        tvHighGradeLvl.setText(highestGradeLevel);

        TextView tvSchoolFrstDate=(TextView)view.findViewById(R.id.tvSchoolFrstDate);
        tvSchoolFrstDate.setText(Util.changeAnyDateFormat(dateSchoolOpened,"yyyy-MM-dd","dd MMM,yyyy"));

        TextView tvLocalCode=(TextView)view.findViewById(R.id.tvLocalCode);
        tvLocalCode.setText(locale);

        TextView tvGender=(TextView)view.findViewById(R.id.tvGender);
        tvGender.setText(gender);

        TextView tvInternet=(TextView)view.findViewById(R.id.tvInternet);
        tvInternet.setText(internet);

        TextView tvElectricity=(TextView)view.findViewById(R.id.tvElectricity);
        tvElectricity.setText(electricity);

        TextView tvStatus=(TextView)view.findViewById(R.id.tvStatus);
        tvStatus.setText(status);

        TextView tvStreetAdr1=(TextView)view.findViewById(R.id.tvStreetAdr1);
        tvStreetAdr1.setText(streetAddress1);

        TextView tvState=(TextView)view.findViewById(R.id.tvState);
        tvState.setText(state);

        TextView tvCity=(TextView)view.findViewById(R.id.tvCity);
        tvCity.setText(city);

        TextView tvCounty=(TextView)view.findViewById(R.id.tvCounty);
        tvCounty.setText(county);

        TextView tvCountry=(TextView)view.findViewById(R.id.tvCountry);
        tvCountry.setText(country);

        TextView tvDivision=(TextView)view.findViewById(R.id.tvDivision);
        tvDivision.setText(division);

        TextView tvDistrict=(TextView)view.findViewById(R.id.tvDistrict);
        tvDistrict.setText(district);

        TextView tvPostalCode=(TextView)view.findViewById(R.id.tvPostalCode);
        tvPostalCode.setText(zip);

        TextView tvLatLng=(TextView)view.findViewById(R.id.tvLatLng);
        tvLatLng.setText(latitude+"/"+longitude);


        TextView tvPrincipal=(TextView)view.findViewById(R.id.tvPrincipal);
        tvPrincipal.setText(nameOfPrincipal);

        TextView tvTelephone=(TextView)view.findViewById(R.id.tvTelephone);
        tvTelephone.setText(telephone);

        TextView tvAsstPrincipal=(TextView)view.findViewById(R.id.tvAsstPrincipal);
        tvAsstPrincipal.setText(nameOfAssistantPrincipal);

        TextView tvFax=(TextView)view.findViewById(R.id.tvFax);
        tvFax.setText(fax);

        TextView tvWebsite=(TextView)view.findViewById(R.id.tvWebsite);
        tvWebsite.setText(website);

        TextView tvEmail=(TextView)view.findViewById(R.id.tvEmail);
        tvEmail.setText(email);

        TextView tvTwitter=(TextView)view.findViewById(R.id.tvTwitter);
        tvTwitter.setText(twitter);

        TextView tvFacebook=(TextView)view.findViewById(R.id.tvFacebook);
        tvFacebook.setText(facebook);

        TextView tvInstagram=(TextView)view.findViewById(R.id.tvInstagram);
        tvInstagram.setText(instagram);

       TextView  tvYoutube=(TextView)view.findViewById(R.id.tvYoutube);
        tvYoutube.setText(youtube);

        TextView  tvLinkedin=(TextView)view.findViewById(R.id.tvLinkedin);
        tvLinkedin.setText(linkedIn);



    }

    @Override
    public void onClick(View v) {
        if (v==lnGooglMap){
            String uri = "http://maps.google.com/maps?q=loc:" + latitude + "," + longitude + " (" + streetAddress1 + ")";;
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            startActivity(intent);
        }

    }
}