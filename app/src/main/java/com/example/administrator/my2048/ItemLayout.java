package com.example.administrator.my2048;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Administrator on 2019/4/8.
 */

public class ItemLayout extends RelativeLayout {
    public int numItemCol = 4; //numItemCol*numItemCol小方格
    private int mPadding; //页内边距
    private int mMargin = 10; //小方格之间的距离
    private  Item2048[] item2048s = null; //小方格矩阵
    private boolean flag = true; //绘制界面标志，只绘制一次界面
    private My2048Listener  myItem2048Listener;
    private GestureDetector gestureDetector;
    private boolean isMerge = true;
    private boolean isMove = true;
    private int myScore = 0;

    public ItemLayout(Context context) {
        this(context,null,0);
    }

    public ItemLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPadding = Math.min(getPaddingBottom(),getPaddingLeft());
        gestureDetector = new GestureDetector(context,new MyGestureDetector());
    }

    //重写onMeasure()
    @Override
    public void onMeasure(int widthMeasureSpec,int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        int length = Math.min(getMeasuredHeight(),getMeasuredWidth());
        int ItemWidth = (length - mPadding*2 - mMargin*(numItemCol-1))/numItemCol;
        if(flag){
            if(item2048s == null){
                item2048s = new Item2048[numItemCol*numItemCol];//生成大小为numItemCol*numItemCol的矩阵盛放小方格Item2048
            }
            for(int i = 0 ; i < item2048s.length ; i++){
                Item2048 item2048 = new Item2048(getContext());
                item2048s[i] = item2048;
                item2048.setId(i+1); //每个小方格设置id
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ItemWidth,ItemWidth);
                if((i+1)%numItemCol != 0){ //不是最后一列，设置右边距
                    layoutParams.rightMargin = mMargin;
                }
                if(i%numItemCol != 0){//不是第一列，出现在前一个Item2048的右边
                    layoutParams.addRule(RelativeLayout.RIGHT_OF,item2048s[i-1].getId());
                }
                if((i+1)>numItemCol){//不是第一行，设置上边距，并出现在上一行的下面
                    layoutParams.topMargin = mMargin;
                    layoutParams.addRule(RelativeLayout.BELOW,item2048s[i-numItemCol].getId());
                }
                addView(item2048,layoutParams);
            }
            generateNum();//随机出现一个数字
        }
        flag = false;
        setMeasuredDimension(length,length);//修改布局空间
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        gestureDetector.onTouchEvent(motionEvent);
        return true;
    }
    private enum ACTION{
        LEFT,RIGHT,UP,DOWN
    }
    private class MyGestureDetector implements GestureDetector.OnGestureListener{
        final int FLING_MIN_DISTANCE = 50;

        @Override
        public boolean onDown(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {

        }

        @Override
        /*
        * event1：起始手势位置
        * event2：当前手势位置
        * vx：水平方向像素滚动速度
        * vy：垂直方向像素滚动速度
         */
        public boolean onFling(MotionEvent event1, MotionEvent event2, float vx, float vy) {

            float x = event2.getX() - event1.getX();//x方向，手势距离
            float y = event2.getY() - event1.getY();//y方向，手势距离
            if(x > FLING_MIN_DISTANCE && Math.abs(vx) > Math.abs(vy)){
                action(ACTION.RIGHT);
            }
            if(x < -FLING_MIN_DISTANCE && Math.abs(vx) > Math.abs(vy)){
                action(ACTION.LEFT);
            }
            if(y > FLING_MIN_DISTANCE && Math.abs(vy) > Math.abs(vx)){
                action(ACTION.UP);
            }
            if(y <- FLING_MIN_DISTANCE && Math.abs(vy) > Math.abs(vx)){
                action(ACTION.DOWN);
            }
            return true;
        }

        //游戏数字“移动”逻辑
        private void action(ACTION action) {
            for(int i = 0 ; i < numItemCol ;i++){
                ArrayList<Item2048> itemList = new ArrayList<Item2048>();//上下移动，按列存储，左右移动，按行存储
                for(int j = 0 ; j < numItemCol ; j++){
                    int index = getIndexByAction(action,i,j);
                    Item2048 item = item2048s[index];
                    if(item.getNumber() != 0){
                        itemList.add(item);
                    }
                }
                for(int j = 0 ; j < numItemCol ; j++){
                    int index = getIndexByAction(action, i ,j);
                    Item2048 item = item2048s[index];
                    if(itemList.size() > j){
                        if(item.getNumber() != itemList.get(j).getNumber()){
                            isMove = true;
                            break;
                        }
                    }
                }
                mergeItem(itemList);//合并
                //设置合并后的值
                for(int j = 0 ; j < numItemCol ; j++){
                    int index = getIndexByAction(action , i , j);
                    if(itemList.size() > j){
                        item2048s[index].setNumber(itemList.get(j).getNumber());
                    }else{
                        item2048s[index].setNumber(0);
                    }
                }
            }
            generateNum();
        }

        private void mergeItem(ArrayList<Item2048> itemList) {
            if(itemList.size()<2){
                return;
            }
            for(int j = 0 ; j < itemList.size()-1;j++){
                Item2048 item1 = itemList.get(j);
                Item2048 item2 = itemList.get(j+1);
                if(item1.getNumber() == item2.getNumber()){
                    isMerge = true;
                    int temp = item1.getNumber()*2;
                    item1.setNumber(temp);
                    myScore += temp; //加分
                    if(myItem2048Listener != null){
                        myItem2048Listener.onScoreChange(myScore);
                        itemList.remove(j+1);
                    }
                }
            }
        }

        private int getIndexByAction(ACTION action, int i, int j) {
            int index = -1;
            switch (action){
                case RIGHT:index = numItemCol*i+numItemCol-1-j;
                break;
                case LEFT:index = numItemCol*i+j;
                break;
                case UP:index = numItemCol*(numItemCol-1)+i-numItemCol*j;
                break;
                case DOWN:index = i+j*numItemCol;
                break;
            }
            return index;
        }

    }

    //产生随机数字
    private void generateNum(){
        if(checkOver()){ // checkOver() 检查游戏是否结束
            if(myItem2048Listener != null){
                myItem2048Listener.onGameOver();
            }
            return;
        }
        if(isSuccess()){// checkOver() 检查游戏是否成功
            if(myItem2048Listener != null){
                myItem2048Listener.onGameSuccessfull();
            }
            return;
        }
        if(isMerge || isMove){
            Random random = new Random();
            int next = random.nextInt(numItemCol*numItemCol);
            Item2048 item2048 = item2048s[next];
            while(item2048.getNumber() != 0){
                next = random.nextInt(numItemCol*numItemCol);
                item2048 = item2048s[next];
            }
            item2048.setNumber(Math.random()>0.5?4:2);
            isMove = false;
            isMerge = false;
        }
    }

    //判断游戏是否结束
    private boolean checkOver(){
        if(!isFull()){
            return false; //未满，返回false
        }
        for(int i = 0 ; i < numItemCol ; i++){
            for(int j = 0 ; j < numItemCol ; j++){
                int index = i*numItemCol+j;
                Item2048 item = item2048s[index];
                if((index+1)%numItemCol != 0){//不是最后一列，判断其与右边相邻的数字是否相同
                    Item2048 itemRight = item2048s[index+1];
                    if(item.getNumber() == itemRight.getNumber()){
                        return false;
                    }
                }
                if(index % numItemCol != 0){//不是第一列，判断与左边相邻数字是否相同
                    Item2048 itemLeft = item2048s[index-1];
                    if(item.getNumber() == itemLeft.getNumber()){
                        return false;
                    }
                }
                if(index < numItemCol*(numItemCol-1)){ //不是最后一行，比较下边的相邻数字是否相同
                    Item2048 itemDown= item2048s[index+numItemCol];
                    if(item.getNumber() == itemDown.getNumber()){
                        return false;
                    }
                }
                if(index+1 > numItemCol){ //不是最后一行，比较上边的相邻数字是否相同
                    Item2048 itemUp = item2048s[index-numItemCol];
                    if(item.getNumber() == itemUp.getNumber()){
                        return false;
                    }
                }
            }
        }
        return true;
    }
    //判断游戏是否成功
    public boolean isSuccess(){
        boolean flagS = false;
        for(int i = 0 ; i < item2048s.length ; i++){
            if(item2048s[i].getNumber() == 2048){
                flagS = true;
                break;
            }
        }
        return flagS;
    }

    private boolean isFull() {
        for(int i = 0 ; i < item2048s.length ; i++){
            if(item2048s[i].getNumber() == 0){
                return false;
            }
        }
        return true;
    }

    public interface My2048Listener{
     void onScoreChange(int score);
     void onGameOver();
     void onGameSuccessfull();
    }
    public void setMy2048Listener(My2048Listener my2048Listener){
        this.myItem2048Listener = my2048Listener;
    }
}
