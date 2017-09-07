package com.app.lotteryticket.Service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.app.common.HttpRequest.ResultListener.ResultListener;
import com.app.common.MVP.Model.Implement.Base_Model_Implement;
import com.app.common.MVP.Model.Interface.Base_Model_Interface;
import com.app.common.MVP.Presenter.Implement.ProjectUtil_Presenter_Implement;
import com.app.common.MVP.Presenter.Interface.ProjectUtil_Presenter_Interface;
import com.app.common.Public.SD_FilePath;
import com.app.common.Util.ApkController;
import com.app.common.Util.CommonSharePer_GlobalInfo;
import com.app.common.Util.FileUtils;
import com.app.lotteryticket.R;
import com.fenjuly.library.ArrowDownloadButton;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import java.io.File;

/**
 * 文件下载
 * @ClassName: UploadApkService 
 * @Description: TODO
 * @author: Administrator杨重诚
 * @date: 2016-4-22 下午2:16:27
 */
public class UploadService extends IntentService {
	final static String TAG = "UploadService";
	//页面传参
	private String apkName ;//apk名称
	private String apkUrl ;//apk下载路径
	private int apkIcon;//apk图标
	private int isForced;//标识是否强制更新
	private String filePath;//文件本地存放路径

	private Context context;
	Base_Model_Interface mMainBase_model_interface;
	ProjectUtil_Presenter_Interface mProjectUtil_presenter_interface;
	public UploadService() {
		super("com.lottery.Service.UploadService");
		mMainBase_model_interface =new Base_Model_Implement();
		mProjectUtil_presenter_interface= ProjectUtil_Presenter_Implement.getSingleton(this);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		try {
			context=this;
			Bundle bundle=intent.getExtras();
			sendHander(bundle);
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
			this.obtainMessage();
			switch (msg.what) {
				case 0x1001://下载Apk通知
					Bundle bundle=(Bundle) msg.obj;
					apkUrl = bundle.getString("apkUrl","");
					apkName = bundle.getString("apkName");
					apkIcon = bundle.getInt("apkIcon",0);
					isForced=bundle.getInt("isForced",0);
					handler.post(runnable);
					break;
				case 0x1002://更新下载进度
					int progress = msg.arg1;
					contentView.setProgressBar(R.id.progressbar, 100,
							progress, false);
					notiManage.notify(NOTIFY_ID, note);
					break;
				case 0x1003://下载完成，取消通知
					contentView.setTextViewText(R.id.name, "《" + apkName
							+ "》" + "已下载完成");
					contentView.setViewVisibility(R.id.progressbar, View.GONE);
					notiManage.notify(NOTIFY_ID, note);
					notiManage.cancel(NOTIFY_ID);
					CommonSharePer_GlobalInfo.sharePre_PutIsUpdate(false);//将更新标识为无更新状态
					installApk(context,filePath);//安装apk
					stopSelf();//停掉服务
					break;
			}
		}

	};

