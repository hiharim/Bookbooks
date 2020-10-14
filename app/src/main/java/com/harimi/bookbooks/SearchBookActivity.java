package com.harimi.bookbooks;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class SearchBookActivity extends AppCompatActivity {

    private static final int THUMBNAIL_SIZE =1 ;


    StringBuilder searchResult;
    BufferedReader br;
    String[] cover, title, writer, publisher;
    OnlineAdapter mMyAdapter;

    private ListView mListView;
    int itemCount;
    EditText editText;
    Button search_btn;

    ArrayList<OnlineBook> list;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_book);

        context=this;

        mListView = (ListView) findViewById(R.id.listView_online);
        editText=(EditText)findViewById(R.id.edit);
        search_btn=(Button)findViewById(R.id.button);



        final String input=editText.getText().toString();

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchNaver(input);
            }
        });






    }//onCreate




    public void searchNaver(String searchObject) {
        final String clientId = "bGtlY5qefAoYwCZEmgVc";//애플리케이션 클라이언트 아이디값";
        final String clientSecret = "Cax99Yg8FL";//애플리케이션 클라이언트 시크릿값";
        final int display = 10; // 보여지는 검색결과의 수

        final String str=editText.getText().toString();
        final String search=URLEncoder.encode(str);

        // 네트워크 연결은 Thread 생성 필요
        new Thread() {

            @Override
            public void run() {
                try {
                    //String text = URLEncoder.encode(search, "UTF-8");
                    String str=editText.getText().toString();
                    String search=URLEncoder.encode(str);

                    String apiURL = "https://openapi.naver.com/v1/search/book.json?query="+search+ "&display=" + display+ "&start=1";


                    URL url = new URL(apiURL);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.setRequestProperty("X-Naver-Client-Id", clientId);
                    con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
                    con.connect();

                    int responseCode = con.getResponseCode();


                    if (responseCode == 200) { // 정상 호출
                        br = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    } else {  // 에러 발생
                        br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                    }

                    searchResult = new StringBuilder();
                    String inputLine;
                    while ((inputLine = br.readLine()) != null) {
                        searchResult.append(inputLine + "\n");

                    }
                    br.close();
                    con.disconnect();

                    String data = searchResult.toString();

                    String[] array = data.split("\"");

                    cover=new String[display];
                    title = new String[display];
                    writer = new String[display];
                    publisher = new String[display];


                    itemCount = 0;
                    for (int i = 0; i < array.length; i++) {
                        if (array[i].equals("title"))
                            title[itemCount] = array[i + 2];
                        if (array[i].equals("image"))
                            cover[itemCount] = array[i + 2];
                        if (array[i].equals("author"))
                            writer[itemCount] = array[i + 2];
                        if (array[i].equals("publisher")) {
                            publisher[itemCount] = array[i + 2];
                            itemCount++;
                        }
                    }

                    Log.e("확인", "title잘나오니: " + title[0] + title[1] + title[2]);




                    // 결과를 성공적으로 불러오면, UiThread에서 listView에 데이터를 추가
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listViewDataAdd();
                        }
                    });
                } catch (Exception e) {
                    Log.e("에러확인", "error : " + e);
                }

            }
        }.start();

    };


    public void listViewDataAdd() {
        mMyAdapter = new OnlineAdapter();
        // mMyAdapter=new OnlineAdapter(this,R.layout.item_online,list);

        for (int i = 0; i < itemCount; i++) {

            mMyAdapter.addItem(Html.fromHtml(cover[i]).toString(),
                    Html.fromHtml(title[i]).toString(),
                    Html.fromHtml(writer[i]).toString(),
                    Html.fromHtml(publisher[i]).toString());

        }

        // set adapter on listView
        mListView.setAdapter(mMyAdapter);
    }


    private Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }






}//class
