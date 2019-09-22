package com.hjl.testmodule.retrofit;

public class BaseResponse {


    /**
     * state : false
     * flag :
     * message : 系统异常，操作失败!
     * result :
     * errorCode : 201
     */

    private boolean state;
    private String flag;
    private String message;
    private String result;
    private int errorCode;

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
