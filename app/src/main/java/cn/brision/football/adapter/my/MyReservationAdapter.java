package cn.brision.football.adapter.my;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.brision.football.R;
import cn.brision.football.activity.data.LivesActivity;
import cn.brision.football.activity.data.MatchActivity;
import cn.brision.football.activity.my.MyYuyueActivity;
import cn.brision.football.leancloud.MyUser;
import cn.brision.football.leancloud.MyUserReservation;
import cn.brision.football.model.LivesInfo;
import cn.brision.football.utils.GlideUtils;
import cn.brision.football.utils.TimeturnUtils;
import cn.brision.football.view.SwipeItemLayout;

import static android.support.v7.widget.RecyclerView.*;


public class MyReservationAdapter extends RecyclerView.Adapter<MyReservationAdapter.MyViewHolder> {

    private Context mContext;
    private List<LivesInfo.DataBean.LivesBean> results = new ArrayList<>();         //传入的数据源集合
    private List<SwipeItemLayout> mOpenedSil = new ArrayList<>();                   //当前处于打开状态的item

    public MyReservationAdapter(Context context, List<LivesInfo.DataBean.LivesBean> data) {
        this.mContext = context;
        this.results = data;
    }

    public void remove(LivesInfo.DataBean.LivesBean object) {
        results.remove(object);
        notifyDataSetChanged();
    }

    public LivesInfo.DataBean.LivesBean getItem(int position) {
        return results.get(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_myuserreservation, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final SwipeItemLayout swipeRoot = holder.swipeRoot;
        TextView deleteItem = holder.deleteItem;
        RelativeLayout mItem = holder.mItem;
        ImageView homeLogo = holder.homeLogo;
        TextView homeName = holder.homeName;
        TextView score = holder.score;
        TextView awayName = holder.awayName;
        ImageView awayLogo = holder.awayLogo;
        TextView status = holder.status;
        TextView matchTitle = holder.matchTitle;
        TextView time = holder.time;
        swipeRoot.setSwipeAble(true);
        swipeRoot.setDelegate(new SwipeItemLayout.SwipeItemLayoutDelegate() {
            @Override
            public void onSwipeItemLayoutOpened(SwipeItemLayout swipeItemLayout) {
                closeOpenedSwipeItemLayoutWithAnim();
                mOpenedSil.add(swipeItemLayout);
            }

            @Override
            public void onSwipeItemLayoutClosed(SwipeItemLayout swipeItemLayout) {
                mOpenedSil.remove(swipeItemLayout);
            }

            @Override
            public void onSwipeItemLayoutStartOpen(SwipeItemLayout swipeItemLayout) {
                closeOpenedSwipeItemLayoutWithAnim();
            }
        });

        deleteItem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MyYuyueActivity)mContext).deleteUser(position);
                for (SwipeItemLayout sil : mOpenedSil) {
                    sil.close();
                }
            }
        });

        homeName.setText(results.get(position).getHome().getTeamName());
        awayName.setText(results.get(position).getAway().getTeamName());
        GlideUtils.get(mContext).getImage(results.get(position).getHome().getImage(), mContext.getResources().getDrawable((R.mipmap.logo)), homeLogo, R.anim.fade_in);
        GlideUtils.get(mContext).getImage(results.get(position).getAway().getImage(), mContext.getResources().getDrawable((R.mipmap.logo)), awayLogo, R.anim.fade_in);
        time.setText(TimeturnUtils.livesDateFormat(results.get(position).getStartAt()));
        score.setText("vs");

        long currentTime = System.currentTimeMillis();
        long startTime = TimeturnUtils.toUnixTimestamp(results.get(position).getStartAt());
        if (!(currentTime - startTime > 10800000)) {
            switch (results.get(position).getLivesStatus()) {
                case 0:
                    status.setText("已预约");
                    status.setTextColor(mContext.getResources().getColor(R.color.btg_global_text_blue));
                    status.setBackground(mContext.getResources().getDrawable(R.drawable.yuyue));
                    status.setCompoundDrawablesRelativeWithIntrinsicBounds(R.mipmap.lives_yuyue_blue, 0, 0, 0);
                    break;
                case 1:
                    status.setText("直播中");
                    status.setTextColor(mContext.getResources().getColor(R.color.white));
                    status.setBackground(mContext.getResources().getDrawable(R.drawable.item2_shape));
                    status.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                    break;
                case 2:
                    status.setText("已结束");
                    status.setTextColor(mContext.getResources().getColor(R.color.white));
                    status.setBackground(mContext.getResources().getDrawable(R.drawable.yuyue_shape));
                    status.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                    break;
                default:
                    break;

            }
        } else {
            status.setText("已结束");
            status.setTextColor(mContext.getResources().getColor(R.color.white));
            status.setBackground(mContext.getResources().getDrawable(R.drawable.yuyue_shape));
            status.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
        }

        mItem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                long currentTime = System.currentTimeMillis();
                long startTime = TimeturnUtils.toUnixTimestamp(results.get(position).getStartAt());
                if (!(currentTime - startTime > 10800000)) {
                    switch (results.get(position).getLivesStatus()) {
                        case 1:
                            Intent intent1 = new Intent(mContext, LivesActivity.class);
                            intent1.putExtra("GameID", "7040");
                            mContext.startActivity(intent1);
                            break;
                        case 2:
                            Intent intent = new Intent(mContext, MatchActivity.class);
                            intent.putExtra("GameID", "7040");
                            mContext.startActivity(intent);
                            break;
                        default:
                            break;

                    }
                } else {
                    Intent intent = new Intent(mContext, MatchActivity.class);
                    intent.putExtra("GameID", "7040");
                    mContext.startActivity(intent);
                }
            }
        });

    }

    /**
     * 让所有的item隐藏删除
     */
    public void closeOpenedSwipeItemLayoutWithAnim() {
        for (SwipeItemLayout sil : mOpenedSil) {
            sil.closeWithAnim();
        }
        mOpenedSil.clear();
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    class MyViewHolder extends ViewHolder {

        @Bind(R.id.item_contact_swipe_root)
        SwipeItemLayout swipeRoot;
        @Bind(R.id.item_contact_delete)
        TextView deleteItem;
        @Bind(R.id.yuyue_item)
        RelativeLayout mItem;
        @Bind(R.id.team_home)
        ImageView homeLogo;
        @Bind(R.id.teamname_home)
        TextView homeName;
        @Bind(R.id.score)
        TextView score;
        @Bind(R.id.teamname_away)
        TextView awayName;
        @Bind(R.id.team_away)
        ImageView awayLogo;
        @Bind(R.id.status)
        TextView status;
        @Bind(R.id.match_title)
        TextView matchTitle;
        @Bind(R.id.date_time)
        TextView time;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

