package com.telefonica.movistarhome.templateapp.presentation.commons;


import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;


public class Navigator {


    public void closeFragment(AppCompatActivity parentActivity){

        FragmentManager fragmentManager = parentActivity.getSupportFragmentManager();
        fragmentManager.popBackStack();
    }
}
