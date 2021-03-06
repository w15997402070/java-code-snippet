package com.demo.java8.forkAndJoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

/**
 * 继承 Recursive 来创建可以用于分支/合并框架的任务
 *
 * @see RecursiveTask
 */
public class ForkJoinSumCaculator extends RecursiveTask<Long> {

    /**
     * 要求和的数组
     */
    private final long[] numbers;
    /**
     * 子任务处理的数组的起始位置
     */
    private final int start;
    /**
     * 子任务处理的数组的终止位置
     */
    private final int end;
    /**
     * 不再将任务分解为子任务的数组大小
     */
    public static final long THRESHOLD = 10_000;

    public ForkJoinSumCaculator(long[] numbers) {
        this(numbers, 0, numbers.length);
    }

    public ForkJoinSumCaculator(long[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    /**
     * The main computation performed by this task.
     *
     * @return the result of the computation
     */
    @Override
    protected Long compute() {
        int length = end - start;
        if (length <= THRESHOLD) {
            return computeSequentially();
        }
        ForkJoinSumCaculator leftTask = new ForkJoinSumCaculator(numbers, start, start + length / 2);
        leftTask.fork();
        ForkJoinSumCaculator rightTask = new ForkJoinSumCaculator(numbers, start + length / 2, end);
        long rightResult = rightTask.compute();
        long leftResult = leftTask.join();
        return rightResult + leftResult;
    }

    private Long computeSequentially() {
        long sum = 0;
        for (int i = start; i < end; i++) {
            sum += numbers[i];
        }
        return sum;
    }

    /**
     * 请注意在实际应用时，使用多个ForkJoinPoo1是没有什么意义的。正是出于这个原因，一般来说把它实例化一次，然后把实例保存在静态字段中，使之成为单例，
     *
     * @param n
     * @return
     */
    private static long forkJoinSum(long n) {
        long[] numbers = LongStream.rangeClosed(0, n).toArray();
        ForkJoinTask<Long> task = new ForkJoinSumCaculator(numbers);
        return new ForkJoinPool().invoke(task);
    }

}
