package com.hjl.module_net.net.vo;

import java.util.List;

/**
 * created by long on 2019/10/23
 */
public class MusicDetailVo {

    /**
     * status : 1
     * err_code : 0
     * data : {"hash":"d285a231d05a8c4d430cab5749c5997e","timelength":287477,"filesize":4600248,"audio_name":"薛之谦 - 意外","have_album":1,"album_name":"意外","album_id":"960327","img":"http://imge.kugou.com/stdmusic/20150715/20150715184807521489.jpg","have_mv":1,"video_id":"595974","author_name":"薛之谦","song_name":"意外","lyrics":"[id:$00000000]\r\n[ti:意外 (《如果我爱你》电视剧插曲)]\r\n[ar:薛之谦]\r\n[00:00.50]意外 (《如果我爱你》电视剧插曲) - 薛之谦\r\n[00:03.41]词：杨子朴\r\n[00:04.28]曲：杨子朴\r\n[00:12.65]我在清晨的路上\r\n[00:16.62]谁被我遗忘\r\n[00:24.65]我在深夜里旅行\r\n[00:28.59]谁被我遗忘\r\n[00:35.45]肩上的破旧行囊\r\n[00:41.36]能收藏多少坚强\r\n[00:47.40]不如全身赤裸\r\n[00:51.13]还给我那脆弱\r\n[00:54.52]明知这是一场意外\r\n[00:58.62]你要不要来\r\n[01:06.64]明知这是一场重伤害\r\n[01:10.60]你会不会来\r\n[01:18.21]当疯狂慢慢从爱情离开\r\n[01:24.09]还有什么你值得感慨\r\n[01:28.66]如果风景早已都不存在\r\n[01:33.88]我想我谁都不爱\r\n[01:36.71]都不爱都不爱\r\n[01:39.09]都不爱都不爱\r\n[01:44.44]都不爱\r\n[01:48.49]都不爱都不爱\r\n[01:51.15]都不爱都不爱\r\n[02:03.58]我在清晨的路上\r\n[02:07.64]谁被我遗忘\r\n[02:15.59]我在深夜里旅行\r\n[02:19.61]谁被我遗忘\r\n[02:26.45]肩上的破旧行囊\r\n[02:32.39]能收藏多少坚强\r\n[02:38.57]不如全身赤裸\r\n[02:42.15]还给我那脆弱\r\n[02:45.49]明知这是一场意外\r\n[02:49.68]你要不要来\r\n[02:57.65]明知这是一场重伤害\r\n[03:01.65]你会不会来\r\n[03:09.25]当疯狂慢慢从爱情离开\r\n[03:15.15]还有什么你值得感慨\r\n[03:19.71]如果风景早已都不存在\r\n[03:24.77]我想我谁都不爱\r\n[03:27.53]明知这是一场意外\r\n[03:31.66]你要不要来\r\n[03:39.56]明知这是一场重伤害\r\n[03:43.53]你会不会来\r\n[03:51.26]当疯狂慢慢从爱情离开\r\n[03:57.10]还有什么你值得感慨\r\n[04:01.62]如果风景早已都不存在\r\n[04:06.74]我想我谁都不爱\r\n[04:14.39]谁都不爱\r\n","author_id":"3060","privilege":0,"privilege2":"0","play_url":"https://webfs.yun.kugou.com/201910242202/93e8f6259254e80885cc28fd35de9776/G013/M02/14/10/rYYBAFT9NkmAcPm5AEYxuCed8ko021.mp3","authors":[{"author_id":"3060","is_publish":"1","sizable_avatar":"http://singerimg.kugou.com/uploadpic/softhead/{size}/20190103/20190103191232626.jpg","author_name":"薛之谦","avatar":"http://singerimg.kugou.com/uploadpic/softhead/400/20190103/20190103191232626.jpg"}],"bitrate":128,"audio_id":"3936377","play_backup_url":"https://webfs.cloud.kugou.com/201910242202/592eb149017f456bdb7f8bd5e4973ad6/G013/M02/14/10/rYYBAFT9NkmAcPm5AEYxuCed8ko021.mp3"}
     */

