package com.app.userinfo.MVP.Model.Bean;

/**最新公告
 * @ClassName: com.ygworld.MVP.Model.Bean
 * @author: Administrator 杨重诚
 * @date: 2016/10/9:16:46
 */

public class Userinfo_NoticeBean {
    private int id;
    private String title;//标题
    private String content;//内容
    private long publishTime;//时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }
}
