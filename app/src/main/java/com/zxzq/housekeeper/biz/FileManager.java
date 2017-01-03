package com.zxzq.housekeeper.biz;

import com.zxzq.housekeeper.entity.FileInfo;
import com.zxzq.housekeeper.util.FileTypeUtil;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/16.
 */

public class FileManager {
    //创建搜索过程监听
    private SearchFileListener listener;
    //是否停止搜索
    private boolean isStopSearch;
    //内置SD卡目录
    private static File inSdcardDir = null;
    //外置SD卡目录
    private static File outSdcardDir = null;
    //全部的集合
    private ArrayList<FileInfo> allArray = new ArrayList<FileInfo>();
    //全部文件的大小
    private long allArraySize;
    //文件的集合
    private ArrayList<FileInfo> fileArray = new ArrayList<FileInfo>();
    //文件的大小
    private long fileArraySize;
    //视频的集合
    private ArrayList<FileInfo> videoArray = new ArrayList<FileInfo>();
    //视频的大小
    private long videoArraySize;
    //音频的集合
    private ArrayList<FileInfo> musicArray = new ArrayList<FileInfo>();
    //音频的大小
    private long musicArraySize;
    //图像的集合
    private ArrayList<FileInfo> pictureArray = new ArrayList<FileInfo>();
    //图像的大小
    private long pictureArraySize;
    //压缩包的集合
    private ArrayList<FileInfo> zipArray = new ArrayList<FileInfo>();
    //压缩包件的大小
    private long zipArraySize;
    //程序包的集合
    private ArrayList<FileInfo> apkArray = new ArrayList<FileInfo>();
    //程序包的大小
    private long apkArraySize;

    public static FileManager fileManager=new FileManager();

    private FileManager() {
    }

    public static FileManager getFileManager(){
        return fileManager;
    }

    /**
     * 初始化内置SD卡和外置SD卡目录
     */
    static {
        if (MemoryManager.getPhoneInSDCardPath() != null){
            inSdcardDir = null;
            inSdcardDir = new File(MemoryManager.getPhoneInSDCardPath());
        }
        if (MemoryManager.getPhoneOutSDCardPath() != null){
            outSdcardDir = null;
            outSdcardDir = new File(MemoryManager.getPhoneOutSDCardPath());
        }
    }
    /**
     * 初始化各类文件的大小
     * @return
     */
    public void initSize(){
        isStopSearch = false;

        allArraySize = 0;
        fileArraySize = 0;
        videoArraySize = 0;
        musicArraySize = 0;
        zipArraySize = 0;
        apkArraySize = 0;

        allArray.clear();
        fileArray.clear();
        videoArray.clear();
        musicArray.clear();
        zipArray.clear();
        apkArray.clear();
    }



    /**
     * 搜索内置和外置SD卡的文件
     */
    public void searchSDFile(){
        if (allArray == null || allArray.size() <=0 ){
            //没有搜索，开始搜索
            searchFile(inSdcardDir,false);
            searchFile(outSdcardDir,true);
        }else {
            //搜索完毕，正常结束
            callBackSearchFileListenerEnd(false);
        }
    }

    /**
     * 用递归方法搜索文件并进行文件信息缓存
     * @return
     */
    private void searchFile(File file){
        //排除不正常文件
        if (file == null || !file.canRead() || !file.exists()){
            return;
      }
        //是文件，但非目录
        if (!file.isDirectory()){
            //判断文件大小
            if (file.length() <= 0){
                return;
            }
            //如果文件中没有“.”,则是未知文件类型
            if (file.getName().lastIndexOf(".") == -1){
                return;
            }
            //是正常文件，并将文件加入全部文件集合中
            allArray.add(new FileInfo(file,null,file.getName()));
            //迭加计算总文件大小
            allArraySize += file.length();
            //  回调接口 searching  方法(用作通知调用者数据更新了)
            return;
        }

        //是目录
        File[] files = file.listFiles();
        if (files == null || files.length <=0){
            return;
        }
        for (int i = 0;i < files.length;i++){
            File tmpFile = files[i];
            searchFile(tmpFile);
        }
    }

