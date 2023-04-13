package com.opensis.others.parser;

import android.content.Context;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;
import com.opensis.R;
import com.opensis.others.utility.AppData;
import com.opensis.others.utility.MyCustomProgressDialog;
import com.opensis.others.utility.Util;


import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;


/**
 * Created on 16/9/16.
 *
 * @author Manas
 */
public class PostJsonArrayDataParser {
    MyCustomProgressDialog dialog;

    private void showpDialog() {
        if (!dialog.isShowing())
            dialog.show();
    }

    private void hidepDialog() {
        if (dialog.isShowing())
            dialog.dismiss();
    }

   /*
   pass jsonRequest null if there is no params
   this method is invoked with header
   */

    public PostJsonArrayDataParser(final Context context, int method, String url, JSONArray jsonRequest, final boolean flag, final OnGetResponseListner listner) {
        // final SharedPreferenceHelper helper = SharedPreferenceHelper.getInstance(context);
        // AppData.sToken = helper.getUserToken();
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
        final JsonArrayRequestCustom jsonObjReq = new JsonArrayRequestCustom(method,url, jsonRequest, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
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
                if (statusCode == 500) {
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
                return headers;
            }
        };
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonObjReq);
    }
    public PostJsonArrayDataParser(final Context context, int method, String url, JSONArray jsonRequest, final boolean flag, View view, final OnGetResponseListner listner) {
        // final SharedPreferenceHelper helper = SharedPreferenceHelper.getInstance(context);
        // AppData.sToken = helper.getUserToken();
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
        final JsonArrayRequestCustom jsonObjReq = new JsonArrayRequestCustom(method,url, jsonRequest, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
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
                if (statusCode == 500) {
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
                return headers;
            }
        };
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonObjReq);
    }
    /*
  pass jsonRequest null if there is no params
  this method is invoked without header
  */
    public PostJsonArrayDataParser(final Context context, int method, String url, JSONArray jsonRequest, final boolean flag, boolean noHeader, final OnGetResponseListner listner) {
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


        final JsonArrayRequestCustom jsonObjReq = new JsonArrayRequestCustom(method,url, jsonRequest, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
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
                Util.showSnakBar(context, context.getResources().getString(R.string.networkerror));
                listner.onGetResponse(null);
                VolleyLog.d("Error: " + error.getMessage());
                //TastyToast.makeText(context, "Network error.", TastyToast.LENGTH_SHORT, TastyToast.ERROR);


            }
        });
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonObjReq);
    }
    /*
      pass jsonRequest null if there is no params
      this method is invoked with header and try to refresh api
      */
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
                            if (view ==null) {
                                Activity a = (Activity) context;
                                Intent i = a.getIntent();
                                a.overridePendingTransition(0, 0);
                                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                a.finish();
                                a.overridePendingTransition(0, 0);
                                a.startActivity(i);
                            }else {
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
        void onGetResponse(JSONArray response);
    }
}
