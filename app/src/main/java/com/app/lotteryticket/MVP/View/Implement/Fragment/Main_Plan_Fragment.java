package com.app.lotteryticket.MVP.View.Implement.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.app.common.Public.RouterUrl;
import com.app.common.Util.ToastUtil;
import com.app.lotteryticket.Base.Mian_BaseFragment;
import com.app.lotteryticket.MVP.Contract.Fragment.Main_PlanFragment_Contract;
import com.app.lotteryticket.MVP.Presenter.Implement.Fragment.Main_PlanFragment_Presenter;
import com.app.lotteryticket.R;
import com.leon.lib.settingview.LSettingItem;

import butterknife.BindView;

/**
 * 首页计划fragment
 * Created by ${杨重诚} on 2017/7/13.
 */
@SuppressLint("ValidFragment")
public class Main_Plan_Fragment extends Mian_BaseFragment<Main_PlanFragment_Contract.Presenter, Main_PlanFragment_Presenter> implements Main_PlanFragment_Contract.View , LSettingItem.OnLSettingItemClick{
    @BindView(R.id.itemPlan3)
    LSettingItem itemPlan3;
    @BindView(R.id.itemPlan5)
    LSettingItem itemPlan5;
    public static Fragment newInstance(Bundle bundle) {
        Main_Plan_Fragment fragment = new Main_Plan_Fragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    //==========LSettingItem列表点击事件===================
    @Override
    public void click(boolean isChecked, View view) {
        Bundle bundle = new Bundle();
        if (view.getId() == R.id.itemPlan3) {
            ToastUtil.RightImageToast(context,"开发中，敬请期待！");
            //getIntentTool().intent_RouterTo(context,RouterUrl.mainPlanCenterRouterUrl+"?type=3");
        }else if(view.getId() == R.id.itemPlan5){
            getIntentTool().intent_RouterTo(context,RouterUrl.mainPlanCenterRouterUrl+"?type=5");
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
    }

    @Override
    protected View setContentView() {
        return inflater.inflate(R.layout.main_fragment_plan_layout, null);
    }

    @Override
    public void initMyView() {
        super.initMyView();
    }

    @Override
    protected void init() {
    }

    @Override
    protected void setTitleBar() {
        setActionbarBar(getResources().getString(R.string.app_name), R.color.app_color, R.color.white, false, true);
    }

    @Override
    protected void setListeners() {
        itemPlan3.setmOnLSettingItemClick(this);
        itemPlan5.setmOnLSettingItemClick(this);
    }

}
