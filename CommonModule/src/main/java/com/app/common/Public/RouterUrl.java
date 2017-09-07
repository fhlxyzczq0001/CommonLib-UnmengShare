package com.app.common.Public;

/**路由机制URL
 * Created by Administrator on 2017/6/3.
 */

public class RouterUrl {
    //前缀
    public final static String RouterHTTP="lotterRoute://";
    //主app项目Host
    final static String mainAppHost=RouterHTTP+"lotter/";
    //主页面
    public final static String mainMainActivityRouterUrl =mainAppHost+"mainActivity";
    //webView页面
    public final static String mainWebViewRouterUrl =mainAppHost+"interactionWebView";
    //启动页面
    public final static String mainWelcomePageRouterUrl =mainAppHost+"welcomePage";
    //引导页面
    public final static String mainGuideRouterUrl =mainAppHost+"guide";
    //网址页面
    public final static String mainURLViewRouterUrl =mainAppHost+"urlView";
    //关于我们
    public final static String mainAboutUsRouterUrl =mainAppHost+"aboutUs";
    //历史开奖
    public final static String mainHistoryPrizeRouterUrl =mainAppHost+"historyPrize";
    //计划中心
    public final static String mainPlanCenterRouterUrl =mainAppHost+"planCenter";
    //计划内容
    public final static String mainPlanContentRouterUrl =mainAppHost+"planContent";
//======================================================================================================
    //用户包名
    final static String userInfoAppHost=RouterHTTP+"userInfo/";
    //登陆界面
    public final static String userinfo_UserLogingRouterUrl =userInfoAppHost+"log";
    //忘记密码界面
    public final static String userinfo_ForgetPasswordRouterUrl =userInfoAppHost+"forgetPassword";
    //注册界面
    public final static String userinfo_UserRegisterRouterUrl =userInfoAppHost+"userRegister";
    //修改密码页面
    public final static String userinfo_UserModifyPasswordRouterUrl =userInfoAppHost+"userModifyPassword";
    //公告列表页面
    public final static String userinfo_NoticeListRouterUrl =userInfoAppHost+"noticeListActivity";
    //用户通知消息页面
    public final static String userinfo_UserNoticeRouterUrl =userInfoAppHost+"uerNotice";
    //==============================拦截器====================================
    //用户信息界面拦截器
    public final static String interceptor_UserInfo_RouterUrl =RouterHTTP+"userInfo";

}
