package com.app.lotteryticket.MVP.View.Implement.Activity;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.app.common.CustomView.PullToRefreshFoot_Loading_Layout;
import com.app.common.CustomView.PullToRefreshHead_Loading_Layout;
import com.app.common.Public.RouterUrl;
import com.app.common.Util.ToastUtil;
import com.app.common.Util.VibratorUtil;
import com.app.lotteryticket.Adapter.Main_PlanContent_Adapter;
import com.app.lotteryticket.Base.Main_BaseActivity;
import com.app.lotteryticket.MVP.Contract.Main_PlanContent_Contract;
import com.app.lotteryticket.MVP.Presenter.Implement.Main_PlanContent_Presenter;
import com.app.lotteryticket.R;
import com.chenenyu.router.annotation.Route;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.extras.recyclerview.EmptyRecyclerView;
import com.handmark.pulltorefresh.library.extras.recyclerview.PullToRefreshRecyclerView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**计划内容
 * @ClassName: com.ygworld.MVP.View.Implement.Activity
 * @author: Administrator 杨重诚
 * @date: 2016/10/21:11:26
 */
@Route(value = RouterUrl.mainPlanContentRouterUrl,interceptors = RouterUrl.interceptor_UserInfo_RouterUrl)
public class Main_PlanContent_View extends Main_BaseActivity<Main_PlanContent_Contract.Presenter,Main_PlanContent_Presenter> implements Main_PlanContent_Contract.View {
    //下拉刷新父控件
    @BindView(R.id.category_goods_layout)
    LinearLayout mCategoryGoodsLayout;
    //下拉刷新组件
    PullToRefreshRecyclerView mPullRefreshRecycler;
    //列表适配器
    Main_PlanContent_Adapter mMain_planContent_adapter;
    private EmptyRecyclerView goodsRecyclerView;
    int scrollToPosition=0;//设置RecyclerView滚动到的位置
    private int countHttpMethod = 1;//总共有多少个请求网络数据的方法
    Subscription subscription;//用于间隔请求
    @Override
    protected void setContentView() {
        setContentView(R.layout.main_act_plancontent_layout);
    }
    //---------------------点击事件---------------------------------------
    @OnClick({R.id.acbr_text})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.acbr_text:
                //复制
                String content=mPresenter.copyContent();
                ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                cmb.setText(content.trim()); // 将内容放入粘贴管理器,在别的地方长按选择"粘贴"即可
                ToastUtil.RightImageToast(context, "计划内容复制成功");
                break;
        }
    }
    @Override
    protected void init() {
        mPresenter.getBundleValues(mBundle);
        initPullToRefreshRecyclerView();//初始化PullToRefresh
        requestHttpMethod();
    }

    @Override
    protected void setListeners() {
    }

    @Override
    protected void setTitleBar() {
        setActionbarBar("计划内容", R.color.app_color, R.color.white, true, true);
        acbr_right_text.setText("复制");
        acbr_right_text.setTextColor(context.getResources().getColor(R.color.white));
        acbr_right_text.setVisibility(View.VISIBLE);
    }
    /**
     * 初始化PullToRefreshRecyclerView
     */
    public void initPullToRefreshRecyclerView() {
        if(mPullRefreshRecycler==null){
            mPullRefreshRecycler=new PullToRefreshRecyclerView(context);
            mPullRefreshRecycler.setMode(PullToRefreshBase.Mode.BOTH);
            RecyclerView.LayoutParams params= new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,RecyclerView.LayoutParams.MATCH_PARENT);
            mPullRefreshRecycler.setLayoutParams(params);
            //获取RecyclerView实例
            goodsRecyclerView = mPullRefreshRecycler.getRefreshableView();
            //设置空视图
            View emptyView = LayoutInflater.from(context).inflate(R.layout.common_base_common_emptylist, null);
            emptyView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));
            mCategoryGoodsLayout.addView(emptyView);
            goodsRecyclerView.setEmptyView(emptyView);
            //添加下拉刷新
            mCategoryGoodsLayout.addView(mPullRefreshRecycler);
        }
        //设置刷新动画
        mPullRefreshRecycler.setHeaderLayout(new PullToRefreshHead_Loading_Layout(context));
        mPullRefreshRecycler.setFooterLayout(new PullToRefreshFoot_Loading_Layout(context, PullToRefreshBase.Mode.PULL_FROM_END));
        //设置layoutManager
        goodsRecyclerView.setLayoutManager(new GridLayoutManager(context,3));
        //--------------------------设置刷新监听-----------------------------------------
        mPullRefreshRecycler.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<EmptyRecyclerView>() {
            @Override
            public void onPullDownToRefresh(
                    PullToRefreshBase<EmptyRecyclerView> refreshView) {
                // TODO Auto-generated method stub
                //请求服务器数据的方法
                requestHttpMethod();
            }

            @Override
            public void onPullUpToRefresh(
                    PullToRefreshBase<EmptyRecyclerView> refreshView) {
                //上拉加载更多
            }
        });
    }

    @Override
    public void getBundleValues(Bundle mBundle){
        super.getBundleValues(mBundle);
    }

    /**
     * 请求服务器数据的方法
     */
    public void requestHttpMethod() {
        //初始化参数
        mPresenter.initData(countHttpMethod);
        //请求计划内容数据
        mPresenter.requestPlanContentData();
    }
    /**
     * 设置计划内容列表展示
     */
    @Override
    public void setPlanContentList(final List<String> beans) {
        if (beans == null) {
            return;
        }
        if(beans.size()>1){
            ToastUtil.RightImageToast(context,"亲，有计划内容了！"+beans.size());
            VibratorUtil.Vibrate((Activity) context, 10000);   //震动100ms
        }
        //设置Adapter
        if (mMain_planContent_adapter == null) {
            mMain_planContent_adapter = new Main_PlanContent_Adapter(context, beans);
            goodsRecyclerView.setAdapter(mMain_planContent_adapter);
        } else {
            mMain_planContent_adapter.setData(beans);
            mMain_planContent_adapter.notifyDataSetChanged();
        }
    }
    /**
     * 关闭刷新控件
     */
    @Override
    public void closeRefresh() {
        if (mPullRefreshRecycler != null) {
            mPullRefreshRecycler.onRefreshComplete();
        }
    }
    @Override
    protected void getData() {

    }
    @Override
    public void onResume() {
        super.onResume();
        Subscriber<Long> subscriber = new Subscriber<Long>() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable e) {
            }
            @Override
            public void onNext(Long l) {
                requestHttpMethod();
            }
        };
        subscription= Observable.interval(2,  TimeUnit.MINUTES)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(subscription!=null){
            subscription.unsubscribe();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(subscription!=null){
            subscription.unsubscribe();
        }
    }
}
