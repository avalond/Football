package cn.brision.football.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import cn.brision.football.model.LivesInfo;

/**
 * Created by brision on 16/8/29.
 */
public class TimeturnUtils implements Comparator<Object> {

//    public static String onTimeturn(String string) {
//        int time = Integer.valueOf(string);
//        String allSecondStr;
//        if (time >= 3600) {
//            allSecondStr = formatPlayTime(time * 1000 + 16 * 60 * 60 * 1000);
//        }else {
//            allSecondStr = formatPlayTime(time * 1000);
//        }
//        return allSecondStr;
//    }
//
//    private static String formatPlayTime(long time) {
//        DateFormat formatter = null;
//        if (time >= 3600 * 1000) {
//            formatter = new SimpleDateFormat("HH:mm:ss");
//        } else {
//            formatter = new SimpleDateFormat("mm:ss");
//        }
//        return formatter.format(new Date(time));
//    }

    public static String onTimeturn(String string) {
        int time = Integer.valueOf(string);
        if (time > 0 && time < 10) {
            return "00:0" + time;
        } else if (time >= 10 && time < 60) {
            return "00:" + time;
        } else if (time >= 60) {
            int m = time / 60;
            int s = time % 60;

            if (m < 10 && s < 10) {
                return "0" + m + ":" + "0" + s;
            } else if (m < 10 && s >= 10) {
                return "0" + m + ":" + s;
            } else if (m >= 10 && s < 10) {
                return m + ":" + "0" + s;
            } else if (m >= 10 && s >= 10) {
                return m + ":" + s;
            }
        }
        return "";
    }

    /**
     * 日期格式转换yyyy-MM-dd'T'HH:mm:ss.SSSXXX  (yyyy-MM-dd'T'HH:mm:ss.SSSZ) TO  yyyy年MM月dd日 HH:mm:ss
     * aaa 24消失处于上午还是下午 小写表示12消失 大写24小时
     */
    public static String livesDateFormat(String oldDateStr) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            Date date = df.parse(oldDateStr);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String s = simpleDateFormat.format(date);
            return s;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String livesDateFormat11(String oldDateStr) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            Date date = df.parse(oldDateStr);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("aaahh:mm");
            String s = simpleDateFormat.format(date);
            return s;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String livesDateFormat22(String oldDateStr) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            Date date = df.parse(oldDateStr);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
            String s = simpleDateFormat.format(date);
            return s;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String livesDateFormat33(String oldDateStr) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            Date date = df.parse(oldDateStr);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            String s = simpleDateFormat.format(date);
            return s;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String livesDateFormatWeek(String oldDateStr) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            Date date = df.parse(oldDateStr);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy年MM月dd日");
            String s = simpleDateFormat.format(date);
            return s;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static long toUnixTimestamp(String oldDateStr) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            Date date = df.parse(oldDateStr);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
            String s = simpleDateFormat.format(date);
            long epoch = new SimpleDateFormat("yyyy年MM月dd日 HH:mm").parse(s).getTime();
            return epoch;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int compare(Object o, Object t) {
        LivesInfo.DataBean.LivesBean o1 = (LivesInfo.DataBean.LivesBean) o;
        LivesInfo.DataBean.LivesBean t1 = (LivesInfo.DataBean.LivesBean) t;
        int flag = livesDateFormat(o1.getStartAt()).compareTo(livesDateFormat(t1.getStartAt()));
        return flag;
    }
}
