package com.example.samplegame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class backGround {
    int x=0,y=0;
    Bitmap backgroundp;
    backGround(int screenx, int screeny, Resources res){
        backgroundp = BitmapFactory.decodeResource(res,R.drawable.background);
        backgroundp = Bitmap.createScaledBitmap(backgroundp,screenx,screeny,false);

    }
}
