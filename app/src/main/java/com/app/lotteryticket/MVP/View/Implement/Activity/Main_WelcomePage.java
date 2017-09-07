package com.app.lotteryticket.MVP.View.Implement.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.common.CustomView.CircleTextProgressbar;
import com.app.common.MVP.Presenter.Implement.ProjectUtil_Presenter_Implement;
import com.app.common.MVP.Presenter.Interface.ProjectUtil_Presenter_Interface;
import com.app.common.Public.RouterUrl;
import com.app.common.Public.SD_FilePath;
import com.app.common.Util.FileUtils;
import com.app.common.Util.WindowUtils;
import com.app.lotteryticket.Base.Main_BaseActivity;
import com.app.lotteryticket.MVP.Contract.Main_WelcomePage_Contract;
import com.app.lotteryticket.MVP.Model.Bean.WelcomePageBean;
import com.app.lotteryticket.MVP.Presenter.Implement.Main_WelcomePage_Presenter;
import com.app.lotteryticket.R;
import com.app.lotteryticket.Service.WelcomePageService;
import com.app.lotteryticket.Util.Main_SharePer_GlobalInfo;
import com.app.lotteryticket.Util.Main_SharePer_SdCard_Info;
import com.chenenyu.router.annotation.Route;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 启动页
 *
 * @ClassName: com.ygworld.MVP.View.Implement.Activity
 * @author: Administrator 杨重诚
 * @date: 2016/10/14:16:23
 */
@Route(RouterUrl.mainWelcomePageRouterUrl)
public class Main_WelcomePage extends Main_BaseActivity<Main_WelcomePage_Contract.Presenter,Main_WelcomePage_Presenter> implements Main_WelcomePage_Contract.View{
    @BindView(R.id.myParent_layout)
    LinearLayout myParent_layout;
    //显示图片
    @BindView(R.id.mydk_lay_top)
    ImageView mydk_lay_top;
    //跳过进度条
    @BindView(R.id.mydk_lay_close)
    CircleTextProgressbar mydk_lay_close;

    Bitmap pageBitmap;// 启动页图片
    ProjectUtil_Presenter_Interface mMainProjectUtil_presenter_interface;
    boolean isOnStop=false;//标识是否执行了OnStop方法
    //---------------------------倒计时--------------------------------
    private int allTime=5*1000;//总时间

    //---------------------点击事件---------------------------------------
    @OnClick({R.id.mydk_lay_close,R.id.mydk_lay_top})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mydk_lay_close:
                //跳过按钮点击事件
                toNextActivity();
                break;
            case R.id.mydk_lay_top:
                //图片点击事件
                WelcomePageBean welcomePageBean= Main_SharePer_SdCard_Info.sharePre_GetWelcomePageBean();
                if(welcomePageBean!=null&&!welcomePageBean.getLink().isEmpty()){
                    mMainProjectUtil_presenter_interface.urlIntentJudge(context,welcomePageBean.getLink(),"");
                    mydk_lay_close.stop();
                }
                break;
        }
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.main_act_welcome_page_layout);
    }
    @Override
    public void onResume() {
        super.onResume();
        if(isOnStop){
            toNextActivity();
        }
    }
    @Override
    protected void init() {
        if((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0){
            finish();
            return;
        }
        //初始化presenter
        mMainProjectUtil_presenter_interface =new ProjectUtil_Presenter_Implement(context);
        //请求是否下载启动页
        //mPresenter.requestStartPageUpdate(mPresenter.getStartPageUpdate_Params());
        //设置启动页
        setWelcomePageData();
        //5秒后跳转
        startCountDownTimer(allTime);
    }

    @Override
    public void initMyView() {
        super.initMyView();
    }

    @Override
    protected void setListeners() {
    }

    @Override
    protected void setTitleBar() {
        setActionbarGone();//隐藏actionbar
        int StatusHeight = WindowUtils.getStatusHeight((Activity) context);// 获取状态栏高度
        myParent_layout.setPadding(0, StatusHeight, 0, 0);
    }

    @Override
    protected void getData() {

    }

    @Override
    public void getBundleValues(Bundle mBundle) {
        super.getBundleValues(mBundle);
    }

    /**
     * 启动启动页service后台下载
     * @param pageBean
     */
    @Override
    public void startWelcomePageService(WelcomePageBean pageBean) {
        // 启动service后台下载
        Intent intent = new Intent(context,
                WelcomePageService.class);
        intent.putExtra("WelcomePageBean",pageBean);
        context.startService(intent);
    }

    /**
     * 设置启动页
     *
     * @Title: setWelcomePageData
     * @Description: TODO
     * @return: void
     */
    private void setWelcomePageData() {
        List<String> filePaths = FileUtils.getFileName(new File(
                SD_FilePath.welcomePagePath).listFiles());
        if (null != filePaths && filePaths.size() > 0) {
            // 随机抽取一张启动页显示
            int index = (int) (Math.random() * filePaths.size());
            pageBitmap = BitmapFactory.decodeFile(filePaths.get(index));// 读取本地图片
            mydk_lay_top.setImageBitmap(pageBitmap);
            //设置动画
            Animation animationTop = AnimationUtils.loadAnimation(this,
                    R.anim.tutorail_scalate_top);//渐变放大效果
            mydk_lay_top.startAnimation(animationTop);
        }else{
            mydk_lay_top.setImageResource(R.mipmap.bg_welcome_default);
        }
    }
    @Override
    /**
     * 待跳转的下个界面
     */
    public void toNextActivity(){
        mydk_lay_close.stop();//停止倒计时
        if (Main_SharePer_GlobalInfo.sharePre_GetFirstInstall()) {
            Main_SharePer_GlobalInfo.sharePre_PutFirstInstall(false);//设置不是第一次进入
            // 进入引导页
            getIntentTool().intent_RouterTo(context, RouterUrl.mainGuideRouterUrl);
            finish();
        } else {
            // 进入首页
            getIntentTool().intent_RouterTo(context, RouterUrl.mainMainActivityRouterUrl);
            finish();
        }
    }
    /**
     * 启动倒计时
     */
    public void startCountDownTimer(long downTime) {
        // 和系统普通进度条一样，0-100。
        mydk_lay_close.setProgressType(CircleTextProgressbar.ProgressType.COUNT);
        // 改变外部边框颜色。
        mydk_lay_close.setOutLineColor(Color.parseColor("#20dbdbdb"));
        // 设置倒计时时间毫秒
        mydk_lay_close.setTimeMillis(downTime);
        // 改变圆心颜色。
        mydk_lay_close.setInCircleColor(Color.parseColor("#20000000"));
        //设置进度条颜色
        mydk_lay_close.setProgressColor(context.getResources().getColor(R.color.app_color));
        //设置进度条边宽
        mydk_lay_close.setProgressLineWidth(1);
        //设进度监听
        mydk_lay_close.setCountdownProgressListener(1, new CircleTextProgressbar.OnCountdownProgressListener() {
            @Override
            public void onProgress(int what, int progress) {
                if(progress==100){
                    //倒计时结束
                    toNextActivity();
                }
            }
        });
        //启动倒计时
        mydk_lay_close.reStart();
    }
    @Override
    protected void onPause() {
        super.onPause();
        isOnStop=true;
    }

    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (pageBitmap != null) {
            pageBitmap.recycle();
        }
    }
}
