package com.demo.util;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Random;

/**
 * @author wang
 */
@Slf4j
public class RandomUtil {

    public static void main(String[] args) {
//        testRandom();
//        testRandomSeed();
//        randomPassword(6);
//        transfer();
        randomPassword();
    }

    /**
     *  Math.random() 生成 [0,1) 生成类型为double
     * 包含0但不包含1
     */
    public static void testRandom(){
        for (int i = 0; i < 5 ; i++) {
            log.info(Math.random()+"");
        }
    }

    /**
     * Random random = new Random(long seed)
     * 构造函数传入种子参数,种子参数相同生成的随机数就相同,无论在哪里,什么时候运行结果都相同
     * 可以生成可控制的随机数
     */
    public static void testRandomSeed(){
        Random random = new Random(20160824);
        for (int i = 0; i < 5 ; i++) {
            log.info(random.nextInt(100)+"");//69 13 13 94 50
        }
    }

    /**
     * 生成6位随机密码
     * @param length
     */
    public static void randomPassword(int length){
        char [] chars = new char[length];
        Random random = new Random();
        for (int i = 0; i < length ; i++) {
            chars[i] = (char)('0'+random.nextInt(10));
        }
        log.info(new String(chars));
    }

    /**
     * 生成8位随机密码
     */
    public static void randomPassword(){
        char [] chars = new char[8];
        Random random = new Random();
        for (int i = 0; i < 8 ; i++) {
            chars[i] = nextChar(random);
        }
        log.info(new String(chars));
    }

    private static char nextChar(Random random){
        final String SPECIAL_CHARS = "!@#$%^&*_=+-/";
        switch (random.nextInt(4)){
            case 0 :
                return (char)('a'+random.nextInt(26));
            case 1 :
                return (char)('A'+random.nextInt(26));
            case 2 :
                return (char)('0'+random.nextInt(26));
            default:
                return SPECIAL_CHARS.charAt(random.nextInt(SPECIAL_CHARS.length()));
        }
    }

    /**
     * char 和 int 转化
     */
    public static void transfer(){
        char a = '0'+9;//运算过程 '0' == 48 -> 48+9 = 57(int) -> 57(int) == 9(char)
        int c = '0'+9;
        char i = 57;
        char b = (char) i;
        log.info(a+" char");//9
        log.info(c+" int ");// 57
        log.info(i+" int-char "); // 9
        log.info(('0'+9)+"");
    }
}
