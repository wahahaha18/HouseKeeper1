package com.zxzq.housekeeper.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zxzq.housekeeper.R;
import com.zxzq.housekeeper.adapter.TellistAdapter;
import com.zxzq.housekeeper.base.BaseActivity;
import com.zxzq.housekeeper.biz.DBReader;

/**
 * Created by Administrator on 2016/11/22.
 */

public class TellistActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    private ListView tel_table;
    private TellistAdapter tellistAdapter;
    private int idx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tellist);

        String title = getResources().getString(R.string.telmgr);
        initActionBar(title,R.drawable.btn_homeasup_default,-1,this);
        idx = getIntent().getIntExtra("idx",-1);
        tel_table = (ListView) findViewById(R.id.tel_table);
        tel_table.setOnItemClickListener(this);
        tellistAdapter = new TellistAdapter(TellistActivity.this);
        tel_table.setAdapter(tellistAdapter);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String name = tellistAdapter.getItem(position).getName();
        String number = tellistAdapter.getItem(position).getNumber();
        showCallDialog(name,number);

    }

    @Override
    protected void onResume() {
        super.onResume();
        tellistAdapter.addDataToAdapter(DBReader.readerTeldbTable(idx));
    }

    private void showCallDialog(final String name,final String number){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("警告");
        builder.setMessage("是否开始拨打"+name+"电话？\nTEL:"+number);
        builder.setNegativeButton("取消",null);
        builder.setPositiveButton("拨号", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel://"+number));
                startActivity(intent);
            }
        });
        builder.show();
    }

    @Override
    public void onClick(View v) {
        startActivity(TelmsgActivity.class);
    }
}
