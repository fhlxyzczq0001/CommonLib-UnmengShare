package com.app.common.Public;

import android.os.Environment;

/**
 * SD卡文件存储路径
 * @ClassName: SD_FilePath 
 * @Description: TODO
 * @author: yzc
 * @date: 2016-4-24 上午10:40:50
 */
public class SD_FilePath {
    public static String sdcardDir = Environment.getExternalStorageDirectory()
            .getPath();// SD卡根目录绝对路径
    public static String appSdcardDir = Environment.getExternalStorageDirectory()
            .getPath()+"/com.lottery";// 应用SD卡根目录绝对路径
    public static String welcomePagePath = sdcardDir+"/com.lottery/welcomePagePath";// 启动页保存路径
    public static String apkPath = sdcardDir+"/com.lottery/apkPath";// apk下载保存路径
}
