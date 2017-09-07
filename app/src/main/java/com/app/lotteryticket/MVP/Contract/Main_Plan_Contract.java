package com.app.lotteryticket.MVP.Contract;

import android.os.Bundle;

import com.app.common.Base.BasePresenter;
import com.app.common.Base.BaseView;
import com.app.common.MVP.Model.Bean.CommonMenuBean;
import com.app.lotteryticket.MVP.Model.Bean.Main_PlanCenterBean;

import java.util.List;

/**
 *  计划页面
 *  @Author: 杨重诚
 *  @CreatTime: 2017/7/18 14:04
 */

public interface Main_Plan_Contract {
    interface View extends BaseView {
        /**
         * 设置菜单列表展示
         */
        public void setPlanMenuList(List<CommonMenuBean> beans);
        /**
         *关闭刷新控件
         */
        public void closeRefresh();
        /**
         * 设置计划列表展示
         */
        public void setPlanCenterList(List<Main_PlanCenterBean> beans);
    }

    abstract class Presenter extends BasePresenter<View> {
        @Override
        public void onStart() {

        }
        /**
         * 获取页面传值
         * @param mBundle
         */
        public abstract void getBundleValues(Bundle mBundle);
        /**
         *
         * @param countHttpMethod 共有多少个请求方法
         */
        public abstract void initData(int countHttpMethod);
        /**
         *关闭刷新控件
         */
        public abstract void closeRefresh();
        /**
         * 获取计划菜单数据
         */
        public abstract void requestPlanMenuData();
        /**
         * 获取计划列表数据
         */
        public abstract void requestPlanCenterData();
        /**
         * 设置计划列表
         * @param beans
         */
        public abstract void setPlanCenterData(List<Main_PlanCenterBean> beans);
        /**
         * 设置页数
         * @param page
         */
        public abstract void setClassGoods_page(int page);
        /**
         * 获取页数
         * @return
         */
        public abstract int getClassGoods_page();

    }
}
