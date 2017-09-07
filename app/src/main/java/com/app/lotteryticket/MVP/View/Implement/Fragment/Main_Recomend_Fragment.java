package com.app.lotteryticket.MVP.View.Implement.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.app.common.CustomView.PullToRefreshFoot_Loading_Layout;
import com.app.common.CustomView.PullToRefreshHead_Loading_Layout;
import com.app.common.MVP.Model.Bean.CommonMenuBean;
import com.app.lotteryticket.Adapter.Main_Recomend_Menu_Adapter;
import com.app.lotteryticket.Base.Mian_BaseFragment;
import com.app.lotteryticket.MVP.Contract.Fragment.Main_RecommendFragment_Contract;
import com.app.lotteryticket.MVP.Presenter.Implement.Fragment.Main_RecommendFragment_Presenter;
import com.app.lotteryticket.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.extras.recyclerview.EmptyRecyclerView;
import com.handmark.pulltorefresh.library.extras.recyclerview.PullToRefreshRecyclerView;

import java.util.List;

import butterknife.BindView;

/**
 * 首页推荐fragment
 * Created by ${杨重诚} on 2017/7/13.
 */
@SuppressLint("ValidFragment")
public class Main_Recomend_Fragment extends Mian_BaseFragment<Main_RecommendFragment_Contract.Presenter, Main_RecommendFragment_Presenter> implements Main_RecommendFragment_Contract.View {
    //下拉刷新父控件
    @BindView(R.id.category_goods_layout)
    LinearLayout mCategoryGoodsLayout;
    //下拉刷新组件
    PullToRefreshRecyclerView mPullRefreshRecycler;
    private EmptyRecyclerView goodsRecyclerView;
    //总共有多少个请求网络数据的方法
    private int countHttpMethod=1;
    //推荐列表适配器
    Main_Recomend_Menu_Adapter mMain_recomend_menu_adapter;
    public static Fragment newInstance(Bundle bundle) {
        Main_Recomend_Fragment fragment = new Main_Recomend_Fragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
    }

    @Override
    protected View setContentView() {
        return inflater.inflate(R.layout.main_fragment_recommend_layout, null);
    }

    @Override
    public void initMyView() {
        super.initMyView();
    }

    @Override
    protected void init() {
        initPullToRefreshRecyclerView();//初始化PullToRefresh
        requestHttpMethod();
    }

    @Override
    protected void setTitleBar() {
        setActionbarBar(getResources().getString(R.string.app_name), R.color.app_color, R.color.white, false, true);
    }

    @Override
    protected void setListeners() {}


    /**
     * 初始化PullToRefreshRecyclerView
     */
    public void initPullToRefreshRecyclerView() {
        if(mPullRefreshRecycler==null){
            mPullRefreshRecycler=new PullToRefreshRecyclerView(context);
            mPullRefreshRecycler.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
            RecyclerView.LayoutParams params= new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,RecyclerView.LayoutParams.MATCH_PARENT);
            mPullRefreshRecycler.setLayoutParams(params);
            //获取RecyclerView实例
            goodsRecyclerView = mPullRefreshRecycler.getRefreshableView();
            //添加下拉刷新
            mCategoryGoodsLayout.addView(mPullRefreshRecycler);
        }
        //设置刷新动画
        mPullRefreshRecycler.setHeaderLayout(new PullToRefreshHead_Loading_Layout(context));
        mPullRefreshRecycler.setFooterLayout(new PullToRefreshFoot_Loading_Layout(context, PullToRefreshBase.Mode.PULL_FROM_END));
        //设置layoutManager
        goodsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        //--------------------------设置刷新监听-----------------------------------------
        mPullRefreshRecycler.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<EmptyRecyclerView>() {
            @Override
            public void onPullDownToRefresh(
                    PullToRefreshBase<EmptyRecyclerView> refreshView) {
                // TODO Auto-generated method stub
                // 下拉刷新
                requestHttpMethod();
            }

            @Override
            public void onPullUpToRefresh(
                    PullToRefreshBase<EmptyRecyclerView> refreshView) {
                //上拉加载更多
            }
        });
    }
    /**
     * 请求服务器数据的方法
     */
    public void requestHttpMethod() {
        //初始化参数
        mPresenter.initData(countHttpMethod);
        //请求推荐数据
        mPresenter.requestRecommendData();
    }
    /**
     * 关闭刷新控件
     */
    @Override
    public void closeRefresh() {
        if(mPullRefreshRecycler!=null){
            mPullRefreshRecycler.onRefreshComplete();
        }
    }
    /**
     * 设置菜单列表展示
     */
    @Override
    public void setRecommendMenuList(List<CommonMenuBean> beans) {
        if (beans == null) {
            return;
        }
        if (mMain_recomend_menu_adapter == null) {
            mMain_recomend_menu_adapter = new Main_Recomend_Menu_Adapter(context, beans);
            goodsRecyclerView.setAdapter(mMain_recomend_menu_adapter);
        } else {
           /* mMain_recomend_menu_adapter.replaceAll(beans);
            mMain_recomend_menu_adapter.notifyDataSetChanged();*/
        }
    }
}
