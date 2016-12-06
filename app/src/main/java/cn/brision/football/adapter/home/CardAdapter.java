package cn.brision.football.adapter.home;

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
import cn.brision.football.model.HomeCardInfo;
import cn.brision.football.utils.GlideUtils;
import cn.brision.football.utils.TimeturnUtils;
import retrofit2.http.POST;

/**
 * Created by brision on 16/9/30.
 */
public class CardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<CommentInfo.DataBean> list = new ArrayList<>();
    private List<HomeCardInfo> headList = new ArrayList<>();
    public static final int ITEM_TYPE_HEAD = 0;
    public static final int ITEM_TYPE_BODY = 1;

    public CardAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<CommentInfo.DataBean> data) {
        list.addAll(data);
        notifyDataSetChanged();
    }

    public void setHeadData(List<HomeCardInfo> data) {
        headList.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        list.clear();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_HEAD) {
            return new MyViewHolderHead(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardlistview_head, parent, false));
        }
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_comment, parent, false));
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof MyViewHolder) {
            ((MyViewHolder) holder).mName.setText(list.get(position-1).getPoster().getNick());
            ((MyViewHolder) holder).mText.setText(list.get(position-1).getMessage());
            ((MyViewHolder) holder).mTime.setText(TimeturnUtils.livesDateFormat22(list.get(position-1).getCreatedAt()));
            String avatar = list.get(position-1).getPoster().getAvatar();
            GlideUtils.get(mContext).getImage(avatar, mContext.getResources().getDrawable(R.drawable.logo), ((MyViewHolder) holder).mIcon, R.anim.fade_in);
        }
        if (holder instanceof MyViewHolderHead){
            if(headList.size()!=0) {
                ((MyViewHolderHead) holder).commentCount.setText("精彩评论(" + headList.get(0).getData().getCommentsCount() + ")");
                ((MyViewHolderHead) holder).title.setText(headList.get(0).getData().getDescription());
            }
        }
    }
    @Override
    public int getItemCount() {
        return list.size() + headList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == ITEM_TYPE_HEAD)
            return ITEM_TYPE_HEAD;
        else
            return ITEM_TYPE_BODY;
    }

    class MyViewHolderHead extends RecyclerView.ViewHolder {

        TextView title;
        TextView commentCount;

        public MyViewHolderHead(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            commentCount = (TextView) itemView.findViewById(R.id.commentCount);
        }

    }

    class MyViewHolder extends RecyclerView.ViewHolder {

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
