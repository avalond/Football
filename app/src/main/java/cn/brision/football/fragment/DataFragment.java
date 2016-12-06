package cn.brision.football.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.brision.football.R;
import cn.brision.football.activity.MainActivity;
import cn.brision.football.activity.data.MatchActivity;
import cn.brision.football.activity.data.PlayerActivity;
import cn.brision.football.adapter.data.IntegralAdapter;
import cn.brision.football.adapter.data.ScheduleAdapter;
import cn.brision.football.adapter.data.TopScorerAdapter;
import cn.brision.football.data.MatchAction;
import cn.brision.football.model.DataIntegralInfo;
import cn.brision.football.model.DataScheduleInfo;
import cn.brision.football.model.DataSeasonInfo;
import cn.brision.football.model.DataTitleInfo;
import cn.brision.football.model.DataTopScorerInfo;
import cn.brision.football.utils.PreferencesManager;
import cn.brision.football.utils.ToastUtil;
import cn.brision.football.view.Dialog.SeasonDialog;

/**
 * Created by wangchengcheng on 16/8/2.
 * 数据页面
 */
public class DataFragment extends BaseFragment implements View.OnClickListener, SeasonDialog.seasonSelectorListener, AdapterView.OnItemClickListener {

    @Bind(R.id.data_title_text_right)
    TextView seasonLogo;
    @Bind(R.id.data_title)
    LinearLayout dataTitle;
    @Bind(R.id.data_schedule)
    TextView scheduleTextView;
    @Bind(R.id.data_integral)
    TextView integralTextView;
    @Bind(R.id.data_topscorer)
    TextView topscorerTextView;
    @Bind(R.id.data_content_schedule)
    ListView scheduleListView;
    @Bind(R.id.data_content_integral)
    ListView integralListView;
    @Bind(R.id.data_content_topscorer)
    ListView topscorerListView;
    @Bind(R.id.horizontalscrollview)
    HorizontalScrollView scrollView;
    @Bind(R.id.topview_integral)
    RelativeLayout topviewIntegral;
    @Bind(R.id.topview_topscorer)
    RelativeLayout topviewTopscorer;
    @Bind(R.id.topview_schedule)
    LinearLayout topviewSchedule;

