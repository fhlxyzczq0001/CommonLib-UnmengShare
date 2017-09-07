package com.app.lotteryticket.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.app.common.MVP.Model.Bean.CommonMenuBean;
import com.app.common.MVP.Presenter.Implement.ProjectUtil_Presenter_Implement;
import com.app.common.MVP.Presenter.Interface.ProjectUtil_Presenter_Interface;
import com.app.lotteryticket.R;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * 计划页面菜单适配器
 * Created by ${杨重诚} on 2017/6/7.
 */

public class Main_PlanCenter_Menu_Adapter extends SuperAdapter<CommonMenuBean> {
    ProjectUtil_Presenter_Interface mProjectUtil_presenter_interface;
    public Main_PlanCenter_Menu_Adapter(Context context, List<CommonMenuBean> items) {
        super(context, items, R.layout.main_item_plan_center_menu_layout);
        mProjectUtil_presenter_interface=new ProjectUtil_Presenter_Implement(context);
    }
    @SuppressLint("MissingSuperCall")
    @Override
    public SuperViewHolder onCreate(View convertView, ViewGroup parent, int viewType) {
        // These code show how to add click listener to item view of ViewHolder.
        SuperViewHolder superViewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(mMulItemViewType == null ?
                    mLayoutResId : mMulItemViewType.getLayoutId(viewType), parent, false);
            superViewHolder = new ViewHolder(convertView);
            convertView.setTag(superViewHolder);
        } else {
            superViewHolder = (SuperViewHolder) convertView.getTag();
        }
        return superViewHolder;
    }
    @Override
    public void onBind(SuperViewHolder holder, int viewType, final int layoutPosition, final CommonMenuBean bean) {
        if (holder instanceof ViewHolder) {
            final ViewHolder holder1 = ((ViewHolder) holder);
            holder1.textMenu.setText(bean.getTitle());
            holder1.rippleViewItemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mProjectUtil_presenter_interface.urlIntentJudge(mContext,bean.getUrl()+"&plan=jh"+(layoutPosition+1),bean.getTitle());
                }
            });
        }
    }
    public class ViewHolder extends SuperViewHolder  {
        //菜单
        TextView textMenu;
        RippleView rippleViewItemLayout;
        ViewHolder(View convertView) {
            super(convertView);
            textMenu=(TextView)convertView.findViewById(R.id.textMenu);
            rippleViewItemLayout=(RippleView)convertView.findViewById(R.id.rippleViewItemLayout);
        }
    }
}
