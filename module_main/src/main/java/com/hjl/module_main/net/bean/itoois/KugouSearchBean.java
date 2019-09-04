package com.hjl.module_main.net.bean.itoois;

import java.util.List;

public class KugouSearchBean {




    private int code;
    private String msg;
    private long timestamp;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private int total;
        private int istagresult;
        private String tab;
        private int correctiontype;
        private int forcecorrection;
        private String correctiontip;
        private int istag;
        private int allowerr;
        private int timestamp;
        private List<AggregationBean> aggregation;
        private List<InfoBean> info;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getIstagresult() {
            return istagresult;
        }

        public void setIstagresult(int istagresult) {
            this.istagresult = istagresult;
        }

        public String getTab() {
            return tab;
        }

        public void setTab(String tab) {
            this.tab = tab;
        }

        public int getCorrectiontype() {
            return correctiontype;
        }

        public void setCorrectiontype(int correctiontype) {
            this.correctiontype = correctiontype;
        }

        public int getForcecorrection() {
            return forcecorrection;
        }

        public void setForcecorrection(int forcecorrection) {
            this.forcecorrection = forcecorrection;
        }

        public String getCorrectiontip() {
            return correctiontip;
        }

        public void setCorrectiontip(String correctiontip) {
            this.correctiontip = correctiontip;
        }

        public int getIstag() {
            return istag;
        }

        public void setIstag(int istag) {
            this.istag = istag;
        }

        public int getAllowerr() {
            return allowerr;
        }

        public void setAllowerr(int allowerr) {
            this.allowerr = allowerr;
        }

        public int getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(int timestamp) {
            this.timestamp = timestamp;
        }

        public List<AggregationBean> getAggregation() {
            return aggregation;
        }

        public void setAggregation(List<AggregationBean> aggregation) {
            this.aggregation = aggregation;
        }

        public List<InfoBean> getInfo() {
            return info;
        }

        public void setInfo(List<InfoBean> info) {
            this.info = info;
        }

        public static class AggregationBean {
            /**
             * count : 0
             * key : DJ
             */

