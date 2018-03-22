package com.telefonica.movistarhome.templateapp.data;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface LoginService {
    @GET("user/login")
    Observable<String> login();
}