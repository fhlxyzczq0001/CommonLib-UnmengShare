package com.app.common.MVP.Model.Implement;

import android.content.Context;

import com.app.common.Base.CommonApplication;
import com.app.common.BuildConfig;
import com.app.common.HttpRequest.CustomRequestResponseBeanDataManager;
import com.app.common.HttpRequest.HttpManager.HttpDownManager;
import com.app.common.HttpRequest.HttpPath;
import com.app.common.HttpRequest.HttpRequestMethod;
import com.app.common.HttpRequest.ResultListener.ResultDataListener;
import com.app.common.MVP.Model.Interface.Base_HttpRequest_Interface;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.downlaod.DownInfo;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.RetryWhenNetworkException;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpDownOnNextListener;

import java.net.URL;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @ClassName: com.ygworld.MVP.Model.Implement
 * @author: Administrator 杨重诚
 * @date: 2016/8/23:17:17
 */
public class Base_HttpRequest_Implement implements Base_HttpRequest_Interface {
    public boolean isOpenPost=true;//强制开启post请求
    public Base_HttpRequest_Implement(){
        if(BuildConfig.IsHttpPost){
            isOpenPost=true;
        }else {
            isOpenPost=false;
        }
    }
    /**
     * 请求网络数据
     * @param context 上下文对象
     * @param URL 请求链接
     * @param params 请求参数
     * @param resultListener 回调
     */
    @Override
    public void requestData(final Context context, final String URL, final Map<String, String> params, final ResultDataListener resultListener, final HttpRequestMethod method) {
        if(BuildConfig.IsRequestHttpDns){
            //Rxjava请求模式
            RXjavaRequest(context, new HttpDnsListener() {
                @Override
                public void requestListener(boolean isSucc, final String newUrl) {
                    if (isSucc){
                        if(isOpenPost){
                            //强制开启Post请求
                            new CustomRequestResponseBeanDataManager().requestPost(context,getURL(newUrl,URL),
                                    params, resultListener,true);
                        }else {
                            if(method== HttpRequestMethod.POST){
                                new CustomRequestResponseBeanDataManager().requestPost(context,getURL(newUrl,URL),
                                        params, resultListener,true);
                            }else {
                                new CustomRequestResponseBeanDataManager().requestGet(context,getURL(newUrl,URL),
                                        resultListener,true);
                            }
                        }
                    }
                }
            });
        }else {
            if(isOpenPost){
                //强制开启Post请求
                new CustomRequestResponseBeanDataManager().requestPost(context,getURL(HttpPath.HTTP_HOST_PROJECT,URL),
                        params, resultListener,true);
            }else {
                if(method==HttpRequestMethod.POST){
                    new CustomRequestResponseBeanDataManager().requestPost(context,getURL(HttpPath.HTTP_HOST_PROJECT,URL),
                            params, resultListener,true);
                }else {
                    new CustomRequestResponseBeanDataManager().requestGet(context,getURL(HttpPath.HTTP_HOST_PROJECT,URL),
                            resultListener,true);
                }
            }
        }
    }

    /**
     * 请求网络数据
     * @param context 上下文对象
     * @param URL 请求链接
     * @param params 请求参数
     * @param resultListener 回调
     * @param isLoadingDialog 是否加载动画
     */
    @Override
    public void requestData(final Context context,final String URL, final Map<String, String> params, final ResultDataListener resultListener,final boolean isLoadingDialog,final HttpRequestMethod method) {
        if(BuildConfig.IsRequestHttpDns){
            //Rxjava请求模式
            RXjavaRequest(context, new HttpDnsListener() {
                @Override
                public void requestListener(boolean isSucc, final String newUrl) {
                    if (isSucc){
                        if(isOpenPost){
                            //强制开启Post请求
                            new CustomRequestResponseBeanDataManager().requestPost(context,getURL(newUrl,URL),
                                    params, resultListener,isLoadingDialog);
                        }else {
                            if(method==HttpRequestMethod.POST){
                                new CustomRequestResponseBeanDataManager().requestPost(context,getURL(newUrl,URL),
                                        params, resultListener,isLoadingDialog);
                            }else {
                                new CustomRequestResponseBeanDataManager().requestGet(context,getURL(newUrl,URL),
                                        resultListener,isLoadingDialog);
                            }
                        }
                    }
                }
            });
        }else {
            if(isOpenPost){
                //强制开启Post请求
                new CustomRequestResponseBeanDataManager().requestPost(context,getURL(HttpPath.HTTP_HOST_PROJECT,URL),
                        params, resultListener,isLoadingDialog);
            }else {
                if(method==HttpRequestMethod.POST){
                    new CustomRequestResponseBeanDataManager().requestPost(context,getURL(HttpPath.HTTP_HOST_PROJECT,URL),
                            params, resultListener,isLoadingDialog);
                }else {
                    new CustomRequestResponseBeanDataManager().requestGet(context,getURL(HttpPath.HTTP_HOST_PROJECT,URL),
                            resultListener,isLoadingDialog);
                }
            }
        }
    }

    /**
     * 获取URL
     * @param newUrl
     * @param oldUrl
     */
    private String getURL(String newUrl,String oldUrl){
        return newUrl+oldUrl;
    }
    //---------------------------------get请求-------------------------------------
    /**
     * 请求网络数据
     * @param context 上下文对象
     * @param URL 请求链接
     * @param resultListener 回调
     */
    @Override
    public void requestData(final Context context, final String URL,final ResultDataListener resultListener) {
        new CustomRequestResponseBeanDataManager().requestGet(context,URL,resultListener,true);
    }

