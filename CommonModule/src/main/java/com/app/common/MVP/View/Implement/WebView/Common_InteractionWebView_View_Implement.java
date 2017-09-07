package com.app.common.MVP.View.Implement.WebView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.app.common.Base.CommonApplication;
import com.app.common.Base.Common_View_BaseActivity;
import com.app.common.MVP.Model.Bean.CookieMapBean;
import com.app.common.MVP.Presenter.Implement.Common_Main_WebView_Presenter_Javascript_Implement;
import com.app.common.MVP.Presenter.Implement.ProjectUtil_Presenter_Implement;
import com.app.common.MVP.Presenter.Interface.Common_WebView_Presenter_Javascript_Interface;
import com.app.common.MVP.View.Interface.Common_InteractionWebView_View_Interface;
import com.app.common.Public.RouterUrl;
import com.app.common.Public.SharedPreferences_Key;
import com.app.common.R;
import com.app.common.R2;
import com.app.common.Util.Common_SharePer_UserInfo;
import com.app.common.Util.L;
import com.app.common.Util.SharePre;
import com.chenenyu.router.annotation.Route;
import com.gc.materialdesign.views.ProgressBarDeterminate;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 交互webview的实现类
 *
 * @ClassName: com.ygworld.MVP.View.Implement.Activity.WebView
 * @author: Administrator 杨重诚
 * @date: 2016/11/3:9:26
 */
@Route(RouterUrl.mainWebViewRouterUrl)
public class Common_InteractionWebView_View_Implement extends Common_View_BaseActivity implements Common_InteractionWebView_View_Interface {
    //进度条
    @BindView(R2.id.progressDeterminate)
    ProgressBarDeterminate progressDeterminate;
    //WebView
    @BindView(R2.id.webView)
    WebView webView;
    // 全屏时视频加载view
    @BindView(R2.id.video_view)
    FrameLayout videoview;

    private View xCustomView;
    private WebChromeClient.CustomViewCallback xCustomViewCallback;
    private SampleWebChromeClient xwebchromeclient;
    ValueCallback mUploadMessage;

    Message mMessage;
    Handler webViewHandle;

    private String url;//页面链接
    private String bar_name;//标题
    private String shareLink;// 分享链接
    private String shareImage;//分享图片
    private String shareTitle;// 分享标题
    private String shareContent;// 分享内容
    private String marker = "";// 用于标记一些特殊的类别，比如“赚“活动，需要做特殊处理
    Bitmap uploadBitmap = null;//上传图片的bitmap

    ProjectUtil_Presenter_Implement mProjectUtil_presenter_implement;
    Common_WebView_Presenter_Javascript_Interface mMainWebView_presenter_javascript_interface;

