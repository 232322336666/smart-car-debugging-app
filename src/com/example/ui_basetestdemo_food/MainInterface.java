package com.example.ui_basetestdemo_food;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by 79463 on 2019/6/2.
 */

public class MainInterface extends Activity {
    private BluetoothAdapter bluetoothAdapter;
    private Switch bluetoothSwitch;
    private Button bluetoothSearch;
    private Spinner bluetoothList;
    private Button bluetoothConnect;
    private ArrayAdapter<String> adapter;
    private List<String> list = new ArrayList<String>();
    private String strMacAddress;
    private static boolean booleanConnect = false;


    /*方向按钮定义*/

    private Button task_1;
    private Button task_2;
    private Button task_3;
    private Button task_4;
    private Button task_5;
    private Button task_6;
    private Button task_7;
    private Button task_8;
    private Button task_9;
    private TextView tvCSB;
    private TextView tvVolume;
    /*功能按钮*/

    //msg 定义
    private static final int msgShowConnect = 1;

    /**************service 命令*********/
    static final int CMD_STOP_SERVICE = 0x01;       // Main -> service
    static final int CMD_SEND_DATA = 0x02;          // Main -> service
    static final int CMD_SYSTEM_EXIT =0x03;         // service -> Main
    static final int CMD_SHOW_TOAST =0x04;          // service -> Main
    static final int CMD_CONNECT_BLUETOOTH = 0x05;  // Main -> service
    static final int CMD_RECEIVE_DATA = 0x06;       // service -> Main

    MyReceiver1 receiver1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*设置全屏*/
//        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_maininterface);

        /*按钮监听按下弹起*/
        ButtonListener1 b = new ButtonListener1();
        task_1 = (Button) findViewById(R.id.button_task_1);
        task_2 = (Button) findViewById(R.id.button_task_2);
        task_3 = (Button) findViewById(R.id.button_task_3);
        task_4 = (Button) findViewById(R.id.button_task_4);
        task_5 = (Button) findViewById(R.id.button_task_5);
        task_6 = (Button) findViewById(R.id.button_task_6);
        task_7 = (Button) findViewById(R.id.button_task_7);
        task_8 = (Button) findViewById(R.id.button_task_8);
        task_9 = (Button) findViewById(R.id.button_break);
        tvCSB=(TextView)findViewById(R.id.textView4);
        tvVolume=(TextView)findViewById(R.id.textView5);

        task_1.setOnTouchListener(b);
        task_2.setOnTouchListener(b);
        task_3.setOnTouchListener(b);
        task_4.setOnTouchListener(b);
        task_5.setOnTouchListener(b);
        task_6.setOnTouchListener(b);
        task_7.setOnTouchListener(b);
        task_8.setOnTouchListener(b);
        task_9.setOnTouchListener(b);

    }

