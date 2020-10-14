package com.harimi.bookbooks;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;


/**
 * 앱에서 로그인 유지할때의 특정 클래스
 * 로그인하는 액티비티에서 로그인을 성공하였을 떼 특정 키값으로 값들을 넣어놓는다
 * 이 값들은 특정한명의 회원의 아이디,비번,이름 듣이다.
 * 이 값들을 로그인이 성공하는 수간 넣어준다
 * 그리고 이 값들은 나중에 다른 액티비티에서 쓰면된다다 *
 * */
public class RbPreference {

    private final String PREF_NAME="";
    public final static String PREF_INTRO_USER_AGREEMENT="PREF_USER_AGREEMENT";
    public final static String PREF_MAIN_VALUE="PREF_MAIN_VALUE";
    static Context context;

    public RbPreference(Context c) {
        context=c;
    }



    public void put(String key,String value){
        SharedPreferences pref=context.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor=pref.edit();
        editor.putString(key,value);
        editor.apply();
    }



    public void put(String key,boolean value){
        SharedPreferences pref=context.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor=pref.edit();
        editor.putBoolean(key,value);
        editor.apply();
    }


    public void put(String key,int value){
        SharedPreferences pref=context.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor=pref.edit();
        editor.putInt(key,value);
        editor.apply();
    }


    public String getValue(String key,String dftVaule){
        SharedPreferences pref=context.getSharedPreferences(PREF_NAME,Activity.MODE_PRIVATE);
        try{
            return  pref.getString(key,dftVaule);
        }catch (Exception e){
            return dftVaule;
        }
    }

    public int getValue(String key,int dftVaule){
        SharedPreferences pref=context.getSharedPreferences(PREF_NAME,Activity.MODE_PRIVATE);
        try{
            return  pref.getInt(key,dftVaule);
        }catch (Exception e){
            return dftVaule;
        }
    }

    public boolean getValue(String key,boolean dftVaule){
        SharedPreferences pref=context.getSharedPreferences(PREF_NAME,Activity.MODE_PRIVATE);
        try{
            return  pref.getBoolean(key,dftVaule);
        }catch (Exception e){
            return dftVaule;
        }
    }


    public void clear(Context context){
        SharedPreferences pref=context.getSharedPreferences(PREF_NAME,Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit=pref.edit();
        try{
            edit.clear();
            edit.apply();
        }catch (Exception e){
            return;
        }

    }









}//class
