package com.app.common.MVP.Model.Bean;

/**
 * Created by Administrator on 2016/11/8.
 */

public class VersionBean {
    //"content":"1","res_code":1,"res_msg":"更新校验成功","code":1,"url":"http:\/\/"
    private String url;
    private int forced = 0;
    private String content;
    private String code;
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public int getForced() {
        return forced;
    }
    public void setForced(int forced) {
        this.forced = forced;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    @Override
    public String toString() {
        return "Version_Model [url=" + url + ", forced=" + forced + ", content=" + content + ", code=" + code + "]";
    }

}
