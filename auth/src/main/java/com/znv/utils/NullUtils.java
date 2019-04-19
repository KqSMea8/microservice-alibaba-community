package com.znv.utils;

public class NullUtils {

    /**
     * 是否全部为空
     * @param objs
     * @return
     */
    public static boolean allNull(Object... objs) {
        if (objs == null)
            return true;

        for (Object obj :
                objs) {
            if (obj != null) {
                return false;
            }
        }
        return true;
    }
}
