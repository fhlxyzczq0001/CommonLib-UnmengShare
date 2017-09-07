package com.app.lotteryticket.MVP.Presenter.Implement;


import com.alibaba.fastjson.JSON;
import com.app.common.HttpRequest.HttpPath;
import com.app.common.HttpRequest.HttpRequestMethod;
import com.app.common.HttpRequest.ResultListener.ResultDataListener;
import com.app.common.MVP.Model.Bean.Request_Bean;
import com.app.common.MVP.Model.Implement.Base_HttpRequest_Implement;
import com.app.common.MVP.Model.Interface.Base_HttpRequest_Interface;
import com.app.common.Util.ToastUtil;
import com.app.lotteryticket.MVP.Contract.Main_HistoryPrize_Contract;
import com.app.lotteryticket.MVP.Model.Bean.Main_HistoryPrizeBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 历史开奖
 * Created by ${杨重诚} on 2017/6/2.
 */

public class Main_HistoryPrize_Presenter extends Main_HistoryPrize_Contract.Presenter {
    Base_HttpRequest_Interface mMain_base_httpRequest_interface;
    private int countHttpMethod = 0;//总共有多少个请求网络数据的方法
    private int indexHttpMethod = 0;//记录当前执行到第几个请求方法
    private int classGoods_page = 1;//分页请求，默认第一页
    private List<Main_HistoryPrizeBean> mMain_historyPrizeBeanList = new ArrayList<>();//存放数据的数据源
    public Main_HistoryPrize_Presenter() {
        mMain_base_httpRequest_interface=new Base_HttpRequest_Implement();
    }
    /**
     * 初始化参数
     */
    @Override
    public void initData(int countHttpMethod) {
        this.countHttpMethod = countHttpMethod;
        indexHttpMethod = 0;
    }
    /**
     * 关闭刷新控件
     */
    @Override
    public void closeRefresh() {
        indexHttpMethod++;
        if (indexHttpMethod >= countHttpMethod) {
            indexHttpMethod = 0;
            mView.closeRefresh();
        }
    }
    /**
     * 获取历史开奖数据
     */
    @Override
    public void requestHistoryPrizeData() {
        requestHistoryPrizeData(getHistoryPrize_Params());
    }
    /**
     *获取历史开奖数据的Params
     * @return
     */
    public Map<String, String> getHistoryPrize_Params() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("ACT","GetResultPage");
        params.put("page",Integer.toString(classGoods_page));
        return params;
    }
    /**
     * 获取历史开奖数据
     * @param params
     */
    public void requestHistoryPrizeData(Map<String, String> params) {
        mMain_base_httpRequest_interface.requestData(context, HttpPath.URL_API_GET_HISTORY_PRIZE_RECORD, params, new ResultDataListener() {
            @Override
            public void onResult(boolean isSucc, String msg, Request_Bean request_bean) {
                if (isSucc) {
                    if(request_bean.getData()!=null){
                        List<Main_HistoryPrizeBean> main_historyPrizeBeanList= JSON.parseArray(request_bean.getData().toString(),
                                Main_HistoryPrizeBean.class);
                        if(classGoods_page == 1){
                            String periods=main_historyPrizeBeanList.get(0).getPeriods();
                            String prizeDtae="20"+periods.substring(0,2)+"-"+periods.substring(2,4)+"-"+periods.substring(4,6);
                            mView.setPrizeDate(prizeDtae);
                        }
                        setHistoryPrizeData(main_historyPrizeBeanList);
                    }else {
                       /* Main_HistoryPrize_ListBean main_historyPrize_listBean=new Main_HistoryPrize_ListBean();
                        main_historyPrize_listBean.setPrizeDtae(Long.toString(System.currentTimeMillis()));
                        List<Main_HistoryPrizeBean> main_historyPrizeBeanList=new ArrayList<Main_HistoryPrizeBean>();
                        for(int i=0;i<20;i++){
                            Main_HistoryPrizeBean prizeBean=new Main_HistoryPrizeBean();
                            prizeBean.setPeriods(Integer.toString(67-i));
                            prizeBean.setTime(Long.toString(System.currentTimeMillis()));
                            prizeBean.setResults(Integer.toString(59312-i*2));
                            main_historyPrizeBeanList.add(prizeBean);
                        }
                        main_historyPrize_listBean.setList(main_historyPrizeBeanList);

                        if(classGoods_page == 1){
                            mView.setPrizeDate(main_historyPrize_listBean.getPrizeDtae());
                        }
                        setHistoryPrizeData(main_historyPrize_listBean.getList());*/
                    }

                }else {
                    // 请求失败
                    ToastUtil.ErrorImageToast(context,msg);
                }
                closeRefresh();
            }
        }, HttpRequestMethod.POST);
    }
    /**
     * 设置历史开奖
     * @param beans
     */
    @Override
    public void setHistoryPrizeData(List<Main_HistoryPrizeBean> beans) {
        if (beans != null && beans.size() > 0) {
            //------------判断是下拉刷新还是上拉加载----------------------------
            if (classGoods_page == 1) {
                mMain_historyPrizeBeanList.clear();
                mMain_historyPrizeBeanList = beans;
            } else {
                mMain_historyPrizeBeanList.addAll(beans);
            }
        } else if(classGoods_page==1&&beans != null && beans.size() <= 0){
            mMain_historyPrizeBeanList.clear();
        }
        //调用View的接口，设置列表
        mView.setHistoryPrizeList(mMain_historyPrizeBeanList);
    }
    /**
     * 设置页数
     * @param page
     */
    @Override
    public void setClassGoods_page(int page) {
        classGoods_page=page;
    }
    /**
     * 获取页数
     * @return
     */
    @Override
    public int getClassGoods_page() {
        return classGoods_page;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
