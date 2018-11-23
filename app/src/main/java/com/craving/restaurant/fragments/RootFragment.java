package com.craving.restaurant.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.craving.restaurant.Enum.AppMenu;
import com.craving.restaurant.MainActivity;
import com.craving.restaurant.R;

public class RootFragment extends BaseFragment {

    int mStackLevel = 1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_root, null, false);

        Fragment fragment = getChildFragmentManager().findFragmentById(R.id.fl_root);
        if (fragment == null) {
            shitchFragment(AppMenu.valueOf(getArguments().getString("appMenu")));
        }
        return v;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        // baseActivity.getSupportActionBar().setTitle("Home");
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }

    public void displayView(Fragment fragment) {
        if (fragment != null) {
            FragmentManager fragmentManager = getChildFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fl_root, fragment).addToBackStack(null).commit();

        }
    }

    public void goBack() {

        ((MainActivity) getActivity()).onBackPressed();
    }

    public void shitchFragment(AppMenu appMenu){
        switch (appMenu){


            case HOME:
                getChildFragmentManager().beginTransaction().add(R.id.fl_root, new DashboardFragment()).commit();
                break;
            case PAYMENT:
                //getChildFragmentManager().beginTransaction().add(R.id.fl_root, new CartFragment()).commit();
                break;
            case PRODUCT_FILTER:
                getChildFragmentManager().beginTransaction().add(R.id.fl_root, new ProductFilterFragment()).commit();
                break;
            case REVIEW_MNAGEMENT:
                //  getChildFragmentManager().beginTransaction().add(R.id.fl_root, new ProfileFragment()).commit();
                break;
            case ACCOUNT_MANAGEMENT:
                //  getChildFragmentManager().beginTransaction().add(R.id.fl_root, new ProfileFragment()).commit();
                break;
            case MENUS:
                  getChildFragmentManager().beginTransaction().add(R.id.fl_root, new AllMenueFragment()).commit();
                break;


        }
    }

    public void clearSatck(){
        FragmentManager fm = getChildFragmentManager();
        int count = fm.getBackStackEntryCount();
        for(int i = 0; i < count; ++i) {
            fm.popBackStackImmediate();
        }
        Fragment fragment = getChildFragmentManager().findFragmentById(R.id.fl_root);
        getChildFragmentManager().beginTransaction().add(R.id.fl_root, new DashboardFragment()).commit();

    }

   /* void addFragmentToStack() {
        mStackLevel++;

        // Instantiate a new fragment.
        Fragment newFragment = CountingFragment.newInstance(mStackLevel);

        // Add the fragment to the activity, pushing this transaction
        // on to the back stack.
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.fragment_slide_left_enter,
                R.anim.fragment_slide_left_exit,
                R.anim.fragment_slide_right_enter,
                R.anim.fragment_slide_right_exit);
        ft.replace(R.id.simple_fragment, newFragment);
        ft.addToBackStack(null);
        ft.commit();
    }*/

}
