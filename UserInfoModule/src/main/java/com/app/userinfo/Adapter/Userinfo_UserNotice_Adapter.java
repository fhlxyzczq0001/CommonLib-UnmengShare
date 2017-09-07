package com.app.userinfo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.common.CustomView.AnimatedExpandableListView;
import com.app.common.Util.Textutils;
import com.app.userinfo.MVP.Model.Bean.Userinfo_NoticeBean;
import com.app.userinfo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * 最新公告
 *
 * @ClassName: com.ygworld.Adapter
 * @author: Administrator 杨重诚
 * @date: 2016/10/9:16:48
 */

public class Userinfo_UserNotice_Adapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter {
    private Context mContext;
    private List<Userinfo_NoticeBean> mUserinfoNoticeBeanList;//服务器获取最新公告数据
    private List<GroupItem> items=new ArrayList<>();//列表展示数据数组，重新拼接

    public Userinfo_UserNotice_Adapter(Context context, List<Userinfo_NoticeBean> mUserinfoNoticeBeanList) {
        mContext = context;
        this.mUserinfoNoticeBeanList = mUserinfoNoticeBeanList;
        //重新拼接数据源
        for (Userinfo_NoticeBean userinfoNoticeBean : this.mUserinfoNoticeBeanList) {
            //创建父bean对象
            GroupItem groupItem = new GroupItem();
            //groupItem.id = userinfoNoticeBean.getId();
            groupItem.title = userinfoNoticeBean.getTitle();
            //groupItem.publishTime = userinfoNoticeBean.getPublishTime();

            //创建子bean对象
            ChildItem childItem = new ChildItem();
            childItem.content = userinfoNoticeBean.getContent();
            //父对象添加子对象
            groupItem.mChildItems.add(childItem);
            //展示数据添加父对象
            items.add(groupItem);
        }
    }

    /**
     * 父列表展示
     *
     * @param groupPosition
     * @param isExpanded
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder holder;//父列表holder
        GroupItem groupItem = getGroup(groupPosition);//获取父列表bean
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.userinfo_item_user_setting_notice_group, parent, false);
            holder = new GroupHolder(convertView);
            holder.notice_iv= (ImageView) convertView.findViewById(R.id.notice_iv);
            holder.notice_tv_time= (TextView) convertView.findViewById(R.id.notice_tv_time);
            holder.notice_tv_title= (TextView) convertView.findViewById(R.id.notice_tv_title);
            convertView.setTag(holder);
        } else {
            holder = (GroupHolder) convertView.getTag();
        }

        //设置标题
        holder.notice_tv_title.setText(Textutils.checkText(groupItem.title));
        //设置时间
        //long time = groupItem.publishTime;
        //SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        //holder.notice_tv_time.setText(sf.format(time));
        //设置箭头
        if (isExpanded) {
            holder.notice_iv.setImageResource(R.mipmap.icon_arrow_up);
        } else {
            holder.notice_iv.setImageResource(R.mipmap.icon_arrow_down);
        }
        return convertView;
    }

    /**
     * 子列表展示
     *
     * @param groupPosition
     * @param childPosition
     * @param isLastChild
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder holder;//子列表holder
        ChildItem childItem = getChild(groupPosition, childPosition);//获取子列表bean
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.userinfo_item_user_setting_notice_child, parent, false);
            holder = new ChildHolder(convertView);
            holder.notice_tv_desc= (TextView) convertView.findViewById(R.id.notice_tv_desc);
            convertView.setTag(holder);
        } else {
            holder = (ChildHolder) convertView.getTag();
        }
        //设置内容
        String content = childItem.content;
        // <p>段落替换为换行
        content = content.replaceAll("<.*?>", "");
        content = content.replaceAll("&nbsp;", "");
        holder.notice_tv_desc.setText(content);

        return convertView;
    }

    @Override
    public int getRealChildrenCount(int groupPosition) {
        return items.get(groupPosition).mChildItems.size();
    }

    @Override
    public int getGroupCount() {
        return items.size();
    }

    /**
     * 获取父列表bean
     *
     * @param groupPosition
     * @return
     */
    @Override
    public GroupItem getGroup(int groupPosition) {
        return items.get(groupPosition);
    }

    /**
     * 获取子列表bean
     *
     * @param groupPosition
     * @param childPosition
     * @return
     */
    @Override
    public ChildItem getChild(int groupPosition, int childPosition) {
        return items.get(groupPosition).mChildItems.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    /**
     * 父列表bean对象
     */
    private class GroupItem {
        int id;
        String title;//标题
        long publishTime;//时间
        List<ChildItem> mChildItems = new ArrayList<ChildItem>();//字列表数据
    }

    /**
     * 子列表bean对象
     */
    private class ChildItem {
        String content;//展示内容
    }

    /**
     * 子列表Holder
     */
    public class ChildHolder {
        //展示内容
        TextView notice_tv_desc;

        public ChildHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    /**
     * 父列表Holder
     */
    public class GroupHolder {
        //标题
        TextView notice_tv_title;
        //时间
        TextView notice_tv_time;
        //箭头
        ImageView notice_iv;

        public GroupHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
