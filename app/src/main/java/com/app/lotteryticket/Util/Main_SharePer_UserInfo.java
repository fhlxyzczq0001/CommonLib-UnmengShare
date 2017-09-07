package com.app.lotteryticket.Util;

import com.app.common.Util.SharePre;
import com.app.lotteryticket.Base.My_Application;

public class Main_SharePer_UserInfo {
    //初始化SharedPreferences
    static SharePre sharePre = My_Application.getInstance().getUserSharedPreferences();

    public static SharePre getSharePre(){
        return sharePre;
    }
}
