package com.app.lotteryticket.MVP.Contract;

import com.app.common.Base.BasePresenter;
import com.app.common.Base.BaseView;

/**引导页接口契约类
 * Created by ${杨重诚} on 2017/6/2.
 */

public interface Main_Guide_Contract {
    interface View extends BaseView {

    }

    abstract class Presenter extends BasePresenter<View> {
        @Override
        public void onStart() {

        }
        /**
         * 获取默认引导页
         * @Title: setDefaultGuidPage
         * @Description: TODO
         * @return: void
         */
        public abstract int[]  getDefaultGuidPage();
    }
}
