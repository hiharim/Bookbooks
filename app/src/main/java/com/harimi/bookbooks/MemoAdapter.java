package com.harimi.bookbooks;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import android.text.format.DateFormat;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.*;

public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.CustomViewHolder>{
    /** 메모 어댑터 클래스
     * 리사이클러뷰 상속
     * onCreateViewHolder, onBindViewHolder, getItemCount 메소드 정의
     * 어댑터 상속 및 구현
     * 뷰홀더 상속 및 구현
     * 아이템 추가,수정,삭제 메소드
     * */


    private ArrayList<MemoData> arrayList;
    private Context context;
    public int itemposition;



    //(1) 커스텀 리스너 인터페이스 정의 - onItemClick
    public interface OnItemClickListener
    {
        void onItemClick(View v, int pos);
    }





//    //(2) 리스너 객체를 전달하는 메서드와 전달된 객체를 저장할 변수(mListener) 추가
//    private OnItemClickListener listener =null;

//    public void setOnItemClickListener(OnItemClickListener listener)
//    {
//        this.listener = listener;
//    }


    //https://stackoverflow.com/questions/35596985/cannot-resolve-method-format-java-lang-string-java-util-date
//    android.text.format.DateFormat df = new android.text.format.DateFormat();
//    dateTextView.setText(df.format("dd/MM/yyyy hh:mm:ss", date).toString());


    //생성자에서 데이터리스트 객체를 전달받는다
    public MemoAdapter(ArrayList<MemoData> arrayList) {
        this.arrayList = arrayList;
    }

    public ArrayList<MemoData> getListData(){
        return arrayList;
    }



    /*리스너 객체 참조를 저장하는 변수
    */
    private OnItemClickListener listener=null; //리스너 객체 추가



    /*OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    어댑터의 외부(액티비티)에서 리스너 객체 참조를 어댑터에 전달하는 메서드
    */
    public void setOnItemClickListener(OnItemClickListener listener){

        this.listener=listener;
    }



    /**@brief addItem
     * @param
     * @detail 아이템 추가
     * */
    public void addItem(MemoData memoData){

        arrayList.add(memoData);
    }


    public void setArrayList(ArrayList<MemoData> data,Context context){
        this.arrayList=data;
        this.context=context;
    }

//    public void updateData(MemoData memoData){
//        // update adapter element like NAME, EMAIL e.t.c. here
//
//        // then in order to refresh the views notify the RecyclerView
//        arrayList.clear();
//        arrayList.addall(memoData);
//        notifyDataSetChanged();
//    }






    public void remove(int position){
        //예외상황이 벌어졌을때 강제진행을 하는것
        try{
            arrayList.remove(position);
            notifyItemRemoved(position);
        }catch (IndexOutOfBoundsException ex){
            ex.printStackTrace();
        }
    }


    /** 데이터 셋의 크기를 리턴해준다
     * 전체 데이터 갯수 리턴.
     * 아이템의 개수 리턴*/
    @Override
    public int getItemCount() {
       // return (arrayList == null) ? 0 : arrayList.size();
        return arrayList.size();
    }



    /**커스텀뷰홀더 상속 및 구현
     * item.xml에 존재하는 위젯들을 바인딩한다 */
    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView date;
        protected TextView line;
        protected TextView page;
        protected TextView memo;
        protected ImageView picture;

        public CustomViewHolder(@NonNull final View itemView) {
            super(itemView);
            this.date=(TextView)itemView.findViewById(R.id.item_memo_date_tv);
            this.line=(TextView)itemView.findViewById(R.id.item_memo_line_tv);
            this.page=(TextView)itemView.findViewById(R.id.item_memo_page_tv);
            this.memo=(TextView)itemView.findViewById(R.id.item_memo_memo_tv);
            this.picture=(ImageView)itemView.findViewById(R.id.item_memo_picture_iv);

//            itemView.setOnCreateContextMenuListener((View.OnCreateContextMenuListener) this);

            //뷰홀더가 만들어지는 시점에 아이템 클릭 이벤트처리
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    /*아이템 위치 알아내기
//                    리사이클러뷰의 뷰홀더에는 자신의 위치를 확인할 수 있는 아래 메서드 제공
//                    */
//                    int position=getAdapterPosition();
//                    if(position !=RecyclerView.NO_POSITION){
//                        //어댑터가 참조하고있는 데이터 리스트로부터 데이터 가져오기
//                        MemoData memoData=arrayList.get(position);
//                        //intent객체를 생성하여 날린다 EditMemoActivity는 다음넘어갈 화면
//                        Intent intent=new Intent(itemView.getContext(),EditMemoActivity.class);
//
//                        //intent객체에 데이터를 실어서 보내기
//                        intent.putExtra("data",arrayList);
//
//                        //intent.putExtra("포스트제목",((TextView)view.findViewById(R.id.title_board)).getText().toString());
//
//
//                        intent.putExtra("clickdate",((TextView)itemView.findViewById(R.id.item_memo_date_tv)).getText().toString());
//                        intent.putExtra("clickpage",((TextView)itemView.findViewById(R.id.item_memo_page_tv)).getText().toString());
//                        intent.putExtra("clickline",((TextView)itemView.findViewById(R.id.item_memo_line_tv)).getText().toString());
//                        intent.putExtra("clickmemo",((TextView)itemView.findViewById(R.id.item_memo_memo_tv)).getText().toString());
//                        intent.putExtra("clickpicture",((ImageView)itemView.findViewById(R.id.item_memo_picture_iv)).getContext().toString());
//
//
//
//
//                        itemView.getContext().startActivity(intent);
//                        if(listener!=null){
//
//                            listener.onItemClick(CustomViewHolder.this,view,getAdapterPosition());
//                            Log.e("뷰홀더클래스 ViewHolder.class", "리스너 온 아이템클릭 listener.onItemClick메소드");
//                            MemoData item=arrayList.get(position);
//                            Log.e("뷰홀더클래스 ViewHolder.class", "Data_board 아이템의 get(positon) 포지션값얻는다");
//                        }
//
//
//
//                    }
//
//
//                }
//            });

            // (3)뷰홀더가 만들어지는 시점에 클릭 이벤트를 처리해야 한다.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*리사이클러뷰의 뷰홀더에는 자신의 위치를 확인할 수 있는 아래 메서드 제공
                      현재 클릭 이벤트가 발생된 아이템 위치(position)를 알아내는 것
                    */
                    int pos = getAdapterPosition();
                    itemposition=pos;
                    if(pos!= RecyclerView.NO_POSITION)
                    {
                      /*어댑터가 참조하고 있는 데이터 리스트로부터 데이터를 가져오는 것
                       데이터 리스트로부터 아이템 데이터참조
                      */
                        MemoData memo=arrayList.get(pos);
                        //리스너 객체의 메서드 호출
                        if(listener !=null){
                            listener.onItemClick(v,pos);
                        }

//                        Intent intent=new Intent(itemView.getContext(),EditMemoActivity.class);
//                        intent.putExtra("ItemDate",arrayList.get(pos).getDate());
//                        intent.putExtra("ItemPage",arrayList.get(pos).getPage());
//                        intent.putExtra("ItemLine",arrayList.get(pos).getLine());
//                        intent.putExtra("ItemMemo",arrayList.get(pos).getMemo());
//                        intent.putExtra("ItemPicture",arrayList.get(pos).getPicture());
//
//                         context.startActivity(intent);




                    }
                }
            });





        }

        // 컨텍스트 메뉴를 생성하고 메뉴 항목 선택시 호출되는 리스너를 등록해줍니다.
        // ID 1001, 1002로 어떤 메뉴를 선택했는지 리스너에서 구분하게 됩니다.
