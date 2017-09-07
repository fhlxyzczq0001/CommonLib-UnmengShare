package com.app.common.Base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.common.MVP.View.Implement.NetworkError_View_Implement;
import com.app.common.R;
import com.app.common.Util.IntentUtil;
import com.app.common.Util.NetworkUtils;
import com.app.common.Util.ToastUtil;

import java.util.HashMap;

import butterknife.ButterKnife;

public abstract class Common_BaseNoActionbarFragment extends Fragment implements
		View.OnClickListener {
	protected View public_view;
	protected LayoutInflater inflater;
	protected IntentUtil intentTool;
	protected Context context;
	protected HashMap<String, Object> paramList;
	public boolean isVisibleToUser=false;//判断当前页面是否显示
	protected int contentView=-555;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (public_view == null) {
			this.inflater=inflater;
			public_view = setContentView();
			context = public_view.getContext();
			initActionbarView();
			initMyView();
			initApplication();
			intentTool = new IntentUtil();
			ButterKnife.bind(this, public_view);
			setTitleBar();
			init();
			setListeners();
		}

		// 缓存的rootView需要判断是否已经被加过parent，
		// 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
		ViewGroup parent = (ViewGroup) public_view.getParent();
		if (parent != null) {
			parent.removeView(public_view);
		}
		if(contentView!= R.layout.common_act_network_error_layout){
			checkNetwork();//检查网络是否正常
		}
		return public_view;
	}

	protected abstract void initApplication();
	protected abstract View setContentView();
	public void initActionbarView(){
	}
	public void initMyView(){
	}
	/**
	 * @category 初始化网络组件和数据组件以及LogUtil所需的clazz参数，设置布局
	 * 
	 */
	protected abstract void init();

	/**
	 * @category 初始化Android组件并实现组件并可以对组件赋值
	 * @TODO 初始化Android组件并实现组件并可以对组件赋值
	 */
	// protected abstract void findViews();

	/**
	 * @category 设置监听
	 * 
	 */
	protected abstract void setListeners();

	/**
	 * 设置标题栏
	 * 
	 * @category 设置标题栏
	 * 
	 */
	protected abstract void setTitleBar();

	/**
	 * @category 网络数据
	 * 
	 */
	//protected abstract void getDataList(HashMap<String, Object> paramList);


	public void onPause() {
		super.onPause();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	public IntentUtil getIntentTool() {
		return intentTool;
	}

	/**
	 * 检查网络是否正常
	 */
	private void checkNetwork() {
		NetworkUtils.NetworkStatus(context, new NetworkUtils.NetworkListener() {
			@Override
			public void onResult(boolean Status, String msg, NetworkUtils.NoNetworkType noNetworkType) {
				if (!Status) {
					ToastUtil.ErrorImageToast(context, msg);
					//跳转到网络异常页面
					Bundle bundle=new Bundle();
					if(noNetworkType.equals(NetworkUtils.NoNetworkType.NO_NETWORK)){
						//无网络
						bundle.putInt("errorType", NetworkError_View_Implement.NO_NETWORK);
					}else if(noNetworkType.equals(NetworkUtils.NoNetworkType.NO_PING)){
						//不能访问外网
						bundle.putInt("errorType",NetworkError_View_Implement.NO_PING);
					}
					getIntentTool().intent__no_animation_to(context, NetworkError_View_Implement.class,bundle);
				}
			}
		});
	}

}
