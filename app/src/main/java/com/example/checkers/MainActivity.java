package com.example.checkers;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
 ImageButton Player1_VS_Player2;
 ImageButton Player_VS_Computer;
 MediaPlayer mp;
 AdView mAdView;
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MobileAds.initialize(this,new OnInitializationCompleteListener() {
                    @Override
                    public void onInitializationComplete(InitializationStatus initializationStatus) {}
                });
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        mp  = MediaPlayer.create(this, R.raw.mm);
        Player1_VS_Player2= findViewById(R.id.Player1_VS_Player2);
        Player_VS_Computer= findViewById(R.id.Player_VS_Computer);
        Player1_VS_Player2.setOnClickListener(this);
        Player_VS_Computer.setOnClickListener(this);

        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-7979606640397319/1413895114");
        AdSize adSize = new AdSize(320, 50);
       // mAdView = findViewById(R.id.adView);
      //  AdRequest adRequest = new Builder().build();
        //mAdView.loadAd(adRequest);

    }


    @Override
    public void onClick(View v) {
        if(v==Player1_VS_Player2){
            mp.start();
            open_Player1_VS_Player2();
        }
        if(v==Player_VS_Computer){
            mp.start();
            open_Player_VS_Computer();
        }
    }

    private void open_Player1_VS_Player2() {
        Intent intent=new Intent(this,Board.class);
        startActivity(intent);
     }
    private void open_Player_VS_Computer() {
        Intent intent1=new Intent(this,level.class);
        startActivity(intent1);
    }
}