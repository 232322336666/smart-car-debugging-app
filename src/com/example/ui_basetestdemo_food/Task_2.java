package com.example.ui_basetestdemo_food;

import com.example.ui_basetestdemo_food.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;

/**
 * Created by 79463 on 2019/6/4.
 */

public class Task_2 extends Activity{
    private Button break2;
    private Button button_3;
    private Button button_5;
    private Button button_10;
    private Button button_15;
    private Button button_run;
    static final int CMD_STOP_SERVICE = 0x01;       // Main -> service
    static final int CMD_SEND_DATA = 0x02;          // Main -> service
    static final int CMD_SYSTEM_EXIT =0x03;         // service -> Main
    static final int CMD_SHOW_TOAST =0x04;          // service -> Main
    static final int CMD_CONNECT_BLUETOOTH = 0x05;  // Main -> service
    static final int CMD_RECEIVE_DATA = 0x06;       // service -> Main

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_2);

        break2=(Button)findViewById(R.id.button_break2);
        button_3 =(Button) findViewById(R.id.button_3);
        button_5 = (Button)findViewById(R.id.button_5);
        button_10 = (Button)findViewById(R.id.button_10);
        button_15 = (Button)findViewById(R.id.button_15);
        button_run = (Button)findViewById(R.id.button_run);
        
        break2.setOnClickListener(new View.OnClickListener() {
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
        
        button_3.setOnClickListener(new View.OnClickListener() {
			
 			@Override
 			public void onClick(View v) {
 				// TODO Auto-generated method stub
                 v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS,HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
                 SendBlueToothProtocol("$1,1#");
 			}
 		});
        
        button_5.setOnClickListener(new View.OnClickListener() {
 			
 			@Override
 			public void onClick(View v) {
 				// TODO Auto-generated method stub
                 v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS,HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
                 SendBlueToothProtocol("$1,2#");
 			}
 		});
        button_10.setOnClickListener(new View.OnClickListener() {
 			
 			@Override
 			public void onClick(View v) {
 				// TODO Auto-generated method stub
                 v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS,HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
                 SendBlueToothProtocol("$1,3#");
 			}
 		});
        button_15.setOnClickListener(new View.OnClickListener() {
 			
 			@Override
 			public void onClick(View v) {
 				// TODO Auto-generated method stub
                 v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS,HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
                 SendBlueToothProtocol("$1,4#");
 			}
 		});
        button_run.setOnClickListener(new View.OnClickListener() {
 			
 			@Override
 			public void onClick(View v) {
 				// TODO Auto-generated method stub
                 v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS,HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
                 SendBlueToothProtocol("$1,5#");
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
