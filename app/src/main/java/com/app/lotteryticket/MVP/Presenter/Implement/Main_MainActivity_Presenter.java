package com.app.lotteryticket.MVP.Presenter.Implement;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.app.common.MVP.Model.Implement.Base_Model_Implement;
import com.app.common.MVP.Model.Interface.Base_Model_Interface;
import com.app.common.Util.ToastUtil;
import com.app.lotteryticket.Base.My_Application;
import com.app.lotteryticket.MVP.Contract.Main_MainActivity_Contract;


/**
 * Created by ${杨重诚} on 2017/6/2.
 */

public class Main_MainActivity_Presenter extends Main_MainActivity_Contract.Presenter {
    My_Application mApplication;
    Base_Model_Interface mBase_model_implement;//请求网络数据的model实现类
    boolean isExit;//返回按钮退出的监听标识

    public Main_MainActivity_Presenter() {
        mApplication = My_Application.getInstance();
        mBase_model_implement = new Base_Model_Implement();
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    public void exit() {
        if (!isExit) {
            isExit = true;
            ToastUtil.RightImageToast(context, "再次返回退出程序", "show");
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            final Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    context.startActivity(intent);
                    System.exit(0);
                }
            }, 300);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
