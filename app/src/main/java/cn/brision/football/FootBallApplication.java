package cn.brision.football;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.bugtags.library.Bugtags;
import com.bugtags.library.BugtagsOptions;
import com.tendcloud.tenddata.TCAgent;

import cn.brision.football.data.services.DataManger;
import cn.brision.football.leancloud.MyUser;
import cn.brision.football.leancloud.MyUserReservation;

import static cn.brision.football.BuildConfig.*;

public class FootBallApplication extends Application {
    protected DataManger mDataManager;

    @Override
    public void onCreate() {
        super.onCreate();

        setupBugtags();

        initTalkingData();

        initLeanCloud();

        initDataManager();
    }

    public DataManger getDataManger() {
        return mDataManager;
    }

    private void setupBugtags() {
        BugtagsOptions options = new BugtagsOptions.Builder().
                trackingLocation(true).//是否获取位置，默认 true
                trackingCrashLog(true).//是否收集crash，默认 true
                trackingConsoleLog(true).//是否收集console log，默认 true
                trackingUserSteps(true).//是否收集用户操作步骤，默认 true
                trackingNetworkURLFilter("(.*)").//自定义网络请求跟踪的 url 规则，默认 null
                build();

        Bugtags.start(BUGTAGS_APPKEY, this, BUGTAGS_TYPE);
    }

    private void initTalkingData() {
        if (API_ENV == false) {
            TCAgent.LOG_ON = true;
            TCAgent.init(this, APPID, CHANNEL_ID);
            TCAgent.setReportUncaughtExceptions(true);
        }
    }

    private void initLeanCloud() {
        AVObject.registerSubclass(MyUserReservation.class);
        AVUser.alwaysUseSubUserClass(MyUser.class);
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this, LEANCLOUND_APPID, LEANCLOUND_APPKEY);
    }

    private void initDataManager() {
        mDataManager = DataManger.init(this);
    }


}
