package com.app.lotteryticket.MVP.Contract;

import android.os.Bundle;

import com.app.common.Base.BasePresenter;
import com.app.common.Base.BaseView;

import java.util.List;

/**
 * 计划内容页面
 *  @Author: 杨重诚
 *  @CreatTime: 2017/7/18 14:04
 */

public interface Main_PlanContent_Contract {
    interface View extends BaseView {
        /**
         *关闭刷新控件
         */
        public void closeRefresh();
        /**
         * 设置计划内容列表展示
         */
        public void setPlanContentList(List<String> beans);
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
         * 获取计划内容数据
         */
        public abstract void requestPlanContentData();
        /**
         * 设置计划内容
         * @param beans
         */
        public abstract void setPlanContentData(List<String> beans);
        /**
         * 复制内容
         * @return
         */
        public abstract String copyContent();
    }
}
