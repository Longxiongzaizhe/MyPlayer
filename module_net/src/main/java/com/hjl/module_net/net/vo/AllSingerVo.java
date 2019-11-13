package com.hjl.module_net.net.vo;

import java.util.List;

/**
 * created by long on 2019/11/11
 */
public class AllSingerVo {
    /**
     * status : 1
     * error :
     * data : {"timestamp":1573464636,"info":[{"heatoffset":1,"sortoffset":-43,"singername":"周杰伦","intro":"","fanscount":9471901,"songcount":0,"imgurl":"http://singerimg.kugou.com/uploadpic/softhead/{size}/20180515/20180515002522714.jpg","banner_url":"","is_settled":0,"singerid":3520,"heat":201993,"mvcount":0,"albumcount":0},{"heatoffset":-1,"sortoffset":26,"singername":"薛之谦","intro":"","fanscount":13222749,"songcount":0,"imgurl":"http://singerimg.kugou.com/uploadpic/softhead/{size}/20190103/20190103191232626.jpg","banner_url":"","is_settled":1,"singerid":3060,"heat":186074,"mvcount":0,"albumcount":0},{"heatoffset":0,"sortoffset":17,"singername":"G.E.M.邓紫棋","intro":"","fanscount":7297867,"songcount":0,"imgurl":"http://singerimg.kugou.com/uploadpic/softhead/{size}/20190720/20190720220214641.jpg","banner_url":"","is_settled":1,"singerid":4490,"heat":127808,"mvcount":0,"albumcount":0},{"heatoffset":0,"sortoffset":0,"singername":"小阿枫","intro":"","fanscount":317260,"songcount":0,"imgurl":"http://singerimg.kugou.com/uploadpic/softhead/{size}/20180716/20180716172540971.jpg","banner_url":"","is_settled":1,"singerid":753317,"heat":123288,"mvcount":0,"albumcount":0},{"heatoffset":0,"sortoffset":-40,"singername":"林俊杰","intro":"","fanscount":6403413,"songcount":0,"imgurl":"http://singerimg.kugou.com/uploadpic/softhead/{size}/20191017/20191017142309922.jpg","banner_url":"","is_settled":1,"singerid":1574,"heat":109518,"mvcount":0,"albumcount":0},{"heatoffset":0,"sortoffset":38,"singername":"刘德华","intro":"","fanscount":2817768,"songcount":0,"imgurl":"http://singerimg.kugou.com/uploadpic/softhead/{size}/20180507/20180507120242140.jpg","banner_url":"","is_settled":0,"singerid":1573,"heat":99214,"mvcount":0,"albumcount":0},{"heatoffset":0,"sortoffset":15,"singername":"刀郎","intro":"","fanscount":2119678,"songcount":0,"imgurl":"http://singerimg.kugou.com/uploadpic/softhead/{size}/20190115/20190115150401884.jpg","banner_url":"","is_settled":0,"singerid":730,"heat":81432,"mvcount":0,"albumcount":0},{"heatoffset":0,"sortoffset":-10,"singername":"张学友","intro":"","fanscount":3075653,"songcount":0,"imgurl":"http://singerimg.kugou.com/uploadpic/softhead/{size}/20140527/20140527095042283066.jpg","banner_url":"","is_settled":0,"singerid":3521,"heat":77847,"mvcount":0,"albumcount":0},{"heatoffset":18,"sortoffset":8,"singername":"阿悠悠","intro":"","fanscount":477295,"songcount":0,"imgurl":"http://singerimg.kugou.com/uploadpic/softhead/{size}/20190723/20190723094144991.jpg","banner_url":"","is_settled":1,"singerid":832532,"heat":67810,"mvcount":0,"albumcount":0},{"heatoffset":-1,"sortoffset":-1,"singername":"王俊凯","intro":"","fanscount":4824827,"songcount":0,"imgurl":"http://singerimg.kugou.com/uploadpic/softhead/{size}/20190723/20190723175319741.jpg","banner_url":"","is_settled":1,"singerid":155515,"heat":66492,"mvcount":0,"albumcount":0}],"total":221551}
     * errcode : 0
     */

