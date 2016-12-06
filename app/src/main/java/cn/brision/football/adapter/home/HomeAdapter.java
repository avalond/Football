package cn.brision.football.adapter.home;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.DeleteCallback;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.brision.football.R;
import cn.brision.football.activity.home.CardActivity;
import cn.brision.football.activity.data.LivesActivity;
import cn.brision.football.activity.data.MatchActivity;
import cn.brision.football.activity.home.HomePlayerVideoActivity;
import cn.brision.football.activity.logins.LoginActivity;
import cn.brision.football.commen.HomeNotificationReceiver;
import cn.brision.football.leancloud.MyUser;
import cn.brision.football.leancloud.MyUserReservation;
import cn.brision.football.model.HomeBanner;
import cn.brision.football.model.HomeHotvideos;
import cn.brision.football.model.HomeLives;
import cn.brision.football.model.HomeTops;
import cn.brision.football.model.HomeUserfollows;
import cn.brision.football.utils.GlideUtils;
import cn.brision.football.utils.PreferencesManager;
import cn.brision.football.utils.TimeturnUtils;
import cn.brision.football.utils.ToastUtil;
import cn.brision.football.view.banner.BannerView;


/**
 * Created by wangchengcheng on 16/10/11.
 */
public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int ITEM_TYPE_BANNER = 0;
    public static final int ITEM_TYPE_LIVES = 1;
    public static final int ITEM_TYPE_TITLE = 2;
    public static final int ITEM_TYPE_TOPS = 3;
    public static final int ITEM_TYPE_FOLLOWS = 4;
    private Context context;
    private List<HomeBanner.DataBean> banner = new ArrayList<>();
    private List<HomeLives.DataBean> lives = new ArrayList<>();
    private List<HomeTops.DataBean> tops = new ArrayList<>();
    private List<HomeUserfollows.DataBean> follows = new ArrayList<>();
    private List<HomeHotvideos.DataBean> hotvideos = new ArrayList<>();
    private List<HomeLives.DataBean> result = new ArrayList<>();

    public HomeAdapter(Context context) {
        this.context = context;
    }

    public void addBannerData(List<HomeBanner.DataBean> data) {
        this.banner.clear();
        this.banner = data;
        notifyDataSetChanged();
    }

    public void addLivesData(List<HomeLives.DataBean> data) {
        this.lives.clear();
        this.lives = data;
        notifyDataSetChanged();
    }

    public void addYuYueLivesData(List<HomeLives.DataBean> results) {
        result.clear();
        result.addAll(results);
        notifyDataSetChanged();
    }

    public void addTopsData(List<HomeTops.DataBean> data) {
        this.tops.clear();
        this.tops = data;
        notifyDataSetChanged();
    }

    public void addTopsFollowsData(List<HomeUserfollows.DataBean> data) {
        this.follows.clear();
        this.follows = data;
        notifyDataSetChanged();
    }

    public void addTopsHotvdeosData(List<HomeHotvideos.DataBean> data) {
        this.hotvideos.clear();
        this.hotvideos = data;
        notifyDataSetChanged();
    }

    private boolean isLoging() {
        PreferencesManager pm = PreferencesManager.getInstance(context);
        String accessToken = pm.get("Access_token", "");

        return accessToken.length() > 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_BANNER) {
            return new HomeViewHolderPager(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_banner, parent, false));
        }
        if (viewType == ITEM_TYPE_LIVES) {
            return new HomeViewHolderLives(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_lives, parent, false));
        }
        if (viewType == ITEM_TYPE_TITLE) {
            return new HomeViewHolderTitle(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_title, parent, false));
        }
        if (viewType == ITEM_TYPE_TOPS) {
            return new HomeViewHolderTop(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_top, parent, false));
        }

        return new HomeViewHolderFollows(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_follows, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof HomeViewHolderPager) {
            HomeViewHolderPager bannerViewHolder = (HomeViewHolderPager) holder;
            bannerViewHolder.mBannerView.delayTime(3).build(banner);

        }
        if (holder instanceof HomeViewHolderLives) {
            final TextView mStatus = ((HomeViewHolderLives) holder).mStatus;
            GlideUtils.get(context).getImage(lives.get(position - 2).getHome().getImage(), ((HomeViewHolderLives) holder).homeIcon, R.anim.fade_in);
            GlideUtils.get(context).getImage(lives.get(position - 2).getAway().getImage(), ((HomeViewHolderLives) holder).awayIcon, R.anim.fade_in);
            ((HomeViewHolderLives) holder).homeName.setText(lives.get(position - 2).getHome().getTeamName());
            ((HomeViewHolderLives) holder).awayName.setText(lives.get(position - 2).getAway().getTeamName());
            ((HomeViewHolderLives) holder).mMatch.setText(lives.get(position - 2).getScore());
            if (lives.get(position - 2).getLivesStatus() == 0) {
                ((HomeViewHolderLives) holder).mStatus.setText(context.getResources().getString(R.string.lives_one));
                ((HomeViewHolderLives) holder).mMatch.setText("VS");

                if (isLoging()) {
                    for (int i = 0; i < result.size(); i++) {
                        if (result.get(i).getId() == lives.get(position - 2).getId()) {
                            ((HomeViewHolderLives) holder).mStatus.setText(context.getString(R.string.lives_two));
                            ((HomeViewHolderLives) holder).mStatus.setBackground(context.getResources().getDrawable(R.drawable.item2_shape));
                            ((HomeViewHolderLives) holder).mStatus.setTextColor(context.getResources().getColor(R.color.white));
                        }
                    }
                }
            }
            if (lives.get(position - 2).getLivesStatus() == 1) {
                ((HomeViewHolderLives) holder).mStatus.setText(context.getResources().getString(R.string.lives_three));
                ((HomeViewHolderLives) holder).mStatus.setBackground(context.getResources().getDrawable(R.drawable.item2_shape));
                ((HomeViewHolderLives) holder).mStatus.setTextColor(context.getResources().getColor(R.color.white));
            }
            if (lives.get(position - 2).getLivesStatus() == 2)
                ((HomeViewHolderLives) holder).mStatus.setText(context.getResources().getString(R.string.lives_four));
            ((HomeViewHolderLives) holder).mTime.setText(TimeturnUtils.livesDateFormat11(lives.get(position - 2).getStartAt()));


            ((HomeViewHolderLives) holder).mItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (lives.get(position - 2).getLivesStatus()) {
                        case 1://直播中
                            Intent intent1 = new Intent(context, LivesActivity.class);
                            intent1.putExtra("GameID", "7040");
                            context.startActivity(intent1);
                            break;
                        case 2://以结束
                            Intent intent = new Intent(context, MatchActivity.class);
                            intent.putExtra("GameID", "7040");
                            context.startActivity(intent);
                            break;
                        default:
                            break;
                    }
                }
            });

            ((HomeViewHolderLives) holder).mStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (lives.get(position - 2).getLivesStatus()) {
                        case 0://可预约
                            if (isLoging()) {
                                if (mStatus.getText().equals(context.getString(R.string.lives_one))) {
                                    setNotice(lives.get(position - 2));
                                    ((HomeViewHolderLives) holder).mStatus.setText(context.getString(R.string.lives_two));
                                    ((HomeViewHolderLives) holder).mStatus.setBackground(context.getResources().getDrawable(R.drawable.item2_shape));
                                    ((HomeViewHolderLives) holder).mStatus.setTextColor(context.getResources().getColor(R.color.white));
                                    MyUser user = (MyUser) MyUser.getCurrentUser();
                                    HomeLives.DataBean reservation = lives.get(position - 2);

                                    JSONObject jsonObject = null;
                                    Gson gson = new Gson();
                                    String s = gson.toJson(reservation);
                                    result.add(reservation);
                                    try {
                                        jsonObject = new JSONObject(s);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    MyUserReservation myUserReservation = new MyUserReservation();
                                    myUserReservation.setUser(user);
                                    myUserReservation.setGameId(String.valueOf(reservation.getId()));
                                    myUserReservation.setReservation(jsonObject);
                                    myUserReservation.saveEventually();
                                } else {
                                    mStatus.setText(context.getString(R.string.lives_one));
                                    result.remove(lives.get(position - 2));
                                    mStatus.setTextColor(context.getResources().getColor(R.color.white));
                                    mStatus.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.mipmap.lives_yuyue_while), null, null, null);
                                    MyUser user = (MyUser) MyUser.getCurrentUser();
                                    AVQuery<MyUserReservation> query = AVObject.getQuery(MyUserReservation.class);
                                    query.whereEqualTo("gameId", String.valueOf(lives.get(position - 2).getId()));
                                    query.deleteAllInBackground(new DeleteCallback() {
                                        @Override
                                        public void done(AVException e) {
                                        }
                                    });
                                }
                            } else
                                context.startActivity(new Intent(context, LoginActivity.class));
                            break;
                        default:
                            break;
                    }
                }
            });

        }
        if (holder instanceof HomeViewHolderTitle) {
            if (position == 1) {
                if (lives.size() != 0) {
                    ((HomeViewHolderTitle) holder).name.setText(context.getResources().getString(R.string.home_lives));
                    ((HomeViewHolderTitle) holder).all.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ToastUtil.showToastDefault(context, "更多热门比赛期待你的关注!!!");
                        }
                    });
                }
                if (lives.size() == 0 && !((HomeViewHolderTitle) holder).name.getText().equals(context.getResources().getString(R.string.home_top))) {
                    ((HomeViewHolderTitle) holder).name.setText(context.getResources().getString(R.string.home_top));
                    ((HomeViewHolderTitle) holder).all.setVisibility(View.GONE);
                    hideTitle(((HomeViewHolderTitle) holder));
                }
            }
            if (position == lives.size() + 2) {
                ((HomeViewHolderTitle) holder).name.setText(context.getResources().getString(R.string.home_top));
                ((HomeViewHolderTitle) holder).all.setVisibility(View.GONE);
            }
            if (position == 3 + lives.size() + tops.size()) {
                ((HomeViewHolderTitle) holder).name.setText(context.getResources().getString(R.string.follow));
                ((HomeViewHolderTitle) holder).all.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ToastUtil.showToastDefault(context, "期待你关注更多的球员或者比赛!!!");
                    }
                });
                if (follows.size() == 0) {
                    hideTitle((HomeViewHolderTitle) holder);
                }
            }
            if (position == 4 + lives.size() + tops.size() + follows.size()) {
                ((HomeViewHolderTitle) holder).name.setText(context.getResources().getString(R.string.home_hotvideo));
                ((HomeViewHolderTitle) holder).all.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ToastUtil.showToastDefault(context, "更多热门视频期待你的关注!!!");
                    }
                });
            }
        }
        if (holder instanceof HomeViewHolderTop) {
            final HomeTops.DataBean dataBean = tops.get(position - lives.size() - 3);
            GlideUtils.get(context).getImage(dataBean.getImgpath(), ((HomeViewHolderTop) holder).topIv, R.anim.fade_in);
            ((HomeViewHolderTop) holder).topTitle.setText(dataBean.getTitle());
            ((HomeViewHolderTop) holder).topTime.setText(dataBean.getCreatAt().substring(0, 10));
            String des = "\u3000\u3000" + dataBean.getDescrib();
            ((HomeViewHolderTop) holder).topDes.setText(des);
            int viewCount = dataBean.getViewCount();
            if (viewCount > 9999) {
                double i = ((double) viewCount) / 10000;
                BigDecimal bigDecimal = new BigDecimal(i).setScale(1, BigDecimal.ROUND_DOWN);
                String count = " " + bigDecimal.toString() + "万";
                ((HomeViewHolderTop) holder).viewCount.setText(count);
            } else {
                String count = " " + viewCount;
                ((HomeViewHolderTop) holder).viewCount.setText(count);
            }
            ((HomeViewHolderTop) holder).top.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, HomePlayerVideoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("tops", dataBean);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });

        }
        if (holder instanceof HomeViewHolderFollows) {
            if (position > 3 + lives.size() + tops.size() && position < 4 + lives.size() + tops.size() + follows.size()) {
                final HomeUserfollows.DataBean dataBean = follows.get(position - 4 - lives.size() - tops.size());
                GlideUtils.get(context).getImage(dataBean.getImgpath(), ((HomeViewHolderFollows) holder).mImage, R.anim.fade_in);
                ((HomeViewHolderFollows) holder).mComments.setText(String.valueOf(dataBean.getNotesCount()));
                String creatAt = dataBean.getCreatAt();
                String time = creatAt.substring(5, 10) + creatAt.substring(creatAt.length() - 5, creatAt.length());
                ((HomeViewHolderFollows) holder).mTime.setText(time);
                ((HomeViewHolderFollows) holder).mTitle.setText(dataBean.getTitle());
                ((HomeViewHolderFollows) holder).viewCount.setText("0");

                ((HomeViewHolderFollows) holder).mFollows.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, CardActivity.class);
                        intent.putExtra("eventId", dataBean.getId());
                        context.startActivity(intent);
                    }
                });
            } else {
                final HomeHotvideos.DataBean dataBean = hotvideos.get(position - 5 - lives.size() - tops.size() - follows.size());
                GlideUtils.get(context).getImage(dataBean.getImgpath(), ((HomeViewHolderFollows) holder).mImage, R.anim.fade_in);
                ((HomeViewHolderFollows) holder).mComments.setText(String.valueOf(dataBean.getNotesCount()));
                String creatAt = dataBean.getCreatAt();
                String time = creatAt.substring(5, 10) + " " + creatAt.substring(creatAt.length() - 5, creatAt.length());
                ((HomeViewHolderFollows) holder).mTime.setText(time);
                ((HomeViewHolderFollows) holder).mTitle.setText(dataBean.getTitle());
                int viewCount = dataBean.getViewCount();
                if (viewCount > 9999) {
                    double i = ((double) viewCount) / 10000;
                    BigDecimal bigDecimal = new BigDecimal(i).setScale(1, BigDecimal.ROUND_DOWN);
                    String count = " " + bigDecimal.toString() + "万";
                    ((HomeViewHolderFollows) holder).viewCount.setText(count);
                } else {
                    String count = " " + viewCount;
                    ((HomeViewHolderFollows) holder).viewCount.setText(count);
                }

                ((HomeViewHolderFollows) holder).mFollows.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, CardActivity.class);
                        intent.putExtra("eventId", dataBean.getId());
                        context.startActivity(intent);
                    }
                });
            }
        }
    }


    private void hideTitle(HomeViewHolderTitle holder) {
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) holder.rl.getLayoutParams();
        params.height = 0;
        params.topMargin = 0;
        holder.rl.setLayoutParams(params);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ITEM_TYPE_BANNER;
        }
        if (position == 1 || position == lives.size() + 2 || position == 3 + lives.size() + tops.size() || position == 4 + lives.size() + tops.size() + follows.size()) {
            return ITEM_TYPE_TITLE;
        }
        if (position > lives.size() + 2 && position < 3 + lives.size() + tops.size()) {
            return ITEM_TYPE_TOPS;
        }
        if (position > 1 && position < lives.size() + 2) {
            return ITEM_TYPE_LIVES;
        }
        return ITEM_TYPE_FOLLOWS;
    }

    @Override
    public int getItemCount() {
        if (lives != null && tops != null && follows != null && hotvideos != null) {
            return 5 + lives.size() + tops.size() + follows.size() + hotvideos.size();
        }
        return 0;
    }

    private void setNotice(HomeLives.DataBean bean) {
        long l = TimeturnUtils.toUnixTimestamp(bean.getStartAt());
        Intent intent = new Intent(context, HomeNotificationReceiver.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean", bean);
        intent.putExtras(bundle);
        intent.setAction("VIDEO_TIMER");
        // PendingIntent这个类用于处理即将发生的事情
        PendingIntent sender = PendingIntent.getBroadcast(context, bean.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        // AlarmManager.ELAPSED_REALTIME_WAKEUP表示闹钟在睡眠状态下会唤醒系统并执行提示功能，该状态下闹钟使用相对时间
        // SystemClock.elapsedRealtime()表示手机开始到现在经过的时间
        am.set(AlarmManager.RTC_WAKEUP, l, sender);
    }

    public class HomeViewHolderLives extends RecyclerView.ViewHolder {

        @Bind(R.id.home_icon)
        public ImageView homeIcon;
        @Bind(R.id.away_icon)
        public ImageView awayIcon;
        @Bind(R.id.home_name)
        public TextView homeName;
        @Bind(R.id.away_name)
        public TextView awayName;
        @Bind(R.id.live_time)
        public TextView mTime;
        @Bind(R.id.live_match)
        public TextView mMatch;
        @Bind(R.id.live_status)
        public TextView mStatus;
        @Bind(R.id.home_lives_item)
        public RelativeLayout mItem;

        public HomeViewHolderLives(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class HomeViewHolderTitle extends RecyclerView.ViewHolder {

        @Bind(R.id.title_name)
        public TextView name;
        @Bind(R.id.title_all)
        public TextView all;
        @Bind(R.id.rl)
        public RelativeLayout rl;

        public HomeViewHolderTitle(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class HomeViewHolderTop extends RecyclerView.ViewHolder {

        @Bind(R.id.home_top)
        public RelativeLayout top;
        @Bind(R.id.top_image)
        public ImageView topIv;
        @Bind(R.id.top_title)
        public TextView topTitle;
        @Bind(R.id.top_time)
        public TextView topTime;
        @Bind(R.id.top_description)
        public TextView topDes;
        @Bind(R.id.viewCount)
        public TextView viewCount;

        public HomeViewHolderTop(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class HomeViewHolderFollows extends RecyclerView.ViewHolder {

        @Bind(R.id.follows_comments)
        public TextView mComments;
        @Bind(R.id.follows_time)
        public TextView mTime;
        @Bind(R.id.follows_title)
        public TextView mTitle;
        @Bind(R.id.follows_image)
        public ImageView mImage;
        @Bind(R.id.viewCount)
        public TextView viewCount;
        @Bind(R.id.home_follows)
        public RelativeLayout mFollows;

        public HomeViewHolderFollows(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class HomeViewHolderPager extends RecyclerView.ViewHolder {

        @Bind(R.id.home_recommended_banner)
        BannerView mBannerView;

        public HomeViewHolderPager(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}