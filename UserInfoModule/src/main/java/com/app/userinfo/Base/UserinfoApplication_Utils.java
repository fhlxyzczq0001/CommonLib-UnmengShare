package com.app.userinfo.Base;


import com.app.userinfo.Base.Application.UserInfoApplication_Interface;

/**
 * Created by Administrator on 2017/7/7.
 * 获取当前的application对象
 */

public class UserinfoApplication_Utils {

    static UserInfoApplication_Interface application_interface;

    public static UserInfoApplication_Interface getApplication(){
        /*if(BuildConfig.IsBuildMudle){
            application_interface = com.ddt.ygworld.userinfo.Base.Application.debug.UserInfo_Application.getInstance();
        }else {
            application_interface = com.ddt.ygworld.userinfo.Base.Application.release.UserInfo_Application.getInstance();
        }*/
        application_interface = com.app.userinfo.Base.Application.release.UserInfo_Application.getInstance();
        return application_interface;
    }
}
