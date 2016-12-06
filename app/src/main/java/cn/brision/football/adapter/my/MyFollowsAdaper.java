package cn.brision.football.adapter.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

import cn.brision.football.R;
import cn.brision.football.adapter.CommonRecyclerViewAdapter;
import cn.brision.football.adapter.CommonRecyclerViewHolder;
import cn.brision.football.adapter.ListenerWithPosition;
import cn.brision.football.fragment.circleSelection.CircleItemActivity;
import cn.brision.football.model.BaseCircleInfo;
import cn.brision.football.utils.GlideUtils;


/**
 * Created by wangchengcheng on 16/11/14.
 *
 */
public class MyFollowsAdaper extends CommonRecyclerViewAdapter<BaseCircleInfo> implements ListenerWithPosition.OnClickWithPositionListener {

    private Context context;
    private List<BaseCircleInfo> data;

    public MyFollowsAdaper(Context context, List<BaseCircleInfo> data, int layoutId) {
        super(context, data, layoutId);
        this.context = context;
    }

    @Override
    public void convert(CommonRecyclerViewHolder holder, BaseCircleInfo dataBean) {
        this.data = super.mData;
        holder.setTextViewText(R.id.myfollows_name, dataBean.getTitle());
        GlideUtils.get(context).getImage(dataBean.getImage(), context.getResources().getDrawable((R.mipmap.logo)), (ImageView) holder.getView(R.id.myfollows_logo), R.anim.fade_in);
        holder.setOnClickListener(this, R.id.item);
    }

    @Override
    public void onClick(View v, int position, Object holder) {
        Intent intent = new Intent(context, CircleItemActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("BaseCircleInfo", data.get(position));
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
