package com.app.lotteryticket.Base;

import com.app.common.Base.BasePresenter;
import com.app.common.Base.BaseView;
import com.app.common.Base.Common_BaseNoActionbarFragment;
import com.app.common.Util.TUtil;

public abstract class Mian_BaseNoActionbarFragment<T extends BasePresenter,TT extends BasePresenter> extends Common_BaseNoActionbarFragment {
	protected My_Application myApplication;
	public T mPresenter;

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
