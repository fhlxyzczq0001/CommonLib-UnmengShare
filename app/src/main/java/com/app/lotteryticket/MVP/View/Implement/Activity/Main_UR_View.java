package com.app.lotteryticket.MVP.View.Implement.Activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.app.common.CustomView.PullToRefreshFoot_Loading_Layout;
import com.app.common.CustomView.PullToRefreshHead_Loading_Layout;
import com.app.common.MVP.Model.Bean.CommonMenuBean;
import com.app.common.Public.RouterUrl;
import com.app.lotteryticket.Adapter.Main_URL_Menu_Adapter;
import com.app.lotteryticket.Base.Main_BaseActivity;
import com.app.lotteryticket.MVP.Contract.Main_URL_View_Contract;
import com.app.lotteryticket.MVP.Presenter.Implement.Main_URL_View_Presenter;
import com.app.lotteryticket.R;
import com.chenenyu.router.annotation.Route;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.extras.recyclerview.EmptyRecyclerView;
import com.handmark.pulltorefresh.library.extras.recyclerview.PullToRefreshRecyclerView;

import java.util.List;

import butterknife.BindView;

/**
 * 网址页面
 * Created by ${杨重诚} on 2017/7/13.
 */
@Route(RouterUrl.mainURLViewRouterUrl)
public class Main_UR_View extends Main_BaseActivity<Main_URL_View_Contract.Presenter, Main_URL_View_Presenter> implements Main_URL_View_Contract.View {
    //下拉刷新父控件
    @BindView(R.id.category_goods_layout)
    LinearLayout mCategoryGoodsLayout;
    //下拉刷新组件
    PullToRefreshRecyclerView mPullRefreshRecycler;
    private EmptyRecyclerView goodsRecyclerView;
    //总共有多少个请求网络数据的方法
    private int countHttpMethod=1;
    //推荐列表适配器
    Main_URL_Menu_Adapter mMain_url_menu_adapter;
    @Override
    protected void setContentView() {
        setContentView(R.layout.main_actt_url_layout);
    }

    @Override
    protected void init() {
        initPullToRefreshRecyclerView();//初始化PullToRefresh
        requestHttpMethod();
    }

    @Override
    protected void setListeners() {
    }

    @Override
    protected void setTitleBar() {
        setActionbarBar("网址", R.color.app_color, R.color.white, true, true);
    }
    @Override
    protected void getData() {

    }
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
        mPresenter.requestURLData();
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
    public void setURLMenuList(List<CommonMenuBean> beans) {
        if (beans == null) {
            return;
        }
        if (mMain_url_menu_adapter == null) {
            mMain_url_menu_adapter = new Main_URL_Menu_Adapter(context, beans);
            goodsRecyclerView.setAdapter(mMain_url_menu_adapter);
        } else {
           /* mMain_recomend_menu_adapter.replaceAll(beans);
            mMain_recomend_menu_adapter.notifyDataSetChanged();*/
        }
    }
}
