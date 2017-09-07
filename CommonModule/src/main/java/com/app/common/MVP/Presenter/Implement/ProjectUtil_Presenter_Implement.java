package com.app.common.MVP.Presenter.Implement;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.app.common.Base.CommonApplication;
import com.app.common.HttpRequest.HttpPath;
import com.app.common.HttpRequest.ResultListener.ResultListener;
import com.app.common.MVP.Model.Bean.EventBus.SyncCookie_EventBus;
import com.app.common.MVP.Model.Bean.VersionBean;
import com.app.common.MVP.Model.Implement.Base_Model_Implement;
import com.app.common.MVP.Model.Interface.Base_Model_Interface;
import com.app.common.MVP.Presenter.Interface.Common_UserAll_Presenter_Interface;
import com.app.common.MVP.Presenter.Interface.ProjectUtil_Presenter_Interface;
import com.app.common.Public.RouterUrl;
import com.app.common.R;
import com.app.common.Util.AppUtils;
import com.app.common.Util.Common_SharePer_UserInfo;
import com.app.common.Util.CustomDialogBuilder;
import com.app.common.Util.IntentUtil;
import com.app.common.Util.L;
import com.app.common.Util.StringUtils;
import com.app.common.Util.ToastUtil;
import com.fenjuly.library.ArrowDownloadButton;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

/**
 * 应用需求工具类实现
 *
 * @ClassName: com.ygworld.MVP.Presenter.Implement
 * @author: Administrator 杨重诚
 * @date: 2016/11/2:14:10
 */

public class ProjectUtil_Presenter_Implement implements ProjectUtil_Presenter_Interface {
    CommonApplication mCommonApplication;
    private Context mContext;
    IntentUtil intentUtil;
    Base_Model_Interface mBase_model_implement;//请求网络数据的model实现类
    private static ProjectUtil_Presenter_Implement projectUtilInterface = null;
    Common_UserAll_Presenter_Interface mCommon_userAll_presenter_interface;
    /**
     * 获取当前类的单例模式，避免调用更新对话框时重复创建
     *
     * @param context
     * @return
     */
    public static ProjectUtil_Presenter_Implement getSingleton(Context context) {
        if (projectUtilInterface == null) {
            projectUtilInterface = new ProjectUtil_Presenter_Implement(context);
        }
        return projectUtilInterface;
    }

    public ProjectUtil_Presenter_Implement(Context context) {
        mContext = context;
        mCommonApplication = CommonApplication.getInstance();
        this.mBase_model_implement = new Base_Model_Implement();
        intentUtil = new IntentUtil();
    }

    /**
     * 根据URL跳转不同页面
     *
     * @param context
     * @param menuUrl
     * @param title
     */
    @Override
    public void urlIntentJudge(Context context, String menuUrl, String title) {
        L.e("menuUrl:", menuUrl);
        final Bundle bundle = new Bundle();
         if (menuUrl.startsWith("http")) {
            //跳转至WebView页面
            bundle.putString("barname", title);
            bundle.putString("url", menuUrl);
            bundle.putString("shareLink", "");
            bundle.putString("shareContent", "");
            bundle.putString("shareTitle", "");
            intentUtil.intent_RouterTo(context, RouterUrl.mainWebViewRouterUrl, bundle);
        }else if(menuUrl.contains(RouterUrl.mainMainActivityRouterUrl)){
             intentUtil.intent_destruction_other_activity_RouterTo(context, menuUrl);
         }else if (menuUrl.contains(RouterUrl.RouterHTTP)) {
            //路由跳转
            intentUtil.intent_RouterTo(context, menuUrl);
        }
    }

public interface SyncCookieListener {
    /**
     * 同步cookie回调
     *
     * @param isSucc
     */
    public void onResult(boolean isSucc);
}

    /**
     * 将cookie同步到WebView
     *
     * @return true 同步cookie成功，false同步cookie失败
     * @Author JPH
     */
    @Override
    public void syncCookie(final Context mContext, final String url, final SyncCookieListener syncCookieListener) {
        setCookie("", url, syncCookieListener);
    }

