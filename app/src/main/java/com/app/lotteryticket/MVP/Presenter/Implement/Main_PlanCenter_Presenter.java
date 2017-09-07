package com.app.lotteryticket.MVP.Presenter.Implement;


import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.app.common.HttpRequest.HttpPath;
import com.app.common.HttpRequest.HttpRequestMethod;
import com.app.common.HttpRequest.ResultListener.ResultDataListener;
import com.app.common.MVP.Model.Bean.CommonMenuBean;
import com.app.common.MVP.Model.Bean.Request_Bean;
import com.app.common.MVP.Model.Implement.Base_HttpRequest_Implement;
import com.app.common.MVP.Model.Interface.Base_HttpRequest_Interface;
import com.app.common.Public.RouterUrl;
import com.app.lotteryticket.MVP.Contract.Main_Plan_Contract;
import com.app.lotteryticket.MVP.Model.Bean.Main_PlanCenterBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 计划中心
 * Created by ${杨重诚} on 2017/6/2.
 */

public class Main_PlanCenter_Presenter extends Main_Plan_Contract.Presenter {
    Base_HttpRequest_Interface mMain_base_httpRequest_interface;
    private int countHttpMethod = 0;//总共有多少个请求网络数据的方法
    private int indexHttpMethod = 0;//记录当前执行到第几个请求方法
    private int classGoods_page = 1;//分页请求，默认第一页
    List<CommonMenuBean> commonMenuBeanList;//菜单数据
    String type="5";//标识是3个号码还是5个
    private List<Main_PlanCenterBean> mMain_planCenterBeanList = new ArrayList<>();//存放数据的数据源
    public Main_PlanCenter_Presenter() {
        mMain_base_httpRequest_interface=new Base_HttpRequest_Implement();
    }
    /**
     * 获取页面传值
     * @param mBundle
     */
    @Override
    public void getBundleValues(Bundle mBundle){
         type=mBundle.getString("type","5");
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
     * 获取计划菜单数据
     */
    @Override
    public void requestPlanMenuData() {
        if(commonMenuBeanList==null){
            commonMenuBeanList=new ArrayList<>();
            CommonMenuBean commonMenuBean1=new CommonMenuBean();
            commonMenuBean1.setTitle("计划一");
            commonMenuBean1.setUrl(RouterUrl.mainPlanContentRouterUrl+"?type="+type);
            CommonMenuBean commonMenuBean2=new CommonMenuBean();
            commonMenuBean2.setTitle("计划二");
            commonMenuBean2.setUrl(RouterUrl.mainPlanContentRouterUrl+"?type="+type);
            CommonMenuBean commonMenuBean3=new CommonMenuBean();
            commonMenuBean3.setTitle("计划三");
            commonMenuBean3.setUrl(RouterUrl.mainPlanContentRouterUrl+"?type="+type);
            CommonMenuBean commonMenuBean4=new CommonMenuBean();
            commonMenuBean4.setTitle("计划四");
            commonMenuBean4.setUrl(RouterUrl.mainPlanContentRouterUrl+"?type="+type);
            commonMenuBeanList.add(commonMenuBean1);
            commonMenuBeanList.add(commonMenuBean2);
            commonMenuBeanList.add(commonMenuBean3);
            commonMenuBeanList.add(commonMenuBean4);
        }
        mView.setPlanMenuList(commonMenuBeanList);
    }

    /**
     * 获取计划列表数据
     */
    @Override
    public void requestPlanCenterData() {
        requestPlanCenterData(getPlanCenter_Params());
    }
    /**
     *获取计划列表数据的Params
     * @return
     */
    public Map<String, String> getPlanCenter_Params() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("ACT","GetResultPage");
        params.put("page",Integer.toString(classGoods_page));
        return params;
    }
    /**
     * 获取计划列表数据
     * @param params
     */
    public void requestPlanCenterData(Map<String, String> params) {
        mMain_base_httpRequest_interface.requestData(context, HttpPath.URL_API_GET_PLAN_CENTER, params, new ResultDataListener() {
            @Override
            public void onResult(boolean isSucc, String msg, Request_Bean request_bean) {
                if (isSucc) {
                    if(request_bean.getData()!=null){
                        List<Main_PlanCenterBean> mMain_planCenterBeanList= JSON.parseArray(request_bean.getData().toString(),Main_PlanCenterBean.class);
                        setPlanCenterData(mMain_planCenterBeanList);
                    }else {
                        List<Main_PlanCenterBean> mMain_planCenterBeanList=new ArrayList<Main_PlanCenterBean>();
                        for(int i=0;i<20;i++){
                            Main_PlanCenterBean planCenterBean=new Main_PlanCenterBean();
                            if(type.contains("5")){
                                planCenterBean.setPeriods(Integer.toString(51478+i));
                            }else {
                                planCenterBean.setPeriods(Integer.toString(478+i));
                            }
                            planCenterBean.setPlanOne("中");
                            planCenterBean.setPlanTwo("挂");
                            planCenterBean.setPlanThree("中");
                            planCenterBean.setPlanFour("挂");
                            mMain_planCenterBeanList.add(planCenterBean);
                        }
                        setPlanCenterData(mMain_planCenterBeanList);
                    }

                }else {
                    // 请求失败
                }
                closeRefresh();
            }
        }, HttpRequestMethod.POST);
        /*List<Main_PlanCenterBean> mMain_planCenterBeanList=new ArrayList<Main_PlanCenterBean>();
        for(int i=0;i<20;i++){
            Main_PlanCenterBean planCenterBean=new Main_PlanCenterBean();
            if(type.contains("5")){
                planCenterBean.setPeriods(Integer.toString(51478+i));
            }else {
                planCenterBean.setPeriods(Integer.toString(478+i));
            }
            planCenterBean.setPlanOne("中");
            planCenterBean.setPlanTwo("挂");
            planCenterBean.setPlanThree("中");
            planCenterBean.setPlanFour("挂");
            mMain_planCenterBeanList.add(planCenterBean);
        }
        setPlanCenterData(mMain_planCenterBeanList);
        closeRefresh();*/
    }
    /**
     * 设置计划列表
     * @param beans
     */
    @Override
    public void setPlanCenterData(List<Main_PlanCenterBean> beans) {
        if (beans != null && beans.size() > 0) {
            //------------判断是下拉刷新还是上拉加载----------------------------
            if (classGoods_page == 1) {
                mMain_planCenterBeanList.clear();
                mMain_planCenterBeanList = beans;
            } else {
                mMain_planCenterBeanList.addAll(beans);
            }
        } else if(classGoods_page==1&&beans != null && beans.size() <= 0){
            mMain_planCenterBeanList.clear();
        }
        //调用View的接口，设置列表
        mView.setPlanCenterList(mMain_planCenterBeanList);
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
