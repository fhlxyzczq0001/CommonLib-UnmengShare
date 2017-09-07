package com.app.common.HttpRequest;

/**
 * 网络请求路径
 * 
 * @ClassName: HttpPath
 * @Description: TODO
 * @author: yzc
 * @date: 2016-4-24 上午10:38:33
 */
public class HttpPath {
//========================云购全球主项目===============================
//连接前缀
private static String HTTP = "http://";
	//域名
    public static String HOSTS = "mamenggou.com:880";
//      public static String HOSTS = "apk.dadetongkeji.net.cn";
	//测试域名
    //public static String HOSTS = "ygqq-apk.dadetongkeji.net.cn";
	//后台项目名
	public static String PROJECT = "/GetJson.aspx";
	//整体的域名地址
	public static String HTTP_HOST = HTTP + HOSTS;
	//整体后台路径
	public static String HTTP_HOST_PROJECT = HTTP_HOST + PROJECT;
	/**
	 * 请求用户信息
	 */
	public static final String URL_API_USER_INFO = "";
	/**
	 * 检测更新
	 */
	public static final String CHECK_UPDATE = "checkUpdate.do";
	/**
	 * 维护界面
	 */
	public static final String SERVER_STATUS = "http://update.ygqq.com/ServerStatus.json";
	/**
	 * 请求启动页更新
	 */
	public static final String URL_API_STARTPAGEUPDATE = "start_page.do";
	/**
	 * 最新公告
	 */
	public static final String URL_API_NEW_NOTICE = "";
	/**
	 * 获取历史开奖记录
	 */
	public static final String URL_API_GET_HISTORY_PRIZE_RECORD = "";
	/**
	 * 请求退出登录
	 */
	public static final String URL_API_LOGING_OUT = "";
	/**
	 * 获取计划中心数据
	 */
	public static final String URL_API_GET_PLAN_CENTER = "";
	/**
	 * 获取计划内容数据
	 */
	public static final String URL_API_GET_PLAN_CONTENT = "";
	/**
	 * 修改密码
	 */
	public static final String URL_API_USER_MODIFY_PASS = "";
	/**
	 * 个人中心
	 */
	public static final String URL_API_USER_CENTER_INFO = "";
	/**
	 * 获取开奖结果
	 */
	public static final String URL_API_LOTTERY_RESULTS = "";
}
