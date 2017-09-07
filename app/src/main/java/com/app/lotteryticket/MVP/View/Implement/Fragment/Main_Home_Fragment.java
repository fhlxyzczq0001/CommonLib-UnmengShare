package com.app.lotteryticket.MVP.View.Implement.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.common.CustomView.AutoScrollTextView;
import com.app.common.CustomView.PullToRefreshFoot_Loading_Layout;
import com.app.common.CustomView.PullToRefreshHead_Loading_Layout;
import com.app.common.MVP.Model.Bean.CommonMenuBean;
import com.app.common.MVP.Model.Bean.Common_Notice_Bean;
import com.app.common.Public.RouterUrl;
import com.app.common.Util.MyAnimation;
import com.app.common.Util.Textutils;
import com.app.lotteryticket.Adapter.Main_Home_Menu_Adapter;
import com.app.lotteryticket.Adapter.Main_Home_WinningNumbers_Adapter;
import com.app.lotteryticket.Base.Mian_BaseFragment;
import com.app.lotteryticket.MVP.Contract.Fragment.Main_HomeFragment_Contract;
import com.app.lotteryticket.MVP.Model.Bean.Main_PrizeCaseBean;
import com.app.lotteryticket.MVP.Presenter.Implement.Fragment.Main_HomeFragment_Presenter;
import com.app.lotteryticket.R;
import com.ddt.countdownview.CountdownView;
import com.ddt.countdownview.DynamicConfig;
import com.handmark.pulltorefresh.library.CustomView.ObservableScrollView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefresh_ObservableScrollView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * 大首页fragment
 * Created by ${杨重诚} on 2017/7/13.
 */
@SuppressLint("ValidFragment")
public class Main_Home_Fragment extends Mian_BaseFragment<Main_HomeFragment_Contract.Presenter, Main_HomeFragment_Presenter> implements Main_HomeFragment_Contract.View {
    // 公告布局
    @BindView(R.id.announcement_layout)
    RelativeLayout announcement_layout;
    //循环滚动的通知消息
    @BindView(R.id.autoScrollTextView)
    AutoScrollTextView autoScrollTextView;
    //下拉刷新pull_refresh_scrollview
    @BindView(R.id.pull_refresh_scrollview)
    PullToRefresh_ObservableScrollView mPullRefreshScrollView;
    //中奖号码的RecyclerView
    @BindView(R.id.recyclerWinningNumbers)
    RecyclerView recyclerWinningNumbers;
    //其他功能菜单的RecyclerView
    @BindView(R.id.recyclerOtherFunction)
    RecyclerView recyclerOtherFunction;
    //期号
    @BindView(R.id.textPeriodNum)
    TextView textPeriodNum;
    //倒计时
    @BindView(R.id.countdownViewTime)
    CountdownView countdownViewTime;

