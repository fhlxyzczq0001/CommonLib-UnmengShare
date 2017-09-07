package com.app.lotteryticket.MVP.View.Implement.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.app.common.CustomView.PullToRefreshFoot_Loading_Layout;
import com.app.common.CustomView.PullToRefreshHead_Loading_Layout;
import com.app.common.MVP.Presenter.Implement.ProjectUtil_Presenter_Implement;
import com.app.common.MVP.Presenter.Interface.ProjectUtil_Presenter_Interface;
import com.app.common.Public.Main_PublicCode;
import com.app.common.Public.RouterUrl;
import com.app.common.Util.Common_SharePer_UserInfo;
import com.app.common.Util.CustomDialogBuilder;
import com.app.lotteryticket.Base.Mian_BaseFragment;
import com.app.lotteryticket.MVP.Contract.Fragment.Main_UserCenterFragment_Contract;
import com.app.lotteryticket.MVP.Model.Bean.Main_UserCenterBean;
import com.app.lotteryticket.MVP.Presenter.Implement.Fragment.Main_UserCenterFragment_Presenter;
import com.app.lotteryticket.MVP.View.Implement.Activity.Main_MainActivity_View;
import com.app.lotteryticket.R;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.handmark.pulltorefresh.library.CustomView.ObservableScrollView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefresh_ObservableScrollView;
import com.leon.lib.settingview.LSettingItem;

import butterknife.BindView;
import butterknife.OnClick;

import static com.app.lotteryticket.R.id.pull_refresh_scrollview;

/**
 * 用户中心fragment
 * Created by ${杨重诚} on 2017/7/13.
 */
@SuppressLint("ValidFragment")
public class Main_UserCenter_Fragment extends Mian_BaseFragment<Main_UserCenterFragment_Contract.Presenter, Main_UserCenterFragment_Presenter> implements Main_UserCenterFragment_Contract.View, LSettingItem.OnLSettingItemClick {
    //下拉刷新pull_refresh_scrollview
    @BindView(pull_refresh_scrollview)
    PullToRefresh_ObservableScrollView mPullRefreshScrollView;
    //账号
    @BindView(R.id.itemAccount)
    LSettingItem itemAccount;
    //修改密码
    @BindView(R.id.itemModifyPassword)
    LSettingItem itemModifyPassword;
    //账号时效
    @BindView(R.id.itemAccountAging)
    LSettingItem itemAccountAging;
    //联系qq
    @BindView(R.id.itemContacQQ)
    LSettingItem itemContacQQ;
    //充值qq
    @BindView(R.id.itemRechargeQQ)
    LSettingItem itemRechargeQQ;
    //总共有多少个请求网络数据的方法
    private int countHttpMethod = 1;
    ProjectUtil_Presenter_Interface mProjectUtil_presenter_interface;

    public static Fragment newInstance(Bundle bundle) {
        Main_UserCenter_Fragment fragment = new Main_UserCenter_Fragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    //---------------------点击事件---------------------------------------
    @OnClick({R.id.setting_quit_bnt})
    public void onClicks(View view) {
        switch (view.getId()) {
            case R.id.setting_quit_bnt:
                //退出按钮点击事件
                CustomDialogBuilder customDialogBuilder = new CustomDialogBuilder(context);
                final NiftyDialogBuilder dialogBuilder = customDialogBuilder.showDialog(getResources().getString(R.string.app_name), "您确定要退出当前帐号吗？", R.color.account_text_gray, "取消", R.color.gray, "确定", R.color.app_color);
                dialogBuilder.setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogBuilder.dismiss();
                    }
                });
                dialogBuilder.setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.requestUserInfoLogout();
                        dialogBuilder.dismiss();
                    }
                });
                break;
        }
    }

    //==========LSettingItem列表点击事件===================
    @Override
    public void click(boolean isChecked, View view) {
        Bundle bundle = new Bundle();
        if (view.getId() == R.id.itemModifyPassword) {
            //修改密码点击事件
            getIntentTool().intent_RouterTo(context, RouterUrl.userinfo_UserModifyPasswordRouterUrl);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
    }

    @Override
    protected View setContentView() {
        return inflater.inflate(R.layout.main_fragment_user_center_layout, null);
    }

    @Override
    public void initMyView() {
        super.initMyView();
    }

    @Override
    protected void init() {
        mProjectUtil_presenter_interface = new ProjectUtil_Presenter_Implement(context);
        //初始化PullRefreshScrollView
        initPullRefreshScrollView();
        //请求服务器数据的方法
        requestHttpMethod();
    }

    @Override
    protected void setTitleBar() {
        setActionbarBar(getResources().getString(R.string.app_name), R.color.app_color, R.color.white, false, true);
    }

    @Override
    protected void setListeners() {
        itemModifyPassword.setmOnLSettingItemClick(this);
    }


    /**
     * 初始化PullRefreshScrollView
     */
    public void initPullRefreshScrollView() {
        mPullRefreshScrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        //设置刷新动画
        mPullRefreshScrollView.setHeaderLayout(new PullToRefreshHead_Loading_Layout(context));
        mPullRefreshScrollView.setFooterLayout(new PullToRefreshFoot_Loading_Layout(context, PullToRefreshBase.Mode.PULL_FROM_END));
        //--------------------------设置刷新监听-----------------------------------------
        mPullRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ObservableScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ObservableScrollView> refreshView) {
                // 下拉刷新
                //请求服务器数据的方法
                requestHttpMethod();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ObservableScrollView> refreshView) {

            }
        });
    }

    /**
     * 请求服务器数据的方法
     */
    public void requestHttpMethod() {
        if(mProjectUtil_presenter_interface.isLoging()){
            //初始化参数
            mPresenter.initData(countHttpMethod);
            //请求用户中心数据
            mPresenter.requestUserCenterData();
        }
    }

    /**
     * 关闭刷新控件
     */
    @Override
    public void closeRefresh() {
        if (mPullRefreshScrollView != null) {
            mPullRefreshScrollView.onRefreshComplete();
        }
    }

    /**
     * 设置用户中心数据
     */
    @Override
    public void setUserCenterData(Main_UserCenterBean userCenterData) {
        //账号
        itemAccount.setRightText(Common_SharePer_UserInfo.sharePre_GetUserID());
        //账号时效
        itemAccountAging.setRightText(userCenterData.getAccountAging());
    }

    /**
     * 用户退出应用成功后的操作
     */
    @Override
    public void quitSuccess() {
        getIntentTool().intent_destruction_other_activity_RouterTo(context, RouterUrl.mainMainActivityRouterUrl + "?tab=" + Main_PublicCode.MAIN_TAB_HOME.toString());
    }

    @Override
    public void onResume() {
        super.onResume();
        if(Main_MainActivity_View.menuIndex==3){
            //请求服务器数据的方法
            requestHttpMethod();
        }
    }
}
