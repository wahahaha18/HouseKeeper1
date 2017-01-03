package com.zxzq.housekeeper.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zxzq.housekeeper.R;

/**
 * Created by Administrator on 2016/11/1.
 */

public class ActionBarView extends LinearLayout {
    //不需要为控件赋值时，资源Id传入 -1
    public static final int NULL_ID = -1;

    //声明ActionBar中的控件
    private ImageView iv_left,iv_right;
    private TextView tv_title;
    //布局加载器
//    LayoutInflater layoutInflater;
    public ActionBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //加载自定义的ActionBar,********************
       inflate(context,R.layout.activity_actionbar,this);
        iv_left= (ImageView) findViewById(R.id.iv_left);
        iv_right = (ImageView) findViewById(R.id.iv_right);
        tv_title = (TextView) findViewById(R.id.tv_title);
    }

    /**
     *
     * @param title  中间标题的资源
     * @param resIdLeft  左边图片的资源
     * @param resIdRight  右边图片的资源
     * @param listener 左右两边监听事件
     */
    public void initActionBar(String title,int resIdLeft,int resIdRight,OnClickListener listener){
        if (title == null){
            tv_title.setText("");
        }else{
            tv_title.setText(title);
        }
        //左边图片有资源，设置图片及其监听事件；否则，左边图片不显示
        if (resIdLeft != NULL_ID){
            iv_left.setImageResource(resIdLeft);
            iv_left.setOnClickListener(listener);
        }else {
            iv_left.setVisibility(INVISIBLE);
        }
        if (resIdRight != NULL_ID){
            iv_right.setImageResource(resIdRight);
            iv_right.setOnClickListener(listener);
        }else {
            iv_right.setVisibility(INVISIBLE);
        }
    }
}
