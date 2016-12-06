package cn.brision.football.data;

import android.util.Log;

import java.util.List;
import java.util.Map;

import cn.brision.football.BuildConfig;
import cn.brision.football.data.services.AbsRemoteAction;
import cn.brision.football.data.services.LoginService;
import cn.brision.football.data.services.MatchService;
import cn.brision.football.model.BaseInfo;
import cn.brision.football.model.CircleFollowService;
import cn.brision.football.model.CircleFollowsInfo;
import cn.brision.football.model.CircleUnFollowsInfo;
import cn.brision.football.model.CommentInfo;
import cn.brision.football.model.CommentMessage;
import cn.brision.football.model.CommentMessagepost;
import cn.brision.football.model.DataIntegralInfo;
import cn.brision.football.model.DataScheduleInfo;
import cn.brision.football.model.DataSeasonInfo;
import cn.brision.football.model.DataTitleInfo;
import cn.brision.football.model.DataTopScorerInfo;
import cn.brision.football.model.IntegralTeam;
import cn.brision.football.model.LiveleaguesInfo;
import cn.brision.football.model.LivesInfo;
import cn.brision.football.model.PlayerCiecleSubjectIdInfo;
import cn.brision.football.model.PlayerCircleInfo;
import cn.brision.football.model.PlayerEventInfo;
import cn.brision.football.model.PlayerFollowInfo;
import cn.brision.football.model.PlayerStatisticsInfo;
import cn.brision.football.model.PlayerTitleInfo;
import cn.brision.football.model.SchdeuleGameInfo;
import cn.brision.football.model.ScheduleGameEvent;
import cn.brision.football.model.TeamFollowInfo;
import cn.brision.football.model.TeamPlayer;
import cn.brision.football.model.TeamSchedule;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by brision on 16/9/7.
 */
public class MatchAction extends AbsRemoteAction {

    private final MatchService mMatchService;
    public static final boolean DEBUG = BuildConfig.API_ENV;

    public MatchAction(OkHttpClient httpClient) {
        super(httpClient);
        if (DEBUG)
            mMatchService = (MatchService) createService(MatchService.DEBUG_BASE_RUL, MatchService.class);
        else
            mMatchService = (MatchService) createService(MatchService.PRODUCT_BASE_RUL, MatchService.class);
    }


    /**
     * datafragment
     *
     * @param sportId
     * @param mDataTitleDataListener
     */
    public void dataTitle(String sportId, final DataTitleDataListener mDataTitleDataListener) {
        Call<DataTitleInfo> call = mMatchService.dataTitle(sportId);
        call.enqueue(new Callback<DataTitleInfo>() {
            @Override
            public void onResponse(Call<DataTitleInfo> call, Response<DataTitleInfo> response) {
                mDataTitleDataListener.dataTitleData(response.body());
            }

            @Override
            public void onFailure(Call<DataTitleInfo> call, Throwable t) {
            }
        });
    }

    public interface DataTitleDataListener {
        void dataTitleData(DataTitleInfo mDataTitleInfo);
    }

    public void dataSeason(String leagueId, final DataSeasonDataListener mDataSeasonDataListener) {
        Call<DataSeasonInfo> call = mMatchService.dataSeason(leagueId);
        call.enqueue(new Callback<DataSeasonInfo>() {
            @Override
            public void onResponse(Call<DataSeasonInfo> call, Response<DataSeasonInfo> response) {
                mDataSeasonDataListener.dataSeasonData(response.body());
            }

            @Override
            public void onFailure(Call<DataSeasonInfo> call, Throwable t) {
            }
        });
    }

    public interface DataSeasonDataListener {
        void dataSeasonData(DataSeasonInfo mDataSeasonInfo);
    }

    public void dataSchdeule(String seasonId, int count, final DataSchdeuleDataListener mDataSchdeuleDataListener) {
        Call<DataScheduleInfo> call = mMatchService.dataSchdeule(seasonId, count);
        call.enqueue(new Callback<DataScheduleInfo>() {
            @Override
            public void onResponse(Call<DataScheduleInfo> call, Response<DataScheduleInfo> response) {
                mDataSchdeuleDataListener.dataSchdeuleData(response.body());
            }

            @Override
            public void onFailure(Call<DataScheduleInfo> call, Throwable t) {
            }
        });
    }

