package wj.com.myplayer.Utils;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class JsonUtils {

    private static final String TAG = "JsonError";

    public static Object toJsonObject(String json){
        return JSON.parseObject(json);
    }

    public static <T> T toJsonBean(String json,Class<T> cls){
        if (!json.startsWith("{") && !json.startsWith("[")) {
            return (T) json;
        }
        try {
            T result = JSON.parseObject(json, cls);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Json解析错误. " + e.toString());
            Log.e(TAG, "json =  " + json);
        }
        return null;
    }

}
