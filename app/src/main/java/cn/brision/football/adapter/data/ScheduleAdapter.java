package cn.brision.football.adapter.data;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import java.util.List;

import cn.brision.football.R;
import cn.brision.football.activity.data.IntegralActivity;
import cn.brision.football.adapter.ListenerWithPosition;
import cn.brision.football.adapter.SimpleAdapter;
import cn.brision.football.adapter.SimpleViewHolder;
import cn.brision.football.model.DataScheduleInfo;
import cn.brision.football.utils.TimeturnUtils;

/**
 * Created by wangchengcheng on 16/8/16.
 * 赛程数据适配器
 */
public class ScheduleAdapter extends SimpleAdapter {

    private Context mContext;
    private String seasonId;
    public ScheduleAdapter(Context context, List data, String seasonId,int layoutId) {
        super(context, data, layoutId);
        this.mContext = context;
        this.seasonId = seasonId;
    }

    @Override
    public void convert(final SimpleViewHolder holder, Object o, int i) {
        final DataScheduleInfo.DataBean.GameBean data = ((DataScheduleInfo.DataBean.GameBean) o);
        holder.setTextViewText(R.id.teamname_home, data.getHome());
        holder.setTextViewText(R.id.teamname_away, data.getAway());
        holder.setTextViewText(R.id.score, data.getScore());
        holder.setTextViewText(R.id.date_time, TimeturnUtils.livesDateFormat(data.getDatetime()));

        if (data.getHome_logo() != null) {
            holder.setImageViewImage(R.id.team_home, R.drawable.defaultlogo, data.getHome_logo(), R.anim.fade_in);
        }
        if (data.getAway_logo() != null) {
            holder.setImageViewImage(R.id.team_away, R.drawable.defaultlogo, data.getAway_logo(), R.anim.fade_in);
        }

        holder.setOnClickListener(new ListenerWithPosition.OnClickWithPositionListener() {
            @Override
            public void onClick(View v, int position, Object holder) {
                Intent intent = new Intent(mContext, IntegralActivity.class);
                intent.putExtra("teamId", String.valueOf(data.getHome_id()));
                intent.putExtra("seasonId", seasonId);
                mContext.startActivity(intent);
            }
        },null,R.id.team_home);

        holder.setOnClickListener(new ListenerWithPosition.OnClickWithPositionListener() {
            @Override
            public void onClick(View v, int position, Object holder) {
                Intent intent = new Intent(mContext, IntegralActivity.class);
                intent.putExtra("teamId", String.valueOf(data.getAway_id()));
                intent.putExtra("seasonId", seasonId);
                mContext.startActivity(intent);
            }
        },null,R.id.team_away);
    }

}