    public interface DataSchdeuleDataListener {
        void dataSchdeuleData(DataScheduleInfo mDataScheduleInfo);
    }

    public void dataIntegral(String seasonId, final DataIntegralDataListener mDataIntegralDataListener) {
        Call<DataIntegralInfo> call = mMatchService.dataIntegral(seasonId);
        call.enqueue(new Callback<DataIntegralInfo>() {
            @Override
            public void onResponse(Call<DataIntegralInfo> call, Response<DataIntegralInfo> response) {
                mDataIntegralDataListener.dataIntegralData(response.body());
            }

            @Override
            public void onFailure(Call<DataIntegralInfo> call, Throwable t) {
            }
        });
    }

    public interface DataIntegralDataListener {
        void dataIntegralData(DataIntegralInfo mDataIntegralInfo);
    }

    public void dataScorer(String seasonId, String action, final DataScorerDataListener mDataScorerDataListener) {
        Call<DataTopScorerInfo> call = mMatchService.dataScorer(seasonId, action);
        call.enqueue(new Callback<DataTopScorerInfo>() {
            @Override
            public void onResponse(Call<DataTopScorerInfo> call, Response<DataTopScorerInfo> response) {
                mDataScorerDataListener.dataScorerData(response.body());
            }

            @Override
            public void onFailure(Call<DataTopScorerInfo> call, Throwable t) {
            }
        });
    }

    public interface DataScorerDataListener {
        void dataScorerData(DataTopScorerInfo mDataTopScorerInfo);
    }

    /**
     * 直播fragment
     *
     * @param mDataLivesDataListener
     */
    public void dataLives(final DataLivesDataListener mDataLivesDataListener) {
        Call<LivesInfo> call = mMatchService.dataLives();
        call.enqueue(new Callback<LivesInfo>() {
            @Override
            public void onResponse(Call<LivesInfo> call, Response<LivesInfo> response) {
                mDataLivesDataListener.dataLivesData(response.body());
            }

            @Override
            public void onFailure(Call<LivesInfo> call, Throwable t) {
            }
        });
    }

    public interface DataLivesDataListener {
        void dataLivesData(LivesInfo mLivesInfo);
    }

    /**
     * 直播赛事筛选
     *
     * @param mLiveLeaguesListener
     */
    public void liveleagues(final LiveLeaguesListener mLiveLeaguesListener) {
        Call<LiveleaguesInfo> call = mMatchService.liveleagues();
        call.enqueue(new Callback<LiveleaguesInfo>() {
            @Override
            public void onResponse(Call<LiveleaguesInfo> call, Response<LiveleaguesInfo> response) {
                mLiveLeaguesListener.liveLeagues(response.body());
            }

            @Override
            public void onFailure(Call<LiveleaguesInfo> call, Throwable t) {

            }
        });
    }

    public interface LiveLeaguesListener {
        void liveLeagues(LiveleaguesInfo mLiveleaguesInfo);
    }


    /**
     * 积分球队Activity的title部分的数据请求
     *
     * @param teamId
     * @param mIntegralTitleDataListener
     */
    public void integralTitle(String teamId, final IntegralTitleDataListener mIntegralTitleDataListener) {
        Call<IntegralTeam> call = mMatchService.integralTitle(teamId);
        call.enqueue(new Callback<IntegralTeam>() {
            @Override
            public void onResponse(Call<IntegralTeam> call, Response<IntegralTeam> response) {
                mIntegralTitleDataListener.integralTitleData(response.body());
            }

            @Override
            public void onFailure(Call<IntegralTeam> call, Throwable t) {
            }
        });
    }

    public interface IntegralTitleDataListener {
        void integralTitleData(IntegralTeam mIntegralTeam);
    }

