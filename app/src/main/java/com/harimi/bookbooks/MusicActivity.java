package com.harimi.bookbooks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.ArrayList;



public class MusicActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> arrayList;


    private Button btn_start, btn_stop;

    //음악 재생위치를 나타내는 식바
    SeekBar seekBar;
    //재생중인지 확인하는 변수
    boolean isPlaying = false;
    //재생 멈춘시점
    int pos;

    //서비스 클래스 액티비티에서 선언
    private MusicService musicService;

    // Activity내에서 Background Thread가 직접 View를 update하지 않기 위해서
    // Main Thread에 작업을 넘길 때 사용
    //UI스레드에 UI변경작업을 지시하기위해 선언
    private Handler handler = new Handler();
    //user event와 상관없이 장시간동안 service에 접근해서 데이터 획득하는 역할
    private Thread background;


    //bindService(): 내 액티비티에서 저 서비스 연결해서 뭔가 뽑아내고자할때


    /* 서비스 커넥션 선언
     서비스가 연결될 때 onServiceConnected 메서드가 호출되며
     이 때 인수로 전달되는 binder객체를 서비스 객체타입으로 캐스팅한다.
     이 서비스 객체를 구해두면 이후부터 이 객체의 메서드를 호출함으로써 서비스의 기능을 사용한다
    */
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            MusicService.ServiceBinder binder = (MusicService.ServiceBinder) service;
            musicService = binder.getService();
            // background.start(); //이 때부터 run()이 실행된다

        }


        //예기치 않은 종료
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            //onStopService();
            musicService=null;
        }
    };


    /*
    서비스 시작
     */
    private void onStartService() {
        Intent intent = new Intent(getApplicationContext(), MusicService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

        //  background=new Thread(null,this,"test");

    }

    /*
   서비스 종료
    */
    private void onStopService() {
        if (musicService != null) {
            unbindService(serviceConnection);
            musicService = null;

        }
        handler.removeCallbacksAndMessages(null);

    }

    ;


    //UI스레드에게 넘겨줄 작업
    //inner class-Runnable이 interface이므로
//    private Runnable updateBar=new Runnable() {
//        @Override
//        public void run() {
//            //UI스레드는 간단하게 짠다. 루프같은거 넣지 않는다
//            //bar가 새롭게 그려진다
//            seekBar.setProgress(musicService.getCurrentPosition());
//
//        }
//    };


//    //백버튼막기
//    @Override
//    public void onBackPressed() {
//        //super.onBackPressed();
//    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);


        listView = findViewById(R.id.list);

//        arrayList=new ArrayList<String>();
//
//        Field[] fields=R.raw.class.getFields();
//        for(int i=0; i<fields.length; i++){
//            arrayList.add(fields[i].getName());
//        }
//
//        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);
//        listView.setAdapter(adapter);
//
//
//
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
////                if(musicService.mediaPlayer !=null){
////                    musicService.mediaPlayer.release();
////                }
////
////                int resId=getResources().getIdentifier(arrayList.get(i),"raw",getPackageName());
////                musicService.mediaPlayer= MediaPlayer.create(MainActivity.this,resId);
////                musicService.mediaPlayer.start();
//
//                onStartService();
//
//
//            }
//        });



        btn_start = findViewById(R.id.btn_start);
        btn_stop = findViewById(R.id.btn_stop);

        // seekBar.setMax(musicService.getDuration()); // 음악의 총 길이를 시크바 최대값에 적용

//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
////                if(fromUser){ // 사용자가 시크바를 움직이면
////                    // 재생위치를 바꿔준다(움직인 곳에서의 음악재생)
////                    musicService.mediaPlayer.seekTo(progress);
////                }
//
//
////                if (seekBar.getMax() == progress) {
////                    isPlaying = false;
////                    onStopService();
////                }
//
//
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//                isPlaying = true;
//                onStopService();
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
////
////                isPlaying = true;
////                int ttt = seekBar.getProgress();
////                musicService.mediaPlayer.seekTo(ttt);
////                onStartService();
////                new Thread().start();
//
//
//            }
//        });


        //서비스 시작
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onStartService();
                Toast.makeText(MusicActivity.this, "서비스 시작", Toast.LENGTH_SHORT).show();

//                new Thread(new Runnable(){  // 쓰레드 생성
//                    @Override
//                    public void run() {
//                        while(musicService.mediaPlayer.isPlaying()){  // 음악이 실행중일때 계속 돌아가게 함
//                            try{
//                                Thread.sleep(1000); // 1초마다 시크바 움직이게 함
//                            } catch(Exception e){
//                                e.printStackTrace();
//                            }
//                            // 현재 재생중인 위치를 가져와 시크바에 적용
//                            seekBar.setProgress(musicService.mediaPlayer.getCurrentPosition());
//                        }
//                    }
//                }).start();


            }
        });

        //서비스 종료
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // musicService.mediaPlayer.stop();


                onStopService();
                Toast.makeText(MusicActivity.this, "서비스 종료", Toast.LENGTH_SHORT).show();
                //Log.e("서비스종료",String.valueOf(musicService.isFlag()));

            }
        });




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

        //설정버튼
        Button setbtn=(Button)findViewById(R.id.status_setting);
        setbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),SettingActivity.class);
                startActivity(intent);

            }
        });

    }


    //background thread가 작업할 내용을 가지는 메서드
    //background thread는 돌면서 1초마다
    //UI updateBar thread에게 progress를 표시하도록 post한다
//    @Override
//    public void run() {
//
//        seekBar.setMax(musicService.getDuration());
//        Log.e("flag갑 확인1 while 전",String.valueOf(!musicService.isFlag()));
//
//        while( !musicService.isFlag()) {
//
//            Log.e("flag값 확인2 while 후",String.valueOf(!musicService.isFlag()));
//
//            try{
//                Thread.sleep(1000);
//                // Progress Bar를 handling하자.
//                // background thread에 의해 view가 직접 조작된면 error가 발생할 수 있기에
//                // handler를 이용해서 UI Thread에게 조정을 의뢰한다.
//                //1초마다 main thread한테 update seekbar 하라고 날린다
//               handler.post(updateBar);
//
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//
//        }
//
//    }


}












