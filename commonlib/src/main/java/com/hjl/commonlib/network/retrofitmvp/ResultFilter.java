package com.hjl.commonlib.network.retrofitmvp;


import io.reactivex.functions.Function;

/**
 * created by long on 2019/12/17
 */
public class ResultFilter<T> implements Function<HttpBaseResult<T>, T> {

    @Override
    public T apply(HttpBaseResult<T> tHttpBaseResult) throws Exception {
        if (tHttpBaseResult.errorCode != 200) throw new HttpRequestException(tHttpBaseResult.message);

        return tHttpBaseResult.result;
    }
}
