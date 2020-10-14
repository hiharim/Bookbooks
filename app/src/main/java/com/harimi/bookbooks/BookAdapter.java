package com.harimi.bookbooks;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

//BookAdapter는 BookData객체 리스트에 있는 데이터를 ListView의 row 레이아웃으로 제공 할 수 있는 ArrayAdapter이다
public class BookAdapter extends ArrayAdapter<BookData>  {

    Context myContext;
    LayoutInflater inflater;
  // private ArrayList<BookData> bookList;




    /* itemposition: adapter의 position값을 HomeActivity에서도 사용할 수 있기 위해서 itemposition을 전역변수로 빼두었다
    */
    public int itemposition;


    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList (원본데이터)
    private ArrayList<BookData> list=new ArrayList<>();

    Filter filter;
    // 필터링된 결과 데이터를 저장하기 위한 ArrayList. 최초에는 전체 리스트 보유.
    private ArrayList<BookData> filteredList=list;


    public BookAdapter(@NonNull Context context, int resource, @NonNull ArrayList<BookData> objects) {
        super(context, resource, objects);
        myContext=context;
       // DataList=objects;
        list=objects;
        inflater=LayoutInflater.from(context);
    }



    // Container Class for item
    private class ViewHolder {

        ImageView coveriv;
        TextView titletv;
        TextView writertv;
        TextView publishertv;
        TextView datetv;
        TextView startv;

    }



    public void addItem(BookData item) {
        list.add(item);

    }


    public void setItem(BookData item){
        list.set(itemposition,item);

    }

//    public Filter getFilter(){
//        if(filter==null){
//            filter=new ListFilter();
//        }
//        return filter;
//    }
//
//    private class ListFilter extends Filter{
//
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            FilterResults results=new FilterResults();
//
//            if(constraint ==null || constraint.length()==0){
//                results.values=list;
//                results.count=list.size();
//            }else{
//                ArrayList<BookData> itemList=new ArrayList<BookData>();
//                for(BookData item: list){
//                    if( item.getTitle().toUpperCase().contains(constraint.toString().toUpperCase()) )
//                    {
//                        itemList.add(item);
//                    }
//
//                }
//                results.values=itemList;
//                results.count=itemList.size();
//
//            }
//
//            return results;
//        }
//
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//
//
//            filteredList = (ArrayList<BookData>) results.values;
//
//            if(results.count>0) {
//                notifyDataSetChanged();
//            }
//            else{
//                notifyDataSetInvalidated();
//            }
//
//
//
//        }
//    }



//    public View getView(int position, View view, ViewGroup parent) {
////        LayoutInflater inflater = (LayoutInflater) myContext
////                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//        final ViewHolder holder;
//        if (view == null) {
//            holder = new ViewHolder();
//            view = inflater.inflate(R.layout.item_book, null);
//
//            holder.coveriv = (ImageView) view.findViewById(R.id.item_book_cover);
//            holder.titletv = (TextView) view.findViewById(R.id.item_book_title);
//            holder.writertv = (TextView) view.findViewById(R.id.item_book_writer);
//
//            final BookData bookData = list.get(position);
//
//            if (null != bookData) {
//                holder.coveriv.setImageURI(Uri.parse(bookData.getCover()));
//                holder.titletv.setText(bookData.getTitle());
//                holder.writertv.setText(bookData.getWriter());
//            }
//
//            view.setTag(holder);
//
//        }
//        else {
//            holder = (ViewHolder) view.getTag();
//        }
//
//        return view;
//    }




