package com.app.common.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.app.common.R;
import com.chenenyu.router.Router;

public class IntentUtil {

	Intent intent;

	public IntentUtil() {

		intent = new Intent();

	}

	public void intent_to(Context context, Class target_class) {

		intent.setClass(context, target_class);
		context.startActivity(intent);
		((Activity) context).overridePendingTransition(R.anim.slide_in_right,
				R.anim.slide_out_left);
	}
	public void intent_to(Context context, Class target_class,int flag) {

		intent.setClass(context, target_class);
		((Activity) context).startActivityForResult(intent,flag);
		((Activity) context).overridePendingTransition(R.anim.slide_in_right,
				R.anim.slide_out_left);
	}

	public void intent_to(Context context, Class target_class, int startAnim,
			int endAnim) {

		intent.setClass(context, target_class);
		context.startActivity(intent);
		((Activity) context).overridePendingTransition(startAnim, endAnim);
	}

	public void intent_to(Context context, Class target_class, Bundle bundle) {

		intent.setClass(context, target_class);
		intent.putExtras(bundle);
		context.startActivity(intent);
		((Activity) context).overridePendingTransition(R.anim.slide_in_right,
				R.anim.slide_out_left);
	}

	public void intent_to(Context context, Class target_class, Bundle bundle,int flag) {

		intent.setClass(context, target_class);
		intent.putExtras(bundle);
		((Activity) context).startActivityForResult(intent,flag);
		((Activity) context).overridePendingTransition(R.anim.slide_in_right,
				R.anim.slide_out_left);
	}

	public void intent_newTask_to(Context context, Class target_class) {
		intent.setClass(context, target_class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	public void intent_newTask_to(Context context, Class target_class, Bundle bundle) {
		intent.setClass(context, target_class);
		intent.putExtras(bundle);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	/**
	 * 没动画的跳转，无缝对接
	 * @param context
	 * @param target_class
     */
	public void intent_no_animation_to(Context context, Class target_class) {

		intent.setClass(context, target_class);
		context.startActivity(intent);
		((Activity) context).overridePendingTransition(0,
				0);
	}
	/**
	 * 没动画的跳转，无缝对接
	 * @param context
	 * @param target_class
	 */
	public void intent__no_animation_to(Context context, Class target_class, Bundle bundle) {

		intent.setClass(context, target_class);
		intent.putExtras(bundle);
		context.startActivity(intent);
		((Activity) context).overridePendingTransition(0,
				0);
	}

	/**
	 * 跳转activity并销毁其它activity的方法
	 * @param context
	 * @param target_class
     */
	public void intent_destruction_other_activity_to(Context context, Class target_class) {
		intent.setClass(context, target_class);
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(intent);
		((Activity) context).overridePendingTransition(R.anim.slide_in_right,
				R.anim.slide_out_left);
	}

	public void intent_destruction_other_activity_to(Context context, Class target_class,int flag) {
		intent.setClass(context, target_class);
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		((Activity) context).startActivityForResult(intent,flag);
		((Activity) context).overridePendingTransition(R.anim.slide_in_right,
				R.anim.slide_out_left);
	}
	/**
	 * 跳转activity并销毁其它activity的方法
	 * @param context
	 * @param target_class
	 */
	public void intent_destruction_other_activity_to(Context context, Class target_class, Bundle bundle) {
		intent.setClass(context, target_class);
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtras(bundle);
		context.startActivity(intent);
		((Activity) context).overridePendingTransition(R.anim.slide_in_right,
				R.anim.slide_out_left);
	}
	public void intent_destruction_other_activity_to(Context context, Class target_class, Bundle bundle,int flag) {
		intent.setClass(context, target_class);
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtras(bundle);
		((Activity) context).startActivityForResult(intent,flag);
		((Activity) context).overridePendingTransition(R.anim.slide_in_right,
				R.anim.slide_out_left);
	}

	/**
	 * 跳转activity并销毁其它activity的方法，没动画的
	 * @param context
	 * @param target_class
	 */
	public void intent_destruction_other_activity_no_animation_to(Context context, Class target_class) {
		intent.setClass(context, target_class);
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(intent);
		((Activity) context).overridePendingTransition(0,
				0);
	}

	/**
	 * 跳转activity并销毁其它activity的方法，没动画的
	 * @param context
	 * @param target_class
	 */
	public void intent_destruction_other_activity_no_animation_to(Context context, Class target_class, Bundle bundle) {
		intent.setClass(context, target_class);
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtras(bundle);
		context.startActivity(intent);
		((Activity) context).overridePendingTransition(0,
				0);
	}
	int defaultStartAnim=R.anim.slide_in_right;
	int defaultEndAnim=R.anim.slide_out_left;
//==================================路由机制=================================================
public void intent_RouterTo(Context context, String classUrl) {
	Router.build(classUrl).anim(defaultStartAnim,
			defaultEndAnim).go(context);
}
	public void intent_RouterTo(Context context, String classUrl,int flag) {
		Router.build(classUrl).requestCode(flag).anim(defaultStartAnim,
				defaultEndAnim).go(context);
	}

	public void intent_RouterTo(Context context,String classUrl, int startAnim,
						  int endAnim) {
		Router.build(classUrl).anim(startAnim,
				endAnim).go(context);
	}

	public void intent_RouterTo(Context context, String classUrl, Bundle bundle) {
		Router.build(classUrl).with(bundle).anim(defaultStartAnim,
				defaultEndAnim).go(context);
	}

	public void intent_RouterTo(Context context, String classUrl, Bundle bundle,int flag) {

		Router.build(classUrl).requestCode(flag).with(bundle).anim(defaultStartAnim,
				defaultEndAnim).go(context);
	}

	public void intent_newTask_RouterTo(Context context, String classUrl) {
		Router.build(classUrl).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).go(context);
	}

	public void intent_newTask_RouterTo(Context context, String classUrl, Bundle bundle) {
		Router.build(classUrl).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).with(bundle).go(context);
	}
	public void intent_newTaskClearTop_RouterTo(Context context, String classUrl) {
		Router.build(classUrl).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
				addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).anim(defaultStartAnim,defaultEndAnim).go(context);
	}
	/**
	 * 没动画的跳转，无缝对接
	 * @param context
	 */
	public void intent_no_animation_RouterTo(Context context, String classUrl) {
		Router.build(classUrl).anim(0,0).go(context);
	}
	/**
	 * 没动画的跳转，无缝对接
	 * @param context
	 */
	public void intent__no_animation_RouterTo(Context context, String classUrl, Bundle bundle) {
		Router.build(classUrl).with(bundle).anim(0,0).go(context);
	}

