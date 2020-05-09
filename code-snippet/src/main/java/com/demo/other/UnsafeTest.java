package com.demo.other;

import sun.misc.Unsafe;

public class UnsafeTest {

    private static Unsafe theUnsafe = null;

    public static void main(String[] args) {
        theUnsafe = Unsafe.getUnsafe();
    }
}
