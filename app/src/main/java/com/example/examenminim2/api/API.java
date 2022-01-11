package com.example.examenminim2.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface API {
    public static final String BASE_URL = "https://api.github.com/";

    //repos
    @GET("users/{username}/repos")
    Call<List<Repos>> getRepos(@Path("username") String name);

    //User
    @GET("users/{username}")
    Call<Users> getInfo(@Path("username") String name);

}
