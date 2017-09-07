package com.app.userinfo.MVP.Contract;


import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.app.common.Base.BasePresenter;
import com.app.common.Base.BaseView;
import com.app.common.CustomView.ClearEditText;

/**登录接口契约类
 * Created by ${杨重诚} on 2017/6/2.
 */

public interface Userinfo_LogingView_Contract {
    interface View extends BaseView {
        /**
         * 获取用户名输入控件
         */
        public ClearEditText getEditTextLoginUserName();
        /**
         * 登录成功
         * @return
         */
        public boolean longSuccess(String msg);
    }

    abstract class Presenter extends BasePresenter<View> {
        @Override
        public void onStart() {

        }
        /**
         * 设置用户名输入
         */
        public abstract void setEditTextLoginUserName();
        /**
         * 登录
         * @param userName
         * @param passWord
         */
        public abstract void login(ClearEditText userName,ClearEditText passWord);
    }
}
