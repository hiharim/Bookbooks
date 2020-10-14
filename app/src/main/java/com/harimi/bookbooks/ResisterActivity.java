package com.harimi.bookbooks;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import java.util.Set;

public class ResisterActivity extends AppCompatActivity {

    /**
     * 회원가입 액티비티
     * 기능: 개인정보 동의, 비밀번호 일치 확인, 비어있는텍스트있나 확인
     */

    ResisterActivity resisterActivity = this;
    //SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resister);
        setTitle("회원가입");


        //이메일,비밀번호,비밀번호확인,이름
        final EditText etEmail = (EditText) findViewById(R.id.activity_resister_et_email);
        final EditText etPassword = (EditText) findViewById(R.id.activity_resister_et_pw);
        final EditText etPasswordConfirm = (EditText) findViewById(R.id.activity_resister_et_repw);
        final EditText etName = (EditText) findViewById(R.id.activity_resister_et_name);
        final CheckBox checkBox=(CheckBox)findViewById(R.id.activity_resister_cb);




        /*** 개인정보 동의 버튼*/
        Button agree_button;
        agree_button = findViewById(R.id.activity_resister_btn_agree);
        agree_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(resisterActivity);
                builder.setTitle("개인정보 정보 동의");
                builder.setMessage("Bookbooks 개인정보 취급방침\n" +
                        "정보통신망 이용촉진 등에 관한 법률 등 관련 법률에 의한 개인정보 보호규정 및 정보통신부가 제정한 개인정보지침을 준수하고 있습니다.\n" +
                        "\n" +
                        "1. 개인정보의 수집 이용 목적\n" +
                        "* 이메일, 비밀번호, 닉네임 : 회원 가입시에 사용자확인, 중복가입 방지, 부정 이용 방지를 위한 목적으로 사용합니다.\n" +
                        "* 이용자의 IP Address, 접속기록 등 : 불량회원의 부정 이용방지와 비인가 사용방지, 통계학적 분석에 사용합니다.\n" +
                        "\n" +
                        "2. 개인정보의 수집 항목\n" +
                        "원활한 서비스 이용과 고객과의 소통을 위해 수집합니다.\n" +
                        "* 필수항목: 이메일,비밀번호,이름\n" +
                        "* 자동수집항목: IP Address, 접속 기록, 쿠키 등\n" +
                        "\n" +
                        "3. 개인정보의 보유 및 이용기간\n" +
                        "-가입 회원정보는 탈퇴할 시 5년간 보관 후 파기됩니다.\n" +
                        "-단 게시물에서의 개인정보(닉네임 등)는 앱 폐쇄 시까지 보관합니다.\n");

