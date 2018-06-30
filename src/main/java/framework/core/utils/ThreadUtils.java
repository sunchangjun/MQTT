package framework.core.utils;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 * 防止web服务器关闭时，有无法管理的线程或者线程池不能关闭，导致web服务器无法正常关闭，
 * 这里是系统 所有线程的产生地. 
 * 如果是一次性执行的线程，调用ThreadUtils.addExecuteTask()获取 
 * 如果个人需要一个线程池,调用ThreadUtils.get*()获取
 * </pre>
 */
public class ThreadUtils {

    // ================================================================
    // Constants
    // ================================================================

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(ThreadUtils.class);

    /** 线程池维护线程的最少数量 */
    private final static int CORE_POOL_SIZE = 10;

    /** 线程池维护线程的最大数量 */
    private final static int MAX_POOL_SIZE = 30;

    /** 线程池维护线程所允许的空闲时间 */
    private final static int KEEP_ALIVE_TIME = 0;

    /** 线程池所使用的缓冲队列大小 */
    private final static int WORK_QUEUE_SIZE = 50;

    /** 任务调度周期 */
    private final static int TASK_QOS_PERIOD = 10;

    // ================================================================
    // Fields
    // ================================================================

    // 任务缓冲队列
    private static Queue<Runnable> taskQueue = new ConcurrentLinkedDeque<>();

    // 全局线程池列表，系统的线程池通过这里获得和销毁
    private static List<ExecutorService> executorServiceList = new CopyOnWriteArrayList<>();

    /*
     * 线程池超出界线时将任务加入缓冲队列
     */
    static final RejectedExecutionHandler handler = (task, executor) -> taskQueue.offer(task);

    /*
     * 将缓冲队列中的任务重新加载到线程池
     */
    static final Runnable accessBufferThread = new Runnable() {
        @Override
        public void run() {
            if (hasMoreAcquire()) {
                threadPool.execute(taskQueue.poll());
            }
        }
    };

    /*
     * 创建一个调度线程池
     */
    static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    /*
     * 通过调度线程周期性的执行缓冲队列中任务
     */
    static final ScheduledFuture<?> taskHandler = scheduler.scheduleAtFixedRate(accessBufferThread, 0, TASK_QOS_PERIOD, TimeUnit.MILLISECONDS);

    /*
     * 线程池
     */
    static final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, new ArrayBlockingQueue<>(WORK_QUEUE_SIZE), handler);

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
     * 向线程池中添加任务方法
     */
    public static void addThread(Runnable task) {
        ThreadUtils.addExecuteTask(task);
    }

    /**
     * 向线程池中添加任务方法
     */
    public static void addExecuteTask(Runnable task) {
        if (task != null) {
            threadPool.execute(task);
        }
    }

    /**
     * 向全局线程池列表中添加线程池,便于管理
     */
    public static void putExecutorServiceToList(ExecutorService executorService) {
        executorServiceList.add(executorService);
    }

    /**
     * 从全局线程池列表中获取线程池,便于管理
     */
    public static ScheduledExecutorService getScheduledExecutorService() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        executorServiceList.add(scheduler);

        return scheduler;
    }

    /**
     * 从全局线程池列表中获取线程池,便于管理
     */
    public static ScheduledExecutorService getScheduledExecutorService(int size) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(size);
        executorServiceList.add(scheduler);

        return scheduler;
    }

    /**
     * 从全局线程池列表中获取线程池,便于管理
     */
    public static ExecutorService getThreadPoolExecutor() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorServiceList.add(executorService);

        return executorService;
    }

    /**
     * 从全局线程池列表中获取线程池,便于管理
     */
    public static ExecutorService getThreadPoolExecutor(int size) {
        ExecutorService executorService = Executors.newFixedThreadPool(size);
        executorServiceList.add(executorService);

        return executorService;
    }

    /**
     * sleep等待,单位为毫秒,忽略InterruptedException.
     */
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            // Ignore.
        }
    }

    /**
     * sleep等待,忽略InterruptedException.
     */
    public static void sleep(long duration, TimeUnit unit) {
        try {
            Thread.sleep(unit.toMillis(duration));
        } catch (InterruptedException e) {
            // Ignore.
        }
    }

    /**
     * 整个服务器关闭的时候，所有的线程池都要关闭
     */
    public static void serverShutdown() {
        // scheduler关闭
        gracefulShutdown(scheduler, 100, 100, TimeUnit.MILLISECONDS);
        // threadPool关闭
        gracefulShutdown(threadPool, 100, 100, TimeUnit.MILLISECONDS);
        // executorServiceList关闭
        if (CollectionUtils.isNotEmpty(executorServiceList)) {
            for (ExecutorService executorService : executorServiceList) {
                gracefulShutdown(executorService, 100, 100, TimeUnit.MILLISECONDS);
            }
            executorServiceList.clear();
        }
    }

    /**
     * <pre>
     * 按照ExecutorService JavaDoc示例代码编写的Graceful Shutdown方法.
     * 先使用shutdown, 停止接收新任务并尝试完成所有已存在任务.
     * 如果超时, 则调用shutdownNow, 取消在workQueue中Pending的任务,并中断所有阻塞函数.
     * 如果仍人超時，則強制退出. 另对在shutdown时线程本身被调用中断做了处理.
     * </pre>
     */
    public static void gracefulShutdown(ExecutorService pool, int shutdownTimeout, int shutdownNowTimeout, TimeUnit timeUnit) {
        pool.shutdown(); // Disable new tasks from being submitted
        try {
            // Wait a while for existing tasks to terminate
            if (!pool.awaitTermination(shutdownTimeout, timeUnit)) {
                pool.shutdownNow(); // Cancel currently executing tasks
                // Wait a while for tasks to respond to being cancelled
                if (!pool.awaitTermination(shutdownNowTimeout, timeUnit)) {
                    logger.info("Pool did not terminated");
                }
            }
        } catch (InterruptedException ie) {
            // (Re-)Cancel if current thread also interrupted
            pool.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }
    }

    /**
     * 直接调用shutdownNow的方法, 有timeout控制.取消在workQueue中Pending的任务,并中断所有阻塞函数.
     */
    public static void normalShutdown(ExecutorService pool, int timeout, TimeUnit timeUnit) {
        try {
            pool.shutdownNow();
            if (!pool.awaitTermination(timeout, timeUnit)) {
                logger.info("Pool did not terminated");
            }
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    // ================================================================
    // Getter & Setter
    // ================================================================

    // ================================================================
    // Private Methods
    // ================================================================

    /*
     * 消息队列检查方法
     */
    private static boolean hasMoreAcquire() {
        return !taskQueue.isEmpty();
    }

    // ================================================================
    // Inner or Anonymous Class
    // ================================================================

    // ================================================================
    // Test Methods
    // ================================================================
}
