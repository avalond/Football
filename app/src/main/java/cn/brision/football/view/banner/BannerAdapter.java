package cn.brision.football.view.banner;

/**
 * Created by brision on 16/10/12.
 */

import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewPager适配器
 *
 * @author Rabbit_Lee
 */
public class BannerAdapter extends PagerAdapter {

    //数据源
    private List<View> mList = new ArrayList<>();

    private int pos;

    public BannerAdapter(List<View> list) {
        mList.clear();
        this.mList = list;
    }

    private ViewPagerOnItemClickListener mViewPagerOnItemClickListener;

    void setmViewPagerOnItemClickListener(ViewPagerOnItemClickListener mViewPagerOnItemClickListener) {

        this.mViewPagerOnItemClickListener = mViewPagerOnItemClickListener;
    }

    @Override
    public int getCount() {
        //取超大的数，实现无线循环效果
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        if (mList.size() != 0) {
            container.removeView(mList.get(position % mList.size()));
            View view = mList.get(position % mList.size());
//            pos = position;
            //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
            ViewParent vp = view.getParent();
            if (vp != null) {
                ViewGroup parent = (ViewGroup) vp;
                parent.removeView(view);
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mViewPagerOnItemClickListener != null) {
                        mViewPagerOnItemClickListener.onItemClick();
                    }
                }
            });

            container.addView(mList.get(position % mList.size()));
            return mList.get(position % mList.size());
        }
        return 0;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (mList.size() != 0) {
            container.removeView(mList.get(position % mList.size()));
        }
    }

    interface ViewPagerOnItemClickListener {

        void onItemClick();
    }

}
