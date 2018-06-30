package framework.core.function;

import org.beetl.core.Context;
import org.beetl.core.Function;
import org.springframework.stereotype.Service;

import framework.core.utils.MessageUtils;


@Service
public class I18n implements Function {

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

    @Override
    public Object call(Object[] objects, Context context) {
        // 参数为空
        if (objects == null || objects.length == 0) {
            return "";
        }
        // key为空
        if (objects[0] == null) {
            return "";
        }
        if (objects.length == 1) {
            // 无参数
            return MessageUtils.getInstance().getMessage(objects[0].toString());
        } else {
            // 有参数
            Object[] args = new Object[objects.length - 1];
            System.arraycopy(objects, 1, args, 0, objects.length - 1);
            return MessageUtils.getInstance().getMessage(objects[0].toString(), args);
        }
    }

    // ================================================================
    // Public or Protected Methods
    // ================================================================

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
