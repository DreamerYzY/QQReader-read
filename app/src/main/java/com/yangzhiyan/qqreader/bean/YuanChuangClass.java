package com.yangzhiyan.qqreader.bean;

import java.util.List;

/**
 * Created by YZY on 2016/10/24.
 */

public class YuanChuangClass {

    /**
     * bookCount : 195745
     * categoryFlag : 1
     * newBookCount : 52
     */

    public CountBean count;
    /**
     * title : 以下分类推荐女生阅读
     */

    public LineBean line;
    /**
     * title : 魑魅魍魉
     * image : http://wfqqreader.3g.qq.com/cover/topic/newad_69465644_1477291582442.png
     * type : 3
     * intro : 茅山道士，捉妖降魔！
     * qurl : uniteqqreader://nativepage/tag?key=魑魅魍魉&actionTag=25014,-1,-1,-1,-1,6&pagestamp=1
     */

    public List<RecmdBean> recmd;
    /**
     * categoryName : 古代言情
     * level3categoryName : 穿越奇情/古典架空/经商种田/女尊王朝/古代情缘/宫闱宅斗
     * bookCount : 38018
     * img : http://wfqqreader.3g.qq.com/cover/view_data/view_data_1430902577322.jpg
     * bids : 13650939,13598632,13721646,13779926,185422,13789425
     * actionId : 30013
     * recommend : false
     */

    public List<GirlCategoryListBean> girlCategoryList;
    /**
     * categoryName : 玄幻
     * level3categoryName : 东方玄幻/异世大陆/王朝争霸/高武世界
     * bookCount : 30864
     * img : http://wfqqreader.3g.qq.com/cover/view_data/view_data_1430902409028.jpg
     * bids : 13812603,13722759,13669046,413455,713729,239254
     * actionId : 20001
     * recommend : true
     */

    public List<BoyCategoryListBean> boyCategoryList;

    public static class CountBean {
        public int bookCount;
        public int categoryFlag;
        public int newBookCount;
    }

    public static class LineBean {
        public String title;
    }

    public static class RecmdBean {
        public String title;
        public String image;
        public String type;
        public String intro;
        public String qurl;
    }

    public static class GirlCategoryListBean {
        public String categoryName;
        public String level3categoryName;
        public int bookCount;
        public String img;
        public String bids;
        public int actionId;
        public boolean recommend;
    }

    public static class BoyCategoryListBean {
        public String categoryName;
        public String level3categoryName;
        public int bookCount;
        public String img;
        public String bids;
        public int actionId;
        public boolean recommend;
    }
}
