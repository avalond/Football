package cn.brision.football.adapter.data.schdeule;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.brision.football.R;
import cn.brision.football.model.ScheduleGameEvent;

/**
 * Created by brision on 16/9/1.
 */
public class ExpListViewAdapterWithCheckbox extends BaseExpandableListAdapter {

    private Context mContext;

    private HashMap<String, List<String>> mListDataChild = new HashMap<>();
    private ArrayList<String> mListDataGroup;
    private HashMap<Integer, boolean[]> mChildCheckStates;
    private ChildViewHolder childViewHolder;
    private GroupViewHolder groupViewHolder;
    private String groupText;
    private String childText;
    int num = 0;
    public List<ScheduleGameEvent.DataBean> newData = new ArrayList<>();

    public ExpListViewAdapterWithCheckbox(Context context, ArrayList<String> listDataGroup, HashMap<String, List<String>> listDataChild, List<ScheduleGameEvent.DataBean> data) {

        this.newData.addAll(data);
        mContext = context;
        mListDataGroup = listDataGroup;
        mListDataChild = listDataChild;
        mChildCheckStates = new HashMap<Integer, boolean[]>();
        saveCheck();
    }

    private void saveCheck() {
        for (int i = 0; i < mListDataGroup.size(); i++) {
            boolean[] booleen = new boolean[mListDataChild.get(mListDataGroup.get(i)).size()];
            for (int j = 0; j < mListDataChild.get(mListDataGroup.get(i)).size(); j++) {
                booleen[j] = true;
            }
            mChildCheckStates.put(i, booleen);
        }
    }

    @Override
    public int getGroupCount() {
        return mListDataGroup.size();
    }


    @Override
    public String getGroup(int groupPosition) {
        return mListDataGroup.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        groupText = getGroup(groupPosition);
        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_expend_parent, null);

            groupViewHolder = new GroupViewHolder();
            groupViewHolder.mGroupImage = (ImageView) convertView.findViewById(R.id.parent_imageview);
            groupViewHolder.mGroupView = convertView.findViewById(R.id.view);
            groupViewHolder.mGroupText = (TextView) convertView.findViewById(R.id.expend_text);

            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }

        if (isExpanded) {
            groupViewHolder.mGroupImage.setBackgroundResource(R.mipmap.jiantou_up);
            groupViewHolder.mGroupView.setBackgroundColor(Color.BLUE);
        } else {
            groupViewHolder.mGroupView.setBackgroundColor(Color.GRAY);
            groupViewHolder.mGroupImage.setBackgroundResource(R.mipmap.jiantou_down);

        }
        groupViewHolder.mGroupText.setText(groupText);

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mListDataChild.get(mListDataGroup.get(groupPosition)).size();
    }

    @Override
    public String getChild(int groupPosition, int childPosition) {
        return mListDataChild.get(mListDataGroup.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final int mGroupPosition = groupPosition;
        final int mChildPosition = childPosition;

        childText = getChild(mGroupPosition, mChildPosition);

        LayoutInflater inflater = (LayoutInflater) this.mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_fifter_data, null);

        childViewHolder = new ChildViewHolder();

        childViewHolder.mChildText = (TextView) convertView
                .findViewById(R.id.fiftter_player);

        childViewHolder.mCheckBox = (CheckBox) convertView
                .findViewById(R.id.fiftter_checkBox);

        convertView.setTag(childViewHolder);

        childViewHolder.mChildText.setText(childText);

        if (mChildCheckStates.containsKey(mGroupPosition)) {
            num++;
            boolean getChecked[] = mChildCheckStates.get(mGroupPosition);
            childViewHolder.mCheckBox.setChecked(getChecked[mChildPosition]);

        } else {
            num++;
            boolean getChecked[] = new boolean[getChildrenCount(mGroupPosition)];
            mChildCheckStates.put(mGroupPosition, getChecked);
            childViewHolder.mCheckBox.setChecked(false);
        }

        childViewHolder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    boolean getChecked[] = mChildCheckStates.get(mGroupPosition);
                    getChecked[mChildPosition] = isChecked;
                    mChildCheckStates.put(mGroupPosition, getChecked);

                } else {
                    boolean getChecked[] = mChildCheckStates.get(mGroupPosition);
                    getChecked[mChildPosition] = isChecked;
                    mChildCheckStates.put(mGroupPosition, getChecked);

                }
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    public List<ScheduleGameEvent.DataBean> compareDta() {

        boolean[] booleen = mChildCheckStates.get(0);

        for (int i = 0; i < booleen.length; i++) {
            if (!booleen[i]) {
                for (int j = 0; j < newData.size(); j++) {
                    if (newData.get(j).getAction().contains(mListDataChild.get(mListDataGroup.get(0)).get(i))) {
                        newData.remove(j);
                        j = j - 1;
                    }
                }
            }

        }
        boolean[] booleen1 = mChildCheckStates.get(1);
        for (int i = 0; i < booleen1.length; i++) {
            if (!booleen1[i]) {
                for (int j = 0; j < newData.size(); j++) {
                    if (newData.get(j).getPlayer().contains(mListDataChild.get(mListDataGroup.get(1)).get(i))) {
                        newData.remove(j);
                        j = j - 1;
                    }
                }
            }

        }
        boolean[] booleen2 = mChildCheckStates.get(2);
        for (int i = 0; i < booleen2.length; i++) {
            if (!booleen2[i]) {
                for (int j = 0; j < newData.size(); j++) {
                    if (newData.get(j).getTeam().contains(mListDataChild.get(mListDataGroup.get(2)).get(i))) {
                        newData.remove(j);
                        j = j - 1;
                    }
                }
            }

        }
        return newData;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    public final class GroupViewHolder {

        TextView mGroupText;
        ImageView mGroupImage;
        View mGroupView;
    }

    public final class ChildViewHolder {

        TextView mChildText;
        CheckBox mCheckBox;
    }
}
