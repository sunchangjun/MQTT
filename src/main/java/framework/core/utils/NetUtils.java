package framework.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.ServerSocket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 提供网络处理的一些工具
 */
public class NetUtils {

    // ================================================================
    // Constants
    // ================================================================

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(NetUtils.class);

    /**
     * 端口正则
     */
    public static final String REGEX_PORT = "^([0-9]|[1-9]\\d|[1-9]\\d{2}|[1-9]\\d{3}|[1-5]\\d{4}|6[0-4]\\d{3}|65[0-4]\\d{2}|655[0-2]\\d|6553[0-5])$";
    public static final String REGEX_PORT_MESSAGE = "端口必须在[0~65535]之间";
    /**
     * 端口或ANY正则
     */
    public static final String REGEX_PORT_OR_ANY = "^([0-9]|[1-9]\\d|[1-9]\\d{2}|[1-9]\\d{3}|[1-5]\\d{4}|6[0-4]\\d{3}|65[0-4]\\d{2}|655[0-2]\\d|6553[0-5]|ANY)$";
    public static final String REGEX_PORT_OR_ANY_MESSAGE = REGEX_PORT_MESSAGE + "或者ANY";

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
     * port校验
     */
    public static Boolean isPort(String port) {
        Pattern p = Pattern.compile(REGEX_PORT);
        Matcher m = p.matcher(port);
        return m.matches();
    }

    /**
     * port or any校验
     */
    public static Boolean isPortOrAny(String port) {
        Pattern p = Pattern.compile(REGEX_PORT_OR_ANY);
        Matcher m = p.matcher(port);
        return m.matches();
    }

    /**
     * 数字字符串转ASCII码字符串
     *
     * @param content 字符串
     * @return ASCII字符串
     */
    public static String stringToAsciiString(String content) {
        String result = "";
        int max = content.length();
        for (int i = 0; i < max; i++) {
            char c = content.charAt(i);
            String b = Integer.toHexString(c);
            result = result + b;
        }
        return result;
    }

    /**
     * 十六进制字符串装十进制
     *
     * @param hex 十六进制字符串
     * @return 十进制数值
     */
    public static int hexStringToAlgorism(String hex) {
        hex = hex.toUpperCase();
        int max = hex.length();
        int result = 0;
        for (int i = max; i > 0; i--) {
            char c = hex.charAt(i - 1);
            int algorism;
            if (c >= '0' && c <= '9') {
                algorism = c - '0';
            } else {
                algorism = c - 55;
            }
            result += Math.pow(16, max - i) * algorism;
        }
        return result;
    }

    /**
     * 十六转二进制
     *
     * @param hex 十六进制字符串
     * @return 二进制字符串
     */
    public static String hexStringToBinary(String hex) {
        hex = hex.toUpperCase();
        String result = "";
        int max = hex.length();
        for (int i = 0; i < max; i++) {
            char c = hex.charAt(i);
            switch (c) {
                case '0':
                    result += "0000";
                    break;
                case '1':
                    result += "0001";
                    break;
                case '2':
                    result += "0010";
                    break;
                case '3':
                    result += "0011";
                    break;
                case '4':
                    result += "0100";
                    break;
                case '5':
                    result += "0101";
                    break;
                case '6':
                    result += "0110";
                    break;
                case '7':
                    result += "0111";
                    break;
                case '8':
                    result += "1000";
                    break;
                case '9':
                    result += "1001";
                    break;
                case 'A':
                    result += "1010";
                    break;
                case 'B':
                    result += "1011";
                    break;
                case 'C':
                    result += "1100";
                    break;
                case 'D':
                    result += "1101";
                    break;
                case 'E':
                    result += "1110";
                    break;
                case 'F':
                    result += "1111";
                    break;
            }
        }
        return result;
    }

    /**
     * ASCII码字符串转数字字符串
     *
     * @param content ASCII字符串
     * @return 字符串
     */
    public static String asciiStringToString(String content) {
        String result = "";
        int length = content.length() / 2;
        for (int i = 0; i < length; i++) {
            String c = content.substring(i * 2, i * 2 + 2);
            int a = hexStringToAlgorism(c);
            char b = (char) a;
            String d = String.valueOf(b);
            result += d;
        }
        return result;
    }

    /**
     * 将十进制转换为指定长度的十六进制字符串
     *
     * @param algorism  int 十进制数字
     * @param maxLength int 转换后的十六进制字符串长度
     * @return String 转换后的十六进制字符串
     */
    public static String algorismToHEXString(int algorism, int maxLength) {
        String result = Integer.toHexString(algorism);

        if (result.length() % 2 == 1) {
            result = "0" + result;
        }
        return patchHexString(result.toUpperCase(), maxLength);
    }

