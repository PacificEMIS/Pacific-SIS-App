package com.opensissas.others.parser;

import android.content.Context;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.opensissas.R;
import com.opensissas.others.utility.AppData;
import com.opensissas.others.utility.MyCustomProgressDialog;
import com.opensissas.others.utility.Pref;
import com.opensissas.others.utility.Util;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created on 16/9/16.
 *
 * @author Manas
 */
public class PostJsonDataParser {
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

    public PostJsonDataParser(final Context context, int method, String url, JSONObject jsonRequest, final boolean flag, final OnGetResponseListner listner) {
        // final SharedPreferenceHelper helper = SharedPreferenceHelper.getInstance(context);
        // AppData.sToken = helper.getUserToken();
        Pref pref=new Pref(context);
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
        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(method,url, jsonRequest, new Response.Listener<JSONObject>() {

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


                    Util.showSnakBar(context, context.getResources().getString(R.string.networkerror));
                    listner.onGetResponse(null);

                VolleyLog.d("Error: " + error.getMessage());
                //TastyToast.makeText(context, "Network error.", TastyToast.LENGTH_SHORT, TastyToast.ERROR);


            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer ");
                return headers;
            }
        };
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonObjReq);
    }
    public PostJsonDataParser(final Context context, int method, String url, JSONObject jsonRequest, final boolean flag, final boolean flag1, final OnGetResponseListner listner) {
        // final SharedPreferenceHelper helper = SharedPreferenceHelper.getInstance(context);
        // AppData.sToken = helper.getUserToken();
        Pref pref=new Pref(context);
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
        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(method,url, jsonRequest, new Response.Listener<JSONObject>() {

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

                    Util.showSnakBar(context, context.getResources().getString(R.string.networkerror));
                    listner.onGetResponse(null);

                VolleyLog.d("Error: " + error.getMessage());
                //TastyToast.makeText(context, "Network error.", TastyToast.LENGTH_SHORT, TastyToast.ERROR);


            }
        }) {

        };
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonObjReq);
    }
    /*
  pass jsonRequest null if there is no params
  this method is invoked without header
  */

   /* public PostJsonDataParser(final Context context, int method, String url, JSONObject jsonRequest, final boolean flag, boolean noHeader, final OnGetResponseListner listner) {
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
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(method, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(method,url, jsonRequest, new Response.Listener<JSONObject>() {

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
                    Util.showSnakBar(context, context.getResources().getString(R.string.invalid));
                    listner.onGetResponse(null);
                }else {
                    Util.showSnakBar(context, context.getResources().getString(R.string.networkerror));
                    listner.onGetResponse(null);
                }
                listner.onGetResponse(null);
                VolleyLog.d("Error: " + error.getMessage());
                //TastyToast.makeText(context, "Network error.", TastyToast.LENGTH_SHORT, TastyToast.ERROR);


            }
        });
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonObjReq);
    }
*/





    /*
      pass jsonRequest null if there is no params
      this method is invoked with header and try to refresh api
      */
    /*private void getAccessToken(Context context,View view) {
        Pref pref=new Pref(context);
        new PostJsonDataParser(context, Request.Method.GET, Api.accesstokenApi,null, true, new OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    try {
                        String success = response.optString("success");
                        if (success.equalsIgnoreCase("1")){
                            String token=response.optString("token");
                            AppData.accessToken=token;
                            pref.saveAccessToken(token);





                        }



                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }*/

    public PostJsonDataParser(final Context context, int method, String url,  final boolean flag, final OnGetResponseListner listner) {
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
        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(method,url, null, new Response.Listener<JSONObject>() {

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
                if (statusCode == 500) {
                //    getAccessToken(context,null);
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
    public interface OnGetResponseListner {
        void onGetResponse(JSONObject response);
    }
}
