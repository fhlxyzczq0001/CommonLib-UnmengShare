package com.app.common.MVP.View.Implement;

import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.common.Base.Common_BaseActivity;
import com.app.common.R;
import com.app.common.R2;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 网络异常显示界面
 *
 * @ClassName: com.ygworld.MVP.View.Implement.Activity
 * @author: Administrator 杨重诚
 * @date: 2016/10/25:13:38
 */

public class NetworkError_View_Implement extends Common_BaseActivity {
    //显示图片
    @BindView(R2.id.img)
    ImageView mImg;
    //点击重试
    @BindView(R2.id.prompt_information)
    TextView mPromptInformation;

    public static int NO_NETWORK=0;//无网络
    public static int NO_PING=1;//不能访问外网
    public static int NO_REQUEST=2;//无数据


    //返回按钮点击事件
    @OnClick(R2.id.prompt_information)
    void finish(View view) {
        finish();
    }

    @Override
    protected void initApplication() {

    }

    @Override
    protected void setContentView() {
        contentView= R.layout.common_act_network_error_layout;
        setContentView(contentView);
    }

    @Override
    protected void init() {

    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void setTitleBar() {
        //设置Actionbar
        setActionbarBar("网络异常", R.color.white, R.color.app_text_black, false);
    }

    @Override
    protected void getData() {

    }

    @Override
    protected void onMyPause() {

    }

    /**
     * 返回键的监听
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {

            // 退出
            finish();

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
