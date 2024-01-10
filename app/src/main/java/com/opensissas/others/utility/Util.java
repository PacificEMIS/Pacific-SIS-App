package com.opensissas.others.utility;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.listeners.ActionClickListener;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.WINDOW_SERVICE;

/**
 * Created by root on 1/2/18.
 */

public class Util implements ActivityCompat.OnRequestPermissionsResultCallback {
    private static NetworkInfo networkInfo;
    private static int countryCode;
    private static Context c = null;
    public static String globalDateFormate = "yyyy-MM-dd'T'HH:mm:ss";

    /**
     * return true if connection is enabled otherwise return false
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        try {
            networkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // test for connection for WIFI
        if (networkInfo != null
                && networkInfo.isAvailable()
                && networkInfo.isConnected()) {
            return true;
        }

        networkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        // test for connection for Mobile
        return networkInfo != null
                && networkInfo.isAvailable()
                && networkInfo.isConnected();

    }

    /**
     * return true if email id pattern is correct
     * @param target
     * @return
     */
    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    /*public static boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }*/

    /**
     * convert string to JSONObject
     * @param responce
     * @return
     */
    public static JSONObject getjsonobject(String responce) {
        JSONObject jobj = null;
        try {
            jobj = new JSONObject(responce);
        } catch (Exception e) {

        }
        return jobj;
    }

    public static final boolean isValidPhoneNumber(CharSequence target) {
        if (target.length() != 10) {
            return false;
        } else {
            return android.util.Patterns.PHONE.matcher(target).matches();
        }
    }

    /**
     * convert date one pattern to another
     * @param reqdate
     * @param dateformat
     * @param reqformat
     * @return
     */
    public static String changeAnyDateFormat(String reqdate, String dateformat, String reqformat) {
        //String	date1=reqdate;

        if (reqdate.equalsIgnoreCase("") ||reqdate.equalsIgnoreCase("null") || dateformat.equalsIgnoreCase("") || reqformat.equalsIgnoreCase(""))
            return "";
        SimpleDateFormat format = new SimpleDateFormat(dateformat);
        String changedate = "";
        Date dt = null;
        if (!reqdate.equals("") && !reqdate.equals("null")) {
            try {
                dt = format.parse(reqdate);
                //SimpleDateFormat your_format = new SimpleDateFormat("dd-MMM-yyyy");
                SimpleDateFormat your_format = new SimpleDateFormat(reqformat);
                changedate = your_format.format(dt);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return reqdate;
            }


        }
        return changedate;
    }
    public static Date changeStringToDate(String reqdate, String dateformat) {
        Date dt = null;
        if (reqdate.equalsIgnoreCase("") || dateformat.equalsIgnoreCase(""))
            return dt;
        SimpleDateFormat format = new SimpleDateFormat(dateformat);

        if (!reqdate.equals("") && !reqdate.equals("null")) {
            try {
                dt = format.parse(reqdate);

            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

            }


        }
        return dt;
    }

    /**
     * show error message
     * @param context
     * @param message
     */
    public static void showSnakBar(final Context context, String message) {
        Snackbar.with(context) // context
                .text(message) // text to display
                .actionLabel("Try Again") // action button label
                .duration(Snackbar.SnackbarDuration.LENGTH_INDEFINITE)
                .animation(true)
                .actionListener(new ActionClickListener() {
                    @Override
                    public void onActionClicked(Snackbar snackbar) {
                        Activity a = (Activity) context;
                        Intent i = a.getIntent();
                        a.overridePendingTransition(0, 0);
                        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        a.finish();
                        a.overridePendingTransition(0, 0);
                        a.startActivity(i);

                    }
                })
                .show((Activity) context);

    }

    public static void showSnakBar(final Context context, String message, final View view) {
        if (c != context) {

            Snackbar.with(context) // context
                    .text(message) // text to display
                    .actionLabel("Try Again") // action button label
                    .duration(Snackbar.SnackbarDuration.LENGTH_INDEFINITE)
                    .animation(true)
                    .actionListener(new ActionClickListener() {
                        @Override
                        public void onActionClicked(Snackbar snackbar) {
                            view.performClick();

                        }
                    })
                    .show((Activity) context);
        }
        c = context;

    }

