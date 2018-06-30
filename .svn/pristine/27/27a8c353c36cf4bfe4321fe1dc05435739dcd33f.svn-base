package framework.core.utils;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.StringTemplateResourceLoader;

import java.util.Map;

/**
 * 模板工具类,使用beetl开源工具
 */
public class BeetlUtils {

    // ================================================================
    // Constants
    // ================================================================

    // ================================================================
    // Fields
    // ================================================================

    // 模板父类，构造模板
    private static GroupTemplate groupTemplate;

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
     * 渲染模板
     *
     * @param templateString 渲染前模板数据
     * @param model          替换数据
     * @return 渲染后模板数据
     */
    public static String renderString(String templateString, Map<String, ?> model) {
        try {
            if (groupTemplate == null) {
                // 自己创建
                StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
                Configuration cfg = Configuration.defaultConfiguration();
                groupTemplate = new GroupTemplate(resourceLoader, cfg);
            }

            // 渲染字符串
            Template template = groupTemplate.getTemplate(templateString);
            template.binding(model);
            return template.render();
        } catch (Exception e) {
            throw Exceptions.unchecked(e);
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

    // ================================================================
    // Test Methods
    // ================================================================

}
