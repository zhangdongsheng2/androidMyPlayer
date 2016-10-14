package com.example.myplayer.util;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;

import com.example.myplayer.MyApplication;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * Created by Administrator on 2016/3/29.
 */
public class ExceptionUtil {
    /**
     * 打印异常日志进内存卡 PlayerException 文件夹
     * @param throwable
     */
    public static void printThrowable(Throwable throwable) {
        File files = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator
                + "PlayerException" + File.separator);
        files.mkdirs();
        File file = new File(files.getAbsolutePath() + File.separator + "ExceptionLog.log");
        try {
            file.createNewFile();
        } catch (IOException var4) {
            var4.printStackTrace();
        }
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
        } catch (IOException e) {
            // Auto-generated catch block
            e.printStackTrace();
        }
        // 导出发生异常的时间
        pw.println(DateUtil.formatDate(new Date(), "yyyy-MM-dd-HH-mm-ss"));
        // 导出手机信息
        try {
            // 应用的版本名称和版本号
            PackageManager pm = MyApplication.getContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo( MyApplication.getContext().getPackageName(), PackageManager.GET_ACTIVITIES);
            pw.print("App Version: ");
            pw.print(pi.versionName);
            pw.print('_');
            pw.println(pi.versionCode);
            pw.println();
            // android版本号
            pw.print("OS Version: ");
            pw.print(Build.VERSION.RELEASE);
            pw.print("_");
            pw.println(Build.VERSION.SDK_INT);
            pw.println();
            // 手机制造商
            pw.print("Vendor: ");
            pw.println(Build.MANUFACTURER);
            pw.println();
            // 手机型号
            pw.print("Model: ");
            pw.println(Build.MODEL);
            pw.println();
            // cpu架构
            pw.print("CPU ABI: ");
            pw.println(Build.CPU_ABI);
            pw.println();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        pw.println();
        // 导出异常的调用栈信息
        throwable.printStackTrace(pw);
        pw.println();
        pw.close();
    }
}
