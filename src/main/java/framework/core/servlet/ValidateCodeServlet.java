package framework.core.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import framework.core.utils.VerifyCodeUtils;


/**
 * 生成随机验证码
 */
public class ValidateCodeServlet extends HttpServlet {

    // ================================================================
    // Constants
    // ================================================================

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(ValidateCodeServlet.class);

    /**
     * validate key
     */
    public static final String VALIDATE_CODE = "validateCode";

    // ================================================================
    // Fields
    // ================================================================

    private int width = 70;
    private int height = 26;

    // ================================================================
    // Constructors
    // ================================================================

    public ValidateCodeServlet() {
        super();
    }

    // ================================================================
    // Methods from/for super Interfaces or SuperClass
    // ================================================================

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String validateCode = request.getParameter(VALIDATE_CODE);
        // AJAX验证，成功返回true
        if (StringUtils.isNotBlank(validateCode)) {
            response.getOutputStream().print(validate(request, validateCode) ? "true" : "false");
        } else {
            this.doPost(request, response);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        createImage(request, response);
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    // ================================================================
    // Public or Protected Methods
    // ================================================================

    /**
     * 验证code是否一致
     */
    public static boolean validate(HttpServletRequest request, String validateCode) {
        Object code = request.getSession().getAttribute(VALIDATE_CODE);
        if (code != null) {
            return StringUtils.equalsIgnoreCase(validateCode, code.toString());
        }
        return false;
    }

    // ================================================================
    // Getter & Setter
    // ================================================================

    // ================================================================
    // Private Methods
    // ================================================================

    /**
     * 生成图片,返回前台
     */
    private void createImage(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

		/*
         * 得到参数高，宽，都为数字时，则使用设置高宽，否则使用默认值
		 */
        String wid = request.getParameter("width");
        String hei = request.getParameter("height");
        if (StringUtils.isNumeric(wid) && StringUtils.isNumeric(hei)) {
            width = NumberUtils.toInt(wid);
            height = NumberUtils.toInt(hei);
        }
        logger.debug("生成图片的宽高: {}--{}.", width, height);

		/*
         * 生成字符
		 */
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        request.getSession().setAttribute(VALIDATE_CODE, verifyCode.toLowerCase());

        OutputStream out = response.getOutputStream();
        VerifyCodeUtils.outputImage(width, height, out, verifyCode);
        out.close();
    }

    // ================================================================
    // Inner or Anonymous Class
    // ================================================================

    // ================================================================
    // Test Methods
    // ================================================================
}