    /**getView() 메소드는 실제로 CustomListView의 아이템을 구성하는 메소드로, 사용하려는 레이아웃xml파일로부터
        View객체를 생성하고 그 View를 바탕으로 필요한 정보를 설정한다
     * getView() 메소드에서 convertView가 null일경우에만 LayoutInflater를 통해 View객체를 생성하도록 하여
        효율적으로 레이아웃을 구성할 수 있도록 한다.
     * 그리고 레이아웃.xml파일로부터 생성된 View로부터 레이아웃 안의 다른 View를 불러와야하므로
        findViewById를 통해 View를 가져올때, 위에서 생성한 view를 이용하여 view.findViewById();를해야한다는점을 주의해라!
     * 내가 아이템이 계속 중복되서 겹쳤던 이유 : getView를 잘못 쓰고 있었음.......
     * */
    public View getView(final int position, View convertView, ViewGroup parent){
        //convertView : 각 아이템을 구성하는 view
        View view=convertView;
//        itemposition=position;

        if(view==null) {
            //아이템을 구성하는 convertView가 null일경우
            //layout xml파일로부터 view객체를 생성
            view = inflater.inflate(R.layout.item_book, null);
        }

            /*
            생성한 view 객체로부터 설정해야하는 뷰를 획득
            이 경우 위에서 생성한 view를 가져오는 것이므로
            view.findViewById(R.id.view_id) 를 통해 획득해야함
             */
            ImageView coveriv = (ImageView) view.findViewById(R.id.item_book_cover);
            TextView titletv = (TextView) view.findViewById(R.id.item_book_title);
            TextView writertv = (TextView) view.findViewById(R.id.item_book_writer);




            //listView포지션에 따른 데이터를 로드
            final BookData bookData=list.get(position);




            // Data Set(filteredItemList)에서 position에 위치한 데이터 참조 획득
           // final BookData bookData=filteredList.get(position);




            //listView포지션에 따른 데이터를 뷰에 세팅
            coveriv.setImageURI(Uri.parse(bookData.getCover()));
            //온라인으로 책추가했을때 url형식의 이미지 뷰에세팅
             Glide.with(myContext).load(bookData.getCover()).into(coveriv);

            titletv.setText(bookData.getTitle());
            writertv.setText(bookData.getWriter());


            // 리스트 아이템 클릭시 발생하는 이벤트
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    getItem(position);

                    Intent intent=new Intent(getContext(),DetailBookActivity.class);

                 /*
                intent로 데이터 전송하여 이동
                DetailBookActivity에서 받은 books key값으로 현재 bookList의 position값 전달
                */
                    //intent.putExtra("Clickbooks",bookList.get(position));

                    intent.putExtra("Clickcover",bookData.getCover());
                    intent.putExtra("Clicktitle",bookData.getTitle());
                    intent.putExtra("Clickwriter",bookData.getWriter());
                    intent.putExtra("Clickpublisher",bookData.getPublisher());
                    intent.putExtra("Clickdate",bookData.getReaddate());
                    intent.putExtra("Clickstar",bookData.getStar());


                    view.getContext().startActivity(intent);
                    Log.e("어댑터","클릭");
                }
            });




            /* setOnLongClickListener : 리스트뷰 아이템을 꾹 롱클릭했을때 context메뉴가 나타나서 아이템편집을 하게되는데
                이 때 컨텍스트메뉴를 이용하기위해서 아이템을 꾹 누름과 동시에 롱클릭리스너도 호출이 되기때문에
                바로 전역변수 itemposition값이 해당 position값으로 넘어가고 그 position값을 HomeActivity에서 사용할 수 있게된다
            */
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    itemposition=getPosition(bookData);
                    return false;
                }
            });


        return view;

    }








    @Override
    public void remove(BookData object) {
        list.remove(object);
        notifyDataSetChanged();
    }


    public List<BookData> getMyList() {
        return list;
    }

    @Override // 리스트 뷰가 어댑터에게 데이터 몇 개 가지고 있니? 물어보는 함수
    public int getCount() {
        return list.size();

       // return filteredList.size();
    }



    //// 지정한 위치(postion)에 있는 데이터와 관계된 아이템의 ID를 리턴
    @Override
    public long getItemId(int position) { return position; }


    // 지정한 위치(position)에 있는 데이터 리턴
    public BookData getItem(int position){
        return list.get(position);

        //return filteredList.get(position);
    }





}
