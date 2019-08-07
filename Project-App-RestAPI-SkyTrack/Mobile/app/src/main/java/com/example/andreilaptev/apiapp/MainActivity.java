package com.example.andreilaptev.apiapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
/*Developed by Manoel B. Burgos Filho*/
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
   public void StartButton(View v){
        startActivity(new Intent(this, SelectAirportActivity.class));
    }
    public void backgroud_service (View v){
        if (v.getId()==R.id.btnservice_start){
            startService(new Intent (getBaseContext(),BackGroudService.class));
        }
        else if (v.getId()==R.id.btnservice_stop)
        {
            stopService(new Intent (getBaseContext(),BackGroudService.class));
        }
    }
}
