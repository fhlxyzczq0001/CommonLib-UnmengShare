package com.app.common.Base;

import android.view.View;

import com.andexert.library.RippleView;
import com.app.common.R;
import com.app.common.Util.TUtil;

/**
 * Created by ${杨重诚} on 2017/6/8.
 */

public abstract class Common_View_BaseFragment<T extends BasePresenter,TT extends BasePresenter> extends Common_BaseFragment {
    protected CommonApplication mCommonApplication;
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
