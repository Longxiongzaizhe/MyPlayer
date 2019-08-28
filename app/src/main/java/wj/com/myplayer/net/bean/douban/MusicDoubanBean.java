package wj.com.myplayer.net.bean.douban;

import java.util.List;

public class MusicDoubanBean {
    /**
     * rating : {"max":10,"average":"8.0","numRaters":3539,"min":0}
     * author : [{"name":"莫文蔚"}]
     * alt_title :
     * image : https://img3.doubanio.com/view/subject/s/public/s29705704.jpg
     * tags : [{"count":637,"name":"莫文蔚"},{"count":221,"name":"华语"},{"count":195,"name":"2018"},{"count":193,"name":"流行"},{"count":192,"name":"安静了。"},{"count":191,"name":"单曲"},{"count":179,"name":"女声"},{"count":169,"name":"李荣浩"}]
     * mobile_link : https://m.douban.com/music/subject/30158301/
     * attrs : {"publisher":["索尼音乐娱乐"],"singer":["莫文蔚"],"pubdate":["2018-02-28"],"title":["慢慢喜欢你"],"media":["数字(Digital)"],"tracks":["慢慢喜欢你"],"version":["单曲"]}
     * title : 慢慢喜欢你
     * alt : https://music.douban.com/subject/30158301/
     * id : 30158301
     */

    private RatingBean rating;
    private String alt_title;
    private String image;
    private String mobile_link;
    private AttrsBean attrs;
    private String title;
    private String alt;
    private String id;
    private List<AuthorBean> author;
    private List<TagsBean> tags;

    public RatingBean getRating() {
        return rating;
    }

    public void setRating(RatingBean rating) {
        this.rating = rating;
    }

    public String getAlt_title() {
        return alt_title;
    }

    public void setAlt_title(String alt_title) {
        this.alt_title = alt_title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMobile_link() {
        return mobile_link;
    }

    public void setMobile_link(String mobile_link) {
        this.mobile_link = mobile_link;
    }

    public AttrsBean getAttrs() {
        return attrs;
    }

    public void setAttrs(AttrsBean attrs) {
        this.attrs = attrs;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<AuthorBean> getAuthor() {
        return author;
    }

    public void setAuthor(List<AuthorBean> author) {
        this.author = author;
    }

    public List<TagsBean> getTags() {
        return tags;
    }

    public void setTags(List<TagsBean> tags) {
        this.tags = tags;
    }

    public static class RatingBean {
        /**
         * max : 10
         * average : 8.0
         * numRaters : 3539
         * min : 0
         */

        private int max;
        private String average;
        private int numRaters;
        private int min;

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public String getAverage() {
            return average;
        }

        public void setAverage(String average) {
            this.average = average;
        }

        public int getNumRaters() {
            return numRaters;
        }

        public void setNumRaters(int numRaters) {
            this.numRaters = numRaters;
        }

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }
    }

    public static class AttrsBean {
        private List<String> publisher;
        private List<String> singer;
        private List<String> pubdate;
        private List<String> title;
        private List<String> media;
        private List<String> tracks;
        private List<String> version;

        public List<String> getPublisher() {
            return publisher;
        }

        public void setPublisher(List<String> publisher) {
            this.publisher = publisher;
        }

        public List<String> getSinger() {
            return singer;
        }

        public void setSinger(List<String> singer) {
            this.singer = singer;
        }

        public List<String> getPubdate() {
            return pubdate;
        }

        public void setPubdate(List<String> pubdate) {
            this.pubdate = pubdate;
        }

        public List<String> getTitle() {
            return title;
        }

        public void setTitle(List<String> title) {
            this.title = title;
        }

        public List<String> getMedia() {
            return media;
        }

        public void setMedia(List<String> media) {
            this.media = media;
        }

        public List<String> getTracks() {
            return tracks;
        }

        public void setTracks(List<String> tracks) {
            this.tracks = tracks;
        }

        public List<String> getVersion() {
            return version;
        }

        public void setVersion(List<String> version) {
            this.version = version;
        }
    }

    public static class AuthorBean {
        /**
         * name : 莫文蔚
         */

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class TagsBean {
        /**
         * count : 637
         * name : 莫文蔚
         */

        private int count;
        private String name;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
