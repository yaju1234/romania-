package com.craving.restaurant;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.craving.restaurant.constants.LocationAddress;
import com.craving.restaurant.preferences.Preference;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.acos;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener, LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final int LOCATION_PERMISSION_REQUEST = 100;
    public Preference preference = null;
    SharedPreferences mPref;
    SharedPreferences.Editor mEditor;
    public static boolean isCategoryView = false;
    String email;
    public int height, width;
    private int actionBarHeight;
    public static boolean flag = true;
    private static final String TAG = "LocationService";
    private static final long INTERVAL = 1000 * 3;
    private static final long FASTEST_INTERVAL = 0;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;
    private File file;
    public static String productCurrentImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preference = new Preference(BaseActivity.this);
        Display display = getWindowManager().getDefaultDisplay();
        height = display.getHeight();
        width = display.getWidth();
        //FirebaseApp.initializeApp(BaseActivity.this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();
        if (checkLocationPermission()) {
            createLocationRequest();
        } else {

        }

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();
            Log.d(TAG, "Connected .....................");
        } else {
            Log.d(TAG, "not connected .....................");
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed .............");
    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        updateUI();
        getAddress(mCurrentLocation);

    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void updateUI() {
        Log.d(TAG, "UI update initiated .............");
        if (null != mCurrentLocation) {
            String lat = String.valueOf(mCurrentLocation.getLatitude());
            String lng = String.valueOf(mCurrentLocation.getLongitude());
            preference.setLat(lat);
            preference.setLng(lng);
            Log.d(TAG, "UI update LAt  ............." + lat + lng);


        } else {
            Log.d(TAG, "location is null ...............");
        }
    }

    protected void startLocationUpdates() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if (mGoogleApiClient!=null&&mLocationRequest!=null) {
            PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this);
        }
        Log.d(TAG, "Location update started ..............: ");
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_PERMISSION_REQUEST);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_PERMISSION_REQUEST);
            }
            return false;
        } else {
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){

            case LOCATION_PERMISSION_REQUEST:{
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        createLocationRequest();
                        /*if (googleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        googleMap.setMyLocationEnabled(true);*/
                    }
                } else {
                    Toast.makeText(this, "permission denied",
                            Toast.LENGTH_LONG).show();
                }
                return;
            }

        }
    }



    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
    protected boolean isValidEmail(CharSequence target) {


            String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

            Pattern pattern = Pattern.compile(EMAIL_PATTERN);
            Matcher matcher = pattern.matcher(target);
            return matcher.matches();


        /*if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }*/
    }

    public void setTickOnValidate(EditText editText, final ImageView tick, final boolean phNo, final boolean name, final boolean email, final boolean password){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(phNo){
                    if(s.toString().length() >= 10){
                        tick.setVisibility(View.VISIBLE);
                    }else tick.setVisibility(View.INVISIBLE);
                }else if(name){
                    if(s.toString().length() >2){
                        tick.setVisibility(View.VISIBLE);
                    }else tick.setVisibility(View.INVISIBLE);
                }else if(email){
                    if(isValidEmail(s.toString())){
                        tick.setVisibility(View.VISIBLE);
                    }else tick.setVisibility(View.INVISIBLE);
                }else if(password){
                    if(s.toString().length() >5){
                        tick.setVisibility(View.VISIBLE);
                    }else tick.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
    public void hideKeyBoard(EditText et) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
    }

    public void showKeyBoard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }




    public void showMsg(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
//        displayDialog(msg);
    }

    protected void showBackArrow() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowHomeEnabled(true);
        }
    }
    public int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
    // A method to find height of the status bar
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    TypedValue tv = new TypedValue();
    public int actionBarHeiht(){
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
        {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }


    protected void hideSoftKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    protected void toogleDrawer(DrawerLayout drawerLayout){
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            drawerLayout.openDrawer(GravityCompat.START);
    }

    protected void showToast(String msg) {
        Toast.makeText(this,msg, Toast.LENGTH_LONG).show();
    }







    public SpannableString underlineText(String text){

        SpannableString str_underline = new SpannableString(text);

        int flag = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;
        str_underline.setSpan(new UnderlineSpan(), 0, str_underline.length(), flag);

        return str_underline;

    }

    public boolean validateEmail(String thisname) {
        String regexStrforEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(!thisname.matches(regexStrforEmail))
            return false;

        return true;
    }

    public static void alertDialog(Context con, String msg , String title){
        AlertDialog.Builder builder = new AlertDialog.Builder(con);
        //Uncomment the below code to Set the message and title from the strings.xml file
        //builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);

        //Setting message manually and performing action on button click
        builder.setMessage(msg)
                .setCancelable(true)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
                /*.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    }
                });*/

        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle(title);
        alert.show();
    }

    public static void alertDialogWithClick(Context con, String msg , String title, final SuccessListener _callback){
        AlertDialog.Builder builder = new AlertDialog.Builder(con);
        //Uncomment the below code to Set the message and title from the strings.xml file
        //builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);

        //Setting message manually and performing action on button click
        builder.setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        _callback.onYesClick();
                    }
                });
                /*.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    }
                });*/

        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle(title);
        alert.show();
    }


    public interface SuccessListener  {
        void onYesClick();
    }

    public interface ForgetPasswordListener{
        void onYesClick(String email);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            /*if(getCameraSetFragment()!=null){
                getCameraSetFragment().onActivityResult(requestCode, resultCode, data);
                setCameraSetFragment(null);
            }*/
    }


    protected View view;

    private final int maxLines = 4;
    private final String[] permissions = new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE };








    protected void hideViews() {}

    public void setView(View view) {
        this.view = view;
    }
    public void showHideProgress(ProgressDialog mProgressDialog) {
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        if(!mProgressDialog.isShowing())
            mProgressDialog.show();
        else
            mProgressDialog.dismiss();
    }

    public void showSnackBar(final CoordinatorLayout coordinatorLayout, String msg){
       /* Snackbar snackbar = Snackbar.make(coordinatorLayout, msg, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(getResources().getColor(R.color.red));
        snackbar.setDuration(2000);
        snackbar.show();*/
        Snackbar snackbar;
        snackbar = Snackbar.make(coordinatorLayout, msg, Snackbar.LENGTH_LONG);
        snackbar.setDuration(5000);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(getResources().getColor(R.color.white));
        TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }

    public void showSnackBarMsg(final CoordinatorLayout coordinatorLayout, String msg){
       /* Snackbar snackbar = Snackbar.make(coordinatorLayout, msg, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(getResources().getColor(R.color.red));
        snackbar.setDuration(2000);
        snackbar.show();*/
        Snackbar snackbar;
        snackbar = Snackbar.make(coordinatorLayout, msg, Snackbar.LENGTH_LONG);
        snackbar.setDuration(4000);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(getResources().getColor(R.color.white));
        TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }

    static double PI_RAD = Math.PI / 180.0;

    /**
     * Use Great Circle distance formula to calculate distance between 2 coordinates in meters.
     */
    /*double greatCircleInFeet(LatLng latLng1, LatLng latLng2) {
        return greatCircleInKilometers(latLng1.latitude, latLng1.longitude, latLng2.latitude,
                latLng2.longitude) * 3280.84;
    }

    *//**
     * Use Great Circle distance formula to calculate distance between 2 coordinates in meters.
     *//*
    public double greatCircleInMeters(LatLng latLng1, LatLng latLng2) {
        return greatCircleInKilometers(latLng1.latitude, latLng1.longitude, latLng2.latitude,
                latLng2.longitude) * 1000;
    }*/

    /**
     * Use Great Circle distance formula to calculate distance between 2 coordinates in kilometers.
     * https://software.intel.com/en-us/blogs/2012/11/25/calculating-geographic-distances-in-location-aware-apps
     */
    public double greatCircleInKilometers(double lat1, double long1, double lat2, double long2) {
        double phi1 = lat1 * PI_RAD;
        double phi2 = lat2 * PI_RAD;
        double lam1 = long1 * PI_RAD;
        double lam2 = long2 * PI_RAD;

        return 6371.01 * acos(sin(phi1) * sin(phi2) + cos(phi1) * cos(phi2) * cos(lam2 - lam1));
    }
    private void getAddress(Location location) {

        //you can hard-code the lat & long if you have issues with getting it
        //remove the below if-condition and use the following couple of lines
        //double latitude = 37.422005;
        //double longitude = -122.084095

        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            LocationAddress locationAddress = new LocationAddress();
            locationAddress.getAddressFromLocation(latitude, longitude,
                    getApplicationContext(), new GeocoderHandler());
        } else {
            Toast.makeText(this,"Unable find the location",Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public void onDestroy() {
        Log.d(TAG, "Location onDestroy ..............: ");
        mGoogleApiClient.disconnect();
        super.onDestroy();
    }
    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
           preference.setAddress(locationAddress);
        }
    }
    public Double getDistanceFromMyLocation(Double toLat, Double toLng){
        Location loc1 = new Location("");
        loc1.setLatitude(Double.parseDouble(preference.getLat()));
        loc1.setLongitude(Double.parseDouble(preference.getLng()));

        Location loc2 = new Location("");
        loc2.setLatitude(toLat);
        loc2.setLongitude(toLng);

        float distanceInMeters = loc1.distanceTo(loc2);
        //float miles= (float) (distanceInMeters*0.000621371192);
        double miles = distanceInMeters * 0.00062137119;
        return miles;
    }
    public static String convertToDaysHoursMinutes(long minutes) {

        int day = (int) TimeUnit.MINUTES.toDays(minutes);
        long hours = TimeUnit.MINUTES.toHours(minutes) - (day *24);
        long minute = TimeUnit.MINUTES.toMinutes(minutes) - (TimeUnit.MINUTES.toHours(minutes)* 60);

        String result = "";

        if (day != 0){
            result += day;
            if (day == 1){
                result += " day ";
            }
            else{
                result += " days ";
            }

            return result;
        }

        if (hours != 0){
            result += hours;

            if (hours == 1){
                result += " hr ";
            }
            else{
                result += " hrs ";
            }
        }

        if (minute != 0){
            result += minute;

            if (minute == 1){
                result += " min";
            }
            else{
                result += " mins";
            }
        }

        return result;
    }
    private String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {
            Log.e(TAG, "getRealPathFromURI Exception : " + e.toString());
            return "";
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