//        @Override
//        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo contextMenuInfo) {
//
//            MenuItem edit=menu.add(Menu.NONE,1001,1,"편집");
//            edit.setOnMenuItemClickListener(onEditMenu);
//
//        }
//
//        private final MenuItem.OnMenuItemClickListener onEditMenu=new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//
//                switch (item.getItemId()) {
//
//                    case 1001:
//                        // 편집 항목을 선택 시 다이얼로그를 보여주기 위해 edit_box.xml 파일을 사용합니다.
//                    AlertDialog.Builder builder=new AlertDialog.Builder(mcontext);
//                    View view=LayoutInflater.from(mcontext).inflate(R.layout.edit_box,null,false);
//
//                    final Button editbtn=(Button)view.findViewById(R.id.edit_box_memo_finish_btn);
//
//                    final EditText editpage=(EditText)view.findViewById(R.id.edit_box_memo_pagenum_et);
//                    final EditText editline=(EditText)view.findViewById(R.id.edit_box_memo_line_et);
//                    final EditText editmemo=(EditText)view.findViewById(R.id.edit_box_memo_content_et);
//                    final ImageView editpicture=(ImageView)view.findViewById(R.id.edit_box_memo_picture_iv);
//
//                    //해당 줄에 입력되어 있던 데이터를 불러와서 다이얼로그에 보여줍니다.
//                    editpage.setText(arrayList.get(getAdapterPosition()).getPage());
//                    editline.setText(arrayList.get(getAdapterPosition()).getLine());
//                    editmemo.setText(arrayList.get(getAdapterPosition()).getMemo());
//                    editpicture.setImageURI(arrayList.get(getAdapterPosition()).getPicture();
//
//
//                    //완료 버튼을 클릭하면 현재 UI에 입력되어 있는 내용으로
//                    final AlertDialog dialog = builder.create();
//                    editbtn.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            String updatepage=editpage.getText().toString();
//                            String updateline=editline.getText().toString();
//                            String updatememo=editmemo.getText().toString();
//                            String updatepicture=editpicture.get
//
//                            Date date=new Date();
//                            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//                            String update=sdf.format(date);
//
//                            MemoData newmemo=new MemoData(update,updatepage,updateline,updatepicture,updatememo);
//
//                            arrayList.set(getAdapterPosition(),newmemo);
//                            notifyDataSetChanged();
//                            dialog.dismiss();
//
//
//
//                        }
//                    });
//
//                    dialog.show();
//                    break;
//
//                }
//
//
//
//
//
//
//
//                return true;
//            }
//        };


    }



    /** 새로운 뷰 홀더 생성될때의 생명주기
     * 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
     * 뷰홀더가 만들어지는 시점에 호출되는 메소드(뷰홀더가 재사용된다면 이 메서드는 호출되지 않는다)*/
    @NonNull
    @Override
    public MemoAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_memo,parent,false);
        context=parent.getContext();
        //CustomViewHolder holder=new CustomViewHolder(view);
        MemoAdapter.CustomViewHolder holder=new MemoAdapter.CustomViewHolder(view);

        return holder;
    }




    /** View의 내용을 해당 포지션의 데이터로 바꿔준다
      position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
     */
   @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder holder, final int position) {

      // MemoData data=arrayList.get(position);

       holder.date.setText(arrayList.get(position).getDate());
       holder.page.setText(arrayList.get(position).getPage());
       holder.line.setText(arrayList.get(position).getLine());
       holder.memo.setText(arrayList.get(position).getMemo());
       holder.picture.setImageURI(Uri.parse(arrayList.get(position).getPicture()));

       // 아이템 클릭이벤트
       //https://goodgoodminki.tistory.com/entry/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-%EB%A6%AC%EC%82%AC%EC%9D%B4%ED%81%B4%EB%9F%AC%EB%B7%B0-%EC%95%84%EC%9D%B4%ED%85%9C-%ED%81%B4%EB%A6%AD-%EC%9D%B4%EB%B2%A4%ED%8A%B8-Android-RecyclerView-Click-event
//       holder.itemView.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View view) {
//
////                Context context=view.getContext();
//
//
////               Intent intent = new Intent(holder.itemView.getContext(), DetailMemoActivity.class);
////               intent.putExtra("ItemDate", arrayList.get(position).getDate());
////               intent.putExtra("ItemPage", arrayList.get(position).getPage());
////               intent.putExtra("ItemLine", arrayList.get(position).getLine());
////               intent.putExtra("ItemMemo", arrayList.get(position).getMemo());
////               intent.putExtra("ItemPicture", arrayList.get(position).getPicture());
////
////               ContextCompat.startActivity(holder.itemView.getContext(), intent, null);
//
//
//                //get(position) - arraylist의 위치값
//
//                Intent intent=new Intent(holder.itemView.getContext(),EditMemoActivity.class);
//                intent.putExtra("ItemDate",arrayList.get(position).getDate());
//                intent.putExtra("ItemPage",arrayList.get(position).getPage());
//                intent.putExtra("ItemLine",arrayList.get(position).getLine());
//                intent.putExtra("ItemMemo",arrayList.get(position).getMemo());
//                intent.putExtra("ItemPicture",arrayList.get(position).getPicture());
//
//              // ContextCompat.startActivity(holder.itemView.getContext(), intent, null);
//                context.startActivity(intent);
//
//
//
//           }
//       });


//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                itemposition=holder.getAdapterPosition();
//
//            }
//        });









   }






        //아이템클릭이벤트
//        holder.itemView.setTag(position);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//
//        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//
//                remove(holder.getAdapterPosition());
//                return true;
//            }
//        });





//    public interface OnItemClickListener {
//        void onItemClick(BookData bookData);
//    }
//    public void setOnItemClickListener(OnItemClickListener listener) {
//        this.listener = listener;
//    }













}//class
