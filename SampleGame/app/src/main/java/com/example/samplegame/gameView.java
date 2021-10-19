package com.example.samplegame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class gameView extends SurfaceView implements Runnable {
    private Thread mythread;
    private Birds[] birds;
    private boolean isPlaying;
    private backGround backGround1,backGround2;
    private int screenX,screenY;
    private Paint paint;
    private Flight flight;
    public static float screenRationx,screenRatioY;
    private Random random;
    private boolean isGameOver=false;
    private int score=0;
    private SharedPreferences preferences;
    private Activity activity;
//    private SoundPool soundPool;
//    private int sound;

    public gameView(GameActivity activity,int screenX,int screenY) {
        super(activity);
        this.activity=activity;
        preferences=activity.getSharedPreferences("game",Context.MODE_PRIVATE);
        this.screenX=screenX;
        this.screenY=screenY;
//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
//            AudioAttributes audioAttributes = new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).setUsage(AudioAttributes.USAGE_GAME).build();
//            soundPool = new SoundPool.Builder().setAudioAttributes(audioAttributes).build();
//
//        }else{
//            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC,0);
//        }

        screenRationx=1920f/screenX;
        screenRatioY = 1080f/screenY;
        backGround1 = new backGround(screenX,screenX,getResources());
        backGround2 = new backGround(screenX,screenY,getResources());
        flight = new Flight(this,screenY,getResources());
        backGround2.x = screenX;
        paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.WHITE);
        birds = new Birds[4];
        for(int i=0;i<4;i++){
            Birds b = new Birds(getResources());
            System.out.println(b.x+"is the position'");
            birds[i]=b;
        }
        random=new Random();
    }

    @Override
    public void run() {
        while (isPlaying){
            Update();
            draw();
            sleep();
        }
    }
    private void Update() {
        backGround1.x-=10*screenRationx;
        backGround2.x-=10*screenRationx;
        if(backGround1.x+backGround1.backgroundp.getWidth()<0){
            backGround1.x=screenX;
        }
        if(backGround2.x+backGround2.backgroundp.getWidth()<0){
            backGround2.x=screenX;
        }
        if(flight.isGointUp){
            flight.y-=30*screenRatioY;
        }else{
            flight.y+=30*screenRatioY;
        }
        if(flight.y<0){
            flight.y=0;
        }
        if(flight.y>screenY-flight.height){
            flight.y=screenY-flight.height;
        }
        List<Bullet> trash = new ArrayList<>();
        for(Bullet bullet:bullets){
            if(bullet.x>screenX){
                trash.add(bullet);
            }
            bullet.x +=50*screenRationx;
            for(Birds b:birds){
                if(Rect.intersects(b.getcollisionSheape(),bullet.getcollisionSheape())){
                    score+=10;
                    b.x=-500;
                    bullet.x = screenX+500;
                    b.wasShot=true;
                }
            }
        }
        for(Bullet b : trash){
            bullets.remove(b);
        }
        for(Birds bird:birds){
            bird.x -= bird.speed;
            if(bird.x+bird.width<0){
                if(!bird.wasShot){
                    isGameOver = true;
                    return;
                }
                int bound = (int) (30*screenRationx);
                bird.speed = random.nextInt(bound);
                if(bird.speed<10*screenRationx)
                    bird.speed= (int) (10*screenRationx);
                bird.x=screenX;
                bird.y=random.nextInt(screenY-bird.height);
                bird.wasShot=false;
            }
            if(Rect.intersects(bird.getcollisionSheape(),flight.getcollisionSheape())){
                isGameOver = true;
                return;
            }
        }
    }
    private void draw(){
        if(getHolder().getSurface().isValid()){
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(backGround1.backgroundp,backGround1.x,backGround1.y,paint);
            canvas.drawBitmap(backGround2.backgroundp,backGround2.x,backGround2.y,paint);
            for(Birds bird:birds){
                canvas.drawBitmap(bird.getBird(),bird.x,bird.y,paint);
            }
            canvas.drawText(score+" ",screenX/2f,165,paint);

            if(isGameOver){
                isPlaying=false;
                canvas.drawBitmap(flight.getDead(),flight.x,flight.y,paint);
                saveHighScore();
                getHolder().unlockCanvasAndPost(canvas);
                WaitBeforeExiting();
                return;
            }

            canvas.drawBitmap(flight.getFlight(),flight.x,flight.y,paint);
            for(Bullet b:bullets){
                canvas.drawBitmap(b.bullet,b.x,b.y,paint);
            }

            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void WaitBeforeExiting() {
        try {
            Thread.sleep(3000);
            activity.startActivity(new Intent(activity,MainActivity.class));
            activity.finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void saveHighScore() {
        if(preferences.getInt("highscore",0)<score){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("highscore",score);
            editor.apply();

        }
    }

    private void sleep(){
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume(){
        isPlaying = true;
        mythread = new Thread(this);
        mythread.start();
    }
    public void pause(){
        try {
            isPlaying=false;
            mythread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(event.getX()<screenX/2){
                    flight.isGointUp = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                flight.isGointUp=false;
                if(event.getX()>screenX/2){
                    flight.toShoot+=1;
                }
                break;

        }
        return true;
    }
    List<Bullet> bullets = new ArrayList<>();
    public void newBullet() {
        Bullet b = new Bullet(getResources());
        b.x=flight.x+flight.width;
        b.y=flight.y+flight.height/2;
        bullets.add(b);
    }
}
