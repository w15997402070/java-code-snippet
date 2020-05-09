

静态内部类Node
```java

 static final class Node {
        /** Marker to indicate a node is waiting in shared(共享) mode */
        static final Node SHARED = new Node();
        /** Marker to indicate a node is waiting in exclusive(排他,独占) mode */
        static final Node EXCLUSIVE = null;

        /** waitStatus value to indicate thread has cancelled等待状态--取消状态  节点从同步队列中取消 */
        static final int CANCELLED =  1;
        /** waitStatus value to indicate successor's thread needs unparking */
        //后继节点的线程处于等待状态，如果当前节点释放同步状态会通知后继节点，使得后继节点的线程能够运行
        static final int SIGNAL    = -1;
        /** waitStatus value to indicate thread is waiting on condition 等待开始条件 */
       //当前节点进入等待队列中
        static final int CONDITION = -2;
        /**
         * waitStatus value to indicate the next acquireShared should
         * unconditionally propagate
         */
        //表示下一次共享式同步状态获取将会无条件传播下去
        static final int PROPAGATE = -3;

        volatile int waitStatus;
        
        volatile Node prev;
        
        volatile Node next;
        
        volatile Thread thread;
       
        Node nextWaiter;

        final boolean isShared() {
            return nextWaiter == SHARED;
        }

        final Node predecessor() throws NullPointerException {
            Node p = prev;
            if (p == null)
                throw new NullPointerException();
            else
                return p;
        }

        Node() {    // Used to establish initial head or SHARED marker
        }

        Node(Thread thread, Node mode) {     // Used by addWaiter
            this.nextWaiter = mode;
            this.thread = thread;
        }

        Node(Thread thread, int waitStatus) { // Used by Condition
            this.waitStatus = waitStatus;
            this.thread = thread;
        }
}

```