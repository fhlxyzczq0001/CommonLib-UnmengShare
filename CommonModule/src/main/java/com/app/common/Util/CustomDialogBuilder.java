package com.app.common.Util;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.TextView;

import com.app.common.R;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

/**
 * 确认对话框
 *
 * @ClassName: com.ddt.supaiwang.Util
 * @Description:
 * @author: Administrator杨重诚
 * @date: 2016/6/6 17: 34
 */
public class CustomDialogBuilder {
    Context context;
    NiftyDialogBuilder dialogBuilder = null;
    private Effectstype effect = Effectstype.RotateBottom;

    public CustomDialogBuilder(Context context) {
        this.context = context;
        if (dialogBuilder == null) {
            dialogBuilder = NiftyDialogBuilder.getInstance(context);
        }
    }

    /**
     * 显示弹出框
     *
     * @param title                      弹出框标题
     * @param titleColor                 弹出框标题颜色
     * @param messageTitle               展示信息标题
     * @param messageColor               展示信息标题颜色
     * @param dialogColor                弹出框背景颜色
     * @param isCancelableOnTouchOutside 是否可以触摸其它地方隐藏弹出框
     * @param button1Text1               操作按钮1的信息
     * @param button1Text2               操作按钮2的信息
     * @param layout                     自定义布局
     * @param buttonDrawable             按钮背景色
     * @return
     */
    public NiftyDialogBuilder showDialog(int layout, String title, int titleColor, String messageTitle, int messageColor, int dialogColor, boolean isCancelableOnTouchOutside, String button1Text1, String button1Text2, int buttonDrawable) {
        dialogBuilder
                .withTitle(title)//设置弹出框标题
                .withTitleColor(context.getResources().getColor(titleColor))//弹出框标题颜色
                .withDividerColor(context.getResources().getColor(R.color.line_gray))//弹出框标题分界线颜色
                .withMessage(messageTitle)//展示信息标题
                .withMessageColor(context.getResources().getColor(messageColor))//展示信息标题颜色
                .withDialogColor(context.getResources().getColor(dialogColor))//弹出框背景颜色
                .withIcon(context.getResources().getDrawable(R.mipmap.icon_launcher))//icon图标
                .isCancelableOnTouchOutside(isCancelableOnTouchOutside)//是否可以触摸其它地方隐藏弹出框
                .withDuration(700)//动画持续时间
                .withEffect(effect)//动画类型
                .withButton1Text(button1Text1)//操作按钮1的信息
                .withButton2Text(button1Text2)//操作按钮2的信息
                .withButtonDrawable(buttonDrawable)//操作按钮的背景色
                .setCustomView(layout, context)//自定义布局展示
                .setButton1Click(new View.OnClickListener() {//默认操作按钮1的点击事件
                    @Override
                    public void onClick(View view) {
                        dialogBuilder.dismiss();
                    }
                })
                .setButton2Click(new View.OnClickListener() {//默认操作按钮2的点击事件
                    @Override
                    public void onClick(View view) {
                        dialogBuilder.dismiss();
                    }
                })
                .show();
        return dialogBuilder;
    }

