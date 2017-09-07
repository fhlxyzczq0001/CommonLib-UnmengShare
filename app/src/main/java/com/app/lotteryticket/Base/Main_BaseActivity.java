package com.app.lotteryticket.Base;


import android.view.View;

import com.app.common.Base.BasePresenter;
import com.app.common.Base.BaseView;
import com.app.common.Base.Common_BaseActivity;
import com.app.common.Util.TUtil;
import com.app.lotteryticket.R;

import butterknife.OnClick;

public abstract class Main_BaseActivity<T extends BasePresenter,TT extends BasePresenter> extends Common_BaseActivity {
    protected My_Application myApplication;
    public T mPresenter;
    //返回按钮点击事件
    @OnClick(R.id.acbr_left1_icon_RippleView)
    void finishA(View view) {
        FinishA();
    }
    @Override
    protected void initApplication() {
        myApplication = My_Application.getInstance();//获取Application
        mPresenter = TUtil.getT(this, 1);
        if (this instanceof BaseView) mPresenter.setVM(this, this);
    }

    @Override
    protected void onMyPause() {
    }

    @Override
    public void onResume() {
        super.onResume();
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
