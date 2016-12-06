package cn.brision.football.adapter.data;

import android.content.Context;

import java.util.List;

import cn.brision.football.R;
import cn.brision.football.adapter.SimpleAdapter;
import cn.brision.football.adapter.SimpleViewHolder;
import cn.brision.football.model.DataSeasonInfo;
import cn.brision.football.utils.PreferencesManager;

/**
 * Created by wangchengcheng on 16/8/12.
 * 赛季页面适配器
 */
public class SeasonAdapter extends SimpleAdapter {

    private Context context;

    public SeasonAdapter(Context context, List data, int layoutId) {
        super(context, data, layoutId);
        this.context = context;
    }

    @Override
    public void convert(SimpleViewHolder holder, Object o, int i) {
        holder.setTextViewText(R.id.season_list_textview, ((DataSeasonInfo.DataBean) o).getSeason() + context.getString(R.string.season));

        PreferencesManager preferencesManager = PreferencesManager.getInstance(context);
        int seasonSelector = preferencesManager.get("seasonSelector", 100);
        if (seasonSelector != 100 && i == seasonSelector) {
            holder.setViewBackground(R.id.season_list_item, R.color.gray_240);
        }
    }

}