    /**
     * 积分榜--球队--赛程数据
     *
     * @param teamId
     * @param seasonId
     * @param count
     * @param mTeamScheduleDataListener
     */
    public void teamSchedule(String teamId, String seasonId, String count, final TeamScheduleDataListener mTeamScheduleDataListener) {
        Call<TeamSchedule> call = mMatchService.teamSchedule(teamId, seasonId, count);
        call.enqueue(new Callback<TeamSchedule>() {
            @Override
            public void onResponse(Call<TeamSchedule> call, Response<TeamSchedule> response) {
                mTeamScheduleDataListener.teamScheduleData(response.body());
            }

            @Override
            public void onFailure(Call<TeamSchedule> call, Throwable t) {

            }
        });
    }

    public interface TeamScheduleDataListener {
        void teamScheduleData(TeamSchedule mTeamSchedule);
    }

    /**
     * 积分榜--球队--球员列表
     *
     * @param teamId
     * @param mTeamPlayerDataListener
     */
    public void teamPlayer(String teamId, final TeamPlayerDataListener mTeamPlayerDataListener) {
        Call<TeamPlayer> call = mMatchService.teamPlayer(teamId);
        call.enqueue(new Callback<TeamPlayer>() {
            @Override
            public void onResponse(Call<TeamPlayer> call, Response<TeamPlayer> response) {
                mTeamPlayerDataListener.teamPlayerData(response.body());
            }

            @Override
            public void onFailure(Call<TeamPlayer> call, Throwable t) {

            }
        });
    }

    public interface TeamPlayerDataListener {
        void teamPlayerData(TeamPlayer mTeamPlayer);
    }

    /**
     * 赛程视频播放Activity的title部分数据请求
     *
     * @param gameId
     * @param mSchdeuleTitleDataListener
     */
    public void schdeuleTitle(String gameId, final SchdeuleTitleDataListener mSchdeuleTitleDataListener) {
        Call<SchdeuleGameInfo> call = mMatchService.schdeuleTitle(gameId);
        call.enqueue(new Callback<SchdeuleGameInfo>() {
            @Override
            public void onResponse(Call<SchdeuleGameInfo> call, Response<SchdeuleGameInfo> response) {
                mSchdeuleTitleDataListener.schdeuleTitleData(response.body());
            }

            @Override
            public void onFailure(Call<SchdeuleGameInfo> call, Throwable t) {
            }
        });
    }

    public interface SchdeuleTitleDataListener {
        void schdeuleTitleData(SchdeuleGameInfo mSchdeuleGameInfo);
    }

    /**
     * 赛程视频播放Activity的Listiew部分数据请求
     *
     * @param gameId
     * @param mSchdeuleContentDataListener
     */
    public void schdeuleContent(String gameId, final SchdeuleContentDataListener mSchdeuleContentDataListener) {
        Call<ScheduleGameEvent> call = mMatchService.schdeuleContent(gameId);
        call.enqueue(new Callback<ScheduleGameEvent>() {
            @Override
            public void onResponse(Call<ScheduleGameEvent> call, Response<ScheduleGameEvent> response) {
                mSchdeuleContentDataListener.schdeuleContentData(response.body());
            }

            @Override
            public void onFailure(Call<ScheduleGameEvent> call, Throwable t) {
            }
        });
    }

    public interface SchdeuleContentDataListener {
        void schdeuleContentData(ScheduleGameEvent mScheduleGameEvent);
    }

    /**
     * 球员Activity的title部分的数据请求
     *
     * @param playerId
     * @param mPlayerTitleDataListener
     */
    public void playerTitle(String playerId, final PlayerTitleDataListener mPlayerTitleDataListener) {
        Call<PlayerTitleInfo> call = mMatchService.playerTitle(playerId);
        call.enqueue(new Callback<PlayerTitleInfo>() {
            @Override
            public void onResponse(Call<PlayerTitleInfo> call, Response<PlayerTitleInfo> response) {
                mPlayerTitleDataListener.playerTitleData(response.body());
            }

            @Override
            public void onFailure(Call<PlayerTitleInfo> call, Throwable t) {
            }
        });
    }