    /**
     * 创建接口，搜索过程的实时监听，用做搜索过程中实时反馈文件信息
     */
    public interface SearchFileListener{
        void searching(String typeName);
        void end(boolean isExceptionEnd);
    }

    /**
     * 设置获取listener，文件查找监听
     * @return
     */
    public void setSearchFileListener(SearchFileListener listener){
        this.listener = listener;
    }

    /**
     * 用来回调SearchFileListener接口中的searching()方法
     * @param typeName
     */
    public void callBackSearchFileListenerSearching(String typeName){
        if (listener != null){
            listener.searching(typeName);
        }
    }

   /* *//**
     * 用来回调SearchFileListener接口中的searching()方法
     * @param size
     *//*
    public void callBackSearchFileListenerSearching(long size){
        if (listener != null){
            listener.searching(size);
        }
    }*/

    /**
     * 用来回调SearchFileListener接口中的end()方法
     * @param isExceptionEnd
     */
    public void callBackSearchFileListenerEnd(boolean isExceptionEnd){
        if (listener != null){
            listener.end(isExceptionEnd);
        }
    }


    /**
     * 搜索文件
     * @param file
     * @param isFirstRun
     */
    //isFirstRun递归调用已经到最底部了，递归调用已经结束了
    private void searchFile(File file,boolean isFirstRun){
        //isStopSearch情况,//第一次搜索就结束，直接返回异常结束
        if (isStopSearch){
            callBackSearchFileListenerEnd(true);
            return;
        }
        //排除不正常文件
        if (file == null || !file.canRead()|| !file.exists()){
            if (isFirstRun){
                callBackSearchFileListenerEnd(true);
            }
            return;
        }
        //是文件，但非目录
        if (!file.isDirectory()){
            if (file.length() <= 0){
                return;
            }
            //文件名中木有搜索到"."，即文件类型未知
            if (file.getName().lastIndexOf(".") == -1){
                return;
            }
            //获取文件图标图片名、文件类型、文件大小
            String[] fileIconAndTypeName = FileTypeUtil.getFileIconAndTypeName(file);
            String iconName = fileIconAndTypeName[0];
            String typeName = fileIconAndTypeName[1];
            FileInfo fileInfo = new FileInfo(file, iconName, typeName);
            allArray.add(fileInfo);
            allArraySize += file.length();
            //判断该文件属于哪个类型，将其添加入对应类型的集合中，且计算文件大小
            if (file.getName().equals(FileTypeUtil.TYPE_TXT)){
                fileArray.add(fileInfo);
                fileArraySize += file.length();
            }else if (file.getName().equals(FileTypeUtil.TYPE_VIDEO)){
                videoArray.add(fileInfo);
                videoArraySize += file.length();
            }else if (file.getName().equals(FileTypeUtil.TYPE_AUDIO)){
                musicArray.add(fileInfo);
                musicArraySize += file.length();
            }else if (file.getName().equals(FileTypeUtil.TYPE_IMAGE)){
                pictureArray.add(fileInfo);
                pictureArraySize += file.length();
            }else if (file.getName().equals(FileTypeUtil.TYPE_ZIP)){
                zipArray.add(fileInfo);
                zipArraySize += file.length();
            }else if (file.getName().equals(FileTypeUtil.TYPE_APK)){
                apkArray.add(fileInfo);
                apkArraySize += file.length();
            }
            callBackSearchFileListenerSearching(typeName);
            return;
        }

        //file是目录,若是,文件夹(目录)，则获取其全部子目录
        File[] files = file.listFiles();
        if (files == null || files.length <= 0){
            return;
        }

        for (File tmpFile : files){
            //依次搜索所有的子文件夹且设置不是第一次运行，即递归未结束
            searchFile(tmpFile,true);
        }
        //首次运行的结束(正常结束)
        if (isFirstRun){
            callBackSearchFileListenerEnd(false);
        }
    }

