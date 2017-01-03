package com.zxzq.housekeeper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zxzq.housekeeper.R;
import com.zxzq.housekeeper.entity.TelClassInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 通讯大全电话分类适配器
 * Created by Administrator on 2016/11/22.
 */

public class TelclassAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    //    当前适配器内的数据集合 ( 当前适配器适配工作只认此集合)
    private ArrayList<TelClassInfo> adapterDatas = new ArrayList<TelClassInfo>();

    //    添加数据到当前适配器集合
    public void  addDataToAdapter(TelClassInfo e){
        if (e != null){
            adapterDatas.add(e);
        }
    }

    //添加数据到当前适配器集合
    public void addDataToAdapter(List<TelClassInfo> e){
        if (e != null){
            adapterDatas.addAll(e);
        }
    }
    //从适配器中获取数据
    public ArrayList<TelClassInfo> getDataFromAdapter(){

        return adapterDatas;
    }

    //删除当前适配器集合内数据
    public void clearDataTOAdapter(){
        adapterDatas.clear();
    }

    //添加构造方法
    public TelclassAdapter(Context context){
        layoutInflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return adapterDatas.size();
    }

    /*返回的是一个TelClassInfo类的对象，它的对象里的属性都是和item里的需要的控件都是一致的，
    这个对象本身具有item所需的数据所以从适配器的数据集中获取的item，
    就相当于获得了TelClassInfo类的对象，*/
    @Override
    public TelClassInfo getItem(int position) {
        return adapterDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TelClassInfo telClassInfo = adapterDatas.get(position);
        ViewHolder viewHolder = null;
//
        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.layout_telmgr_listitem,null);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) convertView.findViewById(R.id.textview);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(telClassInfo.getName());
        return convertView;
    }
    class ViewHolder{
        TextView textView;
    }

}
