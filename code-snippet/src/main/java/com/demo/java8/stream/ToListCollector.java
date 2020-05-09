package com.demo.java8.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class ToListCollector<T> implements Collector<T, List<T>, List<T>> {
    /**
     * 1. 建立新的结果容器
     * @return
     */
    @Override
    public Supplier<List<T>> supplier() {
       Supplier<List<T>> supplier =  new Supplier<List<T>>() {
            @Override
            public List<T> get() {
                return new ArrayList<>();
            }
        };
       return supplier;
//       下面和上面相同,用lambda表达式的形式
//        return () -> new ArrayList<T>();
//        return ArrayList::new;
    }

    /**
     * 2. 将元素添加到结果容器
     * @return
     */
    @Override
    public BiConsumer<List<T>, T> accumulator() {
//        new BiConsumer<List<T>,T>(){
//            @Override
//            public void accept(List<T> list, T t) {
//                list.add(t);
//            }
//        };
        return (List<T> list, T t) -> list.add(t);
    }
    /**
     * 3. 对结果容器的最终转换
     * @return
     */
    @Override
    public Function<List<T>, List<T>> finisher() {
        return Function.identity();
    }
    /**
     * 4. 合并两个结果容器
     * @return
     */
    @Override
    public BinaryOperator<List<T>> combiner() {
        return (List<T> list1,List<T> list2) -> {list1.addAll(list2) ;return list1;};
    }
    @Override
    public Set<Characteristics> characteristics() {
        return null;
    }
}
