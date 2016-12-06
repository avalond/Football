package cn.brision.football.commen;

import android.app.Activity;
import android.content.Context;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * Created by wangchengcheng on 16/8/5.
 * Activity的管理类
 */
public class ActivityManager {
    private Context context;

    private static ActivityManager activityManager;

    public static ActivityManager getActivityManager(Context context) {
        if (activityManager == null) {
            activityManager = new ActivityManager(context);
        }
        return activityManager;
    }

    private ActivityManager(Context conetext) {
        this.context = conetext;
    }

    /**
     * task map, 用于记录Activity的栈,方便退出程序(为了不影响系统回收Activity,使用软引用)
     */
    private final HashMap<String, SoftReference<Activity>> taskMap = new HashMap<String, SoftReference<Activity>>();

    /**
     * 向运用task map里面添加activity
     *
     * @param act: activity
     */
    public final void putActivity(Activity act) {
        taskMap.put(act.toString(), new SoftReference<Activity>(act));
    }

    /**
     * 移除task栈里的某个activity
     *
     * @param act: activity
     */
    public final void removeActivity(Activity act) {
        taskMap.remove(act.toString());
    }

    /**
     * 清除运用的task栈,如果程序正常运行会导致运用退出桌面
     */
    public final void exit() {
        for (Iterator<Entry<String, SoftReference<Activity>>> iterator = taskMap.entrySet().iterator(); iterator.hasNext(); ) {
            SoftReference<Activity> activityReference = iterator.next().getValue();
            Activity activity = activityReference.get();
            if (activity != null) {
                activity.finish();
            }
        }
        taskMap.clear();
    }

}
