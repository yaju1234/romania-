package com.craving.restaurant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.craving.restaurant.constants.Constants;
import com.craving.restaurant.model.ProductMain;
import com.craving.restaurant.restService.RestService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends BaseActivity {

    private static final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 5;
    private int progressStatus;
    Handler handler;
    ProgressBar progressBar;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        handler=new Handler();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkMultiplePermissions();
        } else {
            splashHandlerStart(100);
        }
        //splashHandlerStart(3000);
    }

    public void splashHandlerStart(int timeOut){

        callAPI();


    }

    private void callAPI() {
        if (isNetworkConnected()){
            Call<ProductMain> call=RestService.getInstance().restInterface.getStock();
            call.enqueue(new Callback<ProductMain>() {
                @Override
                public void onResponse(Call<ProductMain> call, Response<ProductMain> response) {
                    ProductMain main=response.body();
                    Constants.allProduct.clear();
                    Constants.allProduct.addAll(main.getProduse());
               //     Toast.makeText(SplashActivity.this,""+Constants.allProduct.size(),Toast.LENGTH_LONG).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(SplashActivity.this,MainActivity.class));
                            finish();
                        }
                    },1000);
                }

                @Override
                public void onFailure(Call<ProductMain> call, Throwable t) {
                    Toast.makeText(SplashActivity.this,""+t,Toast.LENGTH_LONG).show();

                }
            });
        }else
            Toast.makeText(SplashActivity.this,"Unable to connect to the internet",Toast.LENGTH_LONG).show();

    }

    private void checkMultiplePermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> permissionsNeeded = new ArrayList<String>();
            List<String> permissionsList = new ArrayList<String>();
            if(!addPermission(permissionsList, android.Manifest.permission.CAMERA))
            {
                permissionsNeeded.add("Camera");
            }

            if (!addPermission(permissionsList, android.Manifest.permission.READ_EXTERNAL_STORAGE))
            {
                permissionsNeeded.add("Read Storage");
            }

            if(!addPermission(permissionsList, android.Manifest.permission.WRITE_EXTERNAL_STORAGE))
            {
                permissionsNeeded.add("Write Storage");
            }

            if(!addPermission(permissionsList, android.Manifest.permission.ACCESS_FINE_LOCATION))
            {
                permissionsNeeded.add("Access Fine Location");
            }

            if (permissionsList.size() > 0)
            {
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                        REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            }
            else{
                splashHandlerStart(100);
            }
        }
    }

    private boolean addPermission(List<String> permissionsList, String permission) {
        if (Build.VERSION.SDK_INT >= 23)

            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(permission);

                // Check for Rationale Option
                if (!shouldShowRequestPermissionRationale(permission))
                    return false;
            }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {

                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
                perms.put(android.Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(android.Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,PackageManager.PERMISSION_GRANTED);
                perms.put(android.Manifest.permission.ACCESS_FINE_LOCATION,PackageManager.PERMISSION_GRANTED);

                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                if (perms.get(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                        && perms.get(android.Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED
                        && perms.get(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED&& perms.get(android.Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED) {
                    // All Permissions Granted
                    startActivity(new Intent(this,SplashActivity.class));
                    finish();
                    return;
                } else {
                    // Permission Denied
                    if (Build.VERSION.SDK_INT >= 23) {
                        Toast.makeText(getApplicationContext(), "Please permit all the permissions", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onClick(View v) {

    }
}
