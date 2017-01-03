package com.zxzq.housekeeper.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/7.
 */

public abstract class BaseBaseAdapter<DataType> extends BaseAdapter {
    //数据源
    protected ArrayList<DataType> datas = new ArrayList<DataType>();
    //布局加载器
    protected LayoutInflater layoutInflater;
    //上下文
    protected Context context;
    public BaseBaseAdapter(Context context){
        this.context = context;
        layoutInflater = getLayoutInflater();
    }

    public LayoutInflater getLayoutInflater(){
        layoutInflater = LayoutInflater.from(context);
        return layoutInflater;
    }
    //数据源中添加数据
    public ArrayList<DataType> addDataToAadapter(DataType e){
        if (e != null){
            datas.add(e);
        }

        return datas;
    }

    public ArrayList<DataType> addDataToAadapter(List<DataType> e){
        if (e != null){
            datas.addAll(e);
        }

        return datas;
    }

    //给数据源赋值
    public void setDataToAdapter(List<DataType> e){
        if (e != null){
            datas.clear();
            datas.addAll(e);
        }

    }

    //获取数据源
    public ArrayList<DataType> getDataFromAdapter(){
        return datas;
    }

    //删除数据
    public void removeDataFromAdapter(DataType e){
        if (datas.contains(e)){
            datas.remove(e);
        }
    }

    public void removeDataFromAdapter(int index){
        if (datas.contains(datas.get(index))){
            datas.remove(index);
        }
    }
    @Override
    public int getCount() {
        return datas.size();
    }


    @Override
    public DataType getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return initGetView(position,convertView,parent);
    }

    public abstract View initGetView(int position, View convertView, ViewGroup parent);
}
