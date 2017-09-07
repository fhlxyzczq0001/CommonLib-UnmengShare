package com.app.userinfo.MVP.Model.Bean;

import java.util.List;

/**最新公告
 * @ClassName: com.ygworld.MVP.Model.Bean
 * @author: Administrator 杨重诚
 * @date: 2016/10/9:16:47
 */

public class Userinfo_NoticeBean_List {
    private List<Userinfo_NoticeBean> notice_array;
    public List<Userinfo_NoticeBean> getNotice_array() {
        return notice_array;
    }

    public void setNotice_array(List<Userinfo_NoticeBean> notice_array) {
        this.notice_array = notice_array;
    }
}