    /**
     * 设置Cookie
     *
     * @param token
     * @return
     */
    private void setCookie(String token, String url, SyncCookieListener syncCookieListener) {
        CookieSyncManager.createInstance(mContext);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();
        /*if (mApplication.getUseInfoVo() != null && mApplication.getUseInfoVo().getUserId() != null) {
            L.e("loginToken===", token);
            String cookie = "loginToken=" + token;
            cookieManager.setCookie(WeiXinHttpPath.HOST_WX, cookie);
            cookieManager.setCookie(WeiXinHttpPath.HOST_WX_H5, cookie);
            cookieManager.setCookie(urlSetCookie(url), cookie);
        }
        cookieManager.setCookie(WeiXinHttpPath.HOST_WX, "platform=app");
        cookieManager.setCookie(WeiXinHttpPath.HOST_WX_H5, "platform=app");*/
        cookieManager.setCookie(StringUtils.urlSetCookie(url), "platform=app");
        if (mCommonApplication.getUseInfoVo() != null && mCommonApplication.getUseInfoVo().getUserId() != null) {
            cookieManager.setCookie(StringUtils.urlSetCookie(url), "islogin=true");
        }

        CookieSyncManager.getInstance().sync();

        syncCookieListener.onResult(true);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //这里发送粘性事件
                EventBus.getDefault().postSticky(new SyncCookie_EventBus(true));
            }
        }, 300);
    }

    /**
     * 截取url给微信链接设置cookie
     *
     * @param url 传入当前url
     * @return 将截取的url返回
     */
    private String urlSetCookie(String url) {
        String wxUrl = url;
        int index = url.indexOf("/", 7);
        if (index > 0) {
            wxUrl = url.substring(0, index) + "/";
        }
        return wxUrl;
    }

    /**
     * 请求应用版本最新版本
     * @param source
     * @param resultCheckAppListener
     */
    @Override
    public void checkAppVersion(String source,ResultCheckAppListener resultCheckAppListener){
        int code = AppUtils.getAppVersionCode(mContext);
        checkAppVersion(checkAppVersion_Params(String.valueOf(code)),source,resultCheckAppListener);
    }
    @Override
    public Map<String, String> checkAppVersion_Params(String code) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("VersionCode", code);
        return params;
    }

/**
 * 返回是否更新的标识状态
 */
