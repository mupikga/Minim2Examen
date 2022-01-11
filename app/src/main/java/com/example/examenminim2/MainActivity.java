package com.example.examenminim2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView user = (TextView) findViewById(R.id.user);

        Button explore = (Button) findViewById(R.id.explore);

        explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = user.getText().toString();

                SharedPreferences sharedPreferences = getSharedPreferences("username", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("User", username);
                editor.commit();

                Intent intent = new Intent (getApplicationContext(), SecondActivity.class);
                startActivity(intent);

            }
        });


    }

}



