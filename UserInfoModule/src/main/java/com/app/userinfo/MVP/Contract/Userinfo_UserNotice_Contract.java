package com.app.userinfo.MVP.Contract;


import com.app.common.Base.BasePresenter;
import com.app.common.Base.BaseView;
import com.app.userinfo.MVP.Model.Bean.Userinfo_NoticeBean;

import java.util.List;

/**用户通知消息接口契约类
 * Created by ${杨重诚} on 2017/6/2.
 */

public interface Userinfo_UserNotice_Contract {
    interface View extends BaseView {
        /**
         * 设置最新公告数据
         * @param beanList
         */
        public void setNoticeList(List<Userinfo_NoticeBean> beanList);
    }

    abstract class Presenter extends BasePresenter<View> {
        @Override
        public void onStart() {

        }
        /**
         * 请求最新公告数据
         */
        public abstract void requestNotice();
    }
}
