package com.example.ui_basetestdemo_food;

import com.example.ui_basetestdemo_food.MainActivity;
import com.example.ui_basetestdemo_food.Task_3.p_SeekBarListener;
import com.example.ui_basetestdemo_food.Task_3.i_SeekBarListener;
import com.example.ui_basetestdemo_food.Task_3.d_SeekBarListener;

import com.example.ui_basetestdemo_food.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;

/**
 * Created by 79463 on 2019/6/4.
 */

public class Task_3 extends Activity{
    private Button break3;
    private SeekBar p_seekbar;
    private SeekBar i_seekbar;
    private SeekBar d_seekbar;
	private p_SeekBarListener p_seekBarListener;
	private i_SeekBarListener i_seekBarListener;
	private d_SeekBarListener d_seekBarListener;
	private int p=0,i=0,d=0;
    static final int CMD_STOP_SERVICE = 0x01;       // Main -> service
    static final int CMD_SEND_DATA = 0x02;          // Main -> service
    static final int CMD_SYSTEM_EXIT =0x03;         // service -> Main
    static final int CMD_SHOW_TOAST =0x04;          // service -> Main
    static final int CMD_CONNECT_BLUETOOTH = 0x05;  // Main -> service
    static final int CMD_RECEIVE_DATA = 0x06;       // service -> Main

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_3);

        break3=(Button)findViewById(R.id.button_break3);
        p_seekbar=(SeekBar)findViewById(R.id.p_seekbar);
        i_seekbar=(SeekBar)findViewById(R.id.i_seekbar);
        d_seekbar=(SeekBar)findViewById(R.id.d_seekbar);
        
		p_seekbar.setProgress(0);
		i_seekbar.setProgress(0);
		d_seekbar.setProgress(0);
        
        
        break3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS,HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);;
                String s1="$0";
                String s2="#";
                String s=s1+s2;
                SendBlueToothProtocol(s);
                finish();
            }
        });
        
		p_seekBarListener = new p_SeekBarListener();
		p_seekbar.setOnSeekBarChangeListener(p_seekBarListener);
		i_seekBarListener = new i_SeekBarListener();
		i_seekbar.setOnSeekBarChangeListener(i_seekBarListener);
		d_seekBarListener = new d_SeekBarListener();
		d_seekbar.setOnSeekBarChangeListener(d_seekBarListener);
        
    }
    
	class p_SeekBarListener implements OnSeekBarChangeListener {

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			p = seekBar.getProgress();
			Toast.makeText(Task_3.this, "p设置为" + p, 1000).show();
			String MyString = ""+ p ;
			SendBlueToothProtocol("$2"+ MyString +"#");
		}

	}
	
	class i_SeekBarListener implements OnSeekBarChangeListener {

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			i = seekBar.getProgress();
			Toast.makeText(Task_3.this, "i设置为" + i, 1000).show();
			String MyString = ""+ i ;
			SendBlueToothProtocol("$2"+ MyString +"#");
		}

	}
	
	class d_SeekBarListener implements OnSeekBarChangeListener {

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			d = seekBar.getProgress();
			Toast.makeText(Task_3.this, "d设置为" + d, 1000).show();
			String MyString = ""+ d ;
			SendBlueToothProtocol("$2"+ MyString +"#");
		}

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
