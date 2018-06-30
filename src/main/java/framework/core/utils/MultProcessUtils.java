package framework.core.utils;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CountDownLatch、CyclicBarrier 和 Semaphore,这三个是 JUC
 * 中较为常用的同步器，通过它们可以方便地实现很多线程之间协作的功能
 *
 * 1.CountDownLatch 的作用和
 * Thread.join()方法类似，可用于一组线程和另外一组线程的协作。例如，主线程在做一项工作之前需要一系列的准备工作
 * ，只有这些准备工作都完成，主线程才能继续它的工作。这些准备工作彼此独立，所以可以并发执行以提高速度。在这个场景下就可以使用
 * CountDownLatch 协调线程之间的调度
 *
 * 2.CyclicBarrier的方法就是一个：await()。await()
 * 方法每被调用一次，计数便会减少1，并阻塞住当前线程。当计数减至0时，阻塞解除，所有在此 CyclicBarrier
 * 上面阻塞的线程开始运行。在这之后，如果再次调用 await() 方法，计数就又会变成 N-1，新一轮重新开始
 *
 * 3.Semaphore通过构造函数设定一个数量的许可，然后通过 acquire 方法获得许可，release 方法释放许可。它还有 tryAcquire
 * 和 acquireUninterruptibly 方法
 *
 * 总结：CountDownLatch 是能使一组线程等另一组线程都跑完了再继续跑；CyclicBarrier
 * 能够使一组线程在一个时间点上达到同步，可以是一起开始执行全部任务或者一部分任务。同时，它是可以循环使用的；Semaphore
 * 是只允许一定数量的线程同时执行一段任务
 * Created by zeyuphoenix on 16/9/2.
 */
public class MultProcessUtils {

    // ================================================================
    // Constants
    // ================================================================

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(MultProcessUtils.class);

    // ================================================================
    // Fields
    // ================================================================

    // ================================================================
    // Constructors
    // ================================================================

    // ================================================================
    // Methods from/for super Interfaces or SuperClass
    // ================================================================

    // ================================================================
    // Public or Protected Methods
    // ================================================================

    /**
     * 执行任务.
     *
     * @param workers
     *            the workers
     * @throws InterruptedException
     *             the interrupted exception
     */
    public static void invoker(Worker... workers) throws InterruptedException {
        List<Worker> wokerList = Lists.newArrayList();
        CollectionUtils.addAll(wokerList, workers);
        invoker(wokerList);
    }

    /**
     * 执行任务.
     *
     * @param workers
     *            the workers
     * @throws InterruptedException
     *             the interrupted exception
     */
    public static void invoker(List<Worker> workers)
            throws InterruptedException {
        if (CollectionUtils.isNotEmpty(workers)) {
            // 总计数器
            CountDownLatch startSignal = new CountDownLatch(1);
            // 分任务计数器
            CountDownLatch doneSignal = new CountDownLatch(workers.size());
            // 线程池
            ExecutorService executorService = Executors.newCachedThreadPool();
            for (Worker worker : workers) {
                // 设置计数器
                worker.setStartSignal(startSignal);
                worker.setDoneSignal(doneSignal);
                executorService.submit(worker);
            }
            logger.info("I am the main,now i start the singal");
            startSignal.countDown();
            logger.info("Waiting all task over " + doneSignal.getCount());
            doneSignal.await();
            logger.info("All the workers is over ");
        }
    }

    // ================================================================
    // Getter & Setter
    // ================================================================

    // ================================================================
    // Private Methods
    // ================================================================

    // ================================================================
    // Inner or Anonymous Class
    // ================================================================

    /**
     * 真正在工作的线程，需要实现doWork方法.
     *
     * @author zeyuphoenix
     */
    public abstract static class Worker implements Runnable {

        // ================================================================
        // Fields
        // ================================================================

        // 开始标志
        /** The start signal. */
        private CountDownLatch startSignal;
        // 完成标志
        /** The done signal. */
        private CountDownLatch doneSignal;

        // ================================================================
        // Constructors
        // ================================================================

        /**
         * 构造函数.
         */
        public Worker() {
            super();
        }

        /**
         * 构造函数.
         *
         * @param startSignal
         *            the start signal
         * @param doneSignal
         *            the done signal
         */
        public Worker(CountDownLatch startSignal, CountDownLatch doneSignal) {
            super();
            this.startSignal = startSignal;
            this.doneSignal = doneSignal;
        }

        // ================================================================
        // Methods from/for super Interfaces or SuperClass
        // ================================================================

        /*
         * (non-Javadoc)
         *
         * @see java.lang.Runnable#run()
         */
        @Override
        public void run() {
            try {
                logger.debug(Thread.currentThread().getName()
                        + " waiting the start singal...."
                        + startSignal.getCount());
                // 等待开始信号信号
                startSignal.await();
                doWork();
                logger.debug(Thread.currentThread().getName()
                        + " executer is over");
                // 结束的计数器减一
                doneSignal.countDown();
            } catch (InterruptedException ignored) {
            } // return;
        }

        // ================================================================
        // Public or Protected Methods
        // ================================================================

        /**
         * 必须继承这个方法，实现自己的worker.
         */
        public abstract void doWork();

        // ================================================================
        // Getter & Setter
        // ================================================================

        /**
         * Gets the start signal.
         *
         * @return the start signal
         */
        public CountDownLatch getStartSignal() {
            return startSignal;
        }

        /**
         * Sets the start signal.
         *
         * @param startSignal
         *            the new start signal
         */
        public void setStartSignal(CountDownLatch startSignal) {
            this.startSignal = startSignal;
        }

        /**
         * Gets the done signal.
         *
         * @return the done signal
         */
        public CountDownLatch getDoneSignal() {
            return doneSignal;
        }

        /**
         * Sets the done signal.
         *
         * @param doneSignal
         *            the new done signal
         */
        public void setDoneSignal(CountDownLatch doneSignal) {
            this.doneSignal = doneSignal;
        }
    }

    // ================================================================
    // Test Methods
    // ================================================================

}
