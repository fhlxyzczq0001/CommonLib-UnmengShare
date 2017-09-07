package com.app.lotteryticket.MVP.View.Implement.Activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.app.common.Base.CommonApplication;
import com.app.common.CustomView.BottomNavigationViewEx;
import com.app.common.MVP.Presenter.Implement.ProjectUtil_Presenter_Implement;
import com.app.common.MVP.Presenter.Interface.ProjectUtil_Presenter_Interface;
import com.app.common.Public.Main_PublicCode;
import com.app.common.Public.RouterUrl;
import com.app.common.Util.Common_SharePer_UserInfo;
import com.app.lotteryticket.Base.Main_BaseActivity;
import com.app.lotteryticket.MVP.Contract.Main_MainActivity_Contract;
import com.app.lotteryticket.MVP.Presenter.Implement.Main_MainActivity_Presenter;
import com.app.lotteryticket.MVP.View.Implement.Fragment.Main_Home_Fragment;
import com.app.lotteryticket.MVP.View.Implement.Fragment.Main_Plan_Fragment;
import com.app.lotteryticket.MVP.View.Implement.Fragment.Main_Recomend_Fragment;
import com.app.lotteryticket.MVP.View.Implement.Fragment.Main_UserCenter_Fragment;
import com.app.lotteryticket.R;
import com.chenenyu.router.annotation.Route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;


/**
 * Created by ${杨重诚} on 2017/7/14.
 */
@Route(RouterUrl.mainMainActivityRouterUrl)
public class Main_MainActivity_View extends Main_BaseActivity<Main_MainActivity_Contract.Presenter,Main_MainActivity_Presenter> implements Main_MainActivity_Contract.View {
    //最外层父布局
    @BindView(R.id.parentLayout)
    LinearLayout mParentLayout;

    //FragmentManager管理器
    FragmentManager mFragmentManager = null;
    //FragmentTransaction事务
    FragmentTransaction mTransaction=null;
    //当前展示的fragment
    Fragment currentFragment = null;
    //所有的fragment集合
    List<Fragment> mFragmentList;
    BottomNavigationViewEx navigation;
    // 选中第几个tab的标识
    public static int menuIndex = 0;
    int[] tabIds = {R.id.main_title_home, R.id.main_title_recommend,
            R.id.main_title_plan, R.id.main_title_myinfo};//存储底部tab对应的id
    //存储底部tab对应的name
    String[]tabNames={Main_PublicCode.MAIN_TAB_HOME.toString(),Main_PublicCode.MAIN_TAB_RECOMMEND.toString(),
            Main_PublicCode.MAIN_TAB_PLAN.toString(),Main_PublicCode.MAIN_TAB_MYINFO.toString(),};
    //存储name和id的对应关系
    Map<String,Integer> tabMaps;
    ProjectUtil_Presenter_Interface mProjectUtil_presenter_interface;
    @Override
    protected void setContentView() {
        setContentView(R.layout.main_act_main);
    }

