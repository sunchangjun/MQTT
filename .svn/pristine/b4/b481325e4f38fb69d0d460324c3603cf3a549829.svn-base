package framework.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

/**
 * IP处理的工具类
 */
public class IPUtils {

    // ================================================================
    // Constants
    // ================================================================

    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(IPUtils.class);

    // "\\b((\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])(\\b|\\.)){4}"
    /**
     * IP正则验证
     */
    private static final String RE_IP = "(((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))";

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
     * ip校验
     */
    public static Boolean isIpAddress(String s) {
        Pattern p = Pattern.compile(RE_IP);
        Matcher m = p.matcher(s);
        return m.matches();
    }

    /**
     * 获取客户端ip
     */
    public static String getClientAddress(HttpServletRequest request) {
        String address = request.getHeader("X-Forwarded-For");
        if (address != null && isIpAddress(address)) {
            return address;
        }
        return request.getRemoteAddr();
    }

    /**
     * 按顺序获得机器的IP地址列表.
     *
     * @return the local ip list
     */
    public static List<String> getLocalNetCardList() {
        List<String> cardList = new ArrayList<>();
        List<NetworkInterface> netInterfacList = new ArrayList<>();
        try {
            // 获取当前环境下的所有网卡
            Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = netInterfaces.nextElement();
                if (netInterface.isLoopback()) {
                    // 过滤 lo网卡
                    continue;
                }
                /*
                 * 用上述方法获取所有网卡时，得到的顺序与服务器中用ifconfig命令看到的网卡顺序相反， 因此，想要从第一块网卡开始遍历时，需要将EnumerationNetworkInterface>中的元素倒序
				 */
                // 倒置网卡顺序
                netInterfacList.add(0, netInterface);
            }

            // 遍历每个网卡
            for (NetworkInterface netInterface : netInterfacList) {

                // 获取网卡下所有ip
                List<InterfaceAddress> addresses = netInterface.getInterfaceAddresses();

                for (InterfaceAddress interfaceAddress : addresses) {
                    InetAddress address = interfaceAddress.getAddress();
                    // 将网卡下所有ip地址取出
                    if (!address.isLoopbackAddress()) {
                        if (StringUtils.equalsIgnoreCase(address.getHostAddress(), "127.0.0.1")) {
                            // 过滤local地址
                            continue;
                        }
                        if (StringUtils.equalsIgnoreCase(address.getHostAddress(), "0.0.0.0")) {
                            // 过滤0.0.0.0地址
                            continue;
                        }
                        if (address instanceof Inet6Address) {
                            // 过滤ipv6地址
                            continue;
                        }
                        if (address instanceof Inet4Address) {
                            // 返回ipv4地址
                            cardList.add(address.getHostAddress());
                        }
                    }
                }
            }

        } catch (SocketException e) {
            logger.error("socket error: ", e);
        } catch (Exception e) {
            logger.error("", e);
        }
        return cardList;
    }

    /**
     * 获取本地可用ip列表，排除回环口
     *
     * @return ip列表
     */
    public static List<String> getLocalAddress() {
        List<String> ips = new ArrayList<>();
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface in = interfaces.nextElement();
                if (!in.isLoopback()) {
                    String hostAddress = in.getInetAddresses().nextElement().getHostAddress();
                    ips.add(hostAddress);
                }
            }
        } catch (SocketException e) {
            logger.debug("", e);
        }
        return ips;
    }

    /**
     * 把IP地址转化为byte
     *
     * @param ip IP地址
     * @return byte[]
     */
    public static byte[] toBytes(String ip) {
        byte[] ret = new byte[4];
        try {
            String[] ipArr = ip.split("\\.");
            ret[0] = (byte) (Integer.parseInt(ipArr[0]) & 0xFF);
            ret[1] = (byte) (Integer.parseInt(ipArr[1]) & 0xFF);
            ret[2] = (byte) (Integer.parseInt(ipArr[2]) & 0xFF);
            ret[3] = (byte) (Integer.parseInt(ipArr[3]) & 0xFF);
            return ret;
        } catch (Exception e) {
            throw new IllegalArgumentException(ip + " is invalid IP");
        }

    }

    /**
     * 根据位运算把 byte[] -> int
     *
     * @param bytes 数组
     * @return int
     */
    public static int toInt(byte[] bytes) {
        int addr = bytes[3] & 0xFF;
        addr |= ((bytes[2] << 8) & 0xFF00);
        addr |= ((bytes[1] << 16) & 0xFF0000);
        addr |= ((bytes[0] << 24) & 0xFF000000);
        return addr;
    }

    /**
     * Ip转换
     */
    public static String convertIP(long addr) {
        StringBuilder buf = new StringBuilder();
        buf.append(((addr >>> 24) & 0xff)).append('.').append(((addr >>> 16) & 0xff)).append('.').append(((addr >>> 8) & 0xff)).append('.').append(addr & 0xff);
        return buf.toString();
    }

    /**
     * Ip转换
     */
    public static long convertIP2Long(String ip) {
        char[] c = ip.toCharArray();
        byte[] b = {0, 0, 0, 0};
        for (int i = 0, j = 0; i < c.length; ) {
            int d = (byte) (c[i] - '0');
            switch (c[i++]) {
                case '.':
                    ++j;
                    break;
                default:
                    if ((d < 0) || (d > 9))
                        return 0;
                    if ((b[j] & 0xff) * 10 + d > 255)
                        return 0;
                    b[j] = (byte) (b[j] * 10 + d);
            }
        }
        return 0x00000000ffffffffL & (b[0] << 24 | (b[1] & 0xff) << 16 | (b[2] & 0xff) << 8 | (b[3] & 0xff));
    }

    /**
     * 掩码获取
     */
    public static String getMaskByPrefix(int length) {
        String binary = "";
        for (int i = 0; i < 32; i++) {
            if (i < length) {
                binary += "1";
            } else {
                binary += "0";
            }
        }
        return convertIP(NetUtils.binaryToLong(binary));
    }

    /**
     * 把IP地址转化为int
     *
     * @param ip IP地址
     * @return int
     */
    public static int toInt(String ip) {
        try {
            return toInt(toBytes(ip));
        } catch (Exception e) {
            throw new IllegalArgumentException(ip + " is invalid IP");
        }
    }

    /**
     * 字节数组转化为IP
     *
     * @param bytes 字节数组
     * @return IP
     */
    public static String toIp(byte[] bytes) {
        return new StringBuffer().append(bytes[0] & 0xFF).append('.').append(bytes[1] & 0xFF).append('.').append(bytes[2] & 0xFF).append('.').append(bytes[3] & 0xFF).toString();
    }

    /**
     * 把int->ip地址
     *
     * @param ip int类型
     * @return String
     */
    public static String toIp(int ip) {
        return new StringBuilder().append(((ip >> 24) & 0xff)).append('.').append((ip >> 16) & 0xff).append('.').append((ip >> 8) & 0xff).append('.').append((ip & 0xff)).toString();
    }

    /**
     * 掩码字符串转换为整数
     * eg. 255.255.0.0 16
     *
     * @param mask 字符串掩码
     */
    public static int toMask(String mask) {
        String[] aIp = mask.split("\\.");
        String temp = "";
        int index;
        for (String t : aIp) {
            temp += Integer.toBinaryString(Integer.parseInt(t));
            index = temp.indexOf("0");
            if (index != -1) {
                temp = temp.substring(0, index);
            }
        }

        return temp.length();
    }

    /**
     * 掩码字符串转换为整数
     * eg. 255.255.0.0 16
     *
     * @param mask 字符串掩码
     */
    public static int toMask(int mask) {
        StringBuilder bytes = new StringBuilder(32);
        for (int i = 1; i <= 32; i++) {
            if (mask >= i) {
                bytes.append(1);
            } else {
                bytes.append(0);
            }
        }
        StringBuilder _mask = new StringBuilder(15).append(Integer.parseInt(bytes.substring(0, 8), 2)).append(".").append(Integer.parseInt(bytes.substring(8, 16), 2)).append(".").append(Integer.parseInt(bytes.substring(16, 24), 2)).append(".").append(Integer.parseInt(bytes.substring(24), 2));
        return toInt(_mask.toString());
    }

    /**
     * 获得网络号
     *
     * @param ip   ip地址
     * @param mask 掩码
     * @return 网络号
     */
    public static int getNetworkNoToInt(String ip, int mask) {
        return toInt(ip) & toMask(mask);
    }

    public static String getNetworkNoToString(String ip, int mask) {
        return toIp(toInt(ip) & toMask(mask));
    }

    /**
     * 获得子网最小地址
     */
    public static int minIpToInt(String ip, int mask) {
        return getNetworkNoToInt(ip, mask) + 1;
    }

    /**
     * 获得子网最小地址
     */
    public static String minIpToString(String ip, int mask) {
        return toIp(minIpToInt(ip, mask));
    }

    /**
     * 获得子网最小地址
     */
    public static String minIpToString(String ip, String mask) {
        return toIp(minIpToInt(ip, toMask(mask)));
    }

    /**
     * 获得子网最大地址
     */
    public static int maxIpToInt(String ip, int mask) {
        return broadcast(ip, mask) - 1;
    }

    /**
     * 获得子网最大地址
     */

    public static String maxIpToString(String ip, int mask) {
        return toIp(maxIpToInt(ip, mask));
    }

    /**
     * 获得子网最大地址
     */
    public static String maxIpToString(String ip, String mask) {
        return toIp(maxIpToInt(ip, toMask(mask)));
    }

    /**
     * 获得子网广播地址
     */
    public static int broadcast(String ip, int mask) {
        return (~toMask(mask)) | getNetworkNoToInt(ip, mask);
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
