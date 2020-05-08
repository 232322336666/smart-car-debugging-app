package com.example.ui_basetestdemo_food;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

/**
 * Created by 79463 on 2019/6/4.
 */

public class Task_chukong extends Activity {

    private static final int msgShowConnect = 1;

    /**************service 命令*********/
    static final int CMD_STOP_SERVICE = 0x01;       // Main -> service
    static final int CMD_SEND_DATA = 0x02;          // Main -> service
    static final int CMD_SYSTEM_EXIT =0x03;         // service -> Main
    static final int CMD_SHOW_TOAST =0x04;          // service -> Main
    static final int CMD_CONNECT_BLUETOOTH = 0x05;  // Main -> service
    static final int CMD_RECEIVE_DATA = 0x06;       // service -> Main


    private Button break9;
    private FrameLayout frameLayout;
    @SuppressLint({"ClickableViewAccessibility", "WrongViewCast"})
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_chukong);

        break9=(Button)findViewById(R.id.button_break9);
        frameLayout= (FrameLayout)findViewById(R.id.frameLayout_chukong);
        final SmallBall smallBall =new SmallBall(this);
        smallBall.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int a1,a2,a3,a4,b1,b2,b3,b4;
                smallBall.bitmapX=event.getX();
                int x=(int )event.getX();
                smallBall.bitmapY=event.getY();
                int  y= (int) event.getY();
                String s1="$9";
                String s2="#";
                a1=x/1000;
                a2=x/100;
                a2=a2%10;
                a3=x/10;
                a3=a3%10;
                a4=x%10;
                b1=y/1000;
                b2=y/100;
                b2=b2%10;
                b3=y/10;
                b3=b3%10;
                b4=y%10;
                String s=s1+a1+a2+a3+a4+b1+b2+b3+b4+s2;//头帧+X+Y+尾帧
                SendBlueToothProtocol(s);
                smallBall.invalidate();
                return true;
            }
        });
        frameLayout.addView(smallBall);
        break9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS,HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
                String s1="$0";
                String s2="#";
                String s=s1+s2;
                SendBlueToothProtocol(s);
                finish();
            }
        });
    }

    private void SendBlueToothProtocol(String value ) {
        Intent intent = new Intent();//创建Intent对象
        intent.setAction("android.intent.action.cmd");
        intent.putExtra("cmd", CMD_SEND_DATA);
        intent.putExtra("command", (byte)0x00);
        intent.putExtra("value", value);
        sendBroadcast(intent);//发送广播
    }
}
