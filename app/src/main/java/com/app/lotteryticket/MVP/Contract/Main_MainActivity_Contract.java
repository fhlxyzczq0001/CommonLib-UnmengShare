package com.app.lotteryticket.MVP.Contract;


import com.app.common.Base.BasePresenter;
import com.app.common.Base.BaseView;

/**主页面接口契约类
 * Created by ${杨重诚} on 2017/6/2.
 */

public interface Main_MainActivity_Contract {
    interface View extends BaseView {
    }

    abstract class Presenter extends BasePresenter<View> {
        @Override
        public void onStart() {

        }
        /**
         * 退出操作
         */
        public abstract void exit();
    }
}
