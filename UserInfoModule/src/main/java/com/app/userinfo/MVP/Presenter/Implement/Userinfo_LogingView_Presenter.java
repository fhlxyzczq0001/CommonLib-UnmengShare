package com.app.userinfo.MVP.Presenter.Implement;


import android.widget.ArrayAdapter;

import com.app.common.CustomView.ClearEditText;
import com.app.common.HttpRequest.HttpPath;
import com.app.common.HttpRequest.HttpRequestMethod;
import com.app.common.HttpRequest.ResultListener.ResultDataListener;
import com.app.common.MVP.Model.Bean.Request_Bean;
import com.app.common.MVP.Model.Bean.UserInfoBean;
import com.app.common.MVP.Model.Bean.UserLogingPhoneCacheBean;
import com.app.common.MVP.Model.Implement.Base_HttpRequest_Implement;
import com.app.common.MVP.Model.Interface.Base_HttpRequest_Interface;
import com.app.common.Util.CheckUtils;
import com.app.common.Util.CommonSharePer_GlobalInfo;
import com.app.common.Util.Common_SharePer_UserInfo;
import com.app.common.Util.MyAnimation;
import com.app.common.Util.Textutils;
import com.app.common.Util.ToastUtil;
import com.app.userinfo.Base.Application.UserInfoApplication_Interface;
import com.app.userinfo.Base.UserinfoApplication_Utils;
import com.app.userinfo.MVP.Contract.Userinfo_LogingView_Contract;
import com.app.userinfo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by ${杨重诚} on 2017/6/2.
 */

public class Userinfo_LogingView_Presenter extends Userinfo_LogingView_Contract.Presenter {
    Base_HttpRequest_Interface mBase_httpRequest_interface;//请求网络数据的model实现类
    UserInfoApplication_Interface mUserInfoApplicationInterface;
    String [] title={"密码登录","验证码登录"};
    public Userinfo_LogingView_Presenter(){
        mUserInfoApplicationInterface= UserinfoApplication_Utils.getApplication();
        mBase_httpRequest_interface=new Base_HttpRequest_Implement();
    }
    /**
     * 设置用户名输入
     */
    @Override
    public void setEditTextLoginUserName() {
        ClearEditText loginUserName= mView.getEditTextLoginUserName();//获取用户名输入框
        UserLogingPhoneCacheBean usernameCache= CommonSharePer_GlobalInfo.sharePre_GetUserNameCache();//获取用户名本地缓存
        if(usernameCache!=null){
            List<String> usernameCache_List=new ArrayList<>(usernameCache.getPhoneCache());
            String[] usernameCache_String = (String[])usernameCache_List.toArray(new String[usernameCache_List.size()]);
            if(usernameCache.getPhoneCache().size()>0){
                loginUserName.setText(Textutils.checkText(usernameCache_String[0]));//设置最后一次登录账号
            }
            ArrayAdapter arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,usernameCache_String);
            loginUserName.setAdapter(arrayAdapter);
        }
    }
    /**
     * 校验用户名
     * @param clearEditText
     * @return
     */
    public boolean checkUserName(ClearEditText clearEditText) {
        String userName=Textutils.getEditText(clearEditText);//获取用户名
        if(userName.length()==0){
            //用户名为空
            ToastUtil.WarnImageToast(context, Textutils.getValuesString(context, R.string.login_et_err_usename));
            clearEditText.startAnimation(MyAnimation.shakeAnimation(5));//晃动动画
            return false;
        }
        return true;
    }

    /**
     * 校验密码
     * @param clearEditText
     * @return
     */
    public boolean checkPassWord(ClearEditText clearEditText) {
        String password=Textutils.getEditText(clearEditText);//获取密码
        if(password.length()==0){
            //密码为空
            ToastUtil.WarnImageToast(context, Textutils.getValuesString(context,R.string.login_et_err_pwd));
            clearEditText.startAnimation(MyAnimation.shakeAnimation(5));//晃动动画
            return false;
        }
        return true;
    }
    /**
     * 登录
     * @param userName_et
     * @param passWord_et
     */
    @Override
    public void login(ClearEditText userName_et, ClearEditText passWord_et) {
        boolean isTrue_UserName=checkUserName(userName_et);//校验用户名
        if(!isTrue_UserName){
            return;
        }
        boolean isTrue_PawwWord=checkPassWord(passWord_et);//校验密码
        if(!isTrue_PawwWord){
            return;
        }
        if(isTrue_UserName&&isTrue_PawwWord){
            //用户名和密码都校验成功，提交服务器
            String userName=Textutils.checkText(Textutils.getEditText(userName_et));
            String password=Textutils.checkText(Textutils.getEditText(passWord_et));
            //请求登录
            requestLoging(userName,password);
        }
    }
    /**
     * 请求登录
     * @param userName
     * @param password
     */
    public void requestLoging(String userName, String password) {
        getUserInfo(getUserInfo_Params(userName,password));
    }

    /**
     * 获取用户信息的Params
     * @return
     */
    public Map<String, String> getUserInfo_Params(String userName, String password) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("ACT", "Login");
        params.put("username", userName);
        params.put("password", password);
        return params;
    }

    /**
     * 获取用户信息
     * @param params
     */
    public void getUserInfo(final Map<String, String> params) {
        mBase_httpRequest_interface.requestData(context, HttpPath.URL_API_USER_INFO, params, new ResultDataListener() {
            @Override
            public void onResult(boolean isSucc, String msg, Request_Bean request_bean) {
                UserInfoBean infoBean=null;
                if (isSucc) {
                    if(request_bean.getData()!=null){
                       /* infoBean = JSONObject.parseObject(request_bean.getData().toString(),
                                UserInfoBean.class);*/
                        infoBean=new UserInfoBean();
                        String username=params.get("username");
                        infoBean.setUserId(username);
                        infoBean.setUserName(username);
                        logingSuccess(username,infoBean,msg);//登录成功后的操作
                    }
                }else {

                }
            }
        }, HttpRequestMethod.POST);
    }
    /**
     * 登录成功后的操作
     * @param userName
     * @param userInfoBean
     */
    public void logingSuccess(String userName,UserInfoBean userInfoBean,String msg) {
        //设置用户信息
        mUserInfoApplicationInterface.setUseInfoVo(userInfoBean);
        //存放用户id
        Common_SharePer_UserInfo.sharePre_PutUserID(userName);
        //存放用户OauthToken
        //Common_SharePer_UserInfo.sharePre_PutOauthToken(userInfoBean.getOauth_token());
        //存放所有登录成功的用户名
        if (CheckUtils.chekPhone(userName)) {
            //校验用户名，只存放手机号
            CommonSharePer_GlobalInfo.sharePre_PutUserNameCache(userName);
        }
        //结束登录页面
        mView.longSuccess(msg);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mBase_httpRequest_interface=null;
        mUserInfoApplicationInterface=null;
    }
}
