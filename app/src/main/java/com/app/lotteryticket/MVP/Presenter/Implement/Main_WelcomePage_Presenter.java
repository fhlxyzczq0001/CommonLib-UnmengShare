package com.app.lotteryticket.MVP.Presenter.Implement;


import com.alibaba.fastjson.JSONObject;
import com.app.common.HttpRequest.HttpPath;
import com.app.common.HttpRequest.ResultListener.ResultListener;
import com.app.common.MVP.Model.Implement.Base_Model_Implement;
import com.app.common.MVP.Model.Interface.Base_Model_Interface;
import com.app.lotteryticket.Base.My_Application;
import com.app.lotteryticket.MVP.Contract.Main_WelcomePage_Contract;
import com.app.lotteryticket.MVP.Model.Bean.WelcomePageBean;
import com.app.lotteryticket.Util.Main_SharePer_SdCard_Info;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ${杨重诚} on 2017/6/2.
 */

public class Main_WelcomePage_Presenter extends Main_WelcomePage_Contract.Presenter {
    Base_Model_Interface mBase_model_implement;//请求网络数据的model实现类
    My_Application mMy_application;
    public Main_WelcomePage_Presenter(){
        this.mBase_model_implement=new Base_Model_Implement();
        mMy_application=My_Application.getInstance();
    }
    /**
     * 获取启动页更新的Params
     * @return
     */
    @Override
    public Map<String, String> getStartPageUpdate_Params() {
        Map<String, String> params = new HashMap<String, String>();
        return params;
    }
    /**
     * 请求启动页更新
     * @param params
     */
    @Override
    public void requestStartPageUpdate(Map<String, String> params) {
        mBase_model_implement.requestData(context, HttpPath.URL_API_STARTPAGEUPDATE, new HashMap<String, String>(), new ResultListener() {
            @Override
            public void onResult(boolean isSucc, String msg, String request) {
                WelcomePageBean welcomePageBean=null;
                if (isSucc) {
                    welcomePageBean = JSONObject.parseObject(request,
                            WelcomePageBean.class);
                    //获取本地存储启动页缓存
                    WelcomePageBean share_PageBean= Main_SharePer_SdCard_Info.sharePre_GetWelcomePageBean();
                    if(null!=share_PageBean){
                        // 获取到的图片路径
                        String start_page_url =share_PageBean.getUrl();
                        // 取得下载路径的文件名和本地图片名称比对，若本地路径中有不下载，没有则执行下载
                        String webFileName =welcomePageBean.getUrl().substring(start_page_url
                                .lastIndexOf("/") + 1);
                        //获取本地文件名
                        String localFileName = start_page_url.substring(start_page_url
                                .lastIndexOf("/") + 1);
                        if (!webFileName.equals(localFileName)) {
                            //启动service下载图片
                            mView.startWelcomePageService(welcomePageBean);
                        }
                    }else{
                        //启动service下载图片
                        mView.startWelcomePageService(welcomePageBean);
                    }
                }
            }
        },false);
    }
    @Override
    public void onDestroy() {
super.onDestroy();
    }
}
