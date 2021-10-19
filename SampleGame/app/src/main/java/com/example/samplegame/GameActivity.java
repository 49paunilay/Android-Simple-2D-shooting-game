package com.example.samplegame;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.WindowManager;

public class GameActivity extends AppCompatActivity {
    private gameView mgameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        mgameView = new gameView(this,point.x,point.y);
        setContentView(mgameView);

    }

    @Override
    protected void onPause() {
        super.onPause();
        mgameView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mgameView.resume();
    }
}