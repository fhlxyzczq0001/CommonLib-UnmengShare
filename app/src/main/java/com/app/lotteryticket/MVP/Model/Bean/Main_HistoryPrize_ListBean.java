package com.app.lotteryticket.MVP.Model.Bean;

import java.util.List;

/**
 * Created by ${杨重诚} on 2017/8/26.
 */

public class Main_HistoryPrize_ListBean {
    private String prizeDtae;//开奖时间
    private List<Main_HistoryPrizeBean> list;//列表数据

    public String getPrizeDtae() {
        return prizeDtae;
    }

    public void setPrizeDtae(String prizeDtae) {
        this.prizeDtae = prizeDtae;
    }

    public List<Main_HistoryPrizeBean> getList() {
        return list;
    }

    public void setList(List<Main_HistoryPrizeBean> list) {
        this.list = list;
    }
}
