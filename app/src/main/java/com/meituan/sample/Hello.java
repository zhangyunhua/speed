package com.meituan.sample;

/**
 * Created by zhangyunhua on 17/8/22.
 */

public class Hello {

    String abc1 = "abc";

    public static void hello(String abc) {
        int i = 0;
        i++;
        String abc1 = "def";
        if (!abc1.contains("hello")) {
            i = 5;
            try {
                Thread.sleep(350);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void hello() {
        hello("abc");
    }

}
