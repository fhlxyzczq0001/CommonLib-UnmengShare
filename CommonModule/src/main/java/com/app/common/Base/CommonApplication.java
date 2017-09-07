package com.app.common.Base;

import android.app.Application;

import com.app.common.HttpRequest.Interceptor.LoggerInterceptor;
import com.app.common.HttpRequest.Interceptor.RequestEncapsulationInterceptor;
import com.app.common.MVP.Model.Bean.UserInfoBean;
import com.app.common.Public.SharedPreferences_Key;
import com.app.common.Util.FileUtils;
import com.app.common.Util.SharePre;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.Api.BaseApi;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.RxRetrofitApp;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.cookie.CacheInterceptor;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.https.HttpsUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**公告module的Application
 * Created by ${杨重诚} on 2017/5/31.
 */

public class CommonApplication {
    public static Application mApplication;
    public SharePre userSharePre;//用户信息SharePre
    private SharePre globalSharePre;//全局SharePre
    private SharePre sdCardSharePre;//本地存储信息SharePre
    // 用户信息
    private UserInfoBean useInfoVo = null;
    private boolean mainAppIsStart=false;//主App是否启动
    /*单利对象*/
    private volatile static Retrofit myRetrofit;
    //存放dns和域名对应关系的map
    public static Map<String ,String> httpdnsHosMap;//key:ip+项目名，截取前4个/，value 域名

    public static CommonApplication initCommonApplication(Application application){
        mApplication=application;
        mCommonApplication=getInstance();
        mCommonApplication.getUserSharedPreferences();
        httpdnsHosMap=new HashMap<>();
        return mCommonApplication;
    }

    static CommonApplication mCommonApplication;
    public static CommonApplication getInstance(){
        if(mCommonApplication==null){
            //第一次check，避免不必要的同步
            synchronized (CommonApplication.class){//同步
                if(mCommonApplication==null){
                    //第二次check，保证线程安全
                    mCommonApplication=new CommonApplication();
                }
            }
        }
        return mCommonApplication;
    }
    public static Application getApplication(){
       return mApplication;
   }

    /**
     * 获取用户SharePre
     *
     * @return
     */
    public  SharePre getUserSharedPreferences() {
        if (userSharePre == null) {
            userSharePre = new SharePre(mApplication,
                    SharedPreferences_Key.APP_User_SharedPreferences, mApplication.MODE_APPEND);
        }
        return userSharePre;
    }
    /**
     * 获取全局SharePre
     *
     * @return
     */
    public SharePre getGlobalSharedPreferences() {
        if (globalSharePre == null) {
            globalSharePre = new SharePre(mApplication,
                    SharedPreferences_Key.APP_Global_SharedPreferences, mApplication.MODE_APPEND);
        }
        return globalSharePre;
    }
    /**
     * 获取sdcard的SharePre
     *
     * @return
     */
    public SharePre getSdCardSharedPreferences() {
        if (sdCardSharePre == null) {
            sdCardSharePre = new SharePre(mApplication,
                    SharedPreferences_Key.APP_SdCard_SharedPreferences,  mApplication.MODE_APPEND);
        }
        return sdCardSharePre;
    }

    // 取得用户信息
    public UserInfoBean getUseInfoVo() {
        if (useInfoVo != null) {
            return useInfoVo;
        }
        return null;
    }

    // 设置用户信息
    public void setUseInfoVo(UserInfoBean useInfoVo) {
        this.useInfoVo = useInfoVo;
    }

    /**
     * 获取单例Retrofit
     * @return
     */
    public static Retrofit getRetrofit(BaseApi basePar) {
        if (myRetrofit == null) {
            synchronized (mCommonApplication.buildRetrofit(basePar)) {
                if (myRetrofit == null) {
                    mCommonApplication.buildRetrofit(basePar);
                }
            }
        }
        return myRetrofit;
    }

    /**
     * 初始化Retrofit
     * @param basePar
     * @return
     */
    private Retrofit buildRetrofit(BaseApi basePar){
        RxRetrofitApp.init(mApplication);
        // 缓存 http://www.jianshu.com/p/93153b34310e
        File cacheFile = new File(FileUtils.getAppCacheDir(mApplication), "/NetCache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        //==============cookie缓存配置================================
        //ClearableCookieJar cookieJar1 = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(Second_Application.getInstance().getApplicationContext()));
        //CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));

        //===================https 设置可访问所有的https网站=============================
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        //手动创建一个OkHttpClient并设置超时时间缓存等设置
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(basePar.getConnectionTime(), TimeUnit.SECONDS)//请求超时时间
                .readTimeout(basePar.getReadTimeout(), TimeUnit.SECONDS)//读取超时时间
                .writeTimeout(basePar.getWriteTimeout(), TimeUnit.SECONDS)//写入超时时间
                .retryOnConnectionFailure(true)//超时错误重连
                .cache(cache)//缓存设置
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                /*.cookieJar(new CookiesManager(mApp,HttpPath.HOSTS))*///cookie自动管理
                .addInterceptor(new RequestEncapsulationInterceptor())//请求封装等过滤器
                .addInterceptor(new LoggerInterceptor("TAG",true))//log过滤器
                .addInterceptor(new CacheInterceptor())//get请求缓存拦截器
                .followRedirects(false)  //禁制OkHttp的重定向操作，我们自己处理重定向
                .followSslRedirects(false);

        /*创建retrofit对象*/
        myRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(basePar.getBaseUrl())
                .build();
        return myRetrofit;
    }
    /**
     * 设置主App是否启动
     */
    public void setMainAppIsStart(){
        boolean mainAppIsStart=getMainAppIsStart();
        if(!mainAppIsStart){
            this.mainAppIsStart=true;
        }
    }

    /**
     * 获取主App是否启动
     * @return
     */
    public boolean getMainAppIsStart(){
        return mainAppIsStart;
    }
}
