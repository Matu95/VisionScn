package com.example.matu.myapplication.interfaces;

import com.example.matu.myapplication.model.Users;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MyApiService {

    @GET("users")
    Call<Users> users();

}
