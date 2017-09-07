package com.app.userinfo.MVP.Contract;


import android.widget.EditText;

import com.app.common.Base.BasePresenter;
import com.app.common.Base.BaseView;

/**用户修改密码接口契约类
 * Created by ${杨重诚} on 2017/6/2.
 */

public interface Userinfo_UserModifyPass_Contract {
    interface View extends BaseView {
        /**
         * 密码修改成功
         */
        public void modifyPassSuccess(String msg);
    }

    abstract class Presenter extends BasePresenter<View> {
        @Override
        public void onStart() {

        }
        /**
         *  获取界面数据
         * @param text1 旧密码
         * @param text2 新密码
         * @param text3 新密码
         */
        public abstract void getModifyPass(EditText text1, EditText text2, EditText text3);
        /**
         * 修改密码
         */
        public abstract void modifyPass(String userName, String password);
    }
}
