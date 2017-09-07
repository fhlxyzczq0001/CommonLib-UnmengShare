package com.app.lotteryticket.Base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;


import com.app.common.Base.CommonApplication;
import com.app.common.MVP.Model.Bean.UserInfoBean;
import com.app.common.Util.L;
import com.app.common.Util.SharePre;
import com.app.lotteryticket.BuildConfig;
import com.chenenyu.router.Router;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;

/**
 * @ClassName: My_Application
 * @author: Administrator杨重诚
 * @date: 2016/6/6 15:43
 */
public class My_Application extends Application {
    public static My_Application mApp;
    CommonApplication mCommonApplication;

    public static My_Application getInstance() {
        return mApp;
    }
    // 存放全部商品页面－－二级栏目
    public Map<String, String> allGoodsMap = new HashMap<String, String>();

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        initModule();//初始化Module模块
        CustomActivityOnCrash.install(this,-1);//初始化系统异常显示的activity(第二个参数是异常图片，-1是默认图片，否则传R.mipmap.icon)

        //TODO 打包时需把app项目下的common_build.gradle中的LOG_DEBUG定义为false(非调试模式)
        // 调试时，将第三个参数改为true
        Bugly.init(this, "1a380b3dbb", BuildConfig.LOG_DEBUG);
        //bugly声明为开发设备，用于开发者调试使用
        Bugly.setIsDevelopmentDevice(getApplicationContext(), BuildConfig.LOG_DEBUG);
        // 初始化路由框架
        Router.initialize(this);
        //动态判断是debug还是release
        if(BuildConfig.LOG_DEBUG){
            L.isDebug = true;//设置Log打印是否显示
            // 开启路由框架log
            Router.setDebuggable(true);
        }else{
            L.isDebug = false;//设置Log打印是否显示
            // 开启路由框架log
            Router.setDebuggable(false);
        }
    }
    /**
     * 初始化Module模块
     */
    private void initModule(){
        //初始化公共Module
        mCommonApplication=CommonApplication.initCommonApplication(mApp);
        if(!com.app.common.BuildConfig.IsBuildMudle){
            //初始化用户中心模块
            initReflectionModule("com.app.userinfo.Base.Application.release.UserInfo_Application","initUserinfo_Application");
        }
    }

    /**
     * 通过反射初始化Module
     * @param methodName
     */
    private void initReflectionModule(String modulePackage,String methodName){
        //初始化Module,通过反射动态兼容是否是lib

            try {
                //通过完整的类型路径获取类
                Class<?> classType = Class.forName(modulePackage);
                //使用newInstance创建对象
                Object invokeTester = classType.newInstance();
                //获取initSecond_Application方法
                Method method = classType.getMethod(methodName, new Class[]{Application.class,CommonApplication.class});
                //调用invokeTester对象上的initSecond_Application()方法
                method.invoke(invokeTester, new Object[] {mApp, mCommonApplication});
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
    }
    /**
     * 获取全局SharePre
     *
     * @return
     */
    public SharePre getGlobalSharedPreferences() {
        return mCommonApplication.getGlobalSharedPreferences();
    }

    /**
     * 获取用户SharePre
     *
     * @return
     */
    public SharePre getUserSharedPreferences() {
        return mCommonApplication.getUserSharedPreferences();
    }

    /**
     * 获取sdcard的SharePre
     *
     * @return
     */
    public SharePre getSdCardSharedPreferences() {
        return mCommonApplication.getSdCardSharedPreferences();
    }


    // 取得用户信息
    public UserInfoBean getUseInfoVo() {
        if (mCommonApplication.getUseInfoVo() != null) {
            return mCommonApplication.getUseInfoVo();
        }
        return null;
    }

    // 设置用户信息
    public void setUseInfoVo(UserInfoBean useInfoVo) {
        mCommonApplication.setUseInfoVo(useInfoVo);
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        // 安装tinker
        Beta.installTinker();
    }
}
