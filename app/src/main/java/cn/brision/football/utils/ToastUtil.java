package cn.brision.football.utils;

/**
 * Created by wangchengcheng on 16/8/11.
 * Toast的管理类
 */

import android.content.Context;
import android.os.SystemClock;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Toast显示时间根据文字长短确定
 * 提供多次提示只显示一次的方案
 * 提供带图片并且居中显示的Toast
 */
public class ToastUtil {
    private static final int textLengthSign = 6;
    private static long lastShowTime = 0;
    private static String lastShowMessage = null;

    /**
     * 居中带图的toast
     *
     * @param context     上下文
     * @param msg         信息
     * @param drawableRes 图片id
     */
    public static void showToastByPic(Context context, String msg, @DrawableRes int drawableRes) {
        Toast toast = makeText(context, msg);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastView = (LinearLayout) toast.getView();
        ImageView imageCodeProject = new ImageView(context);
        imageCodeProject.setImageResource(drawableRes);
        toastView.addView(imageCodeProject, 0);
        toast.show();
    }

    /**
     * 显示带有图片的Toast
     */
    public static void showToast(Context context, String msg, @DrawableRes int drawableRes) {

        Toast mToast;
        if (msg.equals(lastShowMessage)) {
            mToast = makeText(context, msg);
            //创建LinearLayout布局
            LinearLayout toastView = (LinearLayout) mToast.getView();
            //设置LinearLayout的布局取向
            toastView.setOrientation(LinearLayout.HORIZONTAL);
            //创建ImageView
            ImageView imageCodeProject = new ImageView(context);
            imageCodeProject.setPadding(0, 0, 10, 0);
            imageCodeProject.setImageResource(drawableRes);
            //给toastView添加View布局
            toastView.addView(imageCodeProject, 0);
            //显示Toast
            mToast.setGravity(Gravity.CENTER, 0, 0);
            mToast.show();
            lastShowTime = System.currentTimeMillis();
        } else {
            showToastByPic(context, msg,drawableRes);
            lastShowMessage = msg;
            lastShowTime = System.currentTimeMillis();
        }

    }

    /**
     * 普通Toast
     *
     * @param context
     * @param msg
     */
    public static void showToastDefault(Context context, String msg) {
        Toast toast = makeText(context, msg);
        toast.show();
    }


    /**
     * 显示Toast, 多个相同的消息的Toast只显示一次
     *
     * @param context
     * @param msg
     */
    public static void showToastOnce(Context context, String msg) {
        Toast toast;
        if (msg.equals(lastShowMessage)) {
            if (System.currentTimeMillis() - lastShowTime > 3000) {
                toast = makeText(context, msg);
                toast.show();
                lastShowTime = System.currentTimeMillis();
            }
        } else {
            showToastDefault(context, msg);
            lastShowMessage = msg;
            lastShowTime = System.currentTimeMillis();
        }
    }

    /**
     * 显示Toast，多个同样消息的Toast只显示一次
     *
     * @param context   上下文
     * @param stringRes 信息id
     */
    public static void showToastOnce(Context context, @StringRes int stringRes) {
        showToastOnce(context, context.getString(stringRes));
    }


    private static Toast makeText(Context context, String msg) {
        if (msg.isEmpty()) {
            return Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else if (msg.length() > textLengthSign) {
            return Toast.makeText(context, msg, Toast.LENGTH_LONG);
        } else {
            return Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        }
    }
}
