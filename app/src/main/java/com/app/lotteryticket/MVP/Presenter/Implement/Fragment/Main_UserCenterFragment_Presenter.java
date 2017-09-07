package com.app.lotteryticket.MVP.Presenter.Implement.Fragment;


import android.webkit.CookieManager;

import com.alibaba.fastjson.JSON;
import com.app.common.HttpRequest.HttpPath;
import com.app.common.HttpRequest.HttpRequestMethod;
import com.app.common.HttpRequest.ResultListener.ResultDataListener;
import com.app.common.MVP.Model.Bean.Request_Bean;
import com.app.common.MVP.Model.Implement.Base_HttpRequest_Implement;
import com.app.common.MVP.Model.Interface.Base_HttpRequest_Interface;
import com.app.lotteryticket.Base.My_Application;
import com.app.lotteryticket.MVP.Contract.Fragment.Main_UserCenterFragment_Contract;
import com.app.lotteryticket.MVP.Model.Bean.Main_UserCenterBean;

import java.util.HashMap;
import java.util.Map;

/**
 * 首页用户
 * Created by ${杨重诚} on 2017/6/2.
 */

public class Main_UserCenterFragment_Presenter extends Main_UserCenterFragment_Contract.Presenter {
    Base_HttpRequest_Interface mMain_base_httpRequest_interface;
    private int countHttpMethod = 0;//总共有多少个请求网络数据的方法
    private int indexHttpMethod = 0;//记录当前执行到第几个请求方法
    public Main_UserCenterFragment_Presenter() {
        mMain_base_httpRequest_interface=new Base_HttpRequest_Implement();
    }
    /**
     * 初始化参数
     */
    @Override
    public void initData(int countHttpMethod) {
        this.countHttpMethod = countHttpMethod;
        indexHttpMethod = 0;
    }
    /**
     * 关闭刷新控件
     */
    @Override
    public void closeRefresh() {
        indexHttpMethod++;
        if (indexHttpMethod >= countHttpMethod) {
            indexHttpMethod = 0;
            mView.closeRefresh();
        }
    }
    /**
     * 请求用户中心数据
     */
    @Override
    public void requestUserCenterData() {
        requestUserCenterData(getUserCenter_Params());
    }
    /**
     *获取用户中心数据的Params
     * @return
     */
    public Map<String, String> getUserCenter_Params() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("ACT","GetUser");
        return params;
    }
    /**
     * 用户中心数据
     * @param params
     */
    public void requestUserCenterData(Map<String, String> params) {
        mMain_base_httpRequest_interface.requestData(context, HttpPath.URL_API_USER_CENTER_INFO, params, new ResultDataListener() {
            @Override
            public void onResult(boolean isSucc, String msg, Request_Bean request_bean) {
                if (isSucc) {
                    if(request_bean.getData()!=null){
                        Main_UserCenterBean main_userCenterBean = JSON.parseArray(request_bean.getData().toString(),
                                Main_UserCenterBean.class).get(0);
                        mView.setUserCenterData(main_userCenterBean);
                    }

                }else {
                    // 请求失败
                }
                closeRefresh();
            }
        }, HttpRequestMethod.POST);
    }
    /**
     * 用户退出登录的请求
     */
    @Override
    public void requestUserInfoLogout(){
        requestUserInfoLogout(getUserInfoLogout());
    }
    /**
     * 获取用户退出登录的参数
     *
     * @return
     */
    public Map<String, String> getUserInfoLogout() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("ACT", "LoginOut");
        return params;
    }

    /**
     * 用户退出登录的请求
     * @param params
     */
    public void requestUserInfoLogout(Map<String, String> params) {
        mMain_base_httpRequest_interface.requestData(context, HttpPath.URL_API_LOGING_OUT, params, new ResultDataListener() {
            @Override
            public void onResult(boolean isSucc, String msg, Request_Bean request_bean) {
                if (isSucc) {
                    quitSystem();
                }
            }
        },HttpRequestMethod.POST);
    }
    /**
     * 用户退出登录
     */
    public void quitSystem() {
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        //清除用户信息表（用户名、密码、微信登录token，token过期时间，用户接收通知状态）
        My_Application.getInstance().getUserSharedPreferences().clear();
        My_Application.getInstance().setUseInfoVo(null);
        //注释的
        //		JPushInterface.stopPush(context);
        //用户数据都清空后关闭当前页面，跳转应用首页
        mView.quitSuccess();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
