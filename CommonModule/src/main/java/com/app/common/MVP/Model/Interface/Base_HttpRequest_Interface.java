package com.app.common.MVP.Model.Interface;

import android.content.Context;

import com.app.common.HttpRequest.HttpRequestMethod;
import com.app.common.HttpRequest.ResultListener.ResultDataListener;
import com.app.common.MVP.Model.Implement.Base_HttpRequest_Implement;

import java.util.Map;

/**
 * 请求网络数据的基础类接口
 * @ClassName: com.ygworld.MVP.Model.Interface
 * @author: Administrator 杨重诚
 * @date: 2016/8/23:17:15
 */
public interface Base_HttpRequest_Interface {
    /**post请求
     * 请求网络数据
     * @param context 上下文对象
     * @param URL 请求链接
     * @param params 请求参数
     * @param resultListener 回调
     */
    public void requestData(Context context, String URL, Map<String, String> params, ResultDataListener resultListener, HttpRequestMethod requestMethod);

    /**post请求
     * 请求网络数据
     * @param context 上下文对象
     * @param URL 请求链接
     * @param params 请求参数
     * @param resultListener 回调
     * @param isLoadingDialog 是否加载动画
     */
    public void requestData(Context context, String URL, Map<String, String> params, ResultDataListener resultListener, boolean isLoadingDialog, HttpRequestMethod requestMethod);

    /**get请求
     * 请求网络数据
     * @param context 上下文对象
     * @param URL 请求链接
     * @param resultListener 回调
     */
    public void requestData(Context context, String URL, ResultDataListener resultListener);

    /**get请求
     * 请求网络数据
     * @param context 上下文对象
     * @param URL 请求链接
     * @param resultListener 回调
     * @param isLoadingDialog 是否加载动画
     */
    public void requestData(Context context, String URL, ResultDataListener resultListener, boolean isLoadingDialog);
    /**
     * 下载文件
     * @param saveFilePath 保存路径
     * @param uploadFilePath 下载路径
     * @param resultListener 回调
     */
    public void FileDownloader(String saveFilePath, String uploadFilePath, ResultDataListener resultListener);
    /**
     * 下载文件
     * @param saveFilePath 保存路径
     * @param uploadFilePath 下载路径
     * @param resultListener 回调
     */
    public void FileDownloader(String saveFilePath, String uploadFilePath, ResultDataListener resultListener, Base_HttpRequest_Implement.ProgressResultListener onProgressChange);

}
