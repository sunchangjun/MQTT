package framework.core.utils;

import java.security.SecureRandom;
import java.util.UUID;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * 封装各种生成唯一性ID算法的工具类.
 */
@Service
@Lazy(false)
public class IdGen  {

    // ================================================================
    // Constants
    // ================================================================

    // ================================================================
    // Fields
    // ================================================================

    /**
     * 随机算法
     */
    private static SecureRandom random = new SecureRandom();

    // ================================================================
    // Constructors
    // ================================================================

    // ================================================================
    // Methods from/for super Interfaces or SuperClass
    // ================================================================

    /**
     * Activiti ID 生成
     */
    public String getNextId() {
        return IdGen.uuid();
    }


    // ================================================================
    // Public or Protected Methods
    // ================================================================

    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
     */
    public static String uuid() {
        return IdGen.uuid32();
    }

    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
     */
    public static String uuid32() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间-分割.
     */
    public static String uuid36() {
        return UUID.randomUUID().toString();
    }

    /**
     * 使用SecureRandom随机生成Long.
     */
    public static long randomLong() {
        return Math.abs(random.nextLong());
    }

    /**
     * 基于Base62编码的SecureRandom随机生成bytes.
     */
    public static String randomBase62(int length) {
        byte[] randomBytes = new byte[length];
        random.nextBytes(randomBytes);
        return Encodes.encodeBase62(randomBytes);
    }

    /**
     * 基于twitter的60位唯一id算法
     *
     * @return 唯一id
     */
    public static long nonRepByTwitter() {
        return LongIdGen.get().nextId();
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
     * <pre>
     * 全局唯一ID生成器
     *
     * 调用简化了一下，实际使用中还是应该根据机器节点和数据中心节点来配置相关的参数。
     * 	     这里假设只有一个节点作为ID号的生成器，所以workerId和datacenterId都设为0，
     * 当前时间与计算标记时间twepoch（Thu, 04 Nov 2010 01:42:54 GMT）之间的毫秒数是一个38位长度的long值，
     * 再左移timestampLeftShift（22位），就得到一个60位长度的long数字，
     * 该数字与datacenterId << datacenterIdShift取或，datacenterId最小值为0，最大值为31，
     * 所以长度为1-5位，datacenterIdShift是17位，所以结果就是最小值为0，最大值为22位长度的long，
     * 同理，workerId << workerIdShift的最大值为17位的long。
     * 所以最终生成的会是一个60位长度的long型唯一ID
     * </pre>
     */
    protected static class LongIdGen {

        private long workerId;
        private long datacenterId;
        private long sequence = 0L;
        private long twepoch = 1288834974657L; // Thu, 04 Nov 2010 01:42:54 GMT
        private long workerIdBits = 5L; // 节点ID长度
        private long datacenterIdBits = 5L; // 数据中心ID长度
        private long maxWorkerId = ~(-1L << workerIdBits); // 最大支持机器节点数0~31，一共32个
        private long maxDatacenterId = ~(-1L << datacenterIdBits); // 最大支持数据中心节点数0~31，一共32个
        private long sequenceBits = 12L; // 序列号12位
        private long workerIdShift = sequenceBits; // 机器节点左移12位
        private long datacenterIdShift = sequenceBits + workerIdBits; // 数据中心节点左移17位
        private long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits; // 时间毫秒数左移22位
        private long sequenceMask = ~(-1L << sequenceBits);
        private long lastTimestamp = -1L;

        private static class IdGenHolder {
            private static final LongIdGen instance = new LongIdGen();
        }

        public static LongIdGen get() {
            return IdGenHolder.instance;
        }

        public LongIdGen() {
            this(0L, 0L);
        }

        public LongIdGen(long workerId, long datacenterId) {
            if (workerId > maxWorkerId || workerId < 0) {
                throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
            }
            if (datacenterId > maxDatacenterId || datacenterId < 0) {
                throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
            }
            this.workerId = workerId;
            this.datacenterId = datacenterId;
        }

        public synchronized long nextId() {
            long timestamp = timeGen();
            if (timestamp < lastTimestamp) {
                throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
            }
            if (lastTimestamp == timestamp) {
                sequence = (sequence + 1) & sequenceMask; // 这里是什么意思？
                if (sequence == 0) {
                    timestamp = tilNextMillis(lastTimestamp);
                }
            } else {
                sequence = 0L;
            }
            lastTimestamp = timestamp;
            return ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift) | (workerId << workerIdShift) | sequence;
        }

        protected long tilNextMillis(long lastTimestamp) {
            long timestamp = timeGen();
            while (timestamp <= lastTimestamp) {
                timestamp = timeGen();
            }
            return timestamp;
        }

        protected long timeGen() {
            return System.currentTimeMillis();
        }
    }

    // ================================================================
    // Test Methods
    // ================================================================
}
