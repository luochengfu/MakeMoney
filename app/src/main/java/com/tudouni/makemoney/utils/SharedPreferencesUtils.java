package com.tudouni.makemoney.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/7/13 0013.
 */

public class SharedPreferencesUtils {
    public  static void putString(Context context,String name,String value){
        SharedPreferences sharedPreferences=context.getSharedPreferences(name,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putString(name,value);
        editor.commit();
    }
    public  static void putBoolean(Context context,String name,boolean value){
        SharedPreferences sharedPreferences=context.getSharedPreferences(name,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putBoolean(name,value);
        editor.commit();
    }
    public  static void putInt(Context context,String name,int value){
        SharedPreferences sharedPreferences=context.getSharedPreferences(name,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putInt(name,value);
        editor.commit();
    }

    public  static void putLong(Context context,String name,long value){
        SharedPreferences sharedPreferences=context.getSharedPreferences(name,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putLong(name,value);
        editor.commit();
    }
    public  static String getString(Context context,String name){
        SharedPreferences sharedPreferences=context.getSharedPreferences(name,Context.MODE_PRIVATE);
        return sharedPreferences.getString(name,"");
    }
    public  static boolean getBoolean(Context context,String name){
        SharedPreferences sharedPreferences=context.getSharedPreferences(name,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(name,false);
    }

    public  static boolean getBoolean(Context context,String name,boolean defaultState){
        SharedPreferences sharedPreferences=context.getSharedPreferences(name,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(name,defaultState);
    }

    public  static int getInt(Context context,String name){
        SharedPreferences sharedPreferences=context.getSharedPreferences(name,Context.MODE_PRIVATE);
        return sharedPreferences.getInt(name,0);
    }

    public  static long getLong(Context context,String name){
        SharedPreferences sharedPreferences=context.getSharedPreferences(name,Context.MODE_PRIVATE);
        return sharedPreferences.getLong(name,0l);
    }
}
