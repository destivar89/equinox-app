package com.telefonica.bluetoothwifigiver;


import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkModule {

    private static NetworkModule instance;
    private Retrofit retrofit;
    private WifiApiService service;

    public static NetworkModule getInstance() {
        if (instance == null) {
            instance = new NetworkModule();
            instance.init();
        }
        return instance;
    }

    public void init() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://blooming-refuge-93800.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(WifiApiService.class);
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public WifiApiService getService() {
        return service;
    }

    public WifiDataModel getWifiFromApi(String mac) throws Exception {
        //TODO: Do call async
        try {
            Call<WifiDataModel> callWifiInfo = service.getWifi();
            Response<WifiDataModel> response = callWifiInfo.execute();
            WifiDataModel wifiDataModel = response.body();
            return wifiDataModel;
        } catch (Exception e) {
            throw new Exception();
        }
    }

}
