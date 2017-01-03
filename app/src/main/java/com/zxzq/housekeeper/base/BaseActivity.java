package com.zxzq.housekeeper.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.zxzq.housekeeper.R;
import com.zxzq.housekeeper.util.LogUtil;
import com.zxzq.housekeeper.view.ActionBarView;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * 基础Activity，定义基础方法，用于继承
 * Created by Administrator on 2016/10/28.
 */

public abstract class BaseActivity extends Activity {
    //定义TAG标签为类名
    private static final String TAG = BaseActivity.class.getSimpleName();
//    用于保存所有存在的Activity
    private static ArrayList<BaseActivity> onlineActivitylist = new ArrayList<BaseActivity>();

    private ActionBarView actionBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //添加当前Activity至onlineActivityList中
        onlineActivitylist.add(this);

        //ctrl+shift+enter 自动补全
        //ctrl+alt+v 生成局部变量   ctrl+alt+f 生成成员变量
    }


    protected Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            myHandlerMessage(msg);
        }
    };

    protected void myHandlerMessage(Message msg){

    }


    /**
     *初始化ActionBarView
     * @param title  中间标题的资源
     * @param resIdLeft  左边图片的资源
     * @param resIdRight 右边图片资源
     * @param listener 监听
     */

    public void initActionBar(String title,int resIdLeft,int resIdRight,View.OnClickListener listener){
       //设定XML文件中ActionBarView的id,以后用到标题栏的地方都要写这个id,只要写的都可以用ActionBarView的格式
        actionBarView = (ActionBarView) findViewById(R.id.actionBar);
        actionBarView.initActionBar(title,resIdLeft,resIdRight,listener);
    }


    //依次退出当前存在的所有Activity
    public static void finishAall(){
        Iterator<BaseActivity> iterator = onlineActivitylist.iterator();
        while(iterator.hasNext()){
            iterator.next().finish();
        }
    }

    /**
     * 普通的跳转
     * @param targetClass 目标Activity的字节码文件
     */
    protected void startActivity(Class<?> targetClass){
        Intent intent = new Intent(this,targetClass);
        startActivity(intent);
        finish();
    }

    /**
    * 带参数的跳转
    * @param targetClass 目标Activity的字节码文件
    * @param bundle  数据
    */
    protected void startActivity(Class<?> targetClass,Bundle bundle){
        Intent intent = new Intent(this,targetClass);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }


    /**
     * 带动画的跳转
     * @param targetClass  目标Activity的字节码文件
     * @param inAnimID 进入时的动画  传入res/anim/xxx.xml文件即可
     * @param outAnimID 出来时的动画
     */
    protected void startActivity(Class<?> targetClass,int inAnimID,int outAnimID){
        Intent intent = new Intent(this,targetClass);
        startActivity(intent);
        overridePendingTransition(inAnimID,outAnimID);
    }

    /**
     * 带动画的跳转，并传递参数
     * @param targetClass 目标Activity的字节码文件
     * @param inAnimID 进入时的动画  传入res/anim/xxx.xml文件即可
     * @param outAnimID 出来时的动画
     * @param bundle 参数
     */
    protected void startActivity(Class<?> targetClass,int inAnimID,int outAnimID,Bundle bundle){
        Intent intent = new Intent(this,targetClass);
        intent.putExtras(bundle);
        startActivity(intent);
        overridePendingTransition(inAnimID,outAnimID);
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

       LogUtil.d(TAG, getClass().getSimpleName() + " onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.d(TAG,getClass().getSimpleName()+" onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.d(TAG,getClass().getSimpleName()+" onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.d(TAG,getClass().getSimpleName()+" onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        清空Activity
        if (onlineActivitylist.contains(this)){
            onlineActivitylist.remove(this);
        }
        LogUtil.d(TAG,getClass().getSimpleName()+" onDestroy()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.d(TAG,getClass().getSimpleName()+" onRestart()");

    }
}
