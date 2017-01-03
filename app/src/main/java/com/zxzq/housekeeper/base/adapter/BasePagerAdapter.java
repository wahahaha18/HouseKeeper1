package com.zxzq.housekeeper.base.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/31.
 */

public class BasePagerAdapter extends PagerAdapter {
    //上下文
    private Context context;

    //ViewPager中添加的View视图 的 数据源
    private ArrayList<View> viewList = new ArrayList<View>();

    //ViewPager中存放页签项标题  的  集合
    private ArrayList<String> tabTitleList = new ArrayList<String>();
    public BasePagerAdapter(Context context){

    }

    /**
     * 获取适配器中存放View视图的数据源
     * @return
     */
    public ArrayList<View> getViewList(){
        return viewList;
    }

    /**
     * 添加 View至数据源
     * @param view
     */
    public void addViewToAdapter(View view){
        if (view != null){
            viewList.add(view);
        }
    }

    /**
     * 将标题添加到 存放 页签项标题的集合中
     * @param title
     */
    public void addTabTitleToAdapter(String title){
        tabTitleList.add(title);
    }


    /**
     * 获取 position处tab项的标题
     * @param position
     * @return
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitleList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //根据position获得显示的View视图
        View view = viewList.get(position);
        //从ViewPager中移除View视图
        container.removeView(view);
    }

    //类似ListView 的 Adapter 中的 getItem 方法
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //根据position获得要显示的View视图
        View view = viewList.get(position);
        //将该视图添加至ViewPager中
        container.addView(view);
        //返回该视图
        return view;
    }


    //当前窗体界面数
    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
