package com.example.administrator.my2048;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

public class GameActivity extends AppCompatActivity implements ItemLayout.My2048Listener {

    private ItemLayout itemLayout;
    private TextView myScore;
    private ImageButton imageButtonBack;
    private ImageButton imageButtonClear;
    private ImageButton imageButtonQuestion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        myScore = (TextView) findViewById(R.id.total_score_title);
        itemLayout = (ItemLayout) findViewById(R.id.item_layout);
        itemLayout.setMy2048Listener(this);

        imageButtonBack = (ImageButton) findViewById(R.id.back);
        imageButtonClear = (ImageButton)findViewById(R.id.clear);
        imageButtonQuestion = (ImageButton)findViewById(R.id.question);

        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameActivity.this,EntryActivity.class);
                startActivity(intent);
                Log.d("back", "onClick: 返回上一步");
            }
        });

        imageButtonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("clear","onClick:刷新");
            }
        });

        //弹出游戏说明
        imageButtonQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              AlertDialog.Builder dialog = new AlertDialog.Builder(GameActivity.this);
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
    public void onGameSuccessfull(){
        new AlertDialog.Builder(this).setTitle("游戏成功").setMessage(myScore.getText()).
                setPositiveButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }).show();
    }
}
