package com.app.lotteryticket.MVP.View.Implement.Activity;

import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.app.common.CustomView.PullToRefreshFoot_Loading_Layout;
import com.app.common.CustomView.PullToRefreshHead_Loading_Layout;
import com.app.common.MVP.Model.Bean.CommonMenuBean;
import com.app.common.Public.RouterUrl;
import com.app.common.Util.ToastUtil;
import com.app.lotteryticket.Adapter.Main_PlanCenter_List_Adapter;
import com.app.lotteryticket.Adapter.Main_PlanCenter_Menu_Adapter;
import com.app.lotteryticket.Base.Main_BaseActivity;
import com.app.lotteryticket.MVP.Contract.Main_Plan_Contract;
import com.app.lotteryticket.MVP.Model.Bean.Main_PlanCenterBean;
import com.app.lotteryticket.MVP.Presenter.Implement.Main_PlanCenter_Presenter;
import com.app.lotteryticket.R;
import com.chenenyu.router.annotation.Route;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.extras.recyclerview.EmptyRecyclerView;
import com.handmark.pulltorefresh.library.extras.recyclerview.PullToRefreshRecyclerView;

import java.util.List;

import butterknife.BindView;

/**计划中心
 * @ClassName: com.ygworld.MVP.View.Implement.Activity
 * @author: Administrator 杨重诚
 * @date: 2016/10/21:11:26
 */
@Route(value = RouterUrl.mainPlanCenterRouterUrl,interceptors = RouterUrl.interceptor_UserInfo_RouterUrl)
public class Main_PlanCenter_View extends Main_BaseActivity<Main_Plan_Contract.Presenter,Main_PlanCenter_Presenter> implements Main_Plan_Contract.View {
    //下拉刷新父控件
    @BindView(R.id.category_goods_layout)
    LinearLayout mCategoryGoodsLayout;
    //计划中心菜单的RecyclerView
    @BindView(R.id.recyclerPlanMenu)
    RecyclerView recyclerPlanMenu;
    //下拉刷新组件
    PullToRefreshRecyclerView mPullRefreshRecycler;
    //菜单适配器
    Main_PlanCenter_Menu_Adapter mMain_planCenter_menu_adapter;
    //列表适配器
    Main_PlanCenter_List_Adapter mMain_planCenter_list_adapter;
    private EmptyRecyclerView goodsRecyclerView;
    int scrollToPosition=0;//设置RecyclerView滚动到的位置
    private int countHttpMethod = 1;//总共有多少个请求网络数据的方法
    @Override
    protected void setContentView() {
        setContentView(R.layout.main_act_plan_layout);
    }

    @Override
    protected void init() {
        mPresenter.getBundleValues(mBundle);
        //设置计划中心菜单
        recyclerPlanMenu.setLayoutManager(new GridLayoutManager(context,2));
        recyclerPlanMenu.setNestedScrollingEnabled(false);
        initPullToRefreshRecyclerView();//初始化PullToRefresh
        requestHttpMethod();
    }

    @Override
    protected void setListeners() {
    }

    @Override
    protected void setTitleBar() {
        setActionbarBar("计划中心", R.color.app_color, R.color.white, true, true);
    }
    /**
     * 设置菜单列表展示
     */
    @Override
    public void setPlanMenuList(List<CommonMenuBean> beans) {
        if (beans == null) {
            return;
        }
        if (mMain_planCenter_menu_adapter == null) {
            mMain_planCenter_menu_adapter = new Main_PlanCenter_Menu_Adapter(context, beans);
            recyclerPlanMenu.setAdapter(mMain_planCenter_menu_adapter);
        } else {
            mMain_planCenter_menu_adapter.setData(beans);
            mMain_planCenter_menu_adapter.notifyDataSetChanged();
        }
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
        goodsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        //--------------------------设置刷新监听-----------------------------------------
        mPullRefreshRecycler.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<EmptyRecyclerView>() {
            @Override
            public void onPullDownToRefresh(
                    PullToRefreshBase<EmptyRecyclerView> refreshView) {
                // TODO Auto-generated method stub
                // 下拉刷新
                mPresenter.setClassGoods_page(1);//恢复默认请求页数是第一页
                //请求服务器数据的方法
                requestHttpMethod();
            }

            @Override
            public void onPullUpToRefresh(
                    PullToRefreshBase<EmptyRecyclerView> refreshView) {
                //上拉加载更多
                int classGoods_page=mPresenter.getClassGoods_page();
                classGoods_page++;//页数加一
                if(classGoods_page<=6){
                    mPresenter.setClassGoods_page(classGoods_page);
                    requestHttpMethod();
                }else {
                    ToastUtil.ErrorImageToast(context,"最多可查看120条记录！");
                    closeRefresh();
                }
            }
        });
    }
    /**
     * 请求服务器数据的方法
     */
    public void requestHttpMethod() {
        //初始化参数
        mPresenter.initData(countHttpMethod);
        //请求计划菜单
        mPresenter.requestPlanMenuData();
        //请求计划中心列表数据
        mPresenter.requestPlanCenterData();
    }
    /**
     * 设置计划列表展示
     */
    @Override
    public void setPlanCenterList(List<Main_PlanCenterBean> beans) {
        if (beans == null) {
            return;
        }
        //设置Adapter
        if (mMain_planCenter_list_adapter == null) {
            mMain_planCenter_list_adapter = new Main_PlanCenter_List_Adapter(context, beans);
            goodsRecyclerView.setAdapter(mMain_planCenter_list_adapter);
        } else {
            mMain_planCenter_list_adapter.setData(beans);
            mMain_planCenter_list_adapter.notifyDataSetChanged();
        }
        int classGoods_page=mPresenter.getClassGoods_page();//获取当前页数
        //设置列表滚动到指定位置
        if(classGoods_page==1){
            scrollToPosition=beans.size();
            scrollToPosition(0); //设置RecyclerView滚动到指定位置
        }else if(classGoods_page>1){
            int newScrollToPosition =scrollToPosition*(classGoods_page-1);
            scrollToPosition(newScrollToPosition); //设置RecyclerView滚动到指定位置
        }
    }

    /**
     * 设置RecyclerView滚动到指定位置
     * @param position
     */
    public void scrollToPosition(final int position) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                goodsRecyclerView.scrollToPosition(position);
            }
        });
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
}