public interface ResultCheckAppListener {
    public void onResult(boolean isUpdata);
}

    /**
     * 请求应用版本最新版本
     *
     * @param params
     * @param source 区分进入应用自动请求，手动更新abot
     */
    @Override
    public void checkAppVersion(Map<String, String> params, final String source, final ResultCheckAppListener resultCheckAppListener) {
        mBase_model_implement.requestData(mContext, HttpPath.CHECK_UPDATE, params, new ResultListener() {
            @Override
            public void onResult(boolean isSucc, String msg, String request) {
                VersionBean versionBean = null;
                if (isSucc) {
                    versionBean = JSONObject.parseObject(request,
                            VersionBean.class);
                    if (versionBean != null) {
                        final String content = versionBean.getContent();
                        final String url = versionBean.getUrl();
                        L.e("update","versionBean.getUrl():----"+versionBean.getUrl());
                        if (versionBean.getCode().equals("1")) {
                            //普通更新
                            showAppUpdateDialog_Common(content, url);
                            resultCheckAppListener.onResult(true);//将更新标识为有更新状态
                        } else if (versionBean.getCode().equals("2")) {
                            //强制更新
                            showAppUpdateDialog_Forced(content, url);
                            resultCheckAppListener.onResult(true);
                        } else if (source.equals("abot")) {
                            ToastUtil.RightImageToast(mContext, "暂无更新");
                            resultCheckAppListener.onResult(false);
                        }
                    }
                }
            }
        },false);
    }

    /**
     * 普通更新
     *
     * @param content
     */
    @Override
    public void showAppUpdateDialog_Common(String content, final String url) {
        //普通更新
        CustomDialogBuilder customDialogBuilder = new CustomDialogBuilder(mContext);
        final NiftyDialogBuilder dialogBuilder = customDialogBuilder.showDialog("软件更新", content, R.color.app_text_gray, true, "下次再说", R.color.gray, "马上更新", R.color.app_color);
        dialogBuilder.setButton2Click(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //马上更新
                L.e("update","url:---"+url);
                startUpdateService(url, 0);
                dialogBuilder.dismiss();
            }
        });
    }

    CustomDialogBuilder customDialogBuilder;
    NiftyDialogBuilder dialogBuilder = null;

    /**
     * 强制更新
     *
     * @param content
     */
    @Override
    public void showAppUpdateDialog_Forced(final String content, final String url) {
        //强制更新
        if (customDialogBuilder == null) {
            customDialogBuilder = new CustomDialogBuilder(mContext);
            customDialogBuilder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    customDialogBuilder = null;
                    projectUtilInterface = null;
                    L.e("对话框被销毁");
                }
            });
        }
        if (customDialogBuilder.isShowing()) {
            L.e("对话框已显示");
            return;
        } else {
            L.e("对话框未显示");
        }
        dialogBuilder = customDialogBuilder.showDialog("软件更新", content, R.color.app_text_gray, false, "下次再说", R.color.gray, "马上更新", R.color.app_color);
        dialogBuilder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                }
                return false;
            }
        });
        dialogBuilder.findViewById(R.id.icon_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭应用
                dialogBuilder.dismiss();
                ((Activity) mContext).finish();
                System.exit(0);
            }
        });
        dialogBuilder.setButton1Click(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //关闭应用
                dialogBuilder.dismiss();
                ((Activity) mContext).finish();
                System.exit(0);
            }
        });
        dialogBuilder.setButton2Click(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //关闭应用
                dialogBuilder.withTitle(null)
                        .withDialogColor(mContext.getResources().getColor(R.color.white))
                        .setCustomView(R.layout.common_popupwindow_download_layout, mContext)
                        .withButton1Text(null)
                        .withButton2Text(null);
                TextView downloadTitle = (TextView) dialogBuilder.findViewById(R.id.downloadTitle);
                ArrowDownloadButton button = (ArrowDownloadButton) dialogBuilder.findViewById(R.id.arrow_download_button);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //马上更新
                        startUpdateService(url, 1);
                    }
                });
                //马上更新
                startUpdateService(url, 1);
            }
        });
    }

    /**
     * 获取下载的DialogBuilder
     *
     * @return
     */
    @Override
    public NiftyDialogBuilder getDownloadDialogBuilder() {
        return dialogBuilder;
    }

    /**
     * 启动下载service
     *
     * @param url
     */
    private void startUpdateService(String url, int isForced) {
        // 启动service后台下载
        Intent mIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("apkUrl", url);
        bundle.putString("apkName", mContext.getResources().getString(R.string.app_name));
        bundle.putInt("apkIcon", 0);
        bundle.putInt("isForced", isForced);
        mIntent.putExtras(bundle);

        mIntent.setAction("com.lottery.Service.UploadService");
        mIntent.setPackage(mContext.getPackageName());
        mContext.startService(mIntent);
    }