    public interface PlayerTitleDataListener {
        void playerTitleData(PlayerTitleInfo mPlayerTitleInfo);
    }

    /**
     * 球员Activity的StatisticsFragment的数据请求
     *
     * @param playerId
     * @param mPlayerStatisticsDataListener
     */
    public void playerStatistics(String playerId, final PlayerStatisticsDataListener mPlayerStatisticsDataListener) {
        Call<PlayerStatisticsInfo> call = mMatchService.playerStatistics(playerId);
        call.enqueue(new Callback<PlayerStatisticsInfo>() {
            @Override
            public void onResponse(Call<PlayerStatisticsInfo> call, Response<PlayerStatisticsInfo> response) {
                mPlayerStatisticsDataListener.playerStatisticsData(response.body());
            }

            @Override
            public void onFailure(Call<PlayerStatisticsInfo> call, Throwable t) {
            }
        });
    }

    public interface PlayerStatisticsDataListener {
        void playerStatisticsData(PlayerStatisticsInfo mPlayerStatisticsInfo);
    }

    public void playerEvent(String playerId, String seasonId, String eventType, final PlayerEventDataListener mPlayerEventDataListener) {
        Call<PlayerEventInfo> call = mMatchService.playerEvent(playerId, seasonId, eventType);
        call.enqueue(new Callback<PlayerEventInfo>() {
            @Override
            public void onResponse(Call<PlayerEventInfo> call, Response<PlayerEventInfo> response) {
                mPlayerEventDataListener.playerEventData(response.body());
            }

            @Override
            public void onFailure(Call<PlayerEventInfo> call, Throwable t) {
            }
        });
    }

    public interface PlayerEventDataListener {
        void playerEventData(PlayerEventInfo mPlayerEventInfo);
    }

    public void playerCiecle(String subjectId, int page, final PlayerCiecleDataListener mPlayerCiecleDataListener, final PlayerCiecleErrorListener mPlayerCiecleErrorListener) {
        Call<PlayerCircleInfo> call = mMatchService.playerCiecle(subjectId, page);
        call.enqueue(new Callback<PlayerCircleInfo>() {
            @Override
            public void onResponse(Call<PlayerCircleInfo> call, Response<PlayerCircleInfo> response) {
                mPlayerCiecleDataListener.playerCiecleData(response.body());
            }

            @Override
            public void onFailure(Call<PlayerCircleInfo> call, Throwable t) {
                mPlayerCiecleErrorListener.playerCiecleError();
            }
        });
    }

    public interface PlayerCiecleDataListener {
        void playerCiecleData(PlayerCircleInfo mPlayerCircleInfo);
    }

    public interface PlayerCiecleErrorListener {
        void playerCiecleError();
    }


    public void playerCiecleSubjectId(String playerId, final PlayerCiecleSubjectIdDataListener mPlayerCiecleSubjectIdDataListener) {
        Call<PlayerCiecleSubjectIdInfo> call = mMatchService.playerCiecleSubjectId(playerId);
        call.enqueue(new Callback<PlayerCiecleSubjectIdInfo>() {
            @Override
            public void onResponse(Call<PlayerCiecleSubjectIdInfo> call, Response<PlayerCiecleSubjectIdInfo> response) {
                mPlayerCiecleSubjectIdDataListener.playerCiecleSubjectIdData(response.body());
            }

            @Override
            public void onFailure(Call<PlayerCiecleSubjectIdInfo> call, Throwable t) {
            }
        });
    }

    public interface PlayerCiecleSubjectIdDataListener {
        void playerCiecleSubjectIdData(PlayerCiecleSubjectIdInfo mPlayerCiecleSubjectIdInfo);
    }


