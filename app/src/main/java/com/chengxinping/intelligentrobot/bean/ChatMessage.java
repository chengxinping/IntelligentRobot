package com.chengxinping.intelligentrobot.bean;


import java.net.URL;
import java.util.Date;

/**
 * Created by 平瓶平瓶子 on 2016/5/26.
 */
public class ChatMessage {
    private String name;
    private String msg;
    private Type type;
    private Date date;
    private URL url;

    public enum Type {
        INCOMING, OUTCOMING
    }

    public ChatMessage(Date date, Type type, String msg) {
        this.date = date;
        this.type = type;
        this.msg = msg;
    }

    public ChatMessage() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
