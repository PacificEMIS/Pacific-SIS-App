package com.opensis.others.parser;

import android.content.Context;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.opensis.R;
import com.opensis.others.utility.AppData;
import com.opensis.others.utility.MyCustomProgressDialog;
import com.opensis.others.utility.Util;


import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created on 16/9/16.
 *
 * @author Manas
 */
public class GetDataParser {
    MyCustomProgressDialog dialog;

    private void showpDialog() {
        if (!dialog.isShowing())
            dialog.show();
    }

    private void hidepDialog() {
        if (dialog.isShowing())
            dialog.dismiss();
    }

    /**
     * constructor
     * @param context
     * @param url   request destination
     * @param flag  boolean progress flag
     * @param listner  on success achieved 200 code from request
     */
    public GetDataParser(final Context context, String url, final boolean flag, final OnGetResponseListner listner) {

        if (!Util.isConnected(context)) {
            Util.showSnakBar(context, context.getResources().getString(R.string.internectconnectionerror));
            //TastyToast.makeText(context, "No internet connections.", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
            listner.onGetResponse(null);
            return;
        }
        if (flag) {
            dialog = MyCustomProgressDialog.ctor(context);
            dialog.setCancelable(false);
            dialog.setMessage("Please wait...");
            showpDialog();
        }
        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    listner.onGetResponse(response);
                } catch (Exception e) {
                    listner.onGetResponse(null);
                    e.printStackTrace();
                }
                if (flag)
                    hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (flag)
                    hidepDialog();
                int statusCode = error.networkResponse.statusCode;
                if (statusCode == 401) {
                    getAccessToken(context,null);
                }else {
                    Util.showSnakBar(context, context.getResources().getString(R.string.networkerror));
                    listner.onGetResponse(null);
                }
                VolleyLog.d("Error: " + error.getMessage());
                //TastyToast.makeText(context, "Network error.", TastyToast.LENGTH_SHORT, TastyToast.ERROR);


            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer "+ AppData.accessToken);
                /*if (AppData.sToken != null) {
                    headers.put("token", helper.getUserToken());
                }*/
                return headers;
            }
        };
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonObjReq);
    }
    //with view click
    public GetDataParser(final Context context, String url, final boolean flag, View view, final OnGetResponseListner listner) {

        if (!Util.isConnected(context)) {
            Util.showSnakBar(context, context.getResources().getString(R.string.internectconnectionerror),view);
            //TastyToast.makeText(context, "No internet connections.", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
            listner.onGetResponse(null);
            return;
        }
        if (flag) {
            dialog = MyCustomProgressDialog.ctor(context);
            dialog.setCancelable(false);
            dialog.setMessage("Please wait...");
            showpDialog();
        }
        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    listner.onGetResponse(response);
                } catch (Exception e) {
                    listner.onGetResponse(null);
                    e.printStackTrace();
                }
                if (flag)
                    hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (flag)
                    hidepDialog();
                int statusCode = error.networkResponse.statusCode;
                if (statusCode == 401) {
                    getAccessToken(context,view);
                }else {
                    Util.showSnakBar(context, context.getResources().getString(R.string.networkerror),view);
                    listner.onGetResponse(null);
                }
                VolleyLog.d("Error: " + error.getMessage());
                //TastyToast.makeText(context, "Network error.", TastyToast.LENGTH_SHORT, TastyToast.ERROR);


            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer "+ AppData.accessToken);
                /*if (AppData.sToken != null) {
                    headers.put("token", helper.getUserToken());
                }*/
                return headers;
            }
        };
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonObjReq);
    }

    private void getAccessToken(Context context, View view) {
       /* SharePreference preference = new SharePreference(context);
        JSONObject params = new JSONObject();
        try {
            params.put("clientAPIKey","B67DCE5559FFEBC4B8CD4FFFBBAAE");
            params.put("clientSecret","A1;]12tQHuVfM}@");
            params.put("authenticatedUserEmailId",preference.getUserEmail());
            new PostJsonDataParser(context, Request.Method.POST, Api.getAccessToken, params, false, true, new PostJsonDataParser.OnGetResponseListner() {
                @Override
                public void onGetResponse(JSONObject response) {
                    if (response != null){
                        String accessToken = response.optString("accessToken");
                        AppData.accessToken = accessToken;
                        if (!accessToken.isEmpty()){
                            if (view == null) {
                                Activity a = (Activity) context;
                                Intent i = a.getIntent();
                                a.overridePendingTransition(0, 0);
                                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                a.finish();
                                a.overridePendingTransition(0, 0);
                                a.startActivity(i);
                            }
                            else {
                                view.performClick();
                            }
                        }
                    }
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }*/
    }

    public interface OnGetResponseListner {
        void onGetResponse(JSONObject response);
    }
}
