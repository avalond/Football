package cn.brision.football.adapter.data.integral;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cn.brision.football.R;
import cn.brision.football.activity.data.PlayerActivity;
import cn.brision.football.adapter.expandRecyclerviewadapter.StickyRecyclerHeadersAdapter;
import cn.brision.football.model.TeamPlayer;
import cn.brision.football.utils.GlideUtils;
import cn.brision.football.view.roundedimageview.RoundedImageView;

/**
 * Created by wangchengcheng on 16/11/1.
 */
public class TeamPlayerAdapter extends BasePlayerAdapter<TeamPlayerAdapter.PlayerViewHolder>
        implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {

    private Context mContext;

    public TeamPlayerAdapter(Context context) {
        mContext = context;
    }

    public void addData(List<TeamPlayer.DataBean> data) {
        this.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public PlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_team_player, parent, false);
        return new PlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlayerViewHolder holder, final int position) {
        RelativeLayout mItem = holder.mItem;
        TextView name = holder.name;
        TextView num = holder.num;

        name.setText(getItem(position).getName());
        if (getItem(position).getNumber() != null)
        num.setText(getItem(position).getNumber() + "号");
        else
            num.setText(" ");

        GlideUtils.get(mContext).getImage(getItem(position).getPhoto(), mContext.getResources().getDrawable(R.mipmap.player), holder.photo, R.anim.fade_in);

        mItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PlayerActivity.class);
                intent.putExtra("player", getItem(position).getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public long getHeaderId(int position) {
        long i = (long)getItem(position).getPosition().hashCode();
        return i;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_header_player, parent, false);
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        TextView textView = (TextView) holder.itemView;
        textView.setText(getItem(position).getPosition());
    }

    public class PlayerViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout mItem;
        public RoundedImageView photo;
        public TextView name;
        public TextView num;

        public PlayerViewHolder(View itemView) {
            super(itemView);
            mItem = (RelativeLayout) itemView.findViewById(R.id.teamPlayer);
            photo = (RoundedImageView) itemView.findViewById(R.id.teamPlayer_photo);
            name = (TextView) itemView.findViewById(R.id.teamPlayer_name);
            num = (TextView) itemView.findViewById(R.id.teamPlayer_num);
        }
    }
}