    /**
     * 显示弹出框
     *
     * @param title                      弹出框标题
     * @param titleColor                 弹出框标题颜色
     * @param messageTitle               展示信息标题
     * @param messageColor               展示信息标题颜色
     * @param dialogColor                弹出框背景颜色
     * @param isCancelableOnTouchOutside 是否可以触摸其它地方隐藏弹出框
     * @param button1Text1               操作按钮1的信息
     * @param button1Text2               操作按钮2的信息
     * @param buttonDrawable             按钮背景色
     * @param contentMsg                 内容信息
     * @param contentColor               内容颜色
     * @return
     */
    public NiftyDialogBuilder showDialog(String title, int titleColor, String messageTitle, int messageColor, String contentMsg, int contentColor, int dialogColor, boolean isCancelableOnTouchOutside, String button1Text1, String button1Text2, int buttonDrawable) {
        dialogBuilder
                .withTitle(title)//设置弹出框标题
                .withTitleColor(context.getResources().getColor(titleColor))//弹出框标题颜色
                .withDividerColor(context.getResources().getColor(R.color.line_gray))//弹出框标题分界线颜色
                .withMessage(messageTitle)//展示信息标题
                .withMessageColor(context.getResources().getColor(messageColor))//展示信息标题颜色
                .withDialogColor(context.getResources().getColor(dialogColor))//弹出框背景颜色
                .withIcon(context.getResources().getDrawable(R.mipmap.icon_launcher))//icon图标
                .isCancelableOnTouchOutside(isCancelableOnTouchOutside)//是否可以触摸其它地方隐藏弹出框
                .withDuration(700)//动画持续时间
                .withEffect(effect)//动画类型
                .withButton1Text(button1Text1)//操作按钮1的信息
                .withButton2Text(button1Text2)//操作按钮2的信息
                .withButtonDrawable(buttonDrawable)//操作按钮的背景色
                .setCustomView(R.layout.common_dialog_custom_view, context)//自定义布局展示
                .setButton1Click(new View.OnClickListener() {//默认操作按钮1的点击事件
                    @Override
                    public void onClick(View view) {
                        dialogBuilder.dismiss();
                    }
                })
                .setButton2Click(new View.OnClickListener() {//默认操作按钮2的点击事件
                    @Override
                    public void onClick(View view) {
                        dialogBuilder.dismiss();
                    }
                })
                .show();
        setContentMsg(dialogBuilder, contentMsg, contentColor);//设置弹出内容
        return dialogBuilder;
    }

