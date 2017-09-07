package com.app.lotteryticket.MVP.Contract.Fragment;

import com.app.common.Base.BasePresenter;
import com.app.common.Base.BaseView;
import com.app.common.MVP.Model.Bean.CommonMenuBean;
import com.app.common.MVP.Model.Bean.Common_Notice_Bean;
import com.app.lotteryticket.MVP.Model.Bean.Main_PrizeCaseBean;

import java.util.List;

/**
 *  首页菜单
 *  @Author: 杨重诚
 *  @CreatTime: 2017/7/18 14:04
 */

public interface Main_HomeFragment_Contract {
    interface View extends BaseView {
        /**
         *关闭刷新控件
         */
        public void closeRefresh();

        /**
         * 设置开奖情况说明
         * @param prizeCase
         */
        public void setPrizeCase(Main_PrizeCaseBean prizeCase);
        /**
         * 设置循环滚动消息的控件
         * @param notice_array
         */
        public void setAutoScrollTextView(List<Common_Notice_Bean> notice_array);
        /**
         * 设置其他功能菜单列表
         * @param commonMenuBeen
         */
        public void setOtherFunctionMenuList(List<CommonMenuBean> commonMenuBeen);
        /**
         * 设置倒计时
         * @param countTime
         */
        public  void setCountTime(long countTime);
        /**
         * 获取是否是下拉刷新
         * @return
         */
        public boolean getIsPullDownToRefresh();
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
         * 获取开奖情况说明数据
         */
        public abstract void requestPrizeCaseData();
        /**
         *  获取公告数据
         */
        public abstract void requestNoticeData();
        /**
         * 获取其他功能菜单数据
         *
         */
        public abstract void requestOtherFunctionData();
        /**
         * 获取倒计时时间
         * @return
         */
        public abstract void getCountTime();
    }
}
