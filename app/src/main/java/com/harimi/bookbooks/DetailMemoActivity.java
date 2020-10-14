package com.harimi.bookbooks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

public class DetailMemoActivity extends AppCompatActivity {


    private ArrayList<MemoData> arrayList;
    private MemoAdapter memoAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_memo);


        TextView tvdate=(TextView)findViewById(R.id.activity_detail_memo_date_tv);
        TextView tvline=(TextView)findViewById(R.id.activity_detail_memo_line_tv);
        TextView tvpage=(TextView)findViewById(R.id.activity_detail_memo_page_tv);
        TextView tvmemo=(TextView)findViewById(R.id.activity_detail_memo_content_tv);
        final ImageView ivpicture=(ImageView)findViewById(R.id.activity_detail_memo_picture_iv);

        //뷰홀더에서 아이템클릭했을때 인텐트받는거
        Intent intent=getIntent();

        String datetv= intent.getExtras().getString("ItemDate");
        tvdate.setText(datetv);
        String linetv=intent.getExtras().getString("ItemLine");
        tvline.setText(linetv);
        String pagetv=intent.getExtras().getString("ItemPage");
        tvpage.setText(pagetv);
        String memotv=intent.getExtras().getString("ItemMemo");
        tvmemo.setText(memotv);

        final String pictureiv=intent.getExtras().getString("ItemPicture");
        /**이미지 uri String으로 받아온거 다시 uri로 형변환 시켜줘야함*
         *https://stackoverflow.com/questions/32664505/pass-uri-to-another-activity-and-convert-it-to-image?lq=1
         */
        final Uri pictureUri=Uri.parse(pictureiv);
        ivpicture.setImageURI(pictureUri);



//        final Uri pictureUri=Uri.parse(pictureiv);
//        ivpicture.setImageURI(Uri.parse(pictureiv));




       // ivpicture.setImageURI(Uri.parse(pictureiv));
       // Uri pictureUri=Uri.parse(pictureiv);





        /**편집 버튼
         * intent로 입력된 값들 전달
         *
         */
        Button editbtn=(Button)findViewById(R.id.activity_detail_memo_modify_btn);
        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data=new Intent(getApplicationContext(),EditMemoActivity.class);

                TextView date=(TextView)findViewById(R.id.activity_detail_memo_date_tv);
                TextView line=(TextView)findViewById(R.id.activity_detail_memo_line_tv);
                TextView page=(TextView)findViewById(R.id.activity_detail_memo_page_tv);
                TextView memo=(TextView)findViewById(R.id.activity_detail_memo_content_tv);
                ImageView picture=(ImageView)findViewById(R.id.activity_detail_memo_picture_iv);

                data.putExtra("editdate",((TextView)findViewById(R.id.activity_detail_memo_date_tv)).getText().toString());
                data.putExtra("editline",((TextView)findViewById(R.id.activity_detail_memo_line_tv)).getText().toString());
                data.putExtra("editpage",((TextView)findViewById(R.id.activity_detail_memo_page_tv)).getText().toString());
                data.putExtra("editmemo",((TextView)findViewById(R.id.activity_detail_memo_content_tv)).getText().toString());

                data.putExtra("editpicture",pictureUri.toString());


                startActivityForResult(data,2002);

            }
        });





    }//onCreate

    protected void onActivityResult(int requestCode,int resultCode,Intent intent) {

        super.onActivityResult(requestCode, resultCode, intent);
        if(requestCode==2002) {
            if (resultCode != Activity.RESULT_OK) {
                return;
            }

            String date=intent.getExtras().getString("EditDate");
            String page=intent.getExtras().getString("EditPage");
            String line=intent.getExtras().getString("EditLine");
            String memo=intent.getExtras().getString("EditMemo");
            String picture=intent.getStringExtra("EditImageUri");
            Uri pictureUri=Uri.parse(picture);

            TextView tvdate=(TextView)findViewById(R.id.activity_detail_memo_date_tv);
            TextView tvpage=(TextView)findViewById(R.id.activity_detail_memo_page_tv);
            TextView tvline=(TextView)findViewById(R.id.activity_detail_memo_line_tv);
            TextView tvmemo=(TextView)findViewById(R.id.activity_detail_memo_content_tv);
            ImageView ivpicture=(ImageView)findViewById(R.id.activity_detail_memo_picture_iv);

            try{
            tvdate.setText(date);
            tvpage.setText(page);
            tvline.setText(line);
            tvmemo.setText(memo);
            ivpicture.setImageURI(pictureUri);

            }catch (IndexOutOfBoundsException ex){
                ex.printStackTrace();
            }


            MemoData newmemo=new MemoData(date,page,line,picture,memo);


            Log.e("TAG","수정된 데이터 받아왔는지 확인"+newmemo+date+page+line+picture+memo);

            memoAdapter=new MemoAdapter(arrayList);
            Log.e("TAG","수정된 데이터 받아왔는지 확인"+newmemo+date+page+line+picture+memo);

        }
    }


//    public void onBackPressed(){
//
//        Intent intent=new Intent(getApplicationContext(),DetailMemoActivity.class);
//        ArrayList<MemoData> newList=new ArrayList<>();
//        intent.putExtra("수정리스트",newList);
//        startActivity(intent);
//
//    }



//    @Override
//    protected void onPause() {
//        super.onPause();
//
//
//        Intent intent=new Intent(getApplicationContext(),DetailMemoActivity.class);
//        ArrayList<MemoData> newList=new ArrayList<>();
//        intent.putExtra("수정리스트",newList);
//        startActivity(intent);
//
//    }//onPause




}//class