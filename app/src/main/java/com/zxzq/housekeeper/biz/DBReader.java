package com.zxzq.housekeeper.biz;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zxzq.housekeeper.entity.TelClassInfo;
import com.zxzq.housekeeper.entity.TelNumberInfo;
import com.zxzq.housekeeper.util.LogUtil;

import java.io.File;
import java.util.ArrayList;

/**
 * 读取数据库文件操作
 * Created by Administrator on 2016/11/22.
 */

public class DBReader {
    //    通讯大全File
    public static File telFile;

    //    设置静态代码块，初始化一些数据
    static{
//    数据库存放的位置（默认数据库文件存放位置）
        String dbFileDir = "data/data/com.zxzq.housekeeper/";
        File fileDir = new File(dbFileDir);
//    创建文件夹（文件目录）
        fileDir.mkdirs();
        telFile = new File(dbFileDir,"conmmonnum.db");
        LogUtil.d("DBReader","telFile file path:"+dbFileDir);
    }

    //    判断数据库文件DB是否存在
    public static boolean isExistsTeldbFile(){
        if (!telFile.exists() || telFile.length() <= 0){
            return false;
        }
        return false;
    }


    /**
     * 读取classlist表中的数据
     * */
    public static ArrayList<TelClassInfo> readTeldbClassList(){
        //        声明数据集
        ArrayList<TelClassInfo> classInfos = new ArrayList<TelClassInfo>();

        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(telFile,null);
        Cursor cursor = db.rawQuery("select * from classlist",null);

        LogUtil.d("DBReader","read teldb classlist size="+cursor.getCount());

        while(cursor.moveToNext()){
//            一行一行的读取表中的记录
            String name = cursor.getString(cursor.getColumnIndex("name"));
            int idx = cursor.getInt(cursor.getColumnIndex("idx"));
//            将读取到的数据封装成一个TelClassInfo对象
            TelClassInfo telClassInfo = new TelClassInfo(name,idx);
            classInfos.add(telClassInfo);
        }
        cursor.close();
        db.close();
//        Log日志打印集合大小，应和游标大小一致
        LogUtil.d("DBReader","read classlist size="+classInfos.size());
        return classInfos;
    }

    /**
     * 读取tableX中的数据，并将其封装之后，存放在集合中
     * @param idx 根据idx不同，确定读取哪一个表
     * return data
     * */
    public static ArrayList<TelNumberInfo> readerTeldbTable(int idx){
//        声明数据集
        ArrayList<TelNumberInfo> numberInfos = new ArrayList<TelNumberInfo>();
//        打开数据库，读取tableX中的数据
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(telFile,null);
        Cursor cursor = db.rawQuery("select * from table"+idx,null);
        LogUtil.d("DBReader","read teldb classlist size="+cursor.getCount());
        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String number = cursor.getString(cursor.getColumnIndex("number"));
            TelNumberInfo telNumberInfo = new TelNumberInfo(name,number);
            numberInfos.add(telNumberInfo);
        }
        cursor.close();
        db.close();
        LogUtil.d("DBReader","read tableX data size="+numberInfos.size());
        return numberInfos;
    }

}
