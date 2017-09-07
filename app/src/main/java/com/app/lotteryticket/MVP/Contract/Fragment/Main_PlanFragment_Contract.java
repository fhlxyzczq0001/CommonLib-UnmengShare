package com.app.lotteryticket.MVP.Contract.Fragment;

import com.app.common.Base.BasePresenter;
import com.app.common.Base.BaseView;

/**
 *  首页计划
 *  @Author: 杨重诚
 *  @CreatTime: 2017/7/18 14:04
 */

public interface Main_PlanFragment_Contract {
    interface View extends BaseView {
    }

    abstract class Presenter extends BasePresenter<View> {
        @Override
        public void onStart() {

        }
    }
}
