package cn.brision.football.view.banner;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.brision.football.R;
import cn.brision.football.activity.WebAcitivity;
import cn.brision.football.activity.data.MatchActivity;
import cn.brision.football.activity.home.CardActivity;
import cn.brision.football.model.HomeBanner;
import cn.brision.football.utils.DisplayUtils;
import cn.brision.football.utils.GlideUtils;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


/**
 * Created by hcc on 16/8/4 21:18
 * 100332338@qq.com
 * <p/>
 * 自定义Banner无限轮播控件
 */
public class BannerView extends RelativeLayout implements BannerAdapter.ViewPagerOnItemClickListener {

    @Bind(R.id.layout_banner_viewpager)
    ViewPager viewPager;

    @Bind(R.id.layout_banner_points_group)
    LinearLayout points;

    private CompositeSubscription compositeSubscription;

    //默认轮播时间，10s
    private int delayTime = 10;

    private List<View> imageViewList;

    private Context context;

    //    private List<BannerEntity> bannerList;
    private List<HomeBanner.DataBean> bannerList;

    //选中显示Indicator
    private int selectRes = R.drawable.banner_check;

    //非选中显示Indicator
    private int unSelcetRes = R.drawable.banner_normal;

    private int position;

    public BannerView(Context context) {

        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {

        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.layout_custom_banner, this, true);
        ButterKnife.bind(this);

        imageViewList = new ArrayList<>();
    }

    /**
     * 设置轮播间隔时间
     *
     * @param time 轮播间隔时间，单位秒
     */
    public BannerView delayTime(int time) {

        this.delayTime = time;
        return this;
    }

    /**
     * 设置Points资源 Res
     *
     * @param selectRes   选中状态
     * @param unselcetRes 非选中状态
     */
    public void setPointsRes(int selectRes, int unselcetRes) {

        this.selectRes = selectRes;
        this.unSelcetRes = unselcetRes;
    }

    /**
     * 图片轮播需要传入参数
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void build(List<HomeBanner.DataBean> list) {

        destory();

        if (list.size() == 0) {
            this.setVisibility(GONE);
            return;
        }

        bannerList = new ArrayList<>();
        bannerList.addAll(list);
        final int pointSize;
        pointSize = bannerList.size();

        if (pointSize == 2) {
            bannerList.addAll(list);
        }
        //判断是否清空 指示器点
        if (points.getChildCount() != 0) {
            points.removeAllViewsInLayout();
        }

        //初始化与个数相同的指示器点
        for (int i = 0; i < pointSize; i++) {
            View dot = new View(context);
            dot.setBackgroundResource(unSelcetRes);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    DisplayUtils.dp2px(5, context),
                    DisplayUtils.dp2px(5, context));
            params.leftMargin = 10;
            dot.setLayoutParams(params);
            dot.setEnabled(false);
            points.addView(dot);
        }

        points.getChildAt(0).setBackgroundResource(selectRes);

        for (int i = 0; i < bannerList.size(); i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_banner_icon, null);
            TextView bannerText = (TextView) view.findViewById(R.id.tv_bannertext);
            ImageView bannerImage = (ImageView) view.findViewById(R.id.viewpager_image);
            bannerText.setText(bannerList.get(i).getTitle());
            if (bannerList.get(i).getImgpath().endsWith(".jpg"))
                GlideUtils.get(context).getImage(bannerList.get(i).getImgpath(), context.getResources().getDrawable(R.mipmap.redsee), bannerImage, R.anim.fade_in);
            imageViewList.add(view);
        }

        //监听图片轮播，改变指示器状态
        viewPager.clearOnPageChangeListeners();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int pos) {
                pos = pos % pointSize;
                position=pos;
                for (int i = 0; i < points.getChildCount(); i++) {
                    points.getChildAt(i).setBackgroundResource(unSelcetRes);
                }
                points.getChildAt(pos).setBackgroundResource(selectRes);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

                switch (state) {
                    case ViewPager.SCROLL_STATE_IDLE:
                        if (isStopScroll) {
                            startScroll();
                        }
                        break;
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        stopScroll();
                        compositeSubscription.unsubscribe();
                        break;
                }
            }
        });

        BannerAdapter bannerAdapter = new BannerAdapter(imageViewList);
        viewPager.setAdapter(bannerAdapter);
        bannerAdapter.notifyDataSetChanged();
        bannerAdapter.setmViewPagerOnItemClickListener(this);

        //图片开始轮播
        startScroll();
    }

    private boolean isStopScroll = false;

    /**
     * 图片开始轮播
     */
    private void startScroll() {

        compositeSubscription = new CompositeSubscription();
        isStopScroll = false;
        Subscription subscription = Observable.timer(delayTime, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {


                    }

                    @Override
                    public void onNext(Long aLong) {

                        if (isStopScroll) {
                            return;
                        }
                        isStopScroll = true;
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    }
                });
        compositeSubscription.add(subscription);
    }

    /**
     * 图片停止轮播
     */
    private void stopScroll() {
        isStopScroll = true;
    }

    public void destory() {

        if (compositeSubscription != null) {
            compositeSubscription.unsubscribe();
        }
    }

    /**
     * 设置ViewPager的Item点击回调事件
     *
     */
    @Override
    public void onItemClick() {
        switch (bannerList.get(position).getType()) {
            case 1:
                Intent intent = new Intent(context, CardActivity.class);
                intent.putExtra("eventId", bannerList.get(position).getId());
                context.startActivity(intent);
                break;
            case 2:
                Intent intent1 = new Intent(context, MatchActivity.class);
                intent1.putExtra("GameID", bannerList.get(position).getId());
                context.startActivity(intent1);
                break;
            case 3:
                Intent intent2 = new Intent(context, WebAcitivity.class);
                Bundle bundle = new Bundle();
                // bannerList.get(position).getLinkurl()
                bundle.putString("URL","http://www.baidu.com");
                bundle.putString("TITLE", bannerList.get(position).getTitle());
                intent2.putExtras(bundle);
                context.startActivity(intent2);
                break;
            default:
                break;
        }
    }

}
