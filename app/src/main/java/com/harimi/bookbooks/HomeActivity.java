package com.harimi.bookbooks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.content.ContentValues.TAG;

/**
 * @HomeActivity
 * @brief listView생성
 * @details
 * */

public class HomeActivity extends AppCompatActivity {

   //HomeActivity homeActivity = this;

   // private static final String BOOK_JSON="book_json";

    // ArrayList -> Json으로 변환
   // private static final String SETTINGS_PLAYER_JSON = "settings_item_json";

   // private static final String PLEASE_JSON="some_name";




    /*
    로그인 아이디별로 sharedPreferences 책 리스트 다르게 출력하기 위한 String 변수
     */
    private static String SPECIFIC_JSON="";

    private final int CALLER_REQUEST=1;
    private final int REVISION_LIST=2;

    public static Context context;
    //selectedList : 선택된 리스트 아이템
    public int selectedList;

    BookData bookData;
    ArrayList<BookData> bookList;
    BookAdapter adapter;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        context=this;

        //액션바 타이틀변경
        //getSupportActionBar().setTitle("");
        //액션바 배경색 바꾸기
        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF339999));


        /*
         로그인할때 intent로 보낸 아이디 받는 코드
         SPECIPIC_JSON이라는 변수를 sharedPreferences의 key로 사용한다
         key를 userEmail로 설정함으로써 유저마다 sharedPreferences를 따로 관리하기 위함이다
         */
//        Intent intent=getIntent();
//        SPECIFIC_JSON=intent.getExtras().getString("userEmail");


        RbPreference pref=new RbPreference(this);

        //pref.put("loginemail",SPECIFIC_JSON);
        //pref.put("totalBooks",bookList.size());


        /*
         account : 사용자가 누군지 담을 변수, 로그인할때 RbPreference에 담아준 사용자 email값을 불러온다
         */
        String account=pref.getValue("loginemail",null);
        /*
        SPECIFIC_JSON : 사용자 bookList를 sharedPreferences에 저장하기 위한 key값
        사용자마다 bookList를 따로 관리해야하기 때문에 SPECIFIC_JSON에 사용자 이메일을 설정해서 key값으로 쓴다
         */
        SPECIFIC_JSON=pref.getValue("loginemail",null);
        Log.e("HomeActivity","RbPreference저장된 값"+account+ SPECIFIC_JSON);





        //만약 저장되어있는 책 리스트가 없다면 bookList객체를 생성한다
        if (null == bookList) {
            bookList = new ArrayList<BookData>();
        }

        //전에 저장했던 데이터 onCreate할때 불러오는 코드
        try {

           // ArrayList<String> list = getStringArrayPref(SETTINGS_PLAYER_JSON);
            bookList=loadFromStorage();
            int size=bookList.size();
            pref.put("totalBooks",size);


            Log.e("홈액티비티","RbPreference에 저장"+size);
            Log.e("홈액티비티 onCreate","불러온다"+bookList);


        }catch (Exception e){
            e.printStackTrace();
        }





//        SearchView searchView =findViewById(R.id.activity_home_sv);
//        searchView.setQueryHint("책 검색하기");
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//
//                HomeActivity.this.adapter.getFilter().filter(s);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                HomeActivity.this.adapter.getFilter().filter(newText);
//                return false;
//            }
//        });








