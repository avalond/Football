package cn.brision.football.fragment.circleSelection;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.brision.football.R;
import cn.brision.football.model.BaseCircleInfo;
import cn.brision.football.model.CircleFollowsInfo;
import cn.brision.football.utils.GlideUtils;
import cn.brision.football.view.sectioned.SectionedRecyclerViewAdapter;
import cn.brision.football.view.sectioned.StatelessSection;

/**
 * Created by brision on 16/11/8.
 */
public class CircleFollowSelection extends StatelessSection {

    private List<BaseCircleInfo> followsData = new ArrayList<>();
    private Context context;
    private SectionedRecyclerViewAdapter sectionedRecyclerViewAdapter;

    public CircleFollowSelection(Context context, SectionedRecyclerViewAdapter sectionedRecyclerViewAdapter) {
        super(R.layout.fragment_circle_follows_head, R.layout.fragment_circle_follows_head, R.layout.fragment_circle_unfollows_body);
        this.context = context;
        this.sectionedRecyclerViewAdapter = sectionedRecyclerViewAdapter;
    }

    public void setData(List<BaseCircleInfo> followsData) {
        this.followsData = (followsData);
        sectionedRecyclerViewAdapter.notifyDataSetChanged();
    }


    @Override
    public int getContentItemsTotal() {
        return followsData.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeadViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder getFooterViewHolder(View view) {
        return new FootViewHolder(view);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {

        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        final BaseCircleInfo dataBean = followsData.get(position);
        if (context.getResources() != null)
            GlideUtils.get(context).getImage(dataBean.getImage(), context.getResources().getDrawable(R.mipmap.logo), itemViewHolder.mIcon, R.anim.fade_in);
        itemViewHolder.mName.setText(dataBean.getTitle());
        itemViewHolder.mCount.setText("关注" + dataBean.getParticipates());
        itemViewHolder.mCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CircleItemActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("BaseCircleInfo", dataBean);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeadViewHolder headViewHolder = (HeadViewHolder) holder;
        headViewHolder.mHeadTitle.setText("已关注(" + followsData.size() + ")");
    }

    @Override
    public void onBindFooterViewHolder(RecyclerView.ViewHolder holder) {
        FootViewHolder footViewHolder = (FootViewHolder) holder;
        footViewHolder.mFootTitle.setText("未关注");
    }

    class HeadViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.circler_follows_title)
        TextView mHeadTitle;

        public HeadViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.circler_icon)
        ImageView mIcon;
        @Bind(R.id.circler_name)
        TextView mName;
        @Bind(R.id.circler_count)
        TextView mCount;
        @Bind(R.id.cardview)
        CardView mCardview;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class FootViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.circler_follows_title)
        TextView mFootTitle;

        public FootViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
