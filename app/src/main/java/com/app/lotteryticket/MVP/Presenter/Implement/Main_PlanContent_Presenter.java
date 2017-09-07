package com.app.lotteryticket.MVP.Presenter.Implement;


import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.app.common.HttpRequest.HttpPath;
import com.app.common.HttpRequest.HttpRequestMethod;
import com.app.common.HttpRequest.ResultListener.ResultDataListener;
import com.app.common.MVP.Model.Bean.Request_Bean;
import com.app.common.MVP.Model.Implement.Base_HttpRequest_Implement;
import com.app.common.MVP.Model.Interface.Base_HttpRequest_Interface;
import com.app.lotteryticket.MVP.Contract.Main_PlanContent_Contract;
import com.app.lotteryticket.MVP.Model.Bean.Main_PrizeCaseBean;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 计划内容
 * Created by ${杨重诚} on 2017/6/2.
 */

public class Main_PlanContent_Presenter extends Main_PlanContent_Contract.Presenter {
    Base_HttpRequest_Interface mMain_base_httpRequest_interface;
    private int countHttpMethod = 0;//总共有多少个请求网络数据的方法
    private int indexHttpMethod = 0;//记录当前执行到第几个请求方法
    private int classGoods_page = 1;//分页请求，默认第一页
    List<String> planContentList;//数据源
    String type="5";//标识是3个号码还是5个
    String plan="jh1";//计划几
    String planContents="";//计划内容
    public Main_PlanContent_Presenter() {
        mMain_base_httpRequest_interface=new Base_HttpRequest_Implement();
    }
    /**
     * 获取页面传值
     * @param mBundle
     */
    @Override
    public void getBundleValues(Bundle mBundle){
        type=mBundle.getString("type","5");
        plan=mBundle.getString("plan","jh1");
    };
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
     * 获取计划内容数据
     */
    @Override
    public void requestPlanContentData() {
        //请求最新期数
        requestPrizeCaseData(getPrizeCase_Params());
    }

    /**
     *获取计划内容数据的Params
     * @return
     */
    public Map<String, String> getPlanContent_Params(String qh,String plan) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("ACT","GetPlan");
        params.put("qh",qh);
        params.put("plan",plan);
        return params;
    }
    /**
     * 获取计划内容数据
     * @param params
     */
    public void requestPlanContentData(Map<String, String> params) {
        mMain_base_httpRequest_interface.requestData(context, HttpPath.URL_API_GET_PLAN_CONTENT, params, new ResultDataListener() {
            @Override
            public void onResult(boolean isSucc, String msg, Request_Bean request_bean) {
                if (isSucc) {
                    if(request_bean.getData()!=null){
                        planContents=request_bean.getData().toString();
                        planContentList= Arrays.asList(planContents.split(" "));
                        setPlanContentData(planContentList);
                    }else {
                        planContentList=new ArrayList<String>();
                        for(int i=0;i<10000;i++){
                            if(type.contains("3")){
                                planContentList.add(Integer.toString(24875+i).substring(2,5));
                            }else {
                                planContentList.add(Integer.toString(24875+i));
                            }
                        }
                        setPlanContentData(planContentList);
                    }

                }else {
                    // 请求失败
                }
                closeRefresh();
            }
        }, HttpRequestMethod.POST);
    }
    /**
     * 获取开奖情况说明的Params
     * @return
     */
    public Map<String, String> getPrizeCase_Params() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("ACT","GetResult");
        params.put("num","1");
        return params;
    }
    /**
     * 获取开奖情况说明数据
     * @param params
     */
    public void requestPrizeCaseData(Map<String, String> params) {
        mMain_base_httpRequest_interface.requestData(context, HttpPath.URL_API_LOTTERY_RESULTS, params, new ResultDataListener() {
            @Override
            public void onResult(boolean isSucc, String msg, Request_Bean request_bean) {
                if (isSucc) {
                    if(request_bean.getData()!=null){
                        Main_PrizeCaseBean main_prizeCaseBean = JSON.parseArray(request_bean.getData().toString(),
                                Main_PrizeCaseBean.class).get(0);
                        //获取期号
                        int cqh=Integer.parseInt(main_prizeCaseBean.getPeriodNum().substring(6,9))+1;
                        if(cqh>120){
                            //请求当前时间+001
                            getNetworksData(new ResultListener() {
                                @Override
                                public void onResult(boolean isSucc, Date date) {
                                    SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd");
                                    String data=sdr.format(date);
                                    String []datas=data.split("-");
                                    String cqh=datas[0].substring(2,4)+datas[1]+datas[2]+"001";
                                    //请求计划内容
                                    requestPlanContentData(getPlanContent_Params(cqh,plan));
                                }
                            });
                        }else {
                            //请求计划内容
                            String cqhStr=Integer.toString(Integer.parseInt(main_prizeCaseBean.getPeriodNum())+1);
                            requestPlanContentData(getPlanContent_Params(cqhStr,plan));
                        }
                    }
                }else {
                    // 请求失败
                }
            }
        }, HttpRequestMethod.POST);
    }
    /**
     * 设置计划内容
     * @param beans
     */
    @Override
    public void setPlanContentData(List<String> beans) {
        if (beans != null && beans.size() > 0) {
            //调用View的接口，设置列表
            mView.setPlanContentList(beans);
            //异步请求，将计划内容转化成String
            //getPlanContentString();
        }
    }
    /**
     * 异步请求，将计划内容转化成String
     */
    public void getPlanContentString() {
        //创建被观察者
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    for(String str:planContentList){
                        planContents+=str+",";
                    }
                    subscriber.onNext(planContents);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        })
                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        //结束订阅
                    }
                    @Override
                    public void onError(Throwable e) {
                    }
                    @Override
                    public void onNext(String s) {
                    }
                });
    }
    /**
     * 复制内容
     * @return
     */
    @Override
    public String copyContent() {
        String content=planContents.substring(0,planContents.length()-1);
        return planContents;
    }
    public interface ResultListener  {
        public void onResult(boolean isSucc, Date date);
    }
    /**
     * 获取网络时间
     * @return
     */
    public void getNetworksData(final ResultListener resultListener){
        //创建被观察者
        Observable.create(new Observable.OnSubscribe<Date>() {
            @Override
            public void call(Subscriber<? super Date> subscriber) {
                try {
                    Date data=getNetworkTime("http://www.beijing-time.org");
                    subscriber.onNext(data);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        })
                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Subscriber<Date>() {
                    @Override
                    public void onCompleted() {
                        //结束订阅
                    }
                    @Override
                    public void onError(Throwable e) {
                    }
                    @Override
                    public void onNext(Date date) {
                        resultListener.onResult(true,date);
                    }
                });
    }
    private static Date getNetworkTime(String webUrl) {
        try {
            URL url = new URL(webUrl);
            URLConnection conn = url.openConnection();
            conn.connect();
            long dateL = conn.getDate();
            Date date = new Date(dateL);
           /* SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            L.e("===========",dateFormat.format(date));*/
            return date;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
