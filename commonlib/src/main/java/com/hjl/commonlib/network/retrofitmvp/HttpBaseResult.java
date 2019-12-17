package com.hjl.commonlib.network.retrofitmvp;

/**
 * created by long on 2019/12/17
 */
public class HttpBaseResult<T>  {

    private boolean state;
    public int errorCode;
    private String flag;
    public String message;

    public T result;
}
