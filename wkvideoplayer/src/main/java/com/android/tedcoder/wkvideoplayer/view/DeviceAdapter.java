

package com.android.tedcoder.wkvideoplayer.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.tedcoder.wkvideoplayer.R;

class DeviceAdapter extends BaseAdapter {
    private Context mContext;
    private SuperVideoPlayer mSuperVideoPlayer;

    public DeviceAdapter(SuperVideoPlayer superVideoPlayer,Context context) {
        mSuperVideoPlayer = superVideoPlayer;
        mContext = context;
    }

    @Override
    public int getCount() {
        if (mSuperVideoPlayer.getDevices() == null) {
            return 0;
        } else {
            return mSuperVideoPlayer.getDevices().size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (mSuperVideoPlayer.getDevices() != null) {
            return mSuperVideoPlayer.getDevices().get(position);
        }

        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext,R.layout.item_lv_main, null);
            holder = new ViewHolder();
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_name_item = (TextView) convertView
                .findViewById(R.id.tv_name_item);
        holder.tv_name_item.setText(mSuperVideoPlayer.getDevices().get(position)
                .getFriendlyName());
        return convertView;
    }

    static class ViewHolder {
        private TextView tv_name_item;
    }
}