//        EditText et_filter=findViewById(R.id.activity_home_filter);
//        et_filter.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable edit) {
//
//                String filterText=edit.toString();
//                if(filterText.length()>0){
//                    //listView.setFilterText(filterText);
//                    ((BookAdapter)listView.getAdapter()).getFilter().filter(filterText) ;
//                }else{
//                    listView.clearTextFilter();
//                    //todo et 0이면 올리스트
//                }
//
//
//            }
//        });




        //bookList=new ArrayList<>();

        /*
        adapter 생성후 layout이랑 배열 연결
        adapter : adapter 설정, BookListViewAdapter에 layout.item와 bookList연결
         */
        adapter=new BookAdapter(this,R.layout.item_book,bookList);
        listView=findViewById(R.id.list);

        //adpater 생성후 listView와 연결
        listView.setAdapter(adapter);






        //setOnItemClickListener : 리스트 아이템 클릭시 발생하는 이벤트
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//
//                BookData bookData=bookList.get(position);
//
//                Intent intent=new Intent(HomeActivity.this,DetailBookActivity.class);
//
//                 /*
//                intent로 데이터 전송하여 이동
//                DetailBookActivity에서 받은 books key값으로 현재 bookList의 position값 전달
//                */
//
//                //intent.putExtra("Clickbooks",bookList.get(position));
//
//                intent.putExtra("Clickcover",bookData.getCover());
//                intent.putExtra("Clicktitle",bookData.getTitle());
//                intent.putExtra("Clickwriter",bookData.getWriter());
//                intent.putExtra("Clickpublisher",bookData.getPublisher());
//                intent.putExtra("Clickdate",bookData.getReaddate());
//                intent.putExtra("Clickstar",bookData.getStar());
//
//                /*
//                selectiedList : selectiedList변수에 현재 클릭된 position 값 저장
//                onActivityResult : onActivityReuslt시에 변경사항 수정시 사용
//                */
//                //selectedList=position;
//
//                startActivityForResult(intent,999);
//                //startActivity(intent);
//
//                Log.e("홈액티비티" ,"아이템클릭"+bookData.getCover()+bookData.getTitle()+bookData.getWriter());
//            }
//        });



//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent data=new Intent(getApplicationContext(),DetailBookActivity.class);
//                data.putExtra("USER_ACCOUNT",SPECIFIC_JSON);
//                Log.e("리스트뷰클릭","USER_ACCOUNT보낸다"+SPECIFIC_JSON);
//
//                startActivity(data);
//            }
//        });







//      롱클릭 삭제
//        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view,
//                                           int position, long id) {
//                bookList.remove(position);
//                adapter.notifyDataSetChanged();
//                // 이벤트 처리 종료 , 여기만 리스너 적용시키고 싶으면 true , 아니면 false
//                return true;
//            }
//        });


//        listView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//
//                check_position = listView.getCheckedItemPosition();   //리스트뷰의 포지션을 가져옴.
//
//                return true;
//            }
//        });








        /**StatusBar*/
        //통계버튼
//        Button statbtn = (Button) findViewById(R.id.status_statistic);
//        statbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), StatisticActivity.class);
//                startActivity(intent);
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
        Button setbtn = (Button) findViewById(R.id.status_setting);
        setbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);
            }
        });


        //ListView를 Context 메뉴로 등록
       registerForContextMenu(listView);


    }//onCreate




    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        super.onActivityResult(requestCode, resultCode, intent);
        //requestCode : requestCode가 CALLER_REQUEST일 경우, 새로운 데이터를 입력하는 경우
        if(requestCode==1996) {
            if (resultCode != Activity.RESULT_OK) {
                return;
            }
            //get("KEY") 값으로 해당 값 변수에 저장하기
            String sendcover = intent.getExtras().get("putcover").toString();
            String sendtitle = intent.getExtras().get("puttitle").toString();
            String sendwriter = intent.getExtras().get("putwriter").toString();
            String sendpublisher = intent.getExtras().get("putpublisher").toString();
            String senddate = intent.getExtras().get("putdate").toString();
            // String sendstar=intent.getExtras().get("putstar").toString();
            float sendstar = intent.getFloatExtra("putstar", 0.0f);

            Log.e("메인", "받는다 (key값으로 값 받음 받은값-) " + sendtitle + sendwriter + sendpublisher + sendstar);

            //KEY값으로 받아서 저장된 변수 bookData에 저장
            BookData newbook = new BookData(sendcover, sendtitle, sendwriter, sendpublisher, senddate, sendstar);

            Log.e("메인", "받는다 (arraylist에 데이터가 들어가기 직전) " + sendtitle + sendwriter + sendpublisher + sendstar);
            /*데이터는 서로 잘주고받음...arraylist에 데이터가 잘못들어가고있다...
              arraylist에 데이터가 들어가기 직전에 로그를 찍어봐라
             */

            //bookList에 bookData에 저장
            //bookList.add(newbook);
            Log.e("메인", "받는다 (arraylist에 데이터가 저장한 후) " + sendtitle + sendwriter + sendpublisher + sendstar);

            //json연습
            //adapter.addItem(newbook);

            //  \"  이스케이프 시퀀스
//            String temp="{\"sendcover\""+":"+"\""+intent.getExtras().get("putcover").toString()+
//                    "\""+ ","+"\"sendtitle\""+":"+ "\""+intent.getExtras().get("puttitle").toString()+
//                    "\""+ ","+"\"sendwriter\""+":"+ "\""+intent.getExtras().get("putwriter").toString()+
//                    "\""+ ","+"\"sendpublisher\""+":"+ "\""+intent.getExtras().get("putpublisher").toString()+
//                    "\""+ ","+"\"senddate\""+":"+ "\""+intent.getExtras().get("putdate").toString()+
//                    "\""+ ","+"\"sendstar\""+":"+ "\""+intent.getExtras().get("putstar").toString()+ "\"" + "}";
//
//            array.add(temp);
//
//            Log.e("onAcitivityResult","저장"+temp);

            bookList.add(0, newbook);


            //adapter에 리스트 갱신
            adapter.notifyDataSetChanged();

            Log.e("메인", "받는다 (adpater에 리스트 갱신 후) " + sendtitle + sendwriter + sendpublisher + sendstar);


            //온라인 책추가 PlusActivity에서 데이터 받는코드
        }else if(requestCode==20000){
            if (resultCode != Activity.RESULT_OK) {
                return;
            }

            //get("KEY") 값으로 해당 값 변수에 저장하기
            String addCover = intent.getExtras().get("PlusCover").toString();
//
//            try {
//                Bitmap bitmap= BitmapFactory.decodeStream(getContentResolver().openInputStream(Uri.parse(addCover)));
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }

