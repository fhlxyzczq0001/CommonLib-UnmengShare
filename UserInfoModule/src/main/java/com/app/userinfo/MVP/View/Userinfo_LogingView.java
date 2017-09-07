package com.app.userinfo.MVP.View;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.common.Base.CommonApplication;
import com.app.common.CustomView.ClearEditText;
import com.app.common.MVP.Model.Bean.EventBus.LoginSuccess_EventBus;
import com.app.common.MVP.Presenter.Implement.ProjectUtil_Presenter_Implement;
import com.app.common.MVP.Presenter.Interface.ProjectUtil_Presenter_Interface;
import com.app.common.Public.PublicMsg;
import com.app.common.Public.RouterUrl;
import com.app.common.Util.DensityUtils;
import com.app.common.Util.Textutils;
import com.app.common.Util.ToastUtil;
import com.app.userinfo.Base.Userinfo_BaseActivity;
import com.app.userinfo.MVP.Contract.Userinfo_LogingView_Contract;
import com.app.userinfo.MVP.Presenter.Implement.Userinfo_LogingView_Presenter;
import com.app.userinfo.R;
import com.chenenyu.router.annotation.Route;

import org.greenrobot.eventbus.EventBus;

/**
 * 登录界面
 */
@Route(RouterUrl.userinfo_UserLogingRouterUrl)
public class Userinfo_LogingView extends Userinfo_BaseActivity<Userinfo_LogingView_Contract.Presenter,Userinfo_LogingView_Presenter> implements Userinfo_LogingView_Contract.View{
    //用户名输入框
    ClearEditText login_et_usename;
    //密码输入框
    ClearEditText login_et_pwd;
    //登录按钮
    TextView login_btn;
    //是否显示密码控件
    ImageView iconPasswordIsShowBtn;
    //是否显示密码的标识
    boolean isShowPassword=false;
    ProjectUtil_Presenter_Interface mProjectUtil_Presenter_Interface;
    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v.getId()== R.id.login_btn){
            //登录
            mPresenter.login(login_et_usename,login_et_pwd);
        }else if(v.getId()==R.id.iconPasswordIsShowBtn){
            //是否显示密码
            isShowPassword=mProjectUtil_Presenter_Interface.isShowPassword(isShowPassword,login_et_pwd,iconPasswordIsShowBtn);
        }
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.userinfo_act_user_login_layout);
    }

    @Override
    protected void init() {
        mProjectUtil_Presenter_Interface=new ProjectUtil_Presenter_Implement(context);
        //设置用户名自动提示
        mPresenter.setEditTextLoginUserName();
        //初始化登录按钮
        if(Textutils.getEditText(login_et_usename).length()>0){
            setLoginBtn(true);
        }else {
            setLoginBtn(false);
        }
    }

    @Override
    public void initMyView() {
        super.initMyView();
        //用户名输入框
        login_et_usename=(ClearEditText)findViewById(R.id.login_et_usename);
        //密码输入框
        login_et_pwd=(ClearEditText)findViewById(R.id.login_et_pwd);
        //登录按钮
        login_btn=(TextView)findViewById(R.id.login_btn);
        //是否显示密码控件
        iconPasswordIsShowBtn=(ImageView)findViewById(R.id.iconPasswordIsShowBtn);
    }

    @Override
    protected void setListeners() {
        //用户名输入框监听
        login_et_usename.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0){
                    setLoginBtn(true);
                }else {
                    setLoginBtn(false);
                }
            }
        });
        login_btn.setOnClickListener(this);
        iconPasswordIsShowBtn.setOnClickListener(this);
    }
    /**
     * 设置登录按钮
     * @param isEnabled
     */
    private void setLoginBtn(boolean isEnabled){
        if(isEnabled){
            login_btn.setEnabled(true);
            login_btn.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shape_red_circular_bg));
            login_btn.setPadding(0, (int) DensityUtils.dp2px(context,8),0,(int) DensityUtils.dp2px(context,8));
        }else {
            login_btn.setEnabled(false);
            login_btn.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shape_gray_circle_bg));
            login_btn.setPadding(0, (int) DensityUtils.dp2px(context,8),0,(int) DensityUtils.dp2px(context,8));
        }
    }
    @Override
    protected void setTitleBar() {
        //设置Actionbar
        setActionbarBar("登录", R.color.app_color, R.color.white, true, true);
    }

    @Override
    protected void getData() {

    }
    /**
     * 获取用户名输入控件
     */
    @Override
    public ClearEditText getEditTextLoginUserName() {
        if(login_et_usename!=null){
            return  login_et_usename;
        }
        return null;
    }
    /**
     * 登录成功
     * @return
     */
    @Override
    public boolean longSuccess(String msg) {
        //如果不是注册后返回的，就提示登录成功
        ToastUtil.RightImageToast(context,msg,500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //这里发送粘性事件
                EventBus.getDefault().postSticky(new LoginSuccess_EventBus(true));
            }
        }, 300);
        //如果主activity没有启动
        if(!CommonApplication.getInstance().getMainAppIsStart()){
            ToastUtil.RightImageToast(context,"正在进入应用...");
            getIntentTool().intent_RouterTo(context, PublicMsg.ROUTER_MAINACTIVITY);
        }else {
            finish();
        }
        return true;
    }
}
