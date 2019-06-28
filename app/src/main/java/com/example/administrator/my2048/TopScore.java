package com.example.administrator.my2048;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 利用SharedPreferences进行数据存储，原理采用键值对存储
 * Created by Administrator on 2019/6/26.
 */

class TopScore {
    private SharedPreferences sp ;
    TopScore(Context context){
        sp = context.getSharedPreferences("TopScore",Context.MODE_PRIVATE);
    }
    int getTopScore(){
        return sp.getInt("TopScore",0);
    }
    void setTopScore(int score){
        Editor editor = sp.edit();
        editor.putInt("TopScore", score);
        editor.apply();
    }
}
