package com.app.common.MVP.Model.Bean.EventBus;

/**Cookie同步成功
 * @ClassName: com.ygworld.MVP.Model.Bean.EventBus
 * @author: Administrator 杨重诚
 * @date: 2016/11/14:15:33
 */

public class SyncCookie_EventBus {
    boolean syncCookie =false;
    public SyncCookie_EventBus(boolean syncCookie){
        this.syncCookie=syncCookie;
    }

    public boolean isSyncCookie() {
        return syncCookie;
    }

    public void setSyncCookie(boolean syncCookie) {
        this.syncCookie = syncCookie;
    }
}
