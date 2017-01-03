package com.zxzq.housekeeper.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zxzq.housekeeper.R;
import com.zxzq.housekeeper.adapter.FileShowAdapter;
import com.zxzq.housekeeper.base.BaseActivity;
import com.zxzq.housekeeper.biz.FileManager;
import com.zxzq.housekeeper.entity.FileInfo;
import com.zxzq.housekeeper.util.CommonUtil;
import com.zxzq.housekeeper.util.FileTypeUtil;
import com.zxzq.housekeeper.util.LogUtil;

import java.io.File;
import java.util.ArrayList;

public class FileShowActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    TextView tv_file_text_num,tv_file_num,tv_file_text_size,tv_file_size;
    ListView lv_file_show;
    ProgressBar layout_file_show_pb;
    CheckBox cb_file_show;
    Button b_file_show;
    String title = "";
    ArrayList<FileInfo> fileInfos = new ArrayList<FileInfo>();
    long filesize;
    int fileNum;
    FileManager manager= FileManager.getFileManager();
    int idName;
    FileShowAdapter fileShowAdapter;
    Thread thread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_show);

        //初始跳转过来之后显示的对应类别的文件
        Intent intent = getIntent();
        idName = intent.getIntExtra("idName",R.id.file_rl_all);
        LogUtil.e("FILE:","idName:"+idName);
        initSort();
        initActionBar(title,R.drawable.btn_homeasup_default,-1,this);
        initView();
        initMainUiData();

    }

    /*private void asynData() {
        cb_file_show.setVisibility(View.VISIBLE);
        lv_file_show.setVisibility(View.INVISIBLE);
        thread = new Thread(){
            @Override
            public void run() {
                super.run();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        cb_file_show.setVisibility(View.INVISIBLE);
                        lv_file_show.setVisibility(View.VISIBLE);

                    }
                });
            }
        };
        thread.start();

    }*/


    private void initMainUiData() {
        LogUtil.e("FILE:","fileNum:"+fileNum);
        LogUtil.e("FILE:","tv_file_num:"+tv_file_num);
        tv_file_num.setText(fileNum+"");
        tv_file_size.setText(CommonUtil.getFileSize(filesize));
        lv_file_show.setOnItemClickListener(FileShowActivity.this);
        fileShowAdapter.setDataToAdapter(fileInfos);
        fileShowAdapter.notifyDataSetChanged();

    }

    private void initView() {
        tv_file_text_num = (TextView) findViewById(R.id.tv_file_text_num);
        tv_file_num = (TextView) findViewById(R.id.tv_file_num);
        LogUtil.e("FILE:","tv_file_num:"+R.id.tv_file_num);
        tv_file_text_size = (TextView) findViewById(R.id.tv_file_text_size);
        tv_file_size = (TextView) findViewById(R.id.tv_file_size);

        lv_file_show = (ListView) findViewById(R.id.lv_file_show);
        fileShowAdapter = new FileShowAdapter(this);
        lv_file_show.setAdapter(fileShowAdapter);

//        layout_file_show_pb = (ProgressBar) findViewById(R.id.layout_file_show_pb);

        cb_file_show = (CheckBox) findViewById(R.id.cb_file_show);
        cb_file_show.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ArrayList<FileInfo> fileInfos = fileShowAdapter.getDataFromAdapter();
                for (FileInfo fileInfo : fileInfos){
                    fileInfo.setSelect(isChecked);
                }
                fileShowAdapter.notifyDataSetChanged();
            }
        });

        b_file_show = (Button) findViewById(R.id.b_file_show);
        //注册监听事件
        b_file_show.setOnClickListener(this);

    }

    private void initSort() {


        switch (idName){
            case R.id.file_rl_all://全部
                title = "全部";
                fileInfos = manager.getAllArray();
                filesize = manager.getAllArraySize();
                LogUtil.e("FILE:","manager.getAllArray():"+manager.getAllArray().size());
                break;
            case R.id.file_rl_file://文件
                title = "文件";
                fileInfos=manager.getFileArray();
                filesize = manager.getFileArraySize();
                break;
            case R.id.file_rl_vedio://视频
                title = "视频";
                fileInfos=manager.getVideoArray();
                filesize = manager.getVideoArraySize();
                break;
            case R.id.file_rl_music://音频
                title = "音频";
                fileInfos=manager.getMusicArray();
                filesize = manager.getMusicArraySize();
                break;
            case R.id.file_rl_picture://图像
                title = "图像";
                fileInfos=manager.getPictureArray();
                filesize = manager.getPictureArraySize();
                break;
            case R.id.file_rl_zip://压缩包
                title = "压缩包";
                fileInfos=manager.getZipArray();
                filesize = manager.getZipArraySize();
                break;
            case R.id.file_rl_apk://程序包
                title = "程序包";
                fileInfos=manager.getApkArray();
                filesize = manager.getApkArraySize();

                break;
        }
        fileNum = fileInfos.size();
        LogUtil.e("FILE:","fileInfos.size():"+fileInfos.size());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_left:
                startActivity(FilemgrActivity.class);
                break;
            case R.id.b_file_show:
                deleteAllFile();
                break;
        }
    }

    private void deleteAllFile() {
        ArrayList<FileInfo> deleteList = new ArrayList<FileInfo>();
        ArrayList<FileInfo> dataFromAdapter = fileShowAdapter.getDataFromAdapter();
        for (FileInfo info : dataFromAdapter){
            if (info.isSelect()){
                deleteList.add(info);
            }
        }
        for (FileInfo fileInfo : deleteList){
            File file = fileInfo.getFile();
            long size = file.length();

            if (file.delete()){
                fileShowAdapter.getDataFromAdapter().remove(fileInfo);
                manager.getAllArray().remove(fileInfo);
                manager.setAllArraySize(manager.getAllArraySize() - size);
                filesize = manager.getAllArraySize();
                switch (idName){
                    case R.id.file_rl_file://文件
                        manager.getFileArray().remove(fileInfo);
                        manager.setFileArraySize(manager.getFileArraySize() - size);
                        filesize = manager.getFileArraySize();
                        break;
                    case R.id.file_rl_vedio://视频
                        manager.getVideoArray().remove(fileInfo);
                        manager.setVideoArraySize(manager.getVideoArraySize() - size);
                        filesize = manager.getVideoArraySize();
                        break;
                    case R.id.file_rl_music://音频
                        manager.getMusicArray().remove(fileInfo);
                        manager.setMusicArraySize(manager.getMusicArraySize() - size);
                        filesize = manager.getMusicArraySize();
                        break;
                    case R.id.file_rl_picture://图像
                        manager.getPictureArray().remove(fileInfo);
                        manager.setPictureArraySize(manager.getPictureArraySize() - size);
                        filesize = manager.getPictureArraySize();
                        break;
                    case R.id.file_rl_zip://压缩包
                        manager.getZipArray().remove(fileInfo);
                        manager.setZipArraySize(manager.getZipArraySize() - size);
                        filesize = manager.getZipArraySize();
                        break;
                    case R.id.file_rl_apk://程序包
                        manager.getApkArray().remove(fileInfo);
                        manager.setApkArraySize(manager.getApkArraySize() - size);
                        filesize = manager.getApkArraySize();
                        break;
                }

                fileShowAdapter.notifyDataSetChanged();
                tv_file_num.setText(fileShowAdapter.getDataFromAdapter().size()+"个");
                tv_file_size.setText(CommonUtil.getFileSize(filesize));

                System.gc();
            }


        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        FileInfo fileInfo = fileShowAdapter.getItem(position);
        File file = fileInfo.getFile();
        String mimeType = FileTypeUtil.getMIMEType(file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),mimeType);
        startActivity(intent);
    }
}
