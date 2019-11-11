package com.hjl.module_main.utils;

import com.hjl.commonlib.utils.StringUtils;
import com.hjl.module_main.net.bean.LyricBean;
import com.hjl.module_main.net.bean.LyricLine;

import java.util.regex.Pattern;

/**
 * created by long on 2019/10/25
 */
public class LyricUtils {

    public static LyricBean parseLyric(String content){
        if (StringUtils.isEmpty(content)) return null;

        String[] parse = content.split("\n");
        LyricBean lyric = new LyricBean();
        String regex = "(^\\[\\d\\d).*$";

        System.out.println(parse.length);
        for (String str : parse){
            if (str.contains("ar")){
                lyric.ar = str;
            }else if (str.contains("ti")){
                lyric.ti = str;
            }else if (Pattern.matches(regex,str.trim())){
                lyric.lyricLineList.add(parseLine(str));
            }

        }

        return lyric;
    }

    // 03:59.26
    private static LyricLine parseLine(String content){
        LyricLine lyricLine = new LyricLine();
        String[] data = content.split("]");
        lyricLine.startTime = parseTime(data[0].replace("[",""));
        lyricLine.lyric = data[1];

        return lyricLine;
    }

    private static long parseTime(String time){
        long result;
        String[] data = time.trim().split(":");

        result = Long.valueOf(data[0]) * 60 * 1000;
        result += Float.valueOf(data[1]) * 1000;
        return result;
    }
}
