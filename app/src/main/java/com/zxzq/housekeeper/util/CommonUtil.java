package com.zxzq.housekeeper.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Justin on 2016/5/18.
 */
public class CommonUtil {

    /**
     * 将毫秒转为字符串方式的时间格式 (yyyy-MM-dd hh:mm:ss)
     *
     SimpleDateFormat函数语法：

     G 年代标志符
     y 年
     M 月
     d 日
     h 时 在上午或下午 (1~12)
     H 时 在一天中 (0~23)
     m 分
     s 秒
     S 毫秒
     E 星期
     D 一年中的第几天
     F 一月中第几个星期几
     w 一年中第几个星期
     W 一月中第几个星期
     a 上午 / 下午 标记符
     k 时 在一天中 (1~24)
     K 时 在上午或下午 (0~11)
     z 时区
     *
     * @param filetime
     * @return
     */
    public static String getStrTime(long filetime){
        if(filetime==0){
            return "未知格式";
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String strTime = format.format(filetime);
        return strTime;
    }

    //获取当前的系统时间，格式为：yyyy-MM-dd hh:mm:ss
    public static String getStrTime(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String strTime = format.format(new Date(System.currentTimeMillis()));
        return strTime;
    }


    public static boolean equals(Object a, Object b) {
        return (a == b) || (a == null ? false : a.equals(b));
    }

    /**
     * 符号含义：

     0 一个数字
     # 一个数字，不包括 0
     . 小数的分隔符的占位符
     , 分组分隔符的占位符
     ; 分隔格式。
     - 缺省负数前缀。
     % 乘以 100 和作为百分比显示
     ? 乘以 1000 和作为千进制货币符显示；用货币符号代替；如果双写，用
     国际货币符号代替。如果出现在一个模式中，用货币十进制分隔符代替十进制分隔符。
     X 前缀或后缀中使用的任何其它字符，用来引用前缀或后缀中的特殊字符。
     */
    private static DecimalFormat df = new DecimalFormat("#.00");

    public static String getFileSize(long filesize){
        StringBuffer mstrbuf = new StringBuffer();
        if(filesize<1024){
            mstrbuf.append(filesize);
            mstrbuf.append(" B");
        }else if(filesize<1048576){
            mstrbuf.append(df.format((double)filesize/1024));
            mstrbuf.append(" K");
        }else if(filesize<1073741824){
            mstrbuf.append(df.format((double)filesize/1048576));
            mstrbuf.append(" M");
        }else{
            mstrbuf.append(df.format((double)filesize/1073741824));
            mstrbuf.append(" G");
        }
        return mstrbuf.toString();
    }
}
