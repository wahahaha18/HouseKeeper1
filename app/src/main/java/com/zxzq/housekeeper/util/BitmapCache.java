package com.zxzq.housekeeper.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Hashtable;

/**
 * Created by Administrator on 2016/11/7.
 */

public class BitmapCache {
    //对象锁
    static Object object = new Object();
    //单例对象
    static BitmapCache bitmapCache;
    //存放软引用的集合
    static Hashtable<Integer,MySoftRef> hashRefs;
    //引用队列
    static ReferenceQueue<Bitmap> q;

    //构造方法
    public BitmapCache(){
        hashRefs = new Hashtable<Integer, MySoftRef>();
        q = new ReferenceQueue<Bitmap>();
    }

    //获取bitmapCache的单例对象  单态模式中的懒汉式
    public static BitmapCache getInstance(){
        if (bitmapCache ==null){
            synchronized (object){
                if (bitmapCache == null){
                    bitmapCache = new BitmapCache();
                }
            }
        }
        return bitmapCache;
    }

    //将bitmap添加到缓存中
    public static void addCacheBitmap(Bitmap bitmap,int key){
        //添加缓存之前，先清除一下垃圾引用
        cleanCache();
        MySoftRef mySoftRef = new MySoftRef(bitmap,q,key);
        hashRefs.put(key,mySoftRef);
    }

    /**
     * 根据资源ID，获取其相对应的bitmap对象
     * @param resId 资源ID，可以是drawable文件夹下面的图片资源
     * @param context 上下文
     * @return
     */
    public static Bitmap getBitmap(int resId, Context context){
        Bitmap bitmap = null;
        //如果缓存中存在该bitmap，则直接从缓存中获取
        if (hashRefs.containsKey(resId)){
            MySoftRef mySoftRef = hashRefs.get(resId);
            bitmap = mySoftRef.get();
        }
        //若缓存中没有该bitmap，则从资源ID中获取其bitmap对象，并将获取到的bitmap对象添加到缓存中，以便下次从缓存中获取
        if (bitmap == null){
            bitmap = BitmapFactory.decodeStream(context.getResources().openRawResource(resId));
            addCacheBitmap(bitmap,resId);
        }
        return bitmap;
    }


    public static void clear(){
        cleanCache();
        hashRefs.clear();
       //两种清除方法的意义不一样，都写上会比较全面
        //程序猿 提醒 Java虚拟机 要进行垃圾回收，但是何时进行垃圾回收，不一定。
        // 每个jvm都有自己的垃圾回收机制，依据自己的垃圾回收策略进行垃圾回收
        System.gc();
        //让一些空的引用执行finalize()方法，进行垃圾回收，但是何时回收，也是要依据java虚拟机自己的垃圾回收策略
        System.runFinalization();
    }


    public static void cleanCache(){
        MySoftRef mySoftRef = null;
//        检测队列中是否有引用，如果有引用就清除引用，以便下次添加bitmap到缓存的集合中的时候将新的引用，
//      放到队列中。
        if ((mySoftRef = (MySoftRef)q.poll()) != null){
            hashRefs.remove(mySoftRef._key);
        }
    }




    //自定义一个内部类，继承SoftReference软引用
    public static class MySoftRef extends SoftReference<Bitmap>{
        private int _key = 0;
        //key用来标识一个唯一的bitmap对象，<? super Bitmap>这个泛型指，Bitmap或Bitmap的子类
        public MySoftRef(Bitmap bitmap, ReferenceQueue<? super Bitmap> q,int key){
//            将bitmap放入队列q中，一个队列中只有一个bitmap的软引用
            super(bitmap,q);
            this._key = key;
        }
    }
}