    /**
     * 字节数组转为普通字符串（ASCII对应的字符）
     *
     * @param bytearray byte[]
     * @return String
     */
    public static String bytetoString(byte[] bytearray) {
        String result = "";
        char temp;

        for (byte b : bytearray) {
            temp = (char) b;
            result += temp;
        }
        return result;
    }

    /**
     * 二进制字符串转十进制
     *
     * @param binary 二进制字符串
     * @return 十进制数值
     */
    public static int binaryToAlgorism(String binary) {
        int max = binary.length();
        int result = 0;
        for (int i = max; i > 0; i--) {
            char c = binary.charAt(i - 1);
            int algorism = c - '0';
            result += Math.pow(2, max - i) * algorism;
        }
        return result;
    }

    /**
     * 二进制字符串转十进制
     *
     * @param binary 二进制字符串
     * @return 十进制数值
     */
    public static long binaryToLong(String binary) {
        int max = binary.length();
        long result = 0L;
        for (int i = max; i > 0; i--) {
            char c = binary.charAt(i - 1);
            int algorism = c - '0';
            result += Math.pow(2, max - i) * algorism;
        }
        return result;
    }

    /**
     * 十进制转换为十六进制字符串
     *
     * @param algorism int 十进制的数字
     * @return String 对应的十六进制字符串
     */
    public static String algorismToHEXString(int algorism) {
        String result = Integer.toHexString(algorism);

        if (result.length() % 2 == 1) {
            result = "0" + result;

        }
        result = result.toUpperCase();

        return result;
    }

    /**
     * HEX字符串前补0，主要用于长度位数不足。
     *
     * @param str       String 需要补充长度的十六进制字符串
     * @param maxLength int 补充后十六进制字符串的长度
     * @return 补充结果
     */
    static public String patchHexString(String str, int maxLength) {
        String temp = "";
        for (int i = 0; i < maxLength - str.length(); i++) {
            temp = "0" + temp;
        }
        str = (temp + str).substring(0, maxLength);
        return str;
    }

    /**
     * 将一个字符串转换为int
     *
     * @param s          String 要转换的字符串
     * @param defaultInt int 如果出现异常,默认返回的数字
     * @param radix      int 要转换的字符串是什么进制的,如16 8 10.
     * @return int 转换后的数字
     */
    public static int parseToInt(String s, int defaultInt, int radix) {
        int i;
        try {
            i = Integer.parseInt(s, radix);
        } catch (NumberFormatException ex) {
            i = defaultInt;
        }
        return i;
    }

    /**
     * 将一个十进制形式的数字字符串转换为int
     *
     * @param s          String 要转换的字符串
     * @param defaultInt int 如果出现异常,默认返回的数字
     * @return int 转换后的数字
     */
    public static int parseToInt(String s, int defaultInt) {
        int i;
        try {
            i = Integer.parseInt(s);
        } catch (NumberFormatException ex) {
            i = defaultInt;
        }
        return i;
    }

    /**
     * 十六进制串转化为byte数组
     *
     * @return the array of byte
     */
    public static byte[] hex2byte(String hex) throws IllegalArgumentException {
        if (hex.length() % 2 != 0) {
            throw new IllegalArgumentException();
        }
        char[] arr = hex.toCharArray();
        byte[] b = new byte[hex.length() / 2];
        for (int i = 0, j = 0, l = hex.length(); i < l; i++, j++) {
            String swap = "" + arr[i++] + arr[i];
            int byteint = Integer.parseInt(swap, 16) & 0xFF;
            b[j] = new Integer(byteint).byteValue();
        }
        return b;
    }

