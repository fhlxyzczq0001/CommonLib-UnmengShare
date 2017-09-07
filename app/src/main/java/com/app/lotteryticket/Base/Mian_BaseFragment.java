package com.app.lotteryticket.Base;

import android.view.View;

import com.andexert.library.RippleView;
import com.app.common.Base.BasePresenter;
import com.app.common.Base.BaseView;
import com.app.common.Base.Common_BaseFragment;
import com.app.common.Util.TUtil;
import com.app.lotteryticket.R;

public abstract class Mian_BaseFragment<T extends BasePresenter,TT extends BasePresenter> extends Common_BaseFragment {
	protected My_Application myApplication;
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
		myApplication = My_Application.getInstance();//获取Application
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
