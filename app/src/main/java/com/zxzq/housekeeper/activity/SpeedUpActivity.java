package com.zxzq.housekeeper.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zxzq.housekeeper.R;
import com.zxzq.housekeeper.adapter.RunningAppAdapter;
import com.zxzq.housekeeper.base.BaseActivity;
import com.zxzq.housekeeper.biz.AppInfoManager;
import com.zxzq.housekeeper.biz.MemoryManager;
import com.zxzq.housekeeper.biz.SystemManager;
import com.zxzq.housekeeper.entity.RunningAppInfo;
import com.zxzq.housekeeper.util.CommonUtil;
import com.zxzq.housekeeper.util.LogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/10.
 */

public class SpeedUpActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_phoneName,tv_phoneModel,tv_ramMessage;
    private ProgressBar pb_ram,pb_speedup_listview;
    private Button oneoKeyClear,b_lookApps;
    ListView lv_speedup_listviewLoad;
    CheckBox cb_call;
    RunningAppAdapter appAdapter;
    AppInfoManager appInfoManager;
    Map<Integer,List<RunningAppInfo>> maps = new HashMap<Integer,List<RunningAppInfo>>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speedup);
        String title = "设置";
        initActionBar(title,R.drawable.btn_homeasup_default,-1,this);
        lv_speedup_listviewLoad = (ListView) findViewById(R.id.lv_speedup_listviewLoad);
        appAdapter = new RunningAppAdapter(this);
        lv_speedup_listviewLoad.setAdapter(appAdapter);
        lv_speedup_listviewLoad.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_FLING){
                    appAdapter.setFling(true);
                }
                if (scrollState == SCROLL_STATE_IDLE){
                    appAdapter.setFling(false);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
//        实例化控件
        initWight();
//        获得手机信息数据
        initPhoneData();
//        设置内存信息
        initRamData();
//        加载数据
        loadData();
    }

    private void loadData() {
        pb_speedup_listview.setVisibility(View.VISIBLE);
        lv_speedup_listviewLoad.setVisibility(View.INVISIBLE);
        appInfoManager = AppInfoManager.getAppInfoManager(this);
        new Thread(){
            @Override
            public void run() {
                super.run();
                maps = appInfoManager.getRunningAppInfos();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pb_speedup_listview.setVisibility(View.INVISIBLE);
                        lv_speedup_listviewLoad.setVisibility(View.VISIBLE);
                        appAdapter.setDataToAdapter(maps.get(AppInfoManager.RUNNING_APP_TYPE_USER));
                        Log.e("SpeedUpActivity", "run: appAdapter.getDataFromAdapter().size()="+appAdapter.getDataFromAdapter().size());
                        appAdapter.setState(RunningAppAdapter.STATE_SHOW_USER);
                        LogUtil.d("SpeedUpActivity","VISIBLE");
                        appAdapter.notifyDataSetChanged();
                    }
                });
            }
        }.start();
    }

    private void initRamData() {
        LogUtil.d("SpeedUpActivity","运行了");
        long phoneTotalRamMemory = MemoryManager.getPhoneTotalRamMemory();
        long phoneRunningRamMemory =  phoneTotalRamMemory- MemoryManager.getPhoneFreeRamMemory(this);
        double p = (double) phoneRunningRamMemory/phoneTotalRamMemory;
        /*//       完整运行内存总比例
        float ptrm = phoneTotalRamMemory;
//        正运行的内存所占总运行内存的比例
        float prrm = phoneRunningRamMemory / phoneTotalRamMemory;
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        float ptrmChanged = Float.parseFloat(decimalFormat.format(ptrm));
        float prrmChanged = Float.parseFloat(decimalFormat.format(prrm));*/
        LogUtil.d("SpeedUpActivity","phoneRunningRamMemory"+phoneRunningRamMemory);
        LogUtil.d("SpeedUpActivity","phoneTotalRamMemory"+phoneTotalRamMemory);
        LogUtil.d("SpeedUpActivity","p:"+p);
        LogUtil.d("SpeedUpActivity","phoneRunningRamMemory / phoneTotalRamMemory"+((phoneRunningRamMemory/phoneTotalRamMemory)/1024));
        LogUtil.d("SpeedUpActivity","phoneRunningRamMemory / phoneTotalRamMemory)*100"+(phoneRunningRamMemory / phoneTotalRamMemory)*100);
        int o = (int) (p*100);
        pb_ram.setMax(100);
        pb_ram.setProgress(o);
        LogUtil.d("SpeedUpActivity","p:"+p);
        LogUtil.d("SpeedUpActivity","o:"+o);
//        标示进度条的内存信息
        tv_ramMessage.setText("可用内存："+ CommonUtil.getFileSize(phoneRunningRamMemory)+"/"+
        CommonUtil.getFileSize(phoneTotalRamMemory));
    }

    private void initPhoneData() {
        tv_phoneName.setText(SystemManager.getPhoneName());
        tv_phoneModel.setText(SystemManager.getModelVersionName());
//        tv_ramMessage.setText(SystemManager.getModelVersionName());
    }

    private void initWight() {
        tv_phoneName = (TextView) findViewById(R.id.tv_phoneName);
        tv_phoneModel = (TextView) findViewById(R.id.tv_phoneModel);
        tv_ramMessage = (TextView) findViewById(R.id.tv_ramMessage);

        pb_ram = (ProgressBar) findViewById(R.id.pb_ram);
        pb_speedup_listview = (ProgressBar) findViewById(R.id.pb_speedup_listview);

        cb_call = (CheckBox) findViewById(R.id.cb_call);

        oneoKeyClear = (Button) findViewById(R.id.oneoKeyClear);
        b_lookApps = (Button) findViewById(R.id.b_lookApps);

//        多选框的状态改变监听事件
        cb_call.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ArrayList<RunningAppInfo> runningAppInfos = appAdapter.getDataFromAdapter();
                LogUtil.d("SpeedUpActivity","runningAppInfos.size()="+runningAppInfos.size());
                for (RunningAppInfo runningAppInfo : runningAppInfos){
                    runningAppInfo.setClear(isChecked);

                    LogUtil.d("SpeedUpActivity","33333333333");
                }
                LogUtil.d("SpeedUpActivity","22222222222222");
                appAdapter.notifyDataSetChanged();
            }
        });
        oneoKeyClear.setOnClickListener(this);
        b_lookApps.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.iv_left:
