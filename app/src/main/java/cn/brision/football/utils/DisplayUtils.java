package cn.brision.football.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by wangchengcheng on 16/8/24.
 * 获取屏幕大小等的工具类
 */
public class DisplayUtils {

    /**
     * Android.util 包下的DisplayMetrics 类提供了一种关于显示的通用信息, 如显示大小,分辨率和字体。
     *
     * @param context context
     * @return DisplayMetrics对象
     */
    public static DisplayMetrics getMetrics(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);

        return metrics;
    }

    /**
     * dp转pixl
     *
     * @param dp
     * @param context
     * @return
     */
    public static int dp2px(float dp, Context context) {
        DisplayMetrics metrics = getMetrics(context);
        return (int) (dp * (metrics.densityDpi / 160f));
    }

    /**
     * pixl转dp
     *
     * @param px
     * @param context
     * @return
     */
    public static float px2dp(float px, Context context) {
        DisplayMetrics metrics = getMetrics(context);
        return px / (metrics.densityDpi / 160f);
    }

    /**
     * 获取屏幕的宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = getMetrics(context);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕的高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeigh(Context context) {
        DisplayMetrics dm = getMetrics(context);
        return dm.heightPixels;
    }
}
