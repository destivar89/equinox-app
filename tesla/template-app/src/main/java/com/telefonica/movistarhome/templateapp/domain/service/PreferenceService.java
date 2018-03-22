package com.telefonica.movistarhome.templateapp.domain.service;


import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

public class PreferenceService {

    private static final String SETTINGS_PREFERENCES = "settingsPreferences";

    private static final String ACCOUNT_NUMBER_PREF = "accountNumberPref";
    private static final String CURRENT_ACCOUNT_ID_PREF = "currentAccountId";

    private SharedPreferences preferences;

    @Inject
    public PreferenceService(Context context){
        preferences = context.getSharedPreferences(SETTINGS_PREFERENCES, Context.MODE_PRIVATE);
    }

    public void setAccountNumber(String accountNumber){
        preferences.edit().putString(ACCOUNT_NUMBER_PREF, accountNumber).apply();
    }

    public String getAccountNumber(){
        return preferences.getString(ACCOUNT_NUMBER_PREF, null);
    }

    public void setCurrentAccountId(String accountId){
        preferences.edit().putString(CURRENT_ACCOUNT_ID_PREF, accountId).apply();
    }

    public String getCurrentAccountId(){
        return preferences.getString(CURRENT_ACCOUNT_ID_PREF, null);
    }

}
