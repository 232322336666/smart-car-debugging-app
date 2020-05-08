package com.example.ui_basetestdemo_food;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import com.example.ui_basetestdemo_food.Task_5;


public class MainActivity extends Activity {

    private BluetoothAdapter bluetoothAdapter;
    private Switch bluetoothSwitch;
    private Button bluetoothSearch;
    private Spinner bluetoothList;
    private Button bluetoothConnect;
    private ArrayAdapter<String> adapter;
    private List<String> list = new ArrayList<String>();
    private String strMacAddress;
    private static boolean booleanConnect = false;
    private Button mbstart;
    private TextView tvCSB;
    private TextView tvVolume;
    //msg 定义
    private static final int msgShowConnect = 1;

    /**************service 命令*********/
    static final int CMD_STOP_SERVICE = 0x01;       // Main -> service
    static final int CMD_SEND_DATA = 0x02;          // Main -> service
    static final int CMD_SYSTEM_EXIT =0x03;         // service -> Main
    static final int CMD_SHOW_TOAST =0x04;          // service -> Main
    static final int CMD_CONNECT_BLUETOOTH = 0x05;  // Main -> service
    static final int CMD_RECEIVE_DATA = 0x06;       // service -> Main

    MyReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*设置全屏*/
//        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        bluetoothSwitch = (Switch) findViewById(R.id.bluetoothswitch);//开关
        bluetoothSearch = (Button) findViewById(R.id.buttonSearchBluetooth);//搜索
        bluetoothList = (Spinner) findViewById(R.id.list_bluetooth);//列表
        bluetoothConnect = (Button) findViewById(R.id.buttonConnect);//连接
        mbstart =(Button)findViewById(R.id.button_start);
        tvCSB=(TextView)findViewById(R.id.textView_text1);
        tvVolume=(TextView)findViewById(R.id.textView_text2);

        /*检查手机是否支持蓝牙*/
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            //表明此手机不支持蓝牙
            Toast.makeText(MainActivity.this, "未发现蓝牙设备", Toast.LENGTH_SHORT).show();
            return;
        }

        if (bluetoothAdapter.isEnabled()) {
            bluetoothSwitch.setChecked(true);
        }
 /*        String name = bluetoothAdapter.getName();
        //获取本地蓝牙地址
        String address = bluetoothAdapter.getAddress();
        //打印相关信息
        Log.i("BLE Name", name);
        Log.i("BLE Address", address);
        
*/
        /*添加蓝牙列表*/
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bluetoothList.setAdapter(adapter);
        bluetoothList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strMacAddress = adapter.getItem(i);
                adapterView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        /*蓝牙总开关*//**********************/
           bluetoothSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    if (!bluetoothAdapter.isEnabled()) { //蓝牙未开启，则开启蓝牙
                        Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivity(enableIntent);
                    } else {
                        Toast.makeText(MainActivity.this, "蓝牙已开启", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    bluetoothAdapter.disable();
                    Toast.makeText(MainActivity.this, "蓝牙已关闭", Toast.LENGTH_SHORT).show();
                }
            }
        });


       /*蓝牙搜索*/
        bluetoothSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS,HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);//震动
               if (bluetoothAdapter == null) {
                    Toast.makeText(MainActivity.this, "未发现蓝牙设备", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!bluetoothAdapter.isEnabled()) {
                    Toast.makeText(MainActivity.this, "蓝牙设备未开启", Toast.LENGTH_SHORT).show();
                }

                Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
                if (pairedDevices.size() > 0) {
                    for (BluetoothDevice device : pairedDevices) {
                        adapter.remove(device.getName());
                        adapter.add(device.getName());
                    }
                } else {
                    //注册，当一个设备被发现时调用mReceive
                    Log.i("seach", "hhhhhh");
                    IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                    registerReceiver(mReceiver, filter);
                }
            }
        });

        /*蓝牙连接或断开*/
        bluetoothConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS,HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
              if (strMacAddress == null) {
                    Toast.makeText(MainActivity.this, "请先搜索设备", Toast.LENGTH_SHORT).show();
                } else {
                    if (booleanConnect == false) {

                        Intent i = new Intent(MainActivity.this, MyService.class);
                        i.putExtra("Mac", strMacAddress);
                        startService(i);

                        bluetoothConnect.setEnabled(false);
                    }
                    else // 断开蓝牙
                    {
                        booleanConnect = false;
                        //stopService(new Intent(MainActivity.this, MyService.class));
                        bluetoothConnect.setText("连接");

                        Intent intent = new Intent();//创建Intent对象
                        intent.setAction("android.intent.action.cmd");
                        intent.putExtra("cmd", CMD_STOP_SERVICE);
                        sendBroadcast(intent);//发送广播连接蓝牙

                    }

                }
           }
        });

        mbstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS,HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
                Intent intent =new Intent(MainActivity.this,MainInterface.class);
                startActivity(intent);
            }
        });

    }


    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("fond:", "mReceiver");

            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // 已经配对的则跳过
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    adapter.add(device.getAddress());
                }

            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {  //搜索结束
                Log.e("fond:", "ACTION_DISCOVERY_FINISHED");
                if (adapter.getCount() == 0) {
                    Toast.makeText(MainActivity.this, "没有搜索到设备", Toast.LENGTH_SHORT).show();
                }
            }

        }
    };
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
        if(receiver!=null){
            MainActivity.this.unregisterReceiver(receiver);
        }
    }
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        Log.i("onResume", "Resume");
        receiver = new MyReceiver();
        IntentFilter filter=new IntentFilter();
        filter.addAction("android.intent.action.bluetooth.admin.bluetooth");
        MainActivity.this.registerReceiver(receiver,filter);
    }

    public void showToast(String str){//显示提示信息
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }
    public class MyReceiver extends BroadcastReceiver{
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
                    Log.i("tv",strtemp);
                    int start = strtemp.indexOf("$");
                    int end = strtemp.indexOf("#");
                    tvCSB.setText(strtemp);
                    if (start >= 0 && end > 0 && end > start && strtemp.length() > 23 )
                    {
                        String str = strtemp.substring(23);
                        String strCSB = str.substring(0, str.indexOf(","));
                        String strVolume = str.substring(str.indexOf(",")+1, str.indexOf("#"));

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

}