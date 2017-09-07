package com.app.lotteryticket.Util;


import com.app.common.Public.SharedPreferences_Key;
import com.app.common.Util.SharePre;
import com.app.lotteryticket.Base.My_Application;

import static com.alibaba.fastjson.JSON.parseObject;
import static com.alibaba.fastjson.JSON.toJSONString;

public class Main_SharePer_GlobalInfo {
    //初始化SharedPreferences
    static SharePre sharePre = My_Application.getInstance().getGlobalSharedPreferences();

    public static SharePre getSharePre() {
        return sharePre;
    }

    /**
     * 存放是否第一次启动应用
     *
     * @param isFirst
     */
    public static void sharePre_PutFirstInstall(boolean isFirst) {
        sharePre.put(SharedPreferences_Key.FIRSTINSTALL, isFirst);
        sharePre.commit();
    }

    /**
     * 获取用户是否第一次启动应用
     *
     * @return
     */
    public static boolean sharePre_GetFirstInstall() {
        return sharePre.getBoolean(SharedPreferences_Key.FIRSTINSTALL, true);
    }
}