    public void teamIntegralSubjectId(String teamId, final TeamIntegralSubjectIdDataListener mTeamIntegralSubjectIdDataListener) {
        Call<PlayerCiecleSubjectIdInfo> call = mMatchService.teamIntegralSubjectId(teamId);
        call.enqueue(new Callback<PlayerCiecleSubjectIdInfo>() {
            @Override
            public void onResponse(Call<PlayerCiecleSubjectIdInfo> call, Response<PlayerCiecleSubjectIdInfo> response) {
                mTeamIntegralSubjectIdDataListener.teamIntegralSubjectIdData(response.body());
            }

            @Override
            public void onFailure(Call<PlayerCiecleSubjectIdInfo> call, Throwable t) {
            }
        });
    }

    public interface TeamIntegralSubjectIdDataListener {
        void teamIntegralSubjectIdData(PlayerCiecleSubjectIdInfo mPlayerCiecleSubjectIdInfo);
    }

    public void playercomments(int type, String postId, String pageSize, int page, final PlayerCieclecommentsDataListener mPlayerCieclecommentsDataListener,
                               final PlayercommentsErrorListener mPlayercommentsErrorListener) {
        Call<CommentInfo> call;
        if (type == 0)
            call = mMatchService.playercomments(postId, pageSize, page);
        else
            call = mMatchService.playercomment(postId, pageSize, page);
        call.enqueue(new Callback<CommentInfo>() {
            @Override
            public void onResponse(Call<CommentInfo> call, Response<CommentInfo> response) {
                mPlayerCieclecommentsDataListener.playerCieclecomments(response.body());
            }

            @Override
            public void onFailure(Call<CommentInfo> call, Throwable t) {
                mPlayercommentsErrorListener.playercommentsError();
            }
        });
    }

    public interface PlayerCieclecommentsDataListener {
        void playerCieclecomments(CommentInfo mCommentInfo);
    }

    public interface PlayercommentsErrorListener {
        void playercommentsError();
    }

    public void playerSendComments(int type, String postId, CommentMessage message, CommentMessagepost messagepost, final PlayerSendCommentsDataListener mPlayerSendCommentsDataListener) {
        Call<BaseInfo> call;
        if (type == 0)
            call = mMatchService.playerSendComment(postId, message);
        else
            call = mMatchService.playerSendComments(postId, messagepost);
        call.enqueue(new Callback<BaseInfo>() {
            @Override
            public void onResponse(Call<BaseInfo> call, Response<BaseInfo> response) {
                mPlayerSendCommentsDataListener.playerSendComments(response.body());
            }

            @Override
            public void onFailure(Call<BaseInfo> call, Throwable t) {
            }
        });
    }

    public interface PlayerSendCommentsDataListener {
        void playerSendComments(BaseInfo mBaseInfo);
    }


    public void playerFollow(String subjectId, final PlayerFollowListener mPlayerFollowListener) {
        Call<PlayerFollowInfo> call = mMatchService.playerFollow(subjectId);
        call.enqueue(new Callback<PlayerFollowInfo>() {
            @Override
            public void onResponse(Call<PlayerFollowInfo> call, Response<PlayerFollowInfo> response) {
                mPlayerFollowListener.playerFollowData(response.body());
            }

            @Override
            public void onFailure(Call<PlayerFollowInfo> call, Throwable t) {
            }
        });
    }

    public interface PlayerFollowListener {
        void playerFollowData(PlayerFollowInfo mPlayerFollowInfo);
    }

    public void playerUnfollow(String subjectId, final PlayerUnfollowListener mPlayerUnfollowListener) {
        Call<PlayerFollowInfo> call = mMatchService.playerUnfollow(subjectId);
        call.enqueue(new Callback<PlayerFollowInfo>() {
            @Override
            public void onResponse(Call<PlayerFollowInfo> call, Response<PlayerFollowInfo> response) {
                mPlayerUnfollowListener.playerUnfollowData(response.body());
            }

            @Override
            public void onFailure(Call<PlayerFollowInfo> call, Throwable t) {
            }
        });
    }

    public interface PlayerUnfollowListener {
        void playerUnfollowData(PlayerFollowInfo mPlayerFollowInfo);
    }

