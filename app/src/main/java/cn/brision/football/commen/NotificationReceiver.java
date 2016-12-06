package cn.brision.football.commen;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.brision.football.R;
import cn.brision.football.activity.data.LivesActivity;
import cn.brision.football.leancloud.MyUser;
import cn.brision.football.leancloud.MyUserReservation;
import cn.brision.football.model.LivesInfo;
import cn.brision.football.utils.Const;
import cn.brision.football.utils.PreferencesManager;
import cn.brision.football.utils.TimeturnUtils;

public class NotificationReceiver extends BroadcastReceiver {

    private Context mContext;
    private List<LivesInfo.DataBean.LivesBean> results = new ArrayList<>();

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        if (intent.getAction().equals("VIDEO_TIMER")) {
            LivesInfo.DataBean.LivesBean bean = (LivesInfo.DataBean.LivesBean) intent.getSerializableExtra("bean");
            if(bean!=null) {
                Intent intent1 = new Intent(context, LivesActivity.class);
                intent1.putExtra("GameID", bean.getId());
                PendingIntent pendingIntent = PendingIntent.getActivity(context, Integer.parseInt(bean.getId()),
                        intent1, PendingIntent.FLAG_UPDATE_CURRENT);
                // 通过Notification.Builder来创建通知，注意API Level
                // API16之后才支持
                Log.d("asfasfas11111", "44444444");
                Notification notify = new Notification.Builder(context)
                        .setSmallIcon(R.mipmap.logo)
                        .setTicker("点球:" + "您有新短消息，请注意查收！")
                        .setContentTitle(context.getString(R.string.app_name))
                        .setContentText(bean.getHome().getTeamName() + "对阵" + bean.getAway().getTeamName() + "的比赛已经开始了")
                        .setDefaults(Notification.DEFAULT_VIBRATE)//设置震动
                        .setDefaults(Notification.DEFAULT_SOUND)//设置默认声音
                        .setContentIntent(pendingIntent).build(); // 需要注意build()是在API
                // level16及之后增加的，API11可以使用getNotificatin()来替代
                notify.flags |= Notification.FLAG_AUTO_CANCEL; // FLAG_AUTO_CANCEL表明当通知被用户点击时，通知将被清除。
                // 在Android进行通知处理，首先需要重系统哪里获得通知管理器NotificationManager，它是一个系统Service。
                NotificationManager manager = (NotificationManager) context
                        .getSystemService(Context.NOTIFICATION_SERVICE);
                manager.notify(Integer.parseInt(bean.getId()), notify);// 步骤4：通过通知管理器来发起通知。如果id不同，则每click，在status哪里增加一个提示
            }

        }
        //登录状态发生改变
        if (intent.getAction().equals(Const.LOGIN_STATE_CHANGE)) {
            Log.d("asfasfas","lkjojoijo");
            //退出登录
            if (PreferencesManager.getInstance(mContext).get("Access_token").equals("")) {
                Log.d("asfasfas","lkjojoijo111111111");
                NotificationManager manager = (NotificationManager) context
                        .getSystemService(Context.NOTIFICATION_SERVICE);
                manager.cancelAll();
            } else {
                Log.d("asfasfas","lkjojoijo2222222");
                initClean();
            }
        }
    }

    /**
     * 在登陆成功的时候重新请求leadclone数据添加未过期通知
     */
    private void initClean() {
        MyUser user = (MyUser) MyUser.getCurrentUser();
        AVQuery<MyUserReservation> query = AVObject.getQuery(MyUserReservation.class);
        query.whereEqualTo("user", user);
        query.findInBackground(new FindCallback<MyUserReservation>() {
            @Override
            public void done(List<MyUserReservation> results1, AVException e) {
                if (results1 != null) {
                    for (int i = 0; i < results1.size(); i++) {
                        Log.d("asfasfas","lkjojoijo3333333");
                        JSONObject reservation = results1.get(i).getReservation();
                        Gson gson = new Gson();
                        LivesInfo.DataBean.LivesBean livesBean = gson.fromJson(reservation.toString(), LivesInfo.DataBean.LivesBean.class);
                        long currentTime = System.currentTimeMillis();
                        long startTime = TimeturnUtils.toUnixTimestamp(livesBean.getStartAt());
                        if ((currentTime - startTime < 0)) {
                            results.add(livesBean);
                            Log.d("ahksdhikhaoid",results.size()+"");
                            setNotice(livesBean);
                        }
                    }
                }
            }
        });
    }

    private void setNotice(LivesInfo.DataBean.LivesBean bean) {
        long l = TimeturnUtils.toUnixTimestamp(bean.getStartAt());
        Intent intent = new Intent(mContext, NotificationReceiver.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean", bean);
        intent.putExtras(bundle);
        intent.setAction("VIDEO_TIMER");
        // PendingIntent这个类用于处理即将发生的事情
        PendingIntent sender = PendingIntent.getBroadcast(mContext, Integer.parseInt(bean.getId()), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) mContext.getSystemService(mContext.ALARM_SERVICE);
        // AlarmManager.ELAPSED_REALTIME_WAKEUP表示闹钟在睡眠状态下会唤醒系统并执行提示功能，该状态下闹钟使用相对时间
        // SystemClock.elapsedRealtime()表示手机开始到现在经过的时间
        am.set(AlarmManager.RTC_WAKEUP, 1480651500000l, sender);
    }

}


