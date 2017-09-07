package com.app.common.Base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.app.common.HttpRequest.CustomRequestResponseBeanDataManager;
import com.app.common.HttpRequest.CustomRequestResponseManager;
import com.app.common.MVP.View.Implement.NetworkError_View_Implement;
import com.app.common.R;
import com.app.common.R2;
import com.app.common.Util.AppManager;
import com.app.common.Util.IntentUtil;
import com.app.common.Util.L;
import com.app.common.Util.NetworkUtils;
import com.app.common.Util.ToastUtil;
import com.app.common.Util.WindowUtils;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public abstract class Common_BaseActivity extends RxAppCompatActivity implements
        OnClickListener {
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

    protected Bundle mBundle;
    protected IntentUtil intentTool;
    protected Context context;
    protected int contentView=-555;
    protected boolean isShowSystemBarTintManager=true;//是否显示沉浸式状态栏
    protected int systemBarTintManagerColor= R.color.black;//沉浸式状态栏背景色

    //---------------------点击事件---------------------------------------
    //返回按钮点击事件
    @OnClick(R2.id.acbr_left1_icon_RippleView)
    void finishA(View view) {
        FinishA();
    }
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initApplication();
        context = this;//获取上下文
        intentTool = new IntentUtil();//获取intentTool
        mBundle=getIntent().getExtras();
        getBundleValues(mBundle);//获取页面传值
        setContentView();//设置布局
        ButterKnife.bind(this);//初始化ButterKnife
        initActionbarView();
        initMyView();
        setActionbarBar();
        setTitleBar();//设置title
        init();//初始化数据
        AppManager.getAppManager().addActivity(this);//添加当前Activity到AppManager
        setListeners();//设置监听事件
        getData();
        if(contentView!= R.layout.common_act_network_error_layout){
            checkNetwork();//检查网络是否正常
        }
    }
    public void initActionbarView(){
        acbr_Parent_Layout=(LinearLayout) findViewById(R.id.acbr_Parent_Layout);
        acbr_Layout=(RelativeLayout) findViewById(R.id.acbr_Layout);
        acbr_title=(TextView) findViewById(R.id.acbr_title);
        acbr_left1_text=(TextView) findViewById(R.id.acbr_left1_text);
        acbr_left1_icon=(ImageView) findViewById(R.id.acbr_left1_icon);
        acbr_right1_icon=(ImageView) findViewById(R.id.acbr_right1_icon);
        acbr_right1_icon_RippleView=(RippleView) findViewById(R.id.acbr_right1_icon_RippleView);
        acbr_right_text=(TextView) findViewById(R.id.acbr_text);
        line_view=(View) findViewById(R.id.line_view);
        acbr_left1_icon_RippleView=(RippleView) findViewById(R.id.acbr_left1_icon_RippleView);
    }
    public void initMyView(){
    }
    protected abstract void initApplication();

    protected abstract void setContentView();

    /**
     * @category 初始化网络组件和数据组件以及LogUtil所需的clazz参数，设置布局
     */
    protected abstract void init();

    /**
     * @category 初始化Android组件并实现组件并可以对组件赋值
     * @TODO 初始化Android组件并实现组件并可以对组件赋值
     */
    // protected abstract void findViews();

    /**
     * @category 设置监听
     */
    protected abstract void setListeners();

    /**
     * 获取页面传值
     * @param mBundle
     */
    public void getBundleValues(Bundle mBundle){
    };

    /**
     * 设置标题栏
     *
     * @category 设置标题栏
     */
    protected abstract void setTitleBar();

    public void setActionbarBar(String title, int bg, int titleColor, boolean showReturn) {
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
            acbr_left1_icon.setColorFilter(context.getResources().getColor(R.color.white));
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
            acbr_left1_icon.setColorFilter(context.getResources().getColor(R.color.white));
        if (showLineView)
            line_view.setVisibility(View.VISIBLE);//显示分割线
    }

    public void setActionbarBar() {
        //设置Actionbar的高度
       // WindowUtils.setActionbarHeight(context, acbr_Parent_Layout);
        // ---------------------设置背景色---------------------------------
        acbr_Layout.setBackgroundResource(R.color.black);// 设置actionbar背景
        acbr_Parent_Layout.setBackgroundDrawable(acbr_Layout.getBackground());// 设置actionbar最外层颜色和里层颜色一致
        // ---------------------设置标题---------------------------------
        acbr_title.setText(context.getResources().getString(R.string.app_name));
        acbr_title.setTextColor(context.getResources().getColor(R.color.white));
    }

    /**
     * @category 网络数据
     */
    //protected abstract void getDataList(HashMap<String, Object> paramList);
    protected abstract void getData();

    public void onResume() {
        super.onResume();
//        JPushInterface.onResume(this);
        //MobclickAgent.onResume(this);
        // --------------------------设置沉浸式状态栏----------------------------------
        if(isShowSystemBarTintManager){
            WindowUtils.setSystemBarTintManager(context, acbr_Parent_Layout, getResources().getColor(systemBarTintManagerColor));
        }
    }
    protected abstract void onMyPause();

    protected void onPause() {
        super.onPause();
        //MobclickAgent.onPause(this);
//        JPushInterface.onPause(this);
        CustomRequestResponseManager.projectUtil_presenter_implement=null;
        CustomRequestResponseBeanDataManager.projectUtil_presenter_implement=null;
        onMyPause();
    }

    /*
     * @Override public boolean dispatchTouchEvent(MotionEvent ev) { if
     * (ev.getAction() == MotionEvent.ACTION_DOWN) { if
     * (ClickUtil.isFastDoubleClick()) { return true; } } return
     * super.dispatchTouchEvent(ev); }
     */
    public IntentUtil getIntentTool() {
        return intentTool;
    }

    /**
     * 返回键的监听
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {

            // 退出
            finish();
            overridePendingTransition(R.anim.slide_in_left,
                    R.anim.slide_out_right);

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (isFastDoubleClick()) {
                return true;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    long lastClickTime = System.currentTimeMillis();

    public boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (timeD >= 0 && timeD <= 500) {
            return true;
        } else {
            lastClickTime = time;
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        //UMShareAPI.get(this).release();//在使用分享或者授权的Activity中，重写onDestory()方法：内存泄漏
        //维护页面初始化默认值
        /*if(ProjectUtil_Presenter_Implement.getSingleton(context).getIsServerState()){
            ProjectUtil_Presenter_Implement.getSingleton(context).setIsServerState(false);
        }*/
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
    }

    @Override
    public void finish() {
        try {
            NiftyDialogBuilder.getInstance(context).dismiss();
            L.e("对话框关闭");
        }catch (Exception e){
            L.e("对话框关闭失败");
        }
        super.finish();
    }

    /**
     * finish当前Activity
     */
    public void FinishA() {
        finish();
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
    }

    /**
     * 设置隐藏Actionbar
     */
    public void setActionbarGone() {
        //设置Actionbar的高度
        acbr_Parent_Layout.setVisibility(View.GONE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        try{
           //UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        }catch (Exception e){
            e.printStackTrace();
        }
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        try {
           //UMShareAPI.get(this).onSaveInstanceState(outState);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
