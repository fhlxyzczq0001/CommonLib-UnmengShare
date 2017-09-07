package com.app.lotteryticket.Util;

import com.alibaba.fastjson.JSON;
import com.app.common.Public.SharedPreferences_Key;
import com.app.common.Util.SharePre;
import com.app.lotteryticket.Base.My_Application;
import com.app.lotteryticket.MVP.Model.Bean.WelcomePageBean;

import static com.alibaba.fastjson.JSON.parseObject;

public class Main_SharePer_SdCard_Info {
    //初始化SharedPreferences
    static SharePre sharePre = My_Application.getInstance().getSdCardSharedPreferences();

    public static SharePre getSharePre(){
        return sharePre;
    }
    /**
     * 存放启动页对象
     * @param pageBean
     */
    public static void sharePre_PutWelcomePageBean(WelcomePageBean pageBean) {
        String JSONS="";
        if(pageBean!=null){
             JSONS = JSON.toJSONString(pageBean);
        }
        sharePre.put(SharedPreferences_Key.WELCOME_PAGE_BEAN, JSONS);
        sharePre.commit();
    }

    /**
     * 获取启动页对象
     * @return
     */
    public static WelcomePageBean sharePre_GetWelcomePageBean() {
        String JSONS = sharePre.getString(SharedPreferences_Key.WELCOME_PAGE_BEAN,
                "");
        return parseObject(JSONS, WelcomePageBean.class);
    }

}
