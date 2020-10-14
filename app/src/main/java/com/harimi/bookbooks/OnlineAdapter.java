package com.harimi.bookbooks;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


public class OnlineAdapter extends BaseAdapter {


    /* 데이터 그릇들의 집합을 정의 */
    private ArrayList<OnlineBook> mItems = new ArrayList<>();

    LayoutInflater inflater;
    Context myContext;

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public OnlineBook getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



//    public OnlineAdapter(@NonNull Context context, int resource, @NonNull ArrayList<OnlineBook> objects) {
//
//        myContext=context;
//        mItems=objects;
//        inflater=LayoutInflater.from(context);
//    }




    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();
        // 커스텀 리스트뷰의 xml을 inflate
        if (convertView == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_online, parent, false);
        }

        //아이템을 구성하는 convertView가 null일경우
        //layout xml파일로부터 view객체를 생성
//        if(convertView==null){
//            convertView=inflater.inflate(R.layout.item_online,null);
//        }



        /* 커스텀 리스트뷰 xml에 있는 속성값들을 정의 */
        ImageView cover=(ImageView)convertView.findViewById(R.id.item_cover);
        final TextView title=(TextView)convertView.findViewById(R.id.item_title);
        TextView writer=(TextView)convertView.findViewById(R.id.item_writer);
        TextView publisher=(TextView)convertView.findViewById(R.id.item_publisher);


        /* 데이터를 담는 그릇 정의 */
        final OnlineBook naverBook = getItem(position);


//        Uri uri = data.Data;
//        Bitmap bmp= BitmapFactory.DecodeStream(ContentResolver.OpenInputStream(uri));
//        cover.SetImageBitmap (bmp);

        //이미지 로딩 라이브러리 Glide
        Glide.with(context).load(naverBook.getCover()).into(cover);

        /* 해당 그릇에 담긴 정보들을 커스텀 리스트뷰 xml의 각 TextView에 뿌려줌 */
        //cover.setImageURI(Uri.parse(naverBook.getCover())); //todo :다시확인하기 내프로젝트랑비교 ->맞음
        title.setText(naverBook.getTitle());
        writer.setText(naverBook.getWriter());
        publisher.setText(naverBook.getPublisher());


        //아이템클릭리스너
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context, "어댑터 클릭"+position+naverBook.getTitle(), Toast.LENGTH_SHORT).show();

                getItem(position);

                Intent intent=new Intent(context,PlusActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                 /*
                intent로 데이터 전송하여 이동
                PlusActivity로 인텐트전달
                */

                intent.putExtra("NaverCover",naverBook.getCover());
                intent.putExtra("NaverTitle",naverBook.getTitle());
                intent.putExtra("NaverWriter",naverBook.getWriter());
                intent.putExtra("NaverPublisher",naverBook.getPublisher());




                view.getContext().startActivity(intent);

                Log.e("어댑터","클릭");



            }
        });





        return convertView;

    }


    /* 네이버 책 검색 중, 제목, 포스팅 일자, 포스트 링크를 그릇에 담음 */
    public void addItem(String cover,String title, String writer, String publisher) {

        OnlineBook mItem = new OnlineBook();

        mItem.setCover(cover);
        mItem.setTitle(title);
        mItem.setWriter(writer);
        mItem.setPublisher(publisher);

        /* 데이터그릇 mItem에 담음 */
        mItems.add(mItem);

    }








}//class



