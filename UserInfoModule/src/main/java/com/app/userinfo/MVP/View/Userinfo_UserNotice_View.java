package com.app.userinfo.MVP.View;

import android.view.View;
import android.widget.ExpandableListView;

import com.app.common.CustomView.AnimatedExpandableListView;
import com.app.common.Public.RouterUrl;
import com.app.userinfo.Adapter.Userinfo_UserNotice_Adapter;
import com.app.userinfo.Base.Userinfo_BaseActivity;
import com.app.userinfo.MVP.Contract.Userinfo_UserNotice_Contract;
import com.app.userinfo.MVP.Model.Bean.Userinfo_NoticeBean;
import com.app.userinfo.MVP.Presenter.Implement.Userinfo_UserNotice_Presenter;
import com.app.userinfo.R;
import com.chenenyu.router.annotation.Route;

import java.util.List;

/**
 * 用户通知消息
 */
@Route(RouterUrl.userinfo_UserNoticeRouterUrl)
public class Userinfo_UserNotice_View extends Userinfo_BaseActivity<Userinfo_UserNotice_Contract.Presenter,Userinfo_UserNotice_Presenter> implements Userinfo_UserNotice_Contract.View{
    //最新公告列表
    AnimatedExpandableListView notice_list;

    Userinfo_UserNotice_Adapter mUserSettingNotice_adapter;//最新公告列表adapter
    int lastGroupPosition=0;//记录上一次的选中index，默认是第一个


    @Override
    protected void setContentView() {
        setContentView(R.layout.userinfo_act_user_notice_layout);
    }
    @Override
    protected void init() {
        //请求最新公告数据
        mPresenter.requestNotice();
    }
    @Override
    public void initMyView() {
        super.initMyView();
        notice_list= (AnimatedExpandableListView) findViewById(R.id.notice_list);
    }

    @Override
    protected void setListeners() {
        //设置list点击事件
        notice_list.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (notice_list.isGroupExpanded(groupPosition)) {
                    //如果当前列表是打开着，设置关闭动画
                    notice_list.collapseGroupWithAnimation(groupPosition);
                } else {
                    //如果当前列表是关闭着，设置打开动画
                    notice_list.expandGroupWithAnimation(groupPosition);
                    //如果当前打开的位置不是上次的位置
                    if(lastGroupPosition!=groupPosition){
                        //让上次打开的item关闭
                        notice_list.collapseGroupWithAnimation(lastGroupPosition);
                        lastGroupPosition=groupPosition;
                    }
                }
                return true;
            }

        });
    }
    @Override
    protected void setTitleBar() {
        //设置Actionbar
        setActionbarBar("最新公告", R.color.app_color, R.color.white, true, true);
    }

    @Override
    protected void getData() {

    }

    /**
     * 设置最新公告数据
     * @param beanList
     */
    @Override
    public void setNoticeList(List<Userinfo_NoticeBean> beanList) {
        mUserSettingNotice_adapter=new Userinfo_UserNotice_Adapter(context ,beanList);
        notice_list.setAdapter(mUserSettingNotice_adapter);
        notice_list.expandGroup(0);//默认第一个展开
    }
}
