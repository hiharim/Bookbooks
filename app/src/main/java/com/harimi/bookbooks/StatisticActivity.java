package com.harimi.bookbooks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Set;

/**
 * 통계 클래스
 * 기능: 내 서재에 있는 책 리스트 총 개수 표현하는 textView, 몇년 몇월에 책을 몇 권읽었는지 기록되는 막대그래프
 */
public class StatisticActivity extends AppCompatActivity {


    ArrayList<BookData> arrayList;
    Context context;

    ArrayList<String> chartList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        context=this;

        /*
        RbPreference에서 로그인한 사용자 email을 꺼내서 변수 account에 담아서
        사용자에 맞는 책 리스트사이즈를 보여준다
        */
        RbPreference pref=new RbPreference(this);
        String account=pref.getValue("loginemail",null);
        
        int books=pref.getValue("totalBooks",0);

        Log.e("StatisticActivity","RbPreference저장된 값 계정:"+account);
        Log.e("StatisticActivity","RbPreference저장된 값 책 총 권수:"+books);


        TextView total=findViewById(R.id.activity_statistic_totalnum_tv);
        //setText에는 String값이 들어가야하는데 books변수는 int형이기 때문에 String.valueof()로 씌워줬다
        total.setText(String.valueOf(books));





        ImageButton before=(ImageButton)findViewById(R.id.activity_statistic_before_btn);
        before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //jsonParsing();

            }
        });


        ImageButton after=(ImageButton)findViewById(R.id.activity_statistic_after_btn);
        after.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });





        BarChart chart=findViewById(R.id.chartYear);


        //Y축 데이터:
        ArrayList volume=new ArrayList();
        volume.add(new BarEntry(945f,0));
        volume.add(new BarEntry(1040f,1));
        volume.add(new BarEntry(1133f,2));
        volume.add(new BarEntry(1240f,3));
        volume.add(new BarEntry(1369f,4));
        volume.add(new BarEntry(1487f,5));
        volume.add(new BarEntry(1501f,6));
        volume.add(new BarEntry(1645f,7));
        volume.add(new BarEntry(1578f,8));
        volume.add(new BarEntry(1695f,9));



        //X축 정의 : 월 별
        ArrayList<String> monthX= new ArrayList<>();

        monthX.add("1");
        monthX.add("2");
        monthX.add("3");
        monthX.add("4");
        monthX.add("5");
        monthX.add("6");
        monthX.add("7");
        monthX.add("8");
        monthX.add("9");
        monthX.add("10");
        monthX.add("11");
        monthX.add("12");


        BarDataSet barDataSet=new BarDataSet(volume,"권 수");
        chart.animateY(5000);
        BarData data=new BarData(monthX, barDataSet);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        chart.setData(data);









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
//        Button memobtn=(Button)findViewById(R.id.status_memo);
//        memobtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(getApplicationContext(),MemoActivity.class);
//                startActivity(intent);
//
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
        Button setbtn=(Button)findViewById(R.id.status_setting);
        setbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),SettingActivity.class);
                startActivity(intent);

            }
        });


}//onCreate

//    public void jsonParsing() {
//        RbPreference pref=new RbPreference(this);
//        String account=pref.getValue("loginemail",null);
//
//        SharedPreferences mPrefs = context.getSharedPreferences(account, 0);
//        Set<String> set = mPrefs.getStringSet(account, null);
//
//        try{
//            JSONArray jsonArray=new JSONArray(set);
//            for(int i=0; i<jsonArray.length(); i++){
//                JSONObject jsonObject=jsonArray.getJSONObject(i);
//                String date=jsonObject.getString("savedate");
//                //chartList.add(jsonObject.getString("savedate"));
//                chartList.add(date);
//
//            }
//
//            Log.e("파싱확인","StatisticActivity"+chartList);
//
//        }catch (JSONException e){
//            e.printStackTrace();
//        }
//
//    }








}//class