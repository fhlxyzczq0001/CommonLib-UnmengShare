package com.app.lotteryticket.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.app.common.MVP.Model.Bean.CommonMenuBean;
import com.app.common.MVP.Presenter.Implement.ProjectUtil_Presenter_Implement;
import com.app.common.MVP.Presenter.Interface.ProjectUtil_Presenter_Interface;
import com.app.common.Util.Textutils;
import com.app.lotteryticket.R;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * 计划内容适配器
 * Created by ${杨重诚} on 2017/6/7.
 */

public class Main_PlanContent_Adapter extends SuperAdapter<String> {
    public Main_PlanContent_Adapter(Context context, List<String> items) {
        super(context, items, R.layout.main_item_plan_content_layout);
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
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, final String bean) {
        if (holder instanceof ViewHolder) {
            final ViewHolder holder1 = ((ViewHolder) holder);
            holder1.textContent.setText(Textutils.checkText(bean));
        }
    }
    public class ViewHolder extends SuperViewHolder  {
        //计划内容
        TextView textContent;
        ViewHolder(View convertView) {
            super(convertView);
            textContent=(TextView)convertView.findViewById(R.id.textContent);
        }
    }
}
