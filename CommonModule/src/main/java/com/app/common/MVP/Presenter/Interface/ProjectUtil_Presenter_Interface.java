package com.app.common.MVP.Presenter.Interface;

import android.content.Context;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;

import com.app.common.MVP.Presenter.Implement.ProjectUtil_Presenter_Implement;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import java.util.Map;

/**项目相关的工具类接口
 * @ClassName: com.ygworld.MVP.Presenter.Interface
 * @author: Administrator 杨重诚
 * @date: 2016/11/2:14:09
 */

public interface ProjectUtil_Presenter_Interface {
    /**
     * 根据URL跳转不同页面
     * @param context
     * @param menuUrl
     * @param title
     */
    public void urlIntentJudge(Context context, String menuUrl, String title);
    /**
     * 将cookie同步到WebView
     * @return true 同步cookie成功，false同步cookie失败
     * @Author JPH
     */
    public void syncCookie(Context mContext, String url, ProjectUtil_Presenter_Implement.SyncCookieListener syncCookieListener);
    /**
     * 应用检测更新的Params
     * @return
     */
    public Map<String, String> checkAppVersion_Params(String code);
    /**
     * 普通更新
     *
     * @param content
     */
    public void showAppUpdateDialog_Common(String content, final String url);
    /**
     * 强制更新
     *
     * @param content
     */
    public void showAppUpdateDialog_Forced(final String content, final String url);
    /**
     * 应用检测更新
     */
    public void checkAppVersion(String source, ProjectUtil_Presenter_Implement.ResultCheckAppListener resultCheckAppListener);
    /**
     * 应用检测更新
     */
    public void checkAppVersion(Map<String, String> params, final String source, ProjectUtil_Presenter_Implement.ResultCheckAppListener resultCheckAppListener);

    /**
     * 获取下载的DialogBuilder
     * @return
     */
    public NiftyDialogBuilder getDownloadDialogBuilder();

    /**
     * 请求维护页面
     */
    public void requestServerState(ProjectUtil_Presenter_Implement.ServerStateResultListener serverStateResultListener);

    /**
     * 设置维护页
     * @param context
     * @param msgs
     */
    public void setServerState(Context context, String msgs);

    /**
     * 设置WebView的参数并返回
     * @param webView
     * @return
     */
    public WebView setWebViewSetting(WebView webView);
    /**
     * 是否显示密码
     * @param editText 输入框
     * @param imageView 图形控件
     */
    public boolean isShowPassword(boolean isShowPassword, EditText editText, ImageView imageView);
    /**
     * 用户是否登陆
     * @return
     */
    public boolean isLoging();
}