/*********************************************************************************************/

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("OnStart", "Start");
    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Log.i("onDestroy", "Destroy");
        if(receiver1!=null){
            MainInterface.this.unregisterReceiver(receiver1);
        }
    }
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        Log.i("onResume", "Resume");
        receiver1 = new MyReceiver1();
        IntentFilter filter=new IntentFilter();
        filter.addAction("android.intent.action.bluetooth.admin.bluetooth");
        MainInterface.this.registerReceiver(receiver1,filter);
    }

    public void showToast(String str){//显示提示信息
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }
    public class MyReceiver1 extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            if(intent.getAction().equals("android.intent.action.bluetooth.admin.bluetooth")){
                Bundle bundle = intent.getExtras();
                int cmd = bundle.getInt("cmd");

                if(cmd == CMD_SHOW_TOAST){
                    String str = bundle.getString("str");
                    showToast(str);
                    if ("连接成功建立，可以开始操控了!".equals(str))
                    {
                        booleanConnect = true;
                        bluetoothConnect.setEnabled(true);
                        bluetoothConnect.setText("断开");
                    }
                    else if("连接失败".equals(str))
                    {
                        booleanConnect = false;
                        bluetoothConnect.setEnabled(true);
                        bluetoothConnect.setText("连接");
                    }
                }
                else if(cmd == CMD_SYSTEM_EXIT){
                    System.exit(0);
                }
                else if(cmd == CMD_RECEIVE_DATA)  //此处是可以接收蓝牙发送过来的数据可以解析，此例程暂时不解析返回来的数据，需要解析的在我们的全功能版会有
                {
                    String strtemp = bundle.getString("str");
                    int start = strtemp.indexOf("$");
                    int end = strtemp.indexOf("#");

                    if (start >= 0 && end > 0 && end > start && strtemp.length() > 23 )
                   {
                       String str = strtemp.substring(23);
                       String strCSB = str.substring(0, str.indexOf(","));
                       String strVolume = str.substring(str.indexOf(",")+1, str.indexOf("#"));
                       tvCSB.setText(strCSB);
                       tvVolume.setText(strVolume);
                    }
                }

            }
        }
    }



    public void SendBlueToothProtocol(String value){
        Intent intent = new Intent();//创建Intent对象
        intent.setAction("android.intent.action.cmd");
        intent.putExtra("cmd", CMD_SEND_DATA);
        intent.putExtra("command", (byte)0x00);
        intent.putExtra("value", value);
        sendBroadcast(intent);//发送广播
    }



    /*
    * ***********************************************************************************************/



    class ButtonListener1 implements View.OnClickListener, View.OnTouchListener {

        public void onClick(View v) {
            if (v.getId() == R.id.button_task_1) {
                //Log.d("test", "cansal button ---> click");

            }
        }

        public boolean onTouch(View v, MotionEvent event)
        {
            switch (v.getId()) {

                case R.id.button_task_1: {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS,HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
                        v.playSoundEffect(SoundEffectConstants.CLICK);
                        SendBlueToothProtocol("$1#");
                        Intent intent1=new Intent(MainInterface.this,Task_1.class);
                        startActivity(intent1);
                    }
                }break;


                case R.id.button_task_2: {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS,HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
                        v.playSoundEffect(SoundEffectConstants.CLICK);
                        SendBlueToothProtocol("$2#");
                        Intent intent1=new Intent(MainInterface.this,Task_2.class);
                        startActivity(intent1);
                    }
                }break;

               case R.id.button_task_3: {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS,HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
                        v.playSoundEffect(SoundEffectConstants.CLICK);
                        SendBlueToothProtocol("$3,0,0,0,0,0,0,0,0,0#");
                        Intent intent=new Intent(MainInterface.this,Task_3.class);
                        startActivity(intent);
                    }
                }break;

                case R.id.button_task_4: {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS,HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
                        v.playSoundEffect(SoundEffectConstants.CLICK);
                        SendBlueToothProtocol("$4,0,0,0,0,0,0,0,0,0#");
                        Intent intent=new Intent(MainInterface.this,Task_4.class);
                        startActivity(intent);
                    }
                }break;

                case R.id.button_task_5: {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS,HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
                        v.playSoundEffect(SoundEffectConstants.CLICK);
                        SendBlueToothProtocol("$5#");
                        Intent intent=new Intent(MainInterface.this,Task_5.class);
                        startActivity(intent);
                    }
                } break;

                /*左旋*/
                case R.id.button_task_6: {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS,HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
                        v.playSoundEffect(SoundEffectConstants.CLICK);
                        SendBlueToothProtocol("$6#");
                        Intent intent=new Intent(MainInterface.this,Task_6.class);
                        startActivity(intent);
                    }
                } break;

                /*右旋*/
               case R.id.button_task_7: {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS,HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
                        v.playSoundEffect(SoundEffectConstants.CLICK);
                        SendBlueToothProtocol("$7,0,0,0,0,0,0,0,0,0#");
                        Intent intent=new Intent(MainInterface.this,Task_7.class);
                        startActivity(intent);
                    }
                }break;

                case R.id.button_task_8: {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS,HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
                        v.playSoundEffect(SoundEffectConstants.CLICK);
                        Intent intent=new Intent(MainInterface.this,Task_8.class);
                        startActivity(intent);

                    }
                }break;

                case R.id.button_break: {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS,HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
                        v.playSoundEffect(SoundEffectConstants.CLICK);
                        finish();
                    }
                }break;

            }
            return false;
        }

    }



}
