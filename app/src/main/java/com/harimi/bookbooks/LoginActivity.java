package com.harimi.bookbooks;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

/**
 loginActivity
 기능
 1. 로그인체크
 2. 자동로그인
 3. AsyncTask를 이용한 원형 progressDialog스레드
 4. 회원가입버튼
 5. 계정찾기버튼
 */
public class LoginActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPass;
    private CheckBox checkBox;


    public static LottieAnimationView animationView;

    Context context;
    boolean isChecked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context=this;

        etEmail=(EditText)findViewById(R.id.activity_login_et_email);
        etPass=(EditText)findViewById(R.id.activity_login_et_pw);
        //checkBox=(CheckBox)findViewById(R.id.activity_login_cb);


        animationView = (LottieAnimationView) findViewById(R.id.lav_loading);
        animationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //animationView.setVisibility(View.);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });



//        boolean boo=pref.getValue("SaveLogin",false);
//        if(boo){ //자동로그인 체크가 되있다면 아래 코드 수행
//            etEmail.setText(pref.getValue("loginemail",null));
//            etPass.setText(pref.getValue("loginpassword",null));
//            checkBox.setChecked(true); //체크박스는 여전히 체크표시
//
//        }else{
//            pref=new RbPreference(this);
//        }


       // pref=new RbPreference(this);




        //회원가입버튼
        Button resisterbtn=(Button)findViewById(R.id.activity_login_resister_btn);
        resisterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),ResisterActivity.class);
                //SINGLE_TOP :이미 만들어진게 있으면 그걸 쓰고, 없으면 만들어서 써라
                intent.setFlags(intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(intent,11);

            }
        });







        //로그인버튼
        Button loginbtn=(Button)findViewById(R.id.activity_login_btn);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* MEMBERS라는 KEY값으로 sharedPreferences에 저장했던 정보를 불러온다
                  로그인할 때 조건문으로 edittext에 입력한 이메일과 비밀번호가 동시에 userDetails에 포함되면 로그인하게 했다
               */
                SharedPreferences preferences=getSharedPreferences("MEMBERS",MODE_PRIVATE);
                String userEmailValue=etEmail.getText().toString();
                String passwordValue=etPass.getText().toString();

                //String userDetails=preferences.getString(userEmailVaule+passwordValue+"user","");

                /*
                 저장된 사용자 정보를 꺼내기 위해서 key값인 userEmailVaule로 사용자정보를 불러온다
                 */
                String account=preferences.getString(userEmailValue+"","");

                /*
                split함수를 이용해서 , 콤마를 기준으로 사용자정보에서 이메일과 비밀번호의 값을 알아낸다
                */
                String[] values=account.split(",");
                for(int x=0; x<values.length; x++){
                    Log.e("SPLIT테스트","값"+(x+1)+"="+values[x]);

                    //입력한이메일,비밀번호와 저장된 이메일,비밀번호가 같으면 로그인 성공
                    if(userEmailValue.equals(values[x]) && passwordValue.equals(values[x+1])){
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                        /*
                         사용자가 로그인했다는 사실을 앱이 알기위해서 RbPreferences클래스에 로그인한 이메일을 저장한다
                         그럼 다른액티비티에서 계속 이 클래스에서 사용자정보를 꺼내서 쓸 수 있다
                         */
                        RbPreference pref = new RbPreference(getApplicationContext());
                        pref.put("loginemail", userEmailValue);
                        startActivity(intent);


                        //AsyncTask 실행
                        ProgressTask task=new ProgressTask();
                        task.execute();


                        Toast.makeText(LoginActivity.this,values[x+2]+"님 로그인 되었습니다",Toast.LENGTH_SHORT).show();
                        finish();

                        break;


                    }else{
                        Toast.makeText(LoginActivity.this,"이메일과 비밀번호를 확인해주세요",Toast.LENGTH_SHORT).show();

                    }



                }


