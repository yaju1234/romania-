package com.craving.restaurant;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.craving.restaurant.Enum.AppMenu;
import com.craving.restaurant.fragments.RootFragment;

import java.lang.reflect.Field;

public class MainActivity extends BaseActivity {

    private ImageView ivMenue;
    private DrawerLayout drawer;
    private static int ACTIVE_TAB_POSITION = 1;
    private boolean doubleBackToExitPressedOnce = false;
    private View tvDashboard;
    private View tvResDetails;
    private View tvMenus;
    private View tvOrderManagement;
    private View tvPaymentBilling;
    private View tvReviewManagement;
    private View tvAccountmanagemant;
    private View tvLogout;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivMenue=(ImageView)findViewById(R.id.ivMenue);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        ivMenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }else {
                    drawer.openDrawer(GravityCompat.START);
                }

            }
        });
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
        if (fragment == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.frame_container, getRootFragment(AppMenu.HOME),AppMenu.HOME.name()).commit();
        }
        initviews();


    }

    private void initviews() {
        title = findViewById(R.id.title);
        tvDashboard = findViewById(R.id.tvDashboard);
        tvResDetails = findViewById(R.id.tvResDetails);
        tvMenus = findViewById(R.id.tvMenus);
        tvOrderManagement = findViewById(R.id.tvOrderManagement);
        tvPaymentBilling = findViewById(R.id.tvPaymentBilling);
        tvReviewManagement = findViewById(R.id.tvReviewManagement);
        tvAccountmanagemant = findViewById(R.id.tvAccountmanagemant);
        tvLogout = findViewById(R.id.tvLogout);
        tvLogout = findViewById(R.id.tvLogout);
        tvLogout = findViewById(R.id.tvLogout);
        tvLogout = findViewById(R.id.tvLogout);
        tvLogout = findViewById(R.id.tvLogout);


        tvDashboard.setOnClickListener(this);
        tvResDetails.setOnClickListener(this);
        tvMenus.setOnClickListener(this);
        tvOrderManagement.setOnClickListener(this);
        tvPaymentBilling.setOnClickListener(this);
        tvReviewManagement.setOnClickListener(this);
        tvAccountmanagemant.setOnClickListener(this);
        tvLogout.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvDashboard:
                closeDrawer();
                onMenuItemSelect(AppMenu.HOME);
                break;
            case R.id.tvResDetails:
                closeDrawer();
                break;
            case R.id.tvMenus:
                closeDrawer();
                onMenuItemSelect(AppMenu.MENUS);
                break;
            case R.id.tvOrderManagement:
                closeDrawer();
                break;
            case R.id.tvPaymentBilling:
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                break;
            case R.id.tvReviewManagement:
                closeDrawer();
                break;
            case R.id.tvAccountmanagemant:
                closeDrawer();
                break;
            case R.id.tvLogout:
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                break;
        }

    }
    public void setPageTitle(String pageTitle){
        title.setText(pageTitle);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
            if (fragment != null) {
                if (!fragment.getChildFragmentManager().popBackStackImmediate())
                    exitApplication();
            } else {
                exitApplication();
            }
        }


    }
    private void exitApplication() {
        if (ACTIVE_TAB_POSITION != 1) {
            onMenuItemSelect(AppMenu.HOME);
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, R.string.double_tap_to_exit, Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }

    }

    public void onMenuItemSelect(AppMenu appMenu) {
        Fragment fragment = null;
        String tag = null;
        switch (appMenu){
            case HOME:
                tag = AppMenu.HOME.name();
                fragment = getSupportFragmentManager().findFragmentByTag(tag);
                if (ACTIVE_TAB_POSITION == 1) {
                    fragment = null;
                }
                ACTIVE_TAB_POSITION = 1;
                if (fragment == null)
                    fragment = getRootFragment(AppMenu.HOME);
                break;
            case PAYMENT:
                tag = AppMenu.PAYMENT.name();
                fragment = getSupportFragmentManager().findFragmentByTag(tag);
                if (ACTIVE_TAB_POSITION == 2) {
                    fragment = null;
                }
                ACTIVE_TAB_POSITION = 2;
                if (fragment == null)
                    fragment = getRootFragment(AppMenu.PAYMENT);
                break;
            case PRODUCT_FILTER:
                tag = AppMenu.PRODUCT_FILTER.name();
                fragment = getSupportFragmentManager().findFragmentByTag(tag);
                if (ACTIVE_TAB_POSITION == 3) {
                    fragment = null;
                }
                ACTIVE_TAB_POSITION = 3;
                if (fragment == null)
                    fragment = getRootFragment(AppMenu.PRODUCT_FILTER);
                break;
            case REVIEW_MNAGEMENT:
                tag = AppMenu.REVIEW_MNAGEMENT.name();
                fragment = getSupportFragmentManager().findFragmentByTag(tag);
                if (ACTIVE_TAB_POSITION == 4) {
                    fragment = null;
                }
                ACTIVE_TAB_POSITION = 4;
                if (fragment == null)
                    fragment = getRootFragment(AppMenu.REVIEW_MNAGEMENT);
                break;
            case ACCOUNT_MANAGEMENT:
                tag = AppMenu.ACCOUNT_MANAGEMENT.name();
                fragment = getSupportFragmentManager().findFragmentByTag(tag);
                if (ACTIVE_TAB_POSITION == 5) {
                    fragment = null;
                }
                ACTIVE_TAB_POSITION = 5;
                if (fragment == null)
                    fragment = getRootFragment(AppMenu.ACCOUNT_MANAGEMENT);
                break;

            case MENUS:
                tag = AppMenu.MENUS.name();
                fragment = getSupportFragmentManager().findFragmentByTag(tag);
                if (ACTIVE_TAB_POSITION == 6) {
                    fragment = null;
                }
                ACTIVE_TAB_POSITION = 6;
                if (fragment == null)
                    fragment = getRootFragment(AppMenu.MENUS);
                break;

        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_container, fragment, tag).commit();
    }
    public Fragment getRootFragment(AppMenu appMenu){
        Fragment fragment = new RootFragment();
        Bundle bundle = new Bundle();
        switch (appMenu){

            case HOME:
                bundle.putString("appMenu", AppMenu.HOME.name());
                break;
            case MENUS:
                bundle.putString("appMenu", AppMenu.MENUS.name());
                break;
            case PAYMENT:
                bundle.putString("appMenu", AppMenu.PAYMENT.name());
                break;
            case PRODUCT_FILTER:
                bundle.putString("appMenu", AppMenu.PRODUCT_FILTER.name());
                break;
            case REVIEW_MNAGEMENT:
                bundle.putString("appMenu", AppMenu.REVIEW_MNAGEMENT.name());
                break;
            case ACCOUNT_MANAGEMENT:
                bundle.putString("appMenu",AppMenu.ACCOUNT_MANAGEMENT.name());
                break;


        }
        fragment.setArguments(bundle);
        return fragment;
    }


    @SuppressLint("RestrictedApi")
    static void removeShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShifting(false);
                // set once again checked value, so view will be updated
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("ERROR NO SUCH FIELD", "Unable to get shift mode field");
        } catch (IllegalAccessException e) {
            Log.e("ERROR ILLEGAL ALG", "Unable to change value of shift mode");
        }
    }
private void closeDrawer(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
            }
        },100);
}


}
