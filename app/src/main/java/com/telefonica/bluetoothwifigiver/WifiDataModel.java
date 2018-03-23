package com.telefonica.bluetoothwifigiver;

import com.google.gson.annotations.SerializedName;

public class WifiDataModel {
    @SerializedName("ssid")
    private String ssid;
    @SerializedName("password")
    private String password;
    @SerializedName("security")
    private String security;

    public String getSsid() {
        return ssid;
    }

    public String getPassword() {
        return password;
    }

    public String getSecurity() {
        return security;
    }

    public WifiDataModel(String ssid, String password, String security) {
        this.ssid = ssid;
        this.password = password;
        this.security = security;
    }
}
