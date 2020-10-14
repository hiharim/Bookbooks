package com.harimi.bookbooks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ReceiverCallNotAllowedException;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class DetailBookActivity extends AppCompatActivity {


    private TextView tvDate;
    private  TextView tvPage;
    private  TextView tvLine;
    private  TextView tvMemo;
    private ImageView ivPicture;

//    public static final String STATE_DATE="date";
//    private static final String STATE_PAGE ="page" ;
//    private static final String STATE_LINE ="line" ;
//    private static final String STATE_MEMO ="memo" ;
//
//    private EditText et_date;
//    private EditText et_page;
//    private EditText et_line;
//    private EditText et_memo;


    private static final String SAVE_MEMO="SAVE_MEMO";

    private static String MEMO_JSON="";

    private ArrayList<MemoData> arrayList;

    private MemoAdapter memoAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    MemoData memoData;
    public static Context context;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_book);

//        tvDate=(TextView)findViewById(R.id.activity_detail_book_date_tv);
//        tvPage=(TextView)findViewById(R.id.activity_detail_book_page_tv);
//        tvLine=(TextView)findViewById(R.id.activity_detail_book_line_tv);
//        tvMemo=(TextView)findViewById(R.id.activity_detail_book_memo_tv);
//        ivPicture=(ImageView)findViewById(R.id.activity_detail_book_picture_iv);


        context=this;

        RbPreference pref=new RbPreference(this);
        String account=pref.getValue("loginemail",null);
        Log.e("DetailBookActivity","RbPreference저장된 값"+account);



        //listview 아이템 클릭했을때 intent받는 코드
        ImageView ivCover=(ImageView)findViewById(R.id.activity_detail_book_cover_iv);
        TextView tvTitle=(TextView)findViewById(R.id.activity_detail_book_title_tv);
        TextView tvWriter=(TextView)findViewById(R.id.activity_detail_book_writer_tv);
        TextView tvPublisher=(TextView)findViewById(R.id.activity_detail_book_publisher_tv);
        TextView tvDate=(TextView)findViewById(R.id.activity_detail_book_readdate_tv);
        RatingBar ratingBar=(RatingBar)findViewById(R.id.activity_detail_book_ratingbar);

        Intent intent=getIntent();

        //https://answerofgod.tistory.com/98 uri 받는법
        final String cover=intent.getStringExtra("Clickcover");
        if(cover!= null) {
            Uri coveruri=Uri.parse(cover);
            ivCover.setImageURI(coveruri);
        }

        Glide.with(context).load(cover).into(ivCover);

//        final String cover=intent.getExtras().getString("Clickcover");
//        final Uri coveruri=Uri.parse(cover);
//        ivCover.setImageURI(coveruri);

        String title=intent.getExtras().getString("Clicktitle");
        tvTitle.setText(title);

        String wirter=intent.getExtras().getString("Clickwriter");
        tvWriter.setText(wirter);

        String publisher=intent.getExtras().getString("Clickpublisher");
        tvPublisher.setText(publisher);

        String date=intent.getExtras().getString("Clickdate");
        tvDate.setText(date);

        float star=intent.getFloatExtra("Clickstar",0.0f);
        ratingBar.setRating(star);

        Log.e("디테일북액티비티","받았다"+ivCover+tvTitle+tvWriter);


