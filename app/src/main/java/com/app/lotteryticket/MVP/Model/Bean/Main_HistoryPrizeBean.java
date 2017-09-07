package com.app.lotteryticket.MVP.Model.Bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by ${杨重诚} on 2017/8/26.
 */

public class Main_HistoryPrizeBean {
    @JSONField(name="cqh")
    private String periods;//期数
    @JSONField(name="ctm")
    private String time;//时间
    @JSONField(name="ccode")
    private String results;//结果

    public String getPeriods() {
        return periods;
    }

    public void setPeriods(String periods) {
        this.periods = periods;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }
}