    private View mView;
    private View lineView;
    private TextView titleText;
    private ArrayList titleList;
    private String titleSelector;//记录所选择的比赛类型的id
    private String titleDefault;//当没有选择比赛类型的时候,默认选择第一个比赛的id
    private SeasonDialog seasonDialog;
    private MatchAction matchAction;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_data);
        matchAction = dataManger.getMatchAction();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initView() {
        scheduleTextView.setBackgroundResource(R.drawable.data_title);
        //HorizontalScrollView开启动画效果
        scrollView.setSmoothScrollingEnabled(true);

    }

    @Override
    protected void getOkhttpData() {
        /**
         * 获取数据页面title部分的数据信息
         */

        matchAction.dataTitle("9", new MatchAction.DataTitleDataListener() {
            @Override
            public void dataTitleData(DataTitleInfo mDataTitleInfo) {
                if (mDataTitleInfo != null) {
                    titleList = (ArrayList) mDataTitleInfo.getData();

                    titleDefault = ((DataTitleInfo.DataBean) (titleList.get(0))).getId();
                    //下载默认第一个比赛类型被点击的数据
                    getSeasonData(titleDefault);

                    String d = ((DataTitleInfo.DataBean) (titleList.get(0))).getLeague();
                    for (int i = 0; i < titleList.size(); i++) {
                        mView = LayoutInflater.from(getActivity()).inflate(R.layout.title_data_item, null);
                        titleText = (TextView) mView.findViewById(R.id.data_title_text);
                        lineView = mView.findViewById(R.id.data_title_line);
                        titleText.setOnClickListener(DataFragment.this);
                        titleText.setText(((DataTitleInfo.DataBean) (titleList.get(i))).getLeague());
                        titleText.setTag(((DataTitleInfo.DataBean) (titleList.get(i))).getId());

                        dataTitle.addView(mView);
                        if (i != 0) {
                            lineView.setVisibility(View.GONE);
                        }
                    }
                }
            }
        });


    }

    private int num = 0;//记录上一次点击提示线的位置

    @Override
    public void onClick(View view) {
        PreferencesManager pm = PreferencesManager.getInstance(getActivity());
        pm.put("seasonSelector", 0);
        for (int i = 0; i < titleList.size(); i++) {
            String tag = (String) view.getTag();
            if (tag.equals(((DataTitleInfo.DataBean) (titleList.get(i))).getId())) {
                titleSelector = ((DataTitleInfo.DataBean) (titleList.get(i))).getId();

                //点击下载比赛类型对应的赛季信息
                getSeasonData(titleSelector);

                //设置title部分的滑动选择提示条的位置和状态
                int leftLength = dataTitle.getChildAt(i).getLeft();
                int screenWith = getResources().getDisplayMetrics().widthPixels;
                int width = dataTitle.getChildAt(i).getWidth();
                int scrollLength = leftLength - screenWith / 2 + width / 2;
                scrollView.smoothScrollTo(scrollLength, 0);

                View currentLine = dataTitle.getChildAt(i).findViewById(R.id.data_title_line);
                currentLine.setVisibility(View.VISIBLE);
                View agoLine = dataTitle.getChildAt(num).findViewById(R.id.data_title_line);
                if (num != i) {
                    agoLine.setVisibility(View.GONE);
                }
                num = i;
            }
        }
    }

    /**
     * 下载赛季dialog的数据信息
     */
    private List<DataSeasonInfo.DataBean> seasonList;

    public void getSeasonData(String id) {

        if (getActivity() != null) {
            matchAction.dataSeason(id, new MatchAction.DataSeasonDataListener() {
                @Override
                public void dataSeasonData(DataSeasonInfo mDataSeasonInfo) {
                    seasonList = mDataSeasonInfo.getData();
                    //设置默认赛季时,seasonLogo里面的数字
                    seasonLogo.setText(seasonList.get(0).getSeason().substring(
                            seasonList.get(0).getSeason().length() - 2,
                            seasonList.get(0).getSeason().length()));
                    //点击下载比赛类型对应的默认赛季的------赛程信息
                    getScheduleData(seasonList.get(0).getId(), seasonList.get(0).getRoundCount());
                    //点击下载比赛类型对应的默认赛季的------积分信息
                    getIntegralData(seasonList.get(0).getId());
                    //点击下载比赛类型对应的默认赛季的------射手榜信息
                    getTopScorerData(seasonList.get(0).getId(), getResources().getString(R.string.goal));
                }
            });
        }

    }

    /**
     * 下载赛程的数据信息
     *
     * @param seasonId  赛季id
     * @param count     比赛轮数
     */
    private DataScheduleInfo.DataBean scheduleInfo;
    private List<DataScheduleInfo.DataBean.GameBean> gameList;

    private void getScheduleData(final String seasonId, final int count) {

        matchAction.dataSchdeule(seasonId, count, new MatchAction.DataSchdeuleDataListener() {
            @Override
            public void dataSchdeuleData(DataScheduleInfo mDataScheduleInfo) {

                if (mDataScheduleInfo != null) {
                    scheduleInfo = mDataScheduleInfo.getData();
                    gameList = mDataScheduleInfo.getData().getGame();
                    setScheduleListView(seasonId);
                } else {
                    gameList = null;
                    setScheduleListView(seasonId);
                }
            }
        });
    }

    /**
     * 赛程数据展示的设置
     */
    private TextView countTextView;//赛程的轮次记录
    public TextView prevTextView;//赛程上一轮
    public TextView nextTextView;//赛程下一轮

    private void setScheduleListView(final String seasonId) {
        ScheduleAdapter scheduleAdapter = new ScheduleAdapter(getActivity(), gameList,seasonId, R.layout.item_schedule_data);
        scheduleAdapter.notifyDataSetChanged();
        scheduleListView.setOnItemClickListener(DataFragment.this);
        countTextView = (TextView) topviewSchedule.findViewById(R.id.count);
        prevTextView = (TextView) topviewSchedule.findViewById(R.id.prev_schedule);
        nextTextView = (TextView) topviewSchedule.findViewById(R.id.next_schedule);
        scheduleListView.setAdapter(scheduleAdapter);
        //当前比赛的轮次
        countTextView.setText(scheduleInfo.getRound());
        //上一轮点击切换
        prevTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = scheduleInfo.getCount();
                if (scheduleInfo.isPrev()) {
                    getScheduleData(seasonId, --count);
                }
            }
        });
        //下一轮点击切换
        nextTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = scheduleInfo.getCount();
                if (scheduleInfo.isNext()) {
                    getScheduleData(seasonId, ++count);
                }
            }
        });
    }

    /**
     * 下载积分页面数据
     *
     * @param seasonId  seasonId
     */
    private List<DataIntegralInfo.DataBean> integralInfos;

    private void getIntegralData(final String seasonId) {
        matchAction.dataIntegral(seasonId, new MatchAction.DataIntegralDataListener() {
            @Override
            public void dataIntegralData(DataIntegralInfo mDataIntegralInfo) {
                if (mDataIntegralInfo != null) {
                    integralInfos = mDataIntegralInfo.getData();
                    if (integralInfos.size() == 0) {
                        integralInfos.clear();
                    }
                    setIntegralListView(seasonId);
                }
            }
        });

    }

    /**
     * 积分页面数据展示的设置
     *
     * @param seasonId seasonId
     */
    private void setIntegralListView(String seasonId) {
        IntegralAdapter adapter = new IntegralAdapter(getActivity(), integralInfos, R.layout.item_integral_data, seasonId);
        integralListView.setAdapter(adapter);
    }

    /**
     * 下载射手榜页面数据
     *
     * @param seasonId  seasonId
     * @param action    进球
     */
    private List<DataTopScorerInfo.DataBean> topScorerInfos;

    private void getTopScorerData(final String seasonId, String action) {
        matchAction.dataScorer(seasonId, action, new MatchAction.DataScorerDataListener() {
            @Override
            public void dataScorerData(DataTopScorerInfo mDataTopScorerInfo) {
                topScorerInfos = mDataTopScorerInfo.getData();
                setTopScorerListView(seasonId);
            }
        });
    }

    /**
     * 射手榜数据展示的设置
     */
    private void setTopScorerListView(String seasonId) {
        TopScorerAdapter topScorerAdapter = new TopScorerAdapter(getActivity(), topScorerInfos, R.layout.item_topscorer_data);
        topscorerListView.setOnItemClickListener(DataFragment.this);
        topscorerListView.setAdapter(topScorerAdapter);
    }

    @OnClick(R.id.data_title_text_right)
    public void seasonClick(View v) {
        if (!TextUtils.isEmpty(titleSelector)) {
            seasonDialog = new SeasonDialog(getActivity(), seasonList);
            dialogSelector(seasonDialog);
        } else {
            seasonDialog = new SeasonDialog(getActivity(), seasonList);
            dialogSelector(seasonDialog);
        }
        seasonDialog.show();
    }

    /**
     * (赛程页面,射手榜页面) item的点击事件     ---(积分页面)点击事件写在适配器里面
     *
     * @param adapterView
     * @param view
     * @param i
     * @param l
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case R.id.data_content_schedule:
//                ToastUtil.showToastDefault(getActivity(), gameList.get(i).getHome() + "-----" + gameList.get(i).getAway() + "-----" + gameList.get(i).getId());
                Intent intent = new Intent(getActivity(), MatchActivity.class);
                intent.putExtra("GameID", gameList.get(i).getId());
                startActivity(intent);
                break;
            case R.id.data_content_topscorer:
//                ToastUtil.showToastDefault(getActivity(), topScorerInfos.get(i).getPlayer() + "-----" + topScorerInfos.get(i).getTeam());
                Intent intent1 = new Intent(getActivity(), PlayerActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("player", topScorerInfos.get(i));
//                intent1.putExtras(bundle);
                intent1.putExtra("player", topScorerInfos.get(i).getPlayerId());
                startActivity(intent1);
                break;
        }

    }

    /**
     * 接口回调点击所选择的赛季信息
     *
     * @param seasonDialog 赛季lialog
     */
    private void dialogSelector(SeasonDialog seasonDialog) {
        seasonDialog.setDataListener(this);
    }

    @Override
    public void seasonSelector(DataSeasonInfo.DataBean firstInfo, DataSeasonInfo.DataBean selectorInfo) {
//        ToastUtil.showToastDefault(getActivity(), String.valueOf(selectorInfo.getRoundCount()));
        //设置选择赛季时,seasonLogo里面的数字
        seasonLogo.setText(selectorInfo.getSeason().substring(
                selectorInfo.getSeason().length() - 2,
                selectorInfo.getSeason().length()));
        //下载选择相应的赛季的-----赛程数据信息
        getScheduleData(selectorInfo.getId(), selectorInfo.getRoundCount());
        //下载选择相应的赛季的-----积分数据信息
        getIntegralData(selectorInfo.getId());
        //下载选择相应的赛季的-----射手榜数据信息
        getTopScorerData(selectorInfo.getId(), getResources().getString(R.string.goal));
    }

    @OnClick(R.id.data_schedule)
    public void scheduleClick(View view) {
        resetUi();
        scheduleTextView.setBackgroundResource(R.drawable.data_title);
        scheduleTextView.setTextColor(this.getResources().getColor(R.color.black));
        scheduleListView.setVisibility(View.VISIBLE);
        topviewSchedule.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.data_integral)
    public void integralClick(View view) {
        resetUi();
        integralTextView.setBackgroundResource(R.drawable.data_title);
        integralTextView.setTextColor(this.getResources().getColor(R.color.black));
        integralListView.setVisibility(View.VISIBLE);
        topviewIntegral.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.data_topscorer)
    public void topscorerClick(View view) {
        resetUi();
        topscorerTextView.setBackgroundResource(R.drawable.data_title);
        topscorerTextView.setTextColor(this.getResources().getColor(R.color.black));
        topscorerListView.setVisibility(View.VISIBLE);
        topviewTopscorer.setVisibility(View.VISIBLE);
    }

    private void resetUi() {
        scheduleTextView.setBackgroundResource(R.color.white);
        scheduleTextView.setTextColor(this.getResources().getColor(R.color.gray_128));
        integralTextView.setBackgroundResource(R.color.white);
        integralTextView.setTextColor(this.getResources().getColor(R.color.gray_128));
        topscorerTextView.setBackgroundResource(R.color.white);
        topscorerTextView.setTextColor(this.getResources().getColor(R.color.gray_128));

        scheduleListView.setVisibility(View.GONE);
        integralListView.setVisibility(View.GONE);
        topscorerListView.setVisibility(View.GONE);

        topviewSchedule.setVisibility(View.GONE);
        topviewIntegral.setVisibility(View.GONE);
        topviewTopscorer.setVisibility(View.GONE);
    }

}