                builder.setPositiveButton("동의", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        CheckBox checkBox = (CheckBox) findViewById(R.id.activity_resister_cb);
                        checkBox.setChecked(true);
                        Toast.makeText(resisterActivity, "동의되었습니다", Toast.LENGTH_SHORT).show();

                    }
                });

                builder.show();

            }
        });

        //비밀번호 일치 검사
        etPasswordConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String password = etPassword.getText().toString();
                String confirm = etPasswordConfirm.getText().toString();

                if (password.equals(confirm)) {
                    etPassword.setBackgroundColor(Color.GREEN);
                    etPasswordConfirm.setBackgroundColor(Color.GREEN);
                } else {
                    etPassword.setBackgroundColor(Color.RED);
                    etPasswordConfirm.setBackgroundColor(Color.RED);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        //가입버튼
        Button finalresistbtn = (Button) findViewById(R.id.가입);
        finalresistbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //개인정보 동의 확인
                if(!checkBox.isChecked()){
                    Toast.makeText(ResisterActivity.this, "개인정보를 동의를 확인하세요", Toast.LENGTH_SHORT).show();
                    checkBox.requestFocus();
                    return;
                }

                //이메일 입력 확인
                if (etEmail.getText().toString().length() == 0) {
                    Toast.makeText(ResisterActivity.this, "이메일을 입력하세요", Toast.LENGTH_SHORT).show();
                    etEmail.requestFocus();
                    return;
                }

                //비밀번호 입력 확인
                if (etPassword.getText().toString().length() == 0) {
                    Toast.makeText(ResisterActivity.this, "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
                    etPassword.requestFocus();
                    return;
                }

                //비밀번호확인 입력 확인
                if (etPasswordConfirm.getText().toString().length() == 0) {
                    Toast.makeText(ResisterActivity.this, "비밀번호 확인을 입력하세요", Toast.LENGTH_SHORT).show();
                    etPasswordConfirm.requestFocus();
                    return;
                }

                //비밀번호 일치 확인
                if (!etPassword.getText().toString().equals(etPasswordConfirm.getText().toString())) {
                    Toast.makeText(ResisterActivity.this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                    etPassword.setText("");
                    etPasswordConfirm.setText("");
                    etPassword.requestFocus();
                    return;
                }




//                SharedPreferences preferences=getSharedPreferences("MYPREFS",MODE_PRIVATE);
//                String newUser=etName.getText().toString();
//                String newPassword=etPassword.getText().toString();
//                String newEmail=etEmail.getText().toString();
//
//
//
//                SharedPreferences.Editor editor=preferences.edit();
//                editor.putString(newUser+newPassword+"data",newUser+"\n"+newEmail);

//                editor.commit();
//
//                Log.e("쉐어드","저장"+newUser+newPassword+newEmail);

//------------------------------------------------------------------------------------------------

                /* ACCOUNTS라는 KEY값으로 회원가입할때 받는 정보를 한번에 받는다
                   user정보를 회원가입할때마다 계속 sharedPreferences에 저장하기 위해
                   putString하는 key값을 입력값+user로 설정했다
                */
                //SharedPreferences preferences=getSharedPreferences("ACCOUNTS",MODE_PRIVATE);

                SharedPreferences preferences=getSharedPreferences("MEMBERS",MODE_PRIVATE);
                String userNameValue=etName.getText().toString();
                String passwordValue=etPassword.getText().toString();
                String EmailValue=etEmail.getText().toString();


                String userdata=EmailValue+","+passwordValue+","+userNameValue;


                if(userNameValue.length()>=1) {

                    SharedPreferences.Editor editor = preferences.edit();

                        //editor.putString(EmailValue+passwordValue+"user",EmailValue+passwordValue+userNameValue);


                        editor.putString(EmailValue+"",userdata);
                        editor.apply();


                    Toast.makeText(ResisterActivity.this, "회원가입 완료", Toast.LENGTH_SHORT).show();

                    Log.e("쉐어드","저장완료"+userNameValue+passwordValue+EmailValue);

                    Intent intent = new Intent();
                    intent.putExtra("이메일", etEmail.getText().toString());
                    //자신을 호출한 액티비티로 데이터를 보낸다
                    setResult(RESULT_OK, intent);
                    finish();


                }
                else{
                    Toast.makeText(ResisterActivity.this,"빈칸을 다 입력해주세요",Toast.LENGTH_SHORT).show();
                }


//------------------------------------------------------------------------------------------------------------------

//               Intent intent = new Intent();
//                intent.putExtra("이메일", etEmail.getText().toString());
//                //자신을 호출한 액티비티로 데이터를 보낸다
//                setResult(RESULT_OK, intent);
//                finish();





            }

        });



             //취소버튼
                Button cancelbtn = (Button) findViewById(R.id.취소);
                cancelbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                    Toast.makeText(ResisterActivity.this,"취소되었습니다",Toast.LENGTH_SHORT).show();
//                    startActivity(intent);
                    finish();
                }
                });



    }//onCreate


    //앱 종료시 가장 먼저 실행되기때문에 여기서 sharedPreferences 저장
    protected void onPause() {
        super.onPause();





    }//onPause




}//class