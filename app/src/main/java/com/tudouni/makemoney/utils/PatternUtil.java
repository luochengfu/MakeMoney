package com.tudouni.makemoney.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/4/8 0008.
 */

public class PatternUtil {

    public static boolean matchClipStr(String input) {
        String match = "[[A-Za-z]*[0-9]*]|([0-9a-zA-Z]*)$";
        //创建pattern对象
        Pattern pattern = Pattern.compile(match);
        //创建Matcher对象
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public static boolean matchURL(String input) {
        String match = "(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]";
        //创建pattern对象
        Pattern pattern = Pattern.compile(match);
        //创建Matcher对象
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
}
