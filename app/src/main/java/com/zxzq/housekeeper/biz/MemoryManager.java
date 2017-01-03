package com.zxzq.housekeeper.biz;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import com.zxzq.housekeeper.util.LogUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import static android.os.Environment.getExternalStorageState;

/**
 * Created by Administrator on 2016/11/4.
 */

public class MemoryManager {
    private static final String TAG = MemoryManager.class.getSimpleName();

//    设备完整运行内存  单位 B
//    \\s 表示空格,回车,换行等空白符
//    +号表示一个或多个的意思
    public static long getPhoneTotalRamMemory(){
        try {
            FileReader fileReader = new FileReader("/proc/meminfo");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String text = bufferedReader.readLine();
            String[] array = text.split("\\s+");
            return Long.valueOf(array[1])*1024;//原为 kb,  转为 b
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

//    设备空闲运行内存,单位 B

    public static long getPhoneFreeRamMemory(Context context){
//        这个MemoryInfo是ActivityManager中的
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(memoryInfo);
        return memoryInfo.availMem;
    }

    //    获得手机内置SD卡的路径
    public static String getPhoneInSDCardPath() {
        String sdCardState = getExternalStorageState();
        if (sdCardState.equals(Environment.MEDIA_MOUNTED)) {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath();
            LogUtil.e(TAG, "getPhoneInSDCardPath: " + path);
            return path;
        }
        return null;
    }

    public static String getPhoneOutSDCardPath(){
        Map<String,String> maps = System.getenv();
        if (maps.containsKey("SECONDARY_STORAGE")){
            String path = maps.get("SECONDARY_STORAGE");
            String[] pathsArray = path.split(":");
            if (pathsArray == null || pathsArray.length <= 0){
                return null;
            }
            return pathsArray[0];
        }
        return null;
    }
    //    获得手机外置SD卡总空间
    public static long getPhoneOutSDCardSize() {
        try {
            File file = new File(getPhoneOutSDCardPath());
            if (file == null) {
                return 0;
            }
            StatFs statFs = new StatFs(file.getPath());
            long blockCountLong = statFs.getBlockCountLong();
            long blockSizeLong = statFs.getBlockSizeLong();
            return blockSizeLong * blockCountLong;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.d(TAG, "外置SD卡异常: ");
            return 0;
        }
    }

//    获取手机外置SD卡可用空间大小，单位：B

    public static long getPhoneOutSDCardFreeSize() {
        try {
            File file = new File(getPhoneOutSDCardPath());
            if (file == null) {
                return 0;
            }
            StatFs statFs = new StatFs(file.getPath());
            long blockCountLong = statFs.getAvailableBlocksLong();
            long blockSizeLong = statFs.getBlockSizeLong();
            return blockSizeLong * blockCountLong;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.d(TAG, "外置SD卡异常: ");
            return 0;
        }
    }


//    获取手机自身存储空间大小

    public static long getPhoneSelfSize() {

        File rootDirectory = Environment.getRootDirectory();

        Log.e(TAG, "Environment.getRootDirectory(): " + rootDirectory.getAbsolutePath());

        StatFs statFs = new StatFs(rootDirectory.getPath());
        long blockCountLong = statFs.getBlockCountLong();
        long blockSizeLong = statFs.getBlockSizeLong();
        long root = blockCountLong * blockSizeLong;

        File downloadCacheDirectory = Environment.getDownloadCacheDirectory();

        Log.e(TAG, "Environment.getDownloadCacheDirectory(): " + downloadCacheDirectory.getAbsolutePath());

        statFs = new StatFs(rootDirectory.getPath());
        blockCountLong = statFs.getBlockCountLong();
        blockSizeLong = statFs.getBlockSizeLong();
        long download = blockCountLong * blockSizeLong;

        return root + download;

    }

    //    获取手机自身可用的存储空间
    public static long getPhoneSelfFreeSize() {

        File rootDirectory = Environment.getRootDirectory();
        Log.e(TAG, "Environment.getRootDirectory(): " + rootDirectory.getAbsolutePath());

        StatFs statFs = new StatFs(rootDirectory.getPath());
        long blockCountLong = statFs.getAvailableBlocksLong();
        long blockSizeLong = statFs.getBlockSizeLong();
        long root = blockCountLong * blockSizeLong;

        File downloadCacheDirectory = Environment.getDownloadCacheDirectory();
        Log.e(TAG, "Environment.getDownloadCacheDirectory(): " + downloadCacheDirectory.getAbsolutePath());
        statFs = new StatFs(rootDirectory.getPath());
        blockCountLong = statFs.getAvailableBlocksLong();
        blockSizeLong = statFs.getBlockSizeLong();
        long download = blockCountLong * blockSizeLong;

        return root + download;

    }

//    获得手机内置SD卡的总存储空间

    public static long getPhoneSelfSDCardSize() {
        String state = Environment.getExternalStorageState();
        if (!state.equals(Environment.MEDIA_MOUNTED)) {
            return 0;
        }
        StatFs statFs = new StatFs(getPhoneInSDCardPath());
        long blockCountLong = statFs.getBlockCountLong();
        long blockSizeLong = statFs.getBlockSizeLong();
        return blockSizeLong * blockCountLong;
    }

    //      获得手机内置SD卡的可用存储空间
    public static long getPhoneSelfSDCardFreeSize() {
        String state = Environment.getExternalStorageState();
        if (!state.equals(Environment.MEDIA_MOUNTED)) {
            return 0;
        }
        StatFs statFs = new StatFs(getPhoneInSDCardPath());
        long blockCountLong = statFs.getAvailableBlocksLong();
        long blockSizeLong = statFs.getBlockSizeLong();
        return blockSizeLong * blockCountLong;
    }


    //    手机总存储空间
    public static long getPhoneAllSize() {
        File file = Environment.getRootDirectory();
        StatFs statFs = new StatFs(file.getPath());
        long blockCountLong = statFs.getBlockCountLong();
        long blockSizeLong = statFs.getBlockSizeLong();
        long root = blockSizeLong * blockCountLong;

        file = Environment.getDownloadCacheDirectory();
        statFs = new StatFs(file.getPath());
        blockCountLong = statFs.getBlockCountLong();
        blockSizeLong = statFs.getBlockSizeLong();
        long down = blockSizeLong * blockCountLong;

        file = Environment.getDataDirectory();
        statFs = new StatFs(file.getPath());
        blockCountLong = statFs.getBlockCountLong();
        blockSizeLong = statFs.getBlockSizeLong();
        long data = blockSizeLong * blockCountLong;

        return root + data + down;

    }

//    手机可用的总存储空间

    public static long getPhoneAllFreeSize() {
        File file = Environment.getRootDirectory();
        StatFs statFs = new StatFs(file.getPath());
        long blockCountLong = statFs.getAvailableBlocksLong();
        long blockSizeLong = statFs.getBlockSizeLong();
        long root = blockSizeLong * blockCountLong;

        file = Environment.getDownloadCacheDirectory();
        statFs = new StatFs(file.getPath());
        blockCountLong = statFs.getAvailableBlocksLong();
        blockSizeLong = statFs.getBlockSizeLong();
        long down = blockSizeLong * blockCountLong;

        file = Environment.getDataDirectory();
        statFs = new StatFs(file.getPath());
        blockCountLong = statFs.getAvailableBlocksLong();
        blockSizeLong = statFs.getBlockSizeLong();
        long data = blockSizeLong * blockCountLong;

        return root + data + down;
    }
}