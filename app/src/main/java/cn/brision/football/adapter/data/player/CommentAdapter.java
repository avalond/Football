package cn.brision.football.adapter.data.player;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.brision.football.R;
import cn.brision.football.model.CommentInfo;
import cn.brision.football.model.PlayerCircleInfo;
import cn.brision.football.utils.GlideUtils;
import cn.brision.football.utils.TimeturnUtils;

/**
 * Created by brision on 16/9/30.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder>{

    private Context mContext;
    private List<CommentInfo.DataBean> list = new ArrayList<>();

    public CommentAdapter(Context context){
        this.mContext =context;
    }

    public void setData(List<CommentInfo.DataBean> data) {
        list.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData(){
        list.clear();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_comment, parent, false);
        return new MyViewHolder(view);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mName.setText(list.get(position).getPoster().getNick());
        holder.mText.setText(list.get(position).getMessage());
        holder.mTime.setText(TimeturnUtils.livesDateFormat22(list.get(position).getCreatedAt()));
        String avatar = list.get(position).getPoster().getAvatar();
        GlideUtils.get(mContext).getImage(avatar,mContext.getResources().getDrawable(R.drawable.logo),holder.mIcon,R.anim.fade_in);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView mName;
        TextView mText;
        ImageView mIcon;
        TextView mTime;

        public MyViewHolder(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.comment_name);
            mText = (TextView) itemView.findViewById(R.id.comment_text);
            mIcon = (ImageView) itemView.findViewById(R.id.comment_logo);
            mTime = (TextView) itemView.findViewById(R.id.comment_time);
        }

    }
}