    public void teamFollow(String subjectId, final TeamFollowListener mTeamFollowListener) {
        Call<TeamFollowInfo> call = mMatchService.teamFollow(subjectId);
        call.enqueue(new Callback<TeamFollowInfo>() {
            @Override
            public void onResponse(Call<TeamFollowInfo> call, Response<TeamFollowInfo> response) {
                mTeamFollowListener.teamFollowData(response.body());
            }

            @Override
            public void onFailure(Call<TeamFollowInfo> call, Throwable t) {
            }
        });
    }

    public interface TeamFollowListener {
        void teamFollowData(TeamFollowInfo mPlayerFollowInfo);
    }

    public void teamUnfollow(String subjectId, final TeamUnfollowListener mTeamUnfollowListener) {
        Call<TeamFollowInfo> call = mMatchService.teamUnFollow(subjectId);
        call.enqueue(new Callback<TeamFollowInfo>() {
            @Override
            public void onResponse(Call<TeamFollowInfo> call, Response<TeamFollowInfo> response) {
                mTeamUnfollowListener.teamUnfollowData(response.body());
            }

            @Override
            public void onFailure(Call<TeamFollowInfo> call, Throwable t) {
            }
        });
    }

    public interface TeamUnfollowListener {
        void teamUnfollowData(TeamFollowInfo mPlayerFollowInfo);
    }

    /**
     * 圈子页面关注 ,未关注页面
     */
    public void circleFollows(final CircleFollowsListener mCircleFollowsListener) {
        Call<CircleFollowsInfo> call = mMatchService.circleFollows();
        call.enqueue(new Callback<CircleFollowsInfo>() {
            @Override
            public void onResponse(Call<CircleFollowsInfo> call, Response<CircleFollowsInfo> response) {
                mCircleFollowsListener.circleFollowsData(response.body());
            }

            @Override
            public void onFailure(Call<CircleFollowsInfo> call, Throwable t) {
                Log.d("Throwable", t.getMessage());
            }
        });
    }

    public interface CircleFollowsListener {
        void circleFollowsData(CircleFollowsInfo mCircleFollowsInfo);
    }

    public void circleUnFollows(final CircleUnFollowsListener mCircleUnFollowsListener) {
        Call<CircleUnFollowsInfo> call = mMatchService.circleUnFollows();
        call.enqueue(new Callback<CircleUnFollowsInfo>() {
            @Override
            public void onResponse(Call<CircleUnFollowsInfo> call, Response<CircleUnFollowsInfo> response) {
                mCircleUnFollowsListener.circleUnFollowsData(response.body());
            }

            @Override
            public void onFailure(Call<CircleUnFollowsInfo> call, Throwable t) {
            }
        });
    }

    public interface CircleUnFollowsListener {
        void circleUnFollowsData(CircleUnFollowsInfo mCircleUnFollowsInfo);
    }


    public void circleUnFollowsService(String teamId, final CircleUnFollowsServiceListener mCircleUnFollowsServiceListener) {
        Call<CircleFollowService> call = mMatchService.circleUnFollowsService(teamId);
        call.enqueue(new Callback<CircleFollowService>() {
            @Override
            public void onResponse(Call<CircleFollowService> call, Response<CircleFollowService> response) {
                mCircleUnFollowsServiceListener.circleUnFollowsServiceData(response.body());
            }

            @Override
            public void onFailure(Call<CircleFollowService> call, Throwable t) {
            }
        });
    }

    public interface CircleUnFollowsServiceListener {
        void circleUnFollowsServiceData(CircleFollowService mCircleFollowService);
    }

    public void updateProfile(Map<String,String> mProfileInfo, final UpdateProfileListener mUpdateProfileListener) {
        Call<BaseInfo> call = mMatchService.updateProfile(mProfileInfo);
        call.enqueue(new Callback<BaseInfo>() {
            @Override
            public void onResponse(Call<BaseInfo> call, Response<BaseInfo> response) {
                mUpdateProfileListener.updateProfileData(response.body());
            }

            @Override
            public void onFailure(Call<BaseInfo> call, Throwable t) {
            }
        });
    }

    public interface UpdateProfileListener {
        void updateProfileData(BaseInfo mBaseInfo);
    }

}
