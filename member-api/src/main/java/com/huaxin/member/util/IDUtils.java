package com.huaxin.member.util;

import java.util.Random;

public class IDUtils {

    /**
     * 生成随机图片名
     */
    public static String genImageName() {
        //取当前时间的长整形值包含毫秒
        long millis = System.currentTimeMillis();
        //long millis = System.nanoTime();
        //加上三位随机数
        Random random = new Random();
        int end3 = random.nextInt(999);
        //如果不足三位前面补0
        String str = millis + String.format("%03d", end3);

        return str;
    }

    /**
     * 生成随机图片名
     */
    public static String genImageName(boolean isAppendRadom) {
        //取当前时间的长整形值包含毫秒
        long millis = System.currentTimeMillis();
        if (!isAppendRadom) {
            return String.valueOf(millis);
        }
        //long millis = System.nanoTime();
        //加上三位随机数
        Random random = new Random();
        int end3 = random.nextInt(999);
        //如果不足三位前面补0
        String str = millis + String.format("%03d", end3);

        return str;
    }
}
