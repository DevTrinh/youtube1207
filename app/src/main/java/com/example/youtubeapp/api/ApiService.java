package com.example.youtubeapp.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Currency;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public interface ApiService {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiService apiService = new Retrofit.Builder()
            .baseUrl("https://youtube.googleapis.com/youtube/v3/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

}
