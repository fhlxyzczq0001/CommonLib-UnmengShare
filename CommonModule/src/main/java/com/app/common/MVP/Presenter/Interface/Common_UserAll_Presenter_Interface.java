package com.app.common.MVP.Presenter.Interface;


import com.app.common.MVP.Presenter.Implement.Common_UserAll_Presenter_Implement;

/**用户所有信息通用获取接口
 * @ClassName: com.ygworld.MVP.Presenter.Interface
 * @author: Administrator 杨重诚
 * @date: 2016/10/12:17:07
 */

public interface Common_UserAll_Presenter_Interface {
    /**
     * 刷新用户信息
     */
    public void refreshUserInfo(Common_UserAll_Presenter_Implement.RefreshUserInfoListener refreshUserInfoListener);
    /**
     * 刷新用户信息
     */
    public void refreshUserInfo(final Common_UserAll_Presenter_Implement.RefreshUserInfoListener refreshUserInfoListener, boolean isLoadingDialog);
}
