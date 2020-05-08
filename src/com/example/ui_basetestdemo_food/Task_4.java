package com.example.ui_basetestdemo_food;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
/**
 * Created by 79463 on 2019/6/4.
 */

public class Task_4 extends Activity {
    private Button break4;
    private ImageView imageview;
    private Bitmap mbitmap;
    static final int CMD_STOP_SERVICE = 0x01;       // Main -> service
    static final int CMD_SEND_DATA = 0x02;          // Main -> service
    static final int CMD_SYSTEM_EXIT =0x03;         // service -> Main
    static final int CMD_SHOW_TOAST =0x04;          // service -> Main
    static final int CMD_CONNECT_BLUETOOTH = 0x05;  // Main -> service
    static final int CMD_RECEIVE_DATA = 0x06;       // service -> Main

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_4);

        break4=(Button)findViewById(R.id.button_break4);
        imageview = (ImageView)findViewById(R.id.imageview);
        //  #35 $36
        break4.setOnClickListener(new View.OnClickListener() {
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
/*        
        InputStream inputStream=getResources().openRawResource(R.raw.camera);
        
        int lenght = 0;
		try {
			lenght = inputStream.available();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        byte[] data = new byte[lenght];
        try {
			inputStream.read(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        mbitmap = Bitmap.createBitmap(320, 240, Bitmap.Config.RGB_565);
        ByteBuffer buffer = ByteBuffer.wrap(data);
        mbitmap.copyPixelsFromBuffer(buffer);
        imageview.setImageBitmap(mbitmap);
*/        
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
