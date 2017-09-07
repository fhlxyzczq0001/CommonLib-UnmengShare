package com.app.userinfo.Base;

import android.view.View;

import com.andexert.library.RippleView;
import com.app.common.Base.BasePresenter;
import com.app.common.Base.BaseView;
import com.app.common.Base.Common_BaseFragment;
import com.app.common.Util.TUtil;
import com.app.userinfo.Base.Application.UserInfoApplication_Interface;
import com.app.userinfo.R;

/**
 * Created by ${杨重诚} on 2017/6/8.
 */

public abstract class Userinfo_BaseFragment<T extends BasePresenter,TT extends BasePresenter> extends Common_BaseFragment {
    protected UserInfoApplication_Interface mIndianaApplication_interface;
    public T mPresenter;
    RippleView acbr_left1_icon_RippleView;
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if(acbr_left1_icon_RippleView==null){
            acbr_left1_icon_RippleView=(RippleView)public_view.findViewById(R.id.acbr_left1_icon_RippleView);
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
        mIndianaApplication_interface = UserinfoApplication_Utils.getApplication();
        mPresenter = TUtil.getT(this, 1);
        if (this instanceof BaseView) mPresenter.setVM(context, this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mPresenter!=null){
            mPresenter.onDestroy();
            mPresenter=null;
        }
        context=null;
    }
}