//                返回键
                startActivity(HomeActivity.class);
                break;
            case R.id.oneoKeyClear :
//                一键清理
                List<RunningAppInfo> runningAppInfos = appAdapter.getDataFromAdapter();
//                List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfos = new ArrayList<ActivityManager.RunningAppProcessInfo>();
                for (RunningAppInfo runningAppInfo : runningAppInfos){
                    if (runningAppInfo.isClear()){
                        appInfoManager.killProcesses(runningAppInfo.getPackageName());
                    }
                }
                loadData();
                appAdapter.notifyDataSetChanged();
                break;
            case R.id.b_lookApps :
                switch (appAdapter.getState()){
                    case RunningAppAdapter.STATE_SHOW_USER :
                        appAdapter.setDataToAdapter(maps.get(AppInfoManager.RUNNING_APP_TYPE_SYS));
                        appAdapter.setState(RunningAppAdapter.STATE_SHOW_SYS);
                        b_lookApps.setText("显示用户进程");
                        appAdapter.notifyDataSetChanged();
                        break;
                    case RunningAppAdapter.STATE_SHOW_SYS :
                        appAdapter.setDataToAdapter(maps.get(AppInfoManager.RUNNING_APP_TYPE_USER));
                        appAdapter.setState(RunningAppAdapter.STATE_SHOW_USER);
                        b_lookApps.setText("只显示系统进程");
                        appAdapter.notifyDataSetChanged();
                        break;
                }
                break;
        }
    }
}
