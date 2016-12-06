package cn.brision.football.view.Dialog;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.List;

import butterknife.Bind;
import cn.brision.football.R;
import cn.brision.football.adapter.data.SeasonAdapter;
import cn.brision.football.model.DataSeasonInfo;
import cn.brision.football.utils.PreferencesManager;

/**
 * Created by wangchengcheng on 16/8/12.
 * 赛季选择页面
 */
public class SeasonDialog extends BaseDialog implements View.OnClickListener, AdapterView.OnItemClickListener {

    @Bind(R.id.dialog_dismiss)
    LinearLayout seasonDismiss;
    @Bind(R.id.season_listview)
    ListView seasonListView;


    private List<DataSeasonInfo.DataBean> seasonList;
    private SeasonAdapter adapter;

    public SeasonDialog(FragmentActivity context, List<DataSeasonInfo.DataBean> seasonList) {
        super(context, R.layout.dialog_season);
        this.seasonList = seasonList;

        init();

        adapter = new SeasonAdapter(context, seasonList, R.layout.item_season_data);
        seasonListView.setAdapter(adapter);
    }

    private void init() {
        setOnClickListeners(this, seasonDismiss);
        seasonListView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_dismiss:
                dismiss();
                break;
        }
    }

    private int num = 0;//记录上一次点击的位置

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        PreferencesManager preferencesManager = PreferencesManager.getInstance(getOwnerActivity());
        int seasonSelector = preferencesManager.get("seasonSelector", 100);

        //设置标记出所选的赛季
        if (listener != null) {
            seasonListView.getChildAt(i).setBackgroundResource(R.color.gray_240);
            if (seasonSelector != i) {
                seasonListView.getChildAt(seasonSelector).setBackgroundResource(R.color.white);
            }
            if (num != i) {
                seasonListView.getChildAt(num).setBackgroundResource(R.color.white);
            }
            num = i;

            PreferencesManager pm = PreferencesManager.getInstance(getOwnerActivity());
            pm.put("seasonSelector", i);

            listener.seasonSelector(seasonList.get(0), seasonList.get(i));
        } else {
            try {
                throw new Exception("请先设置监听器:调用setDataListener方法!!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private seasonSelectorListener listener;

    public void setDataListener(seasonSelectorListener listener) {
        this.listener = listener;
    }

    public interface seasonSelectorListener {
        void seasonSelector(DataSeasonInfo.DataBean firstInfo, DataSeasonInfo.DataBean selectorInfo);
    }
}
