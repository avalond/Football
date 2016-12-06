package cn.brision.football.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangchengcheng on 16/9/5.
 */
public abstract class CommonRecyclerViewAdapter<T> extends RecyclerView.Adapter {
    public Context mContext;
    public List<T> mData;
    private int layoutId;
    View mView;

    public CommonRecyclerViewAdapter(Context context, List<T> data, int layoutId) {
        this.mContext = context;
        this.mData = data == null ? (List<T>) new ArrayList<>() : data;
        this.layoutId = layoutId;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(layoutId, parent, false);
        return new CommonRecyclerViewHolder(mContext, mView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CommonRecyclerViewHolder commonHolder = (CommonRecyclerViewHolder) holder;
        commonHolder.position = position;
        convert(commonHolder, mData.get(position));
    }

    @Override
    public int getItemCount() {
        return (mData != null) ? mData.size() : 0;
    }

    public abstract void convert(CommonRecyclerViewHolder holder, T t);

}
