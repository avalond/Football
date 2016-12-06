package cn.brision.football.model;

import java.util.List;

/**
 * Created by wangchengcheng on 16/9/27.
 */
public class PlayerStatisticsInfo {

    /**
     * status : 200
     * message :
     * data : {"playerId":"13563","name":"格里兹曼","photo":"http://data.brision.cn/sports/textcaster/photo/5754e59126242.jpg","team":"法国","position":"前锋","nation":"法国","age":25,"birthday":"1991-03-21","height":175,"weight":72,"data":[{"seasonId":"176","leaguename":"欧洲杯2016","detail":{"goal":6,"foul":3,"free_kick":2,"point":1,"header":4,"corner":14,"red_card":0,"yellow_card":0}},{"seasonId":"148","leaguename":"世界杯2014","detail":{"foul":1,"free_kick":1,"corner":3,"goal":0,"red_card":0,"yellow_card":0,"point":0,"header":0}}]}
     */

    private int status;
    private String message;
    /**
     * playerId : 13563
     * name : 格里兹曼
     * photo : http://data.brision.cn/sports/textcaster/photo/5754e59126242.jpg
     * team : 法国
     * position : 前锋
     * nation : 法国
     * age : 25
     * birthday : 1991-03-21
     * height : 175
     * weight : 72
     * data : [{"seasonId":"176","leaguename":"欧洲杯2016","detail":{"goal":6,"foul":3,"free_kick":2,"point":1,"header":4,"corner":14,"red_card":0,"yellow_card":0}},{"seasonId":"148","leaguename":"世界杯2014","detail":{"foul":1,"free_kick":1,"corner":3,"goal":0,"red_card":0,"yellow_card":0,"point":0,"header":0}}]
     */

    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String playerId;
        private String name;
        private String photo;
        private String team;
        private String position;
        private String nation;
        private int age;
        private String birthday;
        private int height;
        private int weight;
        /**
         * seasonId : 176
         * leaguename : 欧洲杯2016
         * detail : {"goal":6,"foul":3,"free_kick":2,"point":1,"header":4,"corner":14,"red_card":0,"yellow_card":0}
         */

        private List<DataBean1> data;

        public String getPlayerId() {
            return playerId;
        }

        public void setPlayerId(String playerId) {
            this.playerId = playerId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getTeam() {
            return team;
        }

        public void setTeam(String team) {
            this.team = team;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getNation() {
            return nation;
        }

        public void setNation(String nation) {
            this.nation = nation;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public List<DataBean1> getData() {
            return data;
        }

        public void setData(List<DataBean1> data) {
            this.data = data;
        }

        public static class DataBean1 {
            private String seasonId;
            private String leaguename;
            /**
             * goal : 6
             * foul : 3
             * free_kick : 2
             * point : 1
             * header : 4
             * corner : 14
             * red_card : 0
             * yellow_card : 0
             */

            private DetailBean detail;

            public String getSeasonId() {
                return seasonId;
            }

            public void setSeasonId(String seasonId) {
                this.seasonId = seasonId;
            }

            public String getLeaguename() {
                return leaguename;
            }

            public void setLeaguename(String leaguename) {
                this.leaguename = leaguename;
            }

            public DetailBean getDetail() {
                return detail;
            }

            public void setDetail(DetailBean detail) {
                this.detail = detail;
            }

            public static class DetailBean {
                private int goal;
                private int foul;
                private int free_kick;
                private int point;
                private int header;
                private int corner;
                private int red_card;
                private int yellow_card;

                public int getGoal() {
                    return goal;
                }

                public void setGoal(int goal) {
                    this.goal = goal;
                }

                public int getFoul() {
                    return foul;
                }

                public void setFoul(int foul) {
                    this.foul = foul;
                }

                public int getFree_kick() {
                    return free_kick;
                }

                public void setFree_kick(int free_kick) {
                    this.free_kick = free_kick;
                }

                public int getPoint() {
                    return point;
                }

                public void setPoint(int point) {
                    this.point = point;
                }

                public int getHeader() {
                    return header;
                }

                public void setHeader(int header) {
                    this.header = header;
                }

                public int getCorner() {
                    return corner;
                }

                public void setCorner(int corner) {
                    this.corner = corner;
                }

                public int getRed_card() {
                    return red_card;
                }

                public void setRed_card(int red_card) {
                    this.red_card = red_card;
                }

                public int getYellow_card() {
                    return yellow_card;
                }

                public void setYellow_card(int yellow_card) {
                    this.yellow_card = yellow_card;
                }
            }
        }
    }
}
