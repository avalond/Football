package cn.brision.football.adapter.data;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.brision.football.R;
import cn.brision.football.activity.data.CircleVideoActivity;
import cn.brision.football.activity.data.CommentActivity;
import cn.brision.football.model.PlayerCircleInfo;
import cn.brision.football.model.PlayerCommentsInfo;
import cn.brision.football.utils.BitmapUtils;
import cn.brision.football.utils.GlideUtils;
import cn.brision.football.utils.TimeturnUtils;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;


/**
 * Created by brision on 16/9/27.
 */
public class PlayerCircleAdapter extends RecyclerView.Adapter<PlayerCircleAdapter.MyViewHolder> {

    private List<PlayerCircleInfo.DataBean> list = new ArrayList<>();
    private Context mContext;
    private final String SHARE_WAB = "http://public.brision.cn/share/playevent.html?id=";
    private OnekeyShare oks = null;
    private List<PlayerCommentsInfo> comments = new ArrayList<>();
    ;

    public PlayerCircleAdapter(Context mContext) {
        //初始化ShareSDK
        ShareSDK.initSDK(mContext, "16e712c25c40e");
        this.mContext = mContext;
    }

    public void setData(List<PlayerCircleInfo.DataBean> data) {
        list.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        list.clear();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_circlefragment, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.mName.setText(list.get(position).getPoster().getNick());
        holder.mTitle.setText(list.get(position).getText());
        holder.mDate.setText(TimeturnUtils.livesDateFormat(list.get(position).getCreatedAt()));
        holder.mSharenum.setText(list.get(position).getShareCount());
        holder.mCommentcount.setText(list.get(position).getCommentsCount() + "");

        holder.mComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayerCircleInfo.DataBean dataBean = list.get(position);
                Intent intent1 = new Intent(mContext, CommentActivity.class);
                Bundle bundle = new Bundle();
                //key是eventId,valus是PostId
                bundle.putString("eventId", dataBean.getPostId());
                bundle.putInt("type", 1);
                intent1.putExtras(bundle);
                mContext.startActivity(intent1);
            }
        });
        holder.mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.logo);
                try {
                    BitmapUtils.saveBitmapToFile(bitmap, BitmapUtils.getSDPath() + "/logo/logo.png");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                showShare(list.get(position));
            }
        });
        holder.mIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CircleVideoActivity.class);
                intent.putExtra("VIDEO_URL", (String) list.get(position).getVideo().get(0));
                intent.putExtra("postId",list.get(position).getPostId());
                mContext.startActivity(intent);
            }
        });


        if (list.get(position).getComments().size() > 0) {

            switch (list.get(position).getComments().size()) {
                case 1:
                    PlayerCommentsInfo playerCommentsInfo = list.get(position).getComments().get(0);
                    holder.miss_one.setVisibility(View.VISIBLE);
                    holder.miss_two.setVisibility(View.GONE);
                    holder.miss_three.setVisibility(View.GONE);
                    setUi(holder.comment_name,holder.comment_text,holder.comment_logo, playerCommentsInfo);

                    break;
                case 2:
                    PlayerCommentsInfo playerCommentsInfo0 = list.get(position).getComments().get(0);
                    PlayerCommentsInfo playerCommentsInfo1 = list.get(position).getComments().get(1);
                    holder.miss_one.setVisibility(View.VISIBLE);
                    holder.miss_two.setVisibility(View.VISIBLE);
                    holder.miss_three.setVisibility(View.GONE);
                    setUi(holder.comment_name,holder.comment_text,holder.comment_logo, playerCommentsInfo0);
                    setUi(holder.comment_name1,holder.comment_text1,holder.comment_logo1, playerCommentsInfo1);
                    break;
                default:
                    PlayerCommentsInfo playerCommentsInfo2 = list.get(position).getComments().get(0);
                    PlayerCommentsInfo playerCommentsInfo3 = list.get(position).getComments().get(1);
                    PlayerCommentsInfo playerCommentsInfo4 = list.get(position).getComments().get(2);
                    holder.miss_one.setVisibility(View.VISIBLE);
                    holder.miss_two.setVisibility(View.VISIBLE);
                    holder.miss_three.setVisibility(View.VISIBLE);
                    setUi(holder.comment_name,holder.comment_text,holder.comment_logo, playerCommentsInfo2);
                    setUi(holder.comment_name1,holder.comment_text1,holder.comment_logo1, playerCommentsInfo3);
                    setUi(holder.comment_name2,holder.comment_text2,holder.comment_logo2, playerCommentsInfo4);
                    break;

            }
        }
        else{
            holder.miss_one.setVisibility(View.GONE);
            holder.miss_two.setVisibility(View.GONE);
            holder.miss_three.setVisibility(View.GONE);
        }
    }

    private void setUi(TextView tvName,TextView tvText,ImageView logo, PlayerCommentsInfo playerCommentsInfo) {
        tvName.setText(playerCommentsInfo.getPoster().getNick());
        tvText.setText(playerCommentsInfo.getMessage());
        GlideUtils.get(mContext).getImage(playerCommentsInfo.getPoster().getAvatar(),
                mContext.getResources().getDrawable(R.mipmap.logo),logo,R.anim.fade_in);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void showShare(final PlayerCircleInfo.DataBean data) {
        if (oks == null) {
            oks = new OnekeyShare();
        }
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        if (!ShareSDK.getPlatform(Wechat.NAME).isClientValid()) {
            oks.addHiddenPlatform(Wechat.NAME);
            oks.addHiddenPlatform(WechatMoments.NAME);
        }

        if (!ShareSDK.getPlatform(QQ.NAME).isClientValid()) {
            oks.addHiddenPlatform(QQ.NAME);
        }

        if (!ShareSDK.getPlatform(SinaWeibo.NAME).isClientValid()) {
            oks.addHiddenPlatform(SinaWeibo.NAME);
        }

        if (data != null) {
            // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
            oks.setTitle(mContext.getResources().getString(R.string.share_basetitle));

            // titleUrl是标题的网络链接，QQ和QQ空间等使用
            oks.setTitleUrl(SHARE_WAB + data.getPostId());

            // text是分享文本，所有平台都需要这个字段
            oks.setText(data.getText());
//            oks.setImageUrl("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png");
            // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
            oks.setImagePath(BitmapUtils.getSDPath() + "/logo/logo.png");//确保SDcard下面存在此张图片
            // url仅在微信（包括好友和朋友圈）中使用
            oks.setUrl(SHARE_WAB + data.getPostId());
            Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.logo);
            oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
                @Override
                public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
                    if (SinaWeibo.NAME.equals(platform.getName())) {
                        String text = data.getText()
                                + "\n" + SHARE_WAB + data.getPostId();
                        paramsToShare.setText(text);
                    }
                    if (WechatMoments.NAME.equals(platform.getName())) {
                        paramsToShare.setTitle(mContext.getString(R.string.share_basetitle) + " " + data.getText());
                    }
                    if ("ShortMessage".equals(platform.getName())) {
                        String url = null;
                        paramsToShare.setImagePath(url);
                        String text = data.getText()
                                + "\n" + SHARE_WAB + data.getPostId();
                        paramsToShare.setText(text);
                    }
                }
            });
            oks.show(mContext);
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mName;
        TextView mCommentcount;
        TextView mDate;
        TextView mSharenum;
        TextView mTitle;

        RelativeLayout miss_one;
        ImageView comment_logo;
        TextView comment_name;
        TextView comment_text;

        RelativeLayout miss_two;
        ImageView comment_logo1;
        TextView comment_name1;
        TextView comment_text1;

        RelativeLayout miss_three;
        ImageView comment_logo2;
        TextView comment_name2;
        TextView comment_text2;

        ImageView mIcon;
        LinearLayout mComment;
        LinearLayout mShare;

        public MyViewHolder(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.circler_name);
            mCommentcount = (TextView) itemView.findViewById(R.id.circler_commentcount);
            mDate = (TextView) itemView.findViewById(R.id.circler_date);
            mSharenum = (TextView) itemView.findViewById(R.id.circler_sharenum);
            mTitle = (TextView) itemView.findViewById(R.id.circler_title);

            mComment = (LinearLayout) itemView.findViewById(R.id.ll_count);
            mIcon = (ImageView) itemView.findViewById(R.id.circler_icon);
            mShare = (LinearLayout) itemView.findViewById(R.id.ll_share);

            miss_one = (RelativeLayout) itemView.findViewById(R.id.miss_one);
            comment_logo = (ImageView) itemView.findViewById(R.id.comment_logo);
            comment_name = (TextView) itemView.findViewById(R.id.comment_name);
            comment_text = (TextView) itemView.findViewById(R.id.comment_text);


            miss_two = (RelativeLayout) itemView.findViewById(R.id.miss_two);
            comment_logo1 = (ImageView) itemView.findViewById(R.id.comment_logo1);
            comment_name1 = (TextView) itemView.findViewById(R.id.comment_name1);
            comment_text1 = (TextView) itemView.findViewById(R.id.comment_text1);

            miss_three = (RelativeLayout) itemView.findViewById(R.id.miss_three);
            comment_logo2 = (ImageView) itemView.findViewById(R.id.comment_logo2);
            comment_name2 = (TextView) itemView.findViewById(R.id.comment_name2);
            comment_text2 = (TextView) itemView.findViewById(R.id.comment_text2);
        }
    }
}
