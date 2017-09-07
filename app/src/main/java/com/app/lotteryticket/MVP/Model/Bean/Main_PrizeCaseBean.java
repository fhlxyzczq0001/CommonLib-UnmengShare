package com.app.lotteryticket.MVP.Model.Bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 首页开奖情况bean对象
 * Created by ${杨重诚} on 2017/8/25.
 */

public class Main_PrizeCaseBean {
    @JSONField(name="cqh")
    private String periodNum;//期号
    @JSONField(name="ccode")
    private String winningNumbers;//中奖号码

    public String getPeriodNum() {
        return periodNum;
    }

    public void setPeriodNum(String periodNum) {
        this.periodNum = periodNum;
    }

    public String getWinningNumbers() {
        return winningNumbers;
    }

    public void setWinningNumbers(String winningNumbers) {
        this.winningNumbers = winningNumbers;
    }
}
