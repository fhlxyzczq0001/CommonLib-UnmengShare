package com.app.common.Public;
/**
 * SharedPreferences存储的key
 * @ClassName: SharedPreferences_Key 
 * @Description: TODO
 * @author: yzc
 * @date: 2016-4-24 上午10:39:31
 */
public class SharedPreferences_Key{
	public static String APP_Global_SharedPreferences="lottery_Global_SharedPreferences";//app全局的SharedPreferences表名

	public static String APP_User_SharedPreferences="lottery_User_SharedPreferences";//app用户信息的SharedPreferences表名

	public static String APP_SdCard_SharedPreferences="lottery_SdCard_SharedPreferences";//SdCard的SharedPreferences表名

	public static String OAUTH_TOKEN="OAUTH_TOKEN";//存放用户oauth_token

	public static String USER_ID="USER_ID";//存放用户id

	public static String USER_NAME_CACHE="usernameCache";//存放所有登录成功的用户名

	public static String WELCOME_PAGE_BEAN = "WelcomePageBean";//启动页bean对象

	public static String FIRSTINSTALL = "FirstInstall";//记录是否第一次启动程序public static String USER_AUTHENTICATION_CODE_TIME="USER_AUTHENCATION_CODE_TIME";//存放语音认证页面获取验证码开始倒计时时间

	public static String IS_UPDATE="IS_UPDATE";//存放是否更新的状态

	public static String COOKIE_CACHE="COOKIE_CACHE";//存放cookie缓存

 }
