package com.app.lotteryticket.Service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.app.common.HttpRequest.ResultListener.ResultListener;
import com.app.common.MVP.Model.Implement.Base_Model_Implement;
import com.app.common.MVP.Model.Interface.Base_Model_Interface;
import com.app.common.Public.SD_FilePath;
import com.app.common.Util.FileUtils;
import com.app.lotteryticket.MVP.Model.Bean.WelcomePageBean;
import com.app.lotteryticket.Util.Main_SharePer_SdCard_Info;

import java.io.File;

/**
 * @ClassName: com.ygworld.Service
 * @author: Administrator 杨重诚
 * @date: 2016/10/14:14:39
 */

public class WelcomePageService extends IntentService {
    Base_Model_Interface mMainBase_model_interface;
    private String uploadPath = "";// 下载文件路径
    WelcomePageBean mWelcomePageBean;// 欢迎页page存储数据对象

    public WelcomePageService() {
        super("com.lottery.Service.WelcomePageService");
        mMainBase_model_interface =new Base_Model_Implement();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            mWelcomePageBean=intent.getParcelableExtra("WelcomePageBean");
            sendHander();
        } catch (Exception e) {
            e.printStackTrace();
            stopSelf();
        }

    }

    public void onDestroy() {
        super.onDestroy();
        stopSelf();
    }

    // -----------------上传服务器的 Handler-----------------------
    private Handler handler = new Handler() {
        @Override
        // 当有消息发送出来的时候就执行Handler的这个方法
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            uploadPath = (String) msg.obj;
            handler.post(runnable);
        }

    };

    // 构建Runnable对象，在runnable中更新界面
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            down(SD_FilePath.welcomePagePath, uploadPath);
        }

    };

    /**
     * 下载文件
     *
     * @Title: down
     * @Description: TODO
     * @param saveFilePath
     * @param uploadFilePath
     * @return: void
     */
    private void down(String saveFilePath, String uploadFilePath) {
        if (!new File(saveFilePath).exists()) {
            try {
                FileUtils.createSDCardDir(saveFilePath);// 创建文件夹
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return;
            }
        } else {
                FileUtils.deleteFile(saveFilePath);// 删除文件夹里面的文件
                Main_SharePer_SdCard_Info.sharePre_PutWelcomePageBean(null);//初始化本地缓存
        }

        String[] str = uploadFilePath.split("/");
        String filePath = saveFilePath + "/"
                + str[str.length - 1];
        mMainBase_model_interface.FileDownloader(filePath, uploadFilePath, new ResultListener() {
            @Override
            public void onResult(boolean isSucc, String msg, String request) {
                if(isSucc){
                    //存放启动页bean对象
                    Main_SharePer_SdCard_Info.sharePre_PutWelcomePageBean(mWelcomePageBean);
                }
                stopSelf();
            }
        });
    }

    /**
     * 发送Hander
     *
     * @Title: sendHander
     * @Description: TODO
     * @return: void
     */
    private void sendHander() {
        uploadPath = mWelcomePageBean.getUrl();
        Message message = Message.obtain();
        message.obj = uploadPath;
        handler.sendMessage(message);
    }
}