//            try ( InputStream is = new URL(addCover ).openStream() ) {
//                Bitmap bitmap = BitmapFactory.decodeStream( is );
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }


            String addTitle = intent.getExtras().get("PlusTitle").toString();
            String addWriter = intent.getExtras().get("PlusWriter").toString();
            String addPublisher = intent.getExtras().get("PlusPublisher").toString();
            String addDate = intent.getExtras().get("PlusDate").toString();
            float addStar = intent.getFloatExtra("PlusStar", 0.0f);

            Log.e("메인", "온라인책추가 (key값으로 값 받음 받은값-) " + addTitle + addWriter + addPublisher + addStar);

            //KEY값으로 받아서 저장된 변수 bookData에 저장
            BookData newbook = new BookData(addCover,addTitle,addWriter,addPublisher,addDate,addStar);

            Log.e("메인", "온라인책추가 받는다 (arraylist에 데이터가 저장한 후) "+ addTitle + addWriter + addPublisher + addStar);

            //bookList에 bookData에 저장
            bookList.add(0, newbook);
            //adapter에 리스트 갱신
            adapter.notifyDataSetChanged();

            Log.e("메인", "온라인책추가받는다 (adpater에 리스트 갱신 후) " + addTitle + addWriter + addPublisher + addStar);


            //책 정보 수정한 데이터 받는 코드
        } else {
            if (resultCode != Activity.RESULT_OK) {
                return;
            }

            //todo 변수명명법 두단어이어있을때 _, or 대문자로표기하기

            String finishcover = intent.getExtras().get("편집완료ImageUri").toString();

            String finishtitle=intent.getExtras().get("편집완료title").toString();
            String finishwriter=intent.getExtras().get("편집완료writer").toString();
            String finishpublisher=intent.getExtras().get("편집완료publisher").toString();
            String finishdate=intent.getExtras().get("편집완료readdate").toString();
            float finishstar=intent.getFloatExtra("편집완료star",0.0f);

           // String finishEdit=intent.getExtras().get("EditPlusCover").toString();


            BookData updatebook=new BookData(finishcover,finishtitle,finishwriter,finishpublisher,finishdate,finishstar);

            Log.e("수정했다","메인"+finishcover+finishtitle+finishwriter+finishdate+finishpublisher+finishstar);

            bookList.set(adapter.itemposition,updatebook);
            adapter.notifyDataSetChanged();
            Toast.makeText(this, " 책이 정상적으로 수정되었습니다", Toast.LENGTH_SHORT).show();



        }

    }//onActivityResult


    //https://kitesoft.tistory.com/68
    //Context 메뉴로 등록한 View(여기서는 ListView)가 처음 클릭되어 만들어질 때 호출되는 메소드
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu,v,menuInfo);

        getMenuInflater().inflate(R.menu.menu_list, menu);

    }

    //Context 메뉴로 등록한 View(여기서는 ListView)가 클릭되었을 때 자동으로 호출되는 메소드
    public boolean onContextItemSelected(MenuItem item){
        super.onContextItemSelected(item);

        /*AdapterContextMenuInfo
        AdapterView가 onCreateContextMenu할때의 추가적인 menu 정보를 관리하는 클래스
        ContextMenu로 등록된 AdapterView(여기서는 Listview)의 선택된 항목에 대한 정보를 관리하는 클래스
        index : AdapterView안에서 ContextMenu를 보여주는 항목의 위치

        */
        AdapterView.AdapterContextMenuInfo menuInfo;
        int index;

        //선택된 ContextMenu의  아이템아이디를 구별하여 원하는 작업 수행
        switch (item.getItemId()){
            case R.id.item_modify :
                menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                index = menuInfo.position;


                BookData editbook=bookList.get(index);

                Intent intent=new Intent(HomeActivity.this,EditBookActivity.class);

                intent.putExtra("책편집cover",editbook.getCover());
                intent.putExtra("책편집title",editbook.getTitle());
                intent.putExtra("책편집writer",editbook.getWriter());
                intent.putExtra("책편집publisher",editbook.getPublisher());
                intent.putExtra("책편집readdate",editbook.getReaddate());
                intent.putExtra("책편집star",editbook.getStar());

                startActivityForResult(intent,0604);


//                adapter.notifyDataSetChanged();
//                Toast.makeText(this, " 수정되었습니다", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.item_delete:
                menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                index = menuInfo.position;
                bookList.remove(index);
                adapter.notifyDataSetChanged();
                Toast.makeText(this, " 삭제되었습니다", Toast.LENGTH_SHORT).show();
                return true;

        }


        return false;
    }






    //액션버튼 메뉴 액션바에 집어 넣기
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);



