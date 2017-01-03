package com.zxzq.housekeeper.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxzq.housekeeper.R;
import com.zxzq.housekeeper.base.BaseActivity;
import com.zxzq.housekeeper.base.adapter.BasePagerAdapter;
import com.zxzq.housekeeper.service.MusicService;
import com.zxzq.housekeeper.util.LogUtil;

/**
 * 引导页
 */

public class LeadActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    //存放小圆点的ImageView数组
    private ImageView[] icons = new ImageView[3];
    //引导页 skip 文本
    private TextView tv_skip;
    //声明ViewPager以及Adapter
    ViewPager vp_lead;
    BasePagerAdapter pagerAdapter;
    //是否来自设置页面
    private boolean isFromSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //判断是否从设置界面跳转过来
        Intent intent = getIntent();
        String className = intent.getStringExtra("className");

        if (className != null && className.equals(SettingActivity.class.getSimpleName())){
            isFromSetting = true;
        }

        //判断是否是第一次使用该App
        SharedPreferences spf = getSharedPreferences("lead_config", MODE_PRIVATE);
//读取SharedPreferences文件的配置信息，默认为第一次使用该App
        boolean isFirstRun = spf.getBoolean("isFirstRun",true);

        if (isFirstRun){
            setContentView(R.layout.activity_lead);
            //调用  初始化小圆点等  的方法
            initLeadIcon();
            //调用  初始化ViewPager  的方法
            initViewPager();
            //调用  初始化ViewPager所需数据  的方法
            initPagerData();
            Intent serviceIntent = new Intent(LeadActivity.this, MusicService.class);
            startService(serviceIntent);

        }else{
            //不是第一次使用该App
            //跳转LogoActivity页面
            startActivity(LogoActivity.class);
            //关闭当前Activity
            finish();
        }

    }

    /**
     * 初始化ViewPager
     */
    private void initViewPager() {
        vp_lead = (ViewPager) findViewById(R.id.vp_lead);
        pagerAdapter = new BasePagerAdapter(LeadActivity.this);
        vp_lead.setAdapter(pagerAdapter);
        vp_lead.addOnPageChangeListener(this);
    }

    /**
     * 初始化ViewPager需要的数据
     */
    private void initPagerData() {
        ImageView imageView = null;
        //实例化一个布局加载器
        LayoutInflater layoutInflater = getLayoutInflater();

        //向Adapter的数据源中添加数据
        imageView = (ImageView) layoutInflater.inflate(R.layout.layout_lead_item,null);
        imageView.setImageResource(R.drawable.adware_style_applist);
        pagerAdapter.addViewToAdapter(imageView);

        imageView = (ImageView) layoutInflater.inflate(R.layout.layout_lead_item,null);
        imageView.setImageResource(R.drawable.adware_style_banner);
        pagerAdapter.addViewToAdapter(imageView);

        imageView = (ImageView) layoutInflater.inflate(R.layout.layout_lead_item,null);
        imageView.setImageResource(R.drawable.adware_style_creditswall);
        pagerAdapter.addViewToAdapter(imageView);

        //通知系统Adapter的数据源发生改变
        pagerAdapter.notifyDataSetChanged();


    }

    /**
     * 初始化小圆点以及skip文本
     */
    private void initLeadIcon() {
        icons[0] = (ImageView) findViewById(R.id.iv_icon1);
        icons[1] = (ImageView) findViewById(R.id.iv_icon2);
        icons[2] = (ImageView) findViewById(R.id.iv_icon3);
        tv_skip = (TextView) findViewById(R.id.tv_skip);
        tv_skip.setVisibility(View.INVISIBLE);
        tv_skip.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //保存配置信息，以后不再是第一次运行该App
        savepreferences();
        if (isFromSetting){
            startActivity(SettingActivity.class);
        }else{
         startActivity(LogoActivity.class);
        }
        Intent serviceIntent1 = new Intent(LeadActivity.this,MusicService.class);
        stopService(serviceIntent1);
        finish();

    }

    /**
     * 保存配置信息：修改为不是第一次运行
     */
    private void savepreferences() {
        SharedPreferences spf = getSharedPreferences("lead_config", MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        editor.putBoolean("isFirstRun",false);
        editor.commit();
        LogUtil.d("LeadActivity"," save_false");
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        //页面滑动时执行该方法
    }

    @Override
    public void onPageSelected(int position) {
        //页面跳转完成后执行该方法
        //设置"skip"文本不可见
        tv_skip.setVisibility(View.INVISIBLE);
        if (position == 2) {
            //已经是最后一张引导页，设置"skip"文本可见
            tv_skip.setVisibility(View.VISIBLE);
        }
        //将所有的小圆点设置为默认的(灰色的)
            for (ImageView iv:icons){
                iv.setImageResource(R.drawable.adware_style_default);
            }
        //将当前页面的小圆点设置为选中状态(绿色的)
            icons[position].setImageResource(R.drawable.adware_style_selected);

        }



    @Override
    public void onPageScrollStateChanged(int state) {

        //页面状态改变时，执行该方法
    }
}