            private int count;
            private String key;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }
        }

        public static class InfoBean {
            /**
             * srctype : 1
             * bitrate : 128
             * source :
             * rp_type : audio
             * songname_original : 喜欢你
             * audio_id : 4195715
             * othername :
             * price : 200
             * mvhash : a779f7c2ce941a0185acadfc1f56d9a4
             * feetype : 0
             * extname : mp3
             * pay_type_sq : 3
             * group : []
             * rp_publish : 1
             * fold_type : 0
             * othername_original :
             * songname : 喜欢你
             * pkg_price_320 : 1
             * sqprivilege : 0
             * sqfilesize : 28531394
             * filename : G.E.M.邓紫棋 - 喜欢你
             * m4afilesize : 982252
             * topic :
             * pkg_price : 1
             * album_id : 557512
             * 320hash : f4b407102569f8e5528cfd97eecc388b
             * pkg_price_sq : 1
             * hash : 41c2e4ab5660eae04021c5893e055f50
             * singername : G.E.M.邓紫棋
             * fail_process_320 : 4
             * fail_process_sq : 4
             * fail_process : 4
             * sqhash : 934e2cd9b9acd9a4460cb4d66269ff4d
             * filesize : 3832784
             * privilege : 0
             * isnew : 0
             * price_sq : 200
             * duration : 239
             * ownercount : 65294
             * pay_type_320 : 3
             * trans_param : {"musicpack_advance":0,"pay_block_tpl":1,"display":0,"display_rate":0,"cid":23079120}
             * album_name : 喜欢你
             * old_cpy : 1
             * album_audio_id : 28139826
             * pay_type : 3
             * 320filesize : 9570494
             * Accompany : 1
             * sourceid : 0
             * 320privilege : 0
             * isoriginal : 0
             * topic_url :
             * price_320 : 200
             */

            private int srctype;
            private int bitrate;
            private String source;
            private String rp_type;
            private String songname_original;
            private int audio_id;
            private String othername;
            private int price;
            private String mvhash;
            private int feetype;
            private String extname;
            private int pay_type_sq;
            private int rp_publish;
            private int fold_type;
            private String othername_original;
            private String songname;
            private int pkg_price_320;
            private int sqprivilege;
            private int sqfilesize;
            private String filename;
            private int m4afilesize;
            private String topic;
            private int pkg_price;
            private String album_id;

            private int pkg_price_sq;
            private String hash;
            private String singername;
            private int fail_process_320;
            private int fail_process_sq;
            private int fail_process;
            private String sqhash;
            private int filesize;
            private int privilege;
            private int isnew;
            private int price_sq;
            private int duration;
            private int ownercount;
            private int pay_type_320;
            private TransParamBean trans_param;
            private String album_name;
            private int old_cpy;
            private int album_audio_id;
            private int pay_type;

            private int Accompany;
            private int sourceid;

            private int isoriginal;
            private String topic_url;
            private int price_320;
            private List<?> group;

            public int getSrctype() {
                return srctype;
            }

            public void setSrctype(int srctype) {
                this.srctype = srctype;
            }

            public int getBitrate() {
                return bitrate;
            }

            public void setBitrate(int bitrate) {
                this.bitrate = bitrate;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public String getRp_type() {
                return rp_type;
            }

            public void setRp_type(String rp_type) {
                this.rp_type = rp_type;
            }

            public String getSongname_original() {
                return songname_original;
            }

            public void setSongname_original(String songname_original) {
                this.songname_original = songname_original;
            }

            public int getAudio_id() {
                return audio_id;
            }

            public void setAudio_id(int audio_id) {
                this.audio_id = audio_id;
            }

            public String getOthername() {
                return othername;
            }

            public void setOthername(String othername) {
                this.othername = othername;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public String getMvhash() {
                return mvhash;
            }

            public void setMvhash(String mvhash) {
                this.mvhash = mvhash;
            }

            public int getFeetype() {
                return feetype;
            }

            public void setFeetype(int feetype) {
                this.feetype = feetype;
            }

            public String getExtname() {
                return extname;
            }

            public void setExtname(String extname) {
                this.extname = extname;
            }

            public int getPay_type_sq() {
                return pay_type_sq;
            }

            public void setPay_type_sq(int pay_type_sq) {
                this.pay_type_sq = pay_type_sq;
            }

            public int getRp_publish() {
                return rp_publish;
            }

            public void setRp_publish(int rp_publish) {
                this.rp_publish = rp_publish;
            }

            public int getFold_type() {
                return fold_type;
            }

            public void setFold_type(int fold_type) {
                this.fold_type = fold_type;
            }

            public String getOthername_original() {
                return othername_original;
            }

            public void setOthername_original(String othername_original) {
                this.othername_original = othername_original;
            }

            public String getSongname() {
                return songname;
            }

            public void setSongname(String songname) {
                this.songname = songname;
            }

            public int getPkg_price_320() {
                return pkg_price_320;
            }

            public void setPkg_price_320(int pkg_price_320) {
                this.pkg_price_320 = pkg_price_320;
            }

            public int getSqprivilege() {
                return sqprivilege;
            }

            public void setSqprivilege(int sqprivilege) {
                this.sqprivilege = sqprivilege;
            }

            public int getSqfilesize() {
                return sqfilesize;
            }

            public void setSqfilesize(int sqfilesize) {
                this.sqfilesize = sqfilesize;
            }

            public String getFilename() {
                return filename;
            }

            public void setFilename(String filename) {
                this.filename = filename;
            }

            public int getM4afilesize() {
                return m4afilesize;
            }

            public void setM4afilesize(int m4afilesize) {
                this.m4afilesize = m4afilesize;
            }

            public String getTopic() {
                return topic;
            }

            public void setTopic(String topic) {
                this.topic = topic;
            }

            public int getPkg_price() {
                return pkg_price;
            }

            public void setPkg_price(int pkg_price) {
                this.pkg_price = pkg_price;
            }

            public String getAlbum_id() {
                return album_id;
            }

            public void setAlbum_id(String album_id) {
                this.album_id = album_id;
            }



            public int getPkg_price_sq() {
                return pkg_price_sq;
            }

            public void setPkg_price_sq(int pkg_price_sq) {
                this.pkg_price_sq = pkg_price_sq;
            }

            public String getHash() {
                return hash;
            }

            public void setHash(String hash) {
                this.hash = hash;
            }

            public String getSingername() {
                return singername;
            }

            public void setSingername(String singername) {
                this.singername = singername;
            }

            public int getFail_process_320() {
                return fail_process_320;
            }

            public void setFail_process_320(int fail_process_320) {
                this.fail_process_320 = fail_process_320;
            }

            public int getFail_process_sq() {
                return fail_process_sq;
            }

            public void setFail_process_sq(int fail_process_sq) {
                this.fail_process_sq = fail_process_sq;
            }

            public int getFail_process() {
                return fail_process;
            }

            public void setFail_process(int fail_process) {
                this.fail_process = fail_process;
            }

            public String getSqhash() {
                return sqhash;
            }

            public void setSqhash(String sqhash) {
                this.sqhash = sqhash;
            }

            public int getFilesize() {
                return filesize;
            }

            public void setFilesize(int filesize) {
                this.filesize = filesize;
            }

            public int getPrivilege() {
                return privilege;
            }

            public void setPrivilege(int privilege) {
                this.privilege = privilege;
            }

            public int getIsnew() {
                return isnew;
            }

            public void setIsnew(int isnew) {
                this.isnew = isnew;
            }

            public int getPrice_sq() {
                return price_sq;
            }

            public void setPrice_sq(int price_sq) {
                this.price_sq = price_sq;
            }

            public int getDuration() {
                return duration;
            }

            public void setDuration(int duration) {
                this.duration = duration;
            }

            public int getOwnercount() {
                return ownercount;
            }

            public void setOwnercount(int ownercount) {
                this.ownercount = ownercount;
            }

            public int getPay_type_320() {
                return pay_type_320;
            }

            public void setPay_type_320(int pay_type_320) {
                this.pay_type_320 = pay_type_320;
            }

            public TransParamBean getTrans_param() {
                return trans_param;
            }

            public void setTrans_param(TransParamBean trans_param) {
                this.trans_param = trans_param;
            }

            public String getAlbum_name() {
                return album_name;
            }

            public void setAlbum_name(String album_name) {
                this.album_name = album_name;
            }

            public int getOld_cpy() {
                return old_cpy;
            }

            public void setOld_cpy(int old_cpy) {
                this.old_cpy = old_cpy;
            }

            public int getAlbum_audio_id() {
                return album_audio_id;
            }

            public void setAlbum_audio_id(int album_audio_id) {
                this.album_audio_id = album_audio_id;
            }

            public int getPay_type() {
                return pay_type;
            }

            public void setPay_type(int pay_type) {
                this.pay_type = pay_type;
            }



            public int getAccompany() {
                return Accompany;
            }

            public void setAccompany(int Accompany) {
                this.Accompany = Accompany;
            }

            public int getSourceid() {
                return sourceid;
            }

            public void setSourceid(int sourceid) {
                this.sourceid = sourceid;
            }


            public int getIsoriginal() {
                return isoriginal;
            }

            public void setIsoriginal(int isoriginal) {
                this.isoriginal = isoriginal;
            }

            public String getTopic_url() {
                return topic_url;
            }

            public void setTopic_url(String topic_url) {
                this.topic_url = topic_url;
            }

            public int getPrice_320() {
                return price_320;
            }

            public void setPrice_320(int price_320) {
                this.price_320 = price_320;
            }

            public List<?> getGroup() {
                return group;
            }

            public void setGroup(List<?> group) {
                this.group = group;
            }

            public static class TransParamBean {
                /**
                 * musicpack_advance : 0
                 * pay_block_tpl : 1
                 * display : 0
                 * display_rate : 0
                 * cid : 23079120
                 */

                private int musicpack_advance;
                private int pay_block_tpl;
                private int display;
                private int display_rate;
                private int cid;

                public int getMusicpack_advance() {
                    return musicpack_advance;
                }

                public void setMusicpack_advance(int musicpack_advance) {
                    this.musicpack_advance = musicpack_advance;
                }

                public int getPay_block_tpl() {
                    return pay_block_tpl;
                }

                public void setPay_block_tpl(int pay_block_tpl) {
                    this.pay_block_tpl = pay_block_tpl;
                }

                public int getDisplay() {
                    return display;
                }

                public void setDisplay(int display) {
                    this.display = display;
                }

                public int getDisplay_rate() {
                    return display_rate;
                }

                public void setDisplay_rate(int display_rate) {
                    this.display_rate = display_rate;
                }

                public int getCid() {
                    return cid;
                }

                public void setCid(int cid) {
                    this.cid = cid;
                }
            }
        }
    }
}