//        MenuItem myActionMenuItem=menu.findItem(R.id.action_search);
//        SearchView searchView=(SearchView)myActionMenuItem.getActionView();
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//
//               if(TextUtils.isEmpty(s)) {
//                   adapter.filter("");
//                   listView.clearTextFilter();
//               }else{
//                   adapter.filter(s);
//               }
//                return true;
//            }
//        });



        return true;

    }

    //액션버튼 메뉴 클릭 할 때 호출되는 메소드
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
//서재추가했던거
//            case R.id.menu_add_library:
////                Intent intent1 = new Intent(HomeActivity.this, AddLibraryActivity.class);
//////                startActivityForResult(intent1, 888);
//////                return true;
//
//                //커스텀다이얼로그 참고: https://blog.plorence.kr/239
//
//                AlertDialog.Builder alert = new AlertDialog.Builder(this);
//                alert.setTitle("이름");
//                alert.setMessage("서재 이름을 입력하세요.\n글자수는 최대 8자입니다.");
//                final EditText name = new EditText(this);
//                InputFilter[] FilterArray = new InputFilter[1];
//                FilterArray[0] = new InputFilter.LengthFilter(8); //글자수 제한
//                name.setFilters(FilterArray);
//                alert.setView(name);
//                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) { //확인 버튼을 클릭했을때
////                        String username = name.getText().toString();
////                        nickname.setText(username);
////                        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
////                        SharedPreferences.Editor editor = pref.edit();
////                        editor.putString("nickname", username);
////                        editor.commit();
//
//                        /**서재 리사이클러뷰 추가하는 코드*/
//
//
//                        Toast.makeText(homeActivity, "서재 추가를 완료하였습니다", Toast.LENGTH_SHORT).show();
//
//                    }
//                });
//                alert.setNegativeButton("취소",new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) { //취소 버튼을 클릭
//                        Toast.makeText(homeActivity, "서재 추가를 취소하였습니다", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                alert.show();
//                return true;

            case R.id.menu_online_book:
                //온라인 책검색하는 코드

                Intent data=new Intent(HomeActivity.this, SearchBookActivity.class);
                startActivityForResult(data,20000);
                return true;

            case R.id.menu_add_book:
                Intent intent = new Intent(HomeActivity.this, SelfAddBookActivity.class);
                /**책 리스트뷰 아이템 추가하는 코드
                 * startActivityForResult
                 * Activity의 결과를 받으려면 호출할 때 startActivity() 대신 startActivityForResult() 메소드를 사용
                 * 인수를 하나 추가(CALLER_REQUEST)
                 * 이 인수(CALLER_REQUEST)는 0보다 크거나 같은 integer 값으로 추후 onActivityForResult()메소드에도 동일한 값이 전달되며
                 * 이를 통해 하나의 onActivityResult()메소드에서 (만약 있다면) 여러 개의 startActivityForResult를 구현
                 * CALLER_REQUEST : 새로 리스트 추가 접근시에
                 * */

                startActivityForResult(intent, 1996);
                return true;
        }


                return super.onOptionsItemSelected(item);
        }




    @Override
    protected void onPause() {
        super.onPause();


        saveData();
        //setStringArrayPref(getApplicationContext(), BOOK_JSON, array);
       // setStringArrayPref(getApplicationContext(), SETTINGS_PLAYER_JSON,bookList);
      //  Log.e(TAG, "Put json"+bookList);


    }


    @Override
    protected void onResume() {
        super.onResume();



    }





      /* @brief : setStringArrayPref()는 ArrayList를 JSON으로 변환하여 SharedPreferences에 String을 저장하는 코드
         @param : context :getContext입력, key: 저장할 key값 입력, vaules: 배열 변수명 입력
         리스트를 JSONArray에 담은 후 그 배열을 문자열로 변환, 프리프런스에 저장하는 메소드이다.
       */
