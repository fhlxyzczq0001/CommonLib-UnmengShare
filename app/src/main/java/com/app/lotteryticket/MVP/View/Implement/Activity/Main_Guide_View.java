package com.app.lotteryticket.MVP.View.Implement.Activity;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;

import com.app.common.MVP.Model.Bean.EventBus.LoginSuccess_EventBus;
import com.app.common.Public.RouterUrl;
import com.app.common.Util.WindowUtils;
import com.app.lotteryticket.Base.Main_BaseActivity;
import com.app.lotteryticket.MVP.Contract.Main_Guide_Contract;
import com.app.lotteryticket.MVP.Presenter.Implement.Main_Guide_Presenter;
import com.app.lotteryticket.R;
import com.chenenyu.router.annotation.Route;
import com.jazzy.viewpager.GuideViewPagers;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**引导页
 * @ClassName: com.ygworld.MVP.View.Implement.Activity
 * @author: Administrator 杨重诚
 * @date: 2016/10/21:11:26
 */
@Route(RouterUrl.mainGuideRouterUrl)
public class Main_Guide_View extends Main_BaseActivity<Main_Guide_Contract.Presenter,Main_Guide_Presenter> implements Main_Guide_Contract.View {
    @BindView(R.id.myParent_layout)
    LinearLayout myParent_layout;
    //引导页
    @BindView(R.id.guideViewPagers)
    GuideViewPagers guideViewPagers;
    @Override
    protected void setContentView() {
        setContentView(R.layout.main_act_guide_layout);
    }

    @Override
    protected void init() {
        //初始化引导页
        guideViewPagers.setGuideData(mPresenter.getDefaultGuidPage(),new int[]{R.mipmap.icon_home_menu_dot2,R.mipmap.icon_home_menu_dot1},R.mipmap.bg_guide1);
        guideViewPagers.setLoging_btContent("登录");
        //设置按钮背景
        guideViewPagers.setLogingBtSty(R.drawable.shap_powder_radio_bg);
        guideViewPagers.setTiYangBtSty(R.drawable.shap_powder_radio_bg);
    }

    @Override
    protected void setListeners() {
        guideViewPagers.getLogingBt().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 进入登录
                getIntentTool().intent_RouterTo(context, RouterUrl.userinfo_UserLogingRouterUrl);
                finish();
            }
        });
        guideViewPagers.getTiyanBt().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 进入首页
                getIntentTool().intent_RouterTo(context,  RouterUrl.mainMainActivityRouterUrl);
                finish();
            }
        });
    }

    @Override
    protected void setTitleBar() {
        setActionbarGone();//隐藏actionbar
        int StatusHeight = WindowUtils.getStatusHeight((Activity) context);// 获取状态栏高度
        myParent_layout.setPadding(0, StatusHeight, 0, 0);
    }
    @Override
    protected void getData() {

    }
    @Override
    public void onResume() {
        super.onResume();
        // 注册EventBus
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 注销EventBus
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * 登录成功执行的操作
     * @param eventBus
     */
    @Subscribe(threadMode = ThreadMode.BACKGROUND,sticky = true)
    public void logSuccess(LoginSuccess_EventBus eventBus) {
        if(eventBus.isLoginSuccess()){
            finish();
        }
    }
}
