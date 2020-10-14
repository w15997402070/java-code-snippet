package com.demo.leetcode.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author wang
 * @since 2020/10/14
 */
public class Demo1002 {

    public static void main(String[] args) {

        args = new String [] {"bella","label","roller"};
        System.out.println(commonChars(args));
    }

    /**
     * 输入：["bella","label","roller"]
     * 输出：["e","l","l"]
     * @param args 参数
     * @return list
     */
    public static List<String> commonChars(String[] args) {
        if (args.length == 0){
            return new ArrayList<>();
        }
        if (args.length == 1) {
            char[] chars = args[0].toCharArray();
            ArrayList<String> list = new ArrayList<>();
            for (char aChar : chars) {
                list.add(String.valueOf(aChar));
            }
            return list;
        }

        Map<Character,Integer> baseMap = new HashMap<>();

        Set<Character> baseSet = new HashSet<>();
        for (int i = 0; i < args.length ; i++) {
            String arg = args[i];
            char[] chars = arg.toCharArray();
            if (i == 0) {
                for (char aChar : chars) {
                    if (baseMap.containsKey(aChar)){
                        baseMap.put(aChar,baseMap.get(aChar)+1);
                    }else {
                        baseMap.put(aChar,1);
                    }
                }
                baseSet = baseMap.keySet();
            } else {
                Map<Character,Integer> comMap = new HashMap<>();
                for (char aChar : chars) {
                    if (comMap.containsKey(aChar)){
                        comMap.put(aChar,comMap.get(aChar)+1);
                    }else {
                        comMap.put(aChar,1);
                    }
                }
                Set<Character> characters = comMap.keySet();
                // 注意这里会删除map中的key
                baseSet.removeIf(character -> !characters.contains(character));
                for (Character character : characters) {
                    if (baseSet.contains(character)) {
                        Integer comCount = comMap.get(character);
                        Integer baseCount = baseMap.get(character);
                        if (comCount <= baseCount) {
                            baseMap.put(character,comCount);
                        }else{
                            baseMap.put(character,baseCount);
                        }
                    }
                }

            }
        }
        List<String> list = new ArrayList<>();
        baseMap.forEach((k,v) -> {
            for (int i = 0; i < v ; i++) {
                list.add(String.valueOf(k));
            }
        });
        return list;
    }
}
