package com.demo.java8.stream;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamTest {

    public static void main(String[] args) {
        List<Dish> menu = Arrays.asList(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("beef", false, 700, Dish.Type.MEAT), new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER), new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("season fruit", true, 120, Dish.Type.OTHER), new Dish("pizza", true, 550, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH), new Dish("salmon", false, 450, Dish.Type.FISH));


    }

    public static void test(List<Dish> menu) {
        List<String> names = menu.stream()
                .filter(d -> d.getCalories() > 300)
                .map(Dish::getName)
                .limit(3)
                .collect(Collectors.toList());
//		List<String> names = menu.stream()
//				.filter(d -> d.getCalories() > 300)
//				.map(Dish::getName)
//				.limit(3)
//				.collect(toList());
        System.out.println(names.toString());
    }

    static List<Dish> menuList = Arrays.asList(
            new Dish("pork", false, 800, Dish.Type.MEAT),
            new Dish("beef", false, 700, Dish.Type.MEAT),
            new Dish("chicken", false, 400, Dish.Type.MEAT),
            new Dish("french fries", true, 530, Dish.Type.OTHER),
            new Dish("rice", true, 350, Dish.Type.OTHER),
            new Dish("season fruit", true, 120, Dish.Type.OTHER),
            new Dish("pizza", true, 550, Dish.Type.OTHER),
            new Dish("prawns", false, 300, Dish.Type.FISH),
            new Dish("salmon", false, 450, Dish.Type.FISH));

    /**
     * 筛选
     */
    @Test
    public  void filter(){
        List<Dish> list = menuList.stream()
                .filter(m -> m.getCalories() > 300)
                .collect(Collectors.toList());
        list.forEach(m -> {
            System.out.println(m.getName());
        });
    }

    /**
     * 排序
     */
    @Test
    public void sort(){
        List<Dish> list = menuList.stream()
                .sorted((m, n) -> {
                    return m.getCalories() > n.getCalories()?1:-1;
                })
                .peek(e -> System.out.println("Mapped value: " + e.getName()))
                .collect(Collectors.toList());
        list.forEach(m -> {
            System.out.println(m.getName() + " --- "+m.getCalories());
        });
    }

    @Test
    public void testStream(){
        List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
        numbers.stream()
                .filter( i -> i % 2 == 0)
                .distinct()
                .limit(1)
                .forEach(System.out::println);
    }
}
