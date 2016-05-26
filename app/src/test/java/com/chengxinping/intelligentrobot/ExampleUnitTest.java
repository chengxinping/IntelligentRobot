package com.chengxinping.intelligentrobot;

import android.test.AndroidTestCase;
import android.util.Log;

import com.chengxinping.intelligentrobot.bean.ChatMessage;
import com.chengxinping.intelligentrobot.utils.HttpUtils;

import org.junit.Test;

import java.net.URL;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        // assertEquals(4, 2 + 2);

        ChatMessage chatMessage = HttpUtils.sendMessage("小狗的图片");
        URL url = chatMessage.getUrl();
        String s = url+"";
        System.out.println(s);

    }
}