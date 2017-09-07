package com.app.common.MVP.Model.Bean;

/**
 * 共同的菜单对象
 * Created by ${杨重诚} on 2017/7/26.
 */

public class CommonMenuBean {
    private String icon;//图标
    private String title;//标题
    private String url;//链接
    private String subTitle;//副标题

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }
}
