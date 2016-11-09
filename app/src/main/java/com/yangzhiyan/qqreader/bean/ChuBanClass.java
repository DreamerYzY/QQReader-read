package com.yangzhiyan.qqreader.bean;

import java.util.List;

/**
 * Created by YZY on 2016/10/24.
 */

public class ChuBanClass {

    /**
     * bookCount : 96185
     * categoryFlag : 3
     * newBookCount : 106
     */

    public CountBean count;
    /**
     * categoryName : 小说
     * level3categoryName : 都市/情感/影视小说/惊悚/恐怖/侦探/悬疑/推理/中国当代小说/中国近现代小说/四大名著/职场/官场/中国古典小说/外国小说/财经/历史/武侠/军事/魔幻/科幻/社会/乡土/世界名著/作品集
     * bookCount : 11396
     * img : http://wfqqreader.3g.qq.com/cover/view_data/view_data_1431510876152.jpg
     * bids : 842452,536968,788041
     * actionId : 13100
     * recommend : false
     */

    public List<PublishCategoryListBean> publishCategoryList;
    /**
     * title : 花样年华
     * image : http://wfqqreader.3g.qq.com/cover/topic/newad_67132388_1464687221115.png
     * type : 4
     * intro : 在这里，你永远是花一样的少年
     * qurl : uniteqqreader://nativepage/category/list?actionId=14300&actionTag=,-1,-1,-1,-1,6&pagestamp=1
     */

    public List<RecmdBean> recmd;

    public static class CountBean {
        public int bookCount;
        public int categoryFlag;
        public int newBookCount;
    }

    public static class PublishCategoryListBean {
        public String categoryName;
        public String level3categoryName;
        public int bookCount;
        public String img;
        public String bids;
        public int actionId;
        public boolean recommend;
    }

    public static class RecmdBean {
        public String title;
        public String image;
        public String type;
        public String intro;
        public String qurl;
    }
}