    //总共有多少个请求网络数据的方法
    private int countHttpMethod=2;
    //下拉刷新内部ScrollView
    ObservableScrollView refreshScrollView;
    //中奖号码适配器
    Main_Home_WinningNumbers_Adapter mMainHomeWinningNumbersAdapter;
    //其他功能菜单适配器
    Main_Home_Menu_Adapter mMain_home_menu_adapter;
    //标识是否是下拉刷新
    private boolean isPullDownToRefresh=false;
    public static Fragment newInstance(Bundle bundle) {
        Main_Home_Fragment fragment = new Main_Home_Fragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    //---------------------点击事件---------------------------------------
    @OnClick({R.id.announcement_layout})
    public void onClicks(View view) {
        switch (view.getId()) {
            case R.id.announcement_layout:
                getIntentTool().intent_RouterTo(context, RouterUrl.userinfo_UserNoticeRouterUrl);
                break;
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
    }

    @Override
    protected View setContentView() {
        return inflater.inflate(R.layout.main_fragment_home_layout, null);
    }

    @Override
    public void initMyView() {
        super.initMyView();
    }

    @Override
    protected void init() {
        //初始化PullRefreshScrollView
        initPullRefreshScrollView();
       //初始化RecyclerView
        initRecyclerView();
        //请求服务器数据的方法
        requestHttpMethod();
    }

    @Override
    protected void setTitleBar() {
        setActionbarBar(getResources().getString(R.string.app_name), R.color.app_color, R.color.white, false, true);
    }

    @Override
    protected void setListeners() {}


    /**
     * 初始化PullRefreshScrollView
     */
    public void initPullRefreshScrollView() {
        refreshScrollView=mPullRefreshScrollView.getRefreshableView();
        mPullRefreshScrollView.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);
        mPullRefreshScrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        //设置刷新动画
        mPullRefreshScrollView.setHeaderLayout(new PullToRefreshHead_Loading_Layout(context));
        mPullRefreshScrollView.setFooterLayout(new PullToRefreshFoot_Loading_Layout(context, PullToRefreshBase.Mode.PULL_FROM_END));
        //--------------------------设置刷新监听-----------------------------------------
        mPullRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ObservableScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ObservableScrollView> refreshView) {
                // 下拉刷新
                isPullDownToRefresh=true;
                //请求服务器数据的方法
                requestHttpMethod();
            }
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ObservableScrollView> refreshView) {

            }
        });
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView(){
        //设置中奖号码的
        recyclerWinningNumbers.setLayoutManager(new GridLayoutManager(context,5));
        recyclerWinningNumbers.setNestedScrollingEnabled(false);
        //设置其他功能菜单的
        recyclerOtherFunction.setLayoutManager(new GridLayoutManager(context,2));
        recyclerOtherFunction.setNestedScrollingEnabled(false);
    }

    /**
     * 填充循环滚动消息的滚动view
     * @param notice_array
     */
    public List<View> initAutoScroll_Views(List<Common_Notice_Bean> notice_array) {
        List<View> autoScrollViewList = new ArrayList<View>();
        for (int i = 0; i < notice_array.size(); i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.common_include_auto_scroll_textview, null);
            TextView textView = (TextView) view.findViewById(R.id.texts);
            Common_Notice_Bean noticeBean=notice_array.get(i);
            textView.setText(noticeBean.getTitle());
            //添加到循环滚动数组里面去
            autoScrollViewList.add(view);
        }
        return autoScrollViewList;
    }

    /**
     * 设置循环滚动消息的控件
     * @param notice_array
     */
    @Override
    public void setAutoScrollTextView(final List<Common_Notice_Bean> notice_array) {
        List<View> views = initAutoScroll_Views(notice_array);
        autoScrollTextView.setViews(views);
        /**
         * 设置item_view的监听
         */
        autoScrollTextView
                .setOnItemClickListener(new AutoScrollTextView.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position, View view) {
                        getIntentTool().intent_RouterTo(context, RouterUrl.userinfo_UserNoticeRouterUrl);
                    }
                });
    }
    /**
     * 请求服务器数据的方法
     */
    public void requestHttpMethod() {
        //初始化参数
        mPresenter.initData(countHttpMethod);
        //请求开奖情况数据
        mPresenter.requestPrizeCaseData();
       //请求公告数据
        mPresenter.requestNoticeData();
        //请求其他功能菜单数据
        mPresenter.requestOtherFunctionData();
    }
    /**
     * 关闭刷新控件
     */
    @Override
    public void closeRefresh() {
        if(mPullRefreshScrollView!=null){
            mPullRefreshScrollView.onRefreshComplete();
        }
    }
    /**
     * 设置开奖情况说明
     * @param prizeCase
     */
    @Override
    public void setPrizeCase(Main_PrizeCaseBean prizeCase) {
        if (prizeCase == null) {
            return;
        }
        isPullDownToRefresh=false;//设置默认
        //设置期号
        textPeriodNum.setText(Textutils.checkText(prizeCase.getPeriodNum()));
        //设置中奖号码
        List<String> winNums= Arrays.asList( getNewResults(prizeCase.getWinningNumbers()).split(","));
        if (mMainHomeWinningNumbersAdapter == null) {
            mMainHomeWinningNumbersAdapter = new Main_Home_WinningNumbers_Adapter(context, winNums);
            recyclerWinningNumbers.setAdapter(mMainHomeWinningNumbersAdapter);
        } else {
            mMainHomeWinningNumbersAdapter.setData(winNums);
            mMainHomeWinningNumbersAdapter.notifyDataSetChanged();
        }
    }
    private String getNewResults(String results){
        StringBuilder newResults=new StringBuilder();
        if(!results.isEmpty()){
            for(int i=0;i<results.length();i++){
                if(i<results.length()-1){
                    newResults.append(results.charAt(i)).append(",");
                }else {
                    newResults.append(results.charAt(i));
                }
            }
            return newResults.toString();
        }
        return "";
    }
    /**
     * 设置其他功能菜单列表
     * @param commonMenuBeen
     */
    @Override
    public void setOtherFunctionMenuList(List<CommonMenuBean> commonMenuBeen) {
        if (commonMenuBeen == null) {
            return;
        }
        if (mMain_home_menu_adapter == null) {
            mMain_home_menu_adapter = new Main_Home_Menu_Adapter(context, commonMenuBeen);
            recyclerOtherFunction.setAdapter(mMain_home_menu_adapter);
        } else {
            mMain_home_menu_adapter.setData(commonMenuBeen);
            mMain_home_menu_adapter.notifyDataSetChanged();
        }
    }
    /**
     * 设置倒计时
     * @param countTime
     */
    @Override
    public void setCountTime(long countTime) {
        countdownViewTime.start(countTime);
        DynamicConfig.Builder dynamicConfigBuilder = new DynamicConfig.Builder();
        if ((countTime / 60 / 60 / 1000) > 1) {
            dynamicConfigBuilder.setShowDay(false)
                    .setShowHour(true).setShowMinute(true)
                    .setShowSecond(true).setShowMillisecond(false);
        } else {// 小于一小时则显示分、秒、毫秒
            dynamicConfigBuilder.setShowDay(false)
                    .setShowHour(false).setShowMinute(true)
                    .setShowSecond(true).setShowMillisecond(true);
        }
        countdownViewTime.dynamicShow(dynamicConfigBuilder
                .build());
        countdownViewTime.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {
                //设置开奖动画
                recyclerWinningNumbers.startAnimation(MyAnimation.shakeReverseAnimation(5));
                Observable.timer(90000 , TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
                                //清除开奖动画
                                recyclerWinningNumbers.clearAnimation();
                                //请求服务器数据
                                requestHttpMethod();
                            }
                        });
            }
        });
    }
    /**
     * 获取是否是下拉刷新
     * @return
     */
    @Override
    public boolean getIsPullDownToRefresh() {
        return isPullDownToRefresh;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
