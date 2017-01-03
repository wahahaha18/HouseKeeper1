package com.zxzq.housekeeper.biz;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Environment;

import com.zxzq.housekeeper.entity.ClearInfo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/21.
 */

public class DBClearPathManager {
    public static final String FILE_NAME = "clearpath.db";//数据文件名
    public static final String PACKAGE_NAME = "com.zxzq.housekeeper";//应用包名
    public static final String FILE_PATH = "/data/data"+"/"+PACKAGE_NAME;//应用包的路径
    private static ArrayList<ClearInfo> infos;
    public static ArrayList<ClearInfo> getDataOfClearPath(){
        ArrayList<ClearInfo> clearInfos = new ArrayList<ClearInfo>();//可能清除的软件的信息的集合
        String path = FILE_PATH + "/" +FILE_NAME;//数据文件的文件路径
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(path, null);//创建数据库。如果存在就打开，如果不存在则创建
        String sql = "select * from softdetail";//编写查询语句
        Cursor cursor = database.rawQuery(sql, null);//利用游标，执行查询语句
        if (cursor.moveToFirst()){//将游标指向第一行信息
            while (cursor.moveToNext()){//当游标移动到下一个位置
                int _id = cursor.getInt(cursor.getColumnIndex("_id"));
                String softChinesename = cursor.getString(cursor.getColumnIndex("softChinesename"));
                String softEnglishname = cursor.getString(cursor.getColumnIndex("softEnglishname"));
                String apkname = cursor.getString(cursor.getColumnIndex("apkname"));
                String filepath = cursor.getString(cursor.getColumnIndex("filepath"));
                filepath = Environment.getExternalStorageDirectory().getPath() + filepath;

                ClearInfo clearInfo = new ClearInfo(_id,softChinesename,softEnglishname,apkname,filepath);
                clearInfos.add(clearInfo);
            }
        }
        //关闭游标，关闭数据库
        cursor.close();
        database.close();
        return clearInfos;
    }

    /**
     * 获得手机上需要清理的软件
     */
    public static ArrayList<ClearInfo> getPhoneClearFiles(Context context){
        if (infos == null){
            infos = getDataOfClearPath();
        }
        ArrayList<ClearInfo> phoneClearFiles = new ArrayList<ClearInfo>();
        for (ClearInfo clearInfo : infos){
            File file = new File(clearInfo.getFilepath());
            if (file.exists()){
                Drawable icon = null;
                try {
                    icon = context.getPackageManager().getApplicationIcon(clearInfo.getApkname());
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                clearInfo.setApkIcon(icon);
                phoneClearFiles.add(clearInfo);
            }
        }
        return phoneClearFiles;
    }
    /**
     * 确定数据库是否在手机上,不在手机上就将数据库文件拷贝到手机上
     */
    public static void updateDataBase(InputStream inputStream){
        File file = new File(FILE_PATH,FILE_NAME);
        if (file.exists()){
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(inputStream);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            byte[] bytes = new byte[1024];
            int non = 0;
            while ((non = bis.read(bytes) )!=-1){
                bos.write(bytes,0,non);
            }
            bos.flush();
            bis.close();
            bos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

    }


}
