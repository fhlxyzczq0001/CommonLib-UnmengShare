package com.app.lotteryticket.MVP.Presenter.Implement.Fragment;


import com.alibaba.fastjson.JSON;
import com.app.common.HttpRequest.HttpPath;
import com.app.common.HttpRequest.HttpRequestMethod;
import com.app.common.HttpRequest.ResultListener.ResultDataListener;
import com.app.common.MVP.Model.Bean.CommonMenuBean;
import com.app.common.MVP.Model.Bean.Common_Notice_Bean;
import com.app.common.MVP.Model.Bean.Request_Bean;
import com.app.common.MVP.Model.Implement.Base_HttpRequest_Implement;
import com.app.common.MVP.Model.Interface.Base_HttpRequest_Interface;
import com.app.common.Public.Main_PublicCode;
import com.app.common.Public.RouterUrl;
import com.app.lotteryticket.MVP.Contract.Fragment.Main_HomeFragment_Contract;
import com.app.lotteryticket.MVP.Model.Bean.Main_PrizeCaseBean;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 首页
 * Created by ${杨重诚} on 2017/6/2.
 */

public class Main_HomeFragment_Presenter extends Main_HomeFragment_Contract.Presenter {
    Base_HttpRequest_Interface mMain_base_httpRequest_interface;
    private int countHttpMethod = 0;//总共有多少个请求网络数据的方法
    private int indexHttpMethod = 0;//记录当前执行到第几个请求方法
    List<Common_Notice_Bean> notice_array;//公告数据集
    List<CommonMenuBean> commonMenuBeanList;//其他功能菜单数据
    public Main_HomeFragment_Presenter() {
        mMain_base_httpRequest_interface=new Base_HttpRequest_Implement();
    }
    /**
     * 初始化参数
     */
    @Override
    public void initData(int countHttpMethod) {
        this.countHttpMethod = countHttpMethod;
        indexHttpMethod = 2;
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
     * 获取开奖情况说明数据
     */
    @Override
    public void requestPrizeCaseData() {
        requestPrizeCaseData(getPrizeCase_Params());
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
                        if(!mView.getIsPullDownToRefresh()){
                            //设置倒计时
                            getCountTime();
                        }
                        mView.setPrizeCase(main_prizeCaseBean);
                    }
                }else {
                    // 请求失败
                }
                closeRefresh();
            }
        }, HttpRequestMethod.POST);
    }
    /**
     * 获取公告数据
     */
    @Override
    public void requestNoticeData() {
        requestNoticeData(getNotice_Params());
    }
    /**
     *获取公告数据的Params
     * @return
     */
    public Map<String, String> getNotice_Params() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("ACT","GetMsg");
        return params;
    }
    /**
     * 获取公告数据
     * @param params
     */
    public void requestNoticeData(Map<String, String> params) {
        if(notice_array!=null&&notice_array.size()>0){
            setAutoScrollTextView(notice_array);
            closeRefresh();
            return;
        }
        mMain_base_httpRequest_interface.requestData(context, HttpPath.URL_API_NEW_NOTICE, params, new ResultDataListener() {
            @Override
            public void onResult(boolean isSucc, String msg, Request_Bean request_bean) {
                if (isSucc) {
                    if(request_bean.getData()!=null){
                        Common_Notice_Bean common_notice_bean=new Common_Notice_Bean();
                        common_notice_bean.setTitle(request_bean.getData().toString());
                        notice_array=new ArrayList<Common_Notice_Bean>();
                        notice_array.add(common_notice_bean);
                        setAutoScrollTextView(notice_array);
                    }

                }else {
                    // 请求失败
                }
                closeRefresh();
            }
        },HttpRequestMethod.POST);
    }
    /**
     * 获取其他功能菜单数据
     *
     */
    @Override
    public void requestOtherFunctionData() {
        if(commonMenuBeanList==null){
            commonMenuBeanList=new ArrayList<>();
            CommonMenuBean commonMenuBean1=new CommonMenuBean();
            commonMenuBean1.setTitle("计划中心");
            commonMenuBean1.setUrl(RouterUrl.mainMainActivityRouterUrl+"?tab="+ Main_PublicCode.MAIN_TAB_PLAN);
            CommonMenuBean commonMenuBean2=new CommonMenuBean();
            commonMenuBean2.setTitle("历史开奖");
            commonMenuBean2.setUrl(RouterUrl.mainHistoryPrizeRouterUrl);
            CommonMenuBean commonMenuBean3=new CommonMenuBean();
            commonMenuBean3.setTitle("软件介绍");
            commonMenuBean3.setUrl(RouterUrl.mainAboutUsRouterUrl);
            CommonMenuBean commonMenuBean4=new CommonMenuBean();
            commonMenuBean4.setTitle("玩法攻略");
            commonMenuBean4.setUrl("http://www.zhcw.com/ssq/szjq/index.shtml?from=ssqkjggbl&do=szjq");
            commonMenuBeanList.add(commonMenuBean1);
            commonMenuBeanList.add(commonMenuBean2);
            commonMenuBeanList.add(commonMenuBean3);
            commonMenuBeanList.add(commonMenuBean4);
        }
        mView.setOtherFunctionMenuList(commonMenuBeanList);
    }

    /**
     * 设置头条滚动
     */
    public void setAutoScrollTextView(List<Common_Notice_Bean> notice_arra) {
        if (null != notice_arra && notice_arra.size() > 0) {
            mView.setAutoScrollTextView(notice_arra);
        }
    }
    public interface ResultListener  {
        public void onResult(boolean isSucc, Date date);
    }
    /**
     * 获取倒计时时间
     * @return
     */
    public void getCountTime(){
        getNetworksData(new ResultListener() {
            @Override
            public void onResult(boolean isSucc, Date date) {
                if(isSucc){
                    // 得到倒计时的时长
                    long totalCountTime =System.currentTimeMillis();
                    Calendar now = Calendar.getInstance();
                    now.setTime(date);
                    int hour=now.get(Calendar.HOUR_OF_DAY);
                    int minute=now.get(Calendar.MINUTE);
                    int second=now.get(Calendar.SECOND);

                    //当前时间>=0并且=<2或者当前时间>=22并且<=23:59:59 每隔5分钟开奖一次
                    if((hour>=0&&hour<2)||(hour>=22&&(
                            hour<23||(hour==23&&(minute<59||(minute==59&&second<=59)))))){
                        totalCountTime=(5-minute%5)*60*1000-second*1000;
                    }else if(hour>=10&&hour<22){
                        //当前时间>10并且<22 每隔10分钟开奖一次
                        totalCountTime=(10-minute%10)*60*1000-second*1000;
                    }else if(hour>=2&&hour<10){
                        long totalDate=10*60*60*1000;
                        long nowDate=(hour*60+minute)*60*1000+second*1000;
                        totalCountTime=totalDate-nowDate;
                    }
                    mView.setCountTime(totalCountTime);
                }
            }
        });
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
