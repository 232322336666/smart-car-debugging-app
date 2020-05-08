package com.example.ui_basetestdemo_food;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;

/**
 * Created by 79463 on 2019/6/4.
 */

public class Task_8 extends Activity {
    private Button break8;
    private Button mbchukong;
    private Button mbjiguang;
    static final int CMD_SEND_DATA = 0x02;          // Main -> service
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_8);

        break8=(Button)findViewById(R.id.button_break8);
        mbchukong=(Button)findViewById(R.id.button_chukong);
        mbjiguang=(Button)findViewById(R.id.button_jiguang);

        break8.setOnClickListener(new View.OnClickListener() {
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
        mbjiguang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS,HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
                SendBlueToothProtocol("$8,0,0,0,0,0,0,0,0,0#");
                Intent intent =new Intent(Task_8.this,Task_jiguang.class);
                startActivity(intent);
            }
        });
        mbchukong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS,HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
                Intent intent =new Intent(Task_8.this,Task_chukong.class);
                startActivity(intent);
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
