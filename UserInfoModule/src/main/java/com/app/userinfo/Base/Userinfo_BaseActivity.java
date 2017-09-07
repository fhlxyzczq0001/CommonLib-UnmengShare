package com.app.userinfo.Base;

import android.os.Bundle;
import android.view.View;

import com.andexert.library.RippleView;
import com.app.common.Base.BasePresenter;
import com.app.common.Base.BaseView;
import com.app.common.Base.Common_BaseActivity;
import com.app.common.Util.TUtil;
import com.app.userinfo.Base.Application.UserInfoApplication_Interface;
import com.app.userinfo.R;

public abstract class Userinfo_BaseActivity<T extends BasePresenter,TT extends BasePresenter> extends Common_BaseActivity {
    protected UserInfoApplication_Interface mUserInfoApplication_interface;
    public T mPresenter;
    RippleView acbr_left1_icon_RippleView;

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if(acbr_left1_icon_RippleView==null){
            acbr_left1_icon_RippleView=(RippleView)findViewById(R.id.acbr_left1_icon_RippleView);
        }
        acbr_left1_icon_RippleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FinishA();
            }
        });
    }
    @Override
    protected void initApplication() {
        mUserInfoApplication_interface = UserinfoApplication_Utils.getApplication();
        mPresenter = TUtil.getT(this, 1);
        if (this instanceof BaseView) mPresenter.setVM(this, this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onMyPause() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mPresenter!=null){
            mPresenter.onDestroy();
            mPresenter=null;
        }
        context=null;
    }
}
