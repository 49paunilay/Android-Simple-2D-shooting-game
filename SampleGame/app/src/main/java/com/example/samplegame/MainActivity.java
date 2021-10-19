package com.example.samplegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private boolean isMute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        findViewById(R.id.playButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,GameActivity.class));
            }
        });
        TextView highScoreText = findViewById(R.id.textView2);
        ImageView imageView = findViewById(R.id.volumeControl);
        SharedPreferences prefs = getSharedPreferences("game",MODE_PRIVATE);
        highScoreText.setText("HighScore : "+prefs.getInt("highscore",0));

        if(isMute){
            imageView.setImageResource(R.drawable.ic_baseline_volume_off_24);
        }else{
            imageView.setImageResource(R.drawable.ic_baseline_volume_up_24);
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isMute=!isMute;
                if(isMute){
                    imageView.setImageResource(R.drawable.ic_baseline_volume_off_24);
                }else{
                    imageView.setImageResource(R.drawable.ic_baseline_volume_up_24);
                }
            }
        });
    }
}