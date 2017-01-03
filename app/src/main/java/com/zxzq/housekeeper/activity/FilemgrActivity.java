package com.zxzq.housekeeper.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zxzq.housekeeper.R;
import com.zxzq.housekeeper.base.BaseActivity;
import com.zxzq.housekeeper.biz.FileManager;
import com.zxzq.housekeeper.util.CommonUtil;
import com.zxzq.housekeeper.util.FileTypeUtil;

public class FilemgrActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_file_size;//主页面显示的总的文件大小
    private RelativeLayout file_rl_all;//全部，布局
    private RelativeLayout file_rl_file;//文件布局
    private RelativeLayout file_rl_vedio;//视频的布局
    private RelativeLayout file_rl_music;//音频的布局
    private RelativeLayout file_rl_picture;//图像的布局
    private RelativeLayout file_rl_zip;//压缩包的布局
    private RelativeLayout file_rl_apk;//程序包的布局

    private TextView file_rl_all_text;//全部的显示大小的文本
    private TextView file_rl_file_tv;//文件的显示大小的文本
    private TextView file_rl_vedio_tv;//视频的显示大小的文本
    private TextView file_rl_music_tv;//音频的显示大小的文本
    private TextView file_rl_picture_tv;//图像的显示大小的文本
    private TextView file_rl_zip_tv;//压缩包的显示大小的文本
    private TextView file_rl_apk_tv;//程序包的显示大小的文本

    private ProgressBar file_rl_all_pb;//全部的进度条
    private ProgressBar file_rl_file_pb;//文件的进度条
    private ProgressBar file_rl_vedio_pb;//视频的进度条
    private ProgressBar file_rl_music_pb;//音频的进度条
    private ProgressBar file_rl_picture_pb;//图像的进度条
    private ProgressBar file_rl_zip_pb;//压缩包的进度条
    private ProgressBar file_rl_apk_pb;//程序包的进度条

    private ImageView file_rl_all_iv;//的向右箭头
    private ImageView file_rl_file_iv;//的向右箭头
    private ImageView file_rl_vedio_iv;//的向右箭头
    private ImageView file_rl_music_iv;//的向右箭头
    private ImageView file_rl_picture_iv;//的向右箭头
    private ImageView file_rl_zip_iv;//的向右箭头
    private ImageView file_rl_apk_iv;//的向右箭头

    FileManager fileManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filemgr);
        String actionName = getResources().getString(R.string.filemgr);
        initActionBar(actionName,R.drawable.btn_homeasup_default,-1,this);
        intiWight();
        asynLoadData();
    }

    @Override
    protected void myHandlerMessage(Message msg) {
        super.myHandlerMessage(msg);
        switch (msg.what){
            case 1:
                tv_file_size.setText(CommonUtil.getFileSize(fileManager.getAllArraySize()));
                file_rl_all_text.setText(CommonUtil.getFileSize(fileManager.getAllArraySize()));

                String typeName = (String) msg.obj;
                if (typeName.equals(FileTypeUtil.TYPE_TXT)){
                    file_rl_file_tv.setText(CommonUtil.getFileSize(fileManager.getFileArraySize()));
                }else if (typeName.equals(FileTypeUtil.TYPE_VIDEO)){
                    file_rl_vedio_tv.setText(CommonUtil.getFileSize(fileManager.getVideoArraySize()));
                }else if (typeName.equals(FileTypeUtil.TYPE_AUDIO)){
                    file_rl_music_tv.setText(CommonUtil.getFileSize(fileManager.getMusicArraySize()));
                }else if (typeName.equals(FileTypeUtil.TYPE_IMAGE)){
                    file_rl_picture_tv.setText(CommonUtil.getFileSize(fileManager.getPictureArraySize()));
                }else if (typeName.equals(FileTypeUtil.TYPE_ZIP)){
                    file_rl_zip_tv.setText(CommonUtil.getFileSize(fileManager.getZipArraySize()));
                }else if (typeName.equals(FileTypeUtil.TYPE_APK)){
                    file_rl_apk_tv.setText(CommonUtil.getFileSize(fileManager.getApkArraySize()));
                }
                break;
            case 2:
                //最终确认值
                tv_file_size.setText(CommonUtil.getFileSize(fileManager.getAllArraySize()));
                file_rl_all_text.setText(CommonUtil.getFileSize(fileManager.getAllArraySize()));
                file_rl_file_tv.setText(CommonUtil.getFileSize(fileManager.getFileArraySize()));
                file_rl_vedio_tv.setText(CommonUtil.getFileSize(fileManager.getVideoArraySize()));
                file_rl_music_tv.setText(CommonUtil.getFileSize(fileManager.getMusicArraySize()));
                file_rl_picture_tv.setText(CommonUtil.getFileSize(fileManager.getPictureArraySize()));
                file_rl_zip_tv.setText(CommonUtil.getFileSize(fileManager.getZipArraySize()));
                file_rl_apk_tv.setText(CommonUtil.getFileSize(fileManager.getApkArraySize()));
                //将进度条，隐藏
                file_rl_all_pb.setVisibility(View.GONE);
                file_rl_file_pb.setVisibility(View.GONE);
                file_rl_vedio_pb.setVisibility(View.GONE);
                file_rl_music_pb.setVisibility(View.GONE);
                file_rl_picture_pb.setVisibility(View.GONE);
                file_rl_zip_pb.setVisibility(View.GONE);
                file_rl_apk_pb.setVisibility(View.GONE);
                //显示向右箭头
                file_rl_all_iv.setVisibility(View.VISIBLE);
                file_rl_file_iv.setVisibility(View.VISIBLE);
                file_rl_vedio_iv.setVisibility(View.VISIBLE);
                file_rl_music_iv.setVisibility(View.VISIBLE);
                file_rl_picture_iv.setVisibility(View.VISIBLE);
                file_rl_zip_iv.setVisibility(View.VISIBLE);
                file_rl_apk_iv.setVisibility(View.VISIBLE);
                break;
        }
    }

    //回调接口初始化
    private FileManager.SearchFileListener listener = new FileManager.SearchFileListener() {
        @Override
        public void searching(String typeName) {
            Message message = mHandler.obtainMessage();
            message.what = 1;
            message.obj = typeName;
            mHandler.sendMessage(message);
        }

        @Override
        public void end(boolean isExceptionEnd) {
            mHandler.sendEmptyMessage(2);
        }
    };



    private void asynLoadData(){
        fileManager = FileManager.getFileManager();
        fileManager.setSearchFileListener(listener);
        new Thread(){
            @Override
            public void run() {
                super.run();
                fileManager.searchSDFile();
            }
        }.start();
    }
    /**
     * 利用映射实现item的点击事件
     * @param view
     */
    public void hitListItem(View view){
        switch (view.getId()){
            case R.id.file_rl_all://全部
            case R.id.file_rl_file://文件
            case R.id.file_rl_vedio://视频
            case R.id.file_rl_music://音频
            case R.id.file_rl_picture://图像
            case R.id.file_rl_zip://压缩包
            case R.id.file_rl_apk://程序包
                Bundle bundle = new Bundle();
                bundle.putInt("idName",view.getId());
                startActivity(FileShowActivity.class,bundle);

                break;
        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                startActivity(HomeActivity.class);
                break;
        }
    }
    private void intiWight() {
        //相对布局实例化
        tv_file_size = (TextView) findViewById(R.id.tv_file_size);
        file_rl_all = (RelativeLayout) findViewById(R.id.file_rl_all);
        file_rl_file = (RelativeLayout) findViewById(R.id.file_rl_file);
        file_rl_vedio = (RelativeLayout) findViewById(R.id.file_rl_vedio);
        file_rl_music = (RelativeLayout) findViewById(R.id.file_rl_music);
        file_rl_picture = (RelativeLayout) findViewById(R.id.file_rl_picture);
        file_rl_zip = (RelativeLayout) findViewById(R.id.file_rl_zip);
        file_rl_apk = (RelativeLayout) findViewById(R.id.file_rl_apk);
        //大小文本实例化
        file_rl_all_text = (TextView) findViewById(R.id.file_rl_all_text);
        file_rl_file_tv = (TextView) findViewById(R.id.file_rl_file_tv);
        file_rl_vedio_tv = (TextView) findViewById(R.id.file_rl_vedio_tv);
        file_rl_music_tv = (TextView) findViewById(R.id.file_rl_music_tv);
        file_rl_picture_tv = (TextView) findViewById(R.id.file_rl_picture_tv);
        file_rl_zip_tv = (TextView) findViewById(R.id.file_rl_zip_tv);
        file_rl_apk_tv = (TextView) findViewById(R.id.file_rl_apk_tv);
        //进度条实例化
        file_rl_all_pb = (ProgressBar) findViewById(R.id.file_rl_all_pb);
        file_rl_file_pb = (ProgressBar) findViewById(R.id.file_rl_file_pb);
        file_rl_vedio_pb = (ProgressBar) findViewById(R.id.file_rl_vedio_pb);
        file_rl_music_pb = (ProgressBar) findViewById(R.id.file_rl_music_pb);
        file_rl_picture_pb = (ProgressBar) findViewById(R.id.file_rl_picture_pb);
        file_rl_zip_pb = (ProgressBar) findViewById(R.id.file_rl_zip_pb);
        file_rl_apk_pb = (ProgressBar) findViewById(R.id.file_rl_apk_pb);
        //向右箭头实例化
        file_rl_all_iv = (ImageView) findViewById(R.id.file_rl_all_iv);
        file_rl_file_iv = (ImageView) findViewById(R.id.file_rl_file_iv);
        file_rl_vedio_iv = (ImageView) findViewById(R.id.file_rl_vedio_iv);
        file_rl_music_iv = (ImageView) findViewById(R.id.file_rl_music_iv);
        file_rl_picture_iv = (ImageView) findViewById(R.id.file_rl_picture_iv);
        file_rl_zip_iv = (ImageView) findViewById(R.id.file_rl_zip_iv);
        file_rl_apk_iv = (ImageView) findViewById(R.id.file_rl_apk_iv);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fileManager.setStopSearch(true);
        Thread.interrupted();

    }
}