//                if(userDetails.contains(userEmailVaule) && userDetails.contains(passwordValue) && userEmailVaule.length()>1&& passwordValue.length()>1){
//
//                    Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
//                    intent.putExtra("userEmail",userEmailVaule);
//                    //intent.putExtra("userPassword",passwordValue);
//                    startActivity(intent);
//                    Toast.makeText(LoginActivity.this,"로그인 되었습니다",Toast.LENGTH_SHORT).show();
//
//                    finish();
//
//
//                }else{
//                    Toast.makeText(LoginActivity.this,"이메일과 비밀번호를 확인해주세요",Toast.LENGTH_SHORT).show();;
//
//                }




            }
        });


        //자동로그인
        //TODO : https://www.youtube.com/watch?v=3Zrwi3FFrC8 자동로그인 코드 구현해야함

//        checkBox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if(((CheckBox)view).isChecked()){
//
//                    pref.put("loginemail",userEmailValue);
//                    pref.put("loginpassword",passwordValue);
//                    pref.put("SaveLogin",((CheckBox)view).isChecked());
//
//
//                }else{
//                    pref.put("loginsave",((CheckBox)view).isChecked());
//                    pref.clear(context);
//                }
//
//
//
//            }
//        });






//        //계정찾기버튼
//        Button findaccbtn=(Button)findViewById(R.id.activity_logint_find_account_btn);
//        findaccbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(getApplicationContext(),FindAccountActivity.class);
//                startActivity(intent);
//            }
//        });


    }//onCreate

    protected void onActivityResult(int requestCode,int resultCode,Intent intent) {

        super.onActivityResult(requestCode, resultCode, intent);
        if(requestCode==11 && resultCode==RESULT_OK){
            Toast.makeText(LoginActivity.this,"회원가입이 완료되었습니다",Toast.LENGTH_SHORT).show();
            etEmail.setText(intent.getStringExtra("이메일"));

        }
    }




    //AsyncTask 실행이 완료되면 다시 실행할 수 없다. --> 일회성 동작!
    class ProgressTask extends AsyncTask<Void, Integer, Void> {
        private ProgressDialog progressDialog=null ;
        public ProgressTask() { super(); }



        @Override
        // doInBackground 전에 실행(UI Thread) - 백그라운드 작업 전 초기화 부분
        // background작업이 시작되기 전 호출된다
        // 작업시작 ProgressDialog객체를 생성하고 시작한다
        protected void onPreExecute() {
            super.onPreExecute();
            // ProgressDialog 생성, 레이아웃 변경
            progressDialog = new ProgressDialog(LoginActivity.this, android.R.style.Theme_Material_Dialog_Alert);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("로그인 중...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }


        @Override
        // 백그라운드 작업 시작, UI 조작 불가, onPreExecute() 종료후 바로 호출된다
        // background 작업 구간에 호출된다 - 변수의 타입으로 Params 를 사용한다
        // 작업 진행중 ProgressDialog의 진행정도를 표현해준다
        protected Void doInBackground(Void... params) {

//            try{
//                for(int i=0; i<5; i++){
//                   // progressDialog.setProgress(i*30);
//                    Thread.sleep(500);
//                }
//
//           }catch (Exception e){
//                e.printStackTrace();
//            }


                //에러 내용: Activity has leaked window Error
                /*오류났던이유 : doInBackground()함수 안에 반복문코드를 잘못 짜놔서 doInBackground()가 계속 반복하고있었음
                그래서 dialog가 dismiss()되지 않았는데 액티비티는 꺼졌다는 오류가 계속 났었다
                AsyncTask는 비동기적 작업이라 메인스레드와 별개로 작동하기때문에 액티비티의 finish()의 위치가 어디있든 asyncTask작업이 끝나는것과 상관이 없다
                */
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            return null;
        }



        @Override
        // UI 조작가능 (UI Thread에서 실행)
        // donInBackground가 실행되는 도중에 호출된다-로딩할때 프로그래스바 컨트롤, 변수의 타입으로 Progress를 사용
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);


        }

        @Override
        // UI Thread에서 실행, doInBackground 종료 후 바로 호출
        // 변수의 타입으로 Result를 사용한다
        // 작업종료 ProgressDialog 종료 기능을 구현
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
          // progressDialog.dismiss();


            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }

        }
    }








}//class