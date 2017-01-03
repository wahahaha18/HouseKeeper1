package com.zxzq.housekeeper.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.zxzq.housekeeper.R;
import com.zxzq.housekeeper.adapter.AppAdapter;
import com.zxzq.housekeeper.base.BaseActivity;
import com.zxzq.housekeeper.biz.AppInfoManager;
import com.zxzq.housekeeper.entity.AppInfo;
import com.zxzq.housekeeper.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class Phonemgr_show_app extends BaseActivity{
//    进度条
    ProgressBar layout_all_progressBar;
//    listview
    ListView layout_all_listview;

//    删除按钮
    Button b_unload;
//    应用信息
    AppInfo appInfo;
//    应用信息的的集合
    List<AppInfo> appInfos = new ArrayList<AppInfo>();;

//    给listview赋值的适配器
    AppAdapter appAdapter;
//   多选框
    CheckBox cb_select_call;
//应用信息管理类
    AppInfoManager appInfoManager = null;
//    软件管理界面对应item的id
    private int id;
//    声明自定义的广播类
    private AppDelReceive appDelReceive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonemgr_show_app);
        Intent intent = getIntent();
        int soft_item = intent.getIntExtra("id",R.id.tv_soft_all);
        this.id = soft_item;
        String title = "";
        switch (soft_item){
            case R.id.tv_soft_all:
                title = getResources().getString(R.string.setting_ruanjianguanli_all);
                break;
            case R.id.tv_soft_system:
                title = getResources().getString(R.string.setting_ruanjianguanli_sys);
                break;
            case R.id.tv_soft_user:
                title = getResources().getString(R.string.setting_ruanjianguanli_user);
                break;

        }

        initActionBar(title,R.drawable.btn_homeasup_default,-1,listener);
        b_unload = (Button) findViewById(R.id.b_unload);
        layout_all_progressBar = (ProgressBar)findViewById(R.id.layout_all_progressBar);
        cb_select_call = (CheckBox) findViewById(R.id.cb_select_call);
        layout_all_listview = (ListView)findViewById(R.id.layout_all_listview);
        appAdapter = new AppAdapter(this);
        layout_all_listview.setAdapter(appAdapter);
        asynLoadApp();

        b_unload.setOnClickListener(listener);
        appDelReceive = new AppDelReceive();
        IntentFilter filter = new IntentFilter();
        filter.addAction(appDelReceive.ACTION_APPDEL);
        filter.addDataScheme("package");
        filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        registerReceiver(appDelReceive,filter);

//        设置多选
        cb_select_call.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                List<AppInfo> appInfos1 = appAdapter.getDataFromAdapter();
                for (AppInfo appInfo : appInfos1){
                    appInfo.setDel(isChecked);
                }
                appAdapter.notifyDataSetChanged();
            }
        });
    }

//    点击事件的监听
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.b_unload:
                    for (AppInfo appInfo : appInfos){

                        if (appInfo.isDel()){
                            String packageName = appInfo.getPackageInfo().packageName;
                            Intent intent = new Intent(Intent.ACTION_DELETE);

                            intent.setData(Uri.parse("package:"+packageName));
                            startActivity(intent);
                        }
                    }
                    break;
                case R.id.iv_left:
                    startActivity(SoftmgrActivity.class);
                    break;
            }
        }
    };
    /*@Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.drawable.btn_homeasup_default:
                finish();
                break;

        }
    }*/

//    给适配器加载数据的方法
    private void asynLoadApp(){
        appInfoManager = AppInfoManager.getAppInfoManager(getApplicationContext());
        layout_all_progressBar.setVisibility(View.VISIBLE);
        layout_all_listview.setVisibility(View.INVISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
//                super.run();
                switch (id){
                    case R.id.tv_soft_all:
                        appInfos = appInfoManager.getAllPackagerInfo(true);
                        break;
                    case R.id.tv_soft_system:
                        appInfos = appInfoManager.getSysPackagerInfo(true);
                        break;
                    case R.id.tv_soft_user:
                        appInfos = appInfoManager.getUserPackagerInfo(true);
                        break;
                }
//                并不是又开启了一个子线程而是回归主线程
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        layout_all_progressBar.setVisibility(View.INVISIBLE);
                        layout_all_listview.setVisibility(View.VISIBLE);
                        appAdapter.setDataToAdapter(appInfos);
                        appAdapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
        /*runOnUiThread(new Runnable() {
            @Override
            public void run() {
                layout_all_progressBar.setVisibility(View.INVISIBLE);
                layout_all_listview.setVisibility(View.VISIBLE);
                appAdapter.setDataToAdapter(appInfos);
                appAdapter.notifyDataSetChanged();
            }
        });*/
    }

//    自定义广播接收类
    public class AppDelReceive extends BroadcastReceiver{
         public static final String ACTION_APPDEL = "com.androidy.app.phone.del";
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(ACTION_APPDEL) || intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)){
                LogUtil.e("TAG","onReceive:");
                asynLoadApp();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (appDelReceive != null){
            unregisterReceiver(appDelReceive);
        }
    }
}
