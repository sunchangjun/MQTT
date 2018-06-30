package framework.core.web;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.JavaType;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import framework.core.common.Constant;
import framework.core.utils.DateUtils;
import framework.core.utils.JsonMapper;
import framework.core.utils.StringUtils;

/**
 * 控制器支持类
 * Created by zeyuphoenix on 16/7/1.
 */
public class BaseController implements Constant {

    // ================================================================
    // Constants
    // ================================================================

    /**
     * 日志对象
     */
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * page return info key.
     */
    public static final String MESSAGE_KEY = "message";

    // ================================================================
    // Fields
    // ================================================================

    // ================================================================
    // Constructors
    // ================================================================

    // ================================================================
    // Methods from/for super Interfaces or SuperClass
    // ================================================================

    /**
     * <pre>
     * 初始化数据绑定
     * 1. 将所有传递进来的String进行HTML编码，防止XSS攻击
     * 2. 将字段中Date类型转换为String类型
     * Note: ProperteyEditor是非线程安全的。通过@InitBinder注解修饰的initBinder函数，
     *       会为每个web请求初始化一个editor实例，并通过WebDataBinder对象注册
     * </pre>
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        // String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
        binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
            }

            @Override
            public String getAsText() {
                Object value = getValue();
                return value != null ? value.toString() : "";
            }
        });
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(DateUtils.parseDate(text));
            }
            // @Override
            // public String getAsText() {
            // Object value = getValue();
            // return value != null ? DateUtils.formatDateTime((Date)value) : "";
            // }
        });
    }

    // ================================================================
    // Public or Protected Methods
    // ================================================================

    /**
     * 重定向到指定url, 简单避免收到拼接
     * @param url 需要redirect的url
     * @return 完整串
     */
    protected String redirectTo(String url) {
        StringBuilder rto = new StringBuilder("redirect:");
        rto.append(url);
        return rto.toString();
    }

    /**
     * 添加Model消息
     *
     * @param messages 消息
     */
    protected void addMessage(Model model, String... messages) {
        StringBuilder sb = new StringBuilder();
        for (String message : messages) {
            sb.append(message).append(messages.length > 1 ? "<br/>" : "");
        }
        model.addAttribute(MESSAGE_KEY, sb.toString());
    }

    /**
     * 添加Flash消息
     *
     * @param messages 消息
     */
    protected void addMessage(RedirectAttributes redirectAttributes, String... messages) {
        StringBuilder sb = new StringBuilder();
        for (String message : messages) {
            sb.append(message).append(messages.length > 1 ? "<br/>" : "");
        }
        redirectAttributes.addFlashAttribute(MESSAGE_KEY, sb.toString());
    }

    /**
     * 客户端返回JSON字符串
     */
    protected String renderString(HttpServletResponse response, Object object) {
        return renderString(response, JsonMapper.toJsonString(object), "application/json");
    }

    /**
     * 客户端返回字符串
     */
    protected String renderString(HttpServletResponse response, String string, String type) {
        try {
            response.reset();
            response.setContentType(type);
            response.setCharacterEncoding(CHARSET_UTF_8);
            response.getWriter().print(string);
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 获取查询接口中的json无法转换的key
     *
     * @param key json的key
     * @return 结果
     */
    protected String readIgnoreValue(String json, String key) {
        if (StringUtils.isNotBlank(json)) {
            JsonObject jb = new JsonParser().parse(json).getAsJsonObject();
            if (jb != null) {
                JsonElement jsonElement = jb.get(key);
                if (jsonElement != null) {
                    return jsonElement.getAsString();
                }

            }
        }
        return null;
    }




    /**
     * 因为页面需要的一些属性javabean 中不存在,转为map,增加属性
     */
    protected Map<String, Object> transEntity(Object obj, Map<String, Object> addKv) {
        JavaType javaType = JsonMapper.getInstance().createCollectionType(Map.class, String.class, Object.class);
        Map<String, Object> tran = JsonMapper.getInstance().convertValue(obj, javaType);
        tran.putAll(addKv);
        return tran;
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