    /**
     * 请求网络数据
     * @param context 上下文对象
     * @param URL 请求链接
     * @param resultListener 回调
     * @param isLoadingDialog 是否加载动画
     */
    @Override
    public void requestData(final Context context, final String URL,final ResultDataListener resultListener,final boolean isLoadingDialog) {
        new CustomRequestResponseBeanDataManager().requestGet(context,URL,resultListener,isLoadingDialog);
    }
    /**
     * 下载文件
     * @param saveFilePath 保存路径
     * @param uploadFilePath 下载路径
     * @param resultListener 回调
     */
    @Override
    public void FileDownloader(String saveFilePath, String uploadFilePath,final ResultDataListener resultListener) {
        HttpDownOnNextListener httpDownOnNextListener=new HttpDownOnNextListener() {
            @Override
            public void onNext(Object o) {
                resultListener.onResult(true,"下载成功",null);
            }
            @Override
            public void onStart() {

            }
            @Override
            public void onComplete() {

            }
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                resultListener.onResult(false,"下载失败",null);
            }
            @Override
            public void updateProgress(long readLength, long countLength) {

            }
        };
        DownInfo downInfo=new DownInfo(uploadFilePath,saveFilePath,httpDownOnNextListener);
        new HttpDownManager().startDown(downInfo);

    }

    /**
     * 下载文件的回调方法
     */
    public interface ProgressResultListener {

        /**
         * 下载进度
         *
         * @param fileSize
         * @param downloadedSize
         */
        public void onProgressChange(long fileSize, long downloadedSize);
    }

    /**
     * 下载文件
     * @param saveFilePath 保存路径
     * @param uploadFilePath 下载路径
     * @param resultListener 回调
     */
    @Override
    public void FileDownloader(String saveFilePath, String uploadFilePath, final ResultDataListener resultListener, final ProgressResultListener onProgressChange) {
        HttpDownOnNextListener httpDownOnNextListener=new HttpDownOnNextListener() {
            @Override
            public void onNext(Object o) {
                resultListener.onResult(true,"下载成功",null);
            }
            @Override
            public void onStart() {

            }
            @Override
            public void onComplete() {

            }
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                resultListener.onResult(false,"下载失败",null);
            }
            @Override
            public void updateProgress(long readLength, long countLength) {
                onProgressChange.onProgressChange(countLength,readLength);
            }
        };
        DownInfo downInfo=new DownInfo(uploadFilePath,saveFilePath,httpDownOnNextListener);
        new HttpDownManager().startDown(downInfo);
    }

    /**
     * 获取HttpDns的回调方法
     */
    public interface HttpDnsListener {
        public void requestListener(boolean isSucc, String newUrl);

    }
    /**
     * 获取HttpDns——observable模式
     * @param context
     * @param originalUrl
     * @param subscriber
     */
    public  void getHttpDnsIp(Context context,String originalUrl, Subscriber<? super String> subscriber) {
        String newUrl=originalUrl;
       /* try {
            URL url = new URL(originalUrl);
            // 同步接口获取IP
            String ip = CommonApplication.getInstance().getHttpDnsService().getIpByHostAsync(url.getHost());
            if (ip != null&&!ip.isEmpty()) {
                // 通过HTTPDNS获取IP成功，进行URL替换和HOST头设置
                //L.e("通过HTTPDNS获取IP成功，进行URL替换和HOST头设置", "Get IP: " + ip + " for host: " + url.getHost() + " from HTTPDNS successfully!");
                newUrl = originalUrl.replaceFirst(url.getHost(), ip);
                //L.e("newUrl:", newUrl);
                //L.e("****************", "****************");

                //=======================获取成功，通知订阅者===================
                subscriber.onNext(newUrl);
                subscriber.onCompleted();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //===============================获取异常，发送自定义异常事件，调起重试机制========================
        subscriber.onError(new Exception(context.getResources().getString(R.string.HTTPDNS_Exception)));*/
        //注：测试时放开下面，注释掉上面，防止HTTPDNS解析失败调起5次重试机制
        subscriber.onNext(newUrl);
    }

    /**
     * RXjava请求模式
     * @param context
     * @param httpDnsListener
     */
    private void RXjavaRequest(final Context context,final HttpDnsListener httpDnsListener){
        //创建被观察者，获取HttpDns
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                getHttpDnsIp(context, HttpPath.HTTP_HOST,subscriber);
            }
        }).retryWhen(new RetryWhenNetworkException())//重试机制
                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        //结束订阅
                    }
                    @Override
                    public void onError(Throwable e) {
                        //如果获取IP失败，则用服务器域名访问
                        try {
                            httpDnsListener.requestListener(true,HttpPath.HTTP_HOST+HttpPath.PROJECT);
                        }catch (Exception exception){
                            exception.printStackTrace();
                        }
                    }
                    @Override
                    public void onNext(String newUrl) {
                        //如果获取IP成功，拼接IP地址继续执行
                        httpDnsListener.requestListener(true,newUrl+HttpPath.PROJECT);
                    }
                });
    }
}
