package framework.core.web;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * 返回对象的封装
 * Created by zeyuphoenix on 2016/3/25.
 */
public class Result implements Serializable {

    // ================================================================
    // Constants
    // ================================================================

    /**
     * UID
     */
    @JsonIgnore
    private static final long serialVersionUID = 1L;

    /**
     * 返回结果状态值
     */
    public static final String SUCCESS = "success";
    public static final String ERROR = "error";
    public static final String WARNING = "warning";
    public static final String INFO = "info";

    // ================================================================
    // Fields
    // ================================================================

    // 状态值
    private String status = SUCCESS;
    // 信息
    private String message = "";
    // 具体内容
    private Object data;

    // ================================================================
    // Constructors
    // ================================================================

    /**
     * Constructor
     */
    public Result() {
    }

    /**
     * Constructor
     */
    public Result(String status) {
        this.status = status;
    }

    /**
     * Constructor
     */
    public Result(String status, String message) {
        this.status = status;
        this.message = message;
    }

    /**
     * Constructor
     */
    public Result(String status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    // ================================================================
    // Methods from/for super Interfaces or SuperClass
    // ================================================================

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    // ================================================================
    // Public or Protected Methods
    // ================================================================

    // ================================================================
    // Getter & Setter
    // ================================================================

    /**
     * 获取状态值
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态值
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取信息
     */
    public String getMessage() {
        return message;
    }

    /**
     * 设置信息
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 获取数据
     */
    public Object getData() {
        return data;
    }

    /**
     * 设置数据
     */
    public void setData(Object data) {
        this.data = data;
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