    private int status;
    private String error;
    private DataBean data;
    private int errcode;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public static class DataBean {
        /**
         * timestamp : 1573464636
         * info : [{"heatoffset":1,"sortoffset":-43,"singername":"周杰伦","intro":"","fanscount":9471901,"songcount":0,"imgurl":"http://singerimg.kugou.com/uploadpic/softhead/{size}/20180515/20180515002522714.jpg","banner_url":"","is_settled":0,"singerid":3520,"heat":201993,"mvcount":0,"albumcount":0},{"heatoffset":-1,"sortoffset":26,"singername":"薛之谦","intro":"","fanscount":13222749,"songcount":0,"imgurl":"http://singerimg.kugou.com/uploadpic/softhead/{size}/20190103/20190103191232626.jpg","banner_url":"","is_settled":1,"singerid":3060,"heat":186074,"mvcount":0,"albumcount":0},{"heatoffset":0,"sortoffset":17,"singername":"G.E.M.邓紫棋","intro":"","fanscount":7297867,"songcount":0,"imgurl":"http://singerimg.kugou.com/uploadpic/softhead/{size}/20190720/20190720220214641.jpg","banner_url":"","is_settled":1,"singerid":4490,"heat":127808,"mvcount":0,"albumcount":0},{"heatoffset":0,"sortoffset":0,"singername":"小阿枫","intro":"","fanscount":317260,"songcount":0,"imgurl":"http://singerimg.kugou.com/uploadpic/softhead/{size}/20180716/20180716172540971.jpg","banner_url":"","is_settled":1,"singerid":753317,"heat":123288,"mvcount":0,"albumcount":0},{"heatoffset":0,"sortoffset":-40,"singername":"林俊杰","intro":"","fanscount":6403413,"songcount":0,"imgurl":"http://singerimg.kugou.com/uploadpic/softhead/{size}/20191017/20191017142309922.jpg","banner_url":"","is_settled":1,"singerid":1574,"heat":109518,"mvcount":0,"albumcount":0},{"heatoffset":0,"sortoffset":38,"singername":"刘德华","intro":"","fanscount":2817768,"songcount":0,"imgurl":"http://singerimg.kugou.com/uploadpic/softhead/{size}/20180507/20180507120242140.jpg","banner_url":"","is_settled":0,"singerid":1573,"heat":99214,"mvcount":0,"albumcount":0},{"heatoffset":0,"sortoffset":15,"singername":"刀郎","intro":"","fanscount":2119678,"songcount":0,"imgurl":"http://singerimg.kugou.com/uploadpic/softhead/{size}/20190115/20190115150401884.jpg","banner_url":"","is_settled":0,"singerid":730,"heat":81432,"mvcount":0,"albumcount":0},{"heatoffset":0,"sortoffset":-10,"singername":"张学友","intro":"","fanscount":3075653,"songcount":0,"imgurl":"http://singerimg.kugou.com/uploadpic/softhead/{size}/20140527/20140527095042283066.jpg","banner_url":"","is_settled":0,"singerid":3521,"heat":77847,"mvcount":0,"albumcount":0},{"heatoffset":18,"sortoffset":8,"singername":"阿悠悠","intro":"","fanscount":477295,"songcount":0,"imgurl":"http://singerimg.kugou.com/uploadpic/softhead/{size}/20190723/20190723094144991.jpg","banner_url":"","is_settled":1,"singerid":832532,"heat":67810,"mvcount":0,"albumcount":0},{"heatoffset":-1,"sortoffset":-1,"singername":"王俊凯","intro":"","fanscount":4824827,"songcount":0,"imgurl":"http://singerimg.kugou.com/uploadpic/softhead/{size}/20190723/20190723175319741.jpg","banner_url":"","is_settled":1,"singerid":155515,"heat":66492,"mvcount":0,"albumcount":0}]
         * total : 221551
         */

        private int timestamp;
        private int total;
        private List<InfoBean> info;

        public int getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(int timestamp) {
            this.timestamp = timestamp;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<InfoBean> getInfo() {
            return info;
        }

        public void setInfo(List<InfoBean> info) {
            this.info = info;
        }

        public static class InfoBean {
            /**
             * heatoffset : 1
             * sortoffset : -43
             * singername : 周杰伦
             * intro :
             * fanscount : 9471901
             * songcount : 0
             * imgurl : http://singerimg.kugou.com/uploadpic/softhead/{size}/20180515/20180515002522714.jpg
             * banner_url :
             * is_settled : 0
             * singerid : 3520
             * heat : 201993
             * mvcount : 0
             * albumcount : 0
             */

            private String singername;
            private String intro;
            private int fanscount;
            private int songcount;
            private String imgurl;
            private String banner_url;
            private int singerid;
            private int heat;
            private int mvcount;

            public String getSingername() {
                return singername;
            }

            public void setSingername(String singername) {
                this.singername = singername;
            }

            public String getIntro() {
                return intro;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }

            public int getFanscount() {
                return fanscount;
            }

            public void setFanscount(int fanscount) {
                this.fanscount = fanscount;
            }

            public int getSongcount() {
                return songcount;
            }

            public void setSongcount(int songcount) {
                this.songcount = songcount;
            }

            public String getImgurl() {
                return imgurl;
            }

            public void setImgurl(String imgurl) {
                this.imgurl = imgurl;
            }

            public String getBanner_url() {
                return banner_url;
            }

            public void setBanner_url(String banner_url) {
                this.banner_url = banner_url;
            }

            public int getSingerid() {
                return singerid;
            }

            public void setSingerid(int singerid) {
                this.singerid = singerid;
            }

            public int getHeat() {
                return heat;
            }

            public void setHeat(int heat) {
                this.heat = heat;
            }

            public int getMvcount() {
                return mvcount;
            }

            public void setMvcount(int mvcount) {
                this.mvcount = mvcount;
            }
        }
    }
}
