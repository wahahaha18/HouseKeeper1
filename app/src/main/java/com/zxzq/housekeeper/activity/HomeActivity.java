package com.zxzq.housekeeper.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxzq.housekeeper.R;
import com.zxzq.housekeeper.base.BaseActivity;
import com.zxzq.housekeeper.biz.AppInfoManager;
import com.zxzq.housekeeper.biz.MemoryManager;
import com.zxzq.housekeeper.view.ClearArcView;

public class HomeActivity extends BaseActivity implements View.OnClickListener {
    ClearArcView clearArcView;
    ImageView iv_home_score;
    TextView tv_home_score,tv_home_text;
    AppInfoManager appInfoManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        String title = getResources().getString(R.string.app_name);
//        已改
        initActionBar(title,R.drawable.ic_launcher,R.drawable.ic_child_configs,this);

        appInfoManager = AppInfoManager.getAppInfoManager(this);
//        实例化组件
        initWight();
//        加载圆饼数据
        initData();
    }

    private void initData() {
        long phoneTotalRamMemory = MemoryManager.getPhoneTotalRamMemory();
        long phoneFreeRamMemory = MemoryManager.getPhoneFreeRamMemory(this);
        long phoneUsedRamMemory = phoneTotalRamMemory - phoneFreeRamMemory;
        double used = (double) phoneUsedRamMemory / phoneTotalRamMemory;
        int usedPercent = (int) (used * 100);
        tv_home_score.setText(usedPercent + "");
        int angle = (int) (used * 360);
        clearArcView.setAngleWithAnim(angle);
    }

    private void initWight() {
        clearArcView = (ClearArcView) findViewById(R.id.cv_home_score);
        tv_home_score = (TextView) findViewById(R.id.tv_home_score);
        tv_home_text = (TextView) findViewById(R.id.tv_home_text);
        iv_home_score = (ImageView) findViewById(R.id.iv_home_score);
        tv_home_score.setOnClickListener(this);
        tv_home_text.setOnClickListener(this);
        iv_home_score.setOnClickListener(this);
    }


    /**
     * 响应TextView的点击事件
     * @param view
     */
    public void hitHomeItem(View view){
        switch (view.getId()){
            //跳转到手机加速
            case R.id.tv_rocket:

                startActivity(SpeedUpActivity.class);
                break;
            //跳转到软件管理
            case R.id.tv_softmgr:
                startActivity(SoftmgrActivity.class);

                break;
            //跳转到手机检测
            case R.id.tv_phonemgr:

                startActivity(PhonemgrActivity.class);
                break;
            //跳转到通讯大全
            case R.id.tv_telmgr:
                startActivity(TelmsgActivity.class);
                break;
            //跳转到文件管理
            case R.id.tv_filemgr:
                startActivity(FilemgrActivity.class);
                break;
            //跳转到垃圾清理
            case R.id.tv_sdclean:

                startActivity(CleanActivity.class);
                break;

        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.iv_left :

                Bundle bundle = new Bundle();
                bundle.putString("className",HomeActivity.class.getSimpleName());
                startActivity(AboutActivity.class,bundle);
                break;
            case R.id.iv_home_score:
            case R.id.tv_home_score:
            case R.id.tv_home_text:
                appInfoManager.killAllProcesses();
                initData();
                break;
            case R.id.iv_right:
                startActivity(SettingActivity.class);
                break;
        }
    }
}
