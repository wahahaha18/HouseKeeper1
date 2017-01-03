package com.zxzq.housekeeper.service;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.IBinder;

import java.io.IOException;

public class MusicService extends Service {
    //声明音乐播放器
    MediaPlayer mediaPlayer;

    public MusicService() {
    }

    /**
     * 设置播放音乐
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //获取Assets资源文件
        AssetManager assetManager = getAssets();
        //加载音乐资源
        try {
            AssetFileDescriptor fileDescriptor = assetManager.openFd("ashanti.mp3");
            mediaPlayer = new MediaPlayer();
            //设置要播放的音乐
            mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(),fileDescriptor.getStartOffset(),fileDescriptor.getLength());
//            循环播放
            mediaPlayer.setLooping(true);
            //准备播放音乐
            mediaPlayer.prepare();
            //开始播放音乐
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        // 停止播放音乐
        mediaPlayer.stop();
        //释放mediaPlayer所占用的资源
        mediaPlayer.release();
//        如果引用是null,垃圾回收机制就会将不用的资源回收
        mediaPlayer = null;
        super.onDestroy();


    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
