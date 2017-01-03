package com.zxzq.housekeeper.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zxzq.housekeeper.R;
import com.zxzq.housekeeper.base.BaseActivity;
import com.zxzq.housekeeper.biz.MemoryManager;
import com.zxzq.housekeeper.util.CommonUtil;
import com.zxzq.housekeeper.util.LogUtil;
import com.zxzq.housekeeper.view.PiechatView;

import java.text.DecimalFormat;

public class SoftmgrActivity extends BaseActivity implements View.OnClickListener {
    PiechatView piechatView;
    ProgressBar pb_soft_progressbar,pb_soft_progressbar1;
    TextView tv_phone_space,tv_sd_space;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_softmgr);
        String actionName = getResources().getString(R.string.softmgr);
        initActionBar(actionName,R.drawable.btn_homeasup_default,R.drawable.ic_child_configs,this);
        initView();
        initSpace();

    }


    private void initSpace() {
//        手机自身内存
        long phoneSelfSize = MemoryManager.getPhoneSelfSize();
        long phoneSelfFreeSize = MemoryManager.getPhoneSelfFreeSize();
        long phoneSelfUsedSize = phoneSelfSize - phoneSelfFreeSize;

//        手机内置SD卡内存
        long phoneSelfSDCardSize = MemoryManager.getPhoneSelfSDCardSize();
        long phoneSelfSDCardFreeSize = MemoryManager.getPhoneSelfSDCardFreeSize();
        long phoneSelfSDCardUsedSize = phoneSelfSDCardSize - phoneSelfSDCardFreeSize;

//        手机外置SD卡内存
        long phoneOutSDCardSize = MemoryManager.getPhoneOutSDCardSize();
        long phoneOutSDCardFreeSize = MemoryManager.getPhoneOutSDCardFreeSize();
        long phoneOutSDCardUsedSize = phoneOutSDCardSize - phoneOutSDCardFreeSize;

//        手机总空间
        long phoneAllSpace = phoneOutSDCardSize + phoneSelfSize +phoneSelfSDCardSize;

//        手机内置空间比例
        float phoneInSpace = (phoneSelfSDCardSize + phoneSelfSize) / phoneAllSpace;
        LogUtil.d("TAG","手机内置空间比例:"+phoneInSpace);
//        外置存储空间比例
        float phoneOutSpace = phoneOutSDCardSize / phoneAllSpace;
        LogUtil.d("TAG","外置存储空间比例:"+phoneOutSpace);
//        计算比例
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        phoneInSpace = Float.parseFloat(decimalFormat.format(phoneInSpace));
        phoneOutSpace = Float.parseFloat(decimalFormat.format(phoneOutSpace));

        piechatView.setPhonePiechatAnim(phoneInSpace,phoneOutSpace);

//      手机内置空间
        long phoneInSpace1 = phoneSelfSDCardSize + phoneSelfSize;
        long phoneInUsedSpace1 = phoneSelfUsedSize + phoneSelfSDCardUsedSize;
        pb_soft_progressbar.setMax((int)(phoneInSpace1/1024));
        pb_soft_progressbar.setProgress((int)(phoneInUsedSpace1/1024));
        String tv_phoneInSpace = CommonUtil.getFileSize(phoneInUsedSpace1) + "/" + CommonUtil.getFileSize(phoneInSpace1);

        LogUtil.d("TAG","手机内置空间:"+tv_phoneInSpace);

        tv_phone_space.setText(tv_phoneInSpace);

//      外置SD卡的存储空间
        long phoneOutSpace1 = phoneOutSDCardSize;
        long phoneOutSDCardUsedSize1 = phoneOutSDCardUsedSize;

        pb_soft_progressbar1.setMax((int)(phoneOutSpace1/1024));
        pb_soft_progressbar1.setProgress((int)(phoneOutSDCardUsedSize1/1024));
        String tv_textsdSpace = CommonUtil.getFileSize(phoneOutSDCardUsedSize1) + "/" + CommonUtil.getFileSize(phoneOutSpace1);

        LogUtil.d("TAG","外置SD卡的存储空间:"+tv_textsdSpace);

        tv_sd_space.setText(tv_textsdSpace);

    }

    private void initView() {
        piechatView = (PiechatView) findViewById(R.id.piechatView);
        pb_soft_progressbar = (ProgressBar) findViewById(R.id.pb_soft_progressbar);
        pb_soft_progressbar1 = (ProgressBar) findViewById(R.id.pb_soft_progressbar1);
        tv_phone_space = (TextView) findViewById(R.id.tv_phone_space);
        tv_sd_space = (TextView) findViewById(R.id.tv_sd_space);
    }

    public View hitSoftItem(View view){
        Intent intent = new Intent();
        Bundle bundle = null;
        switch (view.getId()){

            case R.id.tv_soft_all:
                bundle = new Bundle();
                bundle.putInt("id",R.id.tv_soft_all);

                startActivity(Phonemgr_show_app.class,bundle);
                break;
            case R.id.tv_soft_system:
                bundle = new Bundle();
                bundle.putInt("id",R.id.tv_soft_system);

                startActivity(Phonemgr_show_app.class,bundle);
                break;
            case R.id.tv_soft_user:
                bundle = new Bundle();
                bundle.putInt("id",R.id.tv_soft_user);

                startActivity(Phonemgr_show_app.class,bundle);
                break;
        }
        return view;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_left:
                startActivity(HomeActivity.class);
                break;
        }

    }


}
