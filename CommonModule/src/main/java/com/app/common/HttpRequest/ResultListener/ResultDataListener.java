package com.app.common.HttpRequest.ResultListener;


import com.app.common.MVP.Model.Bean.Request_Bean;

/**
 * 监听请求服务的接口
 * @author Administrator
 */
public interface ResultDataListener {
	/**
	 * 请求服务器的回调接口方法
	 * @param isSucc 请求的结果
	 * @param msg   返回状态类型  ResultCodeEnum
	 * @param request_bean      返回内容
	 * @return
	 */
	public void onResult(boolean isSucc, String msg, Request_Bean request_bean);
}
