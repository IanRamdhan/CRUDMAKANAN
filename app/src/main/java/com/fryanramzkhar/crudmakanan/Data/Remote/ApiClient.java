package com.fryanramzkhar.crudmakanan.Data.Remote;

import com.fryanramzkhar.crudmakanan.Utils.Constant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    Gson gson = new GsonBuilder()
            .setLenient()
            .create();


    private static Retrofit retrofit = null;
    public static Retrofit getClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}