package com.zxzq.housekeeper.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.zxzq.housekeeper.R;
import com.zxzq.housekeeper.base.BaseActivity;
import com.zxzq.housekeeper.util.NotificationUtil;

/**设置页面

 * Created by Administrator on 2016/10/31.
 */

public class SettingActivity extends BaseActivity {
    ToggleButton tb_setting_notifmsg;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        String title = getResources().getString(R.string.setting);
//        已改
        //初始化ActionBar
        initActionBar(title,R.drawable.btn_homeasup_default,-1,clickListener);
        //初始化主要按钮
        initMainButton();
    }


    //     已改
    /**
     * 初始化主要按钮
     */
    private void initMainButton() {

        tb_setting_notifmsg = (ToggleButton) findViewById(R.id.tb_setting_notifmsg);
        //设置首次安装软件是选中状态
        tb_setting_notifmsg.setChecked(NotificationUtil.isOpenNotification(SettingActivity.this));
        //监听是否选中 的状态
        tb_setting_notifmsg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                调用isChecked()方法系统会自动读取用户是否选中，选中true，未选中false,
//                从而进入到不同的代码块中
                if (tb_setting_notifmsg.isChecked()){
                    //发送通知,先发送或先设置都一样
                    NotificationUtil.showAppIconNotification(SettingActivity.this);
                    NotificationUtil.setOpenNotification(SettingActivity.this,true);
                }else{
                    //取消通知
                    NotificationUtil.cancelAppIconNotifiction(SettingActivity.this);
                    NotificationUtil.setOpenNotification(SettingActivity.this,false);
                }
            }
        });
    }



    public void hitSettingitem(View view){
        switch (view.getId()){
            case R.id.rl_setting_about:
                Bundle bundle = new Bundle();
                bundle.putString("className",SettingActivity.class.getSimpleName());
                startActivity(AboutActivity.class,bundle);
                break;
            case R.id.rl_setting_help:
                SharedPreferences spf = getSharedPreferences("lead_config", MODE_PRIVATE);
                SharedPreferences.Editor editor = spf.edit();
                editor.putBoolean("isFirstRun",true);
                editor.commit();
                Bundle bundle1 = new Bundle();
                bundle1.putString("className",SettingActivity.class.getSimpleName());
                startActivity(LeadActivity.class,bundle1);
                break;
        }
    }
    private View.OnClickListener clickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case  R.id.iv_left:
                    startActivity(HomeActivity.class);
                    break;
            }
        }
    };
}
