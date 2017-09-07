package com.app.lotteryticket.MVP.Contract;

import com.app.common.Base.BasePresenter;
import com.app.common.Base.BaseView;

/**关于我们接口契约类
 * Created by ${杨重诚} on 2017/6/2.
 */

public interface Main_AboutAs_Contract {
    interface View extends BaseView {

    }

    abstract class Presenter extends BasePresenter<View> {
        @Override
        public void onStart() {

        }
    }
}
