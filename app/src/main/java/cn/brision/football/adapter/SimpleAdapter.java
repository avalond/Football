package cn.brision.football.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangchengcheng on 16/8/12.
 * 通用的adapter
 */
public abstract class SimpleAdapter<T> extends BaseAdapter {

    public List<T> mData;
    protected Context mContext;
    private int layoutId;

    protected SimpleAdapter(Context context, List<T> data, int layoutId) {
        this.mContext = context;
        this.mData = data == null ? new ArrayList<T>() : data;
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public T getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        SimpleViewHolder holder = SimpleViewHolder.getHolder(mContext, view,
                viewGroup, layoutId, i);

        convert(holder, getItem(i), i);
        return holder.getmConvertView();
    }

    public abstract void convert(SimpleViewHolder holder, T t, int i);

    public void setList(List<T> datas) {
        this.mData.clear();
        this.mData.addAll(datas);
        notifyDataSetChanged();
    }

    public void clearList() {
        this.mData.clear();
        notifyDataSetChanged();
    }

    public void addList(List<T> datas) {
        this.mData.clear();
        this.mData.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 通过类名启动Activity
     *
     * @param cla 需要跳转的类
     */
    protected void openActivity(Class<?> cla) {
        Intent intent = new Intent(mContext, cla);
        mContext.startActivity(intent);
    }
}
