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
import cn.brision.football.model.DataIntegralInfo;

/**
 * Created by wangchengcheng on 16/8/18.
 */
public class IntegralCustomGridAdapter extends SimpleAdapter {

    private Context context;
    private String seasonId;

    protected IntegralCustomGridAdapter(Context context, List data, int layoutId, String seasonId) {
        super(context, data, layoutId);
        this.context = context;
        this.seasonId = seasonId;
    }

    @Override
    public void convert(final SimpleViewHolder holder, Object o, final int i) {
        final DataIntegralInfo.DataBean.TeamsBean data = (DataIntegralInfo.DataBean.TeamsBean) o;
        holder.setTextViewText(R.id.position_integral, i + 1 + "");
        if (i == 0 || i == 1 || i == 2 || i == 3) {
            holder.setTextViewTextColor(R.id.position_integral, context.getResources().getColor(R.color.title_bg));
            holder.setTextViewTextColor(R.id.team_integral, context.getResources().getColor(R.color.title_bg));
            holder.setTextViewTextColor(R.id.score_integral, context.getResources().getColor(R.color.title_bg));
            holder.setTextViewTextColor(R.id.win_integral, context.getResources().getColor(R.color.title_bg));
            holder.setTextViewTextColor(R.id.draw_integral, context.getResources().getColor(R.color.title_bg));
            holder.setTextViewTextColor(R.id.lose_integral, context.getResources().getColor(R.color.title_bg));
            holder.setTextViewTextColor(R.id.goal_integral, context.getResources().getColor(R.color.title_bg));
            holder.setTextViewTextColor(R.id.scored_integral, context.getResources().getColor(R.color.yellow_01));
        } else {
            holder.setTextViewTextColor(R.id.position_integral, context.getResources().getColor(R.color.gray_128));
            holder.setTextViewTextColor(R.id.team_integral, context.getResources().getColor(R.color.black));
            holder.setTextViewTextColor(R.id.score_integral, context.getResources().getColor(R.color.gray_128));
            holder.setTextViewTextColor(R.id.win_integral, context.getResources().getColor(R.color.gray_128));
            holder.setTextViewTextColor(R.id.draw_integral, context.getResources().getColor(R.color.gray_128));
            holder.setTextViewTextColor(R.id.lose_integral, context.getResources().getColor(R.color.gray_128));
            holder.setTextViewTextColor(R.id.goal_integral, context.getResources().getColor(R.color.gray_128));
            holder.setTextViewTextColor(R.id.scored_integral, context.getResources().getColor(R.color.black));
        }
        holder.setTextViewText(R.id.team_integral, data.getTeam());
        holder.setTextViewText(R.id.score_integral, data.getScored() + "");
        holder.setTextViewText(R.id.win_integral, data.getWin() + "");
        holder.setTextViewText(R.id.draw_integral, data.getDraw() + "");
        holder.setTextViewText(R.id.lose_integral, data.getLose() + "");
        holder.setTextViewText(R.id.goal_integral, data.getGoal() + "");
        holder.setTextViewText(R.id.scored_integral, data.getScore() + "");

        if (data.getTeam_logo() != null) {
            holder.setImageViewImage(R.id.logo_integral, R.drawable.defaultlogo, data.getTeam_logo(), R.anim.fade_in);
        }

        holder.setOnClickListener(new ListenerWithPosition.OnClickWithPositionListener() {
            @Override
            public void onClick(View v, int position, Object holder) {
//                ToastUtil.showToastDefault(context, data.getTeam() + data.getTeam_id() + "--seasonId--" + seasonId);
                Intent intent = new Intent(context, IntegralActivity.class);
                intent.putExtra("teamId", String.valueOf(data.getTeam_id()));
                intent.putExtra("seasonId", seasonId);
                context.startActivity(intent);
            }
        }, data, R.id.item_integral);
    }


}
