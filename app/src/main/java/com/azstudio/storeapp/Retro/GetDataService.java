package com.azstudio.storeapp.Retro;

import com.azstudio.storeapp.Models.Product;
import com.azstudio.storeapp.Models.orderDetails;
import com.azstudio.storeapp.Models.product_outer_model;
import com.azstudio.storeapp.Models.responseModel;
import com.google.gson.JsonObject;


import java.util.List;

import retrofit2.Call;


import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GetDataService {
    @GET("products")
    Call<product_outer_model> getAllProducts();

    @FormUrlEncoded
    @POST("sentmail")
    Call<responseModel> sentmail(@Field("email") String user_email);

    @FormUrlEncoded
    @POST("login")
    Call<responseModel> login(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("pinverify")
    Call<responseModel> pinverify(@Field("email") String user_email, @Field("pinnumber") String pin);

    @FormUrlEncoded
    @POST("user")
    Call<responseModel> adduser(@Field("user_name") String username, @Field("user_email") String email, @Field("user_password") String password, @Field("user_role") String role);


    @POST("order")
    Call<List<orderDetails>> addorder(@Body List<orderDetails> obj);

    @FormUrlEncoded
    @POST("sentsms")
    Call<String> sentsms(@Field("number") String number, @Field("product") String product);

    @GET("order/{email}")
    Call<List<orderDetails>> getorder(@Path("email") String email);

    @GET("getproduct/{title}")
    Call<Product> getproduct(@Path("title") String title);
}
