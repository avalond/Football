package cn.brision.football.commen;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cn.brision.football.R;
import cn.brision.football.activity.data.LivesActivity;
import cn.brision.football.model.HomeLives;
import cn.brision.football.model.LivesInfo;

public class HomeNotificationReceiver extends BroadcastReceiver {

    @SuppressLint("NewApi")
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("VIDEO_TIMER")) {
            HomeLives.DataBean bean = (HomeLives.DataBean) intent.getSerializableExtra("bean");
            Intent intent1 = new Intent(context, LivesActivity.class);
            intent1.putExtra("GameID", bean.getId());
            PendingIntent pendingIntent = PendingIntent.getActivity(context, bean.getId(),
                    intent1, PendingIntent.FLAG_UPDATE_CURRENT);
            // 通过Notification.Builder来创建通知，注意API Level
            // API16之后才支持
            Notification notify = new Notification.Builder(context)
                    .setSmallIcon(R.mipmap.logo)
                    .setTicker("点球:" + "您有新短消息，请注意查收！")
                    .setContentTitle(context.getString(R.string.app_name))
                    .setContentText(bean.getHome().getTeamName()+"对阵"+bean.getAway().getTeamName()+"的比赛已经开始了")
                    .setDefaults(Notification.DEFAULT_VIBRATE)//设置震动
                    .setDefaults(Notification.DEFAULT_SOUND)//设置默认声音
                    .setContentIntent(pendingIntent).build(); // 需要注意build()是在API
            // level16及之后增加的，API11可以使用getNotificatin()来替代
            notify.flags |= Notification.FLAG_AUTO_CANCEL; // FLAG_AUTO_CANCEL表明当通知被用户点击时，通知将被清除。
            // 在Android进行通知处理，首先需要重系统哪里获得通知管理器NotificationManager，它是一个系统Service。
            NotificationManager manager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(bean.getRoundId(), notify);// 步骤4：通过通知管理器来发起通知。如果id不同，则每click，在status哪里增加一个提示
        }
    }

}


