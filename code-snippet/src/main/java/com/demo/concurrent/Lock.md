

## Condition 接口

```java
public interface Condition {
    //方法会使当前线程等待，同时释放当前锁，当其他线程中使用signal()方法或者signalAll()方法时，线程会重新获得锁并继续执行。或者当线程被中断时，也能跳出等待。这和Object.wait()方法相似
    void await() throws InterruptedException;
   //与await()方法基本相同，但是它并不会在等待过程中响应中断
    void awaitUninterruptibly();
    long awaitNanos(long nanosTimeout) throws InterruptedException;
    boolean await(long time, TimeUnit unit) throws InterruptedException;
    boolean awaitUntil(Date deadline) throws InterruptedException;
    //用于唤醒一个在等待中的线程
    void signal();
    //唤醒所有在等待中的线程。这和Obejct.notify()方法很类似。
    void signalAll();
}
```

`ArrayBlockingQueue`

```java
public class ArrayBlockingQueue<E> extends AbstractQueue<E>
        implements BlockingQueue<E>, java.io.Serializable {
        
    /** Main lock guarding all access */
    final ReentrantLock lock;

    /** Condition for waiting takes */
    private final Condition notEmpty;

    /** Condition for waiting puts */
    private final Condition notFull;

    public ArrayBlockingQueue(int capacity, boolean fair) {
        if (capacity <= 0){
            throw new IllegalArgumentException();
        }
        this.items = new Object[capacity];
        lock = new ReentrantLock(fair);
        notEmpty = lock.newCondition();//生成与lock绑定的Condition
        notFull =  lock.newCondition();
    }

    public void put(E e) throws InterruptedException {
        checkNotNull(e);
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            while (count == items.length){  //如果当前队列满了
                notFull.await();            //则等待队列有足够的空间
            }
            enqueue(e);               //当notFull被通知说明有足够的空间
        } finally {
            lock.unlock();
        }
    }
    //插入数据的方法
    private void enqueue(E x) {
        // assert lock.getHoldCount() == 1;
        // assert items[putIndex] == null;
        final Object[] items = this.items;
        items[putIndex] = x;
        if (++putIndex == items.length)
            putIndex = 0;
        count++;
        notEmpty.signal();  //通知需要take()方法的线程,队列已有数据
    }
    //take方法实现
    public E take() throws InterruptedException {
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            while (count == 0)    //队列为空
                notEmpty.await();  //则消费者队列要等待一个非空的信号
            return dequeue();
        } finally {
            lock.unlock();
        }
    }
   //出队列
    private E dequeue() {
        // assert lock.getHoldCount() == 1;
        // assert items[takeIndex] != null;
        final Object[] items = this.items;
        @SuppressWarnings("unchecked")
        E x = (E) items[takeIndex];
        items[takeIndex] = null;
        if (++takeIndex == items.length)
            takeIndex = 0;
        count--;
        if (itrs != null)
            itrs.elementDequeued();
        notFull.signal();   //通知put()线程队列已有空闲空间
        return x;
    }

}
```

## ReadWriteLock读写锁

读写锁的访问约束情况
 * 读-读不互斥：读读之间不阻塞。
 * 读-写互斥：读阻塞写，写也会阻塞读。
 * 写-写互斥：写写阻塞。
 
 如果在系统中，读操作的次数远远大于写操作的次数，则读写锁就可以发挥最大的功效，提升系统的性能
 
 
 ## LockSupport
 
 LockSupport的静态方法park()可以阻塞当前线程，类似的还有parkNanos()、parkUntil()等方法。它们实现了一个限时的等待。
 
 ## RateLimiter(Guava)
 
 限流工具RateLimiter
 漏桶算法和令牌桶算法
 
 漏桶算法的基本思想是：利用一个缓存区，当有请求进入系统时，无论请求的速率如何，都先在缓存区内保存，然后以固定的流速流出缓存区进行处理
 
 令牌桶算法是一种反向的漏桶算法。在令牌桶算法中，桶中存放的不再是请求，而是令牌。处理程序只有拿到令牌后，才能对请求进行处理。如果没有令牌，那么处理程序要么丢弃请求，要么等待可用的令牌。为了限制流速，该算法在每个单位时间产生一定量的令牌存入桶中。比如，要限定应用每秒只能处理1个请求，那么令牌桶就会每秒产生1个令牌。通常，桶的容量是有限的，比如，当令牌没有被消耗掉时，只能累计有限单位时间内的令牌数量