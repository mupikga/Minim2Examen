package com.example.examenminim2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.examenminim2.api.API;
import com.example.examenminim2.api.Repos;
import com.example.examenminim2.api.Users;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        getUser();
        getRepos();

        Button returnBtn = (Button) findViewById(R.id.returnBtn2);

        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void getUser() {

        SharedPreferences sharedPref = getSharedPreferences("username", Context.MODE_PRIVATE);
        String username = sharedPref.getString("User", null);

        TextView followers = (TextView) findViewById(R.id.followers);
        TextView following = (TextView) findViewById(R.id.following);
        TextView repos = (TextView) findViewById(R.id.repositories);
        TextView userTV = (TextView) findViewById(R.id.userName);

        ImageView avatar = (ImageView) findViewById(R.id.avatar);

        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(API.BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
        API gerritAPI = retrofit.create(API.class);
        Call<Users> call = gerritAPI.getInfo(username);

        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if(response.isSuccessful()) {
                    Users user = response.body();
                    String f = "Followers: " + user.getFollowers();
                    String r = "Repositories: " + user.getRepos();
                    String f2 = "Following: " + user.getFollowing();
                    userTV.setText(username);
                    followers.setText(f);
                    repos.setText(r);
                    following.setText(f2);
                    Picasso.get().load(user.getAvatar()).into(avatar);
                }else{
                    Log.i("User info failure", "Non existent user");
                    Intent intent = new Intent (getApplicationContext(),ErrorUser.class);
                    startActivity(intent);
                }
            }
            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Log.i("User info failure", t.toString());
                Intent intent = new Intent (getApplicationContext(), ErrorUser.class);
                startActivity(intent);
            }
        });
    }

    public void getRepos(){

        SharedPreferences sharedPref = getSharedPreferences("username", Context.MODE_PRIVATE);
        String username = sharedPref.getString("User",null);

        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(API.BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
        API gerritAPI = retrofit.create(API.class);
        Call<List<Repos>> call = gerritAPI.getRepos(username);

        call.enqueue(new Callback<List<Repos>>() {

            @Override
            public void onResponse(Call<List<Repos>> call, Response<List<Repos>> response) {

                    List<Repos> reposList = response.body();
                    ListAdapter listAdapter = new ListAdapter(reposList, SecondActivity.this);
                    RecyclerView recyclerView = findViewById(R.id.ListRecyclerView);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(SecondActivity.this));
                    recyclerView.setAdapter(listAdapter);
            }

            @Override
            public void onFailure(Call<List<Repos>> call, Throwable t) {
                Log.i("Repos Failure", t.toString());
                Intent intent = new Intent (getApplicationContext(), ErrorRepos.class);
                startActivity(intent);
            }

        });

    }
}



