package cn.brision.football.adapter.data.integral;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.brision.football.model.TeamPlayer;

/**
 * Created by wangchengcheng on 16/11/1.
 */
public abstract class BasePlayerAdapter<VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {
    private ArrayList<TeamPlayer.DataBean> items = new ArrayList<>();

    public BasePlayerAdapter() {
        setHasStableIds(true);
    }

    public void add(TeamPlayer.DataBean object) {
        items.add(object);
        notifyDataSetChanged();
    }

    public void add(int index, TeamPlayer.DataBean object) {
        items.add(index, object);
        notifyDataSetChanged();
    }

    public void addAll(List<TeamPlayer.DataBean> collection) {
        if (collection != null) {
            items.clear();
            items.addAll(collection);
            notifyDataSetChanged();
        }
    }

    public void addAll(TeamPlayer.DataBean... items) {
        addAll(Arrays.asList(items));
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public TeamPlayer.DataBean getItem(int position) {
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