    private int status;
    private int err_code;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getErr_code() {
        return err_code;
    }

    public void setErr_code(int err_code) {
        this.err_code = err_code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * hash : d285a231d05a8c4d430cab5749c5997e
         * timelength : 287477
         * filesize : 4600248
         * audio_name : 薛之谦 - 意外
         * have_album : 1
         * album_name : 意外
         * album_id : 960327
         * img : http://imge.kugou.com/stdmusic/20150715/20150715184807521489.jpg
         * have_mv : 1
         * video_id : 595974
         * author_name : 薛之谦
         * song_name : 意外
         * lyrics : [id:$00000000]
         [ti:意外 (《如果我爱你》电视剧插曲)]
         [ar:薛之谦]
         [00:00.50]意外 (《如果我爱你》电视剧插曲) - 薛之谦
         [00:03.41]词：杨子朴
         [00:04.28]曲：杨子朴
         [00:12.65]我在清晨的路上
         [00:16.62]谁被我遗忘
         [00:24.65]我在深夜里旅行
         [00:28.59]谁被我遗忘
         [00:35.45]肩上的破旧行囊
         [00:41.36]能收藏多少坚强
         [00:47.40]不如全身赤裸
         [00:51.13]还给我那脆弱
         [00:54.52]明知这是一场意外
         [00:58.62]你要不要来
         [01:06.64]明知这是一场重伤害
         [01:10.60]你会不会来
         [01:18.21]当疯狂慢慢从爱情离开
         [01:24.09]还有什么你值得感慨
         [01:28.66]如果风景早已都不存在
         [01:33.88]我想我谁都不爱
         [01:36.71]都不爱都不爱
         [01:39.09]都不爱都不爱
         [01:44.44]都不爱
         [01:48.49]都不爱都不爱
         [01:51.15]都不爱都不爱
         [02:03.58]我在清晨的路上
         [02:07.64]谁被我遗忘
         [02:15.59]我在深夜里旅行
         [02:19.61]谁被我遗忘
         [02:26.45]肩上的破旧行囊
         [02:32.39]能收藏多少坚强
         [02:38.57]不如全身赤裸
         [02:42.15]还给我那脆弱
         [02:45.49]明知这是一场意外
         [02:49.68]你要不要来
         [02:57.65]明知这是一场重伤害
         [03:01.65]你会不会来
         [03:09.25]当疯狂慢慢从爱情离开
         [03:15.15]还有什么你值得感慨
         [03:19.71]如果风景早已都不存在
         [03:24.77]我想我谁都不爱
         [03:27.53]明知这是一场意外
         [03:31.66]你要不要来
         [03:39.56]明知这是一场重伤害
         [03:43.53]你会不会来
         [03:51.26]当疯狂慢慢从爱情离开
         [03:57.10]还有什么你值得感慨
         [04:01.62]如果风景早已都不存在
         [04:06.74]我想我谁都不爱
         [04:14.39]谁都不爱
         * author_id : 3060
         * privilege : 0
         * privilege2 : 0
         * play_url : https://webfs.yun.kugou.com/201910242202/93e8f6259254e80885cc28fd35de9776/G013/M02/14/10/rYYBAFT9NkmAcPm5AEYxuCed8ko021.mp3
         * authors : [{"author_id":"3060","is_publish":"1","sizable_avatar":"http://singerimg.kugou.com/uploadpic/softhead/{size}/20190103/20190103191232626.jpg","author_name":"薛之谦","avatar":"http://singerimg.kugou.com/uploadpic/softhead/400/20190103/20190103191232626.jpg"}]
         * bitrate : 128
         * audio_id : 3936377
         * play_backup_url : https://webfs.cloud.kugou.com/201910242202/592eb149017f456bdb7f8bd5e4973ad6/G013/M02/14/10/rYYBAFT9NkmAcPm5AEYxuCed8ko021.mp3
         */

        private String hash;
        private int timelength;
        private int filesize;
        private String audio_name;
        private int have_album;
        private String album_name;
        private String album_id;
        private String img;
        private int have_mv;
        private String video_id;
        private String author_name;
        private String song_name;
        private String lyrics;
        private String author_id;
        private int privilege;
        private String privilege2;
        private String play_url;
        private int bitrate;
        private String audio_id;
        private String play_backup_url;
        private List<AuthorsBean> authors;

        public String getHash() {
            return hash;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }

        public int getTimelength() {
            return timelength;
        }

        public void setTimelength(int timelength) {
            this.timelength = timelength;
        }

        public int getFilesize() {
            return filesize;
        }

        public void setFilesize(int filesize) {
            this.filesize = filesize;
        }

        public String getAudio_name() {
            return audio_name;
        }

        public void setAudio_name(String audio_name) {
            this.audio_name = audio_name;
        }

        public int getHave_album() {
            return have_album;
        }

        public void setHave_album(int have_album) {
            this.have_album = have_album;
        }

        public String getAlbum_name() {
            return album_name;
        }

        public void setAlbum_name(String album_name) {
            this.album_name = album_name;
        }

        public String getAlbum_id() {
            return album_id;
        }

        public void setAlbum_id(String album_id) {
            this.album_id = album_id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getHave_mv() {
            return have_mv;
        }

        public void setHave_mv(int have_mv) {
            this.have_mv = have_mv;
        }

        public String getVideo_id() {
            return video_id;
        }

        public void setVideo_id(String video_id) {
            this.video_id = video_id;
        }

        public String getAuthor_name() {
            return author_name;
        }

        public void setAuthor_name(String author_name) {
            this.author_name = author_name;
        }

        public String getSong_name() {
            return song_name;
        }

        public void setSong_name(String song_name) {
            this.song_name = song_name;
        }

        public String getLyrics() {
            return lyrics;
        }

        public void setLyrics(String lyrics) {
            this.lyrics = lyrics;
        }

        public String getAuthor_id() {
            return author_id;
        }

        public void setAuthor_id(String author_id) {
            this.author_id = author_id;
        }

        public int getPrivilege() {
            return privilege;
        }

        public void setPrivilege(int privilege) {
            this.privilege = privilege;
        }

        public String getPrivilege2() {
            return privilege2;
        }

        public void setPrivilege2(String privilege2) {
            this.privilege2 = privilege2;
        }

        public String getPlay_url() {
            return play_url;
        }

        public void setPlay_url(String play_url) {
            this.play_url = play_url;
        }

        public int getBitrate() {
            return bitrate;
        }

        public void setBitrate(int bitrate) {
            this.bitrate = bitrate;
        }

        public String getAudio_id() {
            return audio_id;
        }

        public void setAudio_id(String audio_id) {
            this.audio_id = audio_id;
        }

        public String getPlay_backup_url() {
            return play_backup_url;
        }

        public void setPlay_backup_url(String play_backup_url) {
            this.play_backup_url = play_backup_url;
        }

        public List<AuthorsBean> getAuthors() {
            return authors;
        }

        public void setAuthors(List<AuthorsBean> authors) {
            this.authors = authors;
        }

        public static class AuthorsBean {
            /**
             * author_id : 3060
             * is_publish : 1
             * sizable_avatar : http://singerimg.kugou.com/uploadpic/softhead/{size}/20190103/20190103191232626.jpg
             * author_name : 薛之谦
             * avatar : http://singerimg.kugou.com/uploadpic/softhead/400/20190103/20190103191232626.jpg
             */

            private String author_id;
            private String is_publish;
            private String sizable_avatar;
            private String author_name;
            private String avatar;

            public String getAuthor_id() {
                return author_id;
            }

            public void setAuthor_id(String author_id) {
                this.author_id = author_id;
            }

            public String getIs_publish() {
                return is_publish;
            }

            public void setIs_publish(String is_publish) {
                this.is_publish = is_publish;
            }

            public String getSizable_avatar() {
                return sizable_avatar;
            }

            public void setSizable_avatar(String sizable_avatar) {
                this.sizable_avatar = sizable_avatar;
            }

            public String getAuthor_name() {
                return author_name;
            }

            public void setAuthor_name(String author_name) {
                this.author_name = author_name;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }
        }
    }
}
