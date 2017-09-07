package com.app.userinfo.Base;

import com.app.common.Base.BasePresenter;
import com.app.common.Base.BaseView;
import com.app.common.Base.Common_BaseNoActionbarFragment;
import com.app.common.Util.TUtil;
import com.app.userinfo.Base.Application.UserInfoApplication_Interface;

/**
 * Created by ${杨重诚} on 2017/6/7.
 */

public abstract class Userinfo_BaseNoActionbarFragment<T extends BasePresenter,TT extends BasePresenter> extends Common_BaseNoActionbarFragment {
    protected UserInfoApplication_Interface mIndianaApplication_interface;
    public T mPresenter;
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
