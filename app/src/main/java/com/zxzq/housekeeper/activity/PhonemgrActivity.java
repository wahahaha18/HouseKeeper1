package com.zxzq.housekeeper.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zxzq.housekeeper.R;
import com.zxzq.housekeeper.adapter.PhonemgrAdapter;
import com.zxzq.housekeeper.base.BaseActivity;
import com.zxzq.housekeeper.biz.MemoryManager;
import com.zxzq.housekeeper.biz.PhoneManager;
import com.zxzq.housekeeper.entity.PhoneInfo;
import com.zxzq.housekeeper.util.CommonUtil;

public class PhonemgrActivity extends BaseActivity implements View.OnClickListener {
    ProgressBar pb_phonemgr,layout_phonemgr_progressBar;
    ListView layout_phonemgr_listview;
    TextView tv_phonemgr_batteryNumber;
    BatteryBroadcastReceiver batteryBroadcastReceiver;
    PhonemgrAdapter phonemgrAdapter;
    PhoneManager phoneManager;
    LinearLayout ll_phonemgr_battery;
    int currentBattery;
    int temperatureBattery;
    int textOfBattery;
    View view_phonemgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonemgr);
        String title = getResources().getString(R.string.phonemgr);
        initActionBar(title,R.drawable.btn_homeasup_default,-1,this);

        layout_phonemgr_listview = (ListView) findViewById(R.id.layout_phonemgr_listview);
        phonemgrAdapter = new PhonemgrAdapter(this);
        layout_phonemgr_listview.setAdapter(phonemgrAdapter);

        initWight();

        //注册广播
        batteryBroadcastReceiver = new BatteryBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        //动态注册广播的方法
        registerReceiver(batteryBroadcastReceiver,filter);

        loadData();
    }

    /**
     * 加载数据
     */
    private void loadData() {
        phoneManager = PhoneManager.getPhoneManager(this);
        layout_phonemgr_progressBar.setVisibility(View.INVISIBLE);
        layout_phonemgr_listview.setVisibility(View.VISIBLE);
        new Thread(){
            @Override
            public void run() {
                super.run();
                String title;
                String text;
                Drawable icon;

                //       position : 0
                title = "设备名称："+phoneManager.getPhoneName1();
                text = "系统版本："+phoneManager.getPhoneSystemVersion();
                icon = getResources().getDrawable(R.drawable.setting_info_icon_version);
                final PhoneInfo phoneInfo0 = new PhoneInfo(icon,title,text);

               //        position : 1
                title = "全部运行内存："+ CommonUtil.getFileSize(MemoryManager.getPhoneTotalRamMemory());
                text = "剩余运行内存："+CommonUtil.getFileSize(MemoryManager.getPhoneFreeRamMemory(PhonemgrActivity.this));
                icon = getResources().getDrawable(R.drawable.setting_info_icon_space);
                final PhoneInfo phoneInfo1 = new PhoneInfo(icon,title,text);

                //        position : 2
                title = "CPU名称："+ phoneManager.getPhoneCPUName();
                text = "CPU数量："+ phoneManager.getPhoneCpuNumber();
                icon = getResources().getDrawable(R.drawable.setting_info_icon_cpu);
                final PhoneInfo phoneInfo2 = new PhoneInfo(icon,title,text);

                //        position : 3
                title = "手机分辨率："+ phoneManager.getResolution();
                text = "相机分辨率："+ phoneManager.getCameraResolution();
                icon = getResources().getDrawable(R.drawable.setting_info_icon_camera);
                final PhoneInfo phoneInfo3 = new PhoneInfo(icon,title,text);

                //        position : 4
                title = "基带版本："+ phoneManager.getPhoneSystemBasebandVersion();
                text = "是否ROOT："+ (phoneManager.isRoot()? "是" : "否");
                icon = getResources().getDrawable(R.drawable.setting_info_icon_camera);
                final PhoneInfo phoneInfo4 = new PhoneInfo(icon,title,text);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        phonemgrAdapter.addDataToAadapter(phoneInfo0);
                        phonemgrAdapter.addDataToAadapter(phoneInfo1);
                        phonemgrAdapter.addDataToAadapter(phoneInfo2);
                        phonemgrAdapter.addDataToAadapter(phoneInfo3);
                        phonemgrAdapter.addDataToAadapter(phoneInfo4);
                        phonemgrAdapter.notifyDataSetChanged();
                        layout_phonemgr_progressBar.setVisibility(View.INVISIBLE);
                        layout_phonemgr_listview.setVisibility(View.VISIBLE);

                    }
                });
            }
        }.start();
    }

    /**
     * 加载适配器的数据*/
   /* public void loadAdapterData(){
        phoneManager = PhoneManager.getPhoneManager(this);
        String title;
        String text;
        Drawable icon;

//       position : 0
        title = "设备名称："+phoneManager.getPhoneName1();
        text = "系统版本："+phoneManager.getPhoneSystemVersion();
        icon = getResources().getDrawable(R.drawable.setting_info_icon_version);
        PhoneInfo phoneInfo0 = new PhoneInfo(icon,title,text);

//        position : 1
        title = "全部运行内存："+ CommonUtil.getFileSize(MemoryManager.getPhoneTotalRamMemory());
        text = "剩余运行内存："+CommonUtil.getFileSize(MemoryManager.getPhoneFreeRamMemory(this));
        icon = getResources().getDrawable(R.drawable.setting_info_icon_space);
        PhoneInfo phoneInfo1 = new PhoneInfo(icon,title,text);

        //        position : 2
        title = "CPU名称："+ phoneManager.getPhoneCPUName();
        text = "CPU数量："+ phoneManager.getPhoneCpuNumber();
        icon = getResources().getDrawable(R.drawable.setting_info_icon_cpu);
        PhoneInfo phoneInfo2 = new PhoneInfo(icon,title,text);

        //        position : 3
        title = "手机分辨率："+ phoneManager.getResolution();
        text = "相机分辨率："+ phoneManager.getCameraResolution();
        icon = getResources().getDrawable(R.drawable.setting_info_icon_camera);
        PhoneInfo phoneInfo3 = new PhoneInfo(icon,title,text);

        //        position : 4
        title = "基带版本："+ phoneManager.getPhoneSystemBasebandVersion();
        text = "是否ROOT："+ (phoneManager.isRoot()? "是" : "否");
        icon = getResources().getDrawable(R.drawable.setting_info_icon_camera);
        PhoneInfo phoneInfo4 = new PhoneInfo(icon,title,text);

    }*/

    /**
     * 加载主要控件*/
    private void initWight() {
        pb_phonemgr = (ProgressBar) findViewById(R.id.pb_phonemgr);
        layout_phonemgr_progressBar = (ProgressBar) findViewById(R.id.layout_phonemgr_progressBar);
        ll_phonemgr_battery = (LinearLayout) findViewById(R.id.ll_phonemgr_battery);
        tv_phonemgr_batteryNumber = (TextView) findViewById(R.id.tv_phonemgr_batteryNumber);
        view_phonemgr = (View) findViewById(R.id.view_phonemgr);
        ll_phonemgr_battery.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.iv_left :
                startActivity(HomeActivity.class);
                break;

//            点击电池布局，会自动弹出对话框提示电量
            case R.id.ll_phonemgr_battery :
                new AlertDialog.Builder(this)
                        .setTitle("电池电量警告！")
                        .setItems(new String[]{"剩余电量："+textOfBattery+"%","电池温度："+temperatureBattery+"摄氏度"},null)
                        .show();

                break;
        }
    }

    /**
     * 给电池控件设置广播接收，检测手机电量改变，从而使电池进度条随之变化
     */
    public class BatteryBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)){
                Bundle bundle = intent.getExtras();
                int maxBattery = (Integer) bundle.get(BatteryManager.EXTRA_SCALE);
                currentBattery = (Integer) bundle.get(BatteryManager.EXTRA_LEVEL);
                temperatureBattery = (Integer) bundle.get(BatteryManager.EXTRA_TEMPERATURE);
                pb_phonemgr.setMax(maxBattery);
                pb_phonemgr.setProgress(currentBattery);
                textOfBattery = currentBattery * 100 /maxBattery;
                tv_phonemgr_batteryNumber.setText(textOfBattery+" %");
//                如果电量充满，电池将全是绿色
                if (textOfBattery == 100){
                    view_phonemgr.setBackgroundResource(R.color.battery_speed);
                }

            }
        }
    }

    /**
     * 关闭广播*/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (batteryBroadcastReceiver != null){
            unregisterReceiver(batteryBroadcastReceiver);
        }
    }
}
