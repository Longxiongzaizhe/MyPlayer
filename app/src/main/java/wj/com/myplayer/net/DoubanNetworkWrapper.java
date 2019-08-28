package wj.com.myplayer.net;

import com.example.commonlib.network.HttpHandler;
import com.example.commonlib.network.HttpUtils;
import com.example.commonlib.utils.StringUtils;

import wj.com.myplayer.net.bean.douban.MusicSearchBean;

public class DoubanNetworkWrapper {

    public static void searchMusic(String key, String tag, String offset, String count, HttpHandler<MusicSearchBean> httpHandler){
        StringBuilder path = new StringBuilder("https://api.douban.com/v2/music/search?");
        if (!StringUtils.isEmpty(key)){
            path.append("q=");
            path.append(key);
        }
        if (!StringUtils.isEmpty(tag)){
            if (!StringUtils.isEmpty(key)){
                path.append("&");
            }
            path.append("tag=");
            path.append(tag);
        }
        if (!StringUtils.isEmpty(offset)){
            path.append("&offset=");
            path.append(offset);
        }
        if (!StringUtils.isEmpty(tag)){
            path.append("&count=");
            path.append(count);
        }

        HttpUtils.get(path.toString(), httpHandler);
    }
/*
错误代码	错误说明
100	invalid_request_scheme 错误的请求协议
101	invalid_request_method 错误的请求方法
102	access_token_is_missing 未找到access_token
103	invalid_access_token access_token不存在或已被用户删除
104	invalid_apikey apikey不存在或已删除
105	apikey_is_blocked apikey已被禁用
106	access_token_has_expired access_token已过期
107	invalid_request_uri 请求地址未注册
108	invalid_credencial1 用户未授权访问此数据
109	invalid_credencial2 apikey未申请此权限
110	not_trial_user 未注册的测试用户
111	rate_limit_exceeded1 用户访问速度限制
112	rate_limit_exceeded2 IP访问速度限制
113	required_parameter_is_missing 缺少参数
114	unsupported_grant_type 错误的grant_type
115	unsupported_response_type 错误的response_type
116	client_secret_mismatch client_secret不匹配
117	redirect_uri_mismatch redirect_uri不匹配
118	invalid_authorization_code authorization_code不存在或已过期
119	invalid_refresh_token refresh_token不存在或已过期
120	username_password_mismatch 用户名密码不匹配
121	invalid_user 用户不存在或已删除
122	user_has_blocked 用户已被屏蔽
123	access_token_has_expired_since_password_changed 因用户修改密码而导致access_token过期
124	access_token_has_not_expired access_token未过期
125	invalid_request_scope 访问的scope不合法，开发者不用太关注，一般不会出现该错误
999	unknown 未知错误




    HTTP状态码	说明
200	表明api的请求正常
400	表明api的请求出错，具体原因参考上面列出的错误码*/
}
