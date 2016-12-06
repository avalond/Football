package cn.brision.football.adapter.data.integral;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cn.brision.football.R;
import cn.brision.football.activity.data.MatchActivity;
import cn.brision.football.adapter.expandRecyclerviewadapter.StickyRecyclerHeadersAdapter;
import cn.brision.football.model.TeamSchedule;
import cn.brision.football.utils.GlideUtils;

/**
 * Created by wangchengcheng on 16/10/31.
 */
public class TeamScheuleAdapter extends BaseScheuleAdapter<TeamScheuleAdapter.ScheuleViewHolder>
                        implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {

    private Context mContext;

    public TeamScheuleAdapter(Context ct) {
        mContext = ct;
    }

    public void addData(List<TeamSchedule.DataBean> data) {
        this.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public ScheuleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_team_scheule, parent, false);
        return new ScheuleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ScheuleViewHolder holder, final int position) {
        RelativeLayout mItem = holder.mItem;
        TextView date = holder.date;
        TextView home = holder.home;
        TextView score = holder.score;
        TextView away = holder.away;

        date.setText(getItem(position).getTime());
        home.setText(getItem(position).getHome());
        score.setText(getItem(position).getScore());
        away.setText(getItem(position).getAway());

        GlideUtils.get(mContext).getImage(getItem(position).getHome_logo(), holder.homeLogo, R.anim.fade_in);
        GlideUtils.get(mContext).getImage(getItem(position).getAway_logo(), holder.awayLogo, R.anim.fade_in);

        mItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MatchActivity.class);
                intent.putExtra("GameID", getItem(position).getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public long getHeaderId(int position) {
        String year = getItem(position).getDate().substring(0, 4);
        String m = getItem(position).getDate().substring(5, 7);
        String day = getItem(position).getDate().substring(8, 10);
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
        textView.setText(getItem(position).getDate());
    }

    public class ScheuleViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout mItem;
        public TextView date;
        public TextView home;
        public TextView score;
        public TextView away;
        public ImageView homeLogo;
        public ImageView awayLogo;
        public ScheuleViewHolder(View itemView) {
            super(itemView);
            mItem = (RelativeLayout) itemView.findViewById(R.id.teamScheule);
            date = (TextView) itemView.findViewById(R.id.teamScheule_date);
            home = (TextView) itemView.findViewById(R.id.teamScheule_home);
            score = (TextView) itemView.findViewById(R.id.teamScheule_score);
            away = (TextView) itemView.findViewById(R.id.teamScheule_away);
            homeLogo = (ImageView) itemView.findViewById(R.id.teamScheule_homelogo);
            awayLogo = (ImageView) itemView.findViewById(R.id.teamScheule_awaylogo);
        }
    }
}
