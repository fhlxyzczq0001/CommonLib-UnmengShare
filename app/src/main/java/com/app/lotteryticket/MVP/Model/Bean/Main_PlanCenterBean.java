package com.app.lotteryticket.MVP.Model.Bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 计划中心bean对象
 * Created by ${杨重诚} on 2017/8/26.
 */

public class Main_PlanCenterBean {
    @JSONField(name="cqh")
    private String periods;//期数
    @JSONField(name="bingoa")
    private String planOne;//计划一
    @JSONField(name="bingob")
    private String planTwo;//计划二
    @JSONField(name="bingoc")
    private String planThree;//计划三
    @JSONField(name="bingod")
    private String planFour;//计划四

    public String getPeriods() {
        return periods;
    }

    public void setPeriods(String periods) {
        this.periods = periods;
    }

    public String getPlanOne() {
        return planOne;
    }

    public void setPlanOne(String planOne) {
        this.planOne = planOne;
    }

    public String getPlanTwo() {
        return planTwo;
    }

    public void setPlanTwo(String planTwo) {
        this.planTwo = planTwo;
    }

    public String getPlanThree() {
        return planThree;
    }

    public void setPlanThree(String planThree) {
        this.planThree = planThree;
    }

    public String getPlanFour() {
        return planFour;
    }

    public void setPlanFour(String planFour) {
        this.planFour = planFour;
    }
}
