package com.app.lotteryticket.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.common.Util.MyAnimation;
import com.app.lotteryticket.R;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * 首页中奖号码适配器
 * Created by ${杨重诚} on 2017/6/7.
 */

public class Main_Home_WinningNumbers_Adapter extends SuperAdapter<String> {
    public Main_Home_WinningNumbers_Adapter(Context context, List<String> items) {
        super(context, items, R.layout.main_item_home_winning_numbers_layout);
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
            holder1.textNum.setText(bean);
        }
    }
    public class ViewHolder extends SuperViewHolder  {
        //中奖号码
        TextView textNum;
        ViewHolder(View convertView) {
            super(convertView);
            textNum=(TextView)convertView.findViewById(R.id.textNum);
        }
    }
}