//        String line=intent.getExtras().getString("제발line");
//        updateline.setText(line);
//        String page=intent.getExtras().getString("제발page");
//        updatepage.setText(page);
//        String memo=intent.getExtras().getString("제발memo");
//        updatememo.setText(memo);
//
//        final String picture=intent.getExtras().getString("제발picture");
//        final Uri pictureUri=Uri.parse(picture);
//        updatepicture.setImageURI(pictureUri);







        Intent data=getIntent();
        MEMO_JSON=account+data.getExtras().getString("Clicktitle");
        Log.e("DetailBookActivity","인텐트 받는다"+MEMO_JSON);




        //
        if (null == arrayList) {
            arrayList = new ArrayList<MemoData>();
        }


        //전에 저장했던 데이터 onCreate할때 불러오는 코드
        try {

            arrayList=loadFromStorage();

            Log.e("onCreate","불러온다"+arrayList);

        }catch (Exception e){
            e.printStackTrace();
        }




        recyclerView=(RecyclerView)findViewById(R.id.activity_detail_book_rv);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration); //데코

        recyclerView.setHasFixedSize(true);

        //arrayList=new ArrayList<>();
        memoAdapter=new MemoAdapter(arrayList);
        recyclerView.setAdapter(memoAdapter);


        memoAdapter.setOnItemClickListener(new MemoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                // 아이템 클릭 이벤트를 MainActivity에서 처리.

                Toast.makeText(DetailBookActivity.this, "아이템클릭"+pos, Toast.LENGTH_SHORT).show();

                MemoData editmemo=arrayList.get(pos);

                Intent intent=new Intent(DetailBookActivity.this,EditMemoActivity.class);

                intent.putExtra("제발date",editmemo.getDate());
                intent.putExtra("제발line",editmemo.getLine());
                intent.putExtra("제발page",editmemo.getPage());
                intent.putExtra("제발memo",editmemo.getMemo());
                intent.putExtra("제발picture",editmemo.getPicture());


                startActivityForResult(intent,3000);

            }
        });







        //스와이프로 메모삭제
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position= viewHolder.getAdapterPosition();

                arrayList.remove(position);
                memoAdapter.notifyItemRemoved(position);
                Toast.makeText(DetailBookActivity.this, "메모가 삭제되었습니다", Toast.LENGTH_SHORT).show();


                //삭제하시겠냐고 묻는 다이얼로그 코드 적기
//                DialogInterface.OnClickListener onClickListener=(new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int which) {
//
//                        switch (which){
//
//                            case DialogInterface.BUTTON_POSITIVE:
//                                arrayList.remove(position);
//                                memoAdapter.notifyItemRemoved(position);
//                                Toast.makeText(DetailBookActivity.this, "메모가 삭제되었습니다", Toast.LENGTH_SHORT).show();
//                            break;
//
//                            case DialogInterface.BUTTON_NEGATIVE:
//                                break;
//
//                        }
//
//
//
//                    }
//                });
//
//                        new AlertDialog.Builder(DetailBookActivity.this)
//                                .setMessage("삭제하시겠습니까?")
//                                .setPositiveButton("예",onClickListener)
//                                .setNegativeButton("아니오",onClickListener)
//                                .show();






            }
        };


        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);



        //https://lcw126.tistory.com/290
//        MySwipeHelper swipeHelper= new MySwipeHelper(DetailBookActivity.this,recyclerView,300) {
//            @Override
//            public void instantiatrMyButton(final RecyclerView.ViewHolder viewHolder, List<MyButton> buffer) {
//                buffer.add(new MyButton(DetailBookActivity.this,
//                        "Delete",
//                        30,
//                        R.drawable.ic_delete_black_24dp,
//                        Color.parseColor("#FF3C30"),
//                        new MyButtonClickListener() {
//                            @Override
//                            public void onClick(int pos) {
//
//                                //삭제하시겠냐고 묻는 다이얼로그 코드 적기
//
//                                Toast.makeText(DetailBookActivity.this, "메모가 삭제되었습니다", Toast.LENGTH_SHORT).show();
//                                Log.d("TAG",viewHolder.getAdapterPosition()+"");
//
//                                arrayList.remove(viewHolder.getAdapterPosition());// 해당 항목 삭제
//                                memoAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());    // Adapter에 알려주기.
//                            }
//                        }));
//
//
////                buffer.add(new MyButton(DetailBookActivity.this,
////                        "Update",
////                        30,
////                        R.drawable.ic_edit_white_24dp,
////                        Color.parseColor("#03DAC5"),
////                        new MyButtonClickListener() {
////                            @Override
////                            public void onClick(int pos) {
////                               // Toast.makeText(DetailBookActivity.this, "edit click", Toast.LENGTH_SHORT).show();
////                                //TODO: 수정버튼 누르면 EditMemoActiity로 이동
////                                Toast.makeText(DetailBookActivity.this, "수정버튼 클릭", Toast.LENGTH_SHORT).show();
////                                Intent intent=new Intent(getApplicationContext(),EditMemoActivity.class);
////
////                                intent.putExtra("기존리스트",arrayList);
////
////
////                                startActivityForResult(intent,3000);
////
////
////
////
////
////                            }
////                        }));
//
//
//
//            }
//        };// swipeHelper



