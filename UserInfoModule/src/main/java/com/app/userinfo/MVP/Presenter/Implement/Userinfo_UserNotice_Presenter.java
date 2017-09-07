package com.app.userinfo.MVP.Presenter.Implement;


import com.app.common.HttpRequest.HttpPath;
import com.app.common.HttpRequest.HttpRequestMethod;
import com.app.common.HttpRequest.ResultListener.ResultDataListener;
import com.app.common.MVP.Model.Bean.Request_Bean;
import com.app.common.MVP.Model.Implement.Base_HttpRequest_Implement;
import com.app.common.MVP.Model.Interface.Base_HttpRequest_Interface;
import com.app.userinfo.MVP.Contract.Userinfo_UserNotice_Contract;
import com.app.userinfo.MVP.Model.Bean.Userinfo_NoticeBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户通知消息
 * Created by ${杨重诚} on 2017/6/2.
 */

public class Userinfo_UserNotice_Presenter extends Userinfo_UserNotice_Contract.Presenter {
    Base_HttpRequest_Interface mBase_httpRequest_interface;//请求网络数据的model实现类

    public Userinfo_UserNotice_Presenter() {
        this.mBase_httpRequest_interface = new Base_HttpRequest_Implement();
    }

    /**
     * 请求最新公告数据
     */
    @Override
    public void requestNotice() {
        requestNotice(getNotice_Params());
    }

    /**
     * 获取最新公告数据的Params
     *
     * @return
     */
    public Map<String, String> getNotice_Params() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("ACT","GetMsg");
        return params;
    }

    /**
     * 请求最新公告数据
     *
     * @param params
     */
    public void requestNotice(Map<String, String> params) {
        mBase_httpRequest_interface.requestData(context, HttpPath.URL_API_NEW_NOTICE, params, new ResultDataListener() {
            @Override
            public void onResult(boolean isSucc, String msg, Request_Bean request_bean) {
                if (isSucc) {
                    if (request_bean.getData() != null) {
                        Userinfo_NoticeBean userinfo_noticeBean=new Userinfo_NoticeBean();
                        userinfo_noticeBean.setTitle(request_bean.getData().toString());
                        userinfo_noticeBean.setContent(request_bean.getData().toString());
                        List<Userinfo_NoticeBean> notice_array=new ArrayList<Userinfo_NoticeBean>();
                        notice_array.add(userinfo_noticeBean);
                        //设置最新公告列表
                        mView.setNoticeList(notice_array);
                    }
                } else {
                    // 请求失败
                }
            }
        }, HttpRequestMethod.POST);

       /* Userinfo_NoticeBean userinfo_noticeBean=new Userinfo_NoticeBean();
        userinfo_noticeBean.setId(69);
        userinfo_noticeBean.setTitle("善益博软上线公告");
        userinfo_noticeBean.setPublishTime(1502782867000L);
        userinfo_noticeBean.setContent("善益博软上线公告\n善益博软上线公告\t\t善益博软上线公告善益博软上线公告善益博软上线公告善益博软上线公告善益博软上线公告");

        List<Userinfo_NoticeBean> list = new ArrayList<Userinfo_NoticeBean>();
        list.add(userinfo_noticeBean);
        list.add(userinfo_noticeBean);
        list.add(userinfo_noticeBean);
        list.add(userinfo_noticeBean);
        list.add(userinfo_noticeBean);
        //设置最新公告列表
        mView.setNoticeList(list);*/
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
