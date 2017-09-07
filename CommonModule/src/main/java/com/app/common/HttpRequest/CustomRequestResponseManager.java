package com.app.common.HttpRequest;

import android.app.Dialog;
import android.content.Context;
import android.webkit.CookieManager;

import com.alibaba.fastjson.JSONObject;
import com.app.common.Base.CommonApplication;
import com.app.common.HttpRequest.HttpManager.HttpRequestManager;
import com.app.common.HttpRequest.RequestBody.RequestBodyApi;
import com.app.common.HttpRequest.ResultListener.ResultListener;
import com.app.common.MVP.Model.Bean.Request_Bean;
import com.app.common.MVP.Presenter.Implement.ProjectUtil_Presenter_Implement;
import com.app.common.Public.PublicMsg;
import com.app.common.Public.RouterUrl;
import com.app.common.R;
import com.app.common.Util.Common_SharePer_UserInfo;
import com.app.common.Util.IntentUtil;
import com.app.common.Util.L;
import com.app.common.Util.LoadingDialog;
import com.app.common.Util.ToastUtil;
import com.app.common.Util.ToolsUtils;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.ApiException;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;

import java.util.Map;

/**Rxjava+okhttp请求回调类
 * Created by Administrator on 2017/2/24.
 */

public class CustomRequestResponseManager {
    private CommonApplication mCommonApplication;
    public static ProjectUtil_Presenter_Implement projectUtil_presenter_implement = null;
    Dialog dialog;
    public CustomRequestResponseManager() {
        mCommonApplication = CommonApplication.getInstance();
    }

