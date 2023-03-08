package com.zyplayer.doc.data.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 创建一个线程类来不停地来读出Process调用脚本的输出数据，防止缓冲区被缓冲数据塞满而线程阻塞
 * @author diantu
 * @since 2023年3月3日
 */
@Slf4j
public class CleanInputCache extends Thread{

    private final String type;
    private final String charSet;
    private final StringBuilder msg;
    private final InputStream inputStream;

    public CleanInputCache(InputStream inputStream, String type, String charSet, StringBuilder msg) {
        this.type = type;
        this.msg = msg;
        this.charSet = charSet;
        this.inputStream = inputStream;
    }

    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, charSet));
            String line = null;
            while ((line = reader.readLine()) != null) {
                if ("error".equals(type)) {
                    msg.append(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
