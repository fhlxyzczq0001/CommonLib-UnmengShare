package com.app.userinfo.MVP.View;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.app.common.CustomView.ClearEditText;
import com.app.common.Public.RouterUrl;
import com.app.common.Util.ToastUtil;
import com.app.userinfo.Base.Userinfo_BaseActivity;
import com.app.userinfo.MVP.Contract.Userinfo_UserModifyPass_Contract;
import com.app.userinfo.MVP.Presenter.Implement.Userinfo_UserModifyPass_Presenter;
import com.app.userinfo.R;
import com.chenenyu.router.annotation.Route;

/**
 * 修改密码界面
 */
@Route(value = RouterUrl.userinfo_UserModifyPasswordRouterUrl, interceptors = RouterUrl.interceptor_UserInfo_RouterUrl)
public class Userinfo_UserModifyPass_View extends Userinfo_BaseActivity<Userinfo_UserModifyPass_Contract.Presenter, Userinfo_UserModifyPass_Presenter> implements Userinfo_UserModifyPass_Contract.View {
    ClearEditText modifyPasswordOld;
    ClearEditText modifyPasswordNew;
    ClearEditText modifyPasswordAgain;
    Button modify_password_sure;

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.modify_password_sure) {
            mPresenter.getModifyPass(modifyPasswordOld, modifyPasswordNew, modifyPasswordAgain);
        }
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.userinfo_act_user_modify_password_layout);
    }

    @Override
    protected void init() {
    }

    @Override
    public void initMyView() {
        super.initMyView();
        modifyPasswordOld = (ClearEditText) findViewById(R.id.modify_password_old);
        modifyPasswordNew = (ClearEditText) findViewById(R.id.modify_password_new);
        modifyPasswordAgain = (ClearEditText) findViewById(R.id.modify_password_again);
        modify_password_sure = (Button) findViewById(R.id.modify_password_sure);
    }

    @Override
    protected void setListeners() {
        modify_password_sure.setOnClickListener(this);
    }

    @Override
    protected void setTitleBar() {
        setActionbarBar("修改密码", R.color.app_color, R.color.white, true, true);
    }

    @Override
    protected void getData() {

    }

    @Override
    public void getBundleValues(Bundle mBundle) {
        super.getBundleValues(mBundle);
        Bundle bundle = getIntent().getExtras();
    }

    /**
     * 修改密码成功后的操作
     */
    @Override
    public void modifyPassSuccess(String msg) {
        ToastUtil.RightImageToast(context,msg);
        FinishA();
    }
}