public interface ServerStateResultListener {
    public void onResult(boolean isSucc, String msg, String request);
}

    /**
     * 请求维护页面
     */
    @Override
    public void requestServerState(final ServerStateResultListener serverStateResultListener) {
        setIsServerState(true);
        mBase_model_implement.requestData(mContext, HttpPath.SERVER_STATUS, new ResultListener() {
            @Override
            public void onResult(boolean isSucc, String msg, String request) {
                serverStateResultListener.onResult(isSucc, msg, request);
            }
        }, false);
    }

    boolean isServerState = false;//标识是否启动了维护页面

    public void setIsServerState(boolean isServerState) {
        this.isServerState = isServerState;
    }

    public boolean getIsServerState() {
        return isServerState;
    }

    /**
     * 设置维护页
     *
     * @param context
     * @param msgs
     */
    public void setServerState(final Context context, final String msgs) {
        if (isServerState) {
            return;
        }
        requestServerState(new ServerStateResultListener() {
            @Override
            public void onResult(boolean isSucc, String msg, String request) {
                if (isSucc) {
                    JSONObject jsonObject = JSONObject.parseObject(request);
                    if (null != jsonObject && !jsonObject.isEmpty()) {
                        if (null != jsonObject.getString("url") && !jsonObject.getString("url").isEmpty()) {
                            //跳转至WebView页面
                            Bundle bundle = new Bundle();
                            bundle.putString("barname", "维护页面");
                            bundle.putString("url", jsonObject.getString("url"));
                            new IntentUtil().intent_RouterTo(context, RouterUrl.mainWebViewRouterUrl, bundle);

                        } else {
                            ToastUtil.WarnImageToast(context, msg);
                        }
                    } else {
                        ToastUtil.WarnImageToast(context, msg);
                    }
                } else {
                    ToastUtil.ErrorImageToast(context, msgs);
                }
            }
        });
    }

    /**
     * 设置WebView的参数并返回
     *
     * @param webView
     * @return
     */
    @Override
    public WebView setWebViewSetting(WebView webView) {
        WebView mWebView = webView;
        //WebView双击变大，再双击后变小，当手动放大后，双击可以恢复到原始大小
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);//设置自适应任意大小的pc网页

        /**
         * 用WebView显示图片，可使用这个参数 设置网页布局类型： 1、LayoutAlgorithm.NARROW_COLUMNS ：
         * 适应内容大小 2、LayoutAlgorithm.SINGLE_COLUMN:适应屏幕，内容将自动缩放
         */
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        mWebView.getSettings().setSupportZoom(true);   // 支持缩放
        mWebView.getSettings().setBuiltInZoomControls(true);// 设置隐藏缩放按钮
        mWebView.getSettings().setDisplayZoomControls(false);//设定缩放控件隐藏
        mWebView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);

        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//支持通过JS打开新窗口

        //mWebView.getSettings().setTextSize(WebSettings.TextSize.SMALLER);
        mWebView.getSettings().setJavaScriptEnabled(true);
        //提高渲染的优先级
        mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);

        // 开启 DOM storage API 功能
        mWebView.getSettings().setDomStorageEnabled(true);
        //开启 database storage API 功能
        mWebView.getSettings().setDatabaseEnabled(true);
        //开启 Application Caches 功能
        mWebView.getSettings().setAppCacheEnabled(false);

        mWebView.getSettings().setAllowFileAccess(true);//设置可以访问文件

        mWebView.getSettings().setSavePassword(true);
        mWebView.getSettings().setSaveFormData(true);// 保存表单数据

        mWebView.getSettings().setGeolocationEnabled(true);// 启用地理定位
        mWebView.getSettings().setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");// 设置定位的数据库路径

        //把图片加载放在最后来加载渲染
        mWebView.getSettings().setBlockNetworkImage(false);
        mWebView.getSettings().setLoadsImagesAutomatically(true); //自动加载图片
        mWebView.getSettings().setPluginState(WebSettings.PluginState.ON);

        return mWebView;
    }
    /**
     * 是否显示密码
     * @param editText 输入框
     * @param imageView 图形控件
     */
    @Override
    public boolean isShowPassword(boolean isShowPassword, EditText editText, ImageView imageView) {
        if(isShowPassword){
            //不显示
            isShowPassword=false;
            imageView.setImageResource(R.mipmap.icon_eye_hint);
            // 显示为密码
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            // 使光标始终在最后位置
            Editable etable = editText.getText();
            Selection.setSelection(etable, etable.length());
        }else {
            //不显示
            isShowPassword=true;
            imageView.setImageResource(R.mipmap.icon_eye_show);
            // 显示为普通文本
            editText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            // 使光标始终在最后位置
            Editable etable = editText.getText();
            Selection.setSelection(etable, etable.length());
        }
        return isShowPassword;
    }

    /**
     * 用户是否登陆
     * @return
     */
    @Override
    public boolean isLoging(){
        if(CommonApplication.getInstance().getUseInfoVo()==null
                &&( Common_SharePer_UserInfo.sharePre_GetUserID()==null||Common_SharePer_UserInfo.sharePre_GetUserID().isEmpty())){
            return false;
        }
        return true;
    }
}
