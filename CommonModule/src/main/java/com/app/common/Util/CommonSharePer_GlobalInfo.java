package com.app.common.Util;

import com.alibaba.fastjson.JSONObject;
import com.app.common.Base.CommonApplication;
import com.app.common.MVP.Model.Bean.UserLogingPhoneCacheBean;
import com.app.common.Public.SharedPreferences_Key;

public class CommonSharePer_GlobalInfo {
    //初始化SharedPreferences
    static SharePre sharePre = CommonApplication.getInstance().getGlobalSharedPreferences();

    public static SharePre getSharePre() {
        return sharePre;
    }

    /**
     * -存放登录成功的用户名
     *
     * @param userName
     */
    public static void sharePre_PutUserNameCache(String userName) {
        //判断是否已有登录账号
        UserLogingPhoneCacheBean logingPhoneCacheBean = sharePre_GetUserNameCache();
        if (logingPhoneCacheBean == null) {
            logingPhoneCacheBean = new UserLogingPhoneCacheBean();
        }
        logingPhoneCacheBean.getPhoneCache().add(userName);//添加登录名
        String json = JSONObject.toJSONString(logingPhoneCacheBean);//转化成json存放
        sharePre.put(SharedPreferences_Key.USER_NAME_CACHE, json);//存放缓存对象
        sharePre.commit();
    }

    /**
     * 获取存放登录成功的用户名
     *
     * @return
     */
    public static UserLogingPhoneCacheBean sharePre_GetUserNameCache() {
        String userName = sharePre.getString(SharedPreferences_Key.USER_NAME_CACHE,
                "");
        if (userName != null && !userName.equals("")) {
            return JSONObject.parseObject(userName, UserLogingPhoneCacheBean.class);
        }
        return null;
    }
    /**
     * 存放是否更新应用
     *
     * @param isUpdate
     */
    public static void sharePre_PutIsUpdate(boolean isUpdate) {
        sharePre.put(SharedPreferences_Key.IS_UPDATE, isUpdate);
        sharePre.commit();
    }

    /**
     * 获取是否更新应用
     *
     * @return
     */
    public static boolean sharePre_GetIsUpdate() {
        return sharePre.getBoolean(SharedPreferences_Key.IS_UPDATE, false);
    }

}
