package com.harimi.bookbooks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AddLibraryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_library);


        Button cancelbtn=(Button)findViewById(R.id.activitu_add_library_cancel_btn);
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddLibraryActivity.this, "서재 추가를 취소하였습니다", Toast.LENGTH_SHORT).show();
                finish();
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

    }
}