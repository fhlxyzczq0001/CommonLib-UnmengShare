package com.app.lotteryticket.MVP.Presenter.Implement;

import com.app.lotteryticket.MVP.Contract.Main_Guide_Contract;
import com.app.lotteryticket.R;

/**
 * Created by ${杨重诚} on 2017/6/2.
 */

public class Main_Guide_Presenter extends Main_Guide_Contract.Presenter {
    /**
     * 获取默认引导页
     * @Title: setDefaultGuidPage
     * @Description: TODO
     * @return: void
     */
    @Override
    public int[]  getDefaultGuidPage(){
        return new int[]{R.mipmap.bg_guide1,R.mipmap.bg_guide2,R.mipmap.bg_guide3};
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
