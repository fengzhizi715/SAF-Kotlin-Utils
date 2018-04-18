package com.safframework.ext.utils;

/**
 * @version V1.0 <描述当前版本功能>
 * @FileName: com.safframework.ext.utils.Utils.java
 * @author: Tony Shen
 * @date: 2018-04-18 22:51
 */
public class Utils {

    private static final int MIN_DELAY_TIME = 1000;  // 两次点击间隔不能少于1000ms
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= MIN_DELAY_TIME) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        return flag;
    }
}
