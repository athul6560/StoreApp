package com.azstudio.storeapp.Retro;

import com.azstudio.storeapp.Utils.constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClientInstance {



    static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    private static Retrofit retrofit = new retrofit2.Retrofit.Builder()
            .baseUrl(constants.BASE_URL)

            .addConverterFactory(GsonConverterFactory.create())
            .build();


    public static <S> S cteateService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
