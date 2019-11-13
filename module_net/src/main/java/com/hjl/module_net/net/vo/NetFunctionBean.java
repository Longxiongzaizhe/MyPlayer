package com.hjl.module_net.net.vo;

import android.support.annotation.DrawableRes;

/**
 * created by long on 2019/11/11
 */
public class NetFunctionBean {

    private String name;

    @DrawableRes
    private int Cover;

    public NetFunctionBean(String name, int cover) {
        this.name = name;
        Cover = cover;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCover() {
        return Cover;
    }

    public void setCover(int cover) {
        Cover = cover;
    }
}
