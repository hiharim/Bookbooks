package com.harimi.bookbooks;


import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.Nullable;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class MusicService extends Service{



    ArrayList<String> arrayList;


    private IBinder mBinder;
    MediaPlayer mediaPlayer;

    private int duration;
    private int currentPosition;
    boolean flag=false;


    // 아래의 getter/setter 메소드는 bind된 activity에서 호출하기 위해서 선언
    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    //thread에 의해 실행되는 코드
    //bind되는 순간부터 run이 실행되면서 매 초마다 현재 position을 설정한다
//    public void run(){
//        while(!flag) {//음원이 끝나면 나가라
//            setCurrentPosition(mediaPlayer.getCurrentPosition());
//            try{
//                Thread.sleep(1000);
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//
//
//        }
//
//    }


    public void onStopMusic(){

        mediaPlayer.stop();
        mediaPlayer.seekTo(0);
    }


    public MusicService(){

    }

    //Activity bind시 Activity에 전달시킬 객체
    class ServiceBinder extends Binder {
        MusicService getService(){
            return MusicService.this;
        }
    }



    @Override
    public void onCreate() {
        super.onCreate();

        mBinder=new ServiceBinder();
        mediaPlayer=MediaPlayer.create(this,R.raw.hookah_bar);
        mediaPlayer.setLooping(false);


    }//onCreate

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        mediaPlayer.start();

        setDuration(mediaPlayer.getDuration());

        /*
         MediaPlayer의 음원 플레이 시간을 얻어내서 service의 데이터를 업데이트 시키는 작업을 하는 thread
         null : 특별히 group값이 필요없음
         this : thread돌릴 source
         player는 이름
         */
//        Thread thread=new Thread(null,this,"player");
//        thread.start(); //bind되는 순간 run()시작됨


        //bind될 객체 리턴
        return mBinder;
    }






    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop(); //서비스 멈춤
        flag=false; //thread가 멈출수 있게 flag값 설정


    }



    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }




}