    /**
     * 显示弹出框
     *
     * @param messageTitle               展示信息标题
     * @param messageColor               展示信息标题颜色
     * @param dialogColor                弹出框背景颜色
     * @param isCancelableOnTouchOutside 是否可以触摸其它地方隐藏弹出框
     * @param button1Text1               操作按钮1的信息
     * @param button1Text2               操作按钮2的信息
     * @param layout                     自定义布局
     * @param buttonDrawable             按钮背景色
     * @return
     */
    public NiftyDialogBuilder showDialog(String messageTitle, int messageColor, int dialogColor, boolean isCancelableOnTouchOutside, String button1Text1, String button1Text2, int layout, int buttonDrawable) {
        dialogBuilder
                .withTitle(context.getResources().getString(R.string.app_name))
                .withTitleColor(context.getResources().getColor(R.color.black))
                .withDividerColor(context.getResources().getColor(R.color.line_gray))
                .withMessage(messageTitle)
                .withMessageColor(context.getResources().getColor(messageColor))
                .withDialogColor(context.getResources().getColor(dialogColor))
                .withIcon(context.getResources().getDrawable(R.mipmap.icon_launcher))
                .isCancelableOnTouchOutside(isCancelableOnTouchOutside)
                .withDuration(700)
                .withEffect(effect)
                .withButton1Text(button1Text1)
                .withButton2Text(button1Text2)
                .withButtonDrawable(buttonDrawable)
                .setCustomView(layout, context)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogBuilder.dismiss();
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogBuilder.dismiss();
                    }
                })
                .show();
        return dialogBuilder;
    }

    /**
     * 显示弹出框
     *
     * @param messageTitle               展示信息标题
     * @param messageColor               展示信息标题颜色
     * @param dialogColor                弹出框背景颜色
     * @param isCancelableOnTouchOutside 是否可以触摸其它地方隐藏弹出框
     * @param button1Text1               操作按钮1的信息
     * @param button1Text2               操作按钮2的信息
     * @param buttonDrawable             按钮背景色
     * @param contentMsg                 内容信息
     * @param contentColor               内容颜色
     * @return
     */
    public NiftyDialogBuilder showDialog(String messageTitle, int messageColor, String contentMsg, int contentColor, int dialogColor, boolean isCancelableOnTouchOutside, String button1Text1, String button1Text2, int buttonDrawable) {
        dialogBuilder
                .withTitle(context.getResources().getString(R.string.app_name))
                .withTitleColor(context.getResources().getColor(R.color.black))
                .withDividerColor(context.getResources().getColor(R.color.line_gray))
                .withMessage(messageTitle)
                .withMessageColor(context.getResources().getColor(messageColor))
                .withDialogColor(context.getResources().getColor(dialogColor))
                .withIcon(context.getResources().getDrawable(R.mipmap.icon_launcher))
                .isCancelableOnTouchOutside(isCancelableOnTouchOutside)
                .withDuration(700)
                .withEffect(effect)
                .withButton1Text(button1Text1)
                .withButton2Text(button1Text2)
                .withButtonDrawable(buttonDrawable)
                .setCustomView(R.layout.common_dialog_custom_view, context)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogBuilder.dismiss();
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogBuilder.dismiss();
                    }
                })
                .show();
        setContentMsg(dialogBuilder, contentMsg, contentColor);//设置弹出内容
        return dialogBuilder;
    }


    /**
     * 显示弹出框
     *
     * @param messageTitle               展示信息标题
     * @param isCancelableOnTouchOutside 是否可以触摸其它地方隐藏弹出框
     * @param button1Text1               操作按钮1的信息
     * @param button1Text2               操作按钮2的信息
     * @param layout                     自定义布局
     * @param buttonDrawable             按钮背景色
     * @return
     */
    public NiftyDialogBuilder showDialog(String messageTitle, boolean isCancelableOnTouchOutside, String button1Text1, String button1Text2, int layout, int buttonDrawable) {
        dialogBuilder
                .withTitle(context.getResources().getString(R.string.app_name))
                .withTitleColor(context.getResources().getColor(R.color.black))
                .withDividerColor(context.getResources().getColor(R.color.line_gray))
                .withMessage(messageTitle)
                .withMessageColor(context.getResources().getColor(R.color.account_text_gray))
                .withDialogColor(context.getResources().getColor(R.color.white))
                .withIcon(context.getResources().getDrawable(R.mipmap.icon_launcher))
                .isCancelableOnTouchOutside(isCancelableOnTouchOutside)
                .withDuration(700)
                .withEffect(effect)
                .withButton1Text(button1Text1)
                .withButton2Text(button1Text2)
                .withButtonDrawable(buttonDrawable)
                .setCustomView(layout, context)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogBuilder.dismiss();
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogBuilder.dismiss();
                    }
                })
                .show();
        return dialogBuilder;
    }

    /**
     * 显示弹出框
     *
     * @param messageTitle               展示信息标题
     * @param isCancelableOnTouchOutside 是否可以触摸其它地方隐藏弹出框
     * @param button1Text1               操作按钮1的信息
     * @param button1Text2               操作按钮2的信息
     * @param buttonDrawable             按钮背景色
     * @param contentMsg                 内容信息
     * @param contentColor               内容颜色
     * @return
     */
    public NiftyDialogBuilder showDialog(String messageTitle, String contentMsg, int contentColor, boolean isCancelableOnTouchOutside, String button1Text1, String button1Text2, int buttonDrawable) {
        dialogBuilder
                .withTitle(context.getResources().getString(R.string.app_name))
                .withTitleColor(context.getResources().getColor(R.color.black))
                .withDividerColor(context.getResources().getColor(R.color.line_gray))
                .withMessage(messageTitle)
                .withMessageColor(context.getResources().getColor(R.color.account_text_gray))
                .withDialogColor(context.getResources().getColor(R.color.white))
                .withIcon(context.getResources().getDrawable(R.mipmap.icon_launcher))
                .isCancelableOnTouchOutside(isCancelableOnTouchOutside)
                .withDuration(700)
                .withEffect(effect)
                .withButton1Text(button1Text1)
                .withButton2Text(button1Text2)
                .withButtonDrawable(buttonDrawable)
                .setCustomView(R.layout.common_dialog_custom_view, context)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogBuilder.dismiss();
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogBuilder.dismiss();
                    }
                })
                .show();
        setContentMsg(dialogBuilder, contentMsg, contentColor);//设置弹出内容
        return dialogBuilder;
    }

    /**
     * 显示弹出框
     *
     * @param messageTitle   展示信息标题
     * @param button1Text1   操作按钮1的信息
     * @param button1Text2   操作按钮2的信息
     * @param layout         自定义布局
     * @param buttonDrawable 按钮背景色
     * @return
     */
    public NiftyDialogBuilder showDialog(String messageTitle, String button1Text1, String button1Text2, int layout, int buttonDrawable) {
        dialogBuilder
                .withTitle(context.getResources().getString(R.string.app_name))
                .withTitleColor(context.getResources().getColor(R.color.black))
                .withDividerColor(context.getResources().getColor(R.color.line_gray))
                .withMessage(messageTitle)
                .withMessageColor(context.getResources().getColor(R.color.account_text_gray))
                .withDialogColor(context.getResources().getColor(R.color.white))
                .withIcon(context.getResources().getDrawable(R.mipmap.icon_launcher))
                .isCancelableOnTouchOutside(true)
                .withDuration(700)
                .withEffect(effect)
                .withButton1Text(button1Text1)
                .withButton2Text(button1Text2)
                .withButtonDrawable(buttonDrawable)
                .setCustomView(layout, context)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogBuilder.dismiss();
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogBuilder.dismiss();
                    }
                })
                .show();
        return dialogBuilder;
    }

    /**
     * 显示弹出框
     *
     * @param messageTitle   展示信息标题
     * @param button1Text1   操作按钮1的信息
     * @param button1Text2   操作按钮2的信息
     * @param buttonDrawable 按钮背景色
     * @param contentMsg     内容信息
     * @param contentColor   内容颜色
     * @return
     */
    public NiftyDialogBuilder showDialog(String messageTitle, String contentMsg, int contentColor, String button1Text1, String button1Text2, int buttonDrawable) {
        dialogBuilder
                .withTitle(context.getResources().getString(R.string.app_name))
                .withTitleColor(context.getResources().getColor(R.color.black))
                .withDividerColor(context.getResources().getColor(R.color.line_gray))
                .withMessage(messageTitle)
                .withMessageColor(context.getResources().getColor(R.color.account_text_gray))
                .withDialogColor(context.getResources().getColor(R.color.white))
                .withIcon(context.getResources().getDrawable(R.mipmap.icon_launcher))
                .isCancelableOnTouchOutside(true)
                .withDuration(700)
                .withEffect(effect)
                .withButton1Text(button1Text1)
                .withButton2Text(button1Text2)
                .withButtonDrawable(buttonDrawable)
                .setCustomView(R.layout.common_dialog_custom_view, context)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogBuilder.dismiss();
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogBuilder.dismiss();
                    }
                })
                .show();
        setContentMsg(dialogBuilder, contentMsg, contentColor);//设置弹出内容
        return dialogBuilder;
    }

    /**
     * 显示弹出框
     *
     * @param messageTitle   展示信息标题
     * @param layout         自定义布局
     * @param buttonDrawable 按钮背景色
     * @return
     */
    public NiftyDialogBuilder showDialog(String messageTitle, int layout, int buttonDrawable) {
        dialogBuilder
                .withTitle(context.getResources().getString(R.string.app_name))
                .withTitleColor(context.getResources().getColor(R.color.black))
                .withDividerColor(context.getResources().getColor(R.color.line_gray))
                .withMessage(messageTitle)
                .withMessageColor(context.getResources().getColor(R.color.account_text_gray))
                .withDialogColor(context.getResources().getColor(R.color.white))
                .withIcon(context.getResources().getDrawable(R.mipmap.icon_launcher))
                .isCancelableOnTouchOutside(true)
                .withDuration(700)
                .withEffect(effect)
                .withButton1Text("")
                .withButton1Text("取  消")
                .withButton2Text("确  定")
                .withButtonDrawable(buttonDrawable)
                .setCustomView(layout, context)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogBuilder.dismiss();
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogBuilder.dismiss();
                    }
                })
                .show();
        return dialogBuilder;
    }


    /**
     * 显示弹出框
     *
     * @param messageTitle 展示信息标题
     * @param but1Drawable 按钮1背景色
     * @param but2Drawable 按钮2背景色
     * @param contentMsg   内容信息
     * @param contentColor 内容颜色
     * @return
     */
    public NiftyDialogBuilder showDialog(String messageTitle, String contentMsg, int contentColor, int but1Drawable, int but2Drawable) {
        dialogBuilder
                .withTitle(context.getResources().getString(R.string.app_name))
                .withTitleColor(context.getResources().getColor(R.color.black))
                .withDividerColor(context.getResources().getColor(R.color.line_gray))
                .withMessage(messageTitle)
                .withMessageColor(context.getResources().getColor(R.color.account_text_gray))
                .withDialogColor(context.getResources().getColor(R.color.white))
                .withIcon(context.getResources().getDrawable(R.mipmap.icon_launcher))
                .isCancelableOnTouchOutside(true)
                .withDuration(700)
                .withEffect(effect)
                .withButton1Text("")
                .withButton1Text("取  消")
                .withButton2Text("确  定")
                .withButtonDrawable(but1Drawable, but2Drawable)
                .setCustomView(R.layout.common_dialog_custom_view, context)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogBuilder.dismiss();
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogBuilder.dismiss();
                    }
                })
                .show();
        setContentMsg(dialogBuilder, contentMsg, contentColor);//设置弹出内容
        return dialogBuilder;
    }
    //---------------------------------------------------项目中引用到的-------------------------------------------------------------

    /**
     * 显示弹出框-------(购物车红包弹窗)
     *
     * @param messageTitle   展示信息标题
     * @param layout         自定义布局
     * @param buttonDrawable 按钮背景色
     * @return
     */
    public NiftyDialogBuilder showDialog(String title, String messageTitle, int layout, int buttonDrawable) {
        dialogBuilder
                .withTitle(title)
                .withTitleColor(context.getResources().getColor(R.color.black))
                .withDividerColor(context.getResources().getColor(R.color.line_gray))
                .withMessage(messageTitle)
                .withMessageColor(context.getResources().getColor(R.color.account_text_gray))
                .withDialogColor(context.getResources().getColor(R.color.white))
                .withIcon(context.getResources().getDrawable(R.mipmap.icon_launcher))
                .isCancelableOnTouchOutside(true)
                .withDuration(700)
                .withEffect(effect)
                .withButton2Text("确  定")
                .withButtonDrawable(buttonDrawable)
                .setCustomView(layout, context)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogBuilder.dismiss();
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogBuilder.dismiss();
                    }
                })
                .show();
        return dialogBuilder;
    }

    /**
     * 显示弹出框-------（购物车删除）
     *
     * @param but1Drawable 按钮1背景色
     * @param but2Drawable 按钮2背景色
     * @param contentMsg   内容信息
     * @param contentColor 内容颜色
     * @return
     */
    public NiftyDialogBuilder showDialog(String contentMsg, int contentColor, int but1Drawable, int but2Drawable) {
        dialogBuilder
                .withTitle(context.getResources().getString(R.string.app_name))
                .withTitleColor(context.getResources().getColor(R.color.black))
                .withCloseImage(R.mipmap.icon_close_x)
                .withDividerColor(context.getResources().getColor(R.color.line_gray))
                .withMessage(null)
                .withMessageColor(context.getResources().getColor(R.color.account_text_gray))
                .withDialogColor(context.getResources().getColor(R.color.white))
                .withIcon(context.getResources().getDrawable(R.mipmap.icon_launcher))
                .isCancelableOnTouchOutside(true)
                .withDuration(700)
                .withEffect(effect)
                .withButton1Text("")
                .withButton1Text("取  消")
                .withButton2Text("确  定")
                .withButtonDrawable(but1Drawable, but2Drawable)
                .setCustomView(R.layout.common_dialog_custom_view, context)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogBuilder.dismiss();
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogBuilder.dismiss();
                    }
                })
                .show();
        setContentMsg(dialogBuilder, contentMsg, contentColor);//设置弹出内容
        return dialogBuilder;
    }

    /**
     * 显示弹出框——没有确定和取消按钮的
     *
     * @param contentMsg
     * @param contentColor
     * @return
     */
    public NiftyDialogBuilder showDialog(String contentMsg, int contentColor) {
        dialogBuilder
                .withTitle(context.getResources().getString(R.string.app_name))
                .withTitleColor(context.getResources().getColor(R.color.black))
                .withCloseImage(R.mipmap.icon_close_x)
                .withDividerColor(context.getResources().getColor(R.color.line_gray))
                .withMessage(null)
                .withMessageColor(context.getResources().getColor(R.color.account_text_gray))
                .withDialogColor(context.getResources().getColor(R.color.white))
                .withIcon(context.getResources().getDrawable(R.mipmap.icon_launcher))
                .isCancelableOnTouchOutside(true)
                .withDuration(700)
                .withEffect(effect)
                .withButton1Text(null)
                .withButton2Text(null)
                .setCustomView(R.layout.common_dialog_custom_view, context)
                .show();
        setContentMsg(dialogBuilder, contentMsg, contentColor);//设置弹出内容
        return dialogBuilder;
    }

    /**
     * 显示弹出框——没有确定和取消按钮的--自定义contentview
     *
     * @param layout
     * @return
     */
    public NiftyDialogBuilder showDialog(int layout, String title) {
        dialogBuilder
                .withTitle(title)
                .withTitleColor(context.getResources().getColor(R.color.black))
                .withCloseImage(R.mipmap.icon_close_x)
                .withDividerColor(context.getResources().getColor(R.color.line_gray))
                .withMessage(null)
                .withMessageColor(context.getResources().getColor(R.color.account_text_gray))
                .withDialogColor(context.getResources().getColor(R.color.white))
                .withIcon(context.getResources().getDrawable(R.mipmap.icon_launcher))
                .isCancelableOnTouchOutside(true)
                .withDuration(700)
                .withEffect(effect)
                .withButton1Text(null)
                .withButton2Text(null)
                .setCustomView(layout, context)
                .show();
        return dialogBuilder;
    }
    /**
     * 显示弹出框——没有确定和取消按钮的--自定义contentview
     *
     * @param view
     * @return
     */
    public NiftyDialogBuilder showDialog(View view, String title) {
        dialogBuilder
                .withTitle(title)
                .withTitleColor(context.getResources().getColor(R.color.black))
                .withCloseImage(R.mipmap.icon_close_x)
                .withDividerColor(context.getResources().getColor(R.color.line_gray))
                .withMessage(null)
                .withMessageColor(context.getResources().getColor(R.color.account_text_gray))
                .withDialogColor(context.getResources().getColor(R.color.white))
                .withIcon(null)
                .isCancelableOnTouchOutside(false)
                .withDuration(700)
                .withEffect(effect)
                .withButton1Text(null)
                .withButton2Text(null)
                .setCustomView(view, context)
                .show();
        return dialogBuilder;
    }
    /**
     * 设置页面---联系客服/检测更新/清理缓存
     *
     * @param titleMsg     标题
     * @param contentMsg   内容信息
     * @param contentColor 内容颜色
     * @param button1Text1 按钮1内容
     * @param but1Drawable 按钮1背景色
     * @param button1Text2 按钮2内容
     * @param but2Drawable 按钮2背景色
     * @return
     */
    public NiftyDialogBuilder showDialog(String titleMsg, String contentMsg, int contentColor, String button1Text1, int but1Drawable, String button1Text2, int but2Drawable) {
        dialogBuilder
                .withTitle(titleMsg)
                .withTitleColor(context.getResources().getColor(R.color.black))
                .withCloseImage(R.mipmap.icon_close_x)
                .withDividerColor(context.getResources().getColor(R.color.line_gray))
                .withMessage(null)
                .withMessageColor(context.getResources().getColor(R.color.account_text_gray))
                .withDialogColor(context.getResources().getColor(R.color.white))
                .isCancelableOnTouchOutside(true)
                .withDuration(700)
                .withEffect(effect)
                .withButton1Text(button1Text1)
                .withButton2Text(button1Text2)
                .withButtonDrawable(but1Drawable, but2Drawable)
                .setCustomView(R.layout.common_dialog_custom_view, context)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogBuilder.dismiss();
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogBuilder.dismiss();
                    }
                })
                .show();
        setContentMsg(dialogBuilder, contentMsg, contentColor);//设置弹出内容
        return dialogBuilder;
    }

    /**
     * 软件更新
     * @param titleMsg
     * @param contentMsg
     * @param contentColor
     * @param isCancelableOnTouchOutside
     * @param button1Text1
     * @param but1Drawable
     * @param button1Text2
     * @param but2Drawable
     * @return
     */
    public NiftyDialogBuilder showDialog(String titleMsg, String contentMsg, int contentColor,boolean isCancelableOnTouchOutside, String button1Text1, int but1Drawable, String button1Text2, int but2Drawable) {
        dialogBuilder
                .withTitle(titleMsg)
                .withTitleColor(context.getResources().getColor(R.color.black))
                .withCloseImage(R.mipmap.icon_close_x)
                .withDividerColor(context.getResources().getColor(R.color.line_gray))
                .withMessage(null)
                .withMessageColor(context.getResources().getColor(R.color.account_text_gray))
                .withDialogColor(context.getResources().getColor(R.color.white))
                .isCancelableOnTouchOutside(isCancelableOnTouchOutside)
                .withDuration(700)
                .withEffect(effect)
                .withButton1Text(button1Text1)
                .withButton2Text(button1Text2)
                .withButtonDrawable(but1Drawable, but2Drawable)
                .setCustomView(R.layout.common_dialog_custom_view, context)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogBuilder.dismiss();
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogBuilder.dismiss();
                    }
                })
                .show();
        setContentMsg(dialogBuilder, contentMsg, contentColor);//设置弹出内容
        return dialogBuilder;
    }

    /**
     * 显示弹出框-------显示输入圈子密码dialog
     *
     * @param messageTitle   展示信息标题
     * @param layout         自定义布局
     * @param buttonDrawable 按钮背景色
     * @return
     */
    public NiftyDialogBuilder showDialog(String title, String messageTitle, int layout, int buttonDrawable, String button1Text2) {
        dialogBuilder
                .withTitle(title)
                .withTitleColor(context.getResources().getColor(R.color.black))
                .withDividerColor(context.getResources().getColor(R.color.line_gray))
                .withMessage(messageTitle)
                .withMessageColor(context.getResources().getColor(R.color.account_text_gray))
                .withDialogColor(context.getResources().getColor(R.color.white))
                .withIcon(context.getResources().getDrawable(R.mipmap.icon_launcher))
                .isCancelableOnTouchOutside(true)
                .withDuration(700)
                .withEffect(effect)
                .withButton2Text(button1Text2)
                .withButtonDrawable(buttonDrawable)
                .setCustomView(layout, context)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogBuilder.dismiss();
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogBuilder.dismiss();
                    }
                })
                .show();
        return dialogBuilder;
    }

    /**
     * 显示弹出框--显示发起圈子成功的dialog
     *
     * @param isCancelableOnTouchOutside 是否可以触摸其它地方隐藏弹出框
     * @param button1Text1               操作按钮1的信息
     * @param button1Text2               操作按钮2的信息
     * @param layout                     自定义布局
     * @return
     */
    public NiftyDialogBuilder showDialog(String title, boolean isCancelableOnTouchOutside, String button1Text1, String button1Text2, int but1Drawable, int but2Drawable, int layout) {
        dialogBuilder
                .withTitle(title)
                .withTitleColor(context.getResources().getColor(R.color.black))
                .withDividerColor(context.getResources().getColor(R.color.line_gray))
                .withIcon(context.getResources().getDrawable(R.mipmap.icon_launcher))
                .isCancelableOnTouchOutside(isCancelableOnTouchOutside)
                .withDuration(700)
                .withEffect(effect)
                .withButton1Text(button1Text1)
                .withButton2Text(button1Text2)
                .withButtonDrawable(but1Drawable, but2Drawable)
                .setCustomView(layout, context)
                .show();
        return dialogBuilder;
    }


    /**
     * 显示弹出框-------商品详情用户购买记录的弹窗
     *
     * @param layout         自定义布局
     * @param buttonDrawable 按钮背景色
     * @return
     */
    public NiftyDialogBuilder showDialog(String title, int layout, int buttonDrawable,boolean isCancelableOnTouchOutside) {
        dialogBuilder
                .withTitle(title)
                .withTitleColor(context.getResources().getColor(R.color.black))
                .withDividerColor(context.getResources().getColor(R.color.line_gray))
                .withDialogColor(context.getResources().getColor(R.color.white))
                .withIcon(null)
                .isCancelableOnTouchOutside(true)
                .withDuration(700)
                .withEffect(effect)
                .isCancelableOnTouchOutside(isCancelableOnTouchOutside)
                .withButton2Text("确定")
                .withButtonDrawable(buttonDrawable)
                .setCustomView(layout, context)
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogBuilder.dismiss();
                    }
                })
                .show();
        return dialogBuilder;
    }
    /**
     * 充值页面弹框——没有标题头，自定义view，有确认取消按钮
     *
     * @param layout
     * @return
     */
    public NiftyDialogBuilder showDialog(int layout, int but1Drawable, int but2Drawable) {
        dialogBuilder
                .withTitle(null)
                .withTitleColor(context.getResources().getColor(R.color.black))
                .withCloseImage(R.mipmap.icon_close_x)
                .withDividerColor(context.getResources().getColor(R.color.line_gray))
                .withMessage(null)
                .withMessageColor(context.getResources().getColor(R.color.account_text_gray))
                .withDialogColor(context.getResources().getColor(R.color.white))
                .withIcon(context.getResources().getDrawable(R.mipmap.icon_launcher))
                .isCancelableOnTouchOutside(true)
                .withDuration(700)
                .withEffect(effect)
                .withButton1Text("确  定")
                .withButton2Text("取  消")
                .withButtonDrawable(but1Drawable,but2Drawable)
                .setCustomView(layout, context)
                .show();
        return dialogBuilder;
    }

    public NiftyDialogBuilder showDialog(int layout) {
        dialogBuilder
                .withTitle(null)
                .withTitleColor(context.getResources().getColor(R.color.black))
                .withCloseImage(R.mipmap.icon_close_x)
                .withDividerColor(context.getResources().getColor(R.color.line_gray))
                .withMessage(null)
                .withMessageColor(context.getResources().getColor(R.color.account_text_gray))
                .withDialogColor(context.getResources().getColor(R.color.white))
                .withIcon(context.getResources().getDrawable(R.mipmap.icon_launcher))
                .isCancelableOnTouchOutside(true)
                .withDuration(700)
                .withEffect(effect)
                .withButton1Text(null)
                .withButton2Text(null)
                .setCustomView(layout, context)
                .show();
        return dialogBuilder;
    }
//---------------------------------------------------------------------------------------------------------------------------------------
    /**
     * 设置弹出内容
     *
     * @param dialogBuilder
     * @param contentMsg
     * @param contentColor
     */
    private void setContentMsg(NiftyDialogBuilder dialogBuilder, String contentMsg, int contentColor) {
        TextView content = (TextView) dialogBuilder.findViewById(R.id.content);
        content.setText(contentMsg);//设置内容信息
        content.setTextColor(context.getResources().getColor(contentColor));//设置内容
    }

    /**
     * 获取对话框是否已经显示
     * @return
     */
    public boolean isShowing(){
        return dialogBuilder.isShowing();

    }

    /**
     * 监听对话框被销毁
     * @param onDismissListener
     */
    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener){
        dialogBuilder.setOnDismissListener(onDismissListener);
    }
}
