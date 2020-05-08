package com.example.ui_basetestdemo_food;

import com.example.ui_basetestdemo_food.R;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by 79463 on 2019/6/4.
 */

public class Task_1 extends Activity {

    private Button break1;
    private Button button_steer;
    private Button button_machine;
    private Button button_beer;
    private Button button_coder;
    private Button button_gyro;
    
/*  private TextView textView;
    private TextView textView1;*/
    
    static final int CMD_STOP_SERVICE = 0x01;       // Main -> service
    static final int CMD_SEND_DATA = 0x02;          // Main -> service
    static final int CMD_SYSTEM_EXIT =0x03;         // service -> Main
    static final int CMD_SHOW_TOAST =0x04;          // service -> Main
    static final int CMD_CONNECT_BLUETOOTH = 0x05;  // Main -> service
    static final int CMD_RECEIVE_DATA = 0x06;       // service -> Main
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_1);
//        AssetManager mgr=getAssets()
//        Typeface tf=Typeface.createFromAsset(mgr,"STXINGKA.TTF");

        break1 = (Button)findViewById(R.id.button_break1);
        button_steer = (Button)findViewById(R.id.button_steer);
        button_machine =(Button) findViewById(R.id.button_machine);
        button_beer =(Button) findViewById(R.id.button_beer);
        button_coder = (Button)findViewById(R.id.button_coder);
        button_gyro =(Button) findViewById(R.id.button_gyro);
        
        break1.setOnClickListener(new View.OnClickListener() {//创建事件监听器
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
        
        button_steer.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
                v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS,HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
                SendBlueToothProtocol("$1,1#");
			}
		});
       
        button_machine.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
                v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS,HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
                SendBlueToothProtocol("$1,2#");
			}
		});
        button_beer.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
                v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS,HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
                SendBlueToothProtocol("$1,3#");
			}
		});
        button_coder.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
                v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS,HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
                SendBlueToothProtocol("$1,4#");
			}
		});
        button_gyro.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
                v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS,HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
                SendBlueToothProtocol("$1,5#");
			}
		});
        
/*      textView=findViewById(R.id.textView);
        textView1=findViewById(R.id.textView1);
        textView.setTypeface(tf);
        textView1.setTypeface(tf);*/
        
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
