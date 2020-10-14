package com.harimi.bookbooks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
  @SettingActivity
  @brief 로그아웃
  @details
 */
public class SettingActivity extends AppCompatActivity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        context=this;

        final RbPreference pref=new RbPreference(this);

        //로그아웃버튼
        Button logoutbtn=findViewById(R.id.activity_setting_logout_btn);
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            pref.clear(getApplicationContext());

            Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);

            startActivity(intent);
            Toast.makeText(getApplicationContext(),"로그아웃되었습니다",Toast.LENGTH_SHORT).show();

            }
        });


        Button stopwatch=(Button)findViewById(R.id.activity_setting_stopwatch_btn);
        stopwatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), StopWatchActivity.class);
                startActivity(intent);


            }
        });


        Button music=(Button)findViewById(R.id.activity_setting_music_btn);
        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getApplicationContext(),MusicActivity.class);
                startActivity(intent);
            }
        });













        /**StatusBar버튼*/
        //홈버튼
        Button homebtn=(Button)findViewById(R.id.status_home);
        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);
            }
        });

//        //통계버튼
//        Button statbtn=(Button)findViewById(R.id.status_statistic);
//        statbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(getApplicationContext(),StatisticActivity.class);
//                startActivity(intent);
//
//            }
//        });

        //메모버튼
//        Button memobtn=(Button)findViewById(R.id.status_memo);
//        memobtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(getApplicationContext(),MemoActivity.class);
//                startActivity(intent);
//
//            }
//        });

        //사전버튼
        Button memobtn = (Button) findViewById(R.id.status_dictionary);
        memobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DictionaryActivity.class);
                startActivity(intent);
            }
        });

        //설정버튼
        Button setbtn=(Button)findViewById(R.id.status_setting);
        setbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),SettingActivity.class);
                startActivity(intent);

            }
        });

    }//onCreate

}//class