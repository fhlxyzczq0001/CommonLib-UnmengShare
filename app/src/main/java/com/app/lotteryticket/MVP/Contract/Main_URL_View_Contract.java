package com.app.lotteryticket.MVP.Contract;

import com.app.common.Base.BasePresenter;
import com.app.common.Base.BaseView;
import com.app.common.MVP.Model.Bean.CommonMenuBean;

import java.util.List;

/**
 *  首页推荐
 *  @Author: 杨重诚
 *  @CreatTime: 2017/7/18 14:04
 */

public interface Main_URL_View_Contract {
    interface View extends BaseView {
        /**
         *关闭刷新控件
         */
        public void closeRefresh();
        /**
         * 设置网址列表展示
         */
        public void setURLMenuList(List<CommonMenuBean> beans);
    }

    abstract class Presenter extends BasePresenter<View> {
        @Override
        public void onStart() {

        }
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
         * 获取网址数据
         */
        public abstract void requestURLData();
    }
}
