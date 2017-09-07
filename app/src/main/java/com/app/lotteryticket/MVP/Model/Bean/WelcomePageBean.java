package com.app.lotteryticket.MVP.Model.Bean;

import android.os.Parcel;
import android.os.Parcelable;

/**启动页bean
 * @ClassName: com.ygworld.MVP.Model.Bean
 * @author: Administrator 杨重诚
 * @date: 2016/10/14:15:35
 */

public class WelcomePageBean implements Parcelable {
    private String res_code;
    private String res_msg;
    private String url;
    private String link;
    private String isOpenAuthorization;//标识是否开启授权中心

    public String getRes_code() {
        return res_code;
    }

    public void setRes_code(String res_code) {
        this.res_code = res_code;
    }

    public String getRes_msg() {
        return res_msg;
    }

    public void setRes_msg(String res_msg) {
        this.res_msg = res_msg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getIsOpenAuthorization() {
        return isOpenAuthorization;
    }

    public void setIsOpenAuthorization(String isOpenAuthorization) {
        this.isOpenAuthorization = isOpenAuthorization;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.res_code);
        dest.writeString(this.res_msg);
        dest.writeString(this.url);
        dest.writeString(this.link);
        dest.writeString(this.isOpenAuthorization);
    }

    public WelcomePageBean() {
    }

    protected WelcomePageBean(Parcel in) {
        this.res_code = in.readString();
        this.res_msg = in.readString();
        this.url = in.readString();
        this.link = in.readString();
        this.isOpenAuthorization = in.readString();
    }

    public static final Creator<WelcomePageBean> CREATOR = new Creator<WelcomePageBean>() {
        @Override
        public WelcomePageBean createFromParcel(Parcel source) {
            return new WelcomePageBean(source);
        }

        @Override
        public WelcomePageBean[] newArray(int size) {
            return new WelcomePageBean[size];
        }
    };
}
