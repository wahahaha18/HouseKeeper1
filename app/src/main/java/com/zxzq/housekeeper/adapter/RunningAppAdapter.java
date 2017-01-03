package com.zxzq.housekeeper.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxzq.housekeeper.R;
import com.zxzq.housekeeper.base.adapter.BaseBaseAdapter;
import com.zxzq.housekeeper.entity.RunningAppInfo;
import com.zxzq.housekeeper.util.BitmapCache;
import com.zxzq.housekeeper.util.BitmapUtill;
import com.zxzq.housekeeper.util.CommonUtil;
import com.zxzq.housekeeper.util.LogUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/10.
 */

public class RunningAppAdapter extends BaseBaseAdapter<RunningAppInfo> {
    private BitmapCache bitmapCache;
    private boolean isFling;
    private Bitmap defaultBitmap;
    private LayoutInflater layoutInflater;
    private static ArrayList<RunningAppInfo> runningAppInfos;
//    显示用户进程
    private int state = 0;
//    显示用户进程
    public static final int STATE_SHOW_USER = 0;
//    显示全部进程
    public static final int STATE_SHOW_ALL = 1;
//    显示系统进程
    public static final int STATE_SHOW_SYS = 2;

    public RunningAppAdapter(Context context) {
        super(context);
        runningAppInfos = new ArrayList<RunningAppInfo>();
        layoutInflater = LayoutInflater.from(context);
        bitmapCache = BitmapCache.getInstance();
        BitmapUtill.SizeMessage sizeMessage = new BitmapUtill.SizeMessage(context,false,60,60);
        defaultBitmap = BitmapUtill.loadBitmap(R.drawable.ic_launcher,sizeMessage);
    }

/*//    获取数据源集合
    public static ArrayList<RunningAppInfo> getDataList(){
        if (runningAppInfos == null){
            return null;
        }
        Log.e("RunningAppAdapter", "getDataList:"+runningAppInfos.size());
        return runningAppInfos;
    }*/

//    获取位图
    public Bitmap getBitmap(int position){
        if (isFling){
            return defaultBitmap;
        }
        RunningAppInfo runningAppInfo = getItem(position);
        Drawable drawable = runningAppInfo.getIcon();
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        BitmapCache.addCacheBitmap(bitmap,position);
//        Bitmap bitmap = BitmapCache.getBitmap(position, context);

        return bitmap;
    }
    public boolean isFling() {
        return isFling;
    }

    public void setFling(boolean fling) {
        isFling = fling;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public View initGetView(int position, View convertView, ViewGroup parent) {
        RunningAppAdapter.ViewHolder viewHolder = null;
        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.layout_speedup_showapp_listitem,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.cb_speedup_del = (CheckBox) convertView.findViewById(R.id.cb_speedup_del);
            viewHolder.iv_speedup_appIcon = (ImageView) convertView.findViewById(R.id.iv_speedup_appIcon);
            viewHolder.tv_speedup_app_Label = (TextView) convertView.findViewById(R.id.tv_speedup_app_Label);
            viewHolder.tv_speedup_app_PackageName = (TextView) convertView.findViewById(R.id.tv_speedup_app_PackageName);
            viewHolder.tv_speedup_app_Sys = (TextView) convertView.findViewById(R.id.tv_speedup_app_Sys);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

//        获得数据
        RunningAppInfo runningAppInfo = getItem(position);
//        复选框
        boolean isClear = runningAppInfo.isClear();
//        位图
        Bitmap bitmap = getBitmap(position);
//        应用名
        String lable = runningAppInfo.getLableName();
        LogUtil.d("RunningAppAdapter",lable);
//        是否是示系统进程
        String isSystem = runningAppInfo.isSystem() ?  "系统进程" : "";
//      应用大小
        long size = runningAppInfo.getSize();
        String fileSize = CommonUtil.getFileSize(size);
        String sizeFinal = "内存："+fileSize;
        LogUtil.d("RunningAppAdapter",sizeFinal);

//        给控件赋值
        viewHolder.cb_speedup_del.setChecked(isClear);
        viewHolder.iv_speedup_appIcon.setImageBitmap(bitmap);
        viewHolder.tv_speedup_app_Label.setText(lable);
        viewHolder.tv_speedup_app_PackageName.setText(sizeFinal);
        viewHolder.tv_speedup_app_Sys.setText(isSystem);

        viewHolder.cb_speedup_del.setTag(position);
        viewHolder.cb_speedup_del.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int position = (int) buttonView.getTag();
                getItem(position).setClear(isChecked);
            }
        });
        return convertView;
    }
    class ViewHolder{
        CheckBox cb_speedup_del;
        ImageView iv_speedup_appIcon;
        TextView tv_speedup_app_Label;
        TextView tv_speedup_app_PackageName;
        TextView tv_speedup_app_Sys;
    }
}
