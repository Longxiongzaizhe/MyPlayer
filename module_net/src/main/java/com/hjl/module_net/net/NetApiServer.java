package com.hjl.module_net.net;

import com.hjl.module_net.net.vo.AllSingerVo;
import com.hjl.module_net.net.vo.AssociativeWordVo;
import com.hjl.module_net.net.vo.HotSearchVo;
import com.hjl.module_main.net.bean.MusicDetailVo;
import com.hjl.module_net.net.vo.SearchVo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * created by long on 2019/10/23
 */
public interface NetApiServer {

    /**
     * 搜索
     */
    // showtype=14&keyword={keyword}&pagesize={pagesize}&page={page}&highlight=em&tag_aggr=1&tagtype=全部
    // &plat=0&sver=5&correct=1&api_ver=1&version=9108&area_code=1&tag=1&with_res_tag=1
    @GET("http://msearchcdn.kugou.com/api/v3/search/song")
    Observable<SearchVo> searchSong(@Query("showtype") String showtype, @Query("keyword") String keyword, @Query("pagesize") String pagesize,
                                    @Query("page") String page, @Query("highlight") String highlight, @Query("tag_aggr") String tag_aggr,
                                    @Query("tagtype") String tagtype, @Query("plat") String plat, @Query("sver") String sver,
                                    @Query("correct") String correct, @Query("api_ver") String api_ver, @Query("version") String version,
                                    @Query("area_code") String area_code, @Query("tag") String tag, @Query("with_res_tag") String with_res_tag);

    /**
     * 获取热搜
     */
    @GET("http://msearchcdn.kugou.com/api/v3/search/hot?count=20&plat=0&with_res_tag=1")
    Observable<HotSearchVo> getHotSearch();

    /**
     * 获取联想词
     */
    @GET("http://msearchcdn.kugou.com/new/app/i/search.php")
    Observable<AssociativeWordVo> getAssociativeWord(@Query("student")String student, @Query("cmd") String cmd,
                                                     @Query("keyword")String keyword, @Query("with_res_tag") String with_res_tag);
    //student=0&cmd=302&keyword={keyword}&with_res_tag=1

    /**
     * 获取音乐详情（播放、歌词）
     * http://www.kugou.com/yy/index.php?r=play/getdata&hash=CB7EE97F4CC11C4EA7A1FA4B516A5D97
     */
    @Headers("Cookie:kg_mid=2333")
    @GET("http://www.kugou.com/yy/index.php")
    Observable<MusicDetailVo> getMusicDetail(@Query("r") String r,@Query("hash") String hash);


    /**
     * 获取所有歌手
     * sextyoe 0 所有 1 男性 2 女性
     * ?version=9108&showtype=1&plat=0&sextype=0&sort=1&pagesize=100&type=0&page=1
     */

    @GET("http://mobilecdnbj.kugou.com/api/v5/singer/list")
    Observable<AllSingerVo> getAllSinger(@Query("version")String version,@Query("showtype") String showtype,
                                         @Query("plat")String plat,@Query("sextype") String sextype,
                                         @Query("sort")String sort,@Query("pagesize") String pagesize,
                                         @Query("type")String type,@Query("page") String page);

}
