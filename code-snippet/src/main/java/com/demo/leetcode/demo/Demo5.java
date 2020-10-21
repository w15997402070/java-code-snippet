package com.demo.leetcode.demo;

/**
 * 最长回文子串.
 * @see Demo9
 * @author wang
 * @since 2020/10/16
 */
public class Demo5 {
    public static void main(String[] args) {

        System.out.println(longestPalindrome("ac"));
    }

    /**
     * 这样执行时间超长
     * 从一个字符开始遍历,依次查找最长串.
     * @param s 参数
     * @return string
     */
    public static String longestPalindrome(String s) {
        if (s.length() <= 1) {
            return s;
        }
        String result = "";
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char firstChar = chars[i];
            if (i == 0) {
                result = String.valueOf(firstChar);
            }
            StringBuilder sb = new StringBuilder();
            sb.append(firstChar);
            // 如果最长的已经大于后面的长度就不用继续了
            if (result.length() > chars.length - i) {
                return result;
            }
            for (int j = i + 1; j < chars.length; j++) {
                char secChar = chars[j];
                sb.append(secChar);
                boolean palindrome = isPalindrome(sb.toString());
                if (palindrome) {
                    if (result.length() < sb.length()) {
                        result = sb.toString();
                    }
                }
            }
        }
        return result;
    }


    public static String longestPalindrome2(String s) {
        if (s.length() <= 1) {
            return s;
        }
        String result = "";
        char[] chars = s.toCharArray();
        
        return result;
    }

    /**
     * 1221
     * 回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数
     * 判断是否是回文数.
     *
     * @param str 参数
     * @return boolean
     */
    public static boolean isPalindrome(String str) {
        char[] chars = str.toCharArray();
        for (int i = 0, j = chars.length - 1; i < j; i++, j--) {
            if (chars[i] != chars[j]) {
                return false;
            }
        }
        return true;
    }
}
