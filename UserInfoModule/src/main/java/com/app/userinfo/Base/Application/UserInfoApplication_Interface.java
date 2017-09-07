package com.app.userinfo.Base.Application;

import android.app.Application;
import android.content.Context;

import com.app.common.MVP.Model.Bean.UserInfoBean;
import com.app.common.Util.SharePre;

import java.util.List;

/**
 * Created by Administrator on 2017/7/7.
 */

public interface UserInfoApplication_Interface {

    /**
     * 获取application对象
     * @return
     */
    Application getApplication();

    /**
     * 获取sharePre对象
     * @return
     */
    SharePre getUserSharedPreferences();
    /**
     * 获取全局SharePre
     * @return
     */
    public SharePre getGlobalSharedPreferences();

    /**
     * 获取用户信息
     * @return
     */
    UserInfoBean getUseInfoVo();

    /**
     * 设置用户信息
     * @param useInfoVo
     */
    void setUseInfoVo(UserInfoBean useInfoVo);

    /**
     * 加载module的配置信息
     */
    void requestProfile(Context mContext);
}