//        //리사이클러뷰 내의 아이템 클릭시 edit
//        memoAdapter.setOnItemClickListener(new MemoAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View v, int pos) {
//
//                Intent intent=new Intent(DetailBookActivity.this,EditMemoActivity.class) {
//
//                startActivityForResult(intent,3000);
//
//
//                }
//
//
//
//            }
//        });












        //메모추가버튼
        Button addbtn=(Button)findViewById(R.id.activity_detail_book_add_memo_btn);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),WriteMemoActivity.class);
                startActivityForResult(intent,1004);


            }
        });



    }//onCreate


    protected void onActivityResult(int requestCode,int resultCode,Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1004) {
            if (resultCode != Activity.RESULT_OK) {
                return;
            }

            String senddate = data.getExtras().getString("date");
            String sendpage = data.getExtras().getString("page_number");
            String sendline = data.getExtras().getString("line");
            String sendmemo = data.getExtras().getString("memo");
            String sendPicture = data.getStringExtra("imageUri");
            //  ivPicture.setImageURI(Uri.parse(sendPicture));

            //받아온 데이터로 메모를 만든다
            MemoData memo = new MemoData(senddate, sendpage, sendline, sendPicture, sendmemo);
            arrayList.add(memo);
            //memoAdapter.addItem(memo);
            memoAdapter.notifyDataSetChanged();

       // }

          } else {
            if (resultCode != Activity.RESULT_OK) {
                return;
            }
            String updatedate = data.getExtras().getString("수정Date");
            String updatepage = data.getExtras().getString("수정Page");
            String updateline = data.getExtras().getString("수정Line");
            String updatememo = data.getExtras().getString("수정Memo");
            String updatepicture = data.getStringExtra("수정ImageUri");

            MemoData newmemo=new MemoData(updatedate,updatepage,updateline,updatepicture,updatememo);

            Log.e("받았다","메인"+updatedate+updateline+updatepage+updatememo+updatepicture);

          // arrayList.set(getAdapterPosition(),newmemo);

           // memoAdapter.updateData(newmemo);
         //   recyclerView.setAdapter(new MemoAdapter(arrayList));

            arrayList.set(memoAdapter.itemposition,newmemo);
            memoAdapter.notifyDataSetChanged();

        }


    }


    @Override
    protected void onPause() {
        super.onPause();

        saveData();
    }


    public void saveData() {
        SharedPreferences mPrefs = context.getSharedPreferences(MEMO_JSON, 0);
        SharedPreferences.Editor editor = mPrefs.edit();

        Set<String> set = new HashSet<String>();
        for (int i = 0; i < arrayList.size(); i++) {
            set.add(arrayList.get(i).getJSONObject().toString());
        }

        editor.putStringSet(MEMO_JSON, set);
        editor.apply();

    }




    public static ArrayList<MemoData> loadFromStorage() {
        SharedPreferences mPrefs = context.getSharedPreferences(MEMO_JSON, 0);

        ArrayList<MemoData> items = new ArrayList<MemoData>();

        Set<String> set = mPrefs.getStringSet(MEMO_JSON, null);
        if (set != null) {
            for (String s : set) {
                try {
                    JSONObject jsonObject = new JSONObject(s);

                    String line = jsonObject.getString("saveline");
                    String memo = jsonObject.getString("savememo");
                    String page = jsonObject.getString("savepage");
                    String picture = jsonObject.getString("savepicture");
                    String date = jsonObject.getString("savedate");


                    MemoData mymemo = new MemoData(date,page,line,picture,memo);

                    items.add(mymemo);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
        return items;
    }







}//class