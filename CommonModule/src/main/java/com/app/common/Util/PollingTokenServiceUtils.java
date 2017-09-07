package com.app.common.Util;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import java.util.List;

public class PollingTokenServiceUtils {
	
	/** 
	 * 判断某个服务是否正在运行的方法 
	 *  
	 * @param mContext 
	 * @param serviceName 
	 *            是包名+服务的类名（例如：net.loonggg.testbackstage.TestService） 
	 * @return true代表正在运行，false代表服务没有正在运行 
	 */  
	public static boolean isServiceWork(Context mContext, String serviceName) {  
	    boolean isWork = false;  
	    ActivityManager myAM = (ActivityManager) mContext  
	            .getSystemService(Context.ACTIVITY_SERVICE);  
	    List<RunningServiceInfo> myList = myAM.getRunningServices(100);  
	    if (myList.size() <= 0) {  
	        return false;  
	    }  
	    for (int i = 0; i < myList.size(); i++) {  
	        String mName = myList.get(i).service.getClassName().toString();
	        if (mName.equals(serviceName)) {  
	            isWork = true;  
	            break;  
	        }  
	    }  
	    return isWork;  
	}  

	//开启轮询服务
		public static void startPollingService(Context context, int seconds, Class<?> cls,String action) {
			//获取AlarmManager系统服务
			AlarmManager manager = (AlarmManager) context
					.getSystemService(Context.ALARM_SERVICE);
			
			//包装需要执行Service的Intent
			Intent intent = new Intent(context, cls);
			intent.setAction(action);
			PendingIntent pendingIntent = PendingIntent.getService(context, 0,
					intent, PendingIntent.FLAG_UPDATE_CURRENT);
			
			//触发服务的起始时间
			long triggerAtTime = SystemClock.elapsedRealtime();
			
			//使用AlarmManger的setRepeating方法设置定期执行的时间间隔（seconds秒）和需要执行的Service
			manager.setRepeating(AlarmManager.ELAPSED_REALTIME, triggerAtTime,
					seconds * 1000, pendingIntent);
		}

		//停止轮询服务
		public static void stopPollingService(Context context, Class<?> cls,String action) {
			AlarmManager manager = (AlarmManager) context
					.getSystemService(Context.ALARM_SERVICE);
			Intent intent = new Intent(context, cls);
			intent.setAction(action);
			PendingIntent pendingIntent = PendingIntent.getService(context, 0,
					intent, PendingIntent.FLAG_UPDATE_CURRENT);
			//取消正在执行的服务
			manager.cancel(pendingIntent);
			context.stopService(intent);
		}
}
