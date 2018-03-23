package com.telefonica.bluetoothwifigiver;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WifiApiService {
    @GET("/wifi")
    Call<WifiDataModel> getWifi();
}
