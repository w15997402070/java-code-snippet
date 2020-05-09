package com.demo.java8.lambda;

import java.util.UUID;
import java.util.function.Supplier;

public class SupplierTest {
    public static void main(String[] args) {

        Supplier<String> uuidSupplier = () -> {
            return UUID.randomUUID().toString();
        };

        System.out.println(uuidSupplier.get());
    }
}
