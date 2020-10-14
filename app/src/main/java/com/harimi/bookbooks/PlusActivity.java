package com.harimi.bookbooks;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

//네이버로 검색한 책 추가하는 클래스
//어댑터에서 리스트아이템클릭했을때 책추가하는액티비티에 책 이미지,제목,지은이,출판사 값이 넘어와서 set되야한다
public class PlusActivity extends AppCompatActivity {

    PlusActivity plusActivity=this;

    ImageView coverImage;
    TextView title,writer,publisher;



    RatingBar ratingBar;
    TextView ratingText;
    Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener myDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };


    private void updateLabel() {
        String myFormat = "yyyy년 MM월 dd일";    // 출력형식   2018년 11월 28일
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        EditText et_date = (EditText) findViewById(R.id.activity_plus_start_et);
        et_date.setText(sdf.format(myCalendar.getTime()));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plus);

        coverImage=(ImageView)findViewById(R.id.activity_plus_plus_iv);
        title=(TextView)findViewById(R.id.activity_plus_title_et);
        writer=(TextView)findViewById(R.id.activity_plus_writer_et);
        publisher=(TextView)findViewById(R.id.activity_plus_publisher_et);


        //책 검색 결과 리스트아이템 OnlineAdapter.class 에서 intent로 값을받는다
        Intent intent=getIntent();

        final String n_cover=intent.getExtras().getString("NaverCover");
//        Uri coverUri=Uri.parse(n_cover);
//        coverImage.setImageURI(coverUri);

        //이미지 로딩 라이브러리 Glide
        Glide.with(this).load(n_cover).into(coverImage);

        String n_title=intent.getExtras().getString("NaverTitle");
        title.setText(n_title);

        String n_writer=intent.getExtras().getString("NaverWriter");
        writer.setText(n_writer);

        String n_publisher=intent.getExtras().getString("NaverPublisher");
        publisher.setText(n_publisher);






        ratingText=(TextView)findViewById(R.id.activity_plus_rating_tv);
        ratingBar=(RatingBar)findViewById(R.id.activity_plus_ratingbar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingBar.setRating(rating);

            }
        });


        //독서 날짜 선택하는 다이얼로그
        EditText startDate = (EditText) findViewById(R.id.activity_plus_start_et);
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(PlusActivity.this, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



        /*
        온라인 책 추가 저장 버튼
        HomeActivity로 인텐트 보낸다
         */
        Button finish_btn=(Button)findViewById(R.id.activity_plus_finish_btn);
        finish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent=new Intent(getApplicationContext(),HomeActivity.class);

                ImageView cover=(ImageView)findViewById(R.id.activity_plus_plus_iv);

                intent.putExtra("PlusCover",Uri.parse(n_cover).toString());


               // intent.putExtra("PlusCover",Glide.with(PlusActivity.this).load(n_cover).toString());
               // intent.putExtra("putcover",mCurrentPhotoPath);
                //intent.putExtra("PlusCover",Glide.with(cover).load(coverImage).toString());
                //intent.putExtra("PlusCover",Glide.with(cover).load(n_cover).toString());
//                intent.putExtra("PlusCover",Glide.with(PlusActivity.this).load(cover).toString());

                EditText title=(EditText)findViewById(R.id.activity_plus_title_et);
                intent.putExtra("PlusTitle",title.getText().toString());
                EditText writer=(EditText)findViewById(R.id.activity_plus_writer_et);
                intent.putExtra("PlusWriter",writer.getText().toString());
                EditText publisher=(EditText)findViewById(R.id.activity_plus_publisher_et);
                intent.putExtra("PlusPublisher",publisher.getText().toString());
                EditText readdate=(EditText)findViewById(R.id.activity_plus_start_et);
                intent.putExtra("PlusDate",readdate.getText().toString());
                RatingBar ratingBar=(RatingBar)findViewById(R.id.activity_plus_ratingbar);
                intent.putExtra("PlusStar",ratingBar.getRating());




                setResult(RESULT_OK,intent);

                Log.e("책추가 액티비티","보낸다"+title.getText().toString()+writer.getText().toString()+publisher.getText().toString());

                finish();



            }
        });





        Button cancelbtn=(Button)findViewById(R.id.activity_plus_cancel_btn);
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PlusActivity.this, "책추가를 취소하였습니다", Toast.LENGTH_SHORT).show();
                finish();
            }
        });



    }//onCreate



}//class
