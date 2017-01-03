package com.zxzq.housekeeper.biz;

import android.content.Context;

import com.zxzq.housekeeper.util.LogUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2016/11/22.
 * 用来操作本地Assets文件内的db数据的管理类
 */

public class AssetsDBManager {

    /**
     * 复制本地Assets中的db文件到指定目录中
     * @param context
     * @param path
     *           Assets内db文件路径
     * @param toFile
     *          目标位置
     * @throws IOException
     * */
    public static void copyAssetsFileToFile(Context context, String path, File toFile) throws IOException{
        LogUtil.d("AssetsDBManager","copyAssetsFileToFile start");
        LogUtil.d("AssetsDBManager","file path:"+path);
        LogUtil.d("AssetsDBManager","toFile:"+toFile.getAbsolutePath());
        InputStream inSream=null;
        BufferedInputStream bufferedInputStream=null;
        BufferedOutputStream bufferedOutputStream=null;
        try {
            inSream=context.getAssets().open(path);
            bufferedInputStream = new BufferedInputStream(inSream);
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(toFile,false));
            int len=0;
            byte[] bytes = new byte[1024];
            while((len = bufferedInputStream.read(bytes)) != -1 ){
                bufferedOutputStream.write(bytes,0,len);
            }
            bufferedOutputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(bufferedInputStream!=null){
                bufferedInputStream.close();
            }

            if(bufferedOutputStream!=null){
                bufferedOutputStream.close();
            }

            if (inSream != null){
                inSream.close();
            }

            LogUtil.d("AssetsDBManager","copyAssetsFileToFile end");
        }

    }
}
