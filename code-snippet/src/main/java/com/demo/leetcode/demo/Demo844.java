package com.demo.leetcode.demo;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 844. 比较含退格的字符串.
 * @author wang
 * @since 2020/10/19
 */
public class Demo844 {
    public static void main(    String[] args) {

        backspaceCompare("bxj##tw","bxj###tw");

    }

    public static boolean backspaceCompare(String S, String T) {
        String pattern = ".#";
        Pattern compile = Pattern.compile(pattern);
        Matcher matcher = compile.matcher(S);
        while (matcher.find()) {
            S = S.replaceFirst(pattern,"");
            matcher = compile.matcher(S);
        }
        Matcher matcher2 = compile.matcher(T);
        while (matcher2.find()) {
            T = T.replaceFirst(pattern, "");
            matcher2 = compile.matcher(T);
        }
        S = S.replaceAll("#", "");
        T = T.replaceAll("#", "");
        System.out.println(S);
        System.out.println(T);
        return S.equals(T);
    }
}
