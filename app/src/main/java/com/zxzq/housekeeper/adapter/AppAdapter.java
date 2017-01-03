package com.zxzq.housekeeper.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxzq.housekeeper.R;
import com.zxzq.housekeeper.base.adapter.BaseBaseAdapter;
import com.zxzq.housekeeper.entity.AppInfo;
import com.zxzq.housekeeper.util.BitmapCache;
import com.zxzq.housekeeper.util.BitmapUtill;

/**
 * Created by Administrator on 2016/11/7.
 */

public class AppAdapter extends BaseBaseAdapter<AppInfo> {
    public BitmapCache bitmapCache;
    //默认图片
    private Bitmap defaultBitmap;
    //是否在快速滑动，若在快速滑动，则显示默认图像，无需再去加载图像
    private boolean isFling;

    public boolean isFling(){
        return isFling;
    }

    public void setFling(boolean fling){
        this.isFling = fling;
    }

    //返回默认图像
    public Bitmap deBitmap(){
        if (isFling){
            return defaultBitmap;
        }
        return defaultBitmap;
    }

    public AppAdapter(Context context) {
        super(context);
        //实例化bitmapCache，得到一个唯一的对象
        bitmapCache = BitmapCache.getInstance();
        BitmapUtill.SizeMessage sizeMessage = new BitmapUtill.SizeMessage(context,false,60,60);
        defaultBitmap = BitmapUtill.loadBitmap(R.drawable.ic_launcher,sizeMessage);
        deBitmap();
    }



    @Override
    public View initGetView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.layout_phonemgr_showapp_listitem,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.cb_del = (CheckBox)convertView.findViewById(R.id.cb_del);
            viewHolder.iv_appIcon = (ImageView)convertView.findViewById(R.id.iv_appIcon);
            viewHolder.tv_appLabel = (TextView)convertView.findViewById(R.id.tv_appLabel);
            viewHolder.tv_appPackageName = (TextView)convertView.findViewById(R.id.tv_appPackageName);
            viewHolder.tv_appVersion = (TextView)convertView.findViewById(R.id.tv_appVersion);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        //获取要显示的数据，即position处的item 所需要的数据
        AppInfo appInfo = getItem(position);
        //获取是否选中
        boolean isDel = appInfo.isDel();

        //获取应用图标
        Drawable iconDrawable = appInfo.getPackageInfo().applicationInfo.loadIcon(context.getPackageManager());
        Bitmap iconBitmap =((BitmapDrawable) iconDrawable).getBitmap();
        //将获取到的应用图标添加到bitmapCache中
        bitmapCache.addCacheBitmap(iconBitmap,position);

        //获取应用名称
        String label = appInfo.getPackageInfo().applicationInfo.loadLabel(context.getPackageManager()).toString();

        //获取应用包名
        String packageName = appInfo.getPackageInfo().packageName;

        //获取版本
        String versionName = appInfo.getPackageInfo().versionName;

        //为控件赋值
        //为复选框设置选中与否的状态
        viewHolder.cb_del.setChecked(isDel);
        //从bitmapCache缓存中获取bitmap对象，并将其赋值给ImageView
        viewHolder.iv_appIcon.setImageBitmap(bitmapCache.getBitmap(position,context));

        //设置应用名称、包名、版本号等一系列值
        viewHolder.tv_appLabel.setText(label);
        viewHolder.tv_appPackageName.setText(packageName);
        viewHolder.tv_appVersion.setText(versionName);

//        可以给setTag传一个对象，再用getTag将对象取出来用
        viewHolder.cb_del.setTag(position);
        /**
         * 监听复选框的选中与否的状态，item中的（选中）状态要和AppInfo（选中）状态保持一致，一一对应
         */
        
        viewHolder.cb_del.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //复选框所在Item所对应的position,
                int position = (int)buttonView.getTag();
                //修改AppInfo的对象 中 isDel的值为监听到的状态，即选中还是未选中
                getItem(position).setDel(isChecked);
            }
        });

        return convertView;
    }
    class ViewHolder{
        CheckBox cb_del;
        ImageView iv_appIcon;
        TextView tv_appLabel;
        TextView tv_appPackageName;
        TextView tv_appVersion;
    }
}
