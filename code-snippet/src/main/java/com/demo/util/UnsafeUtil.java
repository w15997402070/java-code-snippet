package com.demo.util;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

public class UnsafeUtil {

    private static final Unsafe UNSAFE_INSTANCE;

    static {
        try {
            final PrivilegedExceptionAction<Unsafe> action = () -> {
                Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
                theUnsafe.setAccessible(true);
                return (Unsafe)theUnsafe.get(null);
            };
            UNSAFE_INSTANCE = AccessController.doPrivileged(action);
        } catch (Exception e) {
            throw new RuntimeException("Unable to load unsafe", e);
        }
    }

    private static Unsafe getUnsafeInstance(){
        return UNSAFE_INSTANCE;
    }

    public static void main(String[] args) {
        Unsafe unsafeInstance = UnsafeUtil.getUnsafeInstance();

        int i1 = unsafeInstance.arrayBaseOffset(Object[].class);
        System.out.println(i1);

        int i = unsafeInstance.arrayIndexScale(Object[].class);
        System.out.println(i);


        //  返回4或8,代表是32位还是64位操作系统。
        int i2 = unsafeInstance.addressSize();
        System.out.println(i2);
        // 返回32或64,获取操作系统是32位还是64位
        System.out.println(System.getProperty("sun.arch.data.model"));
    }
}
