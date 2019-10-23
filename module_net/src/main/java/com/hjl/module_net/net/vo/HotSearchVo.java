package com.hjl.module_net.net.vo;

import android.view.View;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;

/**
 * created by long on 2019/10/23
 */
public class HotSearchVo {
    /**
     * status : 1
     * error :
     * data : {"timestamp":1571738096,"info":[{"sort":1,"keyword":"独家首发","jumpurl":"https://m2.service.kugou.com/yueku/category/html/index.html?areaid=30"},{"sort":4,"keyword":"儿歌大全","jumpurl":"https://m2.service.kugou.com/yueku/category/html/index.html?areaid=2"},{"sort":12,"keyword":"动漫","jumpurl":"https://m2.service.kugou.com/yueku/category/html/index.html?areaid=3"},{"sort":13,"keyword":"洗脑电音","jumpurl":"https://m2.service.kugou.com/yueku/category/html/index.html?areaid=25"},{"sort":14,"keyword":"情歌大全","jumpurl":"https://m2.service.kugou.com/yueku/category/html/index.html?areaid=15"}]}
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
         * timestamp : 1571738096
         * info : [{"sort":1,"keyword":"独家首发","jumpurl":"https://m2.service.kugou.com/yueku/category/html/index.html?areaid=30"},{"sort":4,"keyword":"儿歌大全","jumpurl":"https://m2.service.kugou.com/yueku/category/html/index.html?areaid=2"},{"sort":12,"keyword":"动漫","jumpurl":"https://m2.service.kugou.com/yueku/category/html/index.html?areaid=3"},{"sort":13,"keyword":"洗脑电音","jumpurl":"https://m2.service.kugou.com/yueku/category/html/index.html?areaid=25"},{"sort":14,"keyword":"情歌大全","jumpurl":"https://m2.service.kugou.com/yueku/category/html/index.html?areaid=15"}]
         */

        private int timestamp;
        private List<InfoBean> info;

        public int getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(int timestamp) {
            this.timestamp = timestamp;
        }

        public List<InfoBean> getInfo() {

            TagAdapter<InfoBean> a = new TagAdapter<InfoBean>(info) {
                @Override
                public View getView(FlowLayout parent, int position, InfoBean infoBean) {
                    return null;
                }
            };

            return info;
        }

        public void setInfo(List<InfoBean> info) {
            this.info = info;
        }

        public static class InfoBean {
            /**
             * sort : 1
             * keyword : 独家首发
             * jumpurl : https://m2.service.kugou.com/yueku/category/html/index.html?areaid=30
             */

            private int sort;
            private String keyword;
            private String jumpurl;

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public String getKeyword() {
                return keyword;
            }

            public void setKeyword(String keyword) {
                this.keyword = keyword;
            }

            public String getJumpurl() {
                return jumpurl;
            }

            public void setJumpurl(String jumpurl) {
                this.jumpurl = jumpurl;
            }
        }
    }
}