//    private void setStringArrayPref(Context context,String key, ArrayList<String> values){
//        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor=prefs.edit();
//        //JSONArray생성
//        JSONArray a=new JSONArray();
//        //리스트 아이템 하나씩 JSONArray 배열에 추가
//        for(int i=0; i<values.size(); i++){
//            a.put(values.get(i));
//        }
//        if( !values.isEmpty()){
//            //JSONArray를 문자열로 저장
//            editor.putString(key,a.toString());
//        }else{
//            editor.putString(key,null);
//        }
//        editor.apply();
//
//    }



    /* @brief : getStringArrayPref()는 SharedPreferences에서 JSON형식의 String을 가져와서 다시 ArrayList로 변환하는 코드
       @param : context :getContext입력, key: 저장할 key값 입력
       key값으로 저장한 배열값 불러오기
        저장과 다른 점은 try ... catch 제어문으로 NullPointerException을 체크해 주어야 되는 것이다.
        앱 설치 시에는 저장된 프리프런스가 없기 때문에
        프리프런스에 저장된 JSONArray 배열 문자열을 읽어와서 한 칼럼씩 리스트에 담는 메소드이다.
     */
//    private ArrayList<String> getStringArrayPref(Context context,String key) {
//        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(context);
//        // json은 단순한 문자열이 아니라 리스트 아이템을 하나로 연결한 문자열이다
//        //.getString("key값")메서드 : 키값에 저장된 String데이터 가져옴. 없으면 null값
//        String json=prefs.getString(key,null);
//        Log.e("값불러오기확인","getString"+json);
//
//        ArrayList<String> urls=new ArrayList<String>();
//        if(json !=null){
//         try{
//             //읽어온 문자열을 JSONArray 변수에 담는다
//             JSONArray a =new JSONArray(json);
//             for(int i=0; i<a.length(); i++){
//                 //문자열의 한 칼럼씩 나누어 리스트에 담는다
//                 //optString: 배열에서는 인덱스로 값을 가져오고, 오브젝트에서는 키로 값을 가져온다
//                 String url=a.optString(i);
//                 urls.add(url);
//
//             }
//
//         }catch (JSONException e){
//             e.printStackTrace();
//         }
//
//        }
//        Log.e("값불러오기확인2","return값urls"+urls);
//        return urls;
//
//    }



//    // store preference
//    ArrayList<String> list = new ArrayList<String>(Arrays.asList(urls));
//    setStringArrayPref(this, "urls", list);
//
//    // retrieve preference
//    list = getStringArrayPref(this, "urls");
//    urls = (String[]) list.toArray();





    //SharedPreferences에 저장되어있는 데이터는 삭제할 때
//    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//    SharedPreferences.Editor editor = prefs.edit();
//    editor.remove(키값);
//    editor.apply();






    //setStringArrayPref는 ArrayList를 Json으로 변환하여
    //SharedPreferences에 String을 저장하는 코드입니다.