    /**
     * 字节数组转换为十六进制字符串
     *
     * @param b byte[] 需要转换的字节数组
     * @return String 十六进制字符串
     */
    public static String byte2hex(byte b[]) {
        if (b == null) {
            throw new IllegalArgumentException("Argument b ( byte array ) is null! ");
        }
        String hs = "";
        String stmp;
        for (byte item : b) {
            stmp = Integer.toHexString(item & 0xff);
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }

    public static byte intToByte(int i) {
        return (byte) i;
    }

    public static byte[] intToBytes2(int i) {
        byte bytes[] = new byte[2];
        bytes[1] = (byte) (0xff & i);
        bytes[0] = (byte) ((0xff00 & i) >> 8);
        return bytes;
    }

    public static byte[] intToBytes4(int i) {
        byte bytes[] = new byte[4];
        bytes[3] = (byte) (0xff & i);
        bytes[2] = (byte) ((0xff00 & i) >> 8);
        bytes[1] = (byte) ((0xff0000 & i) >> 16);
        bytes[0] = (byte) ((0xff000000 & i) >> 24);
        return bytes;
    }

    public static byte[] toBytes(int value, int size) {
        int loop;
        if (size == 4 || size <= 0)
            loop = size = 4;
        else if (size > 4)
            loop = 4;
        else
            loop = size;
        byte b[] = new byte[size];
        b[size - 1] = (byte) value;
        for (int i = 1; i < loop; i++)
            b[size - 1 - i] = (byte) (value >>= 8);

        return b;
    }

    public static int byteArrayToInt(byte b[]) {
        return (b[0] << 24) + ((b[1] & 0xff) << 16) + ((b[2] & 0xff) << 8) + (b[3] & 0xff);
    }

    public static byte[] longToBytes8(long l) {
        byte bytes[] = new byte[8];
        bytes[7] = (byte) (int) (0xff & l);
        bytes[6] = (byte) (int) ((0xff00L & l) >> 8);
        bytes[5] = (byte) (int) ((0xff0000L & l) >> 16);
        bytes[4] = (byte) (int) ((0xff000000L & l) >> 24);
        bytes[3] = (byte) (int) ((0xff00000000L & l) >> 32);
        bytes[2] = (byte) (int) ((0xff0000000000L & l) >> 40);
        bytes[1] = (byte) (int) ((0xff000000000000L & l) >> 48);
        bytes[0] = (byte) (int) ((0xff00000000000000L & l) >> 56);
        return bytes;
    }

    public static long bytes8ToLong(byte bytes[], int offset) {
        return (255L & (long) bytes[offset]) << 56 | (255L & (long) bytes[offset + 1]) << 48 | (255L & (long) bytes[offset + 2]) << 40 | (255L & (long) bytes[offset + 3]) << 32 | (255L & (long) bytes[offset + 4]) << 24 | (255L & (long) bytes[offset + 5]) << 16 | (255L & (long) bytes[offset + 6]) << 8 | (255L & (long) bytes[offset + 7]);
    }

    public static void longToBytes4(long l, byte bytes[]) {
        bytes[3] = (byte) (int) (255L & l);
        bytes[2] = (byte) (int) ((65280L & l) >> 8);
        bytes[1] = (byte) (int) ((0xff0000L & l) >> 16);
        bytes[0] = (byte) (int) ((0xffffffffff000000L & l) >> 24);
    }

    public static void intToBytes(int i, byte bytes[]) {
        bytes[1] = (byte) (0xff & i);
        bytes[0] = (byte) ((0xff00 & i) >> 8);
    }

    public static void intToBytes4(int i, byte bytes[]) {
        bytes[3] = (byte) (0xff & i);
        bytes[2] = (byte) ((0xff00 & i) >> 8);
        bytes[1] = (byte) ((0xff0000 & i) >> 16);
        bytes[0] = (byte) (int) ((0xffffffffff000000L & (long) i) >> 24);
    }

    public static int bytes4ToInt(byte bytes[]) {
        return (0xff & bytes[0]) << 24 | (0xff & bytes[1]) << 16 | (0xff & bytes[2]) << 8 | 0xff & bytes[3];
    }

    public static long bytes4ToLong(byte bytes[]) {
        return (255L & (long) bytes[0]) << 24 | (255L & (long) bytes[1]) << 16 | (255L & (long) bytes[2]) << 8 | 255L & (long) bytes[3];
    }

    /**
     * 是否ping通
     */
    public static boolean ping(String ipaddr) {
        String cmd;
        int pks = 1;
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("windows")) {// windows平台
            cmd = "ping -w 1 -n  " + pks + " " + ipaddr;
        } else {// 非windows平台
            cmd = "ping -w 1 -c  " + pks + " " + ipaddr;
        }
        StringBuilder message = new StringBuilder();
        Process process = null;
        LineNumberReader reader = null;
        try {
            process = Runtime.getRuntime().exec(cmd);
            process.getOutputStream().close();

            reader = new LineNumberReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                message.append(line).append("\r\n");
            }
            String mess = message.toString().toUpperCase();

            /**
             * 不区分大小写 Linux下是小写 Windows下是大写
             */
            return mess.contains(" TTL=");

        } catch (IOException e) {
            return false;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    logger.debug("", e);
                }
            }
            if (process != null) {
                try {
                    process.getErrorStream().close();
                    process.getInputStream().close();
                } catch (IOException e) {
                    logger.debug("", e);
                }
            }
        }
    }

    /**
     * 端口是否可用
     */
    public static boolean isPortAvailable(int port) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            logger.debug("port {} is available", port);

            return true;
        } catch (Exception e) {
            logger.debug("port {} is occupied", port);
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException ignored) {
                }
            }
        }
        return false;
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
