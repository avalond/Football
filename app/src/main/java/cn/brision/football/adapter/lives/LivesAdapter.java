package cn.brision.football.adapter.lives;

import cn.brision.football.R;
import cn.brision.football.activity.data.LivesActivity;
import cn.brision.football.activity.data.MatchActivity;
import cn.brision.football.activity.logins.LoginActivity;
import cn.brision.football.adapter.expandRecyclerviewadapter.BaseAdapter;
import cn.brision.football.adapter.expandRecyclerviewadapter.StickyRecyclerHeadersAdapter;
import cn.brision.football.commen.NotificationReceiver;
import cn.brision.football.leancloud.MyUser;
import cn.brision.football.leancloud.MyUserReservation;
import cn.brision.football.model.LivesInfo;
import cn.brision.football.utils.GlideUtils;
import cn.brision.football.utils.PreferencesManager;
import cn.brision.football.utils.TimeturnUtils;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.DeleteCallback;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;

public class LivesAdapter extends BaseAdapter<LivesAdapter.LivesViewHolder>
        implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<LivesInfo.DataBean.LivesBean> result = new ArrayList<>();


    public LivesAdapter(Context ct) {
        mContext = ct;
    }

    private boolean isLoging() {
        PreferencesManager pm = PreferencesManager.getInstance(mContext);
        String accessToken = pm.get("Access_token", "");

        return accessToken.length() > 0;
    }

    public void addData(List<LivesInfo.DataBean.LivesBean> data) {
        this.addAll(data);
        notifyDataSetChanged();
    }

    public void addYuYueData(List<LivesInfo.DataBean.LivesBean> results) {
        this.result.clear();
        result.addAll(results);
        notifyDataSetChanged();
    }

    @Override
    public LivesAdapter.LivesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lives, parent, false);
        return new LivesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LivesAdapter.LivesViewHolder holder, final int position) {
        TextView nameHome = holder.nameHome;
        TextView nameAway = holder.nameAway;
        TextView dataTime = holder.dataTime;
        TextView mScore = holder.mScore;
        final ImageView statusIv = holder.statusIv;
        final TextView mStatus = holder.mStatus;
        final RelativeLayout mItem = holder.item;

        nameHome.setText(getItem(position).getHome().getTeamName());
        nameAway.setText(getItem(position).getAway().getTeamName());
        dataTime.setText(TimeturnUtils.livesDateFormat33(getItem(position).getStartAt()));
        mScore.setText(getItem(position).getScore());
        switch (getItem(position).getLivesStatus()) {
            case 2:   //0 will change have  different view
                mStatus.setText(mContext.getString(R.string.lives_one));
                mStatus.setTextColor(mContext.getResources().getColor(R.color.white));
                mStatus.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.mipmap.lives_yuyue_while), null, null, null);
                mStatus.setVisibility(View.VISIBLE);
                mScore.setVisibility(View.GONE);
                statusIv.setVisibility(View.GONE);
                if (isLoging()) {
                    for (int i = 0; i < result.size(); i++) {
                        if (result.get(i).getId().equals(getItem(position).getId())) {
                            mStatus.setText(mContext.getString(R.string.lives_two));
                            mStatus.setTextColor(mContext.getResources().getColor(R.color.title_bg));
                            mStatus.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.mipmap.lives_yuyue_blue), null, null, null);
                        }
                    }
                }
                break;
            case 1:
                mStatus.setVisibility(View.GONE);
                mScore.setVisibility(View.VISIBLE);
                statusIv.setVisibility(View.VISIBLE);
                GlideUtils.get(mContext).getImage(R.mipmap.lives_liveing, statusIv);
                break;
            case 0:  //2   will change have different view
                mStatus.setVisibility(View.GONE);
                mScore.setVisibility(View.VISIBLE);
                statusIv.setVisibility(View.VISIBLE);
                GlideUtils.get(mContext).getImage(R.mipmap.lives_lived, statusIv);
                break;
            default:
                break;

        }
        GlideUtils.get(mContext).getImage(getItem(position).getHome().getImage(), holder.homeLogo, R.anim.fade_in);
        GlideUtils.get(mContext).getImage(getItem(position).getAway().getImage(), holder.awayLogo, R.anim.fade_in);

        mItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (getItem(position).getLivesStatus()) {
                    case 2://可预约    //0 will change have different view
                        if (isLoging()) {
                            if (mStatus.getText().equals(mContext.getString(R.string.lives_one))) {
                                setNotice(getItem(position));
                                mStatus.setText(mContext.getString(R.string.lives_two));
                                mStatus.setTextColor(mContext.getResources().getColor(R.color.title_bg));
                                mStatus.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.mipmap.lives_yuyue_blue), null, null, null);

                                MyUser user = (MyUser) MyUser.getCurrentUser();
                                LivesInfo.DataBean.LivesBean reservation = getItem(position);
                                result.add(reservation);
                                JSONObject jsonObject = null;
                                Gson gson = new Gson();
                                String s = gson.toJson(reservation);
                                try {
                                    jsonObject = new JSONObject(s);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                MyUserReservation myUserReservation = new MyUserReservation();
                                myUserReservation.setUser(user);
                                myUserReservation.setGameId(reservation.getId());
                                myUserReservation.setReservation(jsonObject);
                                myUserReservation.saveEventually();
                            } else {
                                mStatus.setText(mContext.getString(R.string.lives_one));
                                for (int i = 0; i < result.size(); i++) {
                                    if (result.get(i).getId().equals(getItem(position).getId())) {
                                        result.remove(i);
                                        i--;
                                        break;
                                    }
                                }
                                mStatus.setTextColor(mContext.getResources().getColor(R.color.white));
                                mStatus.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.mipmap.lives_yuyue_while), null, null, null);
                                MyUser user = (MyUser) MyUser.getCurrentUser();
                                AVQuery<MyUserReservation> query = AVObject.getQuery(MyUserReservation.class);
                                query.whereEqualTo("gameId", getItem(position).getId());
                                query.deleteAllInBackground(new DeleteCallback() {
                                    @Override
                                    public void done(AVException e) {
                                    }
                                });

                            }
                        } else
                            mContext.startActivity(new Intent(mContext, LoginActivity.class));
                        break;
                    case 1://直播中
                        Intent intent1 = new Intent(mContext, LivesActivity.class);
                        intent1.putExtra("GameID", getItem(position).getId());
                        mContext.startActivity(intent1);
                        break;
                    case 0://以结束 //2 will change have different view
                        Intent intent = new Intent(mContext, MatchActivity.class);
                        intent.putExtra("GameID", getItem(position).getId());
                        mContext.startActivity(intent);
                        break;
                    default:
                        break;
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

    @Override
    public long getHeaderId(int position) {

        String year = getItem(position).getStartAt().substring(0, 4);
        String m = getItem(position).getStartAt().substring(5, 7);
        String day = getItem(position).getStartAt().substring(8, 10);
        String s = year + m + day;
        Long aLong = Long.valueOf(s);
        return aLong;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_header_lives, parent, false);
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        TextView textView = (TextView) holder.itemView;
        textView.setText(TimeturnUtils.livesDateFormatWeek(getItem(position).getStartAt()));
    }

    public class LivesViewHolder extends RecyclerView.ViewHolder {

        public TextView nameHome;
        public TextView nameAway;
        public TextView dataTime;
        public TextView mScore;
        public TextView mStatus;
        public ImageView homeLogo;
        public ImageView awayLogo;
        public ImageView statusIv;
        public RelativeLayout item;

        public LivesViewHolder(View itemView) {
            super(itemView);
            nameHome = (TextView) itemView.findViewById(R.id.teamname_home);
            nameAway = (TextView) itemView.findViewById(R.id.teamname_away);
            dataTime = (TextView) itemView.findViewById(R.id.date_time);
            mScore = (TextView) itemView.findViewById(R.id.score);
            mStatus = (TextView) itemView.findViewById(R.id.status);
            homeLogo = (ImageView) itemView.findViewById(R.id.team_home);
            awayLogo = (ImageView) itemView.findViewById(R.id.team_away);
            item = (RelativeLayout) itemView.findViewById(R.id.schedule_item);
            statusIv = (ImageView) itemView.findViewById(R.id.status_iv);
        }
    }
}