    /**
     * 获取文件总大小
     * @param file
     * @return
     */
    public static long getFileSize(File file){
        long size = 0;
        //是文件，但非目录,直接返回大小
        if (!file.isDirectory()){
           if (file.getName().lastIndexOf(".") == -1){
               return size;
           }
            return file.length();
        }
        //file是目录,//是目录,获取其子文件，再逐一进行判断
        File[] files = file.listFiles();
        if (files == null || files.length <= 0){
            //空目录
            return size;
        }
        for (File tmpFile : files){
            if (tmpFile.isDirectory()){
                //子文件 也是 一个文件夹，再递归求其大小
                size = size + getFileSize(tmpFile);
            }else {
                //子文件是 一个文件
                size = size + tmpFile.length();
            }
        }
        return size;
    }

    /**
     * 删除文件,如果是文件目录，就循环遍历将其中的子文件全部删除，之后再将本文件删除，
     * 如果传入的文件只是文件，那就直接删除
     * @return
     */
    public static void deleteFile(File file){
        if (file.isDirectory()){
            File[] files = file.listFiles();
            if (files != null && files.length >0){
                //非空文件夹
                for (File tmpFile : files){
                    deleteFile(tmpFile);
                }
            }
        }
        file.delete();
    }



    public ArrayList<FileInfo> getAllArray() {
        return allArray;
    }

    public void setAllArray(ArrayList<FileInfo> allArray) {
        this.allArray = allArray;
    }

    public long getAllArraySize() {
        return allArraySize;
    }

    public void setAllArraySize(long allArraySize) {
        this.allArraySize = allArraySize;
    }

    public ArrayList<FileInfo> getFileArray() {
        return fileArray;
    }

    public void setFileArray(ArrayList<FileInfo> fileArray) {
        this.fileArray = fileArray;
    }

    public long getFileArraySize() {
        return fileArraySize;
    }

    public void setFileArraySize(long fileArraySize) {
        this.fileArraySize = fileArraySize;
    }

    public ArrayList<FileInfo> getVideoArray() {
        return videoArray;
    }

    public void setVideoArray(ArrayList<FileInfo> videoArray) {
        this.videoArray = videoArray;
    }

    public long getVideoArraySize() {
        return videoArraySize;
    }

    public void setVideoArraySize(long videoArraySize) {
        this.videoArraySize = videoArraySize;
    }

    public ArrayList<FileInfo> getMusicArray() {
        return musicArray;
    }

    public void setMusicArray(ArrayList<FileInfo> musicArray) {
        this.musicArray = musicArray;
    }

    public long getMusicArraySize() {
        return musicArraySize;
    }

    public void setMusicArraySize(long musicArraySize) {
        this.musicArraySize = musicArraySize;
    }

    public ArrayList<FileInfo> getPictureArray() {
        return pictureArray;
    }

    public void setPictureArray(ArrayList<FileInfo> pictureArray) {
        this.pictureArray = pictureArray;
    }

    public long getPictureArraySize() {
        return pictureArraySize;
    }

    public void setPictureArraySize(long pictureArraySize) {
        this.pictureArraySize = pictureArraySize;
    }

    public ArrayList<FileInfo> getZipArray() {
        return zipArray;
    }

    public void setZipArray(ArrayList<FileInfo> zipArray) {
        this.zipArray = zipArray;
    }

    public long getZipArraySize() {
        return zipArraySize;
    }

    public void setZipArraySize(long zipArraySize) {
        this.zipArraySize = zipArraySize;
    }

    public ArrayList<FileInfo> getApkArray() {
        return apkArray;
    }

    public void setApkArray(ArrayList<FileInfo> apkArray) {
        this.apkArray = apkArray;
    }

    public long getApkArraySize() {
        return apkArraySize;
    }

    public void setApkArraySize(long apkArraySize) {
        this.apkArraySize = apkArraySize;
    }
    public boolean isStopSearch() {
        return isStopSearch;
    }

    public void setStopSearch(boolean stopSearch) {
        isStopSearch = stopSearch;
    }

}
