package com.app.common.MVP.Presenter.Implement;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import com.alibaba.fastjson.JSONObject;
import com.app.common.Base.CommonApplication;
import com.app.common.HttpRequest.HttpPath;
import com.app.common.HttpRequest.ResultListener.ResultListener;
import com.app.common.MVP.Model.Bean.UserInfoBean;
import com.app.common.MVP.Model.Implement.Base_Model_Implement;
import com.app.common.MVP.Model.Interface.Base_Model_Interface;
import com.app.common.MVP.Presenter.Interface.Common_UserAll_Presenter_Interface;
import com.app.common.Util.Common_SharePer_UserInfo;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**用户所有信息通用获取接口
 * @ClassName: com.ygworld.MVP.Presenter.Implement
 * @author: Administrator 杨重诚
 * @date: 2016/10/12:17:07
 */

public class Common_UserAll_Presenter_Implement implements Common_UserAll_Presenter_Interface {
    Context mContext;
    CommonApplication mCommonApplication;
    Base_Model_Interface mBase_model_implement;//请求网络数据的model实现类
    public Common_UserAll_Presenter_Implement(Context context){
        this.mContext=context;
        this.mBase_model_implement=new Base_Model_Implement();
        mCommonApplication=CommonApplication.getInstance();
    }

    public interface RefreshUserInfoListener {
        /**
         * 刷新用户信息Listener
         * @param userInfoBean
         */
        public void requestListener(boolean isSucc, UserInfoBean userInfoBean);
    }
    /**
     * 刷新用户信息
     */
    @Override
    public void refreshUserInfo(final RefreshUserInfoListener refreshUserInfoListener){
        refreshUserInfo(getRefreshUserInfo_Params(),refreshUserInfoListener,false);
    }
    /**
     * 刷新用户信息
     */
    @Override
    public void refreshUserInfo(final RefreshUserInfoListener refreshUserInfoListener, boolean isLoadingDialog){
        refreshUserInfo(getRefreshUserInfo_Params(),refreshUserInfoListener,isLoadingDialog);
    }
    /**
     * 获取刷新用户信息的Params
     * @return
     */
    public Map<String, String> getRefreshUserInfo_Params() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("operation", "refresh");
        params.put("username", "null");
        params.put("password", "null");
        return params;
    }

    /**
     * 刷新用户信息
     * @param params
     */
    public void refreshUserInfo(Map<String, String> params,final RefreshUserInfoListener refreshUserInfoListener, boolean isLoadingDialog) {
        mBase_model_implement.requestData(mContext, HttpPath.URL_API_USER_INFO, params, new ResultListener() {
            @Override
            public void onResult( boolean isSucc, String msg, String request) {
                UserInfoBean infoBean=null;
                if (isSucc) {
                    infoBean = JSONObject.parseObject(request,
                            UserInfoBean.class);
                    //更新用户信息
                    mCommonApplication.setUseInfoVo(infoBean);
                    //存放用户OauthToken
                    Common_SharePer_UserInfo.sharePre_PutOauthToken(infoBean.getOauth_token());
                    String encrypt = infoBean.getUserId();
                    //TalkingDataAppCpa.onLogin("" + encrypt);
                }
                refreshUserInfoListener.requestListener(isSucc,infoBean);
            }
        },isLoadingDialog);
    }
}
