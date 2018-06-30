package framework.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * 流操作公用
 */
public class StreamUtils {

    // ================================================================
    // Constants
    // ================================================================

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(StreamUtils.class);

    /** 默认读取字节数 */
    private final static int BUFFER_SIZE = 4096;

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
     * 将InputStream转换成String
     *
     * @param in
     *            InputStream
     * @return String
     *
     */
    public static String inputStreamTOString(InputStream in) {

        return inputStreamTOString(in, "UTF-8");
    }

    /**
     * 将InputStream转换成某种字符编码的String
     */
    public static String inputStreamTOString(InputStream in, String encoding) {
        String string = null;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[BUFFER_SIZE];
        int count;
        try {
            while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
                outStream.write(data, 0, count);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            string = new String(outStream.toByteArray(), encoding);
        } catch (UnsupportedEncodingException e) {
            logger.debug("", e);
        }
        return string;
    }

    /**
     * 将String转换成InputStream
     */
    public static InputStream stringTOInputStream(String in) throws Exception {

        return new ByteArrayInputStream(in.getBytes("UTF-8"));
    }

    /**
     * 将String转换成InputStream
     */
    public static byte[] stringTObyte(String in) {
        byte[] bytes = null;
        try {
            bytes = inputStreamTOByte(stringTOInputStream(in));
        } catch (IOException ignored) {
        } catch (Exception e) {
            logger.debug("", e);
        }
        return bytes;
    }

    /**
     * 将InputStream转换成byte数组
     *
     * @param in
     *            InputStream
     * @return byte[]
     * @throws IOException
     */
    public static byte[] inputStreamTOByte(InputStream in) throws IOException {

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[BUFFER_SIZE];
        int count;
        while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
            outStream.write(data, 0, count);

        return outStream.toByteArray();
    }

    /**
     * 将byte数组转换成InputStream
     */
    public static InputStream byteTOInputStream(byte[] in) throws Exception {

        return new ByteArrayInputStream(in);
    }

    /**
     * 将byte数组转换成String
     */
    public static String byteTOString(byte[] in) {

        InputStream is = null;
        try {
            is = byteTOInputStream(in);
        } catch (Exception e) {
            logger.debug("", e);
        }
        return inputStreamTOString(is, "UTF-8");
    }

    /**
     * 将byte数组转换成String
     *
     * @param in byte数组
     */
    public static String getString(String in) {

        String is = null;
        try {
            is = byteTOString(stringTObyte(in));
        } catch (Exception e) {
            logger.debug("", e);
        }
        return is;
    }

    /**
     * InputStream 转换成byte[]
     */
    public byte[] getBytes(InputStream is) throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] b = new byte[BUFFER_SIZE];
        int len;

        while ((len = is.read(b, 0, BUFFER_SIZE)) != -1) {
            baos.write(b, 0, len);
        }

        baos.flush();

        // System.out.println(new String(bytes));
        return baos.toByteArray();
    }

    /**
     * 根据文件路径创建文件输入流处理 以字节为单位（非 unicode ）
     *
     * @param filepath 文件路径
     * @return 文件输入流
     */
    public static FileInputStream getFileInputStream(String filepath) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(filepath);
        } catch (FileNotFoundException e) {
            logger.error("错误信息:文件不存在", e);
        }
        return fileInputStream;
    }

    /**
     * 根据文件对象创建文件输入流处理 以字节为单位（非 unicode ）
     *
     * @param file 文件对象
     * @return 文件输入流
     */
    public static FileInputStream getFileInputStream(File file) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            logger.error("错误信息:文件不存在", e);
        }
        return fileInputStream;
    }

    /**
     * 根据文件对象创建文件输出流处理 以字节为单位（非 unicode ）
     *
     * @param file 文件对象
     * @param append
     *            true:文件以追加方式打开,false:则覆盖原文件的内容
     * @return 文件输出流
     */
    public static FileOutputStream getFileOutputStream(File file, boolean append) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file, append);
        } catch (FileNotFoundException e) {
            logger.error("错误信息:文件不存在", e);
        }
        return fileOutputStream;
    }

    /**
     * 根据文件路径创建文件输出流处理 以字节为单位（非 unicode ）
     *
     * @param filepath 文件路径
     * @param append
     *            true:文件以追加方式打开,false:则覆盖原文件的内容
     * @return 文件输出流
     */
    public static FileOutputStream getFileOutputStream(String filepath, boolean append) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(filepath, append);
        } catch (FileNotFoundException e) {
            logger.error("错误信息:文件不存在", e);
        }
        return fileOutputStream;
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
