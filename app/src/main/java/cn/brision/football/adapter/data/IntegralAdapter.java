package cn.brision.football.adapter.data;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.brision.football.R;
import cn.brision.football.adapter.SimpleAdapter;
import cn.brision.football.adapter.SimpleViewHolder;
import cn.brision.football.model.DataIntegralInfo;
import cn.brision.football.view.CustomGridView;

/**
 * Created by wangchengcheng on 16/8/18.
 * 积分数据适配器
 */
public class IntegralAdapter extends SimpleAdapter {

    private ArrayList<DataIntegralInfo.DataBean> mData;
    private Context context;
    private String seasonId;

    public IntegralAdapter(Context context, List<DataIntegralInfo.DataBean> data, int layoutId, String seasonId) {
        super(context, data, layoutId);
        this.mData = (ArrayList<DataIntegralInfo.DataBean>) data;
        this.context = context;
        this.seasonId = seasonId;
        notifyDataSetChanged();
    }

    @Override
    public void convert(SimpleViewHolder holder, Object o, int i) {
        DataIntegralInfo.DataBean dataIntegralInfo = (DataIntegralInfo.DataBean) o;
        if (mData.size() != 1) {
            holder.setTextViewText(R.id.group, dataIntegralInfo.getGroup());
        } else {
            holder.setViewVisibility(R.id.group, View.GONE);
        }

        CustomGridView gridView = holder.getView(R.id.custom_grid);
        IntegralCustomGridAdapter adapter = new IntegralCustomGridAdapter(context, dataIntegralInfo.getTeams(), R.layout.item_customgrid_intrgral, seasonId);
        gridView.setAdapter(adapter);
    }
}