    /**
     * 初始化Handler
     */
    private void initHandler() {
        webViewHandle = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                this.obtainMessage();
            }
        };
    }

    //---------------------点击事件---------------------------------------
    //返回按钮点击事件
    @OnClick({R2.id.acbr_left1_icon_RippleView, R2.id.acbr_left1_text_RippleView})
    void finishA(View view) {
        finishAct();
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.common_act_webview_interaction_layout);
        isShowSystemBarTintManager = false;//不显示沉浸式状态栏
        //设置软键盘弹起方式
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getWindow().setBackgroundDrawableResource(R.color.black);//设置窗体背景色
    }

    @Override
    protected void init() {
        mProjectUtil_presenter_implement = new ProjectUtil_Presenter_Implement(context);
        mMainWebView_presenter_javascript_interface = new Common_Main_WebView_Presenter_Javascript_Implement();
        setWebView(webView, url);//设置webview
        initHandler();//初始化Handler
    }

    @Override
    protected void setListeners() {
    }

    @Override
    protected void setTitleBar() {
        //设置actionbar基本样式
        setActionbarBar(bar_name, R.color.white, R.color.app_text_black, true, true);
        //设置左边返回文字
        acbr_left1_text.setText("返回");
        acbr_left1_text.setVisibility(View.VISIBLE);
    }

    @Override
    protected void getData() {

    }

    /**
     * 获取页面传值
     */
    @Override
    public void getBundleValues(Bundle mBundle) {
        super.getBundleValues(mBundle);
        bar_name = mBundle.getString("bar_name", "");
        url = mBundle.getString("url", "");
        shareLink = mBundle.getString("shareLink", "");
        shareTitle = mBundle.getString("shareTitle", "");
        shareContent = mBundle.getString("shareContent", "");
        marker = mBundle.getString("marker", "");
    }

    /**
     * 设置WebView
     *
     * @param mWebView
     * @param Url
     */
    @Override
    public void setWebView(final WebView mWebView, final String Url) {
        initWebView(mWebView);
        mProjectUtil_presenter_implement.syncCookie(context, url, new ProjectUtil_Presenter_Implement.SyncCookieListener() {
            @Override
            public void onResult(boolean isSucc) {
                if (isSucc) {
                    //将cookie同步到WebView
                    mWebView.loadUrl(Url);
                }

            }
        });
    }


    /**
     * 初始化WebView
     *
     * @param mWebView
     */
    @SuppressLint("JavascriptInterface")
    @Override
    public void initWebView(WebView mWebView) {
        mWebView = mProjectUtil_presenter_implement.setWebViewSetting(mWebView);
        //设置js调用本地方法
        mWebView.addJavascriptInterface(mMainWebView_presenter_javascript_interface, "android");

        //设置监听
        mWebView.setWebViewClient(new SampleWebViewClient());
        xwebchromeclient = new SampleWebChromeClient();
        mWebView.setWebChromeClient(xwebchromeclient);

        //设置监听下载功能
        mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                // 监听下载功能，当用户点击下载链接的时候，直接调用系统的浏览器来下载
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

    /**
     * 自定义WebViewClient
     *
     * @author Administrator
     */
    private class SampleWebViewClient extends WebViewClient {
        // 是否在本WebView中跳转还是通过系统浏览器跳转，true为webview内跳转
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            if (url != null && !url.equals("")) {
                //判断是不是需要重定向到登陆页面
                if (url.trim().equals("ddt://ation/login")) {
                    upLoginOnAndroid();
                } else {
                    //视图内加载url
                    progressDeterminate.setVisibility(View.VISIBLE);
                    //302到授权中心
                    if (url.startsWith("http://passport.ddtkj.net") || url.startsWith("http://account.dadetongkeji.net.cn")) {
                        try {
                            URL parsedUrl = new URL(url);
                            SharePre sharePre = new SharePre(context, SharedPreferences_Key.COOKIE_CACHE, Context.MODE_PRIVATE);
                            //获取cookie缓存对象
                            CookieMapBean cookieMapBean = Common_SharePer_UserInfo.sharePre_GetCookieCache();
                            if (null != cookieMapBean) {
                                HashSet<String> hosts_cookie = cookieMapBean.getCookieMap().get(parsedUrl.getHost());
                                if (null != hosts_cookie && hosts_cookie.size() > 0) {
                                    CookieManager cookieManager = CookieManager.getInstance();
                                    //                                cookieManager.removeAllCookie();
                                    cookieManager.setAcceptCookie(true);
                                    //                                cookieManager.removeSessionCookie();
                                    for (String cookie : hosts_cookie) {
                                        if (url.startsWith("http://passport.ddtkj.net")) {//正式服务器
                                            cookieManager.setCookie("http://passport.ddtkj.net/", cookie);
                                        } else {//测试服务器
                                            cookieManager.setCookie("http://account.dadetongkeji.net.cn/", cookie);
                                        }
                                    }
                                }
                            }
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    }
                    view.loadUrl(url);
                }
                return true;
            }
            return false;
        }

        // 页面开始加载
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            L.e("url:", url);
            progressDeterminate.setVisibility(View.VISIBLE);
            super.onPageStarted(view, url, favicon);
        }

        // 页面加载完成
        @Override
        public void onPageFinished(WebView view, String url) {
            progressDeterminate.setVisibility(View.GONE);
            String t = view.getTitle();
            if (null == t || t.equals("")) {
                return;
            }
            if (t.length() > 10) {
                t = t.substring(0, 10) + "...";
            }
            if (null != bar_name && !"".equals(bar_name)) {
                acbr_title.setText(bar_name);
                L.e("bar_name:", bar_name);
            } else {
                acbr_title.setText(t);
                L.e("t:", t);
            }
            if ((null == shareLink || shareLink.equals(""))
                    || (null == shareTitle || shareTitle.equals(""))
                    || (null == shareContent || shareContent.equals(""))) {
                acbr_right1_icon.setVisibility(View.INVISIBLE);
            } else if (!acbr_right_text.isShown()) {
                acbr_right1_icon.setVisibility(View.VISIBLE);
            }

            super.onPageFinished(view, url);
        }
    }

    private final static int FILECHOOSER_RESULTCODE = 1;

    /**
     * 自定义WebChromeClient
     */
    private class SampleWebChromeClient extends WebChromeClient {
        private Bitmap xdefaltvideo;
        private View xprogressvideo;

        @Override
        // 播放网络视频时全屏会被调用的方法
        public void onShowCustomView(View view, CustomViewCallback callback) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            webView.setVisibility(View.GONE);
            // 如果一个视图已经存在，那么立刻终止并新建一个
            if (xCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }
            videoview.addView(view);
            xCustomView = view;
            xCustomViewCallback = callback;
            videoview.setVisibility(View.VISIBLE);
        }

        @Override
        // 视频播放退出全屏会被调用的
        public void onHideCustomView() {

            if (xCustomView == null)// 不是全屏播放状态
                return;
            // Hide the custom view.
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            xCustomView.setVisibility(View.GONE);

            // Remove the custom view from its container.
            videoview.removeView(xCustomView);
            xCustomView = null;
            videoview.setVisibility(View.GONE);
            xCustomViewCallback.onCustomViewHidden();

            webView.setVisibility(View.VISIBLE);

        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {

            super.onProgressChanged(view, newProgress);
            progressDeterminate.setProgress(newProgress);
            if (newProgress >= 100) {
                progressDeterminate.setVisibility(View.GONE);
            }
        }

    }

    /**
     * 返回按钮的操作
     */
    @Override
    public void finishAct() {
        if (marker != null && !marker.equals("") && marker.equals("earn")
                && webView.canGoBack()) {// 表示“赚”活动的界面
            webView.goBack();
            return;
        }
        webView.loadUrl("about:blank");//加载空页面
        FinishA();
    }

    /**
     * webview重新加载
     */
    @Override
    public void reload() {
        webView.reload();
    }

    /**
     * H5界面请求用户登录
     */
    public void upLoginOnAndroid() {
        //判断用户登录状态 ，如果没有登录，则跳转至登录页面
        if (CommonApplication.getInstance().getUseInfoVo() == null) {
            getIntentTool().intent_RouterTo(context, RouterUrl.userinfo_UserLogingRouterUrl);
            return;
        }
        mProjectUtil_presenter_implement.syncCookie(context, url, new ProjectUtil_Presenter_Implement.SyncCookieListener() {
            @Override
            public void onResult(boolean isSucc) {
                if (isSucc) {
                    //将cookie同步到WebView
                    webView.reload();//刷新webview
                }
            }
        });
    }

    /**
     * webview加载
     *
     * @return
     */
    @Override
    public void webViewLoadUrl(final String url) {
        webViewHandle.post(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                webView.loadUrl(url);
            }
        });
    }

    /**
     * 判断是否是全屏
     *
     * @return
     */
    @Override
    public boolean inCustomView() {
        return (xCustomView != null);
    }

    /**
     * 全屏时按返加键执行退出全屏方法
     */
    @Override
    public void hideCustomView() {
        xwebchromeclient.onHideCustomView();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.stopLoading();
            webView.removeAllViews();
            webView.destroy();
        }
        if (uploadBitmap != null) {
            uploadBitmap.recycle();
        }
    }

    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (inCustomView()) {
                hideCustomView();
                return true;
            } else if (webView.canGoBack()) {
                webView.goBack();
                return true;
            } else {
                webView.loadUrl("about:blank");
                FinishA();
            }
        }
        return super.onKeyDown(keyCode, event);
    }*/
}
