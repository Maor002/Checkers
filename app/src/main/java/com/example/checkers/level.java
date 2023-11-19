package com.example.checkers;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class level extends AppCompatActivity implements View.OnClickListener {
    ImageButton easy;
    ImageButton Normal;
    ImageButton Hard;
    MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        mp  = MediaPlayer.create(this, R.raw.mm);
        easy=(ImageButton)findViewById(R.id.easy);
        Normal=(ImageButton)findViewById(R.id.Normal);
        Hard=(ImageButton)findViewById(R.id.Hard);
        easy.setOnClickListener(this);
        Normal.setOnClickListener(this);
        Hard.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if(v==easy){
            mp.start();
            Board_2.level=1;
        Board_2.time=500;
        }
        if(v==Normal){
            mp.start();
            Board_2.level=3;
            Board_2.time=450;}
        if(v==Hard){
            mp.start();
            Board_2.level=5;
            Board_2.time=5;}
        open_Player_VS_Computer();
    }
    private void open_Player_VS_Computer() {
        Intent intent=new Intent(this,Board_2.class);
        startActivity(intent);
    }
}