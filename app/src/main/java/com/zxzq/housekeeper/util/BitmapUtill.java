package com.zxzq.housekeeper.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * 位图工具类
 * Created by Administrator on 2016/11/7.
 */

public class BitmapUtill {
    //传路径，加载其对应的位图
    public static Bitmap loadBitmap(String pathName,SizeMessage sizeMessage){
        //目标控件的宽度和高度
        int targetWidth = sizeMessage.getWidth_sm();
        int targetHeight = sizeMessage.getHeight_sm();

        //实例化一个Options对象,Options这个类可以保存图像信息
        BitmapFactory.Options options = new BitmapFactory.Options();
        //打开边界处理，此时内存并不会真正的加载bitmap，只是拿到图片的sizeMessage信息
        options.inJustDecodeBounds = true;
        //返回为null，只是将图像信息保存在options对象中
        BitmapFactory.decodeFile(pathName,options);
        //资源的宽度和高度
        int resW = options.outWidth;
        int resH = options.outHeight;

        //如果资源的宽度和高度均小于目标控件的宽度和高度，则将inSampleSize设置为1
        if (resW <= targetWidth && resH <=targetHeight){
            options.inSampleSize = 1;
        }else {
            //将两者中的最大值赋值为inSampleSize,即加载资源时的比例
            int scaleW = resW/targetWidth;
            int scaleH = resH/targetHeight;
            options.inSampleSize = scaleW>scaleH ? scaleW : scaleH;
        }
        //关闭边界处理，此时可以取出真正的bitmap
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(pathName,options);
        return bitmap;

    }

    //传资源ID，加载其对应的位图
    public static Bitmap loadBitmap(int resId,SizeMessage sizeMessage){
        Context context = sizeMessage.getContext();
        int targetWidth = sizeMessage.getWidth_sm();
        int targetHeight = sizeMessage.getHeight_sm();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        //返回为null，只是将图像信息保存在options对象中
        BitmapFactory.decodeResource(context.getResources(),resId,options);
        int resW = options.outWidth;
        int resH = options.outHeight;
        if (resW <= targetWidth && resH <= targetHeight){
            options.inSampleSize = 1;
        }else{
            int scaleW = resW / targetWidth;
            int scaleH = resH / targetHeight;
            options.inSampleSize = scaleW>scaleH?scaleW : scaleH;
        }
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),resId,options);
        return bitmap;
    }

    //保存图片大小信息的类
    public static class SizeMessage{
        //图片宽度
        private int width_sm;
        //图片高度
        private int height_sm;
        private Context context;

        public Context getContext() {
            return context;
        }

        public void setContext(Context context) {
            this.context = context;
        }

        public SizeMessage(Context context, boolean px, int width, int height) {
            this.context=context;
            if (!px){
                //宽度和高度 的长度单位如果不是px，则将其转换为以px为单位
                width_sm = DeviceUtill.dp2px(context,width);
                height_sm = DeviceUtill.dp2px(context,height);
            }
            this.width_sm = width;
            this.height_sm = height;
        }
        public int getWidth_sm() {
            return width_sm;
        }

        public int getHeight_sm() {
            return height_sm;
        }

        public void setWidth_sm(int width_sm) {
            this.width_sm = width_sm;
        }

        public void setHeight_sm(int height_sm) {
            this.height_sm = height_sm;
        }


    }
}
