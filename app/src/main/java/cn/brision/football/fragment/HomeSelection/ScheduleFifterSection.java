package cn.brision.football.fragment.HomeSelection;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.brision.football.R;
import cn.brision.football.activity.data.ScheduleActivity;
import cn.brision.football.model.FifterInfo;
import cn.brision.football.model.FifterStatusInfo;
import cn.brision.football.model.ScheduleGameEvent;
import cn.brision.football.view.sectioned.StatelessSection;

/**
 * Created by brision on 16/10/19.
 */
public class ScheduleFifterSection extends StatelessSection {

    private Context context;
    private String title;
    //存储所有的子item
    private HashMap<String, List<String>> listDataChild = new HashMap<>();
    //存储所有子item的状态
    private HashMap<String, ArrayList<FifterInfo>> mChildCheckStates;

    public ScheduleFifterSection(Context context, String title, HashMap<String, List<String>> listDataChild) {
        super(R.layout.item_fifter_head, R.layout.item_fifter_body);
        this.context = context;
        this.title = title;
        this.listDataChild = listDataChild;
        mChildCheckStates = new HashMap<String, ArrayList<FifterInfo>>();
        saveCheck();
    }

    /**
     * 未点击确定关闭过滤器的时候将item的状态进行还原
     * @param statusInfos
     */
    public void initStatus(List<FifterStatusInfo> statusInfos) {
        if (statusInfos != null) {
            for (int i = 0; i < statusInfos.size(); i++) {
                Set<String> strings = mChildCheckStates.keySet();
                if (mChildCheckStates.containsKey(statusInfos.get(i).getTitle())) {
                    ArrayList<FifterInfo> fifterInfos = mChildCheckStates.get(statusInfos.get(i).getTitle());
                    FifterInfo fifterInfo = fifterInfos.get(statusInfos.get(i).getPos());
                    fifterInfo.status = fifterInfo.status == true ? false : true;
                }
            }
        }

    }

    //item的总数
    @Override
    public int getContentItemsTotal() {
        return listDataChild.get(title).size();
    }

    /**
     * new对象的时候存储每个item的默认值
     */
    private void saveCheck() {
        ArrayList<FifterInfo> fifterInfos = new ArrayList<>();
        for (int j = 0; j < listDataChild.get(title).size(); j++) {
            FifterInfo fifterInfo = new FifterInfo();
            fifterInfo.setName(listDataChild.get(title).get(j));
            fifterInfo.setStatus(false);
            fifterInfos.add(fifterInfo);
        }
        mChildCheckStates.put(title, fifterInfos);
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        itemViewHolder.childText.setText(listDataChild.get(title).get(position));
        final ArrayList<FifterInfo> fifterInfos = mChildCheckStates.get(title);
        final FifterInfo fifterInfo = fifterInfos.get(position);
        if (fifterInfo.status)
            itemViewHolder.childText.setTextColor(Color.GRAY);
        else
            itemViewHolder.childText.setTextColor(Color.WHITE);
        if (mChildCheckStates.containsKey(title)) {
            itemViewHolder.childText.setText(fifterInfo.getName());
            itemViewHolder.childText.setChecked(fifterInfo.status);
        }

        itemViewHolder.childText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fifterInfo.status = fifterInfo.status == true ? false : true;
                if (fifterInfo.status)
                    itemViewHolder.childText.setTextColor(Color.GRAY);
                else
                    itemViewHolder.childText.setTextColor(Color.WHITE);
                compareDta(title, position, fifterInfo.status);
            }
        });
    }


    public void compareDta(String title, int pos, boolean isChecked) {
        ((ScheduleActivity) (context)).getNewStatus(pos, title, isChecked);
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {

        HeaderViewHolder mHeaderViewHolder = (HeaderViewHolder) holder;
        mHeaderViewHolder.parentText.setText(title);

    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.parent_text)
        TextView parentText;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.child_text)
        CheckBox childText;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
