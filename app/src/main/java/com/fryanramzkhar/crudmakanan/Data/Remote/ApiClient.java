package com.fryanramzkhar.crudmakanan.Data.Remote;

import com.fryanramzkhar.crudmakanan.Utils.Constant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    Gson gson = new GsonBuilder()
            .setLenient()
            .create();


    private static Retrofit retrofit = null;
    public static Retrofit getClient(){

        // Membuat object HttpLoggingInterceptor
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

        //set Log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Membuat object httpClient
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        // Menambahkan logginginterceptor ke dalam httpClient
        httpClient.addInterceptor(logging);

        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        return retrofit;
    }
}