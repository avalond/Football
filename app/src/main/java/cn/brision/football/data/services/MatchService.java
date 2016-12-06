package cn.brision.football.data.services;

import java.util.Map;

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
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by brision on 16/9/7.
 */
public interface MatchService {
    String PRODUCT_BASE_RUL = "https://" + BaseService.PRODUCT_HOST;
    String DEBUG_BASE_RUL = "http://" + BaseService.DEBUG_HOST;

    /**
     * datafragment
     *
     * @param sportId
     * @return
     */
    @GET("/webservice/api/data/brisionLeague.php")
    Call<DataTitleInfo> dataTitle(@Query("sportId") String sportId);

    @GET("/webservice/api/data/brisionSeason.php")
    Call<DataSeasonInfo> dataSeason(@Query("leagueId") String leagueId);

    @GET("/webservice/api/data/brisionRound.php")
    Call<DataScheduleInfo> dataSchdeule(@Query("seasonId") String seasonId,
                                        @Query("count") int count);

    @GET("/webservice/api/data/brisionScoreBoard.php")
    Call<DataIntegralInfo> dataIntegral(@Query("seasonId") String seasonId);

    @GET("/webservice/api/data/brisionBoard.php")
    Call<DataTopScorerInfo> dataScorer(@Query("seasonId") String seasonId,
                                       @Query("action") String action);

    //直播页面
    @GET("/webservice/api/live/lives")
    Call<LivesInfo> dataLives();

    @GET("/user/liveleagues")
    Call<LiveleaguesInfo> liveleagues();

    /**
     * 积分球队Activity
     *
     * @param gameId
     * @return
     */
    @GET("/webservice/api/data/brisionGameInfo.php")
    Call<SchdeuleGameInfo> schdeuleTitle(@Query("gameId") String gameId);

    /**
     * 赛程视频播放Activity
     *
     * @param gameId
     * @return
     */
    @GET("/webservice/api/data/brisionGameEvent.php")
    Call<ScheduleGameEvent> schdeuleContent(@Query("gameId") String gameId);

    @GET("/webservice/api/data/brisionTeam.php")
    Call<IntegralTeam> integralTitle(@Query("teamId") String teamId);

    @GET("/webservice/api/data/brisionTeamSchedule.php")
    Call<TeamSchedule> teamSchedule(@Query("teamId") String teamId, @Query("seasonId") String seasonId, @Query("count") String count);

    @GET("/webservice/api/data/brisionTeamPlayer.php")
    Call<TeamPlayer> teamPlayer(@Query("teamId") String teamId);

    /**
     * 球员Activity
     *
     * @param playerId
     * @return
     */
    @GET("/webservice/api/data/brisionPlayer.php")
    Call<PlayerTitleInfo> playerTitle(@Query("playerId") String playerId);

    /**
     * 球员activity
     *
     * @param playerId
     * @return
     */
    @GET("/webservice/api/data/brisionPlayerStatistics.php")
    Call<PlayerStatisticsInfo> playerStatistics(@Query("playerId") String playerId);

    @GET("/webservice/api/data/brisionPlayerEvent.php")
    Call<PlayerEventInfo> playerEvent(@Query("playerId") String playerId, @Query("seasonId") String seasonId, @Query("eventType") String eventType);

    @GET("/webservice/api/subjects/{id}/posts")
    Call<PlayerCircleInfo> playerCiecle(@Path("id") String subjectId, @Query("page") int page);

    @GET("/webservice/api/subjects")
    Call<PlayerCiecleSubjectIdInfo> playerCiecleSubjectId(@Query("playerId") String playerId);

    @GET("/webservice/api/subjects")
    Call<PlayerCiecleSubjectIdInfo> teamIntegralSubjectId(@Query("teamId") String teamId);

    //https://api.brision.cn/webservice/api/comments?postId=5787320ca59e8&pageSize=10
    @GET("/webservice/api/comments")
    Call<CommentInfo> playercomments(@Query("eventId") String eventId, @Query("pageSize") String pageSize, @Query("page") int page);

    @GET("/webservice/api/comments")
    Call<CommentInfo> playercomment(@Query("postId") String eventId, @Query("pageSize") String pageSize, @Query("page") int page);

    @POST("/webservice/api/comments")
    Call<BaseInfo> playerSendComment(@Query("eventId") String eventId, @Body CommentMessage message);
    @POST("/webservice/api/comments")
    Call<BaseInfo> playerSendComments(@Query("postId") String eventId, @Body CommentMessagepost message);

    /**
     * 球员关注
     */
    @GET("/webservice/api/user/follow")
    Call<PlayerFollowInfo> playerFollow(@Query("subjectId") String subjectId);

    @GET("/webservice/api/user/unfollow")
    Call<PlayerFollowInfo> playerUnfollow(@Query("subjectId") String subjectId);

    /**
     * 球队关注
     */
    @POST("/webservice/api/subjects/follow")
    Call<TeamFollowInfo> teamFollow(@Query("subjectId") String subjectId);

    @DELETE("/webservice/api/subjects/unfollow")
    Call<TeamFollowInfo> teamUnFollow(@Query("subjectId") String subjectId);

    /**
     * 圈子页面
     */
    @GET("/webservice/api/subjects/follow")
    Call<CircleFollowsInfo> circleFollows();

    @GET("/webservice/api/subjects")
    Call<CircleUnFollowsInfo> circleUnFollows();

    //圈子关注接口 teamId=933
    @GET("/webservice/api/data/brisionTeam.php")
    Call<CircleFollowService> circleUnFollowsService(@Query("teamId")String teamId);

    //个人信息
    @PATCH("/webservice/api/user/profile")
    Call<BaseInfo> updateProfile(@Body Map<String,String> mProfileInfo);

}
