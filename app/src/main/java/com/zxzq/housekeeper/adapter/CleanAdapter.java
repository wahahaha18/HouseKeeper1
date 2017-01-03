package com.zxzq.housekeeper.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxzq.housekeeper.R;
import com.zxzq.housekeeper.base.adapter.BaseBaseAdapter;
import com.zxzq.housekeeper.entity.ClearInfo;
import com.zxzq.housekeeper.util.CommonUtil;

/**
 * Created by Administrator on 2016/11/21.
 */

public class CleanAdapter extends BaseBaseAdapter <ClearInfo>{
    public CleanAdapter(Context context) {
        super(context);
    }

    @Override
    public View initGetView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView ==null){
            convertView = layoutInflater.inflate(R.layout.layout_clean_showapp_listitem,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.cb_clean_item_del = (CheckBox) convertView.findViewById(R.id.cb_clean_item_del);
            viewHolder.iv_clean_item_appIcon = (ImageView) convertView.findViewById(R.id.iv_clean_item_appIcon);
            viewHolder.tv_clean_item_appName = (TextView) convertView.findViewById(R.id.tv_clean_item_appName);
            viewHolder.tv_clean_item_appVersion = (TextView) convertView.findViewById(R.id.tv_clean_item_appVersion);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
            ClearInfo clearInfo = getItem(position);
            String appName = clearInfo.getSoftChinesename();
            String appsize = CommonUtil.getFileSize(clearInfo.getSize());
            boolean isSelected = clearInfo.isClear();
            Drawable icon = clearInfo.getApkIcon();
            viewHolder.cb_clean_item_del.setChecked(isSelected);
            viewHolder.tv_clean_item_appName.setText(appName);
            viewHolder.tv_clean_item_appVersion.setText(appsize);
            viewHolder.iv_clean_item_appIcon.setImageDrawable(icon);
            viewHolder.cb_clean_item_del.setTag(position);
            viewHolder.cb_clean_item_del.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int position = (int)buttonView.getTag();
                    getItem(position).setClear(isChecked);
                }
            });

        return convertView;
    }

    class ViewHolder{
        CheckBox cb_clean_item_del;
        ImageView iv_clean_item_appIcon;
        TextView tv_clean_item_appName,tv_clean_item_appVersion;

    }
}