//    private void setStringArrayPref(Context context, String key, ArrayList<BookData> bookList) {
//
//        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor=prefs.edit();
//        //JSONArray생성
//        JSONArray a=new JSONArray();
//        //리스트 아이템 하나씩 JSONArray 배열에 추가
//        for(int i=0; i<bookList.size(); i++){
//            a.put(bookList.get(i));
//        }
//        if( !bookList.isEmpty()){
//            //JSONArray를 문자열로 저장
//            editor.putString(key,a.toString());
//        }else{
//            editor.putString(key,null);
//        }
//        editor.apply();
//
//    }


    //getStringArrayPref는 SharedPreferences에서 Json형식의 String을 가져와서
    //다시 ArrayList로 변환하는 코드입니다.
//    private ArrayList getStringArrayPref(Context context, String key) {
//
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
//        String json = prefs.getString(key, null);
//        ArrayList<BookData> bookList = new ArrayList<BookData>();
//
//        if (json != null) {
//            try {
//                //읽어온 문자열을 JSONArray 변수에 담는다
//                JSONArray a = new JSONArray(json);
//
//                //문자열의 한 칼럼씩 나누어 리스트에 담는다
//                for (int i = 0; i < a.length(); i++) {
//
//                    String cover=a.optString(i);
//                    String title=a.optString(i);
//                    String writer=a.optString(i);
//                    String publisher=a.optString(i);
//                    String readdate=a.optString(i);
//                    float star=a.optInt(i);
//
//                    BookData saveBook=new BookData(cover,title,writer,publisher,readdate,star);
//                    bookList.add(saveBook);
//
//
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//        return bookList;
//    }



//    private ArrayList<BookData> getStringArrayPref(Context context,String key) {
//        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(context);
//        String json=prefs.getString(key,null);
//
//        ArrayList<BookData> urls=new ArrayList<BookData>();
//        if(json !=null){
//         try{
//             //읽어온 문자열을 JSONArray 변수에 담는다
//             JSONArray a =new JSONArray(json);
//             for(int i=0; i<a.length(); i++){
//                 //문자열의 한 칼럼씩 나누어 리스트에 담는다
//                 //optString: 배열에서는 인덱스로 값을 가져오고, 오브젝트에서는 키로 값을 가져온다
//                 String cover=a.optString(i);
//                    String title=a.optString(i);
//                    String writer=a.optString(i);
//                    String publisher=a.optString(i);
//                    String readdate=a.optString(i);
//                    float star=a.optInt(i);
//
//                    BookData saveBook=new BookData(cover,title,writer,publisher,readdate,star);
//                    urls.add(saveBook);
//
//             }
//
//         }catch (JSONException e){
//             e.printStackTrace();
//         }
//
//        }
//
//        return urls;
//
//    }









    //bookList 저장하는 메서드
    public void saveData() {
        SharedPreferences mPrefs = context.getSharedPreferences(SPECIFIC_JSON, 0);
        SharedPreferences.Editor editor = mPrefs.edit();

        Set<String> set = new HashSet<String>();
        for (int i = 0; i < bookList.size(); i++) {
            set.add(bookList.get(i).getJSONObject().toString());
        }

        editor.putStringSet(SPECIFIC_JSON, set);
        editor.apply();

    }


    //bookList 불러오는 메서드
    public static ArrayList<BookData> loadFromStorage() {
        SharedPreferences mPrefs = context.getSharedPreferences(SPECIFIC_JSON, 0);

        ArrayList<BookData> items = new ArrayList<BookData>();

        Set<String> set = mPrefs.getStringSet(SPECIFIC_JSON, null);
        if (set != null) {
            for (String s : set) {
                try {
                    JSONObject jsonObject = new JSONObject(s);

                    String cover = jsonObject.getString("savecover");
                    String title = jsonObject.getString("savetitle");
                    String writer = jsonObject.getString("savewriter");
                    String publisher = jsonObject.getString("savepublisher");
                    String readdate = jsonObject.getString("savedate");
                    float star = jsonObject.getInt("savestar");

                    BookData mybook = new BookData(cover, title, writer, publisher, readdate, star);

                    items.add(mybook);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
        return items;
    }









}//class



