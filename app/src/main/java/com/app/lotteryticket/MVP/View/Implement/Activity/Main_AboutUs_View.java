package com.app.lotteryticket.MVP.View.Implement.Activity;

import android.widget.TextView;

import com.app.common.Public.RouterUrl;
import com.app.common.Util.AppUtils;
import com.app.lotteryticket.Base.Main_BaseActivity;
import com.app.lotteryticket.MVP.Contract.Main_AboutAs_Contract;
import com.app.lotteryticket.MVP.Presenter.Implement.Main_AboutAs_Presenter;
import com.app.lotteryticket.R;
import com.chenenyu.router.annotation.Route;

import butterknife.BindView;

/**关于我们
 * @ClassName: com.ygworld.MVP.View.Implement.Activity
 * @author: Administrator 杨重诚
 * @date: 2016/10/21:11:26
 */
@Route(RouterUrl.mainAboutUsRouterUrl)
public class Main_AboutUs_View extends Main_BaseActivity<Main_AboutAs_Contract.Presenter,Main_AboutAs_Presenter> implements Main_AboutAs_Contract.View {
    @BindView(R.id.textVersionCode)
    TextView textVersionCode;
    @Override
    protected void setContentView() {
        setContentView(R.layout.main_act_about_us_layout);
    }

    @Override
    protected void init() {
        textVersionCode.setText("版本号："+ AppUtils.getAppVersionName(context));
    }

    @Override
    protected void setListeners() {
    }

    @Override
    protected void setTitleBar() {
        setActionbarBar("软件介绍", R.color.app_color, R.color.white, true, true);
    }
    @Override
    protected void getData() {

    }
}
