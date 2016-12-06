package cn.brision.football.adapter.data.integral;

/**
 * Created by wangchengcheng on 16/10/31.
 */
import android.support.v7.widget.RecyclerView;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.brision.football.model.TeamSchedule;


public abstract class BaseScheuleAdapter<VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {
    private ArrayList<TeamSchedule.DataBean> items = new ArrayList<>();

    public BaseScheuleAdapter() {
        setHasStableIds(true);
    }

    public void add(TeamSchedule.DataBean object) {
        items.add(object);
        notifyDataSetChanged();
    }

    public void add(int index, TeamSchedule.DataBean object) {
        items.add(index, object);
        notifyDataSetChanged();
    }

    public void addAll(List<TeamSchedule.DataBean> collection) {
        if (collection != null) {
            items.clear();
            items.addAll(collection);
            notifyDataSetChanged();
        }
    }

    public void addAll(TeamSchedule.DataBean... items) {
        addAll(Arrays.asList(items));
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public TeamSchedule.DataBean getItem(int position) {
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
