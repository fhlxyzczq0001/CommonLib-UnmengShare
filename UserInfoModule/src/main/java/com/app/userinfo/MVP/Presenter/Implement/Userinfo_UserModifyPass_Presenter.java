package com.app.userinfo.MVP.Presenter.Implement;


import android.widget.EditText;

import com.app.common.HttpRequest.HttpPath;
import com.app.common.HttpRequest.ResultListener.ResultListener;
import com.app.common.MVP.Model.Implement.Base_Model_Implement;
import com.app.common.MVP.Model.Interface.Base_Model_Interface;
import com.app.common.Util.Textutils;
import com.app.common.Util.ToastUtil;
import com.app.userinfo.MVP.Contract.Userinfo_UserModifyPass_Contract;
import com.app.userinfo.R;

import java.util.HashMap;
import java.util.Map;

/**
 * 修改密码
 * Created by ${杨重诚} on 2017/6/2.
 */

public class Userinfo_UserModifyPass_Presenter extends Userinfo_UserModifyPass_Contract.Presenter {
    Base_Model_Interface mUserinfo_base_model_interface;//请求网络数据的model实现类
    public Userinfo_UserModifyPass_Presenter() {
        this.mUserinfo_base_model_interface = new Base_Model_Implement();
    }

    /**
     * 校验密码
     *
     * @param text
     * @return
     */
    public boolean checkPasswordNull(EditText text,String edtType) {
        String password = Textutils.getEditText(text);
        if (password.length() == 0) {
            //密码为空
            ToastUtil.WarnImageToast(context, edtType+Textutils.getValuesString(context, R.string.modify_password_tishi));
            return false;
        }
        return true;
    }

    /**
     * @param text
     * @return
     */
    public boolean checkPasswordNumber(EditText text,int type) {
        String password = Textutils.getEditText(text);
        //密码8到20位，至少包含字母和数字
        if (!Textutils.checkName8_20(password)) {
            switch (type){
                case 1:
                    ToastUtil.WarnImageToast(context, "原密码输入出错");
                    break;
                case 2:
                    ToastUtil.WarnImageToast(context, "新"+Textutils.getValuesString(context, R.string.modify_password_note));
                    break;
            }
            return false;
        }
        return true;
    }

    /**
     * 获取界面上输入的信息
     *
     * @param text1 旧密码
     * @param text2 新密码
     * @param text3 新密码
     */
    @Override
    public void getModifyPass(EditText text1, EditText text2, EditText text3) {
        boolean isTrueOldPass = false;
        boolean isTrueNewPass = false;
        boolean isTrueAgainPass = false;

        boolean isTrueOldPass_2 = checkPasswordNull(text1,"原密码");
        if (!isTrueOldPass_2)
            return;
        boolean isTrueNewPass_2 = checkPasswordNull(text2,"新密码");
        if (!isTrueNewPass_2)
            return;
        boolean isTrueAgainPass_2 = checkPasswordNull(text3,"确认密码");
        if (!isTrueAgainPass_2)
            return;
     /*   isTrueOldPass = checkPasswordNumber(text1,1);
        if (!isTrueOldPass)
            return;
        isTrueNewPass = checkPasswordNumber(text2,2);
        if (!isTrueNewPass)
            return;*/
        /*isTrueAgainPass = checkPasswordNumber(text3,2);
        if (!isTrueAgainPass)
            return;*/
        String old_password = Textutils.getEditText(text1);
        String new_password1 = Textutils.getEditText(text2);
        String new_password2 = Textutils.getEditText(text3);
        //两次输入的新密码一致
        if (new_password1.equals(new_password2)) {
            modifyPass(getModifyPass_Params(old_password, new_password1));
        } else {
            ToastUtil.WarnImageToast(context, Textutils.getValuesString(context, R.string.modify_password_out));
        }
    }
    /**
     * 请求修改密码方法
     *
     */
    @Override
    public void modifyPass(String old_password, String new_password){

    }
    /**
     * 获取修改密码的params
     *
     * @param old_password
     * @param new_password
     * @return
     */
    public Map<String, String> getModifyPass_Params(String old_password, String new_password) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("ACT", "ChagePass");
        params.put("password", old_password);
        params.put("npassword", new_password);
        return params;
    }

    /**
     * 请求修改密码方法
     *
     * @param params
     */
    public void modifyPass(final Map<String, String> params) {
        mUserinfo_base_model_interface.requestData(context, HttpPath.URL_API_USER_MODIFY_PASS, params, new ResultListener() {
            @Override
            public void onResult(boolean isSucc, String msg, String request) {
                if (isSucc) {
                    mView.modifyPassSuccess(msg);
                } else {
                    ToastUtil.WarnImageToast(context, msg);
                }
            }
        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
