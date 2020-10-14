package com.harimi.bookbooks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 SplashActivity
 앱 실행했을때 뜨는 Splash화면을 실행하는 클래스
 */

public class SplashActivity extends AppCompatActivity {

    /*
    단독 사용한 Handler : 기본 생성자를 통해 Handler생성-생성되는 Handler는 해당 Handler를 호출한 스레드의
    MessageQueue와 Looper에 자동 연결된다
    */
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        /*
         handler의 postDelayed()함수를 통해 1초 딜레이 하고
          지정된 1초가 끝나면 intent를 통해 LoginActivity로 이동하게 한다
          LoginActivity로 이동하고 뒤로가기했을때 다시 SplashActivity화면을 띄우지 않기 위해서 finish()해준다
        */
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1000);


    }
}