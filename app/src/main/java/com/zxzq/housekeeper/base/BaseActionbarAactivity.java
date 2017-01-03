package com.zxzq.housekeeper.base;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxzq.housekeeper.R;
import com.zxzq.housekeeper.util.LogUtil;




/**
 * 带 ActiooonBar  控件数据初始化方法及 onClick  监听的基础 Activity
 * Created by Administrator on 2016/10/28.
 */

public abstract class BaseActionbarAactivity extends BaseActivity {

    /**
     * 设置 ActionBar  控件上的数据
     *
     * @param resIdTitle
     * 中间主标题文本 id, 没有标题时可使用{@link #NULL_ID}
     * @param resIdLeft
     * 左侧图标资源 id, 没有图标时可使用{@link #NULL_ID}
     * @param resIdRight
     * 右侧图标资源 id, 没有图标 时可使用{@link #NULL_ID}
     */

    //不需要设置的控件
    private static final int NULL_ID = -1;

    private static final String TAG = BaseActionbarAactivity.class.getSimpleName();


    /**
     * 给ActionBar赋值s
     * @param resIdTitle  中间标题的资源
     * @param resIdLeft  左边图片的资源
     * @param resIdRight 右边图片的资源
     */
    public void setActionBar(int resIdTitle,int resIdLeft,int resIdRight){

        try {
            //fvb findViewById  alt+enter
            TextView tv_action_title = (TextView) findViewById(R.id.tv_actionbar_title);
            ImageView iv_action_left = (ImageView) findViewById(R.id.iv_action_left);
            ImageView iv_action_right = (ImageView) findViewById(R.id.iv_action_right);
            //若左边图片资源存在，则为左边ImageView赋值，否则，设置左边ImageView不可见
            if (resIdLeft != NULL_ID){
                iv_action_left.setImageResource(resIdLeft);
            }else{
                iv_action_left.setVisibility(View.INVISIBLE);
            }
            if (resIdRight != NULL_ID){
                iv_action_right.setImageResource(resIdRight);
            }else{
                iv_action_right.setVisibility(View.INVISIBLE);
            }
            if (resIdTitle != NULL_ID){
                tv_action_title.setText(resIdTitle);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.d(TAG,"ActionBar有异常！当前页面是否忘记include==include_actionbar页面");
        }

//
    }
}
