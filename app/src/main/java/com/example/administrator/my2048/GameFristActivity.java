package com.example.administrator.my2048;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;

public class GameFristActivity extends AppCompatActivity implements ItemLayout.My2048Listener {

    private ItemLayout itemLayout;
    private TextView myScore;
    private Item2048[] item2048s ;
    protected TextView topScoreView;
    private TopScore topScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_frist);
        topScore = new TopScore(this);
        myScore = findViewById(R.id.total_score_title);
        topScoreView = findViewById(R.id.top_score);
        topScoreView.setText("历史记录："+topScore.getTopScore()); //数据存储,闪退，，，忘记实例化，
        itemLayout = findViewById(R.id.item_layout);
        itemLayout.setMy2048Listener(this);

        ImageButton imageButtonRefresh = findViewById(R.id.refresh);
        ImageButton imageButtonQuestion = findViewById(R.id.question);
        imageButtonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item2048s = itemLayout.getItem2048s();
                for(int i = 0 ; i < 16 ; i++){
                    item2048s[i].setNumber(0);
                }
                Random random = new Random();
                int randomIndex = random.nextInt(16);
                Item2048 item2048 = item2048s[randomIndex];
                item2048.setNumber(Math.random()>0.5?4:2);
                itemLayout.setMyScore(0);
                int score = itemLayout.getMyScore();
                onScoreChange(score);
            }
        });

        //弹出游戏说明
        imageButtonQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              AlertDialog.Builder dialog = new AlertDialog.Builder(GameFristActivity.this);
              dialog.setTitle("游戏说明");
              dialog.setMessage("根据上下左右手势移动小方格，当遇到相邻小方格的数字在手势方向上相等时，则合并，直至出现2048游戏"+
                      "成功结束，若所有小方格均有数字且无2048，则游戏失败");
              dialog.setNegativeButton("退出", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialogInterface, int i) {
                      dialogInterface.dismiss();
                  }
              });
              dialog.show();
            }
        });
    }

    @Override
    public void onScoreChange(int score) {
        String scoreStr = String.valueOf(score);
        myScore.setText("得分："+scoreStr);
    }

    @Override
    public void onGameOver() {
        new AlertDialog.Builder(this).setTitle("游戏结束").setMessage(myScore.getText()).
                setPositiveButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }).show();
    }
    public void onGameSuccessful(){
        new AlertDialog.Builder(this).setTitle("游戏成功").setMessage(myScore.getText()).
                setPositiveButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }).show();
    }
    public void onTopScore(int score){
        if(topScore.getTopScore() < score){
            topScore.setTopScore(score);
        }
    }
}
