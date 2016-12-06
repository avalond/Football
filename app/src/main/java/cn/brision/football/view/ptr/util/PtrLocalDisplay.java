package cn.brision.football.view.ptr.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

public class PtrLocalDisplay {

    public static int sSCREENWIDTHPIXELS;
    public static int sSCREENHEIGHTPIXELS;
    public static float sSCREENDENSITY;
    public static int sSCREENWIDTHDP;
    public static int sSCREENHEIGHTDP;

    public static void init(Context context) {
        if (context == null) {
            return;
        }
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        sSCREENWIDTHPIXELS = dm.widthPixels;
        sSCREENHEIGHTPIXELS = dm.heightPixels;
        sSCREENDENSITY = dm.density;
        sSCREENWIDTHDP = (int) (sSCREENWIDTHPIXELS / dm.density);
        sSCREENHEIGHTDP = (int) (sSCREENHEIGHTPIXELS / dm.density);
    }

    public static int dp2px(float dp) {
        final float scale = sSCREENDENSITY;
        return (int) (dp * scale + 0.5f);
    }

    public static int designedDP2px(float designedDp) {
        if (sSCREENWIDTHDP != 320) {
            designedDp = designedDp * sSCREENWIDTHDP / 320f;
        }
        return dp2px(designedDp);
    }

    public static void setPadding(final View view, float left, float top, float right, float bottom) {
        view.setPadding(designedDP2px(left), dp2px(top), designedDP2px(right), dp2px(bottom));
    }
}