	// 构建Runnable对象，在runnable中更新界面
	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			setNotification(context,apkIcon,apkName);
			down(SD_FilePath.apkPath, apkUrl);
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
		}

		filePath = saveFilePath + "/lottery.apk";

		mMainBase_model_interface.FileDownloader(filePath, uploadFilePath, new ResultListener() {
			@Override
			public void onResult(boolean isSucc, String msg, String request) {
				if(isSucc){
					//stopSelf();
					handler.sendEmptyMessage(0x1003);//通知下载完成
				}
			}
		}, new Base_Model_Implement.ProgressResultListener() {
			@Override
			public void onProgressChange(long fileSize, long downloadedSize) {
				if(isForced==1){
					setProgress(context,fileSize,downloadedSize,filePath);
				}else{
					setProgress(context,fileSize,downloadedSize,apkName,filePath);
				}
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
	private void sendHander(Bundle bundle) {
		Message message = handler.obtainMessage();
		message.obj = bundle;
		message.what=0x1001;
		handler.sendMessage(message);
	}
//-----------------------------------下载通知消息和安装------------------------
	NotificationManager notiManage;
	Notification note;
	RemoteViews contentView;
	private  final int NOTIFY_ID = 5555;
	public  void setNotification(Context mconte, int icon,String notificationTitle) {
		CharSequence tickerText = "开始下载";
		long when = System.currentTimeMillis();
		if(icon == 0){
			icon = R.mipmap.icon_launcher;
		} else {
			icon = R.mipmap.icon_launcher;
		}
		//创建NotificationManager管理器
		notiManage = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		//创建Notification
		note = new Notification(icon, tickerText, when);
		//设置Notification标志位（非必要步骤）
		//note.flags = Notification.FLAG_ONGOING_EVENT;//表明有程序在运行，该Notification不可由用户清除
		note.flags = Notification.FLAG_AUTO_CANCEL;
		contentView = new RemoteViews(mconte.getPackageName(),
				R.layout.main_notification_update_layout);
		contentView.setTextViewText(R.id.name, "正在下载" + "《"
				+ notificationTitle + "》");
		contentView.setImageViewResource(R.id.image, icon);
		note.contentView = contentView;

		notiManage.notify(NOTIFY_ID, note);
	}

	/**
	 * 强制更新下载动画
	 * @param mconte
	 * @param fileSize
	 * @param downloadedSize
	 * @param saveFileName
     */
	public  void setProgress(Context mconte,long fileSize, long downloadedSize,String saveFileName) {
		NiftyDialogBuilder dialogBuilder=mProjectUtil_presenter_interface.getDownloadDialogBuilder();
		TextView downloadTitle= (TextView) dialogBuilder.findViewById(R.id.downloadTitle);
		ArrowDownloadButton downloadButton=(ArrowDownloadButton) dialogBuilder.findViewById(R.id.arrow_download_button);
		if(downloadButton!=null){
			downloadTitle.setText("正在下载");
			downloadButton.setEnabled(false);
			downloadButton.startAnimating();
			downloadButton.setProgress((int) (downloadedSize * 1.0f / fileSize * 100));
			if ((int) (downloadedSize * 1.0f / fileSize * 100) == 100) {
				CommonSharePer_GlobalInfo.sharePre_PutIsUpdate(false);//将更新标识为无更新状态
				installApk(mconte,saveFileName);//安装apk
				downloadButton.setEnabled(true);
				downloadTitle.setText("重新下载");
			}
		}
	}
	/**
	 *
	 * @Title: setProgress
	 * @Description: TODO
	 * @param mconte
	 * @param fileSize 下载文件总大小
	 * @param downloadedSize 正在下载大小
	 * @param notificationTitle 通知栏title
	 * @param saveFileName apk本地
	 * @return: void
	 */
	public  void setProgress(Context mconte,final long fileSize, final long downloadedSize,String notificationTitle,String saveFileName) {
		// 注：downloadedSize 有可能大于 fileSize，具体原因见下面的描述
		int progress=(int) (downloadedSize * 1.0f / fileSize * 100);
		Message msg = handler.obtainMessage();
		msg.what = 0x1002;
		msg.arg1 = progress;
		if (progress == 5) {
			// 更新进度
			handler.sendMessage(msg);
		} else if ((int) (downloadedSize * 1.0f / fileSize * 100) == 15) {
			handler.sendMessage(msg);
		} else if ((int) (downloadedSize * 1.0f / fileSize * 100) == 30) {
			handler.sendMessage(msg);
		} else if ((int) (downloadedSize * 1.0f / fileSize * 100) == 50) {
			handler.sendMessage(msg);
		} else if ((int) (downloadedSize * 1.0f / fileSize * 100) == 65) {
			handler.sendMessage(msg);
		} else if ((int) (downloadedSize * 1.0f / fileSize * 100) == 85) {
			handler.sendMessage(msg);
		} else if ((int) (downloadedSize * 1.0f / fileSize * 100) == 100) {
			handler.sendMessage(msg);
		}
	}

	/**
	 * 安装apk
	 * @param saveFileName
	 */
	private void installApk(Context context,String saveFileName) {
		File apkfile = new File(saveFileName);
		if (!apkfile.exists()) {
			return;
		}
		if(isForced==1){
			//强制更新,若果有root静默安装，没有正常安装
			ApkController.install(saveFileName, this);
		}else{
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setDataAndType(Uri.fromFile(new File(saveFileName)),
					"application/vnd.android.package-archive");
			context.startActivity(intent);
		}
	}

}