    @Override
    protected void init() {
        mProjectUtil_presenter_interface=new ProjectUtil_Presenter_Implement(context);
        mFragmentManager = getSupportFragmentManager();
        initTabMaps();
        initFragment();
        loadFragmentIndex(0);
        //记录主app已经启动
        CommonApplication.getInstance().setMainAppIsStart();
    }
    /**
     * 初始化底部tab的map存储对应关系name——id
     */
    private void initTabMaps(){
        tabMaps=new HashMap<>();
        for(int i=0;i<tabNames.length;i++){
            tabMaps.put(tabNames[i],tabIds[i]);
        }
    }
    @Override
    public void initMyView() {
        super.initMyView();
        navigation = (BottomNavigationViewEx) findViewById(R.id.mainActivity_navigation);
        navigation.enableAnimation(false);
        navigation.enableShiftingMode(false);
        navigation.enableItemShiftingMode(false);
        navigation.setTextSize(12);
//        BottomNavigationViewHelper.disableShiftMode(navigation);
        int[][] states = new int[][]{
                new int[]{-android.R.attr.state_checked},
                new int[]{android.R.attr.state_checked}
        };

        int[] colors = new int[]{getResources().getColor(R.color.app_navigation),
                getResources().getColor(R.color.app_color)
        };
        ColorStateList csl = new ColorStateList(states, colors);
        navigation.setItemTextColor(csl);
        navigation.setItemIconTintList(csl);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    @Override
    protected void setListeners() {

    }

    @Override
    protected void setTitleBar() {
        setActionbarGone();//隐藏Actionbar
    }

    @Override
    protected void getData() {
    }
    /**
     * 初始化导航数据
     */
    public void initFragment() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new Main_Home_Fragment());
        mFragmentList.add(new Main_Recomend_Fragment());
        mFragmentList.add(new Main_Plan_Fragment());
        mFragmentList.add(new Main_UserCenter_Fragment());
    }

    /**
     * 需加载的底部导航位置
     * @param index
     */
    public void loadFragmentIndex(int index) {
        //初始化FragmentTransaction
        mTransaction = mFragmentManager.beginTransaction();
        Fragment fragment = mFragmentManager.findFragmentByTag("Main_MainActivity_View"+index);
        if(currentFragment != null){
            mTransaction.hide(currentFragment);
        }
        if (fragment != null) {
            mTransaction.attach(fragment);
            mTransaction.show(fragment);
            fragment.onResume();
        }else {
            fragment = mFragmentList.get(index);
            mTransaction.add(R.id.mainActivity_content, fragment, "Main_MainActivity_View"+index);
        }
        currentFragment = fragment;
        mTransaction.commitAllowingStateLoss();
    }
    /**
     * 底部按钮的点击处理事件
     */
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if(item.getItemId()==tabIds[0]){
                menuIndex = 0;
                setNavigationItemClick(menuIndex);
                return true;
            }else if(item.getItemId()==tabIds[1]){
                menuIndex = 1;
                setNavigationItemClick(menuIndex);
                return true;
            }else if(item.getItemId()==tabIds[2]){
                menuIndex = 2;
                setNavigationItemClick(menuIndex);
                return true;
            }else if(item.getItemId()==tabIds[3]){
                menuIndex = 3;
                setNavigationItemClick(menuIndex);
                if(myApplication.getUseInfoVo()==null&&( Common_SharePer_UserInfo.sharePre_GetUserID()==null||Common_SharePer_UserInfo.sharePre_GetUserID().isEmpty())){
                    getIntentTool().intent_RouterTo(context,RouterUrl.userinfo_UserLogingRouterUrl);
                }
                return true;
            }
            return false;
        }
    };

    /**
     * 设置navigage点击事件
     * @param menuIndex  当前点击的索引
     */
    public void setNavigationItemClick(int menuIndex){
        loadFragmentIndex(menuIndex);
    }
    @Override
    public void getBundleValues(Bundle mBundle) {
        super.getBundleValues(mBundle);
        setNavigationTabIndex(mBundle);
    }
    /**
     * 接收再次进入主activity的传参
     *
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.setIntent(intent);
        if (intent.getExtras() != null) {
            setNavigationTabIndex(intent.getExtras());
        }
    }
    /**
     * 设置tab被动切换位置
     *
     * @param mBundle
     */
    private void setNavigationTabIndex(Bundle mBundle) {
        if (mBundle != null) {
            if (mBundle.getString("tab") != null) {
                String tabName = mBundle.getString("tab");
                if(tabMaps==null){
                    for(int i=0;i<tabNames.length;i++){
                        if(tabName.contains(tabNames[i])){
                            menuIndex=i;
                        }
                    }
                }else {
                    navigation.setSelectedItemId(tabMaps.get(tabName));
                }
            }
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if(menuIndex==3){
            if(!mProjectUtil_presenter_interface.isLoging()){
                navigation.setSelectedItemId(R.id.main_title_home);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        menuIndex=-1;
    }

    //按返回键退出的监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            mPresenter.exit();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
