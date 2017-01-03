package com.zxzq.housekeeper.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxzq.housekeeper.R;
import com.zxzq.housekeeper.base.adapter.BaseBaseAdapter;
import com.zxzq.housekeeper.entity.PhoneInfo;

/**
 * Created by Administrator on 2016/11/15.
 */

public class PhonemgrAdapter extends BaseBaseAdapter <PhoneInfo> {
    public PhonemgrAdapter(Context context) {
        super(context);
    }

    @Override
    public View initGetView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.layout_phonemgr_listview_item,null);
            viewHolder = new ViewHolder();
            viewHolder.iv_phonemagr_listview_icon = (ImageView) convertView.findViewById(R.id.iv_phonemagr_listview_icon);
            viewHolder.tv_phonemagr_listview_title = (TextView) convertView.findViewById(R.id.tv_phonemagr_listview_title);
            viewHolder.tv_phonemagr_listview_text = (TextView) convertView.findViewById(R.id.tv_phonemagr_listview_text);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

//        给viewHolder赋值
        PhoneInfo phoneInfo = getItem(position);
        viewHolder.iv_phonemagr_listview_icon.setImageDrawable(phoneInfo.getIcon());
        viewHolder.tv_phonemagr_listview_title.setText(phoneInfo.getTitle());
        viewHolder.tv_phonemagr_listview_text.setText(phoneInfo.getText());

//        设置动态加载icon的背景色
        switch (position % 3){
            case 0 :
                viewHolder.iv_phonemagr_listview_icon.setBackgroundResource(R.drawable.notification_information_progress_green);
                break;
            case 1 :
                viewHolder.iv_phonemagr_listview_icon.setBackgroundResource(R.drawable.notification_information_progress_red);
                break;
            case 2 :
                viewHolder.iv_phonemagr_listview_icon.setBackgroundResource(R.drawable.notification_information_progress_yellow);
                break;
        }

        return convertView;
    }
    class ViewHolder{
        ImageView iv_phonemagr_listview_icon;
        TextView tv_phonemagr_listview_title,tv_phonemagr_listview_text;
    }
}
