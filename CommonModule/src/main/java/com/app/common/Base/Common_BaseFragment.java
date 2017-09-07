package com.app.common.Base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.app.common.MVP.View.Implement.NetworkError_View_Implement;
import com.app.common.R;
import com.app.common.R2;
import com.app.common.Util.IntentUtil;
import com.app.common.Util.NetworkUtils;
import com.app.common.Util.ToastUtil;
import com.app.common.Util.WindowUtils;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.OnClick;

public abstract class Common_BaseFragment extends Fragment implements
		View.OnClickListener {
	//标题栏父布局
	public LinearLayout acbr_Parent_Layout;
	//标题栏布局
	public RelativeLayout acbr_Layout;
	//标题栏title
	public TextView acbr_title;
	//返回文字title
	public TextView acbr_left1_text;
	//标题栏返回按钮
	public RippleView acbr_left1_icon_RippleView;
	//标题栏返回按钮
	public ImageView acbr_left1_icon;
	//标题栏右边按钮
	public ImageView acbr_right1_icon;
	//标题栏右边按钮
	public RippleView acbr_right1_icon_RippleView;
	//标题栏右边文字
	public TextView acbr_right_text;
	//分割线
	public View line_view;
	public ImageView acbr_title_img;
	//返回按钮点击事件
	@OnClick(R2.id.acbr_left1_icon_RippleView)
	void finishA(View view) {
		FinishA();
	}
	protected View public_view;
	protected LayoutInflater inflater;
	protected IntentUtil intentTool;
	protected Context context;
	protected HashMap<String, Object> paramList;
	public boolean isVisibleToUser=false;//判断当前页面是否显示
	protected int contentView=-555;
	protected int systemBarTintManagerColor= R.color.black;//沉浸式状态栏背景色
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (public_view == null) {
			this.inflater=inflater;
			public_view = setContentView();
			initActionbarView();
			initMyView();
			context = public_view.getContext();
			initApplication();
			intentTool = new IntentUtil();
			ButterKnife.bind(this, public_view);
			setActionbarBar();
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
	public void initActionbarView(){
		acbr_Parent_Layout=(LinearLayout)public_view. findViewById(R.id.acbr_Parent_Layout);
		acbr_Layout=(RelativeLayout)public_view.  findViewById(R.id.acbr_Layout);
		acbr_title=(TextView)public_view.  findViewById(R.id.acbr_title);
		acbr_left1_text=(TextView)public_view.  findViewById(R.id.acbr_left1_text);
		acbr_left1_icon=(ImageView)public_view. findViewById(R.id.acbr_left1_icon);
		acbr_right1_icon=(ImageView)public_view.  findViewById(R.id.acbr_right1_icon);
		acbr_right1_icon_RippleView=(RippleView)public_view.  findViewById(R.id.acbr_right1_icon_RippleView);
		acbr_right_text=(TextView) public_view. findViewById(R.id.acbr_text);
		line_view=(View)public_view.  findViewById(R.id.line_view);
		acbr_title_img=(ImageView) public_view.  findViewById(R.id.acbr_title_img);
		acbr_left1_icon_RippleView=(RippleView)public_view.  findViewById(R.id.acbr_left1_icon_RippleView);
	}
	public void initMyView(){
	}
	protected abstract void initApplication();

	protected abstract View setContentView();

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
	public void setActionbarBar(String title,int bg,int titleColor){
		//设置Actionbar的高度
		//WindowUtils.setActionbarHeight(context, acbr_Parent_Layout);
		// ---------------------设置背景色---------------------------------
		acbr_Layout.setBackgroundResource(bg);// 设置actionbar背景
		acbr_Parent_Layout.setBackgroundDrawable(acbr_Layout.getBackground());// 设置actionbar最外层颜色和里层颜色一致
		// ---------------------设置标题---------------------------------
		acbr_title.setText(title);
		acbr_title.setTextColor(context.getResources().getColor(titleColor));
		acbr_title_img.setVisibility(View.INVISIBLE);
		acbr_title.setVisibility(View.VISIBLE);
	}
	public void setActionbarBar(String title, int bg, int titleColor, boolean showReturn) {
		//设置Actionbar的高度
		//WindowUtils.setActionbarHeight(context, acbr_Parent_Layout);
		// ---------------------设置背景色---------------------------------
		acbr_Layout.setBackgroundResource(bg);// 设置actionbar背景
		acbr_Parent_Layout.setBackgroundDrawable(acbr_Layout.getBackground());// 设置actionbar最外层颜色和里层颜色一致
		// ---------------------设置标题---------------------------------
		acbr_title.setText(title);
		acbr_title.setTextColor(context.getResources().getColor(titleColor));
		acbr_title_img.setVisibility(View.INVISIBLE);
		acbr_title.setVisibility(View.VISIBLE);
		if (showReturn)
			acbr_left1_icon_RippleView.setVisibility(View.VISIBLE);//显示返回按钮
	}
	public void setActionbarBar(String title, int bg, int titleColor, boolean showReturn,boolean showLineView) {
		//设置Actionbar的高度
		//WindowUtils.setActionbarHeight(context, acbr_Parent_Layout);
		// ---------------------设置背景色---------------------------------
		acbr_Layout.setBackgroundResource(bg);// 设置actionbar背景
		acbr_Parent_Layout.setBackgroundDrawable(acbr_Layout.getBackground());// 设置actionbar最外层颜色和里层颜色一致
		// ---------------------设置标题---------------------------------
		acbr_title.setText(title);
		acbr_title.setTextColor(context.getResources().getColor(titleColor));

		if (showReturn)
			acbr_left1_icon_RippleView.setVisibility(View.VISIBLE);//显示返回按钮
		if (showLineView)
			line_view.setVisibility(View.VISIBLE);//显示分割线
	}
	public void setActionbarBar(int bg,boolean showTitleImg, boolean showReturn) {
		//设置Actionbar的高度
		//WindowUtils.setActionbarHeight(context, acbr_Parent_Layout);
		// ---------------------设置背景色---------------------------------
		acbr_Layout.setBackgroundResource(bg);// 设置actionbar背景
		acbr_Parent_Layout.setBackgroundDrawable(acbr_Layout.getBackground());// 设置actionbar最外层颜色和里层颜色一致
		// ---------------------设置标题---------------------------------
		acbr_title_img.setVisibility(View.VISIBLE);
		acbr_title.setVisibility(View.INVISIBLE);
		if (showReturn)
			acbr_left1_icon_RippleView.setVisibility(View.VISIBLE);//显示返回按钮
	}

	public void setActionbarBar(){
		//设置Actionbar的高度
		//WindowUtils.setActionbarHeight(context, acbr_Parent_Layout);
		// ---------------------设置背景色---------------------------------
		acbr_Layout.setBackgroundResource(R.color.black);// 设置actionbar背景
		acbr_Parent_Layout.setBackgroundDrawable(acbr_Layout.getBackground());// 设置actionbar最外层颜色和里层颜色一致
		// ---------------------设置标题---------------------------------
		acbr_title.setText(context.getResources().getString(R.string.app_name));
		acbr_title.setTextColor(context.getResources().getColor(R.color.white));
		acbr_title_img.setVisibility(View.INVISIBLE);
		acbr_title.setVisibility(View.VISIBLE);
	}

	/**
	 * @category 网络数据
	 * 
	 */
	//protected abstract void getDataList(HashMap<String, Object> paramList);

	public void onResume() {
		super.onResume();
		// --------------------------设置沉浸式状态栏----------------------------------
		WindowUtils.setSystemBarTintManager(context, acbr_Parent_Layout, getResources().getColor(systemBarTintManagerColor));
	}

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
	 * 设置隐藏Actionbar
	 */
	public void setActionbarGone(){
		//设置Actionbar的高度
		acbr_Parent_Layout.setVisibility(View.GONE);
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

	@Override
	public void onClick(View v) {

	}

	/**
	 * finish当前Activity
	 */
	public void FinishA() {
		getActivity().finish();
		getActivity().overridePendingTransition(R.anim.slide_in_left,
				R.anim.slide_out_right);
	}
}
