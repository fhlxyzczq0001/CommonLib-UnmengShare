package com.app.common.Public;

/**全局标识
 * @ClassName: com.ygworld.Public
 * @author: Administrator 杨重诚
 * @date: 2016/11/3:9:41
 */

public enum Main_PublicCode {
    MAIN_TAB_HOME("菜单"),
    MAIN_TAB_RECOMMEND("推荐"),
    MAIN_TAB_PLAN("计划"),
    MAIN_TAB_MYINFO("个人");
    // 定义私有变量
    private String nCode;

    // 构造函数，枚举类型只能为私有
    private Main_PublicCode(String _nCode) {
        this.nCode = _nCode;
    }

    @Override
    public String toString() {
        return String.valueOf(this.nCode);
    }
}
