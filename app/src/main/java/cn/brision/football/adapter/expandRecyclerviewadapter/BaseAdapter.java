package cn.brision.football.adapter.expandRecyclerviewadapter;

import android.support.v7.widget.RecyclerView;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.brision.football.model.LivesInfo;


public abstract class BaseAdapter<VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {
    private ArrayList<LivesInfo.DataBean.LivesBean> items = new ArrayList<>();

    public BaseAdapter() {
        setHasStableIds(true);
    }

    public void add(LivesInfo.DataBean.LivesBean object) {
        items.add(object);
        notifyDataSetChanged();
    }

    public void add(int index, LivesInfo.DataBean.LivesBean object) {
        items.add(index, object);
        notifyDataSetChanged();
    }

    public void addAll(List<LivesInfo.DataBean.LivesBean> collection) {
        if (collection != null) {
            items.clear();
            items.addAll(collection);
            notifyDataSetChanged();
        }
    }

    public void addAll(LivesInfo.DataBean.LivesBean... items) {
        addAll(Arrays.asList(items));
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public LivesInfo.DataBean.LivesBean getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).hashCode();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
