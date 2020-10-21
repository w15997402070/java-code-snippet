package com.demo.leetcode.demo;


/**
 * 14. 最长公共前缀.
 * @author wang
 * @since 2020/10/19
 */
public class Demo14 {
    public static void main(String[] args) {
        String[] strings = {"dog","racecar","car"};

        System.out.println(longestCommonPrefix(strings));
    }

    public static String longestCommonPrefix(String[] strs) {

        if (strs.length == 0) {
            return "";
        }
        if (strs.length == 1) {
            return strs[0];
        }
        String result = "";
        for (int i = 1; i < strs.length; i++) {
            String str = strs[i];
            if (i == 1) {
                String str0 = strs[0];
                result = getLong(str0, str);
            } else {
                result = getLong(result, str);
            }
            if (result.length() == 0){
                return result;
            }
        }
        return result;
    }

    public static String getLong(String str1, String str2) {
        char[] chars = str1.toCharArray();
        char[] chars1 = str2.toCharArray();
        int length = Math.min(chars.length, chars1.length);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length ; i++) {
            if (chars[i] == chars1[i]) {
                sb.append(chars[i]);
            } else {
                return sb.toString();
            }
        }
        return sb.toString();
    }
}
