package com.chengxinping.intelligentrobot.bean;

import java.net.URL;

/**
 * Created by 平瓶平瓶子 on 2016/5/26.
 */
public class Result {
    private int code;
    private String text;
    private URL url;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }
}
