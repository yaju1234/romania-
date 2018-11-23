package com.craving.restaurant.preferences;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Sumanta on 12/23/2015.
 */
public class Preference {

    private Context context = null;
    public final String prefName = "Cannogo";
    private String profileImageURL;
    private String appName;

    public Preference(Context context) {
        this.context = context;
    }

    public String getString(String key, String def) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        String s = prefs.getString(key, def);
        return s;
    }

    public void setString(String key, String val) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor e = prefs.edit();
        e.putString(key, val);
        e.commit();
    }

    public void setBoolean(String key, boolean val){
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor e = prefs.edit();
        e.putBoolean(key, val);
        e.commit();
    }

    public boolean getBoolean(String key){
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        boolean s = prefs.getBoolean(key, false);
        return s;
    }

    public  void clearData(){
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor e = prefs.edit();
        e.clear();
        e.commit();


    }

    public void setUserId(String UserId) {
        setString("UserId", UserId);
    }

    public String getUserId() {
        return getString("UserId", null);
    }
    public void setAccessToken(String AccessToken) {
        setString("AccessToken", AccessToken);
    }

    public String getAccessToken() {
        return getString("AccessToken", null);
    }



    public void setJobID(String UserId) {
        setString("jobid", UserId);
    }

    public String getJobID() {
        return getString("jobid", null);
    }




    public void setName(String Name) {
        setString("Name", Name);
    }

    public String getName() {
        return getString("Name", "");
    }

    public void setVerificationCode(String Name) {
        setString("code_", Name);
    }

    public String getVerificationCode() {
        return getString("code_", "");
    }

    public void setEmail(String Email) {
        setString("Email", Email);
    }

    public String getEmail() {
        return getString("Email", "");
    }

    public void setPhone(String Phone) {
        setString("Phone", Phone);
    }

    public String getPhone() {
        return getString("Phone", "");
    }
    public void setIsAdmin(String Phone) {
        setString("isAdmin", Phone);
    }

    public String getIsAdmin() {
        return getString("isAdmin", "");
    }

    public void setProfImage(String ProfImage) {
        setString("ProfImage", ProfImage);
    }

    public String getProfImage() {
        return getString("ProfImage", "");
    }



    public void setUserStatus(String setUserStatus) {
        setString("UserStatus", setUserStatus);
    }

    public String getUserStatus() {
        return getString("setUserStatus", "");
    }

    public void setLat(String lat) {
        setString("lat", lat);
    }

    public String getLat() {
        return getString("lat", "");
    }

    public void setLng(String lng) {
        setString("lng", lng);
    }

    public String getLng() {
        return getString("lng", "");
    }



    public void setAddress(String lng) {
        setString("addr", lng);
    }

    public String getAddress() {
        return getString("addr", "");
    }
    public void setDeviceToken(String DeviceToken) {
        setString("DeviceId", DeviceToken);
    }

    public String getDeviceToken() {
        return getString("DeviceId", "");
    }

    public String getReferralCode() {
        return getString("req", "");
    }

    public void setReferralCode(String req) {
        setString("req", req);
    }

    public String getIsVerified() {
        return getString("isVerify", "");
    }

    public void setIsVerified(String isVerify) {
        setString("isVerify", isVerify);
    }

    public String getIdentityImage() {
        return getString("identityImage", "");
    }

    public void setIdentityImage(String identityImage) {
        setString("identityImage", identityImage);
    }


    /*--ProviderState--*//*
    public void setProviderState(String ProviderState) {
        setString("ProviderState", ProviderState);
    }

    public String getProviderState() {
        return getString("ProviderState", "");
    }


    *//*--DeviceId--*//*
    public void setDeviceId(String DeviceId) {
        setString("DeviceId", DeviceId);
    }

    public String getDeviceId() {
        return getString("DeviceId", "");
    }


    *//*--Latitude--*//*
    public void setLatitude(Double Latitude) {
        setString("Latitude", String.valueOf(Latitude));
    }

    public Double getLatitude() {
//        return Double.parseDouble(getString("Latitude", "0.0"));
        String latitude = getString("Latitude", null);
        if (latitude != null) {
            return Double.parseDouble(latitude);
        }else{
            return null;
        }
    }

    *//*--Longitude--*//*
    public void setLongitude(Double Longitude) {
        setString("Longitude", String.valueOf(Longitude));
    }

    public Double getLongitude() {
        String longitude = getString("Longitude", null);
        if (longitude != null) {
            return Double.parseDouble(longitude);
        }else{
            return null;
        }
    }

    *//*--CustId--*//*
    public void setUserId(String UserId) {
        setString("UserId", UserId);
    }

    public String getUserId() {
        return getString("UserId", null);
    }
    public void setUserType(String UserId) {
        setString("user_type", UserId);
    }

    public String getUserType() {
        return getString("user_type", null);
    }



    public void setUserName(String UserId) {
        setString("username", UserId);
    }

    public String getUserName() {
        return getString("username", null);
    }

    public void setLoginType(String UserId) {
        setString("login_type", UserId);
    }

    public String getLoginType() {
        return getString("login_type", null);
    }


    *//*--SessionToken--*//*
    public void setSessionToken(String SessionToken) {
        setString("SessionToken", SessionToken);
    }

    public String getSessionToken() {
        return getString("SessionToken", null);
    }

    *//*--Email--*//*
    public void setEmail(String Email) {
        setString("Email", Email);
    }

    public String getEmail() {
        return getString("Email", "");
    }


    *//*--Name--*//*
    public void setName(String Name) {
        setString("Name", Name);
    }

    public String getName() {
        return getString("Name", "");
    }

    *//*CustAddress*//*
    public void setCustAddress(String CustAddress) {
        setString("CustAddress", CustAddress);
    }

    public String getCustAddress() {
        return getString("CustAddress", "");
    }

    *//*--CustDrivingLincese--*//*
    public void setCustDrivingLincese(String CustDrivingLincese) {
        setString("CustDrivingLincese", CustDrivingLincese);
    }

    public String getCustDrivingLincese() {
        return getString("CustDrivingLincese", "");
    }

    *//*--CustDrivingLincese--*//*
    public void setCustDrivingLinceseNo(String CustDrivingLinceseNo) {
        setString("CustDrivingLinceseNo", CustDrivingLinceseNo);
    }

    public String getCustDrivingLinceseNo() {
        return getString("CustDrivingLinceseNo", "");
    }


    *//*--CustImage--*//*
    public void setCustImage(String CustImage) {
        setString("CustImage", CustImage);
    }

    public String getCustImage() {
        return getString("CustImage", "");
    }

    *//*--CustPhNo--*//*
    public void setCustPhNo(String CustPhNo) {
        setString("CustPhNo", CustPhNo);
    }

    public String getCustPhNo() {
        return getString("CustPhNo", "");
    }

    *//*--SocialUser--*//*
    public void setIsSocialUser(String IsSocialUser) {
        setString("IsSocialUser", IsSocialUser);
    }

    public String getIsSocialUser() {
        return getString("IsSocialUser", "");
    }


    *//*--StartTime--*//*
    public void setStartTime(String StartTime) {
        setString("StartTime", StartTime);
    }

    public String getStartTime() {
        return getString("StartTime", "");
    }


    *//*--EndTime--*//*
    public void setEndTime(String EndTime) {
        setString("EndTime", EndTime);
    }

    public String getEndTime() {
        return getString("EndTime", "");
    }

    *//*--PinCode--*//*
    public void setPinCode(String PinCode) {
        setString("PinCode", PinCode);
    }

    public String getPinCode() {
        return getString("PinCode", "");
    }


    *//*--Date Of Birth--*//*
    public void setDOB(String DOB) {
        setString("DOB", DOB);
    }

    public String getDOB() {
        return getString("DOB", "");
    }


    *//*--Date Of Birth--*//*
    public void setHomeDelivery(boolean status) {
        setBoolean("HomeDelivery", status);
    }

    public boolean getHomeDelivery() {
        return getBoolean("HomeDelivery");
    }


    *//*--DeviceId--*//*
    public void setProfileImageURL(String profileImageURL) {
        setString("ImageURL", profileImageURL);
    }

    public String getProfileImageURL() {
        return getString("ImageURL", "");
    }

    public void setFullName(String volume) {
        setString("full_name", volume);
    }

    public String getFullName() {
        return getString("full_name", "");
    }
    public void setVolumeLavel(String volume) {
        setString("volume_level", volume);
    }

    public String getVolumeLavel() {
        return getString("volume_level", "");
    }


    public void setAppName(String appName) {
        setString("app_name", appName);
    }
    public String getAppName() {
        return getString("app_name", "");
    }

    public void setDeviceToken(String deviceToken) {
        setString("device_token", deviceToken);
    }
    public String getDeviceToken() {
        return getString("device_token", "");
    }*/
}

