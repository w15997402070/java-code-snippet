package com.demo.leetcode.demo;

import java.util.HashMap;
import java.util.Map;

/**
 * 13. 罗马数字转整数
 * @author wang
 * @since 2020/10/18
 */
public class Demo13 {


    public static void main(String[] args) {


        System.out.println(romanToInt("IX"));
    }

    /**
     * I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
     * X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。 
     * C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900.
     *
     * @param s 参数
     * @return int
     */
    public static int romanToInt(String s) {
        Map<String, Integer> map = new HashMap<>(7);
        map.put("I", 1);
        map.put("V", 5);
        map.put("X", 10);
        map.put("L", 50);
        map.put("C", 100);
        map.put("D", 500);
        map.put("M", 1000);
        Map<String, Integer> map2 = new HashMap<>(7);
        map2.put("IV", 4);
        map2.put("IX", 9);
        map2.put("XL", 40);
        map2.put("XC", 90);
        map2.put("CD", 400);
        map2.put("CM", 900);

        switch (s) {
            case "IV": return 4;
            case "IX": return 9;
            case "XL": return 40;
            case "XC": return 90;
            case "CD": return 400;
            case "CM": return 900;
            default:
                char[] chars = s.toCharArray();
                int result = 0;
                int length = chars.length;
                for (int i = 0; i < length; i++) {
                    if (i + 1 < chars.length) {
                        int k = i + 1;
                        String key = chars[i] + String.valueOf(chars[k]);
                        if (map2.get(key) != null) {
                            result += map2.get(key);
                            i++;
                        } else {
                            result += map.get(String.valueOf(chars[i]));
                        }
                    } else {
                        result += map.get(String.valueOf(chars[i]));
                    }
                }
                return result;
        }
    }
}
