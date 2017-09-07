package com.app.lotteryticket.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.common.MVP.Presenter.Implement.ProjectUtil_Presenter_Implement;
import com.app.common.MVP.Presenter.Interface.ProjectUtil_Presenter_Interface;
import com.app.common.Util.Textutils;
import com.app.lotteryticket.MVP.Model.Bean.Main_PlanCenterBean;
import com.app.lotteryticket.R;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * 计划页面列表适配器
 * Created by ${杨重诚} on 2017/6/7.
 */

public class Main_PlanCenter_List_Adapter extends SuperAdapter<Main_PlanCenterBean> {
    ProjectUtil_Presenter_Interface mProjectUtil_presenter_interface;
    public Main_PlanCenter_List_Adapter(Context context, List<Main_PlanCenterBean> items) {
        super(context, items, R.layout.main_item_plan_center_list_layout);
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
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, final Main_PlanCenterBean bean) {
        if (holder instanceof ViewHolder) {
            final ViewHolder holder1 = ((ViewHolder) holder);
            holder1.textPeriods.setText(Textutils.checkText(bean.getPeriods()));
            holder1.textPlan1.setText(Textutils.checkText(bean.getPlanOne()));
            holder1.textPlan2.setText(Textutils.checkText(bean.getPlanTwo()));
            holder1.textPlan3.setText(Textutils.checkText(bean.getPlanThree()));
            holder1.textPlan4.setText(Textutils.checkText(bean.getPlanFour()));
            //渲染
            drawingTextView(holder1.textPlan1);
            drawingTextView(holder1.textPlan2);
            drawingTextView(holder1.textPlan3);
            drawingTextView(holder1.textPlan4);
            if(layoutPosition%2==0){
                holder1.parentLayout.setBackgroundColor(mContext.getResources().getColor(R.color.line_gray));
            }else {
                holder1.parentLayout.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            }
        }
    }
    public class ViewHolder extends SuperViewHolder  {
        LinearLayout parentLayout;
        //期号
        TextView textPeriods;
        //计划一
        TextView textPlan1;
        //计划二
        TextView textPlan2;
        //计划三
        TextView textPlan3;
        //计划四
        TextView textPlan4;
        ViewHolder(View convertView) {
            super(convertView);
            parentLayout=(LinearLayout)convertView.findViewById(R.id.parentLayout);
            textPeriods=(TextView)convertView.findViewById(R.id.textPeriods);
            textPlan1=(TextView)convertView.findViewById(R.id.textPlan1);
            textPlan2=(TextView)convertView.findViewById(R.id.textPlan2);
            textPlan3=(TextView)convertView.findViewById(R.id.textPlan3);
            textPlan4=(TextView)convertView.findViewById(R.id.textPlan4);
        }
    }

    /**
     * 渲染
     * @param textView
     */
    private void drawingTextView(TextView textView){
        if(Textutils.getEditText(textView).contains("中")){
            textView.setTextColor(mContext.getResources().getColor(R.color.limegreen));
        }else {
            textView.setTextColor(mContext.getResources().getColor(R.color.app_color));
        }
    }
}
