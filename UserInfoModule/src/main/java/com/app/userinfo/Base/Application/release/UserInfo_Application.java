package com.app.userinfo.Base.Application.release;

import android.app.Application;
import android.content.Context;

import com.app.common.Base.CommonApplication;
import com.app.common.MVP.Model.Bean.UserInfoBean;
import com.app.common.Util.SharePre;
import com.app.userinfo.Base.Application.UserInfoApplication_Interface;

import java.util.List;

/**
 * @ClassName: UserInfo_Application
 * @author: Administrator杨重诚
 * @date: 2016/6/6 15:43
 */
//public class UserInfo_Application extends LitePalApplication {
public class UserInfo_Application implements UserInfoApplication_Interface {
    public static Application mApplication;//上下文
    static CommonApplication mCommonApplication;//公共lib的Application
    static UserInfo_Application indianaApplication;//自己的Application
    /**
     * 单元测试会调用onCreate方法，所以要初始化一些东西
     */
   /*@Override
    public void onCreate() {
        super.onCreate();
        mSecond_Application=this;
        mApplication=mSecond_Application;
        mCommonApplication=CommonApplication.initCommonApplication(mSecond_Application);
        // 初始化
        Router.initialize(mApplication);
        // 开启log
        if (BuildConfig.DEBUG) {
            // 开启路由框架log
            Router.setDebuggable(true);
        }
    }*/

    /**
     * 联调打包要在主项目app初始化这个方法
     * @param application
     * @param mCommonApplication
     * @return
     */
    public void initUserinfo_Application(Application application, CommonApplication mCommonApplication){
        mApplication=application;
        UserInfo_Application.mCommonApplication=mCommonApplication;
        indianaApplication=getInstance();
    }

    /**
     * 初始化配置文件
     * @param mContext
     */
    @Override
    public void requestProfile(Context mContext){
    }

    /**
     * 获取单例对象
     * @return
     */
    public static UserInfo_Application getInstance(){
        if(indianaApplication==null){
            //第一次check，避免不必要的同步
            synchronized (UserInfo_Application.class){//同步
                if(indianaApplication==null){
                    //第二次check，保证线程安全
                    indianaApplication=new UserInfo_Application();
                }
            }
        }
        return indianaApplication;
    }

    /**
     * 获取上下文
     * @return
     */
    public Application getApplication(){
        return mApplication;
    }

    /**
     * 获取用户SharePre
     *
     * @return
     */
    @Override
    public SharePre getUserSharedPreferences() {
        return mCommonApplication.getUserSharedPreferences();
    }
    /**
     * 获取全局SharePre
     * @return
     */
    @Override
    public SharePre getGlobalSharedPreferences() {
        return mCommonApplication.getGlobalSharedPreferences();
    }
    // 取得用户信息
    @Override
    public UserInfoBean getUseInfoVo() {
        if (mCommonApplication.getUseInfoVo() != null) {
            return mCommonApplication.getUseInfoVo();
        }
        return null;
    }
    // 设置用户信息
    @Override
    public void setUseInfoVo(UserInfoBean useInfoVo) {
        mCommonApplication.setUseInfoVo(useInfoVo);
    }

}
