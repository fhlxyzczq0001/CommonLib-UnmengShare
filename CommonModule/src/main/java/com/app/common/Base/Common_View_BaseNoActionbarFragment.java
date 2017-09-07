package com.app.common.Base;


import com.app.common.Util.TUtil;

/**
 * Created by ${杨重诚} on 2017/6/7.
 */

public abstract class Common_View_BaseNoActionbarFragment<T extends BasePresenter,TT extends BasePresenter> extends Common_BaseNoActionbarFragment {
    protected CommonApplication mCommonApplication;
    public T mPresenter;
    @Override
    protected void initApplication() {
        mCommonApplication = CommonApplication.getInstance();
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
