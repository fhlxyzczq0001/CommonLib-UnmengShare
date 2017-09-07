package com.app.userinfo.MVP.View.Interface;

import android.content.Context;

import com.app.common.MVP.Presenter.Implement.ProjectUtil_Presenter_Implement;
import com.app.common.MVP.Presenter.Interface.ProjectUtil_Presenter_Interface;
import com.app.common.Public.RouterUrl;
import com.app.common.Util.IntentUtil;
import com.chenenyu.router.RouteInterceptor;
import com.chenenyu.router.RouteRequest;
import com.chenenyu.router.annotation.Interceptor;
/**
 * 用户信息拦截器
 * Created by ${杨重诚} on 2017/7/28.
 */
@Interceptor(RouterUrl.interceptor_UserInfo_RouterUrl)
public class Userinfo_UserInfoInterceptor implements RouteInterceptor {
    ProjectUtil_Presenter_Interface mProjectUtil_presenter_interface;
    @Override
    public boolean intercept(Context context, RouteRequest routeRequest) {
        mProjectUtil_presenter_interface=new ProjectUtil_Presenter_Implement(context);
        if(!mProjectUtil_presenter_interface.isLoging()){
            new IntentUtil().intent_RouterTo(context, RouterUrl.userinfo_UserLogingRouterUrl);
            //拦截
            return true;
        }
        return false;
    }
}
