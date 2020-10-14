package com.harimi.bookbooks;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

public class DictionaryActivity extends AppCompatActivity {

    //xml파싱 변수
    EditText edit;
    TextView text;
    XmlPullParser xpp;


    String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);


        ActionBar ab = getSupportActionBar() ;
        ab.setTitle("표준국어대사전") ;


        edit= (EditText)findViewById(R.id.edit);
        text= (TextView)findViewById(R.id.result);



    }

    //xml파싱
    public void mOnClick(View v){
        switch (v.getId()){
            case R.id.button:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //data : xml파싱된 결과값담는변수
                        // 메소드를 호출하여 XML data를 파싱해서 String 객체로 얻어오기
                        data=getXmlData();


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                text.setText(data);



                            }
                        });
                    }
                }).start();
                break;
        }
    }

    String getXmlData(){

        StringBuffer buffer=new StringBuffer();
        String str= edit.getText().toString();//EditText에 작성된 Text얻어오기
        String res = URLEncoder.encode(str);

        Log.e("확인",str);
        Log.e("인코딩확인",res);


        String queryUrl="https://stdict.korean.go.kr/api/search.do?certkey_no=1951&key=BF0F39E3749864B7901797E04B09FA04&q="+res;




        try{
            URL url= new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is= url.openStream(); //url위치로 입력스트림 연결

            //xml파서 생성
            //XmlPullParserFactory를 통해 XmlPullParser의 인스턴스를 만드는 방법
            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();//xml파싱을 위한

            //Xml 클래스의 newPullParser() 함수를 통해 XmlPullParser 인스턴스를 만드는 방법
            XmlPullParser xpp= factory.newPullParser();

            //파서가 처리할 입력 스트림을 지정하는 setInput() 함수가 존재하며, setInput() 함수를 사용하여 파서 외부에서 오픈된 입력 스트림을 처리
            xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부터 xml 입력받기

            String tag;

            xpp.next();
            int eventType= xpp.getEventType();

            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){
                    //	START_DOCUMENT : XML 문서의 시작. 파싱(Parsing)의 시작을 알림.
                    case XmlPullParser.START_DOCUMENT:
                        buffer.append("파싱 시작...\n\n");
                        break;

                    //START_TAG : XML 시작 태그. (ex. <TAG>).
                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();//테그 이름 얻어오기

                        if(tag.equals("item")) ;// 첫번째 검색결과


                        else if(tag.equals("word")){
                            buffer.append("표제어: ");
                            xpp.next();
                            buffer.append(xpp.getText());//CODE_VALUE 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n\n");//줄바꿈 문자 추가

                        }
                        else if(tag.equals("pos")){
                            buffer.append("품사 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n\n"); //줄바꿈 문자 추가
                        }

                        else if(tag.equals("definition")){
                            buffer.append("뜻풀이 :");
                            xpp.next();
                            buffer.append(xpp.getText());

                            buffer.append("\n\n");//줄바꿈 문자 추가
                        }

//                        else if(tag.equals("OP_TIME")){
//                            buffer.append("이용시간 :");
//                            xpp.next();
//                            buffer.append(xpp.getText());
//                            buffer.append("\n\n");//줄바꿈 문자 추가
//                        }
//                        else if(tag.equals("FDRM_CLOSE_DATE")){
//                            buffer.append("휴관일 :");
//                            xpp.next();
//                            buffer.append(xpp.getText());
//                            buffer.append("\n\n\n\n"); //줄바꿈 문자 추가
//                        }


                        break;

                    // 시작태그와 종료태그 사이 값을 만나는순간 (JSON으로 치면 key를 넣어서 값을 얻음)
                    // TEXT : XML에 포함된 텍스트. 기본적으로 시작 태그와 종료 태그 사이의 문자열. (ex. <TAG>TEXT</TAG>))
                    case XmlPullParser.TEXT://parser가 내용에 접근했을때


                        break;

                    // 종료태그를 만나는순간 (JSON으로 치면, value 찾고 이제 볼일없음)
                    case XmlPullParser.END_TAG:
                        tag= xpp.getName(); //테그 이름 얻어오기

                        if(tag.equals("item")) buffer.append("\n\n");// 첫번째 검색결과종료..줄바꿈

                        break;
                }

                eventType= xpp.next();


            }

        } catch (Exception e){
            e.printStackTrace();
        }




        buffer.append("더 이상 내용이 없습니다\n");





        return  buffer.toString();//StringBuffer 문자열 객체 반환


    }//getXmlData method....



}