    /**
     * open keyboard
     * @param context
     */
    public static void openKeyBoard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * close keyboard
     * @param context
     */
    public static void closeKeyBoard(Context context) {
        View view = ((Activity) context).getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /*public static void addRippleEffect(View v) {
        MaterialRippleLayout.on(v).rippleColor(Color.parseColor("#3e948888")).rippleAlpha(0.1f).rippleHover(true).create();
    }*/

    /**
     * used to encode string
     * @param value
     * @return
     */
    public static String encode(String value) {
        try {
            value = URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static boolean isTimeAfter(Date startTime, Date endTime) {
        if (endTime.before(startTime)) { //Same way you can check with after() method also.
            return false;
        } else {
            return true;
        }
    }

    public static boolean isTimeBefore(Date startTime, Date endTime) {
        if (startTime.after(endTime)) { //Same way you can check with after() method also.
            return false;
        } else {
            return true;
        }
    }

    public static boolean nullChecker(String value) {
        return (value.equals("") || value.equals("null") || value.isEmpty() || value == null) ? true : false;
    }

    public static String getFreshValue(String value) {
        return (value.equals("") || value.equals("null") || value.isEmpty() || value == null) ? "" : value;
    }


    public static String getFreshValue(String value, String defaultValue) {
        return ( value == null  || value.equals("null") || value.equals("NaN")) ? defaultValue : value;
    }
    public static String getDecimalTwoPoint(double d) {
        String s = "" + d;
        if (s.equalsIgnoreCase("")) {
            return s;
        }
        try {
//            s=new DecimalFormat("##.##").format(d);
            NumberFormat formatter = NumberFormat.getNumberInstance();
            formatter.setMinimumFractionDigits(2);
            formatter.setMaximumFractionDigits(2);
            s = formatter.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    public static Calendar parseDateString(String date) {
        final SimpleDateFormat BIRTHDAY_FORMAT_PARSER = new SimpleDateFormat("MM/dd/yyyy");
        Calendar calendar = Calendar.getInstance();
        BIRTHDAY_FORMAT_PARSER.setLenient(false);
        try {
            calendar.setTime(BIRTHDAY_FORMAT_PARSER.parse(date));
        } catch (ParseException e) {
        }
        return calendar;
    }

    public static boolean isValidBirthday(String birthday) {
        Calendar calendar = parseDateString(birthday);
        int year = calendar.get(Calendar.YEAR);
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        return year >= 1900 && year < thisYear;
    }

    /**
     * show images preview


  /* public static PopupWindow getPopUpWindow(Context context, String image) {
        View v = LayoutInflater.from(context).inflate(R.layout.image_view_row, null);
        ImageView imageView = v.findViewById(R.id.ivImageRow);
        ImageView ivCancel = v.findViewById(R.id.ivCancel);

        final PopupWindow popupWindow = new PopupWindow(v, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, true);
       if (!TextUtils.isEmpty(image)) {
           Picasso.with(context).load(image).placeholder(R.drawable.no_thumbnail).error(R.drawable.no_thumbnail).into(imageView);
       }

        //Picasso.with(context).load(image).into(imageView);


        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        return popupWindow;
    }*/


    static File getDirectory(String variableName, String defaultPath, String fileName) {
        String path = System.getenv(variableName);
        return path == null ? new File(defaultPath + fileName) : new File(path + fileName);
    }

    public static File getExternalStorageDirectory(String fileName) {
        return getDirectory("EXTERNAL_STORAGE", "/TakePhoto", fileName);
    }


    public static String getSystemDateTime() {
        String currentDateTime = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            currentDateTime = sdf.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return currentDateTime;
    }

    /**
     * get device width
     * @param context
     * @return
     */
    public static int getScreenWidthInDPs(Context context){
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        //int widthInDP = Math.round(dm.widthPixels / dm.density);
        return dm.widthPixels;
    }

    /**
     * get device screen height
     * @param context
     * @return
     */
    public static int getScreenHeightInDPs(Context context){
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        int heightInDP = Math.round(dm.heightPixels / dm.density);
        return dm.heightPixels;
    }

    /**
     * change color of vector drawable images
     * @param imageView
     * @param colorCode
     */
    public static void changeDrawableTint(ImageView imageView, String colorCode) {
        if (imageView.getDrawable()!=null)
        imageView.getDrawable().setColorFilter(Color.parseColor(colorCode), PorterDuff.Mode.SRC_IN);

    }
    public static byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    /**
     * get file name from file path
     * @param path
     * @return
     */
    public static String getFileName(String path) {
        String name="";
        try {
            int cut = path.lastIndexOf('/');
            if (cut != -1) {
                name = path.substring(cut + 1);
            }
        }
        catch (Exception e){
            name="";
            e.printStackTrace();
        }
        return name;
    }

  /*  public static String getProfileName(String profile_id){
        switch (profile_id){
            case ConstantData.driverProfileId:
                return "Driver";
            case ConstantData.helperProfileId:
                return "Helper";
            case ConstantData.packerProfileId:
                return "Packer";
            case ConstantData.thirdPartyProfileId:
                return "3rd Party";
            case ConstantData.claimRepairProfileId:
                return "Claim Repaire";
            case ConstantData.moverProfileId:
                return "Mover";
            case ConstantData.customerNonMilitoryProfileId:
                return "Customer";
            case ConstantData.customerMilitoryProfileId:
                return "Customer";

            default:
                return "";
        }
    }*/

    /**
     * check card expiry date valid or not
     * @param sdate
     * @return
     */
    public static boolean ValidCardExpiry(String sdate){
        boolean expired=false;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/yyyy");
        simpleDateFormat.setLenient(false);
        Date expiry = null;
        try {
            expiry = simpleDateFormat.parse(sdate);
            expired= expiry.after(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
            //expired=false
        }

        return expired;
    }
    /*public static void getOnLine(Context context) {
        try {


            SharedPreferences preferences = context.getSharedPreferences("MyReloPro", MODE_PRIVATE);
            String profileId = preferences.getString("profileId", "") ;
            if (profileId.equalsIgnoreCase(ConstantData.driverProfileId)||
                    profileId.equalsIgnoreCase(ConstantData.claimRepairProfileId)||
                    profileId.equalsIgnoreCase(ConstantData.packerProfileId)) {

                final SharedPreferenceHelper driver = SharedPreferenceHelper.getInstance(context);
                AppData.sUserId = driver.getUserId();

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String timestamp = dateFormat.format(new Date());
                ActiveUserModel model = new ActiveUserModel(AppData.sUserId, true, "", "", "", timestamp);
                FirebaseDatabase databaseInstance = FirebaseDatabase.getInstance();
                databaseInstance.goOnline();
                DatabaseReference databaseRef = databaseInstance.getReference("active_users");
                databaseRef.child(AppData.sUserId).setValue(model);
            }
            else{
                final com.myrelopro.helper.service.preference.SharedPreferenceHelper helper = com.myrelopro.helper.service.preference.SharedPreferenceHelper.getInstance(context);
                com.myrelopro.helper.app.AppData.sUserId = helper.getUserId();

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String timestamp = dateFormat.format(new Date());
                ActiveUserModel model = new ActiveUserModel(com.myrelopro.helper.app.AppData.sUserId, true, "", "", "", timestamp);
                FirebaseDatabase databaseInstance = FirebaseDatabase.getInstance();
                databaseInstance.goOnline();
                DatabaseReference databaseRef = databaseInstance.getReference("active_users");
                databaseRef.child(com.myrelopro.helper.app.AppData.sUserId).setValue(model);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void getOffLine(Context context) {
        try {
            SharedPreferences preferences = context.getSharedPreferences("MyReloPro", MODE_PRIVATE);
            String profileId = preferences.getString("profileId", "") ;
            if (profileId.equalsIgnoreCase(ConstantData.driverProfileId)||
                    profileId.equalsIgnoreCase(ConstantData.claimRepairProfileId)||
                    profileId.equalsIgnoreCase(ConstantData.packerProfileId)) {

                final SharedPreferenceHelper driver = SharedPreferenceHelper.getInstance(context);
                AppData.sUserId = driver.getUserId();

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String timestamp = dateFormat.format(new Date());
                ActiveUserModel model = new ActiveUserModel(AppData.sUserId, false, "", "", "", timestamp);
                FirebaseDatabase databaseInstance = FirebaseDatabase.getInstance();
                databaseInstance.goOnline();
                DatabaseReference databaseRef = databaseInstance.getReference("active_users");
                databaseRef.child(AppData.sUserId).setValue(model);
            }
            else{
                final com.myrelopro.helper.service.preference.SharedPreferenceHelper helper = com.myrelopro.helper.service.preference.SharedPreferenceHelper.getInstance(context);
                com.myrelopro.helper.app.AppData.sUserId = helper.getUserId();

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String timestamp = dateFormat.format(new Date());
                ActiveUserModel model = new ActiveUserModel(com.myrelopro.helper.app.AppData.sUserId, false, "", "", "", timestamp);
                FirebaseDatabase databaseInstance = FirebaseDatabase.getInstance();
                databaseInstance.goOnline();
                DatabaseReference databaseRef = databaseInstance.getReference("active_users");
                databaseRef.child(com.myrelopro.helper.app.AppData.sUserId).setValue(model);
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
*/
    /**
     * download file using download manager
     * @param document
     * @param context
     */
    public static void downloadFile(String document, Context context){
        int cameraPermission = ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int storagePermission = ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (Build.VERSION.SDK_INT >= 23) {
            if (cameraPermission != PackageManager.PERMISSION_GRANTED ||
                    storagePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                return;
            }
        }
        DownloadManager downloadManager= (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        ArrayList<Long> list=new ArrayList<>();
        Uri uri;
        list.clear();

        uri= Uri.parse(document);
        DownloadManager.Request request=new DownloadManager.Request(uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setAllowedOverRoaming(false);
        request.setTitle(" Downloading "+(document.substring(document.lastIndexOf("/")+1)));
        request.setDescription("Downloading ...");
        request.setVisibleInDownloadsUi(true);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"/ReloHelper/"+document.substring(document.lastIndexOf("/")+1));
        //kamalika
        Toast.makeText(context, "download complete", Toast.LENGTH_LONG).show();
        long refId=downloadManager.enqueue(request);
        list.add(refId);

        /*if(list.size()>0) {
            File myFile = new File(Environment.DIRECTORY_DOWNLOADS, "/ReloHelper/" + document.substring(document.lastIndexOf("/") + 1));
            try {
                //openFile(context, myFile);
               // File file=myFile;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

    }





}
