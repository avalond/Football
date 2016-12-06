package cn.brision.football.adapter.data.player;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cn.brision.football.R;
import cn.brision.football.activity.data.PlayerStatisticsVideoActivity;
import cn.brision.football.adapter.CommonRecyclerViewAdapter;
import cn.brision.football.adapter.CommonRecyclerViewHolder;
import cn.brision.football.adapter.ListenerWithPosition;
import cn.brision.football.model.PlayerStatisticsInfo;
import cn.brision.football.utils.ToastUtil;

/**
 * Created by wangchengcheng on 16/9/27.
 *
 */
public class PlayerStatisticsAdapter extends CommonRecyclerViewAdapter<PlayerStatisticsInfo.DataBean.DataBean1> {

    private Context context;
    private String playerId;

    public PlayerStatisticsAdapter(Context context, List data, int layoutId) {
        super(context, data, layoutId);
        this.context = context;
    }

    public void setPlayerId(String id) {
        playerId = id;
    }

    @Override
    public void convert(CommonRecyclerViewHolder holder, PlayerStatisticsInfo.DataBean.DataBean1 dataBean1) {
        holder.setTextViewText(R.id.statistics_title, dataBean1.getLeaguename());
        holder.setTextViewText(R.id.jinqiu_number, dataBean1.getDetail().getGoal() + "");
        holder.setTextViewText(R.id.hongpai_number, dataBean1.getDetail().getRed_card() + "");
        holder.setTextViewText(R.id.huangpai_number, dataBean1.getDetail().getYellow_card() + "");
        holder.setTextViewText(R.id.fangui_number, dataBean1.getDetail().getFoul() + "");
        holder.setTextViewText(R.id.renyiqiu_number, dataBean1.getDetail().getFree_kick() + "");
        holder.setTextViewText(R.id.dianqiu_number, dataBean1.getDetail().getPoint() + "");
        holder.setTextViewText(R.id.touqiu_number, dataBean1.getDetail().getHeader() + "");
        holder.setTextViewText(R.id.jiaoqiu_number, dataBean1.getDetail().getCorner() + "");

        setImageAndClick(holder, dataBean1, dataBean1.getDetail().getGoal(), R.id.jinqiu_click, R.id.RelativeLayout_jinqiu);
        setImageAndClick(holder, dataBean1, dataBean1.getDetail().getRed_card(), R.id.hongpai_click, R.id.RelativeLayout_hongpai);
        setImageAndClick(holder, dataBean1, dataBean1.getDetail().getYellow_card(), R.id.huangpai_click, R.id.RelativeLayout_huangpai);
        setImageAndClick(holder, dataBean1, dataBean1.getDetail().getFoul(), R.id.fangui_click, R.id.RelativeLayout_fangui);
        setImageAndClick(holder, dataBean1, dataBean1.getDetail().getFree_kick(), R.id.renyiqiu_click, R.id.RelativeLayout_renyiqiu);
        setImageAndClick(holder, dataBean1, dataBean1.getDetail().getPoint(), R.id.dianqiu_click, R.id.RelativeLayout_dianqiu);
        setImageAndClick(holder, dataBean1, dataBean1.getDetail().getHeader(), R.id.touqiu_click, R.id.RelativeLayout_touqiu);
        setImageAndClick(holder, dataBean1, dataBean1.getDetail().getCorner(), R.id.jiaoqiu_click, R.id.RelativeLayout_jiaoqiu);
    }

    private void setImageAndClick(CommonRecyclerViewHolder holder,
                                  final PlayerStatisticsInfo.DataBean.DataBean1 dataBean1,
                                  final int num,
                                  @IdRes int imageId,
                                  @IdRes int itemId) {
        if (num != 0) {
            holder.setImageViewImage(imageId, R.mipmap.player_play);

            final Intent intent = new Intent(context, PlayerStatisticsVideoActivity.class);

            holder.setOnClickListener(new ListenerWithPosition.OnClickWithPositionListener() {
                @Override
                public void onClick(View v, int position, Object holder) {
                    String text = (String) ((TextView) (((RelativeLayout) v).getChildAt(0))).getText();
                    String eventType = null;
                    switch (text) {
                        case "进球":
                            eventType = "goal";
                            break;
                        case "红牌":
                            eventType = "red_card";
                            break;
                        case "黄牌":
                            eventType = "yellow_card";
                            break;
                        case "犯规":
                            eventType = "foul";
                            break;
                        case "任意球":
                            eventType = "free_kick";
                            break;
                        case "点球":
                            eventType = "point";
                            break;
                        case "头球":
                            eventType = "header";
                            break;
                        case "角球":
                            eventType = "corner";
                            break;
                    }
                    intent.putExtra("SeasonId", dataBean1.getSeasonId());
                    intent.putExtra("playerId", playerId);
                    intent.putExtra("eventType", eventType);
                    intent.putExtra("theme", text);
                    context.startActivity(intent);
                }
            }, itemId);
        } else {
            holder.setImageViewImage(imageId, R.mipmap.player_unplay);
        }
    }
}



