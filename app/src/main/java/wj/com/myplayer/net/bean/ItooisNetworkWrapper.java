package wj.com.myplayer.net.bean;

import com.example.commonlib.network.HttpHandler;
import com.example.commonlib.network.HttpUtils;
import com.example.commonlib.network.RequestParams;

import wj.com.myplayer.BuildConfig;
import wj.com.myplayer.net.bean.itoois.KugouSearchBean;

public class ItooisNetworkWrapper {
    /**
     * 酷狗音乐搜索歌曲
     */
    public static void searchMusic(String keyword, String type, int pageSize, int page, HttpHandler<KugouSearchBean> handler){

        String path = getPath("kugou/search");

        RequestParams params = new RequestParams();
        params.add("keyword",keyword);
        params.add("type",type);
        params.add("pageSize",String.valueOf(pageSize));
        params.add("page",String.valueOf(page));

        HttpUtils.get(path, params,handler);



    }



    private static String getPath(String path){
        StringBuffer sb = new StringBuffer(BuildConfig.ITOOIS_URL);
        sb.append(path);
        return sb.toString();
    }

}

