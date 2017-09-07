package com.app.lotteryticket.MVP.Presenter.Implement;


import com.app.common.MVP.Model.Bean.CommonMenuBean;
import com.app.common.MVP.Model.Implement.Base_HttpRequest_Implement;
import com.app.common.MVP.Model.Interface.Base_HttpRequest_Interface;
import com.app.lotteryticket.MVP.Contract.Main_URL_View_Contract;

import java.util.ArrayList;
import java.util.List;

/**
 * 网址页面
 * Created by ${杨重诚} on 2017/6/2.
 */

public class Main_URL_View_Presenter extends Main_URL_View_Contract.Presenter {
    Base_HttpRequest_Interface mMain_base_httpRequest_interface;
    private int countHttpMethod = 0;//总共有多少个请求网络数据的方法
    private int indexHttpMethod = 0;//记录当前执行到第几个请求方法
    List<CommonMenuBean> commonMenuBeanList;//菜单数据
    public Main_URL_View_Presenter() {
        mMain_base_httpRequest_interface=new Base_HttpRequest_Implement();
    }
    /**
     * 初始化参数
     */
    @Override
    public void initData(int countHttpMethod) {
        this.countHttpMethod = countHttpMethod;
        indexHttpMethod = 0;
    }
    /**
     * 关闭刷新控件
     */
    @Override
    public void closeRefresh() {
        indexHttpMethod++;
        if (indexHttpMethod >= countHttpMethod) {
            indexHttpMethod = 0;
            mView.closeRefresh();
        }
    }
    /**
     * 获取网址数据
     */
    @Override
    public void requestURLData() {
        if(commonMenuBeanList==null){
            commonMenuBeanList=new ArrayList<>();
            CommonMenuBean commonMenuBean1=new CommonMenuBean();
            commonMenuBean1.setTitle("网易云音乐");
            commonMenuBean1.setUrl("http://music.163.com/");
            CommonMenuBean commonMenuBean2=new CommonMenuBean();
            commonMenuBean2.setTitle("未知笔记");
            commonMenuBean2.setUrl("http://note.youdao.com/noteintro.html?vendor=unsilent25");
            CommonMenuBean commonMenuBean3=new CommonMenuBean();
            commonMenuBean3.setTitle("新浪新闻");
            commonMenuBean3.setUrl("http://www.sina.com.cn/");
            commonMenuBeanList.add(commonMenuBean1);
            commonMenuBeanList.add(commonMenuBean2);
            commonMenuBeanList.add(commonMenuBean3);
        }
        mView.setURLMenuList(commonMenuBeanList);
        closeRefresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
