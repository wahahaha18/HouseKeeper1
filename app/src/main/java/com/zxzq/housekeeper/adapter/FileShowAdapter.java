package com.zxzq.housekeeper.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxzq.housekeeper.R;
import com.zxzq.housekeeper.base.adapter.BaseBaseAdapter;
import com.zxzq.housekeeper.entity.FileInfo;
import com.zxzq.housekeeper.util.BitmapUtill;
import com.zxzq.housekeeper.util.CommonUtil;
import com.zxzq.housekeeper.util.FileTypeUtil;

/**
 * Created by Administrator on 2016/11/18.
 */

public class FileShowAdapter extends BaseBaseAdapter <FileInfo>{
    LayoutInflater layoutInflater;
    LruCache<String,Bitmap> lruCache;
    public FileShowAdapter(Context context) {
        super(context);
        layoutInflater = getLayoutInflater();
        lruCache = new LruCache<String,Bitmap>(4*1024*1024){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }


    @Override
    public View initGetView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.layout_file_show_listiview_item,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.cb_file_item_del = (CheckBox) convertView.findViewById(R.id.cb_file_item_del);
            viewHolder.iv_file_item_appIcon = (ImageView) convertView.findViewById(R.id.iv_file_item_appIcon);
            viewHolder.tv_file_item_name = (TextView) convertView.findViewById(R.id.tv_file_item_name);
            viewHolder.tv_file_item_date = (TextView) convertView.findViewById(R.id.tv_file_item_date);
            viewHolder.tv_file_item_size = (TextView) convertView.findViewById(R.id.tv_file_item_size);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        FileInfo fileInfo = getItem(position);
        String fileName = fileInfo.getFile().getName();
        String fileSize = CommonUtil.getFileSize(fileInfo.getFile().length());
        String fileDate = CommonUtil.getStrTime(fileInfo.getFile().lastModified());
        boolean select = fileInfo.isSelect();
        Bitmap icon = getBitmapIcon(fileInfo,viewHolder.iv_file_item_appIcon);

        viewHolder.cb_file_item_del.setChecked(select);

        if (icon != null){
            viewHolder.iv_file_item_appIcon.setImageBitmap(icon);
        }
        viewHolder.tv_file_item_name.setText(fileName);
        viewHolder.tv_file_item_date.setText(fileDate);
        viewHolder.tv_file_item_size.setText(fileSize);

        viewHolder.cb_file_item_del.setTag(position);
        viewHolder.cb_file_item_del.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int position = (int)buttonView.getTag();
                getItem(position).setSelect(isChecked);
            }
        });
        return convertView;
    }

    private Bitmap getBitmapIcon(FileInfo fileInfo,ImageView imageView){
        Bitmap bitmap1 = null;
        String path = fileInfo.getFile().getPath();
        //如果缓存中有对应位图就直接用缓存中位图
        bitmap1 = lruCache.get(path);
        if (bitmap1 != null){
            return bitmap1;
        }
        //如果文件类型是图片的话直接用图片自身当图标
        if (fileInfo.getFileType().equals(FileTypeUtil.TYPE_IMAGE)){
            bitmap1 = BitmapUtill.loadBitmap(path,new BitmapUtill.SizeMessage(context,true,40,40));
            if (bitmap1 != null){
                lruCache.put(path,bitmap1);
                return bitmap1;
            }

        }

        //用文件自身指定的图片当图标
        Resources resources = context.getResources();
        int icon = resources.getIdentifier(fileInfo.getIconName(), "drawable", context.getPackageName());
        if (icon <= 0){
            icon = R.drawable.icon_file;
        }
        bitmap1 = BitmapFactory.decodeResource(resources, icon);
        return bitmap1;

    }
    class ViewHolder{
        CheckBox cb_file_item_del;
        ImageView iv_file_item_appIcon;
        TextView tv_file_item_name,tv_file_item_date,tv_file_item_size;
    }
}
