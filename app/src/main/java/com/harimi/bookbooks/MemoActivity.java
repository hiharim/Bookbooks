package com.harimi.bookbooks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);


//        Intent intent=getIntent();
//        //날짜
//        TextView tvDate=(TextView)findViewById(R.id.activity_memo_date_tv);
//        String date=intent.getStringExtra("date");
//        tvDate.setText(date);
//
//        //페이지번호
//        TextView tvPage=(TextView)findViewById(R.id.activity_memo_page_tv);
//        String page=intent.getStringExtra("page_number");
//        tvPage.setText(page);
//
//
//        //밑줄
//        TextView tvLine=(TextView)findViewById(R.id.activity_memo_line_tv);
//        String line=intent.getStringExtra("line");
//        tvLine.setText(line);
//
//
//        //메모
//        TextView tvMemo=(TextView)findViewById(R.id.activity_memo_content_tv);
//        String memo=intent.getStringExtra("memo");
//        tvMemo.setText(memo);

        //이미지







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


        //통계버튼
        Button statbtn=(Button)findViewById(R.id.status_statistic);
        statbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),StatisticActivity.class);
                startActivity(intent);

            }
        });

        //메모버튼
        Button memobtn=(Button)findViewById(R.id.status_memo);
        memobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),MemoActivity.class);
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