package com.example.samplegame;

import static com.example.samplegame.gameView.screenRatioY;
import static com.example.samplegame.gameView.screenRationx;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Flight {
    public boolean isGointUp=false;
    public int toShoot=0,shootcounter=1;
    int x,y,width,height,wingCounter = 0;
    Bitmap flight1,flight2,dead;
    Bitmap shoot1,shoot2,shoot3,shoot4,shoot5;
    private gameView gview;
    Flight(gameView gview,int screeny, Resources res){
        this.gview = gview;
        flight1 = BitmapFactory.decodeResource(res,R.drawable.fly1);
        flight2 = BitmapFactory.decodeResource(res,R.drawable.fly2);
        dead = BitmapFactory.decodeResource(res,R.drawable.dead);
        width=flight1.getWidth();
        height = flight1.getHeight();
        width = width/4;
        height=height/4;
        width = (int) (width*screenRationx);
        height = (int) (height*screenRatioY);
        flight1 = Bitmap.createScaledBitmap(flight1,width,height,false);
        flight2= Bitmap.createScaledBitmap(flight2,width,height,false);
        shoot1 = BitmapFactory.decodeResource(res,R.drawable.shoot1);
        shoot2 = BitmapFactory.decodeResource(res,R.drawable.shoot2);
        shoot3 = BitmapFactory.decodeResource(res,R.drawable.shoot3);
        shoot4 = BitmapFactory.decodeResource(res,R.drawable.shoot4);
        shoot5 = BitmapFactory.decodeResource(res,R.drawable.shoot5);

        shoot1 = Bitmap.createScaledBitmap(shoot1,width,height,false);
        shoot2 = Bitmap.createScaledBitmap(shoot2,width,height,false);
        shoot3 = Bitmap.createScaledBitmap(shoot3,width,height,false);
        shoot4 = Bitmap.createScaledBitmap(shoot4,width,height,false);
        shoot5 = Bitmap.createScaledBitmap(shoot5,width,height,false);
        dead=Bitmap.createScaledBitmap(dead,width,height,false);

        y = screeny/2;
        x = (int) (64*screenRationx);

    }
    Bitmap getFlight(){
        if(toShoot!=0){
            if(shootcounter==1){
                shootcounter+=1;
                return shoot1;
            }
            if(shootcounter==2){
                shootcounter+=1;
                return shoot2;
            }
            if(shootcounter==3){
                shootcounter+=1;
                return shoot3;
            }
            if(shootcounter==4){
                shootcounter+=1;
                return shoot4;
            }
            shootcounter=1;
            toShoot--;
            gview.newBullet();
            return shoot5;
        }
        if(wingCounter==0){
            wingCounter++;
            return flight1;
        }
        wingCounter--;
        return flight2;
    }
    Rect getcollisionSheape(){
        return new Rect(x,y,x+width,y+height);
    }
    Bitmap getDead(){
        return dead;
    }
}
