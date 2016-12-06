package cn.brision.football.adapter.lives;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.brision.football.R;
import cn.brision.football.adapter.CommonRecyclerViewAdapter;
import cn.brision.football.adapter.CommonRecyclerViewHolder;
import cn.brision.football.adapter.ListenerWithPosition;
import cn.brision.football.eventbus.RefreshLiveLeagues;
import cn.brision.football.model.LiveleaguesInfo;
import cn.brision.football.utils.GlideUtils;
import cn.brision.football.utils.PreferencesManager;


/**
 * Created by wangchengcheng on 16/11/7.
 *
 */
public class LiveNotAdapter extends CommonRecyclerViewAdapter<LiveleaguesInfo.DataBean> implements ListenerWithPosition.OnClickWithPositionListener {

    private Context context;
    private List<LiveleaguesInfo.DataBean> data;    //已定制
    private List<LiveleaguesInfo.DataBean> not;     //未定制
    private PreferencesManager preferencesManager;

    public LiveNotAdapter(Context context, List<LiveleaguesInfo.DataBean> data, int layoutId) {
        super(context, data, layoutId);
        this.context = context;

    }

    public void addData(List<LiveleaguesInfo.DataBean> data, PreferencesManager preferencesManager){
        this.data = data;
        this.preferencesManager = preferencesManager;
    }

    @Override
    public void convert(CommonRecyclerViewHolder holder, LiveleaguesInfo.DataBean dataBean) {
        holder.setTextViewText(R.id.livelegues_name, dataBean.getLeague());
        GlideUtils.get(context).getImage(dataBean.getImage(), context.getResources().getDrawable((R.mipmap.logo)), (ImageView) holder.getView(R.id.livelegues_logo), R.anim.fade_in);
        holder.setImageViewImage(R.id.livelegues_status, R.mipmap.liveleagues_green);

        holder.setOnClickListener(this, R.id.livelegues_status);
    }

    @Override
    public void onClick(View v, int position, Object holder) {
        this.not = super.mData;
        data.add(not.get(position));

        not.remove(position);
        notifyDataSetChanged();

        Set<String> dingYue = new HashSet<>();
        Set<String> noDingYue = new HashSet<>();

        for (int i = 0; i < not.size(); i++) {
            noDingYue.add(not.get(i).getLeague());
        }
        for (int j = 0; j < data.size(); j++) {
            dingYue.add(data.get(j).getLeague());
        }
        preferencesManager.put("noDingYue", noDingYue);
        preferencesManager.put("dingYue", dingYue);

        EventBus.getDefault().post(new RefreshLiveLeagues(data));
    }
}
