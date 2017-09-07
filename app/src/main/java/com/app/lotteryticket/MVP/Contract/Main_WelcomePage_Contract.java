package com.app.lotteryticket.MVP.Contract;



import com.app.common.Base.BasePresenter;
import com.app.common.Base.BaseView;
import com.app.lotteryticket.MVP.Model.Bean.WelcomePageBean;

import java.util.Map;

/**启动页接口契约类
 * Created by ${杨重诚} on 2017/6/2.
 */

public interface Main_WelcomePage_Contract {
    interface View extends BaseView {
        /**
         * 启动启动页service后台下载
         * @param pageBean
         */
        public void startWelcomePageService(WelcomePageBean pageBean);

        /**
         * 待跳转的下个界面
         */
        public void toNextActivity();
    }

    abstract class Presenter extends BasePresenter<View> {
        @Override
        public void onStart() {

        }
        /**
         * 获取启动页更新的Params
         * @return
         */
        public abstract Map<String, String> getStartPageUpdate_Params();
        /**
         * 请求启动页更新
         * @param params
         */
        public abstract void requestStartPageUpdate(Map<String, String> params);
    }
}
