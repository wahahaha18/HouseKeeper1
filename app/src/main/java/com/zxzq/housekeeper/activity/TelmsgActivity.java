package com.zxzq.housekeeper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.zxzq.housekeeper.R;
import com.zxzq.housekeeper.adapter.TelclassAdapter;
import com.zxzq.housekeeper.base.BaseActivity;
import com.zxzq.housekeeper.biz.AssetsDBManager;
import com.zxzq.housekeeper.biz.DBReader;
import com.zxzq.housekeeper.entity.TelClassInfo;
import com.zxzq.housekeeper.util.ToastUtil;

import java.io.IOException;

/**
 * Created by Administrator on 2016/11/22.
 */

public class TelmsgActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    /**全局定义ListView控件*/
    protected ListView listView;
    private TelclassAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telmsg);
        String title = getResources().getString(R.string.telmgr);
        initActionBar(title,R.drawable.btn_homeasup_default,-1,this);
//        初始化ListView控件
        listView=(ListView)findViewById(R.id.listview);
//        实例化自定义适配器
        adapter = new TelclassAdapter(TelmsgActivity.this);
//        ListView控件执行适配器
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

    }

    //    初始化数据库文件
    public void initAppDBFile(){
//          检测是否存在通讯大全 DB 文件
        if (!DBReader.isExistsTeldbFile()){
            try {
                /*将本地项目中的 Assets/db/commonnum.db  文件复制写出到
                DBRead.telFile文件中*/
                AssetsDBManager.copyAssetsFileToFile(getApplicationContext(),"db/commonnum.db",DBReader.telFile);
            } catch (IOException e) {
                ToastUtil.show(this,"初始通讯大全数据库文件异常", Toast.LENGTH_SHORT);
            }
        }
    }

    //    初始化展示的数据
    @Override
    protected void onResume() {
        super.onResume();
//        适配数据
        initAppDBFile();
        adapter.clearDataTOAdapter();
        adapter.addDataToAdapter(new TelClassInfo("本地电话",0));
        adapter.addDataToAdapter(DBReader.readTeldbClassList());
//        通知应用，adapter数据源发生改变
        adapter.notifyDataSetChanged();
    }

    //    此处的position,是通过点击ListView 的item,然后系统自动给item监听事件传一个对应的position
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0){
//            本地通话
//            利用隐式意图，跳转到系统的拨号界面
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_DIAL);
            startActivity(intent);
//            用return是为了结束if语句，不用再往下执行，如果用if  else就得可以不加return
            return;
        }
//        position处对应的item视图所需要的数据
        TelClassInfo classInfo = adapter.getItem(position);
        Intent intent = new Intent(TelmsgActivity.this,TellistActivity.class);
//        根据idx不同，确定调转的界面，需要将idx传到下一个界面
        intent.putExtra("idx",classInfo.getIdx());
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        startActivity(HomeActivity.class);
    }
}
