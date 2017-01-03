package com.zxzq.housekeeper.activity;

import android.os.Bundle;
import android.view.View;

import com.zxzq.housekeeper.R;
import com.zxzq.housekeeper.base.BaseActivity;

/**
 * 关于我们
 */
public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        String title = getResources().getString(R.string.about);
//        有问题
        //初始化ActionBar
        initActionBar(title,R.drawable.btn_homeasup_default,-1,clickListener);
    }

    //响应ActionBarView中左右两边图片的点击事件
    private View.OnClickListener clickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                //点击左边 返回
                case R.id.iv_left:
                    String fromClassName = getIntent().getStringExtra("className");

                    //无值，默认从HomeActivity跳转过来
                    if (fromClassName == null || fromClassName.equals("")){
                        startActivity(HomeActivity.class);
                        return;
                    }
                    //从SettingActivity页面跳转过来，仍然返回设置界面
                    if (fromClassName.equals(SettingActivity.class.getSimpleName())){
                    startActivity(SettingActivity.class);
                    }else{
                    startActivity(HomeActivity.class);
                    }
                    break;
            }
        }
    };
}
