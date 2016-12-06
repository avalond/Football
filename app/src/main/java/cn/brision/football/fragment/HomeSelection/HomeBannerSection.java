package cn.brision.football.fragment.HomeSelection;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.brision.football.R;
import cn.brision.football.model.HomeBanner;
import cn.brision.football.view.banner.BannerView;
import cn.brision.football.view.sectioned.StatelessSection;

/**
 * Created by brision on 16/10/17.
 */
public class HomeBannerSection extends StatelessSection {

    private List<HomeBanner.DataBean> bannerList;

    public HomeBannerSection(List<HomeBanner.DataBean> bannerList){
        super(R.layout.layout_banner, R.layout.layout_home_recommend_empty);
        this.bannerList = bannerList;
    }

    @Override
    public int getContentItemsTotal() {
        return 1;
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view)
    {
        return new BannerViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
        bannerViewHolder.mBannerView.delayTime(3).build(bannerList);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {


    }

    static class ItemViewHolder extends RecyclerView.ViewHolder
    {
        public ItemViewHolder(View itemView)
        {
            super(itemView);
        }
    }

    static class BannerViewHolder extends RecyclerView.ViewHolder
    {

        @Bind(R.id.home_recommended_banner)
        BannerView mBannerView;

        BannerViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
