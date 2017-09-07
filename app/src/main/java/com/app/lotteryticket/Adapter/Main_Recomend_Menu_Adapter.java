package com.app.lotteryticket.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.app.common.MVP.Model.Bean.CommonMenuBean;
import com.app.common.MVP.Presenter.Implement.ProjectUtil_Presenter_Implement;
import com.app.common.MVP.Presenter.Interface.ProjectUtil_Presenter_Interface;
import com.app.lotteryticket.R;
import com.leon.lib.settingview.LSettingItem;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * 首页推荐菜单适配器
 * Created by ${杨重诚} on 2017/6/7.
 */

public class Main_Recomend_Menu_Adapter extends SuperAdapter<CommonMenuBean> {
    ProjectUtil_Presenter_Interface mProjectUtil_presenter_interface;
    public Main_Recomend_Menu_Adapter(Context context, List<CommonMenuBean> items) {
        super(context, items, R.layout.main_item_home_recommend_menu_layout);
        mProjectUtil_presenter_interface=new ProjectUtil_Presenter_Implement(mContext);
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
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, final CommonMenuBean bean) {
        if (holder instanceof ViewHolder) {
            final ViewHolder holder1 = ((ViewHolder) holder);
            holder1.itemMenu.setLeftText(bean.getTitle());
            holder1.itemMenu.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
                @Override
                public void click(boolean isChecked, View view) {
                    mProjectUtil_presenter_interface.urlIntentJudge(mContext,bean.getUrl(),"");
                }
            });
        }
    }
    public class ViewHolder extends SuperViewHolder  {
        //菜单item
        LSettingItem itemMenu;
        ViewHolder(View convertView) {
            super(convertView);
            itemMenu=(LSettingItem)convertView.findViewById(R.id.itemMenu);
        }
    }
}
