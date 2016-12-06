package cn.brision.football.adapter.data.schdeule;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.brision.football.R;
import cn.brision.football.activity.data.ScheduleActivity;
import cn.brision.football.model.ScheduleGameEvent;
import cn.brision.football.utils.GlideUtils;
import cn.brision.football.utils.TimeturnUtils;
import cn.brision.football.view.roundedimageview.RoundedImageView;

/**
 * Created by wangchengcheng on 16/8/26.
 * 赛程视频播放页面适配器
 */
public class SchdeuleActivityAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private List<ScheduleGameEvent.DataBean> list = new ArrayList<>();
    private int index;
    private Context context;


    public SchdeuleActivityAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setList(List datas, int index) {
        this.list.clear();
        this.list.addAll(datas);
        this.index = index;
        notifyDataSetChanged();
    }

    public void setIndex(int index) {

        this.index = index;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        int type = getItemViewType(position);
        ViewHolderBig hodlerBig = null;
        ViewHolderSmall hodlerSmall = null;
        if (convertView == null) {
            switch (type) {
                case 1:
                    convertView = inflater.inflate(R.layout.item_videoplay_schdele2, null);
                    hodlerBig = new ViewHolderBig();
                    hodlerBig.bigPlayer = (RoundedImageView) convertView.findViewById(R.id.big_player_img);
                    hodlerBig.bigPlayerName = (TextView) convertView.findViewById(R.id.player_name);
                    hodlerBig.bigDescription = (TextView) convertView.findViewById(R.id.big_description);
                    hodlerBig.bigGameClock = (TextView) convertView.findViewById(R.id.big_gameclock);
                    convertView.setTag(hodlerBig);
                    break;
                default:
                    convertView = inflater.inflate(R.layout.item_videoplay_schdeule, null);
                    hodlerSmall = new ViewHolderSmall();
                    hodlerSmall.smallPlayer = (RoundedImageView) convertView.findViewById(R.id.player_img);
                    hodlerSmall.smallDescription = (TextView) convertView.findViewById(R.id.description);
                    hodlerSmall.smallGameClock = (TextView) convertView.findViewById(R.id.gameclock);
                    convertView.setTag(hodlerSmall);
            }
        } else {
            switch (type) {
                case 1:
                    hodlerBig = (ViewHolderBig) convertView.getTag();
                    break;
                default:
                    hodlerSmall = (ViewHolderSmall) convertView.getTag();
                    break;
            }
        }
        ScheduleGameEvent.DataBean data = list.get(position);
        switch (type) {
            case 1:
                if (data.getPhoto() != null && data.getPhoto().endsWith(".jpg")) {
                    final ViewHolderBig finalHodlerBig = hodlerBig;
                    GlideUtils.get(context).getImage(data.getPhoto(),finalHodlerBig.bigPlayer,R.anim.fade_in);
                }
//                hodlerBig.bigPlayerName.setText(data.getPlayer());
                hodlerBig.bigGameClock.setText(TimeturnUtils.onTimeturn(data.getGameClock()));
                hodlerBig.bigDescription.setText(data.getDescription());
                ((ScheduleActivity)context).share(data);
                break;
            default:
                hodlerSmall.smallGameClock.setText(TimeturnUtils.onTimeturn(data.getGameClock()));

                SpannableString msp = new SpannableString(data.getDescription());
                char s = data.getAction().charAt(data.getAction().length() - 1);
                int index = data.getDescription().indexOf(s);
                msp.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.title_bg)), 0, data.getPlayer().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                if (index >= 0) {
                    msp.setSpan(new ForegroundColorSpan(Color.BLACK), index - data.getAction().length() + 1, index + 1, Spanned.SPAN_COMPOSING);
                }
                hodlerSmall.smallDescription.setText(msp);


                if (data.getPhoto() != null && data.getPhoto().endsWith(".jpg")) {
                    final ViewHolderSmall finalHodlerSmall = hodlerSmall;
                    GlideUtils.get(context).getImage(data.getPhoto(),finalHodlerSmall.smallPlayer,R.anim.fade_in);
                }
                break;
        }
        return convertView;
    }

    class ViewHolderSmall {
        RoundedImageView smallPlayer;
        TextView smallDescription;
        TextView smallGameClock;
    }

    class ViewHolderBig {
        RoundedImageView bigPlayer;
        TextView bigPlayerName;
        TextView bigDescription;
        TextView bigGameClock;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        int type;
        if (position == index) {
            type = 1;
        } else {
            type = 0;
        }
        return type;
    }

    public interface oneKeyShareListener{
        void share(ScheduleGameEvent.DataBean data);
    }
}

