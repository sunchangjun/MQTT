package framework.core.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Pinyin4j 简繁体转拼音辅助类
 * Created by zeyuphoenix on 2017/3/1.
 */
public class Pinyin4jUtils {

    // ================================================================
    // Constants
    // ================================================================

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(Pinyin4jUtils.class);

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
     * 拼音首字母，获取第一个结果。 （北京市长:bjsz,bjsc 返回 bjsz）
     *
     * @param chines
     *            	汉字
     * @return 拼音首字母
     */
    public static String converterToFirstSpell(String chines) {
        String pinyin = converterToAllFirstSpell(chines);
        if (pinyin != null && pinyin.contains(",")) {
            return pinyin.split(",")[0];
        }
        return pinyin;
    }

    /**
     * 汉字转换位汉语拼音首字母，英文字符不变，特殊字符丢失 支持多音字。 （北京市长:bjsz,bjsc）
     *
     * @param chines
     *            	汉字
     * @return 拼音首字母
     */
    public static String converterToAllFirstSpell(String chines) {
        StringBuilder pinyinName = new StringBuilder();
        char[] nameChars = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (char nameChar : nameChars) {
            if (nameChar > 128) {
                try {
                    /* 取得当前汉字的所有全拼 */
                    String[] strs = PinyinHelper.toHanyuPinyinStringArray(nameChar, defaultFormat);
                    if (strs != null) {
                        for (int j = 0; j < strs.length; j++) {
                            // 取首字母
                            pinyinName.append(strs[j].charAt(0));
                            if (j != strs.length - 1) {
                                pinyinName.append(",");
                            }
                        }
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    logger.error("", e);
                }
            } else {
                pinyinName.append(nameChar);
            }
            pinyinName.append(" ");
        }
        return parseTheChineseByObject(discountTheChinese(pinyinName.toString()));
    }

    /**
     * 汉语全拼，获取第一个结果。（北京市长:beijingshizhang,beijingshichang 返回 beijingshizhang）
     *
     * @param chines
     *            	汉字
     * @return 拼音
     */
    public static String converterToSpell(String chines) {
        String pinyin = converterToAllSpell(chines);
        if (pinyin != null && pinyin.contains(",")) {
            return pinyin.split(",")[0];
        }
        return pinyin;
    }

    /**
     * 汉字转换位汉语全拼，英文字符不变，特殊字符丢失
     * 支持多音字，生成方式如（北京市长:beijingshizhang,beijingshichang）
     *
     * @param chines
     *            	汉字
     * @return 拼音
     */
    public static String converterToAllSpell(String chines) {
        StringBuilder pinyinName = new StringBuilder();
        char[] nameChars = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (char nameChar : nameChars) {
            if (nameChar > 128) {
                try {
                    /* 取得当前汉字的所有全拼 */
                    String[] strs = PinyinHelper.toHanyuPinyinStringArray(nameChar, defaultFormat);
                    if (strs != null) {
                        for (int j = 0; j < strs.length; j++) {
                            pinyinName.append(strs[j]);
                            if (j != strs.length - 1) {
                                pinyinName.append(",");
                            }
                        }
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    logger.error("", e);
                }
            } else {
                pinyinName.append(nameChar);
            }
            pinyinName.append(" ");
        }
        return parseTheChineseByObject(discountTheChinese(pinyinName.toString()));
    }


    /**
     * 去除多音字重复数据
     *
     * @param theStr
     * 				多音字
     */
    protected static List<Map<String, Integer>> discountTheChinese(String theStr) {
        /* 去除重复拼音后的拼音列表 */
        List<Map<String, Integer>> mapList = new ArrayList<>();
        Map<String, Integer> onlyOne;
        String[] firsts = theStr.split(" ");
        /*
         * 读出每个汉字的拼音，多音字处理
		 */
        for (String str : firsts) {
            onlyOne = new Hashtable<>();
            String[] china = str.split(",");
            for (String s : china) {
                Integer count = onlyOne.get(s);
                if (count == null) {
                    onlyOne.put(s, 1);
                } else {
                    onlyOne.remove(s);
                    count++;
                    onlyOne.put(s, count);
                }
            }
            mapList.add(onlyOne);
        }
        return mapList;
    }


    /**
     * 解析并组合拼音，对象合并方案(推荐使用)
     */
    protected static String parseTheChineseByObject(List<Map<String, Integer>> list) {
        Map<String, Integer> first = null; // 用于统计每一次,集合组合数据
        // 遍历每一组集合
        for (Map<String, Integer> aList : list) {
            // 每一组集合与上一次组合的Map
            Map<String, Integer> temp = new Hashtable<>();
            // 第一次循环，first为空
            if (first != null) {
                // 取出上次组合与此次集合的字符，并保存
                for (String s : first.keySet()) {
                    for (String s1 : aList.keySet()) {
                        String str = s + s1;
                        temp.put(str, 1);
                    }
                }
                // 清理上一次组合数据
                if (temp.size() > 0) {
                    first.clear();
                }
            } else {
                for (String s : aList.keySet()) {
                    temp.put(s, 1);
                }
            }
            // 保存组合数据以便下次循环使用
            if (temp.size() > 0) {
                first = temp;
            }
        }
        String returnStr = "";
        if (first != null) {
            // 遍历取出组合字符串
            for (String str : first.keySet()) {
                returnStr += (str + ",");
            }
        }
        if (returnStr.length() > 0) {
            returnStr = returnStr.substring(0, returnStr.length() - 1);
        }
        return returnStr;
    }

    /**
     * 将字符串转换成ASCII码
     */
    public static String getCnASCII(String cnStr) {
        StringBuilder builder = new StringBuilder();
        // 将字符串转换成字节序列
        byte[] bGBK = cnStr.getBytes();
        for (byte aBGBK : bGBK) {
            // 将每个字符转换成ASCII码
            builder.append(Integer.toHexString(aBGBK & 0xff));
        }
        return builder.toString();
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