	/**
	 * 跳转activity并销毁其它activity的方法
	 * @param context
	 */
	public void intent_destruction_other_activity_RouterTo(Context context, String classUrl) {
		Router.build(classUrl).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP).
				addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).anim(defaultStartAnim,defaultEndAnim).go(context);
	}

	public void intent_destruction_other_activity_RouterTo(Context context, String classUrl,int flag) {

		Router.build(classUrl).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP).
				addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).requestCode(flag).anim(defaultStartAnim,defaultEndAnim).go(context);
	}
	/**
	 * 跳转activity并销毁其它activity的方法
	 * @param context
	 */
	public void intent_destruction_other_activity_RouterTo(Context context, String classUrl, Bundle bundle) {
		Router.build(classUrl).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP).
				addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).with(bundle).anim(defaultStartAnim,defaultEndAnim).go(context);
	}
	public void intent_destruction_other_activity_RouterTo(Context context, String classUrl, Bundle bundle,int flag) {
		Router.build(classUrl).requestCode(flag).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP).
				addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).with(bundle).anim(defaultStartAnim,defaultEndAnim).go(context);
	}

	/**
	 * 跳转activity并销毁其它activity的方法，没动画的
	 * @param context
	 */
	public void intent_destruction_other_activity_no_animation_RouterTo(Context context,  String classUrl) {

		Router.build(classUrl).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP).
				addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).anim(0,0).go(context);
	}

	/**
	 * 跳转activity并销毁其它activity的方法，没动画的
	 * @param context
	 */
	public void intent_destruction_other_activity_no_animation_RouterTo(Context context, String classUrl, Bundle bundle) {
		Router.build(classUrl).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP).
				addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).with(bundle).anim(0,0).go(context);
	}
}
