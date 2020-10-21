package com.demo.leetcode.demo;

/**
 * 测试类.
 *
 * @author wang
 * @since 2020/10/15
 */
public class Demo9 {

    /**
     * main.
     *
     * @param args 参数
     */
    public static void main(String[] args) {
        System.out.println(isPalindrome(1));
    }

    /**
     * 1221
     * 回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数
     * 判断是否是回文数.
     *
     * @param x 参数
     * @return boolean
     */
    public static boolean isPalindrome(int x) {
        String str = String.valueOf(x);
        char[] chars = str.toCharArray();
        for (int i = 0, j = chars.length - 1; i < j; i++, j--) {
            if (chars[i] != chars[j]) {
                return false;
            }
        }
        return true;
    }
}
