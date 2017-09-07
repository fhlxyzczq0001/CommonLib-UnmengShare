package com.app.common.MVP.Model.Bean;

import android.os.Parcel;
import android.os.Parcelable;

/**用户登录成功后用户信息
 * @ClassName: com.ygworld.MVP.Model.Bean
 * @author: Administrator 杨重诚
 * @date: 2016/8/19:16:14
 */
public class UserInfoBean implements Parcelable {
    private String userId;
    private String userName;
    private String oauth_token;
    private String refreshToken;
    private String nickName;
    private String user_icon;
    private String mobilePhone;
    private String email;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOauth_token() {
        return oauth_token;
    }

    public void setOauth_token(String oauth_token) {
        this.oauth_token = oauth_token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUser_icon() {
        return user_icon;
    }

    public void setUser_icon(String user_icon) {
        this.user_icon = user_icon;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeString(this.userName);
        dest.writeString(this.oauth_token);
        dest.writeString(this.refreshToken);
        dest.writeString(this.nickName);
        dest.writeString(this.user_icon);
        dest.writeString(this.mobilePhone);
        dest.writeString(this.email);
    }

    public UserInfoBean() {
    }

    protected UserInfoBean(Parcel in) {
        this.userId = in.readString();
        this.userName = in.readString();
        this.oauth_token = in.readString();
        this.refreshToken = in.readString();
        this.nickName = in.readString();
        this.user_icon = in.readString();
        this.mobilePhone = in.readString();
        this.email = in.readString();
    }

    public static final Creator<UserInfoBean> CREATOR = new Creator<UserInfoBean>() {
        @Override
        public UserInfoBean createFromParcel(Parcel source) {
            return new UserInfoBean(source);
        }

        @Override
        public UserInfoBean[] newArray(int size) {
            return new UserInfoBean[size];
        }
    };
}
