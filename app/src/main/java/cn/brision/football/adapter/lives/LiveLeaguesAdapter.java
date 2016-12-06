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
import cn.brision.football.eventbus.RefreshLiveNotLeagues;
import cn.brision.football.model.LiveleaguesInfo;
import cn.brision.football.utils.GlideUtils;
import cn.brision.football.utils.PreferencesManager;

/**
 * Created by wangchengcheng on 16/11/7.
 *
 */
public class LiveLeaguesAdapter extends CommonRecyclerViewAdapter<LiveleaguesInfo.DataBean>
        implements ListenerWithPosition.OnClickWithPositionListener {

    private Context context;
    private List<LiveleaguesInfo.DataBean> data;    //已定制数据
    private List<LiveleaguesInfo.DataBean> not;     //未定制数据
    private PreferencesManager preferencesManager;



    public LiveLeaguesAdapter(Context context, List<LiveleaguesInfo.DataBean> data, int layoutId) {
        super(context, data, layoutId);
        this.context = context;

    }

    public void addNot(List<LiveleaguesInfo.DataBean> not, PreferencesManager preferencesManager){
        this.not = not;
        this.preferencesManager = preferencesManager;
    }

    @Override
    public void convert(CommonRecyclerViewHolder holder, LiveleaguesInfo.DataBean dataBean) {
        holder.setTextViewText(R.id.livelegues_name, dataBean.getLeague());
        GlideUtils.get(context).getImage(dataBean.getImage(), context.getResources().getDrawable((R.mipmap.logo)), (ImageView) holder.getView(R.id.livelegues_logo), R.anim.fade_in);
        holder.setImageViewImage(R.id.livelegues_status, R.mipmap.liveleagues_rad);

        holder.setOnClickListener(this, R.id.livelegues_status);
    }

    @Override
    public void onClick(View v, int position, Object holder) {
        this.data = super.mData;
        not.add(data.get(position));

        data.remove(position);
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

        EventBus.getDefault().post(new RefreshLiveNotLeagues(not));
    }
}
