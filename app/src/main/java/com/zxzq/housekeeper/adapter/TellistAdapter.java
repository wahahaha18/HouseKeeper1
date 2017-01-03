package com.zxzq.housekeeper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zxzq.housekeeper.R;
import com.zxzq.housekeeper.entity.TelNumberInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/22.
 */

public class TellistAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private ArrayList<TelNumberInfo> adapterDatas = new ArrayList<TelNumberInfo>();
    public TellistAdapter(Context context){
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        layoutInflater = LayoutInflater.from(context);
    }

    public void  addDataToAdapter(TelNumberInfo e){
        if (e != null){
            adapterDatas.add(e);
        }
    }
    public void  addDataToAdapter(List<TelNumberInfo> e){
        if (e != null){
            adapterDatas.addAll(e);
        }
    }
    public void replaceDataToAdapter(List<TelNumberInfo> e){
        if (e != null){
            adapterDatas.clear();
            adapterDatas.addAll(e);
        }
    }
    public ArrayList<TelNumberInfo> getDataFromAdapter(){
        return adapterDatas;
    }

    @Override
    public int getCount() {
        return adapterDatas.size();
    }

    @Override
    public TelNumberInfo getItem(int position) {
        return adapterDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TelNumberInfo telNumberInfo = adapterDatas.get(position);
        ViewHolder viewHolder=null;

        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.layout_tellist_listitem,null);
//            convertView = layoutInflater.inflate(R.layout.inflate_tellist_listitem,null);
            viewHolder = new ViewHolder();
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_number = (TextView) convertView.findViewById(R.id.tv_number);
            convertView.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_name.setText(telNumberInfo.getName());
        viewHolder.tv_number.setText(telNumberInfo.getNumber());
        return convertView;
    }

    class ViewHolder{
        TextView tv_name;
        TextView tv_number;

    }
}
