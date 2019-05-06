package wj.com.myplayer.Utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class JsonUtils {

    public static Object toJsonObject(String json){
        return JSON.parseObject(json);
    }

}
