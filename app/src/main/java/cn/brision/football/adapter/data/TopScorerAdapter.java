package cn.brision.football.adapter.data;

import android.content.Context;

import java.util.List;

import cn.brision.football.R;
import cn.brision.football.adapter.SimpleAdapter;
import cn.brision.football.adapter.SimpleViewHolder;
import cn.brision.football.model.DataTopScorerInfo;

/**
 * Created by wangchengcheng on 16/8/17.
 * 射手榜页面适配器
 */
public class TopScorerAdapter extends SimpleAdapter {

    private Context context;

    public TopScorerAdapter(Context context, List data, int layoutId) {
        super(context, data, layoutId);
        this.context = context;
    }

    @Override
    public void convert(final SimpleViewHolder holder, Object o, int i) {
        DataTopScorerInfo.DataBean data = (DataTopScorerInfo.DataBean) o;
        holder.setTextViewText(R.id.position, String.valueOf(i + 1));
        if (i == 0 || i == 1 || i == 2) {
            holder.setTextViewTextColor(R.id.position, context.getResources().getColor(R.color.title_bg));
            holder.setTextViewTextColor(R.id.team, context.getResources().getColor(R.color.title_bg));
            holder.setTextViewTextColor(R.id.team_name, context.getResources().getColor(R.color.title_bg));
            holder.setTextViewTextColor(R.id.number, context.getResources().getColor(R.color.title_bg));
        } else {
            holder.setTextViewTextColor(R.id.position, context.getResources().getColor(R.color.gray_128));
            holder.setTextViewTextColor(R.id.team, context.getResources().getColor(R.color.black));
            holder.setTextViewTextColor(R.id.team_name, context.getResources().getColor(R.color.gray_128));
            holder.setTextViewTextColor(R.id.number, context.getResources().getColor(R.color.black));
        }
        holder.setTextViewText(R.id.team, data.getPlayer());
        holder.setTextViewText(R.id.team_name, data.getTeam());
        holder.setTextViewText(R.id.number, data.getNumber());

        if (data.getPhoto() != null && data.getPhoto().endsWith(".jpg")) {
            holder.setImageViewImage(R.id.logo_integral, R.mipmap.player, data.getPhoto(), R.anim.fade_in);
        }
    }

}
