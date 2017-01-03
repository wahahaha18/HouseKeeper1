package com.zxzq.housekeeper.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 *  基础适配器,  根据集合内的数据，进行适配
 * Created by Administrator on 2016/10/28.
 */
//
public abstract class BaseDataAdapter<E> extends BaseAdapter {
    //数据源
    private List<E> adapterDatas = new ArrayList<E>();
    //上下文
    protected Context context;
//    在 BaseDataAdapter 类中定义 LayoutInflater 布局加载器并在构造方法中实例化，布局加载器,动态加载item
    protected LayoutInflater layoutInflater;


//在 BaseDataAdapter 类中传递参数 Context，并创建 Context,用于承接上下
    public BaseDataAdapter(Context context){
        this.context = context;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * 为 ArrayList<E> 添加 get()set()方法
     * 获取适配器中的数据源
     * @return
     */
    public List<E> getDataFromAdapter(){
        return adapterDatas;

    }

    /**
     * 为适配器中的数据源赋值
     * @param e
     */
    public void setDataAdapter(List<E> e){
        adapterDatas.clear();
        if (e != null){
            adapterDatas.addAll(e);
        }
    }

    /**
     * 添加数据到当前适配器集合
     * 添加数据至适配器集合
     * @param e
     */
    public void addDataToAdapter(E e){
        if (e != null){
            adapterDatas.add(e);
        }
    }

    public void addDataToAdapter(List<E> e){
        if (e != null){
            adapterDatas.addAll(e);
        }
    }


    /**
     * 清空适配器集合
     */
    public void clearAdapter(){
        adapterDatas.clear();
    }

    /**
     * 删除当前适配器集合内数据
     * 从适配器集合中移除元素
     * @param e
     */

    public void removeDataFromAdapter(E e){
        adapterDatas.remove(e);
    }

    public void removeDataFromAdapter(int index){
        adapterDatas.remove(index);
    }
    @Override
    public int getCount() {
        return adapterDatas.size();
    }

    @Override
    public E getItem(int position) {
        return adapterDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
