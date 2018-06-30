package framework.core.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

/**
 * 基于Spring的message工具类
 */
public class MessageUtils {

    // ================================================================
    // Constants
    // ================================================================

    // ================================================================
    // Fields
    // ================================================================

    /**
     * 使用类初始化方法,避免同步
     */
    private static class SingletonHolder {
        // inner class.
        public final static MessageUtils instance = new MessageUtils();
    }

    // ================================================================
    // Constructors
    // ================================================================

    /**
     * The Constructors Method.
     */
    private MessageUtils() {
    }

    // ================================================================
    // Methods from/for super Interfaces or SuperClass
    // ================================================================

    // ================================================================
    // Public or Protected Methods
    // ================================================================

    /**
     * get Message
     *
     * @param key properties key
     * @return the message
     */
    public String getMessage(String key) {
        return getMessage(key, null, null, getLocale());
    }

    /**
     * get Message
     *
     * @return the message
     */
    public String getMessage(String key, String defaultMessage) {
        return getMessage(key, null, defaultMessage);
    }

    /**
     * get Message
     *
     * @param key  properties key
     * @param args the parameters
     * @return the message
     */
    public String getMessage(String key, Object[] args) {

        ApplicationContext actx = SpringContextHolder.getApplicationContext();

        String value = actx.getMessage(key, args, getLocale());

        if (StringUtils.isEmpty(value)) {
            value = key;
        }
        return value;
    }

    /**
     * get Message
     *
     * @return the message
     */
    public String getMessage(String key, Object[] args, String defaultMessage) {

        ApplicationContext actx = SpringContextHolder.getApplicationContext();

        String value = actx.getMessage(key, args, defaultMessage, getLocale());

        if (StringUtils.isEmpty(value)) {
            value = key;
        }
        return value;
    }

    /**
     * get Message
     *
     * @param key properties key
     * @param args the parameters
     * @return the message
     */
    public String getMessage(String key, Object[] args, Locale locale) {

        ApplicationContext actx = SpringContextHolder.getApplicationContext();

        String value = actx.getMessage(key, args, locale);

        if (StringUtils.isEmpty(value)) {
            value = key;
        }
        return value;
    }

    /**
     * get Message
     *
     * @return the message
     */
    public String getMessage(String key, Object[] args, String defaultMessage, Locale locale) {

        ApplicationContext actx = SpringContextHolder.getApplicationContext();

        String value = actx.getMessage(key, args, defaultMessage, locale);

        if (StringUtils.isEmpty(value)) {
            value = key;
        }
        return value;
    }

    /**
     * get the Locale info
     *
     * @return the Locale info
     */
    public Locale getLocale() {

        try {
            HttpServletRequest request = getHttpServletRequest();

            LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);

            return localeResolver.resolveLocale(request);
        } catch (Exception e) {
            return Locale.US;
        }
    }

    /**
     * getIELanguage
     */
    public String getIELanguage() {

        return getHttpServletRequest().getLocale().getLanguage();
    }

    /**
     * get the request.
     *
     * @return the request
     */
    public HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes != null) {
            return servletRequestAttributes.getRequest();
        } else {
            return null;
        }
    }

    // ================================================================
    // Getter & Setter
    // ================================================================

    /**
     * get the single instance of MessageManager.
     *
     * @return the single instance
     */
    public static MessageUtils getInstance() {
        return SingletonHolder.instance;
    }

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
