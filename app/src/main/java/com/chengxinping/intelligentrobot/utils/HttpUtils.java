package com.chengxinping.intelligentrobot.utils;

import com.chengxinping.intelligentrobot.bean.ChatMessage;
import com.chengxinping.intelligentrobot.bean.Result;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

/**
 * Created by 平瓶平瓶子 on 2016/5/26.
 */
public class HttpUtils {
    private static final String URL = "http://www.tuling123.com/openapi/api";
    private static final String APIKEY = "ca6b8a84bd0780d59174b18adafa78f0";

    /**
     * 发送一个消息，得到一个返回的消息
     *
     * @param msg
     * @return
     */
    public static ChatMessage sendMessage(String msg) {
        ChatMessage chatMessage = new ChatMessage();
        String jsonRes = doPost(msg);
        Gson gson = new Gson();
        try {
            Result result = gson.fromJson(jsonRes, Result.class);
            if (result.getCode() == 100000) {
                chatMessage.setMsg(result.getText());
            }
            if (result.getCode() == 200000) {
                String string = result.getUrl().toString();
                chatMessage.setMsg(result.getText() + "\r\n" + string);
            }
            if (result.getCode() == 302000) {
                String s = "你想看新闻了吗？反正就是不告你今天的新闻！";
                chatMessage.setMsg(s);
            }
            if (result.getCode() == 308000) {
                chatMessage.setMsg("你要学做菜啊？好厉害啊！");
            }
        } catch (Exception e) {
            chatMessage.setMsg("你烦死了，我不想理你了");
        }
        chatMessage.setDate(new Date());
        chatMessage.setType(ChatMessage.Type.INCOMING);
        return chatMessage;
    }

    public static String doPost(String msg) {
        String result = "";
        String url = setParmas(msg);
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            java.net.URL urlNet = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlNet.openConnection();
            connection.setReadTimeout(5 * 1000);
            connection.setConnectTimeout(5 * 1000);
            connection.setRequestMethod("POST");
            is = connection.getInputStream();
            int len = -1;
            byte[] buf = new byte[128];
            baos = new ByteArrayOutputStream();
            while ((len = is.read(buf)) != -1) {
                baos.write(buf, 0, len);
            }
            baos.flush();
            result = new String(baos.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }

    private static String setParmas(String msg) {
        String url = null;
        try {
            url = URL + "?key=" + APIKEY + "&info=" + URLEncoder.encode(msg, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }
}
