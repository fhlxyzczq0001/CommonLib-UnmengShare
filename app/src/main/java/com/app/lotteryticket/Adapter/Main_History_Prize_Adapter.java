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
import com.app.lotteryticket.MVP.Model.Bean.Main_HistoryPrizeBean;
import com.app.lotteryticket.R;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * 历史开奖菜单适配器
 * Created by ${杨重诚} on 2017/6/7.
 */

public class Main_History_Prize_Adapter extends SuperAdapter<Main_HistoryPrizeBean> {
    ProjectUtil_Presenter_Interface mProjectUtil_presenter_interface;
    public Main_History_Prize_Adapter(Context context, List<Main_HistoryPrizeBean> items) {
        super(context, items, R.layout.main_item_history_prize_layout);
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
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, final Main_HistoryPrizeBean bean) {
        if (holder instanceof ViewHolder) {
            final ViewHolder holder1 = ((ViewHolder) holder);
            holder1.textPeriods.setText(Textutils.checkText(bean.getPeriods())+"期");
            holder1.textTime.setText(bean.getTime());
            holder1.textResults.setText(getNewResults(bean.getResults()));
            if(layoutPosition%2==0){
                holder1.parentLayout.setBackgroundColor(mContext.getResources().getColor(R.color.line_gray));
            }else {
                holder1.parentLayout.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            }
        }
    }
    public class ViewHolder extends SuperViewHolder  {
        LinearLayout parentLayout;
        //期数
        TextView textPeriods;
        //时间
        TextView textTime;
        //结果
        TextView textResults;
        ViewHolder(View convertView) {
            super(convertView);
            parentLayout=(LinearLayout)convertView.findViewById(R.id.parentLayout);
            textPeriods=(TextView)convertView.findViewById(R.id.textPeriods);
            textTime=(TextView)convertView.findViewById(R.id.textTime);
            textResults=(TextView)convertView.findViewById(R.id.textResults);
        }
    }

    private String getNewResults(String results){
        StringBuilder newResults=new StringBuilder();
        if(!results.isEmpty()){
            for(int i=0;i<results.length();i++){
                if(i<results.length()-1){
                    newResults.append(results.charAt(i)).append("\t\t");
                }else {
                    newResults.append(results.charAt(i));
                }
            }
            return newResults.toString();
        }
        return "";
    }
}
