package com.example.ui_basetestdemo_food;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by 79463 on 2019/6/4.
 */

public class Task_6 extends Activity {
    private Button break6;
    private Button button_1;
    private Button button_2;
    private Button button_3;
    private Button button_4;

    static final int CMD_STOP_SERVICE = 0x01;       // Main -> service
    static final int CMD_SEND_DATA = 0x02;          // Main -> service
    static final int CMD_SYSTEM_EXIT =0x03;         // service -> Main
    static final int CMD_SHOW_TOAST =0x04;          // service -> Main
    static final int CMD_CONNECT_BLUETOOTH = 0x05;  // Main -> service
    static final int CMD_RECEIVE_DATA = 0x06;       // service -> Main


    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_6);

        break6=(Button)findViewById(R.id.button_break6);
        button_1=(Button)findViewById(R.id.button_1);
        button_2=(Button)findViewById(R.id.button_2);
        button_3=(Button)findViewById(R.id.button_3);
        button_4=(Button)findViewById(R.id.button_4);
        break6.setOnClickListener(new View.OnClickListener() {
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
        button_1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
                v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS,HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
                SendBlueToothProtocol("$6,1#");
			}
		});
       
        button_2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
                v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS,HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
                SendBlueToothProtocol("$6,2#");
			}
		});
        button_3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
                v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS,HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
                SendBlueToothProtocol("$6,3#");
			}
		});
        button_4.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
                v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS,HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
                SendBlueToothProtocol("$6,4#");
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