    /**
     * pos请求
     *
     * @param context
     * @param url
     * @param mParams
     * @param resultListener
     * @param isLoadingDialog
     */
    public void requestPost(final Context context, final String url, Map<String, String> mParams, final ResultListener resultListener, final boolean isLoadingDialog) {
       /* long time = System.currentTimeMillis() / 1000;
        mParams.put(PublicMsg.Post_PARAM_TS, String.valueOf(time));
        mParams.put(PublicMsg.Post_PARAM_SIGNA, ToolsUtils.encryption(ToolsUtils.encryption(String.valueOf(PublicMsg.Post_APPSECRET_ANDROID + time)) + PublicMsg.Post_APPKEY_ANDROID).toUpperCase());
        mParams.put(PublicMsg.Post_PARAM_APPKEY, PublicMsg.Post_APPKEY_ANDROID);
        mParams.put(PublicMsg.Post_APP_VERSION, AppUtils.getAppVersionName(context));*/

        if (null != mCommonApplication.getUseInfoVo() && null != Common_SharePer_UserInfo.sharePre_GetUserID() && !Common_SharePer_UserInfo.sharePre_GetUserID().isEmpty()) {
            String userId = mCommonApplication.getUseInfoVo().getUserId();
            String oauth_token = mCommonApplication.getUseInfoVo().getOauth_token();
            mParams.put(PublicMsg.Post_Uid, userId);
            //mParams.put(PublicMsg.Post_Token, oauth_token);

        } else if (null != Common_SharePer_UserInfo.sharePre_GetUserID() && !Common_SharePer_UserInfo.sharePre_GetUserID().isEmpty()) {
            String userId = Common_SharePer_UserInfo.sharePre_GetUserID();
            String oauth_token = Common_SharePer_UserInfo.sharePre_GetOauthToken();
            mParams.put(PublicMsg.Post_Uid, userId);
            //mParams.put(PublicMsg.Post_Token, oauth_token);
        } else {
            mParams.put(PublicMsg.Post_Uid, "");
            //mParams.put(PublicMsg.Post_Token, "");
        }

        //如果请求地址是刷新token的地址
        /*if (url.contains(HttpPath.REFRESH_TOKEN) && !Common_SharePer_UserInfo.sharePre_GetOauthLongToken().isEmpty()) {
            mParams.put(PublicMsg.Post_Token, "");//将autoToken设置成空
            mParams.put(PublicMsg.Refresh_Token, Common_SharePer_UserInfo.sharePre_GetOauthLongToken());
        }*/

        HttpRequestManager httpRequestManager = new HttpRequestManager(new HttpOnNextListener() {
            @Override
            public void onNext(String resulte, String mothead) {
                JSONObject jsonObject = JSONObject.parseObject(resulte);
                int resCode = jsonObject.getIntValue("res_code");
                String res_Msg = jsonObject.getString("res_msg");
                boolean isSucc = false;
                switch (resCode) {
                    case 1:
                        //请求成功
                        isSucc = true;
                        break;
                    case -1:
                        //登录超时,需要重新登陆
                        //清理当前存放的用户信息
                        CommonApplication.getInstance().setUseInfoVo(null);
                        CommonApplication.getInstance().getUserSharedPreferences().clear();
                        CookieManager.getInstance().removeAllCookie();
                        break;
                    case 2:
                        if (!"WelcomePage_View_Implement".equals(context.getClass().getSimpleName())) {
                            String updateMessage = jsonObject.getString("message");
                            String updateUrl = jsonObject.getString("url");
                            ProjectUtil_Presenter_Implement.getSingleton(context).showAppUpdateDialog_Forced(updateMessage, updateUrl);
                        } else {
                            //版本不符，需强制更新
                            ToastUtil.WarnImageToast(context, res_Msg);
                        }
                        break;
                    case 3:
                        //服务器连接失败，请求维护页面
                        if (projectUtil_presenter_implement == null) {
                            projectUtil_presenter_implement = new ProjectUtil_Presenter_Implement(context);
                        }
                        projectUtil_presenter_implement.setServerState(context, res_Msg);
                        //ToastUtil.WarnImageToast(context,res_Msg);
                        break;
                    case 4:
                        //普通更新
                        if (!"WelcomePage_View_Implement".equals(context.getClass().getSimpleName())) {
                            String updateMessage = jsonObject.getString("message");
                            String updateUrl = jsonObject.getString("url");
                            ProjectUtil_Presenter_Implement.getSingleton(context).showAppUpdateDialog_Common(updateMessage, updateUrl);
                        } else {
                            //版本不符，需强制更新
                            ToastUtil.WarnImageToast(context, res_Msg);
                        }
                        break;
                    case 0:
                        //服务器返回失败
                        if(url.contains("national_activity.do")){//检查活动开启状态
                        }else{
                            ToastUtil.WarnImageToast(context, res_Msg);
                        }
                        break;
                    case 10011:
                        //登录超时,需要重新登陆
                        ToastUtil.WarnImageToast(context, res_Msg);
                        //清理当前存放的用户信息
                        CommonApplication.getInstance().setUseInfoVo(null);
                        CommonApplication.getInstance().getUserSharedPreferences().clear();
                        CookieManager.getInstance().removeAllCookie();
                        break;
                    default:
                        // 操作失败 或 错误码未知
                        //ToastUtil.WarnImageToast(context, res_Msg);
                        break;
                }
                Request_Bean request_Bean = JSONObject.parseObject(resulte, Request_Bean.class);
                resultListener.onResult(isSucc, request_Bean.getRes_msg(), resulte);
                dialogDismiss(dialog);
                if(resCode==10011){
                    new IntentUtil().intent_RouterTo(context, RouterUrl.userinfo_UserLogingRouterUrl);
                }
            }

            @Override
            public void onError(ApiException e) {
                e.printStackTrace();
                L.e("请求失败", "请求失败" + e.hashCode());
               /* resultListener.onResult(false, "请求失败", context.getResources().getString(R.string.ERROR_MESSAGE));
                //请求维护页面
                if (projectUtil_presenter_implement == null) {
                    projectUtil_presenter_implement = new ProjectUtil_Presenter_Implement(context);
                }
                projectUtil_presenter_implement.setServerState(context, context.getResources().getString(R.string.ERROR_MESSAGE));*/
                dialogDismiss(dialog);
            }
        }, context);
        //存储ip与域名的关系
        String key=url.substring(0, ToolsUtils.getCharacterPosition(url,"/",3));
        CommonApplication.httpdnsHosMap.put(key, HttpPath.HOSTS);
        //====================显示进度加载=============================
            dialog= (Dialog) LoadingDialog.initProgressDialog(context).getDialog();
            if(isLoadingDialog){
                if(dialog==null){
                }else {
                }
                dialog.show();
            }
        RequestBodyApi requestBodyApi = new RequestBodyApi(url,"POST");
        requestBodyApi.setParams(mParams);
        requestBodyApi.setShowProgress(false);
        requestBodyApi.setDialog(dialog);
        httpRequestManager.doHttpDeal(requestBodyApi);
    }
    /**
     * get请求
     * @param context
     * @param url
     * @param resultListener
     * @param isLoadingDialog
     */
    public void requestGet(final Context context, String url, final ResultListener resultListener, final boolean isLoadingDialog) {
        HttpRequestManager httpRequestManager =new HttpRequestManager(new HttpOnNextListener() {
            @Override
            public void onNext(String resulte, String mothead) {
                resultListener.onResult(true, "请求成功", resulte);
                dialogDismiss(dialog);
            }

            @Override
            public void onError(ApiException e) {
                resultListener.onResult(false, "请求失败", context.getResources().getString(R.string.ERROR_MESSAGE));
                dialogDismiss(dialog);
            }
        },context);
        //====================显示进度加载=============================
        try {
            dialog= (Dialog) LoadingDialog.initProgressDialog(context).getDialog();
            if(isLoadingDialog){
                dialog.show();
            }
        }catch (Exception e){

        }

        RequestBodyApi requestBodyApi = new RequestBodyApi(url,"GET");
        requestBodyApi.setShowProgress(false);
        requestBodyApi.setDialog(LoadingDialog.initProgressDialog(context).getDialog());
        httpRequestManager.doHttpDeal(requestBodyApi);
    }
    private void dialogDismiss(Dialog dialog){
        if (dialog!=null){
            dialog.dismiss();
        }
    }
}