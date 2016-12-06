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
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.brision.football.R;
import cn.brision.football.model.BaseCircleInfo;
import cn.brision.football.model.CircleUnFollowsInfo;
import cn.brision.football.utils.GlideUtils;
import cn.brision.football.view.sectioned.SectionedRecyclerViewAdapter;
import cn.brision.football.view.sectioned.StatelessSection;

/**
 * Created by brision on 16/11/8.
 */
public class CircleUnfollowsSelection extends StatelessSection {

    private CircleUnFollowsInfo.DataBean unfollowsData = new CircleUnFollowsInfo.DataBean();
    private CircleUnFollowsInfo.DataBean unfollowsDataorigin = new CircleUnFollowsInfo.DataBean();
    private Context context;
    private Boolean isFold = false;
    private ItemViewHolder itemViewHolder;
    private SectionedRecyclerViewAdapter sectionedRecyclerViewAdapter;

    public CircleUnfollowsSelection(Context context, SectionedRecyclerViewAdapter sectionedRecyclerViewAdapter) {
        super(R.layout.fragment_circle_unfollows_head, R.layout.fragment_circle_unfollows_body);
        this.context = context;
        this.sectionedRecyclerViewAdapter = sectionedRecyclerViewAdapter;
    }

    public void setData(CircleUnFollowsInfo.DataBean unfollowsData) {
        this.unfollowsDataorigin = unfollowsData;
        if (isFold)
            setdisplayData(unfollowsData);
    }

    private void setdisplayData(CircleUnFollowsInfo.DataBean unfollowsData) {
        this.unfollowsData = unfollowsData;
        sectionedRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public int getContentItemsTotal() {
        if (isFold)
            return unfollowsData.getContent().size();
        else
            return 0;
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
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        itemViewHolder = (ItemViewHolder) holder;
        if (unfollowsData.getContent() != null) {
            final BaseCircleInfo contentBean = unfollowsData.getContent().get(position);
            if (context.getResources() != null)
                GlideUtils.get(context).getImage(contentBean.getImage(), context.getResources().getDrawable(R.mipmap.logo), itemViewHolder.mIcon, R.anim.fade_in);
            itemViewHolder.mName.setText(contentBean.getTitle());
            itemViewHolder.mCount.setText("关注" + contentBean.getParticipates());

            itemViewHolder.cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CircleItemActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("BaseCircleInfo", contentBean);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        final HeadViewHolder headViewHolder = (HeadViewHolder) holder;
        if (unfollowsDataorigin.getContent() != null) {
            headViewHolder.mHeadTitle.setText(unfollowsDataorigin.getTitle() + "(" + unfollowsDataorigin.getContent().size() + ")");
            headViewHolder.mFold.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isFold = isFold ? false : true;
                    if (isFold) {
                        GlideUtils.get(context).getImage(R.mipmap.jiantou_down, headViewHolder.unfollowOIcon);
                        setdisplayData(unfollowsDataorigin);
                    } else {
                        GlideUtils.get(context).getImage(R.mipmap.jiantou_up, headViewHolder.unfollowOIcon);
                        setdisplayData(new CircleUnFollowsInfo.DataBean());
                    }
                }
            });
        }
    }

    class HeadViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.unfollows_title)
        TextView mHeadTitle;
        @Bind(R.id.unfollows_icon)
        ImageView unfollowOIcon;
        @Bind(R.id.circler_fold)
        RelativeLayout mFold;

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
        CardView cardview;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
