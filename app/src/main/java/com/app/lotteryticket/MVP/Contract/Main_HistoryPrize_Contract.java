package com.app.lotteryticket.MVP.Contract;

import com.app.common.Base.BasePresenter;
import com.app.common.Base.BaseView;
import com.app.lotteryticket.MVP.Model.Bean.Main_HistoryPrizeBean;

import java.util.List;

/**
 *  历史开奖页面
 *  @Author: 杨重诚
 *  @CreatTime: 2017/7/18 14:04
 */

public interface Main_HistoryPrize_Contract {
    interface View extends BaseView {
        /**
         *关闭刷新控件
         */
        public void closeRefresh();

        /**
         * 设置开间时间
         * @param date
         */
        public void setPrizeDate(String date);
        /**
         * 设置历史开奖列表展示
         */
        public void setHistoryPrizeList(List<Main_HistoryPrizeBean> beans);
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
         * 获取历史开奖数据
         */
        public abstract void requestHistoryPrizeData();
        /**
         * 设置历史开奖
         * @param beans
         */
        public abstract void setHistoryPrizeData(List<Main_HistoryPrizeBean> beans);
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
