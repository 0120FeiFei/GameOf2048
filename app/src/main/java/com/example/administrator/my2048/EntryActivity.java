package com.example.administrator.my2048;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        //开始2048游戏
        Button frist_game_btn = findViewById(R.id.frist_game_btn01);
        frist_game_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EntryActivity.this,GameFristActivity.class);
                startActivity(intent);
            }
        });
        //开始24点
        Button second_game_btn = findViewById(R.id.second_game_btn02);
        second_game_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EntryActivity.this,GameSecondActivity.class);
                startActivity(intent);
            }
        });
    }
}
