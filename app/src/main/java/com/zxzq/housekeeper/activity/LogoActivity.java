package com.zxzq.housekeeper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.zxzq.housekeeper.R;


/**
 * Created by Administrator on 2016/10/21.
 */

public class LogoActivity extends Activity {
    ImageView iv_logo;
    Animation alpha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logo_activity);
        iv_logo=(ImageView) findViewById(R.id.iv_logo);
        alpha= AnimationUtils.loadAnimation(LogoActivity.this,R.anim.anim_logo);
        alpha.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent=new Intent();
                intent.setClass(LogoActivity.this, HomeActivity.class);
                startActivity(intent);
                LogoActivity.this.finish();


            }

            @Override
            public void onAnimationRepeat(Animation animation) {



            }
        });
        iv_logo.setAnimation(alpha);

    }
}
