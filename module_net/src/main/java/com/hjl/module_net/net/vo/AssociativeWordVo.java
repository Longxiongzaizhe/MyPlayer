package com.hjl.module_net.net.vo;

import java.util.List;

/**
 * created by long on 2019/10/23
 */
public class AssociativeWordVo {
    /**
     * recordcount : 10
     * data : [{"songcount":296,"searchcount":0,"keyword":"你好不好"},{"songcount":26,"searchcount":0,"keyword":"你好毒"},{"songcount":217,"searchcount":0,"keyword":"你好"},{"songcount":296,"searchcount":0,"keyword":"你，好不好"},{"songcount":396,"searchcount":0,"keyword":"你好吗"},{"songcount":79,"searchcount":0,"keyword":"你好旧时光"},{"songcount":282,"searchcount":0,"keyword":"你好再见"},{"songcount":70,"searchcount":0,"keyword":"你好陌生人"},{"songcount":10,"searchcount":0,"keyword":"你好好照顾你自己"},{"songcount":29,"searchcount":0,"keyword":"你好可爱"}]
     * status : 1
     * error :
     * errcode : 0
     */

    private int recordcount;
    private int status;
    private String error;
    private int errcode;
    private List<DataBean> data;

    public int getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(int recordcount) {
        this.recordcount = recordcount;
    }

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

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * songcount : 296
         * searchcount : 0
         * keyword : 你好不好
         */

        private int songcount;
        private int searchcount;
        private String keyword;

        public int getSongcount() {
            return songcount;
        }

        public void setSongcount(int songcount) {
            this.songcount = songcount;
        }

        public int getSearchcount() {
            return searchcount;
        }

        public void setSearchcount(int searchcount) {
            this.searchcount = searchcount;
        }

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }
    }
}
