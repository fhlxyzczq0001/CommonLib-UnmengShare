package com.app.lotteryticket.MVP.Contract.Fragment;

import com.app.common.Base.BasePresenter;
import com.app.common.Base.BaseView;
import com.app.lotteryticket.MVP.Model.Bean.Main_UserCenterBean;

/**
 *  首页用户
 *  @Author: 杨重诚
 *  @CreatTime: 2017/7/18 14:04
 */

public interface Main_UserCenterFragment_Contract {
    interface View extends BaseView {
        /**
         *关闭刷新控件
         */
        public void closeRefresh();
        /**
         * 设置用户中心数据
         */
        public void setUserCenterData(Main_UserCenterBean userCenterData);
        /**
         * 用户退出应用成功后的操作
         */
        public void quitSuccess();
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
         * 请求用户中心数据
         */
        public abstract void requestUserCenterData();
        /**
         * 用户退出登录的请求
         */
        public abstract void requestUserInfoLogout();
    }
}
