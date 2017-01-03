package com.zxzq.housekeeper.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zxzq.housekeeper.R;
import com.zxzq.housekeeper.adapter.CleanAdapter;
import com.zxzq.housekeeper.base.BaseActivity;
import com.zxzq.housekeeper.biz.DBClearPathManager;
import com.zxzq.housekeeper.biz.FileManager;
import com.zxzq.housekeeper.entity.ClearInfo;
import com.zxzq.housekeeper.util.CommonUtil;
import com.zxzq.housekeeper.util.LogUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class CleanActivity extends BaseActivity implements View.OnClickListener {
    TextView tv_file_show_size;
    ListView lv_clean_listviewLoad;
    ProgressBar pb_clean_listview;
    CheckBox cb_clean_call;
    Button b_clean_lookApps;
    CleanAdapter cleanAdapter;
    long topSize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear);
        String clean = getResources().getString(R.string.sdclean);
        initActionBar(clean,R.drawable.btn_homeasup_default,-1,this);
        initWidget();
        asynData();
    }

    private void asynData() {
        topSize=0;
        try {
            InputStream inputStream = getResources().getAssets().open("clearpath.db");
            DBClearPathManager.updateDataBase(inputStream);
            final ArrayList<ClearInfo> clearInfos = DBClearPathManager.getPhoneClearFiles(CleanActivity.this);
            new Thread(){
                @Override
                public void run() {
                    super.run();


                    for (ClearInfo clearInfo : clearInfos){
                        File file = new File(clearInfo.getFilepath());
                        long size = FileManager.getFileSize(file);
                        clearInfo.setSize(size);
                        Message message = mHandler.obtainMessage();
                        message.obj = size;
                        message.what = 1;
                        mHandler.sendMessage(message);
                    }

                    Message msg = mHandler.obtainMessage();
                    msg.obj = clearInfos;
                    msg.what = 2;
                    mHandler.sendMessage(msg);
                }
            }.start();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    @Override
    protected void myHandlerMessage(Message msg) {
        super.myHandlerMessage(msg);
        switch (msg.what){
            case 1 :
                long size = (long)msg.obj;
                topSize += size;
                tv_file_show_size.setText(CommonUtil.getFileSize(topSize));
                break;
            case 2 :
                lv_clean_listviewLoad.setVisibility(View.VISIBLE);
                pb_clean_listview.setVisibility(View.INVISIBLE);
                ArrayList<ClearInfo> clearInfos = (ArrayList<ClearInfo>) msg.obj;
                cleanAdapter.setDataToAdapter(clearInfos);
                cleanAdapter.notifyDataSetChanged();


                break;
        }
    }

    private void initWidget() {
        tv_file_show_size = (TextView) findViewById(R.id.tv_file_show_size);
        lv_clean_listviewLoad = (ListView) findViewById(R.id.lv_clean_listviewLoad);
        cleanAdapter = new CleanAdapter(this);
        lv_clean_listviewLoad.setAdapter(cleanAdapter);

        pb_clean_listview = (ProgressBar) findViewById(R.id.pb_clean_listview);
        cb_clean_call = (CheckBox) findViewById(R.id.cb_clean_call);
        cb_clean_call.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ArrayList<ClearInfo> clearInfos = cleanAdapter.getDataFromAdapter();
                 for (ClearInfo clearInfo : clearInfos){
                     clearInfo.setClear(isChecked);
                 }
                cleanAdapter.notifyDataSetChanged();
            }
        });

        b_clean_lookApps = (Button) findViewById(R.id.b_clean_lookApps);
        b_clean_lookApps.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_left:
                startActivity(HomeActivity.class);
                break;
            case R.id.b_clean_lookApps :
//                long sizeFinal = 0;
                ArrayList<ClearInfo> dataFromAdapter = cleanAdapter.getDataFromAdapter();
                for (ClearInfo clearInfo : dataFromAdapter){
                    if (clearInfo.isClear()){
                        File file = new File(clearInfo.getFilepath());
                        long size=FileManager.getFileSize(file);

                        FileManager.deleteFile(file);
                        if (!file.exists()){
//                            sizeFinal += FileManager.getFileSize(file);
                            cleanAdapter.removeDataFromAdapter(clearInfo);
                            cleanAdapter.notifyDataSetChanged();
                            LogUtil.e("CleanActivity:","清除。。。。。。。。。。。。。。。");
                            tv_file_show_size.setText(CommonUtil.getFileSize(topSize - size));

                        }

                    }
                }
                cb_clean_call.setChecked(false);

        }
    }
}
