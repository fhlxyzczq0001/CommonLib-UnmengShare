package com.app.common.MVP.Model.Interface;

import android.content.Context;

import com.app.common.HttpRequest.ResultListener.ResultListener;
import com.app.common.MVP.Model.Implement.Base_Model_Implement;

import java.util.Map;

/**
 * 请求网络数据的基础类接口
 * @ClassName: com.ygworld.MVP.Model.Interface
 * @author: Administrator 杨重诚
 * @date: 2016/8/23:17:15
 */
public interface Base_Model_Interface {
    /**post请求
     * 请求网络数据
     * @param context 上下文对象
     * @param URL 请求链接
     * @param params 请求参数
     * @param resultListener 回调
     */
    public void requestData(Context context, String URL, Map<String, String> params, ResultListener resultListener);

    /**post请求
     * 请求网络数据
     * @param context 上下文对象
     * @param URL 请求链接
     * @param params 请求参数
     * @param resultListener 回调
     * @param isLoadingDialog 是否加载动画
     */
    public void requestData(Context context, String URL, Map<String, String> params, ResultListener resultListener, boolean isLoadingDialog);

    /**post请求
     * 请求网络数据
     * @param context 上下文对象
     * @param URL 请求链接
     * @param params 请求参数
     * @param resultListener 回调
     * @param isLoadingDialog 是否加载动画
     */
    public void refreshToken(Context context, String URL, Map<String, String> params, ResultListener resultListener, boolean isLoadingDialog);


    /**get请求
     * 请求网络数据
     * @param context 上下文对象
     * @param URL 请求链接
     * @param resultListener 回调
     */
    public void requestData(Context context, String URL, ResultListener resultListener);

    /**get请求
     * 请求网络数据
     * @param context 上下文对象
     * @param URL 请求链接
     * @param resultListener 回调
     * @param isLoadingDialog 是否加载动画
     */
    public void requestData(Context context, String URL, ResultListener resultListener, boolean isLoadingDialog);
    /**
     * 下载文件
     * @param saveFilePath 保存路径
     * @param uploadFilePath 下载路径
     * @param resultListener 回调
     */
    public void FileDownloader(String saveFilePath, String uploadFilePath, ResultListener resultListener);
    /**
     * 下载文件
     * @param saveFilePath 保存路径
     * @param uploadFilePath 下载路径
     * @param resultListener 回调
     */
    public void FileDownloader(String saveFilePath, String uploadFilePath, ResultListener resultListener, Base_Model_Implement.ProgressResultListener onProgressChange);

    /**
     * 判断autoToke是否超时
     * @param refreshTokenFinishListener
     */
    public void refreshToken(Context context, final Base_Model_Implement.RefreshTokenFinishListener refreshTokenFinishListener);

}
