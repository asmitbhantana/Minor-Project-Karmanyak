package org.vansoft.karmanyak.utils;

import org.vansoft.karmanyak.model.Event;
import org.vansoft.karmanyak.model.LoginResponse;
import org.vansoft.karmanyak.model.User;
import org.vansoft.karmanyak.model.UserRegisterResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public class Const {
    public static String url = "http://192.168.0.112:8080/";
    public static final String userLogin = "api/users/login";
    public static final String userRegistrationUrl = "api/users/register";
    public static final String alleventUrl = "api/event/getevent";
//    public static final String balanceUrl = "";
//    public static final String transferUrl = "";
    public static final String verification = "api/qrverification/verification";
    public static final String transactionUrl = "api/solidity/transfer";



    public static ApiService apiService = null;

    public static ApiService getApiService(){
        if(apiService==null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            apiService = retrofit.create(ApiService.class);
        }
        return apiService;
    }

    public interface ApiService{
        @GET(alleventUrl)
        Call<List<Event>> getEvents();

        @POST(userRegistrationUrl)
        Call<UserRegisterResponse> registerUser(@Body User user);

        @POST(userLogin)
        Call<LoginResponse> loginUser(@Body User user);


    }



}
