package com.telefonica.movistarhome.templateapp.infraestructure.di.commons;


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.telefonica.movistarhome.templateapp.data.LoginService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {
    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient().newBuilder();
        OkHttpClient client = clientBuilder.build();
        return client;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofitClient(OkHttpClient client) {
        Retrofit retrofitClient = new Retrofit.Builder()
                .baseUrl("https://owner-api.teslamotors.com/api/1/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofitClient;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofitLoginClient(OkHttpClient client) {
        Retrofit retrofitLoginClient = new Retrofit.Builder()
                .baseUrl("https://my.teslamotors.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofitLoginClient;
    }

    @Provides
    @Singleton
    public LoginService provideApiService(Retrofit retrofit) {
        LoginService apiService = retrofit.create(LoginService.class);
        return apiService;
    }
}
