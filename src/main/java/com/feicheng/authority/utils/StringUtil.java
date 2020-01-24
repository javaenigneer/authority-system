package com.feicheng.authority.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 字符串处理工具
 *
 * @author Lenovo
 */
public class StringUtil {

    /**
     * 将字符串转成List<Long>型
     *
     * @param string
     * @return
     */
    public static List<Long> stingConvertLong(String string) {

        List<Long> longs = new ArrayList<>();

        String[] split = string.split(",");

        List<String> strings = Arrays.asList(split);

        for (String str : strings
        ) {
            longs.add(Long.valueOf(str));
        }
        return longs;
    }
}
