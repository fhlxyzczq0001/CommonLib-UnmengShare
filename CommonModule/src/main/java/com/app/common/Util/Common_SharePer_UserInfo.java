package com.app.common.Util;

import android.app.Application;
import android.os.Bundle;


import com.app.common.Base.CommonApplication;
import com.app.common.MVP.Model.Bean.CookieMapBean;
import com.app.common.Public.SharedPreferences_Key;

import static com.alibaba.fastjson.JSON.parseObject;
import static com.alibaba.fastjson.JSON.toJSONString;

public class Common_SharePer_UserInfo extends Application {
    //初始化SharedPreferences
    static SharePre sharePre= CommonApplication.getInstance().getUserSharedPreferences();;
    /**
     * 存放cookie缓存
     */
    public static void sharePre_PutCookieCache(CookieMapBean cookieMapBean) {
        String JSONS = toJSONString(cookieMapBean);
        sharePre.put(SharedPreferences_Key.COOKIE_CACHE, JSONS);
        sharePre.commit();
    }

    /**
     * 读取Cookie缓存
     *
     * @return
     */
    public static CookieMapBean sharePre_GetCookieCache() {
        String JSONS = sharePre.getString(SharedPreferences_Key.COOKIE_CACHE,
                "");
        return parseObject(JSONS, CookieMapBean.class);
    }
    /**
     * 存放用户id
     * @param userId
     */
    public static void sharePre_PutUserID(String userId) {
        sharePre.put(SharedPreferences_Key.USER_ID, userId);
        sharePre.commit();
    }
    /**
     * 获取用户id
     * @return
     */
    public static String sharePre_GetUserID() {
        return sharePre.getString(SharedPreferences_Key.USER_ID, "");
    }

    /**
     * 存放用户oauthToken
     * @param oauthToken
     */
    public static void sharePre_PutOauthToken(String oauthToken) {
        sharePre.put(SharedPreferences_Key.OAUTH_TOKEN, oauthToken);
        sharePre.commit();
    }
    /**
     * 获取用户authToken
     * @return
     */
    public static String sharePre_GetOauthToken() {
        return sharePre.getString(SharedPreferences_Key.OAUTH_TOKEN, "");
    }
}
