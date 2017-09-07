package com.app.lotteryticket.MVP.Model.Bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 首页用户中心
 * Created by ${杨重诚} on 2017/8/26.
 */

public class Main_UserCenterBean {
    @JSONField(name="cedt")
    private String accountAging;//账号时效

    public String getAccountAging() {
        return accountAging;
    }

    public void setAccountAging(String accountAging) {
        this.accountAging = accountAging;
    }
}
