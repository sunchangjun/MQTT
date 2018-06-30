package framework.core.utils;

import com.beust.jcommander.internal.Lists;
import org.apache.commons.lang3.ArrayUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * 克隆对象,apache里的ObjectUtils 虽然提供了clone方法,但是有局限性,这里使用读写方式实现
 * Created by zeyuphoenix on 16/9/2.
 */
public class CloneUtils {

    // ================================================================
    // Constants
    // ================================================================

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
     * 依据文件流克隆对象
     *
     * @param src
     *            要克隆的对象
     * @return 克隆后对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T cloneTo(T src) {
        // empty return
        if (src == null) {
            return null;
        }
        // memory clone
        ByteArrayOutputStream memoryBuffer;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        T dist = null;
        try {
            memoryBuffer = new ByteArrayOutputStream();
            // write to output
            out = new ObjectOutputStream(memoryBuffer);
            out.writeObject(src);
            out.flush();

            // read from memory
            in = new ObjectInputStream(new ByteArrayInputStream(
                    memoryBuffer.toByteArray()));
            dist = (T) in.readObject();

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            // close output
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ignored) {
                }
            }
            // close input
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ignored) {
                }
            }
        }

        return dist;
    }

    /**
     * 克隆数组到列表
     *
     * @param array
     *            数组
     * @return 列表
     */
    public static <T> List<T> cloneArray2List(T[] array) {
        // empty return
        if (ArrayUtils.isEmpty(array)) {
            return Lists.newArrayList(0);
        }
        List<T> list = Lists.newArrayList(array.length);
        // each clone.
        for (T t : array) {
            list.add(cloneTo(t));
        }

        return list;
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

    // ================================================================
    // Test Methods
    // ================================================================

}
