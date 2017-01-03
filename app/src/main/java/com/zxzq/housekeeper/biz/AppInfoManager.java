package com.zxzq.housekeeper.biz;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Debug;

import com.zxzq.housekeeper.entity.AppInfo;
import com.zxzq.housekeeper.entity.RunningAppInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**应用信息
 * 获取
 * Created by Administrator on 2016/11/8.
 */

public class AppInfoManager {
    private Context context;
    private PackageManager packageManager;
    private ActivityManager activityManager;
    //存放所有软件的集合
    private List<AppInfo> allPackagerInfo = new ArrayList<AppInfo>();
    //存放系统软件的集合
    private List<AppInfo> sysPackagerInfo = new ArrayList<AppInfo>();
    //存放用户软件的集合
    private List<AppInfo> userPackagerInfo = new ArrayList<AppInfo>();
    //获取唯一对象appInfoManager
    private static AppInfoManager appInfoManager = null;

//    类中定义了两个常量区分，系统的进程和用户的进程，用一个 hashmap 保存运行进程
    public static final int RUNNING_APP_TYPE_SYS = 0;
    public static final int RUNNING_APP_TYPE_USER = 1;

    private AppInfoManager(Context context){
        this.context = context;
        packageManager = context.getPackageManager();
        activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    }
    public static AppInfoManager getAppInfoManager(Context context){
        if (appInfoManager == null){
            synchronized (context){
                if (appInfoManager == null){
                    appInfoManager = new AppInfoManager(context);
                }
            }
        }
        return appInfoManager;
    }

//    获取正在运行应用
    public Map<Integer,List<RunningAppInfo>> getRunningAppInfos(){
         Map<Integer,List<RunningAppInfo>> integerListMap = new HashMap<Integer,List<RunningAppInfo>>();

        List<RunningAppInfo> sysapp = new ArrayList<RunningAppInfo>();
        List<RunningAppInfo> userapp = new ArrayList<RunningAppInfo>();
        List<ActivityManager.RunningAppProcessInfo> appProcessInfos = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcessInfo :appProcessInfos){
            //  正在运行程序进程名
            String packageName = appProcessInfo.processName;
            //  正在运行程序进程 ID，端口号
            int pid = appProcessInfo.pid;
            //  正在运行程序进程级别
            int importance = appProcessInfo.importance;

            //  服务进程（包括）级别,服务进程以下的进程（一般清理只会清理服务进程以下的进程，所以没必要全取出来
            // 所以才会用这种if判断条件）
            if (importance >= ActivityManager.RunningAppProcessInfo.IMPORTANCE_SERVICE){
                Drawable icon;
                String lableName;
                long size;
                Debug.MemoryInfo[] memoryInfos = activityManager.getProcessMemoryInfo(new int[]{pid});
                size = (memoryInfos[0].getTotalPrivateDirty())*1024;
                try {
                    icon = packageManager.getApplicationIcon(packageName);
                    ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName,
                            PackageManager.GET_META_DATA | PackageManager.GET_SHARED_LIBRARY_FILES |
                    PackageManager.GET_UNINSTALLED_PACKAGES);

                    lableName = packageManager.getApplicationLabel(applicationInfo).toString();

                    RunningAppInfo runningAppInfo = new RunningAppInfo(packageName,icon,size,lableName);

                    if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0){
                        runningAppInfo.setSystem(true);
                        runningAppInfo.setClear(false);
                        sysapp.add(runningAppInfo);
                    }else {
                        runningAppInfo.setSystem(false);
                        runningAppInfo.setClear(true);
                        userapp.add(runningAppInfo);
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        integerListMap.put(RUNNING_APP_TYPE_SYS,sysapp);
        integerListMap.put(RUNNING_APP_TYPE_USER,userapp);

        return integerListMap;
    }


//    清理所有正在运行的程序( 级别为服务进程以上的非系统进程)
    public void killAllProcesses(){
        List<ActivityManager.RunningAppProcessInfo> appProcessInfos = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcessInfo : appProcessInfos){
            if (appProcessInfo.importance >= ActivityManager.RunningAppProcessInfo.IMPORTANCE_SERVICE){
                String packageName = appProcessInfo.processName;
                try {
                    ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName,packageManager.GET_META_DATA |
                            PackageManager.GET_SHARED_LIBRARY_FILES |
                            PackageManager.GET_UNINSTALLED_PACKAGES);
                    if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0){

                    }else {
                        activityManager.killBackgroundProcesses(packageName);
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
//    清理指定程序

    public void killProcesses(String packageName){
        activityManager.killBackgroundProcesses(packageName);
    }




    //加载所有的Activity应用程序包
    public void loadAllActivityPackage(){
        List<PackageInfo> Infos = packageManager.getInstalledPackages(PackageManager.GET_ACTIVITIES);
        allPackagerInfo.clear();
        for (PackageInfo packageInfo : Infos){
            allPackagerInfo.add(new AppInfo(packageInfo));
        }
    }

    /**
     * 返回所有应用程序列表
     * @param isReset  是否刷新   若刷新，则重新加载数据；否则，直接返回原有集合中的数据
     * @return
     */
    public List<AppInfo> getAllPackagerInfo(boolean isReset){
        if (isReset){
            loadAllActivityPackage();
        }
        return allPackagerInfo;
    }

    /**
     * 返回系统应用程序列表
     * @param isReset
     * @return
     */
    public List<AppInfo> getSysPackagerInfo(boolean isReset){
        if (isReset){
            //重新加载数据 并 清空 原有集合中的数据
            loadAllActivityPackage();
            sysPackagerInfo.clear();
            for (AppInfo appInfo : allPackagerInfo){
//                两者做了与运算
                if ((appInfo.getPackageInfo().applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0){
                    //是系统应用程序，将其添加到 存放系统应用的集合中
                    sysPackagerInfo.add(appInfo);

                }else{
                    //用户软件，什么也不做
                }
            }
        }
        return sysPackagerInfo;
    }

    /**
     * 返回用户应用程序列表
     * @param isReset
     * @return
     */
    public List<AppInfo> getUserPackagerInfo(boolean isReset){
        if (isReset){
            //重新加载数据 并 清空 原有集合中的数据
            loadAllActivityPackage();
            userPackagerInfo.clear();
            for (AppInfo appInfo : allPackagerInfo){
                if ((appInfo.getPackageInfo().applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0){
                    //是系统应用程序，什么也不做
                }else{
                    //用户软件，将其添加到 存放用户应用的集合中
                    userPackagerInfo.add(appInfo);
                }
            }
        }
        return userPackagerInfo;
    }
}
